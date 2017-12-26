package com.grigoryev.sockets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.function.Consumer;

/**
 * Class represents dispatcher
 * for server commands. Implements
 * dispatcher pattern.
 *
 * @author vgrigoryev
 * @since 23.12.2017
 */
public class CommandDispatcher {
    /**
     * Stores commands as keys and corresponding functions as values.
     */
    private final Map<Command, Consumer<List<String>>> dispatch = new HashMap<>();

    /**
     * Logger.
     */
    private static final Logger DISPATCHER_LOGGER = LoggerFactory.getLogger(CommandDispatcher.class);

    /**
     * Writes responses to the client.
     */
    private final PrintWriter serverWriter;

    /**
     * Root path of file system provided by server.
     */
    private Path rootPath;

    /**
     * Current path requested by client.
     */
    private Path currentPath;

    /**
     * Constructor.
     *
     * @param writer writer to the client
     * @param rootPath server file system root path
     */
    public CommandDispatcher(PrintWriter writer, Path rootPath) {
        this.serverWriter = writer;
        this.rootPath = rootPath;
        this.currentPath = rootPath;
    }

    /**
     * Initializes command dispatcher.
     * Loads commands and corresponding handlers.
     *
     * @return initialized command dispatcher
     */
    public CommandDispatcher init() {
        this.load(Command.DIRLIST, this.getCurrentDirectoryList());
        this.load(Command.SUBDIR, this.toSubDirectory());
        this.load(Command.PARENT, this.toParentDirectory());
        this.load(Command.DOWNLOAD, this.downLoadFile());
        this.load(Command.LOAD, this.loadFile());
        this.load(Command.EXIT, this.exitServer());
        return this;
    }

    /**
     * Loads command and corresponding handler.
     *
     * @param command command
     * @param handle  command handler
     */
    void load(Command command, Consumer<List<String>> handle) {
        this.dispatch.put(command, handle);
    }

    /**
     * Runs handler determined
     * by command.
     *
     * @param command command from client
     */
    public void perform(String command) {
        String[] commandTokens = command.split(" ");
        Consumer<List<String>> clientCommand = this.dispatch.get(this.toCommand(commandTokens[0]));
        List<String> paths = new ArrayList<>();
        paths.addAll(Arrays.asList(commandTokens).subList(1, commandTokens.length));
        if (clientCommand != null) {
            clientCommand.accept(paths);
        } else {
            throw new IllegalStateException("Could not find a handle for command");
        }
    }

    /**
     * Converts string to command enum value
     * if matches.
     *
     * @param command specified action in string representation
     * @return command which corresponds to one of the loaded actions
     */
    public Command toCommand(String command) {
        return Command.valueOf(command.trim().toUpperCase());
    }

    /**
     * Gets list of directories and files
     * in the current directory.
     *
     * @return function which sends names of subdirectories to the client
     */
    Consumer<List<String>> getCurrentDirectoryList() {
        return (paths) -> {
            try (DirectoryStream<Path> entries = Files.newDirectoryStream(this.rootPath.resolve(this.currentPath))) {
                for (Path entry: entries) {
                    String fileName = entry.getFileName().toString();
                    if (Files.isDirectory(entry)) {
                        fileName = String.format("%s DIR", fileName);
                    }
                    serverWriter.println(fileName);
                }
                serverWriter.println();
            } catch (IOException e) {
                DISPATCHER_LOGGER.error(e.getMessage());
            }
        };
    }

    /**
     * Changes directory. Sets current directory
     * to the one of the current subdirectories.
     *
     * @return function which sends
     * to the client new current directory path
     * in string representation
     */
    Consumer<List<String>> toSubDirectory() {
        return (paths) -> {
            String subDir = paths.get(0);
            try (DirectoryStream<Path> entries = Files.newDirectoryStream(this.currentPath)) {
                boolean exists = false;
                for (Path entry: entries) {
                    String fileName = entry.getFileName().toString();
                    if (Files.isDirectory(entry)) {
                        if (subDir.equals(fileName)) {
                            this.currentPath = this.currentPath.resolve(subDir);
                            exists = true;
                            break;
                        }
                    }
                }
                if (exists) {
                    serverWriter.println(this.rootPath.getParent().relativize(this.currentPath).normalize());
                } else {
                    serverWriter.println("There is no such directory");
                }
                serverWriter.println();
            } catch (IOException e) {
                DISPATCHER_LOGGER.error(e.getMessage());
            }
        };
    }

    /**
     * Changes directory. Sets parent directory of the current
     * directory if current directory is not already root.
     *
     * @return function which sends
     * to the client new current directory path
     * in string representation
     */
    Consumer<List<String>> toParentDirectory() {
        return (paths) -> {
                if (!this.rootPath.equals(this.currentPath)) {
                    this.currentPath = this.currentPath.getParent();
                    String path = String.format("%1$s\\", this.rootPath.getParent().relativize(this.currentPath).normalize().toString());
                    serverWriter.println(path);
                } else {
                    serverWriter.println("You are in the root");
                }
                serverWriter.println();
        };
    }

    /**
     * Downloads specified file if it exists
     * in the current directory. File is downloaded
     * to the client directory which is specified as
     * command arguments.
     *
     * @return function which sends
     * to the client status of the operation
     */
    Consumer<List<String>> downLoadFile() {
        return (paths) -> {
            try (DirectoryStream<Path> entries = Files.newDirectoryStream(this.rootPath.resolve(this.currentPath))) {
                String file = paths.get(0);
                String targetLocation = paths.get(1);
                Path source = this.currentPath;
                boolean exists = false;
                for (Path entry: entries) {
                    String test = entry.toString();
                    if (Files.isRegularFile(entry)) {
                        if (entry.getFileName().toString().equals(file)) {
                            exists = true;
                            source = entry;
                            break;
                        }
                    }
                }
                if (exists) {
                    Path targetFile = Paths.get(String.format("%1$s\\%2$s", targetLocation, file));
                    Files.copy(source, targetFile, StandardCopyOption.REPLACE_EXISTING);
                    serverWriter.println("File is downloaded");
                } else {
                    serverWriter.println("File does not exist");
                }
                serverWriter.println();
            } catch (IOException e) {
                DISPATCHER_LOGGER.error(e.getMessage());
            }
        };
    }

    /**
     * Loads specified file to if it exists
     * in the current directory. File is sent
     * through socket connected to client.
     *
     * @return function which sends
     * to the client file content.
     */
    Consumer<List<String>> loadFile() {
        return (paths) -> {
            try (DirectoryStream<Path> entries = Files.newDirectoryStream(this.rootPath.resolve(this.currentPath))) {
                String file = paths.get(0);
                Path source = this.currentPath;
                boolean exists = false;
                for (Path entry: entries) {
                    if (Files.isRegularFile(entry)) {
                        if (entry.getFileName().toString().equals(file)) {
                            exists = true;
                            source = entry;
                            break;
                        }
                    }
                }
                if (exists) {
                    String content = new String(Files.readAllBytes(source));
                    serverWriter.println(content);
                } else {
                    serverWriter.println("File does not exist");
                }
                serverWriter.println();
            } catch (IOException e) {
                DISPATCHER_LOGGER.error(e.getMessage());
            }
        };
    }

    /**
     * Exits the server.
     *
     * @return function which sends
     * to the client empty string.
     */
    Consumer<List<String>> exitServer() {
        return (paths) -> serverWriter.println("");
    }
}
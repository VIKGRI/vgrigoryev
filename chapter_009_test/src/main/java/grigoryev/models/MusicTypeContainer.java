package grigoryev.models;

import grigoryev.dao.DatabaseDAOException;
import grigoryev.dao.implement.MusicTypeDatabaseDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Container holds single object
 * for each music type.
 *
 * @author vgrigoryev
 * @version 1
 * @since 01.12.2017
 */
public class MusicTypeContainer {
    /**
     * Single instance.
     */
    private static final MusicTypeContainer INSTANCE = new MusicTypeContainer();
    /**
     * Logger.
     */
    private static final Logger MUSIC_TYPE_CONTAINER_LOGGER = LoggerFactory.getLogger(MusicTypeContainer.class);
    /**
     * All music types available in the app.
     */
    private Map<String, MusicType> musicTypes = new HashMap<>();

    /**
     * Constructor.
     */
    private MusicTypeContainer() {
        try {
            for (MusicType type: MusicTypeDatabaseDao.getInstance().selectAll()) {
                musicTypes.put(type.getTitle().toLowerCase(), type);
            }
        } catch (DatabaseDAOException e) {
            MUSIC_TYPE_CONTAINER_LOGGER.error(e.getMessage(), e);
        }
    }

    /**
     * Checks whether new music types've been added in the database before retrieve the instance.
     */
    private void updateMusicTypesState() {
        try {
            for (MusicType type: MusicTypeDatabaseDao.getInstance().selectAll()) {
                musicTypes.putIfAbsent(type.getTitle().toLowerCase(), type);
            }
        } catch (DatabaseDAOException e) {
            MUSIC_TYPE_CONTAINER_LOGGER.error(e.getMessage(), e);
        }
    }
    /**
     * Gets instance.
     * @return instance of MusicTypeContainer
     */
    public static MusicTypeContainer getInstance() {
        //INSTANCE.updateMusicTypesState();
        return INSTANCE;
    }

    /**
     * Gets music type by it's name if such a music type exists in the app.
     * @param musicTypeName specified music type name
     * @return Music type with the specified name or null
     */
    public MusicType getMusicType(String musicTypeName) {
        return this.musicTypes.get(musicTypeName.toLowerCase());
    }

    /**
     * Gets titles of all music types in the app.
     * @return titles of all music types
     */
    public List<String> getMusicTypeNames() {
        return new ArrayList<>(this.musicTypes.keySet());
    }
}

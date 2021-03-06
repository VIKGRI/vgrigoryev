package grigoryev.controllers.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import grigoryev.dao.DatabaseDAOException;
import grigoryev.dao.implement.MusicTypeDatabaseDao;
import grigoryev.models.MusicType;
import org.slf4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Constructs json response.
 *
 * @author vgrigoryev
 * @version 1
 * @since 28.11.2017
 */
public class JsonController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Logger logger = (Logger) req.getServletContext().getAttribute("appLogger");
        try {
            resp.setContentType("application/json");
            PrintWriter writer = resp.getWriter();
            List<String> musicTypes = new ArrayList<>();

            for (MusicType type : MusicTypeDatabaseDao.getInstance().selectAll()) {
                musicTypes.add(type.getTitle());
            }

            Map<String, List<String>> objects = new HashMap<>();
            objects.put("musicTypes", musicTypes);
            ObjectMapper mapper = new ObjectMapper();
            writer.append(mapper.writeValueAsString(objects));
            writer.flush();
        } catch (DatabaseDAOException e) {
            logger.error(e.getMessage(), e);
        }
    }
}

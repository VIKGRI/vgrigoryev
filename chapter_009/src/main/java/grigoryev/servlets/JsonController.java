package grigoryev.servlets;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
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
            List<String> cities = UserStorage.USER_STORAGE.getCities();
            List<String> countries = UserStorage.USER_STORAGE.getCountries();

            Map<String, List<String>> objects = new HashMap<>();
            objects.put("cities", cities);
            objects.put("countries", countries);
            ObjectMapper mapper = new ObjectMapper();

            writer.append(mapper.writeValueAsString(objects));
            writer.flush();
        } catch (JsonGenerationException | JsonMappingException | UserStorageDAOException e) {
            logger.error(e.getMessage(), e);
        }
    }
}


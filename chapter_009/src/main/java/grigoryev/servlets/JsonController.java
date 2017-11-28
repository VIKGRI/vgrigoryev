package grigoryev.servlets;

import org.slf4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

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

            writer.append("{");
            writer.append("\"cities\": [");
            int i = 0;
            for (String city : cities) {
                writer.append("\"" + city + "\"");
                if (i != cities.size() - 1) {
                    writer.append(",");
                }
                i++;
            }
            writer.append("],");

            writer.append("\"countries\": [");
            i = 0;
            for (String country : countries) {
                writer.append("\"" + country + "\"");
                if (i != countries.size() - 1) {
                    writer.append(",");
                }
                i++;
            }
            writer.append("]");
            writer.append("}");
            writer.flush();
        } catch (UserStorageDAOException e) {
            logger.error(e.getMessage(), e);
        }
    }
}


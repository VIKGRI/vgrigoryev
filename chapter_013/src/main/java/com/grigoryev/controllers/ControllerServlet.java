package com.grigoryev.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.grigoryev.models.Item;
import com.grigoryev.dao.DatabaseDAOException;
import com.grigoryev.dao.impl.ItemDataBaseDao;
import org.slf4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.List;

/**
 * Provides adding new item and selecting
 * all items.
 *
 * @author vgrigoryev
 * @version 1
 * @since 02.01.2018
 */
public class ControllerServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Logger logger = (Logger) req.getServletContext().getAttribute("appLogger");
        String showAll = req.getParameter("showAll");
        try {
            PrintWriter writer = resp.getWriter();
            resp.setContentType("application/json");
            List<Item> items = null;
            if (showAll == null || showAll.equals("true")) {
                items = ItemDataBaseDao.getInstance().selectAll();
            } else {
                items = ItemDataBaseDao.getInstance().selectAllUndone();
            }
            ObjectMapper mapper = new ObjectMapper();
            writer.append(mapper.writeValueAsString(items));
            writer.flush();
        } catch (DatabaseDAOException ex) {
            logger.error(ex.getMessage(), ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Logger logger = (Logger) req.getServletContext().getAttribute("appLogger");
        String id = req.getParameter("desc");
        try {
            Item item = new Item();
            item.setDesc(id);
            item.setCreated(new Timestamp(System.currentTimeMillis()));
            item.setDone(false);
            ItemDataBaseDao.getInstance().insert(item);
            this.doGet(req, resp);
        } catch (DatabaseDAOException ex) {
            logger.error(ex.getMessage(), ex);
        }
    }
}

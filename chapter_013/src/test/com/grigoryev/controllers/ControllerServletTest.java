package com.grigoryev.controllers;

import com.grigoryev.models.Item;
import com.grigoryev.services.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import static org.junit.Assert.*;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Provides testing for ControllerServlet's methods.
 *
 * @author vgrigoryev
 * @version 1
 * @since 04.01.2018
 */
public class ControllerServletTest {
    /**
     * Test adding item to the database.
     * @throws ServletException servlet exception
     * @throws IOException io exception
     */
    @Test
    public void whenAddItemWhenFetchItFromDataBase() throws ServletException, IOException {

        ControllerServlet controller = new ControllerServlet();
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        ServletContext context = mock(ServletContext.class);
        Logger logger = LoggerFactory.getLogger(ControllerServletTest.class);
        PrintWriter writer = mock(PrintWriter.class);
        when(request.getParameter("desc")).thenReturn("Description 1025");
        when(request.getParameter("showAll")).thenReturn("true");
        when(request.getServletContext()).thenReturn(context);
        when(context.getAttribute("appLogger")).thenReturn(logger);
        when(response.getWriter()).thenReturn(writer);

        controller.doPost(request, response);
        List<Item> items = null;
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Query query = session.createQuery("FROM Item I ORDER BY I.created DESC");
        items = query.list();
        session.getTransaction().commit();

        assertThat(items.get(0).getDesc(), is("Description 1025"));
    }
}
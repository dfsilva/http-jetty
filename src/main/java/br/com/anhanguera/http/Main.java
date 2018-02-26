package br.com.anhanguera.http;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.resource.Resource;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.net.URL;

public class Main {

    public static void main(String[] args) throws Exception {

        Server server = new Server(8080);

        ServletContextHandler servletHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        servletHandler.setContextPath("/");
        servletHandler.setBaseResource(Resource.newResource(Main.class.getClassLoader().getResource("html/").toURI()));
        servletHandler.setWelcomeFiles(new String[] { "index.html" });

        ServletHolder holderPwd = new ServletHolder("default", DefaultServlet.class);
        holderPwd.setInitParameter("dirAllowed", "true");
        servletHandler.addServlet(holderPwd, "/");

        servletHandler.addServlet(new ServletHolder(new HelloServlet()), "/servlet1");

        server.setHandler(servletHandler);

        server.start();
        server.join();
    }

    static class HelloServlet extends HttpServlet {
        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            response.setContentType("text/html");
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().println("<h1>" + " ola " + "</h1>");
            response.getWriter().println("session=" + request.getSession(true).getId());
        }
    }
}

package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class JMSClientServlet
 */
@WebServlet("/JMSClientServlet")
public class JMSClientServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Context ic = null;
    ConnectionFactory cf = null;
    Connection connection = null;
	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		try {
            ic = new InitialContext();
            cf = (ConnectionFactory) ic.lookup("/ConnectionFactory");
        } catch (NamingException e) {
            e.printStackTrace();
        }
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String destinationName = "java:/queue/test";
        PrintWriter out = response.getWriter();
 
        try {
 
            connection = cf.createConnection();
 
            Queue queue = (Queue) ic.lookup(destinationName);
 
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            MessageProducer publisher = session.createProducer(queue);
 
            connection.start();
            TextMessage message = session.createTextMessage(request.getParameter("message"));
            // publish the message to the defined Queue
            publisher.send(message);
 
            out.println("---------Message sent to  the JMS Provider--------");
 
        } catch (Exception exc) {
            exc.printStackTrace();
        } finally {
 
            if (connection != null) {
                try {
                    connection.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

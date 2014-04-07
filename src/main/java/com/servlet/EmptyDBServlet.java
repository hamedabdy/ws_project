package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EmptyDBServlet extends HttpServlet {

	private static final long serialVersionUID = -4355292591525154809L;

	public EmptyDBServlet() {
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		PrintWriter out = res.getWriter();
		res.setContentType("text/html");

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("manager1");
		EntityManager em = emf.createEntityManager();

		em.getTransaction().begin();

		// Delete all entries from DB
		int deletedCount = em.createQuery("DELETE FROM AddConcerts").executeUpdate();
		System.out.println("delete entries : " + deletedCount);

		out.println("Database cleared! - All data removed! <br>");
		out.println("<br><a href='/projet' >back</a>");

		em.close();
		out.close();

	}

}

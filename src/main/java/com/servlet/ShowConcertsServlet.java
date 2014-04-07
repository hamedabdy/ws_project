package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.db.AddConcerts;

public class ShowConcertsServlet extends HttpServlet {

	private static final long serialVersionUID = -329415198111187287L;

	public ShowConcertsServlet() {
		// TODO Auto-generated constructor stub
	}
	
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		PrintWriter out = res.getWriter();
		res.setContentType("text/html");
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("manager1");
		EntityManager em = emf.createEntityManager();
		
		em.getTransaction().begin();
		
		List<AddConcerts> concerts= em.createQuery("select c from AddConcerts c").getResultList();
		for(int i=0; i<concerts.size(); i++){
			//System.out.println(concerts.get(i).getId());
			out.println("ID : " + concerts.get(i).getId());
			out.println(" -- TITLE : " + concerts.get(i).getTitle());
			out.println(" -- ARTISTS : " + concerts.get(i).getArtists());
			out.println("<br>");
		}
		
		out.println("<br><a href='/projet' >back</a>");
		em.close();
		out.close();
	}

}

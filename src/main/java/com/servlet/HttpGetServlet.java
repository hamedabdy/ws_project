package com.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.persistence.exceptions.DatabaseException;
import org.json.JSONObject;

import com.db.AddConcerts;


public class HttpGetServlet extends HttpServlet {

	private static final long serialVersionUID = 7030489847145729915L;

	public HttpGetServlet() {}

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		PrintWriter out = res.getWriter();
		res.setContentType("text/html");

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("manager1");
		EntityManager em = emf.createEntityManager();

		String s = "http://ws.audioscrobbler.com/2.0/?method=geo.getevents&location=paris&limit=10"
				+ "&api_key=dbc287366d92998e7f5fb5ba6fb7e7f1&format=json";
		URL url = new URL(s);
		InputStream response = url.openStream();
		BufferedReader br = new BufferedReader( new InputStreamReader(response));
		String line = "";
		String results = "";
		while((line = br.readLine()) !=null) {
			//System.out.println(line);
			results += line;
		}

		System.out.println( " -----------------------------------------");
		JSONObject jo = new JSONObject(results);
		int taille = jo.getJSONObject("events").getJSONArray("event").length();
		String title = "";
		String id = "";
		String artists = "";
		for(int i=0; i<taille; i++) {
			JSONObject jo2 = (JSONObject) jo.getJSONObject("events").getJSONArray("event").get(i);
			id = jo2.get("id").toString();
			title = jo2.get("title").toString();
			artists = jo2.getJSONObject("artists").get("artist").toString();
			System.out.println("id: " + id + " title: " + title + " artist: " + artists);
			out.println("id: " + id + " title: " + title + "<br>");

			// Adding to DB
			AddConcerts addToDb = new AddConcerts();
			em.getTransaction().begin();
			addToDb.setId(Long.parseLong(id));
			addToDb.setTitle(title);
			addToDb.setArtists(artists);
			em.persist(addToDb);
			em.getTransaction().commit();
		}
		out.println("<br><a href='/projet' >back</a>");
		em.close();
		out.close();

	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {}

}

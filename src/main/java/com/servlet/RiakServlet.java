package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.basho.riak.client.IRiakClient;
import com.basho.riak.client.IRiakObject;
import com.basho.riak.client.RiakException;
import com.basho.riak.client.RiakFactory;
import com.basho.riak.client.bucket.Bucket;
import com.db.AddConcerts;

/**
 * Servlet implementation class RiakServlet
 */
public class RiakServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RiakServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		
		String myData = "this is my data";
		IRiakClient riakClient;
		IRiakObject myObject;
		//JSONObject jo;
		String value, key;
		AddConcerts concert = new AddConcerts();
		concert.setId(10);
		concert.setTitle("someTitle");
		concert.setArtists("someArtists");
		try {
			
			// initialize riak 
			riakClient = RiakFactory.httpClient();
			// Create or fetch a Bucket
			Bucket myBucket = riakClient.fetchBucket("TestBucket").execute();
			//store data on bucket under key name "TestKey"
			myBucket.store("TestKey", myData).execute();
			
			//fetch data frome riak
			myObject = myBucket.fetch("TestKey").execute();
			key = (new JSONObject(myObject).getString("key")).toString();
			value = (new JSONObject(myObject).getString("valueAsString")).toString();
			System.out.println("myobject : { key: " + key + ", value: " + value + " }");
			out.println("myobject : { key: " + key + ", value: " + value + " }" + "<br><br>");
			
			myBucket.store("concerts", concert).execute();
			myObject = myBucket.fetch("concerts").execute();
			key = (new JSONObject(myObject).getString("key")).toString();
			value = (new JSONObject(myObject).getString("valueAsString")).toString();
			System.out.println("myobject : { key: " + key + ", value: " + value + " }");
			out.println("myobject : { key: " + key + ", value: " + value + " }" + "<br><br>");
			
			
			// Close riak connection
			riakClient.shutdown();
		} catch (RiakException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}

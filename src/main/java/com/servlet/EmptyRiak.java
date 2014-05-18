package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import com.basho.riak.client.IRiakClient;
import com.basho.riak.client.RiakException;
import com.basho.riak.client.RiakFactory;
import com.basho.riak.client.bucket.Bucket;


public class EmptyRiak extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		PrintWriter out = response.getWriter();
		response.setContentType("text/html");

		out.println("<a href='/projet'>Go back </a><br>");

		IRiakClient riakClient;

		try {
			// initialize riak 
			riakClient = RiakFactory.httpClient();
			// Store a Video into riak -->
			Bucket videoBucket = riakClient.fetchBucket("Videos").execute();
			// Show all keys (entries) in Riak
			JSONObject jo = new JSONObject(videoBucket.keys());
			JSONArray jArray = jo.getJSONArray("all");
			for(int i=0; i<jArray.length(); i++){
				// Deleting all of the keys (entries)
				videoBucket.delete(jArray.getString(i)).execute();
				out.println("deleted: " + jArray.getString(i) + "<br>");
			}
			// Close riak connection
			riakClient.shutdown();
		} catch (RiakException e) {
			e.printStackTrace();
		}

	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}

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
import com.basho.riak.client.IRiakObject;
import com.basho.riak.client.RiakException;
import com.basho.riak.client.RiakFactory;
import com.basho.riak.client.bucket.Bucket;


public class GetVideoFromDb extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// get PWD
		//System.out.println(System.getProperty("user.dir"));
		// get $HOME
		//System.out.println(System.getProperty("user.home"));
		
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		
		out.println("<a href='/projet'>Go back </a> &nbsp &nbsp <a href='/projet/emptyRiak'>Empty Riak</a><br><br><br>");

		IRiakClient riakClient;
		IRiakObject myObject;
		try {
			// initialize riak 
			riakClient = RiakFactory.httpClient();
			// Store a Video into riak -->
			Bucket videoBucket = riakClient.fetchBucket("Videos").execute();
			// Show all keys (entries) in Riak
			JSONObject jo = new JSONObject(videoBucket.keys());
			JSONArray jArray = jo.getJSONArray("all");
			for(int i=0; i<jArray.length(); i++){
				
				myObject = videoBucket.fetch(jArray.getString(i)).execute();
				JSONObject jo2 = new JSONObject(myObject.getValueAsString());
				//out.println("<br>" + jArray.get(i) + " -- " + jo2.get("fileName") + " -- " + jo2.get("filePath"));
				out.println("<a href=uploads/"+ jo2.get("fileName") 
						+ " title='VideoId: " + jArray.get(i) + "'>" 
						+ "Download - " + jo2.get("fileName") + "</a><br>"); 
				out.println("<video controls preload='auto' width='620'	height='349'>");
				out.println("<source src='uploads/" + jo2.get("fileName") +  "' type='video/webm'>"
						+ "<source src='uploads/" + jo2.get("fileName") +  "' type='video/ogg'>"
						+ "<source src='uploads/" + jo2.get("fileName") +  "' type='video/mp4'>"
						+ "<source src='uploads/" + jo2.get("fileName") +  "' type='video/flv'>"
						+ "</video><br><br>");
			}
			
			// Close riak connection
			riakClient.shutdown();
		} catch (RiakException e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	}

}

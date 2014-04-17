package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

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

/**
 * Servlet implementation class GetVideoFromDb
 */
public class GetVideoFromDb extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GetVideoFromDb() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		PrintWriter out = response.getWriter();
		response.setContentType("text/html");

		IRiakClient riakClient;
		IRiakObject myObject;
		//String value, key;

		try {
			// initialize riak 
			riakClient = RiakFactory.httpClient();
			// Store a Video into riak -->
			Bucket videoBucket = riakClient.fetchBucket("Videos").execute();
			//myObject = videoBucket.fetch(String.valueOf(v.getId())).execute();
			//key = (new JSONObject(myObject).getString("key")).toString();
			//value = (new JSONObject(myObject).getString("valueAsString")).toString();
			//System.out.println("myobject : { key: " + key + ", value: " + value + " }");
			//out.println("myobject : { key: " + key + ", value: " + value + " }" + "<br><br>");
			
			// Show all keys (entries) in Riak
			JSONObject jo = new JSONObject(videoBucket.keys());
			JSONArray jArray = jo.getJSONArray("all");
			List<String> idLList = new ArrayList<String>();
			for(int i=0; i<jArray.length(); i++){
				idLList.add((String) jArray.get(i));
				myObject = videoBucket.fetch(jArray.getString(i)).execute();
				JSONObject jo2 = new JSONObject(myObject.getValueAsString());
				//System.out.println(jArray.get(i) + " -- " + jo2.get("fileName") + " -- " + jo2.get("filePath"));
				out.println("<br>" + jArray.get(i) + " -- " + jo2.get("fileName") + " -- " + jo2.get("filePath"));
				out.println("<a href=uploads/" + jo2.get("fileName") 
						+ " title='VideoId: " + jArray.get(i) + "'>" 
						+ jo2.get("fileName") + "</a>");
			}
			
			
			//System.out.println(jArray.length() + "\n " + jArray.toString());
			//System.out.println("All keys: " + (new JSONObject(videoBucket.keys())).toString());
			//out.println("All keys: " + (new JSONObject(videoBucket.keys())).toString() + "<br><br>");
			// <--

			// Close riak connection
			riakClient.shutdown();
		} catch (RiakException e) {
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

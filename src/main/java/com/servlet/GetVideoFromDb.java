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
import com.db.Video;

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
		//JSONObject jo;
		String value, key;

		Video v = new Video();

		try {
			// initialize riak 
			riakClient = RiakFactory.httpClient();
			// Store a video into riak -->
			Bucket videoBucket = riakClient.fetchBucket("Videos").execute();
			myObject = videoBucket.fetch(String.valueOf(v.getId())).execute();
			key = (new JSONObject(myObject).getString("key")).toString();
			value = (new JSONObject(myObject).getString("valueAsString")).toString();
			System.out.println("myobject : { key: " + key + ", value: " + value + " }");
			out.println("myobject : { key: " + key + ", value: " + value + " }" + "<br><br>");
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

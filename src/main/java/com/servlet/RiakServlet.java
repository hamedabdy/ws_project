package com.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
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
import com.db.Video;

/**
 * Servlet implementation class RiakServlet
 */
@MultipartConfig
public class RiakServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RiakServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {

		
		File myFile = new File("/Users/hamed/Desktop/ossc/videoplayer/small.webm");
		System.out.println("file exists? " + myFile.exists());
		System.out.println("file:  " + myFile.getAbsolutePath() + " \n " + myFile.getCanonicalPath() + " \n " 
		+ myFile.getName() + " \n " + myFile.getPath() + " \n " + myFile.toURI()
		+ "\n" );
		
		
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
		
		Video v = new Video();
		v.setFilePath("/Users/hamed/Desktop/ossc/videoplayer/small.webm");
		v.setFileName("small.webm");
		v.setDescription("small webm video");
		v.setUploadDate(new Date());
		
		try {
			
			// initialize riak 
			riakClient = RiakFactory.httpClient();
			// Create or fetch a Bucket
			Bucket myBucket = riakClient.fetchBucket("TestBucket").execute();
			//store data on bucket under key name "TestKey"
			myBucket.store("TestKey", myData).execute();
			//fetch data from riak  -->
			myObject = myBucket.fetch("TestKey").execute();
			key = (new JSONObject(myObject).getString("key")).toString();
			value = (new JSONObject(myObject).getString("valueAsString")).toString();
			System.out.println("myobject : { key: " + key + ", value: " + value + " }");
			out.println("myobject : { key: " + key + ", value: " + value + " }" + "<br><br>");
			// <--
			// store a concert into riak -->
			myBucket.store("concerts", concert).execute();
			myObject = myBucket.fetch("concerts").execute();
			key = (new JSONObject(myObject).getString("key")).toString();
			value = (new JSONObject(myObject).getString("valueAsString")).toString();
			System.out.println("myobject : { key: " + key + ", value: " + value + " }");
			out.println("myobject : { key: " + key + ", value: " + value + " }" + "<br><br>");
			// <--
			// Store a video into riak -->
			myBucket.store("videos", v).execute();
			myObject = myBucket.fetch("videos").execute();
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

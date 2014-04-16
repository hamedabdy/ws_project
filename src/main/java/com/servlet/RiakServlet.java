package com.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

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
		v.generateId();
		v.setFilePath("/Users/hamed/Desktop/ossc/videoplayer/small.webm");
		v.setFileName("small");
		v.setFileFormat("webm");
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
			Bucket videoBucket = riakClient.fetchBucket("Videos").execute();
			videoBucket.store(String.valueOf(v.getId()),v).execute();
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

		String videoTitle = request.getParameter("video-title");
		System.out.println("videoTitle:  " + videoTitle);
		Part filePart = request.getPart("video");
		String fileName = getFilename(filePart);
		System.out.println("file name  : "  + fileName);
		InputStream fileContent = filePart.getInputStream();
		String pathToFile = writeToDisk(fileContent, fileName, "", "/Users/hamed/Desktop/ossc/uploads/");
		
		

		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		out.println("<p>Video sent to server :)</p>");
		out.println("<a href='index.html'>Go Back</a>");

	}

	private static String getFilename(Part part) {
		for (String cd : part.getHeader("content-disposition").split(";")) {
			if (cd.trim().startsWith("filename")) {
				String filename = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
				return filename.substring(filename.lastIndexOf('/') + 1).substring(filename.lastIndexOf('\\') + 1); // MSIE fix.
			}
		}
		return null;
	}

	private String writeToDisk(InputStream inputStream, String filename, String VideoTitle, String filePath) 
			throws IOException {
		
		byte[] buffer = new byte[8 * 1024];
		File myFile = new File(filePath + filename);
		OutputStream output = new FileOutputStream(myFile);
		int bytesRead=0;
		while ((bytesRead = inputStream.read(buffer)) != -1) {
			output.write(buffer, 0, bytesRead);
		}
		output.close();
		inputStream.close();
		return myFile.getPath();
	}
	
	private Video writeToVideo(String title, String filePath, String fileName
			, String fileFormat, String description, Date uploadDate) {
		Video newVideo = new Video();
		newVideo.setFilePath(filePath);
		newVideo.setFileName(fileName);
		newVideo.setDescription(description);
		newVideo.setUploadDate(uploadDate);
		return newVideo;
	}

}

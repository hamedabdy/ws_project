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
import com.db.Video;


@MultipartConfig
public class UploadVideoToDb extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// Setting PWD to tomcat server root dir
		System.out.println(System.getProperty("user.dir"));

		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		
		out.println(System.getProperty("user.dir"));

		/*
		 * GET form parameters
		 */
		String videoTitle = request.getParameter("video-title");
		//System.out.println("videoTitle:  " + videoTitle);
		Part filePart = request.getPart("video");
		String fileName = getFilename(filePart);
		//System.out.println("file name  : "  + fileName);
		InputStream fileContent = filePart.getInputStream();
		// Creating a new Video instance to get a new VideoId
		Video newVideo = new Video();
		// Write uploaded file to disk
		String pathToFile = writeToDisk(fileContent, newVideo.getId(), fileName
				, newVideo.getUploadDate(), "", "../webapps/projet/uploads/");
		// Create new video instance with uploaded parameters
		newVideo = writeToVideo(videoTitle, pathToFile, newVideo.getId()+"_"+fileName, "", "");

		/*
		 *  Riak section
		 */
		IRiakClient riakClient;
		IRiakObject myObject;
		String value, key;

		try {
			// Store a video into Riak -->
			riakClient = RiakFactory.httpClient();
			Bucket videoBucket = riakClient.fetchBucket("Videos").execute();
			videoBucket.store(String.valueOf(newVideo.getId()),newVideo).execute();
			myObject = videoBucket.fetch(String.valueOf(newVideo.getId())).execute();
			key = (new JSONObject(myObject).getString("key")).toString();
			value = (new JSONObject(myObject).getString("valueAsString")).toString();
			//System.out.println("myobject : { key: " + key + "\n value: " + value + " }");
			out.println("myobject : { key: " + key + ", value: " + value + " }" + "<br><br>");
			// <--

			// Close Riak connection
			riakClient.shutdown();
		} catch (RiakException e) {
			e.printStackTrace();
		}

		out.println("<p>Video sent to server :)</p>");
		out.println("<a href='/projet'>Go Back</a> <a href='/projet/getVideo'>Get a list of videos</a>");

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

	private String writeToDisk(InputStream inputStream, int id, String filename, Date date
			, String VideoTitle, String filePath) 
					throws IOException {

		byte[] buffer = new byte[8 * 1024];
		File myFile = new File(filePath+id+"_"+filename);
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
			, String fileFormat, String description) {
		Video newVideo = new Video();
		newVideo.setFilePath(filePath);
		newVideo.setFileName(fileName);
		newVideo.setDescription(description);
		return newVideo;
	}

}

package de.starwit.ljprojectbuilder.servlet;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

@WebServlet(name = "ZipDownloadServlet", value = "/downloadproject")
public class ZipDownloadServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final Logger LOG = Logger.getLogger(ZipDownloadServlet.class);
	public static final String FILE_SEPARATOR = System.getProperty("file.separator");

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String projectpath = request.getParameter("projectpath");
			String projectname = request.getParameter("projectname");
			File directory = new File(projectpath);
			String[] files = directory.list();
			
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ZipOutputStream zos = new ZipOutputStream(baos);
		    addDirToZipArchive(zos, directory, null);
		    zos.flush();
		    baos.flush();
		    zos.close();
		    baos.close();

			if (files != null && files.length > 0) {
				ServletOutputStream sos = response.getOutputStream();
				response.setContentType("application/zip");
				response.setHeader("Content-Disposition", "attachment; filename=\"" + projectname + ".ZIP\"");
				sos.write(baos.toByteArray());
				sos.flush();
			}
		} catch (Exception e) {
			LOG.error("Error downloading project as ZIP-file", e.getCause());
			ServletOutputStream sos = response.getOutputStream();
			response.setContentType("application/json");
			response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
			sos.print("{response : {data : { metadata : {message : \"error.downloadproject\", responseCode : \"ERROR\"} } }");
			sos.flush();
			sos.close();
		}
	}

	public static void addDirToZipArchive(ZipOutputStream zos, File fileToZip, String parrentDirectoryName) throws Exception {
	    if (fileToZip == null || !fileToZip.exists()) {
	        return;
	    }

	    String zipEntryName = fileToZip.getName();
	    if (parrentDirectoryName!=null && !parrentDirectoryName.isEmpty()) {
	        zipEntryName = parrentDirectoryName + "/" + fileToZip.getName();
	    }

	    if (fileToZip.isDirectory()) {
	        LOG.info("+" + zipEntryName);
	        for (File file : fileToZip.listFiles()) {
	            addDirToZipArchive(zos, file, zipEntryName);
	        }
	    } else {
	    	LOG.info("   " + zipEntryName);
	        byte[] buffer = new byte[1024];
	        FileInputStream fis = new FileInputStream(fileToZip);
	        zos.putNextEntry(new ZipEntry(zipEntryName));
	        int length;
	        while ((length = fis.read(buffer)) > 0) {
	            zos.write(buffer, 0, length);
	        }
	        zos.closeEntry();
	        fis.close();
	    }
	}
}
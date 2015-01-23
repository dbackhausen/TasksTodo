package org.taskstodo.controller;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.bson.types.ObjectId;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.taskstodo.model.Attachment;
import org.taskstodo.service.FileService;
import org.taskstodo.service.TaskService;

import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSFile;

@Controller
@RequestMapping(value = "/attachments")
public class AttachmentController {
  /* The Logger */
  private static final Logger LOGGER = LoggerFactory.getLogger(AttachmentController.class);
  
  @Autowired
  private TaskService taskService;
  
  @Autowired
  private FileService fileService;
  
  @Value("#{appProperties['application.host']}")
  private String applicationHost;
  
  // --

  @RequestMapping(value = "/api/create/{taskId}", method = RequestMethod.POST)
  public @ResponseBody Attachment upload(@PathVariable("taskId") ObjectId taskId, @RequestPart("file") MultipartFile file) {
	  GridFSFile gf = null;
	  
    try {
      // Store binary file to database
      gf = fileService.saveFile(taskId, file);
    } catch (IOException e) {
      LOGGER.error(e.getMessage(), e);
    }
	  
	  if (gf != null) {
	    // Create a new attachment object to describe the file
	    Attachment attachment = new Attachment();
	    attachment.setId((ObjectId) gf.getId());
	    attachment.setTaskId(taskId);
	    attachment.setFilename(gf.getFilename());
	    attachment.setUrl(getApplicationHost() + "/attachments/download/" + gf.getId().toString());
	    attachment.setSize(gf.getLength());
	    attachment.setContentType(gf.getContentType());
	    attachment.setCreated(gf.getUploadDate());
	    return attachment;
	  } else {
	    return null;
	  }
  }
  

  @RequestMapping(value = "/api/save", method = RequestMethod.POST)
  public @ResponseBody Attachment save(@RequestBody JSONObject json) {
    String sTaskId = (String) json.get("taskId");
    String sUrl = (String) json.get("url");
    
    if (sTaskId != null && sUrl != null) {     
      URL url = null;
      URLConnection uc = null;
      InputStream is = null;
      DataInputStream dis = null;
      
      try {
        ObjectId taskId = new ObjectId(sTaskId);
       
        url = new URL(sUrl);
        uc = url.openConnection();

        // Get information about the file
        String filename = FilenameUtils.getName(url.getFile());
        String contentType = uc.getContentType();
        
        // Read data from remote host
        is = url.openStream();
        dis = new DataInputStream(new BufferedInputStream(is));
        
        // Store binary file to database
        GridFSFile gf = fileService.saveFile(taskId, dis, filename, contentType);
        
        if (gf != null) {
          // Create a new attachment object to describe the file
          Attachment attachment = new Attachment();
          attachment.setId((ObjectId) gf.getId());
          attachment.setTaskId(taskId);
          attachment.setFilename(gf.getFilename());
          attachment.setUrl(getApplicationHost() + "/attachments/download/" + gf.getId().toString());
          attachment.setSize(gf.getLength());
          attachment.setContentType(gf.getContentType());
          attachment.setCreated(gf.getUploadDate());
          return attachment;
        } else {
          return null;
        }
      } catch (MalformedURLException e) {
        LOGGER.error(e.getMessage(), e);
      } catch (IOException e) {
        LOGGER.error(e.getMessage(), e);
      } finally {
        try {
          is.close();
          dis.close();
        } catch (Exception e) {
        }
      }
    }

    return null;    
  }
  
  @RequestMapping(value = "/api/list/{taskId}", method = RequestMethod.GET)
  public @ResponseBody LinkedList<Attachment> list(@PathVariable("taskId") ObjectId taskId) {
    LinkedList<Attachment> attachments = new LinkedList<Attachment>();
    
    if (taskId != null) {
      List<GridFSDBFile> files = fileService.getFiles(taskId);
      
      for (GridFSDBFile file : files) {
        Attachment attachment = new Attachment();
        attachment.setId((ObjectId) file.getId());
        attachment.setTaskId(taskId);
        attachment.setFilename(file.getFilename());
        attachment.setUrl(getApplicationHost() + "/attachments/download/" + file.getId().toString());
        attachment.setSize(file.getLength());
        attachment.setContentType(file.getContentType());
        attachment.setCreated(file.getUploadDate());
        attachments.add(attachment);
      }
    }

    return attachments;
  }
  
  @RequestMapping(value = "/api/delete/{attachmentId}", method = RequestMethod.POST)
  public void delete(@PathVariable("attachmentId") ObjectId attachmentId, HttpServletResponse response) {
    fileService.delete(attachmentId);
  }
  
  @RequestMapping("/download/{attachmentId}")
  public void download(@PathVariable("attachmentId") ObjectId attachmentId, HttpServletResponse response) {
    try {
      GridFSDBFile file = fileService.loadFile(attachmentId);
      response.setHeader("Content-Disposition", "inline;filename=\"" + file.getFilename() + "\"");
      OutputStream out = response.getOutputStream();
      response.setContentType(file.getContentType());
      file.writeTo(out);
      out.flush();
      out.close();
    } catch (IOException e) {
      LOGGER.error(e.getMessage(), e);
    }
  }
  
  public String getApplicationHost() {
    return applicationHost;
  }
  
  public void setApplicationHost(String applicationHost) {
    this.applicationHost = applicationHost;
  }
}
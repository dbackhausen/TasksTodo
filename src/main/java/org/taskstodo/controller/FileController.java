package org.taskstodo.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.taskstodo.model.File;
import org.taskstodo.service.FileService;
import org.taskstodo.service.TaskService;
import org.taskstodo.util.FileUtil;

import com.mongodb.gridfs.GridFSDBFile;

@Controller
@RequestMapping(value = "/files")
public class FileController {
  /* The Logger */
  private static final Logger LOGGER = LoggerFactory.getLogger(FileController.class);
  
  @Autowired
  private TaskService taskService;
  
  @Autowired
  private FileService fileService;
  
  // --
  
  @RequestMapping(value = "/api/create/{taskId}", method = RequestMethod.POST)
  public void upload(@PathVariable("taskId") ObjectId taskId, 
      MultipartHttpServletRequest request, HttpServletResponse response) {
    Iterator<String> itr = request.getFileNames();
    MultipartFile mpf = null;

    if (taskId != null) {
      while (itr.hasNext()) {
        // Get the next MultipartFile
        mpf = request.getFile(itr.next());
        
        try {
          // Save file
          fileService.saveFile(taskId, mpf);
        } catch (IOException e) {
          LOGGER.error(e.getMessage(), e);
        }
      }
    }
  }
  
  @RequestMapping(value = "/api/list/{taskId}", method = RequestMethod.GET)
  public @ResponseBody LinkedList<File> list(@PathVariable("taskId") ObjectId taskId) {
    LinkedList<File> _files = new LinkedList<File>();
    
    if (taskId != null) {
      List<GridFSDBFile> files = fileService.getFiles(taskId);
      
      for (GridFSDBFile file : files) {
        _files.add(FileUtil.copy(file, new File()));
      }
    }
    
    return _files;
  }
  
  @RequestMapping(value = "/api/delete/{fileId}", method = RequestMethod.POST)
  public void delete(@PathVariable("fileId") ObjectId fileId, HttpServletResponse response) {
    fileService.delete(fileId);
  }
  
  @RequestMapping("/download/{fileId}")
  public void download(@PathVariable("fileId") ObjectId fileId, HttpServletResponse response) {
    try {
      GridFSDBFile file = fileService.loadFile(fileId);
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
}
package org.taskstodo.test;

import java.io.FileInputStream;
import java.io.IOException;

import javax.annotation.Resource;

import org.apache.commons.io.IOUtils;
import org.bson.types.ObjectId;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.taskstodo.model.File;
import org.taskstodo.model.Tag;
import org.taskstodo.service.FileService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/application-context.xml"})
public class FileTest {

  @Resource
  private FileService fileService;
  
  @Before
  public void setup() {
  }

  @Test
  public void testAddFile() {
    try {
    java.io.File f = new java.io.File("/Users/dbackhausen/test.h2.db");
    byte[] fbytes = IOUtils.toByteArray(new FileInputStream(f));
    
    File file = new File();
    file.setTaskId(new ObjectId("52950ebe3004676c2b42a108"));
    file.setTitle("JUnit Test File");
    file.setDescription("This is a test file");
    file.setBytes(fbytes);
    
    ObjectId id = fileService.saveFile(file);
    Assert.assertNotNull(id);
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
} 
<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>Home page</title>
</head>
<body>
  <h1>Home page</h1>
  <p>
    <p>${message}</p>
    <ul>
      <li><a href="${pageContext.request.contextPath}/task/add.html">Add new task</a></li>
      <li><a href="${pageContext.request.contextPath}/task/list.html">Task list</a></li>
    </ul>
  </p>
</body>
</html>
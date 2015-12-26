<%-- 
    Document   : error
    Created on : Dec 26, 2015, 12:43:07 AM
    Author     : Arun Kumar
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="true"%>
<!DOCTYPE html>
<html>
  <head><title>Error</title></head>
  <body>Failed to connect to the Twitter API<br>
  ${exception.message}</body>
</html>

<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>后台管理系统</title>
<%@ include file="common/load-css.jsp"%>
<%@ include file="common/load-ext.jsp"%>
<script>
   function refresh()
   {
    document.getElementById("authImg").src='authImg?now=' + new Date();
   }
</script>
<script type="text/javascript" src="js/index.js"></script>
</head>
<body>

<form action="uploadFile" method="post" enctype="multipart/form-data">
<p>File:</p><input type="file" name="file01"/>
<input type="submit" value="submit"/>
</form>
<s:form action="uploadbook" namespace="/file" method="POST" enctype="multipart/form-data">
	<s:file name="ebook"></s:file>
	<s:submit value="submit"></s:submit>
</s:form>

<s:form action="login" namespace="/file" method="POST">
	<s:label>userName</s:label><s:textfield name="userName"></s:textfield>
	<s:label>password</s:label><s:textfield name="password"></s:textfield>
	<img src="authImg" id="authImg"/>看不清？<a href="#" onClick="refresh()">刷新</a>
	<s:submit value="submit"></s:submit>
</s:form>

</body>
</html>
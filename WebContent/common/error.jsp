<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String msg = String.valueOf(request.getAttribute("exceptionMsg"));

if (msg == null)
{
    msg = "testmsg";
}
out.print("{\"exceptionMsg\":\""+ msg + "\",\"success\":" + false +"}");
%>

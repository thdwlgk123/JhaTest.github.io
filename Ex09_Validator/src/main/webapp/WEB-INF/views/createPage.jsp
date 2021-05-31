<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
createPage.jsp
<%
	String conPath=request.getContextPath();
%>
<br> 
<form action="<%=conPath %>/create">
	작성ㅈr : <input type="text" name="writer" value="${dto.writer }"><br/>
	내용 : <input type="text" name="content" value="${dto.content }"><br/>
	<input type="submit" value="전송"> <br/>
</form>
</body>
</html>
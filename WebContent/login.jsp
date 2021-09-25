<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% if(session.getAttribute("ValidMem") !=null){ %>
	<jsp:forward page="main.jsp"></jsp:forward>
<%} %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인</title>
<script src="http://code.jquery.com/jquery.js"></script>

<script>
function submit_ajax(){
	var queryString=$("#reg_frm").serialize();
//	console.log(queryString);
	$.ajax({
		url:'loginOk.do',
		type:'POST',
		data: queryString,
		dataType: 'text',
		success: function(json){
			console.log(json);
			var result=JSON.parse(json);
			if(result.code=="success"){
				alert(result.desc)
				window.location.replace("main.jsp");
			}else{
				alert(result.desc);
			}
		}
	})
}
</script>
</head>
<body>
	<form id="reg_frm">
		아이디 : <input type="text" id="id" name="id" 
			value="<% if(session.getAttribute("id") !=null)
					out.println(session.getAttribute("id"));%>"><br>
		비밀번호 : <input type="password" id="pw" name="pw" size="10"><br><p>
		
		<input type="button" value="로그인" onclick="submit_ajax()">&nbsp;&nbsp;
		<input type="button" value="회원가입" onclick="javascript:window.location='join.jsp'"> 		
	</form>
</body>
</html>
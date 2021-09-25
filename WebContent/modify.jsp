<%@ page import="com.study.jsp.FrontCon" %>
<%@ page import="com.study.jsp.MemberDAO" %>
<%@ page import="com.study.jsp.MemberDTO" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%request.setCharacterEncoding("UTF-8"); %>   
<%
	String name=(String)session.getAttribute("name");
	String id=(String)session.getAttribute("id");
	MemberDAO dao=MemberDAO.getInstance();
	MemberDTO dto=dao.getMember(id);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
<link rel="stylesheet" href="css/style.css" type="text/css" />	
<title>정보수정</title>
<script src="http://code.jquery.com/jquery.js"></script>
<script>
function updateInfoConfirm(){
	if($('#pw').val().length==0){
		alert("패스워드를 입력하세요.");
		$('#pw').focus();
		return;
	}
	if($('#pw').val() !=$('#pw_check').val()){
		alert('비밀번호가 일치하지 않습니다.');
		$('#pw').focus();
		return;
	}
	if($('#eMail').val().length ==0){
		alert('메일은 필수사항입니다..');
		$('#eMail').focus();
		return;
	}
	submit_ajax();	
}
function submit_ajax(){
	var queryString=$("#reg_frm").serialize();
	$.ajax({
		url:'modifyOk.do',
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
function withdrawcheck(){
	alert("회원탈퇴 창으로 이동합니다.");
	window.location.replace("withdraw.jsp");
}
</script>
<style type="text/css">
	.header,body{padding-bottom:20px}.header,.jumbotron{border-bottom:1px solid #e5e5e5}body{padding-top:20px}.footer,.header,.marketing{padding-right:15px;padding-left:15px}.header h3{margin-top:0;margin-bottom:0;line-height:40px}.footer{padding-top:19px;color:#777;border-top:1px solid #e5e5e5}@media (min-width:768px){.container{max-width:730px}}.container-narrow>hr{margin:30px 0}.jumbotron{text-align:center}.jumbotron .btn{padding:14px 24px;font-size:21px}.marketing{margin:40px 0}.marketing p+h4{margin-top:28px}@media screen and (min-width:768px){.footer,.header,.marketing{padding-right:0;padding-left:0}.header{margin-bottom:30px}.jumbotron{border-bottom:0}}
	</style>
</head>
<body>
	<nav class="navbar navbar-expand-lg navbar-light bg-light">
  <a class="navbar-brand" href="#">Navbar</a>
  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
    <span class="navbar-toggler-icon"></span>
  </button>

  <div class="collapse navbar-collapse" id="navbarSupportedContent">
    <ul class="navbar-nav mr-auto">
      <li class="nav-item active">
        <a class="nav-link" href="main.do">Home <span class="sr-only">(current)</span></a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="list.do">게시판 목록보기</a>
      </li>
      <li class="nav-item dropdown">
        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
          Dropdown
        </a>
        <div class="dropdown-menu" aria-labelledby="navbarDropdown">
          <a class="dropdown-item" href="#">Action</a>
          <a class="dropdown-item" href="#">Another action</a>
          <div class="dropdown-divider"></div>
          <a class="dropdown-item" href="#">Something else here</a>
        </div>
      </li>
      <li class="nav-item">
        <a class="nav-link disabled" href="#">Disabled</a>
      </li>
    </ul>
    <form class="form-inline my-2 my-lg-0">
      <input class="form-control mr-sm-2" type="search" placeholder="Search" aria-label="Search">
      <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>
	
    </form>
  </div>
</nav>	
	
	<div id="content">
	<div id="main_content">
	<div style="text-align:center"><h2>회원 정보 수정</h2></div>
	<div style="text-align:center"><h5>회원 정보를 수정합니다.</h5></div>

	<form id="reg_frm">
		<table class="table" style="width:40%">
		<tr><th scope="row"  style="text-align:center">아이디 : </th><td><%=dto.getId() %></td></tr>
		<tr><th scope="row"  style="text-align:center">비밀번호 : </th><td><input type="password" id="pw" name="pw" size="20"></td></tr>	
		<tr><th scope="row"  style="text-align:center"> 비밀번호 확인 : </th><td><input type="password" id="pw_check" name="pw_check" size="20"></td></tr>
		<tr><th scope="row"  style="text-align:center">이름: </th><td><%=dto.getName() %></td></tr>
		<tr><th scope="row"  style="text-align:center">메일 : </th><td><input type="text" id="eMail" name="eMail" size="20" value="<%=dto.geteMail() %>"></td></tr>
		<tr><th scope="row"  style="text-align:center">주소 : </th><td><input type="text" id="address" name="address" size="50" value="<%=dto.getAddress() %>"></td></tr>
		<th><input type="button" value="수정" onclick="updateInfoConfirm()"> </th>
		<th><input type="reset" value="취소" onclick="javascript:window.location='main.do'"></th>
		<th><input type="button" value="회원탈퇴" onclick="withdrawcheck()"> </th>
		</table>
	</form>	
	</div>
	</div>
	<div id="aside_content">
	<div>
		<table align="center">
		<tr><td><img src="https://user-images.githubusercontent.com/6022883/45476027-e45c1a80-b778-11e8-9716-4e39c6d6e58e.png" width="50"><br/></td></tr>
		<tr><td>
<% if (session.getAttribute("id").equals("manager")){ %>		
		<%=name %> 모드입니다.</td></tr>
<%}else{ %>		
		<%=name %> 님 반갑습니다.</td></tr>
<%} %>		
		<tr><td><a id="gnbJoin" href="logout.do" >로그아웃</a></td></tr>
		<tr><td><a id="gnbModify" href="modify.jsp" >정보수정</a></td></tr>
		</table>
	</div>	
	<br/>
	<br/>
<% if (session.getAttribute("id").equals("manager")){ %>
	<div class="managerbox">
		<h5>관리자 모드 창</h5>
		<ul>
			<li><a href="memberlist.mng">회원목록보기</a></li>
		</ul>
	</div>
<%} %>	
</div>
	</div>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
	
</body>
</html>
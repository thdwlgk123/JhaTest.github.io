<%@ page import="com.study.jsp.FrontCon" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% if(session.getAttribute("ValidMem") ==null){ %>
	<jsp:forward page="mainpage.jsp" />
<%
} if (!session.getAttribute("id").equals("manager")){ %>
	<jsp:forward page="main.jsp" />
<% 	
}
	String name=(String)session.getAttribute("name");
	String id=(String)session.getAttribute("id");
%> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title>회원목록 게시판</title>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
<link rel="stylesheet" href="css/style.css" type="text/css" />	
<script src="http://code.jquery.com/jquery.js"></script>
	<style type="text/css">
	.header,body{padding-bottom:20px}.header,.jumbotron{border-bottom:1px solid #e5e5e5}body{padding-top:20px}.footer,.header,.marketing{padding-right:15px;padding-left:15px}.header h3{margin-top:0;margin-bottom:0;line-height:40px}.footer{padding-top:19px;color:#777;border-top:1px solid #e5e5e5}@media (min-width:768px){.container{max-width:730px}}.container-narrow>hr{margin:30px 0}.jumbotron{text-align:center}.jumbotron .btn{padding:14px 24px;font-size:21px}.marketing{margin:40px 0}.marketing p+h4{margin-top:28px}@media screen and (min-width:768px){.footer,.header,.marketing{padding-right:0;padding-left:0}.header{margin-bottom:30px}.jumbotron{border-bottom:0}}
	</style>
	<script>
		function blacklist_check(){
			var id="${member_view.id}";
			$.ajax({
				url:'blackcheck.mng',
				type:'POST',
				data: { id: id},
				dataType: 'json',
				success: function(json){
					console.log(json);
					if(json.code=="success"){
						alert(json.desc);
						blacklist_submit();
					}else{
						alert(json.desc);
					}
				}
			})
		}
		function blacklist_submit(){
			alert("등록진행 ajax들옴");
			var id="${member_view.id}";
			var name="${member_view.name}";
			$.ajax({
				url:'insertblack.mng',
				type:'POST',
				data: { id: id, name: name},
				dataType: 'json',
				success: function(json){
					console.log(json);
					if(json.code=="success"){
						alert(json.desc);
// 						window.location.replace("main.jsp");
					}else{
						alert(json.desc);
					}
				}
			})
		}
		function withdraw_check(){
			withdraw_submit();
		}
		function withdraw_submit(){

			var id="${member_view.id}";
			var name="<%=name%>";
			$.ajax({
				url:'withdraw.do',
				type:'POST',
				data: { id: id, pw: name},
				dataType: 'json',
				success: function(json){
					console.log(json);
					if(json.code=="success"){
						alert(json.desc);
						window.location.replace("memberlist.mng");
					}else{
						alert(json.desc);
					}
				}
			})
		}
	</script>
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
<div style="text-align:center"><h2>회원 정보 조회</h2></div>
<div style="text-align:center"><h5>회원 정보를 조회 및 관리합니다.</h5></div>
	<table class="table" style="width:50%">
			<tr>
				<th scope="row" style="text-align:center" >ID</th>
				<td> ${member_view.id }</td>
			</tr>
			<tr>
				<th scope="row" style="text-align:center">NAME</th>
				<td>${member_view.name}</td>
			</tr>
			<tr>
				<th scope="row" style="text-align:center">EMAIL</th>
				<td> ${member_view.eMail }</td>
			</tr>
			<tr >
				<th scope="row" style="text-align:center">가입날짜</th>
				<td> ${member_view.rDate }</td>
			</tr>
			<tr >
				<th scope="row" style="text-align:center">주소/로그인경로</th>
				<td> ${member_view.address }</td>
			</tr>
<!-- 			<tr height="1" bgcolor="#82B5DF"><td colspan="2"></td></tr> -->
					
	</table>
		
	<table class="table" style="width:50%" cellpadding="0" cellspacing="0" border="0">
		<tr align="right">
		<td>
			<a href="#" onclick="blacklist_check()">블랙리스트 등록</a>&nbsp;&nbsp;
		</td>
		<td>	
			<a href="#" onclick="withdraw_check()">회원 강제 탈퇴</a>&nbsp;&nbsp;
		</td>
		<td>
			<a href="memberlist.mng">목록보기</a>&nbsp;&nbsp;
		</td>
		</tr>
		</table>
	<table width="100%" cellpadding="0" cellspacing="0" border="0">
	<form action="memsearch.mng" method="post" name="search_form">
		<tr>
		<td align="center">
		<select name="searchtype" id="searchtype">
			<option name="name" value="name" >NAME</option>
			<option name="id" value="id">ID</option>	
		</select>
		<input name="searchtext" id="searchtext" type="text" placeholder="Search" aria-label="Search" size="20" maxlength="30">
		<button class="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>
		</td>
		</tr>	
	</form>
	</table>
	
</div>	

<div id="aside_content">
	<div>
		<table align="center">
		<tr><td><img src="https://user-images.githubusercontent.com/6022883/45476027-e45c1a80-b778-11e8-9716-4e39c6d6e58e.png" width="50"><br/></td></tr>
		<tr><td><%=name %> 모드입니다.</td></tr>
		<tr><td><a id="gnbJoin" href="logout.do" >로그아웃</a></td></tr>
		<tr><td><a id="gnbModify" href="modify.jsp" >정보수정</a></td></tr>
		</table>
	</div>	
	<br/>
	<br/>
	<div class="managerbox">
		<h5>관리자 모드 창</h5>
		<ul>
			<li><a href="memberlist.mng">회원목록보기</a></li>
		</ul>
	</div>
</div>
</div>


	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>

</body>
</html>
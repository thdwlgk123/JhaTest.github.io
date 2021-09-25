<%@ page import="com.study.jsp.FrontCon" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% if(session.getAttribute("ValidMem") ==null){ %>
	alert("로그인을 먼저 진행하세요.");
	<jsp:forward page="mainpage.jsp" />
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

<title>글작성창</title>

<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
	
	<script src="http://code.jquery.com/jquery.js"></script>
	<link rel="stylesheet" href="css/style.css" type="text/css" />	
<script src="https://cdn.ckeditor.com/4.16.0/standard/ckeditor.js"></script>
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
        <a class="nav-link" href="#">Home <span class="sr-only">(current)</span></a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="list.do">게시판 목록보기</a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="noticelist.do">공지사항 게시판</a>
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
<% if (session.getAttribute("id").equals("manager")){ %>
		<a id="IdLogin_loginButton" href="javascript:modalOpen()" role="button">
			<br><br><p><%=name %>모드입니다.</p>
		</a>
<%}else{ %>		
		<a id="IdLogin_loginButton" href="javascript:modalOpen()" role="button">
			<p><%=name %>님 반갑습니다.</p>
		</a> 
<%} %>	
	     
      <a id="gnbJoin" href="logout.do" >로그아웃</a>

    </form>
  </div>
</nav>	

<div id="content">
<div id="main_content">
<div style="text-align:center"><h2>공지사항 수정</h2></div>
<div style="text-align:center"><h5>공지사항을 수정할 수 있습니다.</h5></div>

<table class="table">
	<form action="noticewrite.mng" method="post">
	<tr>
		<th scope="row" style="text-align:center" >이름</th>
		<td><%=name %></td>
	</tr>
	<tr>
		<th scope="row" style="text-align:center" >제목</th>
		<td> <input type="text" name="bTitle" size="50"></td>
	</tr>
	<tr>
		<th scope="row" style="text-align:center" >내용</th>
		<td> 
			<textarea name="bContent" id="editor1" rows="10" cols="80">
			여기다가 쓰시오.
			</textarea>
			<script>
                 CKEDITOR.replace( 'editor1' );            
            </script>
		</td>
	</tr>
	<tr align="right">
		<td style="border-collapse:collapse" colspan="2">
			<input type="submit" value="입력">&nbsp;&nbsp;
			<a href="noticelist.do">목록보기</a>
		</td>
	</tr>	
	</form>
</table>
</div>
<div id="aside_content">
	<div class="managerbox">
		<h3>관리자 모드 창</h3>
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
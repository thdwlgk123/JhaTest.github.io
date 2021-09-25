<%@ page import="com.study.jsp.FrontCon" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
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
<title>list 창</title>
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
	<script src="http://code.jquery.com/jquery.js"></script>
	<link rel="stylesheet" href="css/style.css" type="text/css" />
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
<div style="text-align:center"><h2>회원 찾기</h2></div>
<table class="table">
	<thead>
	<tr class="table success">
		<th scope="col">ID</th>
		<th scope="col">NAME</th>
		<th scope="col">EMAIL</th>
		<th scope="col">가입날짜</th>
		<th scope="col">주소/로그인경로</th>
	</tr>
	</thead>
	<tbody>
		<c:forEach items="${memsearchlist}" var="dto">
		<tr>
			<th scope="row">${dto.id }</th>
			<td><a href="member_view.mng?id=${dto.id }">${dto.name }</td>
			<td>${dto.eMail }</td>
			<td>${dto.rDate }</td>
			<td>${dto.address }</td>
		</tr>
		</c:forEach>
	</tbody>	
	<tr>
		<td colspan="5" align="center">	
		<!-- 처음 -->
		<c:choose>
		<c:when test="${(memsearch_page.curPage-1) < 1 }">
			[처음으로]
		</c:when>
		<c:otherwise>
			<a  href="memberlist.do?page=1">[처음으로]</a>
		</c:otherwise>
		</c:choose>
		
		<!-- 이전 -->
		<c:choose>
		<c:when test="${(memsearch_page.curPage-1) < 1 }">
			[이전]
		</c:when>
		<c:otherwise>
			<a  href="memberlist.do?page=${memsearch_page.curPage-1}">[이전]</a>
		</c:otherwise>
		</c:choose>
		<!-- 개별페이지 -->
		<c:forEach var="fEach" begin="${memsearch_page.startPage }" end="${memsearch_page.endPage }" step="1">
			<c:choose>
			<c:when test="${memsearch_page.curPage==fEach}">
				[${fEach }] &nbsp;
			</c:when>
			<c:otherwise>
				<a  href="memberlist.do?page=${fEach}">[${fEach }]</a>&nbsp;
			</c:otherwise>
			</c:choose>
		</c:forEach>
		<!-- 다음 -->
		<c:choose>
		<c:when test="${(memsearch_page.curPage+1)>memsearch_page.totalPage }">
			[다음]
		</c:when>
		<c:otherwise>
			<a href="memberlist.do?page=${memsearch_page.curPage+1 }">[다음]</a>
		</c:otherwise>
		</c:choose>
		<!-- 끝 -->
		<c:choose>
		<c:when test="${memsearch_page.curPage==memsearch_page.totalPage }">
			[끝으로]
		</c:when>
		<c:otherwise>
			<a href="memberlist.do?page=${memsearch_page.totalPage}">[끝으로]</a>
		</c:otherwise>
		</c:choose>	
		</td>
	</tr>
	<tr >
		<td style="border-collapse:collapse" colspan="4">&nbsp;</td>
		<td ><a href="memberlist.do?page=<%= session.getAttribute("cpage")%>">회원목록보기</a></td>
	</tr>						
</table>

<table class="table" width="100%" cellpadding="0" cellspacing="0" border="0">
<form action="memsearch.mng" method="post" name="search_form">
<% 
if(request.getAttribute("searchtype").equals("name")){
%>
	<tr><td align="center">
	<select name="searchtype" id="searchtype">
			<option name="name" value="name" selected="selected" >NAME</option>
			<option name="id" value="id">ID</option>	
	</select>
<%
} else {
%>
	<tr><select name="searchtype" id="searchtype">
		<option name="name" value="name" >NAME</option>
		<option name="id" value="id" selected="selected">ID</option>	
	</select>
<%
	}
 %>
	<input name="searchtext" id="searchtext" type="text" aria-label="Search" value="<%=request.getAttribute("searchtext") %>" size="20">
	<button class="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>
	</td></tr>
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
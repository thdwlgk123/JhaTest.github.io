<%@ page import="com.study.jsp.FrontCon" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% if(session.getAttribute("ValidMem") ==null){ %>

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
<title>글내용 및 댓글작성창</title>

<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
	
	<script src="http://code.jquery.com/jquery.js"></script>
	
	<link rel="stylesheet" href="css/style.css" type="text/css" />
	<style type="text/css">
	.header,body{padding-bottom:20px}.header,.jumbotron{border-bottom:1px solid #e5e5e5}body{padding-top:20px}.footer,.header,.marketing{padding-right:15px;padding-left:15px}.header h3{margin-top:0;margin-bottom:0;line-height:40px}.footer{padding-top:19px;color:#777;border-top:1px solid #e5e5e5}@media (min-width:768px){.container{max-width:730px}}.container-narrow>hr{margin:30px 0}.jumbotron{text-align:center}.jumbotron .btn{padding:14px 24px;font-size:21px}.marketing{margin:40px 0}.marketing p+h4{margin-top:28px}@media screen and (min-width:768px){.footer,.header,.marketing{padding-right:0;padding-left:0}.header{margin-bottom:30px}.jumbotron{border-bottom:0}}
	</style>
<script src="https://cdn.ckeditor.com/4.16.0/standard/ckeditor.js"></script>
<script>
	function delete_check(){
		if("<%=name%>"=="${content_view.bName }"){
			alert("작성자 권한 삭제")
			delete_submit();
		}else if("<%=name%>"=="관리자"){
			alert("관리자 권한 삭제");
			delete_submit();
		}else{
			alert("작성자 또는 관리자만 삭제할 수 있습니다.");
		}
	}
	function delete_submit(){
		var bId="${content_view.bId }";
		$.ajax({
			url:'delete.do',
			type:'POST',
			data: {bId:bId},
			dataType: 'json',
			success: function(json){
				console.log(json);
				if(json.code=="success"){
					alert(json.desc);
					window.location.replace("list.do?page=1");
				}else{
					alert(json.desc);
				}
			}
		})
	}
	function modify_check(){
		if("<%=name%>"=="${content_view.bName }"){
			alert("작성자 권한 수정");
			window.location.replace("modify_view.do?bId=${content_view.bId }");
		}else{
			alert("작성자만 수정할 수 있습니다.");
		}
	}
	
	function commentdelete_check(bName, bId){
		var bName=bName;
		var bId=bId;
		if(bName=="<%=name%>"){
			alert("작성자 접속자 일치");
			commentdelete_submit(bId);
		}else if(bName=="관리자"){
			alert("관리자 권한");
			commentdelete_submit(bId);
		}
		else{
			alert("권한이 없습니다.");
		}
	}
	function commentdelete_submit(bId){
		var bId=bId;
		$.ajax({
			url:'commentdelete.do',
			type:'POST',
			data: {bId:bId},
			dataType: 'json',
			success: function(json){
				console.log(json);
				if(json.code=="success"){
					alert(json.desc);
					window.location.replace("content_view.do?bId=${content_view.bId }");
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
    </form>
  </div>
</nav>
<div id="content">
<div id="main_content">
<div style="text-align:center"><h2>게시글 조회</h2></div>
<div style="text-align:center"><h5>개별 게시글의 내용 및 댓글을 확인할 수 있습니다.</h5></div>

	<form action="write.do" method="post">
		<table class="table" style="width:50%">
			<tr>
				<th scope="row" style="text-align:center">번호</td>
				<td> ${content_view.bId }</td>
			</tr>
			<tr>
				<th scope="row" style="text-align:center">히트</td>
				<td>${content_view.bHit }</td>
			</tr>
			<tr>
				<th scope="row" style="text-align:center">작성자</td>
				<td>${content_view.bName }</td>
			</tr>
			<tr>
				<th scope="row" style="text-align:center">제목</td>
				<td> ${content_view.bTitle }</td>
			</tr>
			<tr >
				<th scope="row" style="text-align:center">내용</td>
				<td> ${content_view.bContent }</td>
			</tr>	
		<tr align="right">
			<td colspan="2">
				<a href="#" onclick="modify_check()">수정</a>&nbsp;&nbsp;
				<a href="list.do?page=<%= session.getAttribute("cpage")%>">목록보기</a>&nbsp;&nbsp;
				<a href="#" onclick="delete_check()">삭제</a>&nbsp;&nbsp;
				<a href="reply_view.do?bId=${content_view.bId }">답변</a>&nbsp;&nbsp;
			</td>
		</tr>
		</table>
	</form>
	<br/>
	<br/>

	<div style="text-align:center"><h5>댓글 목록</h5></div>
	
	<table class="table" id="comment_table">
	<c:forEach items="${commentlist}" var="dto">
	<tr>
	<th scope="row" style="text-align:center">${dto.bName }</th>
		<td width="550">
		<div class="text_wrapper">
			${dto.bContent }
		</div>
		</td>
		<td><font size="2" color="lightgray">${dto.bDate }</font></td>
		<td>
		<a href="#" onclick='commentdelete_check("${dto.bName }","${dto.bId }")'>삭제</a></td>	
	</tr>
	</c:forEach>	
	
	<tr>
		<td colspan="5" align="center">
		<!-- 처음 -->
		<c:choose>
		<c:when test="${(comment_page.curPage-1) < 1 }">
			[&lt;&lt;]
		</c:when>
		<c:otherwise>
			<a  href="commentlist.do?comment_page=1&bId=${content_view.bId }">[&lt;&lt;]</a>
		</c:otherwise>
		</c:choose>
		
		<!-- 이전 -->
		<c:choose>
		<c:when test="${(comment_page.curPage-1) < 1 }">
			[&lt;]
		</c:when>
		<c:otherwise>
			<a  href="commentlist.do?page=${comment_page.curPage-1}&bId=${content_view.bId }">[&lt;]</a>
		</c:otherwise>
		</c:choose>
		<!-- 개별페이지 -->
		<c:forEach var="fEach" begin="${comment_page.startPage }" end="${comment_page.endPage }" step="1">
			<c:choose>
			<c:when test="${comment_page.curPage==fEach}">
				[${fEach }] &nbsp;
			</c:when>
			<c:otherwise>
				<a  href="commentlist.do?page=${fEach}&bId=${content_view.bId }">[${fEach }]</a>&nbsp;
			</c:otherwise>
			</c:choose>
		</c:forEach>
		<!-- 다음 -->
		<c:choose>
		<c:when test="${(comment_page.curPage+1)>comment_page.totalPage }">
			[&gt;]
		</c:when>
		<c:otherwise>
			<a href="commentlist.do?page=${comment_page.curPage+1 }&bId=${content_view.bId }">[&gt;]</a>
		</c:otherwise>
		</c:choose>
		<!-- 끝 -->
		<c:choose>
		<c:when test="${comment_page.curPage==comment_page.totalPage }">
			[&gt;&gt;]
		</c:when>
		<c:otherwise>
			<a href="commentlist.do?page=${comment_page.totalPage}&bId=${content_view.bId }">[&gt;&gt;]</a>
		</c:otherwise>
		</c:choose>	
		</td>
	</tr>				
			
</table>
	

	<table width="100%" cellpadding="0" cellspacing="0" border="0">
	<form action="comment.do" method="post">	
		<input type="hidden" name="bId" value="${content_view.bId }">
		<input type="hidden" name="comment_bname" value="<%=name %>">
		<tr>	
			<td align="center">[<%=name %>]&nbsp;&nbsp;
			<textarea name="comment_content" rows="1" cols="60"></textarea>
			<button id="btn" style="text-align:center;"><input type="submit" value="댓글등록"></button>
			</td>
		</tr>					
	</form>
	</table>	
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
		<h3>관리자 모드 창</h3>
		<ul>
			<li><a href="memberlist.mng">회원목록보기</a></li>
		</ul>
	</div>
<%} %>	
</div>
</div>
</body>
</html>
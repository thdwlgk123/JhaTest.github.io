<%@ page import="com.study.jsp.FrontCon" %>
<%@ page import="com.study.jsp.MemberDAO" %>
<%@ page import="com.study.jsp.MemberDTO" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%--  <%request.setCharacterEncoding("UTF-8"); %>    --%>
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
<title>회원탈퇴</title>
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
	
	<script src="http://code.jquery.com/jquery.js"></script>
	<link rel="stylesheet" href="css/style.css" type="text/css" />	
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
		withdraw_ajax();	
	}	
	function withdraw_ajax(){
		alert("회원 탈퇴를 진행합니다.");
		var id="<%=dto.getId()%>";
		var pw=$('#pw').val();
		alert(id);
		$.ajax({
			url:'withdraw.do',
			type:'POST',
			data: {
				id: id,
				pw: pw
			},
			dataType: 'json',
			success: function(json){
				console.log(json);
				if(json.code=="success"){
					alert(json.desc)
					window.location.replace("mainpage.jsp");
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
<div style="text-align:center"><h2>회원 탈퇴</h2></div>
<div style="text-align:center"><h5>모든 정보를 삭제하고 회원 탈퇴를 진행합니다.</h5></div>
	<div class="list_content">
	<form id="withdraw_frm">
		<table class="table" style="width:40%">
		<tr><th scope="row"  style="text-align:center">아이디 : </th><td><%=dto.getId() %></td></tr>
		<tr><th scope="row"  style="text-align:center">비밀번호 : </th><td><input type="password" id="pw" name="pw" placeholder="회원탈퇴를 위한 비밀번호를 입력하세요" size="20"></td>	
		<tr><th scope="row"  style="text-align:center">비밀번호 확인 : </th><td><input type="password" id="pw_check" name="pw_check" placeholder="위와 같은 비밀번호를 입력하세요" size="20"></td>
		<tr><th scope="row"  style="text-align:center">이름: </th><td><%=dto.getName() %></td></tr>
		<th>
		<input type="button" value="탈퇴" onclick="updateInfoConfirm()"> &nbsp;&nbsp;&nbsp;
		</tr>
		<tr>
		<input type="reset" value="취소" onclick="javascript:window.location='main.jsp'">
		</th>
		</table>
	</form>	
	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
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
</body>
</html>
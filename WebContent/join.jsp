<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입</title>
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
	<script src="http://code.jquery.com/jquery.js"></script>
	<script src="https://apis.google.com/js/api:client.js"></script>
	<link rel="stylesheet" href="css/style.css" type="text/css" />	
	<script>
		function modalOpen(){
			$('#exampleModal').modal({backdrop: 'static', keyboard: false});
		}
		function save(){
			console.log("aaa");
			$('#exampleModal').modal('hide');
		}
// 		로그인 함수
		function submit_ajax(){
// 			var queryString=$("#main_form").serialize();
			var id=$('#id').val();
			var pw=$('#pw').val();
			console.log(id);
			console.log(pw);
			$.ajax({
				url:'loginOk.do',
				type:'POST',
				data: {id:id, pw:pw},
				dataType: 'json',
				success: function(json){
					console.log(json);
// 					var result=JSON.parse(json);
					if(json.code=="success"){
						alert(json.desc)
						window.location.replace("main.jsp");
					}else{
						alert(json.desc);
					}
				}
			})
		}
		

</script>
<script src="https://apis.google.com/js/api:client.js"></script>
<script>
// 구글로그인
	var googleUser = {};
	var startApp = function() {
		gapi.load('auth2', function() {
			// Retrieve the singleton for the GoogleAuth library and set up the client.
			auth2 = gapi.auth2.init({
				client_id: '24703938961-d97g23oh251odcqvs54ujposj03vlm3l.apps.googleusercontent.com',
				cookiepolicy: 'single_host_origin',
        		// Request scopes in addition to 'profile' and 'email'
        		//scope: 'additional_scope'
			});
			attachSignin(document.getElementById('login'));
		});
	};
	
	function attachSignin(element) {
		auth2.attachClickHandler(element, {},
			function(googleUser) {
// 				var profile = googleUser.getBasicProfile();
				var id=profile.getId();
				var name=profile.getName();
				var email=profile.getEmail();
				$.ajax({
					url:'googlejoinOk.do',
					type:'POST',
					data: {
						id: id,
						name: name,
						email: email					
					},
					dataType: 'json',
					success: function(json){
						if(json.code=="success"){
							alert(json.desc);
							signOut();
							window.location.replace("main.jsp");

						}else{
							alert(json.desc);
						}
					}
				})
				
			}, function(error) {
				alert(JSON.stringify(error, undefined, 2));
			});
	}
	function signOut() {
	    var auth2 = gapi.auth2.getAuthInstance();
	    auth2.signOut()

	}
	</script>
	
	<style type="text/css"></style>

<script>
	function form_check(){

		if($('#jid').val().length==0){
			alert('아이디는 필수사항입니다.');
			$('#id').focus();
			return;
		}
		if($('#jid').val().length<4){
			alert('아이디는 4글자 이상이어야 합니다.');
			$('#id').focus();
			return;
		}
		if($('#jpw').val().length==0){
			alert('비밀번호는 필수사항입니다.');
			$('#pw').focus();
			return;
		}
		if($('#jpw').val() !=$('#pw_check').val()){
			alert('비밀번호가 일치하지 않습니다.');
			$('#pw').focus();
			return;
		}
		if($('#name').val().length ==0){
			alert('이름은 필수사항입니다..');
			$('#name').focus();
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
		console.log(queryString);
		$.ajax({
			url:'joinOk.do',
			type:'POST',
			data: queryString,
			dataType: 'text',
			success: function(json){
				console.log(json);
				var result=JSON.parse(json);
				if(result.code=="success"){
					alert(result.desc);
					alert("로그인을 진행하세요");
					window.location.replace("mainpage.jsp");
				}else{
					alert(result.desc);
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
        <a class="nav-link" href="main.jsp">Home <span class="sr-only">(current)</span></a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="list.do">전체 게시판</a>
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
      <button type="button"  class="btn btn-outline-secondary" id="IdLogin_loginButton" onclick="modalOpen();">Login</button>
<!-- 		<a id="IdLogin_loginButton" href="javascript:modalOpen()" role="button">로그인</a>         -->
<!--       <button type="button" id="gnbJoin" onclick="javascript:window.location='join.jsp'">회원가입</button> -->
      <a id="gnbJoin" href="join.jsp" >회원가입</a>
    </form>
  </div>
</nav>
<div class="modal" id="exampleModal" tabindex="-1" role="dialog">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      
      <div class="modal-header">
        <div align="center">로그인</div>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      
      <div class="modal-body">
        <form class="main_form">
        	<label class="atom_field">
        		<span class="label visually_hidden">아이디</span>
        		<input type="text" id="id" name="id" autofocus="true" placeholder="아이디">
        	</label>
        	<label class="atom_field">
        		<span class="label visually_hidden">비밀번호</span>
        		<input type="password" id="pw" name="pw" placeholder="비밀번호">
        	</label>
        	<button class=atom_button button signin e-confirm"  type="button" onclick="submit_ajax()">로그인</button>
        </form>
        
      </div>
      	
      <div class="modal-footerform">
     	<div align="center">아직 회원이 아니세요?</div>
     	<div align="center"> <a id="gnbJoin" href="join.jsp" >회원가입</a></div>
      	<br/><br/>
     	<table align="center">
 		 <tr><td><button type="button" id="facebook" class="btn btn-primary btn-lg btn-block"><div class="facebooktext">FaceBook 로그인</div></button></td></tr>
 		 <tr><td><div id="naverIdLogin">
			<button id="naverIdLogin_loginButton" href="#" role="button"><div class="navertext">네이버 로그인</div></button>
		</div></td></tr>
		<tr><td><button type="button" id="login" class="btn btn-primary btn-lg btn-block"><div class="googletext"> Google 로그인</div></td></tr>	
		<tr><td><button type="button" class="btn btn-warning btn-lg btn-block"><div>KakaoTalk 로그인</div></td></tr>
  		</table>
  		   <br/><br/>
      </div>
</div>
  </div>
  </div>

	<script>startApp();</script>
	
<div id="content">
<div id="main_content">	
	<div class="py-5 text-center">
	       <h2>회원가입</h2>
           <p class="lead">회원 가입을 하면 게시판을 열람할 수 있습니다.</p>
    </div>
	<form id="reg_frm">
	<table class="table" style="align:center">
		<tr>
		<th scope="row">아이디</th><td> <input type="text" id="jid" name="id" size="20"></td>
		</tr>
		<tr>
		<th scope="row">비밀번호 : </th><td><input type="password" id="jpw" name="pw" size="20"></td>
		</tr>
		<tr>
		<th scope="row">비밀번호 확인 : </th><td><input type="password" id="pw_check" name="pw_check" size="20"></td>
		</tr>
		<tr>
		<th scope="row">이름 : </th><td><input type="text" id="name" name="name" size="20"></td>
		</tr>
		<tr>
		<th scope="row">메일 : </th><td><input type="text" id="eMail" name="eMail" size="20"></td>
		</tr>
		<tr>
		<th scope="row">주소 : </th><td><input type="text" id="address" name="address" size="50"></td>
		</tr>
		<th><input type="button" value="회원가입" onclick="form_check()">&nbsp;&nbsp;&nbsp;</th>
	</table>
	</form>
</div>
<div id="aside_content">

<br/>
</div>
</div>


	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>

	<script src="naveridlogin_js_sdk_2.0.2.js"></script>

	<script>
		
		var naverLogin = new naver.LoginWithNaverId(
			{

				clientId: "UGPpj08CUtBDtjs47xqn",
				callbackUrl: "http://localhost:8081/Project04/mainpage.jsp",
				isPopup: false,
				loginButton: {color: "green", type: 3, height: 60}
			}
		);
		/* (4) 네아로 로그인 정보를 초기화하기 위하여 init을 호출 */
		naverLogin.init();
		
		/* (4-1) 임의의 링크를 설정해줄 필요가 있는 경우 */
		$("#gnbLogin").attr("href", naverLogin.generateAuthorizeUrl());

		/* (5) 현재 로그인 상태를 확인 */
		window.addEventListener('load', function () {
			naverLogin.getLoginStatus(function (status) {
				if (status) {			
					   ajax_naversubmit();
				}
			});
		});

		
		function ajax_naversubmit(){
			var uid = naverLogin.user.getId();
			var uName = naverLogin.user.getName();
			var nickName = naverLogin.user.getNickName();
			var eMail = naverLogin.user.getEmail();
			$.ajax({
				url:'naverjoinOk.do',
				type:'POST',
				data: {
					id: uid,
					name: uName,
					pw: nickName,
					email: eMail,
				},
				dataType: 'json',
				success: function(json){
					if(json.code=="success"){
						console.log(json.desc);
						naverLogin.logout();
						window.location.replace("main.jsp");
					}else{
						alert(json.desc);
					}
				}
			})
		}
	</script>
	
</body>
</html>
package com.study.jsp;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class loginOk implements Service{
	
	public loginOk() {
		
	}
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		
		System.out.println("loginOk");
		
		request.setCharacterEncoding("UTF-8");
		
		HttpSession session=request.getSession();	
		
		String id=request.getParameter("id");
		String pw=request.getParameter("pw");
		System.out.println(id+" :" +pw);
		
		MemberDAO dao=MemberDAO.getInstance();
		
		int checkNum=dao.userCheck(id, pw);
		String json_data="";
		
		if(checkNum==-1) {
			json_data="{\"code\":\"fail\",\"desc\":\"아이디가 존재하지 않습니다.\"}";
		}else if(checkNum==0) {
			json_data="{\"code\":\"fail\",\"desc\":\"비밀번호가 일치하지 않습니다.\"}";
		}else if(checkNum==1) {
			MemberDTO dto=dao.getMember(id);
			
			String name=dto.getName();
		
			session.setAttribute("id",id);
			session.setAttribute("name",name);
			session.setAttribute("ValidMem","yes");
			json_data="{\"code\":\"success\",\"desc\":\"!!!!로그인 성공!!!!\"}";
		}
		
		response.setContentType("application/json; charset=UTF-8");
		PrintWriter out=response.getWriter();
		out.println(json_data);
		out.close();
	}
}

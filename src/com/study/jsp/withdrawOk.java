package com.study.jsp;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class withdrawOk implements Service
{
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		
		System.out.println("withdrawOk");
		
		request.setCharacterEncoding("UTF-8");
		
		HttpSession session=request.getSession();	
		
		String id=request.getParameter("id");
		String pw=request.getParameter("pw");
		System.out.println(id+" : "+pw);
		
		MemberDAO dao=MemberDAO.getInstance();
		String json_data="";
		
		if(pw.equals("관리자")) {
			dao.withdraw(id);
			json_data="{\"code\":\"success\",\"desc\":\"회원 강제 탈퇴 완료\"}";
		}else {
			int checkNum=dao.userCheck(id, pw);
						
			if(checkNum==0) {
				json_data="{\"code\":\"fail\",\"desc\":\"비밀번호가 일치하지 않습니다.\"}";
			}else if(checkNum==1) {
				dao.withdraw(id);
				json_data="{\"code\":\"success\",\"desc\":\"회원탈퇴에 성공하였습니다.\"}";
			}
		}
				
		response.setContentType("application/json; charset=UTF-8");
		PrintWriter out=response.getWriter();
		out.println(json_data);
		out.close();

	}
}

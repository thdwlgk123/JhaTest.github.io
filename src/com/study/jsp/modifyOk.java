package com.study.jsp;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class modifyOk implements Service{
	
	public modifyOk() {
		
	}
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		
		System.out.println("modifyOk");
		request.setCharacterEncoding("UTF-8");
		String pw=request.getParameter("pw");
		String eMail=request.getParameter("eMail");
		String address=request.getParameter("address");
		
		HttpSession session=request.getSession();	
		String id=(String)session.getAttribute("id");
		
		MemberDTO dto=new MemberDTO();
		dto.setId(id);
		dto.setPw(pw);
		dto.seteMail(eMail);
		dto.setAddress(address);
		
		MemberDAO dao=MemberDAO.getInstance();
		int ri=dao.updateMember(dto);
		
		String json_data="";
		
		if(ri==1) {
			json_data="{\"code\":\"success\",\"desc\":\"정보가 수정되었습니다.\"}";
		
		}else {
			json_data= "{\"code\":\"fail\",\"desc\":\"정보 수정에 실패하였습니다.\"}";
		}
		
		response.setContentType("application/json; charset=UTF-8");
		PrintWriter out=response.getWriter();
		out.println(json_data);
		out.close();

	}
}


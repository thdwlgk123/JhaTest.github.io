package com.study.jsp;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class naverjoinOk implements Service {
	
	
    public naverjoinOk() {
      
    }
    
    public void execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
    	System.out.println("NaverjoinOk");
		
		request.setCharacterEncoding("UTF-8");
		
		HttpSession session=request.getSession();	
		
		String id=request.getParameter("id");
		String pw=request.getParameter("pw"); //pw는 닉네임값 받아옴
		String name=request.getParameter("name");
		String eMail=request.getParameter("email");
//		String profileImage=request.getParameter("profileImage");
		
		System.out.println(id+":"+pw+":"+name+":"+eMail);
		System.out.println("profileImage");
		
		MemberDTO dto=new MemberDTO();
		dto.setId(id);
		dto.setPw(pw);
		dto.setName(name);
		dto.seteMail(eMail);
		dto.setAddress("네이버로그인");
		dto.setrDate(new Timestamp(System.currentTimeMillis()));
		
		MemberDAO dao=MemberDAO.getInstance();
		String json_data="";
		
		if(dao.confirmId(dto.getId())==MemberDAO.MEMBER_EXISTENT) {
			session.setAttribute("id", dto.getId());
			session.setAttribute("name", dto.getName());
//			session.setAttribute("profile",	profileImage);
			session.setAttribute("ValidMem","yes");
			json_data="{\"code\":\"success\",\"desc\":\"로그인에 성공하였습니다.\"}";
		}else {
			int ri=dao.insertMember(dto);
			if(ri==MemberDAO.MEMBER_JOIN_SUCCESS) {

				session.setAttribute("id", dto.getId());
				session.setAttribute("name", dto.getName());
//				session.setAttribute("profile",	profileImage);
				session.setAttribute("ValidMem","yes");
				json_data="{\"code\":\"success\",\"desc\":\"회원 등록이 되어있지 않아 회원등록 후 로그인하였습니다.\"}";
			}else {
				json_data= "{\"code\":\"fail\",\"desc\":\"에러가 발생하여 회원등록에 실패했습니다.\"}";
			}
		}
		
		response.setContentType("application/json; charset=UTF-8");
		PrintWriter out=response.getWriter();
		out.println(json_data);
		out.close();
//		session.invalidate();

    }
}

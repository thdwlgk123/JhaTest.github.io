package com.study.jsp.manager;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.study.jsp.MemberDAO;
import com.study.jsp.MemberDTO;

public class MBlackCheckCommand implements MCommand
{
	public void execute(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException
	{
		System.out.println("joinOk");
		
		request.setCharacterEncoding("UTF-8");
		String id=request.getParameter("id");
		
		MDAo mdao=MDAo.getInstance();
		int check=mdao.blackCheck(id);
		
		System.out.println(check);
		
		String json_data="";
		if(check==1) {
			json_data="{\"code\":\"fail\",\"desc\":\"이미 블랙리스트 회원입니다.\"}";
		}else if(check==-1) {
			json_data="{\"code\":\"success\",\"desc\":\"블랙리스트 등록을 진행합니다.\"}";
		}else {
			json_data="{\"code\":\"fail\",\"desc\":\"블랙리스트 체크실패.\"}";
		}
		
		response.setContentType("application/json; charset=UTF-8");
		PrintWriter out=response.getWriter();
		out.println(json_data);
		out.close();
		
	}
}

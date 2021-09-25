package com.study.jsp.manager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.study.jsp.BDao;

public class MWriteCommand implements MCommand
{
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session=request.getSession();	
		
		String bName=(String)session.getAttribute("name");
		String bTitle=request.getParameter("bTitle");
		String bContent=request.getParameter("bContent");
		
		System.out.println(bName);
		
		MDAo mdao=MDAo.getInstance();
		mdao.noticewrite(bName, bTitle, bContent);
	}
}

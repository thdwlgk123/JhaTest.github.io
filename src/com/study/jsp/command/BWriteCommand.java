package com.study.jsp.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.study.jsp.BDao;
import com.study.jsp.MemberDTO;

public class BWriteCommand implements BCommand {
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
	{
		HttpSession session=request.getSession();	
		
		String bName=(String)session.getAttribute("name");
		String bTitle=request.getParameter("bTitle");
		String bContent=request.getParameter("bContent");
		
		System.out.println(bName);
		
		BDao dao=BDao.getInstance();
		dao.write(bName, bTitle, bContent);
	}
}

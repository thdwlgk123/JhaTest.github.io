package com.study.jsp.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.study.jsp.BDao;
import com.study.jsp.BDto;



public class BContentCommand implements BCommand {
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
	{
		String bId=request.getParameter("bId");
		String id=request.getParameter("id");
		
		System.out.println(id);
		
		BDao dao=BDao.getInstance();
		BDto dto=dao.contentView(id, bId);		
		request.setAttribute("content_view", dto);
		
	}
}

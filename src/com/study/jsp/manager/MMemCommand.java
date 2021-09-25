package com.study.jsp.manager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.study.jsp.BDao;
import com.study.jsp.BDto;


public class MMemCommand implements MCommand {
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
	{
		String id=request.getParameter("id");
		MDAo mdao=MDAo.getInstance();
		ManMemDTO mdto=mdao.memberView(id);		
		request.setAttribute("member_view", mdto);
		
	}
}
package com.study.jsp.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.study.jsp.BDao;
import com.study.jsp.BDto;

public class BNoticeCommand implements BCommand
{
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
	{
		String bId=request.getParameter("bId");
		String id=request.getParameter("id");
		BDao dao=BDao.getInstance();
		BDto dto=dao.noticeView(id, bId);		
		request.setAttribute("notice_view", dto);		
	}
}

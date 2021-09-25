package com.study.jsp.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.study.jsp.BDao;

public class BCommentCommand implements BCommand {
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
	{
		String comment_bid=request.getParameter("bId");
		String bName=request.getParameter("comment_bname");
		String bContent=request.getParameter("comment_content");
		
		int bId=Integer.parseInt(comment_bid);
		
		System.out.println(bId+":"+bName+":"+bContent);
		
		BDao dao=BDao.getInstance();
		dao.commentWrite(bName, bContent, bId);

	}
}

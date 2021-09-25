package com.study.jsp.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.study.jsp.BDao;

public class BNCommentCommand implements BCommand {
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
	{
		String comment_bid=request.getParameter("bId");
		String bName=request.getParameter("comment_bname");
		String bContent=request.getParameter("comment_content");
		System.out.println(comment_bid+":"+bName+":"+bContent);

		int bId=Integer.parseInt(comment_bid);
		
				
		BDao dao=BDao.getInstance();
		dao.NcommentWrite(bName, bContent, bId);

	}
}

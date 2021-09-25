package com.study.jsp.command;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.study.jsp.BDao;
import com.study.jsp.BDto;
import com.study.jsp.BPageInfo;

public class BNListCommentCommand implements BCommand {

	public void execute(HttpServletRequest request, HttpServletResponse response) {
		
		String comment_bid=(String)request.getParameter("bId");
		System.out.println(comment_bid);
		int bId=Integer.parseInt(comment_bid);
		
		int nPage=1;
		try {
			String sPage=request.getParameter("page");
			nPage=Integer.parseInt(sPage);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		BDao dao=BDao.getInstance();
		BPageInfo pinfo=dao.NcommentPage(bId, nPage);
		request.setAttribute("comment_page", pinfo);
		
		nPage=pinfo.getCurPage();
		
		HttpSession session=null;
		session=request.getSession();
		session.setAttribute("comment_cpage", nPage);
		
		ArrayList<BDto> dtos=dao.Ncommentlist(bId,nPage);
		request.setAttribute("commentlist", dtos);
	}

}

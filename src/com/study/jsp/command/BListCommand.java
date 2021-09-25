package com.study.jsp.command;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.study.jsp.BDao;
import com.study.jsp.BDto;
import com.study.jsp.BPageInfo;



public class BListCommand implements BCommand {

	public void execute(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("emfdha");
		int nPage=1;
		try {
			String sPage=request.getParameter("page");
			nPage=Integer.parseInt(sPage);
		}catch(Exception e) {
//			e.printStackTrace();
		}
		
		BDao dao=BDao.getInstance();
		BPageInfo pinfo=dao.articlePage(nPage);
		request.setAttribute("page", pinfo);
		
		nPage=pinfo.getCurPage();
		
		HttpSession session=null;
		session=request.getSession();
		session.setAttribute("cpage", nPage);
		
		ArrayList<BDto> dtos=dao.list(nPage);
		request.setAttribute("list", dtos);
	}

}

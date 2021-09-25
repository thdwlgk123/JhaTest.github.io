package com.study.jsp.command;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.study.jsp.BDao;
import com.study.jsp.BDto;
import com.study.jsp.BPageInfo;

public class BSearchCommand implements BCommand{
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		
		System.out.println("searchOk");
		String searchtype="";
		String searchtext="";
		HttpSession session=null;
		session=request.getSession();
		
		int nPage=1;
		try {
			request.setCharacterEncoding("UTF-8");
			
			searchtype=request.getParameter("searchtype");
			searchtext=request.getParameter("searchtext");
			
			nPage=1;
			
			String sPage=request.getParameter("page");
			nPage=Integer.parseInt(sPage);
		}catch(Exception e) {
//			e.printStackTrace();
		}
		
		BDao dao=BDao.getInstance();
		BPageInfo pinfo=dao.searchPage(searchtype, searchtext, nPage);
		request.setAttribute("page", pinfo);
		
		nPage=pinfo.getCurPage();
		session.setAttribute("cpage", nPage);
		
		searchtype=((BPageInfo) request.getAttribute("page")).getSearchType();
		
		request.setAttribute("searchtype", searchtype);
		request.setAttribute("searchtext", searchtext);
		
		ArrayList<BDto> dtos=dao.searchlist(searchtype, searchtext, nPage);
		request.setAttribute("list", dtos);

	}

}

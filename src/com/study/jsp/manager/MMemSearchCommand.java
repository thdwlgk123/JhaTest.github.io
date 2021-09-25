package com.study.jsp.manager;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.study.jsp.BDao;
import com.study.jsp.BDto;
import com.study.jsp.BPageInfo;

public class MMemSearchCommand implements MCommand {

	public void execute(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("membersearchOk");
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
		
		MDAo mdao=MDAo.getInstance();
		BPageInfo pinfo=mdao.msearchPage(searchtype, searchtext, nPage);
		request.setAttribute("memsearch_page", pinfo);
		
		nPage=pinfo.getCurPage();
		session.setAttribute("cpage", nPage);
		
		searchtype=((BPageInfo) request.getAttribute("memsearch_page")).getSearchType();
		
		request.setAttribute("searchtype", searchtype);
		request.setAttribute("searchtext", searchtext);
		
		ArrayList<ManMemDTO> dtos=mdao.memsearchlist(searchtype, searchtext, nPage);
		request.setAttribute("memsearchlist", dtos);
	}
}

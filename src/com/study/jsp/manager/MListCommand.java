package com.study.jsp.manager;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.study.jsp.BDao;
import com.study.jsp.BDto;
import com.study.jsp.BPageInfo;


public class MListCommand implements MCommand {

	public void execute(HttpServletRequest request, HttpServletResponse response) {
		int nPage=1;
		try {
			String sPage=request.getParameter("page");
			nPage=Integer.parseInt(sPage);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		MDAo mdao=MDAo.getInstance();
		BPageInfo pinfo=mdao.memberPage(nPage);
		request.setAttribute("page", pinfo);
		
		nPage=pinfo.getCurPage();
		
		HttpSession session=null;
		session=request.getSession();
		session.setAttribute("cpage", nPage);
		
		ArrayList<ManMemDTO> dtos=mdao.memlist(nPage);
		request.setAttribute("memlist", dtos);
	}
}

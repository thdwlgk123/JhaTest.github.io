package com.study.jsp.command;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.study.jsp.BDao;
import com.study.jsp.BDto;
import com.study.jsp.BPageInfo;

public class BNoticeListCommand implements BCommand
{
public void execute(HttpServletRequest request, HttpServletResponse response) {
		
		int nPage=1;
		try {
			String sPage=request.getParameter("page");
			nPage=Integer.parseInt(sPage);
		}catch(Exception e) {
//			e.printStackTrace();
		}
		
		BDao dao=BDao.getInstance();
		BPageInfo pinfo=dao.noticePage(nPage);
		request.setAttribute("notice_page", pinfo);
		
		nPage=pinfo.getCurPage();
		
		HttpSession session=null;
		session=request.getSession();
		session.setAttribute("cpage", nPage);
		
		ArrayList<BDto> dtos=dao.noticelist(nPage);
		request.setAttribute("noticelist", dtos);
	}

}

package com.study.jsp.command;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.study.jsp.BDao;
import com.study.jsp.BDto;
import com.study.jsp.BPageInfo;

public class BMainPageCommand implements BCommand {

	public void execute(HttpServletRequest request, HttpServletResponse response) {

		int nPage=1;
		
		BDao dao=BDao.getInstance();
		
		ArrayList<BDto> dtos=dao.mainList(nPage);
		request.setAttribute("list", dtos);
		
		ArrayList<BDto> dton=dao.mainNList(nPage);
		request.setAttribute("nlist", dton);
	}

}

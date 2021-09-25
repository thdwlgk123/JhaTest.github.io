package com.study.jsp.command;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.study.jsp.BDao;

public class BNCommentDeleteCommand implements BCommand{
	public void execute(HttpServletRequest request, HttpServletResponse response){
		String bId=request.getParameter("bId");

		BDao dao=BDao.getInstance();
		int check1=dao.deleteNcomment(bId);
	
		System.out.println(check1);
		
		String json_data="";
		if(check1==1) {
			json_data="{\"code\":\"success\",\"desc\":\"댓글 삭제를 완료하였습니다.\"}";
		}else {
			json_data="{\"code\":\"fail\",\"desc\":\"댓글 삭제 오류입니다.\"}";
		}
		
		try {
			response.setContentType("application/json; charset=UTF-8");
			PrintWriter out=response.getWriter();
			out.println(json_data);
			out.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}



}

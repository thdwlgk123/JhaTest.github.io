package com.study.jsp.manager;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MBlackCommand implements MCommand {
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		String id=request.getParameter("id");
		String name=request.getParameter("name");
		
		MDAo mdao=MDAo.getInstance();
		int check=mdao.insertBlack(id, name);		
		
		String json_data="";
		if(check==1) {
			json_data="{\"code\":\"success\",\"desc\":\"블랙리스트 등록을 완료하였습니다.\"}";
		}else {
			json_data="{\"code\":\"fail\",\"desc\":\"블랙리스트 등록 오류.\"}";
		}
		
		response.setContentType("application/json; charset=UTF-8");
		PrintWriter out=response.getWriter();
		out.println(json_data);
		out.close();
		
	}
}

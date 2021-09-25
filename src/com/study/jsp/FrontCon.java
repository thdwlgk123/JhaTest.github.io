package com.study.jsp;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.study.jsp.command.BCommand;
import com.study.jsp.command.BCommentCommand;
import com.study.jsp.command.BCommentDeleteCommand;
import com.study.jsp.command.BContentCommand;
import com.study.jsp.command.BDeleteCommand;
import com.study.jsp.command.BListCommand;
import com.study.jsp.command.BListCommentCommand;
import com.study.jsp.command.BMainPageCommand;
import com.study.jsp.command.BModifyCommand;
import com.study.jsp.command.BNCommentCommand;
import com.study.jsp.command.BNCommentDeleteCommand;
import com.study.jsp.command.BNListCommentCommand;
import com.study.jsp.command.BNModifyCommand;
import com.study.jsp.command.BNoticeCommand;
import com.study.jsp.command.BNoticeListCommand;
import com.study.jsp.command.BNoticeSearchCommand;
import com.study.jsp.command.BReplyCommand;
import com.study.jsp.command.BReplyViewCommand;
import com.study.jsp.command.BSearchCommand;
import com.study.jsp.command.BWriteCommand;
import com.study.jsp.manager.MBlackCheckCommand;
import com.study.jsp.manager.MBlackCommand;
import com.study.jsp.manager.MCommand;
import com.study.jsp.manager.MListCommand;
import com.study.jsp.manager.MMemCommand;
import com.study.jsp.manager.MMemSearchCommand;
import com.study.jsp.manager.MWriteCommand;


@WebServlet({"*.do","*.mng"})
public class FrontCon extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public FrontCon() {
        super();

    }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {

		System.out.println("doGet");
		actionDo(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		System.out.println("doPost");
		actionDo(request, response);
	}
	
	private void actionDo(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {

		System.out.println("actionDo");		
		
		request.setCharacterEncoding("UTF-8");
		
		String viewPage=null;
		BCommand command=null;
		MCommand mcom=null;
		
		String uri=request.getRequestURI();
		String conPath=request.getContextPath();
		String com=uri.substring(conPath.length());
		System.out.println("command : "+com);
		
		HttpSession session=null;
		session=request.getSession();
		int curPage=1;
		
		if(com.equals("/loginOk.do")) {
			Service service=new loginOk();
			service.execute(request, response);
		}else if(com.equals("/naverjoinOk.do")) {
			Service service=new naverjoinOk();
			service.execute(request, response);
		} else if(com.equals("/googlejoinOk.do")) {
			Service service=new googlejoinOk();
			service.execute(request, response);
		} 
		else if(com.equals("/modifyOk.do")) {
			Service service=new modifyOk();
			service.execute(request, response);
		}else if(com.equals("/joinOk.do")) {
			Service service=new joinOk();
			service.execute(request, response);
		}else if(com.equals("/logout.do")) {
			logoutOk(request,response);
		}else if(com.equals("/main.do")) {
			System.out.println("main.do");
			command=new BMainPageCommand();
			command.execute(request, response);
			viewPage="main.jsp";
		} 
		else if(com.equals("/mngmain.do")) {
			System.out.println("mngmain.do");
			command=new BMainPageCommand();
			command.execute(request, response);
			viewPage="mngmain.jsp";
		} 
		else if(com.equals("/withdraw.do")) {
			Service service=new withdrawOk();
			service.execute(request, response);
		} 
		else if(com.equals("/write_view.do")) {
			viewPage="write_view.jsp";
		}	
		else if(com.equals("/write.do")) {
			command=new BWriteCommand();
			command.execute(request,  response);
			viewPage="list.do";
		}else if (com.equals("/list.do")) {
			command=new BListCommand();
			command.execute(request, response);
			viewPage="list.jsp";
		}
		else if (com.equals("/content_view.do")) {
			command=new BListCommentCommand();
			command.execute(request, response);
			
			command=new BContentCommand();
			command.execute(request, response);
			viewPage="content_view.jsp";
		}else if (com.equals("/modify_view.do")) {
			command=new BContentCommand();
			command.execute(request, response);
			viewPage="modify_view.jsp";
		}else if (com.equals("/modify.do")) {
			command=new BModifyCommand();
			command.execute(request, response);
			
			command=new BContentCommand();
			command.execute(request, response);
			viewPage="content_view.jsp";
		}else if (com.equals("/delete.do")) {
			command=new BDeleteCommand();
			command.execute(request, response);
//			viewPage="list.do?page="+curPage;
		}else if (com.equals("/reply_view.do")) {
			command=new BReplyViewCommand();
			command.execute(request, response);
			String bId=request.getParameter("bId");
//			System.out.println("FRONT 출력"+bId);
			viewPage="reply_view.jsp";
		}else if (com.equals("/reply.do")) {
			command=new BReplyCommand();
			command.execute(request, response);
			viewPage="list.do?page="+curPage;
		}else if (com.equals("/notice_view.do")) {
			command=new BNoticeCommand();
			command.execute(request, response);
			
			command=new BNListCommentCommand();
			command.execute(request, response);
			viewPage="notice_view.jsp";
		}
		else if (com.equals("/noticewrite.mng")) {
			mcom=new MWriteCommand();
			mcom.execute(request, response);
			viewPage="noticelist.do?";
		}else if (com.equals("/noticewrite_view.do")) {
			viewPage="noticewrite_view.jsp";
		}
		
		else if (com.equals("/search.do")) {
			command=new BSearchCommand();
			command.execute(request, response);
			viewPage="searchlist.jsp";
		}else if(com.equals("/comment.do")) {
			command=new BCommentCommand();
			command.execute(request, response);
			viewPage="commentlist.do";
		}else if(com.equals("/ncomment.do")) {
			command=new BNCommentCommand();
			command.execute(request, response);
			viewPage="ncommentlist.do";
		}
		else if(com.equals("/ncommentlist.do")) {
			command=new BNListCommentCommand();
			command.execute(request, response);
			
			command=new BNoticeCommand();
			command.execute(request, response);
			viewPage="notice_view.jsp";
		}
		else if(com.equals("/commentlist.do")) {
			command=new BListCommentCommand();
			command.execute(request, response);
			
			command=new BContentCommand();
			command.execute(request, response);
			viewPage="content_view.jsp";
		}	else if(com.equals("/commentdelete.do")) {
			command=new BCommentDeleteCommand();
			command.execute(request, response);
		}	
		else if(com.equals("/ncommentdelete.do")) {
			command=new BNCommentDeleteCommand();
			command.execute(request, response);
		}	
		else if(com.equals("/noticelist.do")) {
			command=new BNoticeListCommand();
			command.execute(request, response);
			viewPage="noticelist.jsp";
		}else if (com.equals("/noticesearch.do")) {
			command=new BNoticeSearchCommand();
			command.execute(request, response);
			viewPage="noticesearchlist.jsp";
		}	
		else if (com.equals("/nmodify_view.do")) {
			command=new BNoticeCommand();
			command.execute(request, response);
			viewPage="nmodify_view.jsp";
		}
		else if (com.equals("/nmodify.do")) {
			command=new BNModifyCommand();
			command.execute(request, response);
			
			command=new BNoticeCommand();
			command.execute(request, response);
			viewPage="notice_view.jsp";
		}
		else if(com.equals("/memberlist.mng")) {
			mcom=new MListCommand();
			mcom.execute(request, response);
			viewPage="memlist.jsp";		
		}else if(com.equals("/memsearch.mng")) {
			mcom=new MMemSearchCommand();
			mcom.execute(request, response);
			viewPage="memsearchlist.jsp";
		}else if(com.equals("/member_view.mng")) {
			mcom=new MMemCommand();
			mcom.execute(request, response);
			viewPage="member_view.jsp";
		}
		else if(com.equals("/blackcheck.mng")) {
			mcom=new MBlackCheckCommand();
			mcom.execute(request, response);
		}else if(com.equals("/insertblack.mng")) {
			mcom=new MBlackCommand();
			mcom.execute(request, response);
		}else if(com.equals("/loginblackcheck.do")) {
			mcom=new MBlackCheckCommand();
			mcom.execute(request, response);
		}
		
		
		if(viewPage!=null) {
			RequestDispatcher dispatcher=request.getRequestDispatcher(viewPage);
			dispatcher.forward(request, response);
		}
	}
	
	public void logoutOk(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException{
		System.out.println("logoutOk");
		HttpSession session=request.getSession();	
		session.invalidate();
		response.sendRedirect("mainpage.jsp");		
	}
}

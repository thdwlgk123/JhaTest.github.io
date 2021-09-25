package com.study.jsp.manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.study.jsp.BDao;
import com.study.jsp.BDto;
import com.study.jsp.BPageInfo;
import com.study.jsp.MemberDAO;
import com.study.jsp.MemberDTO;

public class MDAo
{
	private static MDAo instance=new MDAo();
	DataSource dataSource;
	
	int listCount=10;
	int pageCount=4;
	
	private MDAo() {
		try {
			Context context=new InitialContext();
			dataSource=(DataSource)context.lookup("java:comp/env/jdbc/Oracle11g");
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	public static MDAo getInstance() {
		return instance;
	}
	
	public void noticewrite(String bName, String bTitle, String bContent) {
		Connection con=null;
		PreparedStatement pstmt=null;
		
		try {
			con=dataSource.getConnection();
			String query="insert into mvc_noticeboard"+
						"(bId, bName, bTitle, bContent, bHit, bGroup, bStep, bIndent)"+
						"values "+
						"(mvc_noticeboard_seq.nextval, ?, ?, ?, 0, mvc_noticeboard_seq.currval, 0, 0)";
			pstmt=con.prepareStatement(query);
			pstmt.setString(1, bName);
			pstmt.setString(2, bTitle);
			pstmt.setString(3, bContent);
			int rn=pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(pstmt!=null) pstmt.close();
				if(con!=null) pstmt.close();
			}catch(Exception e2) {
				e2.printStackTrace();
			}
		}
	}
	
	public ArrayList<ManMemDTO> memsearchlist(String searchtype, String searchtext, int curPage){
		
		ArrayList<ManMemDTO> dtos=new ArrayList<ManMemDTO>();
		Connection con=null;
		Statement stmt=null;		
		ResultSet resultSet=null;

		String id="";
		String name="";
		String email="";
		Timestamp rdate=null;
		String address="";
		try{
			con=dataSource.getConnection();
			stmt=con.createStatement();
			StringBuffer sb=new StringBuffer();
			
			System.out.println(searchtype);
			System.out.println(searchtext);
			sb.setLength(0);
			sb.append("select * from members");
			sb.append(" where "+searchtype+" like '%"+searchtext+"%'");
			sb.append(" order by "+searchtype);
			resultSet=stmt.executeQuery(sb.toString());

			while(resultSet.next()) {
				id=resultSet.getString("ID");
				name=resultSet.getString("Name");
				email=resultSet.getString("Email");
				rdate=resultSet.getTimestamp("Rdate");
				address=resultSet.getString("Address");
				
				ManMemDTO dto=new ManMemDTO(id, name, email, rdate, address);
				dtos.add(dto);	
			}
				
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(resultSet !=null) resultSet.close();
				if(stmt!=null) stmt.close();
				if(con!=null) con.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		return dtos;
	}
	
	public ArrayList<ManMemDTO> memlist(int curPage){
		
		ArrayList<ManMemDTO> dtos=new ArrayList<ManMemDTO>();
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet resultSet=null;
		
		int nStart=(curPage-1)*listCount+1;
		int nEnd=(curPage-1)*listCount+listCount;
		
		String id="";
		String name="";
		String email="";
		Timestamp rdate=null;
		String address="";
		try{
			con=dataSource.getConnection();
			
			String query="select * from members where id!='manager' order by name";
			
			pstmt=con.prepareStatement(query);
			resultSet=pstmt.executeQuery();
			
			while(resultSet.next()) {
				id=resultSet.getString("ID");
				name=resultSet.getString("Name");
				email=resultSet.getString("Email");
				rdate=resultSet.getTimestamp("Rdate");
				address=resultSet.getString("Address");
				
				ManMemDTO dto=new ManMemDTO(id, name, email, rdate, address);
				dtos.add(dto);	
			}
						
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(resultSet !=null) resultSet.close();
				if(pstmt!=null) pstmt.close();
				if(con!=null) con.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		return dtos;
	}
	
	public BPageInfo memberPage(int curPage) {
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet resultSet=null;
		
		int totalCount=0;
		try {
			con=dataSource.getConnection();
			
			String query="select count(*) as total from members where id!='manager'";
			pstmt=con.prepareStatement(query);
			resultSet=pstmt.executeQuery();
			
			if(resultSet.next()) {
				totalCount=resultSet.getInt("total");
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (resultSet !=null) resultSet.close();
				if(pstmt!=null) pstmt.close();
				if(con!=null) con.close();
			}catch(Exception e2) {
				e2.printStackTrace();
			}
		}
		
		int totalPage=totalCount/listCount;
		if(totalCount % listCount >0)
			totalPage++;		
		int myCurPage=curPage;
		if(myCurPage>totalPage)
			myCurPage=totalPage;
		if(myCurPage<1)
			myCurPage=1;
		
		int startPage=((myCurPage-1)/pageCount)*pageCount+1;
		
		int endPage=startPage+pageCount-1;
		if(endPage>totalPage)
			endPage=totalPage;
		
		BPageInfo pinfo=new BPageInfo();
		pinfo.setTotalCount(totalCount);
		pinfo.setListCount(listCount);
		pinfo.setTotalPage(totalPage);
		pinfo.setCurPage(myCurPage);
		pinfo.setPageCount(pageCount);
		pinfo.setStartPage(startPage);
		pinfo.setEndPage(endPage);
		
		return pinfo;
		
	}
	public BPageInfo msearchPage(String searchtype, String searchtext, int curpage) {
		Connection con=null;
		ResultSet rs=null;
		Statement stmt=null;

		
		if(searchtype.equals("name")) {
			searchtype="name";
		} else if(searchtype.equals("id")) {
			searchtype="id";
		} 

		int totalCount=0;
		StringBuffer sb=new StringBuffer();
				
		try {
			con=dataSource.getConnection();
			stmt=con.createStatement();
			sb.setLength(0);
			sb.append("select count(*) as total from members where "+searchtype+" like '%"+searchtext+"%'");
			rs=stmt.executeQuery(sb.toString());

			if(rs.next()) {
				totalCount=rs.getInt("total");
				
				System.out.println(totalCount);
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs !=null) rs.close();
				if(stmt!=null) stmt.close();
				if(con!=null) con.close();
			}catch(Exception e2) {
				e2.printStackTrace();
			}
		}
		
		int totalPage=totalCount/listCount;
		if(totalCount % listCount >0)
			totalPage++;
		
		int myCurPage=curpage;
		if(myCurPage>totalPage)
			myCurPage=totalPage;
		if(myCurPage<1)
			myCurPage=1;
		
		int startPage=((myCurPage-1)/pageCount)*pageCount+1;
		
		int endPage=startPage+pageCount-1;
		if(endPage>totalPage)
			endPage=totalPage;
		
		BPageInfo pinfo=new BPageInfo();
		pinfo.setTotalCount(totalCount);
		pinfo.setListCount(listCount);
		pinfo.setTotalPage(totalPage);
		pinfo.setCurPage(myCurPage);
		pinfo.setPageCount(pageCount);
		pinfo.setStartPage(startPage);
		pinfo.setEndPage(endPage);
	
		pinfo.setSearchType(searchtype);
		return pinfo;
		
	}
	public ManMemDTO memberView(String strID) {
		
		ManMemDTO mdto=null;
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet resultSet=null;
		
		System.out.println(strID);
		
		try {
			con=dataSource.getConnection();
			
			String query="select * from members where Id=?";
			pstmt=con.prepareStatement(query);
			pstmt.setString(1,  strID);
			resultSet=pstmt.executeQuery();
			
			if(resultSet.next()){
				String id=resultSet.getString("ID");
				String name=resultSet.getString("Name");
				String email=resultSet.getString("Email");
				Timestamp rdate=resultSet.getTimestamp("Rdate");
				String address=resultSet.getString("Address");
				
				mdto=new ManMemDTO(id, name, email, rdate, address);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(resultSet!=null) resultSet.close();
				if(pstmt!=null) pstmt.close();
				if(con!=null) con.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		return mdto;
	}
	public int blackCheck(String id) {
		int ri=0;
		
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet set=null;
		String query="select name from mvc_blacklist where id=?";
		
		try {
			con=dataSource.getConnection();
			pstmt=con.prepareStatement(query);
			pstmt.setString(1,id);
			set=pstmt.executeQuery();
			
			if(set.next()) {
				ri=1;
				System.out.println("ri : "+ri);
			}else {
				ri=-1;
				System.out.println("블랙리스트에 없음");
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				set.close();
				pstmt.close();
				con.close();
			}catch(Exception e2) {
				e2.printStackTrace();
			}
		}
		
		return ri;
		
	}
	
	public int insertBlack(String id, String name) {
		int ri=0;
		
		Connection con=null;
		PreparedStatement pstmt=null;
		String query="insert into mvc_blacklist values (?,?)";
		
		try {
			con=dataSource.getConnection();
			pstmt=con.prepareStatement(query);
			pstmt.setString(1,  id);
			pstmt.setString(2,  name);
			pstmt.executeUpdate();
			ri=1;
			System.out.println("등록성공");
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(pstmt!=null) pstmt.close();
				if(con!=null) con.close();
			}catch(Exception e2) {
				e2.printStackTrace();
			}
		}
		return ri;
	}
}

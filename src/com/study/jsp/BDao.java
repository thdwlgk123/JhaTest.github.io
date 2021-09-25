package com.study.jsp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;


public class BDao {
	private static BDao instance=new BDao();
	DataSource dataSource;
	
	int listCount=10;
	int pageCount=4;
	
	private BDao() {
		try {
			Context context=new InitialContext();
			dataSource=(DataSource)context.lookup("java:comp/env/jdbc/Oracle11g");
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	public static BDao getInstance() {
		return instance;
	}
	
	public void write(String bName, String bTitle, String bContent) {
		Connection con=null;
		PreparedStatement pstmt=null;
		
		try {
			con=dataSource.getConnection();
			String query="insert into mvc_board"+
						"(bId, bName, bTitle, bContent, bHit, bGroup, bStep, bIndent)"+
						"values "+
						"(mvc_board_seq.nextval, ?, ?, ?, 0, mvc_board_seq.currval, 0, 0)";
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
	public void commentWrite(String bName, String bContent, int bId) {
		Connection con=null;
		PreparedStatement pstmt=null;
		
		try {
			con=dataSource.getConnection();
			String query="insert into mvc_commentboard"+
						"(bId, bName, bContent, bGroup, bStep, bIndent)"+
						"values "+
						"(mvc_commentboard_seq.nextval, ?, ?, ?, 0, 0)";
			pstmt=con.prepareStatement(query);
			pstmt.setString(1, bName);
			pstmt.setString(2, bContent);
			pstmt.setInt(3, bId);
			int rn=pstmt.executeUpdate();
			System.out.println("db 추가완료");
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
	public void NcommentWrite(String bName, String bContent, int bId) {
		Connection con=null;
		PreparedStatement pstmt=null;
		
		try {
			con=dataSource.getConnection();
			String query="insert into mvc_ncommentboard"+
						"(bId, bName, bContent, bGroup, bStep, bIndent)"+
						"values "+
						"(mvc_ncommentboard_seq.nextval, ?, ?, ?, 0, 0)";
			pstmt=con.prepareStatement(query);
			pstmt.setString(1, bName);
			pstmt.setString(2, bContent);
			pstmt.setInt(3, bId);
			int rn=pstmt.executeUpdate();
			System.out.println("db 추가완료");
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
	
	public ArrayList<BDto> list(int curPage){
		
		ArrayList<BDto> dtos=new ArrayList<BDto>();
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet resultSet=null;
		
		int nStart=(curPage-1)*listCount+1;
		int nEnd=(curPage-1)*listCount+listCount;
		
		try{
			con=dataSource.getConnection();
			
			String query="select * "+
						" from ( "+
						" select rownum num, A.* "+
						" from( "+
						" select * from mvc_board "+
						" order by bgroup desc, bstep asc ) A "+
						" where rownum <=?) B "+
						" where B.num >=? ";
			
			pstmt=con.prepareStatement(query);
			pstmt.setInt(1, nEnd);
			pstmt.setInt(2, nStart);
			resultSet=pstmt.executeQuery();
			
			while(resultSet.next()) {
				int bId=resultSet.getInt("bId");
				String bName=resultSet.getString("bName");
				String bTitle=resultSet.getString("bTitle");
				String bContent=resultSet.getString("bContent");
				Timestamp bDate=resultSet.getTimestamp("bDate");
				int bHit=resultSet.getInt("bHit");
				int bGroup=resultSet.getInt("bGroup");
				int bStep=resultSet.getInt("bStep");
				int bIndent=resultSet.getInt("bIndent");
								
				long time=(System.currentTimeMillis()-bDate.getTime());
				long bHour=TimeUnit.MILLISECONDS.toHours(time);
				
				BDto dto=new BDto(bId, bName, bTitle, bContent, bDate, bHit, bGroup, bStep, bIndent, bHour);
				
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
	public ArrayList<BDto> mainList(int curPage){
		
		ArrayList<BDto> dtos=new ArrayList<BDto>();
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet resultSet=null;
		
		try{
			con=dataSource.getConnection();
			
			String query="select * "+
						" from ( "+
						" select rownum num, A.* "+
						" from( "+
						" select * from mvc_board "+
						" order by bgroup desc, bstep asc ) A "+
						" where rownum <=5) B "+
						" where B.num >=1 ";
			
			pstmt=con.prepareStatement(query);
			resultSet=pstmt.executeQuery();
			
			while(resultSet.next()) {
				int bId=resultSet.getInt("bId");
				String bName=resultSet.getString("bName");
				String bTitle=resultSet.getString("bTitle");
				String bContent=resultSet.getString("bContent");
				Timestamp bDate=resultSet.getTimestamp("bDate");
				int bHit=resultSet.getInt("bHit");
				int bGroup=resultSet.getInt("bGroup");
				int bStep=resultSet.getInt("bStep");
				int bIndent=resultSet.getInt("bIndent");
								
				long time=(System.currentTimeMillis()-bDate.getTime());
				long bHour=TimeUnit.MILLISECONDS.toHours(time);
				
				BDto dto=new BDto(bId, bName, bTitle, bContent, bDate, bHit, bGroup, bStep, bIndent, bHour);
				
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
	public ArrayList<BDto> mainNList(int curPage){
		
		ArrayList<BDto> dtos=new ArrayList<BDto>();
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet resultSet=null;
		
		try{
			con=dataSource.getConnection();
			
			String query="select * "+
						" from ( "+
						" select rownum num, A.* "+
						" from( "+
						" select * from mvc_noticeboard "+
						" order by bgroup desc, bstep asc ) A "+
						" where rownum <=3) B "+
						" where B.num >=1 ";
			
			pstmt=con.prepareStatement(query);
			resultSet=pstmt.executeQuery();
			
			while(resultSet.next()) {
				int bId=resultSet.getInt("bId");
				String bName=resultSet.getString("bName");
				String bTitle=resultSet.getString("bTitle");
				String bContent=resultSet.getString("bContent");
				Timestamp bDate=resultSet.getTimestamp("bDate");
				int bHit=resultSet.getInt("bHit");
				int bGroup=resultSet.getInt("bGroup");
				int bStep=resultSet.getInt("bStep");
				int bIndent=resultSet.getInt("bIndent");
								
				long time=(System.currentTimeMillis()-bDate.getTime());
				long bHour=TimeUnit.MILLISECONDS.toHours(time);
				
				BDto dto=new BDto(bId, bName, bTitle, bContent, bDate, bHit, bGroup, bStep, bIndent, bHour);
				
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
	public ArrayList<BDto> noticelist(int curPage){
		
		ArrayList<BDto> dtos=new ArrayList<BDto>();
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet resultSet=null;
		
		int nStart=(curPage-1)*listCount+1;
		int nEnd=(curPage-1)*listCount+listCount;
		
		try{
			con=dataSource.getConnection();
			
			String query="select * "+
						" from ( "+
						" select rownum num, A.* "+
						" from( "+
						" select * from mvc_noticeboard"+
						" order by bgroup desc, bstep asc ) A "+
						" where rownum <=?) B "+
						" where B.num >=? ";
			
			pstmt=con.prepareStatement(query);
			pstmt.setInt(1, nEnd);
			pstmt.setInt(2, nStart);
			resultSet=pstmt.executeQuery();
			
			while(resultSet.next()) {
				int bId=resultSet.getInt("bId");
				String bName=resultSet.getString("bName");
				String bTitle=resultSet.getString("bTitle");
				String bContent=resultSet.getString("bContent");
				Timestamp bDate=resultSet.getTimestamp("bDate");
				int bHit=resultSet.getInt("bHit");
				int bGroup=resultSet.getInt("bGroup");
				int bStep=resultSet.getInt("bStep");
				int bIndent=resultSet.getInt("bIndent");
				
				BDto dto=new BDto(bId, bName, bTitle, bContent, bDate, bHit, bGroup, bStep, bIndent, 0);
				
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
	public ArrayList<BDto> searchlist(String searchtype, String searchtext, int curPage){
		
		ArrayList<BDto> dtos=new ArrayList<BDto>();
		Connection con=null;
		Statement stmt=null;		
		ResultSet resultSet=null;
		
		int nStart=(curPage-1)*listCount+1;
		int nEnd=(curPage-1)*listCount+listCount;

		try{
			con=dataSource.getConnection();
			stmt=con.createStatement();
			StringBuffer sb=new StringBuffer();
			
			System.out.println(searchtype);
			System.out.println(searchtext);
			sb.setLength(0);
			sb.append("select * from (select rownum num, A.* from");
			sb.append("(select * from mvc_board");
			sb.append(" where "+searchtype+" like '%"+searchtext+"%'");
			sb.append(" order by bgroup desc, bstep asc) A");
			sb.append(" where rownum <="+nEnd+" ) B ");
			sb.append("where B.num >="+ nStart);
			resultSet=stmt.executeQuery(sb.toString());

			while(resultSet.next()) {
				int bId=resultSet.getInt("bId");
				String bName=resultSet.getString("bName");
				String bTitle=resultSet.getString("bTitle");
				String bContent=resultSet.getString("bContent");
				Timestamp bDate=resultSet.getTimestamp("bDate");
				int bHit=resultSet.getInt("bHit");
				int bGroup=resultSet.getInt("bGroup");
				int bStep=resultSet.getInt("bStep");
				int bIndent=resultSet.getInt("bIndent");
				
				BDto dto=new BDto(bId, bName, bTitle, bContent, bDate, bHit, bGroup, bStep, bIndent, 0);
				
				dtos.add(dto);
				System.out.println("db체크완");
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
	public ArrayList<BDto> noticeSearchList(String searchtype, String searchtext, int curPage){
		
		ArrayList<BDto> dtos=new ArrayList<BDto>();
		Connection con=null;
		Statement stmt=null;		
		ResultSet resultSet=null;
		
		int nStart=(curPage-1)*listCount+1;
		int nEnd=(curPage-1)*listCount+listCount;

		try{
			con=dataSource.getConnection();
			stmt=con.createStatement();
			StringBuffer sb=new StringBuffer();
			
			System.out.println(searchtype);
			System.out.println(searchtext);
			sb.setLength(0);
			sb.append("select * from (select rownum num, A.* from");
			sb.append("(select * from mvc_noticeboard");
			sb.append(" where "+searchtype+" like '%"+searchtext+"%'");
			sb.append(" order by bgroup desc, bstep asc) A");
			sb.append(" where rownum <="+nEnd+" ) B ");
			sb.append("where B.num >="+ nStart);
			resultSet=stmt.executeQuery(sb.toString());

			while(resultSet.next()) {
				int bId=resultSet.getInt("bId");
				String bName=resultSet.getString("bName");
				String bTitle=resultSet.getString("bTitle");
				String bContent=resultSet.getString("bContent");
				Timestamp bDate=resultSet.getTimestamp("bDate");
				int bHit=resultSet.getInt("bHit");
				int bGroup=resultSet.getInt("bGroup");
				int bStep=resultSet.getInt("bStep");
				int bIndent=resultSet.getInt("bIndent");
				
				BDto dto=new BDto(bId, bName, bTitle, bContent, bDate, bHit, bGroup, bStep, bIndent, 0);
				
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
	
	public ArrayList<BDto> commentlist(int group, int curPage){
		
		ArrayList<BDto> dtos=new ArrayList<BDto>();
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet resultSet=null;
		
		int nStart=(curPage-1)*listCount+1;
		int nEnd=(curPage-1)*listCount+listCount;
		
		try{
			con=dataSource.getConnection();
			
			String query="select * "+
						" from ( "+
						" select rownum num, A.* "+
						" from( "+
						" select * from mvc_commentboard where bGroup=? "+
						" order by bid, bstep asc ) A "+
						" where rownum <=?) B "+
						" where B.num >=? ";
			
			pstmt=con.prepareStatement(query);
			pstmt.setInt(1, group);
			pstmt.setInt(2, nEnd);
			pstmt.setInt(3, nStart);
			resultSet=pstmt.executeQuery();
			
			while(resultSet.next()) {
				int bId=resultSet.getInt("bId");
				String bName=resultSet.getString("bName");
				String bTitle="";
				String bContent=resultSet.getString("bContent");
				Timestamp bDate=resultSet.getTimestamp("bDate");
				int bHit=0;
				int bGroup=resultSet.getInt("bGroup");
				int bStep=resultSet.getInt("bStep");
				int bIndent=resultSet.getInt("bIndent");
				
				BDto dto=new BDto(bId, bName, bTitle, bContent, bDate, bHit, bGroup, bStep, bIndent, 0);
				
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
	public ArrayList<BDto> Ncommentlist(int group, int curPage){
		
		ArrayList<BDto> dtos=new ArrayList<BDto>();
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet resultSet=null;
		
		int nStart=(curPage-1)*listCount+1;
		int nEnd=(curPage-1)*listCount+listCount;
		
		try{
			con=dataSource.getConnection();
			
			String query="select * "+
						" from ( "+
						" select rownum num, A.* "+
						" from( "+
						" select * from mvc_ncommentboard where bGroup=? "+
						" order by bid, bstep asc ) A "+
						" where rownum <=?) B "+
						" where B.num >=? ";
			
			pstmt=con.prepareStatement(query);
			pstmt.setInt(1, group);
			pstmt.setInt(2, nEnd);
			pstmt.setInt(3, nStart);
			resultSet=pstmt.executeQuery();
			
			while(resultSet.next()) {
				int bId=resultSet.getInt("bId");
				String bName=resultSet.getString("bName");
				String bTitle="";
				String bContent=resultSet.getString("bContent");
				Timestamp bDate=resultSet.getTimestamp("bDate");
				int bHit=0;
				int bGroup=resultSet.getInt("bGroup");
				int bStep=resultSet.getInt("bStep");
				int bIndent=resultSet.getInt("bIndent");
				
				BDto dto=new BDto(bId, bName, bTitle, bContent, bDate, bHit, bGroup, bStep, bIndent, 0);
				
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
	
	public BDto contentView(String id,String strID) {
		
		int check=upHitCheck(id, strID);
		if(id!=null) {
			if(check==0) {
				upHit(id, strID);
			}
		}
		BDto dto=null;
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet resultSet=null;
		
		try {
			con=dataSource.getConnection();
			
			String query="select * from mvc_board where bId=?";
			pstmt=con.prepareStatement(query);
			pstmt.setInt(1,  Integer.parseInt(strID));
			resultSet=pstmt.executeQuery();
			
			if(resultSet.next()){
				int bId=resultSet.getInt("bId");
				String bName=resultSet.getString("bName");
				String bTitle=resultSet.getString("bTitle");
				String bContent=resultSet.getString("bContent");
				Timestamp bDate=resultSet.getTimestamp("bDate");
				int bHit=resultSet.getInt("bHit");
				int bGroup=resultSet.getInt("bGroup");
				int bStep=resultSet.getInt("bStep");
				int bIndent=resultSet.getInt("bIndent");
				
				dto=new BDto(bId, bName, bTitle, bContent, bDate, bHit, bGroup, bStep, bIndent, 0);
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
		
		return dto;
	}
	public BDto commentView(String strID) {
		BDto dto=null;
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet resultSet=null;
		
		try {
			con=dataSource.getConnection();
			
			String query="select * from mvc_commentboard where bGroup=?";
			pstmt=con.prepareStatement(query);
			pstmt.setInt(1,  Integer.parseInt(strID));
			resultSet=pstmt.executeQuery();
			
			if(resultSet.next()){
				int bId=resultSet.getInt("bId");
				String bName=resultSet.getString("bName");
				String bTitle="";
				String bContent=resultSet.getString("bContent");
				Timestamp bDate=resultSet.getTimestamp("bDate");
				int bHit=0;
				int bGroup=resultSet.getInt("bGroup");
				int bStep=resultSet.getInt("bStep");
				int bIndent=resultSet.getInt("bIndent");
				
				dto=new BDto(bId, bName, bTitle, bContent, bDate, bHit, bGroup, bStep, bIndent, 0);
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
		
		return dto;
	}
	public BDto noticeView(String id, String strID) {
		int check=NupHitCheck(id, strID);
		System.out.println(check);
		if(id!=null) {
			if(check==0) {
				NupHit(id, strID);
			}
		}
		
		BDto dto=null;
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet resultSet=null;
		
		System.out.println(strID);
		
		try {
			con=dataSource.getConnection();
			
			String query="select * from mvc_noticeboard where bId=?";
			pstmt=con.prepareStatement(query);
			pstmt.setInt(1,  Integer.parseInt(strID));
			resultSet=pstmt.executeQuery();
			
			if(resultSet.next()){
				int bId=resultSet.getInt("bId");
				String bName=resultSet.getString("bName");
				String bTitle=resultSet.getString("bTitle");
				String bContent=resultSet.getString("bContent");
				Timestamp bDate=resultSet.getTimestamp("bDate");
				int bHit=resultSet.getInt("bHit");
				int bGroup=resultSet.getInt("bGroup");
				int bStep=resultSet.getInt("bStep");
				int bIndent=resultSet.getInt("bIndent");
				
				dto=new BDto(bId, bName, bTitle, bContent, bDate, bHit, bGroup, bStep, bIndent, 0);
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
		
		return dto;
	}
	public void modify(String bId, String bName, String bTitle, String bContent) {
		Connection con=null;
		PreparedStatement pstmt=null;
		
		try {
			con=dataSource.getConnection();
			
			String query="update mvc_board set bName=?, bTitle=?, bContent=? where bId=?";
			pstmt=con.prepareStatement(query);
			pstmt.setString(1, bName);
			pstmt.setString(2, bTitle);
			pstmt.setString(3, bContent);
			pstmt.setInt(4, Integer.parseInt(bId));
			int rn=pstmt.executeUpdate();
						
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
	}
	public void nmodify(String bId, String bName, String bTitle, String bContent) {
		Connection con=null;
		PreparedStatement pstmt=null;
		
		try {
			con=dataSource.getConnection();
			
			String query="update mvc_noticeboard set bName=?, bTitle=?, bContent=? where bId=?";
			pstmt=con.prepareStatement(query);
			pstmt.setString(1, bName);
			pstmt.setString(2, bTitle);
			pstmt.setString(3, bContent);
			pstmt.setInt(4, Integer.parseInt(bId));
			int rn=pstmt.executeUpdate();
						
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
	}
	
	public int deleteAcomment(String bId) {
		Connection con=null;
		PreparedStatement pstmt=null;
		int rn=0;
		try {
			con=dataSource.getConnection();
			String query="delete from mvc_commentboard where bGroup=?";
			pstmt=con.prepareStatement(query);
			pstmt.setInt(1, Integer.parseInt(bId));
			pstmt.executeUpdate();
			rn=1;
			System.out.println("댓삭완");
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if (pstmt!=null) pstmt.close();
				if(con!=null) con.close();
			}catch(Exception e2) {
				e2.printStackTrace();
			}
		}
		
		return rn;
	}
	
	public int deleteNcomment(String bId) {
		Connection con=null;
		PreparedStatement pstmt=null;
		int rn=0;
		try {
			con=dataSource.getConnection();
			String query="delete from mvc_ncommentboard where bid=?";
			pstmt=con.prepareStatement(query);
			pstmt.setInt(1, Integer.parseInt(bId));
			pstmt.executeUpdate();
			rn=1;
			System.out.println("댓삭완");
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if (pstmt!=null) pstmt.close();
				if(con!=null) con.close();
			}catch(Exception e2) {
				e2.printStackTrace();
			}
		}
		
		return rn;
	}
	public int deletecomment(String bId) {
		Connection con=null;
		PreparedStatement pstmt=null;
		int rn=0;
		try {
			con=dataSource.getConnection();
			String query="delete from mvc_commentboard where bid=?";
			pstmt=con.prepareStatement(query);
			pstmt.setInt(1, Integer.parseInt(bId));
			pstmt.executeUpdate();
			rn=1;
			System.out.println("댓삭완");
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if (pstmt!=null) pstmt.close();
				if(con!=null) con.close();
			}catch(Exception e2) {
				e2.printStackTrace();
			}
		}
		
		return rn;
	}
	public int delete(String bId) {
		Connection con=null;
		PreparedStatement pstmt=null;
		int rn=0;
		try {
			con=dataSource.getConnection();
			String query="delete from mvc_board where bId=?";
			pstmt=con.prepareStatement(query);
			pstmt.setInt(1, Integer.parseInt(bId));
			rn=pstmt.executeUpdate();
			
			System.out.println("글삭완");
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if (pstmt!=null) pstmt.close();
				if(con!=null) con.close();
			}catch(Exception e2) {
				e2.printStackTrace();
			}
		}
		
		return rn;
	}
	
	public BDto reply_view(String str) {
		
		BDto dto=null;
		
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet resultSet=null;
		try {
			con=dataSource.getConnection();
			String query="select * from mvc_board where bId=?";
			pstmt=con.prepareStatement(query);
			pstmt.setInt(1, Integer.parseInt(str));
			resultSet=pstmt.executeQuery();
			
			if(resultSet.next()) {
				int bId=resultSet.getInt("bId");
				String bName=resultSet.getString("bName");
				String bTitle=resultSet.getString("bTitle");
				String bContent=resultSet.getString("bContent");
				Timestamp bDate=resultSet.getTimestamp("bDate");
				int bHit=resultSet.getInt("bHit");
				int bGroup=resultSet.getInt("bGroup");
				int bStep=resultSet.getInt("bStep");
				int bIndent=resultSet.getInt("bIndent");
				
				System.out.println(bId+":"+bName+":"+bTitle+":"+bContent+":"+bDate+":"+bHit+":"+bGroup+":"+bStep+":"+bIndent);
				dto=new BDto(bId, bName, bTitle, bContent, bDate, bHit, bGroup, bStep, bIndent, 0);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if (pstmt!=null) pstmt.close();
				if(con!=null) con.close();
			}catch(Exception e2) {
				e2.printStackTrace();
			}
		}
		
		return dto;
	}
	
	public void reply(String bId, String bName, String bTitle, String bContent, String bGroup, String bStep, String bIndent) {
		replyShape(bGroup, bStep);
		
		Connection con=null;
		PreparedStatement pstmt=null;
		
		try {
			con=dataSource.getConnection();
			String query="insert into mvc_board"+
							" (bId, bName, bTitle, bContent, bGroup, bStep, bIndent) "+
							"values (mvc_board_seq.nextval, ?, ?, ?, ?, ?, ?)";
			pstmt=con.prepareStatement(query);
			
			pstmt.setString(1, bName);
			pstmt.setString(2, bTitle);
			pstmt.setString(3, bContent);
			pstmt.setInt(4, Integer.parseInt(bGroup));
			pstmt.setInt(5, Integer.parseInt(bStep)+1);
			pstmt.setInt(6, Integer.parseInt(bIndent)+1);
			
			int rn=pstmt.executeUpdate();
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
	}
	
	public BPageInfo searchPage(String searchtype, String searchtext, int curpage) {
		Connection con=null;
		ResultSet rs=null;
		Statement stmt=null;

		
		if(searchtype.equals("title")) {
			searchtype="bTitle";
		} else if(searchtype.equals("content")) {
			searchtype="bContent";
		} else if(searchtype.equals("writer")) {
			searchtype="bname";
		}

		int totalCount=0;
		StringBuffer sb=new StringBuffer();
				
		try {
			con=dataSource.getConnection();
			stmt=con.createStatement();
			sb.setLength(0);
			sb.append("select count(*) as total from mvc_board where "+searchtype+" like '%"+searchtext+"%'");
			rs=stmt.executeQuery(sb.toString());

			if(rs.next()) {
				totalCount=rs.getInt("total");
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
	public BPageInfo noticesearchPage(String searchtype, String searchtext, int curpage) {
		Connection con=null;
		ResultSet rs=null;
		Statement stmt=null;

		
		if(searchtype.equals("title")) {
			searchtype="bTitle";
		} else if(searchtype.equals("content")) {
			searchtype="bContent";
		} 
		
		int totalCount=0;
		StringBuffer sb=new StringBuffer();
				
		try {
			con=dataSource.getConnection();
			stmt=con.createStatement();
			sb.setLength(0);
			sb.append("select count(*) as total from mvc_noticeboard where "+searchtype+" like '%"+searchtext+"%'");
			rs=stmt.executeQuery(sb.toString());

			if(rs.next()) {
				totalCount=rs.getInt("total");
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
	
	public BPageInfo articlePage(int curPage) {
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet resultSet=null;
		
		int totalCount=0;
		try {
			con=dataSource.getConnection();
			
			String query="select count(*) as total from mvc_board";
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
	
	public BPageInfo commentPage(int bId, int curpage) {
		Connection con=null;
		ResultSet rs=null;
		PreparedStatement pstmt=null;

		int totalCount=0;
		StringBuffer sb=new StringBuffer();
				
		try {
			con=dataSource.getConnection();
			
			String query="select count(*) as total from mvc_commentboard where bGroup=?";
			pstmt=con.prepareStatement(query);
			pstmt.setInt(1, bId);
			rs=pstmt.executeQuery();

			if(rs.next()) {
				totalCount=rs.getInt("total");
				
				System.out.println(totalCount);
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs !=null) rs.close();
				if(pstmt!=null) pstmt.close();
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

		return pinfo;		
	}
	public BPageInfo NcommentPage(int bId, int curpage) {
		Connection con=null;
		ResultSet rs=null;
		PreparedStatement pstmt=null;

		int totalCount=0;
		StringBuffer sb=new StringBuffer();
				
		try {
			con=dataSource.getConnection();
			
			String query="select count(*) as total from mvc_ncommentboard where bGroup=?";
			pstmt=con.prepareStatement(query);
			pstmt.setInt(1, bId);
			rs=pstmt.executeQuery();

			if(rs.next()) {
				totalCount=rs.getInt("total");
				
				System.out.println(totalCount);
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs !=null) rs.close();
				if(pstmt!=null) pstmt.close();
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

		return pinfo;		
	}
	
	public BPageInfo noticePage(int curPage) {
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet resultSet=null;
		
		int totalCount=0;
		try {
			con=dataSource.getConnection();
			
			String query="select count(*) as total from mvc_noticeboard";
			pstmt=con.prepareStatement(query);
			resultSet=pstmt.executeQuery();
			
			if(resultSet.next()) {
				totalCount=resultSet.getInt("total");
				System.out.println(totalCount);
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
	
	public int upHitCheck(String id, String bId) {
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet resultSet=null;
		int rn=0;
		try {
			con=dataSource.getConnection();
			String query="select bId from uphitcheck where bgroup=? and bid=?";
			pstmt=con.prepareStatement(query);
			pstmt.setInt(1,  Integer.parseInt(bId));
			pstmt.setString(2, id);
			resultSet=pstmt.executeQuery();
			
			rn=pstmt.executeUpdate();
			System.out.println("uphitcheck 완료");
			System.out.println(rn);
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
		return rn;
	}
	public int NupHitCheck(String id, String bId) {
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet resultSet=null;
		int rn=0;
		try {
			con=dataSource.getConnection();
			String query="select bId from nuphitcheck where bgroup=? and bid=?";
			pstmt=con.prepareStatement(query);
			pstmt.setInt(1,  Integer.parseInt(bId));
			pstmt.setString(2, id );
			resultSet=pstmt.executeQuery();
			
			pstmt.executeUpdate();
			System.out.println("nuphit 체크완료");
			
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
		return rn;
	}
	private void upHit(String id,String bId) {
		Connection con=null;
		PreparedStatement pstmt=null;
		
		try {
			con=dataSource.getConnection();
			String query="update mvc_board set bHit=bHit+1 where bId=?";
			pstmt=con.prepareStatement(query);
			pstmt.setString(1, bId);
			
			pstmt.executeUpdate();
			System.out.println("uphit 체크완료");
			
			query="insert into uphitcheck values(?,?)";
			pstmt=con.prepareStatement(query);
			pstmt.setString(1, id);
			pstmt.setInt(2, Integer.parseInt(bId));
			pstmt.executeUpdate();
			int rn=pstmt.executeUpdate();
			System.out.println("uphit 추가완료");
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
	}
	private void NupHit(String id,String bId) {
		Connection con=null;
		PreparedStatement pstmt=null;
		
		try {
			con=dataSource.getConnection();
			String query="update mvc_noticeboard set bHit=bHit+1 where bId=?";
			pstmt=con.prepareStatement(query);
			pstmt.setString(1, bId);
			
			pstmt.executeUpdate();
			System.out.println("nuphit 체크완료");
			
			query="insert into nuphitcheck values(?,?)";
			pstmt=con.prepareStatement(query);
			pstmt.setString(1, id);
			pstmt.setInt(2, Integer.parseInt(bId));
			pstmt.executeUpdate();
			int rn=pstmt.executeUpdate();
			System.out.println("nuphit 추가완료");
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
	}
	
	private void replyShape(String strGroup, String strStep) {
		Connection con=null;
		PreparedStatement pstmt=null;
		
		try {
			con=dataSource.getConnection();
			String query="update mvc_board "+
						" set bStep=bStep+1 "+
						" where bGroup=? and bStep>?";
			pstmt=con.prepareStatement(query);
			pstmt.setInt(1,  Integer.parseInt(strGroup));
			pstmt.setInt(1,  Integer.parseInt(strStep));
			
			int rn=pstmt.executeUpdate();
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
	}

}

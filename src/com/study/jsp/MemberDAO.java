package com.study.jsp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class MemberDAO {
	
	public static final int MEMBER_NONEXISTENT=0;
	public static final int MEMBER_EXISTENT=1;
	public static final int MEMBER_JOIN_FAIL=0;
	public static final int MEMBER_JOIN_SUCCESS=1;
	public static final int MEMBER_LOGIN_PW_NO_GOOD=0;
	public static final int MEMBER_LOGIN_SUCCESS=1;
	public static final int MEMBER_LOGIN_IS_NOT=-1;
	
	private static MemberDAO instance=new MemberDAO();
	
	private MemberDAO() {
		
	}
	public static MemberDAO getInstance() {
		return instance;
	}
	
	public int insertMember(MemberDTO dto) {
		int ri=0;
		
		Connection con=null;
		PreparedStatement pstmt=null;
		String query="insert into members values (?,?,?,?,?,?)";
		
		try {
			con=getConnection();
			pstmt=con.prepareStatement(query);
			pstmt.setString(1,  dto.getId());
			pstmt.setString(2,  dto.getPw());
			pstmt.setString(3,  dto.getName());
			pstmt.setString(4,  dto.geteMail());
			pstmt.setTimestamp(5,  dto.getrDate());
			pstmt.setString(6,  dto.getAddress());
			pstmt.executeUpdate();
			ri=MemberDAO.MEMBER_JOIN_SUCCESS;
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
	
	public int confirmId(String id) {
		int ri=0;
		
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet set=null;
		String query="select id from members where id=?";
		
		try {
			con=getConnection();
			pstmt=con.prepareStatement(query);
			pstmt.setString(1,id);
			set=pstmt.executeQuery();
			if(set.next()) {
				ri=MemberDAO.MEMBER_EXISTENT;
			}else {
				ri=MemberDAO.MEMBER_NONEXISTENT;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				set.close();
				pstmt.close();
				con.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		return ri;
	}
	
	public int userCheck(String id, String pw) {
		int ri=0;
		String dbPw;
		
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet set=null;
		String query="select pw from members where id=?";
		
		try {
			con=getConnection();
			pstmt=con.prepareStatement(query);
			pstmt.setString(1,id);
			set=pstmt.executeQuery();
			
			if(set.next()) {
				dbPw=set.getString("pw");
				if(dbPw.equals(pw)) {
					System.out.println("login ok");
					ri=MemberDAO.MEMBER_LOGIN_SUCCESS;
				}else {
					System.out.println("login fail");
					ri=MemberDAO.MEMBER_LOGIN_PW_NO_GOOD;
				}
			}else {
				System.out.println("login fail");
				ri=MemberDAO.MEMBER_LOGIN_IS_NOT;
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
	public void withdraw(String id) {
		Connection con=null;
		PreparedStatement pstmt=null;

		try {
			con=getConnection();
			
			String query="delete members where id=?";
			pstmt=con.prepareStatement(query);
			pstmt.setString(1, id);
			pstmt.executeUpdate();
			System.out.println("db 삭제 끝");
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public MemberDTO getMember(String id) {
		
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet set=null;
		String query="select * from members where id=?";
		MemberDTO dto=null;
		
		try {
			con=getConnection();
			pstmt=con.prepareStatement(query);
			pstmt.setString(1,id);
			set=pstmt.executeQuery();
			
			if(set.next()) {
				dto=new MemberDTO();
				dto.setId(set.getString("id"));
				dto.setPw(set.getString("pw"));
				dto.setName(set.getString("name"));
				dto.seteMail(set.getString("eMail"));
				dto.setrDate(set.getTimestamp("rDate"));
				dto.setAddress(set.getString("address"));
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
		
		return dto;
		
	}
	
	public int updateMember(MemberDTO dto) {
		int ri=0;
		
		Connection con=null;
		PreparedStatement pstmt=null;
		String query="update members set pw=?, eMail=?, address=? where id=?";
		
		try {
			con=getConnection();
			pstmt=con.prepareStatement(query);
			pstmt.setString(1, dto.getPw());
			pstmt.setString(2, dto.geteMail());
			pstmt.setString(3, dto.getAddress());
			pstmt.setString(4, dto.getId());
			ri=pstmt.executeUpdate();
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				pstmt.close();
				con.close();
			}catch(Exception e2) {
				e2.printStackTrace();
			}
		}
		
		return ri;
	}
	
	private Connection getConnection() {
		Context context=null;
		DataSource dataSource=null;
		Connection con=null;
		try {
			context=new InitialContext();
			dataSource=(DataSource)context.lookup("java:comp/env/jdbc/Oracle11g");
			con=dataSource.getConnection();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return con;
	}
	
	
}

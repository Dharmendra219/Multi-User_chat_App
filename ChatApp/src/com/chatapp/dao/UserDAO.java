package com.chatapp.dao;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.chatapp.dto.UserDTO;
import com.chatapp.utils.Encryption;

//user CRUD
public class UserDAO {
	
	@SuppressWarnings("null")
	public boolean isLogin(UserDTO userDTO) throws SQLException, ClassNotFoundException, NoSuchAlgorithmException {
		
		//all are interface..connection pstmt and result..
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		//Doing Projection..
		final String sql = "select userid from users where userid=? and password=?";// ? : this tells us userid and password generated at runtime..replaced at runtime..
		
		try {
			con = CommanDAO.createConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, userDTO.getUserid());
			
			String encryptedPwd = Encryption.passwordEncrypt(new String(userDTO.getPassword()));
			pstmt.setString(2, encryptedPwd);
			
			rs = pstmt.executeQuery();
			return rs.next();
			/*
			 * if(rs.next()) {
				return true;
			}
			else return false;
			  */
		}
		
		finally {
			if(rs != null)rs.close();
			if(pstmt != null)pstmt.close();
			if(con != null)con.close();
		}
		
	}
	
	public int add(UserDTO userDTO) throws ClassNotFoundException, SQLException, Exception {
		System.out.println("Rec "+userDTO.getUserid()+" "+userDTO.getPassword());
		
		Connection connection = null;//first set the connection..
		Statement stmt = null;//query...
		
		try {

			connection = CommanDAO.createConnection();//step-1 create the connection
			//step-2 we do a query
			stmt = connection.createStatement();
			int record = stmt.executeUpdate("insert into users (userid, password) values('"+userDTO.getUserid()+"','"+Encryption.passwordEncrypt(new String(userDTO.getPassword()))+"')");
			return record;
		}
		
		finally {
			if(stmt != null) {
				stmt.close();
			}
			
			if(connection != null) {
				connection.close();
			}
		}
	}
}

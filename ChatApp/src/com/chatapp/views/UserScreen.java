package com.chatapp.views;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Font;
import java.awt.Window.Type;
import javax.swing.UIManager;

import com.chatapp.utils.UserInfo;
import com.chatapp.dao.UserDAO;
import com.chatapp.dto.UserDTO;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.SystemColor;
import java.awt.event.ActionListener;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;

public class UserScreen extends JFrame{
	private JTextField loginField;
	private JPasswordField passwordField;

	public static void main(String[] args) {
		UserScreen window = new UserScreen();
	}
	
	UserDAO userDAO = new UserDAO();
	
	private void doLogin() {
		String userid = loginField.getText();
		char[] password = passwordField.getPassword();
		UserDTO userDTO = new UserDTO(userid, password);
		
		try {
			
			String message = "";
			
			if(userDAO.isLogin(userDTO)) {
				message = "Welcome " + userid;
				UserInfo.USER_NAME = userid;
				setVisible(false);
				dispose();
				JOptionPane.showMessageDialog(this, message);
				DashBoard dashBoard = new DashBoard(message);
				dashBoard.setVisible(true);
			}
			else {
				message = "Invalid userid and password ";
				JOptionPane.showMessageDialog(this, message);
			}
			
			
		} catch (ClassNotFoundException | NoSuchAlgorithmException | SQLException e) {
			
			e.printStackTrace();
		}
	}
	
	private void register() {
		String userid = loginField.getText();
		char[] password = passwordField.getPassword();
		UserDTO userDTO = new UserDTO(userid, password);
		try {
			int result = userDAO.add(userDTO);
			if(result > 0) {
				JOptionPane.showMessageDialog(this, "Register SuccessFully");
			}
			else {
				JOptionPane.showMessageDialog(this, "Register Fail");
			}
		}
		catch(ClassNotFoundException | SQLException e) {
			System.out.println("DB Issue...");
			e.getStackTrace();
		}
		catch(Exception e){
			System.out.println("Some Generic Exception Raised...");
			e.getStackTrace();
		}
		System.out.print("userid "+userid+" password "+password);
	}
	
	public UserScreen() {
		
		setTitle("LOGIN");
		getContentPane().setBackground(Color.WHITE);
		getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("LOGIN");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 40));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(256, 36, 179, 73);
		getContentPane().add(lblNewLabel);
		
		loginField = new JTextField();
		loginField.setBounds(319, 146, 263, 31);
		getContentPane().add(loginField);
		loginField.setColumns(10);
		
		JLabel useridlbl = new JLabel("UserId");
		useridlbl.setFont(new Font("Tahoma", Font.PLAIN, 18));
		useridlbl.setBounds(164, 143, 63, 31);
		getContentPane().add(useridlbl);
		
		JLabel pwdlbl = new JLabel("password");
		pwdlbl.setFont(new Font("Tahoma", Font.PLAIN, 18));
		pwdlbl.setBounds(161, 207, 82, 31);
		getContentPane().add(pwdlbl);
		
		JButton loginbt = new JButton("Login");
		loginbt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doLogin();
			}
		});
		loginbt.setFont(new Font("Tahoma", Font.BOLD, 13));
		loginbt.setBounds(224, 286, 104, 41);
		getContentPane().add(loginbt);
		loginbt.setBackground(Color.WHITE);
		
		JButton registerbt = new JButton("Register");
		registerbt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				register();
			}
		});
		registerbt.setFont(new Font("Tahoma", Font.BOLD, 13));
		registerbt.setBackground(Color.WHITE);
		registerbt.setBounds(381, 286, 104, 41);
		setSize(750, 430);
		getContentPane().add(registerbt);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(319, 204, 263, 31);
		getContentPane().add(passwordField);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setVisible(true);
	}
}

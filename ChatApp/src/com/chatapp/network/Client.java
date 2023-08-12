package com.chatapp.network;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JTextArea;

import com.chatapp.utils.ConfigReader;

public class Client {
	Socket socket; 
	InputStream in;
	OutputStream out;
	ClientWorker worker;
	JTextArea textArea;
	public Client(JTextArea textArea) throws UnknownHostException, IOException {
		int PORT = Integer.parseInt(ConfigReader.getValue("PORTNO"));
		socket = new Socket(ConfigReader.getValue("SERVER_IP"), PORT);
		
		out = socket.getOutputStream();
		in = socket.getInputStream();
		this.textArea = textArea;
		readMessages();
		/*
		System.out.println("Client comes...");
		
		System.out.println("Enter the Message to Send to Server...");
		
		Scanner scanner = new Scanner(System.in);
		String message = scanner.nextLine();
		OutputStream out = socket.getOutputStream();// write Bytes on Network..
		out.write(message.getBytes());
		System.out.println("Message sent to Server");
		out.close();
		socket.close();
		scanner.close();
		*/
	}
	
	public void sendMessages(String message) throws IOException{
		message = message + "\n";
		out.write(message.getBytes());
	}
	
	public void readMessages() {
		worker = new ClientWorker(in, textArea);
		worker.start();
	}
	
}

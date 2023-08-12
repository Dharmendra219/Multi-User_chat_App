package com.chatapp.network;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import com.chatapp.utils.ConfigReader;

public class Server {
		
	ServerSocket serverSocket;
	ArrayList<ServerWorker> workers = new ArrayList<>(); // contains all client sockets...
	public Server() throws IOException {
		int PORT = Integer.parseInt(ConfigReader.getValue("PORTNO"));
		serverSocket = new ServerSocket(PORT);
		System.out.println("Server Start and waiting for the Clients...");
		handleClientRequest();
	}
	
	//Multiple Client HandShaking...
	public void handleClientRequest() throws IOException {
		while(true) {
			Socket clientSocket = serverSocket.accept();//HandShaking...
			//Per Client Per Thread...
			ServerWorker serverWorker = new ServerWorker(clientSocket, this);//Creating a New Worker..
			workers.add(serverWorker);
			serverWorker.start();
		}
	}
	//Works fine for one client....
	/*	
		int PORT = Integer.parseInt(ConfigReader.getValue("PORTNO"));
		serverSocket = new ServerSocket(PORT);
		
		System.out.println("Server Started and Waiting for the Connection...");
		Socket socket = serverSocket.accept();//HandShaking...
		System.out.println("Client Joins the Server...");
		InputStream in = socket.getInputStream();
		byte[] arr = in.readAllBytes();
		String str = new String(arr);
		System.out.println("Message Rec from Client "+str);
		in.close();
		socket.close();
	}
	*/
	
	public static void main(String[] args) throws IOException {
		Server server = new Server();
	}
}

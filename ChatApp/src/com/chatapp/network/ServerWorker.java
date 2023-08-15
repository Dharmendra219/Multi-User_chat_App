package com.chatapp.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

//Thread == Worker
//Worker need a Job to Perform
//For Job u give runnable
//once job is created via runnable so write the job logic inside a run function
//Assign a work to the Thread/ Worker
public class ServerWorker extends Thread {
	
	private Socket clientSocket;
	private InputStream in;
	private OutputStream out;
	private Server server;
	
	public ServerWorker(Socket clientSocket, Server server) throws IOException {
		this.server = server;
		this.clientSocket = clientSocket;
		in = clientSocket.getInputStream(); // Client Data Read..
		out = clientSocket.getOutputStream();// Client Data Write..
		System.out.println("New Client Comes...");
	}
	
	@Override
	public void run() {
		//Read the Data from the Client and BroadCast the Data to all...
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String line;
		try {
			while(true) {
			
				line = br.readLine();
				System.out.println("Line Read..."+line);
				if(line.equalsIgnoreCase("quit")) {
					break;// Client chat end
				}
				//out.write(line.getBytes());// client send
				//to BroadCast to all clients...
				for(ServerWorker serverWorker : server.workers) {
					line = line + "\n";
					serverWorker.out.write(line.getBytes());
				}
			} 
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			try {
				if(br != null)br.close();
				if(in != null)in.close();
				if (out != null) {
					out.close();
				}
				if (clientSocket != null) {
					clientSocket.close();
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
}

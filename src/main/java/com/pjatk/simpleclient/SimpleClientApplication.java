package com.pjatk.simpleclient;


import java.io.*;
import java.net.Socket;

public class SimpleClientApplication {

	private static Socket socket= null;
	private static BufferedReader bufferedReader= null;
	private static PrintWriter printWriter= null;

	public static void main(String[] args) {
		String host= "localhost";
		int port= 8888;

		openConn(host, port);

		String response= request("Client request");
		log(response);

		log(request("Second "+response));

		closeConn();
	}

	public static void log(String msg){
		System.out.println("[" + Thread.currentThread().getName() + "] - [" + msg + "]");
	}

	public static void openConn(String ip, int port) {
		try {
			log("Opening connection...");
			socket = new Socket(ip, port);
			bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			printWriter = new PrintWriter(socket.getOutputStream(), true);
		}
		catch (IOException e) {
			log(e.getMessage());
		}
		finally {
			log("Closing connection...");
		}
	}

	public static void closeConn() {
		log("Closing connection...");
		try {
			bufferedReader.close();
			printWriter.close();
			socket.close();
		}
		catch (IOException e) {
			log(e.getMessage());
		}
		finally {
			log("Connection closed");
		}
	}

	public static String request(String payload) {
		try {
			printWriter.println(payload);
			return bufferedReader.readLine();
		} catch (Exception e) {
			log("exception while requesting: "+e.getMessage());
			return null;
		}
	}
}


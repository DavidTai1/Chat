package logic;

import java.io.*;
import java.util.*;

import javax.persistence.EntityManager;

import java.net.*;
import java.sql.ResultSet;
import java.sql.SQLException;

import dataAccess.*;
import data_objects.Users;
import example.jdbc.DataAccess;


public class Server {
	private Socket insocket;
	private HashMap<Integer,Socket> clients;
	private Queue<String> receiverQueue;
	
	static public boolean DEBUG=true;
	
	public Server(){
		init();
	}	
	public void init(){
		clients = new HashMap<Integer,Socket>();
		receiverQueue = new LinkedList<String>();
	}
	
	// TODO: take commands
	public void run(ServerSocket serverSocket) throws Exception{
		if (DEBUG) System.out.println("IP:" + InetAddress.getLocalHost() +" PORT: "+serverSocket.getLocalPort());
		int message_length;
		byte inputBytes[] = new byte[1024];
		insocket=serverSocket.accept();
		//insocket.setSoTimeout(3000);
		while(true){
			if (DEBUG)System.out.println("receiving");
			
			message_length = insocket.getInputStream().read(inputBytes);
			String message = new String(inputBytes,0,message_length);
			Command(message);
//			if (DEBUG) System.out.println("PUT "+message.split(":")[0]+" "+ Integer.parseInt(message.split(":")[1]));
			// add new user
//			if (message_length != 0){
//				if( ! clients.containsKey( Integer.parseInt(message.split(":")[1]) )){
//					clients.put(Integer.getInteger(message.split(":")[1]), 
//							new Socket(message.split(":")[0].split("/")[1], Integer.parseInt(message.split(":")[1])) );
//					Socket reply = clients.get(Integer.getInteger(message.split(":")[1]));
//					reply.getOutputStream().write(("SERVER: PUT "+message.split(":")[0]+" "+ Integer.parseInt(message.split(":")[1]) ).getBytes());
//				}
//				int count = 0;				
//			}
			insocket=serverSocket.accept();
		}
	}
	
	public void Command(String data){
		String command = data.split("::")[0];
		if (DEBUG)System.out.println("DATA: "+data);
		if (DEBUG)System.out.println("COMMAND: "+command);
		switch(command){
		case "REGIS": 
			register(data);
			break;
		case "LOGIN":
			check_login(data);
			break;
		case "CHATS":
			forward(data);
			break;
		default:
			System.out.println("Unknown Command");
				
		}
	}
	public String register(String info){
		String regis_info[] = info.split("::");
		String name = regis_info[1];
		String password = regis_info[2];
		// check "PASSWORD" "USERNAME" "SUCCESS"
		if (DEBUG) System.out.println("register: name:"+name+" password: "+password);
		Users u = new Users(name,password);
		Jpa_user.addUser(u);
		System.out.println("This is userid: "+u.get_userid());
		return "SUCCESS";
	}
	
	public String check_login(String info){
		String login_info[] = info.split("::");
		String name = login_info[1];
		String password = login_info[2];
		if (DEBUG) System.out.println("login: name:"+name+" password: "+password);
		EntityManager em = Jpa_Runner.getEntiryManager();
		Users u = em.find(Users.class, name);
		if (u == null){
			return "USERNAME";
		}
		if (u.get_password() == password){
			return "SUCCESS";
		}
		// check
		return "PASSWORD";
	}
	public void forward(String info){
		String friend_id = info.split("::")[1];
		String message = info.split("::")[2];
		// forward information
		
	}
	public void logto_db(String query){
		DataAccess da = new DataAccess();
		da.update(query);
	}
	public Boolean retrieve_db(String userid, String password) throws SQLException{
		/*
		 * TODO check user login information with database
		 */
		DataAccess da = new DataAccess();
		ResultSet rst = da.select("SELECT userid,password FROM ");
		if(rst.next()){
			String pass= rst.getString("password");
			if(pass.equals(password)){
				return true;
			}
			return false;
		}
		return false;
	}
	
	
	
	public static void main(String[] args){
		try{
			//Server s = new Server();	
			//s.run(new ServerSocket(0));
			Users u = Jpa_user.getUser("a");
			if(u==null){
				System.out.println("nulllllllllllllllllllllllllllllllllllllllllllllllllll");}
			else{
				System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
			}
			
		}catch(Exception e){
			System.out.println(e+" in main");
		}
	}
}

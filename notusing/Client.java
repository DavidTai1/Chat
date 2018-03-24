package logic;
import java.io.*;
import java.net.*;
import java.util.*;
import java.sql.*;
import dataAccess.*;
import example.jdbc.DataAccess;


public class Client {
	private int local_port;
	private String local_ip;
	private Socket insocket;
	private ServerSocket serverSocket;
	private Socket outsocket;
	private HashMap<String,String> configData;
	
	
	
	static boolean DEBUG=true;
	/*
	 * 
	 */
	public Client() throws IOException {
		serverSocket = new ServerSocket(0);
		init();
				
	}
	public void init(){
		load_config();
		try{
			if (DEBUG) System.out.println("ADDRESS"+" "+configData.get("ADDRESS")+"\nPORT "+ Integer.valueOf(configData.get("PORT")));
			outsocket = new Socket(configData.get("ADDRESS"),Integer.valueOf(configData.get("PORT")));
			
//			outsocket.getOutputStream().write((InetAddress.getLocalHost().toString()+":" + serverSocket.getLocalPort()).getBytes());
//			outsocket.getOutputStream().flush();
//			
//			String m = receive();
//			System.out.println(m);
			
		}catch(IOException ioe){
			System.out.println(ioe+ " in Client.java init()");
		}
	}
	public void load_config(){
		try{ 
			// load config file for address and port into hashmap
			configData = new HashMap<String,String>();
			FileReader file = new FileReader("src/main/resources/client_config");
			BufferedReader buffer = new BufferedReader(file);
			String s = buffer.readLine();
			while (s != null){
				configData.put(s.split("=")[0], s.split("=")[1]);
				s=buffer.readLine();
			}
			// close connection
			buffer.close();
			file.close();
			// if != 2, then config is not setup properly.
			if (configData.size() != 2){
				/* display error message
				 * ask for setup ip port
				 */
				System.out.println("config not setup");
			}
		}catch(IOException ioe){
			System.out.println(ioe + " in client load_config");
		}
		
		
	}
	public void send(String text){
		System.out.println("sending: "+text);
		byte[] outtext = text.getBytes();
		try{
			outsocket.getOutputStream().write(outtext);
		}catch(IOException ioe){
			System.out.println(ioe+ " in client send");
		}
	}
	// TODO ----
	public void groupsend(String text){
		for(int i = 0;i<5;i++){
			send(text);
		}
	}
	// receive text from server
	public String receive(){
		try{
			insocket = serverSocket.accept();
			
			byte[] temp = new byte[1024]; 
			int length = insocket.getInputStream().read(temp);
			String message = new String(temp,0,length);
			return message;
		}catch(IOException ioe){
			System.out.println(ioe+ " in client receive");
		}
		return "ERROR";
	}
	// home screen regis message TODO: server reply, check valid
	public void register(String name,String password){
		StringBuffer s = new StringBuffer();
		s.append("REGIS::");
		s.append(name);
		s.append("::");
		s.append(password);
		send(s.toString());
		String message = receive();
		switch(message){
		case "PASSWORD":
			//password not meet requirement
			break;
		case "USERNAME":
			// username already used
			break;
		case "SUCCESS":
			System.out.println("successfuly resgistered");
			break;
		default:
			System.out.println("ERROR. unknown command in register");
			break;
		}
		
	}
	// home screen login messgae TODO: server reply, check valid
	public void login(String name,String password){
		StringBuffer s = new StringBuffer();
		s.append("LOGIN::");
		s.append(name);
		s.append("::");
		s.append(password);
		send(s.toString());
		String message = receive();
		switch(message){
		case "PASSWORD":
			//password not match
			break;
		case "USERNAME":
			// username not exist
			break;
		case "SUCCESS":
			System.out.println("successfuly login");
			break;
		default:
			System.out.println("ERROR. unknown command in login");
			break;
		}
	}
	public void chat_message(String message, String friend_id){
		StringBuffer s = new StringBuffer();
		s.append("CHATS::");
		s.append(friend_id);
		s.append("::");
		s.append(message);
		send(s.toString());
	}
	public void add_firend(){
		
	}
	public String[] search(String s){
		return new String[5];
	}
	public void history_local(){
		
	}
	public void history_db(String query) throws SQLException{
		/*
		 * TODO check with server login infromation
		 */
		DataAccess da = new DataAccess();
		ResultSet rst = da.select(query);
		history_to_file(rst);
		/*
		 * refresh view of history table
		 */
	}
	public void history_to_file(ResultSet rst){
		/*
		 * TODO write history to file, overwrite all content
		 */
	}
	public void writeToFile(String text,String filename){
		try{
			BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true));
			writer.append(text);
			writer.newLine();
			writer.close();
		}catch(IOException ioe){
			System.out.println(ioe+" in writeToFile");
		}
	}
	public void load_history(){
		/*
		 * TODO load local history from file, display order by timestamp, userid
		 */
	}

	public static void main(String[] args) throws IOException {
		Client c = new Client();
		Scanner s = new Scanner(System.in);
		String input;
		while ((input = s.nextLine()) != null){
			System.out.println("INPUT: "+input);
			switch(input){
			case "1":
				c.register("Admin", "123456");
				break;
			case "2":
				c.login("Admin", "123456");
				break;
			case "3":
				c.chat_message("Hello from client", "Admin");
			}
		}
	}
}

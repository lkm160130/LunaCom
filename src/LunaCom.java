import javax.swing.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class LunaCom {

	static boolean connected = false;
	static Scanner sc = new Scanner(System.in);

	static DataInputStream in = null;
	static DataOutputStream out = null;

	static Socket socket;

	static Thread mr;

	static LunaComGUI2 lcg;

	public static void main(String[] args){
		lcg = new LunaComGUI2();
		lcg.setSize(500,400);
		lcg.setVisible(true);
		lcg.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		LunaCom lc = new LunaCom();
		lc.go();
	}

	public static void go(){
		String userInput = "";
		while(true){
			userInput = sc.nextLine();
			if(userInput.length() > 0 && userInput.charAt(0) == '#'){
				executeCommand(userInput);
			}
			else{
				sendMessage(userInput);
			}
		}
	}


	public static void executeCommand(String command){
		command = command.substring(1); //get rid of the #
		String[] parsedCommand = command.split(" "); //beak up commands by space



		switch (parsedCommand[0].toLowerCase()){
			case "connect":
				System.out.println("connect command entered");
				if(connect(parsedCommand[1])){

					System.out.println("Connection successful");
				}
				else{
					System.out.println("Connection could not be established");
				}
				break;
			case "exit":
				disconnect();
				break;
			default:
				System.out.println("unrecognized command");
				break;
		}

	}

	public static void disconnect(){

		socket = null;
		mr.stop();
		in = null;
		out = null;
		connected = false;
		System.out.println("Disconnected");
	}

	public static void sendMessage(String message){
		if(message.compareTo("") == 0){
			return;
		}
		if(!connected){
			System.out.println("You are not connected to a server. Cannot send message.");
			return;
		}

		try {
			out.writeUTF(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static boolean connect(String ip){
		try {

			socket = new Socket(ip,5555);
		} catch (IOException e) {
			System.err.println("Socket connection failed");
			return false;
		}

		try {
			out = new DataOutputStream(socket.getOutputStream());
			in = new DataInputStream(socket.getInputStream());
		} catch (IOException e) {
			System.err.print("Could not establish in/out streams");
			return false;
		}

		mr =new Thread(new MessageReciever(in));
		mr.start();
		System.out.println("Connection to " + ip + " Successful");
		connected = true;
		return true;
	}
}

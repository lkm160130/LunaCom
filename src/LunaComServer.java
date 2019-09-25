import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;

public class LunaComServer {
	ServerSocket serverSocket;
	Socket socket;
	static LinkedList<Thread> connections = new LinkedList<>();
	int nextId = 1;

	static ArrayList<DataOutputStream> outStreams = new ArrayList<>();

	public static void main(String[] args){
		LunaComServer lcs = new LunaComServer();
		lcs.start();
	}

	public void start(){
		try {
			serverSocket = new ServerSocket(5555);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("ServerSocket Ready");
		while(true){
			try {
				System.out.println("Waiting for connection");
				socket = serverSocket.accept();
				System.out.println("Client accepted");
			} catch (IOException e) {
				e.printStackTrace();
			}

			try {
				outStreams.add(new DataOutputStream(socket.getOutputStream()));
			} catch (IOException e) {
				e.printStackTrace();
			}
			connections.add(new Thread(new Connection(socket, nextId )));
			connections.getLast().start();
			nextId++;
		}
	}
}

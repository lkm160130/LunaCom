import java.io.*;
import java.net.Socket;

public class Connection implements Runnable {
	DataOutputStream out;
	DataInputStream in;
	Socket socket;

	int id;

	Connection(Socket socket, int id){
		this.socket = socket;
		this.id = id;
	}

	@Override
	public void run() {
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(new File("ConnectionLog"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		pw.write("Log Start:");



		try {
			out = new DataOutputStream(socket.getOutputStream());
			in = new DataInputStream(socket.getInputStream());
		} catch (IOException e) {
				e.printStackTrace();
		}
		while(true) {
			try {
				String input = in.readUTF();
				System.out.println("Read message " + input);
				/*if(input.compareTo("exit") == 0){
					out.writeUTF("exit");
					pw.close();
					LunaComServer.outStreams.remove(socket.getOutputStream()); //we are exiting so the server needs to remove this
																				//output stream from the list it sends messages to.
					System.out.println("A connection left");
					break;
				}*/

					pw.write(input + "\n");
					for (DataOutputStream os: LunaComServer.outStreams)
					{
						os.writeUTF("(" + id + ")" + input);
					}

			} catch (IOException e) {
				System.out.println("Terminating Connection");
				try {
					out.writeUTF("exit");
					LunaComServer.outStreams.remove(socket.getOutputStream());
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				pw.close();
				break;
				
			}


		}

	}

}

import java.awt.*;
import java.io.DataInputStream;
import java.io.IOException;

public class MessageReciever implements Runnable {

	DataInputStream in;

	MessageReciever(DataInputStream in){
		this.in = in;
	}
	@Override
	public void run() {
		while(true){
			try {
				String input = in.readUTF();
				if(input.compareTo("exit") ==0){
					break;
				}
				Toolkit.getDefaultToolkit().beep();
				System.out.println(input);
				LunaCom.lcg.chat.append("\n" + input);
			} catch (IOException e) {
				System.out.println("Connection Terminated");
				break;
			}
		}
	}
}

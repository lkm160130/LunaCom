import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LunaComGUI extends JFrame {
	JButton connect;
	JButton append;
	JButton send;
	JTextField ip;
	JTextArea chat;
	JTextField messageBox;

	public LunaComGUI(){


		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(500,400);
		setLayout(new FlowLayout(FlowLayout.LEADING));




		connect = new JButton("Connect");
		append = new JButton("Append");
		send = new JButton("Send");
		ip = new JTextField(15);
		ip.setSize(200,20);
		chat = new JTextArea(20,25);

		messageBox = new JTextField(50);




		getContentPane().add(connect);
		getContentPane().add(append);
		getContentPane().add(send);
		getContentPane().add(ip);
		getContentPane().add(chat);
		getContentPane().add(messageBox);




		connect.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				if(LunaCom.connected){

				}
				else if(LunaCom.connect(ip.getText())){
					connect.setName("Disconnect");
					LunaCom.connected = true;
				}
				else{
					chat.append("\nFailed to establish connection to " + ip.getText());
				}
				System.out.println(ip.getText());
			}
		});

		append.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				chat.append("\nTodo");
			}
		});

		send.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				if(LunaCom.connected) {
					LunaCom.sendMessage(messageBox.getText());
					messageBox.setText("");
				}
				else{

				}
			}
		});
		this.setVisible(true);
	}
}

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LunaComGUI2 extends JFrame {
	protected JTextArea chat;
	private JButton sendButton;
	private JButton connectButton;
	private JTextField message_box;
	private JTextField ip_field;
	private JPanel rootPanel;

	LunaComGUI2(){
		add(rootPanel);

		sendButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				if(LunaCom.connected) {
					LunaCom.sendMessage(message_box.getText());
					message_box.setText("");
				}
				else{
					chat.append("\n You are not connected");
				}
			}
		});

		connectButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				if(LunaCom.connected){

				}
				else if(LunaCom.connect(ip_field.getText())){
					connectButton.setName("Disconnect");
					LunaCom.connected = true;
				}
				else{
					chat.append("\nFailed to establish connection to " + ip_field.getText());
				}
				System.out.println(ip_field.getText());
			}
		});


	}

	private void createUIComponents() {
		// TODO: place custom component creation code here
	}
}

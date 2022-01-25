package cop2805;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import javax.swing.*;
import javax.swing.border.*;
import java.util.*;

public class Client {
	private static void constructGUI() {
		//Setting up the basics of the frame
		JFrame.setDefaultLookAndFeelDecorated(true);
		MyFrame frame = new MyFrame();
		frame.setVisible(true);
	}		
		
	public static void main(String[] args) {
		//instantiate the GUI
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				constructGUI();
			}
		});
	}
}

class MyFrame extends JFrame {
	
	public JTextField userInput;
	public JButton transmit;
	public JLabel instances;
	public int[] lines;
	public Object selected;
	public DefaultListModel<Integer> listModel;
	public JList<Integer> list;
	
	public MyFrame() {
		super();
		init();
	}
	private void init() {
		userInput = new JTextField();
		transmit = new JButton("Transmit");
		instances = new JLabel();
		listModel = new DefaultListModel<Integer>();
		list = new JList<Integer>(listModel);
		
		//create actionListener
		transmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				//1. clear jList
				listModel.clear();
				
				try {
					//2. Read the String from the text field
					String userString = userInput.getText();
					
					//3.open a socket to the server
					Socket connection = new Socket("127.0.0.1",1236);
					InputStream input = connection.getInputStream();
					OutputStream output = connection.getOutputStream();
				
					//4. send the string to the server
					output.write(userString.length());
					output.write(userString.getBytes());
				
					int n = input.read();
					//byte[] data = new byte[n];
					//input.read(data);
					
					//5. read the results from the server
					//if following the reccomended communication format this will involve a while loop that converts each string from the server into an integer.
					BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
					
					//add the integer values to the jList as they come in
					String serverResponse = "";
					while (serverResponse != null) {
						serverResponse = reader.readLine();
						if(serverResponse != null) {
							//System.out.println(serverResponse);
							listModel.addElement(Integer.parseInt(serverResponse));

						}
					}
					
					//6. close the socket to the server
					if(!connection.isClosed())
						connection.close();
			
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Word Searcher");
	
		//Set up contents of the frame
		this.setLayout(new BorderLayout()); 
		
		JPanel inputPanel = new JPanel(new GridLayout(2,1));
		inputPanel.add(new JLabel("Find:"));
		inputPanel.add(userInput);
		inputPanel.setBorder(new EmptyBorder(10,10,10,10));
		this.add(inputPanel,BorderLayout.NORTH);
		
		JScrollPane resultPane = new JScrollPane();
		resultPane.setViewportView(list);
		list.setLayoutOrientation(JList.VERTICAL);
		JPanel resultPanel = new JPanel(new GridLayout(2,1));
		resultPanel.add(new JLabel ("Result:"));
		resultPanel.add(resultPane);
		resultPanel.setBorder(new EmptyBorder(0,10,0,10));
		this.add(resultPanel,BorderLayout.CENTER);
		
		JPanel transmitButton = new JPanel();
		transmitButton.add(transmit);
		transmitButton.setBorder(new EmptyBorder(10,10,10,10));
		this.add(transmitButton,BorderLayout.SOUTH);

	
		//Set the frame position/size
		int frameWidth = 500;
		int frameHeight = 250;
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setBounds((int) (screenSize.getWidth() / 2) - (frameWidth/2), //x coordinate
			((int) (screenSize.getHeight() / 2) - frameHeight/2), //y coordinate
			frameWidth, //width
			frameHeight); //height
		}
	

}
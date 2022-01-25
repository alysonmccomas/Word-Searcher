package cop2805;
import java.net.*;
import java.io.*;
import java.nio.charset.*;
import java.util.ArrayList;
import java.util.List;

public class Server {
	
	public static void main(String[] args) {
		ServerSocket server = null;
		boolean shutdown = false;
		
		try {
			server = new ServerSocket(1236);
			System.out.println("Port Bound. Accepting Connections.");
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		
		while(!shutdown) {
			Socket client = null;
			InputStream input = null;
			OutputStream output = null;
			
			try {
				client = server.accept();
				input = client.getInputStream();
				output = client.getOutputStream();
				
				int n = input.read();
				byte[] data = new byte[n];
				input.read(data);
				
				String clientInput = new String(data, StandardCharsets.UTF_8);
				clientInput.replace("\n"," ");
				
				WordSearcher hamletText = new WordSearcher();
				List<Integer> returnList = new ArrayList<Integer>();
				returnList = hamletText.wordSearcher(clientInput);
				
				output.write(returnList.size());
				
				for (int i = 0; i < returnList.size(); i++) {
					String response = returnList.get(i).toString()+"\n";
					output.write(response.getBytes());
				}
				
				client.close();
				if(clientInput.equalsIgnoreCase("shutdown")) {
					System.out.println("Shutting down...");
					shutdown = true;
				}
				
			} catch (IOException e) {
				e.printStackTrace();
				continue;
			}
			
		}
	}
}

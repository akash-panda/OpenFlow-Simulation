package server;

import java.awt.Dialog;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

public class MultiClientManager implements Runnable {

	int clientId;
	String username, clientip;
	Socket clientSocket;
	PrintWriter outs;
	BufferedReader ins;
	Vector<String> currentClients;
	boolean terminate;
	String typeofclient;
	static int countofclients = 0;
	static int controllerpresent = 0;
	static String expectedRecepient = "";
	static String sendersname1 = "";
	static String segmentoverload = "";
	private static Server s1;
	static int flagforpacketlogmessage = 0;

	public MultiClientManager(int clientId, Socket clientSocket, Server s1) {
		
		this.s1 = s1;
		this.clientId = clientId;
		this.clientSocket = clientSocket;
		currentClients = new Vector<>();
		terminate = false;

		System.out.println("initialized Client " + clientId);

		try {
			outs = new PrintWriter(clientSocket.getOutputStream(), true);
			ins = new BufferedReader(new InputStreamReader(
					clientSocket.getInputStream()));
		} catch (IOException e) {
			System.out.println("IOE in MultiClientManager PrintWriter");
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		String msg = null;
		
		String sendingClientIP = "";
		String recievingClientIP = "";
		int flagiffound = 0;
		
		System.out.println("run of " + clientId);
		int controllerorder = 0;
		Server.outstreams.add(outs);
		Server.instreams.add(ins);

		// Prevent duplication of UserId
		do {
			try {
				typeofclient = ins.readLine();
				username = ins.readLine();
				clientip = ins.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println("Check userid : " + username);
			if (Server.clients.contains(username))
				outs.println("false");
			else
				outs.println("true");

		} while (Server.clients.contains(username));
		
		Server.x = new ArrayList<String>();
		Server.x .add(typeofclient);
		Server.x.add(username);
		Server.x.add(clientip);
		Server.x.add(String.valueOf(clientId));
		Server.recordforclients.add(Server.x);
		String sd ="->" + username + "(" + clientip + ")" + "\n" + " Connected at " + new Date().toString() + "\n";
		if(typeofclient.equals("Controller"))
		{
			Server.jTextArea3.append(sd);
			
		}
		else
		{
		Server.jTextArea1.append(sd);
		
		}
		
		
		Server.clients.add(username);
		Server.clientscount.add(clientId);
		System.out.println(Server.clientscount.get(0));
		currentClients.addAll(Server.clients);
		outs.println("*)-(*");
		int k = 0;
		for (String client : Server.clients) {
			outs.println(client);
			k++;
		}
		outs.println("*(-)*");
		int index = 0;
		int i = 0;
		// Announce new user
		for (PrintWriter outstream : Server.outstreams) {
			
			outstream.println("Server : Welcome " + username);
			outstream.println("Sent at : " + new Date().toString());
			System.out.println("Announced to : " + index++);
			
		}
		
		
		while (!terminate) {

			
			try {
				msg = ins.readLine();
				if(flagforpacketlogmessage == 0)
				{
				Server.jTextArea1.append("\n" + "-------------Packet Log--------------");
				flagforpacketlogmessage = 1;
				}
				//JOptionPane.showMessageDialog(null, "Message recieved here at Switch");
				
				if (msg.contains("_x8x_fOr")) {
					String[] vartogetsendersname;
					vartogetsendersname = msg.split(":::");
					String name = vartogetsendersname[0];
					sendersname1 = name.substring(0, 5 );
					Server.jTextArea1.append("\n" + "->" +"Packet recieved here at Switch.");
					
					Server.jTextArea1.append("\n" + "->" +"The sender is " + sendersname1);
					
					//System.out.println("The sender is " + sendersname1);
					expectedRecepient = vartogetsendersname[1].substring(vartogetsendersname[1].indexOf("_x8x_fOr")+8, vartogetsendersname[1].length());
					Server.jTextArea1.append("\n" + "->" + "Expected Recepient is : "+expectedRecepient);
					
					Server.jTextArea1.append("\n" + "->" +"Now checking the entry in FlowTable");
					
					//System.out.println("Expected Recepient is : "+expectedRecepient);
					msg = vartogetsendersname[1].substring(0, vartogetsendersname[1].indexOf("_x8x_fOr")-1);
					segmentoverload = msg;
					
				}
				
				System.out.println("Message received is : " + msg );
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("Some problem in receiving msg from Client");
				e.printStackTrace();
			}
			
			
			
			if(!expectedRecepient.equals("Server"))
			{
				String tosendasperflowtable = ""; 
				int clientorderno = 0;
				String names = null;
				System.out.println("M inside this");
				for(int i1=0; i1<Server.recordforclients.size();++i1)
				{
					ArrayList innerlist = (ArrayList) Server.recordforclients.get(i1);
					String recipient = (String) innerlist.get(1);
					if(sendersname1.equals(recipient))
					{
						sendingClientIP = (String) innerlist.get(2);
					}
					if(expectedRecepient.equals(recipient))
					{
						recievingClientIP = (String) innerlist.get(2);
					}
				}
				
				if(msg.startsWith("Flowtableupdate"))
				{
					String[] x = msg.split("::");
					String des = null;
					
					for(int i1=0; i1<Server.recordforclients.size();++i1)
					{
						ArrayList innerlist = (ArrayList) Server.recordforclients.get(i1);
						String recipient = (String) innerlist.get(1);
						if(x[1].equals(recipient))
						{
							des = (String) innerlist.get(2);
						}
						
					}
					Server.x1 = new ArrayList<String>();
					Server.x1.add(sendingClientIP);
					Server.x1.add(des);
					Server.flowtable.add(Server.x1);
					String xs = "\n" + sendingClientIP + "     --->     " + des;
					Server.jTextArea3.append("\n" + "->" + "Update : " + des);
					
					
					Server.jTextArea2.append(xs);
					
					Writer output = null;
					try {
						output = new BufferedWriter(new FileWriter("M:\\Flowtable.txt",true));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					try {
						output.append(sendingClientIP + "     --->     " + des + "\n");
						
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					try {
						output.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					
				}
				
					
						
						
						for(int t = 0;t<2;t++)
						{
							
							
							System.out.print(".");
						}
						for(int i1=0; i1<Server.flowtable.size();++i1)
						{
						
						
						ArrayList innerlist = (ArrayList) Server.flowtable.get(i1);
						String recipient = (String) innerlist.get(0);
						if(sendingClientIP.equals(recipient))
						{
							tosendasperflowtable = (String) innerlist.get(1);
							flagiffound = 1;
							Server.jTextArea1.append("\n" + "->" + "Entry found in flowtable");
							
							System.out.println("AKash Panda");
							
						
						}
						
					}
						
						for(int i1=0; i1<Server.recordforclients.size();++i1)
						{
							ArrayList innerlist = (ArrayList) Server.recordforclients.get(i1);
							String recipient = (String) innerlist.get(2);
							
							if(tosendasperflowtable.equals(recipient))
							{
								String xyz = (String) innerlist.get(3);
								clientorderno = Integer.parseInt(xyz);
							}
							
							if(((String)innerlist.get(0)).equals("Controller"))
							{
								controllerpresent = 1;
								controllerorder = Integer.parseInt((String) innerlist.get(3));
								names = (String) innerlist.get(1);
							}
						}
						
						
						
						if(flagiffound == 1)
						{
						int q = 0;
						for (PrintWriter outstream : Server.outstreams) {
							
							
							if(q == clientorderno)
							{
							Server.jTextArea1.append("\n" +"->" + sendersname1 + " is sending the Packet");
							
							Server.jTextArea1.append("\n" +"->" + "Packet successfully sent.");
							outstream.println("Packet" + ":::" + segmentoverload);
							
							}
							q++;
						}
						}
					
				
				
				if(flagiffound == 0)
		        {
		        	JOptionPane.showMessageDialog(null, "Packet entry not found in flowtable");
		        	Server.jTextArea1.append("\n" + "->" + "Entry not found in flowtable");
		        	if(controllerpresent == 1)
		        	{
		        		JOptionPane.showMessageDialog(null, "Transfering the details to Controller now...");
		        		Server.jTextArea1.append("\n" + "->" + "Sending the packet to controller");
		        		Server.jTextArea3.append("\n" + "->" + "Packet recieved at Controller.");
		        		
		        	}
		        	else
		        	{
		        		JOptionPane.showMessageDialog(null, "Controller is not present. Please restart the simulation.");
		        		System.exit(0);
		        	}
		        	
		        	int q = 0;
					for (PrintWriter outstream : Server.outstreams) {
						
						
						if(q == controllerorder)
						{
						outstream.println("Controller" + "::::" + names + "::::" + sendingClientIP + "::::" + recievingClientIP);
						
						}
						q++;
					}
		        	//Controller(flowtable, currentthreadid);
		        }
		        	
				
			}
			
			if (expectedRecepient.equals("Server")) {
				for (PrintWriter outstream : Server.outstreams) {
					outstream.println(username + " : " + msg);
					outstream.println("Sent at : " + new Date().toString());
				}
				if (msg.equals("*--* Bye all *--*")) {
					Server.outstreams.remove(outs);
					Server.clients.remove(username);
					System.out.println("Removed : " + username);
					System.out.println("number of clients : "
							+ Server.clients.size());
					terminate = true;
				}
			}
		}
	}
}
package client;

import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Vector;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



/**
 *
 * @author Nilay
 */
public class Client extends javax.swing.JFrame implements ActionListener{

    /**
     * Creates new form client
     * 
     */
	
	
	JFrame window1, window2;
	JPanel jp1, jp2, jp3;
	JTextField jIP, jPort, jUsername;
	JLabel jl1, jl2, jl3;
	JButton jconnect,host,controller,ok;
	Socket clientSocket;
	static boolean proceed;
	PrintWriter outs;
	BufferedReader ins;
	String username, clientip;
	String typeofclient;
	static int packetcounter = 0;
	
	Vector<String> onlineClients;

    public Client() throws IOException {
    	
    	proceed = false;
    	jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jIP = new javax.swing.JTextField();
        jPort = new javax.swing.JTextField();
        BufferedImage buttonIcon = ImageIO.read(new File("M:\\material_design_wallpaper (1) copynew.png"));
        jconnect = new JButton(new ImageIcon(buttonIcon));
        jconnect.setBorder(BorderFactory.createEmptyBorder());
        jconnect.setContentAreaFilled(false);
        JLabel jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        jLabel2.setFont(new java.awt.Font("Roboto Condensed", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Ip address :");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(40, 120, 150, 30);

        jLabel1.setFont(new java.awt.Font("Roboto Condensed", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Port number :");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(40, 200, 160, 40);

        jIP.setFont(new java.awt.Font("Roboto Condensed", 1, 18)); // NOI18N
        jIP.setText("192.168.221.1");
        getContentPane().add(jIP);
        jIP.setBounds(200, 120, 250, 30);

        jPort.setFont(new java.awt.Font("Roboto Condensed", 1, 18)); // NOI18N
        getContentPane().add(jPort);
        jPort.setBounds(200, 210, 250, 30);

        jconnect.setFont(new java.awt.Font("Roboto Condensed", 1, 24)); // NOI18N
        jconnect.setText("");
        jconnect.setName("Connect");
        jconnect.addActionListener(this);
        getContentPane().add(jconnect);
        jconnect.setBounds(150, 290, 200, 140);

        jLabel3.setIcon(new javax.swing.ImageIcon("M:\\rsz_14_-_19.jpg")); // NOI18N
        jLabel3.setText("jLabel3");
        getContentPane().add(jLabel3);
        jLabel3.setBounds(-6, 0, 480, 480);

        pack();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    // </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    
    public void actionPerformed(ActionEvent e) {
		// handle Connect button
		if (e.getSource() == jconnect) {
			try {
				
				clientSocket = new Socket(jIP.getText(), Integer.parseInt(jPort
						.getText()));
				
				proceed = true;
			} catch (NumberFormatException e1) {
				JOptionPane
						.showMessageDialog(null,
								"Integer expected. Something else encountered. Try again");
				System.out
						.println("Integer expected. Something else encountered. Try again");
				this.dispose();
				proceed = false;
				try {
					new Client();
				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				e1.printStackTrace();
			} catch (UnknownHostException e1) {
				JOptionPane.showMessageDialog(null,
						"IP somehow inappropriate. Try again");
				System.out.println("IP somehow inappropriate. Try again");
				this.dispose();
				proceed = false;
				try {
					new Client();
				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				e1.printStackTrace();
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(null,
						"It seems the Server is not up and running. Try again");
				System.out
						.println("It seems the Server is not up and running. Try again");
				this.dispose();
				proceed = false;
				try {
					new Client();
				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				e1.printStackTrace();
			}
			// System.out.println("We're Successful");
			this.dispose();

			try {
				outs = new PrintWriter(clientSocket.getOutputStream(), true);
				ins = new BufferedReader(new InputStreamReader(
						clientSocket.getInputStream()));
			} catch (IOException e1) {
				System.out.println("IOE in MultiClientManager PrintWriter");
				e1.printStackTrace();
			}

			String confirmUser = "";
			if (proceed) {

				do {
					
					/*BlockingQueue queue = new ArrayBlockingQueue(1024);
					
					GUInput gi = new GUInput(queue);
	                */
					
					typeofclient = JOptionPane.showInputDialog("What are you ?");
					outs.println(typeofclient);
					username = JOptionPane.showInputDialog("Username :");
					outs.println(username);
					try {
						clientip = InetAddress.getLocalHost().getHostAddress();
					} catch (UnknownHostException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					outs.println(clientip);
					System.out.println("printed " + username);
					System.out.println("IP " + clientip);
					try {
						confirmUser = ins.readLine();
						if (confirmUser.equals("false"))
							JOptionPane.showMessageDialog(null,
									"Username already taken. Try again");
					} catch (HeadlessException | IOException e1) {
						e1.printStackTrace();
					}
				} while (confirmUser.equals("false"));
			}
			ClientChatWindow somname = null;
			try {
				somname = new ClientChatWindow(username
						+ " connected to Server @ " + jIP.getText(), outs, ins);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			somname.setVisible(true);
			somname.setSize(477, 349);
			new Thread(somname).start();
					
		}
	}

    
    
    
    public static void main(String args[]) throws IOException {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Client.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Client.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Client.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Client.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                
                Client somename = null;
				try {
					somename = new Client();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                somename.setSize(477, 510);
                somename.setVisible(true);
            }
        });
        
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JList jList1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import client.Client;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



/**
 *
 * @author Nilay
 */
public class ClientChatWindow extends javax.swing.JFrame implements ActionListener, Runnable {
	
	private static final long serialVersionUID = 1L;
	PrintWriter outs;
	BufferedReader ins;
	static boolean terminate;
	static BufferedReader br;
	static File fobj;
	static FileReader fr;
	private static JFileChooser chooser;
	 private static String username = "";

    /**
     * Creates new form client
     * @throws IOException 
     */
    public ClientChatWindow(String uName, PrintWriter outs, BufferedReader ins) throws IOException {
    	
    	this.username = uName;
    	this.outs = outs;
		this.ins = ins;
		terminate = false;
    	
    	
    	
		setTitle("Client "+uName);
    	BufferedImage buttonIcon = ImageIO.read(new File("M:\\material_design_wallpaper (1) fdfdfcopy.png"));
        jButton1 = new JButton(new ImageIcon(buttonIcon));
        jButton1.setBorder(BorderFactory.createEmptyBorder());
        jButton1.setContentAreaFilled(false);
        BufferedImage buttonIcon1 = ImageIO.read(new File("M:\\material_design_wallpaper (1) copjjjy.png"));
        jButton2 = new JButton(new ImageIcon(buttonIcon1));
        jButton2.setBorder(BorderFactory.createEmptyBorder());
        jButton2.setContentAreaFilled(false);
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>(model);
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBounds(new java.awt.Rectangle(500, 500, 310, 300));
        getContentPane().setLayout(null);

        jButton1.setFont(new java.awt.Font("Roboto Condensed", 1, 24)); // NOI18N
        jButton1.setText("");
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        getContentPane().add(jButton1);
        jButton1.setBounds(270, 42, 160, 110);
        jButton1.addActionListener(this);
        
        jButton2.setFont(new java.awt.Font("Roboto Condensed", 1, 24)); // NOI18N
        jButton2.setText("");
        jButton2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        getContentPane().add(jButton2);
        jButton2.setBounds(270, 170, 160, 110);
        jButton2.addActionListener(this);
        
        jList1.setBackground(new java.awt.Color(102, 102, 102));
        jList1.setFont(new java.awt.Font("Roboto Condensed", 0, 18)); // NOI18N
        jList1.setForeground(new java.awt.Color(255, 255, 255));
        jScrollPane1.setViewportView(jList1);

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(30, 50, 190, 230);

        jLabel2.setFont(new java.awt.Font("Roboto Condensed", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Online Clients");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(30, 10, 170, 29);

        jLabel1.setIcon(new javax.swing.ImageIcon("M:\\14 - 17.png")); // NOI18N
        jLabel1.setText("jLabel1");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(0, 0, 460, 310);

        pack();
    }
    
    
public ClientChatWindow() throws IOException {
		
		this("", null, null);
	}

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     * @throws IOException 
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    // </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    
    public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
if (e.getSource() == jButton1) {
			
			
			
			chooser = new JFileChooser();
        	int chooserValue1 = chooser.showOpenDialog(null);
        	if(chooserValue1 == JFileChooser.APPROVE_OPTION)
            {
            	fobj = chooser.getSelectedFile();
            }
        	try {
        		fr = new FileReader(fobj);
        	} catch (FileNotFoundException e1) {
        		// TODO Auto-generated catch block
        		e1.printStackTrace();
        	}
        	br = new BufferedReader(fr);
        	String line = null;
			try {
				line = br.readLine();
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
        	String line1 = "";
        	while(line!=null)
            {
            	
        		line1 = line1 + line;
        		try {
					line = br.readLine();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            }
        	
        	outs.println(username + ":::" + line1.trim()+" _x8x_fOr"+jList1.getSelectedValue());
        		
		}
		else if (e.getSource() == jButton2 && terminate==false) {
			int choice = JOptionPane.showConfirmDialog(this, "Are you sure you wanna terminate?", "Confirm Terminate box", 2);
			
			if (choice == JOptionPane.OK_OPTION && terminate==false) {
				outs.println("*--* Bye all *--* _x8x_fOrServer");
				terminate = true;
			}
			jButton2.setEnabled(false);
			jButton1.setEnabled(false);
		}
		
	}
    
    
    
    
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        /*try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }*/
        //</editor-fold>
    	
    	try { 
    	    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    	} catch (Exception e) {
    	    e.printStackTrace();
    	}
    	
        /* Create and display the form */
        
                
    	ClientChatWindow somename = null;
				try {
					somename = new ClientChatWindow();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                somename.setSize(477, 349);
                somename.setVisible(true);
          
    }
    
    
public void run() {

        
		// TODO Auto-generated method stub
		try {
			if(ins.readLine().equals("*)-(*")) {
				String client = "";
				while (!(client = ins.readLine()).equals("*(-)*")) {
					if (!model.contains(client))
					{
						
						model.addElement(client);
						
						
					}
				}
			}
		} catch (IOException e) {
			System.out.println("there's something fishy with Client List code in Client side");
			e.printStackTrace();
		}
		
		// Handling actual Client Op
		//while(!terminate) {
		String text = null;
		try {
			while(!terminate) {
				
				text = ins.readLine().trim();
				if (text.startsWith("Server : "))
					if (!model.contains(text.split(" ")[3]))
					{
						model.addElement(text.split(" ")[3]);
						
					}
				//chatSpace.append("\n"+text);
				/*if (text.contains("*--* Bye all *--*")) {
					model.removeElement(text.split(" ")[0]);
				}*/
				
				if(text.startsWith("Controller"))
				{
					String[] selectchoice = text.split("::::");
					String ats = "Hello " + selectchoice[1] + ", you've been asked to update the flowtable.";
					JOptionPane.showMessageDialog(null, ats);
					String updatedrec = JOptionPane.showInputDialog("The expected recipient is " + selectchoice[3] + "\nEnter your decision :");
					outs.println("Flowtableupdate" + "::" + updatedrec);
				}
				
				if(text.startsWith("Packet"))
				{
					Client.packetcounter++;
					  String[] selectchoice = text.split(":::");
					  String packetname = "packet" + Client.packetcounter;
					  //File fobj = new File("M:\\OpenFlow\\Host " + selectchoice[1] + "\\" + packetname + ".txt");
					 /* File fobj = new File("M:\\OpenFlow\\packet.txt")*/;
					 
					 JFileChooser jc = new JFileChooser();
					 int chooservalue = jc.showSaveDialog(null);
					 
					 if(chooservalue == JFileChooser.APPROVE_OPTION)
						{
							File fobj = jc.getSelectedFile();
					 
					 
					 
					  PrintWriter writer = null;
					try {
						writer = new PrintWriter(fobj);
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				      
				      
				    	String line = selectchoice[1];
				        
				    	writer.println(line);
				    	writer.close();
				    	System.out.println("Packet recieved here successfully");
						}
				        
				    	
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Treminated ");
	}

		
	

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JList<String> jList1;
    private javax.swing.JScrollPane jScrollPane1;
    DefaultListModel<String> model = new DefaultListModel<>();
    // End of variables declaration//GEN-END:variables

	
}

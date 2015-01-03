package client;

import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class hcchoice extends JFrame implements ActionListener{
	
	

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		CheckboxGroup cbg = new CheckboxGroup();
		Checkbox ch1 = new Checkbox("Host", cbg, true);
		Checkbox ch2 = new Checkbox("Controller", cbg, false);
		JButton b1 = new JButton("OK");
		JLabel l1 = new JLabel("            What are you ?");
		frame.setLayout(new GridLayout(4,1));
		frame.setVisible(true);
		frame.setSize(200, 150);
		frame.add(l1);
		frame.add(ch1);
		frame.add(ch2);
		frame.add(b1);
		b1.addActionListener(null);
	}
	
}
package binaparts.gui;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import binaparts.gui.MainFrames.MainPanel;

public class BDLFrame extends JFrame 
{
	public BDLFrame() {
	}
	private BDLMain pnlMain;
	JFrame BDLframe = new JFrame("BreakDown List Manager:");
	
	public void displayBDL() 
	{
		BDLframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel contentPane = new JPanel();
		contentPane.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		contentPane.setLayout(new CardLayout());
		pnlMain = new BDLMain(contentPane);
		contentPane.add(pnlMain, "Main Menu");
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int height = screenSize.height;
		int width = screenSize.width;
		BDLframe.setResizable(false);
		BDLframe.setSize(width/2, height/2);
		BDLframe.setLocationRelativeTo(null);
		BDLframe.setSize(865, 555);
		BDLframe.setContentPane(contentPane);
		BDLframe.setVisible(true);	
	}
		
		class BDLMain extends JPanel 
		{
			private JLabel lblBosal;
			private JLabel lblBDL;
			
			JPanel contentPane;
			
			public BDLMain(JPanel pnlMain) 	
			{	
				setBackground(new Color(105, 105, 105));
				contentPane = pnlMain;
				setOpaque(true);
				setVisible(true);
								
			//Images
				ImageIcon bosal = new ImageIcon(getClass().getResource("/images/bosal.jpg"));
				lblBosal = new JLabel(bosal);
				
			//JLabels
				lblBDL = new JLabel("Breakdown List Manager");
				
				
				setupPanel();
			}
			private void setupPanel() 
			{
				// TODO Auto-generated method stub
				
			}
		}
}
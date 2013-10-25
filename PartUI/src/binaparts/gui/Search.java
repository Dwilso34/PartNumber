package binaparts.gui;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;

import binaparts.dao.DBConnect;
import binaparts.gui.BDLFrame.BDLMain;

public class Search extends JFrame {
	public Search(){}
	private Searchpnl pnlMain;
	JFrame Searchframe = new JFrame("Search:");
	DBConnect con = new DBConnect();
	
	public void displayBDL() 
	{
		JPanel contentPane = new JPanel();
		contentPane.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		contentPane.setLayout(new CardLayout());
		pnlMain = new Searchpnl(contentPane);
		contentPane.add(pnlMain, "Search Window");
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int height = screenSize.height;
		int width = screenSize.width;
		Searchframe.setResizable(true);
		Searchframe.setSize(width/2, height/2);
		Searchframe.setLocationRelativeTo(null);
		Searchframe.setSize(300, 300);
		Searchframe.setContentPane(contentPane);
		Searchframe.setVisible(true);	
	}
		
	class Searchpnl extends JPanel 
	{	
		
		
		public Searchpnl(JPanel pnlMain) 	
		{
			
		}
	}
}

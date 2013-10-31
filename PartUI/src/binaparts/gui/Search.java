package binaparts.gui;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.GroupLayout.Alignment;

import org.json.JSONArray;
import org.json.JSONObject;




import binaparts.dao.DBConnect;
import binaparts.gui.BDLFrame.BDLMain;

public class Search extends JFrame 
{
	public Search(){}
	private Searchpnl pnlMain;
	JFrame Searchframe = new JFrame("Search:");
	DBConnect con = new DBConnect();
	private String searchText;
	
	/**
	 * 
	 * @return the searchText
	 */
	public String getSearchText() {
		return searchText;
	}

	/**
	 * @param searchText the searchText to set
	 */
	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}
	
	public void displaySearch() 
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
		Searchframe.setSize(336, 95);
		Searchframe.setContentPane(contentPane);
		Searchframe.setVisible(true);	
		try{
			Searchframe.setIconImage(ImageIO.read(new File("res/bosalimage.png")));
		}catch(Exception ex){ex.printStackTrace();}
	}
	
	class Searchpnl extends JPanel 
	{	
		private JLabel lblSearch;
		private JTextField txtSearch;
		private JButton btnSearch;
		
		public Searchpnl(final JPanel pnlMain) 	
		{
			setBackground(new Color(105, 105, 105));
			setVisible(true);
		
		//JLabel
			lblSearch = new JLabel("Search:");
			lblSearch.setFont(new Font("Tahoma", Font.BOLD, 14));
			lblSearch.setForeground(Color.BLACK);
			
		//JTextField
			txtSearch = new JTextField();
			txtSearch.setForeground(Color.BLACK);
			txtSearch.addMouseListener(new ContextMenuMouseListener());
			
		//JButton
			ImageIcon experiment = new ImageIcon(getClass().getResource("/images/Search.jpg"));
			btnSearch = new JButton(experiment);
			btnSearch.addActionListener(new ActionListener() {
						
				public void actionPerformed(ActionEvent e) 
				{
					if (e.getSource() == btnSearch) 
					{
						setSearchText(txtSearch.getText());	
						
						/*try{
							JSONArray temp = (con.queryDatabase("parts list", "BosalPartNumber", search));							
						}catch(Exception ex){
							JOptionPane.showMessageDialog(
									    Searchframe,
									    "Bosal Part Number: " + search + " does not exist",
									    "Missing Part Number",
										JOptionPane.ERROR_MESSAGE);
						}*/
					}
				}					
			});			
			setupPanel();			
		}

		private void setupPanel() 
			{
				GroupLayout groupLayout = new GroupLayout(this);
				groupLayout.setHorizontalGroup(
					groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(10)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(lblSearch, GroupLayout.PREFERRED_SIZE, 57, GroupLayout.PREFERRED_SIZE)
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(55)
									.addComponent(txtSearch, GroupLayout.PREFERRED_SIZE, 129, GroupLayout.PREFERRED_SIZE)))
							.addGap(10)
							.addComponent(btnSearch, GroupLayout.PREFERRED_SIZE, 106, GroupLayout.PREFERRED_SIZE))
				);
				groupLayout.setVerticalGroup(
					groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(23)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(lblSearch)
								.addComponent(txtSearch, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(16)
							.addComponent(btnSearch, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE))
				);
				setLayout(groupLayout);
			}
	}
}
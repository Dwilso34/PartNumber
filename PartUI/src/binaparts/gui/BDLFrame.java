package binaparts.gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import org.json.JSONArray;
import org.json.JSONException;

import binaparts.dao.DBConnect;
import binaparts.util.ComponentResizer;

@SuppressWarnings("serial")
public class BDLFrame extends JFrame 
{
	public BDLFrame(){}
	private BDLMain pnlMain;
	JFrame BDLframe = new JFrame("BreakDown List Manager:");	
	private String searchText;
	DBConnect con = new DBConnect();
	
	public void displayBDL() 
	{
		JPanel contentPane = new JPanel();
		contentPane.setPreferredSize(new Dimension(1300, 900));
		contentPane.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		JScrollPane scrollPane = new JScrollPane(contentPane, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setViewportView(contentPane);
        scrollPane.getVerticalScrollBar().setUnitIncrement(5);
		contentPane.setLayout(new CardLayout());
		pnlMain = new BDLMain(scrollPane);
		contentPane.add(pnlMain, "Main Menu");
		BDLframe.setContentPane(scrollPane);
		BDLframe.setResizable(true);
		BDLframe.setPreferredSize(new Dimension(1300, 700));
		BDLframe.setMinimumSize(new Dimension(1300, 500));
		BDLframe.setMaximumSize(new Dimension(1285, Integer.MAX_VALUE));
		pack();
		BDLframe.setVisible(true);	
		try{
			BDLframe.setIconImage(ImageIO.read(new File("res/bosalimage.png")));
		}catch(Exception ex){ex.printStackTrace();}
		System.out.println(pnlMain.getHeight());
	}
	public String getSearchText() {
		return searchText;
	}
	public void setSearchText(String searchText) {
		this.searchText = searchText;		
	}
		
	public class BDLMain extends JPanel
	{//global variables
		private String customer;
		private String platform;
		private String name;
		private String type;
		private String volume;
		private String power;
		private String searchText;		
		
		public String getSearchText() {
			return searchText;
		}
		public void setSearchText(String searchText) {
			this.searchText = searchText;
		}		
		public String getCustomer() {
			return customer;
		}
		public void setCustomer(String customer) {
			System.out.println(customer+" is being put into the customer variable");
			this.customer = customer;
			System.out.println(this.customer+" was put into the customer variable");
		}
		public String getPlatform() {
			return platform;
		}
		public void setPlatform(String platform) {
			System.out.println(platform+" is being put into the platform variable");
			this.platform = platform;
			System.out.println(this.platform+" was put into the platform variable");
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			System.out.println(name+" is being put into the name variable");
			this.name = name;
			System.out.println(this.name+" was put into the name variable");
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			System.out.println(type+" is being put into the type variable");
			this.type = type;
			System.out.println(this.type+" was put into the type variable");
		}
		public String getVolume() {
			return volume;
		}
		public void setVolume(String volume) {
			System.out.println(volume+" is being put into the volume variable");
			this.volume = volume;
			System.out.println(this.volume+" was put into the volume variable");
		}
		public String getPower() {
			return power;
		}
		public void setPower(String power) {
			System.out.println(power+" is being put into the power variable");
			this.power = power;
			System.out.println(this.power+" was put into the power variable");
		}
		
	//temp arrays to hold comboBox info 
		private JSONArray temp1;
		private JSONArray temp2;
		private JSONArray temp3;
	//^^reset will be on doClick() action of rbtnCreateBDL^^
		
	//JLabels	
		private JLabel lblBosal;
		private JLabel lblBDL;
		private JLabel lblCustomer;
		private JLabel lblPlatform;
		private JLabel lblType;
		private JLabel lblName;
		private JLabel lblVolume;
		private JLabel lblPower;
		private JLabel lblEngine;
		private JLabel lblBosalPartNum;
		private JLabel lblCustomerPartNum;
		private JLabel lblIMDS;
		private JLabel lblDescription;
		private JLabel lblSilencer;
		private JLabel lblVolume2;
		private JLabel lblLength;
		private JLabel lblSection;
		private JLabel lblIssuedBy;
		private JLabel lblPage;
		private JLabel lblREV;
		private JLabel lblRelDate;
		private JLabel lblREVDate;
		private JLabel lblProduction;
		private JLabel lblRelPlant1;
		private JLabel lblRelPlant2;
		private JLabel lblRelSupplier;
		private JLabel lblBOSAL;
		private JLabel lblCUSTOMER;
		private JLabel lblNote;
		private JLabel lblRemark;

	//JTextFields
		private JTextField txtCustomer;
		private JTextField txtPlatform;
		private JTextField txtType;
		private JTextField txtName;
		private JTextField txtVolume;
		private JTextField txtPower;
		private JTextField txtBosalPartNum;
		private JTextField txtCustomerPartNum;
		private JTextField txtIMDS;
		private JTextField txtDescription;
		private JTextField txtVolume2;
		private JTextField txtLength;
		private JTextField txtSection;
		private JTextField txtIssuedBy;
		private JTextField txtPage;
		private JTextField txtREV;
		private JTextField txtRelDate;
		private JTextField txtREVDate;
		private JTextField txtProduction;
		private JTextField txtRelPlant1;
		private JTextField txtRelPlant2;
		private JTextField txtRelSupplier;
		
	//JTextArea	
		private JTextArea txtaRemark;
		private JTextArea txtaNote;
		private JTextArea txtCustImage;
		
	//JButton
		private JButton btnAdd;
		private JButton btnDelete;
		private JButton btnSave;
		private JButton btnPdfPrint;
		
	//JTable	
		private JTable myTable;
		private DefaultTableModel table1;
		private JScrollPane scrollPane;
		public void bdlHeaders(){
			try{
	            String[] columnNames = {"ITEM", "QTY", " ", "Description", 
	            		"Bosal Part-NR", "OLD Part-NR", "Rev", "DWG NR", 
	            		"DWG Rev", "DWG Rev Date", "Prod Rel Date", "FRM",
	            		"Part-NR", "DWG-NR", "DWG Rev", "DWG Rev Date"};
	            String[][] data = new String[0][0];	             	      
	            table1 = (new DefaultTableModel(data, columnNames));
	           
	        }catch(Exception ex){ex.printStackTrace();}	  	
	    }
		public void setItemColumnIndex(){
			int rows = table1.getRowCount();
			for(int i = 0; i < rows; i++){
				table1.setValueAt(i+1, i, 0);
				table1.setValueAt(0, i, 1);
			}
		}
		public String getItemsFromTable(){
			String jsonString = null;
			TableModel table = myTable.getModel();
			int rowCount = table.getRowCount();
			//checks to see if the BDL table has any rows in it
			if(rowCount == 0){
				JOptionPane.showMessageDialog(BDLframe,
					    "No Items were added to this breakdown list",
					    "Invalid Entry",
					    JOptionPane.ERROR_MESSAGE);	
			} else {
				String[] itm = new String[rowCount];
				String[] qty = new String[rowCount];
				jsonString = "[{\"BreakdownListNumber\":\""+txtBosalPartNum.getText()+"\"},";
				//loop to dynamically grab the values from the table
				for(int i = 0; i < rowCount; i++){
					itm[i] = table.getValueAt(i, 4).toString();
					if(table.getValueAt(i,1).toString().equals("")){
						qty[i] = "0";
					} else {
						qty[i] = table.getValueAt(i, 1).toString();
					}
				}
				//loop to dynamically construct a JSON String of the BDL Items 
				for(int i = 0; i < rowCount; i++){
					jsonString = jsonString+"{\"Item"+(i+1)+"\":\""+itm[i]+"\","+"\"Qty"+(i+1)+"\":"+qty[i]+"}";
					if (i < (rowCount - 1)){
						jsonString = jsonString + ",";
					}
				}
				jsonString = jsonString + "]";			
			}
			return jsonString;
		}
		public boolean containsBDL(){
			try {
				JSONArray temp = con.queryDatabase("breakdown lists", "BreakdownListNumber", getSearchText());
				if (temp.length() > 0) {
					return true;
				}
			} catch (Exception ex) {				
				ex.printStackTrace();
			}
			return false;
		}
		public void setItemsForTable(){
			TableModel table = myTable.getModel();
			int rowCount = 0;
			int tempRowCount = 0;
			try {
				JSONArray temp = con.queryDatabase("breakdown lists", "BreakdownListNumber", getSearchText());
				
				//checks to see if any info was returned from the Database
				if(temp.length() == 0){
					JOptionPane.showMessageDialog(BDLframe,
						    "No Items were added to this breakdown list",
						    "Invalid Entry",
						    JOptionPane.ERROR_MESSAGE);	
				} else {
					for (int i = 0; i < temp.length(); i++) {
						for (int j = 0; j < (temp.getJSONObject(i).length()-5); j++) {
							rowCount++;
						}
					}
					String[] itm = new String[rowCount];
					String[] qty = new String[rowCount];
					//loop to dynamically grab the values from the returned JSONArray
					for (int i = 0; i < temp.length(); i++) {
						if (i == (temp.length()-1)) {
							tempRowCount = (temp.getJSONObject(i).length()-5);
						} else {	
							tempRowCount = 20;
						}
						for (int j = 0; j < (tempRowCount/2); j++) {
							itm[(i*10)+j] = temp.getJSONObject(i).get("Item"+(j+1)).toString();
							qty[(i*10)+j] = temp.getJSONObject(i).get("Qty"+(j+1)).toString();
						}
					}			
					//loop to dynamically add items from Database to table 			
					for (int i = 0; i < (rowCount/2); i++) {
						btnAdd.doClick();
						table.setValueAt(itm[i], i, 4);
						table.setValueAt(qty[i], i, 1);
						//checks to see if there is a sub BDL
						if (containsBDL() == true) {
							table.setValueAt("#", i, 2);
							//setSubItemsForTable(itm[i]);
						}
					}
				}
			} catch (HeadlessException ex) {
				ex.printStackTrace();
			} catch (JSONException ex) {
				ex.printStackTrace();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		public void setSubItemsForTable(String BDLNumber){
			TableModel table = myTable.getModel();
			int rowCount = 0;
			int tempRowCount = 0;
			try {
				JSONArray temp = con.queryDatabase("breakdown lists", "BreakdownListNumber", BDLNumber);
				
				//checks to see if any info was returned from the Database
				if(temp.length() == 0){
					JOptionPane.showMessageDialog(BDLframe,
						    "No Items were added to this breakdown list",
						    "Invalid Entry",
						    JOptionPane.ERROR_MESSAGE);	
				} else {
					for (int i = 0; i < temp.length(); i++) {
						for (int j = 0; j < (temp.getJSONObject(i).length()-5); j++) {
							rowCount++;
						}
					}
					String[] itm = new String[rowCount];
					String[] qty = new String[rowCount];
					//loop to dynamically grab the values from the returned JSONArray
					for (int i = 0; i < temp.length(); i++) {
						if (i == (temp.length()-1)) {
							tempRowCount = (temp.getJSONObject(i).length()-5);
						} else {	
							tempRowCount = 20;
						}
						for (int j = 0; j < (tempRowCount/2); j++) {
							itm[(i*10)+j] = temp.getJSONObject(i).get("Item"+(j+1)).toString();
							qty[(i*10)+j] = temp.getJSONObject(i).get("Qty"+(j+1)).toString();
						}
					}			
					//loop to dynamically add items from Database to table 			
					for (int i = 0; i < (rowCount/2); i++) {
						btnAdd.doClick();
						table.setValueAt(itm[i], i, 4);
						table.setValueAt(qty[i], i, 1);
						//checks to see if there is a sub BDL
						if (containsBDL() == true) {
							table.setValueAt("#", i, 2);
						}
					}
				}
			} catch (HeadlessException ex) {
				ex.printStackTrace();
			} catch (JSONException ex) {
				ex.printStackTrace();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		
	//JCheckBoxes
		private JCheckBox cbxCustomer;
		private JCheckBox cbxPlatform;
		private JCheckBox cbxName;
		
	//JComboBoxes
		private JComboBox<String> cboCustomer;
		private ComboBoxModel<String> resetCustomerComboBox()
		{
			ComboBoxModel<String> CustComboBoxDefault = null;
			String[] Cust = null;				
				try {
					Cust = new String[temp1.length()];
					for(int i = 0; i < temp1.length(); i++){
						Cust[i] = temp1.getJSONObject(i).get("Customer").toString();
					}
					CustComboBoxDefault = (new DefaultComboBoxModel<String> (Cust));
				}catch(Exception ex){ex.printStackTrace();}
			return CustComboBoxDefault;
		}
		private JComboBox<String> cboPlatform;
		private ComboBoxModel<String> resetPlatformComboBox()
		{
			ComboBoxModel<String> proComboBoxDefault = null;
			String[] types = null;
			try {
				types = new String[temp2.length()];
				for(int i = 0; i < temp2.length(); i++){
					types[i] = temp2.getJSONObject(i).getString("Program").toString();
				}	
				proComboBoxDefault = (new DefaultComboBoxModel<String> (types));
			}catch(Exception ex){ex.printStackTrace();}
			return proComboBoxDefault;
		}
		private JComboBox<String> cboName;
		private ComboBoxModel<String> resetEngineComboBox()
		{
			ComboBoxModel<String> engineComboBoxDefault = null;
			String[] types = null;
			try {
				types = new String[temp3.length()];
				for(int i = 0; i < temp3.length(); i++){
					types[i] = temp3.getJSONObject(i).getString("Engine").toString();
				}	
				engineComboBoxDefault = (new DefaultComboBoxModel<String> (types));
			}catch(Exception ex){ex.printStackTrace();}
			return engineComboBoxDefault;
		}
				
	//JRadioButtons
		private JRadioButton rbtnCreateBDL;
		private JRadioButton rbtnSearchBDL;
		private JRadioButton rbtnUpdateBDL;
		
		JScrollPane contentPane;
		
		public BDLMain(final JScrollPane scrollPane2) 	
		{	
			setBackground(new Color(105, 105, 105));
			contentPane = scrollPane2;
			setOpaque(true);
			setVisible(true);
							
		//Images
			final ImageIcon bosal = new ImageIcon(getClass().getResource("/images/smallBosalMuffler.png"));
			lblBosal = new JLabel(bosal);
			lblBosal.setBounds(399, 5, 192, 91);
			
		//JLabels
			lblBDL = new JLabel("Breakdown List");
			lblBDL.setHorizontalAlignment(SwingConstants.CENTER);
			lblBDL.setBounds(555, 25, 255, 40);
			lblBDL.setFont(new Font("Tahoma", Font.BOLD, 32));
			lblBDL.setForeground(Color.BLACK);
			lblCustomer = new JLabel("Customer");
			lblCustomer.setBounds(30, 10, 128, 92);
			lblCustomer.setHorizontalAlignment(SwingConstants.CENTER);
			lblCustomer.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			lblCustomer.setBackground(new Color(150, 150, 150));
			lblCustomer.setOpaque(true);
			lblCustomer.setFont(new Font("Tahoma", Font.BOLD, 14));
			lblCustomer.setForeground(Color.BLACK);
			lblPlatform = new JLabel("Platform");
			lblPlatform.setBounds(30, 101, 128, 20);
			lblPlatform.setHorizontalAlignment(SwingConstants.CENTER);
			lblPlatform.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			lblPlatform.setBackground(new Color(150, 150, 150));
			lblPlatform.setOpaque(true);
			lblPlatform.setFont(new Font("Tahoma", Font.BOLD, 14));
			lblPlatform.setForeground(Color.BLACK);
			lblType = new JLabel("Type:");
			lblType.setBounds(76, 120, 82, 20);
			lblType.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			lblType.setBackground(new Color(150, 150, 150));
			lblType.setOpaque(true);
			lblType.setFont(new Font("Tahoma", Font.BOLD, 12));
			lblType.setForeground(Color.BLACK);
			lblName = new JLabel("Name:");
			lblName.setBounds(76, 139, 82, 20);
			lblName.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			lblName.setBackground(new Color(150, 150, 150));
			lblName.setOpaque(true);
			lblName.setFont(new Font("Tahoma", Font.BOLD, 12));
			lblName.setForeground(Color.BLACK);
			lblVolume = new JLabel("Volume (L):");
			lblVolume.setBounds(76, 158, 82, 20);
			lblVolume.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			lblVolume.setBackground(new Color(150, 150, 150));
			lblVolume.setOpaque(true);
			lblVolume.setFont(new Font("Tahoma", Font.BOLD, 12));
			lblVolume.setForeground(Color.BLACK);
			lblPower = new JLabel("Power (kW):");
			lblPower.setBounds(76, 177, 82, 20);
			lblPower.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			lblPower.setBackground(new Color(150, 150, 150));
			lblPower.setOpaque(true);
			lblPower.setFont(new Font("Tahoma", Font.BOLD, 12));
			lblPower.setForeground(Color.BLACK);
			lblEngine = new JLabel("Engine");
			lblEngine.setBounds(30, 120, 47, 77);
			lblEngine.setHorizontalAlignment(SwingConstants.CENTER);
			lblEngine.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			lblEngine.setBackground(new Color(150, 150, 150));
			lblEngine.setOpaque(true);
			lblEngine.setFont(new Font("Tahoma", Font.BOLD, 12));
			lblEngine.setForeground(Color.BLACK);
			lblBosalPartNum = new JLabel("BOSAL PART NR");
			lblBosalPartNum.setBounds(399, 100, 166, 20);
			lblBosalPartNum.setHorizontalAlignment(SwingConstants.CENTER);
			lblBosalPartNum.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			lblBosalPartNum.setBackground(new Color(150, 150, 150));
			lblBosalPartNum.setOpaque(true);
			lblBosalPartNum.setFont(new Font("Tahoma", Font.BOLD, 14));
			lblBosalPartNum.setForeground(Color.BLACK);
			lblCustomerPartNum = new JLabel("CUSTOMER PART NR");
			lblCustomerPartNum.setBounds(564, 100, 187, 20);
			lblCustomerPartNum.setHorizontalAlignment(SwingConstants.CENTER);
			lblCustomerPartNum.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			lblCustomerPartNum.setBackground(new Color(150, 150, 150));
			lblCustomerPartNum.setOpaque(true);
			lblCustomerPartNum.setFont(new Font("Tahoma", Font.BOLD, 14));
			lblCustomerPartNum.setForeground(Color.BLACK);
			lblIMDS = new JLabel("IMDS");
			lblIMDS.setBounds(750, 100, 98, 20);
			lblIMDS.setHorizontalAlignment(SwingConstants.CENTER);
			lblIMDS.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			lblIMDS.setBackground(new Color(150, 150, 150));
			lblIMDS.setOpaque(true);
			lblIMDS.setFont(new Font("Tahoma", Font.BOLD, 14));
			lblIMDS.setForeground(Color.BLACK);
			lblDescription = new JLabel("DESCRIPTION");
			lblDescription.setBounds(399, 138, 166, 20);
			lblDescription.setHorizontalAlignment(SwingConstants.CENTER);
			lblDescription.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			lblDescription.setBackground(new Color(150, 150, 150));
			lblDescription.setOpaque(true);
			lblDescription.setFont(new Font("Tahoma", Font.BOLD, 14));
			lblDescription.setForeground(Color.BLACK);
			lblSilencer = new JLabel("Silencer");
			lblSilencer.setBounds(399, 157, 77, 58);
			lblSilencer.setHorizontalAlignment(SwingConstants.CENTER);
			lblSilencer.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			lblSilencer.setBackground(new Color(150, 150, 150));
			lblSilencer.setOpaque(true);
			lblSilencer.setFont(new Font("Tahoma", Font.BOLD, 14));
			lblSilencer.setForeground(Color.BLACK);
			lblVolume2 = new JLabel("Volume (L):");
			lblVolume2.setBounds(475, 157, 90, 20);
			lblVolume2.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			lblVolume2.setBackground(new Color(150, 150, 150));
			lblVolume2.setOpaque(true);
			lblVolume2.setFont(new Font("Tahoma", Font.BOLD, 12));
			lblVolume2.setForeground(Color.BLACK);
			lblLength = new JLabel("Length:");
			lblLength.setBounds(475, 176, 90, 20);
			lblLength.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			lblLength.setBackground(new Color(150, 150, 150));
			lblLength.setOpaque(true);
			lblLength.setFont(new Font("Tahoma", Font.BOLD, 12));
			lblLength.setForeground(Color.BLACK);
			lblSection = new JLabel("Section:");
			lblSection.setBounds(475, 195, 90, 20);
			lblSection.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			lblSection.setBackground(new Color(150, 150, 150));
			lblSection.setOpaque(true);
			lblSection.setFont(new Font("Tahoma", Font.BOLD, 12));
			lblSection.setForeground(Color.BLACK);
			lblIssuedBy = new JLabel("Issued By:");
			lblIssuedBy.setBounds(669, 70, 82, 20);
			lblIssuedBy.setHorizontalAlignment(SwingConstants.RIGHT);
			lblIssuedBy.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			lblIssuedBy.setBackground(new Color(150, 150, 150));
			lblIssuedBy.setOpaque(true);
			lblIssuedBy.setFont(new Font("Tahoma", Font.BOLD, 12));
			lblIssuedBy.setForeground(Color.BLACK);
			lblPage = new JLabel("Page:");
			lblPage.setBounds(1064, 10, 77, 20);
			lblPage.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			lblPage.setBackground(new Color(150, 150, 150));
			lblPage.setOpaque(true);
			lblPage.setFont(new Font("Tahoma", Font.BOLD, 14));
			lblPage.setForeground(Color.BLACK);
			lblREV = new JLabel("REV:");
			lblREV.setBounds(1064, 29, 77, 20);
			lblREV.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			lblREV.setBackground(new Color(150, 150, 150));
			lblREV.setOpaque(true);
			lblREV.setFont(new Font("Tahoma", Font.BOLD, 14));
			lblREV.setForeground(Color.BLACK);
			lblRelDate = new JLabel("Rel Date:");
			lblRelDate.setBounds(1064, 48, 77, 20);
			lblRelDate.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			lblRelDate.setBackground(new Color(150, 150, 150));
			lblRelDate.setOpaque(true);
			lblRelDate.setFont(new Font("Tahoma", Font.BOLD, 14));
			lblRelDate.setForeground(Color.BLACK);
			lblREVDate = new JLabel("REV Date:");
			lblREVDate.setBounds(1064, 67, 77, 20);
			lblREVDate.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			lblREVDate.setBackground(new Color(150, 150, 150));
			lblREVDate.setOpaque(true);
			lblREVDate.setFont(new Font("Tahoma", Font.BOLD, 14));
			lblREVDate.setForeground(Color.BLACK);
			lblProduction = new JLabel("Production");
			lblProduction.setHorizontalAlignment(SwingConstants.CENTER);
			lblProduction.setBounds(1041, 92, 200, 20);
			lblProduction.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			lblProduction.setBackground(new Color(150, 150, 150));
			lblProduction.setOpaque(true);
			lblProduction.setFont(new Font("Tahoma", Font.BOLD, 14));
			lblProduction.setForeground(Color.BLACK);
			lblRelPlant1 = new JLabel("Rel Plant 1:");
			lblRelPlant1.setBounds(1050, 136, 91, 20);
			lblRelPlant1.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			lblRelPlant1.setBackground(new Color(150, 150, 150));
			lblRelPlant1.setOpaque(true);
			lblRelPlant1.setFont(new Font("Tahoma", Font.BOLD, 14));
			lblRelPlant1.setForeground(Color.BLACK);
			lblRelPlant2 = new JLabel("Rel Plant 2:");
			lblRelPlant2.setBounds(1050, 155, 91, 20);
			lblRelPlant2.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			lblRelPlant2.setBackground(new Color(150, 150, 150));
			lblRelPlant2.setOpaque(true);
			lblRelPlant2.setFont(new Font("Tahoma", Font.BOLD, 14));
			lblRelPlant2.setForeground(Color.BLACK);
			lblRelSupplier = new JLabel("Rel Supplier:");
			lblRelSupplier.setBounds(1050, 174, 91, 20);
			lblRelSupplier.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			lblRelSupplier.setBackground(new Color(150, 150, 150));
			lblRelSupplier.setOpaque(true);
			lblRelSupplier.setFont(new Font("Tahoma", Font.BOLD, 14));
			lblRelSupplier.setForeground(Color.BLACK);
			lblBOSAL = new JLabel("BOSAL");
			lblBOSAL.setHorizontalAlignment(SwingConstants.CENTER);
			lblBOSAL.setBounds(276, 223, 628, 20);
			lblBOSAL.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			lblBOSAL.setBackground(new Color(150, 150, 150));
			lblBOSAL.setOpaque(true);
			lblBOSAL.setFont(new Font("Tahoma", Font.BOLD, 14));
			lblBOSAL.setForeground(Color.BLACK);
			lblCUSTOMER = new JLabel("CUSTOMER");
			lblCUSTOMER.setHorizontalAlignment(SwingConstants.CENTER);
			lblCUSTOMER.setBounds(903, 223, 337, 20);
			lblCUSTOMER.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			lblCUSTOMER.setBackground(new Color(150, 150, 150));
			lblCUSTOMER.setOpaque(true);
			lblCUSTOMER.setFont(new Font("Tahoma", Font.BOLD, 14));
			lblCUSTOMER.setForeground(Color.BLACK);
			lblNote = new JLabel("Note");
			lblNote.setHorizontalAlignment(SwingConstants.CENTER);
			lblNote.setBounds(30, 834, 1211, 20);
			lblNote.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			lblNote.setBackground(new Color(150, 150, 150));
			lblNote.setOpaque(true);
			lblNote.setFont(new Font("Tahoma", Font.BOLD, 14));
			lblNote.setForeground(Color.BLACK);
			lblRemark = new JLabel("Remark");
			lblRemark.setHorizontalAlignment(SwingConstants.CENTER);
			lblRemark.setBounds(30, 771, 1211, 20);
			lblRemark.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			lblRemark.setBackground(new Color(150, 150, 150));
			lblRemark.setOpaque(true);
			lblRemark.setFont(new Font("Tahoma", Font.BOLD, 14));
			lblRemark.setForeground(Color.BLACK);
			
		//JButtons
			btnAdd = new JButton("Add");
			btnAdd.setBounds(30, 210, 75, 20);
			btnAdd.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					if (e.getSource() == btnAdd)
					{				
						String[] data = new String[0];
						table1.addRow(data);
						setItemColumnIndex();
					}
				}				
			});
			btnDelete = new JButton("Delete");
			btnDelete.setBounds(115, 210, 75, 20);
			btnDelete.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					if (e.getSource() == btnDelete)
					{
						int n = -1;
						int[] rows = myTable.getSelectedRows();
						if(rows.length == 0){
							JOptionPane.showMessageDialog(
								    BDLframe,
								    rows.length + " rows were selected!",
								    "Invalid Selection",
									JOptionPane.ERROR_MESSAGE);
						}else{
							n = JOptionPane.showConfirmDialog(
								    BDLframe,
								    "Are you sure you want to delete selected rows?",
								    "Save:",
								    JOptionPane.YES_NO_OPTION,
									JOptionPane.WARNING_MESSAGE
									);
						}
						if (n == 0) {
							for (int i = rows.length - 1; i >= 0; i--) {
								table1.removeRow(rows[i]);
								setItemColumnIndex();
							}
						}						
					}
				}				
			});
		
		ImageIcon save = new ImageIcon(getClass().getResource("/images/save.jpg"));
		btnSave = new JButton(save);
		btnSave.setBounds(875, 177, 103, 34);
		btnSave.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == btnSave)
				{
					if(txtBosalPartNum.getText() == null || txtBosalPartNum.getText().equals("")){
						JOptionPane.showMessageDialog(BDLframe,
							    "Please Enter a Bosal Part Number",
							    "Invalid Entry",
							    JOptionPane.ERROR_MESSAGE);					
					} else {
						int n = JOptionPane.showConfirmDialog(
							    BDLframe,
							    "Are you sure you want to save part data?",
							    "Save:",
							    JOptionPane.YES_NO_OPTION,
								JOptionPane.WARNING_MESSAGE);
						if(n == 0){
							TableModel table = myTable.getModel();
							int rowCount = table.getRowCount();
							if(rowCount == 0){
								JOptionPane.showMessageDialog(BDLframe,
									    "No Items were added to this breakdown list",
									    "Invalid Entry",
									    JOptionPane.ERROR_MESSAGE);	
							} else {
								String[] itm = new String[rowCount];
								String[] qty = new String[rowCount];
								String s1 = "[{\"BreakdownListNumber\":\""+txtBosalPartNum.getText()+"\"},";
								for(int i = 0; i < rowCount; i++){
									itm[i] = table.getValueAt(i, 4).toString();
									if(table.getValueAt(i,1).toString().equals("")){
										qty[i] = "0";
									} else {
										qty[i] = table.getValueAt(i, 1).toString();
									}
								}
								for(int i = 0; i < rowCount; i++){
									s1 = s1+"{\"Item"+(i+1)+"\":\""+itm[i]+"\","+"\"Qty"+(i+1)+"\":"+qty[i]+"}";
									if (i < (rowCount - 1)){
										s1 = s1 + ",";
									}
								}
								s1 = s1 + "]";

								String s2 = "[{\"BreakdownListNumber\":\""+txtBosalPartNum.getText()+"\"}";
								if (!(txtREV.getText().equals("")) || !(txtREV.getText() == null)) {
									s2 = s2+", {\"Rev\":\""+txtREV.getText()+"\"}";
								}
								if (!(txtREVDate.getText().equals("")) || !(txtREVDate.getText() == null)) {
									s2 = s2+", {\"RevDate\":\""+txtREVDate.getText()+"\"}";
								}
								if (!(txtRelDate.getText().equals("")) || !(txtRelDate.getText() == null)) {
									s2 = s2+", {\"ReleaseDate\":\""+txtRelDate.getText()+"\"}";
								}
								if (!(txtProduction.getText().equals("")) || !(txtProduction.getText() == null)) {
									s2 = s2+", {\"Production\":\""+txtProduction.getText()+"\"}";
								}
								if (!(txtRelPlant1.getText().equals("")) || !(txtRelPlant1.getText() == null)) {
									s2 = s2+", {\"RelPlant1\":\""+txtRelPlant1.getText()+"\"}";
								}
								if (!(txtRelPlant2.getText().equals("")) || !(txtRelPlant2.getText() == null)) {
									s2 = s2+", {\"RelPlant2\":\""+txtRelPlant2.getText()+"\"}";
								}
								if (!(txtRelSupplier.getText().equals("")) || !(txtRelSupplier.getText() == null)) {
									s2 = s2+", {\"RelSupplier\":\""+txtRelSupplier.getText()+"\"}";
								}
								if (!(txtVolume2.getText().equals("")) || !(txtVolume2.getText() == null)) {
									s2 = s2+", {\"Volume\":\""+txtVolume2.getText()+"\"}";
								}
								if (!(txtLength.getText().equals("")) || !(txtLength.getText() == null)) {
									s2 = s2+", {\"Length\":\""+txtLength.getText()+"\"}";
								}
								if (!(txtSection.getText().equals("")) || !(txtSection.getText() == null)) {
									s2 = s2+", {\"Section\":\""+txtSection.getText()+"\"}";
								}
								if (!(txtName.getText().equals("")) || !(txtName.getText() == null)) {
									s2 = s2+", {\"Engine\":\""+txtName.getText()+"\"}";
								}
								if (!(txtPlatform.getText().equals("")) || !(txtPlatform.getText() == null)) {
									s2 = s2+", {\"Platform\":\""+txtPlatform.getText()+"\"}";
								}
								if (!(txtCustomer.getText().equals("")) || !(txtCustomer.getText() == null)) {
									s2 = s2+", {\"Customer\":\""+txtCustomer.getText()+"\"}";
								}
								s2 = s2 + "]";
								System.out.print(s2);
								JSONArray temp1;
								JSONArray temp2;
								try {
									temp1 = new JSONArray(s1);
									temp2 = new JSONArray(s2);
									if (rbtnCreateBDL.isSelected() == true) {
										con.insertNewBDL(temp1);
										con.insertBDLInfo(temp2);
									}
									else if (rbtnSearchBDL.isSelected() == true) {
										con.insertNewBDL(temp1);
										con.insertBDLInfo(temp2);
									}
									
									System.out.println(temp1);
									System.out.println(temp2);
								} catch (JSONException ex) {
									ex.printStackTrace();
								} catch (Exception ex) {
									ex.printStackTrace();
								}
							}
						}
					}
				}
			}
		});
		
		ImageIcon print = new ImageIcon(getClass().getResource("/images/pdf.jpg"));
		btnPdfPrint = new JButton(print);
		btnPdfPrint.setBounds(875, 130, 103, 34);
		btnPdfPrint.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == btnPdfPrint)
				{
					setBackground(Color.WHITE);
					rbtnUpdateBDL.setVisible(false);
					rbtnCreateBDL.setVisible(false);
					rbtnSearchBDL.setVisible(false);
					btnAdd.setVisible(false);
					btnDelete.setVisible(false);
					btnPdfPrint.setVisible(false);
					btnSave.setVisible(false);
					cbxCustomer.setVisible(false);
					cbxPlatform.setVisible(false);
					cbxName.setVisible(false);
					
					JFileChooser fc = new JFileChooser();
	                fc.showSaveDialog(null);
	                File file = fc.getSelectedFile();
	             
					/*Path p1 = Paths.get(System.getProperty("user.home"), "Desktop");
					System.out.println(p1);
					
					String s = (String)JOptionPane.showInputDialog(
		                    BDLframe,
		                    "Enter New File Name:",
		                    "Search Dialog",
		                    JOptionPane.PLAIN_MESSAGE,
		                    bosal,
		                    null,
		                    p1+"\\"+txtBosalPartNum.getText()+""+.bosal.0000+"");*/
		//Code used for sending JPanel to PDF
					try {
			            Document document = new Document(PageSize.A4.rotate(), 50, 50, 50, 50);
			            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file+".pdf"));
			            document.open();
			            PdfContentByte cb = writer.getDirectContent();
			            PdfTemplate tp = cb.createTemplate(pnlMain.getWidth(), pnlMain.getHeight());
			            @SuppressWarnings("deprecation")
						Graphics2D g2 = tp.createGraphics(pnlMain.getWidth(), pnlMain.getHeight());
			            AffineTransform at = new AffineTransform();
			            if(pnlMain.getHeight() > 594){
			            	int x = getX(); 
			            	int y = getY() + (pnlMain.getHeight() - 589);
			            	System.out.println(pnlMain.getHeight());
			            at.translate(x + 20, y);}
			            if(pnlMain.getHeight() == 594){
			            	int x = getX(); 
			            	int y = getY();
			            	System.out.println(pnlMain.getHeight());
			            at.translate(x + 20, y);}       
			            g2.transform(at);
			            g2.scale(0.63, 0.63);
			            pnlMain.printAll(g2);
			            g2.dispose();
			            cb.addTemplate(tp, 0, 0);
			            document.close();
			            setBackground(new Color(105, 105, 105));
			            rbtnUpdateBDL.setVisible(true);
						rbtnCreateBDL.setVisible(true);
						rbtnSearchBDL.setVisible(true);
						btnAdd.setVisible(true);
						btnDelete.setVisible(true);
						btnPdfPrint.setVisible(true);
						btnSave.setVisible(true);
						cbxCustomer.setVisible(true);
						cbxPlatform.setVisible(true);
						cbxName.setVisible(true);
					} catch (Exception e1) {
					    e1.printStackTrace();
					}
		//End

		}}});
		
		//JTable
			final MouseAdapter mouseClickListener = new MouseAdapter(){
				public void mouseClicked(MouseEvent e) {
					if(e.getClickCount() == 2){
						
						if(e.getSource() == txtBosalPartNum){
							System.out.println("I made it");
							String s = (String)JOptionPane.showInputDialog(
				                    BDLframe,
				                    "Enter a Bosal Part Number:",
				                    "Search Dialog",
				                    JOptionPane.PLAIN_MESSAGE,
				                    bosal,
				                    null,
				                    "");
							//If a string was returned, say so.
							if ((s != null) && (s.length() > 0)) {
							    setSearchText(s);
							}	
							txtBosalPartNum.setText(getSearchText());
						}else if(e.getSource() == myTable){
							JTable table = ((JTable)e.getSource());
							int row = table.getSelectedRow();
							int column = table.getSelectedColumn();
							if(column == 4){
								System.out.println("I made it");
								String s = (String)JOptionPane.showInputDialog(
					                    BDLframe,
					                    "Enter a Bosal Part Number:",
					                    "Search Dialog",
					                    JOptionPane.PLAIN_MESSAGE,
					                    bosal,
					                    null,
					                    "");
								//If a string was returned, say so.
								if ((s != null) && (s.length() > 0)) {
								    setSearchText(s);
								}
								table.setValueAt(getSearchText(), row, column);
							}
						}else if (e.getSource() == txtCustImage){
							System.out.println("I made it");
							JFileChooser fc = new JFileChooser();
			                fc.showSaveDialog(null);
			                File file = fc.getSelectedFile();
			                if ((file != null) && (file.length() > 0)) {
								    
								}
							}
						}
					}					
				};
			
			final TableModelListener columnListener = new TableModelListener(){
				public void tableChanged(TableModelEvent e) {
					if(e.getColumn() == 4){
						int row = e.getLastRow();
						String description = null;
						int rev = -1;
						String drawingNumber = null;
						int drawingRev = -1;
						String drawingRevDate = null;
						String productionReleaseDate = null;
						String customer = null;
						
						try {
							JSONArray temp = con.queryDatabase("bosal parts", "BosalPartNumber", getSearchText());
							for(int i = 0; i < temp.length(); i++){
								try{
									description = temp.getJSONObject(i).getString("PartDescription").toString();
								}catch(Exception ex){
									//System.out.println("Part " + getSearchText() + " does not contain PartDescription");
								}
								try{
									rev = (int)temp.getJSONObject(i).getInt("Rev");
								}catch(Exception ex){
									//System.out.println("Part " + getSearchText() + " does not contain Rev");
								}
								try{
									drawingNumber = temp.getJSONObject(i).get("DrawingNumber").toString();
								}catch(Exception ex){
									//System.out.println("Part " + getSearchText() + " does not contain DrawingNumber");
								}
								try{
								drawingRev = (int)temp.getJSONObject(i).getInt("DrawingRev");
								}catch(Exception ex){
									//System.out.println("Part " + getSearchText() + " does not contain DrawingRev");
								}
								try{
									drawingRevDate = temp.getJSONObject(i).get("DrawingRevDate").toString();
								}catch(Exception ex){
									//System.out.println("Part " + getSearchText() + " does not contain DrawingRevDate");
								}
								try{
									productionReleaseDate = temp.getJSONObject(i).get("ProductionReleaseDate").toString();
								}catch(Exception ex){
									//System.out.println("Part " + getSearchText() + " does not contain ProductionReleaseDate");
								}
								try{
									customer = temp.getJSONObject(i).get("CustPartNumber").toString();
								}catch(Exception ex){
									//System.out.println("Part " + getSearchText() + " does not contain CustPartNumber");
								}
							}
						} catch (Exception ex) {
							ex.printStackTrace();
						}
						if(description != null){
							table1.setValueAt(description, row, 3);
						}
						if(rev != -1){
							table1.setValueAt(rev, row, 6);
						}
						if(drawingNumber != null){
							table1.setValueAt(drawingNumber, row, 7);
						}
						if(drawingRev != -1){
							table1.setValueAt(drawingRev, row, 8);
						}
						if(drawingRevDate != null){
							table1.setValueAt(drawingRevDate, row, 9);
						}
						if(productionReleaseDate != null){
							table1.setValueAt(productionReleaseDate, row, 10);	
						}
						if(customer != null){
							table1.setValueAt(customer, row, 12);	
						}
					}					
				}				
			};
			bdlHeaders();
			myTable = new JTable(table1){	
				public boolean isCellEditable(int row, int column){
					if(column == 1){
						return true;
					}else{						
						return false;
					}
				}
			};
			
			scrollPane = new JScrollPane(myTable, ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			scrollPane.setBounds(30, 242, 1210, 519);
			scrollPane.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			scrollPane.setViewportView(myTable);

			//myTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);	
			int[] columnsWidth = {
				     //  1   2   3   4    5    6    7   8   9  10  11  12  13  14  15  16  (Column Numbers)
		                40, 50, 25, 127, 100, 100, 55, 80, 70, 90, 90, 35, 89, 80, 70, 90
		        };
			int i = 0;
			for (int width : columnsWidth) {
			            TableColumn column = myTable.getColumnModel().getColumn(i++);
			            column.setMinWidth(width);
			            column.setPreferredWidth(width);
			}
		
		//JComboBoxes
			cboCustomer = new JComboBox<String>();
			cboCustomer.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			cboCustomer.setBounds(157, 10, 128, 20);
			cboCustomer.setEditable(true);
			AutoCompleteDecorator.decorate(cboCustomer);
			cboCustomer.addMouseListener(new ContextMenuMouseListener());
			cboCustomer.setForeground(Color.BLACK);
			cboPlatform = new JComboBox<String>();
			cboPlatform.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			cboPlatform.setBounds(157, 101, 128, 20);
			cboPlatform.setEditable(true);
			AutoCompleteDecorator.decorate(cboPlatform);
			cboPlatform.addMouseListener(new ContextMenuMouseListener());
			cboPlatform.setForeground(Color.BLACK);
			cboName = new JComboBox<String>();
			cboName.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			cboName.setBounds(157, 139, 128, 20);
			cboName.setEditable(true);
			AutoCompleteDecorator.decorate(cboName);
			cboName.addMouseListener(new ContextMenuMouseListener());
			cboName.setForeground(Color.BLACK);	
			
			final ItemListener cboGetInfo = (new ItemListener(){	
				public void itemStateChanged(ItemEvent e)
				{							
					if(e.getStateChange() == ItemEvent.SELECTED){
						if(e.getSource().equals(cboCustomer)){
							if(e.getStateChange() == ItemEvent.SELECTED){
								System.out.println(cboCustomer.getSelectedItem().toString());
								setCustomer(cboCustomer.getSelectedItem().toString());
								System.out.println("See I put "+getCustomer()+" into Customer like you said!");
								System.out.println("I Grabbed the Text from the Customer ComboBox!");								
								System.out.println("I am about to Click the Customer CheckBox!");
								cbxCustomer.doClick();
							}
						}
						else if(e.getSource().equals(cboPlatform)){							
							if(e.getStateChange() == ItemEvent.SELECTED){
								System.out.println(cboPlatform.getSelectedItem().toString());
								setPlatform(cboPlatform.getSelectedItem().toString());
								System.out.println("See I put "+getPlatform()+" into Platform like you said!");
								System.out.println("I Grabbed the Text from the Platform ComboBox!");
								System.out.println("I am about to Click the Platform CheckBox!");
								cbxPlatform.doClick();
							}
						}
						else if(e.getSource().equals(cboName)){							
							if(e.getStateChange() == ItemEvent.SELECTED){
								System.out.println(cboName.getSelectedItem().toString());
								if(cboName.getSelectedItem().toString() != null){
									setName(cboName.getSelectedItem().toString());
									System.out.println("See I put "+getName()+" into Name like you said!");
									System.out.println(cboName.getSelectedItem().toString());
									System.out.println("I Grabbed the Text from the Name ComboBox!");
									System.out.println("I am about to Click the Name CheckBox!");
								}
								cbxName.doClick();
							}
						}				
						//possible truth table outcomes
						if(cbxCustomer.isSelected() == true){
							System.out.println(getCustomer()+" is the Customer that was selected");
							if(cbxPlatform.isSelected() == true){
								System.out.println(getPlatform()+" is the Platform that was selected");
								if(cbxName.isSelected() == true){
									System.out.println(getName()+" is the Engine that was selected");
									System.out.println("T T T1");
									try{		
										System.out.println("T T T2");
										for(int i = 0; i < temp3.length(); i++){
											System.out.println("T T T3");
											if(getName().equals(temp3.getJSONObject(i).get("Engine").toString())){
												System.out.println("T T T4");
												setType(temp3.getJSONObject(i).get("Type").toString());
												setVolume(temp3.getJSONObject(i).get("Volume").toString());
												setPower(temp3.getJSONObject(i).get("Power").toString());
												i=temp3.length();												
											}
										}	
									}catch(Exception ex){ex.printStackTrace();}
								}
								else if(cbxName.isSelected() == false){
									System.out.println("No Engine was selected");
									System.out.println("T T F");
								}
							}
							else if(cbxPlatform.isSelected() == false){
								System.out.println("No Platform was selected");
								if(cbxName.isSelected() == true){
									System.out.println(getName()+" is the Engine that was selected");
									System.out.println("T F T1");
									try{	
										System.out.println("T F T2");
										for(int i = 0; i < temp3.length(); i++){
											System.out.println("T F T3");
											if(getName().equals(temp3.getJSONObject(i).get("Engine").toString())){
												System.out.println("T F T4");
												setPlatform(temp3.getJSONObject(i).get("Platform").toString());
												setType(temp3.getJSONObject(i).get("Type").toString());
												setVolume(temp3.getJSONObject(i).get("Volume").toString());
												setPower(temp3.getJSONObject(i).get("Power").toString());
												i=temp3.length();
											}
										}	
									}catch(Exception ex){ex.printStackTrace();}
								}
								else if(cbxName.isSelected() == false){
									System.out.println("No Engine was selected");
									System.out.println("T F F");
								}
							}
						}
						else if(cbxCustomer.isSelected() == false){
							System.out.println("No Customer was selected");
							if(cbxPlatform.isSelected() == true){
								System.out.println(getPlatform()+" is the Platform that was selected");
								if(cbxName.isSelected() == true){
									System.out.println(getName()+" is the Engine that was selected");
									System.out.println("F T T1");
									try{		
										System.out.println("F T T2");
										for(int i = 0; i < temp3.length(); i++){
											System.out.println("F T T3");
											if(getName().equals(temp3.getJSONObject(i).get("Engine").toString())){
												System.out.println("F T T4");
												setType(temp3.getJSONObject(i).get("Type").toString());
												setVolume(temp3.getJSONObject(i).get("Volume").toString());
												setPower(temp3.getJSONObject(i).get("Power").toString());
												i=temp3.length();
											}
										}	
										for(int i = 0; i < temp2.length(); i++){
											System.out.println("F T T5");
											if(getPlatform().equals(temp2.getJSONObject(i).get("Program").toString())){
												System.out.println("F T T6");
												setCustomer(temp2.getJSONObject(i).get("Customer").toString());
												i=temp2.length();
											}
										}
									}catch(Exception ex){ex.printStackTrace();}
								}
								else if(cbxName.isSelected() == false){
									System.out.println("No Engine was selected");
									System.out.println("F T F1");
									try{
										System.out.println("F T F2");
										for(int i = 0; i < temp2.length(); i++){
											System.out.println("F T F3");
											if(getPlatform().equals(temp2.getJSONObject(i).get("Program").toString())){
												System.out.println("F T F4");
												setCustomer(temp2.getJSONObject(i).get("Customer").toString());
												i=temp2.length();
											}
										}
									}catch(Exception ex){ex.printStackTrace();}									
								}
							}
							else if(cbxPlatform.isSelected() == false){
								System.out.println("No Platform was selected");
								if(cbxName.isSelected() == true){
									System.out.println(getName()+" is the Engine that was selected");
									System.out.println("F F T1");
									try{
										System.out.println("F F T2");
										for(int i = 0; i < temp3.length(); i++){
											System.out.println("F F T3");
											if(getName().equals(temp3.getJSONObject(i).get("Engine").toString())){
												System.out.println("F F T4");
												setPlatform(temp3.getJSONObject(i).get("Platform").toString());
												setType(temp3.getJSONObject(i).get("Type").toString());
												setVolume(temp3.getJSONObject(i).get("Volume").toString());
												power = temp3.getJSONObject(i).get("Power").toString();
												i=temp3.length();
											}
										}	
										for(int i = 0; i < temp2.length(); i++){
											System.out.println("F F T5");
											if(getPlatform().equals(temp2.getJSONObject(i).get("Program").toString())){
												System.out.println("F F T6");
												setCustomer(temp2.getJSONObject(i).get("Customer").toString());
												i=temp2.length();
											}
										}
									}catch(Exception ex){ex.printStackTrace();}
								}
								else if(cbxName.isSelected() == false){
									System.out.println("No Engine was selected");
									System.out.println("F F F");
								}
							}
						}
						if(e.getSource().equals(cboCustomer)){		
							//nothing is set if cboCustomer is selected				
						}
						if(e.getSource().equals(cboPlatform)){		
							//only the customer is set if cboPlatform is selected
							if(cbxCustomer.isSelected() == false){						
								cboCustomer.setSelectedItem(getCustomer());
							}												
						}
						if(e.getSource().equals(cboName)){	
							//both customer and platform are set if cboName is selected
							if(cbxCustomer.isSelected() == false){						
								cboCustomer.setSelectedItem(getCustomer());
							}					
							if(cbxPlatform.isSelected() == false){						
								cboPlatform.setSelectedItem(getPlatform());					
							}
							txtType.setText(getType());
							txtVolume.setText(getVolume());
							txtPower.setText(getPower());
						}
					}					
				}
			});		
			
		//JTextFields
			DocumentListener documentListener = new DocumentListener() {
			      public void changedUpdate(DocumentEvent documentEvent) {
			        printIt(documentEvent);
			      }
			      public void insertUpdate(DocumentEvent documentEvent) {
			        printIt(documentEvent);
			        String customer = null;
					String platform = null;
					String custPartNum = null;
					String description = null;
					
					if (rbtnSearchBDL.isSelected() == true){
						//clear all rows on the table already
						for (int i = table1.getRowCount(); i > 0; i--) {
							table1.removeRow(i-1);
						}
						setItemsForTable();
						
						try {
							JSONArray temp = con.queryDatabase("breakdown lists info", "BreakdownListNumber", getSearchText());
							
							//set text for Volume JTextField
							String volume2= null;
							try{
								volume2 = temp.getJSONObject(0).get("Volume").toString();
							}catch(Exception ex){volume2 = "-";}
							txtVolume2.setText(volume2);
							
							//set text for Length JTextField
							String length= null;
							try{
								length = temp.getJSONObject(0).get("Length").toString();
							}catch(Exception ex){length = "-";}
							txtLength.setText(length);
							
							//set text for Section JTextField
							String section= null;
							try{
								section = temp.getJSONObject(0).get("Section").toString();
							}catch(Exception ex){section = "-";}
							txtSection.setText(section);
							
							//set text for Rev JTextField
							String rev= null;
							try{
								rev = temp.getJSONObject(0).get("Rev").toString();
							}catch(Exception ex){rev = "-";}
							txtREV.setText(rev);
							
							//set text for Release Date JTextField
							String releaseDate= null;
							try{
								releaseDate = temp.getJSONObject(0).get("ReleaseDate").toString();
							}catch(Exception ex){releaseDate = "-";}
							txtRelDate.setText(releaseDate);
							
							//set text for Rev Date JTextField
							String revDate= null;
							try{
								revDate = temp.getJSONObject(0).get("RevDate").toString();
							}catch(Exception ex){revDate = "-";}
							txtREVDate.setText(revDate);
							
							//set text for Production JTextField
							String production= null;
							try{
								production = temp.getJSONObject(0).get("Production").toString();
							}catch(Exception ex){production = "-";}
							txtProduction.setText(production);
							
							//set text for RelPlant1 JTextField
							String relPlant1= null;
							try{
								relPlant1 = temp.getJSONObject(0).get("RelPlant1").toString();
							}catch(Exception ex){relPlant1 = "-";}
							txtRelPlant1.setText(relPlant1);
							
							//set text for RelPlant2 JTextField
							String relPlant2= null;
							try{
								relPlant2 = temp.getJSONObject(0).get("RelPlant2").toString();
							}catch(Exception ex){relPlant2 = "-";}
							txtRelPlant2.setText(relPlant2);
							
							//set text for RelSupplier JTextField
							String relSupplier= null;
							try{
								relSupplier = temp.getJSONObject(0).get("RelSupplier").toString();
							}catch(Exception ex){relSupplier = "-";}
							txtRelSupplier.setText(relSupplier);
							
							//set text for Engine JTextField
							String engine= null;
							try{
								engine = temp.getJSONObject(0).get("Engine").toString();
							}catch(Exception ex){engine = "-";}
							txtName.setText(engine);
							
							//set text for Platform JTextField
							String Platform= null;
							try{
								Platform = temp.getJSONObject(0).get("Platform").toString();
							}catch(Exception ex){Platform = "-";}
							txtPlatform.setText(Platform);
							
							//set text for Customer JTextField
							String Customer= null;
							try{
								Customer = temp.getJSONObject(0).get("Customer").toString();
							}catch(Exception ex){Customer = "-";}
							txtCustomer.setText(Customer);
							
							//set text for Type JTextField
							String type= null;
							try{
								type = temp.getJSONObject(0).get("Type").toString();
							}catch(Exception ex){type = "-";}
							txtType.setText(type);
							
							temp = con.queryDatabase("engines", "Engine", engine);
							//set text for Volume1 JTextField
							String volume1= null;
							try{
								volume1 = temp.getJSONObject(0).get("Volume").toString();
							}catch(Exception ex){volume1 = "-";}
							txtVolume.setText(volume1);
							
							//set text for Power JTextField
							String power= null;
							try{
								power = temp.getJSONObject(0).get("Power").toString();
							}catch(Exception ex){power = "-";}
							txtPower.setText(power);
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}
					if (rbtnCreateBDL.isSelected() == true){
						try {						
				        	JSONArray temp = con.queryDatabase("bosal parts", "BosalPartNumber", getSearchText());
				        	for(int i = 0; i < temp.length(); i++){
				        		try{
									platform = temp.getJSONObject(i).getString("Program").toString();								
									JSONArray temp2 = con.queryDatabase("programs", "Program", platform);
									for(int j = 0; j < temp2.length(); j++){
						        		try{
						        			customer = temp.getJSONObject(j).get("Customer").toString();
						        		}catch(Exception ex){
						        			//System.out.println("Program " + platform + " does not contain Customer");
						        		}
						        	}
								}catch(Exception ex){
									//System.out.println("Part " + getSearchText() + " does not contain Program");
								}
				        		try{
									custPartNum = temp.getJSONObject(i).getString("CustPartNumber").toString();
								}catch(Exception ex){
									//System.out.println("Part " + getSearchText() + " does not contain CustPartNumber");
								}
				        		try{
									description = temp.getJSONObject(i).getString("PartDescription").toString();
								}catch(Exception ex){
									//System.out.println("Part " + getSearchText() + " does not contain PartDescription");
								}
							}						
						} catch (Exception ex) {ex.printStackTrace();}
				        if(custPartNum != null){
							txtCustomerPartNum.setText(custPartNum);
						}
				        if(description != null){
							txtDescription.setText(description);
						}
				        if(rbtnCreateBDL.isSelected() == true || rbtnUpdateBDL.isSelected() == true){
					        if(customer != null){
								cboCustomer.setSelectedItem(customer);
							}
					        if(platform != null){
								cboPlatform.setSelectedItem(platform);
							}
				        }else if(rbtnSearchBDL.isSelected() == true){
				        	if(customer != null){
								txtCustomer.setText(customer);
							}	
				        	if(platform != null){
								txtPlatform.setText(platform);
							}	
				        }	
					}
			      }
			      public void removeUpdate(DocumentEvent documentEvent) {
			        printIt(documentEvent);
			      }
			      private void printIt(DocumentEvent documentEvent) {
			        DocumentEvent.EventType type = documentEvent.getType();
			        String typeString = null;
			        if (type.equals(DocumentEvent.EventType.CHANGE)) {
			          typeString = "Change";
			        }  else if (type.equals(DocumentEvent.EventType.INSERT)) {
			          typeString = "Insert";
			        }  else if (type.equals(DocumentEvent.EventType.REMOVE)) {
			          typeString = "Remove";
			        }
			        System.out.print("Type : " + typeString);
			        //javax.swing.event.DocumentEvent source = (DocumentEvent) documentEvent.getDocument();
			        //int length = ((DocumentEvent) source).getLength();
			        //System.out.println("Length: " + length);
			     }
			};
			txtCustomer = new JTextField();
			txtCustomer.setBackground(Color.white);
			txtCustomer.setBounds(157, 10, 128, 20);
			txtCustomer.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			txtCustomer.setForeground(Color.BLACK);
			txtPlatform = new JTextField();
			txtPlatform.setBackground(Color.white);
			txtPlatform.setBounds(157, 101, 128, 20);
			txtPlatform.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			txtPlatform.setForeground(Color.BLACK);
			txtType = new JTextField();
			txtType.setBounds(157, 120, 128, 20);
			txtType.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			txtType.setForeground(Color.BLACK);
			txtName = new JTextField();
			txtName.setBackground(Color.white);
			txtName.setBounds(157, 139, 128, 20);
			txtName.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			txtName.setForeground(Color.BLACK);
			txtVolume = new JTextField();
			txtVolume.setBounds(157, 158, 128, 20);
			txtVolume.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			txtVolume.setForeground(Color.BLACK);
			txtPower = new JTextField();
			txtPower.setBounds(157, 177, 128, 20);
			txtPower.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			txtPower.setForeground(Color.BLACK);
			txtBosalPartNum = new JTextField();
			txtBosalPartNum.setBounds(399, 119, 166, 20);
			txtBosalPartNum.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			txtBosalPartNum.setForeground(Color.BLACK);
			txtBosalPartNum.addMouseListener(mouseClickListener);
			txtBosalPartNum.getDocument().addDocumentListener(documentListener);
			txtCustomerPartNum = new JTextField();
			txtCustomerPartNum.setBounds(564, 119, 187, 20);
			txtCustomerPartNum.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			txtCustomerPartNum.setForeground(Color.BLACK);
			txtIMDS = new JTextField();
			txtIMDS.setBounds(750, 119, 98, 20);
			txtIMDS.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			txtIMDS.setForeground(Color.BLACK);
			txtDescription = new JTextField();
			txtDescription.setBounds(564, 138, 284, 20);
			txtDescription.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			txtDescription.setForeground(Color.BLACK);
			txtVolume2 = new JTextField();
			txtVolume2.setBounds(564, 157, 284, 20);
			txtVolume2.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			txtVolume2.setForeground(Color.BLACK);
			txtLength = new JTextField();
			txtLength.setBounds(564, 176, 284, 20);
			txtLength.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			txtLength.setForeground(Color.BLACK);
			txtSection = new JTextField();
			txtSection.setBounds(564, 195, 284, 20);
			txtSection.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			txtSection.setForeground(Color.BLACK);
			txtIssuedBy = new JTextField();
			txtIssuedBy.setBackground(Color.white);
			txtIssuedBy.setBounds(750, 70, 98, 20);
			txtIssuedBy.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			txtIssuedBy.setForeground(Color.BLACK);
			txtIssuedBy.setEditable(false);
			txtPage = new JTextField();
			txtPage.setBounds(1140, 10, 101, 20);
			txtPage.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			txtPage.setForeground(Color.BLACK);
			txtREV = new JTextField();
			txtREV.setBounds(1140, 29, 101, 20);
			txtREV.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			txtREV.setForeground(Color.BLACK);
			txtRelDate = new JTextField();
			txtRelDate.setBounds(1140, 48, 101, 20);
			txtRelDate.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			txtRelDate.setForeground(Color.BLACK);
			txtREVDate = new JTextField();
			txtREVDate.setBounds(1140, 67, 101, 20);
			txtREVDate.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			txtREVDate.setForeground(Color.BLACK);
			txtProduction = new JTextField();
			txtProduction.setBounds(1041, 111, 200, 20);
			txtProduction.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			txtProduction.setForeground(Color.BLACK);
			txtRelPlant1 = new JTextField();
			txtRelPlant1.setBounds(1140, 136, 100, 20);
			txtRelPlant1.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			txtRelPlant1.setForeground(Color.BLACK);
			txtRelPlant2 = new JTextField();
			txtRelPlant2.setBounds(1140, 155, 100, 20);
			txtRelPlant2.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			txtRelPlant2.setForeground(Color.BLACK);
			txtRelSupplier = new JTextField();
			txtRelSupplier.setBounds(1140, 174, 100, 20);
			txtRelSupplier.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			txtRelSupplier.setForeground(Color.BLACK);
			txtaRemark = new JTextArea();
			txtaRemark.setBounds(30, 789, 1211, 35);
			txtaRemark.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			txtaRemark.setForeground(Color.BLACK);
			txtaNote = new JTextArea();
			txtaNote.setBounds(30, 853, 1211, 35);
			txtaNote.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			txtaNote.setForeground(Color.BLACK);
			txtCustImage = new JTextArea();
			txtCustImage.setBounds(157, 29, 128, 73);
			txtCustImage.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			txtCustImage.setForeground(Color.BLACK);
			txtCustImage.addMouseListener(mouseClickListener);
		
		//JCheckBoxes
			cbxCustomer = new JCheckBox();
			cbxCustomer.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			cbxCustomer.setBounds(290, 15, 13, 13);
			cbxCustomer.setBackground(new Color(105, 105, 105));
			cbxCustomer.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			cbxPlatform = new JCheckBox();
			cbxPlatform.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			cbxPlatform.setBounds(290, 106, 13, 13);
			cbxPlatform.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			cbxPlatform.setBackground(new Color(105, 105, 105));
			cbxName = new JCheckBox();
			cbxName.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			cbxName.setBounds(290, 144, 13, 13);
			cbxName.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			cbxName.setBackground(new Color(105, 105, 105));
			
			ActionListener cbxListener = (new ActionListener(){
				public void actionPerformed(ActionEvent e){
					if(e.getSource() == cbxCustomer){
						if(cbxCustomer.isSelected() == true){
							System.out.println("Customer CheckBox was Clicked!");
							System.out.println("I am putting "+getCustomer()+" into the Customer txtField!");
							txtCustomer.setText(getCustomer());
							cboCustomer.removeItemListener(cboGetInfo);
							cboCustomer.setVisible(false);
							txtCustomer.setVisible(true);
							txtCustomer.setEditable(false);
						}
						else if (cbxCustomer.isSelected() == false){
							System.out.println("cbxCustomer was Deselected!");
							txtCustomer.setEditable(true);
							txtCustomer.setVisible(false);
							cboCustomer.setVisible(true);
							cboCustomer.setSelectedIndex(-1);
							cboCustomer.addItemListener(cboGetInfo);
						}
					}					
					if(e.getSource() == cbxPlatform){
						if(cbxPlatform.isSelected() == true){
							System.out.println("Platform CheckBox was Clicked!");
							System.out.println("I am putting "+getPlatform()+" into the Platform txtField!");
							if(cbxCustomer.isSelected() == true){
								cbxCustomer.doClick();
							}
							txtPlatform.setText(getPlatform());
							cboCustomer.removeItemListener(cboGetInfo);
							cboPlatform.removeItemListener(cboGetInfo);
							cboPlatform.setVisible(false);
							txtPlatform.setVisible(true);
							txtPlatform.setEditable(false);
						}
						else if (cbxPlatform.isSelected() == false){
							System.out.println("cbxPlatform was Deselected!");
							txtPlatform.setEditable(true);
							txtPlatform.setVisible(false);
							cboPlatform.setVisible(true);
							cboPlatform.setSelectedIndex(-1);
							cboPlatform.addItemListener(cboGetInfo);
						}
					}					
					if(e.getSource() == cbxName){
						if(cbxName.isSelected() == true){
							System.out.println("Name CheckBox was Clicked!");
							System.out.println("I am putting "+getName()+" into the Name txtField!");
							if(cbxCustomer.isSelected() == true){
								cbxCustomer.doClick();
							}
							if(cbxPlatform.isSelected() == true){
								cbxPlatform.doClick();
							}
							txtName.setText(getName());
							cboCustomer.removeItemListener(cboGetInfo);
							cboPlatform.removeItemListener(cboGetInfo);
							cboName.removeItemListener(cboGetInfo);	
							cboName.setVisible(false);
							txtName.setVisible(true);
							txtName.setEditable(false);
						}
						else if (cbxName.isSelected() == false){
							System.out.println("cbxName was Deselected!");
							txtType.setText("");
							txtVolume.setText("");
							txtPower.setText("");
							txtName.setEditable(true);
							txtName.setVisible(false);
							cboName.setVisible(true);			
							cboName.setSelectedIndex(-1);
							cboName.addItemListener(cboGetInfo);
						}
					}
				}
			});
			cbxCustomer.addActionListener(cbxListener);
			cbxPlatform.addActionListener(cbxListener);
			cbxName.addActionListener(cbxListener);
			
		//JRadioButtons
			rbtnCreateBDL = new JRadioButton("Create BDL");
			rbtnCreateBDL.setBounds(875, 39, 103, 27);
			rbtnCreateBDL.setBackground(new Color(105, 105, 105));
			rbtnCreateBDL.setFont(new Font("Tahoma", Font.BOLD, 14));
			rbtnCreateBDL.setForeground(Color.BLACK);
			rbtnCreateBDL.addActionListener(new ActionListener(){				
				public void actionPerformed(ActionEvent e)
				{		
					if (e.getSource() == rbtnCreateBDL){
						try {
							txtIssuedBy.setText(con.getUsersName());
							temp1 = con.queryReturnAllCustomers();
							temp2 = con.queryReturnAllPrograms();
							temp3 = con.queryReturnAllEngines();
							txtType.setText("");
							txtVolume.setText("");
							txtPower.setText("");
							txtBosalPartNum.setText("");
							txtCustomerPartNum.setText("");
							txtIMDS.setText("");
							txtDescription.setText("");
							txtVolume2.setText("");
							txtLength.setText("");
							txtSection.setText("");
							txtPage.setText("");
							txtREV.setText("");
							txtRelDate.setText("");
							txtREVDate.setText("");
							txtProduction.setText("");
							txtRelPlant1.setText("");
							txtRelPlant2.setText("");
							txtRelSupplier.setText("");
							txtCustomer.setText("");
							txtCustomer.setVisible(false);
							cbxCustomer.setVisible(true);
							cboCustomer.setVisible(true);
							cboCustomer.setModel(resetCustomerComboBox());
							cboCustomer.setSelectedIndex(-1);
							btnAdd.setVisible(true);
							btnDelete.setVisible(true);
							txtPlatform.setText("");
							txtPlatform.setVisible(false);
							cbxPlatform.setVisible(true);
							cboPlatform.setVisible(true);
							cboPlatform.setModel(resetPlatformComboBox());
							cboPlatform.setSelectedIndex(-1);
							txtName.setText("");
							txtName.setVisible(false);
							cbxName.setVisible(true);
							cboName.setVisible(true);
							cboName.setModel(resetEngineComboBox());
							cboName.setSelectedIndex(-1);
							cboCustomer.addItemListener(cboGetInfo);
							cboPlatform.addItemListener(cboGetInfo);
							cboName.addItemListener(cboGetInfo);
							myTable.getModel().addTableModelListener(columnListener);
							myTable.addMouseListener(mouseClickListener);
						}catch (Exception ex){ex.printStackTrace();}
			            
					}						
			}});
			rbtnCreateBDL.doClick();
			
			rbtnSearchBDL = new JRadioButton("Search BDL");
			rbtnSearchBDL.setBounds(875, 96, 111, 27);
			rbtnSearchBDL.setBackground(new Color(105, 105, 105));
			rbtnSearchBDL.setForeground(Color.BLACK);
			rbtnSearchBDL.setFont(new Font("Tahoma", Font.BOLD, 14));
			rbtnSearchBDL.addActionListener(new ActionListener(){
				
				public void actionPerformed(ActionEvent e)
				{		
					if (e.getSource() == rbtnSearchBDL){
						try {
							txtIssuedBy.setText(con.getUsersName());
							txtType.setText("");
							txtVolume.setText("");
							txtPower.setText("");
							txtBosalPartNum.setText("");
							txtCustomerPartNum.setText("");
							txtIMDS.setText("");
							txtDescription.setText("");
							txtVolume2.setText("");
							txtLength.setText("");
							txtSection.setText("");
							txtPage.setText("");
							txtREV.setText("");
							txtRelDate.setText("");
							txtREVDate.setText("");
							txtProduction.setText("");
							txtRelPlant1.setText("");
							txtRelPlant2.setText("");
							txtRelSupplier.setText("");
							txtCustomer.setText("");
							txtCustomer.setVisible(true);
							cboCustomer.setVisible(false);
							txtPlatform.setText("");
							txtPlatform.setVisible(true);
							cboPlatform.setVisible(false);
							txtName.setText("");
							txtName.setVisible(true);
							cbxCustomer.setVisible(false);
							cbxPlatform.setVisible(false);
							cbxName.setVisible(false);
							cboName.setVisible(false);
							btnAdd.setVisible(false);
							btnDelete.setVisible(false);
							cboCustomer.removeItemListener(cboGetInfo);
							cboPlatform.removeItemListener(cboGetInfo);
							cboName.removeItemListener(cboGetInfo);
							myTable.getModel().removeTableModelListener(columnListener);
							myTable.removeMouseListener(mouseClickListener);							
						} catch (Exception ex) {ex.printStackTrace();}			            
					}						
				}});
			
			rbtnUpdateBDL = new JRadioButton("Update BDL");
			rbtnUpdateBDL.setBounds(875, 68, 111, 27);
			rbtnUpdateBDL.setBackground(new Color(105, 105, 105));
			rbtnUpdateBDL.setForeground(Color.BLACK);
			rbtnUpdateBDL.setFont(new Font("Tahoma", Font.BOLD, 14));
			rbtnUpdateBDL.addActionListener(new ActionListener(){
				
				public void actionPerformed(ActionEvent e)
				{		
					
				}
			});
			
		//ButtonGroup
			ButtonGroup group = new ButtonGroup();
			
			group.add(rbtnCreateBDL);	
			group.add(rbtnSearchBDL);
			group.add(rbtnUpdateBDL);
			
			setupPanel();
		}
		private void setupPanel() 
		{
			setLayout(null);
			add(lblBosal);
			add(lblBDL);
			add(cboCustomer);
			add(lblCustomer);
			add(lblType);
			add(lblEngine);
			add(txtVolume);
			add(lblName);
			add(txtName);
			add(txtPower);
			add(cboName);
			add(txtType);
			add(lblVolume);
			add(txtCustomer);
			add(lblPower);
			add(txtPlatform);
			add(lblPlatform);
			add(cboPlatform);
			add(cbxCustomer);
			add(cbxPlatform);
			add(cbxName);
			add(rbtnUpdateBDL);
			add(rbtnSearchBDL);
			add(rbtnCreateBDL);
			add(txtIssuedBy);
			add(lblIssuedBy);
			add(lblSilencer);
			add(txtIMDS);
			add(txtLength);
			add(txtSection);
			add(txtVolume2);
			add(txtDescription);
			add(lblLength);
			add(txtBosalPartNum);
			add(lblBosalPartNum);
			add(lblIMDS);
			add(txtCustomerPartNum);
			add(lblCustomerPartNum);
			add(lblDescription);
			add(lblVolume2);
			add(lblSection);
			add(lblREVDate);
			add(lblREV);
			add(lblPage);
			add(txtPage);
			add(txtRelDate);
			add(txtREV);
			add(lblRelDate);
			add(txtREVDate);
			add(txtProduction);
			add(lblProduction);
			add(txtRelPlant1);
			add(lblRelPlant2);
			add(lblRelPlant1);
			add(txtRelPlant2);
			add(txtRelSupplier);
			add(lblRelSupplier);
			add(btnAdd);
			add(btnDelete);
			add(lblCUSTOMER);
			add(lblBOSAL);
			add(scrollPane);
			add(btnSave);
			add(btnPdfPrint);
			add(txtaNote);
			add(txtaRemark);
			add(lblNote);
			add(lblRemark);
			add(txtCustImage);
		}
	}

}
package binaparts.gui;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.GroupLayout.Alignment;

import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import org.json.JSONArray;

import binaparts.dao.DBConnect;
import binaparts.gui.MainFrames.MainPanel;

public class BDLFrame extends JFrame 
{
	public BDLFrame(){}
	private BDLMain pnlMain;
	JFrame BDLframe = new JFrame("BreakDown List Manager:");
	DBConnect con = new DBConnect();
	
	public void displayBDL() 
	{
		JPanel contentPane = new JPanel();
		contentPane.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		contentPane.setLayout(new CardLayout());
		pnlMain = new BDLMain(contentPane);
		contentPane.add(pnlMain, "Main Menu");
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int height = screenSize.height;
		int width = screenSize.width;
		BDLframe.setResizable(true);
		BDLframe.setSize(width/2, height/2);
		BDLframe.setLocationRelativeTo(null);
		BDLframe.setSize(1100, 555);
		BDLframe.setContentPane(contentPane);
		BDLframe.setVisible(true);	
		try{
			BDLframe.setIconImage(ImageIO.read(new File("res/bosalimage.png")));
		}catch(Exception ex){ex.printStackTrace();}
	}
		
	@SuppressWarnings("serial")
	class BDLMain extends JPanel 
	{			

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
		
	//JComboBoxes
		private JComboBox<String> cboCustomer;
		private ComboBoxModel<String> resetCustomerComboBox()
		{
			JSONArray temp1 = new JSONArray();
			ComboBoxModel<String> CustComboBoxDefault = null;
			String[] Cust = null;
			
			try {
				temp1 = con.queryReturnAllCustomers();
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
			JSONArray temp1 = new JSONArray();
			ComboBoxModel<String> proComboBoxDefault = null;
			String[] types = null;
			try {
				temp1 = con.queryReturnAllPrograms();
				types = new String[temp1.length()];
				for(int i = 0; i < temp1.length(); i++){
					types[i] = temp1.getJSONObject(i).getString("Program").toString();
				}	
				proComboBoxDefault = (new DefaultComboBoxModel<String> (types));
			}catch(Exception ex){ex.printStackTrace();}
			return proComboBoxDefault;
		}
		private JComboBox<String> cboEngine;
		private ComboBoxModel<String> resetEngineComboBox()
		{
			JSONArray temp1 = new JSONArray();
			ComboBoxModel<String> engineComboBoxDefault = null;
			String[] types = null;
			try {
				temp1 = con.queryReturnAllEngines();
				types = new String[temp1.length()];
				for(int i = 0; i < temp1.length(); i++){
					types[i] = temp1.getJSONObject(i).getString("Engine").toString();
				}	
				engineComboBoxDefault = (new DefaultComboBoxModel<String> (types));
			}catch(Exception ex){ex.printStackTrace();}
			return engineComboBoxDefault;
		}
	
	//JRadioButtons
		private JRadioButton rbtnCreateBDL;
		private JRadioButton rbtnSearchBDL;
		
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
			lblBDL.setFont(new Font("EucrosiaUPC", Font.BOLD, 64));
			lblBDL.setForeground(Color.BLACK);
			lblCustomer = new JLabel("Customer");
			lblCustomer.setHorizontalAlignment(SwingConstants.CENTER);
			lblCustomer.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			lblCustomer.setBackground(new Color(150, 150, 150));
			lblCustomer.setOpaque(true);
			lblCustomer.setFont(new Font("Tahoma", Font.BOLD, 14));
			lblCustomer.setForeground(Color.BLACK);
			lblPlatform = new JLabel("Platform");
			lblPlatform.setHorizontalAlignment(SwingConstants.CENTER);
			lblPlatform.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			lblPlatform.setBackground(new Color(150, 150, 150));
			lblPlatform.setOpaque(true);
			lblPlatform.setFont(new Font("Tahoma", Font.BOLD, 14));
			lblPlatform.setForeground(Color.BLACK);
			lblType = new JLabel("Type:");
			lblType.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			lblType.setBackground(new Color(150, 150, 150));
			lblType.setOpaque(true);
			lblType.setFont(new Font("Tahoma", Font.BOLD, 12));
			lblType.setForeground(Color.BLACK);
			lblName = new JLabel("Name:");
			lblName.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			lblName.setBackground(new Color(150, 150, 150));
			lblName.setOpaque(true);
			lblName.setFont(new Font("Tahoma", Font.BOLD, 12));
			lblName.setForeground(Color.BLACK);
			lblVolume = new JLabel("Volume (L):");
			lblVolume.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			lblVolume.setBackground(new Color(150, 150, 150));
			lblVolume.setOpaque(true);
			lblVolume.setFont(new Font("Tahoma", Font.BOLD, 12));
			lblVolume.setForeground(Color.BLACK);
			lblPower = new JLabel("Power (kW):");
			lblPower.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			lblPower.setBackground(new Color(150, 150, 150));
			lblPower.setOpaque(true);
			lblPower.setFont(new Font("Tahoma", Font.BOLD, 12));
			lblPower.setForeground(Color.BLACK);
			lblEngine = new JLabel("Engine");
			lblEngine.setHorizontalAlignment(SwingConstants.CENTER);
			lblEngine.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			lblEngine.setBackground(new Color(150, 150, 150));
			lblEngine.setOpaque(true);
			lblEngine.setFont(new Font("Tahoma", Font.BOLD, 12));
			lblEngine.setForeground(Color.BLACK);
			lblBosalPartNum = new JLabel("BOSAL PART NR");
			lblBosalPartNum.setHorizontalAlignment(SwingConstants.CENTER);
			lblBosalPartNum.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			lblBosalPartNum.setBackground(new Color(150, 150, 150));
			lblBosalPartNum.setOpaque(true);
			lblBosalPartNum.setFont(new Font("Tahoma", Font.BOLD, 14));
			lblBosalPartNum.setForeground(Color.BLACK);
			lblCustomerPartNum = new JLabel("CUSTOMER PART NR");
			lblCustomerPartNum.setHorizontalAlignment(SwingConstants.CENTER);
			lblCustomerPartNum.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			lblCustomerPartNum.setBackground(new Color(150, 150, 150));
			lblCustomerPartNum.setOpaque(true);
			lblCustomerPartNum.setFont(new Font("Tahoma", Font.BOLD, 14));
			lblCustomerPartNum.setForeground(Color.BLACK);
			lblIMDS = new JLabel("IMDS");
			lblIMDS.setHorizontalAlignment(SwingConstants.CENTER);
			lblIMDS.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			lblIMDS.setBackground(new Color(150, 150, 150));
			lblIMDS.setOpaque(true);
			lblIMDS.setFont(new Font("Tahoma", Font.BOLD, 14));
			lblIMDS.setForeground(Color.BLACK);
			lblDescription = new JLabel("DESCRIPTION");
			lblDescription.setHorizontalAlignment(SwingConstants.CENTER);
			lblDescription.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			lblDescription.setBackground(new Color(150, 150, 150));
			lblDescription.setOpaque(true);
			lblDescription.setFont(new Font("Tahoma", Font.BOLD, 14));
			lblDescription.setForeground(Color.BLACK);
			lblSilencer = new JLabel("Silencer");
			lblSilencer.setHorizontalAlignment(SwingConstants.CENTER);
			lblSilencer.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			lblSilencer.setBackground(new Color(150, 150, 150));
			lblSilencer.setOpaque(true);
			lblSilencer.setFont(new Font("Tahoma", Font.BOLD, 14));
			lblSilencer.setForeground(Color.BLACK);
			lblVolume2 = new JLabel("Volume (L):");
			lblVolume2.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			lblVolume2.setBackground(new Color(150, 150, 150));
			lblVolume2.setOpaque(true);
			lblVolume2.setFont(new Font("Tahoma", Font.BOLD, 12));
			lblVolume2.setForeground(Color.BLACK);
			lblLength = new JLabel("Length:");
			lblLength.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			lblLength.setBackground(new Color(150, 150, 150));
			lblLength.setOpaque(true);
			lblLength.setFont(new Font("Tahoma", Font.BOLD, 12));
			lblLength.setForeground(Color.BLACK);
			lblSection = new JLabel("Section:");
			lblSection.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			lblSection.setBackground(new Color(150, 150, 150));
			lblSection.setOpaque(true);
			lblSection.setFont(new Font("Tahoma", Font.BOLD, 12));
			lblSection.setForeground(Color.BLACK);
			lblIssuedBy = new JLabel("Issued By:");
			lblIssuedBy.setHorizontalAlignment(SwingConstants.RIGHT);
			lblIssuedBy.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			lblIssuedBy.setBackground(new Color(150, 150, 150));
			lblIssuedBy.setOpaque(true);
			lblIssuedBy.setFont(new Font("Tahoma", Font.BOLD, 12));
			lblIssuedBy.setForeground(Color.BLACK);
			lblPage = new JLabel("Page:");
			lblPage.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			lblPage.setBackground(new Color(150, 150, 150));
			lblPage.setOpaque(true);
			lblPage.setFont(new Font("Tahoma", Font.BOLD, 14));
			lblPage.setForeground(Color.BLACK);
			lblREV = new JLabel("REV:");
			lblREV.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			lblREV.setBackground(new Color(150, 150, 150));
			lblREV.setOpaque(true);
			lblREV.setFont(new Font("Tahoma", Font.BOLD, 14));
			lblREV.setForeground(Color.BLACK);
			lblRelDate = new JLabel("Rel Date:");
			lblRelDate.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			lblRelDate.setBackground(new Color(150, 150, 150));
			lblRelDate.setOpaque(true);
			lblRelDate.setFont(new Font("Tahoma", Font.BOLD, 14));
			lblRelDate.setForeground(Color.BLACK);
			lblREVDate = new JLabel("REV Date:");
			lblREVDate.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			lblREVDate.setBackground(new Color(150, 150, 150));
			lblREVDate.setOpaque(true);
			lblREVDate.setFont(new Font("Tahoma", Font.BOLD, 14));
			lblREVDate.setForeground(Color.BLACK);
			lblProduction = new JLabel("Production:");
			lblProduction.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			lblProduction.setBackground(new Color(150, 150, 150));
			lblProduction.setOpaque(true);
			lblProduction.setFont(new Font("Tahoma", Font.BOLD, 14));
			lblProduction.setForeground(Color.BLACK);
			lblRelPlant1 = new JLabel("Rel Plant 1:");
			lblRelPlant1.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			lblRelPlant1.setBackground(new Color(150, 150, 150));
			lblRelPlant1.setOpaque(true);
			lblRelPlant1.setFont(new Font("Tahoma", Font.BOLD, 14));
			lblRelPlant1.setForeground(Color.BLACK);
			lblRelPlant2 = new JLabel("Rel Plant 2:");
			lblRelPlant2.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			lblRelPlant2.setBackground(new Color(150, 150, 150));
			lblRelPlant2.setOpaque(true);
			lblRelPlant2.setFont(new Font("Tahoma", Font.BOLD, 14));
			lblRelPlant2.setForeground(Color.BLACK);
			lblRelSupplier = new JLabel("Rel Supplier:");
			lblRelSupplier.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			lblRelSupplier.setBackground(new Color(150, 150, 150));
			lblRelSupplier.setOpaque(true);
			lblRelSupplier.setFont(new Font("Tahoma", Font.BOLD, 14));
			lblRelSupplier.setForeground(Color.BLACK);
			lblBOSAL = new JLabel("BOSAL");
			lblBOSAL.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			lblBOSAL.setBackground(new Color(150, 150, 150));
			lblBOSAL.setOpaque(true);
			lblBOSAL.setFont(new Font("Tahoma", Font.BOLD, 14));
			lblBOSAL.setForeground(Color.BLACK);
			lblCUSTOMER = new JLabel("CUSTOMER");
			lblCUSTOMER.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			lblCUSTOMER.setBackground(new Color(150, 150, 150));
			lblCUSTOMER.setOpaque(true);
			lblCUSTOMER.setFont(new Font("Tahoma", Font.BOLD, 14));
			lblCUSTOMER.setForeground(Color.BLACK);

		//JComboBoxes
			cboCustomer = new JComboBox<String>();
			AutoCompleteDecorator.decorate(cboCustomer);
			cboCustomer.addMouseListener(new ContextMenuMouseListener());
			cboCustomer.setForeground(Color.BLACK);
			cboPlatform = new JComboBox<String>();
			AutoCompleteDecorator.decorate(cboPlatform);
			cboPlatform.addMouseListener(new ContextMenuMouseListener());
			cboPlatform.setForeground(Color.BLACK);
			cboEngine = new JComboBox<String>();
			AutoCompleteDecorator.decorate(cboEngine);
			cboEngine.addMouseListener(new ContextMenuMouseListener());
			cboEngine.setForeground(Color.BLACK);		
			
			ItemListener comboBoxSelectionListener = (new ItemListener(){	
				public void itemStateChanged(ItemEvent e)
				{
					if(e.getSource().equals(cboCustomer)){
						if(e.getStateChange() == ItemEvent.SELECTED){
							String customer =  cboCustomer.getSelectedItem().toString();
							JSONArray temp1 = new JSONArray();
							String[] platform = null;
							ComboBoxModel<String> platformComboBoxModel = null;
							
							try{
								temp1 = con.queryDatabase("programs", "Customer", customer);
								platform = new String[temp1.length()];
								
								for(int i = 0; i < temp1.length(); i++){
									platform[i] = temp1.getJSONObject(i).get("Program").toString();
								}
								platformComboBoxModel =  (new DefaultComboBoxModel<String> (platform));
							}catch(Exception ex){ex.printStackTrace();}
							cboPlatform.setModel(platformComboBoxModel);
							cboPlatform.setSelectedIndex(-1);
							txtType.setText("");
							txtVolume.setText("");
							txtPower.setText("");
						}
					}	
					if(e.getSource().equals(cboPlatform)){
						if(e.getStateChange() == ItemEvent.SELECTED){
							String customer =  cboPlatform.getSelectedItem().toString();
							JSONArray temp1 = new JSONArray();
							String[] engine = null;
							ComboBoxModel<String> engineComboBoxModel = null;
							
							try{
								temp1 = con.queryDatabase("engines", "Platform", customer);
								engine = new String[temp1.length()];
								
								for(int i = 0; i < temp1.length(); i++){
									engine[i] = temp1.getJSONObject(i).get("Engine").toString();
								}
								engineComboBoxModel =  (new DefaultComboBoxModel<String> (engine));
							}catch(Exception ex){ex.printStackTrace();}
							cboEngine.setModel(engineComboBoxModel);
							cboEngine.setSelectedIndex(-1);
							txtType.setText("");
							txtVolume.setText("");
							txtPower.setText("");
						}
					}	
					if(e.getSource().equals(cboEngine)){
						if(e.getStateChange() == ItemEvent.SELECTED){
							String customer = null;
							String platform = null;
							String engine = null;
							try{
								engine = cboEngine.getSelectedItem().toString();
								platform = cboPlatform.getSelectedItem().toString();
								customer = cboCustomer.getSelectedItem().toString();
							} catch (Exception ex) {ex.printStackTrace();}
							
							if(customer == null && platform == null){
								String[] customers = null;
								String[] platforms = null;
								ComboBoxModel<String> customerComboBoxModel = null;
								ComboBoxModel<String> platformComboBoxModel = null;
								JSONArray temp1 = new JSONArray();
								JSONArray temp2 = new JSONArray();
								//JSONArray temp3 = new JSONArray();
								
								try {
									temp1 = con.queryDatabase("engines",
											"Engine", engine);
									platform = temp1.getJSONObject(0).getString("Platform").toString();
									System.out.println(platform);
									temp2 = con.queryDatabase("programs",
											"Program", platform);
									System.out.println(temp2.length());
									System.out.println(temp2.toString());
									customers = new String[temp2.length()];
									platforms = new String[temp2.length()];
									
									for(int i = 0; i < temp1.length(); i++){
										customers[i] = temp2.getJSONObject(i).get("Customer").toString();
										platforms[i] = temp2.getJSONObject(i).get("Program").toString();
									}
									customerComboBoxModel =  (new DefaultComboBoxModel<String> (customers));
									platformComboBoxModel =  (new DefaultComboBoxModel<String> (platforms));
									
									txtType.setText(temp1.getJSONObject(0)
											.get("Type").toString());
									txtVolume.setText(temp1.getJSONObject(0)
											.get("Volume").toString());
									txtPower.setText(temp1.getJSONObject(0)
											.get("Power").toString());
								} catch (Exception ex) {
									ex.printStackTrace();
								}
								cboCustomer.setModel(customerComboBoxModel);
								cboPlatform.setModel(platformComboBoxModel);
							}
							if(customer != null && platform == null){
								String[] platforms = null;
								ComboBoxModel<String> platformComboBoxModel = null;
								JSONArray temp1 = new JSONArray();
								JSONArray temp2 = new JSONArray();
								
								try {
									temp1 = con.queryDatabase("engines",
											"Engine", engine);
									temp2 = con.queryDatabase("programs",
											"Customer", customer);
									platforms = new String[temp2.length()];
									
									for(int i = 0; i < temp1.length(); i++){
										platforms[i] = temp2.getJSONObject(i).get("Program").toString();
									}
									platformComboBoxModel =  (new DefaultComboBoxModel<String> (platforms));
									
									txtType.setText(temp1.getJSONObject(0)
											.get("Type").toString());
									txtVolume.setText(temp1.getJSONObject(0)
											.get("Volume").toString());
									txtPower.setText(temp1.getJSONObject(0)
											.get("Power").toString());
								} catch (Exception ex) {
									ex.printStackTrace();
								}
								cboPlatform.setModel(platformComboBoxModel);
							}
							if (customer != null && platform != null) {
								JSONArray temp1 = new JSONArray();
								try {
									temp1 = con.queryDatabase("engines",
											"Engine", engine);
									txtType.setText(temp1.getJSONObject(0)
											.get("Type").toString());
									txtVolume.setText(temp1.getJSONObject(0)
											.get("Volume").toString());
									txtPower.setText(temp1.getJSONObject(0)
											.get("Power").toString());
								} catch (Exception ex) {
									ex.printStackTrace();
								}
							}
						}
					}	
				}});
			cboCustomer.addItemListener(comboBoxSelectionListener);
			cboPlatform.addItemListener(comboBoxSelectionListener);
			cboEngine.addItemListener(comboBoxSelectionListener);
			
		//JTextFields
			txtCustomer = new JTextField();
			txtCustomer.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			txtCustomer.setForeground(Color.BLACK);
			txtPlatform = new JTextField();
			txtPlatform.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			txtPlatform.setForeground(Color.BLACK);
			txtType = new JTextField();
			txtType.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			txtType.setForeground(Color.BLACK);
			txtName = new JTextField();
			txtName.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			txtName.setForeground(Color.BLACK);
			txtVolume = new JTextField();
			txtVolume.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			txtVolume.setForeground(Color.BLACK);
			txtPower = new JTextField();
			txtPower.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			txtPower.setForeground(Color.BLACK);
			txtBosalPartNum = new JTextField();
			txtBosalPartNum.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			txtBosalPartNum.setForeground(Color.BLACK);
			txtCustomerPartNum = new JTextField();
			txtCustomerPartNum.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			txtCustomerPartNum.setForeground(Color.BLACK);
			txtIMDS = new JTextField();
			txtIMDS.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			txtIMDS.setForeground(Color.BLACK);
			txtDescription = new JTextField();
			txtDescription.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			txtDescription.setForeground(Color.BLACK);
			txtVolume2 = new JTextField();
			txtVolume2.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			txtVolume2.setForeground(Color.BLACK);
			txtLength = new JTextField();
			txtLength.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			txtLength.setForeground(Color.BLACK);
			txtSection = new JTextField();
			txtSection.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			txtSection.setForeground(Color.BLACK);
			txtIssuedBy = new JTextField();
			txtIssuedBy.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			txtIssuedBy.setForeground(Color.BLACK);
			txtPage = new JTextField();
			txtPage.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			txtPage.setForeground(Color.BLACK);
			txtREV = new JTextField();
			txtREV.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			txtREV.setForeground(Color.BLACK);
			txtRelDate = new JTextField();
			txtRelDate.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			txtRelDate.setForeground(Color.BLACK);
			txtREVDate = new JTextField();
			txtREVDate.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			txtREVDate.setForeground(Color.BLACK);
			txtProduction = new JTextField();
			txtProduction.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			txtProduction.setForeground(Color.BLACK);
			txtRelPlant1 = new JTextField();
			txtRelPlant1.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			txtRelPlant1.setForeground(Color.BLACK);
			txtRelPlant2 = new JTextField();
			txtRelPlant2.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			txtRelPlant2.setForeground(Color.BLACK);
			txtRelSupplier = new JTextField();
			txtRelSupplier.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			txtRelSupplier.setForeground(Color.BLACK);
		
		//JRadioButtons
			rbtnCreateBDL = new JRadioButton("Create BDL");
			rbtnCreateBDL.setBackground(new Color(105, 105, 105));
			rbtnCreateBDL.setFont(new Font("Tahoma", Font.BOLD, 14));
			rbtnCreateBDL.setForeground(Color.BLACK);
			rbtnCreateBDL.addActionListener(new ActionListener(){
				
				public void actionPerformed(ActionEvent e)
				{		
					if (e.getSource() == rbtnCreateBDL){
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
							txtCustomer.setVisible(false);
							cboCustomer.setVisible(true);
							cboCustomer.setModel(resetCustomerComboBox());
							cboCustomer.setSelectedIndex(-1);
							txtPlatform.setText("");
							txtPlatform.setVisible(false);
							cboPlatform.setVisible(true);
							cboPlatform.setModel(resetPlatformComboBox());
							cboPlatform.setSelectedIndex(-1);
							txtName.setText("");
							txtName.setVisible(false);
							cboEngine.setVisible(true);
							cboEngine.setModel(resetEngineComboBox());
							cboEngine.setSelectedIndex(-1);
						}catch (Exception ex){ex.printStackTrace();}
			            
					}						
			}});
			rbtnCreateBDL.doClick();
			
			rbtnSearchBDL = new JRadioButton("Search BDL");
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
							cboEngine.setVisible(false);
							Search s = new Search();
							s.displaySearch();
						} catch (Exception ex) {ex.printStackTrace();}
			            
					}						
			}});
			
		//ButtonGroup
			ButtonGroup group = new ButtonGroup();
			
			group.add(rbtnCreateBDL);	
			group.add(rbtnSearchBDL);
			
			setupPanel();
		}
		private void setupPanel() 
		{
			GroupLayout groupLayout = new GroupLayout(this);
			groupLayout.setHorizontalGroup(
				groupLayout.createParallelGroup(Alignment.LEADING)
					.addGroup(groupLayout.createSequentialGroup()
						.addGap(10)
						.addComponent(lblBosal)
						.addGap(10)
						.addComponent(lblBDL, GroupLayout.PREFERRED_SIZE, 482, GroupLayout.PREFERRED_SIZE))
					.addGroup(groupLayout.createSequentialGroup()
						.addGap(30)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
							.addGroup(groupLayout.createSequentialGroup()
								.addGap(127)
								.addComponent(txtCustomer, GroupLayout.PREFERRED_SIZE, 128, GroupLayout.PREFERRED_SIZE))
							.addComponent(lblCustomer, GroupLayout.PREFERRED_SIZE, 128, GroupLayout.PREFERRED_SIZE)
							.addGroup(groupLayout.createSequentialGroup()
								.addGap(127)
								.addComponent(cboCustomer, GroupLayout.PREFERRED_SIZE, 128, GroupLayout.PREFERRED_SIZE))
							.addComponent(lblEngine, GroupLayout.PREFERRED_SIZE, 47, GroupLayout.PREFERRED_SIZE)
							.addGroup(groupLayout.createSequentialGroup()
								.addGap(127)
								.addComponent(cboEngine, GroupLayout.PREFERRED_SIZE, 128, GroupLayout.PREFERRED_SIZE))
							.addGroup(groupLayout.createSequentialGroup()
								.addGap(46)
								.addComponent(lblName, GroupLayout.PREFERRED_SIZE, 82, GroupLayout.PREFERRED_SIZE))
							.addGroup(groupLayout.createSequentialGroup()
								.addGap(127)
								.addComponent(txtName, GroupLayout.PREFERRED_SIZE, 128, GroupLayout.PREFERRED_SIZE))
							.addGroup(groupLayout.createSequentialGroup()
								.addGap(127)
								.addComponent(txtType, GroupLayout.PREFERRED_SIZE, 128, GroupLayout.PREFERRED_SIZE))
							.addGroup(groupLayout.createSequentialGroup()
								.addGap(127)
								.addComponent(txtPlatform, GroupLayout.PREFERRED_SIZE, 128, GroupLayout.PREFERRED_SIZE))
							.addComponent(lblPlatform, GroupLayout.PREFERRED_SIZE, 128, GroupLayout.PREFERRED_SIZE)
							.addGroup(groupLayout.createSequentialGroup()
								.addGap(46)
								.addComponent(lblType, GroupLayout.PREFERRED_SIZE, 82, GroupLayout.PREFERRED_SIZE))
							.addGroup(groupLayout.createSequentialGroup()
								.addGap(127)
								.addComponent(txtPower, GroupLayout.PREFERRED_SIZE, 128, GroupLayout.PREFERRED_SIZE))
							.addGroup(groupLayout.createSequentialGroup()
								.addGap(127)
								.addComponent(txtVolume, GroupLayout.PREFERRED_SIZE, 128, GroupLayout.PREFERRED_SIZE))
							.addGroup(groupLayout.createSequentialGroup()
								.addGap(46)
								.addComponent(lblPower, GroupLayout.PREFERRED_SIZE, 82, GroupLayout.PREFERRED_SIZE))
							.addGroup(groupLayout.createSequentialGroup()
								.addGap(46)
								.addComponent(lblVolume, GroupLayout.PREFERRED_SIZE, 82, GroupLayout.PREFERRED_SIZE))
							.addGroup(groupLayout.createSequentialGroup()
								.addGap(127)
								.addComponent(cboPlatform, GroupLayout.PREFERRED_SIZE, 128, GroupLayout.PREFERRED_SIZE)))
						.addGap(31)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
							.addGroup(groupLayout.createSequentialGroup()
								.addGap(260)
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
									.addGroup(groupLayout.createSequentialGroup()
										.addGap(81)
										.addComponent(txtIssuedBy, GroupLayout.PREFERRED_SIZE, 98, GroupLayout.PREFERRED_SIZE))
									.addComponent(lblIssuedBy, GroupLayout.PREFERRED_SIZE, 82, GroupLayout.PREFERRED_SIZE)))
							.addGroup(groupLayout.createSequentialGroup()
								.addGap(35)
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
									.addGroup(groupLayout.createSequentialGroup()
										.addGap(141)
										.addComponent(txtVolume2, GroupLayout.PREFERRED_SIZE, 263, GroupLayout.PREFERRED_SIZE))
									.addGroup(groupLayout.createSequentialGroup()
										.addGap(65)
										.addComponent(lblVolume2, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE))
									.addComponent(lblBosalPartNum, GroupLayout.PREFERRED_SIZE, 142, GroupLayout.PREFERRED_SIZE)
									.addGroup(groupLayout.createSequentialGroup()
										.addGap(141)
										.addComponent(txtCustomerPartNum, GroupLayout.PREFERRED_SIZE, 166, GroupLayout.PREFERRED_SIZE))
									.addComponent(lblDescription, GroupLayout.PREFERRED_SIZE, 142, GroupLayout.PREFERRED_SIZE)
									.addComponent(lblSilencer, GroupLayout.PREFERRED_SIZE, 66, GroupLayout.PREFERRED_SIZE)
									.addGroup(groupLayout.createSequentialGroup()
										.addGap(306)
										.addComponent(txtIMDS, GroupLayout.PREFERRED_SIZE, 98, GroupLayout.PREFERRED_SIZE))
									.addGroup(groupLayout.createSequentialGroup()
										.addGap(65)
										.addComponent(lblSection, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE))
									.addGroup(groupLayout.createSequentialGroup()
										.addGap(65)
										.addComponent(lblLength, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE))
									.addGroup(groupLayout.createSequentialGroup()
										.addGap(141)
										.addComponent(txtLength, GroupLayout.PREFERRED_SIZE, 263, GroupLayout.PREFERRED_SIZE))
									.addGroup(groupLayout.createSequentialGroup()
										.addGap(141)
										.addComponent(lblCustomerPartNum, GroupLayout.PREFERRED_SIZE, 166, GroupLayout.PREFERRED_SIZE))
									.addComponent(txtBosalPartNum, GroupLayout.PREFERRED_SIZE, 142, GroupLayout.PREFERRED_SIZE)
									.addGroup(groupLayout.createSequentialGroup()
										.addGap(141)
										.addComponent(txtSection, GroupLayout.PREFERRED_SIZE, 263, GroupLayout.PREFERRED_SIZE))
									.addGroup(groupLayout.createSequentialGroup()
										.addGap(141)
										.addComponent(txtDescription, GroupLayout.PREFERRED_SIZE, 263, GroupLayout.PREFERRED_SIZE))
									.addGroup(groupLayout.createSequentialGroup()
										.addGap(306)
										.addComponent(lblIMDS, GroupLayout.PREFERRED_SIZE, 98, GroupLayout.PREFERRED_SIZE))))
							.addGroup(groupLayout.createSequentialGroup()
								.addComponent(rbtnCreateBDL)
								.addGap(23)
								.addComponent(rbtnSearchBDL, GroupLayout.PREFERRED_SIZE, 111, GroupLayout.PREFERRED_SIZE)))
						.addGap(91)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
							.addGroup(groupLayout.createSequentialGroup()
								.addGap(11)
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
									.addComponent(lblPage, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE)
									.addGroup(groupLayout.createSequentialGroup()
										.addGap(76)
										.addComponent(txtPage, GroupLayout.PREFERRED_SIZE, 101, GroupLayout.PREFERRED_SIZE))
									.addComponent(lblREVDate, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE)
									.addComponent(lblREV, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE)
									.addGroup(groupLayout.createSequentialGroup()
										.addGap(76)
										.addComponent(txtREVDate, GroupLayout.PREFERRED_SIZE, 101, GroupLayout.PREFERRED_SIZE))
									.addComponent(lblRelDate, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE)
									.addGroup(groupLayout.createSequentialGroup()
										.addGap(76)
										.addComponent(txtREV, GroupLayout.PREFERRED_SIZE, 101, GroupLayout.PREFERRED_SIZE))
									.addGroup(groupLayout.createSequentialGroup()
										.addGap(76)
										.addComponent(txtRelDate, GroupLayout.PREFERRED_SIZE, 101, GroupLayout.PREFERRED_SIZE))))
							.addComponent(lblProduction, GroupLayout.PREFERRED_SIZE, 163, GroupLayout.PREFERRED_SIZE)
							.addComponent(txtProduction, GroupLayout.PREFERRED_SIZE, 163, GroupLayout.PREFERRED_SIZE)
							.addGroup(groupLayout.createSequentialGroup()
								.addGap(11)
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
									.addGroup(groupLayout.createSequentialGroup()
										.addGap(90)
										.addComponent(txtRelPlant2, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE))
									.addGroup(groupLayout.createSequentialGroup()
										.addGap(90)
										.addComponent(txtRelSupplier, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE))
									.addComponent(lblRelPlant2, GroupLayout.PREFERRED_SIZE, 91, GroupLayout.PREFERRED_SIZE)
									.addComponent(lblRelSupplier, GroupLayout.PREFERRED_SIZE, 91, GroupLayout.PREFERRED_SIZE)
									.addGroup(groupLayout.createSequentialGroup()
										.addGap(90)
										.addComponent(txtRelPlant1, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE))
									.addComponent(lblRelPlant1, GroupLayout.PREFERRED_SIZE, 91, GroupLayout.PREFERRED_SIZE)))))
					.addGroup(groupLayout.createSequentialGroup()
						.addGap(297)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
							.addGroup(groupLayout.createSequentialGroup()
								.addGap(397)
								.addComponent(lblCUSTOMER, GroupLayout.PREFERRED_SIZE, 289, GroupLayout.PREFERRED_SIZE))
							.addComponent(lblBOSAL, GroupLayout.PREFERRED_SIZE, 398, GroupLayout.PREFERRED_SIZE)))
			);
			groupLayout.setVerticalGroup(
				groupLayout.createParallelGroup(Alignment.LEADING)
					.addGroup(groupLayout.createSequentialGroup()
						.addGap(11)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
							.addComponent(lblBosal)
							.addGroup(groupLayout.createSequentialGroup()
								.addGap(15)
								.addComponent(lblBDL, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)))
						.addGap(15)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
							.addGroup(groupLayout.createSequentialGroup()
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
									.addComponent(txtCustomer, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)
									.addComponent(lblCustomer, GroupLayout.PREFERRED_SIZE, 92, GroupLayout.PREFERRED_SIZE)
									.addComponent(cboCustomer, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE))
								.addGap(25)
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
									.addComponent(lblEngine, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE)
									.addGroup(groupLayout.createSequentialGroup()
										.addGap(26)
										.addComponent(cboEngine, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE))
									.addGroup(groupLayout.createSequentialGroup()
										.addGap(26)
										.addComponent(lblName, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE))
									.addGroup(groupLayout.createSequentialGroup()
										.addGap(26)
										.addComponent(txtName, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE))))
							.addGroup(groupLayout.createSequentialGroup()
								.addGap(117)
								.addComponent(txtType, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE))
							.addGroup(groupLayout.createSequentialGroup()
								.addGap(91)
								.addComponent(txtPlatform, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE))
							.addGroup(groupLayout.createSequentialGroup()
								.addGap(91)
								.addComponent(lblPlatform, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE))
							.addGroup(groupLayout.createSequentialGroup()
								.addGap(117)
								.addComponent(lblType, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE))
							.addGroup(groupLayout.createSequentialGroup()
								.addGap(195)
								.addComponent(txtPower, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE))
							.addGroup(groupLayout.createSequentialGroup()
								.addGap(169)
								.addComponent(txtVolume, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE))
							.addGroup(groupLayout.createSequentialGroup()
								.addGap(195)
								.addComponent(lblPower, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE))
							.addGroup(groupLayout.createSequentialGroup()
								.addGap(169)
								.addComponent(lblVolume, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE))
							.addGroup(groupLayout.createSequentialGroup()
								.addGap(91)
								.addComponent(cboPlatform, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE))
							.addGroup(groupLayout.createSequentialGroup()
								.addGap(13)
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
									.addComponent(txtIssuedBy, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)
									.addComponent(lblIssuedBy, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE))
								.addGap(11)
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
									.addGroup(groupLayout.createSequentialGroup()
										.addGap(78)
										.addComponent(txtVolume2, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE))
									.addGroup(groupLayout.createSequentialGroup()
										.addGap(78)
										.addComponent(lblVolume2, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE))
									.addComponent(lblBosalPartNum, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)
									.addGroup(groupLayout.createSequentialGroup()
										.addGap(26)
										.addComponent(txtCustomerPartNum, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE))
									.addGroup(groupLayout.createSequentialGroup()
										.addGap(52)
										.addComponent(lblDescription, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE))
									.addGroup(groupLayout.createSequentialGroup()
										.addGap(78)
										.addComponent(lblSilencer, GroupLayout.PREFERRED_SIZE, 79, GroupLayout.PREFERRED_SIZE))
									.addGroup(groupLayout.createSequentialGroup()
										.addGap(26)
										.addComponent(txtIMDS, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE))
									.addGroup(groupLayout.createSequentialGroup()
										.addGap(130)
										.addComponent(lblSection, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE))
									.addGroup(groupLayout.createSequentialGroup()
										.addGap(104)
										.addComponent(lblLength, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE))
									.addGroup(groupLayout.createSequentialGroup()
										.addGap(104)
										.addComponent(txtLength, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE))
									.addComponent(lblCustomerPartNum, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)
									.addGroup(groupLayout.createSequentialGroup()
										.addGap(26)
										.addComponent(txtBosalPartNum, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE))
									.addGroup(groupLayout.createSequentialGroup()
										.addGap(130)
										.addComponent(txtSection, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE))
									.addGroup(groupLayout.createSequentialGroup()
										.addGap(52)
										.addComponent(txtDescription, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE))
									.addComponent(lblIMDS, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE))
								.addGap(12)
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
									.addComponent(rbtnCreateBDL, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)
									.addComponent(rbtnSearchBDL, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)))
							.addGroup(groupLayout.createSequentialGroup()
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
									.addComponent(lblPage, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)
									.addComponent(txtPage, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)
									.addGroup(groupLayout.createSequentialGroup()
										.addGap(78)
										.addComponent(lblREVDate, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE))
									.addGroup(groupLayout.createSequentialGroup()
										.addGap(26)
										.addComponent(lblREV, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE))
									.addGroup(groupLayout.createSequentialGroup()
										.addGap(78)
										.addComponent(txtREVDate, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE))
									.addGroup(groupLayout.createSequentialGroup()
										.addGap(52)
										.addComponent(lblRelDate, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE))
									.addGroup(groupLayout.createSequentialGroup()
										.addGap(26)
										.addComponent(txtREV, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE))
									.addGroup(groupLayout.createSequentialGroup()
										.addGap(52)
										.addComponent(txtRelDate, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)))
								.addGap(13)
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
									.addComponent(lblProduction, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
									.addGroup(groupLayout.createSequentialGroup()
										.addGap(21)
										.addComponent(txtProduction, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)))
								.addGap(2)
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
									.addGroup(groupLayout.createSequentialGroup()
										.addGap(26)
										.addComponent(txtRelPlant2, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE))
									.addGroup(groupLayout.createSequentialGroup()
										.addGap(52)
										.addComponent(txtRelSupplier, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE))
									.addGroup(groupLayout.createSequentialGroup()
										.addGap(26)
										.addComponent(lblRelPlant2, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE))
									.addGroup(groupLayout.createSequentialGroup()
										.addGap(52)
										.addComponent(lblRelSupplier, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE))
									.addComponent(txtRelPlant1, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)
									.addComponent(lblRelPlant1, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE))))
						.addGap(10)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
							.addComponent(lblCUSTOMER, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)
							.addComponent(lblBOSAL, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)))
			);
			setLayout(groupLayout);
		}
	}
}
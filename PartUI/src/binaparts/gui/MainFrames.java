package binaparts.gui;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.IOException;
import java.sql.*;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import org.json.*;
import org.jdesktop.swingx.autocomplete.*;

import binaparts.dao.*;
import binaparts.properties.ConfigurationManager;

@SuppressWarnings("serial")
public class MainFrames extends JFrame
{
	public MainFrames(){}
	private MainPanel main;
	private CreatePanel create;
	private UpdatePanel update;
	private FindPanel find;
	private SettingsPanel settings;
	private ManagePanel manage;
	private BDLPanel bdl;
	private ExperimentalPanel experimental;
	JFrame frame = new JFrame("Main Menu:");
	static final String configFilePath = "config.properties";
	DBConnect con = new DBConnect();
	ConfigurationManager config = null;
	
	public void displayGUI()
	{
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel contentPane = new JPanel();
		contentPane.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		contentPane.setLayout(new CardLayout());
		main = new MainPanel(contentPane);
		create = new CreatePanel(contentPane);
		update = new UpdatePanel(contentPane);
		find = new FindPanel(contentPane);
		settings = new SettingsPanel(contentPane);
		manage = new ManagePanel(contentPane);
		bdl = new BDLPanel(contentPane);
		experimental = new ExperimentalPanel(contentPane);
		contentPane.add(main, "Main Menu");
		contentPane.add(create, "Create Part");
		contentPane.add(update, "Update Part");
		contentPane.add(find, "Find Part");
		contentPane.add(settings, "Settings");
		contentPane.add(manage, "Manage Users");
		contentPane.add(bdl, "Breakdown List");
		contentPane.add(experimental, "Experimental Part");
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int height = screenSize.height;
		int width = screenSize.width;
		frame.setResizable(false);
		frame.setSize(width/2, height/2);
		frame.setLocationRelativeTo(null);
		frame.setSize(865, 555);
		frame.setContentPane(contentPane);
		frame.setVisible(true);	
		
		try{
			frame.setIconImage(ImageIO.read(new File("res/bosalimage.png")));
		}catch(Exception ex){/*Ignore*/}
	}
	
	class MainPanel extends JPanel
	{
	//Image
		
		private JLabel lblMainPic;

	//JButton
			
		private JButton btnFindPartInfo;
		private JButton btnCreatePart;
		private JButton btnUpdatePart;
		private JButton btnSetting;
		private JButton btnTest;
		private JButton btnManageUsers;
		private JButton btnBDL;
		private JButton btnExperimental;
		JPanel contentPane;
		private JLabel Bosal;
		JLabel status = new JLabel();
			
		public void setStatus()
		{
			try{
				if(con.verifyUser() == false){
					status.setText("Not Connected: Check Username/Password");
					status.setForeground(new Color(204, 0, 0));
				}else{
					status.setText("Connected");
					status.setForeground(new Color(154, 205, 50));
				}
			}catch(SQLException SQLex){
				status.setText("Not Connected SQLException");
				status.setForeground(new Color(204, 0, 0));
				SQLex.printStackTrace();
			}catch(Exception ex){
				status.setText("Not Connected EXCEPTION");
				status.setForeground(new Color(204, 0, 0));
				ex.printStackTrace();
			}
		}	
		
		public MainPanel(final JPanel main) 	
		{	
			setBackground(new Color(105, 105, 105));
			contentPane = main;
			setOpaque(true);
			setVisible(true);
			setStatus();	
			
		//Images
			
			ImageIcon bosal = new ImageIcon(getClass().getResource("/images/bosal.jpg"));
			Bosal = new JLabel(bosal);
			ImageIcon mainI = new ImageIcon(getClass().getResource("/images/mainpic.jpg"));
			lblMainPic = new JLabel(mainI);
			
		//Buttons
			
			ImageIcon experiment = new ImageIcon(getClass().getResource("/images/Experimental.jpg"));
			btnExperimental = new JButton(experiment);
			btnExperimental.addActionListener(new ActionListener() {
						
				public void actionPerformed(ActionEvent e) 
				{
					if (e.getSource() == btnExperimental) {
						setVisible(false);
						frame.setSize(837,470);
						frame.setTitle("Experimental Parts:");
						frame.setResizable(false);
						frame.setLocationRelativeTo(main);
						main.add(experimental);
						experimental.setVisible(true);
					}
				}					
			});
			
			ImageIcon bd = new ImageIcon(getClass().getResource("/images/BDL Manager.jpg"));
			btnBDL = new JButton(bd);
			btnBDL.addActionListener(new ActionListener() {
						
				public void actionPerformed(ActionEvent e) 
				{
					if (e.getSource() == btnBDL) {
						setVisible(false);
						frame.setSize(850,410);
						frame.setTitle("Breakdown List Manager:");
						frame.setResizable(false);
						frame.setLocationRelativeTo(main);
						main.add(bdl);
						bdl.setVisible(true);
					}
				}					
			});
			
			ImageIcon manager = new ImageIcon(getClass().getResource("/images/manage.jpg"));
			btnManageUsers = new JButton(manager);
			btnManageUsers.addActionListener(new ActionListener() {
						
				public void actionPerformed(ActionEvent e) 
				{
					if (e.getSource() == btnManageUsers) {
						setVisible(false);
						frame.setSize(750,460);
						frame.setTitle("Manage Users:");
						frame.setResizable(false);
						frame.setLocationRelativeTo(main);
						main.add(manage);
						manage.setVisible(true);
					}
				}					
			});
			
			ImageIcon setting = new ImageIcon(getClass().getResource("/images/testconnection.jpg"));
			btnTest = new JButton(setting);
			btnTest.addActionListener(new ActionListener() {
						
				public void actionPerformed(ActionEvent e) 
				{
					if (e.getSource() == btnTest) {
						status.setText("Connecting.....");
						status.setForeground(Color.yellow);
						setStatus();
			}}});
			
			ImageIcon setting1 = new ImageIcon(getClass().getResource("/images/settings.jpg"));
			btnSetting = new JButton(setting1);
			btnSetting.addActionListener(new ActionListener() {
						
				public void actionPerformed(ActionEvent e) 
				{
					if (e.getSource() == btnSetting) {
						setVisible(false);
						frame.setSize(600,340);
						frame.setTitle("Settings:");
						frame.setResizable(false);
						frame.setLocationRelativeTo(main);
						main.add(settings);
						settings.setVisible(true);
			}}});
			
			ImageIcon createI = new ImageIcon(getClass().getResource("/images/createpart.jpg"));
			btnCreatePart = new JButton(createI);
			btnCreatePart.addActionListener(new ActionListener() {
			
				public void actionPerformed(ActionEvent e)
				{
					if (e.getSource() == btnCreatePart){
						try{
							if(con.verifyUser() == true){
								setStatus();
								setVisible(false);
								frame.setSize(695,400);
								frame.setTitle("Create Part:");
								frame.setResizable(false);
								frame.setLocationRelativeTo(main);
								main.add(create);
								create.setVisible(true);
							}else{setStatus();}
						}catch(Exception ex){/*ignore*/}
			}}});
		
			ImageIcon updateI = new ImageIcon(getClass().getResource("/images/updatepart.jpg"));
			btnUpdatePart = new JButton(updateI);
			btnUpdatePart.addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent e) {
					if (e.getSource() == btnUpdatePart){
						try{
							if(con.verifyUser() == true){
								setStatus();
								setVisible(false);
								frame.setSize(750,425);
								frame.setTitle("Update Part:");
								frame.setResizable(false);
								frame.setLocationRelativeTo(main);
								main.add(update);
								update.setVisible(true);
							}else{setStatus();}
						}catch(Exception ex){/*ignore*/}
			}}});
			
			ImageIcon findI = new ImageIcon(getClass().getResource("/images/findpartinfo.jpg"));
			btnFindPartInfo = new JButton(findI);
			btnFindPartInfo.addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent e) {
					if (e.getSource() == btnFindPartInfo){
						try{
							if(con.verifyUser() == true){
								setStatus();
								setVisible(false);
								frame.setSize(1297,450);
								frame.setTitle("Find Part Info:");
								frame.setResizable(true);
								frame.setLocationRelativeTo(main);
								main.add(find);
								find.setVisible(true);
								
							}else{setStatus();}
						}catch(Exception ex){/*ignore*/}
			}}});
			setupPanel();
		}
		
		private void setupPanel()
		{
			GroupLayout groupLayout = new GroupLayout(this);
			groupLayout.setHorizontalGroup(
				groupLayout.createParallelGroup(Alignment.LEADING)
					.addComponent(status)
					.addGroup(groupLayout.createSequentialGroup()
						.addGap(334)
						.addComponent(Bosal))
					.addGroup(groupLayout.createSequentialGroup()
						.addGap(33)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
							.addComponent(btnCreatePart, GroupLayout.PREFERRED_SIZE, 155, GroupLayout.PREFERRED_SIZE)
							.addComponent(btnUpdatePart, GroupLayout.PREFERRED_SIZE, 155, GroupLayout.PREFERRED_SIZE)
							.addComponent(btnFindPartInfo, GroupLayout.PREFERRED_SIZE, 155, GroupLayout.PREFERRED_SIZE)
							.addComponent(btnExperimental, GroupLayout.PREFERRED_SIZE, 155, GroupLayout.PREFERRED_SIZE))
						.addGap(27)
						.addComponent(lblMainPic, GroupLayout.PREFERRED_SIZE, 430, GroupLayout.PREFERRED_SIZE)
						.addGap(27)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
							.addComponent(btnBDL, GroupLayout.PREFERRED_SIZE, 155, GroupLayout.PREFERRED_SIZE)
							.addComponent(btnSetting, GroupLayout.PREFERRED_SIZE, 155, GroupLayout.PREFERRED_SIZE)
							.addComponent(btnManageUsers, GroupLayout.PREFERRED_SIZE, 155, GroupLayout.PREFERRED_SIZE)
							.addComponent(btnTest, GroupLayout.PREFERRED_SIZE, 155, GroupLayout.PREFERRED_SIZE)))
			);
			groupLayout.setVerticalGroup(
				groupLayout.createParallelGroup(Alignment.LEADING)
					.addGroup(groupLayout.createSequentialGroup()
						.addGap(10)
						.addComponent(status, GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE)
						.addGap(9)
						.addComponent(Bosal)
						.addGap(11)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
							.addGroup(groupLayout.createSequentialGroup()
								.addGap(32)
								.addComponent(btnCreatePart, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE)
								.addGap(46)
								.addComponent(btnUpdatePart, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE)
								.addGap(46)
								.addComponent(btnFindPartInfo, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE)
								.addGap(46)
								.addComponent(btnExperimental, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE))
							.addComponent(lblMainPic, GroupLayout.PREFERRED_SIZE, 407, GroupLayout.PREFERRED_SIZE)
							.addGroup(groupLayout.createSequentialGroup()
								.addGap(32)
								.addComponent(btnBDL, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE)
								.addGap(46)
								.addComponent(btnSetting, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE)
								.addGap(46)
								.addComponent(btnManageUsers, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE)
								.addGap(46)
								.addComponent(btnTest, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE))))
			);
			setLayout(groupLayout);
			}
		
}
	class CreatePanel extends JPanel
	{		
	//JLabel
		
		private JLabel lblSeq;
		private JLabel lblDescription;
		private JLabel lblMatterialDescription;
		private JLabel lblTypeDescription;
		private JLabel lblType;
		private JLabel lblMatterial;
		private JLabel lblBosalPartNumber;
		private JLabel lblCustomerPartNumber;
		private JLabel lblSupplierPartNumber;
		private JLabel lblCreateAPart;
		private JLabel lblBosal;
		private JLabel lblProgram;
		private JLabel lblDrawingNum;
	
	//JTextField
		
		private JTextField txtDescrip;
		private JTextField txtMDescrip;
		private JTextField txtDrawingNum;
		private JTextField txtSeq;
		private JTextField txtBPart;
		private JTextField txtCPart;
		private JTextField txtSPart;
		private JTextField txtProgram;
		
	//JComboBox
		
		private JComboBox<String> cboDescrip;
		private JComboBox<String> cboType;
		private JComboBox<String> cboMat;
		JPanel contentPane;	
		private ComboBoxModel<String> resetTypeComboBox()
		{
			JSONArray temp1 = new JSONArray();
			ComboBoxModel<String> typeComboBoxDefault = null;
			String[] types = null;
			
			try {
				temp1 = con.queryReturnAllTypes();
				types = new String[temp1.length()];
				for(int i = 0; i < temp1.length(); i++){
					types[i] = temp1.getJSONObject(i).get("PartType").toString();
				}
				typeComboBoxDefault = (new DefaultComboBoxModel<String> (types));
			}catch(Exception ex){ex.printStackTrace();/*Ignore*/}
			return typeComboBoxDefault;
		}
		private ComboBoxModel<String> resetDescripComboBox()
		{
			JSONArray temp1 = new JSONArray();
			ComboBoxModel<String> descripComboBoxDefault = null;
			String[] types = null;
			
			try {
				temp1 = con.queryReturnAllDescriptions();
				types = new String[temp1.length()];
				for(int i = 0; i < temp1.length(); i++){
					types[i] = temp1.getJSONObject(i).get("Name").toString();
				}
				descripComboBoxDefault = (new DefaultComboBoxModel<String> (types));
			}catch(Exception ex){/*Ignore*/}
			return descripComboBoxDefault;
		}
		private ComboBoxModel<String> resetMatComboBox()
		{
			ComboBoxModel<String> matComboBoxDefault = new DefaultComboBoxModel<String>();
			return matComboBoxDefault;
		}
		
	//JButtons	
		
		private JButton btnSave;
		private JButton btnBack;
		
		
		public String generateBosalPartNumber(String partType, String material, String curSeq)
		{
			String BosalPartNumber = null;
			if(partType.length() < 2){
				for(int i = partType.length(); i < 2; i++){
					partType = "0" + partType;
				}
			}
			if(material.length() < 3){
				for(int i = material.length(); i < 3; i++){
					material = "0" + material;
				}
			}
			if(curSeq.length() < 5){
				for(int i = curSeq.length(); i < 5; i++){
					curSeq = "0" + curSeq;
				}
			}
			BosalPartNumber = partType + material + curSeq;			
			return BosalPartNumber;
		}
	//StringPanel	
		
		@SuppressWarnings("unchecked")
		public CreatePanel(final JPanel create)
		{
		
		//Labels
		
			lblType = new JLabel("Type");
			lblMatterial = new JLabel("Material");
			lblTypeDescription = new JLabel("Type Description");
			lblMatterialDescription = new JLabel("Material Description");
			lblSeq = new JLabel("Seq");
			lblDescription = new JLabel("Description");
			lblBosalPartNumber = new JLabel("Bosal Part Number");
			lblCustomerPartNumber = new JLabel("Customer Part Number");
			lblSupplierPartNumber = new JLabel("Supplier Part Number");
			lblCreateAPart = new JLabel("Create a Part Number");
			lblProgram = new JLabel("Program");
			lblDrawingNum = new JLabel("Drawing Number");
			ImageIcon bosal = new ImageIcon(getClass().getResource("/images/bosal.jpg"));
			lblBosal = new JLabel(bosal);
			setBackground(new Color(105, 105, 105));
			
		//TextFields
				
			txtDescrip = new JTextField();
			txtDescrip.setBackground(new Color(190, 190, 190));
			txtDescrip.setForeground(Color.BLACK);
			txtDescrip.addMouseListener(new ContextMenuMouseListener());
			txtDescrip.setEditable(false);
			txtMDescrip = new JTextField();
			txtMDescrip.setBackground(new Color(190, 190, 190));
			txtMDescrip.setForeground(Color.BLACK);
			txtMDescrip.addMouseListener(new ContextMenuMouseListener());
			txtMDescrip.setEditable(false);
			txtSeq = new JTextField();
			txtSeq.setBackground(new Color(190, 190, 190));
			txtSeq.setForeground(Color.BLACK);
			txtSeq.addMouseListener(new ContextMenuMouseListener());
			txtSeq.setEditable(false);
			txtBPart = new JTextField();
			txtBPart.setEditable(false);
			txtBPart.setBackground(new Color(190, 190, 190));
			txtBPart.setForeground(Color.BLACK);
			txtBPart.addMouseListener(new ContextMenuMouseListener());			
			txtCPart = new JTextField();
			txtCPart.setForeground(Color.BLACK);
			txtCPart.addMouseListener(new ContextMenuMouseListener());
			txtSPart = new JTextField();
			txtSPart.setForeground(Color.BLACK);
			txtSPart.addMouseListener(new ContextMenuMouseListener());
			txtProgram = new JTextField();
			txtProgram.setForeground(Color.BLACK);
			txtProgram.addMouseListener(new ContextMenuMouseListener());
			txtDrawingNum = new JTextField();
			txtDrawingNum.setForeground(Color.BLACK);
			txtDrawingNum.addMouseListener(new ContextMenuMouseListener());
				
		//ComboBoxes
				
			cboType = new JComboBox<String>();
			AutoCompleteDecorator.decorate(cboType);
			cboType.setForeground(Color.BLACK);
			cboType.setModel(resetTypeComboBox());
			cboType.setEditable(true);
			cboType.setSelectedIndex(-1);
			cboType.addMouseListener(new ContextMenuMouseListener());
			cboMat = new JComboBox<String>();
			AutoCompleteDecorator.decorate(cboMat);
			cboMat.setForeground(Color.BLACK);
			cboMat.setModel(resetMatComboBox());
			cboMat.addMouseListener(new ContextMenuMouseListener());
			cboDescrip = new JComboBox<String>();
			cboDescrip.setEditable(true);
			AutoCompleteDecorator.decorate(cboDescrip);
			cboDescrip.setForeground(Color.BLACK);
			cboDescrip.addMouseListener(new ContextMenuMouseListener());
			cboDescrip.setModel(resetDescripComboBox());
			cboDescrip.setSelectedIndex(-1);
			
			ItemListener comboBoxSelectionListener = (new ItemListener(){	
				@SuppressWarnings("rawtypes")
				public void itemStateChanged(ItemEvent e)
				{
					if(e.getSource().equals(cboType)){
						if(e.getStateChange() == ItemEvent.SELECTED){
							int partType = Integer.valueOf((String) cboType.getSelectedItem());
							JSONArray temp1 = new JSONArray();
							JSONArray temp2 = new JSONArray();
							JSONArray temp3 = new JSONArray();
							String[] mats = null;
							String[] descrip = null;
							ComboBoxModel matComboBoxModel = null;
							ComboBoxModel descripComboBoxModel = null;
							
							try{
								temp1 = con.queryDatabase("type file", "PartType", partType);
								temp2 = con.queryMaterialPartType(partType);
								temp3 = con.queryDatabase("description list", "TypeNumber", partType);
								mats = new String[temp2.length()];
								descrip = new String[temp3.length()];
								txtDescrip.setText(temp1.getJSONObject(0).get("TypeDescription").toString());
								txtSeq.setText(temp1.getJSONObject(0).get("SeqNumber").toString());
								
								for(int i = 0; i < temp2.length(); i++){
									mats[i] = temp2.getJSONObject(i).get("Material").toString();
								}
								for(int i = 0; i < temp3.length(); i++){
									descrip[i] = temp3.getJSONObject(i).get("Name").toString();
								}
								matComboBoxModel =  (new DefaultComboBoxModel (mats));
								descripComboBoxModel = (new DefaultComboBoxModel(descrip));
							}catch(Exception ex){/*ignore*/}
							txtMDescrip.setText("");
							txtBPart.setText("");
							txtSPart.setText("");
							txtCPart.setText("");
							txtDrawingNum.setText("");
							txtProgram.setText("");
							cboMat.setModel(matComboBoxModel);
							cboMat.setSelectedIndex(-1);
							cboDescrip.setModel(descripComboBoxModel);
							cboDescrip.setSelectedIndex(-1);
						}
					}
					if(e.getSource().equals(cboMat)){
						if(e.getStateChange() == ItemEvent.SELECTED){
							int partType = Integer.valueOf((String) cboType.getSelectedItem());
							int matNumber = Integer.valueOf((String) cboMat.getSelectedItem());
							JSONArray temp1 = new JSONArray();
							
							try{
								temp1 = con.queryMaterialDescription(partType, matNumber);
								txtMDescrip.setText(temp1.getJSONObject(0).getString("MaterialDescription").toString());
							}catch(Exception ex){/*ignore*/}
						}
					}
					if(cboType.getSelectedItem() != null){
						if(cboMat.getSelectedItem() != null){
							txtBPart.setText(generateBosalPartNumber(cboType.getSelectedItem().toString(), 
									cboMat.getSelectedItem().toString(), 
									txtSeq.getText()));
						}else{
							txtBPart.setText(generateBosalPartNumber(cboType.getSelectedItem().toString(), 
								"", 
								txtSeq.getText()));
						}
					}else{
						txtBPart.setText(generateBosalPartNumber("", "", txtSeq.getText()));
					}
				}});
			cboType.addItemListener(comboBoxSelectionListener);
			cboMat.addItemListener(comboBoxSelectionListener);
			
		//Buttons
				
			ImageIcon save = new ImageIcon(getClass().getResource("/images/save.jpg"));
			btnSave = new JButton(save);
			btnSave.addActionListener(new ActionListener() {
	
				public void actionPerformed(ActionEvent e) 
				{
					if (e.getSource() == btnSave){
						int n = JOptionPane.showConfirmDialog(
							    frame,
							    "Are you sure you want to save part data?",
							    "Save:",
							    JOptionPane.YES_NO_OPTION,
								JOptionPane.WARNING_MESSAGE);
						if(n == 0){
							try {
								int partType = Integer.valueOf((String) cboType.getSelectedItem());
								int mat = 0;
								if(cboMat.getSelectedItem()!= null){
									mat = Integer.valueOf((String) cboMat.getSelectedItem());
								}
								int seq = Integer.valueOf(txtSeq.getText());
								String typeDescription = txtDescrip.getText();
								String Description = (String) cboDescrip.getSelectedItem();
								String BosalPartNumber = txtBPart.getText();
								String CustomerPartNumber = txtCPart.getText();
								String SupplierPartNumber = txtSPart.getText();
								String Program = txtProgram.getText();
								String DrawingNumber = txtDrawingNum.getText();
								int Rev = 0;
								//String CreatedBy = con.getUser();
								//Timestamp Created = con.getTimestamp();
								//Timestamp Updated = con.getTimestamp();
								//String UpdatedBy =  con.getUser();
								con.insertNewPart(partType, mat, BosalPartNumber, CustomerPartNumber, 
										SupplierPartNumber, Description, Program, seq, typeDescription,
										DrawingNumber, Rev);
								
								setVisible(false);
								Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
								int height = screenSize.height;
								int width = screenSize.width;
								frame.setResizable(false);
								frame.setSize(width/2, height/2);
								frame.setLocationRelativeTo(null);
								frame.setSize(865, 555);
								frame.setTitle("Main Menu:");
								main.setVisible(true);
								cboType.setModel(resetTypeComboBox());
								cboType.setSelectedIndex(-1);
								cboMat.setModel(resetMatComboBox());
								cboDescrip.setModel(resetDescripComboBox());
								cboDescrip.setSelectedIndex(-1);
								txtCPart.setText("");
								txtSPart.setText("");
								txtBPart.setText("");
								txtMDescrip.setText("");
								txtDescrip.setText("");
								txtProgram.setText("");
								txtSeq.setText("");	
								txtDrawingNum.setText("");
							}catch(Exception ex){/*Ignore*/};
			}}}});
			
			ImageIcon back = new ImageIcon(getClass().getResource("/images/back.jpg"));
			btnBack = new JButton(back);
			btnBack.addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent e) 
				{
					if (e.getSource() == btnBack)
					{
						setVisible(false);
						Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
						int height = screenSize.height;
						int width = screenSize.width;
						frame.setResizable(false);
						frame.setSize(width/2, height/2);
						frame.setLocationRelativeTo(null);
						frame.setSize(865, 555);
						frame.setTitle("Main Menu:");
						main.setVisible(true);
						cboType.setModel(resetTypeComboBox());
						cboType.setSelectedIndex(-1);
						cboMat.setModel(resetMatComboBox());
						cboDescrip.setModel(resetDescripComboBox());
						cboDescrip.setSelectedIndex(-1);
						txtCPart.setText("");
						txtSPart.setText("");
						txtBPart.setText("");
						txtMDescrip.setText("");
						txtDescrip.setText("");
						txtSeq.setText("");
						txtProgram.setText("");
						txtDrawingNum.setText("");
			}}});
			setupPanel();	
		}
		
		private void setupPanel() 
		{
		//Label Fonts
			
			lblType.setFont(new Font("Tahoma", Font.BOLD, 14));
			lblType.setForeground(Color.BLACK);
			lblMatterial.setFont(new Font("Tahoma", Font.BOLD, 14));
			lblMatterial.setForeground(Color.BLACK);
			lblTypeDescription.setFont(new Font("Tahoma", Font.BOLD, 14));
			lblTypeDescription.setForeground(Color.BLACK);
			lblMatterialDescription.setFont(new Font("Tahoma", Font.BOLD, 14));
			lblMatterialDescription.setForeground(Color.BLACK);
			lblDescription.setFont(new Font("Tahoma", Font.BOLD, 14));
			lblDescription.setForeground(Color.BLACK);
			lblSeq.setFont(new Font("Tahoma", Font.BOLD, 14));
			lblSeq.setForeground(Color.BLACK);
			lblSupplierPartNumber.setFont(new Font("Tahoma", Font.BOLD, 14));
			lblSupplierPartNumber.setForeground(Color.BLACK);
			lblCreateAPart.setFont(new Font("EucrosiaUPC", Font.BOLD, 64));
			lblCreateAPart.setForeground(Color.BLACK);
			lblCustomerPartNumber.setFont(new Font("Tahoma", Font.BOLD, 14));
			lblCustomerPartNumber.setForeground(Color.BLACK);
			lblBosalPartNumber.setFont(new Font("Tahoma", Font.BOLD, 14));
			lblBosalPartNumber.setForeground(Color.BLACK);
			lblProgram.setFont(new Font("Tahoma", Font.BOLD, 14));
			lblProgram.setForeground(Color.BLACK);
			lblDrawingNum.setFont(new Font("Tahoma", Font.BOLD, 14));
			lblDrawingNum.setForeground(Color.BLACK);
			GroupLayout groupLayout = new GroupLayout(this);
			groupLayout.setHorizontalGroup(
				groupLayout.createParallelGroup(Alignment.LEADING)
					.addGroup(groupLayout.createSequentialGroup()
						.addGap(24)
						.addComponent(lblBosal, GroupLayout.PREFERRED_SIZE, 199, GroupLayout.PREFERRED_SIZE)
						.addGap(6)
						.addComponent(lblCreateAPart, GroupLayout.PREFERRED_SIZE, 415, GroupLayout.PREFERRED_SIZE))
					.addGroup(groupLayout.createSequentialGroup()
						.addGap(50)
						.addComponent(lblType)
						.addGap(121)
						.addComponent(lblTypeDescription)
						.addGap(163)
						.addComponent(lblBosalPartNumber))
					.addGroup(groupLayout.createSequentialGroup()
						.addGap(50)
						.addComponent(cboType, GroupLayout.PREFERRED_SIZE, 79, GroupLayout.PREFERRED_SIZE)
						.addGap(76)
						.addComponent(txtDescrip, GroupLayout.PREFERRED_SIZE, 211, GroupLayout.PREFERRED_SIZE)
						.addGap(67)
						.addComponent(txtBPart, GroupLayout.PREFERRED_SIZE, 156, GroupLayout.PREFERRED_SIZE))
					.addGroup(groupLayout.createSequentialGroup()
						.addGap(50)
						.addComponent(lblMatterial)
						.addGap(101)
						.addComponent(lblMatterialDescription)
						.addGap(143)
						.addComponent(lblCustomerPartNumber))
					.addGroup(groupLayout.createSequentialGroup()
						.addGap(50)
						.addComponent(cboMat, GroupLayout.PREFERRED_SIZE, 79, GroupLayout.PREFERRED_SIZE)
						.addGap(76)
						.addComponent(txtMDescrip, GroupLayout.PREFERRED_SIZE, 211, GroupLayout.PREFERRED_SIZE)
						.addGap(67)
						.addComponent(txtCPart, GroupLayout.PREFERRED_SIZE, 156, GroupLayout.PREFERRED_SIZE))
					.addGroup(groupLayout.createSequentialGroup()
						.addGap(50)
						.addComponent(lblSeq)
						.addGap(129)
						.addComponent(lblDescription)
						.addGap(201)
						.addComponent(lblSupplierPartNumber))
					.addGroup(groupLayout.createSequentialGroup()
						.addGap(50)
						.addComponent(txtSeq, GroupLayout.PREFERRED_SIZE, 79, GroupLayout.PREFERRED_SIZE)
						.addGap(76)
						.addComponent(cboDescrip, GroupLayout.PREFERRED_SIZE, 211, GroupLayout.PREFERRED_SIZE)
						.addGap(67)
						.addComponent(txtSPart, GroupLayout.PREFERRED_SIZE, 156, GroupLayout.PREFERRED_SIZE))
					.addGroup(groupLayout.createSequentialGroup()
						.addGap(483)
						.addComponent(lblDrawingNum, GroupLayout.PREFERRED_SIZE, 156, GroupLayout.PREFERRED_SIZE))
					.addGroup(groupLayout.createSequentialGroup()
						.addGap(50)
						.addComponent(btnBack, GroupLayout.PREFERRED_SIZE, 155, GroupLayout.PREFERRED_SIZE)
						.addGap(53)
						.addComponent(btnSave, GroupLayout.PREFERRED_SIZE, 155, GroupLayout.PREFERRED_SIZE)
						.addGap(70)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
							.addComponent(txtDrawingNum, GroupLayout.PREFERRED_SIZE, 156, GroupLayout.PREFERRED_SIZE)
							.addComponent(lblProgram, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
							.addComponent(txtProgram, GroupLayout.PREFERRED_SIZE, 156, GroupLayout.PREFERRED_SIZE)))
			);
			groupLayout.setVerticalGroup(
				groupLayout.createParallelGroup(Alignment.LEADING)
					.addGroup(groupLayout.createSequentialGroup()
						.addGap(32)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
							.addComponent(lblBosal, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
							.addComponent(lblCreateAPart, GroupLayout.PREFERRED_SIZE, 52, GroupLayout.PREFERRED_SIZE))
						.addGap(6)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
							.addComponent(lblType)
							.addComponent(lblTypeDescription)
							.addComponent(lblBosalPartNumber))
						.addGap(6)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
							.addComponent(cboType, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
							.addGroup(groupLayout.createSequentialGroup()
								.addGap(3)
								.addComponent(txtDescrip, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGroup(groupLayout.createSequentialGroup()
								.addGap(3)
								.addComponent(txtBPart, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
						.addGap(6)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
							.addComponent(lblMatterial)
							.addComponent(lblMatterialDescription)
							.addComponent(lblCustomerPartNumber))
						.addGap(6)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
							.addComponent(cboMat, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(txtMDescrip, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(txtCPart, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGap(9)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
							.addComponent(lblSeq)
							.addComponent(lblDescription)
							.addComponent(lblSupplierPartNumber))
						.addGap(6)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
							.addComponent(txtSeq, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(cboDescrip, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(txtSPart, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGap(7)
						.addComponent(lblDrawingNum, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
						.addGap(6)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
							.addGroup(groupLayout.createSequentialGroup()
								.addGap(16)
								.addComponent(btnBack, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE))
							.addGroup(groupLayout.createSequentialGroup()
								.addGap(16)
								.addComponent(btnSave, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE))
							.addGroup(groupLayout.createSequentialGroup()
								.addComponent(txtDrawingNum, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addGap(4)
								.addComponent(lblProgram, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
								.addComponent(txtProgram, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
			);
			setLayout(groupLayout);
		}
	}
	class UpdatePanel extends JPanel
	{
		//JLabels
		
		private JLabel lblBosal;
		private JLabel lblUpdatePart;
		private JLabel lblBosalPartNum;
		private JLabel lblCustomerPartNum;
		private JLabel lblSupplierPartNum;
		private JLabel lblDescription;
		private JLabel lblProgram;
		private JLabel lblRev;
		private JLabel lblDrawingNum;
		
	//JButtons
		
		private JButton btnSave;
		private JButton btnBack;
		private JButton btnCheck;
		private JButton btnDelete;
		
	//JTextFields
		
		private JTextField txtFindBosal;
		private JTextField txtCusDescrip;
		private JTextField txtSupDescrip;
		private JTextField txtProgram;
		private JTextField txtRev;
		private JTextField txtDrawingNum;
		
	//JComboBoxes
		
		private JComboBox<String> cboDescrip;
		private ComboBoxModel<String> resetDescripComboBox()
		{
			JSONArray temp1 = new JSONArray();
			ComboBoxModel<String> descripComboBoxDefault = null;
			String[] types = null;
			
			try {
				temp1 = con.queryReturnAllDescriptions();
				types = new String[temp1.length()];
				for(int i = 0; i < temp1.length(); i++){
					types[i] = temp1.getJSONObject(i).get("Name").toString();
				}
				descripComboBoxDefault = (new DefaultComboBoxModel<String> (types));
			}catch(Exception ex){/*Ignore*/}
			return descripComboBoxDefault;
		}
		
	//Update Panel
				
		public UpdatePanel(final JPanel update) 
		{
			setBackground(new Color(105, 105, 105));
		
	//TextFields		
			
			txtFindBosal = new JTextField();
			txtFindBosal.setForeground(Color.BLACK);
			txtFindBosal.addMouseListener(new ContextMenuMouseListener());
			txtCusDescrip = new JTextField();
			txtCusDescrip.setForeground(Color.BLACK);
			txtCusDescrip.addMouseListener(new ContextMenuMouseListener());
			txtSupDescrip = new JTextField();
			txtSupDescrip.setForeground(Color.BLACK);
			txtSupDescrip.addMouseListener(new ContextMenuMouseListener());
			txtProgram = new JTextField();
			txtProgram.setForeground(Color.BLACK);
			txtProgram.addMouseListener(new ContextMenuMouseListener());
			txtRev = new JTextField();
			txtRev.setEditable(false);
			txtRev.setBackground(new Color(190,190,190));
			txtRev.setForeground(Color.BLACK);
			txtRev.addMouseListener(new ContextMenuMouseListener());
			txtDrawingNum = new JTextField();
			txtDrawingNum.setForeground(Color.BLACK);
			txtDrawingNum.addMouseListener(new ContextMenuMouseListener());
			
	//JComboBoxes
			
			cboDescrip = new JComboBox<String>();
			AutoCompleteDecorator.decorate(cboDescrip);
			cboDescrip.setForeground(Color.BLACK);
			cboDescrip.addMouseListener(new ContextMenuMouseListener());
			cboDescrip.setModel(resetDescripComboBox());
			cboDescrip.setEditable(true);
			cboDescrip.setSelectedIndex(-1);
			
	//Labels		
			
			lblBosalPartNum = new JLabel("Bosal Part Number");
			lblDescription = new JLabel("Description");
			lblCustomerPartNum = new JLabel("Customer Part Number");
			lblSupplierPartNum = new JLabel("Supplier Part Number");
			lblUpdatePart = new JLabel("Update Part");
			lblProgram = new JLabel("Program");
			lblRev = new JLabel("Rev Number");
			lblDrawingNum = new JLabel("Drawing Number");
			
	//Images
			
			ImageIcon bosal = new ImageIcon(getClass().getResource("/images/bosal.jpg"));
			lblBosal = new JLabel(bosal);
			
	//Buttons		
	
			ImageIcon delete = new ImageIcon(getClass().getResource("/images/delete.jpg"));
			btnDelete = new JButton(delete);
			btnDelete.addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent e) {
					if (e.getSource() == btnDelete)
					{
						int n = JOptionPane.showConfirmDialog(
							    frame,
							    "Are you sure you want to delete part data?",
							    "Delete:",
							    JOptionPane.YES_NO_OPTION,
								JOptionPane.WARNING_MESSAGE
								);
						if(n == 0){
							try {
								con.deletePart(txtFindBosal.getText());
								txtProgram.setText("");
								txtSupDescrip.setText("");
								txtCusDescrip.setText("");
								txtFindBosal.setText("");
								cboDescrip.setModel(resetDescripComboBox());
								cboDescrip.setSelectedIndex(-1);
								txtRev.setText("");
								txtDrawingNum.setText("");
							}catch(Exception ex){
								ex.printStackTrace();
							}
						}
					}}});
			
			ImageIcon back = new ImageIcon(getClass().getResource("/images/back.jpg"));
			btnBack = new JButton(back);
			btnBack.addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent e) {
					if (e.getSource() == btnBack)
					{
						setVisible(false);
						Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
						int height = screenSize.height;
						int width = screenSize.width;
						frame.setResizable(false);
						frame.setSize(width/2, height/2);
						frame.setLocationRelativeTo(null);
						frame.setSize(865, 555);
						frame.setTitle("Main Menu:");
						main.setVisible(true);
						txtFindBosal.setText("");
						txtCusDescrip.setText("");
						txtSupDescrip.setText("");
						txtProgram.setText("");
						cboDescrip.setModel(resetDescripComboBox());
						cboDescrip.setSelectedIndex(-1);
						txtRev.setText("");
						txtDrawingNum.setText("");
					}}});
			
			ImageIcon save = new ImageIcon(getClass().getResource("/images/save.jpg"));
			btnSave = new JButton(save);
			btnSave.addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent e) {
					if (e.getSource() == btnSave)
					{
					
						int n = JOptionPane.showConfirmDialog(
							    frame,
							    "Are you sure you want to save part data?",
							    "Save:",
							    JOptionPane.YES_NO_OPTION,
								JOptionPane.WARNING_MESSAGE
								);
						if(n == 0){
							String BosalPartNumber = txtFindBosal.getText();
							String CustomerPartNumber = null;
							String SupplierPartNumber= null;
							String Program = null;
							String DrawingNumber = null;
							int Rev = 0;
							
							if(txtCusDescrip.getText().equals("-") || txtCusDescrip.getText().equals("")){
								CustomerPartNumber = null;
							}else{CustomerPartNumber = txtCusDescrip.getText();}
							if(txtSupDescrip.getText().equals("-") || txtSupDescrip.getText().equals("")){
								SupplierPartNumber = null;
							}else{SupplierPartNumber = txtSupDescrip.getText();}
							if(txtDrawingNum.getText().equals("-") || txtDrawingNum.getText().equals("")){
								DrawingNumber = null;
							}else{DrawingNumber = txtDrawingNum.getText();}
							if(txtRev.getText().equals("-") || txtRev.getText().equals("")){
								Rev = 0;
							}else{Rev = Integer.valueOf(txtRev.getText());}
							
							String Description = (String) cboDescrip.getSelectedItem();
														
							if(txtProgram.getText().equals("-") || txtProgram.getText().equals("")){
								Program = null;
							}else{Program = txtProgram.getText();}
							try {
								con.update(BosalPartNumber, CustomerPartNumber, SupplierPartNumber, 
										Description, Program, DrawingNumber, Rev);
								
								setVisible(false);
								Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
								int height = screenSize.height;
								int width = screenSize.width;
								frame.setResizable(false);
								frame.setSize(width/2, height/2);
								frame.setLocationRelativeTo(null);
								frame.setSize(865, 555);
								frame.setTitle("Main Menu:");
								main.setVisible(true);
								txtFindBosal.setText("");
								txtCusDescrip.setText("");
								txtSupDescrip.setText("");
								txtProgram.setText("");
								cboDescrip.setModel(resetDescripComboBox());
								cboDescrip.setSelectedIndex(-1);
								txtDrawingNum.setText("");
								txtRev.setText("");
							}catch(Exception ex){
								ex.printStackTrace();
							}
						}
					}}});
			
			ImageIcon check = new ImageIcon(getClass().getResource("/images/check.jpg"));
			btnCheck = new JButton(check);
			btnCheck.addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent e) {
					if (e.getSource() == btnCheck)
					{
						con = new DBConnect();
						final String findBosalText = txtFindBosal.getText();
						
						try{
							JSONObject temp = (con.queryDatabase("parts list", "BosalPartNumber", findBosalText)).getJSONObject(0);
							//set text for CustPartNumber JTextField
							String cpartText= null;
							//filter Description Combo Box by the PartType of checked Bosal #
							int partType = Integer.valueOf(txtFindBosal.getText(0, 2));
							JSONArray temp1 = new JSONArray();
							String[] descrip = null;
							ComboBoxModel<String> descripComboBoxModel = null;
							try{
								temp1 = con.queryDatabase("description list", "TypeNumber", partType);
								descrip = new String[temp1.length()];
								for(int i = 0; i < temp1.length(); i++){
									descrip[i] = temp1.getJSONObject(i).get("Name").toString();
								}
								descripComboBoxModel = (new DefaultComboBoxModel<String>(descrip));
								cboDescrip.setModel(descripComboBoxModel);
							}catch(Exception ex){ex.printStackTrace();}
							//set text for CustPartNumber JTextField
							try{
								cpartText = temp.get("CustPartNumber").toString();
							}catch(Exception ex){cpartText = "-";}
							txtCusDescrip.setText(cpartText);
							
							//set text for SupPartNumber JTextField
							String spartText= null;
							try{
								spartText = temp.get("SupPartNumber").toString();
							}catch(Exception ex){spartText = "-";}
							txtSupDescrip.setText(spartText);
							
							//set text for Description JTextField
							String descripText= null;
							try{
								descripText = temp.get("PartDescription").toString();
							}catch(Exception ex){descripText = "-";}
							cboDescrip.setSelectedItem(descripText);
							
							//set text for Program JTextField
							String programText = null;
							try{
								programText = temp.get("Program").toString();
							}catch(Exception ex){programText = "-";}
							txtProgram.setText(programText);
							
							//set text for DrawingNumber JTextField
							String DrawingNumber = null;
							try{
								DrawingNumber = temp.get("DrawingNumber").toString();
							}catch(Exception ex){DrawingNumber = "-";}
							txtDrawingNum.setText(DrawingNumber);
							
							//set text for Program JTextField
							int Rev = 0;
							try{
								Rev = Integer.valueOf(temp.get("Rev").toString());
							}catch(Exception ex){Rev = 0;}
							txtRev.setText(Integer.toString(Rev));
							
						}catch(Exception ex){
							JOptionPane.showMessageDialog(
									    frame,
									    "Bosal Part Number: " + findBosalText + " does not exist",
									    "Missing Part Number",
										JOptionPane.ERROR_MESSAGE);
							txtCusDescrip.setText("");
							txtSupDescrip.setText("");
							txtProgram.setText("");
							cboDescrip.setModel(resetDescripComboBox());
							cboDescrip.setSelectedIndex(-1);
							txtDrawingNum.setText("");
							txtRev.setText("");
						}
			}}});
			
			setupPanel();
		}
		private void setupPanel()
			{
					
	//Label Fonts
				
			lblBosalPartNum.setFont(new Font("Tahoma", Font.BOLD, 14));
			lblBosalPartNum.setForeground(Color.BLACK);
			lblDescription.setFont(new Font("Tahoma", Font.BOLD, 14));
			lblDescription.setForeground(Color.BLACK);
			lblCustomerPartNum.setFont(new Font("Tahoma", Font.BOLD, 14));
			lblCustomerPartNum.setForeground(Color.BLACK);
			lblSupplierPartNum.setFont(new Font("Tahoma", Font.BOLD, 14));
			lblSupplierPartNum.setForeground(Color.BLACK);
			lblUpdatePart.setFont(new Font("EucrosiaUPC", Font.BOLD, 64));
			lblUpdatePart.setForeground(Color.BLACK);
			lblProgram.setFont(new Font("Tahoma", Font.BOLD, 14));
			lblProgram.setForeground(Color.BLACK);
			lblRev.setFont(new Font("Tahoma", Font.BOLD, 14));
			lblRev.setForeground(Color.BLACK);
			lblDrawingNum.setFont(new Font("Tahoma", Font.BOLD, 14));
			lblDrawingNum.setForeground(Color.BLACK);
			
	//Group Layout
			
			txtCusDescrip.setColumns(10);
			txtSupDescrip.setColumns(10);
			txtCusDescrip.setColumns(10);
			txtSupDescrip.setColumns(10);
			GroupLayout groupLayout = new GroupLayout(this);
			groupLayout.setHorizontalGroup(
				groupLayout.createParallelGroup(Alignment.LEADING)
					.addGroup(groupLayout.createSequentialGroup()
						.addGap(10)
						.addComponent(lblBosal)
						.addGap(10)
						.addComponent(lblUpdatePart))
					.addGroup(groupLayout.createSequentialGroup()
						.addGap(28)
						.addComponent(lblBosalPartNum))
					.addGroup(groupLayout.createSequentialGroup()
						.addGap(28)
						.addComponent(txtFindBosal, GroupLayout.PREFERRED_SIZE, 174, GroupLayout.PREFERRED_SIZE)
						.addGap(12)
						.addComponent(btnCheck, GroupLayout.PREFERRED_SIZE, 106, GroupLayout.PREFERRED_SIZE))
					.addGroup(groupLayout.createSequentialGroup()
						.addGap(28)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
							.addComponent(cboDescrip, GroupLayout.PREFERRED_SIZE, 174, GroupLayout.PREFERRED_SIZE)
							.addComponent(lblDescription))
						.addGap(84)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
							.addComponent(txtRev, GroupLayout.PREFERRED_SIZE, 174, GroupLayout.PREFERRED_SIZE)
							.addComponent(lblRev, GroupLayout.PREFERRED_SIZE, 94, GroupLayout.PREFERRED_SIZE))
						.addGap(75)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
							.addComponent(txtDrawingNum, GroupLayout.PREFERRED_SIZE, 174, GroupLayout.PREFERRED_SIZE)
							.addComponent(lblDrawingNum, GroupLayout.PREFERRED_SIZE, 223, GroupLayout.PREFERRED_SIZE)))
					.addGroup(groupLayout.createSequentialGroup()
						.addGap(28)
						.addComponent(lblProgram, GroupLayout.PREFERRED_SIZE, 223, GroupLayout.PREFERRED_SIZE)
						.addGap(35)
						.addComponent(lblCustomerPartNum)
						.addGap(88)
						.addComponent(lblSupplierPartNum))
					.addGroup(groupLayout.createSequentialGroup()
						.addGap(28)
						.addComponent(txtProgram, GroupLayout.PREFERRED_SIZE, 174, GroupLayout.PREFERRED_SIZE)
						.addGap(84)
						.addComponent(txtCusDescrip, GroupLayout.PREFERRED_SIZE, 174, GroupLayout.PREFERRED_SIZE)
						.addGap(75)
						.addComponent(txtSupDescrip, GroupLayout.PREFERRED_SIZE, 174, GroupLayout.PREFERRED_SIZE))
					.addGroup(groupLayout.createSequentialGroup()
						.addGap(28)
						.addComponent(btnBack, GroupLayout.PREFERRED_SIZE, 155, GroupLayout.PREFERRED_SIZE)
						.addGap(116)
						.addComponent(btnDelete, GroupLayout.PREFERRED_SIZE, 155, GroupLayout.PREFERRED_SIZE)
						.addGap(109)
						.addComponent(btnSave, GroupLayout.PREFERRED_SIZE, 155, GroupLayout.PREFERRED_SIZE))
			);
			groupLayout.setVerticalGroup(
				groupLayout.createParallelGroup(Alignment.LEADING)
					.addGroup(groupLayout.createSequentialGroup()
						.addGap(27)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
							.addComponent(lblBosal)
							.addGroup(groupLayout.createSequentialGroup()
								.addGap(10)
								.addComponent(lblUpdatePart)))
						.addGap(34)
						.addComponent(lblBosalPartNum)
						.addGap(5)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
							.addGroup(groupLayout.createSequentialGroup()
								.addGap(6)
								.addComponent(txtFindBosal, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addComponent(btnCheck, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE))
						.addGap(7)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
							.addGroup(groupLayout.createSequentialGroup()
								.addGap(9)
								.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
									.addComponent(lblRev, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
									.addComponent(lblDescription))
								.addPreferredGap(ComponentPlacement.RELATED)
								.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
									.addComponent(cboDescrip, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addComponent(txtRev, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
							.addGroup(groupLayout.createSequentialGroup()
								.addGap(37)
								.addComponent(txtDrawingNum, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addComponent(lblDrawingNum, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE))
						.addGap(23)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
							.addComponent(lblProgram, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
							.addGroup(groupLayout.createSequentialGroup()
								.addGap(11)
								.addComponent(lblCustomerPartNum))
							.addGroup(groupLayout.createSequentialGroup()
								.addGap(11)
								.addComponent(lblSupplierPartNum)))
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
							.addComponent(txtProgram, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(txtCusDescrip, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(txtSupDescrip, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGap(10)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
							.addComponent(btnBack, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE)
							.addComponent(btnDelete, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE)
							.addComponent(btnSave, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE)))
			);
			setLayout(groupLayout);
			}
			
		}
	class FindPanel extends JPanel
	{
	//JLabels
		
		private JLabel lblFindPartInfo;
		private JLabel lblBosal;
		
	//JButtons
		
		private JButton btnBack;
		private JButton btnSearch;
		private JButton btnSearchAll;

	//JTextFields	
		
		private JTextField txtSearch;
		
	//JRadioButtons
		
		private JRadioButton rbtnFindBosal;
		private JRadioButton rbtnFindCus;
		private JRadioButton rbtnFindSup;
		private JRadioButton rbtnFindPro;
		private JRadioButton rbtnFindEuro;
		
	//JTable	
		
		private JTable myTable;
		private JScrollPane scrollPane;
		
		public TableModel populateTableModel(String table, String column, JSONArray temp, String queryValue){
			 
	        TableModel tableModel = null;
	      				
	        try{
	            int columnCount = con.getColumnNames(table, column, queryValue).length;
	            int rowCount = con.getRowCount(table, column, queryValue);
	            String[] temp1 = new String[columnCount];
	            String[] columnNames = new String[(columnCount - 4)];
	            String[][] data = new String[rowCount][columnCount];
	            temp1 = con.getColumnNames(table, column, queryValue);	      
	            
	            int index = 0;
	            for(int i = 0; i < columnCount; i++){
	            	if(temp1[i].equals("PartType")){i++;}
	            	if(temp1[i].equals("Material")){i++;}
	            	if(temp1[i].equals("SeqNumber")){i++;}
	            	if(temp1[i].equals("TypeDescription")){i++;}
	            	columnNames[index] = temp1[i];
	            	index++;
	            }
	            
	            for(int i = 0; i < rowCount; i++){
	                for(int j = 0; j < columnNames.length; j++){
	                	try{
	                    data[i][j] = temp.getJSONObject(i).get(columnNames[j]).toString();
	                    }catch(Exception ex){/*Ignore*/
	                        data[i][j] = "";
	                    }
	                }
	            }
	            tableModel = (new DefaultTableModel(data, columnNames));
	           
				
	        }catch(Exception ex){ex.printStackTrace();}
	       
	       
	        return tableModel;
	    }
		
		public FindPanel(final JPanel find)
		{
		//JTextFields
		
			txtSearch = new JTextField();
			txtSearch.setForeground(Color.BLACK);
			txtSearch.addMouseListener(new ContextMenuMouseListener());
			
			
		//RadioButtons	
			
			rbtnFindBosal = new JRadioButton("Bosal Part Number");
			rbtnFindBosal.setBackground(new Color(105, 105, 105));
			rbtnFindBosal.setFont(new Font("Tahoma", Font.BOLD, 14));
			rbtnFindBosal.setForeground(Color.BLACK);
			rbtnFindCus = new JRadioButton("Customer Part Number");
			rbtnFindCus.setBackground(new Color(105, 105, 105));
			rbtnFindCus.setFont(new Font("Tahoma", Font.BOLD, 14));
			rbtnFindCus.setForeground(Color.BLACK);
			rbtnFindSup = new JRadioButton("Supplier Part Number");
			rbtnFindSup.setBackground(new Color(105, 105, 105));
			rbtnFindSup.setFont(new Font("Tahoma", Font.BOLD, 14));
			rbtnFindSup.setForeground(Color.BLACK);
			rbtnFindPro = new JRadioButton("Program");
			rbtnFindPro.setBackground(new Color(105, 105, 105));
			rbtnFindPro.setFont(new Font("Tahoma", Font.BOLD, 14));
			rbtnFindPro.setForeground(Color.BLACK);
			rbtnFindEuro = new JRadioButton("Europe Part Number");
			rbtnFindEuro.setBackground(new Color(105, 105, 105));
			rbtnFindEuro.setFont(new Font("Tahoma", Font.BOLD, 14));
			rbtnFindEuro.setForeground(Color.BLACK);
			setBackground(new Color(105, 105, 105));
		
		//JLabels	
			
			lblFindPartInfo = new JLabel("Find Part Information");
			
		//JTable	
			
			scrollPane = new JScrollPane();
			scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
			myTable = new JTable(){	
				public boolean isCellEditable(int row, int column){
					return false;
				}
			};
			scrollPane.setViewportView(myTable);

			
		//Image		
				
			ImageIcon bosal = new ImageIcon(getClass().getResource("/images/bosal.jpg"));
			lblBosal = new JLabel(bosal);
		
		//RadioButtons Logic
			
			
			rbtnFindBosal.addActionListener(new ActionListener(){
				
				public void actionPerformed(ActionEvent e)
				{
					if (e.getSource() == rbtnFindBosal){
			            
			            txtSearch.setText("");
			            txtSearch.requestFocusInWindow();
			           }
			}});	
								
			rbtnFindCus.addActionListener(new ActionListener(){
				
				public void actionPerformed(ActionEvent e)
				{
					if (e.getSource() == rbtnFindCus){
						
			            txtSearch.setText("");
			            txtSearch.requestFocusInWindow();
			            }
			}});
			
			rbtnFindSup.addActionListener(new ActionListener(){
				
				public void actionPerformed(ActionEvent e)
				{
					if (e.getSource() == rbtnFindSup){
					
			            txtSearch.setText("");
			            txtSearch.requestFocusInWindow();
			           }
			}});
			
			rbtnFindPro.addActionListener(new ActionListener(){
				
				public void actionPerformed(ActionEvent e)
				{
					if (e.getSource() == rbtnFindPro){
					
			            txtSearch.setText("");
			            txtSearch.requestFocusInWindow();
			           }
			}});
			
			rbtnFindEuro.addActionListener(new ActionListener(){
				
				public void actionPerformed(ActionEvent e)
				{
					if (e.getSource() == rbtnFindEuro){
					
			            txtSearch.setText("");
			            txtSearch.requestFocusInWindow();
			           }
			}});
			rbtnFindBosal.setSelected(true);
		//JButton
				
			ImageIcon search = new ImageIcon(getClass().getResource("/images/search.jpg"));
			btnSearch = new JButton(search);
			btnSearch.addActionListener(new ActionListener() {
				
					public void actionPerformed(ActionEvent e) {
						if (e.getSource() == btnSearch)
						{
							con = new DBConnect();
							final String searchText = txtSearch.getText();
							JSONArray temp = null;
																					
							try{
								//Searches using Bosal part number to fill table 
								if(rbtnFindBosal.isSelected() == true){
									try{
										temp = (con.queryDatabase("parts list", "BosalPartNumber", searchText));
										myTable.setModel(populateTableModel("parts list", "BosalPartNumber", temp, searchText));
										}catch(Exception ex){
										JOptionPane.showMessageDialog(
												    frame,
												    "Bosal Part Number: " + searchText + " does not exist",
												    "Missing Part Number",
													JOptionPane.ERROR_MESSAGE);
								}}
								
								//Searches using Europe part number to fill table 
								if(rbtnFindEuro.isSelected() == true){
									try{
										temp = (con.queryDatabase("delta 1 parts", "DeltaPartNumber", searchText));
										myTable.setModel(populateTableModel("delta 1 parts", "DeltaPartNumber", temp, searchText));
										}catch(Exception ex){
										JOptionPane.showMessageDialog(
												    frame,
												    "European Bosal Part Number: " + searchText + " does not exist",
												    "Missing Part Number",
													JOptionPane.ERROR_MESSAGE);
								}}
							
								//Searches using supplier part number to fill table
								if(rbtnFindSup.isSelected() == true){
									try{
										temp = (con.queryDatabase("parts list", "SupPartNumber", searchText));
										myTable.setModel(populateTableModel("parts list", "SupPartNumber", temp, searchText));
										
										}catch(Exception ex){
										JOptionPane.showMessageDialog(
												    frame,
												    "Supplier Part Number: " + searchText + " does not exist",
												    "Missing Part Number",
													JOptionPane.ERROR_MESSAGE);
								}}
								//Searches using customer number to fill table				
								if(rbtnFindCus.isSelected() == true){
									try{
										temp = (con.queryDatabase("parts list", "CustPartNumber", searchText));
										myTable.setModel(populateTableModel("parts list", "CustPartNumber", temp, searchText));

										}catch(Exception ex){
										JOptionPane.showMessageDialog(
												    frame,
												    "Customer Part Number: " + searchText + " does not exist",
												    "Missing Part Number",
													JOptionPane.ERROR_MESSAGE);
								}}
								if(rbtnFindPro.isSelected() == true){
									try{
										temp = (con.queryDatabase("parts list", "Program", searchText));
										myTable.setModel(populateTableModel("parts list", "Program", temp, searchText));

										}catch(Exception ex){
										JOptionPane.showMessageDialog(
												    frame,
												    "Program: " + searchText + " does not exist",
												    "Missing Part Number",
													JOptionPane.ERROR_MESSAGE);
								}}
							}catch (Exception ex){
								ex.printStackTrace();
							}							
						}
					}
			});
			
			ImageIcon done1 = new ImageIcon(getClass().getResource("/images/back.jpg"));
			btnBack = new JButton(done1);
			btnBack.addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent e) {
					if (e.getSource() == btnBack)
					{

						setVisible(false);
						Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
						int height = screenSize.height;
						int width = screenSize.width;
						frame.setResizable(false);
						frame.setSize(width/2, height/2);
						frame.setLocationRelativeTo(null);
						frame.setSize(865, 555);
						frame.setTitle("Main Menu:");
						main.setVisible(true);
						TableModel newModel = new DefaultTableModel();
						myTable.setModel(newModel);
						txtSearch.setText("");
						rbtnFindBosal.setSelected(true);
										
					}}});

			ImageIcon searchA = new ImageIcon(getClass().getResource("/images/searchAll.jpg"));
			btnSearchAll = new JButton(searchA);
			btnSearchAll.addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent e) {
					if (e.getSource() == btnSearchAll)
					{
						con = new DBConnect();
						final String searchText = txtSearch.getText();
						JSONArray temp = null;
													
						try{
							//Searches using Bosal part number to fill table 
							try{
								temp = (con.queryAllParts(searchText));
								myTable.setModel(populateTableModel("parts list", "All", temp, searchText));
								
								}catch(Exception ex){
								JOptionPane.showMessageDialog(
										    frame,
										    ": " + searchText + " does not exist",
										    "Missing Part Number",
											JOptionPane.ERROR_MESSAGE);
								}
						}catch (Exception ex){
							ex.printStackTrace();
						}								
				}}});
			
			ButtonGroup group = new ButtonGroup();
			group.add(rbtnFindBosal);
			group.add(rbtnFindCus);
			group.add(rbtnFindSup);
			group.add(rbtnFindPro);
			group.add(rbtnFindEuro);
			setupPanel();
			
		}
		
		private void setupPanel()
		
		{
			
	//Label Fonts		
			
			lblFindPartInfo.setFont(new Font("EucrosiaUPC", Font.BOLD, 64));
			lblFindPartInfo.setForeground(Color.BLACK);
			GroupLayout groupLayout = new GroupLayout(this);
			groupLayout.setHorizontalGroup(
				groupLayout.createParallelGroup(Alignment.LEADING)
					.addGroup(groupLayout.createSequentialGroup()
						.addGap(10)
						.addComponent(lblBosal)
						.addGap(10)
						.addComponent(lblFindPartInfo))
					.addGroup(groupLayout.createSequentialGroup()
						.addGap(239)
						.addComponent(rbtnFindBosal, GroupLayout.PREFERRED_SIZE, 161, GroupLayout.PREFERRED_SIZE)
						.addGap(2)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
							.addComponent(rbtnFindEuro, GroupLayout.PREFERRED_SIZE, 183, GroupLayout.PREFERRED_SIZE)
							.addGroup(groupLayout.createSequentialGroup()
								.addGap(178)
								.addComponent(rbtnFindCus, GroupLayout.PREFERRED_SIZE, 194, GroupLayout.PREFERRED_SIZE)))
						.addGap(2)
						.addComponent(rbtnFindSup, GroupLayout.PREFERRED_SIZE, 184, GroupLayout.PREFERRED_SIZE)
						.addGap(2)
						.addComponent(rbtnFindPro, GroupLayout.PREFERRED_SIZE, 184, GroupLayout.PREFERRED_SIZE))
					.addGroup(groupLayout.createSequentialGroup()
						.addGap(528)
						.addComponent(txtSearch, GroupLayout.PREFERRED_SIZE, 232, GroupLayout.PREFERRED_SIZE))
					.addGroup(groupLayout.createSequentialGroup()
						.addGap(33)
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 1225, GroupLayout.PREFERRED_SIZE))
					.addGroup(groupLayout.createSequentialGroup()
						.addGap(33)
						.addComponent(btnBack, GroupLayout.PREFERRED_SIZE, 155, GroupLayout.PREFERRED_SIZE)
						.addGap(382)
						.addComponent(btnSearchAll, GroupLayout.PREFERRED_SIZE, 155, GroupLayout.PREFERRED_SIZE)
						.addGap(380)
						.addComponent(btnSearch, GroupLayout.PREFERRED_SIZE, 155, GroupLayout.PREFERRED_SIZE))
			);
			groupLayout.setVerticalGroup(
				groupLayout.createParallelGroup(Alignment.LEADING)
					.addGroup(groupLayout.createSequentialGroup()
						.addGap(11)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
							.addComponent(lblBosal)
							.addGroup(groupLayout.createSequentialGroup()
								.addGap(17)
								.addComponent(lblFindPartInfo)))
						.addGap(18)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
							.addComponent(rbtnFindBosal, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
							.addComponent(rbtnFindEuro, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
							.addComponent(rbtnFindCus, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
							.addComponent(rbtnFindSup, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
							.addComponent(rbtnFindPro, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE))
						.addGap(20)
						.addComponent(txtSearch, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGap(27)
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
						.addGap(23)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
							.addComponent(btnBack, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE)
							.addComponent(btnSearchAll, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE)
							.addComponent(btnSearch, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE)))
			);
			setLayout(groupLayout);
	}	
}
	class SettingsPanel extends JPanel
	{
	//JTextField	
		
		private JTextField txtIP;
		private JTextField txtUser;
		private JTextField txtDataBase;
		private JTextField txtPort;
		
	//JPasswordField
		
		private JPasswordField ptxtPass;
	
	//JLabel
		
		private JLabel lblIP;
		private JLabel lblUser;
		private JLabel lblPass;
		private JLabel lblDataBase;
		private JLabel lblTitle;
		private JLabel lblBosal;
		private JLabel lblPort;
		
	//JButton
		
		private JButton btnSave;
		private JButton btnBack;
		
		
		public SettingsPanel(JPanel settings)
		{		
			try {
				ConfigurationManager config = new ConfigurationManager(configFilePath);
				setBackground(new Color(105, 105, 105));
				txtIP = new JTextField(config.getProperty("host"));
				txtIP.setForeground(Color.BLACK);
				txtIP.addMouseListener(new ContextMenuMouseListener());
				txtUser = new JTextField(config.getProperty("appUser"));
				txtUser.setForeground(Color.BLACK);
				txtUser.addMouseListener(new ContextMenuMouseListener());
				ptxtPass = new JPasswordField(config.getProperty("appPassword"));
				ptxtPass.setForeground(Color.BLACK);
				ptxtPass.addMouseListener(new ContextMenuMouseListener());
				txtDataBase = new JTextField(config.getProperty("database"));
				txtDataBase.setForeground(Color.BLACK);
				txtDataBase.addMouseListener(new ContextMenuMouseListener());
				txtPort = new JTextField(config.getProperty("port"));
				txtPort.setForeground(Color.BLACK);
				txtPort.addMouseListener(new ContextMenuMouseListener());
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			 lblPort = new JLabel("Port:");
			 lblIP = new JLabel("IP Address:");
			 lblUser = new JLabel("UserName:");
			 lblPass = new JLabel("Password:");
			 lblDataBase = new JLabel("Database Name:");
			 lblTitle = new JLabel("Settings:");
			
			 ImageIcon save = new ImageIcon(getClass().getResource("/images/save.jpg"));
			 btnSave = new JButton(save);
			 btnSave.addActionListener(new ActionListener() {
	
					public void actionPerformed(ActionEvent e) {
						if (e.getSource() == btnSave)
						{
							int n = JOptionPane.showConfirmDialog(
								    frame,
								    "Are you sure you want to save settings?",
								    "Save:",
								    JOptionPane.YES_NO_OPTION,
									JOptionPane.WARNING_MESSAGE);
							if(n == 0){
								try {
									ConfigurationManager config = new ConfigurationManager(configFilePath);
									config.setProperty("host", txtIP.getText());
									config.setProperty("port", txtPort.getText());
									config.setProperty("database", txtDataBase.getText());
									config.setProperty("appUser", txtUser.getText());
									config.setProperty("appPassword", new String(ptxtPass.getPassword()));
									config.save();
									//config.printProperties(config);
									
									setVisible(false);
									Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
									int height = screenSize.height;
									int width = screenSize.width;
									frame.setResizable(false);
									frame.setSize(width/2, height/2);
									frame.setLocationRelativeTo(null);
									frame.setSize(865, 555);
									frame.setTitle("Main Menu:");
									main.setVisible(true);
									
								}catch(Exception ex){
									ex.printStackTrace();
								}
							}
						}}});
				add(btnSave);
			 
			 ImageIcon back = new ImageIcon(getClass().getResource("/images/back.jpg"));
			 btnBack = new JButton(back);
			 btnBack.addActionListener(new ActionListener() {
					
					public void actionPerformed(ActionEvent e) {
						if (e.getSource() == btnBack)
						{
							setVisible(false);
							Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
							int height = screenSize.height;
							int width = screenSize.width;
							frame.setResizable(false);
							frame.setSize(width/2, height/2);
							frame.setLocationRelativeTo(null);
							frame.setSize(865, 555);
							frame.setTitle("Main Menu:");
							main.setVisible(true);
							
						}}});
				add(btnBack);
			 
			 ImageIcon bosal = new ImageIcon(getClass().getResource("/images/bosal.jpg"));
			 lblBosal = new JLabel(bosal);
			 
			 lblIP.setFont(new Font("Tahoma", Font.BOLD, 14));
			 lblIP.setForeground(Color.BLACK);
			 lblUser.setFont(new Font("Tahoma", Font.BOLD, 14));
			 lblUser.setForeground(Color.BLACK);
			 lblPass.setFont(new Font("Tahoma", Font.BOLD, 14));
			 lblPass.setForeground(Color.BLACK);
			 lblDataBase.setFont(new Font("Tahoma", Font.BOLD, 14));
			 lblDataBase.setForeground(Color.BLACK);
			 lblTitle.setFont(new Font("EucrosiaUPC", Font.BOLD, 64));
			 lblTitle.setForeground(Color.BLACK);
			 lblPort.setForeground(Color.BLACK);
			 lblPort.setFont(new Font("Tahoma", Font.BOLD, 14));
			 
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
						.addGap(18)
						.addComponent(lblTitle))
					.addGroup(groupLayout.createSequentialGroup()
						.addGap(123)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
							.addComponent(lblIP)
							.addGroup(groupLayout.createSequentialGroup()
								.addGap(46)
								.addComponent(lblPort)))
						.addGap(6)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
							.addComponent(txtIP, GroupLayout.PREFERRED_SIZE, 169, GroupLayout.PREFERRED_SIZE)
							.addComponent(txtDataBase, GroupLayout.PREFERRED_SIZE, 169, GroupLayout.PREFERRED_SIZE))
						.addGap(37)
						.addComponent(btnSave, GroupLayout.PREFERRED_SIZE, 155, GroupLayout.PREFERRED_SIZE))
					.addGroup(groupLayout.createSequentialGroup()
						.addGap(128)
						.addComponent(lblUser)
						.addGap(6)
						.addComponent(txtUser, GroupLayout.PREFERRED_SIZE, 169, GroupLayout.PREFERRED_SIZE))
					.addGroup(groupLayout.createSequentialGroup()
						.addGap(91)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
							.addGroup(groupLayout.createSequentialGroup()
								.addGap(41)
								.addComponent(lblPass))
							.addComponent(lblDataBase))
						.addGap(6)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
							.addComponent(ptxtPass, GroupLayout.PREFERRED_SIZE, 169, GroupLayout.PREFERRED_SIZE)
							.addComponent(txtPort, GroupLayout.PREFERRED_SIZE, 169, GroupLayout.PREFERRED_SIZE))
						.addGap(37)
						.addComponent(btnBack, GroupLayout.PREFERRED_SIZE, 155, GroupLayout.PREFERRED_SIZE))
			);
			groupLayout.setVerticalGroup(
				groupLayout.createParallelGroup(Alignment.LEADING)
					.addGroup(groupLayout.createSequentialGroup()
						.addGap(10)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
							.addComponent(lblBosal)
							.addGroup(groupLayout.createSequentialGroup()
								.addGap(15)
								.addComponent(lblTitle)))
						.addGap(53)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
							.addGroup(groupLayout.createSequentialGroup()
								.addComponent(lblIP)
								.addGap(20)
								.addComponent(lblPort))
							.addGroup(groupLayout.createSequentialGroup()
								.addComponent(txtIP, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addGap(17)
								.addComponent(txtPort, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addComponent(btnSave, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE))
						.addGap(19)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
							.addComponent(lblUser)
							.addComponent(txtUser, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGap(19)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
							.addGroup(groupLayout.createSequentialGroup()
								.addComponent(lblPass)
								.addGap(22)
								.addComponent(lblDataBase))
							.addGroup(groupLayout.createSequentialGroup()
								.addComponent(ptxtPass, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addGap(19)
								.addComponent(txtDataBase, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGroup(groupLayout.createSequentialGroup()
								.addGap(11)
								.addComponent(btnBack, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE))))
			);
			setLayout(groupLayout);
		}
	}
	class ManagePanel extends JPanel
	{
	//JLabels	
		private JLabel lblbosal;
		private JLabel lblmanageUsers;
		private JLabel lblUsername;
		private JLabel lblPassword;
		private JLabel lblPassword2;
		private JLabel lblRank;
		private JLabel lblPassConfirm;	
		private JLabel lblAddProgram;
		private JLabel lblAddCustomer;
		private JLabel lblProStart;
		private JLabel lblProEnd;
		private JLabel lblCust;
		private JLabel lblCustomer;
		
	//JTextFields

		private JTextField txtUsername;
		private JTextField txtAddCusPro;
		private JTextField txtProStart;
		private JTextField txtProEnd;
		private JTextField txtCust;
		
	//JPasswordFields
		
		private JPasswordField txtPassword;
		private JPasswordField txtConfirmPassword;
		
	//JComboBoxes

		private JComboBox<?> cboUserRank;
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
			}catch(Exception ex){ex.printStackTrace();/*Ignore*/}
			return CustComboBoxDefault;
		}
			
	//JButtons
		
		private JButton btnBack;
		private JButton btnSave;
		private JButton btnDelete;
		
	//JRadioButtons
		
		private JRadioButton rbtnDeleteUser;
		private JRadioButton rbtnCreateUser;
		private JRadioButton rbtnChangeUserRank;
		private JRadioButton rbtnChangePass;
		private JRadioButton rbtnAddProgram;
		private JRadioButton rbtnAddCustomer;
		
	//Password comparison returning true/false
		
		public boolean comparePasswords(String password, String confirmPassword)
		{
			System.out.println(password+":"+confirmPassword);
			boolean status = false;		
			if(password.equals("") || confirmPassword.equals("")){
				System.out.println("empty passwords");
			}
			if(password.equals(confirmPassword) && !password.equals("") && !confirmPassword.equals("")){
				System.out.println("Passwords confirmed");
				status = true;
			}else{System.out.println("Passwords Do Not Match");}
			return status;
		}
		
	//String Panel
		
		@SuppressWarnings("unused")
		public ManagePanel(final JPanel manage)
		{
		// Bosal Image	
		
			ImageIcon bosal = new ImageIcon(getClass().getResource("/images/bosal.jpg"));
			lblbosal = new JLabel(bosal);
			setBackground(new Color(105, 105, 105));
			
			ImageIcon checkmark = new ImageIcon(getClass().getResource("/images/checkmark.jpg"));
			ImageIcon xmark = new ImageIcon(getClass().getResource("/images/xmark.jpg"));
				
		//Labels	
			
			lblmanageUsers = new JLabel("Manage");
			lblmanageUsers.setVisible(true);
			lblmanageUsers.setFont(new Font("EucrosiaUPC", Font.BOLD, 64));
			lblmanageUsers.setForeground(Color.BLACK);
			lblUsername = new JLabel("Username:");
			lblUsername.setVisible(true);
			lblUsername.setFont(new Font("Tahoma", Font.BOLD, 14));
			lblUsername.setForeground(Color.BLACK);
			lblPassword = new JLabel("Password:");
			lblPassword.setVisible(true);
			lblPassword.setFont(new Font("Tahoma", Font.BOLD, 14));
			lblPassword.setForeground(Color.BLACK);
			lblPassword2 = new JLabel("Confirm Password:");
			lblPassword2.setVisible(true);
			lblPassword2.setFont(new Font("Tahoma", Font.BOLD, 14));
			lblPassword2.setForeground(Color.BLACK);
			lblRank = new JLabel("User Rank:");
			lblRank.setVisible(true);
			lblRank.setFont(new Font("Tahoma", Font.BOLD, 14));
			lblRank.setForeground(Color.BLACK);
			lblPassConfirm = new JLabel("");
			lblAddProgram = new JLabel("Enter New Program:");
			lblAddProgram.setVisible(false);
			lblAddProgram.setFont(new Font("Tahoma", Font.BOLD, 14));
			lblAddProgram.setForeground(Color.BLACK);
			lblAddCustomer = new JLabel("Enter New Customer:");
			lblAddCustomer.setVisible(false);
			lblAddCustomer.setFont(new Font("Tahoma", Font.BOLD, 14));
			lblAddCustomer.setForeground(Color.BLACK);
			lblProStart = new JLabel("Program Start:");
			lblProStart.setVisible(false);
			lblProStart.setFont(new Font("Tahoma", Font.BOLD, 14));
			lblProStart.setForeground(Color.BLACK);
			lblProEnd = new JLabel("Program End:");
			lblProEnd.setVisible(false);
			lblProEnd.setFont(new Font("Tahoma", Font.BOLD, 14));
			lblProEnd.setForeground(Color.BLACK);
			lblCust = new JLabel("Customer Abrv:");
			lblCust.setVisible(false);
			lblCust.setFont(new Font("Tahoma", Font.BOLD, 14));
			lblCust.setForeground(Color.BLACK);
			lblCustomer = new JLabel("Customer:");
			lblCustomer.setVisible(false);
			lblCustomer.setFont(new Font("Tahoma", Font.BOLD, 14));
			lblCustomer.setForeground(Color.BLACK);
			
		//TextFields	
			
			txtUsername = new JTextField();
			txtUsername.setVisible(true);
			txtUsername.setForeground(Color.BLACK);
			txtUsername.addMouseListener(new ContextMenuMouseListener());
			txtPassword = new JPasswordField();
			txtPassword.setVisible(true);
			txtPassword.addMouseListener(new ContextMenuMouseListener());
			txtPassword.setForeground(Color.BLACK);
			txtConfirmPassword = new JPasswordField();
			txtConfirmPassword.setVisible(true);
			txtConfirmPassword.addMouseListener(new ContextMenuMouseListener());
			txtConfirmPassword.setForeground(Color.BLACK);
			txtAddCusPro = new JTextField();
			txtAddCusPro.setVisible(false);
			txtAddCusPro.addMouseListener(new ContextMenuMouseListener());
			txtAddCusPro.setForeground(Color.BLACK);
			txtProStart = new JTextField();
			txtProStart.setVisible(false);
			txtProStart.addMouseListener(new ContextMenuMouseListener());
			txtProStart.setForeground(Color.BLACK);
			txtProEnd = new JTextField();
			txtProEnd.setVisible(false);
			txtProEnd.addMouseListener(new ContextMenuMouseListener());
			txtProEnd.setForeground(Color.BLACK);
			txtCust = new JTextField();
			txtCust.setVisible(false);
			txtCust.addMouseListener(new ContextMenuMouseListener());
			txtCust.setForeground(Color.BLACK);
			
		//ComboBox
			
			String[] ranks = {"admin","gui","engineer", "default"};
			cboUserRank = new JComboBox<Object>(ranks);
			cboUserRank.addMouseListener(new ContextMenuMouseListener());
			cboCustomer = new JComboBox<String>();
			cboCustomer.setModel(resetCustomerComboBox());
			cboCustomer.setVisible(false);
			cboCustomer.addMouseListener(new ContextMenuMouseListener());
			
		//RadioButton
			
			ItemListener passwordListener = (new ItemListener(){
				
				public void itemStateChanged(ItemEvent e){
					if(rbtnCreateUser.isSelected() == true || rbtnChangePass.isSelected() == true){
						txtConfirmPassword.addKeyListener(new KeyAdapter(){
							
							public void keyReleased(KeyEvent e)
							{
								String password = (new String (txtPassword.getPassword()));
								String confirmPassword = (new String (txtConfirmPassword.getPassword()));
								if(comparePasswords(password, confirmPassword) == false){
									lblPassConfirm.setText("Passwords Do Not Match");
									lblPassConfirm.setForeground(new Color(204, 0, 0));
								}else if(comparePasswords(password, confirmPassword) == true){
									lblPassConfirm.setText("Confirmed");
									lblPassConfirm.setForeground(new Color(154, 205, 50));
								}else{
									lblPassConfirm.setText("");
								}}});
			}}});
			
			rbtnAddProgram = new JRadioButton("Add Program");
			rbtnAddProgram.setBackground(new Color(105, 105, 105));
			rbtnAddProgram.setFont(new Font("Tahoma", Font.BOLD, 14));
			rbtnAddProgram.setForeground(Color.BLACK);
			rbtnAddProgram.addActionListener(new ActionListener(){
				
				public void actionPerformed(ActionEvent e)
				{		
					if (e.getSource() == rbtnAddProgram){
						cboCustomer.setModel(resetCustomerComboBox());
			            lblUsername.setVisible(false);
			            lblPassword.setVisible(false);
			            lblPassword2.setVisible(false);
			            lblProStart.setVisible(true);
			            lblProEnd.setVisible(true);
			            lblRank.setVisible(false);
			            lblCust.setVisible(false);
			            lblCustomer.setVisible(true);
			            cboCustomer.setVisible(true);
			            cboUserRank.setVisible(false);
			            txtConfirmPassword.setVisible(false);
			            txtPassword.setVisible(false);
			            txtUsername.setVisible(false);
			            txtProStart.setVisible(true);
			            txtProEnd.setVisible(true);
			            txtCust.setVisible(false);
			            lblAddCustomer.setVisible(false);
			            lblAddProgram.setVisible(true);
			            txtAddCusPro.setVisible(true);
			            btnDelete.setVisible(true);
			            btnSave.setVisible(true);
			            
					}						
			}});
			
			rbtnAddCustomer = new JRadioButton("Add Customer");
			rbtnAddCustomer.setBackground(new Color(105, 105, 105));
			rbtnAddCustomer.setFont(new Font("Tahoma", Font.BOLD, 14));
			rbtnAddCustomer.setForeground(Color.BLACK);
			rbtnAddCustomer.addActionListener(new ActionListener(){
				
				public void actionPerformed(ActionEvent e)
				{		
					if (e.getSource() == rbtnAddCustomer){
						lblUsername.setVisible(false);
			            lblPassword.setVisible(false);
			            lblPassword2.setVisible(false);
			            lblRank.setVisible(false);
			            lblProStart.setVisible(false);
			            lblProEnd.setVisible(false);
			            lblCust.setVisible(true);
			            lblCustomer.setVisible(false);
			            cboCustomer.setVisible(false);
			            cboUserRank.setVisible(false);
			            txtConfirmPassword.setVisible(false);
			            txtPassword.setVisible(false);
			            txtProStart.setVisible(false);
			            txtProEnd.setVisible(false);
			            txtUsername.setVisible(false);
			            txtCust.setVisible(true);
			            lblAddProgram.setVisible(false);
			            lblAddCustomer.setVisible(true);
			            txtAddCusPro.setVisible(true);
			            btnDelete.setVisible(true);
			            btnSave.setVisible(true);
					}						
			}});
			
			rbtnDeleteUser = new JRadioButton("Delete User");
			rbtnDeleteUser.setBackground(new Color(105, 105, 105));
			rbtnDeleteUser.setFont(new Font("Tahoma", Font.BOLD, 14));
			rbtnDeleteUser.setForeground(Color.BLACK);
			rbtnDeleteUser.addActionListener(new ActionListener(){
				
				public void actionPerformed(ActionEvent e)
				{		
					if (e.getSource() == rbtnDeleteUser){
						lblUsername.setVisible(true);
			            lblPassword.setVisible(true);
			            lblPassword2.setVisible(true);
			            lblRank.setVisible(true);
			            lblProStart.setVisible(false);
			            lblProEnd.setVisible(false);
			            lblCust.setVisible(false);
			            lblCustomer.setVisible(false);
			            cboCustomer.setVisible(false);
			            cboUserRank.setVisible(true);
			            txtConfirmPassword.setVisible(true);
			            txtPassword.setVisible(true);
			            txtUsername.setVisible(true);
			            lblAddProgram.setVisible(true);
			            lblAddProgram.setVisible(false);
			            lblAddCustomer.setVisible(false);
			            txtAddCusPro.setVisible(false);
			            btnDelete.setVisible(true);
			            btnSave.setVisible(false);
			            txtProStart.setVisible(false);
			            txtProEnd.setVisible(false);
			            txtUsername.setEditable(true);
			            txtCust.setVisible(false);
			            txtUsername.setBackground(Color.white);
			            txtPassword.setText("");
			            txtPassword.setEditable(false);
			            txtPassword.setBackground(new Color(190, 190, 190));
			            txtConfirmPassword.setText("");
			            txtConfirmPassword.setEditable(false);
			            txtConfirmPassword.setBackground(new Color(190, 190, 190));
			            cboUserRank.setEditable(false);
			            cboUserRank.setBackground(new Color(190, 190, 190));
			            lblPassConfirm.setText("");
					}						
			}});
			rbtnDeleteUser.addItemListener(passwordListener);
			
			rbtnCreateUser = new JRadioButton("Create User");
			rbtnCreateUser.setSelected(true);
			rbtnCreateUser.setBackground(new Color(105, 105, 105));
			rbtnCreateUser.setFont(new Font("Tahoma", Font.BOLD, 14));
			rbtnCreateUser.setForeground(Color.BLACK);
			rbtnCreateUser.addActionListener(new ActionListener(){
				
				public void actionPerformed(ActionEvent e) 
				{
					if (e.getSource() == rbtnCreateUser){
						lblUsername.setVisible(true);
			            lblPassword.setVisible(true);
			            lblPassword2.setVisible(true);
			            lblRank.setVisible(true);
			            lblProStart.setVisible(false);
			            lblProEnd.setVisible(false);
			            lblCust.setVisible(false);
			            lblCustomer.setVisible(false);
			            cboCustomer.setVisible(false);
			            cboUserRank.setVisible(true);
			            txtConfirmPassword.setVisible(true);
			            txtPassword.setVisible(true);
			            txtUsername.setVisible(true);
			            lblAddProgram.setVisible(true);
			            lblAddProgram.setVisible(false);
			            lblAddCustomer.setVisible(false);
			            txtAddCusPro.setVisible(false);
			            btnDelete.setVisible(false);
			            btnSave.setVisible(true);
			            txtProStart.setVisible(false);
			            txtCust.setVisible(false);
			            txtProEnd.setVisible(false);
			            txtUsername.setEditable(true);
			            txtUsername.setBackground(Color.white);
			            txtPassword.setEditable(true);
			            txtPassword.setBackground(Color.white);
			            txtConfirmPassword.setEditable(true);
			            txtConfirmPassword.setBackground(Color.white);
			            cboUserRank.setEditable(true);
			            cboUserRank.setBackground(Color.white);
			            lblPassConfirm.setText("");
					}
			}});
			rbtnCreateUser.addItemListener(passwordListener);
			rbtnCreateUser.setSelected(true);
			
			rbtnChangeUserRank = new JRadioButton("Changer User Rank");
			rbtnChangeUserRank.setBackground(new Color(105, 105, 105));
			rbtnChangeUserRank.setFont(new Font("Tahoma", Font.BOLD, 14));
			rbtnChangeUserRank.setForeground(Color.BLACK);
			rbtnChangeUserRank.addActionListener(new ActionListener(){
				
				public void actionPerformed(ActionEvent e) 
				{
					if (e.getSource() == rbtnChangeUserRank){
						lblUsername.setVisible(true);
			            lblPassword.setVisible(true);
			            lblPassword2.setVisible(true);
			            lblProStart.setVisible(false);
			            lblProEnd.setVisible(false);
			            lblRank.setVisible(true);
			            lblCust.setVisible(false);
			            lblCustomer.setVisible(false);
			            cboCustomer.setVisible(false);
			            cboUserRank.setVisible(true);
			            txtConfirmPassword.setVisible(true);
			            txtPassword.setVisible(true);
			            txtUsername.setVisible(true);
			            lblAddProgram.setVisible(true);
			            lblAddProgram.setVisible(false);
			            lblAddCustomer.setVisible(false);
			            txtAddCusPro.setVisible(false);
			            btnDelete.setVisible(false);
			            btnSave.setVisible(true);
			            txtUsername.setEditable(true);
			            txtCust.setVisible(false);
			            txtUsername.setBackground(Color.white);
			            txtPassword.setText("");
			            txtPassword.setEditable(false);
			            txtProStart.setVisible(false);
			            txtProEnd.setVisible(false);
			            txtPassword.setBackground(new Color(190, 190, 190));
			            txtConfirmPassword.setText("");
			            txtConfirmPassword.setEditable(false);
			            txtConfirmPassword.setBackground(new Color(190, 190, 190));
			            cboUserRank.setEditable(true);
			            cboUserRank.setBackground(Color.white);
			            lblPassConfirm.setText("");
					}
			}});
			rbtnChangeUserRank.addItemListener(passwordListener);
			
			rbtnChangePass = new JRadioButton("Change Password");
			rbtnChangePass.setBackground(new Color(105, 105, 105));
			rbtnChangePass.setFont(new Font("Tahoma", Font.BOLD, 14));
			rbtnChangePass.setForeground(Color.BLACK);
			rbtnChangePass.addActionListener(new ActionListener(){
				
				public void actionPerformed(ActionEvent e) 
				{
					if (e.getSource() == rbtnChangePass){
						lblUsername.setVisible(true);
			            lblPassword.setVisible(true);
			            lblPassword2.setVisible(true);
			            lblProStart.setVisible(false);
			            lblProEnd.setVisible(false);
			            lblRank.setVisible(true);
			            lblCust.setVisible(false);
			            lblCustomer.setVisible(false);
			            cboCustomer.setVisible(false);
			            cboUserRank.setVisible(true);
			            txtConfirmPassword.setVisible(true);
			            txtPassword.setVisible(true);
			            txtUsername.setVisible(true);
			            lblAddProgram.setVisible(true);
			            lblAddProgram.setVisible(false);
			            lblAddCustomer.setVisible(false);
			            txtAddCusPro.setVisible(false);
			            btnDelete.setVisible(false);
			            btnSave.setVisible(true);
			            txtProStart.setVisible(false);
			            txtCust.setVisible(false);
			            txtProEnd.setVisible(false);
			            txtUsername.setEditable(true);
			            txtUsername.setBackground(Color.white);
			            txtPassword.setEditable(true);
			            txtPassword.setBackground(Color.white);
			            txtConfirmPassword.setEditable(true);
			            txtConfirmPassword.setBackground(Color.white);
			            cboUserRank.setEditable(false);
			            cboUserRank.setBackground(new Color(190, 190, 190));
			            lblPassConfirm.setText("");
					}
			}});
			rbtnChangePass.addItemListener(passwordListener);
			
			ButtonGroup group = new ButtonGroup();
			
			group.add(rbtnDeleteUser);
			group.add(rbtnCreateUser);
			group.add(rbtnChangeUserRank);
			group.add(rbtnChangePass);
			group.add(rbtnAddProgram);
			group.add(rbtnAddCustomer);
				
		//Buttons	
			
			ImageIcon back = new ImageIcon(getClass().getResource("/images/back.jpg"));
			btnBack = new JButton(back);
			btnBack.addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent e)
				{
					if (e.getSource() == btnBack)
					{
						lblUsername.setVisible(true);
			            lblPassword.setVisible(true);
			            lblPassword2.setVisible(true);
			            lblRank.setVisible(true);
			            lblProStart.setVisible(false);
			            lblProEnd.setVisible(false);
			            lblCust.setVisible(false);
			            lblCustomer.setVisible(false);
			            cboCustomer.setVisible(false);
			            cboUserRank.setVisible(true);
			            txtConfirmPassword.setVisible(true);
			            txtPassword.setVisible(true);
			            txtUsername.setVisible(true);
			            lblAddProgram.setVisible(true);
			            lblAddProgram.setVisible(false);
			            lblAddCustomer.setVisible(false);
			            txtAddCusPro.setVisible(false);
			            btnDelete.setVisible(false);
			            btnSave.setVisible(true);
			            rbtnCreateUser.setSelected(true);
			            txtUsername.setEditable(true);
			            txtUsername.setBackground(Color.white);
			            txtPassword.setEditable(true);
			            txtProStart.setVisible(false);
			            txtProEnd.setVisible(false);
			            txtCust.setVisible(false);
			            txtPassword.setBackground(Color.white);
			            txtConfirmPassword.setEditable(true);
			            txtConfirmPassword.setBackground(Color.white);
			            cboUserRank.setEditable(true);
			            cboUserRank.setBackground(Color.white);
			            lblPassConfirm.setText("");
						setVisible(false);
						Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
						int height = screenSize.height;
						int width = screenSize.width;
						frame.setResizable(false);
						frame.setSize(width/2, height/2);
						frame.setLocationRelativeTo(null);
						frame.setSize(865, 555);
						frame.setTitle("Main Menu:");
						main.setVisible(true);
						txtUsername.setText("");
						txtPassword.setText("");
						txtConfirmPassword.setText("");
			}}});
			
			ImageIcon delete = new ImageIcon(getClass().getResource("/images/delete.jpg"));
			btnDelete = new JButton(delete);
			btnDelete.setVisible(false);
			btnDelete.addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent e)
				{
					int n = 1;
					if (e.getSource() == btnDelete)
					{
						if(rbtnDeleteUser.isSelected() == true){
							if(txtUsername.getText().equals("")){
								JOptionPane.showMessageDialog(
									    frame,
									    "Please Enter A Username",
									    "Creditenials Error",
										JOptionPane.ERROR_MESSAGE);
							}else{
								try{
									if(con.getUserRank().equals("admin")){
										n = JOptionPane.showConfirmDialog(
												    frame,
												    "Are you sure you want to delete user: " + txtUsername.getText() + "?",
												    "Save:",
												    JOptionPane.YES_NO_OPTION,
													JOptionPane.WARNING_MESSAGE);
									}else{
										config = new ConfigurationManager(configFilePath);
										JOptionPane.showMessageDialog(
										    frame,
										    "" + (config.getProperty("appUser") 
										    		+ " does not have permission to Delete Users"),
										    "Creditenials Error",
											JOptionPane.ERROR_MESSAGE);
									}
								}catch(Exception ex){/*Ignore*/}
							}
						}
						if(n == 0){
							try{
								String username = txtUsername.getText();
						if(rbtnDeleteUser.isSelected() == true){
							con.deleteUser(username);
						}
							}catch(Exception ex){/*Ignore*/}
							txtUsername.setText("");
					}}}});
			
			ImageIcon create = new ImageIcon(getClass().getResource("/images/save.jpg"));
			btnSave = new JButton(create);
			btnSave.addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent e) 
				{
					
						if (e.getSource() == btnSave){
							int n = 1;
							if(rbtnCreateUser.isSelected() == true){
								if(txtUsername.getText().equals("")){
									JOptionPane.showMessageDialog(
										    frame,
										    "Please Enter A Username",
										    "Creditenials Error",
											JOptionPane.ERROR_MESSAGE);
								}else{
									try{
										if(con.getUserRank().equals("admin")){
											n = JOptionPane.showConfirmDialog(
													    frame,
													    "Are you sure you want to create user: " + txtUsername.getText() + "?",
													    "Save:",
													    JOptionPane.YES_NO_OPTION,
														JOptionPane.WARNING_MESSAGE);
										}else{
											config = new ConfigurationManager(configFilePath);
											JOptionPane.showMessageDialog(
											    frame,
											    "" + (config.getProperty("appUser") 
											    		+ " does not have permission to Create Users"),
											    "Creditenials Error",
												JOptionPane.ERROR_MESSAGE);
										}
									}catch(Exception ex){/*Ignore*/}
								}
							}
							if(rbtnChangeUserRank.isSelected() == true){
								if(txtUsername.getText().equals("")){
									JOptionPane.showMessageDialog(
										    frame,
										    "Please Enter A Username",
										    "Creditenials Error",
											JOptionPane.ERROR_MESSAGE);
								}else{
									try{
										if(con.getUserRank().equals("admin")){
											n = JOptionPane.showConfirmDialog(
													    frame,
													    "Are you sure you want to change " 
													    		+ txtUsername.getText() + "'s rank to " 
													    		+ cboUserRank.getSelectedItem().toString()+"?",
													    "Save:",
													    JOptionPane.YES_NO_OPTION,
														JOptionPane.WARNING_MESSAGE);
										}else{
											config = new ConfigurationManager(configFilePath);
											JOptionPane.showMessageDialog(
											    frame,
											    "" + (config.getProperty("appUser") 
											    		+ " does not have permission to Change User Rank"),
											    "Creditenials Error",
												JOptionPane.ERROR_MESSAGE);
										}
									}catch(Exception ex){/*Ignore*/}
								}
							}
							if(rbtnChangePass.isSelected() == true){
								if(txtUsername.getText().equals("")){
									JOptionPane.showMessageDialog(
										    frame,
										    "Please Enter A Username",
										    "Creditenials Error",
											JOptionPane.ERROR_MESSAGE);
								}else{
									n = JOptionPane.showConfirmDialog(
										    frame,
										    "Are you sure you want to change " + txtUsername.getText() + "'s password?",
										    "Save:",
										    JOptionPane.YES_NO_OPTION,
											JOptionPane.WARNING_MESSAGE);
								}
							}
							if(rbtnAddProgram.isSelected() == true){
								if(txtAddCusPro.getText().equals("")){
									JOptionPane.showMessageDialog(
											frame,
											"Please Enter a Program",
											"Creditenial Error",
											JOptionPane.ERROR_MESSAGE);
								}else{
									n = JOptionPane.showConfirmDialog(
											frame,
											"Are you sure you want to create " + txtAddCusPro.getText() + " as a new program?",
											"Save:",
											JOptionPane.YES_NO_OPTION,
											JOptionPane.WARNING_MESSAGE);
								}
							}
							if(rbtnAddCustomer.isSelected() == true){
								if(txtAddCusPro.getText().equals("")){
									JOptionPane.showMessageDialog(
											frame,
											"Please Enter a Customer",
											"Creditential Error",
											JOptionPane.ERROR_MESSAGE);
								}else{
									n = JOptionPane.showConfirmDialog(
											frame,
											"Are you sure you want to create " + txtAddCusPro.getText() + " as a new Customer?",
											"Save",
											JOptionPane.YES_NO_OPTION,
											JOptionPane.WARNING_MESSAGE);
								}
							}
								if(n == 0){
									try{
										String username = txtUsername.getText();
										String password = (new String(txtPassword.getPassword()));
										String confirmPassword = (new String(txtConfirmPassword.getPassword()));
										String rank = cboUserRank.getSelectedItem().toString();
										
										if(rbtnCreateUser.isSelected() == true){
											if(comparePasswords(password, confirmPassword) == true){
												con.createUser(username, password, rank);
											}else{
												JOptionPane.showMessageDialog(
													    frame,
													    "Passwords Do Not Match",
													    "Creditenials Error",
														JOptionPane.ERROR_MESSAGE);
											}
										}
										if(rbtnAddCustomer.isSelected() == true){
											String newCust = txtCust.getText();
											String newCustomer = txtAddCusPro.getText();
											String CreatedBy = con.getUser();
											Timestamp Created = con.getTimestamp();
											con.createCustomer(newCustomer, newCust, Created, CreatedBy);
										}
										if(rbtnAddProgram.isSelected() == true){
											String Program = txtAddCusPro.getText();
											String Customer = cboCustomer.getSelectedItem().toString();
											String ProgramStart = txtProStart.getText();
											String ProgramEnd = txtProEnd.getText();
											String CreatedBy = con.getUser();
											Timestamp Created = con.getTimestamp();
											String Cust = con.queryDatabase("customers", "Customer", Customer).getJSONObject(0).getString("Cust").toString();
											con.createProgram(Customer, Cust, Program, ProgramStart, ProgramEnd, Created, CreatedBy);
										}
										if(rbtnChangeUserRank.isSelected() == true){
											con.changeUserRank(username, rank);
										}
										if(rbtnChangePass.isSelected() == true){
											if(comparePasswords(password, confirmPassword) == true){
												con.changeUserPassword(username, password);
											}else{
												JOptionPane.showMessageDialog(
													    frame,
													    "Passwords Do Not Match",
													    "Creditenials Error",
														JOptionPane.ERROR_MESSAGE);
											}
										}
											
									}catch(Exception ex){/*Ignore*/ex.printStackTrace();}
									txtUsername.setText("");
									txtPassword.setText("");
									txtConfirmPassword.setText("");
									txtCust.setText("");
									txtAddCusPro.setText("");
									txtProStart.setText("");
									txtProEnd.setText("");
									cboCustomer.setSelectedIndex(-1);
							}}}});
			setupPanel();
		}
		
		private void setupPanel()
		{
			GroupLayout groupLayout = new GroupLayout(this);
			groupLayout.setHorizontalGroup(
				groupLayout.createParallelGroup(Alignment.LEADING)
					.addGroup(groupLayout.createSequentialGroup()
						.addGap(10)
						.addComponent(lblbosal)
						.addGap(26)
						.addComponent(lblmanageUsers))
					.addGroup(groupLayout.createSequentialGroup()
						.addGap(42)
						.addComponent(rbtnCreateUser))
					.addGroup(groupLayout.createSequentialGroup()
						.addGap(42)
						.addComponent(rbtnDeleteUser)
						.addGap(174)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
							.addComponent(lblCustomer, GroupLayout.PREFERRED_SIZE, 74, GroupLayout.PREFERRED_SIZE)
							.addComponent(lblUsername))
						.addGap(10)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
							.addComponent(cboCustomer, GroupLayout.PREFERRED_SIZE, 182, GroupLayout.PREFERRED_SIZE)
							.addComponent(txtUsername, GroupLayout.PREFERRED_SIZE, 182, GroupLayout.PREFERRED_SIZE)))
					.addGroup(groupLayout.createSequentialGroup()
						.addGap(42)
						.addComponent(rbtnChangeUserRank)
						.addGap(50)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
							.addComponent(lblAddProgram)
							.addGroup(groupLayout.createSequentialGroup()
								.addGap(70)
								.addComponent(lblPassword))
							.addComponent(lblAddCustomer))
						.addGap(10)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
							.addComponent(txtPassword, GroupLayout.PREFERRED_SIZE, 182, GroupLayout.PREFERRED_SIZE)
							.addComponent(txtAddCusPro, GroupLayout.PREFERRED_SIZE, 182, GroupLayout.PREFERRED_SIZE)))
					.addGroup(groupLayout.createSequentialGroup()
						.addGap(42)
						.addComponent(rbtnChangePass)
						.addGap(71)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
							.addGroup(groupLayout.createSequentialGroup()
								.addGap(24)
								.addComponent(lblProStart, GroupLayout.PREFERRED_SIZE, 107, GroupLayout.PREFERRED_SIZE))
							.addGroup(groupLayout.createSequentialGroup()
								.addGap(28)
								.addComponent(lblCust))
							.addComponent(lblPassword2))
						.addGap(10)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
							.addComponent(txtConfirmPassword, GroupLayout.PREFERRED_SIZE, 182, GroupLayout.PREFERRED_SIZE)
							.addComponent(txtCust, GroupLayout.PREFERRED_SIZE, 182, GroupLayout.PREFERRED_SIZE)
							.addComponent(txtProStart, GroupLayout.PREFERRED_SIZE, 182, GroupLayout.PREFERRED_SIZE))
						.addGap(6)
						.addComponent(lblPassConfirm, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE))
					.addGroup(groupLayout.createSequentialGroup()
						.addGap(42)
						.addComponent(rbtnAddCustomer, GroupLayout.PREFERRED_SIZE, 136, GroupLayout.PREFERRED_SIZE)
						.addGap(119)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
							.addComponent(lblProEnd)
							.addGroup(groupLayout.createSequentialGroup()
								.addGap(21)
								.addComponent(lblRank)))
						.addGap(10)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
							.addComponent(txtProEnd, GroupLayout.PREFERRED_SIZE, 182, GroupLayout.PREFERRED_SIZE)
							.addComponent(cboUserRank, GroupLayout.PREFERRED_SIZE, 182, GroupLayout.PREFERRED_SIZE)))
					.addGroup(groupLayout.createSequentialGroup()
						.addGap(42)
						.addComponent(rbtnAddProgram, GroupLayout.PREFERRED_SIZE, 118, GroupLayout.PREFERRED_SIZE))
					.addGroup(groupLayout.createSequentialGroup()
						.addGap(42)
						.addComponent(btnBack, GroupLayout.PREFERRED_SIZE, 155, GroupLayout.PREFERRED_SIZE)
						.addGap(106)
						.addComponent(btnDelete, GroupLayout.PREFERRED_SIZE, 155, GroupLayout.PREFERRED_SIZE)
						.addGap(100)
						.addComponent(btnSave, GroupLayout.PREFERRED_SIZE, 155, GroupLayout.PREFERRED_SIZE))
			);
			groupLayout.setVerticalGroup(
				groupLayout.createParallelGroup(Alignment.LEADING)
					.addGroup(groupLayout.createSequentialGroup()
						.addGap(10)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
							.addComponent(lblbosal)
							.addGroup(groupLayout.createSequentialGroup()
								.addGap(17)
								.addComponent(lblmanageUsers)))
						.addGap(53)
						.addComponent(rbtnCreateUser)
						.addGap(10)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
							.addComponent(rbtnDeleteUser)
							.addGroup(groupLayout.createSequentialGroup()
								.addGap(4)
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
									.addComponent(lblCustomer)
									.addComponent(lblUsername)))
							.addGroup(groupLayout.createSequentialGroup()
								.addGap(4)
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
									.addComponent(cboCustomer, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addComponent(txtUsername, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
						.addGap(10)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
							.addComponent(rbtnChangeUserRank)
							.addGroup(groupLayout.createSequentialGroup()
								.addGap(4)
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
									.addComponent(lblAddProgram)
									.addComponent(lblPassword)
									.addComponent(lblAddCustomer)))
							.addGroup(groupLayout.createSequentialGroup()
								.addGap(4)
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
									.addComponent(txtPassword, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addComponent(txtAddCusPro, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
						.addGap(10)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
							.addComponent(rbtnChangePass)
							.addGroup(groupLayout.createSequentialGroup()
								.addGap(4)
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
									.addComponent(lblProStart)
									.addComponent(lblCust)
									.addComponent(lblPassword2)))
							.addGroup(groupLayout.createSequentialGroup()
								.addGap(3)
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
									.addComponent(txtConfirmPassword, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
									.addGroup(groupLayout.createSequentialGroup()
										.addGap(1)
										.addComponent(txtCust, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
									.addGroup(groupLayout.createSequentialGroup()
										.addGap(1)
										.addComponent(txtProStart, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
							.addGroup(groupLayout.createSequentialGroup()
								.addGap(10)
								.addComponent(lblPassConfirm)))
						.addGap(10)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
							.addComponent(rbtnAddCustomer)
							.addGroup(groupLayout.createSequentialGroup()
								.addGap(4)
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
									.addComponent(lblProEnd)
									.addComponent(lblRank)))
							.addGroup(groupLayout.createSequentialGroup()
								.addGap(4)
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
									.addComponent(txtProEnd, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addComponent(cboUserRank, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
						.addGap(10)
						.addComponent(rbtnAddProgram)
						.addGap(32)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
							.addComponent(btnBack, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE)
							.addComponent(btnDelete, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE)
							.addComponent(btnSave, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE)))
			);
			setLayout(groupLayout);
		}}//End of Class ManageUsersPanel
	class BDLPanel extends JPanel
	{
		//JLabels	
			private JLabel lblBosal;
			private JLabel lblBDLManager;
			
		//JButtons
			private JButton btnCreate;
			private JButton btnUpdate;
			private JButton btnDelete;
			private JButton btnBack;
			private JButton btnCheck;
				
			public BDLPanel(final JPanel bdl) 
			{
			
				setBackground(new Color(105, 105, 105));
				
			//JLabels
				ImageIcon bosal = new ImageIcon(getClass().getResource("/images/bosal.jpg"));
				lblBosal = new JLabel(bosal);
				lblBDLManager = new JLabel("Breakdown List Manager");
				
			//JButtons
				ImageIcon create = new ImageIcon(getClass().getResource("/images/CreateBDL.jpg"));
				btnCreate = new JButton(create);
				btnCreate.addActionListener(new ActionListener() {
					
					public void actionPerformed(ActionEvent e) 
					{
						if (e.getSource() == btnCreate) {
							  File file = new File("C:/users/shawg/desktop/Europe Breakdown List.xlsx");
						        try {
						            Desktop.getDesktop().open(file);
						        } catch (IOException a) {
						            a.printStackTrace();
						        }
						}
					}					
				});
				
				ImageIcon update = new ImageIcon(getClass().getResource("/images/updateBDL.jpg"));
				btnUpdate = new JButton(update);
				
				ImageIcon delete = new ImageIcon(getClass().getResource("/images/delete.jpg"));
				btnDelete = new JButton(delete);
				
				ImageIcon check = new ImageIcon(getClass().getResource("/images/check.jpg"));
				btnCheck = new JButton(check);
				
				ImageIcon back = new ImageIcon(getClass().getResource("/images/back.jpg"));
				btnBack = new JButton(back);
				btnBack.addActionListener(new ActionListener() {
					
					public void actionPerformed(ActionEvent e) 
					{
						if (e.getSource() == btnBack)
						{
							setVisible(false);
							Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
							int height = screenSize.height;
							int width = screenSize.width;
							frame.setResizable(false);
							frame.setSize(width/2, height/2);
							frame.setLocationRelativeTo(null);
							frame.setSize(865, 555);
							frame.setTitle("Main Menu:");
							main.setVisible(true);
						}}});
							
				lblBDLManager.setFont(new Font("EucrosiaUPC", Font.BOLD, 64));
				lblBDLManager.setForeground(Color.BLACK);
				
			setupPanel();
			}
			
				private void setupPanel()
				{
					GroupLayout groupLayout = new GroupLayout(this);
					groupLayout.setHorizontalGroup(
						groupLayout.createParallelGroup(Alignment.LEADING)
							.addGroup(groupLayout.createSequentialGroup()
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
									.addGroup(groupLayout.createSequentialGroup()
										.addGap(14)
										.addComponent(lblBosal)
										.addGap(27)
										.addComponent(lblBDLManager))
									.addGroup(groupLayout.createSequentialGroup()
										.addGap(82)
										.addComponent(btnCreate, GroupLayout.PREFERRED_SIZE, 155, GroupLayout.PREFERRED_SIZE)
										.addGap(251)
										.addComponent(btnUpdate, GroupLayout.PREFERRED_SIZE, 155, GroupLayout.PREFERRED_SIZE))
									.addGroup(groupLayout.createSequentialGroup()
										.addGap(82)
										.addComponent(btnDelete, GroupLayout.PREFERRED_SIZE, 155, GroupLayout.PREFERRED_SIZE)
										.addGap(68)
										.addComponent(btnCheck, GroupLayout.PREFERRED_SIZE, 106, GroupLayout.PREFERRED_SIZE)
										.addGap(79)
										.addComponent(btnBack, GroupLayout.PREFERRED_SIZE, 155, GroupLayout.PREFERRED_SIZE)))
								.addContainerGap(103, Short.MAX_VALUE))
					);
					groupLayout.setVerticalGroup(
						groupLayout.createParallelGroup(Alignment.LEADING)
							.addGroup(groupLayout.createSequentialGroup()
								.addGap(10)
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
									.addComponent(lblBosal)
									.addGroup(groupLayout.createSequentialGroup()
										.addGap(19)
										.addComponent(lblBDLManager)))
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
									.addGroup(groupLayout.createSequentialGroup()
										.addGap(129)
										.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
											.addComponent(btnCreate, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE)
											.addComponent(btnUpdate, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE))
										.addGap(1)
										.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
											.addGroup(groupLayout.createSequentialGroup()
												.addGap(32)
												.addComponent(btnDelete, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE))
											.addGroup(groupLayout.createSequentialGroup()
												.addGap(32)
												.addComponent(btnBack, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE))))
									.addGroup(groupLayout.createSequentialGroup()
										.addGap(184)
										.addComponent(btnCheck, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)))
								.addContainerGap(156, Short.MAX_VALUE))
					);
					setLayout(groupLayout);
					
				}}//End of Class BDLPanel	
	class ExperimentalPanel extends JPanel
	{
	//JLabels
		private JLabel lblCreated;
		private JLabel lblCreatedBy;
		private JLabel lblUpdated;
		private JLabel lblUpdatedBy;
		private JLabel lblProgram;
		private JLabel lblPartDescrip;
		private JLabel lblCustomerPartNum;
		private JLabel lblCustomer;
		private JLabel lblYear;
		private JLabel lblSearchPart;
		private JLabel lblBosal;
		private JLabel lblExperimental;
	
	//JTextFields
		private JTextField txtCreated;
		private JTextField txtCreatedBy;
		private JTextField txtUpdated;
		private JTextField txtUpdatedBy;
		private JTextField txtCustomerPartNum;
		private JTextField txtPartNum;
		private JTextField txtSearchPart;
		
	//JComboBoxes
		private JComboBox<String> cboProgram;
		private ComboBoxModel<String> resetProgramComboBox()
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
			}catch(Exception ex){/*Ignore*/}
			return proComboBoxDefault;
		}
		private JComboBox<String> cboPartDescrip;
		private ComboBoxModel<String> resetDescripComboBox()
		{
			JSONArray temp1 = new JSONArray();
			ComboBoxModel<String> descripComboBoxDefault = null;
			String[] types = null;
			
			try {
				temp1 = con.queryReturnAllDescriptions();
				types = new String[temp1.length()];
				for(int i = 0; i < temp1.length(); i++){
					types[i] = temp1.getJSONObject(i).get("Name").toString();
				}
				descripComboBoxDefault = (new DefaultComboBoxModel<String> (types));
			}catch(Exception ex){/*Ignore*/}
			return descripComboBoxDefault;
		}
		private JComboBox<String> cboCustomer;
		private ComboBoxModel<String> resetCustomerComboBox()
		{
			JSONArray temp1 = new JSONArray();
			ComboBoxModel<String> custComboBoxDefault = null;
			String[] types = null;
			try {
				temp1 = con.queryReturnAllCustomers();
				types = new String[temp1.length()];
						for(int i = 0; i < temp1.length(); i++){
							types[i] = temp1.getJSONObject(i).getString("Cust").toString();
				}
				custComboBoxDefault = (new DefaultComboBoxModel<String> (types));
			}catch(Exception ex){/*Ignore*/}
			return custComboBoxDefault;
		}
		private JComboBox<?> cboYear;
		
	//JButtons
		private JButton btnSave;
		private JButton btnBack;
		private JButton btnCheck;
		
	//JRadioButtons
		private JRadioButton rbtnSearch;
		private JRadioButton rbtnCreate;
		
	public ExperimentalPanel(final JPanel experimental)
	{
		setBackground(new Color(105, 105, 105));
		try {
			config = new ConfigurationManager(configFilePath);
		}catch(Exception ex){ex.printStackTrace();}
		
		//JLabels
			lblCreated = new JLabel("Created");
			lblCreatedBy = new JLabel("Created By");
			lblUpdated = new JLabel("Updated");
			lblUpdatedBy = new JLabel("Updated By");
			lblProgram = new JLabel("Program");
			lblPartDescrip = new JLabel("Part Description");
			lblCustomerPartNum = new JLabel("Customer Part Number");
			lblCustomer = new JLabel("Customer");
			lblYear = new JLabel("Year Code");
			lblSearchPart = new JLabel("Part Number");
			ImageIcon bosal = new ImageIcon(getClass().getResource("/images/bosal.jpg"));
			lblBosal = new JLabel(bosal);
			lblExperimental = new JLabel("Experimental Part Number");
			
		//JTextFields
			txtCreated = new JTextField();
			txtCreated.setBackground(new Color(190, 190, 190));
			txtCreated.setForeground(Color.BLACK);
			txtCreated.addMouseListener(new ContextMenuMouseListener());
			txtCreated.setEditable(false);
			txtCreatedBy = new JTextField();
			txtCreatedBy.setBackground(new Color(190, 190, 190));
			txtCreatedBy.setForeground(Color.BLACK);
			txtCreatedBy.addMouseListener(new ContextMenuMouseListener());
			txtCreatedBy.setEditable(false);
			txtUpdated = new JTextField();
			txtUpdated.setBackground(new Color(190, 190, 190));
			txtUpdated.setForeground(Color.BLACK);
			txtUpdated.addMouseListener(new ContextMenuMouseListener());
			txtUpdated.setEditable(false);
			txtUpdatedBy = new JTextField();
			txtUpdatedBy.setBackground(new Color(190, 190, 190));
			txtUpdatedBy.setForeground(Color.BLACK);
			txtUpdatedBy.addMouseListener(new ContextMenuMouseListener());
			txtUpdatedBy.setEditable(false);
			txtCustomerPartNum = new JTextField();
			txtCustomerPartNum.setForeground(Color.BLACK);
			txtCustomerPartNum.addMouseListener(new ContextMenuMouseListener());
			txtPartNum = new JTextField();
			txtPartNum.setBackground(new Color(190, 190, 190));
			txtPartNum.setForeground(Color.BLACK);
			txtPartNum.addMouseListener(new ContextMenuMouseListener());
			txtPartNum.setEditable(false);
			txtSearchPart = new JTextField();
			txtSearchPart.setForeground(Color.BLACK);
			txtSearchPart.addMouseListener(new ContextMenuMouseListener());
			txtSearchPart.setEditable(false);
						
		//JComboBoxes
			cboProgram = new JComboBox<String>();
			cboProgram.setModel(resetProgramComboBox());
			AutoCompleteDecorator.decorate(cboProgram);
			cboProgram.addMouseListener(new ContextMenuMouseListener());
			cboProgram.setForeground(Color.BLACK);
			cboProgram.setSelectedIndex(-1);
			cboPartDescrip = new JComboBox<String>();
			AutoCompleteDecorator.decorate(cboPartDescrip);
			cboPartDescrip.setForeground(Color.BLACK);
			cboPartDescrip.addMouseListener(new ContextMenuMouseListener());
			cboPartDescrip.setModel(resetDescripComboBox());
			cboPartDescrip.setSelectedIndex(-1);
			cboCustomer = new JComboBox<String>();
			cboCustomer.setModel(resetCustomerComboBox());
			AutoCompleteDecorator.decorate(cboCustomer);
			cboCustomer.setSelectedIndex(-1);
			cboCustomer.addMouseListener(new ContextMenuMouseListener());
			cboCustomer.setForeground(Color.BLACK);
			String[] years = {"13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30"};
			cboYear = new JComboBox<Object>(years);
			cboYear.setSelectedIndex(-1);
			cboYear.setEditable(false);
			cboYear.setForeground(Color.BLACK);
			
		//JRadioButtons
			rbtnSearch = new JRadioButton("Search Part");
			rbtnSearch.setFont(new Font("Tahoma", Font.BOLD, 14));
			rbtnSearch.setForeground(Color.BLACK);
			rbtnSearch.setBackground(new Color(105, 105, 105));
			rbtnCreate = new JRadioButton("Create Part");
			rbtnCreate.setFont(new Font("Tahoma", Font.BOLD, 14));
			rbtnCreate.setForeground(Color.BLACK);
			rbtnCreate.setBackground(new Color(105, 105, 105));
			
		//RadioButton Logic
			rbtnCreate.setSelected(true);
			rbtnCreate.addActionListener(new ActionListener(){
				
				public void actionPerformed(ActionEvent e)
				{
					if (e.getSource() == rbtnCreate){
			            
			            txtSearchPart.setText("");
			            txtSearchPart.setEditable(false);
			            txtCreated.setText("");
			            txtCreatedBy.setText("");
			            txtUpdated.setText("");
			            txtUpdatedBy.setText("");
			            txtCustomerPartNum.setText("");
			            txtPartNum.setText("");
			            cboYear.setSelectedIndex(-1);
			            cboProgram.setSelectedIndex(-1);
			            cboCustomer.setSelectedIndex(-1);
			            cboPartDescrip.setSelectedIndex(-1);
	            }
			}});	
								
			rbtnSearch.addActionListener(new ActionListener(){
				
				public void actionPerformed(ActionEvent e)
				{
					if (e.getSource() == rbtnSearch){
						
			            txtSearchPart.setText("");
			            txtSearchPart.setEditable(true);
			            txtSearchPart.requestFocusInWindow();
			            txtCreated.setText("");
			            txtCreatedBy.setText("");
			            txtUpdated.setText("");
			            txtUpdatedBy.setText("");
			            txtCustomerPartNum.setText("");
			            txtPartNum.setText("");
			            
			            }
			}});
			
		//JButtons
			ImageIcon save = new ImageIcon(getClass().getResource("/images/save.jpg"));
			btnSave = new JButton(save);
			btnSave.addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent e) 
				{
					if (e.getSource() == btnSave) {
						txtCreated.setText(con.getDate().toString());
						txtCreatedBy.setText(config.getProperty("appUser"));
						int n = JOptionPane.showConfirmDialog(
							    frame,
							    "Are you sure you want to save part data?",
							    "Save:",
							    JOptionPane.YES_NO_OPTION,
								JOptionPane.WARNING_MESSAGE);
						if(n == 0){
							try {
								String Program = null;
								String PartDescription = null;
								String CustPartNumber =  null;
								String Customer = null;
								String Year = null;
								
								Program = (String) cboProgram.getSelectedItem();
								PartDescription = (String) cboPartDescrip.getSelectedItem();
								CustPartNumber = txtCustomerPartNum.getText();
								Customer = (String) cboCustomer.getSelectedItem();
								Year = (String) cboYear.getSelectedItem();
								
								con.insertExperimentalPart(Program, PartDescription, CustPartNumber, Customer, Year);								
							}catch(Exception ex){/*Ignore*/};
						}
					}
				}					
			});
						
			ImageIcon back = new ImageIcon(getClass().getResource("/images/back.jpg"));
			btnBack = new JButton(back);
			btnBack.addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent e) 
				{
					if (e.getSource() == btnBack) {
						setVisible(false);
						Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
						int height = screenSize.height;
						int width = screenSize.width;
						frame.setResizable(false);
						frame.setSize(width/2, height/2);
						frame.setLocationRelativeTo(null);
						frame.setSize(865, 555);
						frame.setTitle("Main Menu:");
						main.setVisible(true);
						cboPartDescrip.setSelectedIndex(-1);
						cboYear.setSelectedIndex(-1);
						txtCreated.setText("");
						txtCreatedBy.setText("");
						txtUpdated.setText("");
						txtUpdatedBy.setText("");
						txtCustomerPartNum.setText("");
						txtPartNum.setText("");
						txtSearchPart.setText("");
						cboCustomer.setSelectedIndex(-1);
						cboProgram.setSelectedIndex(-1);
						rbtnCreate.setSelected(true);
					}
				}					
			});
						
			ImageIcon check= new ImageIcon(getClass().getResource("/images/check.jpg"));
			btnCheck = new JButton(check);
			btnCheck.addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent e) 
				{
					if (e.getSource() == btnCheck) {
						con = new DBConnect();
						final String findBosalText = txtSearchPart.getText();
						
						try{
							JSONObject temp = (con.queryDatabase("experimental parts", "PartNumber", findBosalText)).getJSONObject(0);
							String cpartText = null;
						
							//set text for CustPartNumber JTextField
							try{
								cpartText = temp.get("CustPartNumber").toString();
							}catch(Exception ex){cpartText = "-";}
							txtCustomerPartNum.setText(cpartText);
							
							//set text for Description JComboBox
							String descrip = null;
							try{
								descrip = temp.get("PartDescription").toString();
							}catch(Exception ex){descrip = "-";}
							cboPartDescrip.setSelectedItem(descrip);
							
							//set text for Program JComboBox
							String program = null;
							try{
								program = temp.get("Program").toString();
							}catch(Exception ex){program = "-";}
							cboProgram.setSelectedItem(program);
							
							//set text for Customer JComboBox
							String cust = null;
							try{
								cust = temp.get("Customer").toString();
							}catch(Exception ex){cust = "-";}
							cboCustomer.setSelectedItem(cust);
							
							//set text for Year JComboBox
							String year = null;
							try{
								year = temp.getString("YearCode").toString();
							}catch(Exception ex){year = "-";}
							cboYear.setSelectedItem(year);
							
							//set text for Created JTextField
							String created = null;
							try{
								created = temp.get("Created").toString();
							}catch(Exception ex){created = "-";}
							txtCreated.setText(created);
							
							//set text for CreatedBy JTextField
							String createdBy = null;
							try{
								createdBy = temp.get("CreatedBy").toString();
							}catch(Exception ex){createdBy = "-";}
							txtCreatedBy.setText(createdBy);
							
							//set text for Part Number JTextField
							String part = null;
							try{
								part = temp.get("PartNumber").toString();
							}catch(Exception ex){part = "-";}
							txtPartNum.setText(part);
							
						}catch(Exception ex){
							JOptionPane.showMessageDialog(
									    frame,
									    "Bosal Part Number: " + findBosalText + " does not exist",
									    "Missing Part Number",
										JOptionPane.ERROR_MESSAGE);
							
						}
		
					}
				}					
			});
				
						
		setupPanel();
		ButtonGroup group = new ButtonGroup();
		group.add(rbtnCreate);
		group.add(rbtnSearch);
	}
	
		private void setupPanel()
				{
					lblCreated.setFont(new Font("Tahoma", Font.BOLD, 14));
					lblCreated.setForeground(Color.BLACK);
					lblCreatedBy.setFont(new Font("Tahoma", Font.BOLD, 14));
					lblCreatedBy.setForeground(Color.BLACK);
					lblUpdated.setFont(new Font("Tahoma", Font.BOLD, 14));
					lblUpdated.setForeground(Color.BLACK);
					lblUpdatedBy.setFont(new Font("Tahoma", Font.BOLD, 14));
					lblUpdatedBy.setForeground(Color.BLACK);
					lblProgram.setFont(new Font("Tahoma", Font.BOLD, 14));
					lblProgram.setForeground(Color.BLACK);
					lblPartDescrip.setFont(new Font("Tahoma", Font.BOLD, 14));
					lblPartDescrip.setForeground(Color.BLACK);
					lblCustomerPartNum.setFont(new Font("Tahoma", Font.BOLD, 14));
					lblCustomerPartNum.setForeground(Color.BLACK);
					lblCustomer.setFont(new Font("Tahoma", Font.BOLD, 14));
					lblCustomer.setForeground(Color.BLACK);
					lblYear.setFont(new Font("Tahoma", Font.BOLD, 14));
					lblYear.setForeground(Color.BLACK);
					lblSearchPart.setFont(new Font("Tahoma", Font.BOLD, 14));
					lblSearchPart.setForeground(Color.BLACK);
					lblExperimental.setFont(new Font("EucrosiaUPC", Font.BOLD, 64));
					lblExperimental.setForeground(Color.BLACK);
					
					GroupLayout groupLayout = new GroupLayout(this);
					groupLayout.setHorizontalGroup(
						groupLayout.createParallelGroup(Alignment.LEADING)
							.addGroup(groupLayout.createSequentialGroup()
								.addGap(10)
								.addComponent(lblBosal, GroupLayout.PREFERRED_SIZE, 201, GroupLayout.PREFERRED_SIZE)
								.addGap(6)
								.addComponent(lblExperimental))
							.addGroup(groupLayout.createSequentialGroup()
								.addGap(61)
								.addComponent(lblCustomer, GroupLayout.PREFERRED_SIZE, 81, GroupLayout.PREFERRED_SIZE)
								.addGap(20)
								.addComponent(lblProgram, GroupLayout.PREFERRED_SIZE, 96, GroupLayout.PREFERRED_SIZE)
								.addGap(5)
								.addComponent(lblPartDescrip, GroupLayout.PREFERRED_SIZE, 130, GroupLayout.PREFERRED_SIZE)
								.addGap(140)
								.addComponent(lblYear, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE)
								.addGap(14)
								.addComponent(lblSearchPart, GroupLayout.PREFERRED_SIZE, 109, GroupLayout.PREFERRED_SIZE))
							.addGroup(groupLayout.createSequentialGroup()
								.addGap(61)
								.addComponent(cboCustomer, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE)
								.addGap(23)
								.addComponent(cboProgram, GroupLayout.PREFERRED_SIZE, 81, GroupLayout.PREFERRED_SIZE)
								.addGap(21)
								.addComponent(cboPartDescrip, GroupLayout.PREFERRED_SIZE, 250, GroupLayout.PREFERRED_SIZE)
								.addGap(19)
								.addComponent(cboYear, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE)
								.addGap(14)
								.addComponent(txtPartNum, GroupLayout.PREFERRED_SIZE, 140, GroupLayout.PREFERRED_SIZE))
							.addGroup(groupLayout.createSequentialGroup()
								.addGap(61)
								.addComponent(lblCreated, GroupLayout.PREFERRED_SIZE, 96, GroupLayout.PREFERRED_SIZE)
								.addGap(12)
								.addComponent(lblCreatedBy, GroupLayout.PREFERRED_SIZE, 162, GroupLayout.PREFERRED_SIZE)
								.addGap(293)
								.addComponent(lblCustomerPartNum, GroupLayout.PREFERRED_SIZE, 177, GroupLayout.PREFERRED_SIZE))
							.addGroup(groupLayout.createSequentialGroup()
								.addGap(61)
								.addComponent(txtCreated, GroupLayout.PREFERRED_SIZE, 73, GroupLayout.PREFERRED_SIZE)
								.addGap(35)
								.addComponent(txtCreatedBy, GroupLayout.PREFERRED_SIZE, 109, GroupLayout.PREFERRED_SIZE)
								.addGap(343)
								.addComponent(txtCustomerPartNum, GroupLayout.PREFERRED_SIZE, 140, GroupLayout.PREFERRED_SIZE))
							.addGroup(groupLayout.createSequentialGroup()
								.addGap(61)
								.addComponent(lblUpdated, GroupLayout.PREFERRED_SIZE, 96, GroupLayout.PREFERRED_SIZE)
								.addGap(12)
								.addComponent(lblUpdatedBy, GroupLayout.PREFERRED_SIZE, 109, GroupLayout.PREFERRED_SIZE))
							.addGroup(groupLayout.createSequentialGroup()
								.addGap(61)
								.addComponent(txtUpdated, GroupLayout.PREFERRED_SIZE, 73, GroupLayout.PREFERRED_SIZE)
								.addGap(35)
								.addComponent(txtUpdatedBy, GroupLayout.PREFERRED_SIZE, 109, GroupLayout.PREFERRED_SIZE))
							.addGroup(groupLayout.createSequentialGroup()
								.addGap(61)
								.addComponent(rbtnCreate, GroupLayout.PREFERRED_SIZE, 109, GroupLayout.PREFERRED_SIZE))
							.addGroup(groupLayout.createSequentialGroup()
								.addGap(61)
								.addComponent(rbtnSearch, GroupLayout.PREFERRED_SIZE, 109, GroupLayout.PREFERRED_SIZE)
								.addGap(6)
								.addComponent(txtSearchPart, GroupLayout.PREFERRED_SIZE, 109, GroupLayout.PREFERRED_SIZE)
								.addGap(10)
								.addComponent(btnCheck, GroupLayout.PREFERRED_SIZE, 106, GroupLayout.PREFERRED_SIZE))
							.addGroup(groupLayout.createSequentialGroup()
								.addGap(61)
								.addComponent(btnBack, GroupLayout.PREFERRED_SIZE, 155, GroupLayout.PREFERRED_SIZE)
								.addGap(402)
								.addComponent(btnSave, GroupLayout.PREFERRED_SIZE, 155, GroupLayout.PREFERRED_SIZE))
					);
					groupLayout.setVerticalGroup(
						groupLayout.createParallelGroup(Alignment.LEADING)
							.addGroup(groupLayout.createSequentialGroup()
								.addGap(11)
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
									.addComponent(lblBosal, GroupLayout.PREFERRED_SIZE, 63, GroupLayout.PREFERRED_SIZE)
									.addGroup(groupLayout.createSequentialGroup()
										.addGap(15)
										.addComponent(lblExperimental, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE)))
								.addGap(11)
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
									.addComponent(lblProgram, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
									.addComponent(lblPartDescrip, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
									.addGroup(groupLayout.createSequentialGroup()
										.addGap(3)
										.addComponent(lblCustomer, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE))
									.addGroup(groupLayout.createSequentialGroup()
										.addGap(3)
										.addComponent(lblYear, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE))
									.addGroup(groupLayout.createSequentialGroup()
										.addGap(3)
										.addComponent(lblSearchPart, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE)))
								.addGap(5)
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
									.addComponent(cboProgram, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addComponent(cboPartDescrip, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addComponent(cboCustomer, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addComponent(cboYear, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addComponent(txtPartNum, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addGap(13)
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
									.addComponent(lblCreated, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE)
									.addComponent(lblCreatedBy, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE)
									.addComponent(lblCustomerPartNum, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE))
								.addGap(11)
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
									.addComponent(txtCreated, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addComponent(txtCreatedBy, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addComponent(txtCustomerPartNum, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addGap(11)
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
									.addComponent(lblUpdated, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE)
									.addGroup(groupLayout.createSequentialGroup()
										.addGap(1)
										.addComponent(lblUpdatedBy, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE)))
								.addGap(11)
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
									.addComponent(txtUpdated, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addComponent(txtUpdatedBy, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addGap(28)
								.addComponent(rbtnCreate)
								.addGap(3)
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
									.addComponent(rbtnSearch)
									.addGroup(groupLayout.createSequentialGroup()
										.addGap(4)
										.addComponent(txtSearchPart, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
									.addComponent(btnCheck, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE))
								.addGap(29)
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
									.addComponent(btnBack, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE)
									.addComponent(btnSave, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE)))
					);
					setLayout(groupLayout);						
				}}//End of Class Experimental Parts Panel	
}//End of MainFrames

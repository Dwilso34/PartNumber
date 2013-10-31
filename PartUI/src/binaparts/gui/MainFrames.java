package binaparts.gui;

import java.awt.CardLayout;
import java.awt.Color;
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
import java.sql.*;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
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
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
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
		experimental = new ExperimentalPanel(contentPane);
		contentPane.add(main, "Main Menu");
		contentPane.add(create, "Create Part");
		contentPane.add(update, "Update Part");
		contentPane.add(find, "Find Part");
		contentPane.add(settings, "Settings");
		contentPane.add(manage, "Manage Users");
		contentPane.add(experimental, "Experimental Part");
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int height = screenSize.height;
		int width = screenSize.width;
		frame.setResizable(false);
		frame.setSize(width/2, height/2);
		frame.setLocationRelativeTo(null);
		frame.setSize(645, 545);
		frame.setContentPane(contentPane);
		frame.setVisible(true);	
		
		try{
			frame.setIconImage(ImageIO.read(new File("res/bosalimage.png")));
		}catch(Exception ex){ex.printStackTrace();}
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
						frame.setSize(790,340);
						frame.setTitle("Experimental Parts:");
						frame.setResizable(false);
						frame.setLocationRelativeTo(main);
						main.add(experimental);
						experimental.validate();
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
						BDLFrame b = new BDLFrame();
						b.displayBDL();
						
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
						frame.setSize(640,430);
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
								frame.setSize(800,355);
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
								frame.setSize(790,410);
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
								frame.setSize(875,385);
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
						.addGap(280)
						.addComponent(Bosal))
					.addGroup(groupLayout.createSequentialGroup()
						.addGap(33)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
							.addComponent(btnCreatePart, GroupLayout.PREFERRED_SIZE, 106, GroupLayout.PREFERRED_SIZE)
							.addComponent(btnUpdatePart, GroupLayout.PREFERRED_SIZE, 106, GroupLayout.PREFERRED_SIZE)
							.addComponent(btnFindPartInfo, GroupLayout.PREFERRED_SIZE, 106, GroupLayout.PREFERRED_SIZE)
							.addComponent(btnBDL, GroupLayout.PREFERRED_SIZE, 106, GroupLayout.PREFERRED_SIZE)
							.addComponent(btnExperimental, GroupLayout.PREFERRED_SIZE, 106, GroupLayout.PREFERRED_SIZE)
							.addComponent(btnSetting, GroupLayout.PREFERRED_SIZE, 106, GroupLayout.PREFERRED_SIZE)
							.addComponent(btnManageUsers, GroupLayout.PREFERRED_SIZE, 106, GroupLayout.PREFERRED_SIZE)
							.addComponent(btnTest, GroupLayout.PREFERRED_SIZE, 106, GroupLayout.PREFERRED_SIZE))
						.addGap(26)
						.addComponent(lblMainPic, GroupLayout.PREFERRED_SIZE, 430, GroupLayout.PREFERRED_SIZE))
			);
			groupLayout.setVerticalGroup(
				groupLayout.createParallelGroup(Alignment.LEADING)
					.addGroup(groupLayout.createSequentialGroup()
						.addGap(10)
						.addComponent(status, GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE)
						.addGap(10)
						.addComponent(Bosal)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
							.addGroup(groupLayout.createSequentialGroup()
								.addGap(26)
								.addComponent(btnCreatePart, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
								.addGap(11)
								.addComponent(btnUpdatePart, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
								.addGap(11)
								.addComponent(btnFindPartInfo, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
								.addGap(11)
								.addComponent(btnBDL, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
								.addGap(11)
								.addComponent(btnExperimental, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
								.addGap(11)
								.addComponent(btnSetting, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
								.addGap(11)
								.addComponent(btnManageUsers, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
								.addGap(11)
								.addComponent(btnTest, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE))
							.addComponent(lblMainPic, GroupLayout.PREFERRED_SIZE, 407, GroupLayout.PREFERRED_SIZE)))
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
		private JLabel lblDrawingRev;
		private JLabel lblDrawingRevDate;
		private JLabel lblProductionReleaseDate;
		private JLabel lblMonth;
		private JLabel lblDay;
		private JLabel lblYear;
		private JLabel lblMonth2;
		private JLabel lblDay2;
		private JLabel lblYear2;
	
	//JTextField
		private JTextField txtDescrip;
		private JTextField txtMDescrip;
		private JTextField txtDrawingNum;
		private JTextField txtSeq;
		private JTextField txtBPart;
		private JTextField txtCPart;
		private JTextField txtSPart;
		private JTextField txtDrawingRev;
		
	//JComboBox	
		JPanel contentPane;	
		private JComboBox<String> cboProgram;
		private ComboBoxModel<String> resetProgramComboBox()
		{
			JSONArray temp1 = new JSONArray();
			ComboBoxModel<String> programComboBoxDefault = null;
			String[] pros = null;
			
			try {
				temp1 = con.queryReturnAllPrograms();
				pros = new String[temp1.length()];
				for(int i = 0; i < temp1.length(); i++){
					pros[i] = temp1.getJSONObject(i).get("Program").toString();
				}
				programComboBoxDefault = (new DefaultComboBoxModel<String> (pros));
			}catch(Exception ex){ex.printStackTrace();}
			return programComboBoxDefault;
		}
		private JComboBox<String> cboType;
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
			}catch(Exception ex){ex.printStackTrace();}
			return typeComboBoxDefault;
		}
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
			}catch(Exception ex){ex.printStackTrace();}
			return descripComboBoxDefault;
		}
		private JComboBox<String> cboMat;
		private ComboBoxModel<String> resetMatComboBox()
		{
			ComboBoxModel<String> matComboBoxDefault = new DefaultComboBoxModel<String>();
			return matComboBoxDefault;
		}
		private JComboBox<String> cboDrawingDay;
		private JComboBox<String> cboDrawingMonth;
		private JComboBox<String> cboDrawingYear;
		private JComboBox<String> cboProductionDay;
		private JComboBox<String> cboProductionMonth;
		private JComboBox<String> cboProductionYear;
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
			lblDrawingRev = new JLabel("Drawing Rev");
			lblDrawingRev.setFont(new Font("Tahoma", Font.BOLD, 14));
			lblDrawingRev.setForeground(Color.BLACK);
			lblDrawingRevDate = new JLabel("Drawing Rev Date");
			lblDrawingRevDate.setFont(new Font("Tahoma", Font.BOLD, 14));
			lblDrawingRevDate.setForeground(Color.BLACK);
			lblProductionReleaseDate= new JLabel("Production Release Date");
			lblProductionReleaseDate.setFont(new Font("Tahoma", Font.BOLD, 14));
			lblProductionReleaseDate.setForeground(Color.BLACK);
			lblMonth = new JLabel("MM");
			lblMonth.setFont(new Font("Tahoma", Font.BOLD, 14));
			lblMonth.setForeground(Color.BLACK);
			lblDay = new JLabel("DD");
			lblDay.setFont(new Font("Tahoma", Font.BOLD, 14));
			lblDay.setForeground(Color.BLACK);
			lblYear = new JLabel("YYYY");
			lblYear.setFont(new Font("Tahoma", Font.BOLD, 14));
			lblYear.setForeground(Color.BLACK);
			lblMonth2 = new JLabel("MM");
			lblMonth2.setFont(new Font("Tahoma", Font.BOLD, 14));
			lblMonth2.setForeground(Color.BLACK);
			lblDay2 = new JLabel("DD");
			lblDay2.setFont(new Font("Tahoma", Font.BOLD, 14));
			lblDay2.setForeground(Color.BLACK);
			lblYear2 = new JLabel("YYYY");
			lblYear2.setFont(new Font("Tahoma", Font.BOLD, 14));
			lblYear2.setForeground(Color.BLACK);
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
			txtDrawingNum = new JTextField();
			txtDrawingNum.setForeground(Color.BLACK);
			txtDrawingNum.addMouseListener(new ContextMenuMouseListener());
			txtDrawingRev = new JTextField();
			txtDrawingRev.setForeground(Color.BLACK);
			txtDrawingRev.addMouseListener(new ContextMenuMouseListener());
			
		//ComboBoxes
				
			cboType = new JComboBox<String>();
			cboType.setEditable(true);
			cboType.setForeground(Color.BLACK);
			AutoCompleteDecorator.decorate(cboType);
			cboType.setModel(resetTypeComboBox());
			cboType.setSelectedIndex(-1);
			cboType.addMouseListener(new ContextMenuMouseListener());
			cboMat = new JComboBox<String>();
			cboMat.setEditable(true);
			cboMat.setForeground(Color.BLACK);
			AutoCompleteDecorator.decorate(cboMat);
			cboMat.setModel(resetMatComboBox());
			cboMat.addMouseListener(new ContextMenuMouseListener());
			cboDescrip = new JComboBox<String>();
			cboDescrip.setEditable(true);
			cboDescrip.setForeground(Color.BLACK);
			AutoCompleteDecorator.decorate(cboDescrip);
			cboDescrip.addMouseListener(new ContextMenuMouseListener());
			cboDescrip.setModel(resetDescripComboBox());
			cboDescrip.setSelectedIndex(-1);
			cboProgram = new JComboBox<String>();
			cboProgram.setEditable(true);
			cboProgram.setForeground(Color.BLACK);
			AutoCompleteDecorator.decorate(cboProgram);
			cboProgram.addMouseListener(new ContextMenuMouseListener());
			cboProgram.setModel(resetProgramComboBox());
			cboProgram.setSelectedIndex(-1);
			String[] days = {"1", "2", "3", "4", "5", "6", "7", "8", "9", 
					"10", "11", "12", "13", "14", "15", "16", "17", "18",
					"19", "20", "21", "22", "23", "24", "25", "26", "27",
					"28", "29", "30", "31"};
			cboDrawingDay = new JComboBox<String>(days);
			cboDrawingDay.setEditable(true);
			cboDrawingDay.setForeground(Color.BLACK);
			AutoCompleteDecorator.decorate(cboDrawingDay);
			cboDrawingDay.setSelectedIndex(-1);
			cboProductionDay = new JComboBox<String>(days);
			cboProductionDay.setForeground(Color.BLACK);
			cboProductionDay.setEditable(true);
			AutoCompleteDecorator.decorate(cboProductionDay);
			cboProductionDay.setSelectedIndex(-1);
			String[] months = {"1", "2", "3", "4", "5", "6", "7", "8", "9", 
					"10", "11", "12"};
			cboDrawingMonth = new JComboBox<String>(months);
			cboDrawingMonth.setForeground(Color.BLACK);
			cboDrawingMonth.setEditable(true);
			AutoCompleteDecorator.decorate(cboDrawingMonth);
			cboDrawingMonth.setSelectedIndex(-1);
			cboProductionMonth = new JComboBox<String>(months);
			cboProductionMonth.setForeground(Color.BLACK);
			cboProductionMonth.setEditable(true);
			AutoCompleteDecorator.decorate(cboProductionMonth);
			cboProductionMonth.setSelectedIndex(-1);
			String[] years = {"13", "14", "15", "16", "17", 
					"18", "19", "20", "21", "22", "23", "24", "25", "26", 
					"27", "28", "29", "30"};
			cboDrawingYear = new JComboBox<String>(years);
			cboDrawingYear.setForeground(Color.BLACK);
			cboDrawingYear.setEditable(true);
			AutoCompleteDecorator.decorate(cboDrawingYear);
			cboDrawingYear.setSelectedIndex(-1);
			cboProductionYear = new JComboBox<String>(years);
			cboProductionYear.setForeground(Color.BLACK);
			cboProductionYear.setEditable(true);
			AutoCompleteDecorator.decorate(cboProductionYear);
			cboProductionYear.setSelectedIndex(-1);
			
			ItemListener comboBoxSelectionListener = (new ItemListener(){	
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
							ComboBoxModel<String> matComboBoxModel = null;
							ComboBoxModel<String> descripComboBoxModel = null;
							
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
								matComboBoxModel =  (new DefaultComboBoxModel<String> (mats));
								descripComboBoxModel = (new DefaultComboBoxModel<String> (descrip));
							}catch(Exception ex){ex.printStackTrace();}
							txtMDescrip.setText("");
							txtBPart.setText("");
							txtSPart.setText("");
							txtCPart.setText("");
							txtDrawingNum.setText("");
							cboProgram.setSelectedIndex(-1);
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
							}catch(Exception ex){ex.printStackTrace();}
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
								int PartType = Integer.valueOf((String) cboType.getSelectedItem());
								int Material = 0;
								if(cboMat.getSelectedItem()!= null){
									Material = Integer.valueOf((String) cboMat.getSelectedItem());
								}
								String BosalPartNumber = txtBPart.getText();
								String CustomerPartNumber = txtCPart.getText();
								String SupplierPartNumber = txtSPart.getText();
								String Description = (String) cboDescrip.getSelectedItem();
								String Program = (String) cboProgram.getSelectedItem();
								int Seq = Integer.valueOf(txtSeq.getText());
								String TypeDescription = txtDescrip.getText();
								int Rev = 0;
								String DrawingNumber = txtDrawingNum.getText();
								int DrawingRev = Integer.valueOf(txtDrawingRev.getText());
								String DrawingRevDate = (String)cboDrawingMonth.getSelectedItem()+"/"
										+(String)cboDrawingDay.getSelectedItem()+"/"
										+(String)cboDrawingYear.getSelectedItem();
								String ProductionReleaseDate = (String)cboProductionMonth.getSelectedItem()+"/"
										+(String)cboProductionDay.getSelectedItem()+"/"
										+(String)cboProductionYear.getSelectedItem();	
								con.insertNewPart(PartType, Material, BosalPartNumber, CustomerPartNumber, 
										SupplierPartNumber, Description, Program, Seq, TypeDescription,
										Rev, DrawingNumber, DrawingRev, DrawingRevDate, ProductionReleaseDate);
								
								setVisible(false);
								Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
								int height = screenSize.height;
								int width = screenSize.width;
								frame.setResizable(false);
								frame.setSize(width/2, height/2);
								frame.setLocationRelativeTo(null);
								frame.setSize(645, 545);
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
								cboProgram.setSelectedIndex(-1);
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
						frame.setSize(645, 545);
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
						cboProgram.setSelectedIndex(-1);
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
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
							.addGroup(groupLayout.createSequentialGroup()
								.addComponent(lblBosal, GroupLayout.PREFERRED_SIZE, 199, GroupLayout.PREFERRED_SIZE)
								.addGap(6)
								.addComponent(lblCreateAPart, GroupLayout.PREFERRED_SIZE, 415, GroupLayout.PREFERRED_SIZE))
							.addGroup(groupLayout.createSequentialGroup()
								.addComponent(lblType)
								.addGap(79)
								.addComponent(lblTypeDescription)
								.addGap(120)
								.addComponent(lblBosalPartNumber)
								.addGap(49)
								.addComponent(lblProductionReleaseDate, GroupLayout.PREFERRED_SIZE, 177, GroupLayout.PREFERRED_SIZE))
							.addGroup(groupLayout.createSequentialGroup()
								.addComponent(cboType, GroupLayout.PREFERRED_SIZE, 79, GroupLayout.PREFERRED_SIZE)
								.addGap(34)
								.addComponent(txtDescrip, GroupLayout.PREFERRED_SIZE, 211, GroupLayout.PREFERRED_SIZE)
								.addGap(24)
								.addComponent(txtBPart, GroupLayout.PREFERRED_SIZE, 156, GroupLayout.PREFERRED_SIZE)
								.addGap(23)
								.addComponent(lblMonth, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
								.addGap(45)
								.addComponent(lblDay2, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
								.addGap(47)
								.addComponent(lblYear2))
							.addGroup(groupLayout.createSequentialGroup()
								.addComponent(lblMatterial)
								.addGap(59)
								.addComponent(lblMatterialDescription)
								.addGap(100)
								.addComponent(lblCustomerPartNumber)
								.addGap(18)
								.addComponent(cboProductionMonth, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
								.addGap(26)
								.addComponent(cboProductionDay, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
								.addGap(28)
								.addComponent(cboProductionYear, GroupLayout.PREFERRED_SIZE, 73, GroupLayout.PREFERRED_SIZE))
							.addGroup(groupLayout.createSequentialGroup()
								.addComponent(cboMat, GroupLayout.PREFERRED_SIZE, 79, GroupLayout.PREFERRED_SIZE)
								.addGap(34)
								.addComponent(txtMDescrip, GroupLayout.PREFERRED_SIZE, 211, GroupLayout.PREFERRED_SIZE)
								.addGap(24)
								.addComponent(txtCPart, GroupLayout.PREFERRED_SIZE, 156, GroupLayout.PREFERRED_SIZE)
								.addGap(23)
								.addComponent(lblDrawingRevDate, GroupLayout.PREFERRED_SIZE, 138, GroupLayout.PREFERRED_SIZE))
							.addGroup(groupLayout.createSequentialGroup()
								.addComponent(lblSeq)
								.addGap(87)
								.addComponent(lblDescription)
								.addGap(158)
								.addComponent(lblSupplierPartNumber)
								.addGap(30)
								.addComponent(lblMonth2, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
								.addGap(45)
								.addComponent(lblDay, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
								.addGap(47)
								.addComponent(lblYear))
							.addGroup(groupLayout.createSequentialGroup()
								.addComponent(txtSeq, GroupLayout.PREFERRED_SIZE, 79, GroupLayout.PREFERRED_SIZE)
								.addGap(34)
								.addComponent(cboDescrip, GroupLayout.PREFERRED_SIZE, 211, GroupLayout.PREFERRED_SIZE)
								.addGap(24)
								.addComponent(txtSPart, GroupLayout.PREFERRED_SIZE, 156, GroupLayout.PREFERRED_SIZE)
								.addGap(23)
								.addComponent(cboDrawingMonth, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
								.addGap(26)
								.addComponent(cboDrawingDay, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
								.addGap(28)
								.addComponent(cboDrawingYear, GroupLayout.PREFERRED_SIZE, 73, GroupLayout.PREFERRED_SIZE))
							.addGroup(groupLayout.createSequentialGroup()
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
									.addComponent(lblProgram, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
									.addComponent(cboProgram, GroupLayout.PREFERRED_SIZE, 115, GroupLayout.PREFERRED_SIZE))
								.addGap(39)
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
									.addComponent(lblDrawingNum, GroupLayout.PREFERRED_SIZE, 156, GroupLayout.PREFERRED_SIZE)
									.addComponent(txtDrawingNum, GroupLayout.PREFERRED_SIZE, 156, GroupLayout.PREFERRED_SIZE))
								.addGap(38)
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
									.addComponent(lblDrawingRev, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE)
									.addComponent(txtDrawingRev, GroupLayout.PREFERRED_SIZE, 156, GroupLayout.PREFERRED_SIZE))
								.addGap(18)
								.addComponent(btnBack, GroupLayout.PREFERRED_SIZE, 106, GroupLayout.PREFERRED_SIZE)
								.addGap(10)
								.addComponent(btnSave, GroupLayout.PREFERRED_SIZE, 106, GroupLayout.PREFERRED_SIZE))))
			);
			groupLayout.setVerticalGroup(
				groupLayout.createParallelGroup(Alignment.LEADING)
					.addGroup(groupLayout.createSequentialGroup()
						.addGap(32)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
							.addComponent(lblBosal, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
							.addComponent(lblCreateAPart, GroupLayout.PREFERRED_SIZE, 52, GroupLayout.PREFERRED_SIZE))
						.addGap(4)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
							.addGroup(groupLayout.createSequentialGroup()
								.addGap(2)
								.addComponent(lblType))
							.addGroup(groupLayout.createSequentialGroup()
								.addGap(2)
								.addComponent(lblTypeDescription))
							.addGroup(groupLayout.createSequentialGroup()
								.addGap(2)
								.addComponent(lblBosalPartNumber))
							.addComponent(lblProductionReleaseDate, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
						.addGap(7)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
							.addGroup(groupLayout.createSequentialGroup()
								.addGap(1)
								.addComponent(cboType, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE))
							.addGroup(groupLayout.createSequentialGroup()
								.addGap(2)
								.addComponent(txtDescrip, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGroup(groupLayout.createSequentialGroup()
								.addGap(2)
								.addComponent(txtBPart, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addComponent(lblMonth, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
							.addComponent(lblDay2, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
							.addComponent(lblYear2, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
							.addGroup(groupLayout.createSequentialGroup()
								.addGap(3)
								.addComponent(lblMatterial))
							.addGroup(groupLayout.createSequentialGroup()
								.addGap(3)
								.addComponent(lblMatterialDescription))
							.addGroup(groupLayout.createSequentialGroup()
								.addGap(3)
								.addComponent(lblCustomerPartNumber))
							.addComponent(cboProductionMonth, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(cboProductionDay, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(cboProductionYear, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGap(6)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
							.addComponent(cboMat, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(txtMDescrip, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(txtCPart, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(lblDrawingRevDate, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
						.addGap(7)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
							.addGroup(groupLayout.createSequentialGroup()
								.addGap(2)
								.addComponent(lblSeq))
							.addGroup(groupLayout.createSequentialGroup()
								.addGap(2)
								.addComponent(lblDescription))
							.addGroup(groupLayout.createSequentialGroup()
								.addGap(2)
								.addComponent(lblSupplierPartNumber))
							.addComponent(lblMonth2, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
							.addComponent(lblDay, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
							.addComponent(lblYear, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
						.addGap(5)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
							.addComponent(txtSeq, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(cboDescrip, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(txtSPart, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(cboDrawingMonth, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(cboDrawingDay, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(cboDrawingYear, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGap(6)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
							.addGroup(groupLayout.createSequentialGroup()
								.addComponent(lblProgram, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
								.addGap(4)
								.addComponent(cboProgram, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGroup(groupLayout.createSequentialGroup()
								.addGap(1)
								.addComponent(lblDrawingNum, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
								.addGap(6)
								.addComponent(txtDrawingNum, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGroup(groupLayout.createSequentialGroup()
								.addGap(1)
								.addComponent(lblDrawingRev, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
								.addGap(6)
								.addComponent(txtDrawingRev, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGroup(groupLayout.createSequentialGroup()
								.addGap(12)
								.addComponent(btnBack, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE))
							.addGroup(groupLayout.createSequentialGroup()
								.addGap(12)
								.addComponent(btnSave, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE))))
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
			private JLabel lblDrawingRev;
			private JLabel lblDrawingRevDate;
			private JLabel lblProductionReleaseDate;
			private JLabel lblMonth;
			private JLabel lblDay;
			private JLabel lblYear;
			private JLabel lblMonth2;
			private JLabel lblDay2;
			private JLabel lblYear2;
			
		//JButtons
			private JButton btnSave;
			private JButton btnBack;
			private JButton btnCheck;
			private JButton btnDelete;
			
		//JTextFields
			private JTextField txtFindBosal;
			private JTextField txtCusDescrip;
			private JTextField txtSupDescrip;
			private JTextField txtRev;
			private JTextField txtDrawingNum;
			private JTextField txtDrawingRev;
		
		//JComboBoxes			
			private JComboBox<String> cboProgram;
			private ComboBoxModel<String> resetProgramComboBox()
			{
				JSONArray temp1 = new JSONArray();
				ComboBoxModel<String> programComboBoxDefault = null;
				String[] pros = null;
				
				try {
					temp1 = con.queryReturnAllPrograms();
					pros = new String[temp1.length()];
					for(int i = 0; i < temp1.length(); i++){
						pros[i] = temp1.getJSONObject(i).get("Program").toString();
					}
					programComboBoxDefault = (new DefaultComboBoxModel<String> (pros));
				}catch(Exception ex){/*Ignore*/}
				return programComboBoxDefault;
			}
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
			private JComboBox<String> cboDrawingDay;
			private JComboBox<String> cboDrawingMonth;
			private JComboBox<String> cboDrawingYear;
			private JComboBox<String> cboProductionDay;
			private JComboBox<String> cboProductionMonth;
			private JComboBox<String> cboProductionYear;
			
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
				txtRev = new JTextField();
				txtRev.setEditable(true);
				txtRev.setForeground(Color.BLACK);
				txtRev.addMouseListener(new ContextMenuMouseListener());
				txtDrawingNum = new JTextField();
				txtDrawingNum.setForeground(Color.BLACK);
				txtDrawingNum.addMouseListener(new ContextMenuMouseListener());
				txtDrawingRev = new JTextField();
				txtDrawingRev.setForeground(Color.BLACK);
				txtDrawingRev.addMouseListener(new ContextMenuMouseListener());
							
			//JComboBoxes
				
				cboDescrip = new JComboBox<String>();
				cboDescrip.setEditable(true);
				cboDescrip.setForeground(Color.BLACK);
				AutoCompleteDecorator.decorate(cboDescrip);			
				cboDescrip.addMouseListener(new ContextMenuMouseListener());
				cboDescrip.setModel(resetDescripComboBox());
				cboDescrip.setSelectedIndex(-1);
				cboProgram = new JComboBox<String>();
				cboProgram.setEditable(true);
				cboProgram.setForeground(Color.BLACK);
				AutoCompleteDecorator.decorate(cboProgram);
				cboProgram.addMouseListener(new ContextMenuMouseListener());
				cboProgram.setModel(resetProgramComboBox());			
				cboProgram.setSelectedIndex(-1);
				String[] days = {"1", "2", "3", "4", "5", "6", "7", "8", "9", 
						"10", "11", "12", "13", "14", "15", "16", "17", "18",
						"19", "20", "21", "22", "23", "24", "25", "26", "27",
						"28", "29", "30", "31"};
				cboDrawingDay = new JComboBox<String>(days);
				cboDrawingDay.setForeground(Color.BLACK);
				cboDrawingDay.setEditable(true);
				AutoCompleteDecorator.decorate(cboDrawingDay);
				cboDrawingDay.setSelectedIndex(-1);
				cboProductionDay = new JComboBox<String>(days);
				cboProductionDay.setForeground(Color.BLACK);
				cboProductionDay.setEditable(true);
				AutoCompleteDecorator.decorate(cboProductionDay);
				cboProductionDay.setSelectedIndex(-1);
				String[] months = {"1", "2", "3", "4", "5", "6", "7", "8", "9", 
						"10", "11", "12"};
				cboDrawingMonth = new JComboBox<String>(months);
				cboDrawingMonth.setForeground(Color.BLACK);
				cboDrawingMonth.setEditable(true);
				AutoCompleteDecorator.decorate(cboDrawingMonth);
				cboDrawingMonth.setSelectedIndex(-1);
				cboProductionMonth = new JComboBox<String>(months);
				cboProductionMonth.setForeground(Color.BLACK);
				cboProductionMonth.setEditable(true);
				AutoCompleteDecorator.decorate(cboProductionMonth);
				cboProductionMonth.setSelectedIndex(-1);
				String[] years = {"13", "14", "15", "16", "17", 
						"18", "19", "20", "21", "22", "23", "24", "25", "26", 
						"27", "28", "29", "30"};
				cboDrawingYear = new JComboBox<String>(years);
				cboDrawingYear.setForeground(Color.BLACK);
				cboDrawingYear.setEditable(true);
				AutoCompleteDecorator.decorate(cboDrawingYear);
				cboDrawingYear.setSelectedIndex(-1);
				cboProductionYear = new JComboBox<String>(years);
				cboProductionYear.setForeground(Color.BLACK);
				cboProductionYear.setEditable(true);
				AutoCompleteDecorator.decorate(cboProductionYear);
				cboProductionYear.setSelectedIndex(-1);
				
			//Labels		
				
				lblBosalPartNum = new JLabel("Bosal Part Number");
				lblDescription = new JLabel("Description");
				lblCustomerPartNum = new JLabel("Customer Part Number");
				lblSupplierPartNum = new JLabel("Supplier Part Number");
				lblUpdatePart = new JLabel("Update Part");
				lblProgram = new JLabel("Program");
				lblRev = new JLabel("Rev Number");
				lblDrawingNum = new JLabel("Drawing Number");
				lblDrawingRev = new JLabel("Drawing Rev");
				lblDrawingRev.setFont(new Font("Tahoma", Font.BOLD, 14));
				lblDrawingRev.setForeground(Color.BLACK);
				lblDrawingRevDate = new JLabel("Drawing Rev Date");
				lblDrawingRevDate.setFont(new Font("Tahoma", Font.BOLD, 14));
				lblDrawingRevDate.setForeground(Color.BLACK);
				lblProductionReleaseDate= new JLabel("Production Release Date");
				lblProductionReleaseDate.setFont(new Font("Tahoma", Font.BOLD, 14));
				lblProductionReleaseDate.setForeground(Color.BLACK);
				lblMonth = new JLabel("MM");
				lblMonth.setFont(new Font("Tahoma", Font.BOLD, 14));
				lblMonth.setForeground(Color.BLACK);
				lblDay = new JLabel("DD");
				lblDay.setFont(new Font("Tahoma", Font.BOLD, 14));
				lblDay.setForeground(Color.BLACK);
				lblYear = new JLabel("YYYY");
				lblYear.setFont(new Font("Tahoma", Font.BOLD, 14));
				lblYear.setForeground(Color.BLACK);
				lblMonth2 = new JLabel("MM");
				lblMonth2.setFont(new Font("Tahoma", Font.BOLD, 14));
				lblMonth2.setForeground(Color.BLACK);
				lblDay2 = new JLabel("DD");
				lblDay2.setFont(new Font("Tahoma", Font.BOLD, 14));
				lblDay2.setForeground(Color.BLACK);
				lblYear2 = new JLabel("YYYY");
				lblYear2.setFont(new Font("Tahoma", Font.BOLD, 14));
				lblYear2.setForeground(Color.BLACK);
				
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
									cboProgram.setSelectedIndex(-1);
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
							frame.setSize(645, 545);
							frame.setTitle("Main Menu:");
							main.setVisible(true);
							txtFindBosal.setText("");
							txtCusDescrip.setText("");
							txtSupDescrip.setText("");
							cboProgram.setSelectedIndex(-1);
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
								if(txtCusDescrip.getText().equals("-") || txtCusDescrip.getText().equals("")){
									CustomerPartNumber = null;
								}else{CustomerPartNumber = txtCusDescrip.getText();}
								String SupplierPartNumber= null;
								if(txtSupDescrip.getText().equals("-") || txtSupDescrip.getText().equals("")){
									SupplierPartNumber = null;
								}else{SupplierPartNumber = txtSupDescrip.getText();}
								String Description = (String) cboDescrip.getSelectedItem();
								String Program = (String) cboProgram.getSelectedItem();
								int Rev = 0;
								if(txtRev.getText().equals("-") || txtRev.getText().equals("")){
									Rev = 0;
								}else{Rev = Integer.valueOf(txtRev.getText());}							
								String DrawingNumber = null;							
								if(txtDrawingNum.getText().equals("-") || txtDrawingNum.getText().equals("")){
									DrawingNumber = null;
								}else{DrawingNumber = txtDrawingNum.getText();}
								int DrawingRev = 0;							
								if(txtDrawingRev.getText().equals("-") || txtDrawingRev.getText().equals("")){
									DrawingRev = 0;
								}else{Rev = Integer.valueOf(txtDrawingRev.getText());}
								String DrawingRevDate = (String)cboDrawingMonth.getSelectedItem()+"/"
										+(String)cboDrawingDay.getSelectedItem()+"/"
										+(String)cboDrawingYear.getSelectedItem();
								String ProductionReleaseDate = (String)cboProductionMonth.getSelectedItem()+"/"
										+(String)cboProductionDay.getSelectedItem()+"/"
										+(String)cboProductionYear.getSelectedItem();									
								
								try {
									con.update(BosalPartNumber, CustomerPartNumber, SupplierPartNumber, 
											Description, Program, Rev, DrawingNumber, DrawingRev, 
											DrawingRevDate, ProductionReleaseDate);
									
									setVisible(false);
									Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
									int height = screenSize.height;
									int width = screenSize.width;
									frame.setResizable(false);
									frame.setSize(width/2, height/2);
									frame.setLocationRelativeTo(null);
									frame.setSize(645, 545);
									frame.setTitle("Main Menu:");
									main.setVisible(true);
									txtFindBosal.setText("");
									txtCusDescrip.setText("");
									txtSupDescrip.setText("");
									cboProgram.setSelectedIndex(-1);
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
							//con = new DBConnect();
							final String findBosalText = txtFindBosal.getText();
							
							try{
								JSONObject temp = (con.queryDatabase("bosal parts", "BosalPartNumber", findBosalText)).getJSONObject(0);
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
								
								//set text for Description JComboBox
								String descripText= null;
								try{
									descripText = temp.get("PartDescription").toString();
								}catch(Exception ex){descripText = "-";}
								cboDescrip.setSelectedItem(descripText);
								
								//set text for Program JComboBox
								String programText = null;
								try{
									programText = temp.get("Program").toString();
								}catch(Exception ex){programText = "-";}
								cboProgram.setSelectedItem(programText);
								
								//set text for DrawingNumber JTextField
								String DrawingNumber = null;
								try{
									DrawingNumber = temp.get("DrawingNumber").toString();
								}catch(Exception ex){DrawingNumber = "-";}
								txtDrawingNum.setText(DrawingNumber);
								
								//set text for REV JTextField
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
								cboProgram.setSelectedIndex(-1);
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
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(txtFindBosal, GroupLayout.PREFERRED_SIZE, 174, GroupLayout.PREFERRED_SIZE)
									.addGap(12)
									.addComponent(btnCheck, GroupLayout.PREFERRED_SIZE, 106, GroupLayout.PREFERRED_SIZE))
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(cboDescrip, GroupLayout.PREFERRED_SIZE, 174, GroupLayout.PREFERRED_SIZE)
									.addGap(36)
									.addComponent(txtDrawingNum, GroupLayout.PREFERRED_SIZE, 174, GroupLayout.PREFERRED_SIZE))
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(2)
									.addComponent(cboProgram, GroupLayout.PREFERRED_SIZE, 174, GroupLayout.PREFERRED_SIZE))
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(210)
									.addComponent(lblDrawingNum, GroupLayout.PREFERRED_SIZE, 174, GroupLayout.PREFERRED_SIZE))
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(210)
									.addComponent(lblSupplierPartNum))
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(210)
									.addComponent(txtSupDescrip, GroupLayout.PREFERRED_SIZE, 174, GroupLayout.PREFERRED_SIZE))
								.addComponent(lblDescription)
								.addComponent(lblProgram, GroupLayout.PREFERRED_SIZE, 223, GroupLayout.PREFERRED_SIZE))
							.addGap(25)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(lblDrawingRevDate, GroupLayout.PREFERRED_SIZE, 130, GroupLayout.PREFERRED_SIZE)
								.addGroup(groupLayout.createSequentialGroup()
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addComponent(lblMonth, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
										.addComponent(cboDrawingMonth, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE))
									.addGap(14)
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addComponent(cboDrawingDay, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
										.addComponent(lblDay, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE))
									.addGap(15)
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addComponent(lblYear)
										.addComponent(cboDrawingYear, GroupLayout.PREFERRED_SIZE, 73, GroupLayout.PREFERRED_SIZE)))
								.addComponent(cboProductionMonth, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(119)
									.addComponent(lblYear2))
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(59)
									.addComponent(lblDay2, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE))
								.addComponent(lblMonth2, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(119)
									.addComponent(cboProductionYear, GroupLayout.PREFERRED_SIZE, 73, GroupLayout.PREFERRED_SIZE))
								.addComponent(lblProductionReleaseDate, GroupLayout.PREFERRED_SIZE, 174, GroupLayout.PREFERRED_SIZE)
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(59)
									.addComponent(cboProductionDay, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)))
							.addGap(21)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(btnSave, GroupLayout.PREFERRED_SIZE, 106, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnBack, GroupLayout.PREFERRED_SIZE, 106, GroupLayout.PREFERRED_SIZE)))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(28)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(lblRev, GroupLayout.PREFERRED_SIZE, 94, GroupLayout.PREFERRED_SIZE)
								.addComponent(txtRev, GroupLayout.PREFERRED_SIZE, 174, GroupLayout.PREFERRED_SIZE))
							.addGap(36)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(lblCustomerPartNum)
								.addComponent(txtCusDescrip, GroupLayout.PREFERRED_SIZE, 174, GroupLayout.PREFERRED_SIZE))
							.addGap(25)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(lblDrawingRev, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
								.addComponent(txtDrawingRev, GroupLayout.PREFERRED_SIZE, 192, GroupLayout.PREFERRED_SIZE))
							.addGap(21)
							.addComponent(btnDelete, GroupLayout.PREFERRED_SIZE, 106, GroupLayout.PREFERRED_SIZE))
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
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addGroup(groupLayout.createSequentialGroup()
											.addGap(6)
											.addComponent(txtFindBosal, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
										.addComponent(btnCheck, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE))
									.addGap(8)
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addGroup(groupLayout.createSequentialGroup()
											.addGap(36)
											.addComponent(cboDescrip, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
										.addGroup(groupLayout.createSequentialGroup()
											.addGap(36)
											.addComponent(txtDrawingNum, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
										.addGroup(groupLayout.createSequentialGroup()
											.addGap(90)
											.addComponent(cboProgram, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
										.addComponent(lblDrawingNum, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
										.addGroup(groupLayout.createSequentialGroup()
											.addGap(63)
											.addComponent(lblSupplierPartNum))
										.addGroup(groupLayout.createSequentialGroup()
											.addGap(90)
											.addComponent(txtSupDescrip, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
										.addGroup(groupLayout.createSequentialGroup()
											.addGap(11)
											.addComponent(lblDescription))
										.addGroup(groupLayout.createSequentialGroup()
											.addGap(52)
											.addComponent(lblProgram, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE))))
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(25)
									.addComponent(lblDrawingRevDate, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
									.addGap(1)
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addComponent(lblMonth, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
										.addGroup(groupLayout.createSequentialGroup()
											.addGap(18)
											.addComponent(cboDrawingMonth, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
										.addGroup(groupLayout.createSequentialGroup()
											.addGap(18)
											.addComponent(cboDrawingDay, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
										.addComponent(lblDay, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
										.addComponent(lblYear, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
										.addGroup(groupLayout.createSequentialGroup()
											.addGap(18)
											.addComponent(cboDrawingYear, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
									.addGap(11)
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addGroup(groupLayout.createSequentialGroup()
											.addGap(38)
											.addComponent(cboProductionMonth, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
										.addGroup(groupLayout.createSequentialGroup()
											.addGap(19)
											.addComponent(lblYear2, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
										.addGroup(groupLayout.createSequentialGroup()
											.addGap(19)
											.addComponent(lblDay2, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
										.addGroup(groupLayout.createSequentialGroup()
											.addGap(19)
											.addComponent(lblMonth2, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
										.addGroup(groupLayout.createSequentialGroup()
											.addGap(38)
											.addComponent(cboProductionYear, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
										.addComponent(lblProductionReleaseDate, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
										.addGroup(groupLayout.createSequentialGroup()
											.addGap(38)
											.addComponent(cboProductionDay, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(64)
									.addComponent(btnSave, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
									.addGap(19)
									.addComponent(btnBack, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)))
							.addGap(5)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(lblRev, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
									.addGap(8)
									.addComponent(txtRev, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(3)
									.addComponent(lblCustomerPartNum)
									.addGap(10)
									.addComponent(txtCusDescrip, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(1)
									.addComponent(lblDrawingRev, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
									.addGap(9)
									.addComponent(txtDrawingRev, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(15)
									.addComponent(btnDelete, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE))))
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
	                    }catch(Exception ex){
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
		myTable = new JTable(){	
			public boolean isCellEditable(int row, int column){
				return false;
			}
		};
		/*scrollPane = new JScrollPane(myTable, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		myTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);*/
		scrollPane = new JScrollPane();
		scrollPane.setViewportBorder(new LineBorder(new Color(0, 0, 0)));
		scrollPane.setViewportView(myTable);
		myTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
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
		rbtnFindBosal.doClick();
		
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
									temp = (con.queryDatabase("bosal parts", "BosalPartNumber", searchText));
									myTable.setModel(populateTableModel("bosal parts", "BosalPartNumber", temp, searchText));
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
									temp = (con.queryDatabase("bosal parts", "SupPartNumber", searchText));
									myTable.setModel(populateTableModel("bosal parts", "SupPartNumber", temp, searchText));
									
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
									temp = (con.queryDatabase("bosal parts", "CustPartNumber", searchText));
									myTable.setModel(populateTableModel("bosal parts", "CustPartNumber", temp, searchText));

									}catch(Exception ex){
									JOptionPane.showMessageDialog(
											    frame,
											    "Customer Part Number: " + searchText + " does not exist",
											    "Missing Part Number",
												JOptionPane.ERROR_MESSAGE);
							}}
							if(rbtnFindPro.isSelected() == true){
								try{
									temp = (con.queryDatabase("bosal parts", "Program", searchText));
									myTable.setModel(populateTableModel("bosal parts", "Program", temp, searchText));

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
					int[] columnsWidth = {
			                110, 110, 110, 200, 80, 110, 40, 70, 130, 70, 130
			        };
					 int i = 0;
				        for (int width : columnsWidth) {
				            TableColumn column = myTable.getColumnModel().getColumn(i++);
				            column.setMinWidth(width);
				            column.setPreferredWidth(width);
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
				frame.setSize(645, 545);
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
						myTable.setModel(populateTableModel("bosal parts", "All", temp, searchText));
						
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
				int[] columnsWidth = {
		                110, 110, 110, 200, 80, 110, 40, 70, 130, 70, 130
		        };
				 int i = 0;
			        for (int width : columnsWidth) {
			            TableColumn column = myTable.getColumnModel().getColumn(i++);
			            column.setMinWidth(width);
			            column.setPreferredWidth(width);
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
					.addGap(33)
					.addComponent(rbtnFindBosal, GroupLayout.PREFERRED_SIZE, 161, GroupLayout.PREFERRED_SIZE)
					.addComponent(rbtnFindEuro, GroupLayout.PREFERRED_SIZE, 183, GroupLayout.PREFERRED_SIZE)
					.addComponent(rbtnFindCus, GroupLayout.PREFERRED_SIZE, 194, GroupLayout.PREFERRED_SIZE)
					.addComponent(rbtnFindSup, GroupLayout.PREFERRED_SIZE, 184, GroupLayout.PREFERRED_SIZE)
					.addComponent(rbtnFindPro, GroupLayout.PREFERRED_SIZE, 184, GroupLayout.PREFERRED_SIZE))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(33)
					.addComponent(btnBack, GroupLayout.PREFERRED_SIZE, 106, GroupLayout.PREFERRED_SIZE)
					.addGap(28)
					.addComponent(btnSearchAll, GroupLayout.PREFERRED_SIZE, 106, GroupLayout.PREFERRED_SIZE)
					.addGap(26)
					.addComponent(btnSearch, GroupLayout.PREFERRED_SIZE, 106, GroupLayout.PREFERRED_SIZE)
					.addGap(10)
					.addComponent(txtSearch, GroupLayout.PREFERRED_SIZE, 232, GroupLayout.PREFERRED_SIZE))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(33)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 804, GroupLayout.PREFERRED_SIZE))
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
					.addGap(21)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(btnBack, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnSearchAll, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnSearch, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(6)
							.addComponent(txtSearch, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addGap(11)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE))
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
									frame.setSize(645, 545);
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
							frame.setSize(645, 545);
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
							.addComponent(lblIP)
							.addGap(6)
							.addComponent(txtIP, GroupLayout.PREFERRED_SIZE, 169, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(169)
							.addComponent(lblPort)
							.addGap(6)
							.addComponent(txtPort, GroupLayout.PREFERRED_SIZE, 169, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(128)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(lblUser)
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(4)
									.addComponent(lblPass)))
							.addGap(6)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(txtUser, GroupLayout.PREFERRED_SIZE, 169, GroupLayout.PREFERRED_SIZE)
								.addComponent(ptxtPass, GroupLayout.PREFERRED_SIZE, 169, GroupLayout.PREFERRED_SIZE))
							.addGap(24)
							.addComponent(btnSave, GroupLayout.PREFERRED_SIZE, 106, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(91)
							.addComponent(lblDataBase)
							.addGap(6)
							.addComponent(txtDataBase, GroupLayout.PREFERRED_SIZE, 169, GroupLayout.PREFERRED_SIZE)
							.addGap(24)
							.addComponent(btnBack, GroupLayout.PREFERRED_SIZE, 106, GroupLayout.PREFERRED_SIZE))
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
								.addComponent(lblIP)
								.addComponent(txtIP, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(17)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(lblPort)
								.addComponent(txtPort, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(19)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(lblUser)
									.addGap(22)
									.addComponent(lblPass))
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(txtUser, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addGap(19)
									.addComponent(ptxtPass, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(17)
									.addComponent(btnSave, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)))
							.addGap(4)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(15)
									.addComponent(lblDataBase))
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(15)
									.addComponent(txtDataBase, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addComponent(btnBack, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)))
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
		private JLabel lblDeleteCust;
		private JLabel lblDeletePro;
		private JLabel lblFirstName;
		private JLabel lblLastName;
		
	//JTextFields
		private JTextField txtUsername;
		private JTextField txtAddCusPro;
		private JTextField txtProStart;
		private JTextField txtProEnd;
		private JTextField txtCust;
		private JTextField txtFirstName;
		private JTextField txtLastName;
		
	//JPasswordFields
		private JPasswordField txtPassword;
		private JPasswordField txtConfirmPassword;
		
	//JComboBoxes
		private JComboBox<?> cboUserRank;
		private JComboBox<String> cboDeletePro;
		private ComboBoxModel<String> resetDeleteProComboBox()
		{
			JSONArray temp1 = new JSONArray();
			ComboBoxModel<String> ProComboBoxDefault = null;
			String[] Pro = null;
			
			try {
				temp1 = con.queryReturnAllPrograms();
				Pro = new String[temp1.length()];
				for(int i = 0; i < temp1.length(); i ++){
					Pro[i] = temp1.getJSONObject(i).get("Program").toString();
				}
				ProComboBoxDefault = (new DefaultComboBoxModel<String> (Pro));
			}catch(Exception ex){ex.printStackTrace();/*Ignore*/}
			return ProComboBoxDefault;
		}
		private JComboBox<String> cboDeleteCust;
		private ComboBoxModel<String> resetDeleteCustComboBox()
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
			lblDeleteCust = new JLabel("Delete Customer:");
			lblDeleteCust.setVisible(false);
			lblDeleteCust.setFont(new Font("Tahoma", Font.BOLD, 14));
			lblDeleteCust.setForeground(Color.BLACK);
			lblDeletePro = new JLabel("Delete Program:");
			lblDeletePro.setVisible(false);
			lblDeletePro.setFont(new Font("Tahoma", Font.BOLD, 14));
			lblDeletePro.setForeground(Color.BLACK);
			lblFirstName = new JLabel("First Name:");
			lblFirstName.setVisible(true);
			lblFirstName.setFont(new Font("Tahoma", Font.BOLD, 14));
			lblFirstName.setForeground(Color.BLACK);
			lblLastName = new JLabel("Last Name:");
			lblLastName.setVisible(true);
			lblLastName.setFont(new Font("Tahoma", Font.BOLD, 14));
			lblLastName.setForeground(Color.BLACK);
			
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
			txtFirstName = new JTextField();
			txtFirstName.setVisible(true);
			txtFirstName.addMouseListener(new ContextMenuMouseListener());
			txtFirstName.setForeground(Color.BLACK);
			txtLastName = new JTextField();
			txtLastName.setVisible(true);
			txtLastName.addMouseListener(new ContextMenuMouseListener());
			txtLastName.setForeground(Color.BLACK);
			
		//ComboBox
			
			String[] ranks = {"admin","gui","engineer", "default"};
			cboUserRank = new JComboBox<Object>(ranks);
			cboUserRank.addMouseListener(new ContextMenuMouseListener());
			cboUserRank.setSelectedIndex(-1);
			cboUserRank.setBackground(Color.white);
			cboCustomer = new JComboBox<String>();
			cboCustomer.setModel(resetCustomerComboBox());
			cboCustomer.setVisible(false);
			cboCustomer.addMouseListener(new ContextMenuMouseListener());
			cboCustomer.setSelectedIndex(-1);
			cboCustomer.setBackground(Color.white);
			cboDeleteCust = new JComboBox<String>();
			cboDeleteCust.setModel(resetCustomerComboBox());
			cboDeleteCust.setVisible(false);
			cboDeleteCust.addMouseListener(new ContextMenuMouseListener());
			cboDeleteCust.setSelectedIndex(-1);
			cboDeleteCust.setBackground(Color.white);
			cboDeletePro = new JComboBox<String>();
			cboDeletePro.setModel(resetDeleteProComboBox());
			cboDeletePro.setVisible(false);
			cboDeletePro.addMouseListener(new ContextMenuMouseListener());
			cboDeletePro.setSelectedIndex(-1);
			cboDeletePro.setBackground(Color.white);
			
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
			            cboDeleteCust.setVisible(false);
			            lblDeleteCust.setVisible(false);
			            cboDeletePro.setVisible(true);
			            lblDeletePro.setVisible(true);
			            txtLastName.setVisible(false);
			            txtFirstName.setVisible(false);
			            lblLastName.setVisible(false);
			            lblFirstName.setVisible(false);
			            cboUserRank.setSelectedIndex(-1);
			            cboCustomer.setModel(resetCustomerComboBox());
			            cboCustomer.setSelectedIndex(-1);
			            cboDeleteCust.setSelectedIndex(-1);
			            cboDeletePro.setModel(resetDeleteProComboBox());
			            cboDeletePro.setSelectedIndex(-1);
			            
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
			            cboDeleteCust.setVisible(true);
			            lblDeleteCust.setVisible(true);
			            cboDeletePro.setVisible(false);
			            lblDeletePro.setVisible(false);
			            txtLastName.setVisible(false);
			            txtFirstName.setVisible(false);
			            lblLastName.setVisible(false);
			            lblFirstName.setVisible(false);
			            cboUserRank.setSelectedIndex(-1);
			            cboCustomer.setModel(resetCustomerComboBox());
			            cboCustomer.setSelectedIndex(-1);
			            cboDeleteCust.setModel(resetDeleteCustComboBox());
			            cboDeleteCust.setSelectedIndex(-1);
			            cboDeletePro.setSelectedIndex(-1);
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
			            cboDeleteCust.setVisible(false);
			            lblDeleteCust.setVisible(false);
			            cboDeletePro.setVisible(false);
			            lblDeletePro.setVisible(false);
			            txtLastName.setVisible(false);
			            txtFirstName.setVisible(false);
			            lblLastName.setVisible(false);
			            lblFirstName.setVisible(false);
			            cboUserRank.setSelectedIndex(-1);
			            cboCustomer.setSelectedIndex(-1);
			            cboDeleteCust.setSelectedIndex(-1);
			            cboDeletePro.setSelectedIndex(-1);
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
			            cboDeleteCust.setVisible(false);
			            lblDeleteCust.setVisible(false);
			            cboDeletePro.setVisible(false);
			            lblDeletePro.setVisible(false);
			            txtLastName.setVisible(true);
			            txtFirstName.setVisible(true);
			            lblLastName.setVisible(true);
			            lblFirstName.setVisible(true);
			            cboUserRank.setSelectedIndex(-1);
			            cboCustomer.setSelectedIndex(-1);
			            cboDeleteCust.setSelectedIndex(-1);
			            cboDeletePro.setSelectedIndex(-1);
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
			            cboDeleteCust.setVisible(false);
			            lblDeleteCust.setVisible(false);
			            cboDeletePro.setVisible(false);
			            lblDeletePro.setVisible(false);
			            txtLastName.setVisible(false);
			            txtFirstName.setVisible(false);
			            lblLastName.setVisible(false);
			            lblFirstName.setVisible(false);
			            cboUserRank.setSelectedIndex(-1);
			            cboCustomer.setSelectedIndex(-1);
			            cboDeleteCust.setSelectedIndex(-1);
			            cboDeletePro.setSelectedIndex(-1);
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
			            cboDeleteCust.setVisible(false);
			            lblDeleteCust.setVisible(false);
			            cboDeletePro.setVisible(false);
			            lblDeletePro.setVisible(false);
			            txtLastName.setVisible(false);
			            txtFirstName.setVisible(false);
			            lblLastName.setVisible(false);
			            lblFirstName.setVisible(false);
			            cboUserRank.setSelectedIndex(-1);
			            cboCustomer.setSelectedIndex(-1);
			            cboDeleteCust.setSelectedIndex(-1);
			            cboDeletePro.setSelectedIndex(-1);
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
						frame.setSize(645, 545);
						frame.setTitle("Main Menu:");
						main.setVisible(true);
						txtUsername.setText("");
						txtPassword.setText("");
						txtConfirmPassword.setText("");
						cboDeleteCust.setVisible(false);
						lblDeleteCust.setVisible(false);
						cboDeletePro.setVisible(false);
						lblDeletePro.setVisible(false);
						txtLastName.setVisible(true);
				        txtFirstName.setVisible(true);
				        lblLastName.setVisible(true);
				        lblFirstName.setVisible(true);
						cboUserRank.setSelectedIndex(-1);
						cboCustomer.setSelectedIndex(-1);
						cboDeleteCust.setSelectedIndex(-1);
						cboDeletePro.setSelectedIndex(-1);
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
												    "Delete:",
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
						}
						if(rbtnAddProgram.isSelected() == true){
							if(cboDeletePro.getSelectedItem().equals("")){
								JOptionPane.showMessageDialog(
										frame,
										"Please Select a Program",
										"Creditenials Error",
										JOptionPane.ERROR_MESSAGE);
							}else{
								try{
									if(con.getUserRank().equals("admin")){
										n = JOptionPane.showConfirmDialog(
												frame,
												"Are you sure you want to delete " + cboDeletePro.getSelectedItem() + " ?",
												"Delete:",
												JOptionPane.YES_NO_OPTION,
												JOptionPane.WARNING_MESSAGE);
						}else{
							config = new ConfigurationManager(configFilePath);
							JOptionPane.showMessageDialog(
									frame,
									"" + (config.getProperty("appUser")
											+ "does not have permission to Delete Programs"),
											"Creditenials Error",
											JOptionPane.ERROR_MESSAGE);
						}
								}catch(Exception ex){/*Ignore*/}
							}
						}
						if(n == 0){
							try{
								String program = cboDeletePro.getSelectedItem().toString();
						if(rbtnAddProgram.isSelected() == true){
							con.deleteProgram(program);
							cboDeletePro.setModel(resetDeleteProComboBox());
						}
						}catch(Exception ex){/*Ignore*/}
							cboDeletePro.setSelectedIndex(-1);
						}
							if(rbtnAddCustomer.isSelected() == true){
								if(cboDeleteCust.getSelectedItem().equals("")){
									JOptionPane.showMessageDialog(
											frame,
											"Please Select a Customer",
											"Creditenials Error",
											JOptionPane.ERROR_MESSAGE);
								}else{
									try{
										if(con.getUserRank().equals("admin")){
											n = JOptionPane.showConfirmDialog(
													frame,
													"Are you sure you want to delete " + cboDeleteCust.getSelectedItem() + " ?",
													"Delete:",
													JOptionPane.YES_NO_OPTION,
													JOptionPane.WARNING_MESSAGE);
										}else{
											config = new ConfigurationManager(configFilePath);
											JOptionPane.showMessageDialog(
													frame,
													"" + (config.getProperty("appUser")
															+ "does not have permission to Delete Customers"),
															"Creditenials Error",
															JOptionPane.ERROR_MESSAGE);
										}
									}catch(Exception ex){/*Ignore*/}
								}
							}
							if(n == 0){
								try{
									String customer = cboDeleteCust.getSelectedItem().toString();
							if(rbtnAddCustomer.isSelected() == true){
								con.deleteCustomer(customer);
								cboDeleteCust.setModel(resetDeleteCustComboBox());
							}
							}catch(Exception ex){/*Ignore*/}
								cboDeleteCust.setSelectedIndex(-1);
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
										if(rbtnCreateUser.isSelected() == true){
											String confirmPassword = (new String(txtConfirmPassword.getPassword()));
											String password = (new String(txtPassword.getPassword()));
											String username = txtUsername.getText();
											String firstName = txtFirstName.getText();
											String lastName = txtLastName.getText();
											if(comparePasswords(password, confirmPassword) == true){
												String rank = cboUserRank.getSelectedItem().toString();
												con.createUser(username, password, rank, firstName, lastName);
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
											con.createCustomer(newCustomer, newCust);
											cboCustomer.setModel(resetCustomerComboBox());
											cboDeleteCust.setModel(resetDeleteCustComboBox());
										}
										if(rbtnAddProgram.isSelected() == true){
											String Program = txtAddCusPro.getText();
											String Customer = cboCustomer.getSelectedItem().toString();
											String ProgramStart = txtProStart.getText();
											String ProgramEnd = txtProEnd.getText();
											String Cust = con.queryDatabase("customers", "Customer", Customer).getJSONObject(0).getString("Cust").toString();
											con.createProgram(Customer, Cust, Program, ProgramStart, ProgramEnd);
											cboDeletePro.setModel(resetDeleteProComboBox());
										}
										if(rbtnChangeUserRank.isSelected() == true){
											String rank = cboUserRank.getSelectedItem().toString();
											String username = txtUsername.getText();
											con.changeUserRank(username, rank);
										}
										if(rbtnChangePass.isSelected() == true){
											String confirmPassword = (new String(txtConfirmPassword.getPassword()));
											String password = (new String(txtPassword.getPassword()));
											String username = txtUsername.getText();
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
											
									}catch(Exception ex){ex.printStackTrace();}
									txtUsername.setText("");
									txtPassword.setText("");
									txtConfirmPassword.setText("");
									txtCust.setText("");
									txtAddCusPro.setText("");
									txtProStart.setText("");
									txtProEnd.setText("");				
									cboCustomer.setSelectedIndex(-1);
									cboUserRank.setSelectedIndex(-1);
									cboDeleteCust.setSelectedIndex(-1);
									cboDeletePro.setSelectedIndex(-1);
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
					.addComponent(rbtnCreateUser)
					.addGap(234)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblFirstName, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtFirstName, GroupLayout.PREFERRED_SIZE, 96, GroupLayout.PREFERRED_SIZE))
					.addGap(10)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblLastName, GroupLayout.PREFERRED_SIZE, 96, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtLastName, GroupLayout.PREFERRED_SIZE, 96, GroupLayout.PREFERRED_SIZE)))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(42)
					.addComponent(rbtnDeleteUser)
					.addGap(174)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblCustomer, GroupLayout.PREFERRED_SIZE, 74, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblUsername))
					.addGap(10)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(txtUsername, GroupLayout.PREFERRED_SIZE, 182, GroupLayout.PREFERRED_SIZE)
						.addComponent(cboCustomer, GroupLayout.PREFERRED_SIZE, 182, GroupLayout.PREFERRED_SIZE)))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(42)
					.addComponent(rbtnChangeUserRank)
					.addGap(50)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(70)
							.addComponent(lblPassword))
						.addComponent(lblAddCustomer)
						.addComponent(lblAddProgram))
					.addGap(10)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(txtPassword, GroupLayout.PREFERRED_SIZE, 182, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtAddCusPro, GroupLayout.PREFERRED_SIZE, 182, GroupLayout.PREFERRED_SIZE)))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(42)
					.addComponent(rbtnChangePass)
					.addGap(71)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblPassword2)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(24)
							.addComponent(lblProStart, GroupLayout.PREFERRED_SIZE, 107, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(28)
							.addComponent(lblCust)))
					.addGap(10)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(txtCust, GroupLayout.PREFERRED_SIZE, 182, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtProStart, GroupLayout.PREFERRED_SIZE, 182, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtConfirmPassword, GroupLayout.PREFERRED_SIZE, 182, GroupLayout.PREFERRED_SIZE))
					.addGap(6)
					.addComponent(lblPassConfirm, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(42)
					.addComponent(rbtnAddCustomer, GroupLayout.PREFERRED_SIZE, 136, GroupLayout.PREFERRED_SIZE)
					.addGap(102)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(38)
							.addComponent(lblRank))
						.addComponent(lblDeleteCust)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(17)
							.addComponent(lblProEnd)))
					.addGap(10)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(cboDeleteCust, GroupLayout.PREFERRED_SIZE, 182, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtProEnd, GroupLayout.PREFERRED_SIZE, 182, GroupLayout.PREFERRED_SIZE)
						.addComponent(cboUserRank, GroupLayout.PREFERRED_SIZE, 182, GroupLayout.PREFERRED_SIZE)))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(42)
					.addComponent(rbtnAddProgram, GroupLayout.PREFERRED_SIZE, 118, GroupLayout.PREFERRED_SIZE)
					.addGap(121)
					.addComponent(lblDeletePro)
					.addGap(10)
					.addComponent(cboDeletePro, GroupLayout.PREFERRED_SIZE, 182, GroupLayout.PREFERRED_SIZE))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(42)
					.addComponent(btnBack, GroupLayout.PREFERRED_SIZE, 106, GroupLayout.PREFERRED_SIZE)
					.addGap(108)
					.addComponent(btnDelete, GroupLayout.PREFERRED_SIZE, 106, GroupLayout.PREFERRED_SIZE)
					.addGap(125)
					.addComponent(btnSave, GroupLayout.PREFERRED_SIZE, 106, GroupLayout.PREFERRED_SIZE))
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
					.addGap(39)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(14)
							.addComponent(rbtnCreateUser))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblFirstName)
							.addGap(1)
							.addComponent(txtFirstName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblLastName)
							.addGap(1)
							.addComponent(txtLastName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
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
								.addComponent(txtUsername, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(cboCustomer, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
					.addGap(10)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(rbtnChangeUserRank)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(4)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(lblPassword)
								.addComponent(lblAddCustomer)
								.addComponent(lblAddProgram)))
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
								.addComponent(lblPassword2)
								.addComponent(lblProStart)
								.addComponent(lblCust)))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(3)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(1)
									.addComponent(txtCust, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(1)
									.addComponent(txtProStart, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addComponent(txtConfirmPassword, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(10)
							.addComponent(lblPassConfirm)))
					.addGap(10)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(rbtnAddCustomer)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(4)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(lblRank)
								.addComponent(lblDeleteCust)
								.addComponent(lblProEnd)))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(4)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(cboDeleteCust, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(txtProEnd, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(cboUserRank, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
					.addGap(10)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(rbtnAddProgram)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(4)
							.addComponent(lblDeletePro))
						.addComponent(cboDeletePro, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(19)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(btnBack, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnDelete, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnSave, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)))
		);
		setLayout(groupLayout);
	}}//End of Class ManageUsersPanel
	class ExperimentalPanel extends JPanel
	{
	//JLabels
		private JLabel lblCreated;
		private JLabel lblCreatedBy;
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
			}catch(Exception ex){ex.printStackTrace();}
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
			}catch(Exception ex){ex.printStackTrace();}
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
			}catch(Exception ex){ex.printStackTrace();}
			return custComboBoxDefault;
		}
		private JComboBox<String> cboYear;
		
	//JButtons
		private JButton btnSave;
		private JButton btnBack;
		private JButton btnCheck;
		
	//JRadioButtons
		private JRadioButton rbtnSearch;
		private JRadioButton rbtnCreate;
		
		public String generatePartNumber(String Customer, String Year, String curSeq)
		{
			String PartNumber = null;
			if(Year.length() < 2){
				for(int i = Year.length(); i < 2; i++){
					Year = "0" + Year;
				}
			}
			if(curSeq.length() < 4){
				for(int i = curSeq.length(); i < 4; i++){
					curSeq = "0" + curSeq;
				}
			}
			PartNumber = Customer + Year + "-" + curSeq;			
			return PartNumber;
		}
		
		public ExperimentalPanel(final JPanel experimental)
		{
			setBackground(new Color(105, 105, 105));
			try {
				config = new ConfigurationManager(configFilePath);
			}catch(Exception ex){ex.printStackTrace();}
		
		//JLabels
			lblCreated = new JLabel("Created");
			lblCreatedBy = new JLabel("Created By");
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
			cboProgram.setEditable(true);
			cboProgram.setForeground(Color.BLACK);
			AutoCompleteDecorator.decorate(cboProgram);
			cboProgram.setModel(resetProgramComboBox());
			cboProgram.addMouseListener(new ContextMenuMouseListener());
			cboProgram.setSelectedIndex(-1);
			cboPartDescrip = new JComboBox<String>();
			cboPartDescrip.setEditable(true);
			cboPartDescrip.setForeground(Color.BLACK);
			AutoCompleteDecorator.decorate(cboPartDescrip);
			cboPartDescrip.setModel(resetDescripComboBox());
			cboPartDescrip.addMouseListener(new ContextMenuMouseListener());
			cboPartDescrip.setSelectedIndex(-1);
			cboCustomer = new JComboBox<String>();
			cboCustomer.setEditable(true);
			cboCustomer.setForeground(Color.BLACK);
			AutoCompleteDecorator.decorate(cboCustomer);
			cboCustomer.setModel(resetCustomerComboBox());
			cboCustomer.addMouseListener(new ContextMenuMouseListener());
			cboCustomer.setSelectedIndex(-1);
			String[] years = {"13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30"};
			cboYear = new JComboBox<String>(years);
			cboYear.setEditable(true);
			cboYear.setForeground(Color.BLACK);
			AutoCompleteDecorator.decorate(cboYear);
			cboYear.setSelectedIndex(-1);
			
			ItemListener comboBoxSelectionListener = (new ItemListener(){	
				public void itemStateChanged(ItemEvent e)
				{
					if(cboCustomer.getSelectedItem() != null){
						if(cboYear.getSelectedItem() != null){
							txtPartNum.setText(generatePartNumber(cboCustomer.getSelectedItem().toString(), 
									cboYear.getSelectedItem().toString(), ""));
						}else{
							txtPartNum.setText(generatePartNumber(cboCustomer.getSelectedItem().toString(), 
									"", ""));
						}
					}else{
						txtPartNum.setText(generatePartNumber("", "", ""));
					}
				}});
			cboCustomer.addItemListener(comboBoxSelectionListener);
			cboYear.addItemListener(comboBoxSelectionListener);
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
			rbtnCreate.addActionListener(new ActionListener(){
				
				public void actionPerformed(ActionEvent e)
				{
					if (e.getSource() == rbtnCreate){
			            
			            txtSearchPart.setText("");
			            txtSearchPart.setEditable(false);
			            txtCreated.setText("");
			            txtCreatedBy.setText("");
			            txtCustomerPartNum.setText("");
			            txtPartNum.setText("");
			            cboYear.setSelectedIndex(-1);
			            cboProgram.setModel(resetProgramComboBox());
			            cboProgram.setSelectedIndex(-1);
			            cboCustomer.setModel(resetCustomerComboBox());
			            cboCustomer.setSelectedIndex(-1);
			            cboPartDescrip.setSelectedIndex(-1);
	            }
			}});
			rbtnCreate.doClick();
								
			rbtnSearch.addActionListener(new ActionListener(){
				
				public void actionPerformed(ActionEvent e)
				{
					if (e.getSource() == rbtnSearch){
						
			            txtSearchPart.setText("");
			            txtSearchPart.setEditable(true);
			            txtSearchPart.requestFocusInWindow();
			            txtCreated.setText("");
			            txtCreatedBy.setText("");
			            txtCustomerPartNum.setText("");
			            txtPartNum.setText("");
			            cboYear.setSelectedIndex(-1);
			            cboProgram.setModel(resetProgramComboBox());
			            cboProgram.setSelectedIndex(-1);
			            cboCustomer.setModel(resetCustomerComboBox());
			            cboCustomer.setSelectedIndex(-1);
			            cboPartDescrip.setSelectedIndex(-1);
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
								String EPart = null;
								
								Program = (String) cboProgram.getSelectedItem();
								PartDescription = (String) cboPartDescrip.getSelectedItem();
								CustPartNumber = txtCustomerPartNum.getText();
								Customer = (String) cboCustomer.getSelectedItem();
								Year = (String) cboYear.getSelectedItem();
								con.insertExperimentalPart(Program, PartDescription, CustPartNumber, Customer, Year);		
								JSONArray temp = con.queryReturnExpPart();
								EPart = temp.getJSONObject(0).get("PartNumber").toString();
								txtPartNum.setText(generatePartNumber(Customer, Year, EPart));
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
						frame.setSize(645, 545);
						frame.setTitle("Main Menu:");
						main.setVisible(true);
						cboPartDescrip.setSelectedIndex(-1);
						cboYear.setSelectedIndex(-1);
						txtCreated.setText("");
						txtCreatedBy.setText("");
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
								created = temp.get("Date").toString();
							}catch(Exception ex){created = "-";}
							txtCreated.setText(created);
							
							//set text for CreatedBy JTextField
							String createdBy = null;
							try{
								createdBy = temp.get("Engineer").toString();
							}catch(Exception ex){createdBy = "-";}
							txtCreatedBy.setText(createdBy);
							
							//set text for Part Number JTextField
							String part = null;
							try{
								part = temp.get("PartNumber").toString();
							}catch(Exception ex){part = "-";}
							txtPartNum.setText(generatePartNumber(cust, year, part));
							
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
		}
		
			private void setupPanel()
						{
							lblCreated.setFont(new Font("Tahoma", Font.BOLD, 14));
							lblCreated.setForeground(Color.BLACK);
							lblCreatedBy.setFont(new Font("Tahoma", Font.BOLD, 14));
							lblCreatedBy.setForeground(Color.BLACK);
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
							ButtonGroup group = new ButtonGroup();
							group.add(rbtnCreate);
							group.add(rbtnSearch);
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
										.addComponent(txtPartNum, GroupLayout.PREFERRED_SIZE, 93, GroupLayout.PREFERRED_SIZE))
									.addGroup(groupLayout.createSequentialGroup()
										.addGap(61)
										.addComponent(lblCreated, GroupLayout.PREFERRED_SIZE, 96, GroupLayout.PREFERRED_SIZE)
										.addGap(12)
										.addComponent(lblCreatedBy, GroupLayout.PREFERRED_SIZE, 162, GroupLayout.PREFERRED_SIZE)
										.addGap(201)
										.addComponent(lblCustomerPartNum, GroupLayout.PREFERRED_SIZE, 177, GroupLayout.PREFERRED_SIZE))
									.addGroup(groupLayout.createSequentialGroup()
										.addGap(61)
										.addComponent(txtCreated, GroupLayout.PREFERRED_SIZE, 73, GroupLayout.PREFERRED_SIZE)
										.addGap(35)
										.addComponent(txtCreatedBy, GroupLayout.PREFERRED_SIZE, 109, GroupLayout.PREFERRED_SIZE)
										.addGap(254)
										.addComponent(txtCustomerPartNum, GroupLayout.PREFERRED_SIZE, 184, GroupLayout.PREFERRED_SIZE))
									.addGroup(groupLayout.createSequentialGroup()
										.addGap(61)
										.addComponent(rbtnSearch, GroupLayout.PREFERRED_SIZE, 109, GroupLayout.PREFERRED_SIZE))
									.addGroup(groupLayout.createSequentialGroup()
										.addGap(61)
										.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
											.addComponent(rbtnCreate, GroupLayout.PREFERRED_SIZE, 109, GroupLayout.PREFERRED_SIZE)
											.addGroup(groupLayout.createSequentialGroup()
												.addGap(108)
												.addComponent(txtSearchPart, GroupLayout.PREFERRED_SIZE, 109, GroupLayout.PREFERRED_SIZE)))
										.addGap(10)
										.addComponent(btnCheck, GroupLayout.PREFERRED_SIZE, 106, GroupLayout.PREFERRED_SIZE)
										.addGap(91)
										.addComponent(btnBack, GroupLayout.PREFERRED_SIZE, 106, GroupLayout.PREFERRED_SIZE)
										.addGap(22)
										.addComponent(btnSave, GroupLayout.PREFERRED_SIZE, 106, GroupLayout.PREFERRED_SIZE))
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
											.addGroup(groupLayout.createSequentialGroup()
												.addGap(3)
												.addComponent(lblCustomer, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE))
											.addComponent(lblProgram, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
											.addComponent(lblPartDescrip, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
											.addGroup(groupLayout.createSequentialGroup()
												.addGap(3)
												.addComponent(lblYear, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE))
											.addGroup(groupLayout.createSequentialGroup()
												.addGap(3)
												.addComponent(lblSearchPart, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE)))
										.addGap(5)
										.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
											.addComponent(cboCustomer, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
											.addComponent(cboProgram, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
											.addComponent(cboPartDescrip, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
											.addComponent(cboYear, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
											.addComponent(txtPartNum, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE))
										.addGap(13)
										.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
											.addComponent(lblCreated, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE)
											.addComponent(lblCreatedBy, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE)
											.addGroup(groupLayout.createSequentialGroup()
												.addGap(5)
												.addComponent(lblCustomerPartNum, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE)))
										.addGap(6)
										.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
											.addComponent(txtCreated, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
											.addComponent(txtCreatedBy, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
											.addComponent(txtCustomerPartNum, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
										.addGap(16)
										.addComponent(rbtnSearch)
										.addGap(3)
										.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
											.addGroup(groupLayout.createSequentialGroup()
												.addGap(5)
												.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
													.addComponent(rbtnCreate)
													.addGroup(groupLayout.createSequentialGroup()
														.addGap(2)
														.addComponent(txtSearchPart, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
											.addComponent(btnCheck, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
											.addComponent(btnBack, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
											.addComponent(btnSave, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)))
							);
							setLayout(groupLayout);
						}}//End of Class Experimental Parts Panel	
}//End of MainFrames

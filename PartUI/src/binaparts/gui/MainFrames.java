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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
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
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

import org.json.*;
import org.jdesktop.swingx.JXDatePicker;
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
		Toolkit tk = Toolkit.getDefaultToolkit();
	    Dimension screenSize = tk.getScreenSize();
	    int screenHeight = screenSize.height;
	    int screenWidth = screenSize.width;
	    frame.setSize(screenWidth / 2, screenHeight / 2);
	    frame.setLocation(screenWidth / 4, screenHeight / 4);
		frame.setResizable(false);
		frame.setSize(645, 545);
		frame.setContentPane(contentPane);
		frame.setVisible(true);	
		
		try{
			frame.setIconImage(ImageIO.read(new File("res/bosalimage.png")));
		}catch(Exception ex){ex.printStackTrace();}
	}
	public String DateToCalendar(Date date) {			
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		String s = Integer.toString(cal.get(Calendar.MONTH)+1)+"/"
					+Integer.toString(cal.get(Calendar.DAY_OF_MONTH))+"/"
					+Integer.toString(cal.get(Calendar.YEAR));
		return s;
	}
	public Date CalendarToDate(String date) {			
		String[] parts = date.split("/");
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, Integer.valueOf(parts[2]));
		cal.set(Calendar.MONTH, Integer.valueOf(parts[0])-1);
		cal.set(Calendar.DAY_OF_MONTH, Integer.valueOf(parts[1]));		
		return cal.getTime();
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
				Bosal.setBounds(280, 30, 194, 56);
				ImageIcon mainI = new ImageIcon(getClass().getResource("/images/mainpic.jpg"));
				lblMainPic = new JLabel(mainI);
				lblMainPic.setBounds(165, 86, 430, 407);
				
			//Buttons
				
				ImageIcon experiment = new ImageIcon(getClass().getResource("/images/Experimental.jpg"));
				btnExperimental = new JButton(experiment);
				btnExperimental.setBounds(33, 296, 106, 35);
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
				btnBDL.setBounds(33, 250, 106, 35);
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
				btnManageUsers.setBounds(33, 388, 106, 35);
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
				btnTest.setBounds(33, 434, 106, 35);
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
				btnSetting.setBounds(33, 342, 106, 35);
				btnSetting.addActionListener(new ActionListener() {
							
					public void actionPerformed(ActionEvent e) 
					{
						if (e.getSource() == btnSetting) {
							setVisible(false);
							frame.setSize(600,340);
							settings.setVisible(true);
							frame.setTitle("Settings:");
							frame.setResizable(false);
							frame.setLocationRelativeTo(main);
				}}});
				
				ImageIcon createI = new ImageIcon(getClass().getResource("/images/createpart.jpg"));
				btnCreatePart = new JButton(createI);
				btnCreatePart.setBounds(33, 112, 106, 35);
				btnCreatePart.addActionListener(new ActionListener() {
				
					public void actionPerformed(ActionEvent e)
					{
						if (e.getSource() == btnCreatePart){
							try{
								if(con.verifyUser() == true){
									setStatus();
									setVisible(false);
									frame.setSize(800,390);
									frame.setTitle("Create Part:");
									frame.setResizable(false);
									frame.setLocationRelativeTo(main);
									main.add(create);
									create.setVisible(true);
								}else{setStatus();}
							}catch(Exception ex){ex.printStackTrace();}
				}}});
			
				ImageIcon updateI = new ImageIcon(getClass().getResource("/images/updatepart.jpg"));
				btnUpdatePart = new JButton(updateI);
				btnUpdatePart.setBounds(33, 158, 106, 35);
				btnUpdatePart.addActionListener(new ActionListener() {
					
					public void actionPerformed(ActionEvent e) {
						if (e.getSource() == btnUpdatePart){
							try{
								if(con.verifyUser() == true){
									setStatus();
									setVisible(false);
									frame.setSize(790,455);
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
				btnFindPartInfo.setBounds(33, 204, 106, 35);
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
							}catch(Exception ex){ex.printStackTrace();/*ignore*/}
				}}});
				setupPanel();
			}
			
			private void setupPanel()
			{
				setLayout(null);
				status.setBounds(0, 10, 250, 10);
				add(status);
				add(Bosal);
				add(btnCreatePart);
				add(btnUpdatePart);
				add(btnFindPartInfo);
				add(btnBDL);
				add(btnExperimental);
				add(btnSetting);
				add(btnManageUsers);
				add(btnTest);
				add(lblMainPic);
			}
	}
	class CreatePanel extends JPanel
	{
	
//JLabel
		private JLabel lblSeq;
		private JLabel lblDescription;
		private JLabel lblMaterialDescription;
		private JLabel lblTypeDescription;
		private JLabel lblType;
		private JLabel lblMatterial;
		private JLabel lblBosalPartNumber;
		private JLabel lblCustomerPartNumber;
		private JLabel lblCustDrawingNum;
		private JLabel lblCustDrawingRev;
		private JLabel lblCustDrawingRevDate;
		private JLabel lblSupplierPartNumber;
		private JLabel lblCreateAPart;
		private JLabel lblBosal;
		private JLabel lblProgram;
		private JLabel lblDrawingNum;
		private JLabel lblDrawingRev;
		private JLabel lblDrawingRevDate;
		private JLabel lblProductionReleaseDate;
		
	//JTextField
		private JTextField txtDescrip;
		private JTextField txtMDescrip;
		private JTextField txtDrawingNum;
		private JTextField txtDrawingRev;
		private JTextField txtSeq;
		private JTextField txtBPart;
		private JTextField txtCPart;
		private JTextField txtCustDrawingRev;
		private JTextField txtCustDrawingNum;
		private JTextField txtSPart;
		
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
		
	//JXDatePickers
		
		private JXDatePicker jxdProductionReleaseDate;
		private JXDatePicker jxdCustomerDrawingRevDate;
		private JXDatePicker jxdDrawingRevDate;
		
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
	        lblType.setBounds(24, 90, 34, 17);
	        lblType.setFont(new Font("Tahoma", Font.BOLD, 14));
			lblType.setForeground(Color.BLACK);
	        lblMatterial = new JLabel("Material");
	        lblMatterial.setBounds(24, 142, 54, 17);
			lblMatterial.setFont(new Font("Tahoma", Font.BOLD, 14));
			lblMatterial.setForeground(Color.BLACK);
	        lblTypeDescription = new JLabel("Type Description");
	        lblTypeDescription.setBounds(137, 90, 115, 17);
			lblTypeDescription.setFont(new Font("Tahoma", Font.BOLD, 14));
			lblTypeDescription.setForeground(Color.BLACK);
	        lblMaterialDescription = new JLabel("Material Description");
	        lblMaterialDescription.setBounds(137, 142, 135, 17);
			lblMaterialDescription.setFont(new Font("Tahoma", Font.BOLD, 14));
			lblMaterialDescription.setForeground(Color.BLACK);
	        lblDescription = new JLabel("Description");
	        lblDescription.setBounds(137, 194, 77, 17);
			lblDescription.setFont(new Font("Tahoma", Font.BOLD, 14));
			lblDescription.setForeground(Color.BLACK);
	        lblSeq = new JLabel("Seq");
	        lblSeq.setBounds(24, 194, 26, 17);
			lblSeq.setFont(new Font("Tahoma", Font.BOLD, 14));
			lblSeq.setForeground(Color.BLACK);
	        lblSupplierPartNumber = new JLabel("Supplier Part Number");
	        lblSupplierPartNumber.setBounds(372, 194, 149, 17);
			lblSupplierPartNumber.setFont(new Font("Tahoma", Font.BOLD, 14));
			lblSupplierPartNumber.setForeground(Color.BLACK);
	        lblCreateAPart = new JLabel("Create a Part Number");
	        lblCreateAPart.setBounds(229, 27, 415, 52);
			lblCreateAPart.setFont(new Font("Tahoma", Font.BOLD, 32));
			lblCreateAPart.setForeground(Color.BLACK);
	        lblCustomerPartNumber = new JLabel("Customer Part Number");
	        lblCustomerPartNumber.setBounds(372, 142, 161, 17);
			lblCustomerPartNumber.setFont(new Font("Tahoma", Font.BOLD, 14));
			lblCustomerPartNumber.setForeground(Color.BLACK);
	        lblCustDrawingNum = new JLabel("Cust Drawing Number");
	        lblCustDrawingNum.setBounds(178, 295, 170, 20);
			lblCustDrawingNum.setFont(new Font("Tahoma", Font.BOLD, 14));
			lblCustDrawingNum.setForeground(Color.BLACK);
	        lblCustDrawingRev = new JLabel("Cust Drawing Rev");
	        lblCustDrawingRev.setBounds(372, 295, 160, 20);
	        lblCustDrawingRev.setFont(new Font("Tahoma", Font.BOLD, 14));
	        lblCustDrawingRev.setForeground(Color.BLACK);
	        lblCustDrawingRevDate = new JLabel("Cust Drawing Rev Date");
	        lblCustDrawingRevDate.setBounds(551, 88, 170, 20);
	        lblCustDrawingRevDate.setFont(new Font("Tahoma", Font.BOLD, 14));
	        lblCustDrawingRevDate.setForeground(Color.BLACK);
	        lblBosalPartNumber = new JLabel("Bosal Part Number");
	        lblBosalPartNumber.setBounds(372, 90, 130, 17);
			lblBosalPartNumber.setFont(new Font("Tahoma", Font.BOLD, 14));
			lblBosalPartNumber.setForeground(Color.BLACK);
	        lblProgram = new JLabel("Program");
	        lblProgram.setBounds(24, 243, 70, 23);
			lblProgram.setFont(new Font("Tahoma", Font.BOLD, 14));
			lblProgram.setForeground(Color.BLACK);
	        lblDrawingNum = new JLabel("Drawing Number");
	        lblDrawingNum.setBounds(178, 244, 156, 20);
			lblDrawingNum.setFont(new Font("Tahoma", Font.BOLD, 14));
			lblDrawingNum.setForeground(Color.BLACK);
	        lblDrawingRev = new JLabel("Drawing Rev");
	        lblDrawingRev.setBounds(372, 244, 90, 20);
	        lblDrawingRev.setFont(new Font("Tahoma", Font.BOLD, 14));
	        lblDrawingRev.setForeground(Color.BLACK);
	        lblDrawingRevDate = new JLabel("Drawing Rev Date");
	        lblDrawingRevDate.setBounds(551, 192, 138, 20);
	        lblDrawingRevDate.setFont(new Font("Tahoma", Font.BOLD, 14));
	        lblDrawingRevDate.setForeground(Color.BLACK);
	        lblProductionReleaseDate= new JLabel("Production Release Date");
	        lblProductionReleaseDate.setBounds(551, 140, 177, 20);
	        lblProductionReleaseDate.setFont(new Font("Tahoma", Font.BOLD, 14));
	        lblProductionReleaseDate.setForeground(Color.BLACK);
	        
	        ImageIcon bosal = new ImageIcon(getClass().getResource("/images/bosal.jpg"));
	        lblBosal = new JLabel(bosal);
	        lblBosal.setBounds(20, 32, 199, 42);
	        setBackground(new Color(105, 105, 105));
			
		//TextFields
				
			txtDescrip = new JTextField();
			txtDescrip.setBounds(137, 117, 211, 20);
			txtDescrip.setBackground(new Color(190, 190, 190));
			txtDescrip.setForeground(Color.BLACK);
			txtDescrip.addMouseListener(new ContextMenuMouseListener());
			txtDescrip.setEditable(false);
			txtMDescrip = new JTextField();
			txtMDescrip.setBounds(137, 165, 211, 20);
			txtMDescrip.setBackground(new Color(190, 190, 190));
			txtMDescrip.setForeground(Color.BLACK);
			txtMDescrip.addMouseListener(new ContextMenuMouseListener());
			txtMDescrip.setEditable(false);
			txtSeq = new JTextField();
			txtSeq.setBounds(24, 217, 79, 20);
			txtSeq.setBackground(new Color(190, 190, 190));
			txtSeq.setForeground(Color.BLACK);
			txtSeq.addMouseListener(new ContextMenuMouseListener());
			txtSeq.setEditable(false);
			txtBPart = new JTextField();
			txtBPart.setBounds(372, 117, 156, 20);
			txtBPart.setEditable(false);
			txtBPart.setBackground(new Color(190, 190, 190));
			txtBPart.setForeground(Color.BLACK);
			txtBPart.addMouseListener(new ContextMenuMouseListener());			
			txtCPart = new JTextField();
			txtCPart.setBounds(372, 165, 156, 20);
			txtCPart.setForeground(Color.BLACK);
			txtCPart.addMouseListener(new ContextMenuMouseListener());
			txtCustDrawingNum = new JTextField();
			txtCustDrawingNum.setBounds(178, 321, 156, 20);
			txtCustDrawingNum.setForeground(Color.BLACK);
			txtCustDrawingNum.addMouseListener(new ContextMenuMouseListener());
			txtCustDrawingRev = new JTextField();
			txtCustDrawingRev.setBounds(372, 321, 156, 20);
			txtCustDrawingRev.setForeground(Color.BLACK);
			txtCustDrawingRev.addMouseListener(new ContextMenuMouseListener());
			txtSPart = new JTextField();
			txtSPart.setBounds(372, 217, 156, 20);
			txtSPart.setForeground(Color.BLACK);
			txtSPart.addMouseListener(new ContextMenuMouseListener());
			txtDrawingNum = new JTextField();
			txtDrawingNum.setBounds(178, 270, 156, 20);
			txtDrawingNum.setForeground(Color.BLACK);
			txtDrawingNum.addMouseListener(new ContextMenuMouseListener());
			txtDrawingRev = new JTextField();
			txtDrawingRev.setBounds(372, 270, 156, 20);
			txtDrawingRev.setForeground(Color.BLACK);
			txtDrawingRev.addMouseListener(new ContextMenuMouseListener());
			
		//ComboBoxes
				
			cboType = new JComboBox<String>();
			cboType.setBounds(24, 116, 79, 23);
			cboType.setEditable(false);
			cboType.setForeground(Color.BLACK);
			AutoCompleteDecorator.decorate(cboType);
			cboType.setModel(resetTypeComboBox());
			cboType.setSelectedIndex(-1);
			cboType.addMouseListener(new ContextMenuMouseListener());
			cboMat = new JComboBox<String>();
			cboMat.setBounds(24, 165, 79, 20);
			cboMat.setEditable(false);
			cboMat.setForeground(Color.BLACK);
			AutoCompleteDecorator.decorate(cboMat);
			cboMat.setModel(resetMatComboBox());
			cboMat.addMouseListener(new ContextMenuMouseListener());
			cboDescrip = new JComboBox<String>();
			cboDescrip.setBounds(137, 217, 211, 20);
			cboDescrip.setEditable(true);
			cboDescrip.setForeground(Color.BLACK);
			AutoCompleteDecorator.decorate(cboDescrip);
			cboDescrip.addMouseListener(new ContextMenuMouseListener());
			cboDescrip.setModel(resetDescripComboBox());
			cboDescrip.setSelectedIndex(-1);
			cboProgram = new JComboBox<String>();
			cboProgram.setBounds(24, 270, 115, 20);
			cboProgram.setEditable(true);
			cboProgram.setForeground(Color.BLACK);
			AutoCompleteDecorator.decorate(cboProgram);
			cboProgram.addMouseListener(new ContextMenuMouseListener());
			cboProgram.setModel(resetProgramComboBox());
			cboProgram.setSelectedIndex(-1);
			
		//JXDatePickers
			
			DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
			jxdProductionReleaseDate = new JXDatePicker();
			jxdProductionReleaseDate.addMouseListener(new ContextMenuMouseListener());
			jxdProductionReleaseDate.setFormats(dateFormat);
			jxdProductionReleaseDate.setBounds(551, 165, 156, 20);
			jxdProductionReleaseDate.setForeground(Color.BLACK);
			jxdCustomerDrawingRevDate = new JXDatePicker();
			jxdCustomerDrawingRevDate.addMouseListener(new ContextMenuMouseListener());
			jxdCustomerDrawingRevDate.setFormats(dateFormat);
			jxdCustomerDrawingRevDate.setBounds(551, 117, 156, 20);
			jxdCustomerDrawingRevDate.setForeground(Color.BLACK);
			jxdDrawingRevDate = new JXDatePicker();
			jxdDrawingRevDate.addMouseListener(new ContextMenuMouseListener());
			jxdDrawingRevDate.setFormats(dateFormat);
			jxdDrawingRevDate.setBounds(551, 217, 156, 20);
			jxdDrawingRevDate.setForeground(Color.BLACK);
			
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
			
			ImageIcon save = new ImageIcon(getClass().getResource("/images/save.jpg"));
			btnSave = new JButton(save);
			btnSave.setBounds(601, 306, 106, 35);
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
								String DrawingNumber = txtDrawingNum.getText();
								int DrawingRev;
								try{
									DrawingRev = Integer.valueOf(txtDrawingRev.getText());
								}catch(Exception ex){DrawingRev = 0;}
								String DrawingRevDate = DateToCalendar(jxdDrawingRevDate.getDate());
								String CustomerPartNumber = txtCPart.getText();
								String CustDrawingNumber = txtCustDrawingNum.getText();
								int CustDrawingRev;
								try{
									CustDrawingRev = Integer.valueOf(txtCustDrawingRev.getText());
								}catch(Exception ex){CustDrawingRev = 0;}
								String CustDrawingRevDate = DateToCalendar(jxdCustomerDrawingRevDate.getDate());
								String SupplierPartNumber = txtSPart.getText();
								String Description = (String) cboDescrip.getSelectedItem();
								String Program = (String) cboProgram.getSelectedItem();
								int Seq = Integer.valueOf(txtSeq.getText());
								String TypeDescription = txtDescrip.getText();
								int Rev = 0;
								String ProductionReleaseDate = DateToCalendar(jxdProductionReleaseDate.getDate());
								con.insertNewPart(PartType, Material, BosalPartNumber, DrawingNumber, DrawingRev, 
										DrawingRevDate, CustomerPartNumber, CustDrawingNumber, CustDrawingRev, 
										CustDrawingRevDate, SupplierPartNumber, Description, Program, Seq, 
										TypeDescription, Rev, ProductionReleaseDate);							
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
								txtDrawingRev.setText("");
							}catch(Exception ex){ex.printStackTrace();};
			}}}});
			
			ImageIcon back = new ImageIcon(getClass().getResource("/images/back.jpg"));
			btnBack = new JButton(back);
			btnBack.setBounds(601, 255, 106, 35);
			btnBack.addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent e) 
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
					txtDrawingRev.setText("");
					
		}});
		setupPanel();	
	}		
		private void setupPanel() 
		{
	        setLayout(null);
	        add(lblBosal);
	        add(lblCreateAPart);
	        add(lblType);
	        add(lblTypeDescription);
	        add(lblBosalPartNumber);
	        add(lblProductionReleaseDate);
	        add(cboType);
	        add(txtDescrip);
	        add(txtBPart);
	        add(lblMatterial);
	        add(lblMaterialDescription);
	        add(lblCustomerPartNumber);
	        add(cboMat);
	        add(txtMDescrip);
	        add(txtCPart);
	        add(lblDrawingRevDate);
	        add(lblCustDrawingRevDate);
	        add(lblSeq);
	        add(lblDescription);
	        add(lblSupplierPartNumber);
	        add(txtSeq);
	        add(cboDescrip);
	        add(txtSPart);
	        add(lblProgram);
	        add(cboProgram);
	        add(lblDrawingNum);
	        add(txtDrawingNum);
	        add(lblDrawingRev);
	        add(txtDrawingRev);
	        add(lblCustDrawingNum);
	        add(txtCustDrawingNum);
	        add(lblCustDrawingRev);
	        add(txtCustDrawingRev);
	        add(btnBack);
	        add(btnSave);
	        add(jxdCustomerDrawingRevDate);
	        add(jxdProductionReleaseDate);
	        add(jxdDrawingRevDate);
	}
}
	class UpdatePanel extends JPanel
	{
			//JLabels
	private JLabel lblBosal;
	private JLabel lblUpdatePart;
	private JLabel lblBosalPartNum;
	private JLabel lblCustomerPartNum;
	private JLabel lblCustDrawingNum;
	private JLabel lblCustDrawingRev;
	private JLabel lblCustDrawingRevDate;
	private JLabel lblSupplierPartNum;
	private JLabel lblDescription;
	private JLabel lblProgram;
	private JLabel lblRev;
	private JLabel lblDrawingNum;
	private JLabel lblDrawingRev;
	private JLabel lblDrawingRevDate;
	private JLabel lblProductionReleaseDate;
	
	//JButtons
	private JButton btnSave;
	private JButton btnBack;
	private JButton btnCheck;
	private JButton btnDelete;
	
	//JRadioButton
	private JRadioButton rbtnEurope;
	private JRadioButton rbtnAmerica;
	
	//JTextFields
	private JTextField txtFindBosal;
	private JTextField txtCusDescrip;
	private JTextField txtSupDescrip;
	private JTextField txtRev;
	private JTextField txtDrawingNum;
	private JTextField txtDrawingRev;
	private JTextField txtCustDrawingNum;
	private JTextField txtCustDrawingRev;
	
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
	
	//JXDatePickers
	
	private JXDatePicker jxdCustomerDrawingRevDate;
	private JXDatePicker jxdProductionReleaseDate;
	private JXDatePicker jxdDrawingRevDate;
	
	//Update Panel		
	public UpdatePanel(final JPanel update) 
	{
		setBackground(new Color(105, 105, 105));
	
	//TextFields		
		
		txtFindBosal = new JTextField();
		txtFindBosal.setBounds(28, 160, 174, 20);
		txtFindBosal.setForeground(Color.BLACK);
		txtFindBosal.addMouseListener(new ContextMenuMouseListener());
		txtCusDescrip = new JTextField();
		txtCusDescrip.setBounds(28, 383, 174, 20);
		txtCusDescrip.setForeground(Color.BLACK);
		txtCusDescrip.addMouseListener(new ContextMenuMouseListener());
		txtSupDescrip = new JTextField();
		txtSupDescrip.setBounds(238, 218, 174, 20);
		txtSupDescrip.setForeground(Color.BLACK);
		txtSupDescrip.addMouseListener(new ContextMenuMouseListener());
		txtRev = new JTextField();
		txtRev.setBounds(28, 327, 174, 20);
		txtRev.setEditable(true);
		txtRev.setForeground(Color.BLACK);
		txtRev.addMouseListener(new ContextMenuMouseListener());
		txtDrawingNum = new JTextField();
		txtDrawingNum.setBounds(238, 269, 174, 20);
		txtDrawingNum.setForeground(Color.BLACK);
		txtDrawingNum.addMouseListener(new ContextMenuMouseListener());
		txtDrawingRev = new JTextField();
		txtDrawingRev.setBounds(238, 327, 174, 20);
		txtDrawingRev.setForeground(Color.BLACK);
		txtDrawingRev.addMouseListener(new ContextMenuMouseListener());
		txtCustDrawingNum = new JTextField();
		txtCustDrawingNum.setBounds(449, 383, 174, 20);
		txtCustDrawingNum.setForeground(Color.BLACK);
		txtCustDrawingNum.addMouseListener(new ContextMenuMouseListener());
		txtCustDrawingRev = new JTextField();
		txtCustDrawingRev.setBounds(238, 383, 174, 20);
		txtCustDrawingRev.setForeground(Color.BLACK);
		txtCustDrawingRev.addMouseListener(new ContextMenuMouseListener());
					
	//JComboBoxes
		
		cboDescrip = new JComboBox<String>();
		cboDescrip.setBounds(28, 218, 174, 20);
		cboDescrip.setEditable(true);
		cboDescrip.setForeground(Color.BLACK);
		AutoCompleteDecorator.decorate(cboDescrip);			
		cboDescrip.addMouseListener(new ContextMenuMouseListener());
		cboDescrip.setModel(resetDescripComboBox());
		cboDescrip.setSelectedIndex(-1);
		cboProgram = new JComboBox<String>();
		cboProgram.setBounds(30, 272, 174, 20);
		cboProgram.setEditable(true);
		cboProgram.setForeground(Color.BLACK);
		AutoCompleteDecorator.decorate(cboProgram);
		cboProgram.addMouseListener(new ContextMenuMouseListener());
		cboProgram.setModel(resetProgramComboBox());			
		cboProgram.setSelectedIndex(-1);
		
	//JXDatePickers
		
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		jxdCustomerDrawingRevDate = new JXDatePicker();
		jxdCustomerDrawingRevDate.addMouseListener(new ContextMenuMouseListener());
		jxdCustomerDrawingRevDate.setFormats(dateFormat);
		jxdCustomerDrawingRevDate.setBounds(449, 327, 174, 20);
		jxdCustomerDrawingRevDate.setForeground(Color.BLACK);
		jxdProductionReleaseDate = new JXDatePicker();
		jxdProductionReleaseDate.addMouseListener(new ContextMenuMouseListener());
		jxdProductionReleaseDate.setFormats(dateFormat);
		jxdProductionReleaseDate.setBounds(449, 272, 174, 20);
		jxdProductionReleaseDate.setForeground(Color.BLACK);
		jxdDrawingRevDate = new JXDatePicker();
		jxdDrawingRevDate.addMouseListener(new ContextMenuMouseListener());
		jxdDrawingRevDate.setFormats(dateFormat);
		jxdDrawingRevDate.setBounds(449, 218, 174, 20);
		jxdDrawingRevDate.setForeground(Color.BLACK);
		
	//Labels		
		
		lblBosalPartNum = new JLabel("Bosal Part Number");
		lblBosalPartNum.setBounds(28, 132, 130, 17);
		lblDescription = new JLabel("Description");
		lblDescription.setBounds(28, 193, 77, 17);
		lblCustomerPartNum = new JLabel("Customer Part Number");
		lblCustomerPartNum.setBounds(28, 355, 161, 17);
		lblSupplierPartNum = new JLabel("Supplier Part Number");
		lblSupplierPartNum.setBounds(238, 193, 149, 17);
		lblUpdatePart = new JLabel("Update Part");
		lblUpdatePart.setBounds(214, 25, 223, 39);
		lblProgram = new JLabel("Program");
		lblProgram.setBounds(28, 234, 223, 39);
		lblRev = new JLabel("Rev Number");
		lblRev.setBounds(28, 297, 94, 22);
		lblDrawingNum = new JLabel("Drawing Number");
		lblDrawingNum.setBounds(238, 234, 223, 39);
		lblDrawingRev = new JLabel("Drawing Rev");
		lblDrawingRev.setBounds(238, 298, 100, 20);
		lblDrawingRev.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblDrawingRev.setForeground(Color.BLACK);
		lblDrawingRevDate = new JLabel("Drawing Rev Date");
		lblDrawingRevDate.setBounds(449, 191, 130, 20);
		lblDrawingRevDate.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblDrawingRevDate.setForeground(Color.BLACK);
		lblProductionReleaseDate= new JLabel("Production Release Date");
		lblProductionReleaseDate.setBounds(449, 243, 174, 20);
		lblProductionReleaseDate.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblProductionReleaseDate.setForeground(Color.BLACK);
		lblCustDrawingNum = new JLabel("Cust Drawing Number");
		lblCustDrawingNum.setBounds(449, 352, 223, 20);
		lblCustDrawingRev = new JLabel("Cust Drawing Rev");
		lblCustDrawingRev.setBounds(238, 353, 123, 20);
		lblCustDrawingRev.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblCustDrawingRev.setForeground(Color.BLACK);
		lblCustDrawingRevDate = new JLabel("Cust Drawing Rev Date");
		lblCustDrawingRevDate.setBounds(449, 298, 174, 20);
		lblCustDrawingRevDate.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblCustDrawingRevDate.setForeground(Color.BLACK);
		
	//JRadioButton
		rbtnEurope = new JRadioButton("Europe Part Number");
		rbtnEurope.setBackground(new Color(105, 105, 105));
		rbtnEurope.setBounds(212, 85, 168, 22);
		rbtnEurope.setFont(new Font("Tahoma", Font.BOLD, 14));
		rbtnEurope.setForeground(Color.BLACK);
		rbtnAmerica = new JRadioButton("Bosal Part Number");
		rbtnAmerica.setBackground(new Color(105, 105, 105));
		rbtnAmerica.setBounds(39, 85, 178, 22);
		rbtnAmerica.setFont(new Font("Tahoma", Font.BOLD, 14));
		rbtnAmerica.setForeground(Color.BLACK);
		rbtnAmerica.doClick();
		
	//Images
		
		ImageIcon bosal = new ImageIcon(getClass().getResource("/images/bosal.jpg"));
		lblBosal = new JLabel(bosal);
		lblBosal.setBounds(10, 15, 194, 56);
		
	//Buttons		
	
		ImageIcon delete = new ImageIcon(getClass().getResource("/images/delete.jpg"));
		btnDelete = new JButton(delete);
		btnDelete.setBounds(650, 368, 106, 35);
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
					rbtnAmerica.doClick();
				}}});
		
		ImageIcon back = new ImageIcon(getClass().getResource("/images/back.jpg"));
		btnBack = new JButton(back);
		btnBack.setBounds(650, 283, 106, 35);
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
					rbtnAmerica.doClick();
					jxdProductionReleaseDate.setDate(null);
		            jxdCustomerDrawingRevDate.setDate(null);
		            jxdDrawingRevDate.setDate(null);
				}}});
		
		ImageIcon save = new ImageIcon(getClass().getResource("/images/save.jpg"));
		btnSave = new JButton(save);
		btnSave.setBounds(650, 203, 106, 35);
		btnSave.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == btnSave)
				{
				if (rbtnAmerica.isSelected() == true)
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
						String DrawingNumber = null;							
						if(txtDrawingNum.getText().equals("-") || txtDrawingNum.getText().equals("")){
							DrawingNumber = null;
						}else{DrawingNumber = txtDrawingNum.getText();}
						int DrawingRev;						
						if(txtDrawingRev.getText().equals("-") || txtDrawingRev.getText().equals("")){
							DrawingRev = 0;
						}else{
							try{
								DrawingRev = Integer.valueOf(txtDrawingRev.getText());
							}catch(Exception ex){DrawingRev = 0;}
						}
						String DrawingRevDate = DateToCalendar(jxdDrawingRevDate.getDate());
								
						String CustomerPartNumber = null;
						if(txtCusDescrip.getText().equals("-") || txtCusDescrip.getText().equals("")){
							CustomerPartNumber = null;
						}else{CustomerPartNumber = txtCusDescrip.getText();}						
						String CustDrawingNumber = null;							
						if(txtCustDrawingNum.getText().equals("-") || txtCustDrawingNum.getText()
								.equals("")){
							DrawingNumber = null;
						}else{CustDrawingNumber = txtCustDrawingNum.getText();}
						int CustDrawingRev;						
						if(txtCustDrawingRev.getText().equals("-") || txtCustDrawingRev.getText().equals("")){
							CustDrawingRev = 0;
						}else{
							try{
								CustDrawingRev = Integer.valueOf(txtCustDrawingRev.getText());
							}catch(Exception ex){CustDrawingRev = 0;}
						}
						String CustDrawingRevDate = DateToCalendar(jxdCustomerDrawingRevDate.getDate());
								
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
						String ProductionReleaseDate = DateToCalendar(jxdProductionReleaseDate.getDate());
															
						
						try {
							con.updateBosal(BosalPartNumber, DrawingNumber, DrawingRev, 
									DrawingRevDate, CustomerPartNumber, CustDrawingNumber, CustDrawingRev, 
									CustDrawingRevDate, SupplierPartNumber, 
									Description, Program, Rev, ProductionReleaseDate);
							
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
				}
				if (rbtnEurope.isSelected() == true)
				{
						int n = JOptionPane.showConfirmDialog(
							    frame,
							    "Are you sure you want to save part data?",
							    "Save:",
							    JOptionPane.YES_NO_OPTION,
								JOptionPane.WARNING_MESSAGE
								);
						if(n == 0){
							String DeltaPartNumber = txtFindBosal.getText();
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
							int DrawingRev;						
							if(txtDrawingRev.getText().equals("-") || txtDrawingRev.getText().equals("")){
								DrawingRev = 0;
							}else{
								try{
									DrawingRev = Integer.valueOf(txtDrawingRev.getText());
								}catch(Exception ex){/*ex.printStackTrace();Ignore*/DrawingRev = 0;}
							}
							String DrawingRevDate = DateToCalendar(jxdDrawingRevDate.getDate());
									
							String ProductionReleaseDate = DateToCalendar(jxdProductionReleaseDate.getDate());
							String CustDrawingRevDate = DateToCalendar(jxdCustomerDrawingRevDate.getDate());									
							
							try {
								con.updateDelta(DeltaPartNumber, CustomerPartNumber, SupplierPartNumber, 
										CustDrawingRevDate, Description, Program, Rev, DrawingNumber, DrawingRev, 
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
					}
				}
				rbtnAmerica.doClick();
				}});
		
		ImageIcon check = new ImageIcon(getClass().getResource("/images/check.jpg"));
		btnCheck = new JButton(check);
		btnCheck.setBounds(214, 154, 106, 35);
		btnCheck.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == btnCheck)
				{
					final String findBosalText = txtFindBosal.getText();
					JSONArray temp = null;
					try{									
						if(rbtnAmerica.isSelected() == true) {										
							temp = con.queryDatabase("bosal parts", "BosalPartNumber", findBosalText);
							if (temp.length() == 0) { 
								JOptionPane.showMessageDialog(
									    frame,
									    "Bosal Part Number: " + txtFindBosal.getText() + " does not exist",
									    "Missing Part Number",
										JOptionPane.ERROR_MESSAGE);
								return;
							}
						} else if (rbtnEurope.isSelected() == true)	{
							temp = con.queryDatabase("delta 1 parts", "DeltaPartNumber", findBosalText);
							if (temp.length() == 0) { 
								JOptionPane.showMessageDialog(
									    frame,
									    "Delta Part Number: " + txtFindBosal.getText() + " does not exist",
									    "Missing Part Number",
										JOptionPane.ERROR_MESSAGE);
								return;
							}
						}
						//set text for CusPartNumber JTextField
						String cpartText= null;
						try{
							cpartText = temp.getJSONObject(0).getString("CustPartNumber").toString();
						}catch(Exception ex){cpartText = "-";}
						txtCusDescrip.setText(cpartText);
						
						//set text for SupPartNumber JTextField
						String spartText= null;
						try{
							spartText = temp.getJSONObject(0).getString("SupPartNumber").toString();
						}catch(Exception ex){spartText = "-";}
						txtSupDescrip.setText(spartText);
						
						//set text for Description JComboBox
						String descripText= null;
						try{
							descripText = temp.getJSONObject(0).getString("PartDescription").toString();
						}catch(Exception ex){descripText = "-";}
						cboDescrip.setSelectedItem(descripText);
						
						//set text for Program JComboBox
						String programText = null;
						try{
							programText = temp.getJSONObject(0).getString("Program").toString();
						}catch(Exception ex){programText = "-";}
						cboProgram.setSelectedItem(programText);
						
						//set text for Rev JTextField
						int Rev = 0;
						try{
							Rev = Integer.valueOf(temp.getJSONObject(0).getString("Rev").toString());
						}catch(Exception ex){Rev = 0;}
						txtRev.setText(Integer.toString(Rev));
						
						//set text for DrawingNumber JTextField
						String DrawingNumber = null;
						try{
							DrawingNumber = temp.getJSONObject(0).getString("DrawingNumber").toString();
						}catch(Exception ex){DrawingNumber = "-";}
						txtDrawingNum.setText(DrawingNumber);
						
						//set text for DrawingRev JTextField
						String DrawingRev = null;
						try{
							DrawingRev = temp.getJSONObject(0).getString("DrawingRev").toString();
						}catch(Exception ex){DrawingRev = "-";}
						txtDrawingRev.setText(DrawingRev);
						
						Date drawingRevDate = null;
						try {
							drawingRevDate = CalendarToDate((String)temp.getJSONObject(0).get("DrawingRevDate"));
							jxdDrawingRevDate.setDate(drawingRevDate);
						} catch (Exception ex) {drawingRevDate = null;}
						
						Date customerDrawingRevDate = null;
						try {
							customerDrawingRevDate = CalendarToDate((String)temp.getJSONObject(0).get("CustDrawingRevDate"));
							jxdCustomerDrawingRevDate.setDate(customerDrawingRevDate);
						} catch (Exception ex) {customerDrawingRevDate = null;}
						
						//set text for ProductionReleaseDate JComboBoxes
						Date productionReleaseDate = null;
						try {
							productionReleaseDate = CalendarToDate((String)temp.getJSONObject(0).get("ProductionReleaseDate"));
							jxdProductionReleaseDate.setDate(productionReleaseDate);
						} catch (Exception ex) {productionReleaseDate = null;}
					} catch (Exception ex) {
							
						}}
			}});
		
	//ActionEvents for Radiobuttons to clear textfields
		rbtnAmerica.addActionListener(new ActionListener(){
			
			public void actionPerformed(ActionEvent e)
			{
				if (e.getSource() == rbtnAmerica){
				
		            txtFindBosal.setText("");
		            txtSupDescrip.setText("");
		            txtCusDescrip.setText("");
		            txtRev.setText("");
		            txtDrawingRev.setText("");
		            txtDrawingNum.setText("");
		            cboDescrip.setSelectedIndex(-1);
		            cboProgram.setSelectedIndex(-1);
		            txtFindBosal.requestFocusInWindow();
		            jxdProductionReleaseDate.setDate(null);
		            jxdCustomerDrawingRevDate.setDate(null);
		            jxdDrawingRevDate.setDate(null);
		           }
		}});
		
		rbtnEurope.addActionListener(new ActionListener(){
			
			public void actionPerformed(ActionEvent e)
			{
				if (e.getSource() == rbtnEurope){
				
		            txtFindBosal.setText("");
		            txtSupDescrip.setText("");
		            txtCusDescrip.setText("");
		            txtRev.setText("");
		            txtDrawingRev.setText("");
		            txtDrawingNum.setText("");
		            cboDescrip.setSelectedIndex(-1);
		            cboProgram.setSelectedIndex(-1);
		            txtFindBosal.requestFocusInWindow();
		            jxdProductionReleaseDate.setDate(null);
		            jxdCustomerDrawingRevDate.setDate(null);
		            jxdDrawingRevDate.setDate(null);
		           }
		}});
		
	//Radio Button Group
		ButtonGroup group = new ButtonGroup();
		group.add(rbtnEurope);
		group.add(rbtnAmerica);
		
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
		lblUpdatePart.setFont(new Font("Tahoma", Font.BOLD, 32));
		lblUpdatePart.setForeground(Color.BLACK);
		lblProgram.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblProgram.setForeground(Color.BLACK);
		lblRev.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblRev.setForeground(Color.BLACK);
		lblDrawingNum.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblDrawingNum.setForeground(Color.BLACK);
		lblCustDrawingNum.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblCustDrawingNum.setForeground(Color.BLACK);
		
		
		
	//Group Layout
		
		txtCusDescrip.setColumns(10);
		txtSupDescrip.setColumns(10);
		txtCusDescrip.setColumns(10);
		txtSupDescrip.setColumns(10);
		setLayout(null);
		add(lblBosal);
		add(lblUpdatePart);
		add(lblBosalPartNum);
		add(txtFindBosal);
		add(btnCheck);
		add(lblDrawingRevDate);
		add(lblCustDrawingRevDate);
		add(lblSupplierPartNum);
		add(cboProgram);
		add(txtSupDescrip);
		add(txtDrawingNum);
		add(txtCustDrawingNum);
		add(lblProgram);
		add(lblProductionReleaseDate);
		add(cboDescrip);
		add(lblDescription);
		add(lblDrawingNum);
		add(lblCustDrawingNum);
		add(btnSave);
		add(btnBack);
		add(lblRev);
		add(txtRev);
		add(lblCustomerPartNum);
		add(txtCusDescrip);
		add(lblDrawingRev);
		add(txtDrawingRev);
		add(lblCustDrawingRev);
		add(txtCustDrawingRev);
		add(btnDelete);
		add(rbtnEurope);
		add(rbtnAmerica);
		add(jxdDrawingRevDate);
		add(jxdProductionReleaseDate);
		add(jxdCustomerDrawingRevDate);
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
	txtSearch.setBounds(415, 135, 232, 20);
	txtSearch.setForeground(Color.BLACK);
	txtSearch.addMouseListener(new ContextMenuMouseListener());		
	
//RadioButtons	
	rbtnFindBosal = new JRadioButton("Bosal Part Number");
	rbtnFindBosal.setBounds(33, 85, 161, 23);
	rbtnFindBosal.setBackground(new Color(105, 105, 105));
	rbtnFindBosal.setFont(new Font("Tahoma", Font.BOLD, 14));
	rbtnFindBosal.setForeground(Color.BLACK);
	rbtnFindCus = new JRadioButton("Customer Part Number");
	rbtnFindCus.setBounds(377, 85, 194, 23);
	rbtnFindCus.setBackground(new Color(105, 105, 105));
	rbtnFindCus.setFont(new Font("Tahoma", Font.BOLD, 14));
	rbtnFindCus.setForeground(Color.BLACK);
	rbtnFindSup = new JRadioButton("Supplier Part Number");
	rbtnFindSup.setBounds(571, 85, 184, 23);
	rbtnFindSup.setBackground(new Color(105, 105, 105));
	rbtnFindSup.setFont(new Font("Tahoma", Font.BOLD, 14));
	rbtnFindSup.setForeground(Color.BLACK);
	rbtnFindPro = new JRadioButton("Program");
	rbtnFindPro.setBounds(755, 85, 184, 23);
	rbtnFindPro.setBackground(new Color(105, 105, 105));
	rbtnFindPro.setFont(new Font("Tahoma", Font.BOLD, 14));
	rbtnFindPro.setForeground(Color.BLACK);
	rbtnFindEuro = new JRadioButton("Europe Part Number");
	rbtnFindEuro.setBounds(194, 85, 183, 23);
	rbtnFindEuro.setBackground(new Color(105, 105, 105));
	rbtnFindEuro.setFont(new Font("Tahoma", Font.BOLD, 14));
	rbtnFindEuro.setForeground(Color.BLACK);
	setBackground(new Color(105, 105, 105));

//JLabels
	lblFindPartInfo = new JLabel("Find Part Information");
	lblFindPartInfo.setBounds(214, 28, 409, 39);
	
//JTable
	myTable = new JTable(){	
		public boolean isCellEditable(int row, int column){
			return false;
		}
	};
	scrollPane = new JScrollPane(myTable, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
	scrollPane.setBounds(33, 175, 804, 150);
	myTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	
//Image		
	ImageIcon bosal = new ImageIcon(getClass().getResource("/images/bosal.jpg"));
	lblBosal = new JLabel(bosal);
	lblBosal.setBounds(10, 11, 194, 56);

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
	btnSearch.setBounds(299, 129, 106, 35);
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
							try {
								//Grab the data from the correct database
								 temp = con.queryDatabase("bosal parts", "CustPartNumber", searchText);
								 myTable.setModel(populateTableModel("bosal parts", "CustPartNumber", temp, searchText));
								if (temp.length() == 0) {
									try {
										temp = con.queryDatabase("delta 1 parts", "CustPartNumber", searchText);
										myTable.setModel(populateTableModel("delta 1 parts", "CustPartNumber", temp, searchText));
									}catch(Exception ex){
								JOptionPane.showMessageDialog(
										    frame,
										    "Customer Part Number: " + searchText + " does not exist",
										    "Missing Part Number",
											JOptionPane.ERROR_MESSAGE);
						}}}catch(Exception ex){
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
	btnBack.setBounds(33, 129, 106, 35);
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
	btnSearchAll.setBounds(167, 129, 106, 35);
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
	
	lblFindPartInfo.setFont(new Font("Tahoma", Font.BOLD, 32));
	lblFindPartInfo.setForeground(Color.BLACK);
	setLayout(null);
	add(lblBosal);
	add(lblFindPartInfo);
	add(rbtnFindBosal);
	add(rbtnFindEuro);
	add(rbtnFindCus);
	add(rbtnFindSup);
	add(rbtnFindPro);
	add(btnBack);
	add(btnSearchAll);
	add(btnSearch);
	add(txtSearch);
	add(scrollPane);
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
			txtIP.setBounds(210, 119, 169, 20);
			txtIP.setForeground(Color.BLACK);
			txtIP.addMouseListener(new ContextMenuMouseListener());
			txtUser = new JTextField(config.getProperty("appUser"));
			txtUser.setBounds(210, 195, 169, 20);
			txtUser.setForeground(Color.BLACK);
			txtUser.addMouseListener(new ContextMenuMouseListener());
			ptxtPass = new JPasswordField(config.getProperty("appPassword"));
			ptxtPass.setBounds(210, 234, 169, 20);
			ptxtPass.setForeground(Color.BLACK);
			ptxtPass.addMouseListener(new ContextMenuMouseListener());
			txtDataBase = new JTextField(config.getProperty("database"));
			txtDataBase.setBounds(210, 273, 169, 20);
			txtDataBase.setForeground(Color.BLACK);
			txtDataBase.addMouseListener(new ContextMenuMouseListener());
			txtPort = new JTextField(config.getProperty("port"));
			txtPort.setBounds(210, 156, 169, 20);
			txtPort.setForeground(Color.BLACK);
			txtPort.addMouseListener(new ContextMenuMouseListener());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		 lblPort = new JLabel("Port:");
		 lblPort.setBounds(169, 156, 35, 17);
		 lblIP = new JLabel("IP Address:");
		 lblIP.setBounds(123, 119, 81, 17);
		 lblUser = new JLabel("UserName:");
		 lblUser.setBounds(128, 195, 76, 17);
		 lblPass = new JLabel("Password:");
		 lblPass.setBounds(132, 234, 72, 17);
		 lblDataBase = new JLabel("Database Name:");
		 lblDataBase.setBounds(91, 273, 113, 17);
		 lblTitle = new JLabel("Settings:");
		 lblTitle.setBounds(222, 25, 157, 39);
		
		 ImageIcon save = new ImageIcon(getClass().getResource("/images/save.jpg"));
		 btnSave = new JButton(save);
		 btnSave.setBounds(403, 212, 106, 35);
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
		 btnBack.setBounds(403, 258, 106, 35);
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
		 lblBosal.setBounds(10, 10, 194, 56);
		 
		 lblIP.setFont(new Font("Tahoma", Font.BOLD, 14));
		 lblIP.setForeground(Color.BLACK);
		 lblUser.setFont(new Font("Tahoma", Font.BOLD, 14));
		 lblUser.setForeground(Color.BLACK);
		 lblPass.setFont(new Font("Tahoma", Font.BOLD, 14));
		 lblPass.setForeground(Color.BLACK);
		 lblDataBase.setFont(new Font("Tahoma", Font.BOLD, 14));
		 lblDataBase.setForeground(Color.BLACK);
		 lblTitle.setFont(new Font("Tahoma", Font.BOLD, 32));
		 lblTitle.setForeground(Color.BLACK);
		 lblPort.setForeground(Color.BLACK);
		 lblPort.setFont(new Font("Tahoma", Font.BOLD, 14));
		 
		 setupPanel();
		
	}
	private void setupPanel()
		{
			setLayout(null);
			add(btnSave);
			add(btnBack);
			add(lblBosal);
			add(lblTitle);
			add(lblIP);
			add(txtIP);
			add(lblPort);
			add(txtPort);
			add(lblUser);
			add(lblPass);
			add(txtUser);
			add(ptxtPass);
			add(lblDataBase);
			add(txtDataBase);
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
		
<<<<<<< HEAD
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
=======
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
		boolean status = false;		
		if(password.equals("") || confirmPassword.equals("")){
		}
		if(password.equals(confirmPassword) && !password.equals("") && !confirmPassword.equals("")){
			status = true;
		}
		return status;
	}
	
//String Panel
	@SuppressWarnings("unused")
	public ManagePanel(final JPanel manage)
	{
	// Bosal Image	
	
		ImageIcon bosal = new ImageIcon(getClass().getResource("/images/bosal.jpg"));
		lblbosal = new JLabel(bosal);
		lblbosal.setBounds(10, 10, 194, 56);
		setBackground(new Color(105, 105, 105));
		
		ImageIcon checkmark = new ImageIcon(getClass().getResource("/images/checkmark.jpg"));
		ImageIcon xmark = new ImageIcon(getClass().getResource("/images/xmark.jpg"));
>>>>>>> ddf36b368d0eb0bd7b4dd7ae9e4b5787c388268e
			
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
			lblbosal.setBounds(10, 10, 194, 56);
			setBackground(new Color(105, 105, 105));
			
			ImageIcon checkmark = new ImageIcon(getClass().getResource("/images/checkmark.jpg"));
			ImageIcon xmark = new ImageIcon(getClass().getResource("/images/xmark.jpg"));
				
		//Labels	
			
			lblmanageUsers = new JLabel("Manage");
			lblmanageUsers.setBounds(230, 27, 144, 39);
			lblmanageUsers.setVisible(true);
			lblmanageUsers.setFont(new Font("Tahoma", Font.BOLD, 32));
			lblmanageUsers.setForeground(Color.BLACK);
			lblUsername = new JLabel("Username:");
			lblUsername.setBounds(319, 158, 74, 17);
			lblUsername.setVisible(true);
			lblUsername.setFont(new Font("Tahoma", Font.BOLD, 14));
			lblUsername.setForeground(Color.BLACK);
			lblPassword = new JLabel("Password:");
			lblPassword.setBounds(321, 193, 72, 17);
			lblPassword.setVisible(true);
			lblPassword.setFont(new Font("Tahoma", Font.BOLD, 14));
			lblPassword.setForeground(Color.BLACK);
			lblPassword2 = new JLabel("Confirm Password:");
			lblPassword2.setBounds(262, 228, 131, 17);
			lblPassword2.setVisible(true);
			lblPassword2.setFont(new Font("Tahoma", Font.BOLD, 14));
			lblPassword2.setForeground(Color.BLACK);
			lblRank = new JLabel("User Rank:");
			lblRank.setBounds(318, 263, 75, 17);
			lblRank.setVisible(true);
			lblRank.setFont(new Font("Tahoma", Font.BOLD, 14));
			lblRank.setForeground(Color.BLACK);
			lblPassConfirm = new JLabel("");
			lblPassConfirm.setBounds(598, 234, 150, 0);
			lblAddProgram = new JLabel("Enter New Program:");
			lblAddProgram.setBounds(251, 193, 142, 17);
			lblAddProgram.setVisible(false);
			lblAddProgram.setFont(new Font("Tahoma", Font.BOLD, 14));
			lblAddProgram.setForeground(Color.BLACK);
			lblAddCustomer = new JLabel("Enter New Customer:");
			lblAddCustomer.setBounds(243, 193, 150, 17);
			lblAddCustomer.setVisible(false);
			lblAddCustomer.setFont(new Font("Tahoma", Font.BOLD, 14));
			lblAddCustomer.setForeground(Color.BLACK);
			lblProStart = new JLabel("Program Start:");
			lblProStart.setBounds(286, 228, 107, 17);
			lblProStart.setVisible(false);
			lblProStart.setFont(new Font("Tahoma", Font.BOLD, 14));
			lblProStart.setForeground(Color.BLACK);
			lblProEnd = new JLabel("Program End:");
			lblProEnd.setBounds(297, 263, 96, 17);
			lblProEnd.setVisible(false);
			lblProEnd.setFont(new Font("Tahoma", Font.BOLD, 14));
			lblProEnd.setForeground(Color.BLACK);
			lblCust = new JLabel("Customer Abrv:");
			lblCust.setBounds(283, 228, 110, 17);
			lblCust.setVisible(false);
			lblCust.setFont(new Font("Tahoma", Font.BOLD, 14));
			lblCust.setForeground(Color.BLACK);
			lblCustomer = new JLabel("Customer:");
			lblCustomer.setBounds(319, 158, 74, 17);
			lblCustomer.setVisible(false);
			lblCustomer.setFont(new Font("Tahoma", Font.BOLD, 14));
			lblCustomer.setForeground(Color.BLACK);
			lblDeleteCust = new JLabel("Delete Customer:");
			lblDeleteCust.setBounds(273, 263, 120, 17);
			lblDeleteCust.setVisible(false);
			lblDeleteCust.setFont(new Font("Tahoma", Font.BOLD, 14));
			lblDeleteCust.setForeground(Color.BLACK);
			lblDeletePro = new JLabel("Delete Program:");
			lblDeletePro.setBounds(281, 295, 112, 17);
			lblDeletePro.setVisible(false);
			lblDeletePro.setFont(new Font("Tahoma", Font.BOLD, 14));
			lblDeletePro.setForeground(Color.BLACK);
			lblFirstName = new JLabel("First Name:");
			lblFirstName.setBounds(383, 100, 96, 20);
			lblFirstName.setVisible(true);
			lblFirstName.setFont(new Font("Tahoma", Font.BOLD, 14));
			lblFirstName.setForeground(Color.BLACK);
			lblLastName = new JLabel("Last Name:");
			lblLastName.setBounds(489, 100, 96, 20);
			lblLastName.setVisible(true);
			lblLastName.setFont(new Font("Tahoma", Font.BOLD, 14));
			lblLastName.setForeground(Color.BLACK);
			
		//TextFields	
			
			txtUsername = new JTextField();
			txtUsername.setBounds(403, 158, 182, 20);
			txtUsername.setVisible(true);
			txtUsername.setForeground(Color.BLACK);
			txtUsername.addMouseListener(new ContextMenuMouseListener());
			txtPassword = new JPasswordField();
			txtPassword.setBounds(403, 193, 182, 20);
			txtPassword.setVisible(true);
			txtPassword.addMouseListener(new ContextMenuMouseListener());
			txtPassword.setForeground(Color.BLACK);
			txtConfirmPassword = new JPasswordField();
			txtConfirmPassword.setBounds(403, 227, 182, 22);
			txtConfirmPassword.setVisible(true);
			txtConfirmPassword.addMouseListener(new ContextMenuMouseListener());
			txtConfirmPassword.setForeground(Color.BLACK);
			txtAddCusPro = new JTextField();
			txtAddCusPro.setBounds(403, 193, 182, 20);
			txtAddCusPro.setVisible(false);
			txtAddCusPro.addMouseListener(new ContextMenuMouseListener());
			txtAddCusPro.setForeground(Color.BLACK);
			txtProStart = new JTextField();
			txtProStart.setBounds(403, 228, 182, 20);
			txtProStart.setVisible(false);
			txtProStart.addMouseListener(new ContextMenuMouseListener());
			txtProStart.setForeground(Color.BLACK);
			txtProEnd = new JTextField();
			txtProEnd.setBounds(403, 263, 182, 20);
			txtProEnd.setVisible(false);
			txtProEnd.addMouseListener(new ContextMenuMouseListener());
			txtProEnd.setForeground(Color.BLACK);
			txtCust = new JTextField();
			txtCust.setBounds(403, 228, 182, 20);
			txtCust.setVisible(false);
			txtCust.addMouseListener(new ContextMenuMouseListener());
			txtCust.setForeground(Color.BLACK);
			txtFirstName = new JTextField();
			txtFirstName.setBounds(383, 123, 96, 20);
			txtFirstName.setVisible(true);
			txtFirstName.addMouseListener(new ContextMenuMouseListener());
			txtFirstName.setForeground(Color.BLACK);
			txtLastName = new JTextField();
			txtLastName.setBounds(489, 123, 96, 20);
			txtLastName.setVisible(true);
			txtLastName.addMouseListener(new ContextMenuMouseListener());
			txtLastName.setForeground(Color.BLACK);
			
		//ComboBox
			
			String[] ranks = {"admin","gui","engineer", "default"};
			cboUserRank = new JComboBox<Object>(ranks);
			cboUserRank.setBounds(403, 263, 182, 20);
			cboUserRank.addMouseListener(new ContextMenuMouseListener());
			cboUserRank.setSelectedIndex(-1);
			cboUserRank.setBackground(Color.white);
			cboCustomer = new JComboBox<String>();
			cboCustomer.setBounds(403, 158, 182, 20);
			cboCustomer.setModel(resetCustomerComboBox());
			cboCustomer.setVisible(false);
			cboCustomer.addMouseListener(new ContextMenuMouseListener());
			cboCustomer.setSelectedIndex(-1);
			cboCustomer.setBackground(Color.white);
			cboDeleteCust = new JComboBox<String>();
			cboDeleteCust.setBounds(403, 263, 182, 20);
			cboDeleteCust.setModel(resetCustomerComboBox());
			cboDeleteCust.setVisible(false);
			cboDeleteCust.addMouseListener(new ContextMenuMouseListener());
			cboDeleteCust.setSelectedIndex(-1);
			cboDeleteCust.setBackground(Color.white);
			cboDeletePro = new JComboBox<String>();
			cboDeletePro.setBounds(403, 294, 182, 20);
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
			rbtnAddProgram.setBounds(42, 294, 118, 25);
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
			rbtnAddCustomer.setBounds(42, 259, 136, 25);
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
			rbtnDeleteUser.setBounds(42, 154, 103, 25);
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
			rbtnCreateUser.setBounds(42, 119, 107, 25);
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
			rbtnChangeUserRank.setBounds(42, 189, 159, 25);
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
			rbtnChangePass.setBounds(42, 224, 149, 25);
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
			btnBack.setBounds(42, 338, 106, 35);
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
			btnDelete.setBounds(256, 338, 106, 35);
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
			btnSave.setBounds(487, 338, 106, 35);
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
		setLayout(null);
		add(lblbosal);
		add(lblmanageUsers);
		add(rbtnCreateUser);
		add(lblFirstName);
		add(txtFirstName);
		add(lblLastName);
		add(txtLastName);
		add(rbtnDeleteUser);
		add(lblCustomer);
		add(lblUsername);
		add(txtUsername);
		add(cboCustomer);
		add(rbtnChangeUserRank);
		add(lblPassword);
		add(lblAddCustomer);
		add(lblAddProgram);
		add(txtPassword);
		add(txtAddCusPro);
		add(rbtnChangePass);
		add(lblPassword2);
		add(lblProStart);
		add(lblCust);
		add(txtCust);
		add(txtProStart);
		add(txtConfirmPassword);
		add(lblPassConfirm);
		add(rbtnAddCustomer);
		add(lblRank);
		add(lblDeleteCust);
		add(lblProEnd);
		add(cboDeleteCust);
		add(txtProEnd);
		add(cboUserRank);
		add(rbtnAddProgram);
		add(lblDeletePro);
		add(cboDeletePro);
		add(btnBack);
		add(btnDelete);
		add(btnSave);
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
		lblCreated.setBounds(61, 147, 96, 14);
		lblCreatedBy = new JLabel("Created By");
		lblCreatedBy.setBounds(169, 147, 162, 14);
		lblProgram = new JLabel("Program");
		lblProgram.setBounds(162, 85, 96, 20);
		lblPartDescrip = new JLabel("Part Description");
		lblPartDescrip.setBounds(263, 85, 130, 20);
		lblCustomerPartNum = new JLabel("Customer Part Number");
		lblCustomerPartNum.setBounds(532, 152, 177, 14);
		lblCustomer = new JLabel("Customer");
		lblCustomer.setBounds(61, 88, 81, 14);
		lblYear = new JLabel("Year Code");
		lblYear.setBounds(533, 88, 77, 14);
		lblSearchPart = new JLabel("Part Number");
		lblSearchPart.setBounds(624, 88, 109, 14);
		ImageIcon bosal = new ImageIcon(getClass().getResource("/images/bosal.jpg"));
		lblBosal = new JLabel(bosal);
		lblBosal.setBounds(10, 11, 201, 63);
		lblExperimental = new JLabel("Experimental Part Number");
		lblExperimental.setBounds(217, 26, 502, 48);
		
	//JTextFields
		txtCreated = new JTextField();
		txtCreated.setBounds(61, 172, 73, 20);
		txtCreated.setBackground(new Color(190, 190, 190));
		txtCreated.setForeground(Color.BLACK);
		txtCreated.addMouseListener(new ContextMenuMouseListener());
		txtCreated.setEditable(false);
		txtCreatedBy = new JTextField();
		txtCreatedBy.setBounds(169, 172, 109, 20);
		txtCreatedBy.setBackground(new Color(190, 190, 190));
		txtCreatedBy.setForeground(Color.BLACK);
		txtCreatedBy.addMouseListener(new ContextMenuMouseListener());
		txtCreatedBy.setEditable(false);
		txtCustomerPartNum = new JTextField();
		txtCustomerPartNum.setBounds(532, 172, 184, 20);
		txtCustomerPartNum.setForeground(Color.BLACK);
		txtCustomerPartNum.addMouseListener(new ContextMenuMouseListener());
		txtPartNum = new JTextField();
		txtPartNum.setBounds(623, 110, 93, 24);
		txtPartNum.setBackground(new Color(190, 190, 190));
		txtPartNum.setForeground(Color.BLACK);
		txtPartNum.addMouseListener(new ContextMenuMouseListener());
		txtPartNum.setEditable(false);
		txtSearchPart = new JTextField();
		txtSearchPart.setBounds(169, 246, 109, 20);
		txtSearchPart.setForeground(Color.BLACK);
		txtSearchPart.addMouseListener(new ContextMenuMouseListener());
		txtSearchPart.setEditable(false);
					
	//JComboBoxes
		cboProgram = new JComboBox<String>();
		cboProgram.setBounds(161, 110, 81, 20);
		cboProgram.setEditable(true);
		cboProgram.setForeground(Color.BLACK);
		AutoCompleteDecorator.decorate(cboProgram);
		cboProgram.setModel(resetProgramComboBox());
		cboProgram.addMouseListener(new ContextMenuMouseListener());
		cboProgram.setSelectedIndex(-1);
		cboPartDescrip = new JComboBox<String>();
		cboPartDescrip.setBounds(263, 110, 250, 20);
		cboPartDescrip.setEditable(true);
		cboPartDescrip.setForeground(Color.BLACK);
		AutoCompleteDecorator.decorate(cboPartDescrip);
		cboPartDescrip.setModel(resetDescripComboBox());
		cboPartDescrip.addMouseListener(new ContextMenuMouseListener());
		cboPartDescrip.setSelectedIndex(-1);
		cboCustomer = new JComboBox<String>();
		cboCustomer.setBounds(61, 110, 77, 20);
		cboCustomer.setEditable(true);
		cboCustomer.setForeground(Color.BLACK);
		AutoCompleteDecorator.decorate(cboCustomer);
		cboCustomer.setModel(resetCustomerComboBox());
		cboCustomer.addMouseListener(new ContextMenuMouseListener());
		cboCustomer.setSelectedIndex(-1);
		String[] years = {"13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30"};
		cboYear = new JComboBox<String>(years);
		cboYear.setBounds(532, 110, 77, 20);
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
		rbtnSearch.setBounds(61, 246, 109, 25);
		rbtnSearch.setFont(new Font("Tahoma", Font.BOLD, 14));
		rbtnSearch.setForeground(Color.BLACK);
		rbtnSearch.setBackground(new Color(105, 105, 105));
		rbtnCreate = new JRadioButton("Create Part");
		rbtnCreate.setBounds(61, 212, 109, 25);
		rbtnCreate.setFont(new Font("Tahoma", Font.BOLD, 14));
		rbtnCreate.setForeground(Color.BLACK);
		rbtnCreate.setBackground(new Color(105, 105, 105));
		
		//JButtons
				ImageIcon save = new ImageIcon(getClass().getResource("/images/save.jpg"));
				btnSave = new JButton(save);
				btnSave.setVisible(true);
				btnSave.setBounds(613, 236, 106, 35);
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
				btnBack.setBounds(485, 236, 106, 35);
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
							btnSave.setVisible(true);
						}
					}					
				});
							
				ImageIcon check= new ImageIcon(getClass().getResource("/images/check.jpg"));
				btnCheck = new JButton(check);
				btnCheck.setBounds(288, 236, 106, 35);
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
					if( btnSave.isVisible() == false){
						btnSave.setVisible(true);
					}
            }
		}});
		rbtnCreate.doClick();
							
		rbtnSearch.addActionListener(new ActionListener(){
			
			public void actionPerformed(ActionEvent e)
			{
				if (e.getSource() == rbtnSearch){
					
					btnSave.setVisible(false);
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
						lblExperimental.setFont(new Font("Tahoma", Font.BOLD, 32));
						lblExperimental.setForeground(Color.BLACK);
						ButtonGroup group = new ButtonGroup();
						group.add(rbtnCreate);
						group.add(rbtnSearch);
						setLayout(null);
						add(lblBosal);
						add(lblExperimental);
						add(lblCustomer);
						add(lblProgram);
						add(lblPartDescrip);
						add(lblYear);
						add(lblSearchPart);
						add(cboCustomer);
						add(cboProgram);
						add(cboPartDescrip);
						add(cboYear);
						add(txtPartNum);
						add(lblCreated);
						add(lblCreatedBy);
						add(lblCustomerPartNum);
						add(txtCreated);
						add(txtCreatedBy);
						add(txtCustomerPartNum);
						add(rbtnCreate);
						add(rbtnSearch);
						add(txtSearchPart);
						add(btnCheck);
						add(btnBack);
						add(btnSave);
					}}//End of Class Experimental Parts Panel	
}//End of MainFrames
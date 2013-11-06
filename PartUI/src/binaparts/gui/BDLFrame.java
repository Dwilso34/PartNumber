package binaparts.gui;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
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

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.GroupLayout.Alignment;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.text.Document;

import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import org.json.JSONArray;

import binaparts.dao.DBConnect;
import binaparts.util.ToJSON;

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
		contentPane.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		contentPane.setLayout(new CardLayout());
		pnlMain = new BDLMain(contentPane);
		contentPane.add(pnlMain, "Main Menu");
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int height = screenSize.height;
		int width = screenSize.width;
		BDLframe.setResizable(true);
		BDLframe.setSize(width/2, height/2);
		BDLframe.setSize(1285, 610);
		BDLframe.setContentPane(contentPane);
		BDLframe.setVisible(true);	
		try{
			BDLframe.setIconImage(ImageIO.read(new File("res/bosalimage.png")));
		}catch(Exception ex){ex.printStackTrace();}
	}
	public String getSearchText() {
		return searchText;
	}
	public void setSearchText(String searchText) {
		this.searchText = searchText;		
	}
		
	class BDLMain extends JPanel 
	{
		
		//global variables
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
		
		

	    public void printComponent(Component comp) {
	        PrinterJob pj = PrinterJob.getPrinterJob();
	        pj.setJobName(" Print Component ");

	        pj.setPrintable(new ComponentPrintable(comp));

	        if (!pj.printDialog()) {
	            return;
	        }
	        try {
	            pj.print();
	        } catch (PrinterException ex) {
	            System.out.println(ex);
	        }
	    }

	    public class ComponentPrintable implements Printable {

	        private Component comp;

	        private ComponentPrintable(Component comp) {
	            this.comp = comp;
	        }

	        @Override
	        public int print(Graphics g, PageFormat pf, int pageNumber)
	                throws PrinterException {
	            // TODO Auto-generated method stub
	            if (pageNumber > 0) {
	                return Printable.NO_SUCH_PAGE;
	            }

	            // Get the preferred size ofthe component...
	            Dimension compSize = comp.getPreferredSize();
	            // Make sure we size to the preferred size
	            comp.setSize(compSize);
	            // Get the the print size
	            Dimension printSize = new Dimension();
	            printSize.setSize(pf.getImageableWidth(), pf.getImageableHeight());

	            // Calculate the scale factor
	            double scaleFactor = getScaleFactorToFit(compSize, printSize);
	            // Don't want to scale up, only want to scale down
	            if (scaleFactor > 1d) {
	                scaleFactor = 1d;
	            }

	            // Calculate the scaled size...
	            double scaleWidth = compSize.width * scaleFactor;
	            double scaleHeight = compSize.height * scaleFactor;

	            // Create a clone of the graphics context.  This allows us to manipulate
	            // the graphics context without begin worried about what effects
	            // it might have once we're finished
	            Graphics2D g2 = (Graphics2D) g.create();
	            // Calculate the x/y position of the component, this will center
	            // the result on the page if it can
	            double x = ((pf.getImageableWidth() - scaleWidth) / 2d) + pf.getImageableX();
	            double y = ((pf.getImageableHeight() - scaleHeight) / 2d) + pf.getImageableY();
	            // Create a new AffineTransformation
	            AffineTransform at = new AffineTransform();
	            // Translate the offset to out "center" of page
	            at.translate(x, y);
	            // Set the scaling
	            at.scale(scaleFactor, scaleFactor);
	            // Apply the transformation
	            g2.transform(at);
	            // Print the component
	            comp.printAll(g2);
	            // Dispose of the graphics context, freeing up memory and discarding
	            // our changes
	            g2.dispose();

	            comp.revalidate();
	            return Printable.PAGE_EXISTS;
	        }
	    }

	    public double getScaleFactorToFit(Dimension original, Dimension toFit) {

	        double dScale = 1d;

	        if (original != null && toFit != null) {

	            double dScaleWidth = getScaleFactor(original.width, toFit.width);
	            double dScaleHeight = getScaleFactor(original.height, toFit.height);

	            dScale = Math.min(dScaleHeight, dScaleWidth);

	        }

	        return dScale;

	    }

	    public double getScaleFactor(int iMasterSize, int iTargetSize) {

	        double dScale = 1;
	        if (iMasterSize > iTargetSize) {

	            dScale = (double) iTargetSize / (double) iMasterSize;

	        } else {

	            dScale = (double) iTargetSize / (double) iMasterSize;

	        }

	        return dScale;

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
		
	//JButton
		private JButton btnAdd;
		private JButton btnDelete;
		private JButton btnSave;
		private JButton btnPrint;
		
	//JTable	
		private JSONArray bdlItems;
		private String bdlContent;
		private JTable myTable;
		private DefaultTableModel table1;
		private JScrollPane scrollPane;
		public void bdlHeaders(){
			try{
	            String[] columnNames = {"ITEM", "QTY", " ", "Description", 
	            		"JDE Part-NR", "OLD Part-NR", "Rev", "DWG NR", 
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
		
		JPanel contentPane;
		
		public BDLMain(final JPanel pnlMain) 	
		{	
			setBackground(new Color(105, 105, 105));
			contentPane = pnlMain;
			setOpaque(true);
			setVisible(true);
							
		//Images
			final ImageIcon bosal = new ImageIcon(getClass().getResource("/images/bosal.jpg"));
			lblBosal = new JLabel(bosal);
			lblBosal.setBounds(10, 11, 194, 56);
			
		//JLabels
			lblBDL = new JLabel("Breakdown List");
			lblBDL.setBounds(214, 26, 482, 40);
			lblBDL.setFont(new Font("EucrosiaUPC", Font.BOLD, 64));
			lblBDL.setForeground(Color.BLACK);
			lblCustomer = new JLabel("Customer");
			lblCustomer.setBounds(30, 82, 128, 92);
			lblCustomer.setHorizontalAlignment(SwingConstants.CENTER);
			lblCustomer.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			lblCustomer.setBackground(new Color(150, 150, 150));
			lblCustomer.setOpaque(true);
			lblCustomer.setFont(new Font("Tahoma", Font.BOLD, 14));
			lblCustomer.setForeground(Color.BLACK);
			lblPlatform = new JLabel("Platform");
			lblPlatform.setBounds(30, 173, 128, 20);
			lblPlatform.setHorizontalAlignment(SwingConstants.CENTER);
			lblPlatform.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			lblPlatform.setBackground(new Color(150, 150, 150));
			lblPlatform.setOpaque(true);
			lblPlatform.setFont(new Font("Tahoma", Font.BOLD, 14));
			lblPlatform.setForeground(Color.BLACK);
			lblType = new JLabel("Type:");
			lblType.setBounds(76, 192, 82, 20);
			lblType.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			lblType.setBackground(new Color(150, 150, 150));
			lblType.setOpaque(true);
			lblType.setFont(new Font("Tahoma", Font.BOLD, 12));
			lblType.setForeground(Color.BLACK);
			lblName = new JLabel("Name:");
			lblName.setBounds(76, 211, 82, 20);
			lblName.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			lblName.setBackground(new Color(150, 150, 150));
			lblName.setOpaque(true);
			lblName.setFont(new Font("Tahoma", Font.BOLD, 12));
			lblName.setForeground(Color.BLACK);
			lblVolume = new JLabel("Volume (L):");
			lblVolume.setBounds(76, 230, 82, 20);
			lblVolume.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			lblVolume.setBackground(new Color(150, 150, 150));
			lblVolume.setOpaque(true);
			lblVolume.setFont(new Font("Tahoma", Font.BOLD, 12));
			lblVolume.setForeground(Color.BLACK);
			lblPower = new JLabel("Power (kW):");
			lblPower.setBounds(76, 249, 82, 20);
			lblPower.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			lblPower.setBackground(new Color(150, 150, 150));
			lblPower.setOpaque(true);
			lblPower.setFont(new Font("Tahoma", Font.BOLD, 12));
			lblPower.setForeground(Color.BLACK);
			lblEngine = new JLabel("Engine");
			lblEngine.setBounds(30, 192, 47, 77);
			lblEngine.setHorizontalAlignment(SwingConstants.CENTER);
			lblEngine.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			lblEngine.setBackground(new Color(150, 150, 150));
			lblEngine.setOpaque(true);
			lblEngine.setFont(new Font("Tahoma", Font.BOLD, 12));
			lblEngine.setForeground(Color.BLACK);
			lblBosalPartNum = new JLabel("BOSAL PART NR");
			lblBosalPartNum.setBounds(444, 133, 142, 20);
			lblBosalPartNum.setHorizontalAlignment(SwingConstants.CENTER);
			lblBosalPartNum.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			lblBosalPartNum.setBackground(new Color(150, 150, 150));
			lblBosalPartNum.setOpaque(true);
			lblBosalPartNum.setFont(new Font("Tahoma", Font.BOLD, 14));
			lblBosalPartNum.setForeground(Color.BLACK);
			lblCustomerPartNum = new JLabel("CUSTOMER PART NR");
			lblCustomerPartNum.setBounds(585, 133, 166, 20);
			lblCustomerPartNum.setHorizontalAlignment(SwingConstants.CENTER);
			lblCustomerPartNum.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			lblCustomerPartNum.setBackground(new Color(150, 150, 150));
			lblCustomerPartNum.setOpaque(true);
			lblCustomerPartNum.setFont(new Font("Tahoma", Font.BOLD, 14));
			lblCustomerPartNum.setForeground(Color.BLACK);
			lblIMDS = new JLabel("IMDS");
			lblIMDS.setBounds(750, 133, 98, 20);
			lblIMDS.setHorizontalAlignment(SwingConstants.CENTER);
			lblIMDS.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			lblIMDS.setBackground(new Color(150, 150, 150));
			lblIMDS.setOpaque(true);
			lblIMDS.setFont(new Font("Tahoma", Font.BOLD, 14));
			lblIMDS.setForeground(Color.BLACK);
			lblDescription = new JLabel("DESCRIPTION");
			lblDescription.setBounds(444, 171, 142, 20);
			lblDescription.setHorizontalAlignment(SwingConstants.CENTER);
			lblDescription.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			lblDescription.setBackground(new Color(150, 150, 150));
			lblDescription.setOpaque(true);
			lblDescription.setFont(new Font("Tahoma", Font.BOLD, 14));
			lblDescription.setForeground(Color.BLACK);
			lblSilencer = new JLabel("Silencer");
			lblSilencer.setBounds(444, 190, 66, 58);
			lblSilencer.setHorizontalAlignment(SwingConstants.CENTER);
			lblSilencer.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			lblSilencer.setBackground(new Color(150, 150, 150));
			lblSilencer.setOpaque(true);
			lblSilencer.setFont(new Font("Tahoma", Font.BOLD, 14));
			lblSilencer.setForeground(Color.BLACK);
			lblVolume2 = new JLabel("Volume (L):");
			lblVolume2.setBounds(509, 190, 77, 20);
			lblVolume2.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			lblVolume2.setBackground(new Color(150, 150, 150));
			lblVolume2.setOpaque(true);
			lblVolume2.setFont(new Font("Tahoma", Font.BOLD, 12));
			lblVolume2.setForeground(Color.BLACK);
			lblLength = new JLabel("Length:");
			lblLength.setBounds(509, 209, 77, 20);
			lblLength.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			lblLength.setBackground(new Color(150, 150, 150));
			lblLength.setOpaque(true);
			lblLength.setFont(new Font("Tahoma", Font.BOLD, 12));
			lblLength.setForeground(Color.BLACK);
			lblSection = new JLabel("Section:");
			lblSection.setBounds(509, 228, 77, 20);
			lblSection.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			lblSection.setBackground(new Color(150, 150, 150));
			lblSection.setOpaque(true);
			lblSection.setFont(new Font("Tahoma", Font.BOLD, 12));
			lblSection.setForeground(Color.BLACK);
			lblIssuedBy = new JLabel("Issued By:");
			lblIssuedBy.setBounds(669, 95, 82, 20);
			lblIssuedBy.setHorizontalAlignment(SwingConstants.RIGHT);
			lblIssuedBy.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			lblIssuedBy.setBackground(new Color(150, 150, 150));
			lblIssuedBy.setOpaque(true);
			lblIssuedBy.setFont(new Font("Tahoma", Font.BOLD, 12));
			lblIssuedBy.setForeground(Color.BLACK);
			lblPage = new JLabel("Page:");
			lblPage.setBounds(1064, 82, 77, 20);
			lblPage.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			lblPage.setBackground(new Color(150, 150, 150));
			lblPage.setOpaque(true);
			lblPage.setFont(new Font("Tahoma", Font.BOLD, 14));
			lblPage.setForeground(Color.BLACK);
			lblREV = new JLabel("REV:");
			lblREV.setBounds(1064, 101, 77, 20);
			lblREV.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			lblREV.setBackground(new Color(150, 150, 150));
			lblREV.setOpaque(true);
			lblREV.setFont(new Font("Tahoma", Font.BOLD, 14));
			lblREV.setForeground(Color.BLACK);
			lblRelDate = new JLabel("Rel Date:");
			lblRelDate.setBounds(1064, 120, 77, 20);
			lblRelDate.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			lblRelDate.setBackground(new Color(150, 150, 150));
			lblRelDate.setOpaque(true);
			lblRelDate.setFont(new Font("Tahoma", Font.BOLD, 14));
			lblRelDate.setForeground(Color.BLACK);
			lblREVDate = new JLabel("REV Date:");
			lblREVDate.setBounds(1064, 139, 77, 20);
			lblREVDate.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			lblREVDate.setBackground(new Color(150, 150, 150));
			lblREVDate.setOpaque(true);
			lblREVDate.setFont(new Font("Tahoma", Font.BOLD, 14));
			lblREVDate.setForeground(Color.BLACK);
			lblProduction = new JLabel("Production:");
			lblProduction.setBounds(1077, 170, 163, 20);
			lblProduction.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			lblProduction.setBackground(new Color(150, 150, 150));
			lblProduction.setOpaque(true);
			lblProduction.setFont(new Font("Tahoma", Font.BOLD, 14));
			lblProduction.setForeground(Color.BLACK);
			lblRelPlant1 = new JLabel("Rel Plant 1:");
			lblRelPlant1.setBounds(1050, 220, 91, 20);
			lblRelPlant1.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			lblRelPlant1.setBackground(new Color(150, 150, 150));
			lblRelPlant1.setOpaque(true);
			lblRelPlant1.setFont(new Font("Tahoma", Font.BOLD, 14));
			lblRelPlant1.setForeground(Color.BLACK);
			lblRelPlant2 = new JLabel("Rel Plant 2:");
			lblRelPlant2.setBounds(1050, 239, 91, 20);
			lblRelPlant2.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			lblRelPlant2.setBackground(new Color(150, 150, 150));
			lblRelPlant2.setOpaque(true);
			lblRelPlant2.setFont(new Font("Tahoma", Font.BOLD, 14));
			lblRelPlant2.setForeground(Color.BLACK);
			lblRelSupplier = new JLabel("Rel Supplier:");
			lblRelSupplier.setBounds(1050, 258, 91, 20);
			lblRelSupplier.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			lblRelSupplier.setBackground(new Color(150, 150, 150));
			lblRelSupplier.setOpaque(true);
			lblRelSupplier.setFont(new Font("Tahoma", Font.BOLD, 14));
			lblRelSupplier.setForeground(Color.BLACK);
			lblBOSAL = new JLabel("BOSAL");
			lblBOSAL.setBounds(271, 300, 620, 20);
			lblBOSAL.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			lblBOSAL.setBackground(new Color(150, 150, 150));
			lblBOSAL.setOpaque(true);
			lblBOSAL.setFont(new Font("Tahoma", Font.BOLD, 14));
			lblBOSAL.setForeground(Color.BLACK);
			lblCUSTOMER = new JLabel("CUSTOMER");
			lblCUSTOMER.setBounds(890, 300, 350, 20);
			lblCUSTOMER.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			lblCUSTOMER.setBackground(new Color(150, 150, 150));
			lblCUSTOMER.setOpaque(true);
			lblCUSTOMER.setFont(new Font("Tahoma", Font.BOLD, 14));
			lblCUSTOMER.setForeground(Color.BLACK);
			
		//JButtons
			btnAdd = new JButton("Add");
			btnAdd.setBounds(30, 289, 75, 20);
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
			btnDelete.setBounds(115, 289, 75, 20);
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
		btnSave.setBounds(1138, 30, 103, 34);
		btnSave.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == btnSave)
				{

				}
			}
		});
		
		ImageIcon print = new ImageIcon(getClass().getResource("/images/print.jpg"));
		btnPrint = new JButton(print);
		btnPrint.setBounds(900, 30, 103, 34);
		btnPrint.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == btnPrint)
				{
					printComponent(pnlMain);
				}
			}
		});
		
		//JTable			
			final MouseAdapter mouseClickListener = new MouseAdapter(){
				public void mouseClicked(MouseEvent e) {
					if(e.getClickCount() == 2){
						if(e.getSource() == txtBosalPartNum){
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
							System.out.println("row:"+row+" column: "+column);
							if(column == 4){
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
							System.out.println("Trying to Collect Data from Database");
							JSONArray temp = con.queryDatabase("bosal parts", "BosalPartNumber", getSearchText());
							for(int i = 0; i < temp.length(); i++){
								try{
									description = temp.getJSONObject(i).getString("PartDescription").toString();
								}catch(Exception ex){
									System.out.println("Part " + getSearchText() + " does not contain PartDescription");
								}
								try{
									rev = (int)temp.getJSONObject(i).getInt("Rev");
								}catch(Exception ex){
									System.out.println("Part " + getSearchText() + " does not contain Rev");
								}
								try{
									drawingNumber = temp.getJSONObject(i).get("DrawingNumber").toString();
								}catch(Exception ex){
									System.out.println("Part " + getSearchText() + " does not contain DrawingNumber");
								}
								try{
								drawingRev = (int)temp.getJSONObject(i).getInt("DrawingRev");
								}catch(Exception ex){
									System.out.println("Part " + getSearchText() + " does not contain DrawingRev");
								}
								try{
									drawingRevDate = temp.getJSONObject(i).get("DrawingRevDate").toString();
								}catch(Exception ex){
									System.out.println("Part " + getSearchText() + " does not contain DrawingRevDate");
								}
								try{
									productionReleaseDate = temp.getJSONObject(i).get("ProductionReleaseDate").toString();
								}catch(Exception ex){
									System.out.println("Part " + getSearchText() + " does not contain ProductionReleaseDate");
								}
								try{
									customer = temp.getJSONObject(i).get("CustPartNumber").toString();
								}catch(Exception ex){
									System.out.println("Part " + getSearchText() + " does not contain CustPartNumber");
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
			scrollPane = new JScrollPane(myTable, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
			scrollPane.setBounds(30, 320, 1210, 229);
			scrollPane.setViewportBorder(new LineBorder(new Color(0, 0, 0)));
			scrollPane.setViewportView(myTable);
			myTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);	
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
			cboCustomer.setBounds(157, 82, 128, 20);
			cboCustomer.setEditable(true);
			AutoCompleteDecorator.decorate(cboCustomer);
			cboCustomer.addMouseListener(new ContextMenuMouseListener());
			cboCustomer.setForeground(Color.BLACK);
			cboPlatform = new JComboBox<String>();
			cboPlatform.setBounds(157, 173, 128, 20);
			cboPlatform.setEditable(true);
			AutoCompleteDecorator.decorate(cboPlatform);
			cboPlatform.addMouseListener(new ContextMenuMouseListener());
			cboPlatform.setForeground(Color.BLACK);
			cboName = new JComboBox<String>();
			cboName.setBounds(157, 211, 128, 20);
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
					        			System.out.println("Program " + platform + " does not contain Customer");
					        		}
					        	}
							}catch(Exception ex){
								System.out.println("Part " + getSearchText() + " does not contain Program");
							}
			        		try{
								custPartNum = temp.getJSONObject(i).getString("CustPartNumber").toString();
							}catch(Exception ex){
								System.out.println("Part " + getSearchText() + " does not contain CustPartNumber");
							}
			        		try{
								description = temp.getJSONObject(i).getString("PartDescription").toString();
							}catch(Exception ex){
								System.out.println("Part " + getSearchText() + " does not contain PartDescription");
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
			        Document source = documentEvent.getDocument();
			        int length = source.getLength();
			        System.out.println("Length: " + length);
			     }
			};
			txtCustomer = new JTextField();
			txtCustomer.setBounds(157, 82, 128, 20);
			txtCustomer.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			txtCustomer.setForeground(Color.BLACK);
			txtPlatform = new JTextField();
			txtPlatform.setBounds(157, 173, 128, 20);
			txtPlatform.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			txtPlatform.setForeground(Color.BLACK);
			txtType = new JTextField();
			txtType.setBounds(157, 192, 128, 20);
			txtType.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			txtType.setForeground(Color.BLACK);
			txtName = new JTextField();
			txtName.setBounds(157, 211, 128, 20);
			txtName.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			txtName.setForeground(Color.BLACK);
			txtVolume = new JTextField();
			txtVolume.setBounds(157, 230, 128, 20);
			txtVolume.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			txtVolume.setForeground(Color.BLACK);
			txtPower = new JTextField();
			txtPower.setBounds(157, 249, 128, 20);
			txtPower.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			txtPower.setForeground(Color.BLACK);
			txtBosalPartNum = new JTextField();
			txtBosalPartNum.setBounds(444, 152, 142, 20);
			txtBosalPartNum.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			txtBosalPartNum.setForeground(Color.BLACK);
			txtBosalPartNum.addMouseListener(mouseClickListener);
			txtBosalPartNum.getDocument().addDocumentListener(documentListener);
			txtCustomerPartNum = new JTextField();
			txtCustomerPartNum.setBounds(585, 152, 166, 20);
			txtCustomerPartNum.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			txtCustomerPartNum.setForeground(Color.BLACK);
			txtIMDS = new JTextField();
			txtIMDS.setBounds(750, 152, 98, 20);
			txtIMDS.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			txtIMDS.setForeground(Color.BLACK);
			txtDescription = new JTextField();
			txtDescription.setBounds(585, 171, 263, 20);
			txtDescription.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			txtDescription.setForeground(Color.BLACK);
			txtVolume2 = new JTextField();
			txtVolume2.setBounds(585, 190, 263, 20);
			txtVolume2.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			txtVolume2.setForeground(Color.BLACK);
			txtLength = new JTextField();
			txtLength.setBounds(585, 209, 263, 20);
			txtLength.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			txtLength.setForeground(Color.BLACK);
			txtSection = new JTextField();
			txtSection.setBounds(585, 228, 263, 20);
			txtSection.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			txtSection.setForeground(Color.BLACK);
			txtIssuedBy = new JTextField();
			txtIssuedBy.setBounds(750, 95, 98, 20);
			txtIssuedBy.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			txtIssuedBy.setForeground(Color.BLACK);
			txtIssuedBy.setEditable(false);
			txtPage = new JTextField();
			txtPage.setBounds(1140, 82, 101, 20);
			txtPage.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			txtPage.setForeground(Color.BLACK);
			txtREV = new JTextField();
			txtREV.setBounds(1140, 101, 101, 20);
			txtREV.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			txtREV.setForeground(Color.BLACK);
			txtRelDate = new JTextField();
			txtRelDate.setBounds(1140, 120, 101, 20);
			txtRelDate.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			txtRelDate.setForeground(Color.BLACK);
			txtREVDate = new JTextField();
			txtREVDate.setBounds(1140, 139, 101, 20);
			txtREVDate.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			txtREVDate.setForeground(Color.BLACK);
			txtProduction = new JTextField();
			txtProduction.setBounds(1077, 189, 163, 20);
			txtProduction.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			txtProduction.setForeground(Color.BLACK);
			txtRelPlant1 = new JTextField();
			txtRelPlant1.setBounds(1140, 220, 100, 20);
			txtRelPlant1.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			txtRelPlant1.setForeground(Color.BLACK);
			txtRelPlant2 = new JTextField();
			txtRelPlant2.setBounds(1140, 239, 100, 20);
			txtRelPlant2.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			txtRelPlant2.setForeground(Color.BLACK);
			txtRelSupplier = new JTextField();
			txtRelSupplier.setBounds(1140, 258, 100, 20);
			txtRelSupplier.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			txtRelSupplier.setForeground(Color.BLACK);
		
		//JCheckBoxes
			cbxCustomer = new JCheckBox();
			cbxCustomer.setBounds(290, 85, 13, 13);
			cbxCustomer.setBackground(new Color(105, 105, 105));
			cbxCustomer.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			cbxPlatform = new JCheckBox();
			cbxPlatform.setBounds(290, 177, 13, 13);
			cbxPlatform.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			cbxPlatform.setBackground(new Color(105, 105, 105));
			cbxName = new JCheckBox();
			cbxName.setBounds(290, 214, 13, 13);
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
			rbtnCreateBDL.setBounds(444, 95, 103, 27);
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
			rbtnSearchBDL.setBounds(552, 95, 111, 27);
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
							
							/*String s = (String)JOptionPane.showInputDialog(
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
							    final String findBosalText = txtBosalPartNum.getText();
							   
								txtBosalPartNum.setText(getSearchText());
								JSONArray temp = (con.queryDatabase("bosal parts", "BosalPartNumber", findBosalText));
								
							//Sets Customer Part Number When Bosal Number is Searched
								String cpartText= null;
								try{
									cpartText = temp.get("CustPartNumber").toString();
								}catch(Exception ex){cpartText = "-";}
								txtCustomerPartNum.setText(cpartText);
								
							//Sets Part Description When Bosal Number is Searched
								String descripText= null;
								try{
									descripText = temp.get("PartDescription").toString();
								}catch(Exception ex){descripText = "-";}
								txtDescription.setText(descripText);
								
							//Sets Platform When Bosal Number is Searched
								String programText = null;
								try{
									programText = temp.get("Program").toString();
								}catch(Exception ex){programText = "-";}
								txtPlatform.setText(programText);
								
							//Sets Customer When Bosal Number is Searched
								final String findCustomer = txtPlatform.getText();
								JSONObject temp2 = (con.queryDatabase("programs", "Program", findCustomer)).getJSONObject(0);
								 
								String customerText = null;
								try{
									customerText = temp2.get("Customer").toString();
								}catch(Exception ex){customerText = "-";}
								txtCustomer.setText(customerText);
								
							    return;
							}*/
							
						} catch (Exception ex) {ex.printStackTrace();}
			            
					}						
				}});
			
			rbtnUpdateBDL = new JRadioButton("Update BDL");
			rbtnUpdateBDL.setBounds(350, 95, 111, 27);
			rbtnUpdateBDL.setBackground(new Color(105, 105, 105));
			rbtnUpdateBDL.setForeground(Color.BLACK);
			rbtnUpdateBDL.setFont(new Font("Tahoma", Font.BOLD, 14));
			rbtnUpdateBDL.addActionListener(new ActionListener(){
				
				public void actionPerformed(ActionEvent e)
				{		
					if (e.getSource() == rbtnUpdateBDL){
						TableModel table = myTable.getModel();
						int rowCount = table.getRowCount();
						String[] itm = new String[rowCount];
						String[] qty = new String[rowCount];
						String s = null;
						for(int i = 0; i < rowCount; i++){
							itm[i] = table.getValueAt(i, 4).toString();
							qty[i] = table.getValueAt(i, 1).toString();
						}
						for(int i = 0; i < rowCount; i++){
							s = "{Item"+(i+1)+":"+itm[i]+","+"Qty"+(i+1)+":"+qty[i]+"}";
							System.out.println(s);
						}
						
					}
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
			add(btnPrint);
			}
		
}
}



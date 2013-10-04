import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.GroupLayout.Alignment;

import org.json.JSONObject;

import binaparts.dao.DBConnect;
import binaparts.gui.ContextMenuMouseListener;


public class UpdatePanel extends JPanel 
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
			
			private JComboBox<?> cboDescrip;
			
		//Update Panel
			
			public UpdatePanel(final JPanel update) 
			{
				setBackground(new Color(95, 95, 95));
			
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
				txtRev.setForeground(Color.BLACK);
				txtRev.addMouseListener(new ContextMenuMouseListener());
				txtDrawingNum = new JTextField();
				txtDrawingNum.setForeground(Color.BLACK);
				txtDrawingNum.addMouseListener(new ContextMenuMouseListener());
				
		//JComboBoxes
				
				cboDescrip = new JComboBox();
				cboDescrip.setForeground(Color.BLACK);
				cboDescrip.addMouseListener(new ContextMenuMouseListener());
				
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
									txtDescrip.setText("");
									txtFindBosal.setText("");
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
							txtDescrip.setText("");
							txtCusDescrip.setText("");
							txtSupDescrip.setText("");
							txtProgram.setText("");
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
								String Description = null;
								String Program = null;
								
								if(txtCusDescrip.getText().equals("-") || txtCusDescrip.getText().equals("")){
									CustomerPartNumber = null;
								}else{CustomerPartNumber = txtCusDescrip.getText();}
								if(txtSupDescrip.getText().equals("-") || txtSupDescrip.getText().equals("")){
									SupplierPartNumber = null;
								}else{SupplierPartNumber = txtSupDescrip.getText();}
								if(txtDescrip.getText().equals("-") || txtDescrip.getText().equals("")){
									Description = null;
								}else{Description= txtDescrip.getText();}
								if(txtProgram.getText().equals("-") || txtProgram.getText().equals("")){
									Program = null;
								}else{Program = txtProgram.getText();}
								try {
									con.update(BosalPartNumber, CustomerPartNumber, SupplierPartNumber, Description, Program);
									
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
									txtDescrip.setText("");
									txtCusDescrip.setText("");
									txtSupDescrip.setText("");
									txtProgram.setText("");
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
								JSONObject temp = (con.queryBosalPartNumber(findBosalText)).getJSONObject(0);
								//set text for CustPartNumber JTextField
								String cpartText= null;
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
								txtDescrip.setText(descripText);
								
								//set text for CustPartNumber JTextField
								String programText = null;
								try{
									programText = temp.get("Program").toString();
								}catch(Exception ex){programText = "-";}
								txtProgram.setText(programText);
							}catch(Exception ex){
								JOptionPane.showMessageDialog(
										    frame,
										    "Bosal Part Number: " + findBosalText + " does not exist",
										    "Missing Part Number",
											JOptionPane.ERROR_MESSAGE);
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
				
				txtDescrip.setColumns(10);
				txtCusDescrip.setColumns(10);
				txtSupDescrip.setColumns(10);
				txtDescrip.setColumns(10);
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
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(lblBosalPartNum)
								.addComponent(txtFindBosal, GroupLayout.PREFERRED_SIZE, 174, GroupLayout.PREFERRED_SIZE))
							.addGap(12)
							.addComponent(btnCheck, GroupLayout.PREFERRED_SIZE, 106, GroupLayout.PREFERRED_SIZE)
							.addGap(216)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(lblRev, GroupLayout.PREFERRED_SIZE, 223, GroupLayout.PREFERRED_SIZE)
								.addComponent(txtRev, GroupLayout.PREFERRED_SIZE, 174, GroupLayout.PREFERRED_SIZE)))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(28)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(lblDescription)
								.addComponent(txtDescrip, GroupLayout.PREFERRED_SIZE, 432, GroupLayout.PREFERRED_SIZE))
							.addGap(75)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(lblDrawingNum, GroupLayout.PREFERRED_SIZE, 223, GroupLayout.PREFERRED_SIZE)
								.addComponent(txtDrawingNum, GroupLayout.PREFERRED_SIZE, 174, GroupLayout.PREFERRED_SIZE)))
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
							.addGap(23)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(11)
									.addComponent(lblBosalPartNum)
									.addGap(11)
									.addComponent(txtFindBosal, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(33)
									.addComponent(btnCheck, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE))
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(lblRev, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
									.addComponent(txtRev, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
							.addGap(7)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(9)
									.addComponent(lblDescription)
									.addGap(11)
									.addComponent(txtDescrip, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addComponent(lblDrawingNum, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(37)
									.addComponent(txtDrawingNum, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
							.addGap(16)
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

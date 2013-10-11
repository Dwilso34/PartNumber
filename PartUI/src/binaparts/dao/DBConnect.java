package binaparts.dao;

import java.sql.*;

import org.json.JSONArray;
import org.json.JSONObject;

import binaparts.properties.*;
import binaparts.util.ToJSON;

public class DBConnect {

	protected Statement st = null;
	protected ResultSet rs = null;
	protected Connection con = null;	
	protected PreparedStatement pst = null;
	private String configFilePath = "config.properties";
	public DBConnect(){
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		}
	}
	//returns a boolean answer whether ResultSet is empty
	private boolean isResultSetEmpty(ResultSet rs){
		boolean status = true;
		try {
			if (rs.isBeforeFirst() == true){
				 status = false;
			}
		}catch(Exception ex){/*Ignore*/}
		return status;
	}
	//returns a String[] of parts list table's column names
	public String[] getColumnNames(String table, String column, String queryValue){
		try{		
			con = getDBConnection();
			if (column.equals("All")){
				pst = con.prepareStatement("SELECT * FROM `"+table+"`");
			}else{
				pst = con.prepareStatement("SELECT * FROM `"+table+"` WHERE "+column+" = ?");
				pst.setString(1, queryValue);
			}	
			ResultSet rs = pst.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			rs.close();
			int count = rsmd.getColumnCount();
			String[] columnNames = new String[count];
			
			for (int i = 0; i < count; i++ ) {
				columnNames[i] = rsmd.getColumnName(i+1);
			}
			pst.close();
			con.close();
			return columnNames;			
		}catch(Exception ex){
			String[] columnNames = null;
			System.out.println("i screwed up");
			ex.printStackTrace();
			return columnNames;
		}finally{
			try{if(con.isClosed() == false){con.close();}}catch(Exception ex){ex.printStackTrace();}
		}
}
	//returns an integer equal to the number of rows returned by a parts list search
	public int getRowCount(String table, String column, String queryValue) throws Exception{
		int rowCount = 0;
		try{		
			con = getDBConnection();
			if (column.equals("All")){
				pst = con.prepareStatement("SELECT * FROM `"+table+"`");
			}else{
				pst = con.prepareStatement("SELECT * FROM `"+table+"` WHERE "+column+" = ?");
				pst.setString(1, queryValue);
			}
			ResultSet rs = pst.executeQuery();
			
			while(rs.next()){
				rowCount++;
			}
			rs.close();
			pst.close();
			con.close();
		}catch(Exception ex){ex.printStackTrace();}
		finally{
			try{if(con.isClosed() == false){con.close();}}catch(Exception ex){ex.printStackTrace();}
		}
		return rowCount;
	}
	//get connection from database (done)
	private Connection getDBConnection() throws Exception{
		
		ConfigurationManager configProps = new ConfigurationManager(configFilePath);
		String host = configProps.getProperty("host");
		String port = configProps.getProperty("port");
		String database = configProps.getProperty("database");
		String user = configProps.getProperty("user");
		String password = configProps.getProperty("password");
		
	    try{
        	con = DriverManager.getConnection("jdbc:mysql" + "://" + host + ":" + port + "/" + database, user, password);
        }catch(SQLException SQLex){
        	con = null;
        }catch(Exception ex){
        	ex.printStackTrace();
        	con = null;
        }finally{
        	try{rs.close();} catch(Exception ex) { /*ignore*/}
			try{st.close();} catch(Exception ex) { /*ignore*/}
			try{pst.close();} catch(Exception ex) { /*ignore*/}
        }
    return con;
	}
	//returns a Timestamp object of the current timestamp
	public Timestamp getTimestamp(){
		java.util.Date date = new java.util.Date();
		Timestamp curTimestamp = new Timestamp(date.getTime());
		System.out.println(curTimestamp);
		return curTimestamp;
	}
	//returns username based on specific applications config settings 
	public String getUser() throws Exception{
		
		ConfigurationManager config = new ConfigurationManager(configFilePath);
		String username = config.getProperty("appUser");
		return username;
	}
	//returns the rank of the user defined in ConnectionProperties (done)
	public String getUserRank() throws Exception{
		
		ConfigurationManager config = new ConfigurationManager(configFilePath);
		String username = config.getProperty("appUser");
		String rk = null;
		
		try{
			con = getDBConnection();
			pst = con.prepareStatement("SELECT * FROM users WHERE username = ?");
			pst.setString(1, username);
			ResultSet rs = pst.executeQuery();
			
			while(rs.next()){
				rk = rs.getString("rank");
			}	
			pst.close();
			rs.close();
			con.close();
		}catch(SQLException SQLex){
			SQLex.printStackTrace();
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			try{if(con.isClosed() == false){con.close();}}catch(Exception ex){ex.printStackTrace();}
		}
		return rk;
	}
	//returns true/false value to verify if the user defined in ConnectionProperites is valid (done)
	public boolean verifyUser() throws Exception {
		
		ConfigurationManager config = new ConfigurationManager(configFilePath);
		String username = config.getProperty("appUser");
		String password = config.getProperty("appPassword");
		boolean userCheck = false;
		try{
			con = getDBConnection();
			JSONObject temp = queryDatabase("users", "username", username).getJSONObject(0);
			con.close();
			String un = null;
			try{
				un = temp.get("username").toString();
			}catch(Exception ex){un = " ";}
			
			String pw = null;
			try{
				pw = temp.get("password").toString();
			}catch(Exception ex){pw = " ";}
			
			if(username.equals(un)){
				if(password.equals(pw)){
					userCheck = true;
				}
			}
		}catch(Exception ex){ex.printStackTrace();}
		finally{
			try{if(con.isClosed() == false){con.close();}}catch(Exception ex){ex.printStackTrace();}
		}
	return userCheck;
	}
	//create a user and add to database (Requires admin)
	public void createUser(String username, String password, String rank) throws Exception{
		ConfigurationManager config = new ConfigurationManager(configFilePath);
		String appUser = config.getProperty("appUser");
		if(getUserRank().equals("admin")){
			try{
				con = getDBConnection();
				con.setAutoCommit(false);
				
				pst = con.prepareStatement("INSERT INTO users (username, password, rank) VALUES(?, ?, ?)");
				pst.setString(1, username);
				pst.setString(2, password);
				pst.setString(3, rank);
				pst.executeUpdate();
				
				con.commit();
				con.setAutoCommit(true);
			}catch(SQLException SQLex){
				SQLex.printStackTrace();
			}catch (Exception ex) {
		        ex.printStackTrace();	
			}finally{
				con.setAutoCommit(true);
				try{if(con.isClosed() == false){con.close();}}catch(Exception ex){ex.printStackTrace();}
			}
		}else{
			System.out.println(appUser+" does not have permission to do that!");
		}
	}
	//delete a user from database (Requires admin)
	public void deleteUser(String username) throws Exception{
		ConfigurationManager config = new ConfigurationManager(configFilePath);
		String appUser = config.getProperty("appUser");
		if(getUserRank().equals("admin")){
			try{
				con = getDBConnection();
				con.setAutoCommit(false);
				
				pst = con.prepareStatement("DELETE FROM users WHERE username = ?");
				pst.setString(1, username);
				pst.executeUpdate();
				
				con.commit();
				con.setAutoCommit(true);
			}catch(SQLException SQLex){
				SQLex.printStackTrace();
			}catch (Exception ex) {
		        ex.printStackTrace();	
			}finally{
				con.setAutoCommit(true);
				try{if(con.isClosed() == false){con.close();}}catch(Exception ex){ex.printStackTrace();}
			}
		}else{
			System.out.println(appUser+" does not have permission to do that!");
		}
	}
	//changes a user's rank in database (Requires admin)
	public void changeUserRank(String username, String rank) throws Exception{
		ConfigurationManager config = new ConfigurationManager(configFilePath);
		String appUser = config.getProperty("appUser");
		if(getUserRank().equals("admin")){
			try{
				con = getDBConnection();				
				pst = con.prepareStatement("UPDATE `users` SET `rank` = ? WHERE username = ?");
				pst.setString(1, rank);
				pst.setString(2,username);
				pst.executeUpdate();
				System.out.println("User Rank Updated Successfully!");
				con.setAutoCommit(true);
				con.close();
			}catch(SQLException SQLex){
				SQLex.printStackTrace();
			}catch (Exception ex) {
		        ex.printStackTrace();	
			}finally{
				con.setAutoCommit(true);
				try{if(con.isClosed() == false){con.close();}}catch(Exception ex){ex.printStackTrace();}
			}
		}else{
			System.out.println(appUser+" does not have permission to do that!");
		}
	}
	//changes a users password in database (Requires admin)
	public void changeUserPassword(String username, String password) throws Exception{
		try{
			con = getDBConnection();				
			pst = con.prepareStatement("UPDATE `users` SET `password` = ? WHERE username = ?");
			pst.setString(1, password);
			pst.setString(2,username);
			pst.executeUpdate();
			System.out.println("User Password Updated Successfully!");
			con.setAutoCommit(true);
			con.close();
		}catch(SQLException SQLex){
			SQLex.printStackTrace();
		}catch (Exception ex) {
	        ex.printStackTrace();	
		}finally{
			con.setAutoCommit(true);
			try{if(con.isClosed() == false){con.close();}}catch(Exception ex){ex.printStackTrace();}
		}
	}
	//create a program and add to database (Requires engineer)
	public void createProgram(String program) throws Exception{
		ConfigurationManager config = new ConfigurationManager(configFilePath);
		String appUser = config.getProperty("appUser");
		if(getUserRank().equals("admin")){
			try{
				con = getDBConnection();
				con.setAutoCommit(false);
				
				pst = con.prepareStatement("INSERT INTO customers (Program) VALUES(?)");
				pst.setString(1, program);
				pst.executeUpdate();
					  
				con.commit();
				con.setAutoCommit(true);
				con.close();
			}catch(SQLException SQLex){
				SQLex.printStackTrace();
			}catch (Exception ex) {
		        ex.printStackTrace();	
			}finally{
				con.setAutoCommit(true);
				try{if(con.isClosed() == false){con.close();}}catch(Exception ex){ex.printStackTrace();}
			}
		}else{
			System.out.println(appUser+" does not have permission to do that!");
		}
	}
	//returns jsonArray of User filtered by username (done)
	public JSONArray queryDatabase(String table, String column, String queryValue) throws Exception{
			
			ToJSON converter = new ToJSON();
			JSONArray json = new JSONArray();
			
			try{
				con = getDBConnection();
				pst = con.prepareStatement("SELECT * from `"+table+"` WHERE `"+column+"` = ? ORDER BY `"+column+"`");
				pst.setString(1, queryValue);
				ResultSet rs = pst.executeQuery();
				if (isResultSetEmpty(rs) == false ){    
					json = converter.toJSONArray(rs);
				}
				pst.close();
				con.close();
			}catch(SQLException SQLex){
				SQLex.printStackTrace();
			}catch(Exception ex){
				ex.printStackTrace();
			}finally{
				try{if(con.isClosed() == false){con.close();}}catch(Exception ex){ex.printStackTrace();}
			}
			return json;
	}	
	//returns jsonArray of User filtered by username (done)
	public JSONArray queryDatabase(String table, String column, int queryValue) throws Exception{
				
				ToJSON converter = new ToJSON();
				JSONArray json = new JSONArray();
				
				try{
					con = getDBConnection();
					pst = con.prepareStatement("SELECT * from `"+table+"` WHERE `"+column+"` = ? ORDER BY `"+column+"`");
					pst.setInt(1, queryValue);
					ResultSet rs = pst.executeQuery();
					if (isResultSetEmpty(rs) == false ){    
						json = converter.toJSONArray(rs);
					}
					pst.close();
					con.close();
				}catch(SQLException SQLex){
					SQLex.printStackTrace();
				}catch(Exception ex){
					ex.printStackTrace();
				}finally{
					try{if(con.isClosed() == false){con.close();}}catch(Exception ex){ex.printStackTrace();}
				}
				return json;
		}	
	//returns server user as a String (done)
	public String getServerUser() throws Exception{
		String username = null;;
		try{
			con = getDBConnection();
			DatabaseMetaData dmd = con.getMetaData();
			username = dmd.getUserName();
			con.close();
		}catch(SQLException SQLex){
			SQLex.printStackTrace();
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			try{if(con.isClosed() == false){con.close();}}catch(Exception ex){ex.printStackTrace();}
		}
		return username;
	}
	//returns jsonArray of All Part Numbers 
	public JSONArray queryAllParts(String queryValue) throws Exception{
			
			ToJSON converter = new ToJSON();
			JSONArray json = new JSONArray();
			try{
				con = getDBConnection();
				pst = con.prepareStatement("SELECT * FROM `parts list` ORDER BY `BosalPartNumber`");
				ResultSet rs = pst.executeQuery();
				if (isResultSetEmpty(rs) == false ){    
					json = converter.toJSONArray(rs);
				}
				pst.close();
				con.close();
			}catch(SQLException SQLex){
				SQLex.printStackTrace();
			}catch(Exception ex){
				ex.printStackTrace();
			}finally{
				try{if(con.isClosed() == false){con.close();}}catch(Exception ex){ex.printStackTrace();}
			}
			return json;
		}
	//returns jsonArray filtered by Material PartType
	public JSONArray queryMaterialPartType(int queryValue) throws Exception{
		
		ToJSON converter = new ToJSON();
		JSONArray json = new JSONArray();
		
		try{
			con = getDBConnection();
			pst = con.prepareStatement("SELECT * FROM `materials reference` WHERE PartType = ? ORDER BY `PartType`, `Material` ASC");
			pst.setInt(1, queryValue);
			ResultSet rs = pst.executeQuery();
			
			if (isResultSetEmpty(rs) == false){ 
				json = converter.toJSONArray(rs);
			}
			pst.close();
			con.close();
		}catch(SQLException SQLex){
			SQLex.printStackTrace();
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			try{if(con.isClosed() == false){con.close();}}catch(Exception ex){ex.printStackTrace();}
		}
		return json;
	}
	//returns jsonArray filtered by MaterialDescription
	public JSONArray queryMaterialDescription(int partType, int matNumber) throws Exception{
		
		ToJSON converter = new ToJSON();
		JSONArray json = new JSONArray();
		
		try{
			con = getDBConnection();
			pst = con.prepareStatement("SELECT * FROM `materials reference` WHERE `PartType` = ? and `Material` = ?");
			pst.setInt(1, partType);
			pst.setInt(2, matNumber);
			ResultSet rs = pst.executeQuery();
			
			if (isResultSetEmpty(rs) == false){    
				json = converter.toJSONArray(rs);
			}
			pst.close();
			con.close();
		}catch(SQLException SQLex){
			SQLex.printStackTrace();
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			try{if(con.isClosed() == false){con.close();}}catch(Exception ex){ex.printStackTrace();}
		}
		return json;
	}
	//returns jsonArray of all Part Types (done)
	public JSONArray queryReturnAllTypes() throws Exception{
		
		ToJSON converter = new ToJSON();
		JSONArray json = new JSONArray();
		
		try{
			con = getDBConnection();
			pst = con.prepareStatement("SELECT * from `type file` ORDER BY `PartType` ASC");
			
			ResultSet rs = pst.executeQuery();
			
			if (isResultSetEmpty(rs) == false ){    
				json = converter.toJSONArray(rs);
			}
			pst.close();
			con.close();
		}catch(SQLException SQLex){
			SQLex.printStackTrace();
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			try{if(con.isClosed() == false){con.close();}}catch(Exception ex){ex.printStackTrace();}
		}
		return json;
	}
	//returns jsonArray of all Part Descriptions (done)
	public JSONArray queryReturnAllDescriptions() throws Exception{
			
			ToJSON converter = new ToJSON();
			JSONArray json = new JSONArray();
			
			try{
				con = getDBConnection();
				pst = con.prepareStatement("SELECT * from `description list` ORDER BY `Name` ASC");
				
				ResultSet rs = pst.executeQuery();
				
				if (isResultSetEmpty(rs) == false){    
					json = converter.toJSONArray(rs);
				}
				pst.close();
				con.close();
			}catch(SQLException SQLex){
				SQLex.printStackTrace();
			}catch(Exception ex){
				ex.printStackTrace();
			}finally{
				try{if(con.isClosed() == false){con.close();}}catch(Exception ex){ex.printStackTrace();}
			}
			return json;
		}
	//iterates onto the next integer for Sequence based on PartType passed in
	public void iterateNextSequenceNumber(int partType) throws Exception{	
		try{
			int curSeqNum = (int) queryDatabase("type file", "PartType", partType).getJSONObject(0).get("SeqNumber");
			int newSeqNum = curSeqNum + 1;
			con = getDBConnection();
			pst = con.prepareStatement("UPDATE `type file` SET `SeqNumber` = ? WHERE PartType = ?");
			pst.setInt(1, newSeqNum);
			pst.setInt(2, partType);
			pst.executeUpdate();
			pst.close();
			con.close();
		}catch(SQLException SQLex){
			SQLex.printStackTrace();
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			try{if(con.isClosed() == false){con.close();}}catch(Exception ex){ex.printStackTrace();}
		}
	}
	//inserts a new row into `parts list` to create a new part
	public void insertNewPart(int partType, int mat, String BosalPartNumber, 
			String CustomerPartNumber, String SupplierPartNumber, String Description,
			String Program, int seq, String typeDescription, String DrawingNumber,
			int Rev, String CreatedBy, Timestamp Created, Timestamp Updated, String UpdatedBy) throws Exception{	
		try{
			con = getDBConnection();
			pst = con.prepareStatement("INSERT INTO `parts list` (PartType, Material, BosalPartNumber, CustPartNumber,"
										+ " SupPartNumber, PartDescription, Program, SeqNumber, TypeDescription, "
										+ "DrawingNumber, Rev, CreatedBy, Created, UpdatedBy, Updated ) "
										+ "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
			pst.setInt(1, partType);
			pst.setInt(2, mat);
			pst.setString(3, BosalPartNumber);
			pst.setString(4, CustomerPartNumber);
			pst.setString(5, SupplierPartNumber);
			pst.setString(6, Description);
			pst.setString(7, Program);
			pst.setInt(8, seq);
			pst.setString(9, typeDescription);
			pst.setString(10, DrawingNumber);
			pst.setInt(11, Rev);
			pst.setString(12, CreatedBy);
			pst.setTimestamp(13, Created);
			pst.setString(14, UpdatedBy);
			pst.setTimestamp(15, Updated);
			pst.executeUpdate();
			pst.close();
			iterateNextSequenceNumber(partType);
			con.close();
		}catch(SQLException SQLex){
			SQLex.printStackTrace();
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			try{if(con.isClosed() == false){con.close();}}catch(Exception ex){ex.printStackTrace();}
		}
	}
	//deletes a row from `parts list` using BosalPartNumber (admin)
	public void deletePart(String BosalPartNumber) throws Exception{	
			ConfigurationManager config = new ConfigurationManager(configFilePath);
			String appUser = config.getProperty("appUser");
			if(getUserRank().equals("admin")){
				try{
					con = getDBConnection();
					pst = con.prepareStatement("DELETE FROM `parts list` WHERE `BosalPartNumber` = ?");
					pst.setString(1, BosalPartNumber);
					pst.executeUpdate();
					pst.close();	
					con.close();
				}catch(SQLException SQLex){
					SQLex.printStackTrace();
				}catch(Exception ex){
					ex.printStackTrace();
				}finally{
					try{if(con.isClosed() == false){con.close();}}catch(Exception ex){ex.printStackTrace();}
				}
			}else{
				System.out.println(appUser+" does not have permission to do that!");
			}
		}
	//updates Part Information
	public void update(String BosalPartNumber, String CusPartNumber, 
			String SupPartNumber, String Description, String program, 
			String DrawingNumber, int Rev) throws Exception{
			
			try{
				con = getDBConnection();
				pst = con.prepareStatement("UPDATE `parts list` SET `PartDescription` = ?, " 
						+ " `CustPartNumber` = ?, " 
						+ " `SupPartNumber` = ?, "
						+ " `Program` = ?, "
						+ " `DrawingNumber` = ?, "
						+ " `Rev` = ?, "
						+ " `UpdatedBy` = ?, "
						+ " `Updated` = ?"
						+ "WHERE `BosalPartNUmber`= ?");
				pst.setString(1, Description);
				pst.setString(2, CusPartNumber);
				pst.setString(3, SupPartNumber);
				pst.setString(4,  program);
				pst.setString(5, DrawingNumber);
				pst.setInt(6, Rev+1);
				pst.setString(7, getUser());
				pst.setTimestamp(8, getTimestamp());
				pst.setString(9, BosalPartNumber);
				pst.executeUpdate();
				pst.close();
				con.close();
			}catch(SQLException SQLex){
				SQLex.printStackTrace();
			}catch(Exception ex){
				ex.printStackTrace();
			}finally{
				try{if(con.isClosed() == false){con.close();}}catch(Exception ex){ex.printStackTrace();}
			}
		}
}

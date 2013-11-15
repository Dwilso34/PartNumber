package binaparts.dao;

import java.sql.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.joda.time.LocalDate;
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
		}catch(ClassNotFoundException ex){ex.printStackTrace();}
	}
	//returns a boolean answer whether ResultSet is empty
	private boolean isResultSetEmpty(ResultSet rs){
		boolean status = true;
		try {
			if (rs.isBeforeFirst() == true){
				 status = false;
			}
		}catch(Exception ex){ex.printStackTrace();}
		return status;
	}
	//returns a String[] of parts list table's column names
	public String[] getColumnNames(String table, String column, String queryValue){
		try{		
			getDBConnection();
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
			getDBConnection();
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
		return curTimestamp;
	}
	//returns a Timestamp object of the current timestamp
	public LocalDate getDate(){
		LocalDate date = new LocalDate();
		return date;
	}
	//returns username based on specific applications config settings 
	public String getUser() throws Exception{
		
		ConfigurationManager config = new ConfigurationManager(configFilePath);
		String username = config.getProperty("appUser");
		return username;
	}
	//returns users Real name based on specific applications config settings 
	public String getUsersName() throws Exception{
		
		ConfigurationManager config = new ConfigurationManager(configFilePath);
		String username = config.getProperty("appUser");
		String usersName = null;
		String first = null;
		String last = null;
		
		try{
			getDBConnection();
			pst = con.prepareStatement("SELECT * FROM `users` WHERE `username` = ?");
			pst.setString(1, username);
			ResultSet rs = pst.executeQuery();
			
			while(rs.next()){
				first = rs.getString("firstName");
				last = rs.getString("lastName");
			}
			usersName = first + " " + last;
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
		return usersName;
	}
	//returns the rank of the user defined in ConnectionProperties (done)
	public String getUserRank() throws Exception{
		
		ConfigurationManager config = new ConfigurationManager(configFilePath);
		String username = config.getProperty("appUser");
		String rk = null;
		
		try{
			getDBConnection();
			pst = con.prepareStatement("SELECT * FROM `users` WHERE `username` = ?");
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
			getDBConnection();
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
	public void createUser(String username, String password, String rank, 
			String firstName, String lastName) throws Exception{
		ConfigurationManager config = new ConfigurationManager(configFilePath);
		String appUser = config.getProperty("appUser");
		if(getUserRank().equals("admin")){
			try{
				getDBConnection();
				con.setAutoCommit(false);
				
				pst = con.prepareStatement("INSERT INTO `users` (username, password, rank, firstName, lastName) VALUES(?, ?, ?, ?, ?)");
				pst.setString(1, username);
				pst.setString(2, password);
				pst.setString(3, rank);
				pst.setString(4, firstName);
				pst.setString(5, lastName);
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
				getDBConnection();
				con.setAutoCommit(false);
				
				pst = con.prepareStatement("DELETE FROM `users` WHERE `username` = ?");
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
				getDBConnection();				
				pst = con.prepareStatement("UPDATE `users` SET `rank` = ? WHERE username = ?");
				pst.setString(1, rank);
				pst.setString(2,username);
				pst.executeUpdate();
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
	//changes a users password in database 
	public void changeUserPassword(String username, String password) throws Exception{
		try{
			getDBConnection();				
			pst = con.prepareStatement("UPDATE `users` SET `password` = ? WHERE `username` = ?");
			pst.setString(1, password);
			pst.setString(2,username);
			pst.executeUpdate();
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
	//create a program and add to database (Requires admin)
	public void createProgram(String Customer, String Cust, String Program, String programStart, String programEnd) throws Exception{
		try{
			Timestamp Created = getTimestamp();
			String CreatedBy = getUsersName();
			getDBConnection();
			con.setAutoCommit(false);
			pst = con.prepareStatement("INSERT INTO `programs` (Customer, Cust, Program, ProgramStart, ProgramEnd, Created, CreatedBy) VALUES(?, ? ,?, ?, ?, ?, ?)");
			pst.setString(1, Customer);
			pst.setString(2, Cust);
			pst.setString(3, Program);
			pst.setString(4, programStart);
			pst.setString(5, programEnd);
			pst.setTimestamp(6, Created);
			pst.setString(7, CreatedBy);
			pst.executeUpdate();
				  
			con.commit();
			con.setAutoCommit(true);
		}catch(SQLException SQLex){
			SQLex.printStackTrace();
		}catch (Exception ex) {
	        ex.printStackTrace();	
		}finally{
			
			try{if(con.isClosed() == false){con.setAutoCommit(true);con.close();}}catch(Exception ex){ex.printStackTrace();}
		}
	}
	//delete a user from database (Requires admin)
	public void deleteProgram(String program) throws Exception{
		ConfigurationManager config = new ConfigurationManager(configFilePath);
		String appUser = config.getProperty("appUser");
		if(getUserRank().equals("admin")){
			try{
				getDBConnection();
				con.setAutoCommit(false);
				
				pst = con.prepareStatement("DELETE FROM `programs` WHERE `Program` = ?");
				pst.setString(1, program);
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
	//create a customer and add it to the database
	public void createCustomer(String newCustomer, String newCust) throws Exception{
		try{
			Timestamp Created = getTimestamp();
			String CreatedBy = getUsersName();
			getDBConnection();
			con.setAutoCommit(false);
			pst = con.prepareStatement("INSERT INTO `customers` (Customer, Cust, Created, CreatedBy) VALUES(?, ?, ?, ?)");
			pst.setString(1, newCustomer);
			pst.setString(2, newCust);
			pst.setTimestamp(3, Created);
			pst.setString(4, CreatedBy);
			pst.executeUpdate();
				  
			con.commit();
			con.setAutoCommit(true);
			con.close();
		}catch(SQLException SQLex){
			SQLex.printStackTrace();
		}catch (Exception ex) {
	        ex.printStackTrace();	
		}finally{
			
			try{if(con.isClosed() == false){con.setAutoCommit(true);con.close();}}catch(Exception ex){ex.printStackTrace();}
		}		
	}
	//delete a user from database (Requires admin)
	public void deleteCustomer(String customer) throws Exception{
		ConfigurationManager config = new ConfigurationManager(configFilePath);
		String appUser = config.getProperty("appUser");
		if(getUserRank().equals("admin")){
			try{
				getDBConnection();
				con.setAutoCommit(false);
				
				pst = con.prepareStatement("DELETE FROM `customers` WHERE `Customer` = ?");
				pst.setString(1, customer);
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
	//returns jsonArray of User filtered by username (done)
	public JSONArray queryDatabase(String table, String column, String queryValue) throws Exception{
			
			ToJSON converter = new ToJSON();
			JSONArray json = new JSONArray();
			
			try{
				getDBConnection();
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
					getDBConnection();
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
			getDBConnection();
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
				getDBConnection();
				pst = con.prepareStatement("SELECT * FROM `bosal parts` ORDER BY `BosalPartNumber`");
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
			getDBConnection();
			pst = con.prepareStatement("SELECT * FROM `materials reference` WHERE `PartType` = ? ORDER BY `PartType`, `Material` ASC");
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
			getDBConnection();
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
			getDBConnection();
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
	//returns jsonArray of all Programs (done)
	public JSONArray queryReturnAllEngines() throws Exception{
			
			ToJSON converter = new ToJSON();
			JSONArray json = new JSONArray();
			
			try{
				getDBConnection();
				pst = con.prepareStatement("SELECT * from `engines` ORDER BY `Engine` ASC");
				
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
	//returns jsonArray of all Programs (done)
	public JSONArray queryReturnAllPrograms() throws Exception{
			
			ToJSON converter = new ToJSON();
			JSONArray json = new JSONArray();
			
			try{
				getDBConnection();
				pst = con.prepareStatement("SELECT * from `programs` ORDER BY `Program` ASC");
				
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
	//returns jsonArray of all Customers (done)
	public JSONArray queryReturnAllCustomers() throws Exception{
			
			ToJSON converter = new ToJSON();
			JSONArray json = new JSONArray();
			
			try{
				getDBConnection();
				pst = con.prepareStatement("SELECT * from `customers` ORDER BY `Customer` ASC");
				rs = pst.executeQuery();
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
				getDBConnection();
				pst = con.prepareStatement("SELECT * from `description list` ORDER BY `Name` ASC");
				
				rs = pst.executeQuery();
				
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
			getDBConnection();
			pst = con.prepareStatement("UPDATE `type file` SET `SeqNumber` = ? WHERE `PartType` = ?");
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
	public void insertNewPart(int PartType, int Material, String BosalPartNumber, 
			String CustomerPartNumber, String SupplierPartNumber, String Description,
			String Program, int Seq, String TypeDescription, int Rev, String DrawingNumber,
			int DrawingRev, String DrawingRevDate, String ProductionReleaseDate) throws Exception{	
		try{
			String usersname = getUsersName();
			Timestamp timestamp = getTimestamp();
			getDBConnection();
			pst = con.prepareStatement("INSERT INTO `bosal parts` "
					+ "(PartType, "
					+ "Material, "
					+ "BosalPartNumber, "
					+ "CustPartNumber, "
					+ "SupPartNumber, "
					+ "PartDescription, "
					+ "Program, "
					+ "SeqNumber, "
					+ "TypeDescription, "
					+ "Rev, "
					+ "DrawingNumber, "
					+ "DrawingRev, "
					+ "DrawingRevDate, "
					+ "ProductionReleaseDate, "
					+ "CreatedBy, "
					+ "Created, "
					+ "UpdatedBy, "
					+ "Updated) "
					+ "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
			pst.setInt(1, PartType);
			pst.setInt(2, Material);
			pst.setString(3, BosalPartNumber);
			pst.setString(4, CustomerPartNumber);
			pst.setString(5, SupplierPartNumber);
			pst.setString(6, Description);
			pst.setString(7, Program);
			pst.setInt(8, Seq);
			pst.setString(9, TypeDescription);
			pst.setInt(10, Rev);
			pst.setString(11, DrawingNumber);
			pst.setInt(12, DrawingRev);
			pst.setString(13, DrawingRevDate);
			pst.setString(14, ProductionReleaseDate);
			pst.setString(15, usersname);
			pst.setTimestamp(16, timestamp);
			pst.setString(17, usersname);
			pst.setTimestamp(18, timestamp);
			pst.executeUpdate();
			pst.close();
			iterateNextSequenceNumber(PartType);
			con.close();
			System.out.println("New Part Created Successfully");
		}catch(SQLException SQLex){
			SQLex.printStackTrace();
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			try{if(con.isClosed() == false){con.close();}}catch(Exception ex){ex.printStackTrace();}
		}
	}
	//inserts a new row into `experimental parts` to create a new part
	public void insertExperimentalPart(String Program, String PartDescription,
			String CustPartNumber, String Customer, String Year) throws Exception{	
		try{
			getDBConnection();
			String PartNumber = "";
			pst = con.prepareStatement("INSERT INTO `experimental parts` (Engineer, Program, PartDescription,"
					+ "CustPartNumber, Customer, YearCode, PartNumber, Date) "
										+ "VALUES(?, ?, ?, ?, ?, ?, ?, ?)");
			pst.setString(1, getUser());
			pst.setString(2, Program);
			pst.setString(3, PartDescription);
			pst.setString(4, CustPartNumber);
			pst.setString(5, Customer);
			pst.setString(6, Year);
			pst.setString(7, PartNumber);
			pst.setString(8, getDate().toString());
			pst.executeUpdate();
			rs = pst.executeQuery("SELECT LAST_INSERT_ID()");
			int lastID = 0;
			while(rs.next()){
				lastID = rs.getInt(1);
			}
			PartNumber = String.valueOf(lastID + 1328);
			pst = con.prepareStatement("UPDATE `experimental parts` SET `PartNumber` = ? WHERE `Index` = ?");
			pst.setString(1, PartNumber);
			pst.setInt(2, lastID);
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
	//inserts a new row into `breakdown lists info` to create a BDL
	public void insertBDLInfo(JSONArray bdlInfo) throws Exception{	
		try{
			String usersname = getUsersName();
			Timestamp timestamp = getTimestamp();
			getDBConnection();
			String BosalPartNumber = bdlInfo.getJSONObject(0).get("BreakdownListNumber").toString();
			System.out.println("json array length is "+bdlInfo.length());
			int count = (bdlInfo.length()-1);			
			System.out.println("The count is "+count);
			String[] values = new String[count];
			System.out.println(values.length);
			int i = 0;
			String statement = "INSERT INTO `breakdown lists info` (BreakdownListNumber";
			
			try {
				values[i] = bdlInfo.getJSONObject(i+1).get("Rev").toString();
				System.out.println(values[i]);
				statement = statement + ", Rev";
				i++;
			} catch (Exception ex) {ex.printStackTrace();}
			
			try {
				values[i] = bdlInfo.getJSONObject(i+1).get("RevDate").toString();
				System.out.println(values[i]);
				statement = statement + ", RevDate";
				i++;
			} catch (Exception ex) {ex.printStackTrace();}
			
			try {
				values[i] = bdlInfo.getJSONObject(i+1).get("ReleaseDate").toString();
				System.out.println(values[i]);
				statement = statement + ", ReleaseDate";
				i++;
			} catch (Exception ex) {ex.printStackTrace();}
			
			try {
				values[i] = bdlInfo.getJSONObject(i+1).get("Production").toString();
				System.out.println(values[i]);
				statement = statement + ", Production";
				i++;
			} catch (Exception ex) {ex.printStackTrace();}
			
			try {
				values[i] = bdlInfo.getJSONObject(i+1).get("RelPlant1").toString();
				System.out.println(values[i]);
				statement = statement + ", RelPlant1";
				i++;
			} catch (Exception ex) {ex.printStackTrace();}
			
			try {
				values[i] = bdlInfo.getJSONObject(i+1).get("RelPlant2").toString();
				System.out.println(values[i]);
				statement = statement + ", RelPlant2";
				i++;
			} catch (Exception ex) {ex.printStackTrace();}
			
			try {
				values[i] = bdlInfo.getJSONObject(i+1).get("RelSupplier").toString();
				System.out.println(values[i]);
				statement = statement + ", RelSupplier";
				i++;
			} catch (Exception ex) {ex.printStackTrace();}
			
			try {
				values[i] = bdlInfo.getJSONObject(i+1).get("Volume").toString();
				System.out.println(values[i]);
				statement = statement + ", Volume";
				i++;
			} catch (Exception ex) {ex.printStackTrace();}
			
			try {
				values[i] = bdlInfo.getJSONObject(i+1).get("Length").toString();
				System.out.println(values[i]);
				statement = statement + ", Length";
				i++;
			} catch (Exception ex) {ex.printStackTrace();}
			
			try {
				values[i] = bdlInfo.getJSONObject(i+1).get("Section").toString();
				System.out.println(values[i]);
				statement = statement + ", Section";
				i++;
			} catch (Exception ex) {ex.printStackTrace();}
				
			statement = statement + ", CreatedBy, Created, UpdatedBy, Updated) VALUES(";
			
			//loop to add a parameter for each column value going into Database
			for (i = 0; i < count+5; i++){
				if (i == 0){
					statement = statement + "?";
				} else {
					statement = statement + ", ?";
				}				
			}
			statement = statement + ")";
			System.out.println(statement);
			pst = con.prepareStatement(statement);
			//loop to set the values of the parameters going into the Database
			for (i = -1; i < count; i++){
				if (i == -1){
					pst.setString(i+2, BosalPartNumber);
				} 
				//if i is even then an Item is added to the parameters
				else {
					pst.setString(i+2, values[i]);
					System.out.println(values[i]);
				} 
			}				
			pst.setString((count+2), usersname);
			pst.setTimestamp((count+3), timestamp);
			pst.setString((count+4), usersname);
			pst.setTimestamp((count+5), timestamp);
			pst.executeUpdate();
			pst.close();
			con.close();
			System.out.println("New BDL Info Created Successfully");
		}catch(SQLException SQLex){
			SQLex.printStackTrace();
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			try{if(con.isClosed() == false){con.close();}}catch(Exception ex){ex.printStackTrace();}
		}
	}
	//inserts a new row into `breakdown lists info` to create a BDL
	public void insertNewBDL(JSONArray bdl) throws Exception{	
		try{
			String usersname = getUsersName();
			Timestamp timestamp = getTimestamp();
			getDBConnection();
			String BosalPartNumber = bdl.getJSONObject(0).get("BreakdownListNumber").toString();
			for (double x = (((double)bdl.length())-1)/10; x > 0; x--) {
				int count;
				if (x >= 1) {
					count = (2*10);
				} else {
					count = (int)(2*(x*10));
				}				
				int index = 0; //index through the JSONArray
				String[] values = new String[count];
				String statement = "INSERT INTO `breakdown lists` (";
				//loop to grab Column names going into Database
				for (int i = -1; i < count; i++){
					if (i == -1){
						statement = statement + "BreakdownListNumber";
						index++;
					}
					//if i is even then an Item is added to the statement
					else if (i%2 == 0){
						statement = statement + ", Item" + index;
						values[i] = bdl.getJSONObject(index).get("Item"+index).toString();
					}
					//if i is odd then a Qty is added to the statement
					else if (i%2 != 0){
						statement = statement + ", Qty"+index;
						values[i] = bdl.getJSONObject(index).get("Qty"+index).toString();
						index++;
					}
				}
				statement = statement + ", CreatedBy, Created, UpdatedBy, Updated) VALUES(";
				//loop to add a parameter for each column value going into Database
				for (int i = 0; i < count+5; i++){
					if (i == 0){
						statement = statement + "?";
					} else {
						statement = statement + ", ?";
					}				
				}
				statement = statement + ")";
				pst = con.prepareStatement(statement);
				//loop to set the values of the parameters going into the Database
				for (int i = -1; i < count; i++){
					if (i == -1){
						pst.setString(i+2, BosalPartNumber);
					} 
					//if i is even then an Item is added to the parameters
					else if (i%2 == 0){
						pst.setString(i+2, values[i]);
					} 
					//if i is odd then a Qty is added to the parameters
					else if (i%2 != 0){
						pst.setInt(i+2, Integer.valueOf(values[i]));
					}
				}				
				pst.setString((count+2), usersname);
				pst.setTimestamp((count+3), timestamp);
				pst.setString((count+4), usersname);
				pst.setTimestamp((count+5), timestamp);
				pst.executeUpdate();
				pst.close();
			}
			con.close();
			System.out.println("New BDL Info Created Successfully");
		}catch(SQLException SQLex){
			SQLex.printStackTrace();
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			try{if(con.isClosed() == false){con.close();}}catch(Exception ex){ex.printStackTrace();}
		}
	}	
	//Returns JSONArray of Experimental Part Numbers (to be used prior to Auto-Increment for conversion)
	public JSONArray queryReturnAllExpParts() throws Exception{
		
		ToJSON converter = new ToJSON();
		JSONArray json = new JSONArray();
		
		try{
			getDBConnection();
			pst = con.prepareStatement("SELECT * FROM `experimental parts`");
			
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
	//Returns JSONArray of Experimental Part Numbers for displaying part number
	public JSONArray queryReturnExpPart() throws Exception{
			
			ToJSON converter = new ToJSON();
			JSONArray json = new JSONArray();
			
			try{
				getDBConnection();
				pst = con.prepareStatement("SELECT `Customer`, + `YearCode`, + `PartNumber` FROM `experimental parts` ORDER BY `PartNumber` DESC LIMIT 1 ");
				
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
	//deletes a BosalPartNumber from parts list 
	public void deletePart(String BosalPartNumber) throws Exception{	
			ConfigurationManager config = new ConfigurationManager(configFilePath);
			String appUser = config.getProperty("appUser");
			if(getUserRank().equals("admin")){
				try{
					getDBConnection();
					pst = con.prepareStatement("DELETE FROM `bosal parts` WHERE `BosalPartNumber` = ?");
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
	//updates a BosalPartNumber in parts list
	public void update(String BosalPartNumber, String CusPartNumber, 
			String SupPartNumber, String Description, String Program, 
			int Rev, String DrawingNumber, int DrawingRev, String DrawingRevDate,
			String ProductionReleaseDate) throws Exception{
			
			try{
				String usersname = getUsersName();
				Timestamp timestamp = getTimestamp();
				getDBConnection();
				pst = con.prepareStatement("UPDATE `bosal parts` SET "
						+ "`PartDescription` = ?, " 
						+ "`CustPartNumber` = ?, " 
						+ "`SupPartNumber` = ?, "
						+ "`Program` = ?, "
						+ "`Rev` = ?, "
						+ "`DrawingNumber` = ?, "
						+ "`DrawingRev` = ?, "
						+ "`DrawingRevDate` = ?, "
						+ "`ProductionReleaseDate` = ?, "
						+ "`UpdatedBy` = ?, "
						+ "`Updated` = ?"
						+ "WHERE `BosalPartNUmber`= ?");
				pst.setString(1, Description);
				pst.setString(2, CusPartNumber);
				pst.setString(3, SupPartNumber);
				pst.setString(4,  Program);
				pst.setInt(5, Rev);
				pst.setString(6, DrawingNumber);
				pst.setInt(7, DrawingRev);
				pst.setString(8, DrawingRevDate);
				pst.setString(9, ProductionReleaseDate);
				pst.setString(10, usersname);
				pst.setTimestamp(11, timestamp);
				pst.setString(12, BosalPartNumber);
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

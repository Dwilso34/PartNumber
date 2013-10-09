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
			if (!rs.isBeforeFirst() ){
				 status = false;
			}
		}catch(Exception ex){/*Ignore*/}
		return status;
	}
	//returns a String[] of parts list table's column names
	public String[] getColumnNames(String search, String queryValue){
		try{		
			con = getDBConnection();
			if (search.equals("All")){
				pst = con.prepareStatement("SELECT * FROM `parts list`");
			}else{
				pst = con.prepareStatement("SELECT * FROM `parts list` WHERE "+search+" = ?");
				pst.setString(1, queryValue);
			}
						
			ResultSet rs = pst.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			
			int count = rsmd.getColumnCount();
			String[] columnNames = new String[count];
			
			for (int i = 0; i < count; i++ ) {
				columnNames[i] = rsmd.getColumnName(i+1);
			}
			return columnNames;			
		}catch(Exception ex){
			String[] columnNames = null;
			System.out.println("i screwed up");
			ex.printStackTrace();
			return columnNames;
		}
	}
	//returns a String[] of delta 1 table's column names
	public String[] getDelta1ColumnNames(String search, String queryValue){
			try{		
				con = getDBConnection();
				System.out.println("i got a connection");
				if (search.equals("All")){
					pst = con.prepareStatement("SELECT * FROM `delta 1 parts`");
					System.out.println("pst 1 created");
				}else{
					pst = con.prepareStatement("SELECT * FROM `delta 1 parts`, WHERE "+search+" = ?");
					System.out.println("pst 2 created");
					pst.setString(1, queryValue);
					System.out.println("pst set");
				}
							
				ResultSet rs = pst.executeQuery();
				System.out.println("result set filled");
				ResultSetMetaData rsmd = rs.getMetaData();
				System.out.println("rsmd filled");
				int count = rsmd.getColumnCount();
				System.out.println(count);
				String[] columnNames = new String[count];
				
				for (int i = 0; i < count; i++ ) {
					columnNames[i] = rsmd.getColumnName(i+1);
					System.out.println(columnNames[i]);
				}
				return columnNames;			
			}catch(Exception ex){
				String[] columnNames = null;
				System.out.println("i screwed up");
				ex.printStackTrace();
				return columnNames;
			}
		}
	//returns an integer equal to the number of rows returned by a parts list search
	public int getRowCount(String search, String queryValue) throws Exception{
		int rowCount = 0;
		try{		
			con = getDBConnection();
			if (search.equals("All")){
				pst = con.prepareStatement("SELECT * FROM `parts list`");
			}else{
				pst = con.prepareStatement("SELECT * FROM `parts list` WHERE "+search+" = ?");
				pst.setString(1, queryValue);
			}
			ResultSet rs = pst.executeQuery();
			
			while(rs.next()){
				rowCount++;
			}
		}catch(Exception ex){ex.printStackTrace();
		}
		return rowCount;
	}
	//returns an integer equal to the number of rows returned by a delta 1 list search
	public int getDelta1RowCount(String search, String queryValue) throws Exception{
			int rowCount = 0;
			try{		
				con = getDBConnection();
				if (search.equals("All")){
					pst = con.prepareStatement("SELECT * FROM `delta 1 parts`");
				}else{
					pst = con.prepareStatement("SELECT * FROM `delta 1 parts` WHERE "+search+" = ?");
					pst.setString(1, queryValue);
				}
				ResultSet rs = pst.executeQuery();
				
				while(rs.next()){
					for (int i = 1; i <= rs.getMetaData().getColumnCount();i++){
						System.out.print(rs.getString(i)+" ");
					}
					System.out.println();
					rowCount++;
					System.out.println("row count"+rowCount);
				}
			}catch(Exception ex){ex.printStackTrace();
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
			try{rs.close();} catch(Exception ex) { /*ignore*/}
			try{st.close();} catch(Exception ex) { /*ignore*/}
			try{pst.close();} catch(Exception ex) { /*ignore*/}
			try{con.close();} catch(Exception ex) { /*ignore*/}
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
			if(con == null){
			System.out.println("there is not a connection still");
			}
			if(con != null){
				System.out.println("there is a connection still");
			}
			con = getDBConnection();
			JSONObject temp = queryReturnUser(username).getJSONObject(0);
			
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
		}catch(Exception ex){/*ignore*/}
		finally{
			try{
				if(con != null){
					System.out.println("there is a connection still");
				}
				con.close();
				if(con != null){
					System.out.println("there is a connection still");
				}
			}catch(Exception ex){ex.printStackTrace();}
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
				try{rs.close();} catch(Exception ex) { /*ignore*/}
				try{st.close();} catch(Exception ex) { /*ignore*/}
				try{pst.close();} catch(Exception ex) { /*ignore*/}
				try{con.close();} catch(Exception ex) { /*ignore*/}
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
				try{rs.close();} catch(Exception ex) { /*ignore*/}
				try{st.close();} catch(Exception ex) { /*ignore*/}
				try{pst.close();} catch(Exception ex) { /*ignore*/}
				try{con.close();} catch(Exception ex) { /*ignore*/}
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
			}catch(SQLException SQLex){
				SQLex.printStackTrace();
			}catch (Exception ex) {
		        ex.printStackTrace();	
			}finally{
				con.setAutoCommit(true);
				try{rs.close();} catch(Exception ex) { /*ignore*/}
				try{st.close();} catch(Exception ex) { /*ignore*/}
				try{pst.close();} catch(Exception ex) { /*ignore*/}
				try{con.close();} catch(Exception ex) { /*ignore*/}
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
		}catch(SQLException SQLex){
			SQLex.printStackTrace();
		}catch (Exception ex) {
	        ex.printStackTrace();	
		}finally{
			con.setAutoCommit(true);
			try{rs.close();} catch(Exception ex) { /*ignore*/}
			try{st.close();} catch(Exception ex) { /*ignore*/}
			try{pst.close();} catch(Exception ex) { /*ignore*/}
			try{con.close();} catch(Exception ex) { /*ignore*/}
		}
	}
	//returns jsonArray of User filtered by username (done)
	public JSONArray queryReturnUser(String username) throws Exception{
			
			ToJSON converter = new ToJSON();
			JSONArray json = new JSONArray();
			
			try{
				con = getDBConnection();
				pst = con.prepareStatement("SELECT * from `users` WHERE username = ?");
				pst.setString(1, username);
				ResultSet rs = pst.executeQuery();
				json = converter.toJSONArray(rs);
				pst.close();
			}catch(SQLException SQLex){
				SQLex.printStackTrace();
			}catch(Exception ex){
				ex.printStackTrace();
			}finally{
				try{rs.close();} catch(Exception ex) { /*ignore*/}
				try{st.close();} catch(Exception ex) { /*ignore*/}
				try{pst.close();} catch(Exception ex) { /*ignore*/}
				try{con.close();} catch(Exception ex) { ex.printStackTrace();/*ignore*/}
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
		}catch(SQLException SQLex){
			SQLex.printStackTrace();
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			try{rs.close();} catch(Exception ex) { /*ignore*/}
			try{st.close();} catch(Exception ex) { /*ignore*/}
			try{pst.close();} catch(Exception ex) { /*ignore*/}
			try{con.close();} catch(Exception ex) { /*ignore*/}
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
				if (isResultSetEmpty(rs) == true ){    
					json = converter.toJSONArray(rs);
				}
				pst.close();
			}catch(SQLException SQLex){
				SQLex.printStackTrace();
			}catch(Exception ex){
				ex.printStackTrace();
			}finally{
				try{rs.close();} catch(Exception ex) { /*ignore*/}
				try{st.close();} catch(Exception ex) { /*ignore*/}
				try{pst.close();} catch(Exception ex) { /*ignore*/}
				try{con.close();} catch(Exception ex) { /*ignore*/}
			}
			return json;
		}
	//returns jsonArray filtered by BosalPartNumber
	public JSONArray queryBosalPartNumber(String queryValue) throws Exception{
			
			ToJSON converter = new ToJSON();
			JSONArray json = new JSONArray();
			
			try{
				con = getDBConnection();
				pst = con.prepareStatement("SELECT * FROM `parts list` WHERE BosalPartNumber = ? ORDER BY `BosalPartNumber`");
				pst.setString(1, queryValue);
				ResultSet rs = pst.executeQuery();
				if (isResultSetEmpty(rs) == true ){    
					json = converter.toJSONArray(rs);
				}
				pst.close();
			}catch(SQLException SQLex){
				SQLex.printStackTrace();
			}catch(Exception ex){
				ex.printStackTrace();
			}finally{
				try{rs.close();} catch(Exception ex) { /*ignore*/}
				try{st.close();} catch(Exception ex) { /*ignore*/}
				try{pst.close();} catch(Exception ex) { /*ignore*/}
				try{con.close();} catch(Exception ex) { /*ignore*/}
			}
			return json;
		}
	//returns jsonArray filtered by delta 1 number
	public JSONArray queryDeltaPartNumber(String queryValue) throws Exception{
				
				ToJSON converter = new ToJSON();
				JSONArray json = new JSONArray();
				
				try{
					con = getDBConnection();
					pst = con.prepareStatement("SELECT * FROM `delta 1 parts` WHERE DeltaPartNumber = ? ORDER BY `DeltaPartNumber`");
					pst.setString(1, queryValue);
					ResultSet rs = pst.executeQuery();
					if (isResultSetEmpty(rs) == true ){    
						json = converter.toJSONArray(rs);
					}
					pst.close();
				}catch(SQLException SQLex){
					SQLex.printStackTrace();
				}catch(Exception ex){
					ex.printStackTrace();
				}finally{
					try{rs.close();} catch(Exception ex) { /*ignore*/}
					try{st.close();} catch(Exception ex) { /*ignore*/}
					try{pst.close();} catch(Exception ex) { /*ignore*/}
					try{con.close();} catch(Exception ex) { /*ignore*/}
				}
				return json;
			}
	//returns jsonArray filtered by CustomerPartNumber
	public JSONArray queryCustomerPartNumber(String queryValue) throws Exception{
			
			ToJSON converter = new ToJSON();
			JSONArray json = new JSONArray();
			
			try{
				con = getDBConnection();
				pst = con.prepareStatement("SELECT * FROM `parts list` WHERE CustPartNumber = ? ORDER BY `BosalPartNumber`");
				pst.setString(1, queryValue);
				ResultSet rs = pst.executeQuery();
				
				if (isResultSetEmpty(rs) == true ){    
					json = converter.toJSONArray(rs);
				}
				pst.close();
			}catch(SQLException SQLex){
				SQLex.printStackTrace();
			}catch(Exception ex){
				ex.printStackTrace();
			}finally{
				try{rs.close();} catch(Exception ex) { /*ignore*/}
				try{st.close();} catch(Exception ex) { /*ignore*/}
				try{pst.close();} catch(Exception ex) { /*ignore*/}
				try{con.close();} catch(Exception ex) { /*ignore*/}
			}
			return json;
		}
	//returns jsonArray filtered bySupplierPartNumber
	public JSONArray querySupplierPartNumber(String queryValue) throws Exception{
			
			ToJSON converter = new ToJSON();
			JSONArray json = new JSONArray();
			
			try{
				con = getDBConnection();
				pst = con.prepareStatement("SELECT * FROM `parts list` WHERE SupPartNumber = ? ORDER BY `BosalPartNumber`");
				pst.setString(1, queryValue);
				ResultSet rs = pst.executeQuery();
				
				if (isResultSetEmpty(rs) == true ){    
					json = converter.toJSONArray(rs);
				}
				pst.close();
			}catch(SQLException SQLex){
				SQLex.printStackTrace();
			}catch(Exception ex){
				ex.printStackTrace();
			}finally{
				try{rs.close();} catch(Exception ex) { /*ignore*/}
				try{st.close();} catch(Exception ex) { /*ignore*/}
				try{pst.close();} catch(Exception ex) { /*ignore*/}
				try{con.close();} catch(Exception ex) { /*ignore*/}
			}
			return json;
		}
	//returns jsonArray filtered by Program
	public JSONArray queryProgram(String queryValue) throws Exception{
			
			ToJSON converter = new ToJSON();
			JSONArray json = new JSONArray();
			try{
				con = getDBConnection();
				pst = con.prepareStatement("SELECT * FROM `parts list` WHERE Program = ? ORDER BY `BosalPartNumber`");
				pst.setString(1, queryValue);
				ResultSet rs = pst.executeQuery();
				if (isResultSetEmpty(rs) == true ){    
					json = converter.toJSONArray(rs);
				}

				//sort.BosalLowToHigh(json);
				pst.close();
			}catch(SQLException SQLex){
				SQLex.printStackTrace();
			}catch(Exception ex){
				ex.printStackTrace();
			}finally{
				try{rs.close();} catch(Exception ex) { /*ignore*/}
				try{st.close();} catch(Exception ex) { /*ignore*/}
				try{pst.close();} catch(Exception ex) { /*ignore*/}
				try{con.close();} catch(Exception ex) { /*ignore*/}
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
			
			if (isResultSetEmpty(rs) == true ){    
				json = converter.toJSONArray(rs);
			}
			pst.close();
		}catch(SQLException SQLex){
			SQLex.printStackTrace();
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			try{rs.close();} catch(Exception ex) { /*ignore*/}
			try{st.close();} catch(Exception ex) { /*ignore*/}
			try{pst.close();} catch(Exception ex) { /*ignore*/}
			try{con.close();} catch(Exception ex) { /*ignore*/}
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
			
			if (isResultSetEmpty(rs) == true){    
				json = converter.toJSONArray(rs);
			}
			pst.close();
		}catch(SQLException SQLex){
			SQLex.printStackTrace();
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			try{rs.close();} catch(Exception ex) { /*ignore*/}
			try{st.close();} catch(Exception ex) { /*ignore*/}
			try{pst.close();} catch(Exception ex) { /*ignore*/}
			try{con.close();} catch(Exception ex) { /*ignore*/}
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
			
			if (isResultSetEmpty(rs) == true ){    
				json = converter.toJSONArray(rs);
			}
			pst.close();
		}catch(SQLException SQLex){
			SQLex.printStackTrace();
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			try{rs.close();} catch(Exception ex) { /*ignore*/}
			try{st.close();} catch(Exception ex) { /*ignore*/}
			try{pst.close();} catch(Exception ex) { /*ignore*/}
			try{con.close();} catch(Exception ex) { /*ignore*/}
		}
		return json;
	}
	//
	public JSONArray queryReturnAllDescrips() throws Exception{
		
		ToJSON converter = new ToJSON();
		JSONArray json = new JSONArray();
		
		try{
			con = getDBConnection();
			pst = con.prepareStatement("SELECT * from `description list` ORDER BY `Name` ASC");
			
			ResultSet rs = pst.executeQuery();
			
			if (isResultSetEmpty(rs) == true ){    
				json = converter.toJSONArray(rs);
			}
			pst.close();
		}catch(SQLException SQLex){
			SQLex.printStackTrace();
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			try{rs.close();} catch(Exception ex) { /*ignore*/}
			try{st.close();} catch(Exception ex) { /*ignore*/}
			try{pst.close();} catch(Exception ex) { /*ignore*/}
			try{con.close();} catch(Exception ex) { /*ignore*/}
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
				
				if (isResultSetEmpty(rs) == true ){    
					json = converter.toJSONArray(rs);
				}
				pst.close();
			}catch(SQLException SQLex){
				SQLex.printStackTrace();
			}catch(Exception ex){
				ex.printStackTrace();
			}finally{
				try{rs.close();} catch(Exception ex) { /*ignore*/}
				try{st.close();} catch(Exception ex) { /*ignore*/}
				try{pst.close();} catch(Exception ex) { /*ignore*/}
				try{con.close();} catch(Exception ex) { /*ignore*/}
			}
			return json;
		}
	//returns jsonArray filtered by Part Types (rename)
	public JSONArray queryPartType(int queryValue) throws Exception{
		
		ToJSON converter = new ToJSON();
		JSONArray json = new JSONArray();
		
		try{
			con = getDBConnection();
			pst = con.prepareStatement("SELECT * from `type file` WHERE PartType = ?");
			pst.setInt(1, queryValue);
			ResultSet rs = pst.executeQuery();
			
			if (isResultSetEmpty(rs) == true ){    
				json = converter.toJSONArray(rs);
			}
			pst.close();
		}catch(SQLException SQLex){
			SQLex.printStackTrace();
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			try{rs.close();} catch(Exception ex) { /*ignore*/}
			try{st.close();} catch(Exception ex) { /*ignore*/}
			try{pst.close();} catch(Exception ex) { /*ignore*/}
			try{con.close();} catch(Exception ex) { /*ignore*/}
		}
		return json;
	}
	//returns Current Type Sequence Number (done)
	public int queryCurrentTypeSequenceNumber(int queryValue) throws Exception{
		int seq = 0;		
		try{
			con = getDBConnection();
			pst = con.prepareStatement("SELECT * FROM `type file` WHERE PartType = ?");
			pst.setInt(1, queryValue);
			ResultSet rs = pst.executeQuery();
			
			while(rs.next()){
				seq = rs.getInt("SeqNumber");
			}
			pst.close();
		}catch(SQLException SQLex){
			SQLex.printStackTrace();
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			try{rs.close();} catch(Exception ex) { /*ignore*/}
			try{st.close();} catch(Exception ex) { /*ignore*/}
			try{pst.close();} catch(Exception ex) { /*ignore*/}
			try{con.close();} catch(Exception ex) { /*ignore*/}
		}
		return seq;
	}
	//iterates onto the next integer for Sequence based on PartType passed in
	public void iterateNextSequenceNumber(int partType) throws Exception{	
		try{
			int curSeqNum = queryCurrentTypeSequenceNumber(partType);
			int newSeqNum = curSeqNum + 1;
			con = getDBConnection();
			pst = con.prepareStatement("UPDATE `type file` SET `SeqNumber` = ? WHERE PartType = ?");
			pst.setInt(1, newSeqNum);
			pst.setInt(2, partType);
			pst.executeUpdate();
			pst.close();
		}catch(SQLException SQLex){
			SQLex.printStackTrace();
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			try{rs.close();} catch(Exception ex) { /*ignore*/}
			try{st.close();} catch(Exception ex) { /*ignore*/}
			try{pst.close();} catch(Exception ex) { /*ignore*/}
			try{con.close();} catch(Exception ex) { /*ignore*/}
		}
	}
	//inserts a new row into `parts list` to create a new part
	public void insertNewPart(int partType, int mat, int seq, String typeDescription, String Description,
								String BosalPartNumber, String CustomerPartNumber, String SupplierPartNumber,
								String CreatedBy, String Program, Timestamp Created, Timestamp Updated, String UpdatedBy) throws Exception{	
		try{
			con = getDBConnection();
			pst = con.prepareStatement("INSERT INTO `parts list` (PartType, Material, BosalPartNumber, CustPartNumber,"
										+ " SupPartNumber, PartDescription, Program, SeqNumber, TypeDescription, CreatedBy, "
										+ "Created, UpdatedBy, Updated ) "
										+ "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
			pst.setInt(1, partType);
			pst.setInt(2, mat);
			pst.setString(3, BosalPartNumber);
			pst.setString(4, CustomerPartNumber);
			pst.setString(5, SupplierPartNumber);
			pst.setString(6, Description);
			pst.setString(7, Program);
			pst.setInt(8, seq);
			pst.setString(9, typeDescription);
			pst.setString(10, CreatedBy);
			pst.setTimestamp(11, Created);
			pst.setString(12, UpdatedBy);
			pst.setTimestamp(13, Updated);
			pst.executeUpdate();
			pst.close();
			iterateNextSequenceNumber(partType);
		}catch(SQLException SQLex){
			SQLex.printStackTrace();
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			try{rs.close();} catch(Exception ex) { /*ignore*/}
			try{st.close();} catch(Exception ex) { /*ignore*/}
			try{pst.close();} catch(Exception ex) { /*ignore*/}
			try{con.close();} catch(Exception ex) { /*ignore*/}
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
				}catch(SQLException SQLex){
					SQLex.printStackTrace();
				}catch(Exception ex){
					ex.printStackTrace();
				}finally{
					try{rs.close();} catch(Exception ex) { /*ignore*/}
					try{st.close();} catch(Exception ex) { /*ignore*/}
					try{pst.close();} catch(Exception ex) { /*ignore*/}
					try{con.close();} catch(Exception ex) { /*ignore*/}
				}
			}else{
				System.out.println(appUser+" does not have permission to do that!");
			}
		}
	//updates Part Information
	public void update(String bPartNum, String updatedCusPartNumber, String updatedSupPartNumber, String updatedPartDescription, String program) throws Exception{
			
			try{
				con = getDBConnection();
				pst = con.prepareStatement("UPDATE `parts list` SET `PartDescription` = ?, " 
						+ " `CustPartNumber` = ?, " 
						+ " `SupPartNumber` = ?, "
						+ " `Program` = ?, "
						+ " `UpdatedBy` = ?, "
						+ " `Updated` = ?"
						+ "WHERE `BosalPartNUmber`= ?");
				pst.setString(1, updatedPartDescription);
				pst.setString(2, updatedCusPartNumber);
				pst.setString(3, updatedSupPartNumber);
				pst.setString(4,  program);
				pst.setString(5, getUser());
				pst.setTimestamp(6, getTimestamp());
				pst.setString(7, bPartNum);
				pst.executeUpdate();
				System.out.println("update successful!");
				pst.close();
			}catch(SQLException SQLex){
				SQLex.printStackTrace();
			}catch(Exception ex){
				ex.printStackTrace();
			}finally{
				try{rs.close();} catch(Exception ex) { /*ignore*/}
				try{st.close();} catch(Exception ex) { /*ignore*/}
				try{pst.close();} catch(Exception ex) { /*ignore*/}
				try{con.close();} catch(Exception ex) { /*ignore*/}
			}
		}
}

package binaparts.util;

import binaparts.dao.*;

public class UpdateData {
	
	static DBConnect con = new DBConnect();
	
	public static void main(String[] args) {
		
		try {
			//con.insertInto("binapartslist", "binapartslist", "delta 1 parts", "delta 1 parts");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}

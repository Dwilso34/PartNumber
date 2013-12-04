package binaparts.util;

import binaparts.dao.*;

public class UpdateData {
	
	static DBConnect con = new DBConnect();
	
	public static void main(String[] args) {
		
		try {
			con.updateDeltaProgram();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}

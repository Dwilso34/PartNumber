package binaparts.util;

import org.json.JSONArray;
import org.json.JSONObject;
import java.sql.ResultSet;

public class ToJSON {

	public JSONArray toJSONArray(ResultSet rs) throws Exception{
		
		JSONArray json = new JSONArray();
		
		try{
			
			java.sql.ResultSetMetaData rsmd = rs.getMetaData();
			while(rs.next()){
				
				int numColumns = rsmd.getColumnCount();
				
				JSONObject obj = new JSONObject();
				
				for (int i=1; i<numColumns+1; i++){
					
					String column_name = rsmd.getColumnName(i);
					
					if(rsmd.getColumnType(i)==java.sql.Types.ARRAY){
						obj.put(column_name,  rs.getArray(column_name));
						///*Debug*/ System.out.println("ToJson: ARRAY");
					}
					else if(rsmd.getColumnType(i)==java.sql.Types.BIGINT){
						obj.put(column_name,  rs.getInt(column_name));
						//*Debug*/ System.out.println("ToJson: BIGINT");
					}
					else if(rsmd.getColumnType(i)==java.sql.Types.BOOLEAN){
						obj.put(column_name,  rs.getBoolean(column_name));
						//*Debug*/ System.out.println("ToJson: BOOLEAN");
					}
					else if(rsmd.getColumnType(i)==java.sql.Types.BLOB){
						obj.put(column_name,  rs.getBlob(column_name));
						//*Debug*/ System.out.println("ToJson: BLOB");
					}
					else if(rsmd.getColumnType(i)==java.sql.Types.DOUBLE){
						obj.put(column_name,  rs.getDouble(column_name));
						//*Debug*/ System.out.println("ToJson: DOUBLE");
					}
					else if(rsmd.getColumnType(i)==java.sql.Types.FLOAT){
						obj.put(column_name,  rs.getFloat(column_name));
						//*Debug*/ System.out.println("ToJson: FLOAT");
					}
					else if(rsmd.getColumnType(i)==java.sql.Types.INTEGER){
						obj.put(column_name,  rs.getInt(column_name));
						//*Debug*/ System.out.println("ToJson: INTEGER");
					}
					else if(rsmd.getColumnType(i)==java.sql.Types.NVARCHAR){
						obj.put(column_name,  rs.getNString(column_name));
						//*Debug*/ System.out.println("ToJson: NVARCHAR");
					}
					else if(rsmd.getColumnType(i)==java.sql.Types.VARCHAR){
						obj.put(column_name,  rs.getString(column_name));
						//*Debug*/ System.out.println("ToJson: VARCHAR");
					}
					else if(rsmd.getColumnType(i)==java.sql.Types.TINYINT){
						obj.put(column_name,  rs.getInt(column_name));
						//*Debug*/ System.out.println("ToJson: TINYINT");
					}
					else if(rsmd.getColumnType(i)==java.sql.Types.SMALLINT){
						obj.put(column_name,  rs.getInt(column_name));
						//*Debug*/ System.out.println("ToJson: SMALLINT");
					}
					else if(rsmd.getColumnType(i)==java.sql.Types.DATE){
						obj.put(column_name,  rs.getDate(column_name));
						//*Debug*/ System.out.println("ToJson: DATE");
					}
					else if(rsmd.getColumnType(i)==java.sql.Types.TIMESTAMP){
						obj.put(column_name,  rs.getTimestamp(column_name));
						//*Debug*/ System.out.println("ToJson: TIMESTAMP");
					}
					else if(rsmd.getColumnType(i)==java.sql.Types.NUMERIC){
						obj.put(column_name,  rs.getBigDecimal(column_name));
						//*Debug*/ System.out.println("ToJson: NUMERIC");
					}
					else if(rsmd.getColumnType(i)==java.sql.Types.NULL){
						obj.put(column_name, rs.getObject(column_name));
						System.out.println("ToJson: NULL");
						System.out.println(obj);
					}
					else {
						obj.put(column_name,  rs.getObject(column_name));
						//*Debug*/ System.out.println("ToJson: Object "+column_name);
					}
				}
				json.put(obj);
			}
		} catch (Exception ex){
			ex.printStackTrace();
		}
		
		return json;
	}
}
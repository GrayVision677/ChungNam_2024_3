package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;

public class DbManager {

	private String url = "jdbc:mysql://localhost/?"
	         	+ "CharacterEncoding=UTF-8&"
	            + "serverTimezone=UTC&"
	            + "allowPublicKeyRetrieval=true&"
	            + "allowLoadLocalInfile=true&"
	            + "allowMultiQueries=true";;
	private String id = "root";
	private String pw = "1234";
	private Connection con;
	private PreparedStatement pstmt;

	public DbManager() {
		// TODO Auto-generated constructor stub
		try {
			con = DriverManager.getConnection(url,id,pw);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public int dbSet(String sql, Object...val) {
		try {
			pstmt = con.prepareStatement(sql);
			for (int i = 0; i < val.length; i++) {
				pstmt.setObject(i+1, val[i]);
			}
			return pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}
	
	public Vector<Vector<String>> getDb(String sql,Object...val){
		Vector<Vector<String>> data = new Vector<Vector<String>>();
		try {
			pstmt = con.prepareStatement(sql);
			for (int i = 0; i < val.length; i++) {
				pstmt.setObject(i+1, val[i]);
			}
			
			ResultSet rs = pstmt.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			while(rs.next()) {
				Vector<String> row = new Vector<>();
				for (int i = 0; i < rsmd.getColumnCount(); i++) {
					row.add(rs.getObject(i+1) + "");
				}
				
				data.add(row);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;
	}
}

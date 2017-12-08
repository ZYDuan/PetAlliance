package socket;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;


import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;




public class DBUtil {
	public static final String Table_Chat="chat_text";
	public static final String Table_Trans="transaction";
	public static final	String Table_PetInfo="pet_info";
	public static final String Table_PetHome="pet_home";
	public static final String TABLE_TOPIC = "table_topic"; 
	public static final String TABLE_COMMENT = "table_comment"; 
	public static final String Table_UserInfo="user_info";
	public static final String TABLE_EVALUATION="evaluation";
	public static final String TABLE_FEEDBACK="feedback";
	
	private static Connection connect;
	private static Connection getConnection(){
		if(connect == null){
			String url = "jdbc:mysql://localhost:3306/chat";
			try{
				Class.forName("com.mysql.jdbc.Driver");
				connect = (Connection)DriverManager.getConnection(url,"root","zyd2288778.");
				System.out.println("创建数据库连接");
			}catch(ClassNotFoundException e){
				e.printStackTrace();
			}catch(SQLException e){
				System.out.println("SQLException: " + e.getMessage());
				System.out.println("SQLState: " + e.getSQLState());
				System.out.println("VendorError: " + e.getErrorCode());
			}
		}
		return connect;
	}
	
	public static ResultSet query(String querySql)throws SQLException{
		Statement statement=(Statement) getConnection().createStatement();
		return statement.executeQuery(querySql);
	}
	
	public static int update(String insertSql)throws SQLException {
		Statement stateMent = (Statement) getConnection().createStatement();
		return stateMent.executeUpdate(insertSql);
	}
	
	public static void closeConnection() {
		if (connect != null) {
			try {
				connect.close();
				connect = null;
			} catch (SQLException e) {
				System.out.println("数据库关闭异常：[" + e.getErrorCode() + "]" + e.getMessage());
			}
		}
	}
}
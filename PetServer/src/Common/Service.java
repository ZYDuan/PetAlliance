package Common;


import java.sql.ResultSet;
import java.sql.SQLException;


public class Service {

	public Boolean login(String username, String password) {

		// ��ȡSql��ѯ���
		String logSql = "select * from user_info where (name ='" + username
				+"' or phone ='"+username+"' or email ='"+username
				+ "') and password ='" + password +"'";

		// ��ȡDB����
		DBManager sql = DBManager.createInstance();
		sql.connectDB();

		// ����DB����
		try {
			ResultSet rs = sql.executeQuery(logSql);
			if (rs.next()) {
				sql.closeDB();
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		sql.closeDB();
		return false;
	}

	public Boolean register(String username, String password,String address,
			String phone, String email) {

		// ��ȡSql��ѯ���
		String regSql = "insert into user_info (name,password,address,phone,email) values('"
				+ username + "','" + password + "','"+address+"','"+phone+"','"+email+"') ";
		
		
		// ��ȡDB����
		DBManager sql = DBManager.createInstance();
		sql.connectDB();
		
		int ret = sql.executeUpdate(regSql);
//		System.out.println("test"+ ret);
		if (ret != 0) {
			sql.closeDB();
			return true;
		}
		sql.closeDB();

		return false;
	}
}



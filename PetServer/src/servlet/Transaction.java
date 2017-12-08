package servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import socket.DBUtil;
import Common.CommonResponse;

/**
 * Servlet implementation class Transaction
 */
@WebServlet("/Transaction")
public class Transaction extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Transaction() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		BufferedReader read = request.getReader();
		StringBuilder sb = new StringBuilder();
		String line = null;
		while ((line = read.readLine()) != null) {
			sb.append(line);
		}
		String req = new String(sb.toString().getBytes("ISO8859-1"), "UTF-8");
		System.out.println(req);
		
		JSONObject object = JSONObject.fromObject(req);
		String requestCode = object.getString("requestCode");
		JSONObject requestParam = object.getJSONObject("requestParam");
		
		String user_id=requestParam.getString("user_id");
		String start_time=requestParam.getString("start_time");
		String end_time=requestParam.getString("end_time");
		String user_name=requestParam.getString("user_name");
		String user_phone=requestParam.getString("user_phone");
		String pet_type=requestParam.getString("pet_type");
		String to_user_id=requestParam.getString("to_user_id");
		String notes=requestParam.getString("notes");
		
		String sql = String.format("INSERT INTO %s (USER_ID, START_TIME,END_TIME,USER_NAME,USER_PHONE,PET_TYPE,NOTES,TO_USER_ID ) VALUES " +
				"(%s,'%s','%s','%s',%s,'%s','%s',%s)", 
				DBUtil.Table_Trans,user_id,start_time,end_time,user_name,user_phone,pet_type,notes,to_user_id);
		
		System.out.println(sql);

		try {
			DBUtil.update(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		CommonResponse res = new CommonResponse();
		res.setResCode("0"); 
		request.setCharacterEncoding("utf-8");  
		 response.setContentType("text/html;charset=utf-8");
		String resStr = JSONObject.fromObject(res).toString();
		response.getWriter().append(resStr).flush();
	}
	

}

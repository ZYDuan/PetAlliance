package servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import Common.CommonResponse;

import socket.DBUtil;

/**
 * Servlet implementation class Servlet
 */
@WebServlet("/ChatListServlet")
public class ChatListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChatListServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
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
		String req = sb.toString();
		System.out.println(req);
		
		JSONObject object = JSONObject.fromObject(req);
		String requestCode = object.getString("requestCode");
		JSONObject requestParam = object.getJSONObject("requestParam");
		
		String sql = String.format("SELECT CHAT_CONTENT,MAX(CHAT_TIME) AS CHAT_TIMES,USER_ID FROM %s  WHERE TO_USER_ID = %s GROUP BY USER_ID", 
				DBUtil.Table_Chat,requestParam.getString("user_id"));
		System.out.println(sql);

		// 自定义的结果信息类
		CommonResponse res = new CommonResponse();
		try {
			ResultSet result = DBUtil.query(sql); // 数据库查询操作
			while (result.next()) {
				HashMap<String, String> map = new HashMap<>();
				map.put("chat_content", result.getString("chat_content"));
				
				String sqls=String.format("SELECT NAME FROM USER_INFO  WHERE USER_ID = %s ", 
						result.getString("user_id"));
				ResultSet results = DBUtil.query(sqls);
				while (results.next()) {
				map.put("user_name",results.getString("name"));}
				map.put("chat_time", result.getString("chat_times"));
				map.put("user_id",result.getString("user_id"));
				res.addListItem(map);
			}
			res.setResCode("0"); // 表示业务结果正确
		} catch (SQLException e) {
			res.setResult("300", "数据库查询错误");
			e.printStackTrace();
		}
		 request.setCharacterEncoding("utf-8");  
		 response.setContentType("text/html;charset=utf-8");
		String resStr = JSONObject.fromObject(res).toString();
		response.getWriter().append(resStr).flush();
	
	}

}

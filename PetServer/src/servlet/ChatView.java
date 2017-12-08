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
 * Servlet implementation class ChatView
 */
@WebServlet("/ChatView")
public class ChatView extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChatView() {
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
		String req = sb.toString();
		System.out.println(req);
		
		JSONObject object = JSONObject.fromObject(req);
		String requestCode = object.getString("requestCode");
		JSONObject requestParam = object.getJSONObject("requestParam");
		
		String user_id=requestParam.getString("user_id");
		String to_user_id=requestParam.getString("to_user_id");
		
		String sql = String.format("SELECT CHAT_CONTENT,CHAT_TIME,USER_ID,TO_USER_ID FROM %s  WHERE ((TO_USER_ID= %s && USER_ID= %s) || (TO_USER_ID=%s &&USER_ID= %s))", 
				DBUtil.Table_Chat,user_id,to_user_id,to_user_id,user_id);
		
		System.out.println(sql);

		// 自定义的结果信息类
		CommonResponse res = new CommonResponse();
		try {
			ResultSet result = DBUtil.query(sql); // 数据库查询操作
			while (result.next()) {
				HashMap<String, String> map = new HashMap<>();
				map.put("chat_content", result.getString("chat_content"));
				map.put("user_id", result.getString("user_id"));
				map.put("chat_time", result.getString("chat_time"));
				map.put("to_user_id", result.getString("to_user_id"));
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

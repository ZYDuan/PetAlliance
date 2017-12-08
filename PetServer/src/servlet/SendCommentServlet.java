package servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Common.CommonResponse;
import net.sf.json.JSONObject;
import socket.DBUtil;

/**
 * Servlet implementation class SendCommentServlet
 */
@WebServlet("/SendCommentServlet")
public class SendCommentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SendCommentServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		// TODO Auto-generated method stub
				String topic_id = request.getParameter("topic_id");  
		        String comment_content = request.getParameter("comment_content"); 
		        String user_id = request.getParameter("user_id"); 
		        String sqls = String.format("SELECT NAME FROM %s WHERE USER_ID = %s", 
						DBUtil.Table_UserInfo,user_id);
		        String user_name="";
				ResultSet results;
				try {
					results = DBUtil.query(sqls);
					while(results.next()){
						user_name=results.getString("name");
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
		        System.out.println(comment_content);
		        String sql = String.format("INSERT INTO %s (TOPIC_ID,COMMENT_CONTENT,USER_ID,USER_NAME) VALUES (%s,'%s',%s,'%s')", 
						DBUtil.TABLE_COMMENT,topic_id,comment_content,
						user_id,user_name);
				System.out.println(sql);
		        
		        CommonResponse res = new CommonResponse();
				try {
					int result = DBUtil.update(sql); // 数据库查询操作
					if(result>0)
					res.setResCode("0");// 这个不能忘了，表示业务结果正确
					else{
						res.setResult("300", "插入失败");
					}
				} catch (SQLException e) {
					res.setResult("300", "数据库查询错误");
					e.printStackTrace();
				}
				request.setCharacterEncoding("utf-8");
				 response.setContentType("text/html;charset=utf-8");
				String resStr = JSONObject.fromObject(res).toString();
				response.getWriter().append(resStr).flush();
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
				
				// 第一步：获取 客户端 发来的请求，恢复其Json格式——>需要客户端发请求时也封装成Json格式
						JSONObject object = JSONObject.fromObject(req);
						// requestCode暂时用不上
						// 注意下边用到的2个字段名称requestCode、requestParam要和客户端CommonRequest封装时候的名字一致
						String requestCode = object.getString("requestCode");
						JSONObject requestParam = object.getJSONObject("requestParam");
						
						String sqls = String.format("SELECT NAME FROM %s WHERE USER_ID = %s", 
								DBUtil.Table_UserInfo,requestParam.getString("user_id"));
				        String user_name="";
						ResultSet results;
						try {
							results = DBUtil.query(sqls);
							while(results.next()){
								user_name=results.getString("name");
							}
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
						// 第二步：将Json转化为别的数据结构方便使用或者直接使用（此处直接使用），进行业务处理，生成结果
						// 拼接SQL查询语句
						String sql = String.format("INSERT INTO %s (topic_id,comment_content,user_id,user_name) VALUES (%s,'%s',%s,'%s')", 
								DBUtil.TABLE_COMMENT,requestParam.getString("topic_id"),requestParam.getString("comment_content"),
								requestParam.getString("user_id"),user_name);
						System.out.println(sql);
						 
						// 自定义的结果信息类
						CommonResponse res = new CommonResponse();
						try {
							int result = DBUtil.update(sql); // 数据库查询操作
							if(result>0)
							res.setResCode("0");// 这个不能忘了，表示业务结果正确
							else{
								res.setResult("300", "插入失败");
							}
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

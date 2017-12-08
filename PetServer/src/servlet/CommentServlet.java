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

import Common.CommonResponse;
import net.sf.json.JSONObject;
import socket.DBUtil;

/**
 * Servlet implementation class CommentServlet
 */
@WebServlet("/CommentServlet")
public class CommentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CommentServlet() {
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
		request.setCharacterEncoding("utf-8");  
		BufferedReader read = request.getReader();
		StringBuilder sb = new StringBuilder();
		String line = null;
		while ((line = read.readLine()) != null) {
			sb.append(line);
		}
		String req = sb.toString();
		System.out.println(req);
		
		// ��һ������ȡ �ͻ��� ���������󣬻ָ���Json��ʽ����>��Ҫ�ͻ��˷�����ʱҲ��װ��Json��ʽ
				JSONObject object = JSONObject.fromObject(req);
				// requestCode��ʱ�ò���
				// ע���±��õ���2���ֶ�����requestCode��requestParamҪ�Ϳͻ���CommonRequest��װʱ�������һ��
				String requestCode = object.getString("requestCode");
				JSONObject requestParam = object.getJSONObject("requestParam");
				

				// �ڶ�������Jsonת��Ϊ������ݽṹ����ʹ�û���ֱ��ʹ�ã��˴�ֱ��ʹ�ã�������ҵ�������ɽ��
				// ƴ��SQL��ѯ���
				String sql = String.format("SELECT * FROM %s WHERE topic_id=%s", 
						DBUtil.TABLE_COMMENT,requestParam.getString("topic_id"));
				System.out.println(sql);

		// �Զ���Ľ����Ϣ��
		CommonResponse res = new CommonResponse();
		try {
			ResultSet result = DBUtil.query(sql); // ���ݿ��ѯ����
			while (result.next()) {
				HashMap<String, String> map = new HashMap<>();
				map.put("user_name", result.getString("user_name"));
				map.put("comment_content", result.getString("comment_content"));
				map.put("comment_time", result.getString("comment_time"));
				res.addListItem(map);
			}
			res.setResCode("0"); // ����������ˣ���ʾҵ������ȷ
		} catch (SQLException e) {
			res.setResult("300", "���ݿ��ѯ����");
			e.printStackTrace();
		}
		 
		 response.setContentType("text/html;charset=utf-8");
		String resStr = JSONObject.fromObject(res).toString();
		response.getWriter().append(resStr).flush();
	}

}

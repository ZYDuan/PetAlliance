package servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Common.CommonResponse;
import net.sf.json.JSONObject;
import socket.DBUtil;

/**
 * Servlet implementation class ExpressTopicServlet
 */
@WebServlet("/ExpressTopicServlet")
public class ExpressTopicServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ExpressTopicServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String topic_name = request.getParameter("topic_name");  
        String topic_content = request.getParameter("topic_content"); 
        String user_id = request.getParameter("user_id"); 
      
        String sql = String.format("INSERT INTO %s (topic_name,topic_content,user_id) VALUES ('%s','%s',%s)", 
				DBUtil.TABLE_TOPIC,topic_name,topic_content,
				user_id);
		System.out.println(sql);
        
        CommonResponse res = new CommonResponse();
		try {
			int result = DBUtil.update(sql); // ���ݿ��ѯ����
			if(result>0)
			res.setResCode("0");// ����������ˣ���ʾҵ������ȷ
			else{
				res.setResult("300", "����ʧ��");
			}
		} catch (SQLException e) {
			res.setResult("300", "���ݿ��ѯ����");
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
		
		// ��һ������ȡ �ͻ��� ���������󣬻ָ���Json��ʽ����>��Ҫ�ͻ��˷�����ʱҲ��װ��Json��ʽ
				JSONObject object = JSONObject.fromObject(req);
				// requestCode��ʱ�ò���
				// ע���±��õ���2���ֶ�����requestCode��requestParamҪ�Ϳͻ���CommonRequest��װʱ�������һ��
				String requestCode = object.getString("requestCode");
				JSONObject requestParam = object.getJSONObject("requestParam");
				

				// �ڶ�������Jsonת��Ϊ������ݽṹ����ʹ�û���ֱ��ʹ�ã��˴�ֱ��ʹ�ã�������ҵ�������ɽ��
				// ƴ��SQL��ѯ���
				String sql = String.format("INSERT INTO %s (topic_name,topic_content,user_id) VALUES ('%s','%s',%s)", 
						DBUtil.TABLE_TOPIC,requestParam.getString("topic_name"),requestParam.getString("topic_content"),
						requestParam.getString("user_id"));
				System.out.println(sql);
				// �Զ���Ľ����Ϣ��
				CommonResponse res = new CommonResponse();
				try {
					int result = DBUtil.update(sql); // ���ݿ��ѯ����
					if(result>0)
					res.setResCode("0");// ����������ˣ���ʾҵ������ȷ
					else{
						res.setResult("300", "����ʧ��");
					}
				} catch (SQLException e) {
					res.setResult("300", "���ݿ��ѯ����");
					e.printStackTrace();
				}
				 request.setCharacterEncoding("utf-8");  
				 response.setContentType("text/html;charset=utf-8");
				String resStr = JSONObject.fromObject(res).toString();
				response.getWriter().append(resStr).flush();
               }
	

}

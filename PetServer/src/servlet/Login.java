package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import socket.DBUtil;

import Common.Service;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String username = request.getParameter("username");
		username = new String(username.getBytes("ISO-8859-1"), "UTF-8");
		String password = request.getParameter("password");
		String confirm;
		System.out.println(username + "--" + password);

		// 新建服务对象
		Service serv = new Service();
		String user_id = null;
		// 验证处理
		boolean loged = serv.login(username, password);
		if (loged) {
			System.out.print("Succss");
			confirm = "\n登陆成功";
			request.getSession().setAttribute("username", username);
			String sql=String.format("SELECT USER_ID FROM USER_INFO WHERE NAME= '%s'",username);
			ResultSet rs;
			try {
				rs = DBUtil.query(sql);
				while (rs.next()) {
					user_id=rs.getString("user_id");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 
			
			
			// response.sendRedirect("welcome.jsp");
		} else {
			System.out.print("Failed");
			confirm = "\n账号或密码不正确";
		}
		 // 返回信息
		 response.setCharacterEncoding("UTF-8");
		 response.setContentType("text/html");
		 PrintWriter out = response.getWriter();
		 out.print(user_id);
		 out.flush();
		 out.close();

	}

	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}

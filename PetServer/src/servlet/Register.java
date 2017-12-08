package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Common.Service;

/**
 * Servlet implementation class Register
 */
@WebServlet("/Register")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Register() {
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
		Service serv = new Service();
		request.setCharacterEncoding("utf-8");
		
		System.out.println("hello");
		// ����ע����Ϣ
//		String username = request.getParameter("r_name");
		String username = request.getParameter("username");
		//username = new String(username.getBytes("ISO-8859-1"), "UTF-8");
		String password = request.getParameter("password");
		String confirm;
//		String password = request.getParameter("r_password");
		String address = request.getParameter("address");
		address =URLDecoder.decode(address, "utf-8");
		String phone = request.getParameter("phone");
		String email = request.getParameter("email");
		System.out.println(username+password+address+phone+email);
		
		// ��֤����
		boolean reged = serv.register(username,password,address,phone,email);
		System.out.println("test" + reged);
		if (reged) {
			System.out.print("Succss");
			confirm="\nע��ɹ�";
			request.getSession().setAttribute("username", username);
			//response.sendRedirect("welcome.jsp");
		}else {
			System.out.print("Failed");
			confirm="\nע��ʧ�ܣ��ƺ�����ע��";
		}


		// ������Ϣ
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.print("�û�����" + username);
		out.print("���룺" + password);
		out.print(confirm);
		out.flush();
		out.close();
		
	}
	

}

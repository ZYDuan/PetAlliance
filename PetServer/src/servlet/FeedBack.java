package servlet;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import socket.DBUtil;

import com.mysql.jdbc.Connection;

/**
 * Servlet implementation class FeedBack
 */
@WebServlet("/FeedBack")
public class FeedBack extends HttpServlet{
	private static final long serialVersionUID=1L;
	
	public FeedBack(){}
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException{
		String method=request.getMethod();
		
		if("GET".equals(method)){
			System.out.println("the method you request is get");
			doGet(request, response);
		}else if("POST".equals(method)){
			System.out.println("the method you request is post");
			doPost(request, response);
		}else{
			System.out.println("请求方法失败");
		}
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException{
		String code="";
		String message="";
		
		String nId=request.getParameter("nId");
		String appearance=request.getParameter("appearance");
		String function=request.getParameter("function");
		String payment=request.getParameter("payment");
		String longfeedback=request.getParameter("longfeedback");
		String user_id=request.getParameter("user_id");
		try{
			ResultSet result;
			
			String sql="select * from "+DBUtil.TABLE_FEEDBACK+" where NumberId= "+nId;
			result=DBUtil.query(sql);
			if(result.next()){
				code="100";
				message="不能重复反馈";
			}else{
				String sqlinsert="insert into "+DBUtil.TABLE_FEEDBACK+"(NumberId, Appearance, Function, Payment, LongFeedback,user_id) values ('"
						+nId+"','"+appearance+"','"+function+"','"+payment+"','"+longfeedback+"','"+user_id+"')";
				if((DBUtil.update(sqlinsert))>0){
					code="200";
					message="反馈成功";
				}else{
					code="300";
					message="请重新反馈";
				}
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		response.getWriter().append("code: ").append(code).append("; message: ").append(message);
	}
	@Override
	public void destroy(){
		super.destroy();
	}
}

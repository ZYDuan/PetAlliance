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

/**
 * Servlet implementation class Evaluation
 */
@WebServlet("/Evaluation")
public class Evaluation extends HttpServlet {
		private static final long serialVersionUID=1L;
		
		public Evaluation(){}
		
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
			
			String eId=request.getParameter("eId");
			String fId=request.getParameter("fId");
			String star1=request.getParameter("description");
			String star2=request.getParameter("environment");
			String star3=request.getParameter("attitude");
			String longevaluation=request.getParameter("longevaluation");
			String user_id=request.getParameter("user_id");
			eId="1";
			
			
			try{
				ResultSet result;
				String sqlQuery="select * from "+DBUtil.TABLE_EVALUATION+" where FamilyId= "+fId;
				result=DBUtil.query(sqlQuery);
				if(result.next()){
					code="100";
					message="不能重复评论";
				}else{
					String sqlInsert="insert into "+DBUtil.TABLE_EVALUATION+
							"(FamilyId, Description, Environment, Attitude, LongEvaluation,user_id) values ('"
							+fId+"','"+star1+"','"+star2+"','"+star3+"','"+longevaluation+"','"+"222"+"')";
					if(DBUtil.update(sqlInsert)>0){
						code="200";
						message="评价成功";
					}else{
						code="300";
						message="评价失败";
					}
				}
			}catch(SQLException e){
				e.printStackTrace();
			}
			response.getWriter().append("code:").append(code).append(";message:").append(message); 
		}
		
		@Override
		public void destroy(){
			super.destroy();
		}

		
}

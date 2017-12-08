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
 * Servlet implementation class pet_home
 */
@WebServlet("/pet_home")
public class PetHome extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PetHome() {
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
		BufferedReader read=request.getReader() ;
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
		String home_name=requestParam.getString("home_name");
		String home_address=requestParam.getString("home_address");
		String home_exp_year=requestParam.getString("home_exp_year");
		String home_intro=requestParam.getString( "home_intro");
		String home_type=requestParam.getString("home_type");
		String home_pet=requestParam.getString("home_pet");
		
		String sql = String.format("INSERT INTO %s (USER_ID,HOME_NAME,HOME_ADDRESS,HOME_EXP_YEAR,HOME_INTRO,HOME_TYPE,HOME_PET ) VALUES " +
				"(%s,'%s','%s','%s','%s','%s','%s')", 
				DBUtil.Table_PetHome,user_id,home_name,home_address,home_exp_year,home_intro,home_type,home_pet);
		
		System.out.println(sql);

		try {
			DBUtil.update(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		CommonResponse res = new CommonResponse();
		res.setResCode("0");
		response.setContentType("text/html;charset=utf-8");
		String resStr = JSONObject.fromObject(res).toString();
		response.getWriter().append(resStr).flush();
	}
	

}

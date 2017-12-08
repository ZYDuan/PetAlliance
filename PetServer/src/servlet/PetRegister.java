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
 * Servlet implementation class PetRegister
 */
@WebServlet("/PetRegister")
public class PetRegister extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PetRegister() {
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
		String req = new String(sb.toString().getBytes("ISO8859-1"), "UTF-8");
		System.out.println(req);
		
		JSONObject object = JSONObject.fromObject(req);
		String requestCode = object.getString("requestCode");
		JSONObject requestParam = object.getJSONObject("requestParam");
		
		String user_id=requestParam.getString("user_id");
		String pet_name=requestParam.getString("pet_name");
		String pet_kind=requestParam.getString("pet_kind");
		String pet_day=requestParam.getString("pet_day");
		String pet_sex=requestParam.getString("pet_sex");
		String pet_weight=requestParam.getString("pet_weight");
		String pet_intro=requestParam.getString("pet_intro");
		
		String sql = String.format("INSERT INTO %s (USER_ID,PET_NAME,PET_KIND,PET_DAY,PET_SEX,PET_WEIGHT,PET_INTRO ) VALUES " +
				"(%s,'%s','%s','%s','%s','%s','%s')", 
				DBUtil.Table_PetInfo,user_id,pet_name,pet_kind,pet_day,pet_sex,pet_weight,pet_intro);
		
		System.out.println(sql);

		try {
			DBUtil.update(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		CommonResponse res = new CommonResponse();
		res.setResCode("0"); 
		request.setCharacterEncoding("utf-8");  
		 response.setContentType("text/html;charset=utf-8");
		String resStr = JSONObject.fromObject(res).toString();
		response.getWriter().append(resStr).flush();
	}
	

}

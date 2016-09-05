package com.sqk.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sqk.dao.GPSDao;
import com.sqk.dao.UserDao;
import com.sqk.model.User;

public class sendGPS extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public sendGPS() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request, response);
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");

		String userEmail = request.getParameter("u_id");
		String latString = request.getParameter("latString");
		String longString = request.getParameter("longString");
		double latitude = Double.parseDouble(latString);
		double longitude = Double.parseDouble(longString);
		UserDao userDao = new UserDao();
		User user = userDao.searchByEmail(userEmail);
		GPSDao gpsDao = new GPSDao();
		String result = gpsDao.sendGPS(user, latitude, longitude);
		System.out.println("发送GPS信息结果：" + result);
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}

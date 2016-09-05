package com.sqk.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sqk.dao.FriendDao;
import com.sqk.dao.UserDao;
import com.sqk.model.User;

public class AddFollowServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public AddFollowServlet() {
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

		String user_email = request.getParameter("u_id");
		String otherUser_email = request.getParameter("otherUser_id");
		UserDao userDao = new UserDao();
		User user = userDao.searchByEmail(user_email);
		User otherUser = userDao.searchByEmail(otherUser_email);
		FriendDao friendDao = new FriendDao();
		boolean flag = friendDao.addFollow(user, otherUser);
		System.out.println("添加关注的结果："+flag);
		String flagString = String.valueOf(flag);
		
		response.getOutputStream().write(
				flagString.toString().getBytes("UTF-8"));
		response.setContentType("text/json; charset=UTF-8");
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

package com.ensinthanaigal.server.login;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ensinthanaigal.server.util.AdminUtil;
import com.ensinthanaigal.server.util.DataUtil;

public class LoginServlet extends HttpServlet {

	/**
     * 
     */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, java.io.IOException {
		boolean adminLogin = AdminUtil.checkSession(request);
		ServletContext context = getServletContext();
		if (!adminLogin) {
			try {
				AdminUtil.witeHTMLToResponse(context, "/WEB-INF/login.html",
						response);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return;
		}
		try {
			AdminUtil.witeHTMLToResponse(context, "/WEB-INF/admin.html",
					response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String userName = request.getParameter("username");
		String passWord = request.getParameter("password");

		Map<String, String> userCredentials = DataUtil.getCredentials("admin");
		String userNames = userCredentials.get("userName");

		if (Arrays.asList(userNames.split(",")).contains(userName)) {
			if (passWord.equals(userCredentials.get("password"))) {
				HttpSession session = request.getSession();
				session.setAttribute("admin_login", true);
				ServletContext context = getServletContext();
				try {
					AdminUtil.witeHTMLToResponse(context,
							"/WEB-INF/admin.html", response);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return;
			}
		}

		response.sendRedirect("/");
	}
}

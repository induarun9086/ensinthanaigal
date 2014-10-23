package com.ensinthanaigal.server.admin;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ensinthanaigal.server.util.AdminUtil;

public class DefaultServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {
			ServletContext context = getServletContext();
			AdminUtil.witeHTMLToResponse(context, "/index.html", response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

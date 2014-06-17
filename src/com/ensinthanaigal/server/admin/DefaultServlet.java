package com.ensinthanaigal.server.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DefaultServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static final String INDEX = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">\n"
			+ "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n"
			+ "\n"
			+ "  <head>\n"
			+ "  \n"
			+ "  <meta http-equiv=\"content-type\" content=\"text/html; charset=UTF-8\" />\n"
			+ "  <meta name=\"Description\" content=\"Personal Blog of Indumathi Dinesh Arun\" />\n"
			+ "  <meta name=\"Keywords\" content=\"cooking, coding, technology, travel, entertainment\" />\n"
			+ "\t\n"
			+ "  <link type=\"text/css\" rel=\"stylesheet\" href=\"styles/main.css\">\n"
			+ "  <link type=\"text/css\" rel=\"stylesheet\" href=\"styles/simpleLoader.css\">\n"
			+ "\t<link href=\"https://fonts.googleapis.com/css?family=Courgette\" rel=\"stylesheet\" type=\"text/css\">\n"
			+ "\t<link href=\"https://fonts.googleapis.com/css?family=Arizonia\" rel=\"stylesheet\" type=\"text/css\">\n"
			+ "\t<link href=\"https://fonts.googleapis.com/css?family=Salsa\" rel=\"stylesheet\" type=\"text/css\">\n"
			+ "\t<link href=\"https://fonts.googleapis.com/css?family=Nova+Square\" rel=\"stylesheet\" type=\"text/css\">\n"
			+ "\t<link href=\"https://fonts.googleapis.com/css?family=Fredericka+the+Great\" rel=\"stylesheet\" type=\"text/css\">\n"
			+ "\t<link href=\"https://fonts.googleapis.com/css?family=Source+Code+Pro\" rel=\"stylesheet\" type=\"text/css\">\n"
			+ "\t<link href=\"https://fonts.googleapis.com/css?family=Handlee\" rel=\"stylesheet\" type=\"text/css\">\n"
			+ "\t\n"
			+ "\t<script type=\"text/javascript\" src=\"scripts/main.js\"></script>\n"
			+ "  <script type=\"text/javascript\" src=\"scripts/jquery.js\"></script>\n"
			+ "\t\n"
			+ "  <title>என் சிந்தனைகள் - A Sweet Trail of my memories</title>\n"
			+ "\t\n"
			+ "  </head>\n"
			+ "  \n"
			+ "  <body onload=\"startUp()\" onresize=\"resizeMe()\">\n"
			+ "  \n"
			+ "    <!-- Begin: Header Section -->\n"
			+ "\t\n"
			+ "\t<div class=\"home-bar\"> \n"
			+ "\t\t<img class=\"fav-icon\" src=\"favicon.ico\" onclick=\"getPage(0, 0)\" />\n"
			+ "\t\t<img class=\"home-logo\" src=\"imgs/home-logo.png\" onclick=\"getPage(0, 0)\" />\n"
			+ "\t</div>\n"
			+ "\t\n"
			+ "\t<div class=\"social-bar\">\n"
			+ "\t\t<a href=\"https://www.linkedin.com/profile/view?id=102422836\" target=\"_blank\"><img class=\"social-logo\" src=\"imgs/in-logo.png\" /></a>\n"
			+ "\t\t<a href=\"https://twitter.com/indu9086\" target=\"_blank\"><img class=\"social-logo\" src=\"imgs/tw-logo.png\" /></a>\n"
			+ "\t\t<a href=\"https://github.com/induarun9086\" target=\"_blank\"><img class=\"social-logo\" src=\"imgs/git-logo.png\" /></a>\n"
			+ "\t</div>\n"
			+ "\t\n"
			+ "\t<div class=\"header-bar\">\t\t\n"
			+ "\t\t<div class=\"header-nav\" onclick=\"getPage(1, 0)\"> Travel </div>\n"
			+ "\t\t<div class=\"header-nav\" onclick=\"getPage(2, 0)\"> Cooking </div>\n"
			+ "\t\t<div class=\"header-nav\" onclick=\"getPage(3, 0)\"> Technology </div>\n"
			+ "\t\t<div class=\"header-nav\" onclick=\"getPage(4, 0)\"> Entertainment </div>\n"
			+ "\t\t<div class=\"header-nav\" onclick=\"getPage(5, 0)\"> General </div>\n"
			+ "\t\t<!-- <div class=\"dummy-header-nav-right\"> &nbsp; </div> -->\n"
			+ "\t\t<!-- <div class=\"dummy-header-nav-left\"> &nbsp; </div> -->\n"
			+ "\t</div>\n"
			+ "\t\n"
			+ "\t\n"
			+ "\t\n"
			+ "\t<!-- End: Header Section -->\n"
			+ "\t\n"
			+ "\t<!-- Begin: Body Section -->\n"
			+ "\t\n"
			+ "\t<div class=\"content-frame\"> </div>\n"
			+ "\t\n"
			+ "\t<!-- End: Body Section -->\n"
			+ "\t\n"
			+ "\t<!-- Begin: Footer Section -->\n"
			+ "\t\n"
			+ "\t<div class=\"footer-bar\">\n"
			+ "\t\tAll content provided on this blog is for informational purposes only. The owner of this blog makes no representations as to the accuracy or completeness of any information on this site or found by following any link on this site. All the content in this site\n"
			+ "\t\tis either own work or if any copywright is available it goes to the origial owners.\n"
			+ "\t</div>\n"
			+ "\t\t\n"
			+ "\t<!-- End: Footer Section -->\n"
			+ "\t\t\n" + "  </body>\n" + "</html>\n";

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().write(INDEX);
	}

}

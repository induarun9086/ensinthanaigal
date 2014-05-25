package com.ensinthanaigal.server.login;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ensinthanaigal.server.util.AdminUtil;

public class LoginServlet extends HttpServlet
{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private static final String ADMIN_PAGE = "<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">"
	    + "<html>"
	    + "<head>"
	    + "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=ISO-8859-1\">"
	    + "<script type=\"text/javascript\" src=\"scripts/jquery.js\"></script>"
	    + "<script type=\"text/javascript\" src=\"scripts/admin.js\"></script>"
	    + "<link type=\"text/css\" rel=\"stylesheet\" href=\"styles/main.css\">"
	    + "<link type=\"text/css\" rel=\"stylesheet\" href=\"styles/simpleLoader.css\">"
	    + "<link href=\"https://fonts.googleapis.com/css?family=Courgette\" rel=\"stylesheet\" type=\"text/css\">"
	    + "<link href=\"https://fonts.googleapis.com/css?family=Arizonia\" rel=\"stylesheet\" type=\"text/css\">"
	    + "<link href=\"https://fonts.googleapis.com/css?family=Salsa\" rel=\"stylesheet\" type=\"text/css\">"
	    + "<link href=\"https://fonts.googleapis.com/css?family=Nova+Square\" rel=\"stylesheet\" type=\"text/css\">"
	    + "<link href=\"https://fonts.googleapis.com/css?family=Fredericka+the+Great\" rel=\"stylesheet\" type=\"text/css\">"
	    + "<link href=\"https://fonts.googleapis.com/css?family=Handlee\" rel=\"stylesheet\" type=\"text/css\">"
	    + "<title>Admin Page</title>"
	    + "</head>"
	    + "<body onload=\"resizeMe()\" onresize=\"resizeMe()\">"
	    + "  <div class=\"content-preview\" onclick=\"closePreview()\"></div>"
	    + "	<form id=\"adminform\" name=\"adminform\" class=\"adminform\" action=\"/adminaction\" method=\"post\">"
	    + "		<div>"
	    + "			<label>Category</label>"
	    + "			<div>"
	    + "				<select name=\"category\">"
	    + "					<option value=\"1\">Travel</option>"
	    + "					<option value=\"2\">Cooking</option>"
	    + "					<option value=\"3\">Technology</option>"
	    + "					<option value=\"4\">Entertainment</option>"
	    + "				</select>"
	    + "			</div>"
	    + "		</div>"
	    + "		<div>"
	    + "			<label>Title</label>"
	    + "			<div>"
	    + "				<textarea name=\"title\" rows=\"2\" cols=\"50\"></textarea>"
	    + "			</div>"
	    + "		</div>"
	    + "		<div>"
	    + "			<label>tags</label>"
	    + "			<div>"
	    + "				<textarea name=\"tags\" rows=\"2\" cols=\"50\"></textarea>"
	    + "			</div>"
	    + "		</div>"
	    + "		<div>"
	    + "			<label>Content</label>"
	    + "			<div>"
	    + "				<textarea name=\"content\" rows=\"20\" cols=\"100\"></textarea>"
	    + "			</div>"
	    + "		</div>"
	    + "		<div>"
	    + "			<input type=\"checkbox\" name=\"testmode\" checked=\"checked\"> TestMode <br> "
	    + "		</div>"
	    + "		<div>"
	    + "			<input type=\"hidden\" name=\"logout\" value=\"\">"
	    + "		</div>"
	    + "		<button name=\"post\" type=\"submit\" value=\"post\" onclick=\"submitAdminForm()\">Post</button>"
	    + "	        <button type=\"button\" name=\"preview\" onclick=\"createPreview()\" style=\"margin:3%;margin-top:0px;\">Preview</button>"
	    + "	        <button type=\"button\" name=\"logout\" onclick=\"sendLogout()\" style=\"margin:3%;margin-top:0px;\">Logout</button>"
	    + "	</form>"
	    + "</body>" + "</html>";

    private static final String LOGIN_FORM = "<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">"
	    + "<html>"
	    + "<head>"
	    + "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=ISO-8859-1\">"
	    + "<script type=\"text/javascript\" src=\"scripts/jquery.js\"></script>"
	    +"<script src=\"http://crypto-js.googlecode.com/svn/tags/3.1.2/build/rollups/sha256.js\"></script>"
	    + "<script type=\"text/javascript\" src=\"scripts/admin.js\"></script>"
	    + "<link type=\"text/css\" rel=\"stylesheet\" href=\"styles/main.css\">"
	    + "<link type=\"text/css\" rel=\"stylesheet\" href=\"styles/simpleLoader.css\">"
	    + "<link href=\"https://fonts.googleapis.com/css?family=Courgette\" rel=\"stylesheet\" type=\"text/css\">"
	    + "<link href=\"https://fonts.googleapis.com/css?family=Arizonia\" rel=\"stylesheet\" type=\"text/css\">"
	    + "<link href=\"https://fonts.googleapis.com/css?family=Salsa\" rel=\"stylesheet\" type=\"text/css\">"
	    + "<link href=\"https://fonts.googleapis.com/css?family=Nova+Square\" rel=\"stylesheet\" type=\"text/css\">"
	    + "<link href=\"https://fonts.googleapis.com/css?family=Fredericka+the+Great\" rel=\"stylesheet\" type=\"text/css\">"
	    + "<link href=\"https://fonts.googleapis.com/css?family=Handlee\" rel=\"stylesheet\" type=\"text/css\">"
	    + "<title>Admin Page</title>"
	    + "</head>"
	    + "<body onload=\"resizeMe()\" onresize=\"resizeMe()\">"
	    + "<div class=\"loginDiv\"><div class=\"loginHeader\">Ensinthanaigal Admin Login</div>"
	    + "	<form id=\"loginform\" name=\"loginform\" class=\"loginform\" action=\"/admin\" method=\"post\">"
	    + "		<div>"
	    + "			<label>UserName</label>"
	    + "			<div>"
	    + "				<input type=\"text\" name=\"username\" value=\"\" />"
	    + "			</div>"
	    + "		</div>"
	    + "		<div>"
	    + "			<label>Password</label>"
	    + "			<div>"
	    + "				<input type=\"password\" name=\"password\" value=\"\"  />"
	    + "			</div>"
	    + "		</div>"
	    + "		<br /><button name=\"login\" type=\"submit\" class=\"loginBtn\" value=\"login\" onclick=\"submitLoginForm()\">Login</button>"
	    + "	</form></div>" + "</body>" + "</html>";

    private static final String PASSWORD = "f60c88f268e8eff41411138dc0925ed7db585016a0075ecf7fd659c263652aac";

    private static final String USER1 = "dinesharun";

    private static final String USER2 = "indumathi";

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, java.io.IOException
    {
    	boolean adminLogin = AdminUtil.checkSession(request);
    	if ( ! adminLogin)
    	{
    	    response.getWriter().write(LOGIN_FORM);
    	    return;
    	}
    	response.getWriter().write(ADMIN_PAGE);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
	String userName = req.getParameter("username");
	String passWord = req.getParameter("password");

	if (userName.equals(USER1) || userName.equals(USER2))
	{
	    if (passWord.equals(PASSWORD))
	    {
		HttpSession session = req.getSession();
		session.setAttribute("admin_login",true);
		resp.getWriter().write(ADMIN_PAGE);
		return;
	    }
	}

	resp.sendRedirect("/");
    }
}

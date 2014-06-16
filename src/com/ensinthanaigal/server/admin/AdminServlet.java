package com.ensinthanaigal.server.admin;

import java.io.IOException;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ensinthanaigal.data.Post;
import com.ensinthanaigal.server.util.AdminUtil;
import com.google.appengine.api.datastore.Text;

public class AdminServlet extends HttpServlet
{

    private static final long serialVersionUID = 1L;

    private static final Logger log = Logger.getLogger("AdminServlet");

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
	int action = Integer.valueOf(request.getParameter("action"));
	boolean isValidSession = AdminUtil.checkSession(request);
	if ( ! isValidSession)
	{
	    response.getWriter().write("No tresspassing please !!!!");
	    return;
	}
	EntityManager entityManager = null;
	try
	{
	    boolean logOut = Boolean.TRUE;
	    if (AdminUtil.isNullOrEmpty(request.getParameter("logout")))
	    {
		logOut = Boolean.FALSE;
	    }
	    if (logOut == Boolean.TRUE)
	    {
		HttpSession session = request.getSession();
		session.setAttribute("admin_login",false);
	    }
	    else
	    {
		if (action == AdminUtil.CREATE || action == AdminUtil.UPDATE)
		{
		    String title = AdminUtil.checkForNullOrEmpty(
			    request.getParameter("title"),"title");
		    String content = AdminUtil.checkForNullOrEmpty(
			    request.getParameter("content"),"content");
		    String tags = AdminUtil.checkForNullOrEmpty(
			    request.getParameter("tags"),"tags");
		    String category = AdminUtil.checkForNullOrEmpty(
			    request.getParameter("category"),"category");
		    String link = AdminUtil.checkForNullOrEmpty(
				    request.getParameter("link"),"link");
		    boolean testMode = Boolean.TRUE;
		    if (AdminUtil.isNullOrEmpty(request
			    .getParameter("testMode")))
		    {
			testMode = Boolean.FALSE;
		    }

		    EntityManagerFactory emfInstance = Persistence
			    .createEntityManagerFactory("posts");
		    entityManager = emfInstance.createEntityManager();

		    String postidStr = request.getParameter("postid");

		    Post post = null;

		    if (postidStr == null)
		    {
			Calendar cal = Calendar.getInstance(TimeZone
				.getTimeZone("GMT"));
			long createdTime = cal.getTimeInMillis();

			post = new Post();
			post.setPostedAt(createdTime);
		    }
		    else
		    {
			 post = entityManager.find(Post.class, Long.valueOf(postidStr));
		    }

		    post.setTitle(new Text(title));
		    post.setContent(new Text(content));
		    post.setTags(tags);
		    post.setCategory(Integer.valueOf(category));
		    post.setLink(new Text(link.trim()));
		    post.setTestMode(testMode);

		    entityManager.getTransaction().begin();

		    entityManager.persist(post);

		    entityManager.getTransaction().commit();

		    log.log(Level.INFO,"Post inserted successfully");
		}

	    }

	    response.sendRedirect("/");
	}
	catch ( Exception e )
	{
	    log.log(Level.SEVERE,
		    "Exception occured while inserting post into the db"
			    + e.getMessage());
	    response.sendError(HttpServletResponse.SC_BAD_REQUEST,
		    e.getMessage());
	}
	finally
	{
	    if (entityManager != null)
	    {
		entityManager.close();
	    }
	}
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {

    }

}

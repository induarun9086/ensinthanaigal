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

import com.ensinthanaigal.data.Post;
import com.ensinthanaigal.server.util.AdminUtil;

public class AdminServlet extends HttpServlet
{
    
    private static final long serialVersionUID = 1L;

    private static final Logger log = Logger.getLogger("AdminServlet");

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
	EntityManager entityManager = null;
	try
	{
	    String title = AdminUtil.checkForNullOrEmpty(
		    request.getParameter("title"),"title");
	    String content = AdminUtil.checkForNullOrEmpty(
		    request.getParameter("content"),"content");
	    String tags = AdminUtil.checkForNullOrEmpty(
		    request.getParameter("tags"),"tags");
	    String category = AdminUtil.checkForNullOrEmpty(
		    request.getParameter("category"),"category");
	    EntityManagerFactory emfInstance = Persistence
		    .createEntityManagerFactory("posts");
	    entityManager = emfInstance.createEntityManager();

	    Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
	    long createdTime = cal.getTimeInMillis();

	    Post post = new Post();
	    post.setTitle(title);
	    post.setContent(content);
	    post.setTags(tags);
	    post.setCategory(Integer.valueOf(category));
	    post.setPostedAt(createdTime);

	    entityManager.getTransaction().begin();

	    entityManager.persist(post);

	    entityManager.getTransaction().commit();

	    log.log(Level.INFO,"Post inserted successfully");
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

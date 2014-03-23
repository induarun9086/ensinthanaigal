package com.ensinthanaigal.server.page;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ensinthanaigal.server.util.AdminUtil;
import com.google.appengine.labs.repackaged.org.json.JSONArray;
import com.google.appengine.labs.repackaged.org.json.JSONObject;

public class PageServlet extends HttpServlet
{

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {

	EntityManagerFactory emfInstance = Persistence
		.createEntityManagerFactory("posts");
	EntityManager entityManager = emfInstance.createEntityManager();
	try
	{
	    String category = request.getParameter("category");
	    String postId = request.getParameter("postId");
	    String test = request.getParameter("testmode");
	    boolean testMode = Boolean.TRUE;
	    if (AdminUtil.isNullOrEmpty(test))
	    {
		testMode = Boolean.FALSE;
	    }

	    TypedQuery < Object [ ] > q = entityManager
		    .createQuery(
			    "Select title,content,postID,category,tags,postedAt from Post p where p.category ="
				    + category
				    + " and p.postID = "
				    + postId
				    + " and p.testMode = " + testMode,
			    Object [ ].class);
	    List < Object [ ] > results = q.getResultList();

	    JSONObject data = new JSONObject();
	    JSONArray jsonArr = new JSONArray();
	    for ( Object [ ] result : results )
	    {
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("title",result [0]);
		jsonObj.put("content",result [1]);
		jsonObj.put("postId",result [2]);
		jsonObj.put("catId",result [3]);
		jsonObj.put("tags",result [4]);
		jsonObj.put("postedAt",result [5]);
		jsonArr.put(jsonObj);
	    }
	    data.put("data",jsonArr);
	    response.getWriter().write(data.toString());
	    ;
	}
	catch ( Exception e )
	{
	    e.printStackTrace();
	}
	finally
	{
	    entityManager.close();
	}

    }

}

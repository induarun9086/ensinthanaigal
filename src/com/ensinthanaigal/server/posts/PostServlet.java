package com.ensinthanaigal.server.posts;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ensinthanaigal.server.util.AdminUtil;
import com.google.appengine.labs.repackaged.org.json.JSONArray;
import com.google.appengine.labs.repackaged.org.json.JSONObject;

public class PostServlet extends HttpServlet
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
	    String query = "Select title, category, postID from Post p";
	    
	    String testID = request.getParameter("testmode");
	    boolean testMode = Boolean.TRUE;
	    if (AdminUtil.isNullOrEmpty(testID))
	    {
		testMode = Boolean.FALSE;
	    }

	    String category = request.getParameter("category");
	    if (AdminUtil.isNotNullOrEmpty(category))
	    {
		query = query + " where p.category = " + category;
		query = query + " and p.testMode = " + testMode;
	    }
	    else
	    {
		query = query + " where p.testMode = " + testMode;
	    }

	    String sortorder = request.getParameter("sortorder");
	    if (AdminUtil.isNotNullOrEmpty(sortorder))
	    {
		String orderBy = "DESC";
		if (Integer.valueOf(sortorder) == 0)
		{
		    orderBy = "ASC";
		}
		query = query + " ORDER BY p.postedAt " + orderBy;
	    }

	    TypedQuery <Object[]> q = entityManager.createQuery(query,Object[].class);

	    String limit = request.getParameter("limit");
	    if (AdminUtil.isNotNullOrEmpty(limit))
	    {
		q.setMaxResults(Integer.valueOf(limit));
	    }

	    List < Object [ ] > results = q.getResultList();

	    JSONObject data = new JSONObject();
	    JSONArray jsonArr = new JSONArray();
	    for ( Object [ ] result : results )
	    {
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("title",result [0]);
		jsonObj.put("catId",result [1]);
		jsonObj.put("postId",result [2]);
		jsonArr.put(jsonObj);
	    }
	    data.put("data",jsonArr);
	    response.getWriter().write(data.toString());
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

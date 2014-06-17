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
import com.google.appengine.api.datastore.Text;
import com.google.appengine.labs.repackaged.org.json.JSONArray;
import com.google.appengine.labs.repackaged.org.json.JSONObject;

public class PageServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		EntityManagerFactory emfInstance = Persistence
				.createEntityManagerFactory("posts");
		EntityManager entityManager = emfInstance.createEntityManager();
		try {
			String category = request.getParameter("category");
			String postId = request.getParameter("postId");
			String link = request.getParameter("link");
			String test = request.getParameter("testmode");
			String query = " Select title,content,postID,category,tags,postedAt,testMode,link from Post p where p.category ="
					+ category;
			if (AdminUtil.isNotNullOrEmpty(postId)) {
				query = query + " and p.postID = " + postId;
			} else if (AdminUtil.isNotNullOrEmpty(link)) {
				query = query + " and p.link = " + link;
			}
			boolean testMode = Boolean.TRUE;
			if (!(test == null)) {
				testMode = Boolean.valueOf(test);
				query += "and p.testMode = " + testMode;
			}

			TypedQuery<Object[]> q = entityManager.createQuery(query,
					Object[].class);
			List<Object[]> results = q.getResultList();

			JSONObject data = new JSONObject();
			JSONArray jsonArr = new JSONArray();
			for (Object[] result : results) {
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("title", ((Text) result[0]).getValue());
				jsonObj.put("content", ((Text) result[1]).getValue());
				jsonObj.put("postId", result[2]);
				jsonObj.put("catId", result[3]);
				jsonObj.put("tags", result[4]);
				jsonObj.put("postedAt", result[5]);
				jsonObj.put("testMode", result[6]);
				Object linkStr = result[7];
				if (linkStr != null) {
					linkStr = ((Text) result[7]).getValue();
				} else {
					linkStr = "";
				}
				jsonObj.put("link", linkStr);
				jsonArr.put(jsonObj);
			}
			data.put("data", jsonArr);
			response.getWriter().write(data.toString());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			entityManager.close();
		}

	}
}

package com.ensinthanaigal.server.posts;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.labs.repackaged.org.json.JSONArray;
import com.google.appengine.labs.repackaged.org.json.JSONObject;

public class PostServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		EntityManagerFactory emfInstance = Persistence
				.createEntityManagerFactory("posts");
		EntityManager entityManager = emfInstance.createEntityManager();
		try {
			String category = request.getParameter("category");
			Query q = entityManager
					.createQuery("Select title from Post p where p.category = "
							+ category);
			List<String> results = q.getResultList();

			JSONObject data = new JSONObject();
			JSONArray jsonArr = new JSONArray();
			for (int i = 0; i < results.size(); i++) {
				jsonArr.put(results.get(i));
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

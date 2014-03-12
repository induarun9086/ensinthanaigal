package com.ensinthanaigal.server.admin;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ensinthanaigal.data.Post;
import com.ensinthanaigal.server.util.AdminUtil;

public class AdminServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		EntityManager entityManager = null;
		try {
			String title = AdminUtil.checkForNullOrEmpty(
					request.getParameter("title"), "title");
			String content = AdminUtil.checkForNullOrEmpty(
					request.getParameter("content"), "content");
			String tags = AdminUtil.checkForNullOrEmpty(
					request.getParameter("tags"), "tags");
			String category = AdminUtil.checkForNullOrEmpty(
					request.getParameter("category"), "category");
			EntityManagerFactory emfInstance = Persistence
					.createEntityManagerFactory("posts");
			entityManager = emfInstance.createEntityManager();

			Post post = new Post();
			post.setTitle(title);
			post.setContent(content);
			post.setTags(tags);
			post.setCategory(Integer.valueOf(category));

			entityManager.getTransaction().begin();

			entityManager.persist(post);

			entityManager.getTransaction().commit();

			System.out.println("post inserted successfully");
		} catch (Exception e) {
			System.out
					.println("Exception occured while inserting post into the db"
							+ e.getMessage());
			response.sendError(HttpServletResponse.SC_BAD_REQUEST,
					e.getMessage());
		} finally {
			if (entityManager != null) {
				entityManager.close();
			}
		}
	}

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

	}

}

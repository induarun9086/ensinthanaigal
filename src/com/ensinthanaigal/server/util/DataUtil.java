package com.ensinthanaigal.server.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import com.ensinthanaigal.data.Credentials;

public class DataUtil {
	public static Map<String, String> getCredentials(String type) {

		Map<String, String> credentialsMap = new HashMap<String, String>();

		EntityManagerFactory emfInstance = Persistence
				.createEntityManagerFactory("credentials");
		EntityManager entityManager = emfInstance.createEntityManager();

		try {
			String query = "Select c.parameterName,c.parameterValue from Credentials c where "
					+ "c.type = '" + type + "'";
			TypedQuery<Object[]> q = entityManager.createQuery(query,
					Object[].class);

			List<Object[]> resultList = q.getResultList();

			for (Object[] parameter : resultList) {
				credentialsMap
						.put((String) parameter[0], (String) parameter[1]);
			}
		} catch (Exception e) {
			System.out
					.println("Exception Occured while getting the credentials from table");
		}

		return credentialsMap;
	}
}

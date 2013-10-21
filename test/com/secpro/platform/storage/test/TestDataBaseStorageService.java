package com.secpro.platform.storage.test;

import java.sql.Connection;
import java.util.HashMap;

import com.secpro.platform.core.services.ServiceHelper;
import com.secpro.platform.storage.services.DataBaseStorageService;

public class TestDataBaseStorageService {
	public void doATest() {
		final DataBaseStorageService dbss = ServiceHelper.findService(DataBaseStorageService.class);
		final HashMap<Integer, String> cacheMap = new HashMap<Integer, String>();
		for (int i = 0; i < 50; i++) {
			final int fi = i;
			final long val = System.currentTimeMillis();
			new Thread() {
				public void run() {
					try {
						Connection conn = dbss.getConnection();
						if (cacheMap.containsKey(conn.hashCode()) == false) {
							System.out.println(">>>>i:" + fi + "  new one" + conn.getClass().getName() + "connecito:" + (!conn.isClosed()) + " using "
									+ (System.currentTimeMillis() - val) + "MS");
							cacheMap.put(conn.hashCode(), null);
						} else {

							System.out.println(">>>>i:" + fi + "  old one" + conn.hashCode() + "connecito:" + (!conn.isClosed()) + " using " + (System.currentTimeMillis() - val)
									+ "MS");
						}
						System.out.println("SQL execute:" + conn.createStatement().execute("select * from tabs"));
						// conn.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}.start();
		}
	}
}

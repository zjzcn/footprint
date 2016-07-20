package com.footprint.server.platform.ucm.service;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.footprint.client.ucm.DataListener;
import com.footprint.client.ucm.UcmClient;

@Service
public class TestUcmService {

	public void test() {
		try {
			UcmClient client = UcmClient.getInstance();
			String val = client.getConfig("/test");
			System.out.println(val);
			client.watchConfig("/test", new DataListener() {
				@Override
				public void dataChanged(String data) {
					System.out.println("wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww");
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

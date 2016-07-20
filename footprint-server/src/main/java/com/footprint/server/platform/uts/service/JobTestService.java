package com.footprint.server.platform.uts.service;

import org.springframework.stereotype.Service;

@Service("test")
public class JobTestService {

	public void test() {
		System.out.println("================================");
//		throw new RuntimeException();
	}
}

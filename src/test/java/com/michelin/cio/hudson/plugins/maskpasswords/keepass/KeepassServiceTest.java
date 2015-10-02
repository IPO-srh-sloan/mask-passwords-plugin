package com.michelin.cio.hudson.plugins.maskpasswords.keepass;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.*;

import java.io.File;
import java.net.URL;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class KeepassServiceTest {
	
	private static final String MASTER_PASS = "@MasterP455";
	private KeepassService service;
	
	@Before
	public void setup(){
		URL url = getClass().getResource("/TestDB.kdbx");
		assertNotNull("getting resource returned null", url);
		String path = url.getPath();
		System.out.println("Path is " + path);
		assertTrue("test db doesn't exist", new File(path).exists());
		service = new KeepassService(path, MASTER_PASS);
	}

	@Test
	public void testReadEntries() {
		Map<String, String> entries = service.getKeepassEntries();
		assertEquals("wrong number of entries", 2, entries.size());
		assertEquals("Wrong dev password", "devpass", entries.get("dbUser_dev"));
		assertEquals("Wrong test password", "testpass", entries.get("dbUser_test"));
	}

}

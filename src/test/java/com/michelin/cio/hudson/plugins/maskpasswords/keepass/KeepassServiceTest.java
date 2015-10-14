package com.michelin.cio.hudson.plugins.maskpasswords.keepass;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class KeepassServiceTest {
	
	private static final String MASTER_PASS = "@MasterP455";
	private KeepassService service;
	private File kpFile;
	
	@Before
	public void setup(){
		URL url = getClass().getResource("/TestDB.kdbx");
		assertNotNull("getting resource returned null", url);
		String path = url.getPath();
		System.out.println("Path is " + path);
		kpFile = new File(path);
		assertTrue("test db doesn't exist", kpFile.exists());
		service = new KeepassService(path, MASTER_PASS);
	}

	@Test
	public void testReadEntries() {
		Map<String, String> entries = service.getKeepassEntries();
		assertEquals("wrong number of entries", 7, entries.size());
		assertEquals("Wrong dev password", "devpass", entries.get("dbUser_dev_pass"));
		assertEquals("Wrong test password", "testpass", entries.get("dbUser_test_pass"));
		assertEquals("Wrong test userId", "dbuser", entries.get("dbUser_test_userId"));
		assertNull("should be no devDb url", entries.get("dbUser_dev_url"));
		assertEquals("Wrong aaa url", "http://google.com", entries.get("aaauser_url"));
	}

	@Test 
	public void testReadEntryHardcodedPath(){
		String path = "F:\\workspaces\\poc\\mask-passwords-plugin\\src\\test\\resources\\TestDB.kdbx";
		System.out.println("Path is " + path);
		assertTrue("test db doesn't exist", new File(path).exists());
		service = new KeepassService(path, MASTER_PASS);
		Map<String, String> entries = service.getKeepassEntries();
		assertEquals("wrong number of entries", 7, entries.size());
		
	}
	
	@Test
	public void testGetAllPasswords(){
		List<String> passwords = service.getKeepassPasswords();
		assertEquals("Wrong number of entries", 3, passwords.size());
		assertTrue("should contain devpass", passwords.contains("devpass"));
	}
}

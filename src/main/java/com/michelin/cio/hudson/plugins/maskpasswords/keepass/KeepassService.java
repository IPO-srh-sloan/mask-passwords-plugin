package com.michelin.cio.hudson.plugins.maskpasswords.keepass;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.slackspace.openkeepass.KeePassDatabase;
import de.slackspace.openkeepass.domain.Entry;
import de.slackspace.openkeepass.domain.KeePassFile;

public class KeepassService {
	
	private String keepassPath;
	private String masterPass;

	public KeepassService(String keepassPath, String masterPass) {
		super();
		this.keepassPath = keepassPath;
		this.masterPass = masterPass;
	}

	public String getKeepassPath() {
		return keepassPath;
	}

	public void setKeepassPath(String keepassPath) {
		this.keepassPath = keepassPath;
	}

	public String getMasterPass() {
		return masterPass;
	}

	public void setMasterPass(String masterPass) {
		this.masterPass = masterPass;
	}

	public Map<String, String> getKeepassEntries(){
		Map<String, String> allEntries = new HashMap<String, String>();
		
		KeePassFile database = KeePassDatabase.getInstance(keepassPath).openDatabase(masterPass);
		List<Entry> entries = database.getEntries();
		for(Entry e : entries){
			allEntries.put(e.getTitle(), e.getPassword());
		}
		
		return allEntries;
	}

	KeePassFile database = KeePassDatabase.getInstance("\\\\chns\\ns\\Safe\\Deployment.kdbx").openDatabase("@DrDud02wii");
	
}

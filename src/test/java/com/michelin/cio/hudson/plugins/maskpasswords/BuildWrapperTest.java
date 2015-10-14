package com.michelin.cio.hudson.plugins.maskpasswords;

import static org.junit.Assert.*;
import hudson.model.AbstractBuild;
import hudson.model.ParametersAction;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import com.michelin.cio.hudson.plugins.maskpasswords.MaskPasswordsBuildWrapper.GlobalKeepassPair;
import com.michelin.cio.hudson.plugins.maskpasswords.MaskPasswordsBuildWrapper.VarPasswordPair;

public class BuildWrapperTest {
	private static final String MASTER_PASS = "@MasterP455";
	
	MaskPasswordsBuildWrapper wrapper;
	MaskPasswordsConfig config;
	List<GlobalKeepassPair> kpPairs;
	List<VarPasswordPair> vpList;
	AbstractBuild build;
	
	OutputStream logger;
	
	@Before
	public void setUp(){
		logger = new ByteArrayOutputStream();
		
		kpPairs = new ArrayList<GlobalKeepassPair>();
		URL url = getClass().getResource("/TestDB.kdbx");
		kpPairs.add(new GlobalKeepassPair(url.getPath(), MASTER_PASS));
		
		vpList = new ArrayList <VarPasswordPair>();
		
		config = EasyMock.createMockBuilder(MaskPasswordsConfig.class)
				.addMockedMethod("getGlobalVarPasswordPairs")
				.addMockedMethod("getGlobalKeepassLocations")
				.addMockedMethod("isMasked")
				.createMock();
		wrapper = EasyMock.createMockBuilder(MaskPasswordsBuildWrapper.class)
				.addMockedMethod("getConfig")
				.createMock();
		
		EasyMock.expect(config.getGlobalKeepassLocations()).andReturn(kpPairs);
		EasyMock.expect(config.getGlobalVarPasswordPairs()).andReturn(vpList);
		EasyMock.expect(config.isMasked(EasyMock.anyString())).andReturn(true).anyTimes();
		EasyMock.replay(config);
		EasyMock.expect(wrapper.getConfig()).andReturn(config).anyTimes();
		EasyMock.replay(wrapper);
		
		build = EasyMock.createMock(AbstractBuild.class);
		EasyMock.expect(build.getAction(ParametersAction.class)).andReturn(null);
		EasyMock.replay(build);
		
	}

	@Test
	public void testDecorateLogger() throws IOException {
		OutputStream mpos = wrapper.decorateLogger(build, logger);
		mpos.write("devpass is a password in testdb\n".getBytes());
		mpos.write("dbuser is a userId in testdb\n".getBytes());
		String result = logger.toString();
		System.out.println(result);
		assertFalse("devpass should not be in output", result.contains("devpass"));
		assertTrue("dbuser should be in output", result.contains("dbuser"));
	}

}

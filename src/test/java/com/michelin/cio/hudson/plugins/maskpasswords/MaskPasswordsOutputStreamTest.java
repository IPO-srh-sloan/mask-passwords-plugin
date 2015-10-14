package com.michelin.cio.hudson.plugins.maskpasswords;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class MaskPasswordsOutputStreamTest {
	
	private MaskPasswordsOutputStream mpos;
	private OutputStream os;
	List<String> toMask;
	
	@Before
	public void setUp(){
		os = new ByteArrayOutputStream();
		toMask = new ArrayList<String>();
		toMask.add("fine");
		toMask.add("world");
		mpos = new MaskPasswordsOutputStream(os, toMask);
	}

	@Test
	public void testWriteToStream() throws IOException {
		mpos.write("it's the end of the world as we know it\n".getBytes());
		mpos.write("and I feel fine\n".getBytes());
		String result = os.toString();
		System.out.println(result);
		for(String masked : toMask){
			assertFalse("[" + masked + "] should not appear in output", result.contains(masked));
		}
	}

}

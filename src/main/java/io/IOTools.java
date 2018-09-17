package io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.io.IOUtils;

public class IOTools {
	
	public void witeSteam() {
		InputStream is=null;
		OutputStream os=null;
		try {
			IOUtils.copy(is,os);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

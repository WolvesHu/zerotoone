package com.wolves.test.zerotoone.jvm;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

public class HackSystem {
	public final static InputStream in = System.in;
	private static ByteArrayOutputStream baos = new ByteArrayOutputStream();
	public final static PrintStream out = new PrintStream(baos);
	public final static PrintStream err = out;

	public static String getBufferString() {
		return baos.toString();
	}

	public static void clearBuffer() {
		baos.reset();
	}

	public static void setSecurityManager(final SecurityManager s) {
		System.setSecurityManager(s);
	}
	
	public static SecurityManager getSecurityManager() {
		return System.getSecurityManager();
	}
	
	public static long currentTimeMillis() {
		return System.currentTimeMillis();
	}
	
	public static void arraycopy(Object src, int srcPos, Object dest,int destPos, int length) {
		System.arraycopy(src, srcPos, dest, destPos, length);
	}
	
	public 	static int identifyHashCode(Object x) {
		return System.identityHashCode(x);
	}
	
}

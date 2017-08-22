package com.wolves.test.zerotoone.jvm;

public class HotSwapClassLoader extends ClassLoader {
	public HotSwapClassLoader() {
		super(HotSwapClassLoader.class.getClassLoader());
	}

	@SuppressWarnings("rawtypes")
	public Class loadByte(byte[] classByte) {
		return defineClass(null, classByte, 0, classByte.length);
	}
}

package com.wolves.test.zerotoone.jvm;

import java.io.FileInputStream;
import java.io.InputStream;

import org.junit.Test;

public class HackSystemTest {
	@Test
	public void test() {
		try {
			InputStream in = new FileInputStream("E:/workspace/zerotoone/target/classes/com/wolves/test/zerotoone/jvm/TestClass.class");
			byte[] b = new byte[in.available()];
			in.read(b);
			in.close();
			System.out.println(JavaClassExecutor.execute(b));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

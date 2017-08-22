package com.wolves.test.zerotoone.jvm;

import java.lang.reflect.Method;

public class JavaClassExecutor {
	/** 
	* @Title: execute 
	* @Description: 执行外部传过来的代表一个Java类的byte数组，将输入类的byte数组中代表
	* java.lang.System的CONSTANT_Utf8_info常量修改为劫持后的HackSystem类，执行方
	* 法为该类static main(String[] args)方法，输出结果为该类向System.out/err输出的
	* 信息
	* @param @param classByte
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws 
	*/
	@SuppressWarnings("rawtypes")
	public static String execute(byte[] classByte) {
		HackSystem.clearBuffer();
		ClassModifer cm = new ClassModifer(classByte);
		byte[] modifyBytes = cm.modifyUTF8Contant("java/lang/System", "com/wolves/test/zerotoone/jvm/HackSystem");
		HotSwapClassLoader loader = new HotSwapClassLoader();
		
		Class clazz = loader.loadByte(classByte);
		try {
			Method method = clazz.getMethod("main", new Class[]{String[].class});
			method.invoke(null, new String[]{null});
		} catch (Exception e) {
			e.printStackTrace(HackSystem.out);
		}
		
		return HackSystem.getBufferString();
	}
}

package project.lanshan.javarpc;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class TestReflect {
	public static void main(String[] args) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, ClassNotFoundException, InstantiationException{
		Class<?> demo = Class.forName("project.lanshan.JavaRPC.TestDemo");
		Class<?>[] myargs = {String.class,int.class};
		Object[] myar = {"hahahah",127};
		Method method = demo.getMethod("TestArg",myargs);
		TestDemo demo1 = (TestDemo)demo.newInstance();
		demo1.TestArg("hahahaha", 111);
	}
}

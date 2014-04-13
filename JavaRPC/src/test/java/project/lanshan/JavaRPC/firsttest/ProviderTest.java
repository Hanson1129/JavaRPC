package project.lanshan.javarpc.firsttest;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ProviderTest {
	public static void main(String[] args) {
		ApplicationContext acxt = new ClassPathXmlApplicationContext(
				"applicationContext.xml");
		while(true){}
	}
}

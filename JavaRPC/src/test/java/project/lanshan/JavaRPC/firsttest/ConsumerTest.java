package project.lanshan.JavaRPC.firsttest;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ConsumerTest {
	public static void main(String[] args){
		ApplicationContext acxt = new ClassPathXmlApplicationContext("applicationContext.xml");
		Action action = (Action)acxt.getBean("proxy");
		System.out.println(action.getWords());
	}
}

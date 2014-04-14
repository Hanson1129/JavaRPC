package project.lanshan.javarpc.firsttest;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ConsumerTest {
	public static void main(String[] args){
		ApplicationContext acxt = new ClassPathXmlApplicationContext("consumer.xml");
		Action action = (ConsumerAction)acxt.getBean("CService");
		System.out.println(action.getWords());
	}
}

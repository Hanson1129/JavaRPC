package project.lanshan.JavaRPC.firsttest;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import project.lanshan.JavaRPC.Provider.SpringProviderBean;

public class BuildProvider {
	public static void main(String[] args){
		@SuppressWarnings("resource")
		ApplicationContext acxt = new ClassPathXmlApplicationContext("applicationContext.xml");
		SpringProviderBean providerBean = (SpringProviderBean)acxt.getBean("springProviderBean");
		providerBean.publish();
	}
}

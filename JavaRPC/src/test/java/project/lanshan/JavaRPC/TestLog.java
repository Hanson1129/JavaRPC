package project.lanshan.javarpc;

import org.apache.log4j.Logger;

public class TestLog {
	public static void main(String[] args){
		Logger log = Logger.getLogger("TestLog.class");
		log.info("test");
		log.error("testError");
		log.debug("testDebug");
	}
}

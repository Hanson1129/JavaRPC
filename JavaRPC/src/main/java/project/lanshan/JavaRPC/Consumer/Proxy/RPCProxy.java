package project.lanshan.JavaRPC.Consumer.Proxy;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import project.lanshan.JavaRPC.Consumer.Caller.ConsumerCaller;

public class RPCProxy implements MethodInterceptor{
	private ConsumerCaller caller;
	
	@Override
	public Object invoke(MethodInvocation invo) throws Throwable {
		return null;
	}

	public ConsumerCaller getCaller() {
		return caller;
	}

	public void setCaller(ConsumerCaller caller) {
		this.caller = caller;
	}

}

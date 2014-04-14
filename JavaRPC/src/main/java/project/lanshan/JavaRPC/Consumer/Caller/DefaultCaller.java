package project.lanshan.javarpc.consumer.caller;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;





import project.lanshan.javarpc.consumer.netty.Consumer;
import project.lanshan.javarpc.consumer.netty.NettyConsumer;
import project.lanshan.javarpc.model.AddressHolder;
import project.lanshan.javarpc.model.RPCInetAddress;
import project.lanshan.javarpc.model.Request;
import project.lanshan.javarpc.model.ServiceMetadata;

public class DefaultCaller implements InvocationHandler {

	private Consumer consumer = new NettyConsumer();
	private Class<?> clazz;
	private Object target;
	private RPCInetAddress targetAddress = null;

	public boolean call(AddressHolder addresses) {
		targetAddress = selectAddressFromHolder(addresses,
				consumer.getMetadata());
		try {
			consumer.startCall(targetAddress);
		} catch (InterruptedException e) {
			return false;
		}
		if (consumer.getObject(targetAddress) != null) {
			try {
				clazz = Class.forName(consumer.getMetadata().getInterfaceName());
			if (clazz.isInterface()) {
				String packageName = clazz.getPackage().getName();// 获得当前包名
					List<Class<?>> allClass = getClassesByPackageName(packageName);// 获得当前包下以及包下的所有类
					for (int i = 0; i < allClass.size(); i++) {
						
						if (clazz.isAssignableFrom(allClass.get(i))) {
							if (!clazz.equals(allClass.get(i))) {// 本身加不进去
								target = allClass.get(i);
							}
						}
					}
				} 
			}catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			
			return true;
		} else
			return false;
	}

	private RPCInetAddress selectAddressFromHolder(AddressHolder addresses,
			ServiceMetadata metadata) {
		// TODO for test
		return addresses.getAddresses(metadata.getInterfaceName()).get(0);
	}

	public Object getObject() {
		return Proxy.newProxyInstance(target.getClass().getClassLoader(),
				target.getClass().getInterfaces(), this);
	}

	public Consumer getConsumer() {
		return consumer;
	}

	public void setConsumer(Consumer consumer) {
		this.consumer = consumer;
	}

	public void setRequest(Request request) {
		consumer.setRequest(request);
	}

	public Class<?> getObjectType() {
		return consumer.getObjectType();
	}

	public ServiceMetadata getMetadata() {
		return consumer.getMetadata();
	}

	public void setMetadata(ServiceMetadata metadata) {
		consumer.setMetadata(metadata);
	}

	@Override
	public Object invoke(Object target, Method method, Object[] args)
			throws Throwable {
		return consumer.getObject(targetAddress);
	}

	private static List<Class<?>> getClassesByPackageName(String packageName)
			throws IOException, ClassNotFoundException {
		ClassLoader classLoader = Thread.currentThread()
				.getContextClassLoader();
		String path = packageName.replace('.', '/');
		Enumeration<URL> resources = classLoader.getResources(path);
		List<File> dirs = new ArrayList<File>();
		while (resources.hasMoreElements()) {
			URL resource = resources.nextElement();
			dirs.add(new File(resource.getFile()));
		}
		ArrayList<Class<?>> classes = new ArrayList<Class<?>>();
		for (File directory : dirs) {
			classes.addAll(findClasses(directory, packageName));
		}
		return classes;
	}

	private static List<Class<?>> findClasses(File directory, String packageName)
			throws ClassNotFoundException {
		List<Class<?>> classes = new ArrayList<Class<?>>();
		if (!directory.exists()) {
			return classes;
		}
		File[] files = directory.listFiles();
		for (File file : files) {
			if (file.isDirectory()) {
				assert !file.getName().contains(".");
				classes.addAll(findClasses(file,
						packageName + '.' + file.getName()));
			} else if (file.getName().endsWith(".Class<?>")) {
				classes.add(Class.forName(packageName
						+ "."
						+ file.getName().substring(0,
								file.getName().length() - 6)));
			}
		}
		return classes;
	}
}

package project.lanshan.javarpc.provider.netty;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import project.lanshan.javarpc.model.Request;
import project.lanshan.javarpc.model.Response;
import project.lanshan.javarpc.model.ServiceMetadata;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

@Service
public class NettyProvider implements Provider {
//TODO 多服务情况下 metadata有N个怎么办？
	private static Logger log = Logger.getLogger(NettyProvider.class.getName());

	private ServiceMetadata metadata;

	public NettyProvider() {
		metadata = new ServiceMetadata();
	}

	@Override
	public boolean startPublish() {
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();

		try {
			ServerBootstrap bootstrap = new ServerBootstrap();
			bootstrap.group(bossGroup, workerGroup)
					.channel(NioServerSocketChannel.class)
					.childHandler(new ChannelInitializer<SocketChannel>() {

						@Override
						protected void initChannel(SocketChannel ch)
								throws Exception {
							ch.pipeline().addLast(
									new ObjectEncoder(),
									new ObjectDecoder(ClassResolvers
											.cacheDisabled(null)),
									new NettyProviderHandler());
						}
					});

			try {
				bootstrap.bind(metadata.getPort()).sync().channel()
						.closeFuture().sync();
				log.info("bind successful.");
				return true;
			} catch (InterruptedException e) {
				log.error("bind() at " + metadata.getPort() + " fail!");
				return false;
			}
		} finally {
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}

	private class NettyProviderHandler extends ChannelHandlerAdapter {
		@Override
		public void channelRead(ChannelHandlerContext ctx, Object msg) {
			if (!(msg instanceof Request)) {
				log.error("not a request!");
				ctx.close();
				return;
			}

			Request request = (Request) msg;
			Response response;

			if (!request.getInterfaceName().equals(metadata.getInterfaceName())) {
				log.error("Server didn't provide request service.");
				ctx.close();
				return;
			}

			if (request.getCallWay().equals("result")) {
				if ((response = handleResult(request)) != null) {
					ctx.writeAndFlush(response);
				} else {
					ctx.close();
				}

			} else if (request.getCallWay().equals("object")) {
				if ((response = handleObject(request)) != null) {
					ctx.writeAndFlush(response);
				} else {
					ctx.close();
				}
			}
		}

		@Override
		public void channelReadComplete(ChannelHandlerContext ctx)
				throws Exception {
			ctx.flush();
		}

		@Override
		public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
			log.error(cause.getMessage());
			ctx.close();
		}

		public Response handleResult(Request request) {
			String interfaceClassName = metadata.getImplClass();
			String methodName = request.getServiceName();
			Class<?>[] paramatersClass = request.getParametersClass();
			Object[] paramatersObjects = request.getParametersObject();
			try {
				Class<?> interfaceClazz = Class.forName(interfaceClassName);
				if (paramatersClass != null) {
					Method method = interfaceClazz.getMethod(methodName,
							paramatersClass);
					return new Response(method.invoke(
							interfaceClazz.newInstance(), paramatersObjects),interfaceClazz);
				} else {
					Method method = interfaceClazz.getMethod(methodName);
					return new Response(method.invoke(interfaceClazz
							.newInstance()),interfaceClazz);
				}
			} catch (ClassNotFoundException e) {
				log.error("class not found on server");
				return null;
			} catch (SecurityException e) {
				log.error("getDeclaredMethod error");
				return null;
			} catch (NoSuchMethodException e) {
				log.error("not found method in interfaceClazz");
				return null;
			} catch (IllegalArgumentException e) {
				log.error("IllegalArgumentException");
				return null;
			} catch (IllegalAccessException e) {
				log.error("IllegalAccessException");
				return null;
			} catch (InvocationTargetException e) {
				log.error("InvocationTargetException");
				return null;
			} catch (InstantiationException e) {
				log.error("InstantiationException");
				return null;
			}
		}

		public Response handleObject(Request request) {
			String interfaceClassName = metadata.getImplClass();
			try {
				Class<?> interfaceClazz = Class.forName(interfaceClassName);
				return new Response(interfaceClazz.newInstance(),interfaceClazz);
			} catch (ClassNotFoundException e) {
				log.error("class not found on server");
				return null;
			} catch (InstantiationException e) {
				log.error("InstantiationException");
				return null;
			} catch (IllegalAccessException e) {
				log.error("IllegalAccessException");
				return null;
			}
		}
	}


	public String getInterfaceName() {
		return metadata.getInterfaceName();
	}

	public void setInterfaceName(String interfaceName) {
		metadata.setInterfaceName(interfaceName);;
	}

	@Override
	public void setHost(String host) {
		metadata.setHost(host);
	}

	@Override
	public void setPort(int port) {
		metadata.setPort(port);
	}

	@Override
	public ServiceMetadata getMeatadata() {
		return metadata;
	}
}

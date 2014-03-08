package project.lanshan.JavaRPC.Provider.Netty;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.log4j.Logger;

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


import project.lanshan.JavaRPC.model.RPCInetAddress;
import project.lanshan.JavaRPC.model.Request;
import project.lanshan.JavaRPC.model.Response;
import project.lanshan.JavaRPC.model.ServiceMetadata;

public class NettyProvider implements Provider{
	
	private static Logger log = Logger.getLogger(NettyProvider.class.getName());
	
	private ServiceMetadata metadata;
	
	private RPCInetAddress providerAddress;
	
	public NettyProvider(String serviceName,String serviceClass,String host,int port){
		metadata.setServiceName(serviceName);
		metadata.setServiceClass(serviceClass);
		providerAddress.setHost(host);
		providerAddress.setPort(port);
	}

	@Override
	public boolean startPublish(){
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		
		try{
			ServerBootstrap bootstrap = new ServerBootstrap();
			bootstrap.group(bossGroup, workerGroup)
					 .channel(NioServerSocketChannel.class)
					 .childHandler(new ChannelInitializer<SocketChannel>() {

						@Override
						protected void initChannel(SocketChannel ch)
								throws Exception {
							 ch.pipeline().addLast(
			                            new ObjectEncoder(),
			                            new ObjectDecoder(ClassResolvers.cacheDisabled(null)),
			                            new NettyProviderHandler());
						}
					});
				
			try {
				bootstrap.bind(providerAddress.getPort()).sync().channel().closeFuture().sync();
				return true;
			} catch (InterruptedException e) {
				log.error("bind() at "+providerAddress.getPort()+" fail!");
				return false;
			}
		}finally{
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}


	private class NettyProviderHandler extends ChannelHandlerAdapter{
		@Override
		public void channelRead(ChannelHandlerContext ctx,Object msg){
			if(!(msg instanceof Request)){
				log.error("not a request!");
				ctx.close();
				return;
			}
			
			Request request = (Request)msg;
			Response response;
			
			if(request.getServiceName() != metadata.getServiceName() ||
			   request.getClassName() != metadata.getServiceClass()){
				log.error("Server didn't provide request service.");
				ctx.close();
				return;
			}
				
			if(request.getCallWay().equals("result")){
				if((response = handleResult(request)) != null){
					ctx.write(response);
				}else{
					ctx.close();
				}
				
			}else if(request.getCallWay().equals("object")){
				if((response = handleObject(request)) != null){
					ctx.write(response);
				}else{
					ctx.close();
				}
			}
		}
		@Override
		public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
			ctx.flush();
		}

		@Override
		public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
			log.error(cause.getMessage());
			ctx.close();
		}
		
		
		
		public Response handleResult(Request request){
			String interfaceClassName= request.getClassName();
			String methodName = request.getServiceName();
			Class<?>[] paramatersClass = request.getParametersClass();
			Object[] paramatersObjects = request.getParametersObject();
			try{
				Class<?> interfaceClazz = Class.forName(interfaceClassName);
				if(paramatersClass.length > 0){
					Method method = interfaceClazz.getMethod(methodName, paramatersClass);
					return new Response(method.invoke(interfaceClazz.newInstance(),paramatersObjects));
				}else{
					Method method = interfaceClazz.getMethod(methodName);
					return new Response(method.invoke(interfaceClazz.newInstance()));
				}
			}catch(ClassNotFoundException e){
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
		
		public Response handleObject(Request request){
			String interfaceClassName= request.getClassName();
			try{
				Class<?> interfaceClazz = Class.forName(interfaceClassName);
				return new Response(interfaceClazz.newInstance());
			}catch(ClassNotFoundException e){
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
	public String getHost() {
		return providerAddress.getHost();
	}
	public void setHost(String host) {
		providerAddress.setHost(host);
	}
	public int getPort() {
		return providerAddress.getPort();
	}
	public void setPort(int port) {
		providerAddress.setPort(port);
	}
	public String getServiceName() {
		return metadata.getServiceName();
	}
	public void setServiceName(String serviceName) {
		metadata.setServiceName(serviceName);
	}

	public String getServiceClass() {
		return metadata.getServiceClass();
	}
	public void setServiceClass(String serviceClass) {
		metadata.setServiceClass(serviceClass);
	}
}

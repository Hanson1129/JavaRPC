package project.lanshan.JavaRPC.Consumer.Netty;

import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;

import project.lanshan.JavaRPC.Provider.Netty.NettyProvider;
import project.lanshan.JavaRPC.model.RPCInetAddress;
import project.lanshan.JavaRPC.model.Request;
import project.lanshan.JavaRPC.model.Response;
import project.lanshan.JavaRPC.model.ServiceMetadata;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

public class NettyConsumer implements Consumer,InitializingBean{
	private static Logger log = Logger.getLogger(NettyProvider.class.getName());

	private Request request;
	private ServiceMetadata metadata;
	private Object object;
	private RPCInetAddress providerAddress;
	private AtomicBoolean hasObject;


	@Override
	public void afterPropertiesSet() throws Exception {
		hasObject.lazySet(false);
	}

	public NettyConsumer(String serviceName,String serviceClass,String host,int port){
		metadata.setServiceName(serviceName);
		metadata.setServiceClass(serviceClass);
		providerAddress.setHost(host);
		providerAddress.setPort(port);
	}

	
	public void startCall() throws InterruptedException {
	
		EventLoopGroup workerGroup = new NioEventLoopGroup();

		try {
			Bootstrap bootstrap = new Bootstrap();
			bootstrap.group(workerGroup);
			bootstrap.channel(NioSocketChannel.class);
			bootstrap.handler(new ChannelInitializer<SocketChannel>() {

				@Override
				public void initChannel(SocketChannel ch) throws Exception {
					ch.pipeline().addLast(
							new ObjectEncoder(),
							new ObjectDecoder(ClassResolvers
									.cacheDisabled(null)),
							new ReceiveObjectHandler());
				}
			});

			bootstrap.connect(providerAddress.getHost(),providerAddress.getPort()).sync().channel().closeFuture().sync();
		} finally {
			workerGroup.shutdownGracefully();
		}
	}



	private class ReceiveObjectHandler extends ChannelHandlerAdapter {

		@Override
		public void channelActive(ChannelHandlerContext ctx) {
			ctx.write(request);
		}

		@Override
		public void channelRead(ChannelHandlerContext ctx, Object msg) {
			Response response = (Response)msg;
			object = response.getResponseObject();
			ctx.close();
		}

		@Override
		public void channelReadComplete(ChannelHandlerContext ctx)
				throws Exception {
			ctx.close();
		}

		@Override
		public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
			cause.printStackTrace();
			ctx.close();
		}
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


	public Object getObject(){
		if(hasObject.compareAndSet(false, true))
			try {
				if(checkOutRequest())
					startCall();
				else{
					log.error("hasn't finish request.");
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
				log.error("connect fail.");
			}
		return object;
	}

	private boolean checkOutRequest() {
		if(request.getParametersObject() == null || request.getParametersClass() == null)
			return false;
		if(request.getClassName() == null)
			request.setClassName(metadata.getServiceClass());
		if(request.getServiceName() == null)
			request.setServiceName(metadata.getServiceName());
		return true;
	}

	public void setObject(Object object) {
		this.object = object;
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

	@Override
	public void setRequest(Request request) {
		this.request = request;
	}

	@Override
	public String getClassName() {
		return getServiceClass();
	}


}

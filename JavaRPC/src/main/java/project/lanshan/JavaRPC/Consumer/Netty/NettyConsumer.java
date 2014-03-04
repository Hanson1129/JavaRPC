package project.lanshan.JavaRPC.Consumer.Netty;

import org.apache.log4j.Logger;

import project.lanshan.JavaRPC.Provider.Netty.NettyProvider;
import project.lanshan.JavaRPC.model.RPCInetAddress;
import project.lanshan.JavaRPC.model.Request;
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

public class NettyConsumer {
	private static Logger log = Logger.getLogger(NettyProvider.class.getName());

	private Request request;
	private ServiceMetadata metadata;
	private Object object;
	private final RPCInetAddress providerAddress;

	public NettyConsumer(){
		metadata = new ServiceMetadata();
		providerAddress = new RPCInetAddress();
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
			
		}

		@Override
		public void channelRead(ChannelHandlerContext ctx, Object msg) {

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


	public Object getObject() {
		return object;
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
	public Request getRequest() {
		return request;
	}

	public void setRequest(Request request) {
		this.request = request;
	}
}

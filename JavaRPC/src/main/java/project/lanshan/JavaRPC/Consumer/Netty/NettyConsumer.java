package project.lanshan.javarpc.consumer.netty;

import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import project.lanshan.javarpc.model.RPCInetAddress;
import project.lanshan.javarpc.model.Request;
import project.lanshan.javarpc.model.Response;
import project.lanshan.javarpc.model.ServiceMetadata;
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

@Service
public class NettyConsumer implements Consumer {
	private static Logger log = Logger.getLogger(NettyConsumer.class.getName());

	private Request request;
	private Class<?> objectClazz;
	private ServiceMetadata metadata;
	private Object object = null;
	private AtomicBoolean hasObject;

	public NettyConsumer() {
		request = new Request();
		metadata = new ServiceMetadata();
		hasObject = new AtomicBoolean(false);
	}

	public void startCall(RPCInetAddress address) throws InterruptedException {

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

			bootstrap.connect(address.getHost(), address.getPort()).sync()
					.channel().closeFuture().sync();
		} finally {
			workerGroup.shutdownGracefully();
		}
	}

	private class ReceiveObjectHandler extends ChannelHandlerAdapter {

		@Override
		public void channelActive(ChannelHandlerContext ctx) {
			if (checkOutRequest())
				ctx.writeAndFlush(request);
		}

		@Override
		public void channelRead(ChannelHandlerContext ctx, Object msg) {
			Response response = (Response) msg;
			object = response.getResponseObject();
			objectClazz = response.getObjectClzss();
			hasObject.compareAndSet(false, true);
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

	public String getInterfaceName() {
		return metadata.getInterfaceName();
	}


	public Object getObject(RPCInetAddress address) {
		if (hasObject.compareAndSet(false, true))
			try {
				if (checkOutRequest())
					startCall(address);
				else {
					log.error("hasn't finish request.");
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
				log.error("connect fail.");
			}
		return object;
	}

	private boolean checkOutRequest() {

		if (request.getInterfaceName() == null)
			request.setInterfaceName(metadata.getInterfaceName());
		if (request.getServiceName() == null)
			request.setServiceName(metadata.getServiceName());
		return true;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	public String getHost() {
		return metadata.getHost();
	}

	public void setHost(String host) {
		metadata.setHost(host);
	}

	public int getPort() {
		return metadata.getPort();
	}

	public void setPort(int port) {
		metadata.setPort(port);
	}

	@Override
	public void setRequest(Request request) {
		this.request = request;
	}

	@Override
	public Class<?> getObjectType() {
		if (object == null)
			return null;
		else
			return objectClazz;
	}

	@Override
	public ServiceMetadata getMetadata() {
		return metadata;
	}

	@Override
	public void setMetadata(ServiceMetadata metadata) {
		this.metadata = metadata;
	}

}

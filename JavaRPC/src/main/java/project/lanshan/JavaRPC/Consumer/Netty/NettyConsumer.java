package project.lanshan.javarpc.consumer.netty;

import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

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
public class NettyConsumer implements Consumer{
	private static Logger log = Logger.getLogger(NettyConsumer.class.getName());
	

	private Request request;
	private ServiceMetadata metadata;
	private Object object = null;
	private AtomicBoolean hasObject;


	
	public NettyConsumer(){
		metadata = new ServiceMetadata();
		hasObject = new AtomicBoolean(false);
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

			bootstrap.connect(metadata.getHost(),metadata.getPort()).sync().channel().closeFuture().sync();
		} finally {
			workerGroup.shutdownGracefully();
		}
	}



	private class ReceiveObjectHandler extends ChannelHandlerAdapter {

		@Override
		public void channelActive(ChannelHandlerContext ctx) {
			ctx.writeAndFlush(request);
		}

		@Override
		public void channelRead(ChannelHandlerContext ctx, Object msg) {
			Response response = (Response)msg;
			object = response.getResponseObject();
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
		
		if(request.getClassName().length() == 0 )
			request.setClassName(metadata.getServiceClass());
		if(request.getServiceName().length() == 0)
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
    if(object != null)
      return object.getClass();
    else
      return null;
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

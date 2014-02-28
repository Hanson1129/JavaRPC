package project.lanshan.JavaRPC.Provider.Netty;

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

public class NettyProvider {
	
	private static Logger log = Logger.getLogger(NettyProvider.class.getName());
	
	private ServiceMetadata metadata;
	
	private final RPCInetAddress providerAddress;
	
	public NettyProvider(ServiceMetadata metadata,RPCInetAddress providerAddress){
		this.metadata = metadata;
		this.providerAddress = providerAddress;
	}

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

	public ServiceMetadata getMetadata() {
		return metadata;
	}

	public void setMetadata(ServiceMetadata metadata) {
		this.metadata = metadata;
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
			if(request.getCallWay().equals("result")){
				handleResult(request);
			}else if(request.getCallWay().equals("object")){
				handlerObject(request);
			}
		}
		
		public Response handleResult(Request request){
			return null;
		}
		
		public Response handlerObject(Request request){
			return null;
		}
	}
}

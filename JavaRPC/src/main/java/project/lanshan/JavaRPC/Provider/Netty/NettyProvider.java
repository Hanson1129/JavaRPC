package project.lanshan.JavaRPC.Provider.Netty;

import org.apache.log4j.Logger;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;


import project.lanshan.JavaRPC.model.RPCInetAddress;

public class NettyProvider {
	
	private static Logger log = Logger.getLogger(NettyProvider.class.getName());
	
	private final RPCInetAddress providerAddress;
	
	public NettyProvider(RPCInetAddress providerAddress){
		this.providerAddress = providerAddress;
	}

	public void startPublish(){
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
			} catch (InterruptedException e) {
				log.error("bind() at "+providerAddress.getPort()+" fail!");
			}
		}finally{
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}
}

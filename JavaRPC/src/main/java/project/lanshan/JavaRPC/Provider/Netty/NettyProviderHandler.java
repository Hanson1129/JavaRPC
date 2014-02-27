package project.lanshan.JavaRPC.Provider.Netty;

import project.lanshan.JavaRPC.model.Request;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class NettyProviderHandler extends ChannelHandlerAdapter{
	
	@Override
	public void channelRead(ChannelHandlerContext ctx,Object msg){
		Request request = (Request)msg;
		if(request.getCallWay().equals("result")){
			
		}else if(request.getCallWay().equals("object")){
			
		}
	}
}

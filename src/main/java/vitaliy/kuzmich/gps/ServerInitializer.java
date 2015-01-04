package vitaliy.kuzmich.gps;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.concurrent.Future;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import vitaliy.kuzmich.config.ApplicationContextProvider;
import vitaliy.kuzmich.gps.messages.Decoder;
import vitaliy.kuzmich.gps.messages.Encoder;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.net.InetSocketAddress;

@WebListener
public class ServerInitializer implements ServletContextListener {
    EventLoopGroup bossGroup = new NioEventLoopGroup(1);
    EventLoopGroup workerGroup = new NioEventLoopGroup();
    Log log = LogFactory.getLog(ServerInitializer.class);
    int port = 8088;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        log.info("netty initialization run");
        ServerBootstrap b = new ServerBootstrap();
        log.info("GPS socket is running at port:" + port);
        b.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .handler(new LoggingHandler(LogLevel.INFO))
                .childHandler(new ChannelInitializer() {
                    @Override
                    protected void initChannel(Channel channel) throws Exception {
                        ChannelPipeline pipeline = channel.pipeline();
// Enable stream compression (you can remove these two if unnecessary)
                        //  pipeline.addLast(ZlibCodecFactory.newZlibEncoder(ZlibWrapper.GZIP));
                        //  pipeline.addLast(ZlibCodecFactory.newZlibDecoder(ZlibWrapper.GZIP));
// Add the number codec first,
                        pipeline.addLast(new Decoder());
                        pipeline.addLast(new Encoder());
// and then business logic.
// Please note we create a handler for every new channel
// because it has stateful properties.
                        pipeline.addLast(new GPSMessageHandler());
                    }
                });
        b.bind(new InetSocketAddress(
                "192.168.0.219", port));


    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        log.info("netty stopping...");
        Future f1 = bossGroup.shutdownGracefully();
        Future f2 = workerGroup.shutdownGracefully();
        try {
            f1.await();
            f2.await();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}

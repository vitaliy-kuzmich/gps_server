package vitaliy.kuzmich.gps;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import vitaliy.kuzmich.config.ApplicationContextProvider;
import vitaliy.kuzmich.gps.messages.mobile.pojo.Auth;
import vitaliy.kuzmich.gps.messages.mobile.pojo.Position;
import vitaliy.kuzmich.gps.messages.mobile.proto.Message;
import vitaliy.kuzmich.model.PositionModel;
import vitaliy.kuzmich.model.User;
import vitaliy.kuzmich.services.PositionModelService;
import vitaliy.kuzmich.services.UserService;

public class GPSMessageHandler extends SimpleChannelInboundHandler<Message> {
    Log log = LogFactory.getLog(GPSMessageHandler.class);
    ApplicationContext applicationContext = ApplicationContextProvider.getApplicationContext();
    User user;
    Auth auth;
    UserService userService;
    PositionModelService poService;


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Message msg) throws Exception {
        log.info("message accepted code:" + msg.getCode());
        switch (msg.getCode()) {
            case Message.AUTH_MESSAGE:
                Auth a = (Auth) msg.toPojo();
                auth = a;
                if (userService == null) {
                    userService = applicationContext.getBean(UserService.class);
                }

                if (!userService.isValid(auth)) {
                    a.setPassword("authentication failed");
                    ctx.write(a.toMessage());
                    log.error((auth == null ? "" : auth.getUsername()) + " wrong credentials! " + ctx.channel().remoteAddress());
                    throw new Exception(" wrong credentials! " + auth.getUsername());
                } else {
                    ChannelFuture cf = ctx.write(msg);
                    ctx.flush();
                    if (!cf.isSuccess()) {
                        log.error("!!!!!!!!!!!!!!!Send failed: " + cf.cause());
                    }

                    user = userService.getByLogin(auth.getUsername());
                    poService = applicationContext.getBean(PositionModelService.class);
                }
                break;
            case Message.POS_MESSAGE:
                if (user == null)
                    throw new Exception(" not authorized! " + ctx.channel().remoteAddress());
                PositionModel model = new PositionModel((Position) ((Object) msg.toPojo()));
                //model.setSaveTime(System.currentTimeMillis());
                poService.addOrUpdatePos(model, user);
                break;

            default:
                break;
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        log.error(cause);
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);

        log.info("incoming connection accepted " + ctx.channel().remoteAddress());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        log.info((auth == null ? "" : auth.getUsername()) + "client disconnected " + ctx.channel().remoteAddress());
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);

        ctx.flush();
        log.info("flushing " + ctx.channel().remoteAddress());
    }

    public static String humanReadableByteCount(long bytes, boolean si) {
        int unit = si ? 1000 : 1024;
        if (bytes < unit) return bytes + " B";
        int exp = (int) (Math.log(bytes) / Math.log(unit));
        String pre = (si ? "kMGTPE" : "KMGTPE").charAt(exp - 1) + (si ? "" : "i");
        return String.format("%.1f %sB", bytes / Math.pow(unit, exp), pre);
    }
}

package vitaliy.kuzmich.gps.messages;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import vitaliy.kuzmich.gps.messages.mobile.proto.Message;

public class Encoder extends MessageToByteEncoder<Message> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Message message, ByteBuf byteBuf) throws Exception {
        byteBuf.writeBytes(message.toBytes());
    }
}

package vitaliy.kuzmich.gps.messages.mobile.proto;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import vitaliy.kuzmich.gps.messages.AM;
import vitaliy.kuzmich.gps.messages.mobile.pojo.APMesg;

import java.nio.ByteBuffer;


public abstract class Message implements AM {
    protected ByteBuffer buffer;
    private int len;
    private int code;
    protected Log log = LogFactory.getLog(getClass());

    public Message(ByteBuffer buffer, int len, int code, int requestId) {
        this.buffer = buffer;
        this.len = len;
        this.code = code;
        this.requestId = requestId;
    }

    private int requestId;

    public Message(AM a) {
        if (a != null) {
            setLen(a.getLen());
            setCode(a.getCode());
            setRequestId(a.getRequestId());
        }
    }

    public int getLen() {
        return len;
    }

    public void setLen(int len) {
        this.len = len;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public Message(byte[] buf) {
        buffer = ByteBuffer.wrap(buf);
    }

    public static final int AUTH_MESSAGE = 1;
    public static final int POS_MESSAGE = 2;
    public static final int SETTINGS_MESSAGE = 3;
    public abstract APMesg toPojo();

    public byte[] toBytes() {
        return toBuffer().array();
    }

    public ByteBuffer toBuffer() {
        ByteBuffer res = ByteBuffer.allocate(len+12);
        res.putInt(len);
        res.putInt(getRequestId());
        res.putInt(getCode());
        res.put(buffer.array());
        res.flip();
        return res;
    }

}

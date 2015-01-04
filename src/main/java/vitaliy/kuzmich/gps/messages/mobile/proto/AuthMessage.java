package vitaliy.kuzmich.gps.messages.mobile.proto;

import vitaliy.kuzmich.gps.messages.AM;
import vitaliy.kuzmich.gps.messages.mobile.pojo.APMesg;
import vitaliy.kuzmich.gps.messages.mobile.pojo.Auth;

import java.nio.ByteBuffer;

public class AuthMessage extends Message {
    public AuthMessage(ByteBuffer buffer, int len, int code, int requestId) {
        super(buffer, len, code, requestId);
    }


    public AuthMessage(AM am) {
        super(am);
        Auth a = (Auth) am;
        buffer = ByteBuffer.wrap((a.getUsername() + ":" + a.getPassword())
                .getBytes());
    }

    @Override
    public Auth toPojo() {
        Auth res = null;
        try {
            String[] arr = new String(buffer.array(), "UTF-8").split(":");
            res = new Auth(this);
            res.setPassword(arr[1]);
            res.setUsername(arr[0]);

        } catch (Exception e) {
            log.error(e);
        }

        return res;
    }

}

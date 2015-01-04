package vitaliy.kuzmich.gps.messages.mobile.pojo;

import vitaliy.kuzmich.gps.messages.AM;
import vitaliy.kuzmich.gps.messages.mobile.proto.AuthMessage;
import vitaliy.kuzmich.gps.messages.mobile.proto.Message;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;


public class Auth extends APMesg {
    private String username;
    private String password;

    public Auth(AM am) {
        super(am);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
/*

    @Override
    public void readExternal(ObjectInput arg0) throws IOException,
            ClassNotFoundException {
        setLen(arg0.readInt());
        setRequestId(arg0.readInt());
        setCode(arg0.readInt());
        setUsername(arg0.readLine());
        setPassword(arg0.readLine());

    }

    @Override
    public void writeExternal(ObjectOutput arg0) throws IOException {
        arg0.writeInt(getLen());
        arg0.writeInt(getRequestId());
        arg0.writeInt(getCode());
        arg0.writeChars(getUsername());
        arg0.writeChars("\r\n");
        arg0.writeChars(getPassword());
    }
*/

    @Override
    public Message toMessage() {
        return new AuthMessage(this);
    }
}

package vitaliy.kuzmich.gps.messages.mobile.proto;

import vitaliy.kuzmich.gps.messages.AM;
import vitaliy.kuzmich.gps.messages.mobile.pojo.APMesg;
import vitaliy.kuzmich.gps.messages.mobile.pojo.Position;

import java.nio.ByteBuffer;

public class PositionMessage extends Message {
    public PositionMessage(ByteBuffer buffer, int len, int code, int requestId) {
        //len = 44
        super(buffer, len, code, requestId);
    }

    public PositionMessage(AM am) {
        super(am);
        Position a = (Position) am;
        buffer = ByteBuffer.allocate(getLen());

        buffer.putFloat(a.getAccuracy());
        buffer.putDouble(a.getAltitude());
        buffer.putDouble(a.getLatitude());
        buffer.putDouble(a.getLongitude());
        buffer.putFloat(a.getSpeed());
        buffer.putFloat(a.getBearing());
        buffer.putLong(a.getCreationTime());

    }

    @Override
    public Position toPojo() {
        Position pos = new Position(this);
        pos.setAccuracy(buffer.getFloat());
        pos.setAltitude(buffer.getDouble());
        pos.setLatitude(buffer.getDouble());
        pos.setLongitude(buffer.getDouble());
        pos.setSpeed(buffer.getFloat());
        pos.setBearing(buffer.getFloat());
        pos.setCreationTime(buffer.getLong());

        return pos;
    }
}

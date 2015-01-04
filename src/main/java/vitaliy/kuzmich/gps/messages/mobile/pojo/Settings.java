package vitaliy.kuzmich.gps.messages.mobile.pojo;

import vitaliy.kuzmich.gps.messages.AM;
import vitaliy.kuzmich.gps.messages.mobile.proto.Message;
import vitaliy.kuzmich.gps.messages.mobile.proto.SettingsMessage;

public class Settings extends APMesg {
    private float gpsMinChangeDistance;
    private long gpsMinInterval;
    private int socketConnectionTimeOut;
    private String serverAddress;
    private int serverPort;

    public String getServerAddress() {
        return serverAddress;
    }

    public void setServerAddress(String serverAddress) {
        setLen(getLen() + serverAddress.getBytes().length);
        this.serverAddress = serverAddress;
    }


    public int getServerPort() {
        return serverPort;
    }

    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }

    public int getSocketConnectionTimeOut() {
        return socketConnectionTimeOut;
    }

    public void setSocketConnectionTimeOut(int socketConnectionTimeOut) {
        this.socketConnectionTimeOut = socketConnectionTimeOut;
    }

    public float getGpsMinChangeDistance() {
        return gpsMinChangeDistance;
    }

    public void setGpsMinChangeDistance(float gpsMinChangeDistance) {
        this.gpsMinChangeDistance = gpsMinChangeDistance;
    }

    public long getGpsMinInterval() {
        return gpsMinInterval;
    }

    public void setGpsMinInterval(long gpsMinInterval) {
        this.gpsMinInterval = gpsMinInterval;
    }

    public Settings(AM am) {
        super(am);
    }


    @Override
    public SettingsMessage toMessage() {
        return new SettingsMessage(this);
    }

}

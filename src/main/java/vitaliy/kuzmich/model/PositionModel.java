package vitaliy.kuzmich.model;

import vitaliy.kuzmich.gps.messages.mobile.pojo.Position;

import javax.persistence.*;
import java.io.Serializable;

/**
 * DO NOT MODIFY THIS TABLE NAME! see PersistenceConfig.class#sessionFactory
 */
@Entity
public class PositionModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @SequenceGenerator(name = "id")
    private Long id;
    @Column(unique = true)
    private Long saveTime;
    private float accuracy;
    private double altitude;
    private double latitude;
    private double longitude;
    private float speed;
    private float bearing;


    public PositionModel() {

    }

    public PositionModel(Position pos) {
        setAccuracy(pos.getAccuracy());
        setAltitude(pos.getAltitude());
        setLatitude(pos.getLatitude());
        setLongitude(pos.getLongitude());
        setSpeed(pos.getSpeed());
        setBearing(pos.getBearing());
        setSaveTime(pos.getCreationTime());

    }

    public Long getSaveTime() {
        return saveTime;
    }

    public void setSaveTime(Long saveTime) {
        this.saveTime = saveTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public float getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(float accuracy) {
        this.accuracy = accuracy;
    }

    public double getAltitude() {
        return altitude;
    }

    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getBearing() {
        return bearing;
    }

    public void setBearing(float bearing) {
        this.bearing = bearing;
    }


}

package com.tryon.xuanhong.tryon;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

/**
 * Created by Pinky on 13-Jun-17.
 */

public class Glasses implements Serializable {

    @Expose
    private int Bridge;
    @Expose
    private String Color;
    @Expose
    private int Eye;
    @Expose
    private String Id;
    @Expose
    private String Name;
    @Expose
    private  Float Price;
    @Expose
    private  String Producer;
    @Expose
    private  String Status;
    @Expose
    private int Temple;
    @Expose
    private String Thumnail;

    public int getBridge() {
        return Bridge;
    }

    public void setBridge(int bridge) {
        Bridge = bridge;
    }

    public String getColor() {
        return Color;
    }

    public void setColor(String color) {
        Color = color;
    }

    public int getEye() {
        return Eye;
    }

    public void setEye(int eye) {
        Eye = eye;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Float getPrice() {
        return Price;
    }

    public void setPrice(Float price) {
        Price = price;
    }

    public String getProducer() {
        return Producer;
    }

    public void setProducer(String producer) {
        Producer = producer;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public int getTemple() {
        return Temple;
    }

    public void setTemple(int temple) {
        Temple = temple;
    }

    public String getThumnail() { return Thumnail;    }

    public void setThumnail(String thumnail) {
        Thumnail = thumnail;
    }
}

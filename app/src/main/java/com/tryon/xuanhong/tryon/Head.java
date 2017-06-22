package com.tryon.xuanhong.tryon;

/**
 * Created by Pinky on 21-Jun-17.
 */

public class Head {

    int Id;
    String Mtl;
    String Obj;
    String Png1;
    String Png2;
    boolean Used;
    int UserId;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getMtl() {
        return Mtl;
    }

    public void setMtl(String mtl) {
        Mtl = mtl;
    }

    public String getObj() {
        return Obj;
    }

    public void setObj(String obj) {
        Obj = obj;
    }

    public String getPng1() {
        return Png1;
    }

    public void setPng1(String png1) {
        Png1 = png1;
    }

    public String getPng2() {
        return Png2;
    }

    public void setPng2(String png2) {
        Png2 = png2;
    }

    public boolean isUsed() {
        return Used;
    }

    public void setUsed(boolean used) {
        Used = used;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

}

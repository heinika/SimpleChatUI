package com.lingzhuo.simplechatuitest.moudel;

/**
 * Created by heinika on 2015/8/28.
 */
public class Face {
    private int img;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Face(int img, String name) {

        this.img = img;
        this.name = name;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }




}

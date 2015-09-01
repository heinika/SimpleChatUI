package com.lingzhuo.simplechatuitest.moudel;

/**
 * Created by heinika on 2015/8/27.
 */
public class Message {
    private String massageContent;
    private boolean isSend;

    public Message(int type, String massageContent) {
        this.type = type;
        this.massageContent = massageContent;
    }

    public int getType() {

        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    private int type;
    private String time;

    public Message(){}
    public void setMassageContent(String massageContent) {
        this.massageContent = massageContent;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Message(String massageContent, boolean isSend) {
        this.massageContent = massageContent;
        this.isSend = isSend;
    }

    public boolean isSend() {
        return isSend;
    }

    public void setIsSend(boolean isSend) {
        this.isSend = isSend;
    }

    public String getMassageContent() {
        return massageContent;
    }


}

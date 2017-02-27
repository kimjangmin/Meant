package com.mean.demo.getset;

/**
 * Created by CBR on 2016-01-17.
 */
public class Comment_value {
    private String id,year,month,day,msg,comment_num,is_reple,nick;

    public String getis_reple() {
        return is_reple;
    }

    public void setcomment_num(String comment_num) {
        this.comment_num = comment_num;
    }
    public String getcomment_num() {
        return comment_num;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public void setis_reple(String is_reple) {
        this.is_reple = is_reple;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}

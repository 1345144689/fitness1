package com.example.fitness.fellow;

/**
 * 会员类
 */
public class fellow{


    private String membercno;
    private String membername;
    private String membersex;
    private String membertel;
    private String memcoursecno;
    private String coursetimes;
    private String purchasetime;



    public fellow(String membercno, String membername, String membersex, String membertel, String memcoursecno, String coursetimes, String purchasetime) {
        this.membercno = membercno;
        this.membername = membername;
        this.membersex = membersex;
        this.membertel = membertel;
        this.memcoursecno = memcoursecno;
        this.coursetimes = coursetimes;
        this.purchasetime = purchasetime;
    }

    public fellow() {
    }

    public String getMembercno() {
        return membercno;
    }

    public void setMembercno(String membercno) {
        this.membercno = membercno;
    }

    public String getMembername() {
        return membername;
    }

    public void setMembername(String membername) {
        this.membername = membername;
    }

    public String getMembersex() {
        return membersex;
    }

    public void setMembersex(String membersex) {
        this.membersex = membersex;
    }

    public String getMembertel() {
        return membertel;
    }

    public void setMembertel(String membertel) {
        this.membertel = membertel;
    }

    public String getMemcoursecno() {
        return memcoursecno;
    }

    public void setMemcoursecno(String memcoursecno) {
        this.memcoursecno = memcoursecno;
    }

    public String getCoursetimes() {
        return coursetimes;
    }

    public void setCoursetimes(String coursetimes) {
        this.coursetimes = coursetimes;
    }

    public String getPurchasetime() {
        return purchasetime;
    }

    public void setPurchasetime(String purchasetime) {
        this.purchasetime = purchasetime;
    }

    @Override
    public String toString() {
        return "fellow{" +
                "membercno='" + membercno + '\'' +
                ", membername='" + membername + '\'' +
                ", membersex='" + membersex + '\'' +
                ", membertel='" + membertel + '\'' +
                ", memcoursecno='" + memcoursecno + '\'' +
                ", coursetimes='" + coursetimes + '\'' +
                ", purchasetime='" + purchasetime + '\'' +
                '}';
    }
}

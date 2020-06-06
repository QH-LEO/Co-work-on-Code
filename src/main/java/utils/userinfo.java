package utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @Author: Hao Qin
 * @Date: 19-12-4  上午12:05
 * @Version 1.0
 */
public class userinfo {
    private String title;

    public int[] getTcss() {
        return tcss;
    }

    public void setTcss(int[] tcss) {
        this.tcss = tcss;
    }

    public int[] tcss;

    public int[] getTdss() {
        return tdss;
    }

    public void setTdss(int[] tdss) {
        this.tdss = tdss;
    }

    public int[] tdss;


    public List getTcs() {
        return tcs;
    }

    public void setTcs(List tcs) {
        this.tcs = tcs;
    }

    private List tcs;


    public List getTds() {
        return tds;
    }

    public void setTds(List tds) {
        this.tds = tds;
    }

    private List tds;





    public String ts;

    public String getTs() {
        return ts;
    }

    public void setTs(String ts) {
        this.ts = ts;
    }
        private int td;
private int tc;

    public int getTc() {
        return tc;
    }

    public void setTc(int tc) {
        this.tc = tc;
    }

    public int getTd() {
        return td;
    }

    public void setTd(int td) {
        this.td = td;
    }

    private String pc;
    private int code;
    private String pho;
    private  int rs;
    private String uname;
    private String area;
    private String pname;
    private String msg;
    private String onchat;
    private String ptime;
    private String pd;
    private int love;
    public String[] onchats;
    public String[] time;
    public String[] unames;

    public String[] getPks() {
        return pks;
    }

    public void setPks(String[] pks) {
        this.pks = pks;
    }

    public String[] pks;
    public userinfo[] getInfo() {
        return info;
    }

    public void setInfo(userinfo[] info) {
        this.info = info;
    }

    public userinfo[] info;

    public String[] getPnames() {
        return pnames;
    }

    public void setPnames(String[] pnames) {
        this.pnames = pnames;
    }

    public String[] pnames;

    public userinfo[] getInfocollection() {
        return infocollection;
    }

    public void setInfocollection(userinfo[] infocollection) {
        this.infocollection = infocollection;
    }

    public userinfo[] infocollection;
    public userinfo(){

    }


    public String getMsg() {

        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getPho() {
        return pho;
    }

    public void setPho(String pho) {
        this.pho = pho;
    }


    public int getRs() {
        return rs;
    }

    public void setRs(int rs) {
        this.rs = rs;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getOnchat() {
        return onchat;
    }

    public void setOnchat(String onchat) {
        this.onchat = onchat;
    }

    public String getPtime() {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd :HH:mm:ss");
        String A = dateFormat.format(date);
        return A;
    }

    public void setPtime(String ptime) {
        this.ptime = ptime;
    }

    public String getPd() {
        return pd;
    }

    public void setPd(String pd) {
        this.pd = pd;
    }

    public String[] getOnchats() {
        return onchats;
    }

    public void setOnchats(String[] onchats) {
        this.onchats = onchats;
    }

    public String[] getTime() {
        return time;
    }

    public void setTime(String[] time) {
        this.time = time;
    }

    public String[] getUnames() {
        return unames;
    }

    public void setUnames(String[] unames) {
        this.unames = unames;
    }

    public int getLove() {
        return love;
    }

    public void setLove(int love) {
        this.love = love;
    }
    public String getPc() {
        return pc;
    }

    public void setPc(String pc) {
        this.pc = pc;
    }
}

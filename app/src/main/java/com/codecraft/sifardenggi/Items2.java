package com.codecraft.sifardenggi;

public class Items2 {
    private String negeri;
    private String daerah;
    private String lokaliti;

    Items2(String mNegeri, String mDaerah, String mLokaliti){
        this.negeri = mNegeri;
        this.daerah = mDaerah;
        this.lokaliti = mLokaliti;
    }

    public String getNegeri() {
        return negeri;
    }

    public void setNegeri(String negeri) {
        this.negeri = negeri;
    }

    public String getDaerah() {
        return daerah;
    }

    public void setDaerah(String daerah) {
        this.daerah = daerah;
    }

    public String getLokaliti() {
        return lokaliti;
    }

    public void setLokaliti(String lokaliti) {
        this.lokaliti = lokaliti;
    }

}

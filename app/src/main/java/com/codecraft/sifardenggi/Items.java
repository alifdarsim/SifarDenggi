package com.codecraft.sifardenggi;

public class Items {
    private String state;
    private String daily;
    private String cumulative;

    Items(String mState, String mDaily, String mCumulative){
        this.state = mState;
        this.daily = mDaily;
        this.cumulative = mCumulative;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDaily() {
        return daily;
    }

    public void setDaily(String daily) {
        this.daily = daily;
    }

    public String getCumulative() {
        return cumulative;
    }

    public void setCumulative(String daily) {
        this.cumulative = cumulative;
    }

}

package com.tamara.zivototekrug;

public class Report {
    private String volId;
    private String volEmail;
    private String eldId;
    private String eldEmail;
    private String requestId;
    private String reported;

    public Report(String volId, String volEmail, String eldId, String eldEmail, String requestId, String reported) {
        this.volId = volId;
        this.volEmail = volEmail;
        this.eldId = eldId;
        this.eldEmail = eldEmail;
        this.requestId = requestId;
        this.reported = reported;
    }

    public String getVolId() {
        return volId;
    }

    public String getVolEmail() {
        return volEmail;
    }

    public String getEldId() {
        return eldId;
    }

    public String getEldEmail() {
        return eldEmail;
    }

    public String getRequestId() {
        return requestId;
    }

    public String getReported() {
        return reported;
    }
}

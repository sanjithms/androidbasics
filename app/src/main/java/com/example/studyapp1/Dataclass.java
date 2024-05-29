package com.example.studyapp1;

public class Dataclass {
    public String datatitle,datadescription,datajan,dataimage;

    public Dataclass() {
    }

    public Dataclass(String datatitle, String datadescription, String datajan, String dataimage) {
        this.datatitle = datatitle;
        this.datadescription = datadescription;
        this.datajan = datajan;
        this.dataimage = dataimage;
    }

    public String getDatatitle() {
        return datatitle;
    }

    public void setDatatitle(String datatitle) {
        this.datatitle = datatitle;
    }

    public String getDatadescription() {
        return datadescription;
    }

    public void setDatadescription(String datadescription) {
        this.datadescription = datadescription;
    }

    public String getDatajan() {
        return datajan;
    }

    public void setDatajan(String datajan) {
        this.datajan = datajan;
    }

    public String getDataimage() {
        return dataimage;
    }

    public void setDataimage(String dataimage) {
        this.dataimage = dataimage;
    }
}

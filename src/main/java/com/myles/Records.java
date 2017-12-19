package com.myles;

public class Records {

    private String date;
    private String type;
    private String servicedBy;
    private String parts;
    private String description;

    public Records(String date, String type, String servicedBy, String parts, String description){
        this.date = date;
        this.type = type;
        this.servicedBy = servicedBy;
        this.parts = parts;
        this.description = description;
    }

    @Override
    public String toString(){
        String string = ""; //todo finish string
        return string;
    }
}

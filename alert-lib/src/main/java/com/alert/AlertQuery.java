package com.alert;

public class AlertQuery {

    private String eon;
    private String source;
    private String name;

    public AlertQuery()
    {

    }
    
    public AlertQuery(String eon, String source, String name)
    {
        this.eon = eon;
        this.source = source;
        this.name = name;
    }

    public String getEon()
    {
        return this.eon;
    }

    public void setEon(String eon)
    {
        this.eon = eon;
    }

    public String getSource()
    {
        return this.source;
    }

    public void setSource(String source)
    {
        this.source=source;
    }

    public String getName()
    {
        return this.name;
    }

    public void setName(String name)
    {
        this.name=name;
    }
}

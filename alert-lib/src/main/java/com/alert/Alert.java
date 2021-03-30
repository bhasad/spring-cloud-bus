package com.alert;

public class Alert {

    private String eon;
    private String source;
    private long version;
    private String name;
    private String status;

    

    public Alert()
    {
        
    }

    public Alert(String eon, String source, String name)
    {
        this.eon = eon;
        this.source = source ;
        this.name =  name;
        this.version = 0l;
        this.status =  "false";
    }

    public void setEon(String eon)
    {
        this.eon = eon;
    }

    public String getEon()
    {
        return this.eon;
    }

    public void setSource(String source)
    {
        this.source =source;
    }

    public String getSource()
    {
        return this.source;
    }
    

    public void setVersion(long version)
    {
        this.version = version;
    }

    public long getVersion()
    {
        return this.version;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return this.name;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getStatus()
    {
        return this.status;
    }

    public String getAlertPropertyName()
    {
        return AlertConfigProperties.PREFIX+"."+eon+"."+source+"."+name;
    }

    public String getAlertPropertyValue()
    {
        return status;
    }

}

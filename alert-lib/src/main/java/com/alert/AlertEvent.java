package com.alert;
import org.springframework.cloud.bus.event.RemoteApplicationEvent;

/**
 * Chat Events
 */
public class AlertEvent extends RemoteApplicationEvent {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String message;

    public AlertEvent(){
    }


    public AlertEvent(String msg , String originService,
    String destinationService){
        super(msg, originService, destinationService);

        this.message = msg;
    }

    public void setMessage(String message){
        this.message = message;
    }

    public String getMessage(){
        return this.message;
    }
}
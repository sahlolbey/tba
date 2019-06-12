package com.tba.tbaweb;

import com.tba.common.messages.MessageFactory;
import com.tba.common.messages.MessageMap;
import com.tba.common.messages.MessageTransmitter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashSet;

@RestController
@Scope("session")
public class MainController {
    private static final Logger logger = LoggerFactory.getLogger(MainController.class);
    @Autowired
    MessageTransmitter messageTransmitter;
    private HashSet<String> vehicles = new HashSet<String>();



    @RequestMapping("/create")
    public ResponseEntity<String> createVehicle(@RequestParam String vehicleId, @RequestParam Double startLatitude,@RequestParam Double startLongitude)
    {
        logger.debug("create form submited");
        if(!vehicles.add(vehicleId)){
            return ResponseEntity.ok().body("Vehicle name already exists! not created");
        }
        MessageMap message = MessageFactory.createVehicleCreateMessage(vehicleId, startLatitude, startLongitude);
        messageTransmitter.sendMessage(message);
        return ResponseEntity.ok().body("successfully created");
    }

    /**
     * This method triggers on start action of web form and sends a Movement command to vehicle at server
     * side
     * @param vehicleId
     * @param speed :speed in KH
     * @param period: period of time in seconds
     * @param direction : angle to horizon in degree
     * @return
     */
    @RequestMapping("/start")
    public ResponseEntity<String> start(@RequestParam String vehicleId,
                        @RequestParam Double speed,
                        @RequestParam Integer period,
                        @RequestParam Double direction)
    {
        logger.debug("start form submitted");
        MessageMap messageMap = MessageFactory.createMovementMessage(vehicleId,speed,period,direction);
        messageTransmitter.sendMessage(messageMap);
        return ResponseEntity.ok().body("Vehicle Started");
    }


    @RequestMapping("/vehicles")
    public ResponseEntity<HashSet<String>> getVehicles() {
        return ResponseEntity.ok().body(vehicles);
    }

    @RequestMapping("/restart")
    public void restart(HttpSession session){
        session.invalidate();
        return;
    }
}

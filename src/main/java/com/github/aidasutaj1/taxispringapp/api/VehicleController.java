package com.github.aidasutaj1.taxispringapp.api;

import com.github.aidasutaj1.taxispringapp.dto.Signal;
import com.github.aidasutaj1.taxispringapp.service.VehicleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/vehicle")
public class VehicleController {

    private static final Logger log = LoggerFactory.getLogger(VehicleController.class);

    @Autowired
    private VehicleService vehicleService;

    @PostMapping("/send")
    public ResponseEntity<String> acceptSignal(@Valid @RequestBody Signal signal) {
        vehicleService.sendSignalToTopic(signal);
        return ResponseEntity.ok("Signal sent to the topic");
    }

}

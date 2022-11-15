package com.github.aidasutaj1.taxispringapp.api;

import com.github.aidasutaj1.taxispringapp.dto.Signal;
import com.github.aidasutaj1.taxispringapp.service.VechileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.*;
import javax.validation.Valid;

@RestController
@RequestMapping("/vechile")
public class VechileController {

    private static final Logger log = LoggerFactory.getLogger(VechileController.class);

    @Autowired
    private VechileService vechileService;

    @PostMapping("/send")
    public ResponseEntity<String> acceptSignal(@Valid @RequestBody Signal signal) {
        vechileService.sendSignalToTopic(signal);
        return ResponseEntity.ok("Signal sent to the topic");
    }

}

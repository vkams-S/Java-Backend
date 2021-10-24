package com.kafka.kafkaDEMO.resources;


import com.kafka.kafkaDEMO.KafkaServiceImpl;
import com.kafka.kafkaDEMO.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controllers {
    @Autowired
    KafkaServiceImpl kafkaService;
    @PostMapping("/message")
    void publishMessage(@RequestBody Message message){
        kafkaService.publish(message);
    }
}

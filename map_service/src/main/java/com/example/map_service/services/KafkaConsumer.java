package com.example.map_service.services;

import com.example.map_service.data.models.RestaurantDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor
public class KafkaConsumer {
    private final SimpMessagingTemplate messagingTemplate;
    private final MapServices mapServices;


    @KafkaListener(topics = "restaurant_topic2",
            groupId = "group_id",
            containerFactory = "concurrentKafkaListenerContainerFactory")
    public void consume(RestaurantDTO restaurant) {
        messagingTemplate.convertAndSend("/topic/map", mapServices.createMapFromRestaurant(restaurant));
    }

    @KafkaListener(topics = "events_topic",
            groupId = "group_id",
            containerFactory = "concurrentKafkaListenerContainerFactoryEvents")
    public void consume(String message) {
        messagingTemplate.convertAndSend("/topic/event", message);
    }

    @KafkaListener(topics = "events_failed_topic",
            groupId = "group_id",
            containerFactory = "concurrentKafkaListenerContainerFactoryEvents")
    public void consumeFailed(String message) {
        messagingTemplate.convertAndSend("/topic/event/failed", message);
    }
}
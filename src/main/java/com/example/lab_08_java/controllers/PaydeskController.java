package com.example.lab_08_java.controllers;


import com.example.lab_08_java.data.Restaurant;
import com.example.lab_08_java.models.other.QueueRequest;
import com.example.lab_08_java.models.paydesks.UpdatePaydeskRequest;
import com.example.lab_08_java.services.PaydeskServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/paydesk")
public class PaydeskController {
    private final PaydeskServices paydeskServices;
    private final Restaurant restaurant;


    @PostMapping("/update")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> makePaydeskAvailable(
            @RequestBody UpdatePaydeskRequest updatePaydeskRequest){
        paydeskServices.updatePaydesk(updatePaydeskRequest, restaurant);
        return ResponseEntity
                .ok("Paydesk "  + updatePaydeskRequest.getPaydeskIndex()
                        + "was updated to " + updatePaydeskRequest.getAvailability().name() + " successfully");
    }

//    @PostMapping("/queue/stand")
//    public ResponseEntity<?> standToQueueRequest(
//            @RequestBody QueueRequest standToQueueRequest){
//        paydeskServices.standToQueue(standToQueueRequest, restaurant);
//        return ResponseEntity
//                .ok("Paydesk queue was updated"  + standToQueueRequest);
//    }
//    @PostMapping("/queue/leave")
//    public ResponseEntity<?> leaveFromQueueRequest(
//            @RequestBody QueueRequest standToQueueRequest){
//        paydeskServices.leaveFromQueue(standToQueueRequest, restaurant);
//        return ResponseEntity
//                .ok("Paydesk queue was updated"  + standToQueueRequest);
//    }
}

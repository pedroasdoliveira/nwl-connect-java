package br.com.nwl.Events.controllers;

import br.com.nwl.Events.dto.ErrorMessage;
import br.com.nwl.Events.dto.SubscriptionRakingItem;
import br.com.nwl.Events.dto.SubscriptionResponse;
import br.com.nwl.Events.exceptions.EventNotFoundException;
import br.com.nwl.Events.exceptions.SubscriptionConflictException;
import br.com.nwl.Events.exceptions.UserIndicadorNotFoundException;
import br.com.nwl.Events.models.Subscription;
import br.com.nwl.Events.models.User;
import br.com.nwl.Events.services.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SubscriptionController {
    @Autowired
    private SubscriptionService subscriptionService;

    @PostMapping({"/subscription/{prettyName}", "/subscription/{prettyName}/{userId}"})
    public ResponseEntity<?> createSubscription(
            @PathVariable String prettyName,
            @RequestBody User subscriber,
            @PathVariable(required = false) Integer userId)
    {
        try {
            SubscriptionResponse response = subscriptionService.createNewSubscription(prettyName, subscriber, userId);

            if (response != null) {
                return ResponseEntity.ok().body(response);
            }
        } catch (EventNotFoundException | UserIndicadorNotFoundException ex) {
            return ResponseEntity.status(404).body(new ErrorMessage(ex.getMessage()));
        } catch (SubscriptionConflictException ex) {
            return ResponseEntity.status(409).body(new ErrorMessage(ex.getMessage()));
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/subscription/{prettyName}/ranking")
    public ResponseEntity<?> generateRankingByEvent(@PathVariable String prettyName) {
        try {
            List<SubscriptionRakingItem> subsRanking = subscriptionService.getCompleteRanking(prettyName);
            return ResponseEntity.ok().body(subsRanking);
        } catch (EventNotFoundException ex) {
            return ResponseEntity.status(404).body(new ErrorMessage(ex.getMessage()));
        }
    }

    @GetMapping("/subscription/{prettyName}/ranking/{userId}")
    public ResponseEntity<?> generateRankingByEventAndUser(@PathVariable String prettyName,
                                                           @PathVariable Integer userId) {
        try {
            return ResponseEntity.ok(subscriptionService.getRakingByUser(prettyName, userId));
        } catch (Exception ex) {
            return ResponseEntity.status(404).body(new ErrorMessage(ex.getMessage()));
        }
    }
}

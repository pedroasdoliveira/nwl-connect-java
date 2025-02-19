package br.com.nwl.Events.controllers;

import br.com.nwl.Events.dto.ErrorMessage;
import br.com.nwl.Events.dto.SubscriptionResponse;
import br.com.nwl.Events.exceptions.EventNotFoundException;
import br.com.nwl.Events.exceptions.SubscriptionConflictException;
import br.com.nwl.Events.exceptions.UserIndicadorNotFoundException;
import br.com.nwl.Events.models.Subscription;
import br.com.nwl.Events.models.User;
import br.com.nwl.Events.services.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
}

package br.com.nwl.Events.repositories;

import br.com.nwl.Events.models.Event;
import br.com.nwl.Events.models.Subscription;
import br.com.nwl.Events.models.User;
import org.springframework.data.repository.CrudRepository;

public interface SubscriptionRepository extends CrudRepository<Subscription, Integer> {
    public Subscription findByEventAndSubscriber(Event event, User user);
}

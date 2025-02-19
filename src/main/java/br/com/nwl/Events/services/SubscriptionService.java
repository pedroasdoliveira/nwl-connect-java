package br.com.nwl.Events.services;

import br.com.nwl.Events.dto.SubscriptionResponse;
import br.com.nwl.Events.exceptions.EventNotFoundException;
import br.com.nwl.Events.exceptions.SubscriptionConflictException;
import br.com.nwl.Events.exceptions.UserIndicadorNotFoundException;
import br.com.nwl.Events.models.Event;
import br.com.nwl.Events.models.Subscription;
import br.com.nwl.Events.models.User;
import br.com.nwl.Events.repositories.EventRepository;
import br.com.nwl.Events.repositories.SubscriptionRepository;
import br.com.nwl.Events.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubscriptionService {
    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    public SubscriptionResponse createNewSubscription(String eventName, User user, Integer userId) {
        Event event = eventRepository.findByPrettyName(eventName);
        if (event == null) {
            throw new EventNotFoundException("Evento "+eventName+ " não existe no sistema!");
        }

        User userRec = userRepository.findByEmail(user.getEmail());
        if (userRec == null) {
            userRec = userRepository.save(user);
        }

        User indicador = userRepository.findById(userId).orElse(null);
        if (indicador == null) {
            throw new UserIndicadorNotFoundException("Usuário "+userId+ " indicador não existe!");
        }

        Subscription subs = new Subscription();
        subs.setEvent(event);
        subs.setSubscriber(userRec);
        subs.setIndication(indicador);

        Subscription tmpSub = subscriptionRepository.findByEventAndSubscriber(event, userRec);
        if (tmpSub != null) {
            throw new SubscriptionConflictException("Já existe inscrição para o usuário "+user.getName()+ " no evento "+event.getTitle()+ " !");
        }

        Subscription res = subscriptionRepository.save(subs);
        return new SubscriptionResponse(res.getSubscriptionNumber(), "https://eventsCraf.com/subscription/"+res.getEvent().getPrettyName()+"/"+res.getSubscriber().getId());
    }
}

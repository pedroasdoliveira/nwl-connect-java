package br.com.nwl.Events.models;

import jakarta.persistence.*;

@Entity
@Table(name = "tbl_subscription")
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subscription_number")
    private Integer subscriptionNumber;

    @ManyToOne // Multi inscrições para um evento
    @JoinColumn(name = "event_id")
    private Event event;

    @ManyToOne // Multi inscrições para um usuário
    @JoinColumn(name = "subscribed_user_id")
    private User subscriber;

    @ManyToOne
    @JoinColumn(name = "indication_user_id", nullable = true)
    private User indication;

    public Subscription() {}

    public Subscription(Integer subscriptionNumber, Event event, User subscriber, User indication) {
        this.subscriptionNumber = subscriptionNumber;
        this.event = event;
        this.subscriber = subscriber;
        this.indication = indication;
    }

    public Integer getSubscriptionNumber() {
        return subscriptionNumber;
    }

    public void setSubscriptionNumber(Integer subscriptionNumber) {
        this.subscriptionNumber = subscriptionNumber;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public User getSubscriber() {
        return subscriber;
    }

    public void setSubscriber(User subscriber) {
        this.subscriber = subscriber;
    }

    public User getIndication() {
        return indication;
    }

    public void setIndication(User indication) {
        this.indication = indication;
    }
}

package com.fabiogaiera.transactionalrabbitmq.amqp;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Service
public class Initializer {

    @Autowired
    private RabbitAdmin rabbitAdmin;

    @PostConstruct
    protected void init() {

        rabbitAdmin.declareExchange(new DirectExchange("deadletter-exchange", true, false));
        rabbitAdmin.declareQueue(new Queue("deadletter-queue", true, false, false, null));
        rabbitAdmin.declareBinding(new Binding("deadletter-queue", Binding.DestinationType.QUEUE, "deadletter-exchange", "deadletter-routingkey", null));

        Map<String, Object> args = new HashMap<>();
        args.put("x-dead-letter-exchange", "deadletter-exchange");
        args.put("x-dead-letter-routing-key", "deadletter-routingkey");

        rabbitAdmin.declareExchange(new DirectExchange("myexchange", true, false));
        rabbitAdmin.declareQueue(new Queue("myqueue", true, false, true, args));
        rabbitAdmin.declareBinding(new Binding("myqueue", Binding.DestinationType.QUEUE, "myexchange", "myroutingkey", null));

    }

}
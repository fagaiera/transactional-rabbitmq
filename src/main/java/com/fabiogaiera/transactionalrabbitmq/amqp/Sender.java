package com.fabiogaiera.transactionalrabbitmq.amqp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;

@Service
public class Sender {

    private static final Logger logger = LoggerFactory.getLogger(Sender.class);

    private PlatformTransactionManager transactionManager;

    private RabbitTemplate rabbitTemplate;

    @Transactional
    public String sendMessage(String message) {

        logger.info("Sending message: {} with transaction manager: {}", message, transactionManager.getClass().getSimpleName());
        rabbitTemplate.convertAndSend("myexchange", "myroutingkey", message);
        throw new RuntimeException("Runtime Exception Thrown");

    }

    @Autowired
    public void setTransactionManager(PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    @Autowired
    public void setRabbitTemplate(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

}
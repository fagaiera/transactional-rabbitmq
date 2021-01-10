package com.fabiogaiera.transactionalrabbitmq.amqp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;

@Service
@RabbitListener(queues = {"myqueue"})
public class Receiver {

    private static final Logger logger = LoggerFactory.getLogger(Receiver.class);

    private PlatformTransactionManager transactionManager;

    @RabbitHandler
    @Transactional
    public void receiveMessage(byte[] message) {

        String str = new String(message, StandardCharsets.UTF_8);
        logger.info("Receiving message: {} with transaction manager: {}", str, transactionManager.getClass().getSimpleName());
        throw new RuntimeException("Runtime Exception Thrown");

    }

    @Autowired
    public void setTransactionManager(PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

}
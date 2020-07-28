package jku.bac.amqp.cart.com;

import jku.bac.amqp.cart.entity.TransferItem;
import jku.bac.amqp.cart.service.AMQPCartService;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.ArrayList;
import java.util.List;


public class ClientCartCom {

    @Autowired
    AMQPCartService amqpCartService;

    @RabbitListener(queues = "clientcart.item.msg")
    public void receive(TransferItem item) {
        System.out.println("Request came in:" + item.label + " " + item.amount);
        amqpCartService.addToCart(item);
    }

    @RabbitListener(queues = "clientcart.checkout.msg")
    public void receive(String message){
        System.out.println("Message received: " +message);
        this.amqpCartService.checkout(message);
    }

}

package jku.bac.amqp.cart.com;

import jku.bac.amqp.cart.entity.TransferItem;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;

public class CartOfferingCom {

    @Autowired
    @Qualifier(value="offeringTemplate")
    private RabbitTemplate template;
    @Autowired
    @Qualifier(value="cartOfferingQueue")
    private Queue offeringQueue;

    public void sendToOffering(List<TransferItem> itemList){
        if(!itemList.isEmpty()) {
            this.template.convertAndSend(offeringQueue.getName(), itemList);
            System.out.println("Sent item List to Offering Service!");
        }
    }
}

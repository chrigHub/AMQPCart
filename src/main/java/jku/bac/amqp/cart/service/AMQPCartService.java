package jku.bac.amqp.cart.service;

import jku.bac.amqp.cart.com.CartOfferingCom;
import jku.bac.amqp.cart.com.ClientCartCom;
import jku.bac.amqp.cart.entity.TransferItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.crypto.dsig.TransformService;
import java.util.ArrayList;
import java.util.List;

@Service
public class AMQPCartService {
    @Autowired
    CartOfferingCom cartOfferingCom;
    List<TransferItem> itemList = new ArrayList<>();

    public void addToCart(TransferItem item){
        itemList.add(item);
    }

    public void checkout(String message){
        cartOfferingCom.sendToOffering(this.itemList);
        System.out.println("Sent item list to offering!");
        this.itemList = new ArrayList<TransferItem>();
    }
}

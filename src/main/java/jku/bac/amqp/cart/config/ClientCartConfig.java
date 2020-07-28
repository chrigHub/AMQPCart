package jku.bac.amqp.cart.config;

import jku.bac.amqp.cart.com.CartOfferingCom;
import jku.bac.amqp.cart.entity.TransferItem;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import jku.bac.amqp.cart.com.ClientCartCom;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class ClientCartConfig {

    @Bean
    public ClientCartCom clientCartCom(){
        return new ClientCartCom();
    }

    @Bean(name="cartConverter")
    public MessageConverter jsonMessageConverter()
    {
        Jackson2JsonMessageConverter jsonMessageConverter = new Jackson2JsonMessageConverter();
        jsonMessageConverter.setClassMapper(classMapper());
        return jsonMessageConverter;
    }

    @Bean(name="clientTemplate")
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(jsonMessageConverter());
        return template;
    }

    @Bean(name="cartMapper")
    public DefaultClassMapper classMapper(){
        DefaultClassMapper classMapper = new DefaultClassMapper();
        Map<String, Class<?>> idClassMapping = new HashMap<>();
        idClassMapping.put("jku.bac.amqp.Client.entity.TransferItem", TransferItem.class);
        classMapper.setIdClassMapping(idClassMapping);
        return classMapper;
    }
    //==============================================================

    @Bean(name = "cartOfferingQueue")
    public Queue cartOfferingQueue(){
        return new Queue("cartoffering.msg");
    }
    @Bean
    CartOfferingCom cartOfferingCom(){
        return new CartOfferingCom();
    }

    @Bean(name = "offeringTemplate")
    public RabbitTemplate offeringTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(jsonMessageConverter());
        return template;
    }
}

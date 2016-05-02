package com.mdb;

import javax.ejb.MessageDriven;
import javax.ejb.ActivationConfigProperty;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

@MessageDriven(activationConfig = {@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
@ActivationConfigProperty(propertyName = "destination", propertyValue = "java:/queue/test"),
@ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge")})
public class MessegeConsumer implements MessageListener {

	@Override
	public void onMessage(Message message) {
		 TextMessage tm = (TextMessage) message;
	        try {
	            System.out.println("Received message is ==========> " + tm.getText());
	        } catch (JMSException e) {
	 
	            e.printStackTrace();
	        }
		
	}

}

package com.crana.qcontroller.service.txrx.impl;

import com.crana.qcontroller.domain.TxRxMessage;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TxRxMessageBoundaryValueBuilder {
	private String boundaryValue = "---QUADCOPTER---";
	private ObjectMapper objectMapper = new ObjectMapper();

	public String buildTransmissionMessage(TxRxMessage txRxMessage) throws Exception {
		String originalMessage = encrypt(boundaryValue + objectMapper.writeValueAsString(txRxMessage) + boundaryValue);
		txRxMessage.setOriginalMessage(originalMessage);
		return originalMessage;
	}

	private String encrypt(String transmissionMessage) {
		// TODO Auto-generated method stub
		return transmissionMessage;
	}

	public TxRxMessage buildReceivedMessage(String receivedMessage) throws Exception {
		String decyptedMessage = decrypt(receivedMessage);
		String txTxMessageString = removeBoundaryValue(decyptedMessage);
		return objectMapper.readValue(txTxMessageString, TxRxMessage.class);
	}

	private String removeBoundaryValue(String decyptedMessage) {
		int index1 = decyptedMessage.indexOf(boundaryValue) + boundaryValue.length();
		int index2 = decyptedMessage.lastIndexOf(boundaryValue);
		return decyptedMessage.substring(index1, index2);
	}

	private String decrypt(String receivedMessage) {
		// TODO Auto-generated method stub
		return receivedMessage;
	}

}

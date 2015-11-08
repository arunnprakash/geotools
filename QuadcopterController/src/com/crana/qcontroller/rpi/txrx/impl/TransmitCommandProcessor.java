package com.crana.qcontroller.rpi.txrx.impl;

import java.util.LinkedList;
import java.util.Queue;

import com.crana.qcontroller.domain.DeviceConfig;
import com.crana.qcontroller.domain.TxRxMessage;
import com.crana.qcontroller.domain.TxRxMessageBuilder;
import com.crana.qcontroller.service.Command;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TransmitCommandProcessor extends Thread {
	private DeviceConfig myDeviceConfig;
	private Integer messageId = new Integer(1);
	private boolean stopTransmitter = false;
	private Queue<TxRxMessage> messageQueue = new LinkedList<TxRxMessage>();
	private ObjectMapper objectMapper = new ObjectMapper();
	public TransmitCommandProcessor(DeviceConfig myDeviceConfig) {
		this.myDeviceConfig = myDeviceConfig;
	}
	public TxRxMessage getMessage(Command command) throws Exception {
		return process(command);
	}
	private TxRxMessage process(Command command) throws Exception {
		switch(command) {
			case START: {
				return buildMessage(command.getCommandId());
			}
			default: {
				throw new IllegalArgumentException("No Implmentation found in TransmitCommandProcessor for Command::"+command.name());
			}
		}
	}

	private TxRxMessage buildMessage(int commandId) {
		TxRxMessage txRxMessage = TxRxMessageBuilder.txRxMessage()
				.withMessageId(messageId++)
				.withCommandId(commandId)
				.withSender(myDeviceConfig.getDeviceId())
				.withRecipient(myDeviceConfig.getMyNeigbour().getDeviceId())
				.withOriginalSender("")
				.withOriginalRecipient("")
				.withPayload("")
				.build();
		return txRxMessage;
	}
	public void addToMessageProcessorQueue(TxRxMessage message) {
		messageQueue.add(message);
	}
	public boolean isStopTransmitter() {
		return stopTransmitter;
	}
	public void stopTransmitter() {
		this.stopTransmitter = true;
	}
	public void setMessageId(TxRxMessage message) {
		message.setMessageId(messageId++);
	}

}

package com.crana.qcontroller.service.txrx.impl;

import java.util.LinkedList;
import java.util.Queue;

import com.crana.qcontroller.domain.DeviceConfig;
import com.crana.qcontroller.domain.TxRxMessage;
import com.crana.qcontroller.domain.TxRxMessageBuilder;
import com.crana.qcontroller.service.Command;

public class TransmitCommandProcessor extends Thread {
	private DeviceConfig myDeviceConfig;
	private Integer messageId = new Integer(1);
	private boolean stopTransmitter = false;
	private Queue<TxRxMessage> messageQueue = new LinkedList<TxRxMessage>();
	
	public TransmitCommandProcessor(DeviceConfig myDeviceConfig) {
		this.myDeviceConfig = myDeviceConfig;
	}
	public TxRxMessage getMessage(Command command) {
		return process(command);
	}
	private TxRxMessage process(Command command) {
		switch(command) {
			case INVITE: {
				return buildInviteMessage(command.getCommandId());
			}
			case START: {
				return buildMessage(command.getCommandId());
			}
			default: {
				throw new IllegalArgumentException("No Implmentation found in TransmitCommandProcessor with Command::"+command.name());
			}
		}
	}
	private TxRxMessage buildInviteMessage(int commandId) {
		TxRxMessage txRxMessage = TxRxMessageBuilder.txRxMessage()
				.withMessageId(messageId++)
				.withCommandId(commandId)
				.withSender(myDeviceConfig.getDeviceId())
				.withRecipient(null)
				.withOriginalSender(myDeviceConfig.getDeviceId())
				.withOriginalRecipient("")
				.withPayload(null)
				.build();
		return txRxMessage;
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

}

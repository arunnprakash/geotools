package com.crana.qcontroller.service.txrx.impl;

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
			case INVITE: {
				return buildInviteMessage(command.getCommandId());
			}
			case GET_GPS_LOCATION: {
				return buildGetGpsLocationMessage(command.getCommandId());
			}
			case START: {
				return buildMessage(command.getCommandId());
			}
			default: {
				throw new IllegalArgumentException("No Implementation found in TransmitCommandProcessor for Command::"+command.name());
			}
		}
	}
	private TxRxMessage buildInviteMessage(int commandId) throws Exception {
		TxRxMessage txRxMessage = TxRxMessageBuilder.txRxMessage()
				.withMessageId(messageId++)
				.withCommandId(commandId)
				.withSender(myDeviceConfig.getDeviceId())
				.withRecipient(null)
				.withOriginalSender(myDeviceConfig.getDeviceId())
				.withOriginalRecipient(null)
				.withPayload(objectMapper.writeValueAsString(myDeviceConfig))
				.build();
		return txRxMessage;
	}
	private TxRxMessage buildGetGpsLocationMessage(int commandId) {
		TxRxMessage txRxMessage = TxRxMessageBuilder.txRxMessage()
				.withMessageId(messageId++)
				.withCommandId(commandId)
				.withSender(myDeviceConfig.getDeviceId())
				.withRecipient(myDeviceConfig.getMyNeigbour().getDeviceId())
				.withOriginalSender(myDeviceConfig.getDeviceId())
				.withOriginalRecipient("")
				.withPayload("")
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
	public void setMessageId(TxRxMessage message) {
		message.setMessageId(messageId++);
	}
}

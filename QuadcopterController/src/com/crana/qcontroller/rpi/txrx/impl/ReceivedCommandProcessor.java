/**
 * 
 */
package com.crana.qcontroller.rpi.txrx.impl;

import java.util.LinkedList;
import java.util.Queue;

import com.crana.qcontroller.domain.DeviceConfig;
import com.crana.qcontroller.domain.TxRxMessage;
import com.crana.qcontroller.domain.TxRxMessageBuilder;
import com.crana.qcontroller.rpi.txrx.Transmitter;
import com.crana.qcontroller.service.Command;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author ArunPrakash
 *
 */
public class ReceivedCommandProcessor extends Thread {
	private boolean stopReceiver = false;
	private long receiverDelay = 1000;
	private Transmitter transmitter;
	private DeviceConfig myDeviceConfig;
	private Queue<TxRxMessage> messageQueue = new LinkedList<TxRxMessage>();
	private ObjectMapper objectMapper = new ObjectMapper();
	public ReceivedCommandProcessor(DeviceConfig myDeviceConfig, Transmitter transmitter) {
		this.myDeviceConfig = myDeviceConfig;
		this.transmitter = transmitter;
	}
	public void run() {
		try {
			while(!isStopReceiver()) {
				while (!messageQueue.isEmpty()) {
					TxRxMessage message = messageQueue.poll();
					process(message);
				}
				Thread.sleep(receiverDelay);
			}
		} catch (Exception exp) {
			exp.printStackTrace();
		}
	}
	private void process(TxRxMessage message) throws Exception {
		Command command = Command.getCommandByCommandId(message.getCommandId());
		switch(command) {
			case INVITE: {
				transmitter.transmit(buildInviteResponseMessage(message));
				break;
			}
			default: {
				throw new IllegalArgumentException("No Implementation found in ReceivedCommandProcessor with Command::"+command.name());
			}
		}
	}
	private TxRxMessage buildInviteResponseMessage(TxRxMessage receivedMessage) throws Exception {
		TxRxMessage txRxMessage = TxRxMessageBuilder.txRxMessage()
				.withCommandId(Command.INVITE_RESPONSE.getCommandId())
				.withSender(myDeviceConfig.getDeviceId())
				.withRecipient(receivedMessage.getSender())
				.withOriginalSender(myDeviceConfig.getDeviceId())
				.withOriginalRecipient(receivedMessage.getOriginalSender())
				.withPayload(objectMapper.writeValueAsString(myDeviceConfig))
				.build();
		return txRxMessage;
	}
	public void addToMessageProcessorQueue(TxRxMessage message) {
		messageQueue.add(message);
	}
	public boolean isStopReceiver() {
		return stopReceiver;
	}
	public void stopReceiver() {
		this.stopReceiver = true;
	}
}

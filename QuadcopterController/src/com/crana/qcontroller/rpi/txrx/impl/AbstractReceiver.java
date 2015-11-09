package com.crana.qcontroller.rpi.txrx.impl;

import com.crana.qcontroller.domain.DeviceConfig;
import com.crana.qcontroller.domain.TxRxMessage;
import com.crana.qcontroller.rpi.txrx.Receiver;
import com.crana.qcontroller.rpi.txrx.Transmitter;
import com.crana.qcontroller.service.Command;

public abstract class AbstractReceiver extends Thread implements Receiver {
	private boolean ready = false;
	private boolean stopReceiver = false;
	private long receiverDelay = 1000;
	private DeviceConfig myDeviceConfig;
	private ReceivedCommandProcessor receivedCommandProcessor = null;
	public AbstractReceiver(DeviceConfig myDeviceConfig, Transmitter transmitter) {
		this.myDeviceConfig = myDeviceConfig;
		receivedCommandProcessor = new ReceivedCommandProcessor(myDeviceConfig, transmitter);
	}
	protected abstract TxRxMessage receive() throws Exception ;
	public final void run() {
		try {
			receivedCommandProcessor.start();
			while(!stopReceiver) {
				TxRxMessage message = receive();
				if (message != null && isForMe(message)) {
					log(message);
					receivedCommandProcessor.addToMessageProcessorQueue(message);
				}
				Thread.sleep(receiverDelay);
				ready = true;
			}
		} catch (Exception exp) {
			exp.printStackTrace();
		}
	}
	private boolean isForMe(TxRxMessage message) {
		System.out.println("message::"+message);
		System.out.println("myDeviceConfig::"+myDeviceConfig);
		return message.getRecipient() == null 
				|| message.getRecipient().equalsIgnoreCase(myDeviceConfig.getDeviceId()) 
				|| message.getOriginalRecipient().equalsIgnoreCase(myDeviceConfig.getDeviceId());
	}
	public final boolean isReady() {
		return ready;
	}
	public final void startReceiver() {
		this.start();
	}
	public final void stopReceiver() {
		this.stopReceiver = true;
		if (receivedCommandProcessor != null) {
			receivedCommandProcessor.stopReceiver();
		}
	}
	private void log(TxRxMessage txRxMessage) {
		System.out.println(buildLogMessage(txRxMessage));
	}

	private String buildLogMessage(TxRxMessage txRxMessage) {
		StringBuffer sb = new StringBuffer();
		sb.append("\n------------------------------------RECEIVED::"+Command.getCommandByCommandId(txRxMessage.getCommandId())+"-----------------------------------");
		sb.append("\n\tMessageId\t\t:\t"+txRxMessage.getMessageId());
		sb.append("\n\tCommandId\t\t:\t"+txRxMessage.getCommandId());
		sb.append("\n\tSender\t\t:\t"+txRxMessage.getSender());
		sb.append("\n\tRecipient\t\t:\t"+txRxMessage.getRecipient());
		sb.append("\n\tOriginalSender\t:\t"+txRxMessage.getOriginalSender());
		sb.append("\n\tOriginalRecipient\t:\t"+txRxMessage.getOriginalRecipient());
		sb.append("\n\tPayload\t\t:\t"+txRxMessage.getPayload());
		sb.append("\n\t\t"+txRxMessage.getOriginalMessage());
		sb.append("\n------------------------------------------------------------------------------");
		return sb.toString();
	}
}

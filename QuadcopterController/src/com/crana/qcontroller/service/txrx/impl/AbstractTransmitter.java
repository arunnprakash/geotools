package com.crana.qcontroller.service.txrx.impl;

import java.util.LinkedList;
import java.util.Queue;

import javax.swing.JTextPane;

import com.crana.qcontroller.domain.DeviceConfig;
import com.crana.qcontroller.domain.TxRxMessage;
import com.crana.qcontroller.service.Command;
import com.crana.qcontroller.service.txrx.Transmitter;

public abstract class AbstractTransmitter extends Thread implements Transmitter {
	private boolean ready = false;
	private boolean stopTransmitter = false;
	private long transmissionDelay = 1000;
	private JTextPane txMessageLogger;
	private TransmitCommandProcessor transmitCommandProcessor = null;
	private Queue<TxRxMessage> messageQueue = new LinkedList<TxRxMessage>();
	private Queue<Command> commandQueue = new LinkedList<Command>();
	protected abstract void transmitMessage(TxRxMessage txRxMessage);
	public AbstractTransmitter(DeviceConfig myDeviceConfig, JTextPane txMessageLogger) {
		this.txMessageLogger = txMessageLogger;
		transmitCommandProcessor = new TransmitCommandProcessor(myDeviceConfig);
	}
	public final void run() {
		try {
			transmitCommandProcessor.start();
			while(!stopTransmitter) {
				while (!commandQueue.isEmpty()) {
					Command command = commandQueue.poll();
					TxRxMessage txRxMessage = transmitCommandProcessor.getMessage(command);
					messageQueue.add(txRxMessage);
				}
				while (!messageQueue.isEmpty()) {
					TxRxMessage txRxMessage = messageQueue.poll();
					transmitMessage(txRxMessage);
					log(txRxMessage);
				}
				Thread.sleep(transmissionDelay);
				ready = true;
			}
		} catch (Exception exp) {
			exp.printStackTrace();
		}
	}
	public final boolean isReady() {
		return ready;
	}
	public void transmit(Command command) {
		commandQueue.add(command);
	}
	public void transmit(TxRxMessage message) {
		transmitCommandProcessor.setMessageId(message);
		messageQueue.add(message);
	}
	public final void startTransmitter() {
		this.start();
	}
	public final void stopTransmitter() {
		this.stopTransmitter = true;
		if (transmitCommandProcessor != null) {
			transmitCommandProcessor.stopTransmitter();
		}
	}
	public JTextPane getTxMessageLogger() {
		return txMessageLogger;
	}

	private void log(TxRxMessage txRxMessage) {
		getTxMessageLogger().setText(getTxMessageLogger().getText() + buildLogMessage(txRxMessage));
	}

	private String buildLogMessage(TxRxMessage txRxMessage) {
		StringBuffer sb = new StringBuffer();
		sb.append("\n------------------------------------"+Command.getCommandByCommandId(txRxMessage.getCommandId())+"-----------------------------------");
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

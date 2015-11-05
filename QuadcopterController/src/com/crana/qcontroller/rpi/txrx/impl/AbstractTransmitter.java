package com.crana.qcontroller.rpi.txrx.impl;

import java.util.LinkedList;
import java.util.Queue;

import com.crana.qcontroller.domain.DeviceConfig;
import com.crana.qcontroller.domain.TxRxMessage;
import com.crana.qcontroller.rpi.txrx.Transmitter;
import com.crana.qcontroller.service.Command;

public abstract class AbstractTransmitter extends Thread implements Transmitter {
	private boolean ready = false;
	private boolean stopTransmitter = false;
	private long transmissionDelay = 1000;
	private TransmitCommandProcessor transmitCommandProcessor = null;
	private Queue<TxRxMessage> messageQueue = new LinkedList<TxRxMessage>();
	private Queue<Command> commandQueue = new LinkedList<Command>();
	protected abstract void transmitMessage(TxRxMessage txRxMessage);
	public AbstractTransmitter(DeviceConfig myDeviceConfig) {
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
}

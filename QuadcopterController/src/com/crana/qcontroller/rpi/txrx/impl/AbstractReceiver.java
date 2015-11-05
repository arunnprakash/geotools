package com.crana.qcontroller.rpi.txrx.impl;

import com.crana.qcontroller.domain.DeviceConfig;
import com.crana.qcontroller.domain.TxRxMessage;
import com.crana.qcontroller.rpi.txrx.Receiver;
import com.crana.qcontroller.rpi.txrx.Transmitter;

public abstract class AbstractReceiver extends Thread implements Receiver {
	private boolean ready = false;
	private boolean stopReceiver = false;
	private long receiverDelay = 1000;
	private ReceivedCommandProcessor receivedCommandProcessor = null;
	public AbstractReceiver(DeviceConfig myDeviceConfig, Transmitter transmitter) {
		receivedCommandProcessor = new ReceivedCommandProcessor(myDeviceConfig, transmitter);
	}
	protected abstract TxRxMessage receive() throws Exception ;
	public final void run() {
		try {
			receivedCommandProcessor.start();
			while(!stopReceiver) {
				TxRxMessage message = receive();
				if (message != null) {
					receivedCommandProcessor.addToMessageProcessorQueue(message);
				}
				Thread.sleep(receiverDelay);
				ready = true;
			}
		} catch (Exception exp) {
			exp.printStackTrace();
		}
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
}

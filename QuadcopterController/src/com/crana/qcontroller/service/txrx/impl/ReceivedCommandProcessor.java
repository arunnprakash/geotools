/**
 * 
 */
package com.crana.qcontroller.service.txrx.impl;

import java.util.LinkedList;
import java.util.Queue;

import com.crana.qcontroller.domain.DeviceConfig;
import com.crana.qcontroller.domain.TxRxMessage;
import com.crana.qcontroller.service.Command;
import com.crana.qcontroller.service.DistanceCalculator;
import com.crana.qcontroller.ui.QControllerMainWindow;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author ArunPrakash
 *
 */
public class ReceivedCommandProcessor extends Thread {
	private boolean stopReceiver = false;
	private long receiverDelay = 1000;
	private Queue<TxRxMessage> messageQueue = new LinkedList<TxRxMessage>();
	private QControllerMainWindow mainWindow;
	private DeviceConfig myDeviceConfig;
	private ObjectMapper objectMapper = new ObjectMapper();
	public ReceivedCommandProcessor(DeviceConfig myDeviceConfig, QControllerMainWindow mainWindow) {
		this.mainWindow = mainWindow;
		this.myDeviceConfig = myDeviceConfig;
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
			case INVITE_RESPONSE: {
				processInviteResponse(message);
				break;
			}
			case GPS_LOCATION_RESPONSE: {
				processGpsLocationResponse(message);
				break;
			}
			default: {
				throw new IllegalArgumentException("No Implementation found in ReceivedCommandProcessor for Command::"+command.name());
			}
		}
	}
	private void processGpsLocationResponse(TxRxMessage message) throws Exception {
		DeviceConfig deviceConfig = getDeviceConfig(message.getPayload());
		calculateDistance(deviceConfig);
		mainWindow.addNeighbourDevice(deviceConfig);
	}
	private void processInviteResponse(TxRxMessage message) throws Exception {
		DeviceConfig deviceConfig = getDeviceConfig(message.getPayload());
		calculateDistance(deviceConfig);
		mainWindow.addNeighbourDevice(deviceConfig);
	}
	private void calculateDistance(DeviceConfig deviceConfig) {
		double distance = DistanceCalculator.distance(myDeviceConfig.getLatitude(), myDeviceConfig.getLongitude(), deviceConfig.getLatitude(), deviceConfig.getLongitude(), 8, 8);
		deviceConfig.setDistance(distance);
	}
	private DeviceConfig getDeviceConfig(String payload) throws Exception {
		return objectMapper.readValue(payload, DeviceConfig.class);
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

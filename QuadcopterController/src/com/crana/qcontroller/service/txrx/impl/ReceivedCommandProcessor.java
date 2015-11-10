/**
 * 
 */
package com.crana.qcontroller.service.txrx.impl;

import java.util.LinkedList;
import java.util.Queue;

import com.crana.qcontroller.domain.DeviceConfig;
import com.crana.qcontroller.domain.GpsLocation;
import com.crana.qcontroller.domain.TxRxMessage;
import com.crana.qcontroller.domain.TxRxMessageBuilder;
import com.crana.qcontroller.service.Command;
import com.crana.qcontroller.service.DistanceCalculator;
import com.crana.qcontroller.service.txrx.Transmitter;
import com.crana.qcontroller.ui.QControllerMainWindow;
import com.fasterxml.jackson.core.JsonProcessingException;
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
	private QControllerMainWindow mainWindow;
	public ReceivedCommandProcessor(DeviceConfig myDeviceConfig, Transmitter transmitter, QControllerMainWindow mainWindow) {
		this.myDeviceConfig = myDeviceConfig;
		this.transmitter = transmitter;
		this.mainWindow = mainWindow;
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
			case START: {
				break;
			}
			case INVITE: {
				processInviteMessage(message);
				break;
			}
			case INVITE_RESPONSE: {
				processInviteResponse(message);
				break;
			}
			case GET_GPS_LOCATION: {
				Integer commandId = Command.GPS_LOCATION_RESPONSE.getCommandId();
				String payload = objectMapper.writeValueAsString(myDeviceConfig.getGpsLocation());
				transmitter.transmit(buildResponse(commandId, message, payload));
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
	private TxRxMessage buildResponse(Integer commandId, TxRxMessage receivedMessage, String payload) throws Exception {
		TxRxMessage txRxMessage = TxRxMessageBuilder.txRxMessage()
				.withCommandId(commandId)
				.withSender(myDeviceConfig.getDeviceId())
				.withRecipient(receivedMessage.getSender())
				.withOriginalSender(myDeviceConfig.getDeviceId())
				.withOriginalRecipient(receivedMessage.getOriginalSender())
				.withPayload(payload)
				.build();
		return txRxMessage;
	}
	private void processGpsLocationResponse(TxRxMessage message) throws Exception {
		GpsLocation gpsLocation = objectMapper.readValue(message.getPayload(), GpsLocation.class);
		calculateDistance(gpsLocation);
	}
	private void processInviteMessage(TxRxMessage message)
			throws JsonProcessingException, Exception {
		Integer commandId = Command.INVITE_RESPONSE.getCommandId();
		String payload = objectMapper.writeValueAsString(myDeviceConfig);
		transmitter.transmit(buildResponse(commandId, message, payload));
		DeviceConfig deviceConfig = objectMapper.readValue(message.getPayload(), DeviceConfig.class);
		myDeviceConfig.getDevices().put(deviceConfig.getDeviceId(), deviceConfig);
		calculateDistance(deviceConfig.getGpsLocation());
		setNeighbourDevice();
		mainWindow.addNeighbourDevice(deviceConfig);
	}
	private void processInviteResponse(TxRxMessage message) throws Exception {
		DeviceConfig deviceConfig = objectMapper.readValue(message.getPayload(), DeviceConfig.class);
		myDeviceConfig.getDevices().put(deviceConfig.getDeviceId(), deviceConfig);
		calculateDistance(deviceConfig.getGpsLocation());
		setNeighbourDevice();
		mainWindow.addNeighbourDevice(deviceConfig);
	}
	private void setNeighbourDevice() {
		DeviceConfig myNeigbour = null;
		for (String deviceId : myDeviceConfig.getDevices().keySet()) {
			DeviceConfig deviceConfig = myDeviceConfig.getDevices().get(deviceId);
			if (myNeigbour == null || deviceConfig.getGpsLocation().getDistance() < myNeigbour.getGpsLocation().getDistance()) {
				myNeigbour = deviceConfig;
			}
		}
		myDeviceConfig.setMyNeigbour(myNeigbour);
	}
	private void calculateDistance(GpsLocation gpsLocation) {
		double distance = DistanceCalculator.distance(myDeviceConfig.getGpsLocation().getLatitude(), myDeviceConfig.getGpsLocation().getLongitude(), 
				gpsLocation.getLatitude(), gpsLocation.getLongitude(), 8, 8);
		gpsLocation.setDistance(distance);
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

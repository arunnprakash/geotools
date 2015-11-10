/**
 * 
 */
package com.crana.qcontroller.ui;

import com.crana.qcontroller.domain.DeviceConfig;
import com.crana.qcontroller.service.txrx.Transmitter;

/**
 * @author ArunPrakash
 *
 */
public interface ControllerUI {

	public void setTransmitter(Transmitter transmitter);

	public void logTransmissionMessage(String logMessage);

	public void logReceivedMessage(String logMessage);

	public void addNeighbourDevice(DeviceConfig deviceConfig);

}

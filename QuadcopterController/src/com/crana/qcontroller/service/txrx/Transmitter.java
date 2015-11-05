/**
 * 
 */
package com.crana.qcontroller.service.txrx;

import com.crana.qcontroller.service.Command;

/**
 * @author ArunPrakash
 *
 */
public interface Transmitter {
	public void startTransmitter();
	public void transmit(Command command);
	public void stopTransmitter();
	public boolean isReady();
}

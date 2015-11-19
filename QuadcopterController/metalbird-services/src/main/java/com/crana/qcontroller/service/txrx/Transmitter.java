/**
 * 
 */
package com.crana.qcontroller.service.txrx;

import com.crana.qcontroller.domain.TxRxMessage;
import com.crana.qcontroller.service.Command;

/**
 * @author ArunPrakash
 *
 */
public interface Transmitter {
	public void startTransmitter();
	public void transmit(Command command);
	public void transmit(TxRxMessage message);
	public void stopTransmitter();
	public boolean isReady();
}

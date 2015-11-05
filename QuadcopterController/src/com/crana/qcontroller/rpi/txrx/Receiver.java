/**
 * 
 */
package com.crana.qcontroller.rpi.txrx;


/**
 * @author ArunPrakash
 *
 */
public interface Receiver {
	public void startReceiver();
	public void stopReceiver();
	public boolean isReady();
}

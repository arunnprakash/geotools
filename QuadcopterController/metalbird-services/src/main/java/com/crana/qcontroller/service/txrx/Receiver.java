/**
 * 
 */
package com.crana.qcontroller.service.txrx;


/**
 * @author ArunPrakash
 *
 */
public interface Receiver {
	public void startReceiver();
	public void stopReceiver();
	public boolean isReady();
}

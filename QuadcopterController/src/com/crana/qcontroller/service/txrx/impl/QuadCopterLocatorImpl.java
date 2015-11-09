/**
 * 
 */
package com.crana.qcontroller.service.txrx.impl;

import com.crana.qcontroller.service.Command;
import com.crana.qcontroller.service.txrx.QuadCopterLocator;
import com.crana.qcontroller.service.txrx.Transmitter;

/**
 * @author ArunPrakash
 *
 */
public class QuadCopterLocatorImpl extends Thread implements QuadCopterLocator {
	private Transmitter transmitter;
	private boolean stopLocator = true;

	public QuadCopterLocatorImpl(Transmitter transmitter) {
		super();
		this.transmitter = transmitter;
	}

	public void run() {
		try {
			while (true) {
				while (!stopLocator) {
					System.out.println("Before Tranmit Get Gps Location");
					transmitter.transmit(Command.GET_GPS_LOCATION);
					Thread.sleep(1000);
				}
				Thread.sleep(1000);
			}
		} catch(Exception exp) {
			exp.printStackTrace();
		}
	}

	@Override
	public void startTracking() {
		System.out.println("startTracking Via GpsLocator");
		stopLocator = false;
		this.start();
	}

	@Override
	public void stopTracking() {
		this.stopLocator = true;
	}

}

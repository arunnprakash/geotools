/**
 * 
 */
package com.crana.qcontroller.rpi;

import static com.crana.qcontroller.service.MyDeviceConfigKeyConstant.DEVICE_ID_KEY;
import static com.crana.qcontroller.service.MyDeviceConfigKeyConstant.DEVICE_LATITUDE_KEY;
import static com.crana.qcontroller.service.MyDeviceConfigKeyConstant.DEVICE_LONGITUDE_KEY;
import static com.crana.qcontroller.service.MyDeviceConfigKeyConstant.DEVICE_NAME_KEY;
import static com.crana.qcontroller.service.MyDeviceConfigKeyConstant.DEVICE_LOCOMOTION_TYPE_KEY;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import com.crana.qcontroller.domain.DeviceConfig;
import com.crana.qcontroller.domain.DeviceLocomotionType;
import com.crana.qcontroller.rpi.txrx.Receiver;
import com.crana.qcontroller.rpi.txrx.Transmitter;
import com.crana.qcontroller.rpi.txrx.impl.DefaultReceiver;
import com.crana.qcontroller.rpi.txrx.impl.DefaultTransmitter;
import com.crana.qcontroller.service.Command;

/**
 * @author ArunPrakash
 *
 */
public class RPIAppLauncer extends Thread {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			RPIAppLauncer rpiAppLauncer = new RPIAppLauncer();
			rpiAppLauncer.start();
			rpiAppLauncer.join();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	public final void run() {
		try {
			DeviceConfig myDeviceConfig = loadMyDeviceConfig();
			Transmitter transmitter = initTransmitter(myDeviceConfig);
			waitForTransmitterIsToReady(transmitter);
			Receiver receiver = initReceiver(myDeviceConfig, transmitter);
			waitForReceiverIsToReady(receiver);
			//broadCastMyDeviceConfig(transmitter);
			System.out.println("RPI Started");
		} catch (Exception exp) {
			exp.printStackTrace();
		}

	}
	private void broadCastMyDeviceConfig(Transmitter transmitter) {
		transmitter.transmit(Command.INVITE);
	}

	private void waitForReceiverIsToReady(Receiver receiver) throws Exception {
		while (!receiver.isReady()) {
			Thread.sleep(1000);
		}
	}
	private void waitForTransmitterIsToReady(Transmitter transmitter) throws Exception {
		while (!transmitter.isReady()) {
			Thread.sleep(1000);
		}
	}
	private DeviceConfig loadMyDeviceConfig() throws Exception {
		DeviceConfig myDeviceConfig = new DeviceConfig();
		InputStream is = null;
		File myDeviceConfigPropertyFile = new File(System.getProperty("user.home") + "\\rpi_device_default_config.properties");
		if (myDeviceConfigPropertyFile.exists()) {
			is = new FileInputStream(myDeviceConfigPropertyFile);
		} else {
			is = RPIAppLauncer.class.getResourceAsStream("/com/crana/qcontroller/rpi/resources/rpi_device_default_config.properties");
		}
		Properties props = new Properties();
		props.load(is);
		myDeviceConfig.setDeviceName(props.getProperty(DEVICE_NAME_KEY));
		myDeviceConfig.setDeviceId(props.getProperty(DEVICE_ID_KEY));
		myDeviceConfig.setLatitude(Double.parseDouble(props.getProperty(DEVICE_LATITUDE_KEY)));
		myDeviceConfig.setLongitude(Double.parseDouble(props.getProperty(DEVICE_LONGITUDE_KEY)));
		myDeviceConfig.setLocomotionType(DeviceLocomotionType.valueOf(props.getProperty(DEVICE_LOCOMOTION_TYPE_KEY)));
		is.close();
		return myDeviceConfig;
	}
	private Transmitter initTransmitter(DeviceConfig myDeviceConfig) throws Exception {
		Transmitter transmitter = new DefaultTransmitter(myDeviceConfig);
		transmitter.startTransmitter();
		return transmitter;
	}
	private Receiver initReceiver(DeviceConfig myDeviceConfig, Transmitter transmitter) throws Exception {
		Receiver receiver = new DefaultReceiver(myDeviceConfig, transmitter);
		receiver.startReceiver();
		return receiver;
	}
}

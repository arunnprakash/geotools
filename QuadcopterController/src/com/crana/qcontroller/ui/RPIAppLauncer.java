/**
 * 
 */
package com.crana.qcontroller.ui;

import static com.crana.qcontroller.service.MyDeviceConfigKeyConstant.DEVICE_ID_KEY;
import static com.crana.qcontroller.service.MyDeviceConfigKeyConstant.DEVICE_LATITUDE_KEY;
import static com.crana.qcontroller.service.MyDeviceConfigKeyConstant.DEVICE_LONGITUDE_KEY;
import static com.crana.qcontroller.service.MyDeviceConfigKeyConstant.DEVICE_NAME_KEY;
import static com.crana.qcontroller.service.MyDeviceConfigKeyConstant.DEVICE_LOCOMOTION_TYPE_KEY;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.crana.qcontroller.domain.DeviceConfig;
import com.crana.qcontroller.domain.DeviceLocomotionType;
import com.crana.qcontroller.domain.GpsLocation;
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
			broadCastMyDeviceConfig(transmitter);
			initDynamicGpsThread(myDeviceConfig);
			System.out.println("RPI Started");
		} catch (Exception exp) {
			exp.printStackTrace();
		}

	}
	private void initDynamicGpsThread(final DeviceConfig myDeviceConfig) {
		Thread thread = new Thread() {
			@SuppressWarnings("serial")
			public void run() {
				Map<Double, Double> latLonMap = new LinkedHashMap<Double, Double>() {{
					put(12.839686068565335D, 78.81094217995053D);
					put(12.54401688488115D, 77.42129701663487D);
					put(11.568308578723343D, 76.6821240574244D);
					put(10.829135619512883D, 77.18476166968752D);
					put(9.631675425591936D, 77.40651355745065D);
					put(10.651734109302373D, 77.87958425134535D);
				}};
				try {
					List<Map.Entry<Double, Double>> listOfCoOridinates = new ArrayList<Map.Entry<Double, Double>>(latLonMap.entrySet());
					int i = 0;
					while (true) {
						Thread.sleep(1000L);
						Map.Entry<Double, Double> entry = listOfCoOridinates.get(i);
						myDeviceConfig.getGpsLocation().setLatitude(entry.getKey());
						myDeviceConfig.getGpsLocation().setLongitude(entry.getValue());
						i++;
						if (i == listOfCoOridinates.size()) {
							i = 0;
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		};
		thread.start();
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
		GpsLocation gpsLocation = new GpsLocation();
		gpsLocation.setLatitude(Double.parseDouble(props.getProperty(DEVICE_LATITUDE_KEY)));
		gpsLocation.setLongitude(Double.parseDouble(props.getProperty(DEVICE_LONGITUDE_KEY)));
		myDeviceConfig.setGpsLocation(gpsLocation);
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

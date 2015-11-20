package com.crana.qcontroller.service.txrx.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import com.crana.qcontroller.domain.DeviceConfig;
import com.crana.qcontroller.domain.TxRxMessage;
import com.crana.qcontroller.service.txrx.Transmitter;
import com.crana.qcontroller.ui.ControllerUI;

public class DefaultReceiver extends AbstractReceiver {

	private TxRxMessageBoundaryValueBuilder txrxMessageBoundaryValueBuilder;
	private DeviceConfig myDeviceConfig;
	private FileInputStream fis;
	private BufferedReader br;
	public DefaultReceiver(DeviceConfig myDeviceConfig, Transmitter transmitter, ControllerUI controllerUI) throws Exception {
		super(myDeviceConfig, transmitter, controllerUI);
		this.myDeviceConfig = myDeviceConfig;
		txrxMessageBoundaryValueBuilder = new TxRxMessageBoundaryValueBuilder();
		initStream();
	}

	private void initStream() throws Exception {
		File file = new File(System.getProperty("user.home") + "\\" + myDeviceConfig.getDeviceId() + ".txt");
		if (file.exists()) {
			file.delete();
			file.createNewFile();
		} else {
			file.createNewFile();
		}
		fis = new FileInputStream(file);
		br = new BufferedReader(new InputStreamReader(fis));
	}

	@Override
	protected TxRxMessage receive() throws Exception {
		String receivedMessage = null;
		//Todo::Need to Implement Code to read 'receivedMessage' from Receiver USB POrt
		//Todo::Need to Comment out following
		receivedMessage = readFromFile();
		if (null != receivedMessage && !receivedMessage.trim().isEmpty()) {
			TxRxMessage txRxMessage = txrxMessageBoundaryValueBuilder.buildReceivedMessage(receivedMessage);
			return txRxMessage;
		}
		return null;
	}

	private String readFromFile() throws Exception  {
		return br.readLine();
	}
	
	public void finalize() {
		if (br != null) {
			try {
				br.close();
				fis.close();
				File file = new File(System.getProperty("user.home") + "\\" + myDeviceConfig.getDeviceId() + ".txt");
				if (file.exists()) {
					file.delete();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
}

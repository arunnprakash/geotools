package com.crana.qcontroller.service.txrx.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.channels.FileLock;

import com.crana.qcontroller.domain.DeviceConfig;
import com.crana.qcontroller.domain.TxRxMessage;
import com.crana.qcontroller.service.txrx.Transmitter;
import com.crana.qcontroller.ui.QControllerMainWindow;

public class DefaultReceiver extends AbstractReceiver {

	private TxRxMessageBoundaryValueBuilder txrxMessageBoundaryValueBuilder;
	private DeviceConfig myDeviceConfig;
	public DefaultReceiver(DeviceConfig myDeviceConfig, Transmitter transmitter, QControllerMainWindow mainWindow) throws Exception {
		super(myDeviceConfig, transmitter, mainWindow);
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
		File file = new File(System.getProperty("user.home") + "\\" + myDeviceConfig.getDeviceId() + ".txt");
		FileInputStream fis = new FileInputStream(file);
		FileLock lock = fis.getChannel().lock();
		BufferedReader br = new BufferedReader(new InputStreamReader(fis));
		String lineOfString = br.readLine();
		lock.release();
		br.close();
		fis.close();
		return lineOfString;
	}
}

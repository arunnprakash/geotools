package com.crana.qcontroller.rpi.txrx.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.crana.qcontroller.domain.DeviceConfig;
import com.crana.qcontroller.domain.TxRxMessage;
import com.crana.qcontroller.rpi.txrx.Transmitter;

public class DefaultReceiver extends AbstractReceiver {

	private BufferedReader br;
	private TxRxMessageBoundaryValueBuilder txrxMessageBoundaryValueBuilder;
	public DefaultReceiver(DeviceConfig myDeviceConfig, Transmitter transmitter) {
		super(myDeviceConfig, transmitter);
		txrxMessageBoundaryValueBuilder = new TxRxMessageBoundaryValueBuilder();
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
		if (br == null) {
			File file = new File("e:\\tx.txt");
			if (file.exists()) {
				file.delete();
				file.createNewFile();
			}
			InputStream fis = new FileInputStream(file);
			br = new BufferedReader(new InputStreamReader(fis));
		}
		return br.readLine();
	}

	public void finalize() {
		if (br != null) {
			try {
				br.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
	}
}

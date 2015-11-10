package com.crana.qcontroller.rpi.txrx.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

import com.crana.qcontroller.domain.DeviceConfig;
import com.crana.qcontroller.domain.TxRxMessage;
import com.crana.qcontroller.rpi.txrx.Transmitter;

public class DefaultReceiver extends AbstractReceiver {

	private TxRxMessageBoundaryValueBuilder txrxMessageBoundaryValueBuilder;
	private DeviceConfig myDeviceConfig;
	private FileInputStream fis;
	private BufferedReader br;
	public DefaultReceiver(DeviceConfig myDeviceConfig, Transmitter transmitter) throws Exception {
		super(myDeviceConfig, transmitter);
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

package com.crana.qcontroller.rpi.txrx.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.UUID;

import com.crana.qcontroller.domain.DeviceConfig;
import com.crana.qcontroller.domain.TxRxMessage;

public class DefaultTransmitter extends AbstractTransmitter {
	private TxRxMessageBoundaryValueBuilder txrxMessageBoundaryValueBuilder;
	private DeviceConfig myDeviceConfig;
	public DefaultTransmitter(DeviceConfig myDeviceConfig) throws Exception {
		super(myDeviceConfig);
		this.myDeviceConfig = myDeviceConfig;
		txrxMessageBoundaryValueBuilder = new TxRxMessageBoundaryValueBuilder();
	}

	@Override
	protected void transmitMessage(TxRxMessage txRxMessage) {
		String transmissionMessage = null;
		try {
			transmissionMessage = txrxMessageBoundaryValueBuilder.buildTransmissionMessage(txRxMessage);
			//Todo::Implement logic to transmit 'transmissionMessage' via USB Port
			//Todo::Need to Comment out following
			writeToFile(transmissionMessage, txRxMessage.getRecipient());
		} catch (Exception exp) {
			transmissionMessage = exp.getMessage();
		}
	}

	private void writeToFile(String transmissionMessage, String receipient) throws IOException {
		if (receipient != null && !receipient.trim().isEmpty()) {
			System.out.println("Writing on::" + receipient);
			File file = new File(System.getProperty("user.home") + "\\" + receipient + ".txt");
			writeToFile(transmissionMessage, file);
		} else {
			System.out.println("Receipient is Null");
			File rootDirectory = new File(System.getProperty("user.home"));
			File[] files = rootDirectory.listFiles(new FilenameFilter(){
				@Override
				public boolean accept(File directory, String name) {
					try {
						String fileName = name.split("\\.")[0];
						UUID uuid = UUID.fromString(fileName);
						System.out.println("fileNameUUID::"+uuid);
						return uuid != null && !uuid.toString().equalsIgnoreCase(myDeviceConfig.getDeviceId());
					} catch (Exception e) {
						return false;
					}
				}
			});
			if (files != null && files.length > 0) {
				for (int i = 0; i < files.length; i++) {
					System.out.println("Writing on::"+files[i].getName());
					writeToFile(transmissionMessage, files[i]);
				}
			}
		}
	}

	private void writeToFile(String transmissionMessage, File file)
			throws FileNotFoundException, IOException {
		FileChannel channel = new RandomAccessFile(file, "rw").getChannel();
		FileLock lock = channel.lock();
		OutputStream fos = new FileOutputStream(file);
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
		bw.write(transmissionMessage + System.lineSeparator());
		bw.flush();
		lock.release();
		bw.close();
		fos.close();
	}
}

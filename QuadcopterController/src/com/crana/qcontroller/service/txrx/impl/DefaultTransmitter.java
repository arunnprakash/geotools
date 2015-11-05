package com.crana.qcontroller.service.txrx.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import javax.swing.JTextPane;

import com.crana.qcontroller.domain.DeviceConfig;
import com.crana.qcontroller.domain.TxRxMessage;
import com.crana.qcontroller.service.Command;

public class DefaultTransmitter extends AbstractTransmitter {
	private TxRxMessageBoundaryValueBuilder txrxMessageBoundaryValueBuilder;
	private BufferedWriter bw;
	public DefaultTransmitter(DeviceConfig myDeviceConfig, JTextPane txMessageLogger) {
		super(myDeviceConfig, txMessageLogger);
		txrxMessageBoundaryValueBuilder = new TxRxMessageBoundaryValueBuilder();
	}

	@Override
	protected void transmit(TxRxMessage txRxMessage) {
		String transmissionMessage = null;
		try {
			transmissionMessage = txrxMessageBoundaryValueBuilder.buildTransmissionMessage(txRxMessage);
			//Todo::Implement logic to transmit 'transmissionMessage' via USB Port
			//Todo::Need to Comment out following
			writeToFile(transmissionMessage);
		} catch (Exception exp) {
			transmissionMessage = exp.getMessage();
		}
	}

	private void writeToFile(String transmissionMessage) throws IOException {
		if (bw == null) {
			File file = new File(System.getProperty("user.home") + "\\tx.txt");
			OutputStream fos = new FileOutputStream(file);
			bw = new BufferedWriter(new OutputStreamWriter(fos));
		}
		bw.write("\n\r"+transmissionMessage);
		bw.flush();
	}

	public void finalize() {
		if (bw != null) {
			try {
				bw.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
	}
}

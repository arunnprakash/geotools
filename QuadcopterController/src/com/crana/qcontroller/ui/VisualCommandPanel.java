package com.crana.qcontroller.ui;

import javax.swing.JPanel;

import java.awt.GridBagLayout;

import javax.swing.border.TitledBorder;
import javax.swing.border.LineBorder;

import java.awt.Color;

import javax.swing.JButton;

import com.crana.qcontroller.service.txrx.Transmitter;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JToggleButton;

public class VisualCommandPanel extends JPanel {
	private Transmitter transmitter;
	/**
	 * Create the panel.
	 */
	public VisualCommandPanel() {
		setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "VisualCommand", TitledBorder.LEFT, TitledBorder.TOP, null, null));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JToggleButton btnTurnoncamera = new JToggleButton("TurnOnCamera");
		GridBagConstraints gbc_btnTurnoncamera = new GridBagConstraints();
		gbc_btnTurnoncamera.weightx = 1.0;
		gbc_btnTurnoncamera.anchor = GridBagConstraints.EAST;
		gbc_btnTurnoncamera.gridx = 0;
		gbc_btnTurnoncamera.gridy = 0;
		add(btnTurnoncamera, gbc_btnTurnoncamera);
		
		JToggleButton btnTurnonflashlight = new JToggleButton("TurnOnFlashLight");
		GridBagConstraints gbc_btnTurnonflashlight = new GridBagConstraints();
		gbc_btnTurnonflashlight.weightx = 1.0;
		gbc_btnTurnonflashlight.anchor = GridBagConstraints.WEST;
		gbc_btnTurnonflashlight.gridx = 1;
		gbc_btnTurnonflashlight.gridy = 0;
		add(btnTurnonflashlight, gbc_btnTurnonflashlight);
		
		JToggleButton btnStartlivestreaming = new JToggleButton("StartLiveStreaming");
		GridBagConstraints gbc_btnStartlivestreaming = new GridBagConstraints();
		gbc_btnStartlivestreaming.weightx = 1.0;
		gbc_btnStartlivestreaming.gridwidth = 2;
		gbc_btnStartlivestreaming.gridx = 0;
		gbc_btnStartlivestreaming.gridy = 1;
		add(btnStartlivestreaming, gbc_btnStartlivestreaming);
		
		JButton btnShotphoto = new JButton("ShotPhoto");
		GridBagConstraints gbc_btnShotphoto = new GridBagConstraints();
		gbc_btnShotphoto.weightx = 1.0;
		gbc_btnShotphoto.anchor = GridBagConstraints.EAST;
		gbc_btnShotphoto.gridx = 0;
		gbc_btnShotphoto.gridy = 2;
		add(btnShotphoto, gbc_btnShotphoto);
		
		JButton btnGetphoto = new JButton("GetPhoto");
		GridBagConstraints gbc_btnGetphoto = new GridBagConstraints();
		gbc_btnGetphoto.weightx = 1.0;
		gbc_btnGetphoto.anchor = GridBagConstraints.WEST;
		gbc_btnGetphoto.gridx = 1;
		gbc_btnGetphoto.gridy = 2;
		add(btnGetphoto, gbc_btnGetphoto);
		
		JToggleButton btnStartVideoRecording = new JToggleButton("Start Video Recording");
		GridBagConstraints gbc_btnStartVideoRecording = new GridBagConstraints();
		gbc_btnStartVideoRecording.weightx = 1.0;
		gbc_btnStartVideoRecording.anchor = GridBagConstraints.EAST;
		gbc_btnStartVideoRecording.gridx = 0;
		gbc_btnStartVideoRecording.gridy = 3;
		add(btnStartVideoRecording, gbc_btnStartVideoRecording);
		
		JButton btnGetvideo = new JButton("GetVideo");
		GridBagConstraints gbc_btnGetvideo = new GridBagConstraints();
		gbc_btnGetvideo.weightx = 1.0;
		gbc_btnGetvideo.anchor = GridBagConstraints.WEST;
		gbc_btnGetvideo.gridx = 1;
		gbc_btnGetvideo.gridy = 3;
		add(btnGetvideo, gbc_btnGetvideo);

	}

	public void setTransmitter(Transmitter transmitter) {
		this.transmitter = transmitter;
	}
}

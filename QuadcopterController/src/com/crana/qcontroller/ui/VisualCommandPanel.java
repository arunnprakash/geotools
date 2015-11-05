package com.crana.qcontroller.ui;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import com.crana.qcontroller.service.txrx.Transmitter;

public class VisualCommandPanel extends JPanel {
	private Transmitter transmitter;
	private JToggleButton btnTurnoncamera;
	private JToggleButton btnTurnonflashlight;
	private JToggleButton btnStartlivestreaming;
	private JButton btnShotphoto;
	private JButton btnGetphoto;
	private JToggleButton btnStartVideoRecording;
	private JButton btnGetvideo;
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
		
		btnTurnoncamera = new JToggleButton("TurnOnCamera");
		btnTurnoncamera.setEnabled(false);
		GridBagConstraints gbc_btnTurnoncamera = new GridBagConstraints();
		gbc_btnTurnoncamera.weightx = 1.0;
		gbc_btnTurnoncamera.anchor = GridBagConstraints.EAST;
		gbc_btnTurnoncamera.gridx = 0;
		gbc_btnTurnoncamera.gridy = 0;
		add(btnTurnoncamera, gbc_btnTurnoncamera);
		
		btnTurnonflashlight = new JToggleButton("TurnOnFlashLight");
		btnTurnonflashlight.setEnabled(false);
		GridBagConstraints gbc_btnTurnonflashlight = new GridBagConstraints();
		gbc_btnTurnonflashlight.weightx = 1.0;
		gbc_btnTurnonflashlight.anchor = GridBagConstraints.WEST;
		gbc_btnTurnonflashlight.gridx = 1;
		gbc_btnTurnonflashlight.gridy = 0;
		add(btnTurnonflashlight, gbc_btnTurnonflashlight);
		
		btnStartlivestreaming = new JToggleButton("StartLiveStreaming");
		btnStartlivestreaming.setEnabled(false);
		GridBagConstraints gbc_btnStartlivestreaming = new GridBagConstraints();
		gbc_btnStartlivestreaming.weightx = 1.0;
		gbc_btnStartlivestreaming.gridwidth = 2;
		gbc_btnStartlivestreaming.gridx = 0;
		gbc_btnStartlivestreaming.gridy = 1;
		add(btnStartlivestreaming, gbc_btnStartlivestreaming);
		
		btnShotphoto = new JButton("ShotPhoto");
		btnShotphoto.setEnabled(false);
		GridBagConstraints gbc_btnShotphoto = new GridBagConstraints();
		gbc_btnShotphoto.weightx = 1.0;
		gbc_btnShotphoto.anchor = GridBagConstraints.EAST;
		gbc_btnShotphoto.gridx = 0;
		gbc_btnShotphoto.gridy = 2;
		add(btnShotphoto, gbc_btnShotphoto);
		
		btnGetphoto = new JButton("GetPhoto");
		btnGetphoto.setEnabled(false);
		GridBagConstraints gbc_btnGetphoto = new GridBagConstraints();
		gbc_btnGetphoto.weightx = 1.0;
		gbc_btnGetphoto.anchor = GridBagConstraints.WEST;
		gbc_btnGetphoto.gridx = 1;
		gbc_btnGetphoto.gridy = 2;
		add(btnGetphoto, gbc_btnGetphoto);
		
		btnStartVideoRecording = new JToggleButton("Start Video Recording");
		btnStartVideoRecording.setEnabled(false);
		GridBagConstraints gbc_btnStartVideoRecording = new GridBagConstraints();
		gbc_btnStartVideoRecording.weightx = 1.0;
		gbc_btnStartVideoRecording.anchor = GridBagConstraints.EAST;
		gbc_btnStartVideoRecording.gridx = 0;
		gbc_btnStartVideoRecording.gridy = 3;
		add(btnStartVideoRecording, gbc_btnStartVideoRecording);
		
		btnGetvideo = new JButton("GetVideo");
		btnGetvideo.setEnabled(false);
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

	public void enableVisualCommandButtons() {
		btnTurnoncamera.setEnabled(true);
		btnTurnoncamera.setSelected(false);
		btnTurnonflashlight.setEnabled(true);
		btnTurnonflashlight.setSelected(false);
		btnStartlivestreaming.setEnabled(true);
		btnStartlivestreaming.setSelected(false);
		btnShotphoto.setEnabled(true);
		btnGetphoto.setEnabled(true);
		btnStartVideoRecording.setEnabled(true);
		btnStartVideoRecording.setSelected(false);
		btnGetvideo.setEnabled(true);
	}

	public void disableVisualCommandButtons() {
		btnTurnoncamera.setEnabled(false);
		btnTurnoncamera.setSelected(false);
		btnTurnonflashlight.setEnabled(false);
		btnTurnonflashlight.setSelected(false);
		btnStartlivestreaming.setEnabled(false);
		btnStartlivestreaming.setSelected(false);
		btnShotphoto.setEnabled(false);
		btnGetphoto.setEnabled(false);
		btnStartVideoRecording.setEnabled(false);
		btnStartVideoRecording.setSelected(false);
		btnGetvideo.setEnabled(false);
	}
}

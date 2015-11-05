package com.crana.qcontroller.ui;

import javax.swing.JPanel;

import java.awt.GridBagLayout;

import javax.swing.border.TitledBorder;
import javax.swing.border.LineBorder;

import java.awt.Color;
import java.awt.GridBagConstraints;

import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import javax.swing.JButton;

import com.crana.qcontroller.service.Command;
import com.crana.qcontroller.service.txrx.QuadCopterLocator;
import com.crana.qcontroller.service.txrx.Transmitter;
import com.crana.qcontroller.service.txrx.impl.QuadCopterLocatorImpl;

import javax.swing.JToggleButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DrivingCommandPanel extends JPanel {
	private Transmitter transmitter;
	private JToggleButton btnStart;
	private JToggleButton btnTakeoff;
	private JToggleButton btnLand;
	private JToggleButton btnStop;
	private JButton rightByLeftUpButton;
	private JButton upButton;
	private JButton forwardButton;
	private JButton leftByLeftDownButton;
	private JButton standCelestialButton;
	private JButton rightButton;
	private JButton leftByRigtUpButton;
	private JButton backwardButton;
	private JButton downButton;
	private QuadCopterLocator quadCopterLocator;
	private CommandPanel commandPanel;
	/**
	 * Create the panel.
	 * @param commandPanel 
	 */
	public DrivingCommandPanel(CommandPanel commandPanel1) {
		this.commandPanel = commandPanel1;
		setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "Driving Commands", TitledBorder.LEFT, TitledBorder.TOP, null, null));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JPanel panel = new JPanel();
		panel.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 0;
		add(panel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 0, 0, 0, 0};
		gbl_panel.columnWeights = new double[]{1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0};
		panel.setLayout(gbl_panel);
		
		btnStart = new JToggleButton("Start");
		btnStart.setEnabled(false);
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				transmitter.transmit(Command.START);
				btnStart.setEnabled(false);
				btnStart.setText("Started");
				btnTakeoff.setEnabled(true);
				btnTakeoff.setSelected(false);
				btnStop.setEnabled(true);
				btnStop.setSelected(false);
				btnLand.setSelected(false);
				commandPanel.disableQuadCopterPicker();
				commandPanel.enableVisualCommands();
			}
		});
		GridBagConstraints gbc_btnStart = new GridBagConstraints();
		gbc_btnStart.gridx = 0;
		gbc_btnStart.gridy = 0;
		panel.add(btnStart, gbc_btnStart);
		
		btnTakeoff = new JToggleButton("TakeOff");
		btnTakeoff.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				btnTakeoff.setEnabled(false);
				btnStop.setEnabled(false);
				btnLand.setEnabled(true);
				btnLand.setSelected(false);
				enableOrDisableDrivingButtons(true);
			}
		});
		btnTakeoff.setEnabled(false);
		GridBagConstraints gbc_btnTakeoff = new GridBagConstraints();
		gbc_btnTakeoff.gridx = 1;
		gbc_btnTakeoff.gridy = 0;
		panel.add(btnTakeoff, gbc_btnTakeoff);
		
		btnLand = new JToggleButton("Land");
		btnLand.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnTakeoff.setEnabled(true);
				btnTakeoff.setSelected(false);
				btnStop.setEnabled(true);
				enableOrDisableDrivingButtons(false);
			}
		});
		btnLand.setEnabled(false);
		GridBagConstraints gbc_btnLand = new GridBagConstraints();
		gbc_btnLand.gridx = 2;
		gbc_btnLand.gridy = 0;
		panel.add(btnLand, gbc_btnLand);
		
		btnStop = new JToggleButton("Stop");
		btnStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnStart.setText("Start");
				btnStart.setEnabled(true);
				btnStart.setSelected(false);
				btnTakeoff.setEnabled(false);
				btnStop.setEnabled(false);
				btnStop.setSelected(false);
				btnLand.setEnabled(false);
				btnTakeoff.setSelected(false);
				btnLand.setSelected(false);
				commandPanel.enableQuadCopterPicker();
				commandPanel.disableVisualCommands();
			}
		});
		btnStop.setEnabled(false);
		GridBagConstraints gbc_btnStop = new GridBagConstraints();
		gbc_btnStop.gridx = 3;
		gbc_btnStop.gridy = 0;
		panel.add(btnStop, gbc_btnStop);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.gridx = 0;
		gbc_panel_1.gridy = 1;
		add(panel_1, gbc_panel_1);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[]{0, 0, 0, 0};
		gbl_panel_1.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0};
		gbl_panel_1.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel_1.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel_1.setLayout(gbl_panel_1);
		
		upButton = new JButton("Up");
		GridBagConstraints gbc_upButton = new GridBagConstraints();
		gbc_upButton.anchor = GridBagConstraints.SOUTH;
		gbc_upButton.gridx = 1;
		gbc_upButton.gridy = 0;
		panel_1.add(upButton, gbc_upButton);
		
		forwardButton = new JButton("Forward");
		GridBagConstraints gbc_forwardButton = new GridBagConstraints();
		gbc_forwardButton.anchor = GridBagConstraints.SOUTH;
		gbc_forwardButton.gridx = 1;
		gbc_forwardButton.gridy = 1;
		panel_1.add(forwardButton, gbc_forwardButton);
		
		leftByLeftDownButton = new JButton("Left by Left Down");
		GridBagConstraints gbc_leftByLeftDownButton = new GridBagConstraints();
		gbc_leftByLeftDownButton.anchor = GridBagConstraints.SOUTHEAST;
		gbc_leftByLeftDownButton.gridx = 0;
		gbc_leftByLeftDownButton.gridy = 2;
		panel_1.add(leftByLeftDownButton, gbc_leftByLeftDownButton);

		standCelestialButton = new JButton("StandCelestial");
		GridBagConstraints gbc_standCelestialButton = new GridBagConstraints();
		gbc_standCelestialButton.fill = GridBagConstraints.BOTH;
		gbc_standCelestialButton.gridheight = 2;
		gbc_standCelestialButton.gridx = 1;
		gbc_standCelestialButton.gridy = 2;
		panel_1.add(standCelestialButton, gbc_standCelestialButton);

		rightButton = new JButton("Right by Right Down");
		GridBagConstraints gbc_rightButton = new GridBagConstraints();
		gbc_rightButton.anchor = GridBagConstraints.SOUTHWEST;
		gbc_rightButton.gridx = 2;
		gbc_rightButton.gridy = 2;
		panel_1.add(rightButton, gbc_rightButton);
		
		leftByRigtUpButton = new JButton("Left by Rigt Up");
		GridBagConstraints gbc_leftByRigtUpButton = new GridBagConstraints();
		gbc_leftByRigtUpButton.anchor = GridBagConstraints.NORTHEAST;
		gbc_leftByRigtUpButton.gridx = 0;
		gbc_leftByRigtUpButton.gridy = 3;
		panel_1.add(leftByRigtUpButton, gbc_leftByRigtUpButton);
		
		rightByLeftUpButton = new JButton("Right by Left Up");
		GridBagConstraints gbc_rightByLeftUpButton = new GridBagConstraints();
		gbc_rightByLeftUpButton.anchor = GridBagConstraints.NORTHWEST;
		gbc_rightByLeftUpButton.gridx = 2;
		gbc_rightByLeftUpButton.gridy = 3;
		panel_1.add(rightByLeftUpButton, gbc_rightByLeftUpButton);
		
		backwardButton = new JButton("Backward");
		GridBagConstraints gbc_backwardButton = new GridBagConstraints();
		gbc_backwardButton.anchor = GridBagConstraints.NORTH;
		gbc_backwardButton.gridx = 1;
		gbc_backwardButton.gridy = 4;
		panel_1.add(backwardButton, gbc_backwardButton);
		
		downButton = new JButton("Down");
		GridBagConstraints gbc_downButton = new GridBagConstraints();
		gbc_downButton.gridx = 1;
		gbc_downButton.gridy = 5;
		panel_1.add(downButton, gbc_downButton);
		enableOrDisableDrivingButtons(false);
	}
	
	private void enableOrDisableDrivingButtons(boolean enable) {
		rightByLeftUpButton.setEnabled(enable);
		upButton.setEnabled(enable);
		forwardButton.setEnabled(enable);
		leftByLeftDownButton.setEnabled(enable);
		standCelestialButton.setEnabled(enable);
		rightButton.setEnabled(enable);
		leftByRigtUpButton.setEnabled(enable);
		backwardButton.setEnabled(enable);
		downButton.setEnabled(enable);
	}

	public void setTransmitter(Transmitter transmitter) {
		this.transmitter = transmitter;
		if (quadCopterLocator == null) {
			quadCopterLocator = new QuadCopterLocatorImpl(transmitter);
		}
	}

	public void enableStartButton() {
		btnStart.setEnabled(true);
	}

	public void disableStartButton() {
		btnStart.setEnabled(false);		
	}
}

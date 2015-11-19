package com.crana.qcontroller.ui;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.TitledBorder;

import com.crana.qcontroller.service.txrx.Transmitter;
import javax.swing.JToggleButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AutoPilotCommandPanel extends JPanel {
	private Transmitter transmitter;
	private JButton saveAutoPilotConfigButton;
	private JTextField destinationLatTextField;
	private JTextField destinationLonTextField;
	private JToggleButton btnPickFromMap;
	private JToggleButton autoPilotButton;
	private JPanel autoPilotPanel;
	private JLabel latitudeLabel;
	private JLabel longitudeLabel;
	private boolean pickLatLonFromMap = false;
	
	public boolean isPickLatLonFromMap() {
		return pickLatLonFromMap;
	}

	public void setLatLon(double latitude, double longitude) {
		destinationLatTextField.setText(String.valueOf(latitude));
		destinationLonTextField.setText(String.valueOf(longitude));
	}
	/**
	 * Create the panel.
	 */
	public AutoPilotCommandPanel() {
		GridBagLayout gbl_commandPanel = new GridBagLayout();
		gbl_commandPanel.columnWidths = new int[]{0, 0};
		gbl_commandPanel.rowHeights = new int[]{0, 0};
		gbl_commandPanel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_commandPanel.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		this.setLayout(gbl_commandPanel);
		
		autoPilotPanel = new JPanel();
		autoPilotPanel.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "Auto Pilot", TitledBorder.LEFT, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_autoPilotPanel = new GridBagConstraints();
		gbc_autoPilotPanel.fill = GridBagConstraints.BOTH;
		gbc_autoPilotPanel.gridx = 0;
		gbc_autoPilotPanel.gridy = 0;
		this.add(autoPilotPanel, gbc_autoPilotPanel);
		GridBagLayout gbl_autoPilotPanel = new GridBagLayout();
		gbl_autoPilotPanel.columnWidths = new int[] {0};
		gbl_autoPilotPanel.rowHeights = new int[]{0, 0, 0};
		gbl_autoPilotPanel.columnWeights = new double[]{1.0, 0.0};
		gbl_autoPilotPanel.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		autoPilotPanel.setLayout(gbl_autoPilotPanel);
		autoPilotPanel.setEnabled(false);
		
		autoPilotButton = new JToggleButton("Turn On Auto Pilot");
		autoPilotButton.setEnabled(false);
		autoPilotButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (autoPilotButton.isSelected()) {
					autoPilotButton.setText("Turn Off Auto Pilot");
					saveAutoPilotConfigButton.setEnabled(true);
					destinationLatTextField.setEnabled(true);
					destinationLonTextField.setEnabled(true);
					destinationLatTextField.setEditable(true);
					destinationLonTextField.setEditable(true);
					btnPickFromMap.setEnabled(true);
					latitudeLabel.setEnabled(true);
					longitudeLabel.setEnabled(true);
				} else {
					autoPilotButton.setText("Turn On Auto Pilot");
					saveAutoPilotConfigButton.setEnabled(false);
					destinationLatTextField.setEnabled(false);
					destinationLonTextField.setEnabled(false);
					destinationLatTextField.setEditable(false);
					destinationLonTextField.setEditable(false);
					btnPickFromMap.setEnabled(false);
					btnPickFromMap.setSelected(false);
					latitudeLabel.setEnabled(false);
					longitudeLabel.setEnabled(false);
				}
			}
		});
		GridBagConstraints gbc_autoPilotButton = new GridBagConstraints();
		gbc_autoPilotButton.insets = new Insets(0, 0, 5, 0);
		gbc_autoPilotButton.gridx = 0;
		gbc_autoPilotButton.gridy = 0;
		autoPilotPanel.add(autoPilotButton, gbc_autoPilotButton);
		
		JPanel autoPilotConfigpanel = new JPanel();
		GridBagConstraints gbc_autoPilotConfigpanel = new GridBagConstraints();
		gbc_autoPilotConfigpanel.weightx = 1.0;
		gbc_autoPilotConfigpanel.gridwidth = 2;
		gbc_autoPilotConfigpanel.gridx = 0;
		gbc_autoPilotConfigpanel.gridy = 1;
		autoPilotPanel.add(autoPilotConfigpanel, gbc_autoPilotConfigpanel);
		GridBagLayout gbl_autoPilotConfigpanel = new GridBagLayout();
		gbl_autoPilotConfigpanel.columnWidths = new int[]{0, 0, 0};
		gbl_autoPilotConfigpanel.rowHeights = new int[]{0, 0, 0, 0};
		gbl_autoPilotConfigpanel.columnWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
		gbl_autoPilotConfigpanel.rowWeights = new double[]{1.0, 1.0, 0.0, Double.MIN_VALUE};
		autoPilotConfigpanel.setLayout(gbl_autoPilotConfigpanel);
		
		JPanel autoPilotInputConfigPanel = new JPanel();
		GridBagConstraints gbc_autoPilotInputConfigPanel = new GridBagConstraints();
		gbc_autoPilotInputConfigPanel.fill = GridBagConstraints.BOTH;
		gbc_autoPilotInputConfigPanel.gridx = 0;
		gbc_autoPilotInputConfigPanel.gridy = 0;
		autoPilotConfigpanel.add(autoPilotInputConfigPanel, gbc_autoPilotInputConfigPanel);
		GridBagLayout gbl_autoPilotInputConfigPanel = new GridBagLayout();
		gbl_autoPilotInputConfigPanel.columnWidths = new int[]{0, 0, 0};
		gbl_autoPilotInputConfigPanel.rowHeights = new int[] {0};
		gbl_autoPilotInputConfigPanel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_autoPilotInputConfigPanel.rowWeights = new double[]{0.0, 0.0};
		autoPilotInputConfigPanel.setLayout(gbl_autoPilotInputConfigPanel);
		
		latitudeLabel = new JLabel("Destination Point - Latitude  :  ");
		latitudeLabel.setEnabled(false);
		GridBagConstraints gbc_latitudeLabel = new GridBagConstraints();
		gbc_latitudeLabel.anchor = GridBagConstraints.EAST;
		gbc_latitudeLabel.insets = new Insets(0, 0, 5, 5);
		gbc_latitudeLabel.gridx = 0;
		gbc_latitudeLabel.gridy = 0;
		autoPilotInputConfigPanel.add(latitudeLabel, gbc_latitudeLabel);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		GridBagConstraints gbc_panel_2 = new GridBagConstraints();
		gbc_panel_2.anchor = GridBagConstraints.WEST;
		gbc_panel_2.insets = new Insets(0, 0, 5, 0);
		gbc_panel_2.gridx = 1;
		gbc_panel_2.gridy = 0;
		autoPilotInputConfigPanel.add(panel_2, gbc_panel_2);
		GridBagLayout gbl_panel_2 = new GridBagLayout();
		gbl_panel_2.columnWidths = new int[]{0, 0};
		gbl_panel_2.rowHeights = new int[]{0, 0};
		gbl_panel_2.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panel_2.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panel_2.setLayout(gbl_panel_2);
		
		destinationLatTextField = new JTextField();
		destinationLatTextField.setEnabled(false);
		GridBagConstraints gbc_destinationLatTextField = new GridBagConstraints();
		gbc_destinationLatTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_destinationLatTextField.gridx = 0;
		gbc_destinationLatTextField.gridy = 0;
		panel_2.add(destinationLatTextField, gbc_destinationLatTextField);
		destinationLatTextField.setColumns(15);
		
		longitudeLabel = new JLabel("Destination Point - Longitude  :  ");
		longitudeLabel.setEnabled(false);
		GridBagConstraints gbc_longitudeLabel = new GridBagConstraints();
		gbc_longitudeLabel.anchor = GridBagConstraints.EAST;
		gbc_longitudeLabel.insets = new Insets(0, 0, 0, 5);
		gbc_longitudeLabel.gridx = 0;
		gbc_longitudeLabel.gridy = 1;
		autoPilotInputConfigPanel.add(longitudeLabel, gbc_longitudeLabel);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		GridBagConstraints gbc_panel_3 = new GridBagConstraints();
		gbc_panel_3.anchor = GridBagConstraints.WEST;
		gbc_panel_3.gridx = 1;
		gbc_panel_3.gridy = 1;
		autoPilotInputConfigPanel.add(panel_3, gbc_panel_3);
		GridBagLayout gbl_panel_3 = new GridBagLayout();
		gbl_panel_3.columnWidths = new int[]{0, 0};
		gbl_panel_3.rowHeights = new int[]{0, 0};
		gbl_panel_3.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panel_3.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panel_3.setLayout(gbl_panel_3);
		
		destinationLonTextField = new JTextField();
		destinationLonTextField.setEnabled(false);
		GridBagConstraints gbc_destinationLonTextField = new GridBagConstraints();
		gbc_destinationLonTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_destinationLonTextField.gridx = 0;
		gbc_destinationLonTextField.gridy = 0;
		panel_3.add(destinationLonTextField, gbc_destinationLonTextField);
		destinationLonTextField.setColumns(15);
		
		JPanel autoPilotButtonPanel = new JPanel();
		autoPilotButtonPanel.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		GridBagConstraints gbc_autoPilotButtonPanel = new GridBagConstraints();
		gbc_autoPilotButtonPanel.gridwidth = 2;
		gbc_autoPilotButtonPanel.insets = new Insets(0, 0, 5, 0);
		gbc_autoPilotButtonPanel.anchor = GridBagConstraints.NORTH;
		gbc_autoPilotButtonPanel.gridx = 0;
		gbc_autoPilotButtonPanel.gridy = 1;
		autoPilotConfigpanel.add(autoPilotButtonPanel, gbc_autoPilotButtonPanel);
		GridBagLayout gbl_autoPilotButtonPanel = new GridBagLayout();
		gbl_autoPilotButtonPanel.columnWidths = new int[]{0, 0};
		gbl_autoPilotButtonPanel.rowHeights = new int[]{0, 0};
		gbl_autoPilotButtonPanel.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_autoPilotButtonPanel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		autoPilotButtonPanel.setLayout(gbl_autoPilotButtonPanel);
		
		saveAutoPilotConfigButton = new JButton("Save");
		saveAutoPilotConfigButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				pickLatLonFromMap = false;
			}
		});
		saveAutoPilotConfigButton.setEnabled(false);
		GridBagConstraints gbc_saveAutoPilotConfigButton = new GridBagConstraints();
		gbc_saveAutoPilotConfigButton.gridx = 0;
		gbc_saveAutoPilotConfigButton.gridy = 0;
		autoPilotButtonPanel.add(saveAutoPilotConfigButton, gbc_saveAutoPilotConfigButton);
		
		btnPickFromMap = new JToggleButton("Pick From Map");
		btnPickFromMap.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(btnPickFromMap.isSelected()) {
					destinationLatTextField.setEditable(false);
					destinationLonTextField.setEditable(false);
					pickLatLonFromMap = true;
				} else {
					destinationLatTextField.setEditable(true);
					destinationLonTextField.setEditable(true);
					pickLatLonFromMap = false;
				}
			}
		});
		btnPickFromMap.setEnabled(false);
		GridBagConstraints gbc_btnPickFromMap = new GridBagConstraints();
		gbc_btnPickFromMap.fill = GridBagConstraints.BOTH;
		gbc_btnPickFromMap.anchor = GridBagConstraints.WEST;
		gbc_btnPickFromMap.gridx = 1;
		gbc_btnPickFromMap.gridy = 0;
		autoPilotConfigpanel.add(btnPickFromMap, gbc_btnPickFromMap);
	}

	public void setTransmitter(Transmitter transmitter) {
		this.transmitter = transmitter;
	}

	public void enableAutoPilotButton() {
		autoPilotPanel.setEnabled(true);
		autoPilotButton.setSelected(false);
		autoPilotButton.setEnabled(true);
	}

	public void disableAutoPilotButton() {
		autoPilotPanel.setEnabled(false);
		autoPilotButton.setSelected(false);
		autoPilotButton.setEnabled(false);
	}
}

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
		
		JPanel autoPilotPanel = new JPanel();
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
		
		final JToggleButton turnOnAutoPilotButton = new JToggleButton("Turn On Auto Pilot");
		turnOnAutoPilotButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (turnOnAutoPilotButton.isSelected()) {
					turnOnAutoPilotButton.setText("Turn Off Auto Pilot");
					saveAutoPilotConfigButton.setEnabled(true);
					destinationLatTextField.setEnabled(true);
					destinationLonTextField.setEnabled(true);
					destinationLatTextField.setEditable(true);
					destinationLonTextField.setEditable(true);
					btnPickFromMap.setEnabled(true);
				} else {
					turnOnAutoPilotButton.setText("Turn On Auto Pilot");
					saveAutoPilotConfigButton.setEnabled(false);
					destinationLatTextField.setEnabled(false);
					destinationLonTextField.setEnabled(false);
					destinationLatTextField.setEditable(false);
					destinationLonTextField.setEditable(false);
					btnPickFromMap.setEnabled(false);
					btnPickFromMap.setSelected(false);
				}
			}
		});
		GridBagConstraints gbc_turnOnAutoPilotButton = new GridBagConstraints();
		gbc_turnOnAutoPilotButton.insets = new Insets(0, 0, 5, 0);
		gbc_turnOnAutoPilotButton.gridx = 0;
		gbc_turnOnAutoPilotButton.gridy = 0;
		autoPilotPanel.add(turnOnAutoPilotButton, gbc_turnOnAutoPilotButton);
		
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
		
		JLabel lblDestinationPoint = new JLabel("Destination Point - Lattitute  :  ");
		GridBagConstraints gbc_lblDestinationPoint = new GridBagConstraints();
		gbc_lblDestinationPoint.anchor = GridBagConstraints.EAST;
		gbc_lblDestinationPoint.insets = new Insets(0, 0, 5, 5);
		gbc_lblDestinationPoint.gridx = 0;
		gbc_lblDestinationPoint.gridy = 0;
		autoPilotInputConfigPanel.add(lblDestinationPoint, gbc_lblDestinationPoint);
		
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
		
		JLabel lblNewLabel = new JLabel("Destination Point - Longitute  :  ");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel.insets = new Insets(0, 0, 0, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 1;
		autoPilotInputConfigPanel.add(lblNewLabel, gbc_lblNewLabel);
		
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
				} else {
					destinationLatTextField.setEditable(true);
					destinationLonTextField.setEditable(true);
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
}

package com.crana.qcontroller.ui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;

import com.crana.qcontroller.domain.DeviceConfig;
import com.crana.qcontroller.service.txrx.QuadCopterLocator;
import com.crana.qcontroller.service.txrx.Transmitter;

import java.awt.Insets;

public class CommandPanel extends JPanel {
	private VisualCommandPanel visualCommandPanel;
	private DrivingCommandPanel drivingCommandPanel;
	private AutoPilotCommandPanel autoPilotCommandPanel;
	private QuadCopterPickerPanel quadCopterPickerPanel;
	private DeviceConfig myDeviceConfig;
	/**
	 * Create the panel.
	 */
	public CommandPanel() {
		GridBagLayout gbl_commandPanel = new GridBagLayout();
		gbl_commandPanel.columnWidths = new int[]{0, 0};
		gbl_commandPanel.rowHeights = new int[]{0, 0, 0, 0, 0};
		gbl_commandPanel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_commandPanel.rowWeights = new double[]{1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		setLayout(gbl_commandPanel);
		
		quadCopterPickerPanel = new QuadCopterPickerPanel(this);
		GridBagConstraints gbc_quadCopterPickerPanel = new GridBagConstraints();
		gbc_quadCopterPickerPanel.anchor = GridBagConstraints.NORTHWEST;
		gbc_quadCopterPickerPanel.insets = new Insets(0, 0, 5, 0);
		gbc_quadCopterPickerPanel.fill = GridBagConstraints.BOTH;
		gbc_quadCopterPickerPanel.gridx = 0;
		gbc_quadCopterPickerPanel.gridy = 0;
		add(quadCopterPickerPanel, gbc_quadCopterPickerPanel);
		
		autoPilotCommandPanel = new AutoPilotCommandPanel();
		GridBagConstraints gbc_autoPilotCommandPanel = new GridBagConstraints();
		gbc_autoPilotCommandPanel.insets = new Insets(0, 0, 5, 0);
		gbc_autoPilotCommandPanel.fill = GridBagConstraints.HORIZONTAL;
		gbc_autoPilotCommandPanel.anchor = GridBagConstraints.NORTHWEST;
		gbc_autoPilotCommandPanel.gridx = 0;
		gbc_autoPilotCommandPanel.gridy = 1;
		add(autoPilotCommandPanel, gbc_autoPilotCommandPanel);
		
		drivingCommandPanel = new DrivingCommandPanel(this);
		GridBagConstraints gbc_drivingCommandPanel = new GridBagConstraints();
		gbc_drivingCommandPanel.insets = new Insets(0, 0, 5, 0);
		gbc_drivingCommandPanel.fill = GridBagConstraints.HORIZONTAL;
		gbc_drivingCommandPanel.anchor = GridBagConstraints.NORTHWEST;
		gbc_drivingCommandPanel.gridx = 0;
		gbc_drivingCommandPanel.gridy = 2;
		add(drivingCommandPanel, gbc_drivingCommandPanel);
		
		visualCommandPanel = new VisualCommandPanel();
		GridBagLayout gridBagLayout_1 = (GridBagLayout) visualCommandPanel.getLayout();
		gridBagLayout_1.rowHeights = new int[] {0};
		GridBagConstraints gbc_visualCommandPanel = new GridBagConstraints();
		gbc_visualCommandPanel.fill = GridBagConstraints.HORIZONTAL;
		gbc_visualCommandPanel.anchor = GridBagConstraints.NORTHWEST;
		gbc_visualCommandPanel.gridx = 0;
		gbc_visualCommandPanel.gridy = 3;
		add(visualCommandPanel, gbc_visualCommandPanel);
		disableDefaultControlls();
	}
	public CommandPanel(DeviceConfig myDeviceConfig) {
		this();
		this.myDeviceConfig = myDeviceConfig;
		quadCopterPickerPanel.setMyDeviceConfig(myDeviceConfig);
	}
	public void setMyDeviceConfig(DeviceConfig myDeviceConfig) {
		this.myDeviceConfig = myDeviceConfig;
		quadCopterPickerPanel.setMyDeviceConfig(myDeviceConfig);
	}
	public void setTransmitter(Transmitter transmitter) {
		drivingCommandPanel.setTransmitter(transmitter);
		visualCommandPanel.setTransmitter(transmitter);
		autoPilotCommandPanel.setTransmitter(transmitter);
	}
	public QuadCopterPickerPanel getQuadCopterPicker() {
		return quadCopterPickerPanel;
	}
	public void enableVisualCommands() {
		visualCommandPanel.setEnabled(true);
		visualCommandPanel.enableVisualCommandButtons();
	}
	public void disableVisualCommands() {
		visualCommandPanel.setEnabled(false);
		visualCommandPanel.disableVisualCommandButtons();
	}
	public void enableQuadCopterPicker() {
		quadCopterPickerPanel.setEnabled(true);
		quadCopterPickerPanel.enableQuadCopterPickerLabel();
	}
	public void disableQuadCopterPicker() {
		quadCopterPickerPanel.setEnabled(false);
		quadCopterPickerPanel.disableQuadCopterPickerLabel();
	}
	public void enableDefaultControlls() {
		drivingCommandPanel.setEnabled(true);
		autoPilotCommandPanel.setEnabled(true);
		autoPilotCommandPanel.enableAutoPilotButton();
		drivingCommandPanel.enableStartButton();

	}
	public void disableDefaultControlls() {
		visualCommandPanel.setEnabled(false);
		drivingCommandPanel.setEnabled(false);
		autoPilotCommandPanel.setEnabled(false);
		autoPilotCommandPanel.disableAutoPilotButton();
		drivingCommandPanel.disableStartButton();
	}
}

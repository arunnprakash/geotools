package com.crana.qcontroller.ui;

import javax.swing.JPanel;

import java.awt.GridBagLayout;

import javax.swing.JLabel;

import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;

import static com.crana.qcontroller.service.MyDeviceConfigKeyConstant.DEVICE_ID_KEY;
import static com.crana.qcontroller.service.MyDeviceConfigKeyConstant.DEVICE_NAME_KEY;

import com.crana.qcontroller.domain.DeviceConfig;

import java.awt.Color;

import javax.swing.ImageIcon;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Properties;
import java.util.UUID;
import javax.swing.JComboBox;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class MyDeviceConfigPanel extends JPanel {
	private JTextField deviceNameTextField;
	private JTextField deviceIdTextField;
	private DeviceConfig myDeviceConfig;
	private Boolean editButton = Boolean.TRUE;
	private JButton newDeviceIdButton;
	private JComboBox<DeviceConfig> neighbourDevicesComboBox;
	/**
	 * Create the panel.
	 */
	public MyDeviceConfigPanel() {
		setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWeights = new double[]{0.0};
		gridBagLayout.rowWeights = new double[]{0.0};
		setLayout(gridBagLayout);
		
		JPanel deviceConfigContentPanel = new JPanel();
		GridBagConstraints gbc_deviceConfigContentPanel = new GridBagConstraints();
		gbc_deviceConfigContentPanel.fill = GridBagConstraints.BOTH;
		gbc_deviceConfigContentPanel.gridx = 0;
		gbc_deviceConfigContentPanel.gridy = 0;
		add(deviceConfigContentPanel, gbc_deviceConfigContentPanel);
		GridBagLayout gbl_deviceConfigContentPanel = new GridBagLayout();
		gbl_deviceConfigContentPanel.columnWidths = new int[]{0, 0, 0};
		gbl_deviceConfigContentPanel.rowHeights = new int[]{0, 0, 0, 0, 0};
		gbl_deviceConfigContentPanel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_deviceConfigContentPanel.rowWeights = new double[]{0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		deviceConfigContentPanel.setLayout(gbl_deviceConfigContentPanel);
		
		JLabel lblDeviceName = new JLabel("Device Name  :  ");
		GridBagConstraints gbc_lblDeviceName = new GridBagConstraints();
		gbc_lblDeviceName.anchor = GridBagConstraints.EAST;
		gbc_lblDeviceName.insets = new Insets(0, 0, 5, 5);
		gbc_lblDeviceName.gridx = 0;
		gbc_lblDeviceName.gridy = 0;
		deviceConfigContentPanel.add(lblDeviceName, gbc_lblDeviceName);
		
		JPanel panel_1 = new JPanel();
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.anchor = GridBagConstraints.WEST;
		gbc_panel_1.gridx = 1;
		gbc_panel_1.gridy = 0;
		deviceConfigContentPanel.add(panel_1, gbc_panel_1);
		panel_1.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[]{0, 0};
		gbl_panel_1.rowHeights = new int[]{0, 0};
		gbl_panel_1.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panel_1.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panel_1.setLayout(gbl_panel_1);
		
		deviceNameTextField = new JTextField();
		deviceNameTextField.setEditable(false);
		GridBagConstraints gbc_deviceNameTextField = new GridBagConstraints();
		gbc_deviceNameTextField.fill = GridBagConstraints.BOTH;
		gbc_deviceNameTextField.gridx = 0;
		gbc_deviceNameTextField.gridy = 0;
		panel_1.add(deviceNameTextField, gbc_deviceNameTextField);
		deviceNameTextField.setColumns(35);
		
		JLabel lblDeviceId = new JLabel("Device Id  :  ");
		GridBagConstraints gbc_lblDeviceId = new GridBagConstraints();
		gbc_lblDeviceId.insets = new Insets(0, 0, 5, 5);
		gbc_lblDeviceId.anchor = GridBagConstraints.EAST;
		gbc_lblDeviceId.gridx = 0;
		gbc_lblDeviceId.gridy = 1;
		deviceConfigContentPanel.add(lblDeviceId, gbc_lblDeviceId);
		
		JPanel panel_2 = new JPanel();
		GridBagConstraints gbc_panel_2 = new GridBagConstraints();
		gbc_panel_2.fill = GridBagConstraints.BOTH;
		gbc_panel_2.anchor = GridBagConstraints.WEST;
		gbc_panel_2.gridx = 1;
		gbc_panel_2.gridy = 1;
		deviceConfigContentPanel.add(panel_2, gbc_panel_2);
		panel_2.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		GridBagLayout gbl_panel_2 = new GridBagLayout();
		gbl_panel_2.columnWidths = new int[] {0, 0};
		gbl_panel_2.rowHeights = new int[]{0, 0};
		gbl_panel_2.columnWeights = new double[]{1.0, 0.0};
		gbl_panel_2.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panel_2.setLayout(gbl_panel_2);
		
		deviceIdTextField = new JTextField();
		deviceIdTextField.setEditable(false);
		GridBagConstraints gbc_deviceIdTextField = new GridBagConstraints();
		gbc_deviceIdTextField.fill = GridBagConstraints.BOTH;
		gbc_deviceIdTextField.gridx = 0;
		gbc_deviceIdTextField.gridy = 0;
		panel_2.add(deviceIdTextField, gbc_deviceIdTextField);
		deviceIdTextField.setColumns(35);
		
		newDeviceIdButton = new JButton("GetNewId");
		GridBagConstraints gbc_newDeviceIdButton = new GridBagConstraints();
		gbc_newDeviceIdButton.gridx = 1;
		gbc_newDeviceIdButton.gridy = 0;
		panel_2.add(newDeviceIdButton, gbc_newDeviceIdButton);
		newDeviceIdButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deviceIdTextField.setText(UUID.randomUUID().toString());
			}
		});
		newDeviceIdButton.setVisible(false);
		
		JLabel lblMyNeighbourDevice = new JLabel("My Neighbour Device :  ");
		GridBagConstraints gbc_lblMyNeighbourDevice = new GridBagConstraints();
		gbc_lblMyNeighbourDevice.insets = new Insets(0, 0, 5, 5);
		gbc_lblMyNeighbourDevice.gridx = 0;
		gbc_lblMyNeighbourDevice.gridy = 2;
		deviceConfigContentPanel.add(lblMyNeighbourDevice, gbc_lblMyNeighbourDevice);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		GridBagConstraints gbc_panel_3 = new GridBagConstraints();
		gbc_panel_3.fill = GridBagConstraints.BOTH;
		gbc_panel_3.anchor = GridBagConstraints.WEST;
		gbc_panel_3.insets = new Insets(0, 0, 5, 0);
		gbc_panel_3.gridx = 1;
		gbc_panel_3.gridy = 2;
		deviceConfigContentPanel.add(panel_3, gbc_panel_3);
		GridBagLayout gbl_panel_3 = new GridBagLayout();
		gbl_panel_3.columnWidths = new int[]{0, 0, 0};
		gbl_panel_3.rowHeights = new int[]{0, 0};
		gbl_panel_3.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		gbl_panel_3.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panel_3.setLayout(gbl_panel_3);
		
		neighbourDevicesComboBox = new JComboBox<DeviceConfig>();
		neighbourDevicesComboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent itemEvent) {
				DeviceConfig myNeigbour = (DeviceConfig)itemEvent.getItem();
				myDeviceConfig.setMyNeigbour(myNeigbour);
			}
		});
		neighbourDevicesComboBox.setSize(deviceIdTextField.getSize());
		neighbourDevicesComboBox.setEnabled(false);
		GridBagConstraints gbc_neighbourDevicesComboBox = new GridBagConstraints();
		gbc_neighbourDevicesComboBox.fill = GridBagConstraints.BOTH;
		gbc_neighbourDevicesComboBox.weightx = 1.0;
		gbc_neighbourDevicesComboBox.gridx = 0;
		gbc_neighbourDevicesComboBox.gridy = 0;
		panel_3.add(neighbourDevicesComboBox, gbc_neighbourDevicesComboBox);
		
		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.gridwidth = 2;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 3;
		deviceConfigContentPanel.add(panel, gbc_panel);
		panel.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{57, 0};
		gbl_panel.rowHeights = new int[]{23, 0};
		gbl_panel.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		final JButton btnSave = new JButton("Edit");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (editButton) {
						btnSave.setText("Save");
						editButton = Boolean.FALSE;
						deviceNameTextField.setEditable(true);
						deviceIdTextField.setEditable(true);
						newDeviceIdButton.setVisible(true);
						neighbourDevicesComboBox.setEnabled(true);
					} else {
						btnSave.setText("Edit");
						editButton = Boolean.TRUE;
						deviceNameTextField.setEditable(false);
						deviceIdTextField.setEditable(false);
						newDeviceIdButton.setVisible(false);
						neighbourDevicesComboBox.setEnabled(false);
						boolean changesFound = false;
						String deviceName1 = deviceNameTextField.getText().trim();
						String deviceId1 = deviceIdTextField.getText().trim();
						changesFound = !myDeviceConfig.getDeviceName().equals(deviceName1) || !myDeviceConfig.getDeviceId().equals(deviceId1);
						if (changesFound) {
							myDeviceConfig.setDeviceName(deviceName1);
							myDeviceConfig.setDeviceId(deviceId1);
							File myDeviceConfigPropertyFile = new File(System.getProperty("user.home") + "\\my_device_default_config.properties");
				            OutputStream out = new FileOutputStream(myDeviceConfigPropertyFile);
				            Properties props = new Properties();
				            props.setProperty(DEVICE_NAME_KEY, deviceName1);
				            props.setProperty(DEVICE_ID_KEY, deviceId1);
				            props.store(out, "My Device Configuration");
				            out.close();
						}
					}
				} catch (Exception exp) {
					exp.printStackTrace();
				}

			}
		});
		GridBagConstraints gbc_btnSave = new GridBagConstraints();
		gbc_btnSave.gridx = 0;
		gbc_btnSave.gridy = 0;
		panel.add(btnSave, gbc_btnSave);
		btnSave.setSize(45, 25);
/*		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(MyDeviceConfigPanel.class.getResource("/com/crana/qcontroller/resources/images/DRONE_large.jpg")));
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.fill = GridBagConstraints.BOTH;
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 0;
		add(lblNewLabel, gbc_lblNewLabel);
		lblNewLabel.setVisible(false);*/

	}
	public MyDeviceConfigPanel(DeviceConfig myDeviceConfig) {
		this();
		this.myDeviceConfig = myDeviceConfig;
		populateMyDeviceConfig();
	}
	private void populateMyDeviceConfig() {
		if (myDeviceConfig != null) {
			deviceNameTextField.setText(myDeviceConfig.getDeviceName());
			deviceIdTextField.setText(myDeviceConfig.getDeviceId());
		}
	}
	public void setMyDeviceConfig(DeviceConfig myDeviceConfig) {
		this.myDeviceConfig = myDeviceConfig;
		populateMyDeviceConfig();
	}
	public void addNeighbourDevice(DeviceConfig deviceConfig) {
		myDeviceConfig.getDevices().put(deviceConfig.getDeviceId(), deviceConfig);
		neighbourDevicesComboBox.addItem(deviceConfig);
		setNeighbourDevice();
	}
	private void setNeighbourDevice() {
		DeviceConfig myNeigbour = null;
		for (String deviceId : myDeviceConfig.getDevices().keySet()) {
			DeviceConfig deviceConfig = myDeviceConfig.getDevices().get(deviceId);
			if (myNeigbour == null || deviceConfig.getDistance() < myNeigbour.getDistance()) {
				myNeigbour = deviceConfig;
			}
		}
		myDeviceConfig.setMyNeigbour(myNeigbour);
	}
}

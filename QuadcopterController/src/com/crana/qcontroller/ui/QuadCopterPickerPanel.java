package com.crana.qcontroller.ui;

import javax.swing.JPanel;

import java.awt.GridBagLayout;

import javax.swing.JLabel;

import java.awt.GridBagConstraints;

import javax.swing.border.TitledBorder;
import javax.swing.SwingConstants;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JComboBox;

import java.awt.Insets;

import javax.swing.JButton;

import com.crana.qcontroller.domain.DeviceConfig;
import com.crana.qcontroller.domain.DeviceLocomotionType;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.LineBorder;

public class QuadCopterPickerPanel extends JPanel {
	private JLabel lblYouAreGoing;
	private JLabel lblpickYourQuadcopter;
	private JComboBox<DeviceConfig> comboBox;
	private JButton btnDone;
	private DeviceConfig myDeviceConfig;
	/**
	 * Create the panel.
	 */
	public QuadCopterPickerPanel() {
		setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "QuadCopter Picker", TitledBorder.LEFT, TitledBorder.TOP, null, new Color(0, 0, 0)));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 1.0, 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0 };
		setLayout(gridBagLayout);

		lblpickYourQuadcopter = new JLabel("<html><body><u>Pick Your QuadCopter</u></body></html>");
		lblpickYourQuadcopter.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				lblpickYourQuadcopter.setVisible(false);
				comboBox.setVisible(true);
				btnDone.setVisible(true);
				lblYouAreGoing.setVisible(false);
			}
		});

		lblYouAreGoing = new JLabel("You are Going to Drive ");
		lblYouAreGoing.setHorizontalAlignment(SwingConstants.CENTER);
		lblYouAreGoing.setForeground(Color.BLUE);
		lblYouAreGoing.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblYouAreGoing.setVisible(false);
		GridBagConstraints gbc_lblYouAreGoing = new GridBagConstraints();
		gbc_lblYouAreGoing.weighty = 1.0;
		gbc_lblYouAreGoing.weightx = 1.0;
		gbc_lblYouAreGoing.fill = GridBagConstraints.BOTH;
		gbc_lblYouAreGoing.insets = new Insets(10, 0, 5, 0);
		gbc_lblYouAreGoing.gridwidth = 2;
		gbc_lblYouAreGoing.gridx = 0;
		gbc_lblYouAreGoing.gridy = 0;
		add(lblYouAreGoing, gbc_lblYouAreGoing);
		lblpickYourQuadcopter.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblpickYourQuadcopter.setForeground(Color.RED);
		lblpickYourQuadcopter.setHorizontalAlignment(SwingConstants.CENTER);
		lblpickYourQuadcopter.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		GridBagConstraints gbc_lblpickYourQuadcopter = new GridBagConstraints();
		gbc_lblpickYourQuadcopter.gridwidth = 2;
		gbc_lblpickYourQuadcopter.insets = new Insets(0, 0, 5, 0);
		gbc_lblpickYourQuadcopter.weighty = 1.0;
		gbc_lblpickYourQuadcopter.weightx = 1.0;
		gbc_lblpickYourQuadcopter.fill = GridBagConstraints.BOTH;
		gbc_lblpickYourQuadcopter.gridx = 0;
		gbc_lblpickYourQuadcopter.gridy = 2;
		add(lblpickYourQuadcopter, gbc_lblpickYourQuadcopter);

		comboBox = new JComboBox<DeviceConfig>();
		comboBox.setVisible(false);
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.insets = new Insets(20, 20, 20, 0);
		gbc_comboBox.anchor = GridBagConstraints.EAST;
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = 0;
		gbc_comboBox.gridy = 3;
		add(comboBox, gbc_comboBox);

		btnDone = new JButton("Done");
		btnDone.setVisible(false);
		btnDone.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				comboBox.setVisible(false);
				btnDone.setVisible(false);
				lblYouAreGoing.setVisible(true);
				lblpickYourQuadcopter.setVisible(true);
				lblpickYourQuadcopter.setText("<html><body><u>Let Me Pick Another QuadCopter</u></body></html>");
				DeviceConfig deviceConfig = (DeviceConfig)comboBox.getSelectedItem();
				lblYouAreGoing.setText("You are Going to Drive " + deviceConfig.getDeviceName());
				myDeviceConfig.setEdgeTerminalDevice(deviceConfig);
			}
		});
		GridBagConstraints gbc_btnDone = new GridBagConstraints();
		gbc_btnDone.insets = new Insets(0, 0, 0, 20);
		gbc_btnDone.anchor = GridBagConstraints.WEST;
		gbc_btnDone.gridx = 1;
		gbc_btnDone.gridy = 3;
		add(btnDone, gbc_btnDone);
	}
	public void setMyDeviceConfig(DeviceConfig myDeviceConfig) {
		this.myDeviceConfig = myDeviceConfig;
	}
	public void updateQuadCopterList() {
		comboBox.removeAllItems();
		for (String deviceId : myDeviceConfig.getDevices().keySet()) {
			DeviceConfig deviceConfig = myDeviceConfig.getDevices().get(deviceId);
			if (deviceConfig.getLocomotionType().equals(DeviceLocomotionType.DYNAMIC)) {
				comboBox.addItem(deviceConfig);
			}
		}
	}
}

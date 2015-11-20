package com.crana.qcontroller.ui;

import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.border.EmptyBorder;

import static com.crana.qcontroller.service.MyDeviceConfigKeyConstant.DEVICE_ID_KEY;
import static com.crana.qcontroller.service.MyDeviceConfigKeyConstant.DEVICE_IS_BASE_STATION_KEY;
import static com.crana.qcontroller.service.MyDeviceConfigKeyConstant.DEVICE_LOCOMOTION_TYPE_KEY;
import static com.crana.qcontroller.service.MyDeviceConfigKeyConstant.DEVICE_NAME_KEY;
import static com.crana.qcontroller.service.MyDeviceConfigKeyConstant.DEVICE_LATITUDE_KEY;
import static com.crana.qcontroller.service.MyDeviceConfigKeyConstant.DEVICE_LONGITUDE_KEY;

import com.crana.qcontroller.domain.DeviceConfig;
import com.crana.qcontroller.domain.DeviceLocomotionType;
import com.crana.qcontroller.domain.GpsLocation;
import com.crana.qcontroller.service.Command;
import com.crana.qcontroller.service.txrx.Receiver;
import com.crana.qcontroller.service.txrx.Transmitter;
import com.crana.qcontroller.service.txrx.impl.DefaultReceiver;
import com.crana.qcontroller.service.txrx.impl.DefaultTransmitter;

import javax.swing.SwingConstants;

public class RPIAppLauncer extends JFrame {

	private JPanel contentPane;
	private JProgressBar progressBar;
	private JLabel statusLabel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		final RPIAppLauncer appLauncer = new RPIAppLauncer();
		appLauncer.setVisible(true);
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					appLauncer.setStatus("Loading My Device Configuration...");
					DeviceConfig myDeviceConfig = loadMyDeviceConfig();
					appLauncer.setStatus("Loading QuadCopter Controller Window...");
					ControllerMainWindow window = createQControllerMainWindow(myDeviceConfig);
					appLauncer.setProgress(30);
					appLauncer.setStatus("Initialize Transmitter...");
					
					Transmitter transmitter = initTransmitter(myDeviceConfig, window);
					waitForTransmitterIsToReady(transmitter);
					appLauncer.setProgress(50);
					
					appLauncer.setStatus("Initialize Receiver...");
					Receiver receiver = initReceiver(myDeviceConfig, transmitter, window);
					waitForReceiverIsToReady(receiver);
					appLauncer.setProgress(70);
					
					appLauncer.setStatus("BroadCasting My Device Configuration");
					broadCastMyDeviceConfig(transmitter);
					appLauncer.setProgress(90);
					window.getMainWindowFrame().setVisible(true);
					appLauncer.setProgress(100);
					appLauncer.setVisible(false);
					initDynamicGpsThread(myDeviceConfig);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			private void initDynamicGpsThread(final DeviceConfig myDeviceConfig) {
				Thread thread = new Thread() {
					public void run() {
						try {
							double latitude = myDeviceConfig.getGpsLocation().getLatitude();
							double longitude = myDeviceConfig.getGpsLocation().getLongitude();
							while (true) {
								Thread.sleep(10000L);
								latitude = latitude + 0.2d;
								longitude = longitude + 0.018d;
								myDeviceConfig.getGpsLocation().setLatitude(latitude);
								myDeviceConfig.getGpsLocation().setLongitude(longitude);
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
						
					}
				};
				thread.start();
			}
			private void broadCastMyDeviceConfig(Transmitter transmitter) {
				transmitter.transmit(Command.INVITE);
			}

			private void waitForReceiverIsToReady(Receiver receiver) throws Exception {
				while (!receiver.isReady()) {
					Thread.sleep(1000);
				}
			}
			private void waitForTransmitterIsToReady(Transmitter transmitter) throws Exception {
				while (!transmitter.isReady()) {
					Thread.sleep(1000);
				}
			}
			private DeviceConfig loadMyDeviceConfig() throws Exception {
				DeviceConfig myDeviceConfig = new DeviceConfig();
				InputStream is = null;
				File myDeviceConfigPropertyFile = new File(System.getProperty("user.home") + "\\rpi_device_default_config.properties");
				if (myDeviceConfigPropertyFile.exists()) {
					is = new FileInputStream(myDeviceConfigPropertyFile);
				} else {
					is = this.getClass().getResourceAsStream("/com/crana/qcontroller/resources/rpi_device_default_config.properties");
				}
				Properties props = new Properties();
				props.load(is);
				myDeviceConfig.setDeviceName(props.getProperty(DEVICE_NAME_KEY));
				myDeviceConfig.setDeviceId(props.getProperty(DEVICE_ID_KEY));
				myDeviceConfig.setBaseStation(Boolean.parseBoolean(props.getProperty(DEVICE_IS_BASE_STATION_KEY)));
				GpsLocation gpsLocation = new GpsLocation();
				gpsLocation.setLatitude(Double.parseDouble(props.getProperty(DEVICE_LATITUDE_KEY)));
				gpsLocation.setLongitude(Double.parseDouble(props.getProperty(DEVICE_LONGITUDE_KEY)));
				myDeviceConfig.setGpsLocation(gpsLocation);
				myDeviceConfig.setLocomotionType(DeviceLocomotionType.valueOf(props.getProperty(DEVICE_LOCOMOTION_TYPE_KEY)));
				is.close();
				return myDeviceConfig;
			}
			private ControllerMainWindow createQControllerMainWindow(DeviceConfig myDeviceConfig) throws Exception {
				return new ControllerMainWindow(myDeviceConfig);
			}
			private Transmitter initTransmitter(DeviceConfig myDeviceConfig, ControllerUI controllerUI) throws Exception {
				Transmitter transmitter = new DefaultTransmitter(myDeviceConfig, controllerUI);
				transmitter.startTransmitter();
				controllerUI.setTransmitter(transmitter);
				return transmitter;
			}
			private Receiver initReceiver(DeviceConfig myDeviceConfig, Transmitter transmitter, ControllerUI controllerUI) throws Exception {
				Receiver receiver = new DefaultReceiver(myDeviceConfig, transmitter, controllerUI);
				receiver.startReceiver();
				return receiver;
			}
		});
	}

	protected void setStatus(String statusMessage) {
		statusLabel.setText(statusMessage);
		this.update(this.getGraphics());
	}

	protected void setProgress(int progress) {
		progressBar.setValue(progress);
		progressBar.update(progressBar.getGraphics());
	}

	/**
	 * Create the frame.
	 */
	public RPIAppLauncer() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(500, 250);
		setLocationRelativeTo(null);
		setUndecorated(true);
		setAlwaysOnTop(true);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] {0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{0.0};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		statusLabel = new JLabel("");
		statusLabel.setVerticalAlignment(SwingConstants.TOP);
		GridBagConstraints gbc_statusLabel = new GridBagConstraints();
		gbc_statusLabel.insets = new Insets(0, 0, 5, 0);
		gbc_statusLabel.weighty = 1.0;
		gbc_statusLabel.weightx = 1.0;
		gbc_statusLabel.fill = GridBagConstraints.BOTH;
		gbc_statusLabel.gridx = 0;
		gbc_statusLabel.gridy = 0;
		contentPane.add(statusLabel, gbc_statusLabel);
		
		progressBar = new JProgressBar();
		progressBar.setValue(0);
		progressBar.setStringPainted(true);
		progressBar.setMaximum(100);
		GridBagConstraints gbc_progressBar = new GridBagConstraints();
		gbc_progressBar.insets = new Insets(0, 2, 5, 2);
		gbc_progressBar.fill = GridBagConstraints.HORIZONTAL;
		gbc_progressBar.gridx = 0;
		gbc_progressBar.gridy = 1;
		contentPane.add(progressBar, gbc_progressBar);
		
		JLabel imageLabel = new JLabel("");
		imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
		//imageLabel.setIcon(new ImageIcon(AppLauncher.class.getResource("/com/crana/qcontroller/resources/images/DRONE_small.jpg")));
		GridBagConstraints gbc_imageLabel = new GridBagConstraints();
		gbc_imageLabel.weighty = 1.0;
		gbc_imageLabel.weightx = 1.0;
		gbc_imageLabel.gridheight = 2;
		gbc_imageLabel.fill = GridBagConstraints.BOTH;
		gbc_imageLabel.gridx = 0;
		gbc_imageLabel.gridy = 0;
		contentPane.add(imageLabel, gbc_imageLabel);
		contentPane.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
	}

}

package com.crana.qcontroller.ui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import com.crana.qcontroller.domain.DeviceConfig;
import com.crana.qcontroller.service.txrx.Transmitter;

/**
 * @author ArunPrakash
 *
 */
public class QControllerMainWindow {

	private JFrame mainWindowFrame;
	private TxRxLogPanel txRxLogPanel;
	private MapPanel mapPanel;
	private MyDeviceConfigPanel myDeviceConfigPanel;
	private DeviceConfig myDeviceConfig;

	/**
	 * Create the application.
	 * @throws Exception 
	 */
	public QControllerMainWindow() throws Exception {
		initialize();
	}

	public QControllerMainWindow(DeviceConfig myDeviceConfig) throws Exception {
		this();
		this.myDeviceConfig = myDeviceConfig;
		myDeviceConfigPanel.setMyDeviceConfig(myDeviceConfig);
		mapPanel.setMyDeviceConfig(myDeviceConfig);
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws IOException 
	 */
	private void initialize() throws IOException {
		mainWindowFrame = new JFrame();
		mainWindowFrame.setAlwaysOnTop(false);
		//mainWindowFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		mainWindowFrame.setBounds(50, 50, (int) dim.getWidth() - 50, (int) dim.getHeight() - 50);
		mainWindowFrame.setTitle("MyMap");
		mainWindowFrame.setLocationRelativeTo(null);
		mainWindowFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		mainWindowFrame.getContentPane().setLayout(gridBagLayout);
		
		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 0;
		mainWindowFrame.getContentPane().add(panel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 0};
		gbl_panel.rowHeights = new int[]{0, 0};
		gbl_panel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		GridBagConstraints gbc_tabbedPane = new GridBagConstraints();
		gbc_tabbedPane.fill = GridBagConstraints.BOTH;
		gbc_tabbedPane.gridx = 0;
		gbc_tabbedPane.gridy = 0;
		panel.add(tabbedPane, gbc_tabbedPane);
		
		myDeviceConfigPanel = new MyDeviceConfigPanel(myDeviceConfig);
		tabbedPane.addTab("My Device Config", null, myDeviceConfigPanel, null);
		
		mapPanel = new MapPanel();
		tabbedPane.addTab("MapView", null, mapPanel, null);
		
		txRxLogPanel = new TxRxLogPanel();
		tabbedPane.addTab("RxTxLog", null, txRxLogPanel, null);
	}

	public JFrame getMainWindowFrame() {
		return mainWindowFrame;
	}

	public TxRxLogPanel getTxRxLogger() {
		return txRxLogPanel;
	}

	public void setTransmitter(Transmitter transmitter) {
		mapPanel.setTransmitter(transmitter);
	}

	public void addNeighbourDevice(DeviceConfig deviceConfig) {
		myDeviceConfigPanel.addNeighbourDevice(deviceConfig);
		mapPanel.getCommandPanel().getQuadCopterPicker().updateQuadCopterList();
	}
}

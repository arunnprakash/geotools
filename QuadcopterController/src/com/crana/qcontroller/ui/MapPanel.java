package com.crana.qcontroller.ui;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.SoftBevelBorder;

import org.geotools.geometry.jts.ReferencedEnvelope;
import org.geotools.map.MapContent;
import org.geotools.renderer.lite.StreamingRenderer;
import org.geotools.swing.JMapPane;
import org.geotools.swing.action.PanAction;
import org.geotools.swing.action.ZoomInAction;
import org.geotools.swing.action.ZoomOutAction;
import org.geotools.swing.event.MapMouseEvent;

import com.crana.qcontroller.domain.DeviceConfig;
import com.crana.qcontroller.service.txrx.Transmitter;

/**
 * @author ArunPrakash
 *
 */
public class MapPanel extends JPanel {
	private CommandPanel commandPanel;
	private DeviceConfig myDeviceConfig;
	/**
	 * Create the panel.
	 * 
	 * @throws IOException
	 */
	public MapPanel() throws IOException {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 1.0, 1.0, 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 1.0, 1.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);

		JPanel mapCoveringPanel = new JPanel();
		mapCoveringPanel.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		GridBagConstraints gbc_mapCoveringPanel = new GridBagConstraints();
		gbc_mapCoveringPanel.insets = new Insets(0, 2, 0, 2);
		gbc_mapCoveringPanel.fill = GridBagConstraints.BOTH;
		gbc_mapCoveringPanel.gridx = 0;
		gbc_mapCoveringPanel.gridy = 1;
		add(mapCoveringPanel, gbc_mapCoveringPanel);
		GridBagLayout gbl_mapCoveringPanel = new GridBagLayout();
		gbl_mapCoveringPanel.columnWidths = new int[] { 0, 0 };
		gbl_mapCoveringPanel.rowHeights = new int[] { 0, 0, 0 };
		gbl_mapCoveringPanel.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_mapCoveringPanel.rowWeights = new double[] { 1.0, 1.0, Double.MIN_VALUE };
		mapCoveringPanel.setLayout(gbl_mapCoveringPanel);
		JPanel mapButtonPanel = new JPanel();
		mapButtonPanel.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		GridBagConstraints gbc_mapButtonPanel = new GridBagConstraints();
		gbc_mapButtonPanel.weightx = 30.0;
		gbc_mapButtonPanel.gridx = 0;
		gbc_mapButtonPanel.gridy = 0;
		add(mapButtonPanel, gbc_mapButtonPanel);
		GridBagLayout gbl_mapButtonPanel = new GridBagLayout();
		gbl_mapButtonPanel.columnWidths = new int[] { 0, 0, 0, 0 };
		gbl_mapButtonPanel.rowHeights = new int[] { 0, 0 };
		gbl_mapButtonPanel.columnWeights = new double[] { 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_mapButtonPanel.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		mapButtonPanel.setLayout(gbl_mapButtonPanel);

		MapContent mapContent = new MapContent();
		MapLoader mapLoader = new MapLoader(mapContent);
		mapLoader.loadMap();

		
		final JMapPane mapPane = new JMapPane();
		final double clickToZoom = 0.1;
		/*final double minX = 66.28697130720609;
		final double maxX = 90.11019382452594;
		final double minY = 30.06536495575221;
		final double maxY = 4.638656307458902;
		ReferencedEnvelope initialArea = new ReferencedEnvelope(minX, maxX,
				minY, maxY, mapPane.getDisplayArea().getCoordinateReferenceSystem());
				mapPane.setDisplayArea(initialArea);*/
		
		GridBagConstraints gbc_mapPane = new GridBagConstraints();
		gbc_mapPane.weighty = 1.0;
		gbc_mapPane.weightx = 1.0;
		gbc_mapPane.fill = GridBagConstraints.BOTH;
		gbc_mapPane.gridheight = 2;
		gbc_mapPane.gridx = 0;
		gbc_mapPane.gridy = 0;
		mapCoveringPanel.add(mapPane, gbc_mapPane);
		mapPane.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				MapMouseEvent mme = mapPane.getMouseEventDispatcher().convertEvent(e);
				double longitute = mme.getWorldPos().getX();
				double latitute = mme.getWorldPos().getY();
				System.out.println("latitute:: " + latitute);
				System.out.println("longitute:: " + longitute);
			}
		});
		mapPane.addMouseWheelListener(new MouseWheelListener() {
			public void mouseWheelMoved(MouseWheelEvent e) {
				int clicks = e.getWheelRotation();
				int sign = clicks < 0 ? -1 : 1;
				ReferencedEnvelope currentArea = mapPane.getDisplayArea();
				double width = currentArea.getWidth();
				double delta = width * clickToZoom * sign;
				currentArea.expandBy(delta);
				mapPane.setDisplayArea(currentArea);
			}
		});
		mapPane.setRenderer(new StreamingRenderer());
		mapPane.setMapContent(mapContent);
		JButton zoomInButton = new JButton(new ZoomInAction(mapPane));

		JButton zoomOutButton = new JButton(new ZoomOutAction(mapPane));

		JButton panButton = new JButton(new PanAction(mapPane));

		zoomInButton.setText("Zoom In");
		GridBagConstraints gbc_zoomInButton = new GridBagConstraints();
		gbc_zoomInButton.anchor = GridBagConstraints.EAST;
		gbc_zoomInButton.gridx = 0;
		gbc_zoomInButton.gridy = 0;
		mapButtonPanel.add(zoomInButton, gbc_zoomInButton);
		zoomOutButton.setText("Zoom Out");
		GridBagConstraints gbc_zoomOutButton = new GridBagConstraints();
		gbc_zoomOutButton.gridx = 1;
		gbc_zoomOutButton.gridy = 0;
		mapButtonPanel.add(zoomOutButton, gbc_zoomOutButton);
		panButton.setText("Move");
		GridBagConstraints gbc_panButton = new GridBagConstraints();
		gbc_panButton.anchor = GridBagConstraints.WEST;
		gbc_panButton.gridx = 2;
		gbc_panButton.gridy = 0;
		mapButtonPanel.add(panButton, gbc_panButton);

		commandPanel = new CommandPanel(myDeviceConfig);
		GridBagConstraints gbc_commandPanel = new GridBagConstraints();
		gbc_commandPanel.anchor = GridBagConstraints.EAST;
		gbc_commandPanel.gridx = 1;
		gbc_commandPanel.gridy = 1;
		add(commandPanel, gbc_commandPanel);

	}
	public MapPanel(DeviceConfig myDeviceConfig) throws IOException {
		this();
		this.myDeviceConfig = myDeviceConfig;
	}
	public void setMyDeviceConfig(DeviceConfig myDeviceConfig) {
		this.myDeviceConfig = myDeviceConfig;
		commandPanel.setMyDeviceConfig(myDeviceConfig);
	}
	public void setTransmitter(Transmitter transmitter) {
		commandPanel.setTransmitter(transmitter);
	}

	public CommandPanel getCommandPanel() {
		return commandPanel;
	}
}

package com.crana.qcontroller.ui;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import javax.swing.JButton;

public class TxRxLogPanel extends JPanel {
	private JTextPane txLogTextPane;
	private JTextPane rxLogTextPane;

	/**
	 * Create the panel.
	 */
	public TxRxLogPanel() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{450, 0};
		gridBagLayout.rowHeights = new int[]{142, 147, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JPanel txLogPanel = new JPanel();
		txLogPanel.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 1, true), "Transmitter Log", TitledBorder.LEFT, TitledBorder.TOP, null, new Color(0, 0, 0)));
		GridBagConstraints gbc_txLogPanel = new GridBagConstraints();
		gbc_txLogPanel.weighty = 1.0;
		gbc_txLogPanel.fill = GridBagConstraints.BOTH;
		gbc_txLogPanel.insets = new Insets(0, 0, 5, 0);
		gbc_txLogPanel.gridx = 0;
		gbc_txLogPanel.gridy = 0;
		add(txLogPanel, gbc_txLogPanel);
		GridBagLayout gbl_txLogPanel = new GridBagLayout();
		gbl_txLogPanel.columnWidths = new int[]{450, 0};
		gbl_txLogPanel.rowHeights = new int[]{142, 0, 0};
		gbl_txLogPanel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_txLogPanel.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		txLogPanel.setLayout(gbl_txLogPanel);
		
		JScrollPane txLogScrollPane = new JScrollPane();
		GridBagConstraints gbc_txLogScrollPane = new GridBagConstraints();
		gbc_txLogScrollPane.weighty = 1.0;
		gbc_txLogScrollPane.fill = GridBagConstraints.BOTH;
		gbc_txLogScrollPane.gridx = 0;
		gbc_txLogScrollPane.gridy = 0;
		txLogPanel.add(txLogScrollPane, gbc_txLogScrollPane);
		
		txLogTextPane = new JTextPane();
		txLogTextPane.setEditable(false);
		txLogScrollPane.setViewportView(txLogTextPane);
		
		JPanel panel = new JPanel();
		panel.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 1;
		txLogPanel.add(panel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] {0};
		gbl_panel.rowHeights = new int[]{0, 0};
		gbl_panel.columnWeights = new double[]{0.0, 0.0};
		gbl_panel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		JButton btnSave = new JButton("Save");
		GridBagConstraints gbc_btnSave = new GridBagConstraints();
		gbc_btnSave.anchor = GridBagConstraints.WEST;
		gbc_btnSave.gridx = 0;
		gbc_btnSave.gridy = 0;
		panel.add(btnSave, gbc_btnSave);
		
		JButton btnClear = new JButton("Clear");
		GridBagConstraints gbc_btnClear = new GridBagConstraints();
		gbc_btnClear.anchor = GridBagConstraints.EAST;
		gbc_btnClear.gridx = 1;
		gbc_btnClear.gridy = 0;
		panel.add(btnClear, gbc_btnClear);
		
		JPanel rxLogPanel = new JPanel();
		rxLogPanel.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 1, true), "Receiver Log", TitledBorder.LEFT, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_rxLogPanel = new GridBagConstraints();
		gbc_rxLogPanel.weighty = 1.0;
		gbc_rxLogPanel.fill = GridBagConstraints.BOTH;
		gbc_rxLogPanel.gridx = 0;
		gbc_rxLogPanel.gridy = 1;
		add(rxLogPanel, gbc_rxLogPanel);
		GridBagLayout gbl_rxLogPanel = new GridBagLayout();
		gbl_rxLogPanel.columnWidths = new int[]{450, 0};
		gbl_rxLogPanel.rowHeights = new int[]{147, 0, 0};
		gbl_rxLogPanel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_rxLogPanel.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		rxLogPanel.setLayout(gbl_rxLogPanel);
		
		JScrollPane rxLogScrollPane = new JScrollPane();
		GridBagConstraints gbc_rxLogScrollPane = new GridBagConstraints();
		gbc_rxLogScrollPane.weighty = 1.0;
		gbc_rxLogScrollPane.fill = GridBagConstraints.BOTH;
		gbc_rxLogScrollPane.gridx = 0;
		gbc_rxLogScrollPane.gridy = 0;
		rxLogPanel.add(rxLogScrollPane, gbc_rxLogScrollPane);
		
		rxLogTextPane = new JTextPane();
		rxLogTextPane.setEditable(false);
		rxLogScrollPane.setViewportView(rxLogTextPane);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.gridx = 0;
		gbc_panel_1.gridy = 1;
		rxLogPanel.add(panel_1, gbc_panel_1);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[] {0};
		gbl_panel_1.rowHeights = new int[]{0, 0};
		gbl_panel_1.columnWeights = new double[]{0.0, 0.0};
		gbl_panel_1.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panel_1.setLayout(gbl_panel_1);
		
		JButton button_1 = new JButton("Save");
		GridBagConstraints gbc_button_1 = new GridBagConstraints();
		gbc_button_1.anchor = GridBagConstraints.WEST;
		gbc_button_1.gridx = 0;
		gbc_button_1.gridy = 0;
		panel_1.add(button_1, gbc_button_1);
		
		JButton button = new JButton("Clear");
		GridBagConstraints gbc_button = new GridBagConstraints();
		gbc_button.anchor = GridBagConstraints.EAST;
		gbc_button.gridx = 1;
		gbc_button.gridy = 0;
		panel_1.add(button, gbc_button);

	}

	public JTextPane getTxMessageLogger() {
		return txLogTextPane;
	}
	public JTextPane getRxMessageLogger() {
		return rxLogTextPane;
	}
}

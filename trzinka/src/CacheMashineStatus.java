import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;



public class CacheMashineStatus extends JFrame {
	private static final String DELIMETER = "\r\n";
	File file = new File("servers.txt"); // where to save
		
	private class BtnRefreshActionListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			DefaultTableModel m = (DefaultTableModel) table.getModel();
//			while (m.getRowCount()!=0) m.removeRow(m.getRowCount()-1);
			m.fireTableDataChanged();    
			table.repaint(); // Repaint all the component (all Cells).

			if (file.exists()) {

				try {
					BufferedReader reader = new BufferedReader(new FileReader(file));
					String line = null;
					final StringBuilder sb = new StringBuilder();
//					while ((line = reader.readLine()) != null) {
					for (int i = 0; i < m.getRowCount(); i++) {
						line = m.getValueAt(i, 0).toString();
//						int pos=line.indexOf(" ");
//						if (pos>0) line = line.substring(0, pos);
						
						final String fline = line;
						new Thread() {
						      @Override
						      public void run() {
									String response = null;
									String c1 = null;
									String c2 = null;
									DefaultTableModel m = (DefaultTableModel) table.getModel();
									HttpServerInfo si = new HttpServerInfo();

						    	  try {
										response = si.getInfo(fline);
//										m.addRow(new Object[] { fline, response, response});
//										sb.append(fline + " " + response + DELIMETER);
						    		    c1 = response;
									} catch (UnknownHostException uhe) {
//										m.addRow(new Object[] { fline, "ERROR", "UnknownHostException",});
//										sb.append(fline + DELIMETER);
										c1 = "ERROR"; 
										c2  = "UnknownHostException";
									} catch (IOException ioe) {
										c1 = "ERROR"; 
										c2  = ioe.getMessage();
									}
//						    	  m.addRow(new Object[] { fline, c1, c2 });
						    	  for (int i = 0; i < m.getRowCount(); i++) {
						    		  if (m.getValueAt(i, 0).toString().equals(fline)) {
						    			  m.setValueAt(c1, i, 1);
						    			  m.setValueAt(c2, i, 2);
						    		  }
						    	  }
						    	  sb.append(fline + DELIMETER);

						    	  m.fireTableDataChanged();
						    	  table.repaint(); // Repaint all the component (all Cells).

						      }
						    }.start();
						
					}
					reader.close();
					
//					FileWriter fileWritter;
//					fileWritter = new FileWriter(file.getName());
//					PrintWriter bufferWritter = new PrintWriter(fileWritter);
//					bufferWritter.print(sb); 
//					bufferWritter.close();

				} catch (IOException ioe) {
					ioe.printStackTrace();
				}
			}
			
		}
	}
	private class BtnAddActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			DefaultTableModel m = (DefaultTableModel) table.getModel();
			m.addRow(new Object[] { "", "<-- enter server", ""});
			table.revalidate();
		}
	}

	   @SuppressWarnings("serial")
	public class MyTableCellRenderer extends DefaultTableCellRenderer implements TableCellRenderer {

	        @Override
	        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
	            setBackground(null);
	            super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
	            setText(String.valueOf(value));
	            setToolTipText(String.valueOf(value));
	            boolean interestingRow = row % 5 == 2;
	            boolean secondColumn = column == 1;
	            if (interestingRow && secondColumn) {
	                setBackground(Color.ORANGE);
	            } else if (interestingRow) {
	                setBackground(Color.YELLOW);
	            } else if (secondColumn) {
	                setBackground(Color.RED);
	            }
	            return this;
	        }

	    }

	private static final long serialVersionUID = 5431470566872605326L;
	private JPanel contentPane;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CacheMashineStatus frame = new CacheMashineStatus();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public CacheMashineStatus() {
		super("Cashe machine statuses");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 550, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.SOUTH);
		
		JButton btnRefresh = new JButton("Refresh");
		btnRefresh.addActionListener(new BtnRefreshActionListener());
		panel.add(btnRefresh);
		
		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new BtnAddActionListener());
		panel.add(btnAdd);
		
		
		
		
		 Vector<Vector<Object>> data = new Vector<Vector<Object>>();
	        Vector<String> columNames = new Vector<String>();
	        columNames.add("Server");
	        columNames.add("Status");
	        columNames.add("Additional info");
	        
			if (file.exists()) {

				try {
					BufferedReader reader = new BufferedReader(new FileReader(file));
					String line = null;
					int i=0;
					while ((line = reader.readLine()) != null) {
						int pos=line.indexOf(" ");
						if (pos>0) line = line.substring(0, pos);
						Vector<Object> v = new Vector<Object>();
						v.add(line);
						v.add("<-- enter server " + (i + 1));
						v.add("Some other data in row " + (i + 1));
						data.add(v);
						i++;
					}
					reader.close();
				} catch (IOException ioe) {
					ioe.printStackTrace();
				}
			}
				
	        table = new JTable(new DefaultTableModel(data, columNames));
	        Enumeration<TableColumn> en = table.getColumnModel().getColumns();
//	        while (en.hasMoreElements()) {
//	            TableColumn tc = en.nextElement();
//	            tc.setCellRenderer(new MyTableCellRenderer());
//	        }
	        
	        
	        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	        table.getColumnModel().getColumn(0).setPreferredWidth(7);
	        table.getColumnModel().getColumn(1).setPreferredWidth(120);
	        table.getColumnModel().getColumn(2).setPreferredWidth(100);
	        table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);

			contentPane.add(new JScrollPane(table), BorderLayout.CENTER);

	}

}

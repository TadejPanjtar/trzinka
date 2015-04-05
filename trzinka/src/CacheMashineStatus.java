import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTable;
import javax.swing.JButton;

import java.awt.Color;
import java.awt.Component;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;



public class CacheMashineStatus extends JFrame {
	private class BtnAddActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			DefaultTableModel m = (DefaultTableModel) table.getModel();
			m.addRow(new Object[] { "data", "data", "data", "data"});
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

	/**
	 * 
	 */
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
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.SOUTH);
		
		JButton btnRefresh = new JButton("Refresh");
		panel.add(btnRefresh);
		
		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new BtnAddActionListener());
		panel.add(btnAdd);
		
		
		
		
		 Vector<Vector<Object>> data = new Vector<Vector<Object>>();
	        Vector<String> columNames = new Vector<String>();
	        columNames.add("Col 0");
	        columNames.add("Col 1");
	        columNames.add("Col 2");
	        for (int i = 0; i < 20; i++) {
	            Vector<Object> v = new Vector<Object>();
	            v.add(i % 3 == 0 ? "Hello" : "World");
	            v.add("Some data in row " + (i + 1));
	            v.add("Some other data in row " + (i + 1));
	            data.add(v);
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

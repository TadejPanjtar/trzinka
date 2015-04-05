package me.tadejpanjtar;


import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.SwingUtilities;
import javax.swing.event.RowSorterEvent;
import javax.swing.event.RowSorterListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 * This program demonstrates how to sort rows in a table.
 * @author www.codejava.net
 *
 */
public class JTableSortingExample extends JFrame {
	private JTable table;
	
	void dodajMe() {
		EmployeeTableModel model = (EmployeeTableModel) table.getModel();
		model.addRow(new Employee("Tadej", "Programmer", 37));
	}
	
	public JTableSortingExample() {
		super("JTable Sorting Example");
		
		
		List<Employee> listEmployees = createListEmployees();
		TableModel tableModel = new EmployeeTableModel(listEmployees);
		table = new JTable(tableModel);
		
		// ENABLE SORTING
		table.setAutoCreateRowSorter(true);
		
		TableRowSorter<TableModel> sorter = new TableRowSorter<>(table.getModel());
		table.setRowSorter(sorter);
		List<RowSorter.SortKey> sortKeys = new ArrayList<>();
		
		// SORT BY MULTIPE COLUMNS
		int columnIndexForJob = 2;
		sortKeys.add(new RowSorter.SortKey(columnIndexForJob, SortOrder.ASCENDING));
		
		int columnIndexForName = 1;
		sortKeys.add(new RowSorter.SortKey(columnIndexForName, SortOrder.ASCENDING));
			
		sorter.setSortKeys(sortKeys);

		// LISTEN TO SORTING EVENTS
		sorter.addRowSorterListener(new RowSorterListener() {
			@Override
			public void sorterChanged(RowSorterEvent evt) {
				int indexOfNoColumn = 0;
				for (int i = 0; i < table.getRowCount(); i++) {
					table.setValueAt(i + 1, i, indexOfNoColumn);
				}
			}
		});
		
		// DISABLE SORTING FOR A SPECIFIC COLUMN
		sorter.setSortable(0, false);
		
		// SPECIFY COMPARATOR FOR SORTING A SPECIFIC COLUMN
		sorter.setComparator(columnIndexForName, new Comparator<String>() {

			@Override
			public int compare(String name1, String name2) {
				return name1.compareTo(name2);
			}
		});
		
		sorter.sort();
		
		
		add(new JScrollPane(table), BorderLayout.CENTER);
		
		pack();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
	}
	
	public List<Employee> createListEmployees() {
		List<Employee> listEmployees = new ArrayList<>();
		listEmployees.add(new Employee("Tom", "Manager", 40));
		listEmployees.add(new Employee("Peter", "Programmer", 25));
		listEmployees.add(new Employee("Mary", "Designer", 25));
		listEmployees.add(new Employee("Patrick", "Leader", 30));
		listEmployees.add(new Employee("Tim", "Designer", 28));
		listEmployees.add(new Employee("Sam", "Analyst", 50));
		listEmployees.add(new Employee("Jeremy", "Programmer", 32));
		listEmployees.add(new Employee("Paul", "Developer", 29));
		listEmployees.add(new Employee("Kevin", "Programmer", 23));
		listEmployees.add(new Employee("Antonio", "Programmer", 23));
		listEmployees.add(new Employee("John", "Designer", 33));
		listEmployees.add(new Employee("David", "Developer", 28));
		listEmployees.add(new Employee("Hess", "Designer", 31));
		listEmployees.add(new Employee("Cook", "Programmer", 26));
		listEmployees.add(new Employee("Joshep", "Manager", 40));
		
		return listEmployees;
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				JTableSortingExample t = new JTableSortingExample();
				t.setVisible(true);
				t.dodajMe();
			}
		});
	}
}

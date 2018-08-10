package employeeApp;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

public class RemoveEmployeeListener extends MyTableModel implements ActionListener {

	
	
	JButton glbBtn;
	MyTableModel tm1;
	JTable myTab;

	public RemoveEmployeeListener(JButton btnDelete, MyTableModel table, JTable mt1) {
		

		glbBtn = btnDelete;
		tm1 = table;
		 myTab = mt1;
		
		
		glbBtn = btnDelete;
		myTab.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
	}

	public void actionPerformed(ActionEvent e) {
		tm1.deleteRow(myTab.getSelectedRow());
		

			}
}

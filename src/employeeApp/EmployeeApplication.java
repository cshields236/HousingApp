package employeeApp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.Vector;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.filechooser.FileNameExtensionFilter;

public class EmployeeApplication extends JFrame implements ActionListener {
	JTable myTable;

	// Menu structure
	JMenuBar myBar;
	JMenu fileMenu, employeeMenu, exitMenu;
	JMenuItem fileLoad, fileSave, fileSaveAs, filePrintReport, addEmployee, removeEmployee, exitProgram;

	// Array of data types to be used in combo box when defining new structure
	String[] dataTypeNames;

	JPanel p;
	MyTableModel tm;
	JScrollPane myPane;

	// Subdialog used when defining new structure
	// DepositFundsDialog depositDialog;
	JLabel lblPPSNo, lblName, lblGender, lblDepartment, lblPosition, lblSalary;
	JTextField txtPPSNo, txtName, txtDepartment, txtPosition, txtSalary;
	JComboBox cbGender, comboBox;
	String[] genderArr = { "Male", "Female" };
	JButton btnNew, btnUpdate, btnDelete, btnPrintAll, btnClose;

	// Used to indicate whether data is already in a file
	File currentFile;

	public EmployeeApplication() {
		// Create menu bar and table to panel

		JMenuBar myBar = createMenuBar();
		this.setJMenuBar(myBar);

		p = new JPanel();
		tm = new MyTableModel();
		myTable = new JTable(tm);
		myPane = new JScrollPane(myTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		myTable.setSelectionForeground(Color.white);
		myTable.setSelectionBackground(Color.red);
		myTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		p.setLayout(new BorderLayout());
		myPane.setPreferredSize(new Dimension(450, 110));
		p.add(myPane, BorderLayout.NORTH);

		JPanel myCenterPanel = createCentrePanel();
		Border line = BorderFactory.createLineBorder(Color.black);
		myCenterPanel.setBorder(BorderFactory.createTitledBorder(line, "Employee Records Editor"));

		p.add(myCenterPanel, BorderLayout.CENTER);

		JPanel myBottomPanel = createBottomPanel();
		p.add(myBottomPanel, BorderLayout.SOUTH);

		// Associating event listeners with menu items
		// TODO: on row selection populate all the textfields and combobox with
		// the
		// values from the selected row

		AddMouseListener aml = new AddMouseListener();
		myTable.addMouseListener(aml);
		// TODO: Add Employee record - both the add button and the add employee
		// menu
		// item should use
		// the same event handler/listener to add a record and insert a row
		class AddEmployeeListener implements ActionListener {

			public void actionPerformed(ActionEvent e) {
				// Add

				String data1 = txtPPSNo.getText();
				if (txtPPSNo.equals(null))
				{
					JOptionPane.showMessageDialog(null, "Not a valid value for PPS Number");
				}
				
				String data2 = txtName.getText();
				if (txtName.equals(null))
				{
					JOptionPane.showMessageDialog(null, "Not a valid value for PPS Number");
				}
				
				String data3 = (String) cbGender.getSelectedItem();
				if (txtPPSNo.equals(null))
				{
					JOptionPane.showMessageDialog(null, "Not a valid value for PPS Number");
				}
				
				String data4 = txtDepartment.getText();
				
				
				String data5 = txtPosition.getText();
				if (txtPosition.equals(null))
				{
					JOptionPane.showMessageDialog(null, "Not a valid value for PPS Number");
				}
				
				double data6 = Double.parseDouble(txtSalary.getText());
				if (txtSalary.equals(null))
				{
					JOptionPane.showMessageDialog(null, "Not a valid value for PPS Number");
				}
				// Object[] row = { data1, data2, data3, data4, data5, data6 };

				Vector<Object> RowVect = new Vector<Object>();

				RowVect.add(data1);
				RowVect.add(data2);
				RowVect.add(data3);
				RowVect.add(data4);
				RowVect.add(data5);
				RowVect.add(data6);

				tm.addRow(RowVect);

			}
		}

		AddEmployeeListener ael = new AddEmployeeListener();
		addEmployee.addActionListener(ael);
		btnNew.addActionListener(ael);

		// TODO: Remove Employee record - both the delete button and the remove
		// employee
		// menu item should use
		// the same event handler/listener to delete a record
		RemoveEmployeeListener rel = new RemoveEmployeeListener(btnDelete, tm, myTable);

		btnDelete.addActionListener(rel);
		removeEmployee.addActionListener(rel);

		// TODO:Update button

		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String data1 = txtPPSNo.getText();
				String data2 = txtName.getText();
				String data3 = (String) cbGender.getSelectedItem();
				String data4 = txtDepartment.getText();
				String data5 = txtPosition.getText();
				double data6 = Double.parseDouble(txtSalary.getText());

				tm.setValueAt(data1, myTable.getSelectedRow(), 0);
				tm.setValueAt(data2, myTable.getSelectedRow(), 1);
				tm.setValueAt(data3, myTable.getSelectedRow(), 2);
				tm.setValueAt(data4, myTable.getSelectedRow(), 3);
				tm.setValueAt(data5, myTable.getSelectedRow(), 4);
				tm.setValueAt(data6, myTable.getSelectedRow(), 5);

			}
		});

		// TODO load the contents of the employee.dat file into the table
		fileLoad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FileReader in = null;
				JFileChooser fc = new JFileChooser();
				ObjectInputStream os = null;
				int returnVal = fc.showOpenDialog(EmployeeApplication.this);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					// Check to see if there is already a file with this name

					try {
						// We put the implementation of writing into a separate
						// method
						os = new ObjectInputStream(new FileInputStream(file));
						Vector<String> colName = (Vector<String>) os.readObject();
						Vector<Object> data = (Vector<Object>) os.readObject();
						tm.setTableData(data);
						tm.modified = true;
						// String newRow = null;
						in = new FileReader(file);

						if (in != null) {
							in.close();
						}

					} catch (ClassNotFoundException | IOException ex) {

					}

				}
			}
		});

		// Saving the file - usually in a different location
		fileSaveAs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				int returnVal = fc.showSaveDialog(EmployeeApplication.this);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					// Check to see if there is already a file with this name
					if (file.exists()) {
						int result = JOptionPane.showConfirmDialog(EmployeeApplication.this,
								"This will overwrite the existing file.\n Are you sure you want to do this?");
						if (result == 0) {
							try {
								// We put the implementation of writing into a
								// separate method
								writeDataFile(file);
							} catch (IOException ex) {
								JOptionPane.showMessageDialog(EmployeeApplication.this,
										"I/O Exception\n " + ex.toString(), "Error Message", JOptionPane.ERROR_MESSAGE);
							}
						}
					}
					// This is a new file name
					else {
						try {
							writeDataFile(file);
						} catch (IOException ex) {
							JOptionPane.showMessageDialog(EmployeeApplication.this, "I/O Exception\n " + ex.toString(),
									"Error Message", JOptionPane.ERROR_MESSAGE);
						}
					}
				}
			}
		});

		// Saving the file - usually in the same location
		fileSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Data may not yet have been saved to a file
				// currentFile not being null means that the file object has
				// been created and we
				// then check that it exists in the file system
				if (currentFile != null && currentFile.exists()) {
					int result = JOptionPane.showConfirmDialog(EmployeeApplication.this,
							"This will overwrite the existing file.\n Are you sure you want to do this?");
					if (result == 0) {
						try {
							writeDataFile(currentFile);
						} catch (IOException ex) {
							JOptionPane.showMessageDialog(EmployeeApplication.this, "I/O Exception\n " + ex.toString(),
									"Error Message", JOptionPane.ERROR_MESSAGE);
						}
					}
				}
				// Otherwise we need to specify the name to use for this file
				else {
					JOptionPane.showMessageDialog(EmployeeApplication.this, "File doesn't exist.\n", "Error message",
							JOptionPane.ERROR_MESSAGE);
					// Ask user to specify file name (remember user can type in
					// new file name in
					// file chooser)
					JFileChooser fc = new JFileChooser();
					int returnVal = fc.showSaveDialog(EmployeeApplication.this);
					if (returnVal == JFileChooser.APPROVE_OPTION) {
						currentFile = fc.getSelectedFile();
						if (currentFile.exists()) {
							int result = JOptionPane.showConfirmDialog(EmployeeApplication.this,
									"This will overwrite the existing file.\n Are you sure you want to do this?");
							if (result == 0) {
								try {
									writeDataFile(currentFile);
								} catch (IOException ex) {
									JOptionPane.showMessageDialog(EmployeeApplication.this,
											"I/O Exception\n " + ex.toString(), "Error Message",
											JOptionPane.ERROR_MESSAGE);
								}
							}
						} else {
							try {
								writeDataFile(currentFile);
							} catch (IOException ex) {
								JOptionPane.showMessageDialog(EmployeeApplication.this,
										"I/O Exception\n " + ex.toString(), "Error Message", JOptionPane.ERROR_MESSAGE);
							}
						}
					}
				}
			}
		});

		// Prints report as external HTML file with name BankReport.html

		filePrintReport.addActionListener(new ActionListener () {
			public void actionPerformed(ActionEvent e) {
				
			btnPrintAll.addActionListener(this);
			
			
				FileWriter out = null;
				String todaysDate = new SimpleDateFormat("dd/M/yyyy").format(new Date());
				String htmlHeadings = "<html> <body> <p>Report at: " +todaysDate +   " </p> <br /> <table border=<1> ";
				
				String htmlTitles = "<tr>";
				String data = "";
				for (int i = 0; i <tm.getColumnCount(); i++)
				{
					htmlTitles = htmlTitles + "<th>"+ tm.getColumnName(i)+ "</th>";
					
				}
				htmlTitles = htmlTitles + "</tr>";
				
				for(int i = 0; i< tm.getRowCount(); i++)
				{
					data = data + "<tr>";
					for(int j = 0; j<tm.getColumnCount(); j++)		
				{
					 data = data + "<td>" + tm.getValueAt(i, j) + "</td>";
				}
					data = data + "\n</tr>";
				}
				String report = "\n</table> \n </body> \n<html>";
				
				String finalReport = htmlHeadings + htmlTitles + data + report;
				
				System.out.println(finalReport);
				
				try{
					
					out = new FileWriter("PrintReport.html");
					out.write(finalReport);
					
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} finally{
					if (out != null)
					{
						try {
							out.close();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}
				
			}
		});


		btnPrintAll.addActionListener(new ActionListener () {
			public void actionPerformed(ActionEvent e) {
				
			btnPrintAll.addActionListener(this);
			
			
				FileWriter out = null;
				String todaysDate = new SimpleDateFormat("dd/M/yyyy").format(new Date());
				String htmlHeadings = "<html> <body> <p>Report at: " +todaysDate +   " </p> <br /> <table border=<1> ";
				//String htmlTitles =  "<tr> <th> PPS Number:</th><th>Name</th><th>Gender</th><th>Department</th></tr> <tr> <th> Position:</th><th> <tr> <th> Salary</th><th> ";
				String htmlTitles = "<tr>";
				String contents = "";
				for (int i = 0; i <tm.getColumnCount(); i++)
				{
					htmlTitles = htmlTitles + "<th>"+ tm.getColumnName(i)+ "</th>";
					
				}
				htmlTitles = htmlTitles + "</tr>";
				
				for(int i = 0; i< tm.getRowCount(); i++)
				{
					contents = contents + "<tr>";
					for(int j = 0; j<tm.getColumnCount(); j++)		
				{
						contents = contents + "<td>" + tm.getValueAt(i, j) + "</td>";
				}
					contents = contents + "\n</tr>";
				}
				String report = "\n</table> \n </body> \n<html>";
				
				String finalReport = htmlHeadings + htmlTitles + contents + report;
				
				//System.out.println(finalReport);
				
				try{
					
					out = new FileWriter("PrintReport1.html");
					out.write(finalReport);
					
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} finally{
					if (out != null)
					{
						try {
							out.close();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}
				
			}
		});
		// Both of these listeners behave in the same way so we extract their
		// code to a
		// separate method

		// exits program from menu
		exitProgram.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				closeDown();
			}
		});

		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				closeDown();
			}
		});

		// exits program by closing window
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				closeDown();
			}
		}); // end windowlistener

		this.setTitle("Employee Application");
		this.setContentPane(p);
		this.setVisible(true);
		this.pack();
	} // constructor

	public void readDataFile(File f) throws IOException, ClassNotFoundException {
		// TODO

	}

	public void writeDataFile(File f) throws IOException, FileNotFoundException {
		ObjectOutputStream out = null;
		try {
			out = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(f)));
			// Write column headers as one object
			out.writeObject(tm.getColumnNames());
			// Write table data as second object
			out.writeObject(tm.getTableData());
			// This indicates that there is no unsaved data for the moment
			tm.modified = false;
		} catch (FileNotFoundException ex) {
			JOptionPane.showMessageDialog(EmployeeApplication.this, "File Not Found Exception\n " + ex.toString(),
					"Error Message", JOptionPane.ERROR_MESSAGE);
		} catch (IOException ex) {
			JOptionPane.showMessageDialog(EmployeeApplication.this, "I/O Exception\n " + ex.toString(), "Error Message",
					JOptionPane.ERROR_MESSAGE);
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(EmployeeApplication.this, "Exception\n " + ex.toString(), "Error Message",
					JOptionPane.ERROR_MESSAGE);
		}
		// Make sure to close stream
		finally {
			out.close();
		}
	}

	// This behaviour will be used whether we close the application by clicking
	// on
	// the X button in the top-right corner or by selecting
	// an option from a menu, so it makes sense to have it as an independent
	// method
	// that can be reused.
	public void closeDown() {
		// Ask user to confirm decision
		int result = JOptionPane.showConfirmDialog(EmployeeApplication.this,
				"This will close the application.\n Are you sure you want to do this?");
		if (result == 0) {
			// Give user second chance if there is unsaved data
			if (tm.modified) {
				int result2 = JOptionPane.showConfirmDialog(EmployeeApplication.this,
						"You have unsaved data that will be lost.\n Are you sure you want to do this?");
				if (result2 == 0) {
					System.exit(0);
				}
			} else {
				System.exit(0);
			}
		}
	}

	public JMenuBar createMenuBar() {
		// Setting up menu
		fileLoad = new JMenuItem("Open");
		fileSave = new JMenuItem("Save");
		fileSaveAs = new JMenuItem("Save As");
		filePrintReport = new JMenuItem("Print Report");

		fileMenu = new JMenu("File");
		fileMenu.add(fileLoad);
		fileMenu.add(fileSave);
		fileMenu.add(fileSaveAs);
		fileMenu.add(filePrintReport);

		addEmployee = new JMenuItem("Add");
		removeEmployee = new JMenuItem("Remove");

		employeeMenu = new JMenu("Employees");
		employeeMenu.add(addEmployee);
		employeeMenu.add(removeEmployee);

		exitProgram = new JMenuItem("Exit Program");
		exitMenu = new JMenu("Exit");
		exitMenu.add(exitProgram);

		myBar = new JMenuBar();
		myBar.add(fileMenu);
		myBar.add(employeeMenu);
		myBar.add(exitMenu);

		return myBar;

	}

	public JPanel createCentrePanel() {
		JPanel centerPanel = new JPanel(new GridLayout(6, 2));

		lblPPSNo = new JLabel("PPS Number", JLabel.LEFT);
		lblName = new JLabel("FullName", JLabel.LEFT);
		lblGender = new JLabel("Gender", JLabel.LEFT);
		lblDepartment = new JLabel("Department", JLabel.LEFT);
		lblPosition = new JLabel("Position", JLabel.LEFT);
		lblSalary = new JLabel("Salary", JLabel.LEFT);

		txtPPSNo = new JTextField(20);
		txtName = new JTextField(20);
		txtDepartment = new JTextField(20);
		txtPosition = new JTextField(20);
		txtSalary = new JTextField(20);
		txtSalary.setText("0.0");
		String[] genderArr = { "Male", "Female" };
		cbGender = new JComboBox(genderArr);

		centerPanel.add(lblPPSNo);
		centerPanel.add(txtPPSNo);
		centerPanel.add(lblName);
		centerPanel.add(txtName);
		centerPanel.add(lblGender);
		centerPanel.add(cbGender);
		centerPanel.add(lblDepartment);
		centerPanel.add(txtDepartment);
		centerPanel.add(lblPosition);
		centerPanel.add(txtPosition);
		centerPanel.add(lblSalary);
		centerPanel.add(txtSalary);

		return centerPanel;

	}

	public JPanel createBottomPanel() {
		JPanel BottomPanel = new JPanel();
		btnNew = new JButton("Add New");
		btnUpdate = new JButton("Update");
		btnDelete = new JButton("Delete");
		
		btnPrintAll = new JButton("Print All");
		btnClose = new JButton("Close");
		BottomPanel.add(btnNew);
		BottomPanel.add(btnUpdate);
		BottomPanel.add(btnDelete);
		BottomPanel.add(btnPrintAll);
		BottomPanel.add(btnClose);

		return BottomPanel;

	}

	public static void main(String args[]) {
		new EmployeeApplication();
	} // main

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

	class AddMouseListener implements MouseListener {
		public void mouseClicked(MouseEvent e) {

			// get the model from the jtable
			MyTableModel model = (MyTableModel) myTable.getModel();

			// get the selected row index
			int selectedRowIndex = myTable.getSelectedRow();

			// set the selected row data into jtextfields
			txtPPSNo.setText(model.getValueAt(selectedRowIndex, 0).toString());
			txtName.setText(model.getValueAt(selectedRowIndex, 1).toString());
			cbGender.setSelectedItem(model.getValueAt(selectedRowIndex, 2).toString());

			txtDepartment.setText(model.getValueAt(selectedRowIndex, 3).toString());
			txtPosition.setText(model.getValueAt(selectedRowIndex, 4).toString());
			txtSalary.setText(model.getValueAt(selectedRowIndex, 5).toString());

			addMouseListener(this);
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub

		}
	}
}
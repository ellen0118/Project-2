//testing

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

public class ModuleManagement extends JFrame
{
	JTabbedPane tabbedPane;
	JPanel tab1Panel, tab2Panel;
	
	JLabel totalCreditlbl = new JLabel("Total credit of all modules: 0");
	JLabel totalModulelbl = new JLabel("Total modules: 0");
	
	int selectedRow;
	
	ArrayList<Module> modules = new ArrayList<Module>();
	
	public ModuleManagement()
	{
		super("Module Management");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		tabbedPane = new JTabbedPane();
		tab1Panel = new JPanel();
		tab2Panel = new JPanel();
		
		tabbedPane.add(tab1Panel, "Module Details");
		tabbedPane.add(tab2Panel, "Statistics");
		
		add(tabbedPane);
		
		tab1();
		tab2();
	}
	
	public void tab1()
	{
		JLabel titlelbl, creditlbl;
		JTextField txttitle, txtcredit;
		JButton addbtn, updatebtn, removebtn;
		JPanel inputPanel, tablePanel;
		JTable table;
		JScrollPane scroll;
		DefaultTableModel tablemodel;
		
		inputPanel = new JPanel();
		tablePanel = new JPanel();
		
		titlelbl = new JLabel("Title: ");
		creditlbl = new JLabel("Credit: ");
		
		txttitle = new JTextField(20);
		txtcredit = new JTextField(10);
		
		addbtn = new JButton("Add");
		updatebtn = new JButton("Update");
		removebtn = new JButton("Remove");
		
		inputPanel.add(titlelbl);
		inputPanel.add(txttitle);
		inputPanel.add(creditlbl);
		inputPanel.add(txtcredit);
		inputPanel.add(addbtn);
		inputPanel.add(updatebtn);
		inputPanel.add(removebtn);
		
		String[] columnsname = {"Title", "Credit"};
		
		tablemodel = new DefaultTableModel(columnsname, 0);
		
		table = new JTable(tablemodel);
		
		selectedRow = -1;
		
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setAutoCreateRowSorter(true);
		
		scroll = new JScrollPane(table);
		
		tablePanel.add(scroll);
		
		tab1Panel.add(inputPanel);
		tab1Panel.add(tablePanel);
		
		//List Selection Model
		ListSelectionModel selectionModel = table.getSelectionModel();
		selectionModel.addListSelectionListener((ListSelectionListener) new ListSelectionListener()
		{
			public void valueChanged(ListSelectionEvent e)
			{
				selectedRow = table.getSelectedRow();
				
				if (selectedRow == -1)
				{
					txttitle.setText("");
					txtcredit.setText("");
				}
				else
				{
					txttitle.setText((String)tablemodel.getValueAt(selectedRow, 0));
					txtcredit.setText((String)tablemodel.getValueAt(selectedRow, 1));
				}
			}
		});
		
		//actions when the user clicks on the add button
		addbtn.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if ((!txttitle.getText().isEmpty()) && (!txtcredit.getText().isEmpty()))
				{
					Object[] data = {txttitle.getText(), txtcredit.getText()};
					
					try
					{
						Module module;
						
						module = new Module(txttitle.getText(), Integer.parseInt(txtcredit.getText()));
						txttitle.setText("");
						txtcredit.setText("");
						
						table.clearSelection();
						
						//add row to arraylist
						tablemodel.addRow(data);
						modules.add(module);
					}
					catch(Exception ex)
					{
						JOptionPane.showMessageDialog(ModuleManagement.this, "Invalid credit input", "Invalid Input", JOptionPane.WARNING_MESSAGE);
					}
					
					statsCalculation();
				}
			}
		});
		
		//actions when the user clicks on the update button
		updatebtn.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if(selectedRow != -1)
				{
					tablemodel.setValueAt(txttitle.getText(), selectedRow, 0);
					tablemodel.setValueAt(txtcredit.getText(), selectedRow, 1);
					
					modules.get(selectedRow).setModuleName(txttitle.getText());
					modules.get(selectedRow).setModuleCredits(Integer.parseInt(txtcredit.getText()));
				}
			}
		});
		
		//actions when the user clicks on the remove button
		removebtn.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if(selectedRow != -1)
				{
					modules.remove(selectedRow);
					tablemodel.removeRow(selectedRow);
				}
			}
		});
	}
	
	public void tab2()
	{
		JButton calculatebtn;
		JPanel btnPanel, resultsPanel;
		
		tab2Panel.setLayout(new GridLayout(2,1));
		btnPanel = new JPanel();
		resultsPanel = new JPanel();
		calculatebtn = new JButton("Calculation");
		
		btnPanel.add(calculatebtn);
		
		resultsPanel.add(totalCreditlbl);
		resultsPanel.add(totalModulelbl);
		
		tab2Panel.add(btnPanel);
		tab2Panel.add(resultsPanel);
		
		calculatebtn.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				statsCalculation();
			}
		});
	}
	
	private void statsCalculation()
	{
		int totalCredit = 0;
		
		for(Module module: modules)
		{
			totalCredit = totalCredit + module.getModuleCredits();
		}
		
		totalCreditlbl.setText("Total credit of all modules: " + String.valueOf(totalCredit));
		totalModulelbl.setText("Total modules: " + String.valueOf(modules.size()));
	}
	
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		ModuleManagement win = new ModuleManagement();
		win.setSize(700,300);
		win.setVisible(true);
	}
}
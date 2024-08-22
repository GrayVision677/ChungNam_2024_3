package base.Comp;

import java.awt.Color;
import java.util.Vector;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;


public class BaseTable extends JScrollPane {

	public static JTable jTable;

	public BaseTable(Vector<Vector<String>> data, String...val) {
		// TODO Auto-generated constructor stub
		Vector<String> col = new Vector<>();
		
		for (String cols : val) {
			col.add(cols);
		}
		
		DefaultTableModel dtm = new DefaultTableModel(data,col);
		jTable = new JTable(dtm);
		super.setViewportView(jTable);
	}
	
	public BaseTable setCenter() {
		DefaultTableCellRenderer dctr = new DefaultTableCellRenderer();
		dctr.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
		jTable.setDefaultRenderer(Object.class,dctr);
		return this;
	}
	
	public BaseTable setWhilte() {
		jTable.setOpaque(true);
		jTable.setBackground(Color.white);
		return this;
	}

}

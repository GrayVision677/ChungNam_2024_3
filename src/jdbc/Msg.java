package jdbc;

import javax.swing.JOptionPane;

public class Msg {

	public Msg() {
		// TODO Auto-generated constructor stub
	}

	public static void error(String string) {
		// TODO Auto-generated method stub
		JOptionPane.showMessageDialog(null, string,"경고",0);
	}
	
	public static void Info(String string) {
		// TODO Auto-generated method stub
		JOptionPane.showMessageDialog(null, string,"정보",1);
	}

}

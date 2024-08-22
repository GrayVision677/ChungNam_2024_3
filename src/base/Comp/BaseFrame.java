package base.Comp;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import base.IDS;

public abstract class BaseFrame extends JFrame implements IDS{
	
	public BasePanel jpC;
	public BasePanel jpR;
	public BasePanel jpL;
	public BasePanel jpB;
	public BasePanel jpT;
	public BasePanel jpM;

	public void setFrame(String title, int x, int y) {
		// TODO Auto-generated constructor stub
		jpC = new BasePanel();
		jpR = new BasePanel();
		jpL = new BasePanel();
		jpB = new BasePanel();
		jpT = new BasePanel();
		jpM = new BasePanel();
		
		jpM.add(jpC,BorderLayout.CENTER);
		jpM.add(jpR,BorderLayout.EAST);
		jpM.add(jpL,BorderLayout.WEST);
		jpM.add(jpB,BorderLayout.SOUTH);
		jpM.add(jpT,BorderLayout.NORTH);
		super.add(jpM,BorderLayout.CENTER);
		
		Comp();
		DS();
		Event();
		
		super.setTitle(title);
		super.setSize(x, y);
		super.setLocationRelativeTo(null);
		super.setVisible(true);
		
		super.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				super.windowClosing(e);
				System.out.println(e.getWindow().getWidth() + "," + e.getWindow().getHeight());
			}
		});
	}
	
	

}

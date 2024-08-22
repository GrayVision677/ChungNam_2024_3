package base.Comp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

public class BasePanel extends JPanel {

	public BasePanel jpC;
	public BasePanel jpR;
	public BasePanel jpL;
	public BasePanel jpB;
	public BasePanel jpT;

	public BasePanel() {
		// TODO Auto-generated constructor stub
		super.setLayout(new BorderLayout());
		super.setBackground(Color.white);
	}
	
	public BasePanel setFlowLayout(int f) {
		super.setLayout(new FlowLayout(f));
		return this;
	}
	
	public BasePanel setGridLayout(int r, int c, int t, int b) {
		super.setLayout(new GridLayout(r,c,t,b));
		return this;
	}
	
	public BasePanel setEmpty(int t, int l, int b, int r) {
		super.setBorder(new EmptyBorder(t, l, b, r));
		return this;
	}
	
	public BasePanel setColor() {
		super.setOpaque(true);
		super.setBackground(Color.green);
		return this;
	}
	
	public BasePanel setline(int t, int l, int b, int r, Color color) {
		super.setBorder(new MatteBorder(t, l, b, r, color));
		return this;
	}
	
	public BasePanel addChild() {
		jpC = new BasePanel();
		jpR = new BasePanel();
		jpL = new BasePanel();
		jpB = new BasePanel();
		jpT = new BasePanel();
		
		super.add(jpC,BorderLayout.CENTER);
		super.add(jpR,BorderLayout.EAST);
		super.add(jpL,BorderLayout.WEST);
		super.add(jpB,BorderLayout.SOUTH);
		super.add(jpT,BorderLayout.NORTH);
		return this;
	}

}

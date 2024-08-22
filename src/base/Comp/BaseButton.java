package base.Comp;

import java.awt.Color;

import javax.swing.JButton;

public class BaseButton extends JButton {

	public BaseButton(String title) {
		// TODO Auto-generated constructor stub
		super(title);
	}
	
	public BaseButton setColor(Color color1, Color color2) {
		super.setOpaque(true);
		super.setBackground(color1);
		super.setForeground(color2);
		return this;
	}

}

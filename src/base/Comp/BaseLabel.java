package base.Comp;

import java.awt.Font;

import javax.swing.JLabel;

public class BaseLabel extends JLabel{

	public BaseLabel(String text) {
		// TODO Auto-generated constructor stub
		super(text);
	}
	
	public BaseLabel setFont(String title, int size) {
		super.setFont(new Font(title, Font.BOLD,size));
		return this;
	}
	
	public BaseLabel setCenter() {
		super.setHorizontalTextPosition(JLabel.CENTER);
		super.setVerticalTextPosition(JLabel.CENTER);
		return this;
	}

}

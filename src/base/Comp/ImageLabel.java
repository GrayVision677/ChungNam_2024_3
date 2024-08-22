package base.Comp;

import java.awt.Color;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class ImageLabel extends JLabel {

	public ImageLabel(String title,String txt, int x, int y) {
		// TODO Auto-generated constructor stub
		super(title);
		ImageIcon icon = new ImageIcon("./datafiles/" + txt + ".jpg");
		Image img = icon.getImage();
		img = img.getScaledInstance(x, y, Image.SCALE_SMOOTH);
		super.setIcon(new ImageIcon(img));
	}
	
	public ImageLabel(int x,String way,String txt, int w, int h) {
		// TODO Auto-generated constructor stub
		ImageIcon icon = new ImageIcon("./datafiles/" + way + "/" + txt + ".jpg");
		Image img = icon.getImage();
		img = img.getScaledInstance(w, h, Image.SCALE_SMOOTH);
		super.setIcon(new ImageIcon(img));
	}
	
	public ImageLabel(String x,String way,String txt, int w, int h) {
		// TODO Auto-generated constructor stub
		super(x);
		ImageIcon icon = new ImageIcon("./datafiles/" + way + "/" + txt + ".jpeg");
		Image img = icon.getImage();
		img = img.getScaledInstance(w, h, Image.SCALE_SMOOTH);
		super.setIcon(new ImageIcon(img));
	}
	
	public ImageLabel(JLabel c, String data,int w, int h) {
		try {
			c.setIcon(new ImageIcon(new ImageIcon("datafiles/홍보영상/"+data+".gif").getImage()
					.getScaledInstance(w, h, 0)));
		} catch (Exception e) {
			// TODO: handle exception
			c.setIcon(new ImageIcon("datafiles/홍보영상/0.gif"));
		}
	}
	
	public ImageLabel setBottom() {
		super.setHorizontalTextPosition(JLabel.CENTER);
		super.setVerticalTextPosition(JLabel.BOTTOM);
		return this; 
	}
	
	public ImageLabel setCenter() {
		super.setHorizontalAlignment(JLabel.CENTER);
		super.setVerticalAlignment(JLabel.CENTER);
		return this;
	}
	
	public ImageLabel setline() {
		super.setBorder(BorderFactory.createLineBorder(Color.black));
		return this;
	}

}

package window;

import java.awt.Color;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.SwingConstants;

import base.Comp.BaseFrame;
import base.Comp.BaseLabel;
import base.Comp.ImageLabel;
import jdbc.DbManager;

public class hostFrom extends BaseFrame {

	private JComboBox jdiv;
	private DbManager db;
	private JComboBox jcMi;
	private BaseLabel blPrice;
	private BaseLabel blAdd;
	private ImageLabel img;
	private BaseLabel bl;
	private BaseLabel blprice;
	private BaseLabel blADD;

	public hostFrom() {
		// TODO Auto-generated constructor stub
		super.setFrame("시술안내", 358,220);
	}

	@Override
	public void Comp() {
		// TODO Auto-generated method stub
		db = new DbManager();
	}

	@Override
	public void DS() {
		// TODO Auto-generated method stub
		jpT.addChild();
		jpT.jpT.setGridLayout(1, 4, 10, 10).add(new BaseLabel("구분"));
		jpT.jpT.add(jdiv = new JComboBox<>());
		
		Vector<Vector<String>> division = db.getDb("SELECT d_name FROM hair.division;");
		for (int i = 0; i < division.size(); i++) {
			jdiv.addItem(division.get(i).get(0));
		}
		
		jpT.jpT.add(new BaseLabel("세부"));
		jpT.jpT.add(jcMi = new JComboBox<>());
		
		jdiv.setBackground(Color.white);
		jcMi.setBackground(Color.white);
		
		jpT.jpC.setGridLayout(1, 4, 10, 10).add(blprice = new BaseLabel("금액 "));
		jpT.jpC.add(blPrice = new BaseLabel(""));
		
		jpT.jpC.setGridLayout(1, 4, 10, 10).add(blADD = new BaseLabel("기장추가 "));
		jpT.jpC.add(blAdd = new BaseLabel(""));
		
		Vector<Vector<String>> data = db.getDb("SELECT h.dno,d_name,hname,price,Addd FROM hair.division d join hair.hairmenu h\r\n"
				+ "on d.dno = h.dno\r\n"
				+ "where d_name = '펌';");
		
		for (int i = 0; i < data.size(); i++) {
			jcMi.addItem(data.get(i).get(2));
		}
		
		Vector<Vector<String>> parm = db.getDb("SELECT h.hno,explanation FROM hair.hairmenu h join hair.division d\r\n"
				+ "on h.dno = d.dno;");
		
		jcMi.setSelectedIndex(0);
		jdiv.setSelectedIndex(0);
		
		jpC.addChild();
		jpC.jpL.add(img = new ImageLabel(null, "hairmenu", parm.get(0).get(0), 130, 130).setline());
		jpC.jpC.add(bl = new BaseLabel("<html>" + parm.get(0).get(1) + "</html>"));
		bl.setVerticalAlignment(SwingConstants.TOP); // 상단 정렬
		bl.setHorizontalAlignment(SwingConstants.LEFT);
		
		
	}

	@Override
	public void Event() {
		// TODO Auto-generated method stub
		jdiv.addActionListener(e -> {
			int Num = jdiv.getSelectedIndex() + 1;
			
			Vector<Vector<String>> data = db.getDb("SELECT h.dno,d_name,hname,price,Addd FROM hair.division d join hair.hairmenu h\r\n"
					+ "on d.dno = h.dno\r\n"
					+ "where d.dno = ?;",Num);
			
			jcMi.removeAllItems();
			for (int i = 0; i < data.size(); i++) {
				jcMi.addItem(data.get(i).get(2));
			}
		});
		
		jcMi.addActionListener(e -> {
			String list = (String) jcMi.getSelectedItem();
			
			Vector<Vector<String>> listM = db.getDb("SELECT h.dno,d_name,hname,format(price,0),Addd FROM hair.division d join hair.hairmenu h\r\n"
					+ "on d.dno = h.dno\r\n"
					+ "where hname = ?;",list);
			
			blPrice.setText(listM.get(0).get(3));
			blAdd.setText(listM.get(0).get(4));
		});
		
		jcMi.addActionListener(e -> {
			String list = (String) jcMi.getSelectedItem();
			
			Vector<Vector<String>> listM = db.getDb("SELECT h.dno,d_name,hname,format(price,0),Addd FROM hair.division d join hair.hairmenu h\r\n"
					+ "on d.dno = h.dno\r\n"
					+ "where hname = ?;",list);
			
			if(Integer.parseInt(listM.get(0).get(4)) == 0) {
				blADD.setVisible(false);
				blAdd.setVisible(false);
			}else {
				blADD.setVisible(true);
				blAdd.setVisible(true);
			}
			
			jpC.jpL.removeAll();
			
			Vector<Vector<String>> picture = db.getDb("SELECT h.hno,hname,explanation FROM hair.hairmenu h join hair.division d\r\n"
					+ "on h.dno = d.dno\r\n"
					+ "where hname = ?;",list);
			
			jpC.jpL.add(new ImageLabel(null, "hairmenu", picture.get(0).get(0), 130, 130).setline());
			bl.setText("<html>" + picture.get(0).get(2)+ "</html>");
			bl.setVerticalAlignment(SwingConstants.TOP); // 상단 정렬
			bl.setHorizontalAlignment(SwingConstants.LEFT);
			
			jpC.jpL.repaint();
			jpC.jpL.validate();
		});
	}

}

package window;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import ResModel.ResModel;
import base.Comp.BaseButton;
import base.Comp.BaseFrame;
import base.Comp.BaseLabel;
import base.Comp.BaseTable;
import jdbc.DbManager;
import jdbc.Msg;

public class ReviewFrom extends BaseFrame{

	public JComboBox jc;
	public JComboBox jcNick;
	public JTextField jtSel;
	public BaseButton jbSel;
	public DbManager db;
	private BaseLabel jl;
	private int a = 0;
	private BaseTable jt;
	private JScrollPane jscr;

	public ReviewFrom() {
		// TODO Auto-generated constructor stub
		super.setFrame("리뷰현황",600,292);
	}

	@Override
	public void Comp() {
		// TODO Auto-generated method stub
		db = new DbManager();
		jc = new JComboBox<>();
		jl = new BaseLabel("");
	}

	@Override
	public void DS() {
		// TODO Auto-generated method stub
			jpT.setEmpty(0, 10, 0, 10).setFlowLayout(FlowLayout.LEFT).add(new BaseLabel("직급"));
			jpT.add(jc);
			jc.addItem("원장");
			jc.addItem("부원장");
			jc.addItem("실장");
			jc.addItem("디자이너");
			
			jpT.add(new BaseLabel("닉네임"));
			jpT.add(jcNick = new JComboBox<>());
			jcNick.addItem("혜교");
			
			jpC.addChild();
			jpC.jpL.setEmpty(0, 10, 0, 0).setFlowLayout(FlowLayout.LEFT).add(new BaseLabel("검색어"));
			jpC.jpL.add(jtSel = new JTextField(15));
			jpC.jpL.setEmpty(25, 0, 0, 0).add(jbSel = new BaseButton("검색").setColor(Color.green, Color.black));
			
			jcNick.setSelectedIndex(0);
			
			Vector<Vector<String>> count = db.getDb("SELECT count(sname) FROM hair.user u join hair.reservation r\r\n"
					+ "on u.uno = r.uno\r\n"
					+ "join hair.review re\r\n"
					+ "on r.rno = re.rno\r\n"
					+ "join hair.stylist s\r\n"
					+ "on r.sno = s.sno\r\n"
					+ "group by sname;");
			
			Vector<Vector<String>> data = db.getDb("SELECT u.uno,uname,note FROM hair.user u join hair.reservation r\r\n"
					+ "on u.uno = r.uno\r\n"
					+ "join hair.review re\r\n"
					+ "on r.rno = re.rno\r\n"
					+ "join hair.stylist s\r\n"
					+ "on r.sno = s.sno\r\n"
					+ "where sname = ?\r\n"
					+ "order by u.uno asc;",jcNick.getSelectedItem());
			
			
			jpC.jpR.setEmpty(20, 0, 0, 0).setFlowLayout(FlowLayout.RIGHT).add(jl);
			jl.setText("총 " + count.get(0).get(0) + "건");
			
			for (int i = 0; i < data.size(); i++) {
				jpB.add(jt = new BaseTable(data, "번호","회원명","리뷰내용").setCenter().setWhilte());
			}
			jt.setPreferredSize(new Dimension(150,150));
			
	}

	@Override
	public void Event() {
		// TODO Auto-generated method stub
		jc.addActionListener(e -> {
			int Num = jc.getSelectedIndex() + 1;
			String num = (String) jcNick.getSelectedItem();
			jcNick.removeAllItems();
			
			Vector<Vector<String>> grade = db.getDb("select u.uno,r.sno, sname, specialty, count(sname), case\r\n"
					+ "when position = 1 then '원장'\r\n"
					+ "when position = 2 then '부원장'\r\n"
					+ " when position = 3 then '실장'\r\n"
					+ " when position = 4 then '디자이너' end\r\n"
					+ "FROM hair.stylist s join hair.reservation r on \r\n"
					+ "s.sno = r.sno join hair.review re\r\n"
					+ "on r.rno = re.rno\r\n"
					+ "join hair.user u \r\n"
					+ "on r.uno = u.uno\r\n"
					+ "where position = ?\r\n"
					+ "group by sname;",Num);
			
			for (int i = 0; i < grade.size(); i++) {
				jcNick.addItem(grade.get(i).get(2));
			}
			
			jcNick.setSelectedIndex(0);
		});
		
		jcNick.addActionListener(e -> {
			int Num = jc.getSelectedIndex() + 1;
			String num = (String) jcNick.getSelectedItem();
			jpB.removeAll();
			
			Vector<Vector<String>> NickName = db.getDb("SELECT u.uno,uname,note FROM hair.user u join hair.reservation r\r\n"
					+ "on u.uno = r.uno\r\n"
					+ "join hair.review re\r\n"
					+ "on r.rno = re.rno\r\n"
					+ "join hair.stylist s\r\n"
					+ "on r.sno = s.sno\r\n"
					+ "where sname = ?\r\n"
					+ "order by u.uno asc;",num);
			
			Vector<Vector<String>> number = db.getDb("SELECT count(sname) FROM hair.user u join hair.reservation r\r\n"
					+ "on u.uno = r.uno\r\n"
					+ "join hair.review re\r\n"
					+ "on r.rno = re.rno\r\n"
					+ "join hair.stylist s\r\n"
					+ "on r.sno = s.sno\r\n"
					+ "where sname = ?\r\n"
					+ "group by sname;",num);
			
			jcNick.setSelectedIndex(0);
			
			for (int i = 0; i < NickName.size(); i++) {
				jpB.add(jt = new BaseTable(NickName, "번호","회원명","리뷰내용").setCenter().setWhilte());
			}
			jt.setPreferredSize(new Dimension(150,150));
			
			System.out.println(jcNick.getSelectedItem());
			
			jl.setText("총 " + number.get(0).get(0) + "건");
			
			jpB.repaint();
			jpB.validate();
		});
		
		jbSel.addActionListener(e -> {
			String SelVal = jtSel.getText().trim();
			
			if(SelVal.isBlank()) {
				Msg.error("검색어를 입력해주세요.");
				return;
			}
			
			Vector<Vector<String>> data = db.getDb("SELECT u.uno,uname,note FROM hair.user u join hair.reservation r\r\n"
					+ "on u.uno = r.uno\r\n"
					+ "join hair.review re\r\n"
					+ "on r.rno = re.rno\r\n"
					+ "join hair.stylist s\r\n"
					+ "on r.sno = s.sno\r\n"
					+ "where note like '%"+jtSel.getText().trim()+"%';");
			
			System.out.println(data.get(0).get(2).substring(1, SelVal.length() + 1));
			
			for (int i = 0; i < data.size(); i++) {
				if(SelVal.equals(data.get(i).get(2).substring(1, SelVal.length() + 1))) {
					jt.setForeground(Color.red);
				}
			}
			
			jtSel.setText("");
		});
		
		
	}

}

package window;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JLabel;

import ResModel.ResModel;
import base.Comp.BaseButton;
import base.Comp.BaseFrame;
import base.Comp.BaseLabel;
import base.Comp.ImageLabel;
import jdbc.DbManager;
import jdbc.Msg;

public class StyleList extends BaseFrame{

	private DbManager db;
	private JComboBox jc;
	private JComboBox jcNick;
	private BaseLabel bLeft;
	private ImageLabel pcikT;
	private BaseLabel blName;
	private BaseLabel jl;
	private JLabel jl2;
	public int num = 1;
	private BaseButton jbres;
	private BaseLabel bRight;

	public StyleList() {
		// TODO Auto-generated constructor stub
		super.setFrame("스타일리스트", 400,493);
	}

	@Override
	public void Comp() {
		// TODO Auto-generated method stub
		db = new DbManager();
		jbres = new BaseButton("예약").setColor(Color.green, Color.black);
	}

	@Override
	public void DS() {
		// TODO Auto-generated method stub
		jpM.setEmpty(10, 10, 10, 10);
		jpT.addChild();
		jpT.jpL.setGridLayout(1, 2, 10, 10).add(new BaseLabel("직급"));
		jpT.jpL.add(jc = new JComboBox<>());
		jc.addItem("원장");
		jc.addItem("부원장");
		jc.addItem("실장");
		jc.addItem("디자이너");
		
		//jc.setSelectedIndex(0);
		
		jpT.jpC.setEmpty(0, 20, 0, 0).setGridLayout(1, 2, 10, 10).add(new BaseLabel("닉네임"));
		jpT.jpC.add(jcNick = new JComboBox<>());
		jcNick.addItem("혜교");
		
		jpC.addChild();
		jpC.jpL.setEmpty(0, 10, 0, 10).add(bLeft = new BaseLabel("◀").setFont("맑은 고딕", 20));
		jpC.jpC.setFlowLayout(FlowLayout.CENTER).setEmpty(40, 0, 0, 0).add(pcikT = new ImageLabel(0, "stylist", "1", 200, 200).setCenter());
		jpC.jpR.setEmpty(0, 10, 0, 10).add(bRight = new BaseLabel("▶").setFont("맑은 고딕", 20));
		jpC.jpB.setFlowLayout(FlowLayout.CENTER).add(blName = new BaseLabel("혜교").setFont("맑은 고딕", 20));
		
		jpB.addChild();
		
		Vector<Vector<String>> data = db.getDb("SELECT count(sname),specialty, case \r\n"
				+ "when closedDay = 1 then '일'\r\n"
				+ "when closedDay = 2 then '월'\r\n"
				+ "when closedDay = 3 then '화'\r\n"
				+ "when closedDay = 4 then '수'\r\n"
				+ "when closedDay = 5 then '목'\r\n"
				+ "when closedDay = 6 then '금'\r\n"
				+ "when closedDay = 7 then '토' end\r\n"
				+ ",explanatioin\r\n"
				+ "FROM hair.stylist s join hair.reservation r on \r\n"
				+ "s.sno = r.sno\r\n"
				+ "join hair.review re\r\n"
				+ "on r.rno = re.rno\r\n"
				+ "where sname = '혜교'\r\n"
				+ "group by sname;");
		
		jpB.jpT.setFlowLayout(FlowLayout.CENTER).add(jl = new BaseLabel("<html> <p text-align: center>방문자리뷰: " + data.get(0).get(0) + "건<br><br>" + "전문 : #"
					+ data.get(0).get(1) + "<br><br> 매주 " + data.get(0).get(2) + " 휴무<br><br>"
					+ " </p>").setCenter());
		jpB.jpC.add(jl2 = new JLabel(data.get(0).get(3),JLabel.CENTER));
		
		if(ResModel.login == true) {
			jpB.jpB.setFlowLayout(FlowLayout.CENTER).add(jbres);
		}else {
			System.out.println("로그인해주세요");
		}
		
		LocalDate today = LocalDate.now();
		
		DayOfWeek dayow = today.getDayOfWeek();
		
		String[] day = "월,화,수,목,금,토,일".split(",");
		
		String koday = day[dayow.getValue() - 1];
		
		if(koday.equals(data.get(0).get(2))) {
			jbres.setEnabled(false);
			System.out.println("?");
		}else {
			System.out.println("오늘 요일은" + data.get(0).get(2));
		}
	}

	@Override
	public void Event() {
		// TODO Auto-generated method stub
		jbres.addActionListener(e -> {
			Msg.Info("스타일리스트가 변경되었습니다.");
			
			dispose();
			new Mypage();
		});
		
		jc.addActionListener(e -> {
			jcNick.removeAllItems();
			int grade = jc.getSelectedIndex() + 1;
			
			Vector<Vector<String>> data = db.getDb("SELECT sno,position,sname FROM hair.stylist where position = ?;",grade);
			
			for (int i = 0; i < data.size(); i++) {
				jcNick.addItem(data.get(i).get(2));
			}
		});
		
		bRight.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				super.mouseClicked(e);
				jpC.jpC.removeAll();
				if(num == 11) {
					num = 1;
				}else {
					num++;
				}
				jpC.jpC.setFlowLayout(FlowLayout.CENTER).setEmpty(40, 0, 0, 0).add(pcikT = new ImageLabel(0, "stylist", num + "", 200, 200).setCenter());
				
				Vector<Vector<String>> data = db.getDb("SELECT r.sno,sname,count(sname),specialty, case \r\n"
						+ "when closedDay = 1 then '일'\r\n"
						+ "when closedDay = 2 then '월'\r\n"
						+ "when closedDay = 3 then '화'\r\n"
						+ "when closedDay = 4 then '수'\r\n"
						+ "when closedDay = 5 then '목'\r\n"
						+ "when closedDay = 6 then '금'\r\n"
						+ "when closedDay = 7 then '토' end\r\n"
						+ ",explanatioin\r\n"
						+ "FROM hair.stylist s join hair.reservation r on \r\n"
						+ "s.sno = r.sno\r\n"
						+ "join hair.review re\r\n"
						+ "on r.rno = re.rno\r\n"
						+ "where r.sno = ?\r\n"
						+ "group by sname;",num);
				
				blName.setText(data.get(0).get(1));
				jl.setText("<html> <p text-align: center>방문자리뷰: " + data.get(0).get(2) + "건<br><br>" + "전문 : #"
					+ data.get(0).get(3) + "<br><br> 매주 " + data.get(0).get(4) + " 휴무<br><br>"
					+ "</p>");
				jl2.setText(data.get(0).get(5));
				
				LocalDate today = LocalDate.now();
				
				DayOfWeek dayow = today.getDayOfWeek();
				
				String[] day = "월,화,수,목,금,토,일".split(",");
				
				String koday = day[dayow.getValue() - 1];
				
				if(koday.equals(data.get(0).get(4))) {
					jbres.setEnabled(false);
					System.out.println("?");
				}else {
					System.out.println("오늘 요일은" + data.get(0).get(4));
					jbres.setEnabled(true);
				}
				
				
				jpC.jpC.repaint();
				jpC.jpC.validate();
			}
		});
		
		bLeft.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				super.mouseClicked(e);
				jpC.jpC.removeAll();
				if(num == 0) {
					
					num = 11;
				}else {
					num--;
				}
				jpC.jpC.setFlowLayout(FlowLayout.CENTER).setEmpty(40, 0, 0, 0).add(pcikT = new ImageLabel(0, "stylist", num + "", 200, 200).setCenter());
				
				Vector<Vector<String>> data = db.getDb("SELECT r.sno,sname,count(sname),specialty, case \r\n"
						+ "when closedDay = 1 then '일'\r\n"
						+ "when closedDay = 2 then '월'\r\n"
						+ "when closedDay = 3 then '화'\r\n"
						+ "when closedDay = 4 then '수'\r\n"
						+ "when closedDay = 5 then '목'\r\n"
						+ "when closedDay = 6 then '금'\r\n"
						+ "when closedDay = 7 then '토' end\r\n"
						+ ",explanatioin\r\n"
						+ "FROM hair.stylist s join hair.reservation r on \r\n"
						+ "s.sno = r.sno\r\n"
						+ "join hair.review re\r\n"
						+ "on r.rno = re.rno\r\n"
						+ "where r.sno = ?\r\n"
						+ "group by sname;",num);
				
				blName.setText(data.get(0).get(1));
				jl.setText("<html> <p text-align: center>방문자리뷰: " + data.get(0).get(2) + "건<br><br>" + "전문 : #"
					+ data.get(0).get(3) + "<br><br> 매주 " + data.get(0).get(4) + " 휴무<br><br>"
					+ "</p>");
				jl2.setText(data.get(0).get(5));
				
				LocalDate today = LocalDate.now();
				
				DayOfWeek dayow = today.getDayOfWeek();
				
				String[] day = "월,화,수,목,금,토,일".split(",");
				
				String koday = day[dayow.getValue() - 1];
				
				if(koday.equals(data.get(0).get(4))) {
					jbres.setEnabled(false);
					System.out.println("?");
				}else {
					System.out.println("오늘 요일은" + data.get(0).get(4));
					jbres.setEnabled(true);
				}
				
				System.out.println(num);
				
				jpC.jpC.repaint();
				jpC.jpC.validate();
			}
		});
		
		jc.addActionListener(e -> {
			String grade = jc.getSelectedItem().toString();
			int nickName = jcNick.getSelectedIndex() + 1;
			
			System.out.println(grade);
			System.out.println(nickName);
		});
	}

}

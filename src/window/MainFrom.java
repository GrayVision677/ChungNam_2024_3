package window;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import ResModel.ResModel;
import base.Comp.BaseButton;
import base.Comp.BaseFrame;
import base.Comp.BaseLabel;
import base.Comp.ImageLabel;
import jdbc.DbManager;
import jdbc.Msg;

public class MainFrom extends BaseFrame{

	private JLabel jl;
	private JTextField jtid;
	private JTextField jtPw;
	private BaseButton jblogin;
	private DbManager db;
	private BaseButton jblogout;
	
	String[] text = "스타일리스트,시술안내,매출분석,예약".split(",");
	BaseLabel[] bl = new BaseLabel[text.length];
	
	String[] title = "월별예약현황,일별예약현황,리뷰현황".split(",");
	BaseLabel[] bl1 = new BaseLabel[title.length];
	
	private BaseLabel gender;
	private BaseLabel genderM;
	private BaseLabel genderG;

	public MainFrom() {
		// TODO Auto-generated constructor stub
		super.setFrame("메인", 659,444);
	}

	@Override
	public void Comp() {
		// TODO Auto-generated method stub
		db = new DbManager();
		jblogout = new BaseButton("로그아웃").setColor(Color.GREEN, Color.black);
		genderM = new BaseLabel("♠");
		genderG = new BaseLabel("♤");
	}

	@Override
	public void DS() {
		// TODO Auto-generated method stub
		jpT.addChild();
		jpT.jpT.setColor().setFlowLayout(FlowLayout.CENTER).add(new BaseLabel("한국 뷰티헤어").setFont("맑은 고딕", 25));
		
		
		for (int i = 0; i < text.length; i++) {
			jpT.jpC.setGridLayout(1, 4, 10, 10).setColor().add(bl[i] = new BaseLabel(text[i]).setFont("맑은 고딕", 18));
			bl[i].setHorizontalAlignment(JLabel.CENTER);
			bl[i].setVerticalAlignment(JLabel.CENTER);
			bl[i].setBorder(new EmptyBorder(0, 20, 0, 10));
		}
		
		jpC.setline(1, 1, 0, 1, Color.black).addChild();
		jpC.jpL.setEmpty(10, 10, 10, 10).add(new ImageLabel("<html> 경국 구미시 구미로 123 <br> ☎010-1234-1234", "main", 300, 200)
				.setBottom());
		
		JLabel jl = new JLabel();
		jpC.jpC.addChild();
		jpC.jpC.jpC.setEmpty(10, 10, 10, 10).add(new ImageLabel(jl, "1", 300, 200));
		jpC.jpC.jpB.setFlowLayout(FlowLayout.CENTER).add(new BaseLabel("○"));
		jpC.jpC.jpB.setFlowLayout(FlowLayout.CENTER).add(new BaseLabel("○"));
		jpC.jpC.jpB.setFlowLayout(FlowLayout.CENTER).add(new BaseLabel("●"));
		jpC.jpC.jpB.setFlowLayout(FlowLayout.CENTER).add(new BaseLabel("○"));
		jpC.jpC.jpB.setFlowLayout(FlowLayout.CENTER).add(new BaseLabel("○"));
		
		jpB.setGridLayout(1, 5, 10, 10).setEmpty(10, 20, 10, 20).add(new BaseLabel("ID "));
		jpB.add(jtid = new JTextField(15));
		jpB.add(new BaseLabel("PW"));
		jpB.add(jtPw = new JTextField(15));
		jpB.add(jblogin = new BaseButton("로그인").setColor(Color.GREEN, Color.black));
	} 

	@Override
	public void Event() {
		// TODO Auto-generated method stub
		jblogin.addActionListener(e -> {
			String id = jtid.getText().trim();
			String pw = jtPw.getText().trim();
			
			if(id.isBlank() || pw.isBlank()) {
				Msg.error("빈칸이 있습니다.");
				return;
			}
			
			Vector<Vector<String>> user = db.getDb("SELECT id,pw,uname,case when gender = 1 then '남' else '여' end as gender_display,uno FROM hair.user where id = ? and pw = ?;"
					,id,pw);
			
			if(user.size() != 0) {
				Msg.Info(user.get(0).get(2) + "님 환영합니다.");
				ResModel.login = true;
				jpB.removeAll();
				
				jtid.setText("");
				jtPw.setText("");
				jpB.setGridLayout(1, 6, 10, 10).setEmpty(10, 20, 10, 20).add(new BaseLabel("ID "));
				jpB.add(jtid = new JTextField(15));
				jpB.add(new BaseLabel("PW"));
				jpB.add(jtPw = new JTextField(15));
				jpB.add(jblogout);
				
				if(user.get(0).get(3).equals("남")) {
					jpB.add(genderM);
				}else {
					
					jpB.add(genderG);
				}
				
				jpB.repaint();
				jpB.validate();
				return;
			}
			
			if(id.equals("admin") && pw.equals("1234")) {
				jpT.jpC.removeAll();
				jpB.removeAll();
				
				ResModel.admin = true;
				
				jtid.setText("");
				jtPw.setText("");
				
				for (int i = 0; i < title.length; i++) {
					jpT.jpC.setGridLayout(1, 3, 10, 10).setColor().add(bl1[i] = new BaseLabel(title[i]).setFont("맑은 고딕", 18));
					bl1[i].setHorizontalAlignment(JLabel.CENTER);
					bl1[i].setVerticalAlignment(JLabel.CENTER);
					bl1[i].setBorder(new EmptyBorder(0, 40, 0, 30));
					
				}
				
				bl1[2].addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						// TODO Auto-generated method stub
						super.mouseClicked(e);
						dispose();
						new ReviewFrom();
					}
				});
				
				jpB.setGridLayout(1, 6, 10, 10).setEmpty(10, 20, 10, 20).add(new BaseLabel("ID "));
				jpB.add(jtid = new JTextField(15));
				jpB.add(new BaseLabel("PW"));
				jpB.add(jtPw = new JTextField(15));
				jpB.add(jblogout);
				
				jpT.jpC.repaint();
				jpT.jpC.validate();
				jpB.repaint();
				jpB.validate();
			}
			
		});
		
		jblogout.addActionListener(e -> {
			jpT.jpC.removeAll();
			jpB.removeAll();
			
			ResModel.login = false;
			
			jtid.setText("");
			jtPw.setText("");
			
			for (int i = 0; i < text.length; i++) {
				jpT.jpC.setGridLayout(1, 4, 10, 10).setColor().add(bl[i] = new BaseLabel(text[i]).setFont("맑은 고딕", 18));
				bl[i].setHorizontalAlignment(JLabel.CENTER);
				bl[i].setVerticalAlignment(JLabel.CENTER);
				bl[i].setBorder(new EmptyBorder(0, 20, 0, 10));
			}
			
			jpB.setGridLayout(1, 5, 10, 10).setEmpty(10, 20, 10, 20).add(new BaseLabel("ID "));
			jpB.add(jtid = new JTextField(15));
			jpB.add(new BaseLabel("PW"));
			jpB.add(jtPw = new JTextField(15));
			jpB.add(jblogin = new BaseButton("로그인").setColor(Color.GREEN, Color.black));
			
			jpT.jpC.repaint();
			jpT.jpC.validate();
			jpB.repaint();
			jpB.validate();
			
		});
		
		genderM.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				super.mouseClicked(e);
				dispose();
				new Mypage();
			}
		});
		
		genderG.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				super.mouseClicked(e);
				dispose();
				new Mypage();
			}
		});
		
		bl[1].addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				super.mouseClicked(e);
				dispose();
				new hostFrom();
			}
		});
		
		bl[0].addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				dispose();
				new StyleList();
			}
		});
		
		bl[3].addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				super.mouseClicked(e);
				dispose();
				new StyleList();
			}
		});
		
		
		
		
	}


}

package winterProject5_connect6;

import java.awt.*;
import javax.swing.*;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

/*
 *정보
 2021-01-03-11:00~ 2021-01-04 ??:??
 2021-winter 과제5-1, 육목 게임환경 만들기 
 HGU 전산전자공학부_ 22100579 이진주
 */

/*
 *Todo
 1. 육목판때기 만들기..(프레임-돌 패널- 버튼 패널)
 2. 버튼...하여튼 범위 인식하여 트리거로 사용 가능하게 하기(돌 놓기용)
 3. 
 배열로 두면 어떨까 싶음, 시각적으로 보이는거랑 내부적으로 넣어줄 판별기준값을 묶..어서 클래스 인스턴트로?
 //중복놓기 불가능, 두번 실행마다 돌의 색/성격 바뀌도록... (배열에 넣어주는 값을 바꾸자 1이나 2)
 **투명도 실험
 

 */

public class Main extends JFrame{
	
	//자동실행, 바둑판 그리기 
	@Override //그냥...실행되는놈임 
	 public void paint(Graphics g) {
	      super.paint(g);
	      System.out.println("선긋는중...");

			for(int i = 0; i < 19; i ++) {
				
				g.drawLine(30+i*40, 90, 30+i*40, 810);
				g.drawLine(30, 20+i*40+70, 750, 20+i*40+70);
				
				//좌표라벨
				g.drawString(i+1+"", 25+i*40, 80); //가로
				g.drawString(i+1+"", 13, 22+i*40+72); //세로
				
				//점
				if((i == 3 || i == 9) || i == 15) {
					g.fillOval(30+i*40-3, 20+3*40+70-3, 6,6); 
					g.fillOval(30+i*40-3, 20+9*40+70-3, 6,6);
					g.fillOval(30+i*40-3, 20+15*40+70-3, 6,6);
				}
			}
		}
	

	static JPanel backGround; //배경 그려지고 돌 놓아질 그곳
	static int x, y; //현재좌표 
	static Color COR; //색
	
	static boolean on = false; //게임중인가?(돌이 놓아지는가에 대한 어쩌구) 
	
	static JPanel showTurn; //배경색으로 차례 표시 
	
	//육목 판 만들기, 기본세팅 
	public Main() {
		
		int width = 900, height = 900;
		//기본껍데기
		setSize(width, height); //프레임의 사이즈 설정
		setResizable(false);//사용자가 임의로 프레임의 크기를 변경시킬 수 있는가>> 앙대
		setLocationRelativeTo(null);//화면의 어느 위치에서 첫 등장할지>> null이면 자동 센터지정 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //창닫으면 프로그램종료
		setLayout(null); //레이아웃설정
		
		//백그라운드 패널(여기다 돌 둘거임)
		backGround = new JPanel();
		backGround.setBounds(0, 0, width, height);
		backGround.setBackground(new Color(200,160,100));
		backGround.setLayout(null);
		add(backGround); //보여랏
		
		//현재 차례 표시할 어쩌구
		JLabel turnInfo = new JLabel("이번 차례");
		turnInfo.setBounds(770, 50, 200, 50);
		backGround.add(turnInfo);
		
		showTurn = new JPanel();
		showTurn.setBounds(770, 90, 70, 70);
		showTurn.setBackground(Color.black);
		backGround.add(showTurn);
		
		//다시하기
		JButton reset = new JButton("새로운 게임");
		reset.setBounds(760, 180, 100, 50);
		reset.setBackground(Color.LIGHT_GRAY);
		backGround.add(reset);
		reset.addActionListener(event->{
			repaint(); //게임판 리셋하고
			startGame(); //새 게임 시작
		});
		
	
		setVisible(true); //쨘
		startGame();
		
		//돌 넣기... 
		backGround.addMouseListener(new MouseAdapter() {
			public void mouseClicked (MouseEvent e) {
				
				if(on) {
					//이상적인 위치선정(공식 만들어 사용함...)
					double n = ((float)e.getX()-(float)44)/(float)40;
					if(n < 0.05) {
						x = 0;
					}else {
						x = (int)n+1;
					}
					n = ((float)e.getY()-(float)80)/(float)40;
					if(n < 0.05) {
						y = 0;
					}else {
						 y = (int)n+1;
					}
					PlayBoard.setStone();
				}

			}
		});
		
	}
	
	
	//메인함수. 그저 실행할 뿐 
	public static void main(String[] args) {
		System.out.println("Hello World!");
		new Main();
			
	}

	//게임진행 
	public static void startGame() {
		
		setBasicStone(); //중립구 놓기
		new PlayBoard();
		on = true; //게임시쟉 
		
	}

	//중립구 놓기 
	public static void setBasicStone(){
		
		//중립구 갯수 받아오기... (실패)
		JFrame f = new JFrame();
		f.setSize(300,150); //프레임의 사이즈 설정
		f.setResizable(false);//사용자가 임의로 프레임의 크기를 변경시킬 수 있는가>> 앙대
		f.setLocationRelativeTo(null);//화면의 어느 위치에서 첫 등장할지>> null이면 자동 센터지정 
		f.setLayout(null); //레이아웃설정
		
		JLabel info = new JLabel("      시작시 놓일 중립구의 수를 입력하세요");
		info.setBounds(5, 5, 280, 30);
		f.add(info);
		
		JTextField getNum = new JTextField();
		getNum.setBounds(5, 40, 280, 30);
		f.add(getNum);
		
		JButton get = new JButton("confirm");
		get.setBounds(5, 80, 280, 30);
		f.add(get);
		get.addActionListener(event->{
			
			int count = 0;
			int n = Integer.parseInt(getNum.getText());
			//입력받은 갯수만큼의 난수위치에 중립구 놓기 
			while(count < n) {
				x = (int)(Math.random()*18); //0~18범위의 난수 생성 
				y = (int)(Math.random()*18); //0~18범위의 난수 생성 
				if(PlayBoard.playBoard[x][y] == 0){ //빈자리라면
					PlayBoard.count = -1; //회색으로 염색시킨 중립구를
					PlayBoard.putStone(); //난수위치에 둔다
					count ++;
				}
			}
			PlayBoard.count = 1; //다음차례때 흑돌 하나로 시작하도록 세팅
			f.dispose(); //창닫기
			
		});
		
		f.setVisible(true);
		
	}
	
	//승리메세지
	public static void winPopUp() {
		JFrame pop = new JFrame(); //팝업창
  	  	JOptionPane.showMessageDialog(pop, "게임 종료!\n (승자: "+ ((PlayBoard.c == 1)? "흑돌)":"백돌)"));
  	  	on = false; //게임끗 
	}

}


//돌.... 시각적으로 돌이 놓일 위치좌표(픽셀기준)랑 내부적으로 인식될 좌표(배열 인덱스) 포함
class PlayBoard{
	//게임판
	static int visualBoard[][][];
	static int playBoard[][]; //내부
	
	PlayBoard(){ //초기설정. 
		playBoard = new int[19][19]; //0-18의 가로세로공간 부여
		visualBoard = new int[19][19][2]; //0-18의 가로세로공간, 
		
		for(int i = 0; i < 19; i++) {
			for(int j = 0; j < 19; j++) {
				playBoard[i][j] = 0;
				visualBoard[i][j][0] = 24+i*40; //x좌표
				visualBoard[i][j][1] = 60+j*40; //y좌표
				
			}
		}
	}

	static int c; //돌 색깔..! 1이면 백돌 2면 흑돌 5면 거시기 그거 중립구 
	static int count = 0;

	static Graphics g = Main.backGround.getGraphics();
	
	//돌 배치하기
	static void setStone() {
		//System.out.println("playBoard["+(Main.x+1)+"]["+(Main.y+1)+"] = "+ playBoard[Main.x][Main.y]);
		if(Main.x < 19 && Main.y < 19) { //범위 넘지 않는 선에서 
			if(playBoard[Main.x][Main.y] == 0){//빈자리라면 돌 배치
				putStone(); //돌 놓기 
				playBoard[Main.x][Main.y] = c; //돌 넣기 
				if(c != 5) System.out.printf("[system] (%d, %d) 에 %s을 배치했습니다\n", Main.x+1, Main.y+1, (c==1)?"흑돌":"백돌");
				changeTurn(); //현재차례 표시 바꾸기
				if(ScanBoard.scan()) {
					Main.winPopUp();
				}
			}else {
				System.out.println("[system] 이미 놓여진 자리입니다.");
				//System.out.println("playBoard["+(Main.x+1)+"]["+(Main.y+1)+"] = "+ playBoard[Main.x][Main.y]);
			}
		}
	}
	
	//돌 넣는 메서드
	static void putStone() {
		
		if(count == -1) { //회색 
			g.setColor(Color.gray);
		}else if(count < 2) { //흑돌
			c = 1;
			g.setColor(Color.black);
		}else { //백돌 
			c = 2;
			g.setColor(Color.white);
		}
		
		g.fillOval(PlayBoard.visualBoard[Main.x][Main.y][0]-20, PlayBoard.visualBoard[Main.x][Main.y][1]-20, 40, 40);
		
		
		count++;
		if(count >3) {
			count = 0;
		}
		
		
	}

	//현재 차례 표시시키는 메서드
	static void changeTurn() {
		if(count == -1) { //회색 
			//블랙
		}else if(count < 2) { //흑돌
			Main.showTurn.setBackground(Color.black);
		}else { //백돌 
			Main.showTurn.setBackground(Color.WHITE);
		}
	}
	
	//인자로 받은 좌표위치 돌에 빨간 막 씌우는 메서드(승리구 표시용)
	static void markStone(int x, int y) {
		g.setColor(new Color(255,0,0,80));
		PlayBoard.g.fillOval(PlayBoard.visualBoard[x][y][0]-21, PlayBoard.visualBoard[x][y][1]-21, 42, 42);
	}

}

//판정하는놈...!! 
class ScanBoard {
	
	static int x;
	static int y;
	static boolean flag = false; //승리판정 및 반환용
	static int connect[] = {0,0,0,0}; //0세로 1가로 2우대각 3좌대각 
	static int i; //뭐...
	

	//세로판정(아래로/위로)
	static void sero() {
		i = 1;
		while(true) {
			if(y+i > 18) break; //인덱스 넘어갈라카면 스탑 
			//System.out.println(x+","+y+"="+playBoard[x][y+i] );
			if(PlayBoard.playBoard[x][y+i] == PlayBoard.c) {
				if(flag) { //판정 난 상태라면 빨간테두ㄹㅣ 씌워주기
					PlayBoard.markStone(x, y+i);
				}else {
					connect[0]++; //일치시 카운트 증가
				}
			}
			else break; //불일치시 즉시스탑
			i++;
		}
		i = 1;
		while(true) {
			if(y-i < 0) break; //인덱스 넘어갈라카면 스탑 
			if(PlayBoard.playBoard[x][y-i] == PlayBoard.c) {
				if(flag) { //판정 난 상태라면 빨간테두ㄹㅣ 씌워주기
					PlayBoard.markStone(x, y-i);
				}else {
					connect[0]++; //일치시 카운트 증가
				}
			}
			else break; //불일치시 즉시스탑
			i++;
		}
		System.out.println("세로연속점"+connect[0]+"개");
		
	}

	//가로판정
	static void garo() {
		i = 1;
		while(true) {
			if(x+i > 18) break; //인덱스 넘어갈라카면 스탑 
			//System.out.println(x+","+y+"="+playBoard[x][y+i] );
			if(PlayBoard.playBoard[x+i][y] == PlayBoard.c) {
				if(flag) { //판정 난 상태라면 빨간테두ㄹㅣ 씌워주기
					PlayBoard.markStone(x+i, y);
				}else {
					connect[1]++; //일치시 카운트 증가
				}
			}
			else break; //불일치시 즉시스탑
			i++;
		}
		i = 1;
		while(true) {
			if(x-i < 0) break; //인덱스 넘어갈라카면 스탑 
			if(PlayBoard.playBoard[x-i][y] == PlayBoard.c) {
				if(flag) { //판정 난 상태라면 빨간테두ㄹㅣ 씌워주기
					PlayBoard.markStone(x-i, y);
				}else {
					connect[1]++; //일치시 카운트 증가
				}
			}
			else break; //불일치시 즉시스탑
			i++;
		}
		System.out.println("가로연속점"+connect[1]+"개");
		
	}

	//우대각판정
	static void wo() {
		i = 1;
		while(true) {
			if(y+i > 18 || x-i < 0) break; //인덱스 넘어갈라카면 스탑 
			//System.out.println(x+","+y+"="+playBoard[x][y+i] );
			if(PlayBoard.playBoard[x-i][y+i] == PlayBoard.c) {
				if(flag) { //판정 난 상태라면 빨간테두ㄹㅣ 씌워주기
					PlayBoard.markStone(x-i, y+i);
				}else {
					connect[2]++; //일치시 카운트 증가
				}
			}
			else break; //불일치시 즉시스탑
			i++;
		}
		i = 1;
		while(true) {
			if(x+i > 18 || y-i < 0) break; //인덱스 넘어갈라카면 스탑 
			if(PlayBoard.playBoard[x+i][y-i] == PlayBoard.c) {
				if(flag) { //판정 난 상태라면 빨간테두ㄹㅣ 씌워주기
					PlayBoard.markStone(x+i, y-i);
				}else {
					connect[2]++; //일치시 카운트 증가
				}
			}
			else break; //불일치시 즉시스탑
			i++;
		}
		System.out.println("우대각연속점"+connect[2]+"개");
		
	}

	//좌대각판정
	static void jwa() {
		i = 1;
		while(true) {
			if(y+i > 18 || x+i > 18) break; //인덱스 넘어갈라카면 스탑 
			//System.out.println(x+","+y+"="+playBoard[x][y+i] );
			if(PlayBoard.playBoard[x+i][y+i] == PlayBoard.c) {
				if(flag) { //판정 난 상태라면 빨간테두ㄹㅣ 씌워주기
					PlayBoard.markStone(x+i, y+i);
				}else {
					connect[3]++; //일치시 카운트 증가
				}
			}
			else break; //불일치시 즉시스탑
			i++;
		}
		i = 1;
		while(true) {
			if(x-i < 0 || y-i < 0) break; //인덱스 넘어갈라카면 스탑 
			if(PlayBoard.playBoard[x-i][y-i] == PlayBoard.c) {
				if(flag) { //판정 난 상태라면 빨간테두ㄹㅣ 씌워주기
					PlayBoard.markStone(x-i, y-i);
				}else {
					connect[3]++; //일치시 카운트 증가
				}
			}
			else break; //불일치시 즉시스탑
			i++;
		}
		System.out.println("좌대각연속점"+connect[3]+"개");
		
	}

	//여섯개짜리 연결이 있는지 판정 
	static boolean scan() {
		flag = false;
		//현시점의 x,y 받아오기 
		x = Main.x;
		y = Main.y;
		for(i = 0; i < 4; i++) { //초기화 
			connect[i] = 1;
		}
		//팔방으로 뒤지기..! 
		sero();
		garo();
		wo();
		jwa();
		
		//판별 
		for( i = 0; i < 4; i++) {
			if(connect[i] == 6) {
				flag = true;
				System.out.println("승리");
				//빨갛게 씌우기..!! 
				PlayBoard.markStone(x, y);
				if(i == 0) { //세로..
					sero();
					return flag;
				}else if(i == 1) { //가로...
					garo();
					return flag;
				}else if(i == 2) {//우대각..
					wo();
					return flag;
				}else { //좌대각..
					jwa();
					return flag;
				}
			}
		}
		return flag;
	}
	
}
	



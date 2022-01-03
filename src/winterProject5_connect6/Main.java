package winterProject5_connect6;

import java.awt.*;
import javax.swing.*;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
	      //g.setColor(Color.BLACK);

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
	
	
	//돌 하나 놔두는..거엿으면 좋겟다 
	public static void PUT(Graphics g) {
		if(put) {
			g.fillOval(PlayBoard.visualBoard[x][y][0]-20, PlayBoard.visualBoard[x][y][1]-20, 40, 40);
			put = false;
		}
	}
	
	static JPanel backGround; //배경 그려지고 돌 놓아질 그곳
	static JPanel sensor ; //인식..해서..그..어..응.. 거기..잘모르겟슴 
	static int x, y; //현재좌표 
	static Color COR; //색
	static boolean put = false; //매번 돌 놓을때마다 
	
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
		add(backGround); //보여랏
		
		//버튼패널..이름하야 센서.. 이게 겹쳐지는게 될런지 몰것음 
		sensor = new JPanel();
		sensor.setBounds(0, 0, width, height);
		sensor.setBackground(null);
		add(sensor); //보여랍
	
		setVisible(true); //쨘
		
		startGame();
		
		
		Graphics g = backGround.getGraphics();

		//돌 넣기... 
		backGround.addMouseListener(new MouseAdapter() {
			public void mouseClicked (MouseEvent e) {
				
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
			//whyrano@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
			//int num = Integer.getInteger(getNum.getText());
			//System.out.println(getNum.getText());
			
			
			for(int i = 5 ; i < 10; i ++) {
				
				x = i;
				y = i;
				PlayBoard.c = 5;
				PlayBoard.setStone();
			}

			
		});
		
		f.setVisible(true);
		
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
	
	//내부적으로 입력... 
	static void setStone() {
		//System.out.println("playBoard["+(Main.x+1)+"]["+(Main.y+1)+"] = "+ playBoard[Main.x][Main.y]);
		if(playBoard[Main.x][Main.y] == 0) { //빈자리라면 돌 배치
			putStone(); //돌 놓기 
			playBoard[Main.x][Main.y] = c; //돌 넣기 
			System.out.printf("[system] (%d, %d) 에 %s을 배치했습니다\n", Main.x+1, Main.y+1, (c==1)?"흑돌":"백돌");
		}else {
			System.out.println("[system] 이미 놓여진 자리입니다.");
			System.out.println("playBoard["+(Main.x+1)+"]["+(Main.y+1)+"] = "+ playBoard[Main.x][Main.y]);
		}

	}
	
	//돌 넣는 메서드
	static void putStone() {
		
		if(count < 2) { //흑돌
			c = 1;
			g.setColor(Color.black);
		}else { //백돌 
			c = 2;
			g.setColor(Color.white);
		}
		
		if(c == 5) {
			g.setColor(Color.red);
		}
		
		Main.put = true;
		Main.PUT(g);
		
		count++;
		if(count >3) {
			count = 0;
		}
		
	}
	
	
	//playBoard를 스캔해서 조건 어쩌구 하는 메서드도 여기 만들면 좋을듯 
	
	
	
}



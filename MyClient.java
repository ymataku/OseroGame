import java.net.*;
import java.io.*;
import javax.swing.*;
import java.lang.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import javax.swing.Timer;
import java.awt.Font;

public class MyClient extends JFrame implements MouseListener,MouseMotionListener,ActionListener{
	private JButton buttonArray[][];//ボタン用の配列
	private JButton passbutton[];
	private JButton theButtonStart,theButtonlose;
	private Container c;
	private ImageIcon blackIcon, whiteIcon, boardIcon,passbtn,startbtn,losebtn;
	private ImageIcon blacknumber,whitenumber;
	private ImageIcon  background,footer,winback,loseback,drawback;
	private int start = 0;
	private int myColor;
	private ImageIcon myIcon, yourIcon,submyIcon,subyourIcon;
	private int myTurn = 0;
	private int flip = 0;
	private int passcount1=0;
	private int passcount2=0;
	private int myCount = 1000;
	private int timercheck = 0;
	private int black = 0,white = 0;
	private int end = 0;
	int check = 0;
	int checking = 0;
    JProgressBar theProgressBar;
	Timer timer;
	JLabel theLabelForProgressBar;
	JLabel theLabelA;
	JLabel theblacknumber,blackint,thewhitenumber,whiteint;
	JLabel thebackground;
	JLabel thebacknumber;
	JLabel cross;
	JLabel winlabel,loselabel,drawlabel;
	JLabel popup;
	JLabel thefooter;
	JFrame pop = new JFrame();
	PrintWriter out;//出力用のライター

	public MyClient() {
		//名前の入力ダイアログを開く
		String myName = JOptionPane.showInputDialog(null,"名前を入力してください","名前の入力",JOptionPane.QUESTION_MESSAGE);
		String myIP = JOptionPane.showInputDialog(null,"IPアドレスを入力してください","IPアドレスの入力",JOptionPane.QUESTION_MESSAGE);
	
		if(myName.equals("")){
			myName = "No name";//名前がないときは，"No name"とする
		}
		
		//ウィンドウを作成する
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//ウィンドウを閉じるときに，正しく閉じるように設定する
		setTitle("MyClient");//ウィンドウのタイトルを設定する
		setSize(767,645);//ウィンドウのサイズを設定する
		c = getContentPane();//フレームのペインを取得する

		//アイコンの設定
		//オセロの駒
		// System.out.println("white");
		whiteIcon = new ImageIcon("White5.png");
		blackIcon = new ImageIcon("Black5.png");
		boardIcon = new ImageIcon("nothing.png");
        //パスボタン
		passbtn = new ImageIcon("pass3.png");
		//スタートボタン
		startbtn =  new ImageIcon("start3.png");
		//サイド背景
		background = new ImageIcon("fram5.png");
		//アイコン用の黒の駒
		blacknumber = new ImageIcon("blackicon.png");
		//アイコン用の白の駒
		whitenumber = new ImageIcon("whiteicon.png");
		losebtn = new ImageIcon("lose3.png");
        //下側の背景
		footer = new ImageIcon("testbackgroud2.png");
		//負けたときの演出
		loseback = new ImageIcon("loseexample1.png");
		//勝った時の演出
		winback = new ImageIcon("winexample1.png");
		
		//引き分けたときの演出
		drawback = new ImageIcon("draw.png");

		c.setLayout(null);//自動レイアウトの設定を行わない
		//ボタンの生成
		buttonArray = new JButton[8][8];//ボタンの配列を５個作成する[0]から[4]まで使える
		passbutton = new JButton[1];
		
		for(int i=0;i<8;i++){
			for(int j=0;j<8;j++){
				if(i==4&&j==4){
					buttonArray[i][j] = new JButton(whiteIcon);
				}else if(i==4&&j==3){
				buttonArray[i][j] = new JButton(blackIcon);//ボタンにアイコンを設定する
				}else if(i==3&&j==4){
					buttonArray[i][j] = new JButton(blackIcon);
				}else if(i==3&&j==3){
					buttonArray[i][j] = new JButton(whiteIcon);
				}else {
					buttonArray[i][j] = new JButton(boardIcon);
				}
				
				c.add(buttonArray[i][j]);//ペインに貼り付ける
			
				buttonArray[i][j].setBounds(j*60,60*i,60,60);//ボタンの大きさと位置を設定する．(x座標，y座標,xの幅,yの幅）
				buttonArray[i][j].addMouseListener(this);//ボタンをマウスでさわったときに反応するようにする
				buttonArray[i][j].setActionCommand(Integer.toString(i*8+j));//ボタンに配列の情報を付加する（ネットワークを介してオブジェクトを識別するため）
        	    
			}
		}
		//パスボタン作成
		passbutton[0] = new JButton(passbtn);
		c.add(passbutton[0]);
		passbutton[0].setBounds(480,480,135,124);
		passbutton[0].addMouseListener(this);
		passbutton[0].setActionCommand(Integer.toString(8*8+8));

		//結果表示ラベル
		theLabelA = new JLabel("結果表示ラベル");
        c.add(theLabelA);
        theLabelA.setBounds(320,350,120,25);
        theLabelA.addMouseListener(this);
		theProgressBar = new JProgressBar(1,1000);
        theProgressBar.setValue(1000);
		
  

		//スタートボタン作成
		theButtonStart = new JButton(startbtn);
        c.add(theButtonStart);
        theButtonStart.setBounds(615,480,135,124);
        theButtonStart.addActionListener(this);
		//スタートボタンが一回しか機能しないようにするためのアクション設定
        theButtonStart.setActionCommand("judgement");
		
		//降参ボタン作成
		theButtonlose = new JButton(losebtn);
		c.add(theButtonlose);
        theButtonlose.setBounds(615,480,135,124);
        theButtonlose.addActionListener(this);
        theButtonlose.setActionCommand("lose");

		//画面の下部分の背景作成
		thefooter = new JLabel(footer);
		c.add(thefooter);
		thefooter.setBounds(0,480,480,124);

		//プログレスバーの値取得and表示
		String test = String.format("%04d",theProgressBar.getValue());
		theLabelForProgressBar = new JLabel(test);
        c.add(theLabelForProgressBar);
        theLabelForProgressBar.setBounds(500,200,700,400);
		theLabelForProgressBar.setFont(new Font("Comic Sans MS",Font.BOLD,95));

		//勝った時の表示インスタンス化
		winlabel = new JLabel(winback);

		//負けて時の表示インスタンス化
		loselabel = new JLabel(loseback);

		//引き分けの時の表示のインスタンス化
		drawlabel = new JLabel(drawback);

		//サーバに接続する
		Socket socket = null;
		if(myIP.equals("")){
			try{
				socket = new Socket("localhost", 10000);
			} catch (UnknownHostException e) {
				System.err.println("ホストの IP アドレスが判定できません: " + e);
			} catch (IOException e) {
				System.err.println("エラーが発生しました: " + e);
			}
		
		}
		MesgRecvThread mrt = new MesgRecvThread(socket, myName);//受信用のスレッドを作成する
		mrt.start();//スレッドを動かす（Runが動く）
	}
	//メッセージ受信のためのスレッド
	public class MesgRecvThread extends Thread {
		Socket socket;
		String myName;
		public MesgRecvThread(Socket s, String n){
			socket = s;
			myName = n;
		}
		//通信状況を監視し，受信データによって動作する
		public void run() {
			try{
				InputStreamReader sisr = new InputStreamReader(socket.getInputStream());
				BufferedReader br = new BufferedReader(sisr);
				out = new PrintWriter(socket.getOutputStream(), true);
				out.println(myName);//接続の最初に名前を送る
				String myNumberStr = br.readLine();
				int myNumberInt = Integer.parseInt(myNumberStr);
			    // System.out.println(myNumberInt);
				
				if(myNumberInt % 2 == 0){
					myColor = 0;
					myTurn = 0;
				}else{
					myColor = 1;
					myTurn = 0;
				}
				//バックグラウンドの表示を作成
				set();

				//自分の駒の色をポップアップで表示
				if(myColor == 1){
					JOptionPane.showMessageDialog(pop,"あなたは黒です");
				}else if(myColor == 0){
					JOptionPane.showMessageDialog(pop,"あなたは白です");	
				}
				
				while(true) {
					if(end == 1){
						break;
					}
					int turnchange = 0;
					String inputLine = br.readLine();//データを一行分だけ読み込んでみる
					if (inputLine != null) {//読み込んだときにデータが読み込まれたかどうかをチェックする
						// System.out.println(inputLine);//デバッグ（動作確認用）にコンソールに出力する
						String[] inputTokens = inputLine.split(" ");	//入力データを解析するために、スペースで切り分ける
						String cmd = inputTokens[0];//コマンドの取り出し．１つ目の要素を取り出す
						String theBName = inputTokens[1];//ボタンの名前（番号）の取得	
						int theBnum = Integer.parseInt(theBName);
						int x = theBnum % 8;
						int y = theBnum / 8;
						if(cmd.equals("PLACE")){
							int theColor = Integer.parseInt(inputTokens[2]);
							int putcheck = 0;
							if(theColor == 0){
								myIcon = whiteIcon;
								yourIcon = blackIcon;
								passcount1 = 0;
							}else if(theColor == 1){
								myIcon = blackIcon;
								yourIcon = whiteIcon;
								passcount2 = 0;
							}
							//周囲に相手の駒があるかの判定
							if(judgeButton(y,x)){
								int popupcount = 0;
								for(int i=-1;i<2;i++){
									for(int j=-1;j<2;j++){
										flip = filpButtons(y,x,i,j);
										//ひっくり返せる駒がいくつあるかの判定
										if(flip > 0){
											putcheck = 1;
											turnchange = 1;

											//ひっくり返す駒の色をプレイヤーごとに分ける。
											if(theColor == myTurn){
												buttonArray[y][x].setIcon(myIcon);
											} else {
												buttonArray[y][x].setIcon(yourIcon);
											}

											for(int dy=i, dx=j, k=0; k<flip; k++, dy+=i, dx+=j){
												int msgy = y + dy;
												int msgx = x + dx;
												int theArrayIndexe = msgy*8 + msgx;
												String msge = "FLIP"+" "+theArrayIndexe+" "+myColor;
												out.println(msge);
												out.flush();
											}
										}
									}	
								}
								//置いた場所がひっくり返せない判定が出たときポップアップで表示
								if(putcheck == 0){
									if(myColor == myTurn){
										JOptionPane.showMessageDialog(pop,"おけません");
										
									}
								}
							}else{
								//自分のターンでないときに押してしまっときのポップアップお知らせ。
								if(myTurn == myColor){
								 JOptionPane.showMessageDialog(pop,"おけません");
								}

							}
							//駒を置いた後、相手側のタイマーを発動させてからターンを交代する。
							if(turnchange == 1){
								// System.out.println("turnchange enter");
								myTurn = 1 - myTurn;
								ActionEvent ee = new ActionEvent(this,0,"ProgressBarStart");
								actionPerformed(ee);
							}
						//placeから送られてきた値を実際にひっくり返す命令
						}else if(cmd.equals("FLIP")){
							int theColor = Integer.parseInt(inputTokens[2]);
							// System.out.println("color check"+x+" "+y+" "+myIcon);
							buttonArray[y][x].setIcon(myIcon);

						//パスを行うときの命令
						}else if(cmd.equals("pass")){
							int passcheck = Integer.parseInt(inputTokens[1]);
							if(passcheck == 1000){
								// System.out.println("no count");
							}else{
								int theColor = Integer.parseInt(inputTokens[2]);
								if(theColor == 0){
									passcount1++;
								}else if(theColor == 1){
									passcount2++;
								}
							}
							//ターンを交代と同時に相手のタイマーを発動
							myTurn = 1 - myTurn;
							ActionEvent ee = new ActionEvent(this,0,"ProgressBarStart");
							actionPerformed(ee);

						//スタートボタンが押されたときの命令。
						}else if(cmd.equals("start")){
							start =+theBnum;
							c.remove(theButtonStart);

					//降参ボタンが押されたときの命令。
					}else if(cmd.equals("lose")){
						//勝った方の表示
						if(myColor == theBnum){
							c.removeAll();
							c.add(loselabel);
							loselabel.setBounds(0,0,767,645);
							timer.stop();
						}else{
							//負けた方の表示
							c.removeAll();
							c.add(winlabel);
							winlabel.setBounds(0,0,767,645);
							timer.stop();
						}
					}else{
						break;
						}
					}	
					//バックグラウンドの更新
					set();	
					
					if(myTurn == myColor){
						if(autopass() == 1){
							System.out.println("no autopass"+autopass());
						}else if(autopass() == 0){
							int theArrayIndex = 10000;
							String msg = "pass"+" "+theArrayIndex+" "+myTurn;
							out.println(msg);//送信データをバッファに書き出す
							out.flush();
							
						}	
					}
					System.out.println("--------this is autopass judgement-------------------------");
					
					
				}
				socket.close();
			}catch (IOException e) {
				System.err.println("エラーが発生しました: " + e);
			}
		}
	}
	public static void main(String[] args) {
		MyClient net = new MyClient();
		net.setVisible(true);
		
	}
	
	//勝敗がついたかの判断をする
	public int [] judgement(){
	
		int check = 0;
		int judge = 4;
		int blackcount = 0;
		int whitecount = 0;
		int [] judgement = {judge,blackcount,whitecount};
        for(int i=0;i<8;i++){
			for(int j=0;j<8;j++){
				if(buttonArray[j][i].getIcon() == boardIcon){
                    check = 1;
					
				}else if(buttonArray[j][i].getIcon() == blackIcon){
					judgement[1] += 1; 
				}else if(buttonArray[j][i].getIcon() == whiteIcon){
					judgement[2] += 1;
				}
			}
		}
		
		if(check == 0){
			if(judgement[1] > judgement[2]){
				judgement[0] = 2;
			}else if(judgement[2] > judgement[1]){
				judgement[0] = 1;
			}else if(judgement[1] == judgement[2]){
				judgement[0] = 0;
			}
		}else{		
			if(judgement[1] == 0){
				judgement[0] = 1;
			}else if(judgement[2] == 0){
				judgement[0] = 2;
			}
		}
		return judgement;
	} 
	

		 

	//画面の状態
	public void set(){
		int values[] = judgement();
		int judgement = values[0];
		black = values[1];
		white = values[2];

	

		//黒の駒の数
		if(checking == 0){
			
			checking++;
		}else{
			
			c.remove(blackint);
			c.remove(whiteint);
			c.remove(thebackground);
			c.remove(theblacknumber);
			c.remove(thewhitenumber);
			
		}
		blackint = new JLabel(String.valueOf(black));
		whiteint = new JLabel(String.valueOf(white));
		if(myColor == 0){
			theblacknumber = new JLabel(blacknumber);
			c.add(theblacknumber);
			theblacknumber.setBounds(620,50,100,100);
			
			c.add(blackint);
			blackint.setBounds(540,50,100,100);
			blackint.setFont(new Font("Comic Sans MS",Font.BOLD,70));
			
			
			//白の駒の数
			thewhitenumber = new JLabel(whitenumber);
			c.add(thewhitenumber);
			thewhitenumber.setBounds(520,200,100,100);
			
			c.add(whiteint);
			whiteint.setBounds(650,200,100,100);
			whiteint.setFont(new Font("Comic Sans MS",Font.BOLD,70));
			
		}else if(myColor == 1){
			theblacknumber = new JLabel(blacknumber);
			c.add(theblacknumber);
			theblacknumber.setBounds(520,200,100,100);
			
			c.add(blackint);
			blackint.setBounds(650,200,100,100);
			blackint.setFont(new Font("Comic Sans MS",Font.BOLD,70));
			//白の駒の数
			thewhitenumber = new JLabel(whitenumber);
			c.add(thewhitenumber);
			thewhitenumber.setBounds(620,50,100,100);
			
			c.add(whiteint);
			whiteint.setBounds(540,50,100,100);
			whiteint.setFont(new Font("Comic Sans MS",Font.BOLD,70));

		}
		//バックグラウンド
		thebackground = new JLabel(background);
		c.add(thebackground);
		thebackground.setBounds(480,0,270,480);
		
		//引き分けの時
		if(judgement == 0){
		    c.removeAll();
			c.add(drawlabel);
			drawlabel.setBounds(0,0,767,645);
			end = 1;
			// System.out.println("引き分け");
			
		}else if(judgement == 1 || passcount2 == 2){
		    //白の勝ち
		// 	c.removeAll();
		// 	if(myColor == 0){
		// 		c.add(winlabel);
		// 		winlabel.setBounds(0,0,767,645);
		// 	}else{
		// 		c.add(loselabel);
		// 		loselabel.setBounds(0,0,767,645);
		// 	}
		end = 1;
		// timer.stop();

	

		}else if(judgement == 2 || passcount1 == 2){
			//黒の勝ち
		// 	c.removeAll()
		// 	if(myColor == 1){
			
		// 		c.add(winlabel);
		// 		winlabel.setBounds(0,0,767,645);
		// 	}else{
				
		// 		c.add(loselabel);
		// 		loselabel.setBounds(0,0,767,645);
  
		// 	}
		// timer.stop();
		end = 1;
			
		}
	}
	
	//ボタンをクリックしたときの処理
	public void mouseClicked(MouseEvent e) {
		JButton theButton = (JButton)e.getComponent();
		String theArrayIndex = theButton.getActionCommand();
		int XY = Integer.parseInt(theArrayIndex);
		
	
		if(start == 0){
			// スタートボタンが押されていない状態で、他のボタンが押されたときに表示
			JOptionPane.showMessageDialog(pop,"スタートボタンクリックをクリックしてください");
		}else{ 
			if(theArrayIndex.equals("72")){
				// パスを行う処理
				String msg = "pass"+" "+theArrayIndex+" "+myColor;
				out.println(msg);//送信データをバッファに書き出す
				out.flush();
			}else{
				if(myColor == myTurn){
					// 自分のターンだと駒を置く処理を実行
					
					Icon theIcon = theButton.getIcon();
					if(theIcon.equals(boardIcon)){
						// 駒がおけるときの反応
						
						Point theMLoc = e.getPoint();//発生元コンポーネントを基準とする相対座標
					
						// Point theBtnLocation = theButton.getLocation();//クリックしたボタンを座標を取得する
					
						String msg = "PLACE"+" "+theArrayIndex+" "+myColor;
						out.println(msg);//送信データをバッファに書き出す
						out.flush();
					}else{
						// 駒を置く場所に相手、もしくは自分の駒があった時の反応
						JOptionPane.showMessageDialog(pop,"おけません");
					}
				}else{		
					JOptionPane.showMessageDialog(pop,"あなたのターンではありません");

				}
				repaint();//画面のオブジェクトを描画し直す
			}
		}
	}

	//何回ひっくり返せるかの確認	
	public int filpButtons(int y,int x,int j,int i){
		int flipNum = 0;
		if(j==0&&i==0){
			
		}else{
			for(int dy=j, dx=i; ; dy+=j, dx+=i){
				//ボタンの位置情報を作る
				int msgy = y + dy;
				int msgx = x + dx;
				if(msgy < 0 || msgx < 0 || msgy > 7 || msgx > 7){
					// msgy,msgxがオセロの盤面からはみ出したときの処理
					flipNum = 0;
					break;
				}else{
					if(buttonArray[msgy][msgx].getIcon() == boardIcon){
						flipNum = 0;
						break;
					}else if(buttonArray[msgy][msgx].getIcon() == myIcon){
						break;
						
					}else if(buttonArray[msgy][msgx].getIcon() == yourIcon){
						flipNum++;
					}	
				}
			}
		}
		return flipNum;
	}

	// autoとautopass関数で自動パスを行う
	public int auto(int y,int x,int j,int i){
		int flip = 0;
		int flipend = 0;
		if(myColor == 1){
			submyIcon = blackIcon;
			subyourIcon = whiteIcon;
		}else if(myColor == 0){
			submyIcon = whiteIcon;
			subyourIcon = blackIcon;
		}
		if(j==0&&i==0){
			
		}else{
			if((buttonArray[x][y].getIcon() == boardIcon)){
				for(int dy=j, dx=i; ; dy+=j, dx+=i){
					flipend = 0;
					//ボタンの位置情報を作る
					int msgy = y + dy;
					int msgx = x + dx;
					flipend = 0;
					if(msgy < 0 || msgx < 0 || msgy > 7 || msgx > 7){
						break;
					}else{
						if(buttonArray[msgx][msgy].getIcon() == submyIcon){
							if(flip > 0){
								flipend = 1;
							}
							break;
						}else if(buttonArray[msgx][msgy].getIcon() == subyourIcon){
							flip += 1;
						}else{
							break;
						}
					}
				}	
			}	
		}
		return flipend;
	}
	public int autopass(){
		int check = 0;
		for(int x=0;x<8;x++){
			for(int y=0;y<8;y++){
				for(int i=-1;i<2;i++){
					for(int j=-1;j<2;j++){
						check = auto(y,x,j,i);
						if(check > 0){
							return 1;
						}else{
							continue;
						}
						
					}
				}
			}
		}
		return check;
	}

	//周りに相手の駒があるか確認
	public boolean judgeButton(int y, int x){
		boolean flag = false;
		for(int i=-1;i<=1;i++){
			for(int j=-1;j<=1;j++){
				if((x+j) < 0 || (y+i) < 0 || (x+j) > 7 || (y+i) > 7){
					continue;
				}else{
					if(buttonArray[y+i][x+j].getIcon() == yourIcon){
						flag = true;
					}else{
						continue;
					}
				}
			}
		}
		return flag;	
	}
	
	public void mouseEntered(MouseEvent e) {//マウスがオブジェクトに入ったときの処理
		// System.out.println("マウスが入った");
	}
	
	public void mouseExited(MouseEvent e) {//マウスがオブジェクトから出たときの処理
		// System.out.println("マウス脱出");
	}
	
	public void mousePressed(MouseEvent e) {//マウスでオブジェクトを押したときの処理（クリックとの違いに注意）
		// System.out.println("マウスを押した");
	}
	
	public void mouseReleased(MouseEvent e) {//マウスで押していたオブジェクトを離したときの処理
		// System.out.println("マウスを放した");
	}
	
	public void mouseDragged(MouseEvent e) {//マウスでオブジェクトとをドラッグしているときの処理
		// System.out.println("マウスをドラッグ");
		JButton theButton = (JButton)e.getComponent();//型が違うのでキャストする
		String theArrayIndex = theButton.getActionCommand();//ボタンの配列の番号を取り出す
        if(theArrayIndex.equals("0")){
			Point theMLoc = e.getPoint();//発生元コンポーネントを基準とする相対座標
			System.out.println(theMLoc);//デバッグ（確認用）に，取得したマウスの位置をコンソールに出力する
			Point theBtnLocation = theButton.getLocation();//クリックしたボタンを座標を取得する

			String msg = "MOVE"+" "+theArrayIndex+" "+theBtnLocation.x+" "+theBtnLocation.y;

			//サーバに情報を送る
			out.println(msg);//送信データをバッファに書き出す
			out.flush();//送信データをフラッシュ（ネットワーク上にはき出す）する

			repaint();//オブジェクトの再描画を行う

		}else{
			Point theMLoc = e.getPoint();//発生元コンポーネントを基準とする相対座標
			// System.out.println(theMLoc);//デバッグ（確認用）に，取得したマウスの位置をコンソールに出力する
			Point theBtnLocation = theButton.getLocation();//クリックしたボタンを座標を取得する
			theBtnLocation.x += theMLoc.x-15;//ボタンの真ん中当たりにマウスカーソルがくるように補正する
			theBtnLocation.y += theMLoc.y-15;//ボタンの真ん中当たりにマウスカーソルがくるように補正する
			theButton.setLocation(theBtnLocation);//マウスの位置にあわせてオブジェクトを移動する
	
			//送信情報を作成する（受信時には，この送った順番にデータを取り出す．スペースがデータの区切りとなる）
			String msg = "PLACE"+" "+theArrayIndex+" "+myColor;
			//サーバに情報を送る
			out.println(msg);//送信データをバッファに書き出す
			out.flush();//送信データをフラッシュ（ネットワーク上にはき出す）する
			repaint();//オブジェクトの再描画を行う
		}
	}

	public void mouseMoved(MouseEvent e) {//マウスがオブジェクト上で移動したときの処理
		// System.out.println("マウス移動");
		int theMLocX = e.getX();//マウスのx座標を得る
		int theMLocY = e.getY();//マウスのy座標を得る
		// System.out.println(theMLocX+","+theMLocY);//コンソールに出力する
	}
	public void actionPerformed(ActionEvent e) {
        String theCmd = e.getActionCommand();
        Object theObj = e.getSource();
        String theClass = theObj.getClass().getName();//クラス名を使って動きを変える
		//スタートを均一化
		if(theCmd.equals("judgement")){
			
			// System.out.println("judgement enter");
			if(myColor == myTurn){
				// System.out.println("success");
				ActionEvent ee = new ActionEvent(this,0,"ProgressBarStart");
				actionPerformed(ee);
			}else{
				String msg = "pass"+" "+1000;
				//サーバに情報を送る
				out.println(msg);//送信データをバッファに書き出す
				out.flush();//送信データをフラッシュ（ネットワーク上にはき出す）する
			}
			    String msg = "start"+" "+1;
				//サーバに情報を送る
				out.println(msg);//送信データをバッファに書き出す
				out.flush();//送信データをフラッシュ（ネットワーク上にはき出す）する
		}
		if(theCmd.equals("lose")){
			    String msg = "lose"+" "+myColor;
				//サーバに情報を送る
				out.println(msg);//送信データをバッファに書き出す
				out.flush();//送信データをフラッシュ（ネットワーク上にはき出す）する
		}
		if(theCmd.equals("ProgressBarStart")){
			if(timercheck == 0){
				// System.out.println("hello");
			}else{
				timer.stop();
			}
			timercheck = 1;
			theLabelA.setText("ProgressBarStart");
			timer = new Timer(myCount , this);
			timer.setActionCommand("timer");
			theProgressBar.setValue(myCount);
			theButtonStart.setEnabled(false);
			timer.start();
		}
		if(theCmd.equals("timer")){
			if(myColor == myTurn){
				theLabelA.setText("Timer");
				int count = theProgressBar.getValue();
				count = count - 1;
				myCount = count;
				theProgressBar.setValue(count);
				String text = String.format("%04d",theProgressBar.getValue());
				theLabelForProgressBar.setText(text);
				if(count<=0){
					timer.stop();
					String msg = "lose"+" "+myColor;
					out.println(msg);
					out.flush();
					end = 1;
					
				}	
			}
        }
    }
}

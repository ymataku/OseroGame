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
	private JButton buttonArray[][];//�{�^���p�̔z��
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
	PrintWriter out;//�o�͗p�̃��C�^�[

	public MyClient() {
		//���O�̓��̓_�C�A���O���J��
		String myName = JOptionPane.showInputDialog(null,"���O����͂��Ă�������","���O�̓���",JOptionPane.QUESTION_MESSAGE);
		String myIP = JOptionPane.showInputDialog(null,"IP�A�h���X����͂��Ă�������","IP�A�h���X�̓���",JOptionPane.QUESTION_MESSAGE);
	
		if(myName.equals("")){
			myName = "No name";//���O���Ȃ��Ƃ��́C"No name"�Ƃ���
		}
		
		//�E�B���h�E���쐬����
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//�E�B���h�E�����Ƃ��ɁC����������悤�ɐݒ肷��
		setTitle("MyClient");//�E�B���h�E�̃^�C�g����ݒ肷��
		setSize(767,645);//�E�B���h�E�̃T�C�Y��ݒ肷��
		c = getContentPane();//�t���[���̃y�C�����擾����

		//�A�C�R���̐ݒ�
		//�I�Z���̋�
		// System.out.println("white");
		whiteIcon = new ImageIcon("White5.png");
		blackIcon = new ImageIcon("Black5.png");
		boardIcon = new ImageIcon("nothing.png");
        //�p�X�{�^��
		passbtn = new ImageIcon("pass3.png");
		//�X�^�[�g�{�^��
		startbtn =  new ImageIcon("start3.png");
		//�T�C�h�w�i
		background = new ImageIcon("fram5.png");
		//�A�C�R���p�̍��̋�
		blacknumber = new ImageIcon("blackicon.png");
		//�A�C�R���p�̔��̋�
		whitenumber = new ImageIcon("whiteicon.png");
		losebtn = new ImageIcon("lose3.png");
        //�����̔w�i
		footer = new ImageIcon("testbackgroud2.png");
		//�������Ƃ��̉��o
		loseback = new ImageIcon("loseexample1.png");
		//���������̉��o
		winback = new ImageIcon("winexample1.png");
		
		//�����������Ƃ��̉��o
		drawback = new ImageIcon("draw.png");

		c.setLayout(null);//�������C�A�E�g�̐ݒ���s��Ȃ�
		//�{�^���̐���
		buttonArray = new JButton[8][8];//�{�^���̔z����T�쐬����[0]����[4]�܂Ŏg����
		passbutton = new JButton[1];
		
		for(int i=0;i<8;i++){
			for(int j=0;j<8;j++){
				if(i==4&&j==4){
					buttonArray[i][j] = new JButton(whiteIcon);
				}else if(i==4&&j==3){
				buttonArray[i][j] = new JButton(blackIcon);//�{�^���ɃA�C�R����ݒ肷��
				}else if(i==3&&j==4){
					buttonArray[i][j] = new JButton(blackIcon);
				}else if(i==3&&j==3){
					buttonArray[i][j] = new JButton(whiteIcon);
				}else {
					buttonArray[i][j] = new JButton(boardIcon);
				}
				
				c.add(buttonArray[i][j]);//�y�C���ɓ\��t����
			
				buttonArray[i][j].setBounds(j*60,60*i,60,60);//�{�^���̑傫���ƈʒu��ݒ肷��D(x���W�Cy���W,x�̕�,y�̕��j
				buttonArray[i][j].addMouseListener(this);//�{�^�����}�E�X�ł�������Ƃ��ɔ�������悤�ɂ���
				buttonArray[i][j].setActionCommand(Integer.toString(i*8+j));//�{�^���ɔz��̏���t������i�l�b�g���[�N����ăI�u�W�F�N�g�����ʂ��邽�߁j
        	    
			}
		}
		//�p�X�{�^���쐬
		passbutton[0] = new JButton(passbtn);
		c.add(passbutton[0]);
		passbutton[0].setBounds(480,480,135,124);
		passbutton[0].addMouseListener(this);
		passbutton[0].setActionCommand(Integer.toString(8*8+8));

		//���ʕ\�����x��
		theLabelA = new JLabel("���ʕ\�����x��");
        c.add(theLabelA);
        theLabelA.setBounds(320,350,120,25);
        theLabelA.addMouseListener(this);
		theProgressBar = new JProgressBar(1,1000);
        theProgressBar.setValue(1000);
		
  

		//�X�^�[�g�{�^���쐬
		theButtonStart = new JButton(startbtn);
        c.add(theButtonStart);
        theButtonStart.setBounds(615,480,135,124);
        theButtonStart.addActionListener(this);
		//�X�^�[�g�{�^������񂵂��@�\���Ȃ��悤�ɂ��邽�߂̃A�N�V�����ݒ�
        theButtonStart.setActionCommand("judgement");
		
		//�~�Q�{�^���쐬
		theButtonlose = new JButton(losebtn);
		c.add(theButtonlose);
        theButtonlose.setBounds(615,480,135,124);
        theButtonlose.addActionListener(this);
        theButtonlose.setActionCommand("lose");

		//��ʂ̉������̔w�i�쐬
		thefooter = new JLabel(footer);
		c.add(thefooter);
		thefooter.setBounds(0,480,480,124);

		//�v���O���X�o�[�̒l�擾and�\��
		String test = String.format("%04d",theProgressBar.getValue());
		theLabelForProgressBar = new JLabel(test);
        c.add(theLabelForProgressBar);
        theLabelForProgressBar.setBounds(500,200,700,400);
		theLabelForProgressBar.setFont(new Font("Comic Sans MS",Font.BOLD,95));

		//���������̕\���C���X�^���X��
		winlabel = new JLabel(winback);

		//�����Ď��̕\���C���X�^���X��
		loselabel = new JLabel(loseback);

		//���������̎��̕\���̃C���X�^���X��
		drawlabel = new JLabel(drawback);

		//�T�[�o�ɐڑ�����
		Socket socket = null;
		if(myIP.equals("")){
			try{
				socket = new Socket("localhost", 10000);
			} catch (UnknownHostException e) {
				System.err.println("�z�X�g�� IP �A�h���X������ł��܂���: " + e);
			} catch (IOException e) {
				System.err.println("�G���[���������܂���: " + e);
			}
		
		}
		MesgRecvThread mrt = new MesgRecvThread(socket, myName);//��M�p�̃X���b�h���쐬����
		mrt.start();//�X���b�h�𓮂����iRun�������j
	}
	//���b�Z�[�W��M�̂��߂̃X���b�h
	public class MesgRecvThread extends Thread {
		Socket socket;
		String myName;
		public MesgRecvThread(Socket s, String n){
			socket = s;
			myName = n;
		}
		//�ʐM�󋵂��Ď����C��M�f�[�^�ɂ���ē��삷��
		public void run() {
			try{
				InputStreamReader sisr = new InputStreamReader(socket.getInputStream());
				BufferedReader br = new BufferedReader(sisr);
				out = new PrintWriter(socket.getOutputStream(), true);
				out.println(myName);//�ڑ��̍ŏ��ɖ��O�𑗂�
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
				//�o�b�N�O���E���h�̕\�����쐬
				set();

				//�����̋�̐F���|�b�v�A�b�v�ŕ\��
				if(myColor == 1){
					JOptionPane.showMessageDialog(pop,"���Ȃ��͍��ł�");
				}else if(myColor == 0){
					JOptionPane.showMessageDialog(pop,"���Ȃ��͔��ł�");	
				}
				
				while(true) {
					if(end == 1){
						break;
					}
					int turnchange = 0;
					String inputLine = br.readLine();//�f�[�^����s�������ǂݍ���ł݂�
					if (inputLine != null) {//�ǂݍ��񂾂Ƃ��Ƀf�[�^���ǂݍ��܂ꂽ���ǂ������`�F�b�N����
						// System.out.println(inputLine);//�f�o�b�O�i����m�F�p�j�ɃR���\�[���ɏo�͂���
						String[] inputTokens = inputLine.split(" ");	//���̓f�[�^����͂��邽�߂ɁA�X�y�[�X�Ő؂蕪����
						String cmd = inputTokens[0];//�R�}���h�̎��o���D�P�ڂ̗v�f�����o��
						String theBName = inputTokens[1];//�{�^���̖��O�i�ԍ��j�̎擾	
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
							//���͂ɑ���̋���邩�̔���
							if(judgeButton(y,x)){
								int popupcount = 0;
								for(int i=-1;i<2;i++){
									for(int j=-1;j<2;j++){
										flip = filpButtons(y,x,i,j);
										//�Ђ�����Ԃ����������邩�̔���
										if(flip > 0){
											putcheck = 1;
											turnchange = 1;

											//�Ђ�����Ԃ���̐F���v���C���[���Ƃɕ�����B
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
								//�u�����ꏊ���Ђ�����Ԃ��Ȃ����肪�o���Ƃ��|�b�v�A�b�v�ŕ\��
								if(putcheck == 0){
									if(myColor == myTurn){
										JOptionPane.showMessageDialog(pop,"�����܂���");
										
									}
								}
							}else{
								//�����̃^�[���łȂ��Ƃ��ɉ����Ă��܂��Ƃ��̃|�b�v�A�b�v���m�点�B
								if(myTurn == myColor){
								 JOptionPane.showMessageDialog(pop,"�����܂���");
								}

							}
							//���u������A���葤�̃^�C�}�[�𔭓������Ă���^�[������シ��B
							if(turnchange == 1){
								// System.out.println("turnchange enter");
								myTurn = 1 - myTurn;
								ActionEvent ee = new ActionEvent(this,0,"ProgressBarStart");
								actionPerformed(ee);
							}
						//place���瑗���Ă����l�����ۂɂЂ�����Ԃ�����
						}else if(cmd.equals("FLIP")){
							int theColor = Integer.parseInt(inputTokens[2]);
							// System.out.println("color check"+x+" "+y+" "+myIcon);
							buttonArray[y][x].setIcon(myIcon);

						//�p�X���s���Ƃ��̖���
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
							//�^�[�������Ɠ����ɑ���̃^�C�}�[�𔭓�
							myTurn = 1 - myTurn;
							ActionEvent ee = new ActionEvent(this,0,"ProgressBarStart");
							actionPerformed(ee);

						//�X�^�[�g�{�^���������ꂽ�Ƃ��̖��߁B
						}else if(cmd.equals("start")){
							start =+theBnum;
							c.remove(theButtonStart);

					//�~�Q�{�^���������ꂽ�Ƃ��̖��߁B
					}else if(cmd.equals("lose")){
						//���������̕\��
						if(myColor == theBnum){
							c.removeAll();
							c.add(loselabel);
							loselabel.setBounds(0,0,767,645);
							timer.stop();
						}else{
							//���������̕\��
							c.removeAll();
							c.add(winlabel);
							winlabel.setBounds(0,0,767,645);
							timer.stop();
						}
					}else{
						break;
						}
					}	
					//�o�b�N�O���E���h�̍X�V
					set();	
					
					if(myTurn == myColor){
						if(autopass() == 1){
							System.out.println("no autopass"+autopass());
						}else if(autopass() == 0){
							int theArrayIndex = 10000;
							String msg = "pass"+" "+theArrayIndex+" "+myTurn;
							out.println(msg);//���M�f�[�^���o�b�t�@�ɏ����o��
							out.flush();
							
						}	
					}
					System.out.println("--------this is autopass judgement-------------------------");
					
					
				}
				socket.close();
			}catch (IOException e) {
				System.err.println("�G���[���������܂���: " + e);
			}
		}
	}
	public static void main(String[] args) {
		MyClient net = new MyClient();
		net.setVisible(true);
		
	}
	
	//���s���������̔��f������
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
	

		 

	//��ʂ̏��
	public void set(){
		int values[] = judgement();
		int judgement = values[0];
		black = values[1];
		white = values[2];

	

		//���̋�̐�
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
			
			
			//���̋�̐�
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
			//���̋�̐�
			thewhitenumber = new JLabel(whitenumber);
			c.add(thewhitenumber);
			thewhitenumber.setBounds(620,50,100,100);
			
			c.add(whiteint);
			whiteint.setBounds(540,50,100,100);
			whiteint.setFont(new Font("Comic Sans MS",Font.BOLD,70));

		}
		//�o�b�N�O���E���h
		thebackground = new JLabel(background);
		c.add(thebackground);
		thebackground.setBounds(480,0,270,480);
		
		//���������̎�
		if(judgement == 0){
		    c.removeAll();
			c.add(drawlabel);
			drawlabel.setBounds(0,0,767,645);
			end = 1;
			// System.out.println("��������");
			
		}else if(judgement == 1 || passcount2 == 2){
		    //���̏���
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
			//���̏���
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
	
	//�{�^�����N���b�N�����Ƃ��̏���
	public void mouseClicked(MouseEvent e) {
		JButton theButton = (JButton)e.getComponent();
		String theArrayIndex = theButton.getActionCommand();
		int XY = Integer.parseInt(theArrayIndex);
		
	
		if(start == 0){
			// �X�^�[�g�{�^����������Ă��Ȃ���ԂŁA���̃{�^���������ꂽ�Ƃ��ɕ\��
			JOptionPane.showMessageDialog(pop,"�X�^�[�g�{�^���N���b�N���N���b�N���Ă�������");
		}else{ 
			if(theArrayIndex.equals("72")){
				// �p�X���s������
				String msg = "pass"+" "+theArrayIndex+" "+myColor;
				out.println(msg);//���M�f�[�^���o�b�t�@�ɏ����o��
				out.flush();
			}else{
				if(myColor == myTurn){
					// �����̃^�[�����Ƌ��u�����������s
					
					Icon theIcon = theButton.getIcon();
					if(theIcon.equals(boardIcon)){
						// �������Ƃ��̔���
						
						Point theMLoc = e.getPoint();//�������R���|�[�l���g����Ƃ��鑊�΍��W
					
						// Point theBtnLocation = theButton.getLocation();//�N���b�N�����{�^�������W���擾����
					
						String msg = "PLACE"+" "+theArrayIndex+" "+myColor;
						out.println(msg);//���M�f�[�^���o�b�t�@�ɏ����o��
						out.flush();
					}else{
						// ���u���ꏊ�ɑ���A�������͎����̋���������̔���
						JOptionPane.showMessageDialog(pop,"�����܂���");
					}
				}else{		
					JOptionPane.showMessageDialog(pop,"���Ȃ��̃^�[���ł͂���܂���");

				}
				repaint();//��ʂ̃I�u�W�F�N�g��`�悵����
			}
		}
	}

	//����Ђ�����Ԃ��邩�̊m�F	
	public int filpButtons(int y,int x,int j,int i){
		int flipNum = 0;
		if(j==0&&i==0){
			
		}else{
			for(int dy=j, dx=i; ; dy+=j, dx+=i){
				//�{�^���̈ʒu�������
				int msgy = y + dy;
				int msgx = x + dx;
				if(msgy < 0 || msgx < 0 || msgy > 7 || msgx > 7){
					// msgy,msgx���I�Z���̔Ֆʂ���͂ݏo�����Ƃ��̏���
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

	// auto��autopass�֐��Ŏ����p�X���s��
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
					//�{�^���̈ʒu�������
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

	//����ɑ���̋���邩�m�F
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
	
	public void mouseEntered(MouseEvent e) {//�}�E�X���I�u�W�F�N�g�ɓ������Ƃ��̏���
		// System.out.println("�}�E�X��������");
	}
	
	public void mouseExited(MouseEvent e) {//�}�E�X���I�u�W�F�N�g����o���Ƃ��̏���
		// System.out.println("�}�E�X�E�o");
	}
	
	public void mousePressed(MouseEvent e) {//�}�E�X�ŃI�u�W�F�N�g���������Ƃ��̏����i�N���b�N�Ƃ̈Ⴂ�ɒ��Ӂj
		// System.out.println("�}�E�X��������");
	}
	
	public void mouseReleased(MouseEvent e) {//�}�E�X�ŉ����Ă����I�u�W�F�N�g�𗣂����Ƃ��̏���
		// System.out.println("�}�E�X�������");
	}
	
	public void mouseDragged(MouseEvent e) {//�}�E�X�ŃI�u�W�F�N�g�Ƃ��h���b�O���Ă���Ƃ��̏���
		// System.out.println("�}�E�X���h���b�O");
		JButton theButton = (JButton)e.getComponent();//�^���Ⴄ�̂ŃL���X�g����
		String theArrayIndex = theButton.getActionCommand();//�{�^���̔z��̔ԍ������o��
        if(theArrayIndex.equals("0")){
			Point theMLoc = e.getPoint();//�������R���|�[�l���g����Ƃ��鑊�΍��W
			System.out.println(theMLoc);//�f�o�b�O�i�m�F�p�j�ɁC�擾�����}�E�X�̈ʒu���R���\�[���ɏo�͂���
			Point theBtnLocation = theButton.getLocation();//�N���b�N�����{�^�������W���擾����

			String msg = "MOVE"+" "+theArrayIndex+" "+theBtnLocation.x+" "+theBtnLocation.y;

			//�T�[�o�ɏ��𑗂�
			out.println(msg);//���M�f�[�^���o�b�t�@�ɏ����o��
			out.flush();//���M�f�[�^���t���b�V���i�l�b�g���[�N��ɂ͂��o���j����

			repaint();//�I�u�W�F�N�g�̍ĕ`����s��

		}else{
			Point theMLoc = e.getPoint();//�������R���|�[�l���g����Ƃ��鑊�΍��W
			// System.out.println(theMLoc);//�f�o�b�O�i�m�F�p�j�ɁC�擾�����}�E�X�̈ʒu���R���\�[���ɏo�͂���
			Point theBtnLocation = theButton.getLocation();//�N���b�N�����{�^�������W���擾����
			theBtnLocation.x += theMLoc.x-15;//�{�^���̐^�񒆓�����Ƀ}�E�X�J�[�\��������悤�ɕ␳����
			theBtnLocation.y += theMLoc.y-15;//�{�^���̐^�񒆓�����Ƀ}�E�X�J�[�\��������悤�ɕ␳����
			theButton.setLocation(theBtnLocation);//�}�E�X�̈ʒu�ɂ��킹�ăI�u�W�F�N�g���ړ�����
	
			//���M�����쐬����i��M���ɂ́C���̑��������ԂɃf�[�^�����o���D�X�y�[�X���f�[�^�̋�؂�ƂȂ�j
			String msg = "PLACE"+" "+theArrayIndex+" "+myColor;
			//�T�[�o�ɏ��𑗂�
			out.println(msg);//���M�f�[�^���o�b�t�@�ɏ����o��
			out.flush();//���M�f�[�^���t���b�V���i�l�b�g���[�N��ɂ͂��o���j����
			repaint();//�I�u�W�F�N�g�̍ĕ`����s��
		}
	}

	public void mouseMoved(MouseEvent e) {//�}�E�X���I�u�W�F�N�g��ňړ������Ƃ��̏���
		// System.out.println("�}�E�X�ړ�");
		int theMLocX = e.getX();//�}�E�X��x���W�𓾂�
		int theMLocY = e.getY();//�}�E�X��y���W�𓾂�
		// System.out.println(theMLocX+","+theMLocY);//�R���\�[���ɏo�͂���
	}
	public void actionPerformed(ActionEvent e) {
        String theCmd = e.getActionCommand();
        Object theObj = e.getSource();
        String theClass = theObj.getClass().getName();//�N���X�����g���ē�����ς���
		//�X�^�[�g���ψꉻ
		if(theCmd.equals("judgement")){
			
			// System.out.println("judgement enter");
			if(myColor == myTurn){
				// System.out.println("success");
				ActionEvent ee = new ActionEvent(this,0,"ProgressBarStart");
				actionPerformed(ee);
			}else{
				String msg = "pass"+" "+1000;
				//�T�[�o�ɏ��𑗂�
				out.println(msg);//���M�f�[�^���o�b�t�@�ɏ����o��
				out.flush();//���M�f�[�^���t���b�V���i�l�b�g���[�N��ɂ͂��o���j����
			}
			    String msg = "start"+" "+1;
				//�T�[�o�ɏ��𑗂�
				out.println(msg);//���M�f�[�^���o�b�t�@�ɏ����o��
				out.flush();//���M�f�[�^���t���b�V���i�l�b�g���[�N��ɂ͂��o���j����
		}
		if(theCmd.equals("lose")){
			    String msg = "lose"+" "+myColor;
				//�T�[�o�ɏ��𑗂�
				out.println(msg);//���M�f�[�^���o�b�t�@�ɏ����o��
				out.flush();//���M�f�[�^���t���b�V���i�l�b�g���[�N��ɂ͂��o���j����
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

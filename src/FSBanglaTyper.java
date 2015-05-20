import javax.microedition.lcdui.*;
import javax.microedition.midlet.MIDlet;
import javax.microedition.lcdui.game.*;

//TECHNOLOGY : ARMED CHARACTER
//START
class arm{
    arm next = null;
    short a;
    arm(int x1, int y1, int x2, int y2){
	a = 0;
	x1 &= 0x000F;
	x2 &= 0x000F;
	y1 &= 0x000F;
	y2 &= 0x000F;
	
	x1 <<= 12;
	y1 <<= 8;
	x2 <<= 4;
	a = (short)(x1 + y1 + x2 + y2);
    }
}
class BLetter{
    arm root = null, last = null; 
    int width = 0;
    public void add(int x1, int x2, int y1, int y2){
	
	if(root == null){
	    last = new arm(x1,y1,x2,y2);
	    root = last;
	}else{
	    last.next = new arm(x1,y1,x2,y2);
	    last = last.next;
	}
	
	if(width < x1)
	    width = x1;
	if(width < x2)
	    width = x2;
    }
    public void draw(Graphics g, int x, int y){
	arm t = root;
	while(t != null){
	    g.drawLine(x + (int)((t.a >> 12) & 0x000F),y + (int)((t.a >> 8) & 0x000F), x + (int)((t.a >> 4) & 0x000F), y + (int)(t.a & 0x000F));
	    t = t.next;
	}
    }
}

class BAlphabet{
    public static BLetter 
	    ka,
	    kha,
	    ga,
	    gha,
	    uo,
	    cha,
	    ca,
	    ja,
	    jha,
	    ea,
	    ta,
	    tha,
	    da,
	    dha,
	    m_na;
    
    public BAlphabet(){
	ka = new BLetter();
	ka.add(0,6, 1,1);
	ka.add(0,4, 5,2);
	ka.add(0,4, 5,8);
	ka.add(4,4, 1,8);
	ka.add(4,6, 2,4);
    }
}
//TECHNOLOGY : ARMED CHARACTER
//END



//TECHNOLOGY : SEGMENTIZED CHARACTER
//START

class SBAlphabet{
    static int 
	    ka	= 0xF801,
	    oa  = 0xC7C1,
	    e	= 0xB204,
	    ee	= 0xA23C,
	    uo,
	    cha,
	    ca,
	    ja,
	    jha,
	    ea,
	    ta,
	    tha,
	    da,
	    dha,
	    m_na;  
}
class Writer{
    private boolean[] line;
    int x = 30, y = 35, n = 2;
    /** Creates new form viewer */
    public Writer () {
	line = new boolean[16];
    }
    public void dl (int x1, int y1, int x2, int y2, Graphics g){
	
	g.drawLine (x1*n + x , y1*n + y , x2*n + x , y2*n + y );
    }
    public void draw(Graphics g, int segment){
	int i, j;
	for(i=0x8000, j = 0; i > 0; i >>= 1, j++){
	    if((segment & i) != 0)
		line[j] = true;
	    else
		line[j] = false;
	}
	if(line[0]){		//Matra
	    dl (1, 2, 5, 2, g);
	}
	if(line[1]){		//backbone
	    dl (5, 3, 5, 7, g);
	    //dl (4, 3, 4, 7, g);
	}
	if(line[2]){		//ka up slope
	    dl (5, 2, 1, 5, g);
	}
	if(line[3]){		//ka down slopw
	    dl (1, 5, 5, 7, g);
	}
	if(line[4]){		//ka right slope
	    dl (5, 2, 7, 5, g);
	}
	if(line[5]){		//right line
	    dl (6, 2, 6, 7, g);
	    //dl (7, 2, 7, 7, g);
	}
	if(line[6]){		//mid half line
	    dl (2, 2, 2, 3, g);
	    //dl (3, 2, 3, 3, g);
	}
	if(line[7]){		//left line
	    dl (1, 3, 1, 7, g);
	    //dl (0, 3, 0, 7, g);
	}
	if(line[8]){		//down line
	    dl (1, 7, 5, 7, g);
	}
	if(line[9]){		//right down slope
	    dl (5, 6, 6, 6, g);
	}
	if(line[10]){		//
	    dl (1, 5, 6, 3, g);
	}
	if(line[11]){
	    dl (6, 3, 4, 5, g);
	}
	if(line[12]){
	    dl (4, 5, 5, 7, g);
	}
	if(line[13]){
	    dl (5, 2, 5, 1, g);
	    dl (5, 1, 1, 1, g);
	}
	if(line[14]){
	    dl (2, 3, 5, 3, g);
	}
	if(line[15]){
	    dl (5, 2, 5, 3, g);
	    //dl (4, 2, 4, 3, g);
	}
    }
}



class canvas extends Canvas implements Runnable{
    byte x[][][] =  {{{0,0,0,0,0,0,0,0},
		     {0,0,0,0,0,0,0,0},
		     {1,1,1,1,1,1,1,1},
		     {0,0,0,0,1,1,0,0},
		     {0,1,1,1,0,1,1,1},
		     {1,0,0,0,0,1,0,1},
		     {0,1,1,1,0,1,0,0},
		     {0,0,0,0,1,1,0,0}},
		    {{0,0,0,0,0,0,0,0},
		     {0,0,0,0,0,0,0,0},
		     {1,0,1,0,0,1,1,1},
		     {1,1,1,0,0,1,0,0},
		     {0,0,1,0,0,1,0,0},
		     {1,1,0,0,0,1,0,0},
		     {0,1,1,0,0,1,0,0},
		     {0,0,0,1,1,1,0,0}}};
	Thread t;
	boolean fl = false, ss = true;
	canvas()
	{
		//super(false);
		//fl = true;
		//t = new Thread(this);
		//t.start();
	}
	public void paint(Graphics g){
		//g.drawString("Hello",50, 50,20);
//*
		int i,j,k;
		for(k=0;k<2;k++){
		    for(i=0; i<8; i++){
			for(j=0;j<8;j++){
			    if(x[k][j][i] == 1)
				g.drawLine (20+i+(k*9),50+j,20+i+(k*9),50+j);
			}
		    }
		}
		for(k=0;k<1;k++){
		    for(i=0; i<8; i++){
			for(j=0;j<8;j++){
			    if(x[k][j][i] == 1){
				g.drawLine (40+i*2+(k*9),50+j*2,40+i*2+(k*18)+1,50+j*2);
				g.drawLine (40+i*2+(k*9),50+j*2+1,40+i*2+(k*18)+1,50+j*2+1);
			    }
			}
		    }
		}
		
		new BAlphabet().ka.draw(g, 50, 65);
		Writer w = new Writer();
		w.y = 15;
		w.n = 1;
		w.draw(g, SBAlphabet.ka);
		w.x += 16;
		w.draw(g, SBAlphabet.oa);
		w.x += 16;
		w.draw(g, SBAlphabet.e);
		w.x += 16;
		w.draw(g, SBAlphabet.ee);
		w.x += 16;
		w.n = 2;
		w.y = 25;
		w.x = 35;
		w.draw(g, SBAlphabet.ka);
		w.x += 16;
		w.draw(g, SBAlphabet.oa);
		w.x += 16;
		w.draw(g, SBAlphabet.e);
		w.x += 16;
		w.draw(g, SBAlphabet.ee);
		w.x += 16;
//*/
		/*
		g.setColor(0xFFFFFF);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(0x0000FF);
		g.drawLine(100,100,100,200);
		if(ss)
			g.setColor(0xFFFFFF);
		else
			g.setColor(0x0000FF);
		g.drawLine(10,10,10,20);
		 **/
		
	}
	public void run()
	{
		try{
		
			while(fl)
			{
			
				ss = !ss;
			
				Thread.sleep(200);
				repaint();
			
			}
		}catch(Exception e){}
	}
	public void keyRepeated(int key){
		keyPressed(key);
	}
	public void keyPressed(int key){
		System.out.println(key);
	}
	public void stop()
	{
		fl = false;	
	}

}
public class FSBanglaTyper extends MIDlet implements CommandListener
{
	Display d;
	canvas f ;
	Command NEXT_CMD = new Command("Next", Command.SCREEN, 2);
	Command PREV_CMD = new Command("Prev", Command.SCREEN, 3);
	Command EXIT_CMD = new Command("Exit", Command.EXIT, 1);

	public void startApp()
	{
		
		d = Display.getDisplay(this);
		f = new canvas();
			
		f.addCommand(EXIT_CMD);
		f.addCommand(NEXT_CMD);
		f.addCommand(PREV_CMD);
		f.setCommandListener(this);
		
		d.setCurrent(f);
	}

	public void pauseApp()
	{
	
		
	}
	
	public void destroyApp(boolean unconditional)
	{
		
	}
	
	public void commandAction(Command c,Displayable d)
	{
	    if (c.getCommandType() == Command.EXIT)
			notifyDestroyed();
	}
}
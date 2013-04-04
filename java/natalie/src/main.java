import java.applet.*;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public class main extends Applet implements Runnable{

	private static final long serialVersionUID = 1L;
	private Image dbImage;		//Para o double buffering
	private Graphics dbg;		//Para o double buffering	
	
	private Jogador nave;
	private Tiro [] tiros;
	private Tiro [] tirosInimigo;
	private Inimigo [] inimigos;
	private Pontos pontos;

	private int tiroVelocidade = -4;
	private int naveVelocidadeEsq = -4;
	private int naveVelocidadeDir = 4;
	private int inimigoVelocidade = 1;

	private boolean fimCenario = false;
	private boolean acertou = false;
	private boolean naveMoveEsq;
	private boolean naveMoveDir;

	private Timer timer;
	
	public void init(){
		nave = new Jogador(320, 460);
		tiros = new Tiro[25];
		tirosInimigo = new Tiro[10];
		inimigos = new Inimigo [48];
		pontos = new Pontos();
		timer = new Timer();
		
		iniciarInimigos(inimigos);
		
		//tamanho da janela
		setSize(640,480);
		
		timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
            	int x = 0 + (int)(Math.random() * ((47 - 0) + 1));
    			if(inimigos[x] != null && ( x > 39 || (x < 39 && inimigos[x+8] == null) )){
    				for(int i=0; i<tirosInimigo.length; i++){
    					if(tirosInimigo[i] == null){
    						tirosInimigo[i] = inimigos[x].criarTiro();
    						break;
    					}
    				}			
    			}
            }
        }, 1000, 250);
		
	}

	public void start (){
		Thread th = new Thread (this);
		th.start ();
	}

	public void stop(){

	}

	public void destroy(){

	}

	public void run (){
		Thread.currentThread().setPriority(Thread.MIN_PRIORITY);

		while (true){

			for(int i=0; i<tiros.length; i++){
				if(tiros[i] != null){
					tiros[i].moveTiro(tiroVelocidade);
					if(tiros[i].getYPos() < 0)	{
						tiros[i] = null;
					}
				}
			}
			
			for(int i=0; i<tirosInimigo.length; i++){
				if(tirosInimigo[i] != null){
					tirosInimigo[i].moveTiro(-tiroVelocidade);
					if(tirosInimigo[i].getYPos() > 480)	{
						tirosInimigo[i] = null;
					}
				}
			}

			for(int k=0; k<inimigos.length; k++){
				if(inimigos[k] != null){
					inimigos[k].moveX(inimigoVelocidade);
					if(inimigos[k].getXPos() <= 640 && inimigos[k].getXPos() >= 625 || inimigos[k].getXPos() <= 15 && inimigos[k].getXPos() >=0) fimCenario = true;
				}
			}			
	
			for(int i=0; i<inimigos.length; i++){
				if(inimigos[i] != null){				
					for(int k=0; k<tiros.length; k++){				
						if(tiros[k] != null){						
							if(inimigos[i].getYPos() - 10 <= tiros[k].getYPos() && inimigos[i].getYPos() + 10 >= tiros[k].getYPos() 
								&& inimigos[i].getXPos() - 15 <= tiros[k].getXPos() && inimigos[i].getXPos() + 15 >= tiros[k].getXPos()){
								tiros[k] = null;
								acertou = true;
								pontos.maisPontos(10);
								break;
							}
						}
					}
					if(acertou){
						acertou = false;
						inimigos[i] = null;
					}
				}
			}
		
			if(fimCenario){ 
				fimCenario = false;
				inimigoVelocidade = -inimigoVelocidade;
				
				for(int k=0; k<inimigos.length; k++){
					if(inimigos[k] != null){
						inimigos[k].moveY(2);
					}					
				}
			}	

			if(naveMoveEsq){
				nave.moveX(naveVelocidadeEsq);
			}
			else if(naveMoveDir){
				nave.moveX(naveVelocidadeDir);
			}
			
			repaint();

			try{
				Thread.sleep(10);
			}
			catch (InterruptedException ex){
			}

			Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
		}
	}

	public boolean keyDown(Event e, int key){

		if(key == Event.LEFT){
			naveMoveEsq = true;
		}

		else if(key == Event.RIGHT){
			naveMoveDir = true;
		}

		else if(key == 32){
			for(int i=0; i<tiros.length; i++){
				if(tiros[i] == null){
					tiros[i] = nave.criarTiro();
					break;
				}
			}
		}
		return true;
	}

	public boolean keyUp(Event e, int key)	{
		if(key == Event.LEFT){
			naveMoveEsq = false;
		}
		else if(key == Event.RIGHT){
			naveMoveDir = false;
		}

		return true;
	}

	public void update (Graphics g){
	//Inicializa o double buffering
	if (dbImage == null)	{
		dbImage = createImage (this.getSize().width, this.getSize().height);
		dbg = dbImage.getGraphics ();
	}

	//Limpa a tela, background
	dbg.setColor (getBackground ());
	dbg.fillRect (0, 0, this.getSize().width, this.getSize().height);

	// Desenha elementos no background
	dbg.setColor (getForeground());
	paint (dbg);
	
	// Desenha imagem na tela
	g.drawImage (dbImage, 0, 0, this);
	}

	public void paint (Graphics g){
		setBackground (Color.black);
		int i =0;
		
		nave.desenhaJogador(g);
		
		
		for(i=0; i<tiros.length; i++){
			if(tiros[i] != null)	{
				tiros[i].desenhaTiro(g);
			}
		}
		
		for(i=0; i<tirosInimigo.length; i++){
			if(tirosInimigo[i] != null){
				tirosInimigo[i].desenhaTiro(g);				
			}
		}
		
		for(i=0; i<inimigos.length; i++){
			if(inimigos[i] != null)	{			
				inimigos[i].desenhaInimigo(g);
			}
		}
		
		pontos.desenhaPontos(g);
				
	}
	
	public void iniciarInimigos(Inimigo inimigo[]){
		int posX = 250;
		int posY = 50;
		int numInimigo = 0;
		
		for(int linha=0; linha<6; linha++){
			for(int coluna=0; coluna < 8; coluna++){
				inimigos[numInimigo++] = new Inimigo(posX, posY);
				posX = posX + 50;
			}
			posY = posY + 40;
			posX = 250;
		}
	}
	
}

import java.awt.*;

public class Inimigo{
	
	private int x_pos;
	private int y_pos;

	public Inimigo(int x, int y){
		x_pos = x;
		y_pos = y;	
	}
	
	public int getYPos(){
		return y_pos;
	}
	
	public int getXPos(){
		return x_pos;
	}	
	
	public void moveX(int velocidade){
		x_pos = x_pos + velocidade;
	}

	public void moveY(int velocidade){
		y_pos = y_pos + velocidade;
	}
	
	public Tiro criarTiro()	{
		Tiro tiro = new Tiro(x_pos, y_pos);
		return tiro;
	}
	
	public void desenhaInimigo(Graphics g){
		int xPoints[] = {x_pos-20, x_pos-15, x_pos+15, x_pos+20};
		int yPoints[] = {y_pos-10, y_pos+10, y_pos+10, y_pos-10};
		
		g.setColor(Color.red);
		g.fillPolygon(xPoints, yPoints, 4);
		g.setColor(Color.black);
		g.drawPolygon(xPoints, yPoints, 4);
				
	}
}

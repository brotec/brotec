import java.awt.Graphics;
import java.awt.Color;

public class Tiro{
	private int x_pos;
	private int y_pos;

	public Tiro(int x, int y){
		x_pos = x;
		y_pos = y;
	}

	public int getYPos(){
		return y_pos;
	}
	
	public int getXPos(){
		return x_pos;
	}

	public void moveTiro(int velocidade){
		y_pos = y_pos + velocidade;
	}

	public void desenhaTiro(Graphics g){
		int xPoints[] = {x_pos-1, x_pos-1, x_pos+1, x_pos+1};
		int yPoints[] = {y_pos-6, y_pos+6, y_pos+6, y_pos-6};
		
		g.setColor(Color.yellow);
		g.fillPolygon(xPoints, yPoints, 4);
		g.setColor(Color.black);
     	g.drawPolygon(xPoints, yPoints, 4);   			
	}
}

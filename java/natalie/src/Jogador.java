import java.awt.*;

public class Jogador{

	private int x_pos;
	private int y_pos;

	public Jogador(int x, int y){
		x_pos = x;
		y_pos = y;
	}

	public void moveX(int velocidade){
		x_pos = x_pos + velocidade;
		
		if(x_pos  < 640 && x_pos > 620){
			x_pos = 619;
		}
		if(x_pos > 0 && x_pos < 20){
			x_pos = 19;
		}
	}

	public Tiro criarTiro()	{
		Tiro tiro = new Tiro(x_pos, y_pos);
		return tiro;
	}

	public void desenhaJogador(Graphics g){
		int xPoints[] = {x_pos+15, x_pos, x_pos-15, x_pos-7, x_pos, x_pos+7};
		int yPoints[] = {y_pos+15, y_pos-15, y_pos+15, y_pos+7, y_pos+10, y_pos+7};
		
		g.setColor(Color.blue);
		g.drawPolygon(xPoints, yPoints, 6);
	}
}

import java.awt.*;

public class Pontos{
	private int pontos;

	public Pontos(){
		pontos = 0;
	}

	public void maisPontos(int valor){
		pontos = pontos + valor;
	}

	public void desenhaPontos(Graphics g){
		Font font = new Font("Arial",Font.BOLD,25);
		g.setFont(font);		

		g.setColor(Color.WHITE);

		g.drawString("Pontos: "+ pontos, 15, 30);	
	}
}



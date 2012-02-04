package game;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.Serializable;

import javax.swing.ImageIcon;

/**
 *	Classe que implementa uma barra de energia
 *
 *  @author  Paolo Victor, Helder Fernando, �lvaro Magnum, Fabr�cio Gutemberg,
 *           Dalton C�zane.
 *  @version 1.0
 */
public class BarraDeEnergia implements Serializable, Desenhavel
{	
	/**
	 * A energia atual da barra.
	 */
	private int energiaAtual;
	
	/**
	 * A energia m�xima da barra.
	 */
	private int energiaMaxima;
	
	/**
	 * A posi��o X da barra na tela.
	 */
	private int x;
	
	/**
	 * A posi��o Y da barra na tela.
	 */
	private int y;
	
	/**
	 * Imagem com a face da barra.
	 */
	private ImageIcon faceDaBarra;
	
	/**
	 * O nome que aparece na barra de energia.
	 */
	private String nome;
	
	/**
	 * A fonte da barra de energia.
	 */
	private Font fonte;
	
	/**
	 *  M�todo construtor da classe
	 *  @param nomeDaImagem Nome da imagem que aparece na face da barra 
	 *  @param nome O nome que aparece na barra.
	 *  @param energiaInicial A energia da barra
	 *  @param posicaoX A posi��o X da barra
	 *  @param posicaoY A posi��o Y da barra
	 */
	public BarraDeEnergia(String nomeDaImagem, String nome,
	                      int energiaInicial, int posicaoX, int posicaoY)
	{
		faceDaBarra        = new ImageIcon(nomeDaImagem);		
		fonte              = new Font("Arial", Font.BOLD, 16);
		this.energiaAtual  = energiaInicial;
		this.energiaMaxima = energiaInicial;
		this.nome          = nome;
		this.x             = posicaoX;
		this.y             = posicaoY;
	}
	
	/** M�todo que retorna a energia atual da barra
	 *  @return A energia atual
	 */
	public int getEnergiaAtual()
	{
		return(energiaAtual);
	}

	/** M�todo que retorna a energia m�xima da barra
	 *  @return A energia m�xima
	 */
	public int getEnergiaMaxima()
	{
		return(energiaMaxima);
	}
		
	/** Remove energia da barra de energia
	 *  @param quantidade A quantidade de energia removida
	 */
	public void removerEnergia(int quantidade)
	{
		energiaAtual -= quantidade;
		
		if(energiaAtual < 0)
		{
			energiaAtual = 0;
		}
	}
	
	/** Adiciona energia � barra de energia
	 *  @param quantidade A quantidade de energia que ser� adicionada
	 */
	public void adicionarEnergia(int quantidade)
	{
		energiaAtual += quantidade;
		
		if(energiaAtual > energiaMaxima)
		{
			energiaAtual = energiaMaxima;
		}
	}
	
	/** M�todo que desenha a barra de energia.
	 * @param c O componente no qual a barra ser� mostrada.
	 * @param g O Graphics usado para o desenho.
	 */
	public void paint(Graphics g)
	{
		int tamanhoDaBarra;
		int tamanhoDaEnergia;
		
		// Ajustando o tamanho das energias
		if(energiaMaxima > 100)
		{
			tamanhoDaBarra = 100;
		}
		else
		{
			tamanhoDaBarra = energiaMaxima;
		}
		
		if(energiaAtual > 100)
		{
			tamanhoDaEnergia = energiaAtual % 100;
		}
		else
		{
			tamanhoDaEnergia = energiaAtual;
		}
		
		// mudando a fonte
		g.setFont(fonte);
		
		// Mudando a cor e desenhando a borda negra
		g.setColor(Color.black);
		g.fillRect(x + 34, y, tamanhoDaBarra + 5, 16);
		
		// Desenhando a borda do nome
		g.drawString(nome, x + 41, y + 30);
		g.drawString(nome, x + 39, y + 30);
		g.drawString(nome, x + 40, y + 31);
		g.drawString(nome, x + 40, y + 29);
		
		// Mudando a cor e desenhando as borda brancas
		g.setColor(Color.white);
		g.drawRect(x + 34, y + 1, tamanhoDaBarra + 3, 13);
		
		// Desenhando o nome
		g.drawString(nome, x + 40, y + 30);
		
		// Mudando as cores e desenhando as barras
		if(energiaAtual > 300)
		{
			g.setColor(Color.orange);
			g.fillRect(x + 36, y + 3, tamanhoDaBarra, 10);
		
			g.setColor(Color.green);
			g.fillRect(x + 36, y + 3, tamanhoDaEnergia, 10);			
		}
		else if(energiaAtual > 200)
		{
			g.setColor(Color.blue);
			g.fillRect(x + 36, y + 3, tamanhoDaBarra, 10);
		
			g.setColor(Color.orange);
			g.fillRect(x + 36, y + 3, tamanhoDaEnergia, 10);			
		}
		else if(energiaAtual > 100)
		{
			g.setColor(Color.yellow);
			g.fillRect(x + 36, y + 3, tamanhoDaBarra, 10);
		
			g.setColor(Color.blue);
			g.fillRect(x + 36, y + 3, tamanhoDaEnergia, 10);			
		}
		else
		{
			g.setColor(Color.red);
			g.fillRect(x + 36, y + 3, tamanhoDaBarra, 10);
		
			g.setColor(Color.yellow);
			g.fillRect(x + 36, y + 3, tamanhoDaEnergia, 10);
		}
		
		// Desenhando o rosto
		if(faceDaBarra != null)
		{
			g.setColor(Color.black);
			g.fillRect(x, y, 36, 36);
		
			g.setColor(Color.white);
			g.drawRect(x + 1, y + 1, 33, 33);
		
			faceDaBarra.paintIcon(null, g, x + 2, y + 2);		
		}
	}
}
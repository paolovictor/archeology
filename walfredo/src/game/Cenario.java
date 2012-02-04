package game;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 *	Classe que implementa um cenario de jogo
 *
 *  @author  Paolo Victor, Helder Fernando, �lvaro Magnum, Fabr�cio Gutemberg,
 *           Dalton C�zane.
 *  @version 1.0
 */
public class Cenario implements Desenhavel
{	
	/**
	 * A posi��o do cen�rio na tela.
	 */ 
	private int posicaoNaTela;
	
	/**
	 * A imagem de fundo do cen�rio.
	 */	
	private BufferedImage imageData;
	
	/**
	 *  M�todo construtor da classe
	 *  @param nomeDaImagem Nome da imagem de fundo 
	 */
	public Cenario(String nomeDaImagem)
	{
		File imagem = new File( nomeDaImagem );
		try {
			imageData = ImageIO.read( imagem );
		} catch (IOException e) {
		}
		
		posicaoNaTela = 0;
	}

	/**
	 *  M�todo que move o cen�rio.
	 *  @return A quantidade de movimento.
	 */
	public void moverCenario(int quantidade)
	{
		posicaoNaTela += quantidade;
	}
	
	/**
	 * Mostra o cen�rio
	 * @param c O componente no qual o cen�rio ser� mostrado.
	 * @param g Graphics da imagem na qual o cen�rio ser� mostrado.
	 */
	public void paint(Graphics g)
	{
		// Desenhando o cen�rio
		g.setClip( 0, 0, 320, 240 );
		g.drawImage(imageData.getSubimage(posicaoNaTela, 0, 320, 240), 0, 0, imageData.getWidth(), imageData.getHeight(), null, null);
	}
}
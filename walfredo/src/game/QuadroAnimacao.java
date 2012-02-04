package game;
import java.awt.Graphics;
import java.io.Serializable;

import javax.swing.ImageIcon;

/**
 *	Classe que implementa um quadro de uma anima��o.
 *
 *  @author  Paolo Victor, Helder Fernando, �lvaro Magnum, Fabr�cio Gutemberg,
 *           Dalton C�zane.
 *  @version 1.0
 */
public class QuadroAnimacao implements Serializable
{	
	/**
	 * O tempo pelo qual o quadro j� foi exibido.
	 */
	private int tempoExibido;
	
	/**
	 * A imagem do quadro.
	 */
	private ImageIcon imagem;
	
	/** 
	 *  M�todo construtor da classe
	 *  @param nomeDaImagem O nome do arquivo da imagem.
	 */
	public QuadroAnimacao(String nomeDaImagem)
	{
		imagem = new ImageIcon( nomeDaImagem );
		this.tempoExibido = 0;
	}
	
	/**
	 * M�todo que retorna o tempo pelo qual o quadro j� foi exibido.
	 * @return O tempo pelo qual o quadro j� foi exibido.
	 */
	public int getTempoExibido()
	{
		return(tempoExibido);
	}
	
	/** 
	 *  Reinicia o tempo de exibi��o do quadro.
	 */
	public void reiniciarQuadro()
	{
		tempoExibido = 0;
	}
	
	/**
	 * Atualiza o tempo de exibi��o do quadro.
	 */
	public void atualizarTempoDeExibicao()
	{
		tempoExibido++;
	}
	
	/**
	 * Mostra o objeto 
	 * @param c O componente.
	 * @param g Graphics da imagem na qual o objeto ser� mostrado.
	 * @param x A posi��o x na qual o quadro ser� desenhado.
	 * @param y A posi��o y na qual o quadro ser� desenhado.
	 */
	public void desenharQuadro(Graphics g, int x, int y)
	{
		g.setClip( 0, 0, 320, 240 );
		imagem.paintIcon( null, g, x, y );
	}
}
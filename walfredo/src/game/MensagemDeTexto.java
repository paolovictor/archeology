package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.Serializable;

/**
 *	Classe que implementa mensagem de texto
 *
 *  @author  Paolo Victor, Helder Fernando, Alvaro Magnum, Dalton Cezane, Fabricio Gutemberg
 *  @version 1.0
 */
public class MensagemDeTexto implements Serializable, Desenhavel
{	

	/**
	 * Constante que define a cor branca 
	 */
	public static final Color COR_BRANCA   = Color.white;
	
	
	
	public static final Color COR_VERMELHA = Color.red;
	
	/**
	 * Constante que define a cor amarela 
	 */
	public static final Color COR_AMARELA  = Color.yellow;
	
	/**
	 * Constante que indica que a mensagem nunca ira sair da tela
	 */
	public static final int MENSAGEM_ETERNA  = -1;

	/**
	 * O texto da mensagem
	 */	
	private String mensagem;
	
	
	private int posicaoX, posicaoY, tempo;
	private Font fonte;
	private Color cor;
	
	/** M�todo construtor da classe
	 *  @param mensagem A mensagem.
	 *  @param tipo O tipo da fonte
	 *  @param tamanho O tamanho da fonte
	 *  @param cor A cor da fonte
	 *  @param tempo O tempo pelo qual a mensagem ser� mostrada
	 *  @param posicaoX A posicao x da mensagem
	 *  @param posicaoY A posicao y da mensagem
	 */
	public MensagemDeTexto(String mensagem, String tipo, int tamanho,
	                       Color cor, int tempo, int posicaoX, int posicaoY)
	{
		this.mensagem = mensagem;
		this.cor      = cor;
		this.tempo    = tempo;
		this.posicaoX = posicaoX;
		this.posicaoY = posicaoY;
		fonte = new Font(tipo, Font.BOLD, tamanho);
	}
	
	/** M�todo que atualiza a mensagem de texto.
	 *  @return -1 se a mensagem deve ser apagada.
	 */
	public void atualizarMensagem()
	{
		if(tempo > 0)
		{
			tempo--;
		}
	}
	
	/** M�todo que retorna se a mensagem deve ser apagada.
	 *  @return true se ela deve ser apagada.
	 */
	public boolean deveSerApagada()
	{
		return(tempo == 0);
	}
	
	/** M�todo que desenha o quadro */
	public void paint(Graphics g)
	{		
		// Setando a fonte
		g.setFont(fonte);
	
		// Mudando a cor e desenhando a borda negra
		g.setColor(Color.black);
		
		// Desenhando a borda do nome
		g.drawString(mensagem, posicaoX + 1, posicaoY);
		g.drawString(mensagem, posicaoX - 1, posicaoY);
		g.drawString(mensagem, posicaoX, posicaoY + 1);
		g.drawString(mensagem, posicaoX, posicaoY - 1);
		
		// Desenhando o nome
		g.setColor(cor);
		g.drawString(mensagem, posicaoX, posicaoY);
	}
}

package game;
import java.awt.Component;
import java.awt.Graphics;

/**
 *
 * Classe que implementa um defunto.<br><br>
 * 
 * Um defunto � um corpo de inimigo ou do pr�prio
 * jogador.
 *
 * @author  Paolo Victor
 * @version 0.1
 */

public class Defunto extends ObjetoDeJogo implements Desenhavel
{	
	/**
	 * O tempo pelo qual o corpo do inimigo fica na tela.
	 */
	public final static int TEMPO_INIMIGO = 150;
	
	/**
	 * Indica que o corpo nunca vai desaparecer.
	 */
	public final static int TEMPO_ZUMBI = -1;
	
	/**
	 * Tempo pelo qual o defunto j� foi mostrado
	 */
	private int tempoMostrado;
	
	/** O tempo m�ximo pelo qual o defunto deve ser mostrado */
	private int tempoDePermanencia;
	
	/** Construtor padr�o do Defunto
	 *  @param posicaoX A posicao X inicial do jogador.
	 *  @param posicaoY A posicao Y inicial do jogador.
	 *  @param largura A largura do jogador.
	 *  @param altura A altura do jogador.
	 */
	public Defunto(int posicaoX, int posicaoY, int largura, int altura,
	               int direcao, int tempoDePermanencia)
	{
		super(posicaoX, posicaoY, largura, altura, true);
		
		tempoMostrado = 0;
		velocidadeZ   = 3;		
		estaNoAr      = true;		
		this.tempoDePermanencia = tempoDePermanencia;
		
		if(direcao == 0)
		{			
			direcaoDaAnimacao = 0;
			velocidadeX = -2;
		}
		else
		{
			direcaoDaAnimacao = 1;
			velocidadeX = 2;
		}
	}
	
	/** M�todo que atualiza a posi��o do defunto */
    public void atualizarPosicao()
    {
    	super.atualizarPosicao();
    	
    	// Mostrando as anima��es do defunto
    	if(estaNoAr)
    	{
    		setAnimacaoAtual("midair");
    	}
    	else
    	{
    		setAnimacaoAtual("ground");
    	}
    }
    	
	
	/** M�todo que atualiza o status do defunto
	 *  @return Um inteiro dizendo se o defunto deve ser retirado do jogo.
	 */
	public void atualizarStatus()
	{
		tempoMostrado++;
	}
	
	/**
	 * M�todo que mostra o defunto. Foi redefinido para implementar
	 * @param c O componente.
	 * @param g Graphics da imagem na qual o objeto ser� mostrado.
	 */
	public void paint(Component c, Graphics g)
	{
		if(tempoDePermanencia != TEMPO_ZUMBI)
		{
			if(tempoMostrado <= tempoDePermanencia - tempoDePermanencia / 4 ||
			   tempoMostrado % 5 != 0)
			{
				super.paint(g);
			}
		}
		else
		{
			super.paint(g);
		}
	}
	
	/** M�todo que retorna se o defunto deve ser apagado.
	 *  @return true se ele deve ser apagado.
	 */
	public boolean deveSerApagado()
	{
		return(tempoDePermanencia != TEMPO_ZUMBI && tempoMostrado > tempoDePermanencia);
	}
}
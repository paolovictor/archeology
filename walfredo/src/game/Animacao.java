
package game;
import java.awt.Graphics;
import java.io.Serializable;

import javax.swing.JPanel;

/**
 *	Classe que implementa uma anima��o.<br><br>
 *
 *  A anima��o deve estar guardada em arquivos .gif, com a nomenclatura
 *  <code>nomedaimagemX.gif</code>, onde <b>X</b> � o n�mero do quadro, que
 *  inicia em 0.<br><br>
 *
 *  Uma anima��o pode ou n�o entrar em loop, e isso � definido no
 *  construtor da classe. Caso haja o loop, depois que o �ltimo
 *  frame � exibido a anima��o volta ao frame de n�mero definido
 *  pelo atributo <code>reinicioDoLoop</code>. A velocidade da anima��o
 *  tamb�m � definida no construtor, e pode ter um valor entre 0 e 100.
 *
 *  @author  Paolo Victor, Helder Fernando, Alvaro Magnum, Dalton Cezane, Fabricio Gutemberg
 *  @version 1.0
 */
 
public class Animacao extends JPanel implements Serializable
{	
    /**
	 * Constante que define a velocida maxima da animacao
	 */
    private final int VELOCIDADE_MAXIMA = 100;
    
	/**
	 * O nome do arquivo de origem
	 */
	private String  arquivoOrigem, nome;
	
	/**
	 * O numero de quadros da animacao
	 */
	private int     numeroDeQuadros, quadroAtual;
	
	/**
	 * A velocidade da animacao
	 */
	private int     velocidade;
	
	/**
	 * Indica em que quadro a animacao ira recomecar o loop
	 */
	private int     reinicioDoLoop;
	
	/**
	 * Indica se havera loop
	 */
	private boolean loop;
	
	/**
	 * Array de quadros
	 */
	private QuadroAnimacao[] quadros;
	
	/** M�todo construtor da classe. Constr�i uma anima��o com loop.
	 *  @param nome O nome da animacao
	 *  @param arquivoOrigem O nome do arquivon de origem
	 *  @param numeroDeQuadros O numero de quadros da animacao
	 *  @param velocidade Velocidade que os quadros sao exibidos
	 *  @param reinicioDoLoop Incdica o quadro em que animacao ira recomecar
	 *  @param loop Indica se ocorrera loop */
	public Animacao(String nome, String arquivoOrigem, int numeroDeQuadros,
	                int velocidade, int reinicioDoLoop, boolean loop)
	{
		this.nome            = nome;
		this.arquivoOrigem   = arquivoOrigem;
		this.numeroDeQuadros = numeroDeQuadros;
		this.quadroAtual     = 1;		
		this.velocidade      = VELOCIDADE_MAXIMA - velocidade % VELOCIDADE_MAXIMA;
		
		this.reinicioDoLoop  = reinicioDoLoop;
		this.loop            = loop;
		
		this.quadros         = new QuadroAnimacao[numeroDeQuadros];
		
		this.carregaQuadros();
	}
	
	/** M�todo construtor da classe. Constr�i uma anima��o sem loop.
	 *  @param nome O nome da animacao
	 *  @param arquivoOrigem O nome do arquivon de origem
	 *  @param numeroDeQuadros O numero de quadros da animacao
	 *  @param velocidade Velocidade que os quadros sao exibidos */
	public Animacao(String nome, String arquivoOrigem, int numeroDeQuadros, int velocidade)
	{
		this(nome, arquivoOrigem, numeroDeQuadros, velocidade, 1, false);
	}
	
	/**
	 * M�todo que carrega os quadros da anima��o
	 */
	private void carregaQuadros()
	{
		QuadroAnimacao temp;
		
		for(int k = 0; k < numeroDeQuadros; k++)
		{
			temp = new QuadroAnimacao(arquivoOrigem + k + ".gif");
			quadros[k] = temp;
		}
	}

	/** 
	 *  M�todo que retorna o nome da anima��o
	 *  @return nome da animacao
	 */
	public String getNome()
	{
		return(nome);
	}
	
	/**
	 * M�todo que retorna o n�mero de quadros 
	 * @return O numero de quadros da animacao
	 */
	public int getNumeroDeQuadros()
	{
		return(numeroDeQuadros);
	}
	
	/** 
	 *  M�todo que retorna o n�mero do quadro atual 
	 *  @return O quadro atual da animacao
	 */
	public int getQuadroAtual()
	{
		return(quadroAtual);
	}
	
	/**
	 * M�todo que atualiza a anima��o
	 */
	public void atualizaAnimacao()
	{
		int quadroNum = quadroAtual - 1;
		
		quadros[quadroNum].atualizarTempoDeExibicao();
		
		if(quadros[quadroNum].getTempoExibido() >= velocidade
		   && quadroAtual <= numeroDeQuadros)
		{
			quadroAtual++;
			quadros[quadroNum].reiniciarQuadro();
		}	
		
		if(quadroAtual > numeroDeQuadros)
		{
			if(loop)
			{
				quadroAtual = reinicioDoLoop;
			}
			else
			{
				quadroAtual--;
			}
		}		
	}
	
	/**
	 * M�todo que reinicia uma anima��o
	 */
	public void  reiniciarAnimacao()
	{
		this.quadroAtual = 1;		
		quadros[quadroAtual - 1].reiniciarQuadro();
	}
	
	/** 
	 *  M�todo que desenha a anima��o 
	 *  @param c Component O componente no qual sera desenhado
	 *  @param g Graphics O Graphics usado para desenhar no componente
	 *  @param x A coordenanda x onde a animacao sera desenhada
	 *  @param y A coordenanda y onde a animacao sera desenhada
	 */
	public void desenharAnimacao(Graphics g, int x, int y)
	{
		// Desehando o quadro atual
		quadros[quadroAtual - 1].desenharQuadro(g, x, y);
	}
}
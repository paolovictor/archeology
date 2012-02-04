package game;
/**
 *	Classe que implementa uma a��o de um inimigo.<br><br>
 *
 *  @author  Paolo Victor, Helder Fernando, �lvaro Magnum, Fabr�cio Gutemberg,
 *           Dalton C�zane.
 *  @version 1.0
 */
 
public class Acao
{
	/**
	 * Indica que o tipo da a��o � fazer o inimigo esperar.
	 */
	public static final int ESPERAR = 0;
	
	/**
	 * Indica que o tipo da a��o � fazer o inimigo atacar.
	 */
	public static final int ATACAR = 1;
	
	/**
	 * Indica que o tipo da a��o � fazer o inimigo pular.
	 */
	public static final int PULAR = 2;
	
	/**
	 * Indica que o tipo da a��o � mover o inimigo.
	 */
	public static final int MOVER = 3;
	
	/**
	 * O indicador de tipo da a��o.
	 */
	private int indicador;
	
	/**
	 * A lista de atributos da a��o.
	 */
	private int[] atributos;
	
	/** Construtor da classe Acao
	 * @param indicador O tipo da a��o
	 * @param array Parametros para o construtor dependendo do tipo da a��o tomada
	 */
	public Acao(int indicador,int array[])
	{
		this.indicador = indicador;
		this.atributos = array;
	}
	
	/** 
	 *  Retorna o indicador da a��o
	 *  @return o indicador da a��o
	 */
	public int getIndicador()
	{
		return(indicador);
	}
	
	/**
	 *  Retorna os atributos da a��o
	 *  @param posicao A posi��o do atributo
     * @return O atributo na posi��o dada.
 	 */
	public int getAtributo(int posicao)
	{
		return(atributos[posicao]);
	}
		
	
}
	

			

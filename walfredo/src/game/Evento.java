package game;
import java.io.*;
import java.awt.*;

/**
 *
 * Classe que implenta os eventos de uma fase.
 * 
 * @author  Paolo Victor, Helder Fernando, �lvaro Magnum, Fabr�cio Gutemberg,
 *          Dalton C�zane.
 * @version 1.0
 */
 
 public class Evento implements Serializable
 {
 	/**
 	 * Constante para o tipo de evento "Abrir cen�rio".
 	 */
 	public final static int ABRIR_CENARIO = 0;
 	
 	/**
 	 * Constante para o tipo de evento "Abrir criar inimigo".
 	 */
 	public final static int CRIAR_INIMIGO = 1;
 	
 	/**
 	 * Constante para o tipo de evento "Passar de fase".
 	 */
 	public final static int PASSAR_DE_FASE = 2;
 	
 	/**
 	 * Constante para o tipo de evento "Esperar morte dos inimigos".
 	 */
 	public final static int ESPERAR_MORTE = 3;
 	
 	/**
 	 * Constante para o tipo de evento "Mostrar mensagem".
 	 */
 	public final static int MOSTRAR_MENSAGEM = 4;
 	
 	/**
 	 * Constante para o tipo de evento "Tocar m�sica do chefe".
 	 */
 	public final static int MUSICA_CHEFE = 5;
 	
 	/**
 	 * Array de atributos do evento.
 	 */
 	private Object[] atributos;
 	
 	/**
 	 * O tipo do evento.
 	 */
 	private int tipo;
 	
 	/**
 	 * Construtor da classe Evento. Constroi um evento com um tipo e um array de atributos.
 	 */
 	public Evento(int tipo, Object[] atributos)
    {
 	    this.tipo = tipo; 	   
 	    this.atributos = atributos;
 	}
 	
 	/**
 	 * Metodo que retorna o tipo do evento
 	 * @return O tipo do evento
 	 */ 		
 	public int getTipo()
 	{
 		return tipo;
 	}
 	
 	/**
 	 * Metodo que retorna o atributo do evento em forma de inteiro
 	 * @param i O indice do array de atributos
 	 * @return O atributo correspondente ao indice em forma de inteiro
 	 */
 	public int getAtributoInteiro(int i) throws Exception
 	{ 		
 		int retorno = 0;
 		
 		try
 		{
 			retorno = ((Integer)atributos[i]).intValue(); 		 		
 		}
 		catch(Exception e)
 		{
 			throw(new Exception("O atributo n�o � inteiro v�lido."));
 		}
 		
 		return(retorno);
 	}
 	
 	/**
 	 * Metodo que retorna o atributo do evento em forma de String
 	 * @param i O indice do array de atributos
 	 * @return O atributo correspondente ao indice em forma de String
 	 */
 	public String getAtributoString(int i) throws Exception
 	{
 		String retorno = null;
 		
 		try
 		{
 			retorno = (String)atributos[i];
 		}
 		catch(Exception e)
 		{
 			throw(new Exception("O atributo n�o � String v�lido."));
 		}
 		
 		return(retorno);
 	}
 	
 	/**
 	 * Metodo que retorna o atributo do evento em forma de Color. Usado
 	 * para cores das mensagens de texto.
 	 * @param i O indice do array de atributos
 	 * @return O atributo correspondente ao indice em forma de Color
 	 */
 	public Color getAtributoColor(int i) throws Exception
 	{
 		Color retorno = null;
 		
 		try
 		{
 			retorno = (Color)atributos[i];
 		}
 		catch(Exception e)
 		{
 			throw(new Exception("O atributo n�o � color v�lido."));
 		}
 		
 		return(retorno);
 	}
 }

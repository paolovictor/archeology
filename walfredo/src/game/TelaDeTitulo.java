package game;

import java.io.File;
import java.util.Vector;

import javax.swing.JOptionPane;

/**
 *	Classe que implementa a tela de t�tulo do jogo
 *
 *  @author  Paolo Victor, Helder Fernando, Alvaro Magnum, Dalton Cezane, Fabricio Gutemberg
 *  @version 1.0
 */
public class TelaDeTitulo implements ComunicavelComIG
{		
	/** 
	 * O cen�rio da tela. 
	 */
	private Cenario fundoDaTela;
	
	/** 
	 * O cursor 
	 */
	private Cursor cursor;
	
	/** 
	 * A op��o escolhida pelo jogador 
	 */
	private int opcaoEscolhida;
	
	/** 
	 * A posicao do cursor 
	 */
	private int posicaoDoCursor;
	
	/** 
	 * Variavel de controle do cursor 
	 */
	private boolean liberarCursor;
	
	/** 
	 * As jukeboxes 
	 */
	Jukebox musicas;
	
	/**
	 * Constante que define que o jogador n�o escolheu nenhuma op��o
	 */
	public static final int NENHUMA  = 0;
	
	/**
	 * Constante que define que o jogador escolheu a op��o jogar
	 */
	public static final int JOGAR    = 1;
	
	/**
	 * Constante que define que o jogador escolheu a op��o ajuda
	 */
	public static final int AJUDA    = 2;
	
	/**
	 * Constante que define que o jogador escolheu a op��o hiscores
	 */
	public static final int HISCORES = 3;
	
	/**
	 * Constante que define que o jogador escolheu a op��o sair
	 */
	public static final int SAIR     = 4;

	/** M�todo construtor de classe */
	public TelaDeTitulo()
	{
		// Criando a jukebox, adicionando as faixas e tocando a m�sica da tela
		musicas        = new Jukebox();
		
		try
		{
			musicas.adicionarFaixa(GameDirectories.AUDIO_DIR + File.separator + "titulo.mid");
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, "Erro com a sa�da de som:\n" + e);
			System.exit(1);
		}
		musicas.tocarFaixa(0, true);
	
		// Criando o cen�rio e cursor
		fundoDaTela = new Cenario(GameDirectories.GFX_DIR + File.separator + "misc" + File.separator + "teladetitulo.gif");
		cursor = new Cursor(-25, -25, GameDirectories.GFX_DIR + File.separator + "bola_menu.ani");
		
		liberarCursor = false;		
		posicaoDoCursor = JOGAR;
		opcaoEscolhida  = NENHUMA;
	}
	
	/** M�todo que atualiza a tela de t�tulo.
	 *  @param teclasPressionadas m�scara de bits com as teclas pressionadas pelo jogador
	 *  @return A op��o escolhida pelo jogador*/
	public void atualizarTela(byte teclasPressionadas)
	{
		// Atualizando a anima��o do cursor
		cursor.atualizaAnimacao();
		
		// Lendo os comandos do jogador
		if((teclasPressionadas & Teclado.TECLA_DIREITA) > 0
		   && posicaoDoCursor != SAIR && liberarCursor)
    	{
    		posicaoDoCursor++;
    		liberarCursor = false;
    	}
    	
    	if((teclasPressionadas & Teclado.TECLA_ESQUERDA) > 0
    	   && posicaoDoCursor != JOGAR && liberarCursor)
    	{
    		posicaoDoCursor--;
    		liberarCursor = false;
    	}
    	
    	if((teclasPressionadas & Teclado.TECLA_SOCO) > 0)
    	{
    		musicas.pararFaixa();
    		opcaoEscolhida = posicaoDoCursor;
    	}
    	
    	// Posicionando o cursor
    	switch(posicaoDoCursor)
    	{
    		case JOGAR:
    			cursor.setPosicao(7, 183);
    			break;
    		case AJUDA:
    			cursor.setPosicao(76, 209);
    			break;
    		case HISCORES:
    			cursor.setPosicao(147, 183);
    			break;
    		case SAIR:
    			cursor.setPosicao(236, 211);
    			break;
    	}
    	
    	// Liberando o cursor
    	if((teclasPressionadas & Teclado.TECLA_ESQUERDA) == 0 &&
    	   (teclasPressionadas & Teclado.TECLA_DIREITA) == 0)
    	{
    		liberarCursor = true;
    	}
	}
	
	/** M�todo que retorna se a op��o foi escolhida pela jogador,
	 *  e qual � essa op��o.
	 *  @return Um inteiro com a op��o escolhida
	 */
	public int escolheuOpcao()
	{
		return(opcaoEscolhida);		
	}
	
	/** Retorna os objetos da fase. Usado pela classe
	 *  <code>InterfaceGraficaDaFase</code>.
	 */
	public Vector getObjetos()
	{
		// Adicionando todos os objetos � um �nico vector
		Vector todosOsObjetos = new Vector();
		
		todosOsObjetos.add(fundoDaTela);
		todosOsObjetos.add(cursor);
		
		// Retornando o vector
		return(todosOsObjetos);
	}
}
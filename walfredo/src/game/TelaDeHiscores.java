package game;


import java.io.File;
import java.util.Vector;

import javax.swing.JOptionPane;

/**
 *	Classe que implementa a tela de hiscores do jogo.
 *
 *  @author  Paolo Victor, Helder Fernando, Alvaro Magnum, Dalton Cezane, Fabricio Gutemberg
 *  @version 1.0
 */
public class TelaDeHiscores implements ComunicavelComIG
{		
	/** 
	 * O cen�rio da tela. 
	 */
	private Cenario fundoDaTela;
	
	/** 
	 * As jukeboxes 
	 */
	Jukebox musicas;
	
	/**
	 * Os hiscores
	 */
	Hiscores hiscores;
	
	/**
	 * Define se o jogador quer sair da tela
	 */
	private boolean sairDaTela;
	
	/**
	 * Vector com as mensagens dos hiscores
	 */
	private Vector scoreMsgs;

	/**
	 * M�todo construtor de classe
	 * @param score O score do jogador.
	 */
	public TelaDeHiscores(int score)
	{
		try
		{
			musicas = new Jukebox();
			musicas.adicionarFaixa("audio" + File.separator + "hiscores.mid");
			musicas.tocarFaixa(0, true);
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, "Erro com a sa�da de som:\n" + e);
			System.exit(1);
		}
	
		// Criando o cen�rio e cursor
		fundoDaTela = new Cenario( GameDirectories.GFX_DIR + File.separator + "misc" + File.separator + "teladehiscores.gif");
		
		// No come�o, o jogador n�o quer sair da tela
		sairDaTela = false;
		
		// Abre os hiscores
		hiscores = new Hiscores("hiscores.his");
		
		// Testando se o score do jogador deve ser adicionado
		// e o adicionando.
		hiscores.adicionarScore(score);
		
		// Inicializando as mensagens com os scores.
		scoreMsgs = new Vector();
		for(int k = 0; k < Hiscores.NUMERO_MAXIMO_SCORES; k++)
		{
			// Adicionando o nome
			scoreMsgs.add(new MensagemDeTexto((k + 1) + ". " + hiscores.getJogadorEm(k),// msg
		                                      "Arial", // fonte
		                                      20, // tamanho
	                                          MensagemDeTexto.COR_BRANCA, // cor
	                                          MensagemDeTexto.MENSAGEM_ETERNA, // tempo
	                                          50, // posi��o X
	                                          70 + 25 * k)); // posi��o Y

			// Adicionando o score	                                          
	        scoreMsgs.add(new MensagemDeTexto(hiscores.getScoreEm(k), // mensagem
		                                      "Arial", // fonte
		                                      20, // tamanho
	                                          MensagemDeTexto.COR_BRANCA, // cor
	                                          MensagemDeTexto.MENSAGEM_ETERNA, // tempo
	                                          190, // posi��o X
	                                          70 + 25 * k)); // posi��o Y;
		}
		
		// Adicionando a instru��o para sa�da.
		scoreMsgs.add(new MensagemDeTexto("Aperte ESC para sair.", // mensagem
		                                  "Arial", // fonte
	                                      18, // tamanho
                                          MensagemDeTexto.COR_BRANCA, // cor
                                          MensagemDeTexto.MENSAGEM_ETERNA, // tempo
                                          20, // posi��o X
                                          220)); // posi��o Y;
	}
	
	/** M�todo que atualiza a tela de t�tulo.
	 *  @param teclasPressionadas m�scara de bits com as teclas pressionadas pelo jogador
	 */
	public void atualizarTela(byte teclasPressionadas)
	{
		// Saindo da tela
		if((teclasPressionadas & Teclado.TECLA_ESCAPE) > 0)
    	{
    		sairDaTela = true;
    		musicas.pararFaixa();
    	}
	}
	
	/** M�todo que retorna se a tela de hiscores acabou.
	 *  @return <code>true</code> caso verdade.
	 */
	public boolean acabou()
	{
		return(sairDaTela);		
	}
	
	/** Retorna os objetos da fase. Usado pela classe
	 *  <code>InterfaceGraficaDaFase</code>.
	 */
	public Vector getObjetos()
	{
		// Adicionando todos os objetos � um �nico vector
		Vector todosOsObjetos = new Vector();
		
		todosOsObjetos.add(fundoDaTela);
		todosOsObjetos.addAll(scoreMsgs);
		
		// Retornando o vector
		return(todosOsObjetos);
	}
}
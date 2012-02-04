package game;

import java.io.File;
import java.util.Vector;

/**
 *	Classe que implementa a tela de ajuda do jogo
 *
 *  @author  Paolo Victor, Helder Fernando, Alvaro Magnum, Dalton Cezane, Fabricio Gutemberg
 *  @version 1.0
 */
public class TelaDeAjuda implements ComunicavelComIG
{	
	/** 
	 * O cen�rio da tela. 
	 */
	private Cenario fundoDaTela;
	
	/** 
	 * Define se o jogador saiu da tela
	 */
	private boolean saiuDaTela;
	
	/** 
	 *M�todo construtor de classe 
	 */
	public TelaDeAjuda()
	{	
		// Criando o cen�rio
		fundoDaTela = new Cenario( GameDirectories.GFX_DIR + File.separator + "misc" + File.separator + "teladeajuda.gif");
	}
	
	/**
	 *  M�todo que atualiza a tela de t�tulo.
	 *  @param teclasPressionadas m�scara de bits com as teclas pressionadas pelo jogador
	 */
	public void atualizarTela(byte teclasPressionadas)
	{
		
		// Lendo os comandos do jogador - Se ele saiu da tela.
    	saiuDaTela = ((teclasPressionadas & Teclado.TECLA_ESCAPE) > 0);
	}
	
	/** 
	 *  Retorna se a tela de ajuda acabou.
	 *  @return <code>true</code> caso verdadeiro.
	 */
	public boolean acabou()
	{
		return(saiuDaTela);
	}
	
	/** Retorna os objetos da fase. Usado pela classe
	 *  <code>InterfaceGraficaDaFase</code>.
	 */
	public Vector getObjetos()
	{
		// Adicionando todos os objetos � um �nico vector
		Vector todosOsObjetos = new Vector();
		
		todosOsObjetos.add(fundoDaTela);
		
		// Retornando o vector
		return(todosOsObjetos);
	}	
}
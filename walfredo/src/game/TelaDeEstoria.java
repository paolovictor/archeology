package game;

import java.util.Vector;

import javax.swing.JOptionPane;

/**
 *	Classe que implementa uma fase do jogo.
 *
 *  @author  Paolo Victor, Helder Fernando, �lvaro Magnum, Fabr�cio Gutemberg,
 *           Dalton C�zane.
 *  @version 1.0
 */
public class TelaDeEstoria implements ComunicavelComIG
{
	/**
	 * Define o tempo de exibi��o de cada slide.
	 */
	private final int TEMPO_POR_SLIDE = 400;
	
	/**
	 * O Vector com os slides da tela.
	 */
	private Vector slides;
	
	/**
	 * Vector com as mensagens dos slides 
	 */
	private Vector mensagensDosSlides;
	
	/**
	 * A slide que est� sendo mostrado
	 */
	private int slideAtual;
	
	/**
	 * O tempo pelo qual o slide atual j� foi mostrado.
	 */
	private int tempoDoSlide;
	
	/**
	 * A jukebox da tela 
	 */
	private Jukebox musicas;

	/**
	 * M�todo construtor de classe.
	 * @param slides Os slides da tela.
	 * @param mensagensDosSlides As mensagens mostradas em cada slide.
	 * @param nomeDaMusica O nome do arquivo da m�sica que ser� tocado na tela.
	 */
	public TelaDeEstoria(Vector slides, Vector mensagensDosSlides, String nomeDaMusica)
	{	
		// Criando e carregando a jukebox, e tocando a m�sica
		musicas = new Jukebox();
		
		try
		{
			musicas.adicionarFaixa(nomeDaMusica);
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, "Erro com a sa�da de som:\n" + e);
			System.exit(1);
		}
		musicas.tocarFaixa(0, true);
		
		// Criando os slides e as mensagens
		this.slides             = slides;
		this.mensagensDosSlides = mensagensDosSlides;
		slideAtual              = 0;
		tempoDoSlide            = 0;
	}
	
	/**
	 * M�todo que atualiza a tela de est�ria.
	 * @param teclasPressionadas M�scara de bits com as teclas pressionadas pelo jogador.
	 */
	public void atualizarTela(byte teclasPressionadas)
	{		
		if(tempoDoSlide >= TEMPO_POR_SLIDE)
		{
			slideAtual++;
			tempoDoSlide = 0;
		}
		
		if((teclasPressionadas & Teclado.TECLA_ESCAPE) > 0)
    	{
    		slideAtual = slides.size();
    	}
    	
    	if((teclasPressionadas & Teclado.TECLA_SOCO) > 0
    	    && tempoDoSlide > 0)
    	{
    		slideAtual++;
    		tempoDoSlide = 0;
    	}
    	
    	if((teclasPressionadas & Teclado.TECLA_SOCO) == 0)
    	{
    		tempoDoSlide++;
    	}
    	
    	// Se acabou, parar a m�sica
    	if(acabou())
    	{
    		musicas.pararFaixa();
    	}
    }
	
	/** Retorna os objetos da fase. Usado pela classe
	 *  <code>InterfaceGraficaDaFase</code>.
	 * @return Um Vector com os objetos que devem ser mostrados.
	 */
	public Vector getObjetos()
	{
		// Adicionando todos os objetos � um �nico vector
		Vector todosOsObjetos = new Vector();
		
		todosOsObjetos.add(slides.get(slideAtual));
		todosOsObjetos.add(mensagensDosSlides.get(slideAtual));
		todosOsObjetos.add(new MensagemDeTexto("Aperte ESC para sair", "Arial", 12,
	                       MensagemDeTexto.COR_BRANCA, MensagemDeTexto.MENSAGEM_ETERNA,
	                       10, 20));
		
		// Retornando o vector
		return(todosOsObjetos);
	}
	
	/**
	 *  M�todo que retorna se a est�ria acabou
	 *  @return <code>true</code> se a est�ria acabou.
	 */
	public boolean acabou()
	{
		return(slideAtual >= slides.size());
	}
}
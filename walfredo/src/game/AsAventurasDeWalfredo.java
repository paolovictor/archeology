
package game;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.Timer;

/**
 *	Classe que implementa a parte principal do jogo.
 *
 * @author  Paolo Victor, Helder Fernando, �lvaro Magnum, Fabr�cio Gutemberg,
 *           Dalton C�zane.
 * @version 1.0
 */

public class AsAventurasDeWalfredo extends JFrame implements KeyListener, ActionListener
{	
	/**
	 * Constante que define que a tela atual � a primeira tela de est�ria.
	 */
	private final int CUT_SCENE = 0;
	
	/**
	 * Constante que define que a tela atual � a tela de t�tulo.
	 */
	private final int TELA_DE_TITULO = 1;
	
	/**
	 * Constante que define que a tela atual � a fase 1 do jogo.
	 */
	private final int FASE1_DO_JOGO   = 20;
	
	/**
	 * Constante que define que a tela atual � a fase 2 do jogo.
	 */
	private final int FASE2_DO_JOGO   = 21;
	
	/**
	 * Constante que define que a tela atual � a fase 3 do jogo.
	 */
	private final int FASE3_DO_JOGO   = 22;
	
	/**
	 * Constante que define que a tela atual � a tela de ajuda.
	 */
	private final int AJUDA_DO_JOGO  = 3;
	
	/**
	 * Constante que define que a tela atual � a tela de hiscores.
	 */
	private final int HI_SCORES  = 4;
	
	/**
	 * Os frames por segundo do jogo
	 */
	public static final int FPS_PADRAO = 60;

	/** 
	 *  O estado do jogo. Define em que tela (t�tulo, fase, etc) est� o jogo.
	 */
	private int estadoDoJogo;
	
	/**
	 * O score geral do jogo. � a soma dos scores das fases.
	 */
	private int scoreDoJogo;
	
	/**
	 * A interface gr�fica do jogo
	 */
	private InterfaceGrafica interfaceGrafica;
	
	/** A tela de est�ria */
	private TelaDeEstoria telaDeEstoria;

	/** A tela de t�tulo do jogo */
	private TelaDeTitulo telaDeTitulo;
	
	/** A tela de ajuda do jogo */
	private TelaDeAjuda telaDeAjuda;
	
	/** A tela de hiscores do jogo */
	private TelaDeHiscores telaDeHiscores;
	
	/**
	 * A fase do jogo
	 */
	private Fase faseDoJogo;
	
	/**
	 * Timer do jogo. Utilizado para a l�gica do jogo
	 */
	Timer timerDoJogo;
	
	/**
	 * Byte com as teclas pressionadas
	 */
	private byte teclasPressionadas = 0x00;
	
	/**
	 * Construtor padr�o da classe.
	 * @param framesPorSegundo Os frames por segundo desejados.
	 */
	public AsAventurasDeWalfredo(int framesPorSegundo)
	{
		// Mostrando o t�tulo
		super("As Aventuras de Walfredo Cirne");
		
		// inicializando o score
		scoreDoJogo = 0;
		
		// Iniciando a tela de t�tulo
		estadoDoJogo = CUT_SCENE;
		
		// Criando a tela de est�ria
		telaDeEstoria = construirPrimeiraTelaDeEstoria();

		interfaceGrafica = new InterfaceGrafica(telaDeEstoria);
		
		// Adicionando a tela de t�tulo ao content pane
		getContentPane().add(interfaceGrafica, BorderLayout.CENTER);
				
		// Adicionando o key listener e mostrando a aplica��o.
		addKeyListener(this);
	
		// Setando a janela � a mostrando
		setSize(326,272);
		setVisible(true);
		
		// Iniciando o timer
		timerDoJogo = new Timer(1000/framesPorSegundo, this);
		timerDoJogo.start();
	}
	
	/**  Construtor alternativo, com fps padr�o, que � de 30 segundos. */
	public AsAventurasDeWalfredo()
	{
		this(AsAventurasDeWalfredo.FPS_PADRAO);
	}
	
	/**  Atualiza o jogo  */
	private void atualizarJogo()
	{
    	switch(estadoDoJogo)
		{
			case CUT_SCENE:
				// Atualizando a tela
				telaDeEstoria.atualizarTela(teclasPressionadas);
				
				// Testando se a est�ria acabou
				if(telaDeEstoria.acabou())
				{
					telaDeTitulo = new TelaDeTitulo();
					interfaceGrafica.setComponente(telaDeTitulo);
					estadoDoJogo = TELA_DE_TITULO;
				}
				break;
			
			case TELA_DE_TITULO:    				  				
				// Tratando as op��es escolhidas na tela de t�tulo
				telaDeTitulo.atualizarTela(teclasPressionadas);
				
				switch(telaDeTitulo.escolheuOpcao())
				{
					// Come�ando o jogo
					case TelaDeTitulo.JOGAR:
						faseDoJogo = new Fase(GameDirectories.LEVEL_DIR + File.separator + "fase1.evt", scoreDoJogo,
								              GameDirectories.AUDIO_DIR + File.separator + "audio" + File.separator + "fase1.mid");
						interfaceGrafica.setComponente(faseDoJogo);
						estadoDoJogo = FASE1_DO_JOGO;
						break;
					// Come�ando a tela de ajuda
					case TelaDeTitulo.AJUDA:
						telaDeAjuda = new TelaDeAjuda();
						interfaceGrafica.setComponente(telaDeAjuda);
						estadoDoJogo = AJUDA_DO_JOGO;
						break;
					// Come�ando a tela de hiscores
					case TelaDeTitulo.HISCORES:
					    telaDeHiscores = new TelaDeHiscores(scoreDoJogo);
					    interfaceGrafica.setComponente(telaDeHiscores);
						estadoDoJogo = HI_SCORES;
						break;						
					// Saindo do jogo
					case TelaDeTitulo.SAIR:
						System.exit(0);
						break;
				}
				break;				
			case FASE1_DO_JOGO:
			case FASE2_DO_JOGO:
			case FASE3_DO_JOGO:
    			// Atualizando a fase
				faseDoJogo.atualizarFase(teclasPressionadas);
				
				// Testando o status da fase. Ela termina se o jogador
				// morreu ou se a fase foi conclu�da.
				if(faseDoJogo.statusDaFase() != Fase.FASE_CONTINUA)
				{
					// Adicionando o score
					scoreDoJogo += faseDoJogo.getScoreDaFase();
	
					// Se o jogador morreu ou...
					if(faseDoJogo.statusDaFase() == Fase.JOGADOR_MORREU)
					{
						// Iniciando a tela de hiscores
						telaDeHiscores = new TelaDeHiscores(scoreDoJogo);
					    interfaceGrafica.setComponente(telaDeHiscores);
						estadoDoJogo = HI_SCORES;	
						
						// Depois que se entra na tela, o score do jogador
						// deve ser zerado.
						scoreDoJogo = 0;
					}
					// ...passou para pr�xima fase
					else if(faseDoJogo.statusDaFase() == Fase.FASE_CONCLUIDA)
					{
						switch(estadoDoJogo)
						{
							case FASE1_DO_JOGO:
								faseDoJogo = new Fase(GameDirectories.LEVEL_DIR + File.separator + "fase2.evt", scoreDoJogo,
								                      GameDirectories.AUDIO_DIR + File.separator + "fase2.mid");
								interfaceGrafica.setComponente(faseDoJogo);
								break;
							case FASE2_DO_JOGO:
								faseDoJogo = new Fase(GameDirectories.LEVEL_DIR + File.separator + "fase3.evt", scoreDoJogo,
										              GameDirectories.AUDIO_DIR + File.separator + "fase3.mid");
								interfaceGrafica.setComponente(faseDoJogo);
								break;
							case FASE3_DO_JOGO:
								telaDeEstoria = construirSegundaTelaDeEstoria();
								interfaceGrafica.setComponente(telaDeEstoria);
								estadoDoJogo = CUT_SCENE;
								break;							    
						}
					}
				}
				
				break;
			case AJUDA_DO_JOGO:
			 	// Atualizando a tela de ajuda
			 	telaDeAjuda.atualizarTela(teclasPressionadas);
			 	
			 	// Testando se o jogador saiu da tela
				if(telaDeAjuda.acabou())
				{
					telaDeTitulo = new TelaDeTitulo();
					interfaceGrafica.setComponente(telaDeTitulo);
					estadoDoJogo = TELA_DE_TITULO;
				}
				break;
			case HI_SCORES:
				// Atualizando a tela
				telaDeHiscores.atualizarTela(teclasPressionadas);
				
				// Testando se o jogador saiu da tela
				if(telaDeHiscores.acabou())
				{
					// Iniciando a tela de t�tulo
					telaDeTitulo = new TelaDeTitulo();
					interfaceGrafica.setComponente(telaDeTitulo);
					estadoDoJogo = TELA_DE_TITULO;	
				}
				break;
		}
	}
	
	public void keyTyped(KeyEvent e)
	{
		// do nothing.
	}
	
	public void keyPressed(KeyEvent e)
	{
		// Colocando as teclas na vari�vel.
		if(e.getKeyCode() == KeyEvent.VK_RIGHT)
		{
			teclasPressionadas |= Teclado.TECLA_DIREITA;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_LEFT)
		{
			teclasPressionadas |= Teclado.TECLA_ESQUERDA;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_UP)
		{
			teclasPressionadas |= Teclado.TECLA_CIMA;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_DOWN)
		{
			teclasPressionadas |= Teclado.TECLA_BAIXO;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_Z)
		{
			teclasPressionadas |= Teclado.TECLA_PULO;
		}		
		
		if(e.getKeyCode() == KeyEvent.VK_X)
		{
			teclasPressionadas |= Teclado.TECLA_SOCO;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
		{
			teclasPressionadas |= Teclado.TECLA_ESCAPE;
		}
	}
	
	public void keyReleased(KeyEvent e)
	{
		// Removendo as teclas da vari�vel.
		if(e.getKeyCode() == KeyEvent.VK_RIGHT)
		{
			teclasPressionadas ^= Teclado.TECLA_DIREITA;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_LEFT)
		{ 
			teclasPressionadas ^= Teclado.TECLA_ESQUERDA;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_UP)
		{
			teclasPressionadas ^= Teclado.TECLA_CIMA;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_DOWN)
		{
			teclasPressionadas ^= Teclado.TECLA_BAIXO;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_Z)
		{
			teclasPressionadas ^= Teclado.TECLA_PULO;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_X)
		{
			teclasPressionadas ^= Teclado.TECLA_SOCO;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
		{
			teclasPressionadas ^= Teclado.TECLA_ESCAPE;
		}
	}
	
    public void actionPerformed(ActionEvent a)
    {   
    	if(a.getSource() == timerDoJogo)
    	{
    		atualizarJogo();
    		repaint();
    	}
    }
    
    /**
     * M�todo que constr�i a primeira tela de est�ria. Implementado devido a
     * falta de tempo h�bil para o desenvolvimento de um leitor de defini��es
     * como o FaseCreator e AniCreator.
     * @return A primeira tela de est�ria.
     */
    public TelaDeEstoria construirPrimeiraTelaDeEstoria()
    {    	
    	Vector temp1 = new Vector();
		temp1.add(new Cenario(GameDirectories.GFX_DIR + File.separator + "misc" + File.separator + "cutscene1_a.gif"));
		temp1.add(new Cenario(GameDirectories.GFX_DIR + File.separator + "misc" + File.separator + "cutscene1_b.gif"));
		temp1.add(new Cenario(GameDirectories.GFX_DIR + File.separator + "misc" + File.separator + "cutscene_void.gif"));
		temp1.add(new Cenario(GameDirectories.GFX_DIR + File.separator + "misc" + File.separator + "cutscene1_c.gif"));
		temp1.add(new Cenario(GameDirectories.GFX_DIR + File.separator + "misc" + File.separator + "cutscene1_d.gif"));
		temp1.add(new Cenario(GameDirectories.GFX_DIR + File.separator + "misc" + File.separator + "cutscene_void.gif"));
		
		Vector temp2 = new Vector();
		temp2.add(new MensagemDeTexto("Em uma noite tranq�ila, Walfredo dorme.", "Arial", 12,
	                                  MensagemDeTexto.COR_BRANCA, 200, 10, 220));
		temp2.add(new MensagemDeTexto("Mas quem estaria prestes a pegar o cinto?", "Arial",12,
	                                  MensagemDeTexto.COR_BRANCA, 200, 10, 220));
		temp2.add(new MensagemDeTexto("Na manh� seguinte...", "Arial", 12,
	                                  MensagemDeTexto.COR_BRANCA, 200, 10, 220));
		temp2.add(new MensagemDeTexto("Meu cinto!!!!", "Arial", 12,
	                                  MensagemDeTexto.COR_BRANCA, 200, 10, 220));
		temp2.add(new MensagemDeTexto("Malditoooooos!!!!", "Arial", 12,
	                                  MensagemDeTexto.COR_BRANCA, 200, 10, 220));
		temp2.add(new MensagemDeTexto("E assim come�am...", "Arial", 12,
	                                  MensagemDeTexto.COR_BRANCA, 200, 10, 220));
	                                  
	    return(new TelaDeEstoria(temp1, temp2, "audio" + File.separator + "estoria.mid"));
    }
    
    /**
     * M�todo que constr�i a segunda tela de est�ria. Implementado devido a
     * falta de tempo h�bil para o desenvolvimento de um leitor de defini��es
     * como o FaseCreator e AniCreator.
     * @return A segunda tela de est�ria.
     */
    public TelaDeEstoria construirSegundaTelaDeEstoria()
    {    	
    	Vector temp1 = new Vector();
		temp1.add(new Cenario(GameDirectories.GFX_DIR + File.separator + "misc" + File.separator + "cutscene2_a.gif"));
		temp1.add(new Cenario(GameDirectories.GFX_DIR + File.separator + "misc" + File.separator + "cutscene2_a.gif"));
		temp1.add(new Cenario(GameDirectories.GFX_DIR + File.separator + "misc" + File.separator + "cutscene2_b.gif"));
		temp1.add(new Cenario(GameDirectories.GFX_DIR + File.separator + "misc" + File.separator + "cutscene2_b.gif"));
		temp1.add(new Cenario(GameDirectories.GFX_DIR + File.separator + "misc" + File.separator + "cutscene_void.gif"));
		
		Vector temp2 = new Vector();
		temp2.add(new MensagemDeTexto("Ok, voc�s perderam.", "Arial", 12,
	                                  MensagemDeTexto.COR_BRANCA, 200, 10, 220));
		temp2.add(new MensagemDeTexto("A vida � um trade-of, ent�o devolvam meu cinto!",
		                              "Arial",12,
	                                  MensagemDeTexto.COR_BRANCA, 200, 10, 220));
		temp2.add(new MensagemDeTexto("N�o t�o r�pido, Walfredo...", "Arial", 12,
	                                  MensagemDeTexto.COR_BRANCA, 200, 10, 220));
		temp2.add(new MensagemDeTexto("...Antes disso, temos assuntos a tratar.",
		                              "Arial", 12,
	                                  MensagemDeTexto.COR_BRANCA, 200, 10, 220));
		temp2.add(new MensagemDeTexto("TO BE CONTINUED...?", "Arial", 14,
	                                  MensagemDeTexto.COR_BRANCA, 200, 10, 220));
	                                  
	    return(new TelaDeEstoria(temp1, temp2, "audio" + File.separator + "estoria2.mid"));
    }
}
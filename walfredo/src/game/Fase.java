
package game;
import java.io.File;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JOptionPane;

/**
 *	Classe que implementa uma fase do jogo.
 *
 * @author Paolo Victor, Helder Fernando, �lvaro Magnum, Fabr�cio Gutemberg,
 *         Dalton C�zane.
 * @version 1.0
 */
public class Fase implements Comparator, ComunicavelComIG
{
	/**
	 * Constantes que define quanto tempo se passa antes de se sair da fase
	 * em caso de morte ou conclus�o.
	 */
	private final int SAIR_DA_FASE = 400;
	
	/**
	 * Constante que define que o status da fase � que ela est� continuando.
	 */
	public final static int FASE_CONTINUA  = 0;
	
	/**
	 * Constante que define que o status da fase � que ela foi conclu�da.
	 */
	public final static int FASE_CONCLUIDA = 1;
	
	/**
	 * Constante que define que o status da fase � que o jogador morreu.
	 */
	public final static int JOGADOR_MORREU = 2;
		
	/**
	 * O jogador. Ele se encontra em um objeto em separado para facilitar
	 * a manipula��o dos objetos.
	 */
	private Jogador jogador;
	
	/**
	 * O score da fase.
	 */
	private int scoreFase;
	
	/**
	 * O score total do jogo.
	 */
	private int scoreDoJogo;
	
	/**
	 * O cen�rio da fase.
	 */
	private Cenario cenario;
		
	/**
	 * Os inimigos da fase
	 */
	private Vector inimigosDaFase;
	
	/**
	 * Os defuntos dos inimigos ou do jogador - se ele for incompetente o suficiente.
	 */
	private Vector defuntos;
	
	/**
	 * As mensagens de texto
	 */
	private Vector mensagensDeTexto;
	
	/**
	 * A barra de energia inimiga que est� sendo mostrada
	 */
	private BarraDeEnergia barraInimiga;
	
	/**
	 * O tempo pelo qual a barra inimiga ser mostrada
	 */
	private int tempoDaBarraInimiga;
	
	/**
	 * Vari�vel que define o quanto o jogador pode andar pelo cen�rio
	 */
	private int cenarioLivre;
	
	/**
	 * O sistema de eventos da fase
	 */
	private SistemaDeEventos sistemaDeEventos;
	
	/**
	 * Contador que diz se a fase deve ser reiniciada
	 */
	private int contadorDeReinicio;
	
	/**
	 * A jukebox da fase
	 */
	private Jukebox musicas;
	
	/** 
	 * M�todo construtor de classe 
	 * @param arquivoDeEventos O nome do arquivo de eventos da fase.
	 * @param scoreDoJogo O score do jogo.
	 * @param nomeDaMusica Nome da m�sica que toca na fase.
	 */
	public Fase(String arquivoDeEventos, int scoreDoJogo, String nomeDaMusica)
	{	
		// Inicializando vari�veis
		barraInimiga        = null;	
		tempoDaBarraInimiga = 0;	
		cenarioLivre        = 0;	
		contadorDeReinicio  = 0;
		scoreFase           = 0;
		this.scoreDoJogo    = scoreDoJogo;
	
		// Inicializando os objetos de jogo e as mensagens
		mensagensDeTexto = new Vector();
		inimigosDaFase   = new Vector();
		defuntos         = new Vector();
		
		// Criando a jukebox, adicionando as faixas e tocando a m�sica da fase
		musicas = new Jukebox();
		
		try
		{
			musicas.adicionarFaixa(nomeDaMusica);
			musicas.adicionarFaixa(GameDirectories.AUDIO_DIR + File.separator + "chefe.mid");
			musicas.tocarFaixa(0, true);
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, "Erro com a Jukebox:\n" + e);
			System.exit(1);
		}
		
		// Criando o sistema de eventos
		sistemaDeEventos = new SistemaDeEventos(arquivoDeEventos);
		
		// Criando o cen�rio
		cenario = new Cenario(GameDirectories.GFX_DIR + File.separator + "misc" + File.separator + "bg0.gif");
		
		// Criando o jogador
		jogador = new Jogador(30, // posi��o x
		                      120, // posi��o y
		                      60, // largura
		                      70, // altura
		                      200, // tempo de �cio
		                      new BarraDeEnergia(GameDirectories.GFX_DIR + File.separator + "misc" + File.separator + "rostoWalfredo.gif", // rosto
		                                         "Walfredo", // nome
		                                         100, // energia
		                                         5,// posi��o x
		                                         5)); // posi��o y
		jogador.carregarAnimacoes(GameDirectories.GFX_DIR + File.separator + "walfredo.ani");
	}
	
	/**
	 * M�todo que atualiza a fase.
	 * @param teclasPressionadas M�scara de bits com as teclas pressionadas pelo jogador.
	 */
	public void atualizarFase(byte teclasPressionadas)
	{	
		Iterator     iteradorInimigos;
		Iterator     iteradorDefuntos;
		Inimigo      bully;
		Defunto      cadaver;
		
		// Atualizando os eventos
		if(sistemaDeEventos.getEventoAtual() != null && cenarioLivre == 0)
		{
			// Pegando poss�veis exce��es. getAtributoInteiro joga uma exce��o
			// se o atributo desejado n�o � um inteiro v�lido.
			try
			{
				switch(sistemaDeEventos.getEventoAtual().getTipo())
				{
					case Evento.ABRIR_CENARIO: // evento: abrir cen�rio
						cenarioLivre = sistemaDeEventos.getEventoAtual().getAtributoInteiro(0);
						sistemaDeEventos.lerProximoEvento();
						break;
					case Evento.CRIAR_INIMIGO: // evento: criar inimigo
						inimigosDaFase.add(
							criarInimigo(
						    sistemaDeEventos.getEventoAtual().getAtributoInteiro(0),//tipo
							sistemaDeEventos.getEventoAtual().getAtributoInteiro(1),// pos X
						    sistemaDeEventos.getEventoAtual().getAtributoInteiro(2)));// pos Y
						sistemaDeEventos.lerProximoEvento();
						break;
					case Evento.ESPERAR_MORTE: // evento: esperar morte
						if(inimigosDaFase.isEmpty())
						{
							sistemaDeEventos.lerProximoEvento();
						}
						break;
					case Evento.MOSTRAR_MENSAGEM: // evento: mostrar mensagem de texto
						mensagensDeTexto.add(
							 new MensagemDeTexto(
							 sistemaDeEventos.getEventoAtual().getAtributoString(0), // msg
							 sistemaDeEventos.getEventoAtual().getAtributoString(1), // fonte
							 sistemaDeEventos.getEventoAtual().getAtributoInteiro(2), // tam.
							 sistemaDeEventos.getEventoAtual().getAtributoColor(3), // cor
							 sistemaDeEventos.getEventoAtual().getAtributoInteiro(4), // pos x
							 sistemaDeEventos.getEventoAtual().getAtributoInteiro(5), // pos y
							 sistemaDeEventos.getEventoAtual().getAtributoInteiro(6)));// tempo
	                    
	                    sistemaDeEventos.lerProximoEvento();
	                    break;
	                case Evento.PASSAR_DE_FASE: // evento: passar de fase
						// Inicializando o contador de rein�cio
						contadorDeReinicio = 1;
						sistemaDeEventos.lerProximoEvento();
	                    break;
					case Evento.MUSICA_CHEFE: // evento: tocar m�sica do chefe.
						musicas.pararFaixa();
						musicas.tocarFaixa(1, true);
						sistemaDeEventos.lerProximoEvento();
	                    break;
	                default:
					    break;
				}
			}
			catch(Exception e)
			{
				JOptionPane.showMessageDialog(null, "Erro com o sistema de eventos. Abortando." + e);
				System.exit(1);
			}
		}		
		
	    // Atualizando a posicao no cen�rio dos objetos
		if(cenarioLivre > 0 && jogador.getPosicaoX() > 160)
		{
			iteradorInimigos = inimigosDaFase.iterator();
			iteradorDefuntos = defuntos.iterator();			
			
			// Movendo o cen�rio
			cenario.moverCenario(2);
			cenarioLivre -= 2;
			
			// movendo o jogador e os outros objetos para
			// evitar a sensa��o de que o jogador est�
			// se movendo mais r�pido
			jogador.moverObjeto(-2, 0);
			
			while(iteradorInimigos.hasNext())
			{
				bully = (Inimigo)iteradorInimigos.next();
				bully.moverObjeto(-2, 0);
			}
			
			while(iteradorDefuntos.hasNext())
			{
				cadaver = (Defunto)iteradorDefuntos.next();
				cadaver.moverObjeto(-2, 0);
			}
		}
	
	    // Atualizando o jogador, se ele n�o estiver morto.	    
	    if(!jogador.estaMorto())
	    {
	    	// Se os comandos n�o est�o sendo ignorados, ler os comandos do jogador
	    	if(!jogador.comandosIgnorados())
	    	{
		    	jogador.lerComandos(teclasPressionadas);
		    }
		    
		    // O m�todo atualizar status recebe o vector de inimigos para
			// tratar colis�es com inimigos.
			jogador.atualizarStatus(inimigosDaFase);	    
			jogador.atualizarPosicao();
			jogador.atualizarAnimacao();
		}
		// Se o jogador est� morto, criar o defunto e come�ar o contador para
		// reiniciar a fase.
		else if(contadorDeReinicio == 0)		                                 
		{
			// Mostrando a mensagem de Game Over
			mensagensDeTexto.add(new MensagemDeTexto("GAME OVER", // mensagem
			                                         "Arial", // fonte
			                                         42, // tamanho
	                                                 MensagemDeTexto.COR_VERMELHA, // cor
	                                                 300, // posi��o X
	                                                 30, // posi��o Y
	                                                 110)); // tempo de dura��o
			
			// Adicionando o defunto
			Defunto umDefunto = new Defunto(jogador.getPosicaoX(), // posi��o x
			                                jogador.getPosicaoY(), // posi��o y
				                            jogador.getLargura(), // largura
				                            jogador.getAltura(), // altura
				                            jogador.getDirecao(), // dire��o
				                            Defunto.TEMPO_ZUMBI); // tempo que dura
				                                                  // na tela.
			umDefunto.carregarAnimacoes(GameDirectories.GFX_DIR + File.separator + "walfredo_defunto.ani");
			defuntos.add(umDefunto);			
			
			// Inicializando o contador de rein�cio
			contadorDeReinicio = 1;
		}
		
		// Atualizando o contador de reinicio de fase.
		if(contadorDeReinicio > 0)
		{
			contadorDeReinicio++;
		}
		
		// Atualizando os inimigos
		iteradorInimigos = inimigosDaFase.iterator();		
		while(iteradorInimigos.hasNext())
		{
			bully = (Inimigo)iteradorInimigos.next();
			bully.atualizarPosicao();
			bully.atualizarAnimacao();
			bully.atualizarStatus();
			
			// se o nimigo est� morto...
			if(bully.estaMorto())
			{
				// Adicionando o score
				scoreFase += (bully.getTipo() + 1) * 250;
				
				// Adicionar o defunto
				Defunto umDefunto = new Defunto(bully.getPosicaoX(), // posi��o x
				                                bully.getPosicaoY(), // posicao y
				                                bully.getLargura(), // largura
				                                bully.getAltura(),  // altura
				                                bully.getDirecao(), // dire��o
				                                Defunto.TEMPO_INIMIGO); // tempo que dura
				                                                        // na tela				
				switch(bully.getTipo())
				{
					case Inimigo.BULLY1:
						umDefunto.carregarAnimacoes(GameDirectories.GFX_DIR + File.separator + "bully1_defunto.ani");
						break;
					case Inimigo.BULLY2:
						umDefunto.carregarAnimacoes(GameDirectories.GFX_DIR + File.separator + "bully2_defunto.ani");
						break;
					case Inimigo.BULLY3:
						umDefunto.carregarAnimacoes(GameDirectories.GFX_DIR + File.separator + "bully3_defunto.ani");
						break;
					case Inimigo.BULLY4:
						umDefunto.carregarAnimacoes(GameDirectories.GFX_DIR + File.separator + "bully4_defunto.ani");
						break;
				}
				
				defuntos.add(umDefunto);
				
				// Remover o inimigo
				iteradorInimigos.remove();
			}
			else if(bully.levouGolpe())
			{
				// Adicionando o score
				scoreFase += (bully.getTipo() + 1) * 10;
				
				// Se o inimigo levou um golpe, determinar se a sua 
				// barra de energia deve ser mostrada				
				barraInimiga = bully.getBarraDeEnergia();
				tempoDaBarraInimiga = 200;
				
				// E se o jogador acertou o inimigo, ele pode continuar o combo.
				jogador.proximoGolpeDoCombo();
			}
		}
				
		// Atualizando os defuntos
		iteradorDefuntos = defuntos.iterator();		
		while(iteradorDefuntos.hasNext())
		{
			cadaver = (Defunto)iteradorDefuntos.next();
			cadaver.atualizarPosicao();
			cadaver.atualizarAnimacao();
			cadaver.atualizarStatus();	
			
			// Removendo o defunto
			if(cadaver.deveSerApagado())
			{
				iteradorDefuntos.remove();
			}
		}
		
		// Atualizando a barra de energia inimiga
		if(tempoDaBarraInimiga > 0)
		{
			tempoDaBarraInimiga--;
		}
		
		// Atualizando as mensagens
		Iterator iterador;
		iterador = mensagensDeTexto.iterator();	
		MensagemDeTexto mensagem;
		
		while(iterador.hasNext())
		{			
			mensagem = (MensagemDeTexto)iterador.next();			
			mensagem.atualizarMensagem();
			
			if(mensagem.deveSerApagada())
			{
				iterador.remove();
			}			
		}
	}
		
	/**
	 * Adiciona um inimigo � fase
	 * @param tipo O tipo do inimigo.
	 * @param posicaoX A posi��o X do inimigo.
	 * @param posicaoY A posi��o Y do inimigo.
	 */
	private Inimigo criarInimigo(int tipo, int posicaoX, int posicaoY)
	{
		Inimigo inimigo = null;
		
		switch(tipo)
		{
			case Inimigo.BULLY1:
				inimigo = new Inimigo(posicaoX, posicaoY,
				                      60,  // largura
				                      70,  // altura
				                      100, // tempo de �cio inicial
				                      jogador, // alvo
		                              new BarraDeEnergia(GameDirectories.GFX_DIR + File.separator + "misc" + File.separator + "bully1_rosto.gif", //rosto
		                                                 "Graft", // nome
		                                                  Inimigo.BULLY1_ENERGIA, // energia
		                                                  5,  // posi��o X
		                                                  200), // posi��o Y
		                              Inimigo.BULLY1, // tipo
		                              Inimigo.BULLY1_FORCA, // for�a
		                              Inimigo.BULLY1_VELOCIDADE); // velocidade
				inimigo.carregarAnimacoes(GameDirectories.GFX_DIR + File.separator + "bully1.ani");
			break;
			case Inimigo.BULLY2:
				inimigo = new Inimigo(posicaoX, posicaoY,
				                      60,  // largura
				                      70,  // altura
				                      100, // tempo de �cio inicial
				                      jogador, // alvo
		                              new BarraDeEnergia(GameDirectories.GFX_DIR + File.separator + "misc" + File.separator + "bully2_rosto.gif", //rosto
		                                                 "Indigo", // nome
		                                                  Inimigo.BULLY2_ENERGIA, // energia
		                                                  5,  // posi��o X
		                                                  200), // posi��o Y
		                              Inimigo.BULLY2, // tipo
		                              Inimigo.BULLY2_FORCA, // for�a
		                              Inimigo.BULLY2_VELOCIDADE); // velocidade
				inimigo.carregarAnimacoes(GameDirectories.GFX_DIR + File.separator + "bully2.ani");
			break;
			case Inimigo.BULLY3:
								inimigo = new Inimigo(posicaoX, posicaoY,
				                      60,  // largura
				                      70,  // altura
				                      100, // tempo de �cio inicial
				                      jogador, // alvo
		                              new BarraDeEnergia(GameDirectories.GFX_DIR + File.separator + "misc" + File.separator + "bully3_rosto.gif", //rosto
		                                                 "Blaze", // nome
		                                                  Inimigo.BULLY3_ENERGIA, // energia
		                                                  5,  // posi��o X
		                                                  200), // posi��o Y
		                              Inimigo.BULLY3, // tipo
		                              Inimigo.BULLY3_FORCA, // for�a
		                              Inimigo.BULLY3_VELOCIDADE); // velocidade
				inimigo.carregarAnimacoes(GameDirectories.GFX_DIR + File.separator + "bully3.ani");
			break;
			case Inimigo.BULLY4:
								inimigo = new Inimigo(posicaoX, posicaoY,
				                      60,  // largura
				                      70,  // altura
				                      100, // tempo de �cio inicial
				                      jogador, // alvo
		                              new BarraDeEnergia(GameDirectories.GFX_DIR + File.separator + "misc" + File.separator + "bully4_rosto.gif", //rosto
		                                                 "The Boss", // nome
		                                                  Inimigo.BULLY4_ENERGIA, // energia
		                                                  5,  // posi��o X
		                                                  200), // posi��o Y
		                              Inimigo.BULLY4, // tipo
		                              Inimigo.BULLY4_FORCA, // for�a
		                              Inimigo.BULLY4_VELOCIDADE); // velocidade
				inimigo.carregarAnimacoes(GameDirectories.GFX_DIR + File.separator + "bully4.ani");
			break;
		}
		
		return(inimigo);
	}
	
	/**
	 * Retorna o status da fase. Se ela terminou, deve ser reiniciada, etc. 
	 * @return O status da fase.
	 */
	public int statusDaFase()
	{
		if(contadorDeReinicio > SAIR_DA_FASE)
		{
			// Se vamos sair da fase, temos que parar a m�sica.
			musicas.pararFaixa();
			
			if(!jogador.estaMorto())
			{
				return(FASE_CONCLUIDA);
			}
			else
			{
				return(JOGADOR_MORREU);
			}			
		}
		else
		{
			return(FASE_CONTINUA);
		}		
	}
	
	/**
	 * Retorna o score da fase, que ser� adicionado ao score geral do
	 * jogo no final da fase.
	 * @return O score da fase.
	 */
	public int getScoreDaFase()
	{
		return(scoreFase);	
	}
	
	/**
	 * Retorna os objetos da fase. Usado pela classe
	 * <code>InterfaceGraficaDaFase</code>.
	 *
	 * @return Os objetos que devem ser mostrados pela interface gr�fica.
	 */
	public Vector getObjetos()
	{
		// Adicionando todos os objetos � um �nico vector
		Vector todosOsObjetos = new Vector();
		
		// O jogador morto n�o deve ser mostrado
		if(!jogador.estaMorto())
		{
			todosOsObjetos.add(jogador);
		}
		
		if(inimigosDaFase.size() > 0)
		{
			todosOsObjetos.addAll(inimigosDaFase);
		}
		
		if(defuntos.size() > 0)
		{
			todosOsObjetos.addAll(defuntos);
		}
		
		// Transformando o vector em array, e a ordenando
		Object[] objArray = todosOsObjetos.toArray();
		Arrays.sort(objArray, this);
		
		// Limpando o vector para a adi��o dos elementos
		// ordenados.
		todosOsObjetos.removeAllElements();
		
		// Adicionando o cen�rio
		todosOsObjetos.add(cenario);
		
		// Adicionando os objetos de jogo
		for(int k = 0; k < objArray.length; k++)
		{
			todosOsObjetos.add(objArray[k]);
		}
	
		// Adicionando a barra de energia do jogador
		todosOsObjetos.add(jogador.getBarraDeEnergia());
		
		// Adicionando a barra de energia do inimigo, se necess�rio
		if(tempoDaBarraInimiga > 0 && barraInimiga != null)
		{
			todosOsObjetos.add(barraInimiga);
		}
		
		// Adicionando as mensagens de texto
		todosOsObjetos.addAll(mensagensDeTexto);
		
		// Adicionando o score
		todosOsObjetos.add(new MensagemDeTexto("Score: " + (scoreDoJogo + scoreFase),// msg
		                                       "Arial", // fonte
		                                       16, // tamanho
	                                           MensagemDeTexto.COR_BRANCA, // cor
	                                           MensagemDeTexto.MENSAGEM_ETERNA, // tempo
	                                           170, // posi��o X
	                                           25)); // posi��o Y
	
		// Retornando o vector
		return(todosOsObjetos);
	}
	
	/** Compara dois ObjetosDeJogo pela posi��o Y. Usado para a ordena��o
	 *  dos objetos de jogo.
	 */
	public int compare(Object a, Object b)
	{
		ObjetoDeJogo objA = (ObjetoDeJogo)a;
		ObjetoDeJogo objB = (ObjetoDeJogo)b;
		
		return(objA.posicaoY - objB.posicaoY + (objA.altura - objB.altura));
	}
}
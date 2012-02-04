package game;

import java.util.Vector;

/**
 *
 * Classe que implementa um inimigo.<br><br>
 * 
 * Um inimigo � um objeto de jogo especial,
 * com um tratamento de eventos diferenciado
 * e que � controlado pelo jogo.
 *
 * @author  Paolo Victor, Helder Fernando, Alvaro Magnum, Dalton Cezane, Fabricio Gutemberg
 * @version 1.0
 */

public class Inimigo extends ObjetoDeJogo implements Desenhavel
{
	/**
	 * O tipo para o inimigo 1
	 */
	public static final int BULLY1       = 0;
	
	/**
	 * A for�a do inimigo 1
	 */
	public static final int BULLY1_FORCA = 10;
	
	/**
	 * A velocidade do inimigo 1
	 */
	public static final int BULLY1_VELOCIDADE = 1;
	
	/**
	 * A energia do inimigo 1
	 */
	public static final int BULLY1_ENERGIA = 40;
	
	/**
	 * O tipo para o inimigo 2
	 */
	public static final int BULLY2       = 1;
	
	/** A for�a do inimigo 2 */
	public static final int BULLY2_FORCA = 15;
	
	/**
	 * A velocidade do inimigo 2
	 */
	public static final int BULLY2_VELOCIDADE = 1;
	
	/**
	 * A energia do inimigo 2
	 */
	public static final int BULLY2_ENERGIA = 75;
	
	/**
	 * O tipo para o inimigo 3
	 */
	public static final int BULLY3       = 2;
	
	/**
	 * A for�a do inimigo 3
	 */
	public static final int BULLY3_FORCA = 20;
	
	/**
	 * A velocidade do inimigo 3
	 */
	public static final int BULLY3_VELOCIDADE = 1;	
	
	/**
	 * A energia do inimigo 3
	 */
	public static final int BULLY3_ENERGIA = 120;
	
	/**
	 * O tipo para o inimigo 4
	 */
	public static final int BULLY4       = 3;
	
	/**
	 * A for�a do inimigo 4
	 */
	public static final int BULLY4_FORCA = 25;
	
	/**
	 * A velocidade do inimigo 4
	 */
	public static final int BULLY4_VELOCIDADE = 2;	
	
	/**
	 * A energia do inimigo 4
	 */
	public static final int BULLY4_ENERGIA = 200;	
	 
	/**
	 * Constante que define um quadro critico para soco. Um quadro critico indica
	 * qual frame de uma anima��o de ataque � o que deve ser considerado na detec��o 
	 * de colis�es. 
	 */
	public static final int FRAME_CRITICO_SOCO = 3;
	
	/**
	 * Constante que define um quadro critico para voadora. Um quadro critico indica
	 * qual frame de uma anima��o de ataque � o que deve ser considerado na detec��o 
	 * de colis�es. 
	 */
	public static final int FRAME_CRITICO_VOADORA = 1;
	
	/**
	 * Constante que define o tempo que o inimigo esta machucado
	 */
	private final int TEMPO_DE_MACHUCADO = 20;
	
	/**
	 * Constante que define o tempo que o inimigo fica inconsciente
	 */
	private final int TEMPO_DE_INCONSCIENCIA = 60;
			
	/** 
	 * A for�a do inimigo. Ela influi no c�lculo do dano dos ataques
	 */
	private int forca;
	
	/**
	 * A velocidade com a qual o inimigo pode mover-se.
	 */
	private int velocidade;
	
	/** 
	 * A barra de energia do inimigo 
	 */
	private BarraDeEnergia barraDeEnergia;
	
	/** 
	 * Tempo pelo qual as a��es do inimigo s�o ignoradas 
	 */
	private int tempoDeOcio;
	
	/** 
	 * Quantidade de dano que o inimigo tomou recentemente.
	 * usado para definir inconsci�ncia 
	 */
	private int danoRecente;
	
	/** 
	 * A lista de a��es que o inimigo deve executar 
	 */
	private Vector acoesSendoExecutadas;
	
	/** 
	 *A a��o que est� sendo executada pelo inimigo 
	 */
	Acao acaoAtual;		
	
	/** 
	 * O c�rebro do inimigo 
	 */
	private Cerebro cerebro;
	
	/** 
	 * Define se o inimigo est� machucado 
	 */
	private boolean estaMachucado = false;
	
	/** 
	 * Define se o inimigo apanhou do jogador 
	 */
	private boolean apanheiDoJogador = false;
	
	/** 
	 * O alvo do inimigo. O padr�o � o jogador
	 */
	private Jogador alvo;
	
	/** 
	 *O tipo do inimigo 
	 */
	private int tipo;
	
	/** Construtor padr�o do Jogador
	 *  @param posicaoX A posicao X inicial do inimigo.
	 *  @param posicaoY A posicao Y inicial do inimigo.
	 *  @param largura A largura do inimigo.
	 *  @param altura A altura do inimigo.
	 *  @param barraDeEnergia A barra de energia do inimigo
	 *  @param alvo O alvo dos ataques do inimigo
	 */
	public Inimigo(int posicaoX, int posicaoY, int largura, int altura,
	               int tempoDeOcio, Jogador alvo, BarraDeEnergia barraDeEnergia,
	               int tipo, int forca, int velocidade)
	{
		super(posicaoX, posicaoY, largura, altura, true);
		
		this.barraDeEnergia = barraDeEnergia;		
		this.tempoDeOcio    = tempoDeOcio;
		this.alvo           = alvo;
		this.tipo           = tipo;
		this.forca          = forca;
		this.velocidade     = velocidade;
		
		acoesSendoExecutadas = null;
		acaoAtual            = null;
		direcaoDaAnimacao    = 1; // os inimigos aparecem olhando para o jogador.
		
		cerebro = new Cerebro(alvo, this);
	}
	
	/** M�todo que retorna o tipo do inimigo
	 *  @return O tipo do inimigo.
	 */
	public int getTipo()
	{
		return(tipo);
	}
	
	/** M�todo que retorna a barra de energia do inimigo.
	 *  @return A barra de energia do inimigo.
	 */
	public BarraDeEnergia getBarraDeEnergia()
	{
		return(barraDeEnergia);
	}
	
	/** M�todo que atualiza o status do inimigo */
	public void atualizarStatus()
	{
		// Tratando colis�es com o ataque do alvo
    	tratarColisoesComAtaquesDoAlvo();    	
		
		if(tempoDeOcio > 0)
		{
			tempoDeOcio--;
		}
		
		if(danoRecente > 0)
		{
			danoRecente -= 1;
		}
		
		if(estaMachucado && tempoDeOcio == 0)
    	{
    		estaMachucado = false;
    	}
    	
    	// Inconsci�ncia
    	if(danoRecente >= 13)
    	{
    		estaMachucado = true;
    		
    		danoRecente = 0;
	    	tempoDeOcio = TEMPO_DE_INCONSCIENCIA;
	    	
	    	if(posicaoX > alvo.getPosicaoX())
	    	{
	    		velocidadeX = 3;
	    	}
	    	else
	    	{
	    		velocidadeX = -3;
	    	}
	    	
    		velocidadeY = 0;
    		velocidadeZ = 3;
    		estaNoAr    = true;		
    		acaoAtual   = null;
    	}
	}
	
	/** M�todo que atualiza a anima��o do inimigo */
	public void atualizarAnimacao()
	{
		super.atualizarAnimacao();
		
		// Mostrando que o inimigo est� machucado
    	if(estaMachucado)
    	{
    		setAnimacaoAtual("ugh", false);
    	}
    	
    	// Atualizando a dire��o da anima��o
    	if(!estaNoAr &&
    	   !estaMachucado &&
    	   !getAnimacaoAtual().getNome().equals("soco") &&
    	   !getAnimacaoAtual().getNome().equals("voadora"))
    	{
    		if(posicaoX + largura/2 < alvo.getPosicaoX() + alvo.getLargura()/2)
    		{
    			direcaoDaAnimacao = 0;
	    	}
    		else
    		{
	    		direcaoDaAnimacao = 1;
    		}
    	}
    	
    	// Atualizando a anima��o de pulo
    	if(estaNoAr && !estaMachucado && !getAnimacaoAtual().getNome().equals("voadora"))
    	{
    		setAnimacaoAtual("pulo");
    	}
    	
    	// Atualizando a anima��o de parado
    	if(tempoDeOcio > 0 && !estaNoAr
    	   && !getAnimacaoAtual().getNome().equals("soco")
    	   && !estaMachucado)
    	{
    		setAnimacaoAtual("parado", false);
    	}
	}
	
	/** M�todo que atualiza a posi��o do inimigo */
    public void atualizarPosicao()
    {   
    	super.atualizarPosicao();
    	
    	if(tempoDeOcio == 0)
    	{
    		executarAcao();    		
    	}
    }

	/** M�todo que controla o inimigo de acordo com as informa��es
	 *  armazenadas do vector AcoesSendoExecutadas
	 */
	private void executarAcao()
	{		
		// Definindo se todas as a��es j� foram executadas
		if(acoesSendoExecutadas != null && acoesSendoExecutadas.size() == 0)
		{
			acoesSendoExecutadas = null;
		}
		
		// Pensando novas a��es
		if(acoesSendoExecutadas == null && !estaNoAr)
		{
			acoesSendoExecutadas = cerebro.pensar();
		}
		
		// Definindo a a��o atual
		if(acaoAtual == null && acoesSendoExecutadas != null)
		{
			acaoAtual = (Acao)acoesSendoExecutadas.get(0);
			acoesSendoExecutadas.remove(0);
		}
		
		// Executando a a��o
		if(acaoAtual != null && !estaMachucado)
		{
			// Alguns inimigos t�m a probabilidade de escaparem de voadoras e ent�o
			// contra-atacarem. Essa a��o tem uma prioridade maior sobre as outras.
			if((tipo == BULLY2 || tipo == BULLY3)
			   && alvo.getAnimacaoAtual().getNome().equals("voadora")
			   && (int)(Math.random() * 3) <= 1
			   && Math.abs(alvo.getPosicaoX() - this.getPosicaoX()) < this.largura + 10
			   && !estaNoAr)
			{
				acoesSendoExecutadas = cerebro.pensarEvasivo();
				acaoAtual = null;
			}
			// Movendo o inimigo
			else if(acaoAtual.getIndicador() == Acao.MOVER && !estaNoAr)
			{
				int posX = acaoAtual.getAtributo(0);
				int posY = acaoAtual.getAtributo(1);
				
				if(posY + altura > 230)
				{
					posY = 230 - altura;
				}
						
				if(posicaoX > posX - velocidade &&
				   posicaoX < posX + velocidade)
				{
					posicaoX = posX;
					velocidadeX = 0;
				}
				 
				if(posicaoY > posY - velocidade &&
				   posicaoY < posY + velocidade)
				{
					posicaoY = posY;
					velocidadeY = 0;
				}
				
				if(posicaoX == posX && posicaoY == posY)
				{
					acaoAtual = null;
				}				   
				
				if(posicaoX < posX)
				{
					velocidadeX = velocidade;
				}
				
				if(posicaoX > posX)
				{
					velocidadeX = -velocidade;
				}
				
				if(posicaoY < posY)
				{
					velocidadeY = velocidade;
				}
				
				if(posicaoY > posY)
				{
					velocidadeY = -velocidade;
				}
				
				
				setAnimacaoAtual("andando", false);
			}
			// Fazendo o inimigo esperar
			else if(acaoAtual.getIndicador() == Acao.ESPERAR)
			{
				tempoDeOcio = acaoAtual.getAtributo(0);
				acaoAtual = null;
			}
			// Fazendo o inimigo pular
			else if(acaoAtual.getIndicador() == Acao.PULAR && !estaNoAr)
			{			
				estaNoAr = true;
			
				velocidadeX = acaoAtual.getAtributo(0);
				velocidadeY = acaoAtual.getAtributo(1);
				velocidadeZ = acaoAtual.getAtributo(2);
				
				acaoAtual = null;			
			}
			else if(acaoAtual.getIndicador() == Acao.ATACAR)
			{
				if(!estaNoAr)
				{
					if(this.colideCom(alvo))
					{
						setAnimacaoAtual("soco");
						tempoDeOcio = 50;
					}
				}
				else
				{
					setAnimacaoAtual("voadora");
					tempoDeOcio = 100;
				}
				acaoAtual = null;
			}
			else
			{
				acaoAtual = null;
			}
		} // fim do if(acaoAtual != null)
				
    	return;
	}
	
	/** 
	 *  M�todo que detecta e trata colis�es com os ataques do jogador
	 */
	private void tratarColisoesComAtaquesDoAlvo()
	{
		// Detectando colis�es com o jogador.
		// Se o inimigo est� colidindo com o alvo, n�o est� machucado e o quadro
		// de anima��o do alvo � o quadro cr�tico (no. 3), partir para a segunda
		// condi��o
    	if(alvo.estaAtacando() && this.colideCom(alvo) && alvo.getQuadroAtual() == 3
    	   && !estaMachucado)
    	{    		
    		// O alvo n�o pode atacar o inimigo de costas, ent�o s� faz sentido
    		// detectar a colis�o se o alvo estiver de frente para o inimigo.
    		if((alvo.getDirecao() == 0 &&
    		    posicaoX + largura/2 > alvo.getPosicaoX() + alvo.getLargura()/2)
    		   || (alvo.getDirecao() == 1 &&
    		    posicaoX + largura/2 <= alvo.getPosicaoX() + alvo.getLargura()/2))
    		{   	
    			// Se o inimigo apanhou, ele deve pensar em alguma forma
    			// de defesa ou contra-ataque
    			if(!estaNoAr)
    			{
    				acoesSendoExecutadas = cerebro.pensarRapidamente();
    			}
    			else
    			{
    				acoesSendoExecutadas = null;
    			}
    		
    			// Aplicando as penalidades ao inimigo
    			if(alvo.getDirecao() == 0)
    			{
    				direcaoDaAnimacao = 1;
    			}
    			else
    			{
    				direcaoDaAnimacao = 0;
    			}
    			
    			apanheiDoJogador = true;
    			estaMachucado = true;
	    		tempoDeOcio = TEMPO_DE_MACHUCADO;
    			velocidadeX = 0;
    			velocidadeY = 0;
    			acaoAtual = null;
    		
    			// Aplicando o dano ao inimigo
    			// Soco
    			if(alvo.getAnimacaoAtual().getNome().equals("soco"))
    			{
    				barraDeEnergia.removerEnergia(15);
    				danoRecente += 5;
    			}
    		
    			// Chute
    			if(alvo.getAnimacaoAtual().getNome().equals("chute"))
    			{
    				barraDeEnergia.removerEnergia(20);
    				danoRecente += 10;
    			}
    		
    			// Cabe�ada
    			if(alvo.getAnimacaoAtual().getNome().equals("cabecada"))
    			{
    				barraDeEnergia.removerEnergia(25);
    				danoRecente += 15;
    			}
    			
    		    // Voadora
    			if(alvo.getAnimacaoAtual().getNome().equals("voadora"))
    			{
    				barraDeEnergia.removerEnergia(15);
    				danoRecente += 20;
    			}
    		} // fim do if da posi��o
    	}// fim do if da colis�o
	}
	
	/** M�todo que retorna se o inimigo est� morto
	 *  @return Se o inimigo est� morto
	 */
	public boolean estaMorto()
	{
		return(barraDeEnergia.getEnergiaAtual() <= 0);
	}
	
	/** M�todo que retorna se o inimigo levou um golpe
	 *  @return Se o inimigo acabou de levar um golpe
	 */
	public boolean levouGolpe()
	{
		if(apanheiDoJogador)
		{
			apanheiDoJogador = false;
			return(true);
		}
		else
		{
			return(false);
		}
	}
	
	/** M�todo que calcula o dano causado por um golpe
	 *  @return O dano causado.
	 */
	public int calcularDanoDoGolpe()
	{
		if(getAnimacaoAtual().getNome().equals("soco"))
		{
			return(forca);
		}
		
		if(getAnimacaoAtual().getNome().equals("voadora"))
		{
			return((int)(forca * 1.5));
		}
		
		// Se o ataque � indefinido, n�o causa dano.
		return(0);
	}
	
	/** M�todo que retorna se o inimigo est� atacando
	 *  @return Se o inimigo est� atacando
	 */
	public boolean estaAtacando()
	{
		return((getAnimacaoAtual().getNome().equals("soco") &&
		        getQuadroAtual() == FRAME_CRITICO_SOCO)
		        ||
		       (getAnimacaoAtual().getNome().equals("voadora") &&
		        getQuadroAtual() == FRAME_CRITICO_VOADORA));
	}
}
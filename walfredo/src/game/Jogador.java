package game;

import java.util.Iterator;
import java.util.Vector;

/**
 *
 * Classe que implementa um jogador.<br><br>
 * 
 * Um jogador � um objeto de jogo especial,
 * com um tratamento de eventos diferenciado
 * e que pode ser controlado pelo teclado.
 *
 * @author  Paolo Victor, Helder Fernando, Alvaro Magnum, Dalton Cezane, Fabricio Gutemberg
 * @version 1.0
 */

public class Jogador extends ObjetoDeJogo implements Desenhavel
{	
	/**
	 * Constante que define a hora das calcas 
	 */
	private final int HORA_DAS_CALCAS        = 300;
	
	/**
	 * Constante que define o tempo de machucado do jogador
	 */
	private final int TEMPO_DE_MACHUCADO     = 40;
	
	/**
	 * Constante que define o tempo de inconsciencia do jogador
	 */
	private final int TEMPO_DE_INCONSCIENCIA = 80;
	
	/**
	 * Constante que define a resistencia do jogador
	 */
	private final int RESISTENCIA_DO_JOGADOR = 20;
	
	/** 
	 * A barra de energia do jogador 
	 */
	private BarraDeEnergia barraDeEnergia;
	
	/** 
	 * Diz se o jogador est� machucado 
	 */
	private boolean estaMachucado;
	
	/** 
	 * O dano recente sofrido pelo jogador 
	 */
	private int danoRecente;
	
	/** 
	 * Tempo pelo qual os controles do jogador s�o ignorados 
	 */
	private int tempoDeOcio;
	
	/** 
	 * Controle de combos 
	 */
	private int tempoDeCombo;
	
	/**
	 * Golpe atual do combo
	 */
	private int golpeAtualDoCombo;
	
	/** 
	 * O tempo pelo qual o jogador n�o moveu Walfredo. Usado para
	 * a anima��o das cal�as 
	 */
	private int tempoDasCalcas;
	
	/** Construtor padr�o do Jogador.
	 *  @param posicaoX A posicao X inicial do jogador.
	 *  @param posicaoY A posicao Y inicial do jogador.
	 *  @param largura A largura do jogador.
	 *  @param altura A altura do jogador.
	 *  @param tempoDeOcio Tempo pelo qual os controles do jogador s�o ignorados.
	 *  @param barraDeEnergia A barra de energia do jogador.
	 */
	public Jogador(int posicaoX, int posicaoY, int largura, int altura,
	               int tempoDeOcio, BarraDeEnergia barraDeEnergia)
	{
		super(posicaoX, posicaoY, largura, altura, true);
		this.barraDeEnergia = barraDeEnergia;
		this.tempoDeOcio    = tempoDeOcio;
		
		golpeAtualDoCombo = 0;
		tempoDeCombo      = 0;
		tempoDasCalcas    = 0;
		
		danoRecente   = 0;
		estaMachucado = false;
	}
	
	/** M�todo que retorna a barra de energia do jogador.
	 *  @return A barra de energia do jogador.
	 */
	public BarraDeEnergia getBarraDeEnergia()
	{
		return(barraDeEnergia);
	}
	
	/** M�todo que retorna se o controle deve ser ignorado 
	 *  @return Se o controle deve ser ignorado*/
	public boolean comandosIgnorados()
	{
		return(tempoDeOcio > 0);
	}
	
	/** M�todo que atualiza o status do jogador */
	public void atualizarStatus(Vector inimigos)
	{
		// Tratando colis�es
		Iterator i = inimigos.iterator();
		
		while(i.hasNext())
		{
			tratarColisoesComAtaquesDoInimigo((Inimigo)i.next());
		}
		
		// Caindo da voadora
		if(getAnimacaoAtual().getNome().equals("voadora") && !estaNoAr)
		{
			tempoDeOcio = 0;
		}
		
		// Saindo do �cio e zerando combo
		if(tempoDeOcio > 0)
		{
			tempoDeOcio--;
		}
		
		if(tempoDeCombo > 0)
		{
			tempoDeCombo--;
		}
		else if(tempoDeCombo == 0)
		{
			golpeAtualDoCombo = 0;
		}
		
		// Fazendo o jogador ficar inconsciente
		if(danoRecente > RESISTENCIA_DO_JOGADOR)
		{
			danoRecente = 0;			
			tempoDeOcio = TEMPO_DE_INCONSCIENCIA;
	    	
	    	if(direcaoDaAnimacao == 0)
	    	{
	    		velocidadeX = -3;
	    	}
	    	else
	    	{
	    		velocidadeX = 3;
	    	}
	    	
    		velocidadeY = 0;
    		velocidadeZ = 3;
    		estaNoAr    = true;
		}
		
		// Fazendo o jogador se recompor do machucado
		if(tempoDeOcio == 0 && estaMachucado)
		{
			estaMachucado = false;
		}
				
		// Tratando a anima��o das cal�as
		tempoDasCalcas++;
		
		if(tempoDasCalcas == HORA_DAS_CALCAS
		   && !getAnimacaoAtual().getNome().equals("calcas"))
		{
			setAnimacaoAtual("calcas");
		}
		
		if(tempoDasCalcas == HORA_DAS_CALCAS + 60)
		{
			tempoDasCalcas = 0;
			setAnimacaoAtual("parado");
		}
	}
	
	
	/** M�todo que atualiza a posi��o do jogador */
    public void atualizarPosicao()
    {
    	super.atualizarPosicao();
    	
    	// Mantendo o jogador na tela
    	if(posicaoX + largura > 320)
    	{
    		posicaoX = 320 - largura;
    	}
    	
    	if(posicaoX < 0)
    	{
    		posicaoX = 0;
    	}
    }
    
    /** M�todo que atualiza a anima��o do jogador */
    public void atualizarAnimacao()
    {    	
    	// Anima��o: machucado
		if(estaMachucado)
		{
			setAnimacaoAtual("ugh", false);
		}
		else
		{   
			// Atualizando a dire��o da anima��o    	
    		if(velocidadeX > 0)
			{
				direcaoDaAnimacao = 0;
			}
			else if(velocidadeX < 0)
			{
				direcaoDaAnimacao = 1;
			}
		 	
    		// Anima��o: Parado    	
    		if(velocidadeX == 0 && velocidadeY == 0 && !estaNoAr
    	   	&& !getAnimacaoAtual().getNome().equals("calcas")
    	   	&& tempoDeOcio == 0)
    		{
	    		setAnimacaoAtual("parado", false);
    		}
    	
			// Anima��o: Andando
			if((velocidadeX != 0 || velocidadeY != 0) && !estaNoAr)
			{
				setAnimacaoAtual("andando", false);
			}
			
			// Anima��o: Pulando
			if(estaNoAr && !estaAtacando())
			{
				setAnimacaoAtual("pulo", false);
			}
		}
    	
    	super.atualizarAnimacao();
    }
    
    /** M�todo que retorna se o jogador est� atacando 
     *  @return true ou false.
     */
    public boolean estaAtacando()
    {
    	return(getAnimacaoAtual().getNome().equals("soco") ||
    	       getAnimacaoAtual().getNome().equals("chute") ||
    	       getAnimacaoAtual().getNome().equals("cabecada") ||
		       getAnimacaoAtual().getNome().equals("voadora"));
    }

	/** M�todo que controla o Jogador de acordo com o estado
	 *  das teclas do teclado.
	 *  @param teclasPressionadas Um byte com as informa��es sobre as teclas.
	 *  cada bit representa uma tecla de comando.
	 */
	public void lerComandos(byte teclasPressionadas)
	{
		if(teclasPressionadas != 0)
		{
			tempoDasCalcas = 0;
		}
		
    	if((teclasPressionadas & Teclado.TECLA_BAIXO) > 0)
    	{
    		velocidadeY = 2;
    	}
    	
    	if((teclasPressionadas & Teclado.TECLA_CIMA) > 0)
    	{
    		velocidadeY = -2;
    	}
    	
    	if((teclasPressionadas & Teclado.TECLA_DIREITA) > 0)
    	{
    		velocidadeX = 2;
    		direcaoDaAnimacao = 0;
    	}
    	
    	if((teclasPressionadas & Teclado.TECLA_ESQUERDA) > 0)
    	{
    		velocidadeX = -2;
    		direcaoDaAnimacao = 1;
    	}
    	
    	if((teclasPressionadas & Teclado.TECLA_CIMA) == 0 &&
    	   (teclasPressionadas & Teclado.TECLA_BAIXO) == 0)
    	{
    		velocidadeY = 0;
    	}
    	
    	if((teclasPressionadas & Teclado.TECLA_ESQUERDA) == 0 &&
    	   (teclasPressionadas & Teclado.TECLA_DIREITA) == 0)
    	{
    		velocidadeX = 0;
    	}
    	
    	// Comandos: Pulando
		if((teclasPressionadas & Teclado.TECLA_PULO) > 0 && !estaNoAr)
    	{
    		estaNoAr    = true;
    		velocidadeZ = 5;
    	}
    	
    	// Comandos: Soco
    	if((teclasPressionadas & Teclado.TECLA_SOCO) > 0 && !estaAtacando())
    	{
    		if(!estaNoAr) // Ataques normais
    		{
    			velocidadeX = velocidadeY = 0;
    			tempoDeOcio = 30;
	    		
				switch(golpeAtualDoCombo)
				{
					case 0:
						setAnimacaoAtual("soco");
						break;
					case 1:
						if(tempoDeCombo > 0)
						{
							tempoDeCombo = 0;
							setAnimacaoAtual("chute");
						}
						break;
					case 2:
						if(tempoDeCombo > 0)
						{
							tempoDeCombo = 0;
							setAnimacaoAtual("cabecada");
							golpeAtualDoCombo = 0;
						}
						break;
	    		}// fim do switch
	    	}
	    	else // Voadora
	    	{
	    		setAnimacaoAtual("voadora");
	    		tempoDeOcio  = 1000;
	    	}
    	}
	}
	
	/** 
	 *  M�todo que detecta e trata colis�es com os ataques de um inimigo com
	 *  o jogador.
	 *  @param inimigo um objeto do tipo Inimigo
	 */
	private void tratarColisoesComAtaquesDoInimigo(Inimigo inimigo)
	{
		// Detectando colis�es com o jogador.
		// Se o jogador est� colidindo com o inimigo, n�o est� machucado e o quadro
		// de anima��o do inimigo � o quadro cr�tico (no. 3), partir para a segunda
		// condi��o
    	if(inimigo.estaAtacando() && this.colideCom(inimigo) && !estaMachucado)
    	{    		
    		// O inimigo n�o pode atacar o jogador de costas, ent�o s� faz sentido
    		// detectar a colis�o se o inimigo estiver de frente para o inimigo.
    		if((inimigo.getDirecao() == 0 &&
    		    posicaoX + largura/2 > inimigo.getPosicaoX() + inimigo.getLargura()/2)
    		   || (inimigo.getDirecao() == 1 &&
    		    posicaoX + largura/2 <= inimigo.getPosicaoX() + inimigo.getLargura()/2))
    		{    		
    			// Aplicando as penalidades ao jogador
    			if(inimigo.getDirecao() == 0)
    			{
    				direcaoDaAnimacao = 1;
    			}
    			else
    			{
    				direcaoDaAnimacao = 0;
    			}
    			
    			estaMachucado = true;
	    		tempoDeOcio = TEMPO_DE_MACHUCADO;
    			velocidadeX = 0;
    			velocidadeY = 0;
    			velocidadeZ = 0;
    		
    			// Aplicando o dano ao jogador
    			barraDeEnergia.removerEnergia(inimigo.calcularDanoDoGolpe());
    			danoRecente += inimigo.calcularDanoDoGolpe();    			
    		} // fim do if da posi��o
    	}// fim do if da colis�o
	}
	
	/** M�todo que retorna se o jogador est� morto
	 *  @return <code>true</code> se o jogador est� morto
	 */
	public boolean estaMorto()
	{
		return(barraDeEnergia.getEnergiaAtual() <= 0);
	}
	
	/** M�todo que, quando chamado, permite ao jogador
	 *  aplicar o pr�ximo golpe do combo
	 */
	public void proximoGolpeDoCombo()
	{
		tempoDeCombo = 40;
		golpeAtualDoCombo++;
	}
}
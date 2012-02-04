package game;

import java.util.*;

/**
 *  Classe que implementa o cerebro do inimigo, ou seja, implementa a intelig�ncia
 *  artificial do inimigo.
 *
 *  @author  Paolo Victor, Helder Fernando, Alvaro Magnum, Dalton Cezane, Fabricio Gutemberg
 *  @version 1.0
 */
public class Cerebro
{
	
	/**
	 * tipo do pensamento do inimigo.
	 */
	private int tipoDoPensamento;
	
	/**
	 * Constante que define a coragem do inimigo. 
	 */
	private final int CORAGEM  = 60;
	
	/**
	 * Constante que define a covardia do inimigo
	 */
	private final int COVARDIA = 40;
	
	/**
	 * Constante que define o numero de pixels pra perto do jogador
	 */
	private final int NUM_PIXELS_PERTO = -30;
	
	/**
	 * Constante que define o numero de pixels pra longe do jogador
	 */
	private final int NUM_PIXELS_LONGE = 40;
	
	/** 
	 * vector de acoes
	 */	
	private Vector acoes;
	
	/** 
	 *O alvo das a��es 
	 */
	private Jogador alvo;
	
	/** 
	 *O dono do c�rebro 
	 */
	private Inimigo dono;
	
	/**
	 *  Construtor da classe
	 *  @param alvo O alvo dos ataques do c�rebro
	 *  @param dono O dono do c�rebro
	 */
	public Cerebro(Jogador alvo, Inimigo dono)
	{
		this.alvo = alvo;
		this.dono = dono;
		
		// O vector � criado aqui pois o custo de processamento para remover
		// todos os elementos � (?) menor do que o de criar um novo objeto.
		acoes = new Vector();
	}
	
	/**
	 * M�todo que "pensa" a pr�xima a��o do inimigo.
	 */
	public Vector pensar()
	{
		// Limpando o vector de a��es.
		acoes.removeAllElements();
		
		//define o tipo do pensamento do inimigo
		int probabilidadeCovarde  = CORAGEM;
		int probabilidadeCorajoso = COVARDIA;
		
		// Se a energia � baixa, ele � mais covarde.
		if(dono.getBarraDeEnergia().getEnergiaAtual() < 
		   dono.getBarraDeEnergia().getEnergiaMaxima()/2)
		{
			probabilidadeCovarde  += 20;
			probabilidadeCorajoso -= 20;
		}	
		
		// Se o jogador est� de costas, ele � mais corajoso
		if(alvo.getDirecao() == dono.getDirecao())
		{
			probabilidadeCorajoso += 10;
			probabilidadeCovarde  -= 10;
		}
		
		// O jogador est� com a energia baixa? Mais corajoso ainda!
		if(alvo.getBarraDeEnergia().getEnergiaAtual() < 
	   	   alvo.getBarraDeEnergia().getEnergiaMaxima()/3)
		{
			probabilidadeCorajoso += 20;
			probabilidadeCovarde  -= 20;
		}
		
		// O inimigo est� com energia completa? Rambo!
		if(dono.getBarraDeEnergia().getEnergiaAtual() == 
	       dono.getBarraDeEnergia().getEnergiaMaxima())
		{
			probabilidadeCorajoso += 10;
			probabilidadeCovarde  -= 10;
		}
		
		// Mas apesar de tudo, ficar fora da tela n�o d�...
		if(dono.getPosicaoX() < 0 - dono.getLargura() || dono.getPosicaoX() > 320)
		{
			probabilidadeCorajoso = 99;
			probabilidadeCovarde  = 1;
		}
		
		// Definindo o tipo do pensamento
		tipoDoPensamento = (int)(Math.random() * 100);
		
		//Agora, um if/else ponderado...
		if(tipoDoPensamento <= probabilidadeCorajoso) // corajoso
		{
			if((int)(Math.random() * 3) <= 1) // soco
			{
				acoes.add(new Acao (Acao.MOVER, manterDistancia(NUM_PIXELS_PERTO)));
				acoes.add(new Acao (Acao.ATACAR, null));
			}
			else // voadora
			{
				// para o Bully 3 a voadora � diferente.
				if(dono.getTipo() == Inimigo.BULLY3)
				{
					acoes.add(new Acao (Acao.MOVER, manterDistancia(40)));
					acoes.add(new Acao (Acao.ESPERAR, esperar(30)));
					
					if(alvo.getPosicaoX() + alvo.getLargura()/2 > 
					   dono.getPosicaoX() + dono.getLargura()/2)
					{
						acoes.add(new Acao (Acao.PULAR, pular(4, 0, 4)));
					}
					else
					{
						acoes.add(new Acao (Acao.PULAR, pular(-4, 0, 4)));
					}
					
					acoes.add(new Acao (Acao.ATACAR, null));
					acoes.add(new Acao (Acao.ESPERAR, esperar(10)));
				}
				else if(dono.getTipo() == Inimigo.BULLY4) // Para o Bully 4 tamb�m
				{
					acoes.add(new Acao (Acao.MOVER, manterDistancia(60)));
					acoes.add(new Acao (Acao.ESPERAR, esperar(40)));
					
					if(alvo.getPosicaoX() + alvo.getLargura()/2 > 
					   dono.getPosicaoX() + dono.getLargura()/2)
					{
						acoes.add(new Acao (Acao.PULAR, pular(4, 0, 5)));
					}
					else
					{
						acoes.add(new Acao (Acao.PULAR, pular(-4, 0, 5)));
					}
					
					acoes.add(new Acao (Acao.ATACAR, null));
					acoes.add(new Acao (Acao.ESPERAR, esperar(10)));
				}
				else
				{
					acoes.add(new Acao (Acao.MOVER, manterDistancia(40)));
					acoes.add(new Acao (Acao.PULAR, pularEmDirecaoAoAlvo()));
					acoes.add(new Acao (Acao.ESPERAR, esperar(30)));
					acoes.add(new Acao (Acao.ATACAR, null));
				}
			}
		}
		else // covarde
		{
			acoes.add(new Acao (Acao.MOVER,
			                  manterDistancia(NUM_PIXELS_LONGE + (int)(Math.random() * 20))));
			acoes.add(new Acao (Acao.ESPERAR, esperar(70)));
		}
		
		return(acoes);
		
	} // pensar
	

	/** 
	 * M�todo que escolhe uma a��o emergencial para o inimigo.
	 */	 
	public Vector pensarRapidamente()
	{
		// Limpando o vector de a��es.
		acoes.removeAllElements();
		
		// Escolhendo e montando a a��o
		if((int)(Math.random() * 2) == 0)
		{
			acoes.add(new Acao (Acao.MOVER, manterDistancia(NUM_PIXELS_LONGE)));
		}
		else
		{
			acoes.add(new Acao (Acao.ATACAR, null));
		}
				
		return(acoes);
	}	
	
	/** 
	 * M�todo que escolhe uma a��o evasiva para o inimigo.
	 */	 
	public Vector pensarEvasivo()
	{
		// Limpando o vector de a��es.
		acoes.removeAllElements();
		
		if(alvo.getPosicaoX() < dono.getPosicaoX())
		{
			acoes.add(new Acao (Acao.PULAR, pular(3, 0, 3)));
		}
		else
		{
			acoes.add(new Acao (Acao.PULAR, pular(-3, 0, 3)));
		}
				
		return(acoes);
	}	


	/** 
	 * M�todo que monta os atributos da a��o que faz com que
	 * o inimigo se mova para uma certa dist�ncia do jogador.
	 * @param distancia A dist�ncia do inimigo ao jogador.
	 */
	public int[] manterDistancia(int distancia)
	{
		int[] coordenadas = new int[2];
						
		if(alvo.getPosicaoX() + alvo.getLargura()/2 >
		   dono.getPosicaoX() + dono.getLargura()/2)
		{
			coordenadas[0] = alvo.getPosicaoX() - dono.getLargura() - distancia;
		}
		else
		{
			coordenadas[0] = alvo.getPosicaoX() + alvo.getLargura() + distancia;
		}
			
		
		coordenadas[1] = alvo.getPosicaoY() - (int)(Math.random() * 3) + 4;
		
		return(coordenadas);
	}

	/** 
	 * M�todo que monta os atributos da a��o que faz o inimigo pular.
	 * @param velocidadeX A velocidade x do inimigo
	 * @param velocidadeY A velocidade y do inimigo
	 * @param altura A altura do pulo.
	 * @return Os atributos.
	 */
	public int[] pular(int velocidadeX, int velocidadeY, int altura)
	{
		int[] atributos = new int[3];
						
		atributos[0] = velocidadeX;
		atributos[1] = velocidadeY;
		atributos[2] = altura;
		
		return(atributos);
	}
	
	/**
	 * M�todo que monta os atributos da a��o
	 * que faz o inimigo pular em dire��o ao alvo.
	 * @return Os atributos.
	 */
	public int[] pularEmDirecaoAoAlvo()
	{
		int[] atributos = new int[3];
						
		if (alvo.getPosicaoX() + alvo.getLargura()/2 >
		    dono.getPosicaoX() + dono.getLargura()/2)
		{
			atributos[0] = 2;
		}
		else
		{
			atributos[0] = -2;
		}
			
		
		atributos[1] = 0;
		atributos[2] = 5;
		
		return(atributos);
	}
	
	/**
	 *  M�todo que monta os atributos da a��o que
	 *  faz o inimigo esperar um certo tempo
	 *  @param tempo O tempo de espera
	 */
	public int[] esperar(int tempo)
	{
		int[] atributos = new int[1];						
		atributos[0] = tempo;
		
		return(atributos);
	}
		
} //Cerebro		
	 

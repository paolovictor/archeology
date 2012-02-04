package game;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Vector;

import javax.swing.JOptionPane;

/**
 *
 * Classe que implementa um objeto de jogo
 * 
 * @author  Paolo Victor, Helder Fernando, �lvaro Magnum, Fabr�cio Gutemberg,
 *           Dalton C�zane.
 * @version 1.0
 */

public class ObjetoDeJogo implements Desenhavel
{	
	
	/**
 	 * A posi��o X do objeto.
 	 */
	protected int posicaoX;
	
	/**
 	 * A posi��o Y do objeto.
 	 */
	protected int posicaoY;
	
	/**
 	 * A velocidade X do objeto.
 	 */
	protected int velocidadeX;
	
	/**
 	 * A velocidade Y do objeto.
 	 */
	protected int velocidadeY;
	
	/**
 	 * A velocidade Z do objeto.
 	 */
	protected double velocidadeZ;
		
	/**
 	 * A altura da qual o objeto est� no ch�o.
 	 */
 	protected double alturaDoChao;
	
	/**
	 * A largura do objeto.
	 */
	protected int largura;
	
	/**
	 * A altura do objeto.
	 */
	protected int altura;
	
	/**
	 * O espa�o ocupado pelo objeto.
	 */
	protected Rectangle espacoOcupado;
	
	/**
	 * Indica o objeto est� no ar.
	 */
	protected boolean estaNoAr;
	
	/**
	 * A array de anima��es do objeto.
	 */
	protected Animacao[] animacoes;
	
	/**
	 * O n�mero da anima��o atual do objeto.
	 */
	protected int animacaoAtual;
	
	/**
	 * A dire��o da anima��o do objeto.
	 */
	protected int direcaoDaAnimacao;
	
	/**
	 * Indica se a sombra do objeto deve ser mostrada.
	 */
	protected boolean mostrarSombra;
	
	/** M�todo construtor padr�o da classe */
	public ObjetoDeJogo()
	{
		this(0, 0, 0, 0, false);
	}
	
	/** M�todo construtor da classe
	 *  @param posicaoX A posi��o X inicial.
	 *  @param posicaoY A posi��o Y inicial.
	 *  @param largura A largura do objeto.
	 *  @param altura A altura do objeto.
	 *  @param mostrarSombra Se a sombra do objeto deve ser mostrada.
	 */	
	public ObjetoDeJogo(int posicaoX, int posicaoY, int largura, int altura,
	                    boolean mostrarSombra)
	{
		this.posicaoX = posicaoX;
		this.posicaoY = posicaoY;
		
		this.largura  = largura;
		this.altura   = altura;
		
		animacaoAtual     = 0;
		direcaoDaAnimacao = 0; // 0 para direita, 1 para esquerda
		
		this.mostrarSombra = mostrarSombra;
		                                 
		// O espa�o ocupado pelo objeto
		espacoOcupado = new Rectangle(posicaoX + 10, posicaoY + altura - 8,
		                              largura - 20, 16);
	}
    
	/** Atualiza a posi��o do objeto */
	public void atualizarPosicao()
	{
		// Atualizando a posi��o
		posicaoX += velocidadeX;
		posicaoY += velocidadeY;
			
		// N�o deixando que o objeto saia pelas verticais
		if(posicaoY <= 140 - altura)
		{
			posicaoY = 140 - altura;
		}
		
		if(posicaoY + altura > 230)
		{
			posicaoY = 230 - altura;
		}
		
		// Atualizando o espaco ocupado
		espacoOcupado.setLocation(posicaoX + 10, posicaoY + altura - 8);
		
		// Tratando os pulos
    	if(estaNoAr)
    	{
	    	alturaDoChao -= velocidadeZ;
	    	velocidadeZ  -= 0.25;
	    	    	
	    	if(alturaDoChao >= 0)
	    	{
	    		velocidadeX  = 0;
	    		velocidadeY  = 0;
	    		velocidadeZ  = 0;
	    		alturaDoChao = 0;
	    		estaNoAr     = false;
	    	}
	    }
	}
	
	/** M�todo que carrega um arquivo de anima��es
	 *  @param nomeDoArquivo O nome do arquivo
	 */
	public void carregarAnimacoes(String nomeDoArquivo)
	{
		try
   		{
   			// Lendo o arquivo e enchendo a array de anima��es.
   			ObjectInputStream in = new ObjectInputStream( new FileInputStream(nomeDoArquivo));
   		    Vector temp = (Vector)in.readObject();
   		    
   		    animacoes = new Animacao[temp.size()];
   		    
   		    for(int k = 0; k < temp.size(); k++)
   		    {
   		    	animacoes[k] = (Animacao)temp.get(k);
   		    }
   		       		    
   		    in.close();
   		}
   		catch(FileNotFoundException f)
   		{
   			JOptionPane.showMessageDialog(null,f.getMessage());
   			System.exit(1);
   		}
   		catch(ClassNotFoundException c)
   		{
   			JOptionPane.showMessageDialog(null,c.getMessage());
   			System.exit(1);
   		}
        catch(IOException e)
        {
        	JOptionPane.showMessageDialog(null,e.getMessage());    
        	System.exit(1);
        }      
	}
	
	/** Atualiza a anima��o do objeto */
	public void atualizarAnimacao()
	{
		// Atualizando a anima��o
		animacoes[animacaoAtual].atualizaAnimacao();
	}
	
	/** 
	 *  Retorna a anima��o atual do objeto
	 *  @return A anima��o atual
	 */
	public Animacao getAnimacaoAtual()
	{
		return(animacoes[animacaoAtual]);
	}
	
	/** Retorna o n�mero do quadro da anima��o atual atual do objeto
	 *  @return O n�mero do quadro da anima��o atual atual do objeto
	 */
	public int getQuadroAtual()
	{
		return(animacoes[animacaoAtual].getQuadroAtual());
	}
	
	/** Seta a nova anima��o
	 *  @param nome O nome da anima��o
	 */
	public void setAnimacaoAtual(String nome)
	{
		setAnimacaoAtual(nome, true);
	}
	
	/** 
	 *  Seta a nova anima��o
	 *  @param nome O nome da anima��o
	 *  @param reiniciar Se a animacao deve ser reiniciada
	 */
	public void setAnimacaoAtual(String nome, boolean reiniciar)
	{		
		for(int k = 0; k < animacoes.length; k++)
		{
			if(animacoes[k].getNome().equals(nome))
			{
				animacaoAtual = k + direcaoDaAnimacao;
								
				if(reiniciar)
				{
					this.getAnimacaoAtual().reiniciarAnimacao();
				}
				
				return;
			}
		}
		
		// Se chegou aqui, a anima��o n�o existe.
		animacaoAtual = 0;
	}		
	
	/**
	 *  M�todo que determina se o objeto est� colidindo com outro
	 *  @param objeto O objeto com o qual se quer checar a colis�o.
	 *  @return <code>true</code> caso a colis�o seja verdadeira, 
	 *          <code>false</code> caso contr�rio.
	 */
	public boolean colideCom(ObjetoDeJogo objeto)
	{
		// Pegando a caixa de colis�o do primeiro objeto
		Rectangle rect1 = (Rectangle)getEspacoOcupado();
		
		// Pegando a caixa de colis�o do segundo objeto
		Rectangle rect2 = (Rectangle)objeto.getEspacoOcupado();
		
		// Checando a colis�o
		return(rect1.intersects(rect2));
	}

	/**
	 *  M�todo que retorna o ret�ngulo que define o espa�o ocupado
	 *  pelo objeto
	 *  @return O ret�ngulo do tipo <code>Rectangle</code>.
	 */
	public Rectangle getEspacoOcupado()
	{
		return(this.espacoOcupado);
	}
	
	/**
	 * M�todo que retorna a posi��o X do objeto 
	 * @return A posi��o X do objeto.
	 */
	public int getPosicaoX()
	{
		return(posicaoX);
	}
	
	/**
	 * M�todo que retorna a posi��o Y do objeto 
	 * @return A posi��o Y do objeto. 
	 */
	public int getPosicaoY()
	{
		return(posicaoY);
	}
	
	/**
	 * M�todo que retorna a largura do objeto
	 * @return A largura do objeto
	 */
	public int getLargura()
	{
		return(largura);
	}
	
	/**
	 * M�todo que retorna a altura do objeto
	 * @return A altura do objeto. 
	 */
	public int getAltura()
	{
		return(altura);
	}	
	
	/**
	 * M�todo que retorna a dire��o do objeto
	 * @return A dire��o do objeto 
	 */
	public int getDirecao()
	{
		return(direcaoDaAnimacao);
	}
	
	/**
	 * M�todo que move um objeto
	 * @param deltaX A quantidade X de pixels
	 * @param deltaY A quantidade Y de pixels
	 */
	public void moverObjeto(int deltaX, int deltaY)
	{
		posicaoX += deltaX;
		posicaoY += deltaY;
	}
	
	/**
	 * Mostra o objeto 
	 * @param c O componente no qual o objeto ser� mostrado.
	 * @param g Graphics da imagem na qual o objeto ser� mostrado.
	 */
	public void paint(Graphics g)
	{		
		// Desenhando a sombra
		if(mostrarSombra)
		{
			g.fillOval(posicaoX + 10 + Math.abs((int)alturaDoChao)/4,
			           posicaoY + altura - 10 + Math.abs((int)alturaDoChao)/8,
			           largura - 20 - Math.abs((int)alturaDoChao)/2,
			           16 - Math.abs((int)alturaDoChao)/4);
		}
		
		// Desenhando a anima��o atual.
		g.setClip( posicaoX, posicaoY, largura, altura );
		animacoes[animacaoAtual].desenharAnimacao(g, posicaoX, posicaoY + (int)alturaDoChao);
	}
}
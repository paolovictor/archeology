package game;

import java.awt.Graphics;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Vector;

import javax.swing.JOptionPane;

/**
 *
 * Classe que implementa um cursor para a tela de t�tulo.
 * 
 * @author  Paolo Victor, Helder Fernando, �lvaro Magnum, Fabr�cio Gutemberg,
 *           Dalton C�zane.
 * @version 1.0
 */

public class Cursor implements Serializable, Desenhavel
{	
	/**
 	 * A posi��o X do cursor.
 	 */
	protected int posicaoX;
	
	/**
 	 * A posi��o Y do cursor.
 	 */
	protected int posicaoY;
	
	/**
	 * A anima��o do cursor.
	 */
	protected Animacao animacao;
	
	/** M�todo construtor da classe <br><br>
	 *  @param posicaoX A posi��o X inicial.<br>
	 *  @param posicaoY A posi��o Y inicial.
	 *  @param arquivoDaAnimacao O nome do arquivo da anima��o do cursor<br>
	 */	
	public Cursor(int posicaoX, int posicaoY, String arquivoDaAnimacao)
	{
		this.posicaoX = posicaoX;
		this.posicaoY = posicaoY;
		
		Vector temp;
		
		try
   		{
   			ObjectInputStream in = new ObjectInputStream(new FileInputStream(arquivoDaAnimacao));
   		    temp = (Vector)in.readObject();
   		    in.close();
   		    
   		    this.animacao = (Animacao)temp.get(0);
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
	
	/**
	 * Mostra o objeto 
	 * @param c O componente no qual o cursor ser� mostrado.
	 * @param g Graphics da imagem na qual o objeto ser� mostrado.
	 */
	public void paint(Graphics g)
	{	
		// Desenhando a anima��o
		animacao.desenharAnimacao(g, posicaoX, posicaoY);
	}
	
	/** 
	 * Atualiza a anima��o do cursor
	 */
	public void atualizaAnimacao()
	{
		animacao.atualizaAnimacao();
	}
	
	/**
	 * M�todo que muda a posicao do cursor 
	 * @param posicaoX A nova coordenada X
	 * @param posicaoY A nova coordenada Y
	 */
	public void setPosicao(int posicaoX, int posicaoY)
	{
		this.posicaoX = posicaoX;
		this.posicaoY = posicaoY;
	}
}
package game;

import java.awt.Graphics;
import java.util.Vector;

import javax.swing.JPanel;

/**
 *	Classe que implementa uma interface gr�fica do jogo.
 *
 *  @author  Paolo Victor, Helder Fernando, Alvaro Magnum, Dalton Cezane, Fabricio Gutemberg
 *  @version 1.0
 */
public class InterfaceGrafica extends JPanel
{	
	/** 
	 * A fase que ser� mostrada pela interface 
	 */
	private ComunicavelComIG ComponenteEmComunicacao;
	
	/** 
	 *Vector com os objetos da fase 
	 */
	private Vector objetosDoComponente;
	
	/** Construtor padr�o da classe
	 *  @param ComponenteEmComunicacao O componente que 
	 *  ser� mostrado pela interface
	 */
	public InterfaceGrafica(ComunicavelComIG ComponenteEmComunicacao)
	{
		this.ComponenteEmComunicacao = ComponenteEmComunicacao;
		
		// Destivando o buffer duplo
	}
	
	/** Muda o componente que est� sendo mostrado pela interface
	 *  @param ComponenteEmComunicacao O novo componente
	 */
	public void setComponente(ComunicavelComIG ComponenteEmComunicacao)
	{
		this.ComponenteEmComunicacao = ComponenteEmComunicacao;
	}
	
	/** Mostra o componente em comunica��o com a interface.
	 *  @param g Graphics que ser� usado para o desenho.
	 */
	public void paint(Graphics g)
	{	
		g.setClip(0, 0, 320, 240);
		objetosDoComponente = ComponenteEmComunicacao.getObjetos();

		Object temp; // usado para o for
		for(int k = 0; k < objetosDoComponente.size(); k++)
		{
			temp = objetosDoComponente.get(k);
			((Desenhavel)temp).paint(g);
		}
	}	
}
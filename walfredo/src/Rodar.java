import game.AsAventurasDeWalfredo;

import javax.swing.*;

/**
 *	Classe usada para rodar o jogo.
 *
 *  @author  Paolo Victor, Helder Fernando, �lvaro Magnum, Fabr�cio Gutemberg,
 *           Dalton C�zane.
 *  @version 1.0
 */

public class Rodar
{
	/**
	 * O m�todo principal da classe.
	 */
	public static void main(String args[])
	{	
		// O jogo.
		AsAventurasDeWalfredo jogo;
	
		// Definindo se o usu�rio quer redefinir os frames por segundo.
		if(args.length > 0 && args[0].equals("setfps"))
		{
			int fps;
				
			try
			{
				fps = Integer.parseInt(JOptionPane.showInputDialog(null,
				                       "Digite os frames por segundo desejados:"));
			}
			catch(Exception e)
			{
				fps = AsAventurasDeWalfredo.FPS_PADRAO;
			}
			
			jogo = new AsAventurasDeWalfredo(fps);
		}
		else // com fps padr�o
		{
			jogo = new AsAventurasDeWalfredo();
		}
		
		// Setando a opera��o de fechamento.
		jogo.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
package game;
import java.applet.Applet;
import java.applet.AudioClip;
import java.net.URL;
import java.util.Vector;

/**
 *
 * Classe usada para a sa�da de som.
 * 
 * @author  Paolo Victor, Helder Fernando, Alvaro Magnum, Dalton Cezane, Fabricio Gutemberg
 * @version 1.0
 *
 */
 
 public class Jukebox
 {
 	/** 
 	 * A lista de m�sicas e sons 
 	 */
 	Vector    playList;
 	
 	/** 
 	 * A faixa da lista que est� em execu��o 
 	 */
 	AudioClip faixaAtual;
 	
 	/** 
 	 * O construtor padr�o da classe. 
 	 */
 	public Jukebox()
 	{
 		playList   = new Vector();
 		faixaAtual = null;
 	}
 	
 	/** M�todo que adiciona uma faixa � lista
 	 *  @param nomeDoArquivo O nome do arquivo de a�dio
 	 */
 	public void adicionarFaixa(String nomeDoArquivo) throws Exception
 	{
        playList.add(Applet.newAudioClip(new URL("file", "localhost", nomeDoArquivo)));
 	}
 	
 	/** M�todo que toca uma faixa da lista
 	 *  @param faixa O n�mero da faixa
 	 *  @param loop Se a faixa entra em loop
 	 */
 	public void tocarFaixa(int faixa, boolean loop)
 	{
 		if(faixa < playList.size() && faixa >= 0)
 		{
 			faixaAtual = (AudioClip)playList.get(faixa);
 			
 			if(loop)
 			{
 				faixaAtual.loop();
 			}
 			else
 			{
 				faixaAtual.play();
 			}
 		}
	}
 	
 	/** M�todo que p�ra de tocar faixa atual. */
 	public void pararFaixa()
 	{
 		if(estaTocandoFaixa())
 		{
 			faixaAtual.stop();
 			faixaAtual = null;
 		}
 	}
 	
 	/** M�todo que diz se uma m�sica est� tocando
 	 *  @return <code>true</code> se a alguma faixa est� sendo tocada,
 	 *  <code>false</code> se n�o estiver.
 	 */
 	public boolean estaTocandoFaixa()
 	{
 		return(faixaAtual != null);
 	}
 }

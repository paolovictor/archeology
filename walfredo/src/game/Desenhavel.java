package game;
import java.awt.*;

/**
 *
 * Interface para objetos que podem ser desenhados
 * 
 * @author  Paolo Victor
 * @version 0.1
 *
 */ 
 public interface Desenhavel
 {
 	/** M�todo que desenha um objeto 
 	 *  @param c O Componente no qual o objeto ser� desenhado
 	 *  @param g O Graphics no qual o objeto ser� desenhado
 	 */
 	public void paint(Graphics g);
 }

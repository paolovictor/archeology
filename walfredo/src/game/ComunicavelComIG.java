package game;
import java.util.*;

/**
 *
 * Interface para objetos que se comunicam com a interface
 * gr�fica do jogo.
 * 
 * @author  Paolo Victor, Helder Fernando, �lvaro Magnum, Fabr�cio Gutemberg,
 *           Dalton C�zane.
 * @version 1.0
 *
 */ 
 public interface ComunicavelComIG
 {
 	/** M�todo que retorna os objetos que devem ser
 	 *  desenhados pela interface
 	 *  @return Os objetos quer devem ser mostrados.
 	 */
 	public Vector getObjetos();
 }

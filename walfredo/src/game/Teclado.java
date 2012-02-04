
package game;
/**
 *
 * Classe est�tica para constantes de configura��o do teclado
 * 
 * @author  Paolo Victor, Helder Fernando, �lvaro Magnum, Fabr�cio Gutemberg,
 *           Dalton C�zane.
 * @version 1.0
 *
 */
 
 public class Teclado
 {
 	/**
 	 * Representa a tecla "seta para cima", utilizada para
	 * setar a vari�vel teclasPressionadas.
	 **/
    final static public byte TECLA_CIMA     = 0x01;
    
	/**
	 * Representa a tecla "seta para baixo", utilizada para
	 * setar a vari�vel teclasPressionadas.
	 */ 
    final static public byte TECLA_BAIXO    = 0x02;
    
	/**
	 *  Representa a tecla "seta para direita", utilizada para
	 *  setar a vari�vel teclasPressionadas
	 */
    final static public byte TECLA_DIREITA  = 0x04;
    
	/**
	 * Representa a tecla "seta para esquerda", utilizada para
	 * setar a vari�vel teclasPressionadas.
	 */
    final static public byte TECLA_ESQUERDA = 0x08;
    
    /**
     * Representa a tecla "barra espa�o", utilizada para
	 * setar a vari�vel teclasPressionadas 
	 */
    final static public byte TECLA_PULO     = 0x10;
    
    /** 
     * Representa a tecla "control", utilizada para
	 * setar a vari�vel teclasPressionadas 
	 */
    final static public byte TECLA_SOCO     = 0x20;
    
    /**
     * Representa a tecla "escape", utilizada para
	 * setar a vari�vel teclasPressionadas 
	 */
    final static public byte TECLA_ESCAPE   = 0x40;
 }

package game;

import java.util.*;
import java.io.*;
import javax.swing.*;

/** 
 * 
 * Classe que implementa o sistema de eventos do jogo. Este sistema � usado 
 * para manipular as fases, que s�o apenas sequ�ncias de eventos.
 *
 * @author Paolo Victor, Helder Fernando, �lvaro Magnum, Fabr�cio Gutemberg,
 *           Dalton C�zane.
 * @version 1.0
 */ 

public class SistemaDeEventos
{
   
   /**
    * A pilha de eventos do sistema.
    */
   private Stack eventos;
   
   /**
    * O evento atual do sistema.
    */
   private Evento eventoAtual;
   
   /**
    * Construtor do Sistema de Eventos. Este construtor recebe um nome de arquivo para 
    * poder le-lo. Caso ocorram problemas ao tentar ler o arquivo, excessoes sao lancadas.
    * @param nomeArquivo O nome do arquivo a ser lido
    * @throws FileNotFoundException, ClassNotFoundException, IOException 
    */
   public SistemaDeEventos(String nomeArquivo)
   {
   		try
   		{
   			ObjectInputStream in = new ObjectInputStream( new FileInputStream(nomeArquivo));
   		    eventos = (Stack) in.readObject();
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
       
    	lerProximoEvento();
   }
   
   /**
    * Metodo que le o proximo evento da pilha de eventos.
    * Se a pilha estiver vazia, o proximo evento � null.
    */
   public void lerProximoEvento()
   {
  		if (eventos.empty())
  		{
  			eventoAtual = null;
  		}
  		else
  		{	
  			eventoAtual = (Evento) eventos.pop();
  		}
   }
   
   /**
    * Metodo que retorna o evento atual no sistema.
    * @return O evento atual no sistema.
    */
   public Evento getEventoAtual()
   {
   		return eventoAtual;
   }
   
   
}
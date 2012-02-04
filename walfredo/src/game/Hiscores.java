package game;
import javax.swing.*;
import java.io.*;
import java.util.*;

/**
 *
 * Classe que implementa o banco de scores do jogo
 * 
 * @author  Paolo Victor, Helder Fernando, �lvaro Magnum, Fabr�cio Gutemberg,
 *           Dalton C�zane.
 * @version 1.0
 *
 */
 
 public class Hiscores
 {
 	/**
 	 * O n�mero m�ximo de scores
 	 */	
 	public static final int NUMERO_MAXIMO_SCORES = 5;
 	
 	/**
 	 * Os scores do banco
 	 */
 	private int[] scores;
 	
 	/**
 	 * Os nomes dos donos dos scores
 	 */
 	private String[] donosDosScores;
 	
 	/**
 	 * O nome do arquivo de scores
 	 */
 	private String nomeDoArquivo;
 	
 	/**
 	 * Construtor padr�o da classe.
 	 * @param nomeDoArquivo O nome do arquivo <i>.his</i> de hiscores.
 	 */
 	public Hiscores(String nomeDoArquivo)
 	{
 		this.nomeDoArquivo = nomeDoArquivo; 		
 		abrirScores(); 		
 	}
 	
 	/**
 	 * M�todo que salva os scores.
 	 */
 	private void salvarScores()
 	{
 		ObjectOutputStream out = null;
 		
 		try
		{
			out = new ObjectOutputStream(new FileOutputStream(nomeDoArquivo));
			out.writeObject(scores);
			out.writeObject(donosDosScores);
		}
		catch(FileNotFoundException f)
		{
   			System.out.println("Erro ao salvar scores: " + f.getMessage());
			System.exit(1);
   		}
        catch(IOException i)
        {
        	System.out.println("Erro ao salvar scores: " + i.getMessage());
			System.exit(1);
        } 
        finally
        {
        	try
			{
				out.close();
			}
			catch(IOException i)
			{
				System.out.println("Erro ao salvar scores: " + i.getMessage());
				System.exit(1);
			}
        }
 	}
 	
 	/**
 	 * M�todo que abre os scores
 	 */
 	private void abrirScores()
 	{
 		ObjectInputStream in  = null;
 		 		
 		try
   		{
   			in = new ObjectInputStream( new FileInputStream(nomeDoArquivo));
   		    scores = (int[])in.readObject();
   		    donosDosScores = (String[])in.readObject();
   		}
   		catch(FileNotFoundException f)
   		{
   			in = null;
   			
   			// Se o arquivo n�o existir, criar arquivo com scores zerados
   			scores = new int[NUMERO_MAXIMO_SCORES];
   			donosDosScores = new String[NUMERO_MAXIMO_SCORES];
   			
   			for(int k = 0; k < NUMERO_MAXIMO_SCORES; k++)
   			{
   				scores[k] = 0;
   				donosDosScores[k] = new String("Mr. none");
   			}
   			
   			salvarScores();
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
        finally
        {
        	try
        	{
        		if(in != null)
        		{
        			in.close();
        		}
        	}
        	catch(IOException i)
			{
				System.out.println("Erro ao abrir scores: " + i.getMessage());
				System.exit(1);
			}
        }
 	}
 	
 	/**
 	 * M�todo que adicionaum score ao banco se ele for grande o suficiente.
 	 * @param score O score que ser� adicionado.
 	 */
 	public void adicionarScore(int score)
 	{
 		// Criando e enchendo uma array tempor�ria com todos os scores.
 		int[] tempScores = new int[NUMERO_MAXIMO_SCORES + 1]; 		
 		tempScores[0] = score;
 		
 		for(int k = 0; k < NUMERO_MAXIMO_SCORES; k++)
 		{
 			tempScores[k + 1] = scores[k];
 		}
 		
 		// Ordenando a array
 		Arrays.sort(tempScores);
 		
 		// Se o �ltimo score -n�o- for o do jogador, ent�o o seu score
 		// est� na lista. 		
 		if(tempScores[0] != score)
 		{
 			// copiando os tempScores para os scores
 			for(int k = 0; k < NUMERO_MAXIMO_SCORES; k++)
 			{
 				scores[k] = tempScores[k + 1];
 			}
 			
 			// procurando o score do jogador
 			int posicaoScoreJogador = Arrays.binarySearch(scores, score);
 		
	 		// Congratulando e lendo o nome do jogador.
	 		String mensagem = "Parab�ns! Voc� conseguiu um hiscore!\nDigite o seu nome," + 
	 		                  "e clique em OK.";
	 		String nomeDoJogador = JOptionPane.showInputDialog(null, mensagem);
	 		
	        // Inserindo o nome do jogador na nova lista de nomes
	        for(int k = 0; k < NUMERO_MAXIMO_SCORES; k++)
	        {
	        	if(scores[k] == score)
	        	{
	        		donosDosScores[k] = nomeDoJogador;
	        		break;
	        	}
	        	
	        	donosDosScores[k] = donosDosScores[k + 1];
	        }
	        
	        // Salvando os scores
	        salvarScores();
		}
 	}
 	
 	/**
 	 * M�todo que retorna um score em forma de string.
 	 * @param  posicao A posi��o no banco.
 	 * @return O score em forma de string.
 	 */
 	public String getScoreEm(int posicao)
 	{
 		if(posicao >= NUMERO_MAXIMO_SCORES || posicao < 0)
 		{
 			return(null);
 		}
 		else
 		{
 			// O "" + for�a a convers�o para String, j� que o resultado
 			// de uma concatena��o de string + inteiro � uma string.
 			return("" + scores[NUMERO_MAXIMO_SCORES - posicao - 1]);
 		}
 	}
 	
 	/**
 	 * M�todo que retorna um nome de um jogador no banco
 	 * @param  posicao A posi��o no banco.
 	 * @return O nome do jogador.
 	 */
 	public String getJogadorEm(int posicao)
 	{
 		if(posicao >= NUMERO_MAXIMO_SCORES || posicao < 0)
 		{
 			return(null);
 		}
 		else
 		{
 			return(donosDosScores[NUMERO_MAXIMO_SCORES - posicao - 1]);
 		}
 	}
 }

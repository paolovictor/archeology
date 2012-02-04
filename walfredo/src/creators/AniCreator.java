package creators;

import game.Animacao;

import java.io.*;
import java.util.*;

/**
 *
 *  Esta classe l� um arquivo de texto com defini��es os salva em um arquivo
 *  <i>.ani</i> (n�o confundir com o outro formato <i>.ani</i>), que pode ser
 *  usado no jogo.
 *
 *  <br><br>
 *
 *  Os par�metros com o nome do arquivo texto de defini��es 
 *  e o nome do arquivo de sa�da s�o passados na linha de comando. 
 *  O formato da linha de comando �:
 * 
 *  <br><br>
 * 
 *  <code>java AniCreator nomeDoArquivoDeDefini��es.txt nomeDoArquivoDeSaida.ani</code>
 *
 *  <br><br>
 *
 *  A sintaxe do interpretador � simples. Cada linha que n�o � coment�rio (linhas que
 *  come�am com "//") e que n�o est� em branco � lida e interpretada para um objeto
 *  Anima��o. O formato das linhas � o seguinte:
 *
 *  <br><br>
 *
 *  <code>nomeDaAnimacao nomeDoArquivo n�meroDeQuadros velocidade loops reinicioDoLoop</code>
 *
 *  <br><br>
 *
 *  Onde:<br><br>
 *
 *  &nbsp;&nbsp;<b>nomeDaAnima��o</b> - O nome da anima��o.<br>
 *
 *  &nbsp;&nbsp;<b>nomeDoArquivo</b> - O nome do arquivo de anima��o. A extens�o
 *  deve ser omitida, e a anima��o deve estar guardada em arquivos .gif, com a nomenclatura
 *  <i>nomedaimagemX.gif</i>, onde <b>X</b> � o n�mero do quadro, que
 *  inicia em 0.<br>
 * 
 *  &nbsp;&nbsp;<b>n�meroDeQuadros</b> - O n�mero de quadros da anima��o.<br>
 *
 *  &nbsp;&nbsp;<b>velocidade</b> - A velocidade da anima��o.<br>
 *
 *  &nbsp;&nbsp;<b>loop</b> - Se a anima��o entra em loop. Pode ser <code>true</code>
 *  ou <code>false</code>.<br>
 *
 *  &nbsp;&nbsp;<b>rein�cioDoLoop</b> - Onde a anima��o se reinicia. S� � necess�rio
 *  se <b>loop</b> for <code>false</code>.<br>
 *
 *  <br><br>
 *
 *  @author  Paolo Victor, Helder Fernando, �lvaro Magnum, Fabr�cio Gutemberg,
 *           Dalton C�zane.
 *  @version 1.0
 */

public class AniCreator
{
	/**
	 * Marcador de coment�rio.
	 */	
	private static final String COMENTARIO = new String("//");
	
	/**
	 * Indica qual linha est� sendo interpretada pelo programa.
	 */	
	private static int linhaSendoInterpretada;
	
	/**
	 * O m�todo principal da classe.
	 */
	public static void main(String args[])
	{
		Vector linesRead  = lerLinhas(args[0]);
		Vector animacoes  = new Vector();
		
		int animacaoLida = 1;
		linhaSendoInterpretada = 1;
		
		while(linesRead.size() > 0)
		{
			System.out.println("* Animacao " + animacaoLida );
			
			try
			{
				animacoes.add(lerAnimacao(linesRead, args[2]));
			}
			catch(Exception e)
			{
				System.out.println("Erro ao ler anima��es: " + e.getMessage());
				System.exit(1);
			}
						
			animacaoLida++;
		}
		
		salvarAnimacoes(animacoes, args[1]);
	}
	
	/**
	 * M�todo que pega uma linha do vector de linhas gerado pela 
	 * leitura do arquivo de defini��es.
	 * @param linhas O Vector de linhas
	 * @return A linha lida.
	 */
	public static String pegarLinha(Vector linhas)
	{
		// lendo a linha
		if(linhas.size() == 0)
		{
			return(null);
		}
		
		String linha = (String)linhas.get(0);	
		linhas.remove(0);
		
		if(linha.startsWith(COMENTARIO))
		{
			return(null);
		}
		else
		{		
			return(linha);
		}
	}
	
	/**
	 * M�todo que interpreta uma anima��o do Vector de linhas gerado
	 * pela leitura do arquivo de defini��es.
	 * @param linhas O Vector de linhas
	 * @return A anima��o interpretada.
	 */
	public static Animacao lerAnimacao(Vector linhas, String dataDir) throws Exception
	{
		StringTokenizer strTokenizer;
		
		String  nome, arquivoOrigem, tokenStr;
		int     numeroDeQuadros, velocidade, reinicioDoLoop;
		boolean loop;
				
		try
		{	
			while((tokenStr = pegarLinha(linhas)) == null);
			strTokenizer = new StringTokenizer(tokenStr, " ");
			
			// lendo o nome
			nome = strTokenizer.nextToken();
			System.out.println("Nome: " + nome);
			linhaSendoInterpretada++;
			
			// lendo o arquivo de origem
			arquivoOrigem = strTokenizer.nextToken();
			System.out.println("Arquivo de origem: " + arquivoOrigem);
			linhaSendoInterpretada++;
			
			// lendo o n�mero de quadros
			numeroDeQuadros = Integer.parseInt(strTokenizer.nextToken());
			System.out.println("N�mero de quadros: " + numeroDeQuadros);
			linhaSendoInterpretada++;
			
			// lendo a velocidade
			velocidade = Integer.parseInt(strTokenizer.nextToken());
			System.out.println("Velocidade: " + velocidade);
			linhaSendoInterpretada++;
						
			// lendo o loop
			loop = Boolean.valueOf(strTokenizer.nextToken()).booleanValue();
			System.out.println("Loops?: " + loop);
			linhaSendoInterpretada++;
			
			// Se tem loop, ler o rein�cio da anima��o
			if(loop)
			{
				reinicioDoLoop = Integer.parseInt(strTokenizer.nextToken());
				System.out.println("Reinicio do loop: " + reinicioDoLoop);
			}
			else
			{
				reinicioDoLoop = 1;
			}
		}
		catch(Exception e)
		{
			throw(new Exception("Dados inv�lidos na linha " + linhaSendoInterpretada));
		}
		
		// Com os dados lidos, criar a anima��o
		Animacao animacao = new Animacao(nome, dataDir + File.separator + arquivoOrigem, numeroDeQuadros,
		                                 velocidade, reinicioDoLoop, loop);
		
		return(animacao);
	}
	
	/** M�todo que l� um arquivo de texto, gera e retorna um Vector de
	 *  linhas retiradas do texto, ignorando coment�rios e linhas em
	 *  branco.
	 *  @param nomeDoArquivo O nome do arquivo.
	 *  @return Um Vector de linhas.
	 */
	private static Vector lerLinhas(String nomeDoArquivo)
	{
		Vector linesRead  = new Vector();
		String line       = new String("");
		BufferedReader in = null;
		
		// Abrindo o fluxo
		try
		{
			in = new BufferedReader(new FileReader(nomeDoArquivo));
		}
		catch(Exception e)
		{
			System.out.println("Erro: " + e.getMessage());
			System.exit(1);
		}
		
		// lendo as linhas
		try
		{
			while((line = in.readLine()) != null)
			{
				if(!line.startsWith(COMENTARIO) && !line.equals(""))
				{
					linesRead.add(line);
				}
			}
		}
		catch(IOException i)
		{
			System.out.println("Erro: " + i.getMessage());
			System.exit(1);
		}
		finally
		{
			try
			{
				in.close();
			}
			catch(IOException i)
			{
				System.out.println("Erro: " + i.getMessage());
				System.exit(1);
			}
		}
	
		// retornando o resultado	
		return(linesRead);
	}
	
	/** M�todo que salva as anima��es no arquivo <i>.ani</i>.
	 *  @param animacoes O Vector de anima��es.
	 *  @param nome O nome do arquivo <i>.ani</i>.
	 */
	public static void salvarAnimacoes(Vector animacoes, String nome)
	{		
		ObjectOutputStream out = null;
	
		try
		{
			out = new ObjectOutputStream(new FileOutputStream(nome));
			out.writeObject(animacoes);
		}
		catch(FileNotFoundException f)
		{
   			System.out.println("Erro ao salvar anima��es: " + f);
			System.exit(1);
   		}
        catch(IOException i)
        {
        	System.out.println("Erro ao salvar anima��es: " + i);
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
				System.out.println("Erro ao salvar anima��es: " + i.getMessage());
				System.exit(1);
			}
        }
	}
}
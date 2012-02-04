
package creators;
import game.Evento;
import game.MensagemDeTexto;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Stack;
import java.util.StringTokenizer;
import java.util.Vector;

/**
 *
 *  Esta classe l� um arquivo de texto com defini��es os salva em um arquivo
 *  <i>.evt</i> que pode ser
 *  usado como uma fase do jogo.
 *
 *  <br><br>
 *
 *  Os par�metros com o nome do arquivo texto de defini��es 
 *  e o nome do arquivo de sa�da s�o passados na linha de comando. 
 *  O formato da linha de comando �:
 * 
 *  <br><br>
 * 
 *  <code>java FaseCreator nomeDoArquivoDeDefini��es.txt nomeDoArquivoDeSaida.evt</code>
 *
 *  <br><br>
 *
 *  A sintaxe do interpretador � simples. Cada linha que n�o � coment�rio (linhas que
 *  come�am com "//") e que n�o est� em branco � lida e interpretada para um objeto
 *  Evento, em seguida cada evento � empilhado na ordem reversa da de entrada, formando
 *  a pilha de eventos que � salva. O formato das linhas � o seguinte:
 *
 *  <br><br>
 *
 *  <code>nomeDoEvento par�metros</code>
 *
 *  <br><br>
 *
 *  Onde:<br><br>
 *
 *  &nbsp;&nbsp;<b>nomeDaAnima��o</b> - O nome do evento.<br>
 *
 *  &nbsp;&nbsp;<b>par�metros</b> - Depedem do nome do evento.<br><br>
 *
 *  Tabela de eventos e par�metros:<br>
 *  <table border=1>
 *  <tr bgcolor="#CCCCCC">
 *      <td>Nome Do Evento</td> <td> Par�metros </td>
 *  </tr>
 *  <tr>
 *      <td>MostrarMensagem</td> <td>mensagem* fonte* tamanho cor tempo posicaoX posicaoY</td>
 *  </tr>
 *  <tr>
 *      <td>AbrirCenario</td> <td>quantidade</td>
 *  </tr>
 *  <tr>
 *      <td>EsperarMorte</td><td>&nbsp;</td>
 *  </tr>
 *  <tr>
 *      <td>PassarDeFase</td><td>&nbsp;</td>
 *  </tr>
 *  <tr>
 *      <td>MusicaChefe</td><td>&nbsp;</td>
 *  </tr>
 *  </table>
 * 
 *  &nbsp;&nbsp;<b>*</b> Substituir os espa�os da mensagem por underscores ("_").
 *
 *  <br><br>
 *
 *  @author  Paolo Victor, Helder Fernando, �lvaro Magnum, Fabr�cio Gutemberg,
 *           Dalton C�zane.
 *  @version 1.0
 */

public class FaseCreator
{
	/**
	 * Marcador de coment�rio.
	 */	
	private static final String COMENTARIO = new String("//");
	
	/**
	 * Indica qual linha est� sendo interpretada pelo programa.
	 */	
	private static int linhaSendoInterpretada = 1;
	
	/**
	 * O m�todo principal da classe.
	 */
	public static void main(String args[])
	{
		Vector linesRead = lerLinhas(args[0]);
		Vector eventos   = new Vector();
		Stack  eventosSalvos = new Stack();
		
		int eventoLido = 1;
		
		while(linesRead.size() > 0)
		{
			System.out.println("* Evento " + eventoLido );
			
			try
			{
				eventos.add(lerEvento(linesRead));
			}
			catch(Exception e)
			{
				System.out.println("Erro ao ler eventos: " + e.getMessage());
				System.exit(1);
			}
						
			eventoLido++;
		}
		
		while(eventos.size() > 0)
		{
			eventosSalvos.push(eventos.get(eventos.size() - 1));
			eventos.remove(eventos.size() - 1);
		}
		
		salvarFase(eventosSalvos, args[1]);
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
	public static Evento lerEvento(Vector linhas) throws Exception
	{
		StringTokenizer strTokenizer;
		
		String  nome, arquivoOrigem, tokenStr;
		int     numeroDeQuadros, velocidade, reinicioDoLoop;
		boolean loop;
		
		Evento evento = null;
		
		try
		{	
			while((tokenStr = pegarLinha(linhas)) == null);
			strTokenizer = new StringTokenizer(tokenStr, " ");
			
			// lendo o nome
			nome = strTokenizer.nextToken();
			System.out.println("Nome: " + nome);
			linhaSendoInterpretada++;
			
			if(nome.equals("CriarInimigo")) // Criar inimigo
			{
				Object[] atributos = new Object[3];
				
				// Tipo do inimigo
				atributos[0] = Integer.valueOf(strTokenizer.nextToken());
				System.out.println("Tipo do inimigo: " + atributos[0]);
				
				// Posi��o X
				atributos[1] = Integer.valueOf(strTokenizer.nextToken());
				System.out.println("Posi��o X: " + atributos[1]);
				
				// Posicao Y
				atributos[2] = Integer.valueOf(strTokenizer.nextToken());
				System.out.println("Posi��o Y: " + atributos[2]);
				
				// Criando o evento
				evento = new Evento(Evento.CRIAR_INIMIGO, atributos);
			}
			else if(nome.equals("MostrarMensagem")) // Mostrar mensagem
			{
				Object[] atributos = new Object[7];
				String   tempStr;
				
				// Mensagem
				atributos[0] = strTokenizer.nextElement();
				tempStr = (String)atributos[0];
				atributos[0] = tempStr.replace('_', ' ');
				
				System.out.println("Mensagem: " + atributos[0]);
				
				// Fonte
				atributos[1] = strTokenizer.nextElement();
				tempStr = (String)atributos[1];
				atributos[1] = tempStr.replace('_', ' ');
				
				System.out.println("Fonte: " + atributos[1]);
				
				// Tamanho
				atributos[2] = Integer.valueOf(strTokenizer.nextToken());
				
				System.out.println("Tamanho: " + atributos[2]);
				
				// Cor
				atributos[3] = strTokenizer.nextElement();
				System.out.println("Cor: " + atributos[3]);
				
				tempStr = (String)atributos[3];
				
				if(tempStr.equals("BRANCA"))
				{
					atributos[3] =  MensagemDeTexto.COR_BRANCA;
				}
				else if(tempStr.equals("VERMELHA"))
				{
					atributos[3] =  MensagemDeTexto.COR_VERMELHA;
				}
				else
				{
					atributos[3] =  MensagemDeTexto.COR_AMARELA;
				}
				
				// Tempo
				atributos[4] = Integer.valueOf(strTokenizer.nextToken());
				
				System.out.println("Tempo: " + atributos[4]);
				
				// Posi��o X
				atributos[5] = Integer.valueOf(strTokenizer.nextToken());
				
				System.out.println("Posi��o X: " + atributos[5]);
				
				// Posi��o Y
				atributos[6] = Integer.valueOf(strTokenizer.nextToken());
				
				System.out.println("Posi��o Y: " + atributos[6]);
				
				// Criando o evento
				evento = new Evento(Evento.MOSTRAR_MENSAGEM, atributos);
			}
			else if(nome.equals("AbrirCenario"))
			{
				Object[] atributos = new Object[1];
				
				// Quantidade
				atributos[0] = Integer.valueOf(strTokenizer.nextToken());				
				System.out.println("Quantidade: " + atributos[0]);
				
				evento = new Evento(Evento.ABRIR_CENARIO, atributos);
			}
			else if(nome.equals("EsperarMorte"))
			{				
				evento = new Evento(Evento.ESPERAR_MORTE, null);
			}
			else if(nome.equals("PassarDeFase"))
			{				
				evento = new Evento(Evento.PASSAR_DE_FASE, null);
			}else if(nome.equals("MusicaChefe"))
			{				
				evento = new Evento(Evento.MUSICA_CHEFE, null);
			}
		}
		catch(Exception e)
		{
			throw(new Exception("Dados inv�lidos na linha " + linhaSendoInterpretada));
		}
		
		return(evento);
	}
	
	/** 
	 *  M�todo que l� um arquivo de texto, gera e retorna um Vector de
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
	
	/** 
	 *  M�todo que salva a fase no arquivo <i>.evt</i>.
	 *  @param eventos A pilha de eventos..
	 *  @param nome O nome do arquivo <i>.ani</i>.
	 */
	public static void salvarFase(Stack eventos, String nome)
	{		
		ObjectOutputStream out = null;
	
		try
		{
			out = new ObjectOutputStream(new FileOutputStream(nome));
			out.writeObject(eventos);
		}
		catch(FileNotFoundException f)
		{
   			System.out.println("Erro ao salvar fase: " + f);
			System.exit(1);
   		}
        catch(IOException i)
        {
        	System.out.println("Erro ao salvar fase: " + i);
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
				System.out.println("Erro ao salvar fase: " + i.getMessage());
				System.exit(1);
			}
        }
	}
}
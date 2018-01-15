package br.com.ca.gpmcaieapp.tasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import br.com.ca.gpmcaieapp.R;
import br.com.ca.gpmcaieapp.base.IComunicacaoGeral;
import br.com.ca.gpmcaieapp.util.Apoio;
import br.com.ca.gpmcaieapp.util.DialogAlerta;
import br.com.ca.gpmcaieapp.util.LogTrace;
import br.com.ca.gpmcaieapp.util.RestCommunication;
import br.com.ca.gpmcaieapp.util.RestReturn;

public class TaskIpasealGet extends AsyncTask<Void, Boolean, Boolean>
{
	//Variaveis da classe
	private ProgressDialog progressDialog = null;
	private String sMensagem = "";
	private Context context = null;
	private IComunicacaoGeral comunicacaoGeral = null;
	private String sDadosEnvio = null;
	private String sToken = "";
	private boolean bExcecao = false;
	private String sTipoComunicacao = "";


	/**
	 * Construtor da classe
	 */
	public TaskIpasealGet(Context contextParam, IComunicacaoGeral comunicacaoGeralParam, String sDadosEnvioParam, String sTokenParam, String sTipoComunicacaoParam)
	{
		//Alimenta as variaveis da classe
		context = contextParam;
		comunicacaoGeral = comunicacaoGeralParam;
		sDadosEnvio = sDadosEnvioParam;
		sToken = sTokenParam;
		sTipoComunicacao = sTipoComunicacaoParam;

	}

	@Override
	protected void onPreExecute()
	{
		super.onPreExecute();

		//Cria o dialogo e exibe mensagem
		progressDialog = Apoio.criarProgressDialog(context);
		Apoio.progressDialogMensagem(progressDialog, context.getString(R.string.msg_seduc_obtendo));
	}

	@Override
	protected Boolean doInBackground(Void... voidArg)
	{
		boolean bRetorno = false;
		RestReturn restReturn = null;

		try
		{
			//Executa a comunicacao
			restReturn = RestCommunication.efetuaOperacaoRest(context.getString(R.string.url_conexao_gateway) + "gpm/ac/sefaz/dae", sTipoComunicacao, sDadosEnvio, Apoio.TIME_OUT_COMUNICACAO, true, sToken, context, "application/json");

			//Se for código 201 de retorno
			if ( restReturn.getCodigoRetorno() <= 201 )
			{
				//Obtem a mensagem de sucesso
				sMensagem = restReturn.getRetorno();

				//retorna true
				bRetorno = true;
			}
			else
			{
				LogTrace.escreve("Erro de comunicação - Codigo : " + restReturn.getCodigoRetorno() + " - Mensagem : " + restReturn.getMensagemRetorno(), Apoio.getPathLogsSDCard(context), Apoio.getArqErr());
				sMensagem = restReturn.getMensagemRetorno();
			}
		}
		catch (Exception err)
		{
			bExcecao = true;
			LogTrace.escreve(Apoio.localErro(getClass(), err), Apoio.getPathLogsSDCard(context), Apoio.getArqErr());
            sMensagem = Apoio.getMsgErr(err);
		}
		
		return bRetorno;
	}

	@Override
	protected void onPostExecute(Boolean bResult)
	{
		super.onPostExecute(bResult);

		try
		{
			// Caso haja sucesso do processo
			if ( bResult ) 
			{
				//Passa o controle com o resultado para a activity que fez a chamada
				comunicacaoGeral.comunicaGeral(getClass(), 0, true, sMensagem);
			}
			else
			{
				//Se não houve exceção
				if ( !bExcecao )
				{
					//Passa o controle com o resultado para a activity que fez a chamada
					comunicacaoGeral.comunicaGeral(getClass(), 0, false, sMensagem);
				}
				else
				{
					// Nao houve sucesso entao so mostra a mensagem resgatada
					DialogAlerta.show(context, sMensagem, context.getString(R.string.atencao), context.getString(R.string.ok));
				}
			}
		}
		catch(Exception err)
		{
			LogTrace.escreve(Apoio.localErro(getClass(), err), Apoio.getPathLogsSDCard(context), Apoio.getArqErr());
			DialogAlerta.show(context, Apoio.getMsgErr(getClass(), err), context.getString(R.string.atencao), context.getString(R.string.ok));
		}
		finally
		{
			// fecha o dialog
			Apoio.fecharProgressDialog(progressDialog);
		}
	}
}
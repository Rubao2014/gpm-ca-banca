package br.gov.al.itec.gpmapp.tasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import com.google.gson.Gson;

import br.gov.al.itec.gpmapp.R;
import br.gov.al.itec.gpmapp.base.IComunicacaoGeral;
import br.gov.al.itec.gpmapp.models.CommRespostaToken;
import br.gov.al.itec.gpmapp.util.Apoio;
import br.gov.al.itec.gpmapp.util.DialogAlerta;
import br.gov.al.itec.gpmapp.util.LogTrace;
import br.gov.al.itec.gpmapp.util.RestCommunication;
import br.gov.al.itec.gpmapp.util.RestReturn;

public class TaskObtemToken extends AsyncTask<Void, Boolean, Boolean>
{
	//Variaveis da classe	
	private ProgressDialog progressDialog = null;
	private CommRespostaToken commRespostaToken = null;
	private String sMensagem = "";
	private Context context = null;
	private IComunicacaoGeral comunicacaoGeral = null;
	private String sDadosEnvio = null;
	private boolean bExcecao = false;

	/**
	 * Construtor da classe
	 */
	public TaskObtemToken(Context contextParam, IComunicacaoGeral comunicacaoGeralParam, String sDadosEnvioParam)
	{
		//Alimenta as variaveis da classe
		context = contextParam;
		comunicacaoGeral = comunicacaoGeralParam;
		sDadosEnvio = sDadosEnvioParam;
	}

	@Override
	protected void onPreExecute()
	{
		super.onPreExecute();

		//Cria o dialogo e exibe mensagem
		progressDialog = Apoio.criarProgressDialog(context);
		Apoio.progressDialogMensagem(progressDialog, context.getString(R.string.msg_cadastro_sexo_obtendo_identificacao));
	}

	@Override
	protected Boolean doInBackground(Void... voidArg)
	{
		boolean bRetorno = false;
		RestReturn restReturn = null;
		Gson gson = null;

		try
		{
			//Executa a comunicacao
			restReturn = RestCommunication.efetuaOperacaoRest(context.getString(R.string.url_conexao_gateway) + "auth/oauth/v2/token", RestCommunication.RESTPOST, sDadosEnvio, Apoio.TIME_OUT_COMUNICACAO, false, "", context);

			//Se for código 200 de retorno
			if ( restReturn.getCodigoRetorno() <= 200 )
			{
				//Obtem a resposta com o token
				gson = new Gson();
				commRespostaToken = gson.fromJson(restReturn.getRetorno(), CommRespostaToken.class);

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
				comunicacaoGeral.comunicaGeral(getClass(), 0, true, commRespostaToken);
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
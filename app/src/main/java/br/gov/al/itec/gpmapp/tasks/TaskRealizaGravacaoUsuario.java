package br.gov.al.itec.gpmapp.tasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import br.gov.al.itec.gpmapp.R;
import br.gov.al.itec.gpmapp.base.IComunicacaoGeral;
import br.gov.al.itec.gpmapp.util.Apoio;
import br.gov.al.itec.gpmapp.util.DialogAlerta;
import br.gov.al.itec.gpmapp.util.LogTrace;
import br.gov.al.itec.gpmapp.util.RestCommunication;
import br.gov.al.itec.gpmapp.util.RestReturn;

public class TaskRealizaGravacaoUsuario extends AsyncTask<Void, Boolean, Boolean>
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
	public TaskRealizaGravacaoUsuario(Context contextParam, IComunicacaoGeral comunicacaoGeralParam, String sDadosEnvioParam, String sTokenParam, String sTipoComunicacaoParam)
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
		Apoio.progressDialogMensagem(progressDialog, context.getString(R.string.msg_cadastro_sexo_gravando_usuario));
	}

	@Override
	protected Boolean doInBackground(Void... voidArg)
	{
		boolean bRetorno = false;
		RestReturn restReturn = null;

		try
		{
			//Executa a comunicacao
			restReturn = RestCommunication.efetuaOperacaoRest(context.getString(R.string.url_conexao_gateway) + "cidadao/v1/pessoas", sTipoComunicacao, sDadosEnvio, Apoio.TIME_OUT_COMUNICACAO, true, sToken, context);

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
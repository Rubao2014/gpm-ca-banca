package br.com.ca.gpmcaieapp.util;

import android.content.Context;
import android.os.Handler;
import br.com.ca.gpmcaieapp.telas.FrmSplash;

/**
 * Classe auxiliar do handler para diversos pontos do sistema
 */
public class HandlerDiversos extends Handler implements Runnable
{
	//Variaveis da classe
	private Context context = null;

	/**
	 * Construtor da classe
	 */
	public HandlerDiversos(Context contextParam)
	{
		//Carrega as variaveis
		context = contextParam;
	}

	@Override
	public void run()
	{
		FrmSplash frmSplash = null;

		//Verifica a instancia do contexto
		if ( context instanceof FrmSplash )
		{
			//Obtem a classe e chama o método para solicitar as permissoes
			frmSplash = (FrmSplash)context;
			frmSplash.setaPermissoesAplicacao();
		}
	}
}
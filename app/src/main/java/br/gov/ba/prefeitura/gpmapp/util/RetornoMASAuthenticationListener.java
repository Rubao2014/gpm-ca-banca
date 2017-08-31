package br.gov.ba.prefeitura.gpmapp.util;

import android.content.Context;
import android.content.Intent;
import com.ca.mas.foundation.MASAuthenticationListener;
import com.ca.mas.foundation.MASOtpAuthenticationHandler;
import com.ca.mas.foundation.auth.MASAuthenticationProviders;
import br.gov.ba.prefeitura.gpmapp.R;
import br.gov.ba.prefeitura.gpmapp.telas.FrmLoginCallback;

/**
 * Classe para receber o listener de autenticação, se cair o login
 */
public class RetornoMASAuthenticationListener implements MASAuthenticationListener
{
    @Override
    public void onAuthenticateRequest(Context context, long lRequestId, MASAuthenticationProviders providers)
    {
        Intent intent = null;

        try
        {
            //Chama a tela de login
            intent = new Intent(context, FrmLoginCallback.class);
            context.startActivity(intent);
        }
        catch (Exception err)
        {
            LogTrace.escreve(Apoio.localErro(getClass(), err), Apoio.getPathLogsSDCard(context), Apoio.getArqErr());
            DialogAlerta.show(context, Apoio.getMsgErr(getClass(), err), context.getString(R.string.atencao), context.getString(R.string.ok));
        }
    }

    @Override
    public void onOtpAuthenticateRequest(Context context, MASOtpAuthenticationHandler handler)
    {
    }
}
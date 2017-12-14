package br.gov.al.itec.gpmapp.util;

import com.ca.mas.foundation.MASCallback;
import com.ca.mas.foundation.MASUser;

/**
 * Classe para extender o callback (user) e retornar a interface para a classe que fez a chamada
 */
public class RetornoMASCallbackUser extends MASCallback<MASUser>
{
    //Variáveis da classe
    private IRetornoMASCallbackUser retornoMASCallbackUser = null;

    /**
     * Construtor para receber a classe com a interface
     */
    public RetornoMASCallbackUser(IRetornoMASCallbackUser retornoMASCallbackUserParam)
    {
        //Atribui valores
        retornoMASCallbackUser = retornoMASCallbackUserParam;
    }

    @Override
    public void onSuccess(MASUser masuserUsuario)
    {
        //retorno de sucesso
        retornoMASCallbackUser.onSuccess(masuserUsuario);
    }

    @Override
    public void onError(Throwable throwable)
    {
        //retorno de erro
        retornoMASCallbackUser.onError(throwable);
    }
}
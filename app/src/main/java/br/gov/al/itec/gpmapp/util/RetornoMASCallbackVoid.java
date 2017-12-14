package br.gov.al.itec.gpmapp.util;

import com.ca.mas.foundation.MASCallback;

/**
 * Classe para extender o callback e retornar a interface para a classe que fez a chamada
 */
public class RetornoMASCallbackVoid extends MASCallback<Void>
{
    //Variáveis da classe
    private IRetornoMASCallbackVoid retornoMASCallbackVoid = null;

    /**
     * Construtor para receber a classe com a interface
     */
    public RetornoMASCallbackVoid(IRetornoMASCallbackVoid retornoMASCallbackVoidParam)
    {
        //Atribui valores
        retornoMASCallbackVoid = retornoMASCallbackVoidParam;
    }

    @Override
    public void onSuccess(Void voidParam)
    {
        //retorno de sucesso
        retornoMASCallbackVoid.onSuccess(voidParam);
    }

    @Override
    public void onError(Throwable throwable)
    {
        //retorno de erro
        retornoMASCallbackVoid.onError(throwable);
    }
}
package br.com.ca.gpmcaieapp.util;

import com.ca.mas.foundation.MASCallback;
import com.ca.mas.foundation.MASResponse;

/**
 * Classe para extender o callback e retornar a interface para a classe que fez a chamada
 */
public class RetornoMASCallbackString extends MASCallback<MASResponse<String>>
{
    //Variáveis da classe
    private IRetornoMASCallbackString retornoMASCallbackString = null;

    /**
     * Construtor para receber a classe com a interface
     */
    public RetornoMASCallbackString(IRetornoMASCallbackString retornoMASCallbackStringParam)
    {
        //Atribui valores
        retornoMASCallbackString = retornoMASCallbackStringParam;
    }

    @Override
    public void onSuccess(MASResponse<String> masresponseResultado)
    {
        //retorno de sucesso
        retornoMASCallbackString.onSuccess(masresponseResultado);
    }

    @Override
    public void onError(Throwable throwable)
    {
        //retorno de erro
        retornoMASCallbackString.onError(throwable);
    }
}
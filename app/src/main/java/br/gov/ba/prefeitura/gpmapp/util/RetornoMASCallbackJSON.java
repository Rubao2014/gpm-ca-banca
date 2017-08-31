package br.gov.ba.prefeitura.gpmapp.util;

import com.ca.mas.foundation.MASCallback;
import com.ca.mas.foundation.MASResponse;
import org.json.JSONObject;

/**
 * Classe para extender o callback (JSON) e retornar a interface para a classe que fez a chamada
 */
public class RetornoMASCallbackJSON extends MASCallback<MASResponse<JSONObject>>
{
    //Variáveis da classe
    private IRetornoMASCallbackJSON retornoMASCallbackJSON = null;

    /**
     * Construtor para receber a classe com a interface
     */
    public RetornoMASCallbackJSON(IRetornoMASCallbackJSON retornoMASCallbackJSONParam)
    {
        //Atribui valores
        retornoMASCallbackJSON = retornoMASCallbackJSONParam;
    }

    @Override
    public void onSuccess(MASResponse<JSONObject> masresponseObjeto)
    {
        //retorno de sucesso
        retornoMASCallbackJSON.onSuccess(masresponseObjeto, 0);
    }

    @Override
    public void onError(Throwable throwable)
    {
        //retorno de erro
        retornoMASCallbackJSON.onError(throwable);
    }
}
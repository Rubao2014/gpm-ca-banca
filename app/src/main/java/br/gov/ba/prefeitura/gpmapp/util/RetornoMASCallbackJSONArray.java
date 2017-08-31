package br.gov.ba.prefeitura.gpmapp.util;

import com.ca.mas.foundation.MASCallback;
import com.ca.mas.foundation.MASResponse;
import org.json.JSONArray;

/**
 * Classe para extender o callback (JSON Array) e retornar a interface para a classe que fez a chamada
 */
public class RetornoMASCallbackJSONArray extends MASCallback<MASResponse<JSONArray>>
{
    //Variáveis da classe
    private IRetornoMASCallbackJSONArray retornoMASCallbackJSONArray = null;

    /**
     * Construtor para receber a classe com a interface
     */
    public RetornoMASCallbackJSONArray(IRetornoMASCallbackJSONArray retornoMASCallbackJSONArrayParam)
    {
        //Atribui valores
        retornoMASCallbackJSONArray = retornoMASCallbackJSONArrayParam;
    }

    @Override
    public void onSuccess(MASResponse<JSONArray> jsonArrayResultado)
    {
        //retorno de sucesso
        retornoMASCallbackJSONArray.onSuccess(jsonArrayResultado);
    }

    @Override
    public void onError(Throwable throwable)
    {
        //retorno de erro
        retornoMASCallbackJSONArray.onError(throwable);
    }
}
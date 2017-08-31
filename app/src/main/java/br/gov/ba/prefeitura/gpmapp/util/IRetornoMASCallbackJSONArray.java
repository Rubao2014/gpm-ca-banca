package br.gov.ba.prefeitura.gpmapp.util;

import com.ca.mas.foundation.MASResponse;
import org.json.JSONArray;

/**
 * Interface para retorno do call back (JSON Array) da classe do MAS
 */
public interface IRetornoMASCallbackJSONArray
{
    /**
     * Retorno de sucesso com o usuário
     */
    void onSuccess(MASResponse<JSONArray> jsonArrayResultado);

    /**
     * Retorno de erro com a exceção
     */
    void onError(Throwable throwable);
}

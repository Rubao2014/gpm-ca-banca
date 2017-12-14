package br.gov.al.itec.gpmapp.util;

import com.ca.mas.foundation.MASResponse;
import org.json.JSONObject;

/**
 * Interface para retorno do call back (JSON) da classe do MAS
 */
public interface IRetornoMASCallbackJSON
{
    /**
     * Retorno de sucesso com o usuário
     */
    void onSuccess(MASResponse<JSONObject> masresponseObjeto, int iRetorno);

    /**
     * Retorno de erro com a exceção
     */
    void onError(Throwable throwable);
}

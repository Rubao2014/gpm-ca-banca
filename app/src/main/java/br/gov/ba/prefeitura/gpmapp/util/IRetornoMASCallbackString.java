package br.gov.ba.prefeitura.gpmapp.util;

import com.ca.mas.foundation.MASResponse;

/**
 * Interface para retorno do call back (string) da classe do MAS
 */
public interface IRetornoMASCallbackString
{
    /**
     * Retorno de sucesso com o usuário
     */
    void onSuccess(MASResponse<String> masresponseResultado);

    /**
     * Retorno de erro com a exceção
     */
    void onError(Throwable throwable);
}

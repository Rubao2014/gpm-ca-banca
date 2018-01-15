package br.com.ca.gpmcaieapp.util;

import com.ca.mas.foundation.MASUser;

/**
 * Interface para retorno do call back (user) da classe do MAS
 */
public interface IRetornoMASCallbackUser
{
    /**
     * Retorno de sucesso com o usuário
     */
    void onSuccess(MASUser masuserUsuario);

    /**
     * Retorno de erro com a exceção
     */
    void onError(Throwable throwable);
}

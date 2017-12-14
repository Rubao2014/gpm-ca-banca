package br.gov.al.itec.gpmapp.util;

/**
 * Interface para retorno do call back (void) da classe do MAS
 */
public interface IRetornoMASCallbackVoid
{
    /**
     * Retorno de sucesso com o usuário
     */
    void onSuccess(Void voidParam);

    /**
     * Retorno de erro com a exceção
     */
    void onError(Throwable throwable);
}

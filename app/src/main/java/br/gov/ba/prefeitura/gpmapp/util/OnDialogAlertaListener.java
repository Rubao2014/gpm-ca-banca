package br.gov.ba.prefeitura.gpmapp.util;

import android.content.DialogInterface;

/**
 * Listener do Dialog Alerta
 */
public interface OnDialogAlertaListener
{
    /**
     * Metodo criado para retorno das nossas aplicacao
     */
    public void onDialogClick(DialogInterface oDialogInterface, int iIDBotao, int iIdentificacao);
}

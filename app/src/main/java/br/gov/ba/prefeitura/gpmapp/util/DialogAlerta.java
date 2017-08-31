package br.gov.ba.prefeitura.gpmapp.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.view.KeyEvent;
import android.view.WindowManager;

/**
 *
 * @author marcio
 */
public class DialogAlerta implements DialogInterface.OnClickListener
{
    //Variaveis da classe
    private OnDialogAlertaListener _oConfListener = null;
    private int _iIdentificacao = -1;
    private AlertDialog _frmExib = null;

    /**
     * Exibe mensagem (alerta)    
     * @param oContext - Contexto da tela
     * @param sMensagem - Mensagem a ser exibida
     * @param sTitulo - Titulo do alerta
     * @param sTituloBotaoOK - Titulo do botao OK
     */
    public static void show(Context oContext, String sMensagem, String sTitulo, String sTituloBotaoOK)
    {
        AlertDialog.Builder frmAlerta = null;
        AlertDialog frmExib = null;

        //Cria formulario
        frmAlerta = new AlertDialog.Builder(oContext);
        frmAlerta.setMessage(sMensagem);
        frmAlerta.setTitle(sTitulo);
        frmAlerta.setCancelable(false);
        
        //Seta o botao e seu evento
        frmAlerta.setPositiveButton(sTituloBotaoOK, new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int id)
            {
                dialog.cancel();
            }
        });

        //Faz a criacao do alerta
        frmExib = frmAlerta.create();
        
        //Bloqueia as demais teclas
        frmExib.setOnKeyListener(new DialogInterface.OnKeyListener() 
        {
            public boolean onKey(DialogInterface arg0, int arg1, KeyEvent arg2)
            {
               return true;
            }
        }); 
        
        //Exibe o dialog
        frmExib.show();
        
        //Se n�o for a vers�o Ice cream sandwich bloqueamos
        if (Build.VERSION.SDK_INT < 14)
        {
            frmExib.getWindow().setType(WindowManager.LayoutParams.TYPE_KEYGUARD_DIALOG);
        }
    }

    /**
     * Exibe mensagem (confirmacao)
     * @param oContext - Contexto da tela
     * @param sMensagem - Mensagem a ser exibida
     * @param sTitulo - Titulo do alerta
     * @param sTituloBotaoOK - Titulo do botao OK
     * @param sTituloBotaoCancelar - Titulo do botao cancelar
     * @param iIdentificacao - Identificacao para retorno
     */
    public void show(Context oContext, String sMensagem, String sTitulo, String sTituloBotaoOK, String sTituloBotaoCancelar, int iIdentificacao)
    {
        AlertDialog.Builder frmAlerta = null;

        //Atribui o listener
        _oConfListener = (OnDialogAlertaListener)oContext;
        _iIdentificacao = iIdentificacao;

        //Cria formulario
        frmAlerta = new AlertDialog.Builder(oContext);
        frmAlerta.setMessage(sMensagem);
        frmAlerta.setTitle(sTitulo);
        frmAlerta.setCancelable(false);

        //add buttons and listener
        frmAlerta.setPositiveButton(sTituloBotaoOK, this);
        frmAlerta.setNegativeButton(sTituloBotaoCancelar, this);

        //Faz a criacao do alerta
        _frmExib = frmAlerta.create();
        
        //Bloqueia as demais teclas
        _frmExib.setOnKeyListener(new DialogInterface.OnKeyListener() 
        {
            public boolean onKey(DialogInterface arg0, int arg1, KeyEvent arg2)
            {
               return true;
            }
        }); 
        
        //Exibe o dialog
        _frmExib.show();
        
        //Se nao for a versao Ice cream sandwich bloqueamos
        if (Build.VERSION.SDK_INT < 14)
        {
            _frmExib.getWindow().setType(WindowManager.LayoutParams.TYPE_KEYGUARD_DIALOG);
        }
    }

    /**
     * Exibe mensagem (confirmacao)
     * @param oContext - Contexto da tela
     * @param sMensagem - Mensagem a ser exibida
     * @param sTitulo - Titulo do alerta
     * @param sTituloBotaoOK - Titulo do botao OK
     * @param sTituloBotaoCancelar - Titulo do botao cancelar
     * @param iIdentificacao - Identificacao para retorno
     */
    public void show(Context oContext, OnDialogAlertaListener oListener, String sMensagem, String sTitulo, String sTituloBotaoOK, String sTituloBotaoCancelar, int iIdentificacao)
    {
        AlertDialog.Builder frmAlerta = null;

        //Atribui o listener
        _oConfListener = oListener;
        _iIdentificacao = iIdentificacao;

        //Cria formulario
        frmAlerta = new AlertDialog.Builder(oContext);
        frmAlerta.setMessage(sMensagem);
        frmAlerta.setTitle(sTitulo);
        frmAlerta.setCancelable(false);

        //add buttons and listener
        frmAlerta.setPositiveButton(sTituloBotaoOK, this);
        frmAlerta.setNegativeButton(sTituloBotaoCancelar, this);

        //Faz a criacao do alerta
        _frmExib = frmAlerta.create();
        
        //Bloqueia as demais teclas
        _frmExib.setOnKeyListener(new DialogInterface.OnKeyListener() 
        {
            public boolean onKey(DialogInterface arg0, int arg1, KeyEvent arg2)
            {
               return true;
            }
        }); 
        
        //Exibe o dialog
        _frmExib.show();
        
        //Se nao for a versao Ice cream sandwich bloqueamos
        if (Build.VERSION.SDK_INT < 14)
        {
            _frmExib.getWindow().setType(WindowManager.LayoutParams.TYPE_KEYGUARD_DIALOG);
        }
    }
    
    public void onClick(DialogInterface oDialogInterface, int iIDBotao)
    {
    	//Se o formulario estiver sendo exibido
    	if(_frmExib.isShowing())
    	{    	
    		//Chama o listener criado por nós com o parametro novo da identificacao da janela
    		_oConfListener.onDialogClick(oDialogInterface, iIDBotao, _iIdentificacao);
    	}
    }
}
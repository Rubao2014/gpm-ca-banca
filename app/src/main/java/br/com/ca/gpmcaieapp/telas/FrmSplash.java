package br.com.ca.gpmcaieapp.telas;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.WindowManager;
import android.widget.Toast;
import br.com.ca.gpmcaieapp.R;
import br.com.ca.gpmcaieapp.base.ActivityBase;
import br.com.ca.gpmcaieapp.util.Apoio;
import br.com.ca.gpmcaieapp.util.HandlerDiversos;

/**
 * Classe FrmSplash
 */
public class FrmSplash extends ActivityBase
{
    @Override
    protected void onCreate(Bundle bundleSavedInstanceState)
    {
        //Esconde barra do android
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Carrega a tela
        setContentView(R.layout.frm_splash);

        //chama os métodos automáticos da classe pai
        super.onCreate(bundleSavedInstanceState);
    }

    @Override
    public void iniciaControles() throws Exception
    {
    }

    @Override
    public void carregaDados() throws Exception
    {
        HandlerDiversos handlerDiversos = null;

        //Chama a classe que faz o handler, criando o runnable nela mesmo
        handlerDiversos = new HandlerDiversos(this);
        handlerDiversos.postDelayed(handlerDiversos, Apoio.TEMPO_DISPLAY_SPLASH);
    }

    /**
     * Seta as permissões necessárias para a aplicação
     */
    public void setaPermissoesAplicacao()
    {
        int iPermissoes = PackageManager.PERMISSION_GRANTED;
        String[] sPermissoes =  new String[] { Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.INTERNET, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_SMS};
        Intent intent = null;

        try
        {
            //Se for do Android 6 para frente (API 23)
            if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            {
                //Obtem as permissões
                iPermissoes += ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                iPermissoes += ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
                iPermissoes += ActivityCompat.checkSelfPermission(this, Manifest.permission.INTERNET);
                iPermissoes += ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
                iPermissoes += ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);
                iPermissoes += ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_SMS);


                //Se tiver permitido todas as opções
                if ( iPermissoes == PackageManager.PERMISSION_GRANTED )
                {
                    //Chama a tela principal
                    intent = new Intent(this, FrmMainActivity.class);
                    startActivityForResult(intent, Apoio.RETORNO_TELA_LOGIN);
                }
                else
                {
                    //Faz a requisição de permissão para gravar no cartão de memória para a API 23 ou superior
                    ActivityCompat.requestPermissions(this, sPermissoes, Apoio.REQUISICAO_PERMISSAO_GRAVACAO_SD_CARD);
                }
            }
            else
            {
                //Chama a tela principal
                intent = new Intent(this, FrmMainActivity.class);
                startActivityForResult(intent, Apoio.RETORNO_TELA_LOGIN);
            }
        }
        catch(Exception err)
        {
            Toast.makeText(this, Apoio.getMsgErr(this.getClass(), err), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int iRequestCode, String sPermissions[], int[] iGrantResults)
    {
        boolean bCarregar = true;
        int iCont = 0;
        Intent intent = null;

        try
        {
            //De acordo com o código de requisição
            switch (iRequestCode)
            {
                //Se for a requsição de gravação no SD CARD
                case Apoio.REQUISICAO_PERMISSAO_GRAVACAO_SD_CARD:
                {
                    //Se tiver permissões
                    if ( iGrantResults.length > 0 )
                    {
                        //Loop pelas permissões
                        for ( iCont = 0; iCont < iGrantResults.length; iCont++ )
                        {
                            //Se todas foram permitidas
                            bCarregar &= (iGrantResults[iCont] == PackageManager.PERMISSION_GRANTED);
                        }
                    }
                    else
                    {
                        bCarregar = false;
                    }

                    //Se for para sair
                    if ( !bCarregar )
                    {
                        //Finaliza o app
                        Apoio.finalizaActivity(this, RESULT_CANCELED);
                        return;
                    }

                    //Chama a tela principal
                    intent = new Intent(this, FrmMainActivity.class);
                    startActivityForResult(intent, Apoio.RETORNO_TELA_LOGIN);
                    break;
                }
            }
        }
        catch(Exception err)
        {
            Toast.makeText(this, Apoio.getMsgErr(getClass(), err), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int iRequest, int iResultado, Intent intentData)
    {
        super.onActivityResult(iRequest, iResultado, intentData);

        try
        {
            //verifico se é positiva a resposta
            if(iRequest == Apoio.RETORNO_TELA_LOGIN)
            {
                //verifico se é positiva a resposta
                if (iResultado == RESULT_OK)
                {
                    //Finaliza a activity
                    Apoio.finalizaActivity(this, RESULT_OK);
                }
            }
        }
        catch(Exception err)
        {
            Toast.makeText(this, Apoio.getMsgErr(getClass(), err), Toast.LENGTH_LONG).show();
        }
    }
}
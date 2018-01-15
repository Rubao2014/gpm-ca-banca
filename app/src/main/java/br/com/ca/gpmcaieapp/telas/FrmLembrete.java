package br.com.ca.gpmcaieapp.telas;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.ca.mas.foundation.MASUser;
import br.com.ca.gpmcaieapp.R;
import br.com.ca.gpmcaieapp.base.ActivityBase;
import br.com.ca.gpmcaieapp.util.Apoio;
import br.com.ca.gpmcaieapp.util.DialogAlerta;
import br.com.ca.gpmcaieapp.util.IRetornoMASCallbackVoid;
import br.com.ca.gpmcaieapp.util.LogTrace;
import br.com.ca.gpmcaieapp.util.RetornoMASCallbackVoid;

/**
 * Classe FrmLembrete
 */
public class FrmLembrete extends ActivityBase implements View.OnClickListener, IRetornoMASCallbackVoid
{
    // Controles da classe
    private Toolbar toolbar = null;
    private Button cmdEntrarMesmoAssim = null;
    private Button cmdPossuiConta = null;
    private Button cmdCriaConta = null;
    private CoordinatorLayout coordinatorLayoutLembrete = null;
    private ProgressDialog progressDialogLogout = null;

    @Override
    protected void onCreate(Bundle bundleSavedInstanceState)
    {
        // Carrega a tela
        setContentView(R.layout.frm_lembrete);

        //chama os métodos automáticos da classe pai
        super.onCreate(bundleSavedInstanceState);
    }

    @Override
    public void iniciaControles() throws Exception
    {
        Drawable drawableArrow = null;

        // Obtem os controles
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        cmdEntrarMesmoAssim = (Button) findViewById(R.id.cmdEntrarMesmoAssim);
        cmdCriaConta = (Button) findViewById(R.id.cmdCriaConta);
        cmdPossuiConta = (Button) findViewById(R.id.cmdPossuiConta);
        coordinatorLayoutLembrete = (CoordinatorLayout) findViewById(R.id.coordinatorLayoutLembrete);

        //Muda a cor da flecha padrão do Android para branco
        drawableArrow = ContextCompat.getDrawable(this, R.drawable.abc_ic_ab_back_material);
        drawableArrow.setColorFilter(ContextCompat.getColor(this, R.color.branco), PorterDuff.Mode.SRC_ATOP);

        //Seta o navigation item
        toolbar.setNavigationIcon(drawableArrow);

        //Troca a cor do botao de voltar
        toolbar.setNavigationContentDescription(Apoio.TOOLBAR_VOLTAR);

        //Seta o listener dos controles
        cmdEntrarMesmoAssim.setOnClickListener(this);
        cmdPossuiConta.setOnClickListener(this);
        cmdCriaConta.setOnClickListener(this);

        //Coloca a toolbar como actionbar e adiciona as opções de menu
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public void carregaDados() throws Exception
    {
    }

    /**
     * Chama a tela do dashboard
     */
    private void segueDashBoardDeslogado() throws Exception
    {
        MASUser masuserUsuario = null;

        //Obtem o usuário
        masuserUsuario = Apoio.verificaUsuarioLogado(this);

        //Se existir um usuário
        if ( masuserUsuario != null )
        {
            //Cria o dialogo e exibe mensagem
            progressDialogLogout = Apoio.criarProgressDialog(this);
            Apoio.progressDialogMensagem(progressDialogLogout, getString(R.string.msg_lembrete_logout));

            //Realiza o logout do usuário
            masuserUsuario.logout(new RetornoMASCallbackVoid(this));
        }
        else
        {
            //Chama a tela de dashboard
            chamaTelaDashboard();
        }
    }

    /**
     * Chama a tela do dashboard
     */
    private void chamaTelaDashboard() throws Exception
    {
        Intent intent = null;

        //Chama a tela dashboard
        intent = new Intent(this, FrmDashboard.class);
        startActivityForResult(intent, Apoio.RETORNO_TELA_DASHBOARD);
    }

    /**
     *  Chama a tela de bem vindo
     */
    private void chamaTelaBemVindo() throws Exception
    {
        Intent intent = null;

        // chama a tela de bem vindo
        intent = new Intent(this, FrmCadastroInicio.class);
        startActivityForResult(intent, Apoio.RETORNO_TELA_DETRAN);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem mnuItem)
    {
        // Instanciando os itens passados no menu inflate
        super.onOptionsItemSelected(mnuItem);

        try
        {
            // Pego o id da opcao selecionada
            switch (mnuItem.getItemId())
            {
                //Verifica se clicou no botao de return da actionbar/toolbar
                case android.R.id.home:
                {
                    // Seta o resultado e finaliza
                    Apoio.finalizaActivity(this, RESULT_CANCELED);
                    break;
                }
            }
        }
        catch (Exception err)
        {
            LogTrace.escreve(Apoio.localErro(getClass(), err), Apoio.getPathLogsSDCard(this), Apoio.getArqErr());
            DialogAlerta.show(this, Apoio.getMsgErr(getClass(), err), getString(R.string.atencao), getString(R.string.ok));
        }

        return true;
    }

    @Override
    public void onClick(View view)
    {
        try
        {
            //Se clicou no criar conta
            if (view == cmdCriaConta)
            {
                // Chama a tela de Bem Vindo
                chamaTelaBemVindo();
            }
            //Se clicou no entrar mesmo assim
            else if (view == cmdEntrarMesmoAssim)
            {
                //Chama o metodo para deslogar e seguir para o dashboard
                segueDashBoardDeslogado();
            }
            //Se clicou no possui conta
            else if (view == cmdPossuiConta)
            {
                // Volta para a tela de login
                Apoio.finalizaActivity(this, RESULT_OK);
            }
        }
        catch (Exception err)
        {
            LogTrace.escreve(Apoio.localErro(getClass(), err), Apoio.getPathLogsSDCard(this), Apoio.getArqErr());
            DialogAlerta.show(this, Apoio.getMsgErr(getClass(), err), getString(R.string.atencao), getString(R.string.ok));
        }
    }

    @Override
    protected void onActivityResult(int iRequest, int iResultado, Intent intentData)
    {
        super.onActivityResult(iRequest, iResultado, intentData);

        try
        {
            //verifico se é positiva a resposta
            if(iRequest == Apoio.RETORNO_TELA_DETRAN)
            {
                //verifico se é positiva a resposta
                if (iResultado == RESULT_OK)
                {
                    //Finaliza a activity
                    Apoio.finalizaActivity(this, RESULT_OK);
                }
            }
            //verifico se é positiva a resposta
            else if(iRequest == Apoio.RETORNO_TELA_DASHBOARD)
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
            LogTrace.escreve(Apoio.localErro(getClass(), err), Apoio.getPathLogsSDCard(this), Apoio.getArqErr());
            DialogAlerta.show(this, Apoio.getMsgErr(getClass(), err), getString(R.string.atencao), getString(R.string.ok));
        }
    }

    @Override
    public void onSuccess(Void voidParam)
    {
        try
        {
            // fecha o dialog
            Apoio.fecharProgressDialog(progressDialogLogout);

            //Grava a opção de manter conectado
            Apoio.gravaPrefsValorBooleano(this, Apoio.PREFS_MANTER_CONECTADO, false);

            //Chama a tela de dashboard com usuario
            chamaTelaDashboard();
        }
        catch (Exception err)
        {
            LogTrace.escreve(Apoio.localErro(getClass(), err), Apoio.getPathLogsSDCard(this), Apoio.getArqErr());
            Toast.makeText(this, Apoio.getMsgErr(this.getClass(), err), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onError(Throwable throwable)
    {
        Snackbar snackbarErro = null;

        try
        {
            // fecha o dialog
            Apoio.fecharProgressDialog(progressDialogLogout);

            //Loga o erro retornado pela CA
            LogTrace.escreve(Apoio.localErro(getClass(), throwable), Apoio.getPathLogsSDCard(this), Apoio.getArqErr());

            //Monta snackbar com erro
            snackbarErro = Snackbar.make(coordinatorLayoutLembrete, getString(R.string.msg_lembrete_impossivel_logout), Snackbar.LENGTH_LONG);
            snackbarErro.show();
        }
        catch (Exception err)
        {
            LogTrace.escreve(Apoio.localErro(getClass(), err), Apoio.getPathLogsSDCard(this), Apoio.getArqErr());
            Toast.makeText(this, Apoio.getMsgErr(this.getClass(), err), Toast.LENGTH_LONG).show();
        }
    }
}
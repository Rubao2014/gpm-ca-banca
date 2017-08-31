package br.gov.ba.prefeitura.gpmapp.telas;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ca.mas.foundation.MASUser;
import br.gov.ba.prefeitura.gpmapp.R;
import br.gov.ba.prefeitura.gpmapp.base.ActivityBase;
import br.gov.ba.prefeitura.gpmapp.util.Apoio;
import br.gov.ba.prefeitura.gpmapp.util.DialogAlerta;
import br.gov.ba.prefeitura.gpmapp.util.IRetornoMASCallbackUser;
import br.gov.ba.prefeitura.gpmapp.util.LogTrace;
import br.gov.ba.prefeitura.gpmapp.util.RetornoMASCallbackUser;

/**
 * Classe FrmLoginCallback
 */
public class FrmLoginCallback extends ActivityBase implements View.OnClickListener, IRetornoMASCallbackUser
{
    // Controles da classe
    private Toolbar toolbar = null;
    private TextInputLayout inputEmail = null;
    private EditText txtEmail = null;
    private TextInputLayout inputSenha = null;
    private EditText txtSenha = null;
    private Button cmdEntrar = null;
    private CoordinatorLayout coordinatorLayoutMain = null;
    private ProgressDialog progressDialogLogin = null;

    @Override
    protected void onCreate(Bundle bundleSavedInstanceState)
    {
        // Carrega a tela
        setContentView(R.layout.frm_login_callback);

        //chama os métodos automáticos da classe pai
        super.onCreate(bundleSavedInstanceState);
    }

    @Override
    public void iniciaControles() throws Exception
    {
        Drawable drawableArrow = null;

        // Obtem os controles
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        inputEmail = (TextInputLayout) findViewById(R.id.inputEmail);
        txtEmail = (EditText) findViewById(R.id.txtEmail);
        inputSenha = (TextInputLayout) findViewById(R.id.inputSenha);
        txtSenha = (EditText) findViewById(R.id.txtSenha);
        cmdEntrar = (Button) findViewById(R.id.cmdEntrar);
        coordinatorLayoutMain = (CoordinatorLayout) findViewById(R.id.coordinatorLayoutMain);

        //Muda a cor da flecha padrão do Android para branco
        drawableArrow = ContextCompat.getDrawable(this, R.drawable.abc_ic_ab_back_material);
        drawableArrow.setColorFilter(ContextCompat.getColor(this, R.color.branco), PorterDuff.Mode.SRC_ATOP);

        //Seta o navigation item
        toolbar.setNavigationIcon(drawableArrow);

        //Troca a cor do botao de voltar
        toolbar.setNavigationContentDescription(Apoio.TOOLBAR_VOLTAR);

        //Seta o listener dos controles
        cmdEntrar.setOnClickListener(this);

        //Coloca a toolbar como actionbar e adiciona as opções de menu
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public void carregaDados() throws Exception
    {
        //Mostra o teclado
        ((InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE)).toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

        //Seta o foco no controle
        txtEmail.requestFocus();
    }

    /**
     * Remove todos os erros dos InputTextLayout
     */
    private void removerErroDosCampos() throws Exception
    {
        //Tira o erro
        inputEmail.setError("");
        inputEmail.setErrorEnabled(false);
        inputSenha.setError("");
        inputSenha.setErrorEnabled(false);
    }

    /**
     * Valida o login do usuário com as bibliotecas da CA
     */
    private void validaLoginUsuario() throws Exception
    {
        String sEmail = "";
        String sSenha = "";
        View view = null;
        InputMethodManager inputMethodManager = null;

        //Remove os erros atuais dos campos
        removerErroDosCampos();

        //Valida os campos da tela
        if (!validaCamposTela())
        {
            return;
        }

        //Cria o dialogo e exibe mensagem
        progressDialogLogin = Apoio.criarProgressDialog(this);
        Apoio.progressDialogMensagem(progressDialogLogin, getString(R.string.msg_login_validando_usuario));

        //Obtem o e-mail e a senha
        sEmail = txtEmail.getText().toString().trim();
        sSenha = txtSenha.getText().toString().trim();

        //Limpa os campos de usuário e senha
        txtEmail.setText("");
        txtSenha.setText("");
        txtEmail.requestFocus();

        //Se tiver uma view
        view = this.getCurrentFocus();
        if (view != null)
        {
            //Remove o teclado
            inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

        //Realiza o login do usuário
        MASUser.login(sEmail, sSenha, new RetornoMASCallbackUser(this));
    }

    /**
     * Valida os campos da tela
     */
    private boolean validaCamposTela() throws Exception
    {
        String sAuxi = "";

        //Valida o campo de email
        sAuxi = txtEmail.getText().toString().trim();
        if (sAuxi.length() <= 0)
        {
            //Coloca a mensagem de erro embaixo do campo e solicita o foco
            inputEmail.setError(getString(R.string.msg_login_email_em_branco));
            inputEmail.setErrorEnabled(true);
            Apoio.requisitarFoco(txtEmail, this);
            return false;
        }
        else
        {
            //Se estier OK, remove o erro
            inputEmail.setError("");
            inputEmail.setErrorEnabled(false);
        }

        //Se o email não for valido
        /*if (!sAuxi.matches(Apoio.EMAIL_PATTERN))
        {
            //Coloca a mensagem de erro embaixo do campo e solicita o foco
            inputEmail.setError(getString(R.string.msg_login_email_invalido));
            inputEmail.setErrorEnabled(true);
            Apoio.requisitarFoco(txtEmail, this);
            return false;
        }
        else
        {
            //Se estier OK, remove o erro
            inputEmail.setError("");
            inputEmail.setErrorEnabled(false);
        }*/

        //Valida o campo de senha
        if (txtSenha.getText().toString().trim().equals(""))
        {
            //Coloca a mensagem de erro embaixo do campo e solicita o foco
            inputSenha.setError(getString(R.string.msg_login_senha_em_branco));
            inputSenha.setErrorEnabled(true);
            Apoio.requisitarFoco(txtSenha, this);
            return false;
        }
        else
        {
            //Se estier OK, remove o erro
            inputSenha.setError("");
            inputSenha.setErrorEnabled(false);
        }

        return true;
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
            //Quando apertar o botão entrar
            if (view == cmdEntrar)
            {
                //valida o login do usuário
                validaLoginUsuario();
            }
        }
        catch (Exception err)
        {
            LogTrace.escreve(Apoio.localErro(getClass(), err), Apoio.getPathLogsSDCard(this), Apoio.getArqErr());
            DialogAlerta.show(this, Apoio.getMsgErr(getClass(), err), getString(R.string.atencao), getString(R.string.ok));
        }
    }

    @Override
    public void onSuccess(MASUser masuserUsuario)
    {
        try
        {
            // fecha o dialog
            Apoio.fecharProgressDialog(progressDialogLogin);

            // Seta o resultado e finaliza
            Apoio.finalizaActivity(this, RESULT_OK);
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
            Apoio.fecharProgressDialog(progressDialogLogin);

            //Loga o erro retornado pela CA
            LogTrace.escreve(Apoio.localErro(getClass(), throwable), Apoio.getPathLogsSDCard(this), Apoio.getArqErr());

            //Monta snackbar com erro
            snackbarErro = Snackbar.make(coordinatorLayoutMain, getString(R.string.msg_login_impossivel_validar), Snackbar.LENGTH_LONG);
            snackbarErro.show();
        }
        catch (Exception err)
        {
            LogTrace.escreve(Apoio.localErro(getClass(), err), Apoio.getPathLogsSDCard(this), Apoio.getArqErr());
            Toast.makeText(this, Apoio.getMsgErr(this.getClass(), err), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBackPressed()
    {
        try
        {
            // Seta o resultado e finaliza
            Apoio.finalizaActivity(this, RESULT_CANCELED);
        }
        catch (Exception err)
        {
            LogTrace.escreve(Apoio.localErro(getClass(), err), Apoio.getPathLogsSDCard(this), Apoio.getArqErr());
            DialogAlerta.show(this, Apoio.getMsgErr(getClass(), err), getString(R.string.atencao), getString(R.string.ok));
        }
    }
}
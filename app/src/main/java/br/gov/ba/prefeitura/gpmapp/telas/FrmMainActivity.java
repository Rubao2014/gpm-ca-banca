package br.gov.ba.prefeitura.gpmapp.telas;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import com.ca.mas.foundation.MAS;
import com.ca.mas.foundation.MASUser;
import br.gov.ba.prefeitura.gpmapp.R;
import br.gov.ba.prefeitura.gpmapp.base.ActivityBase;
import br.gov.ba.prefeitura.gpmapp.util.Apoio;
import br.gov.ba.prefeitura.gpmapp.util.DialogAlerta;
import br.gov.ba.prefeitura.gpmapp.util.IRetornoMASCallbackUser;
import br.gov.ba.prefeitura.gpmapp.util.LogTrace;
import br.gov.ba.prefeitura.gpmapp.util.RetornoMASAuthenticationListener;
import br.gov.ba.prefeitura.gpmapp.util.RetornoMASCallbackUser;

/**
 * Classe FrmMainActivity
 */
public class FrmMainActivity extends ActivityBase implements View.OnClickListener, IRetornoMASCallbackUser, Runnable
{
    // Controles da classe
    private TextInputLayout inputEmail = null;
    private EditText txtEmail = null;
    private TextInputLayout inputSenha = null;
    private EditText txtSenha = null;
    private Button cmdEntrar = null;
    private Switch switchManterConectado = null;
    private Button cmdRegistrar = null;
    private TextView lblEntrarMesmoAssim = null;
    private TextView lblEsqueciMinhaSenha = null;
    private CoordinatorLayout coordinatorLayoutMain = null;
    private ProgressDialog progressDialogLogin = null;

    @Override
    protected void onCreate(Bundle bundleSavedInstanceState)
    {
        // Carrega a tela
        setContentView(R.layout.frm_main_activity);

        //chama os métodos automáticos da classe pai
        super.onCreate(bundleSavedInstanceState);
    }

    @Override
    public void iniciaControles() throws Exception
    {
        // Obtem os controles
        inputEmail = (TextInputLayout) findViewById(R.id.inputEmail);
        txtEmail = (EditText) findViewById(R.id.txtEmail);
        inputSenha = (TextInputLayout) findViewById(R.id.inputSenha);
        txtSenha = (EditText) findViewById(R.id.txtSenha);
        cmdEntrar = (Button) findViewById(R.id.cmdEntrar);
        switchManterConectado = (Switch) findViewById(R.id.switchManterConectado);
        cmdRegistrar = (Button) findViewById(R.id.cmdRegistrar);
        lblEntrarMesmoAssim = (TextView) findViewById(R.id.lblEntrarMesmoAssim);
        lblEsqueciMinhaSenha = (TextView) findViewById(R.id.lblEsqueciMinhaSenha);
        coordinatorLayoutMain = (CoordinatorLayout) findViewById(R.id.coordinatorLayoutMain);

        //Seta o listener dos controles
        cmdEntrar.setOnClickListener(this);
        cmdRegistrar.setOnClickListener(this);
        lblEntrarMesmoAssim.setOnClickListener(this);
        lblEsqueciMinhaSenha.setOnClickListener(this);
    }

    @Override
    public void carregaDados() throws Exception
    {
        boolean bConectado = false;

        MAS.debug();

        //Cria os diretórios do aplicativo
        Apoio.criaDiretorios(this);

        //Inicia a conexão com a biblioteca da CA (MAS)
        MAS.start(this);
        MAS.setAuthenticationListener(new RetornoMASAuthenticationListener());

        //Marca o check de conectado
        marcaCheckConectado();

        //Obtem a opção de manter conectado e seta na tela
        bConectado = Apoio.retornaPrefsValorBooleano(this, Apoio.PREFS_MANTER_CONECTADO, false);

        //Se tiver com a opção de manter conectado
        if ( bConectado )
        {
            //Se tiver um login registrado
            if ( Apoio.verificaUsuarioLogado(this) != null )
            {
                //Segue para o dashboard
                chamaTelaDashboard();
                return;
            }
        }

        //Mostra o teclado
        ((InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE)).toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

        //Seta o foco no controle
        txtEmail.requestFocus();
    }

    /**
     * Marca o check de conectado, de acordo com a preferencia
     */
    private void marcaCheckConectado() throws Exception
    {
        //Obtem a opção de manter conectado e seta na tela
        switchManterConectado.setChecked(Apoio.retornaPrefsValorBooleano(this, Apoio.PREFS_MANTER_CONECTADO, false));
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
     * Chama a tela inicial de cadastro
     */
    private void chamaTelaCadastro() throws Exception
    {
        Intent intent = null;

        //Chama a tela principal
        intent = new Intent(this, FrmCadastroInicio.class);
        startActivityForResult(intent, Apoio.RETORNO_TELA_DETRAN);
    }

    /**
     * Chama a tela do dashboard
     */
    private void chamaTelaDashboard() throws Exception
    {
        Intent intent = null;

        //Chama a tela principal
        intent = new Intent(this, FrmDashboard.class);
        startActivityForResult(intent, Apoio.RETORNO_TELA_DASHBOARD);
    }

    /**
     * Chama a tela de Esqueci Minha Senha
     */
    private void chamaTelaEsqueciMinhaSenha() throws Exception
    {
        Intent intent = null;

        // chama a tela de esqueci minha senha
        intent = new Intent(this, FrmEsqueciMinhaSenha.class);
        startActivityForResult(intent, Apoio.RETORNO_TELA_ESQUECI_MINHA_SENHA);
    }

    /**
     * Chama a tela de Lembretes
     */
    private void chamaTelaLembrete() throws Exception
    {
        Intent intent = null;

        // Chama a tela de lembretes
        intent = new Intent(this, FrmLembrete.class);
        startActivityForResult(intent, Apoio.RETORNO_TELA_DETRAN);
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

        //TODO: Somente para teste quando esta sem login
        //chamaTelaDashboard();

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
        if (!sAuxi.matches(Apoio.EMAIL_PATTERN))
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
        }

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
            //Quando apertar o botão registrar
            else if (view == cmdRegistrar)
            {
                //Chama a tela de cadastro
                chamaTelaCadastro();
            }
            //Quando apertar o label entrar mesmo assim
            else if (view == lblEntrarMesmoAssim)
            {
                //Chama a tela de Lembrete
                chamaTelaLembrete();
            }
            //Quando apertar o label esqueci minha senha
            else if (view == lblEsqueciMinhaSenha)
            {
                //Chama a tela de esqueci minha senha
                chamaTelaEsqueciMinhaSenha();
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

            //Grava a opção de manter conectado
            Apoio.gravaPrefsValorBooleano(this, Apoio.PREFS_MANTER_CONECTADO, switchManterConectado.isChecked());

            //Chama o preenchimento para rodar em thread, pois no retorno do sucesso, não conseguimos acessar os objetos de tela
            this.runOnUiThread(this);

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
            Apoio.finalizaActivity(this, RESULT_OK);
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
            if(iRequest == Apoio.RETORNO_TELA_DASHBOARD)
            {
                //verifico se é positiva a resposta
                if (iResultado == RESULT_OK)
                {
                    //Marca o check de conectado
                    marcaCheckConectado();
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
    public void run()
    {
        try
        {
            //Limpa os campos de usuário e senha no sucesso
            txtEmail.setText("");
            txtSenha.setText("");
            txtEmail.requestFocus();
        }
        catch(Exception err)
        {
            LogTrace.escreve(Apoio.localErro(getClass(), err), Apoio.getPathLogsSDCard(this), Apoio.getArqErr());
            DialogAlerta.show(this, Apoio.getMsgErr(getClass(), err), getString(R.string.atencao), getString(R.string.ok));
        }
    }
}
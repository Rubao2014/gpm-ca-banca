package br.com.ca.gpmcaieapp.telas;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import java.net.URLEncoder;
import br.com.ca.gpmcaieapp.R;
import br.com.ca.gpmcaieapp.base.ActivityBase;
import br.com.ca.gpmcaieapp.models.CommRespostaToken;
import br.com.ca.gpmcaieapp.tasks.TaskObtemToken;
import br.com.ca.gpmcaieapp.tasks.TaskRealizaSolicitacaoSenha;
import br.com.ca.gpmcaieapp.util.Apoio;
import br.com.ca.gpmcaieapp.util.DialogAlerta;
import br.com.ca.gpmcaieapp.util.LogTrace;
import br.com.ca.gpmcaieapp.util.RestCommunication;

/**
 * Classe FrmEsqueciMinhaSenha
 */
public class FrmEsqueciMinhaSenha extends ActivityBase implements View.OnClickListener
{
    // Controles da classe
    private Toolbar toolbar = null;
    private TextInputLayout inputEmail = null;
    private EditText txtEmail = null;
    private TextInputLayout inputConfirmarEmail = null;
    private EditText txtConfirmarEmail = null;
    private Button cmdEnviar = null;

    @Override
    protected void onCreate(Bundle bundleSavedInstanceState)
    {
        // Carrega a tela
        setContentView(R.layout.frm_esqueci_minha_senha);

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
        inputConfirmarEmail = (TextInputLayout) findViewById(R.id.inputConfirmarEmail);
        txtConfirmarEmail = (EditText) findViewById(R.id.txtConfirmarEmail);
        cmdEnviar = (Button) findViewById(R.id.cmdEnviar);

        //Muda a cor da flecha padrão do Android para branco
        drawableArrow = ContextCompat.getDrawable(this, R.drawable.abc_ic_ab_back_material);
        drawableArrow.setColorFilter(ContextCompat.getColor(this, R.color.branco), PorterDuff.Mode.SRC_ATOP);

        //Seta o navigation item
        toolbar.setNavigationIcon(drawableArrow);

        //Troca a cor do botao de voltar
        toolbar.setNavigationContentDescription(Apoio.TOOLBAR_VOLTAR);

        //Seta o listener dos controles
        cmdEnviar.setOnClickListener(this);

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
     * Finaliza a tela atual
     */
    private void finalizaTela() throws Exception
    {
        //Fecha o teclado
        Apoio.fechaTeclado(this);

        // Seta o resultado e finaliza
        Apoio.finalizaActivity(this, RESULT_CANCELED);
    }

    /**
     * Chama a comunicação para validação do token do app
     */
    private void realizaComunicacaoValidacaoToken() throws Exception
    {
        String sBody = "";

        //remove o erro dos campos
        removerErroDosCampos();

        //Valida os campos da tela
        if (!validaCamposTela())
        {
            return;
        }

        //Se não tiver conexão
        if ( !Apoio.verificaConexao(this) )
        {
            DialogAlerta.show(this, getString(R.string.msg_login_sem_conexao), getString(R.string.atencao), getString(R.string.ok));
            return;
        }

        //Monta o body para passar no post
        sBody = "client_id=" + Apoio.CLIENT_KEY + "&client_secret=" + Apoio.CLIENT_SECRET + "&grant_type=client_credentials&scope=ativacao";

        //Chama a task de solicitação do token
        new TaskObtemToken(this, this, sBody).execute();
    }

    /**
     * Chama a comunicação para solicitar a senha
     */
    private void realizaComunicacaoSolicitaSenha(CommRespostaToken commRespostaToken) throws Exception
    {
        String sBody = "";

        //Monta o body e passa no post
        sBody = "email=" + URLEncoder.encode(txtEmail.getText().toString().trim(), "UTF-8");

        //Chama a task de solicitar senha
        new TaskRealizaSolicitacaoSenha(this, this, sBody, commRespostaToken.sAccessToken, RestCommunication.RESTPOST).execute();
    }

    /**
     * Remove todos os erros dos InputTextLayout
     */
    private void removerErroDosCampos() throws Exception
    {
        //Tira o erro
        inputEmail.setError("");
        inputEmail.setErrorEnabled(false);
        inputConfirmarEmail.setError("");
        inputConfirmarEmail.setErrorEnabled(false);
    }

    /**
     * Valida os campos da tela
     */
    private boolean validaCamposTela() throws Exception
    {
        String sEmail = "";
        String sEmailConfirmacao = "";

        //Valida o campo de email
        sEmail = txtEmail.getText().toString().trim();
        if (sEmail.length() <= 0)
        {
            //Coloca a mensagem de erro embaixo do campo e solicita o foco
            inputEmail.setError(getString(R.string.msg_cadastro_email_em_branco));
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
        if (!sEmail.matches(Apoio.EMAIL_PATTERN))
        {
            //Coloca a mensagem de erro embaixo do campo e solicita o foco
            inputEmail.setError(getString(R.string.msg_cadastro_email_invalido));
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

        //Valida o campo de email
        sEmailConfirmacao = txtConfirmarEmail.getText().toString().trim();
        if (sEmailConfirmacao.length() <= 0)
        {
            //Coloca a mensagem de erro embaixo do campo e solicita o foco
            inputConfirmarEmail.setError(getString(R.string.msg_cadastro_email_conf_em_branco));
            inputConfirmarEmail.setErrorEnabled(true);
            Apoio.requisitarFoco(txtConfirmarEmail, this);
            return false;
        }
        else
        {
            //Se estier OK, remove o erro
            inputConfirmarEmail.setError("");
            inputConfirmarEmail.setErrorEnabled(false);
        }

        //Se o email não for valido
        if (!sEmailConfirmacao.matches(Apoio.EMAIL_PATTERN))
        {
            //Coloca a mensagem de erro embaixo do campo e solicita o foco
            inputConfirmarEmail.setError(getString(R.string.msg_cadastro_email_conf_invalido));
            inputConfirmarEmail.setErrorEnabled(true);
            Apoio.requisitarFoco(txtConfirmarEmail, this);
            return false;
        }
        else
        {
            //Se estier OK, remove o erro
            inputConfirmarEmail.setError("");
            inputConfirmarEmail.setErrorEnabled(false);
        }

        //Se o e-mail não bater com a confirmação
        if (!sEmail.equals(sEmailConfirmacao))
        {
            //Coloca a mensagem de erro embaixo do campo e solicita o foco
            inputConfirmarEmail.setError(getString(R.string.msg_cadastro_email_nao_confere));
            inputConfirmarEmail.setErrorEnabled(true);
            Apoio.requisitarFoco(txtConfirmarEmail, this);
            return false;
        }
        else
        {
            //Se estier OK, remove o erro
            inputConfirmarEmail.setError("");
            inputConfirmarEmail.setErrorEnabled(false);
        }

        return true;
    }

    /**
     * Chama a tela de finalizado
     */
    private void chamaTelaFinalizado(boolean bSucesso, String sMensagem) throws Exception
    {
        Intent intent = null;

        //Chama a tela principal
        intent = new Intent(this, FrmFinalizado.class);
        intent.putExtra(Apoio.TELA_SUCESSO_TIPO_MENSAGEM, bSucesso);
        intent.putExtra(Apoio.TELA_SUCESSO_MENSAGEM, sMensagem);
        intent.putExtra(Apoio.TELA_SUCESSO_TITULO, getString(R.string.toolbar_esqueci_minha_senha));
        startActivityForResult(intent, Apoio.RETORNO_TELA_ESQUECI_MINHA_SENHA);
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
                    finalizaTela();
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
            //Quando apertar o botão enviar
            if (view == cmdEnviar)
            {
                //Faz a comunicação para solicitar o token
                realizaComunicacaoValidacaoToken();
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
            if(iRequest == Apoio.RETORNO_TELA_ESQUECI_MINHA_SENHA)
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
    public void onBackPressed()
    {
        try
        {
            // Seta o resultado e finaliza
            finalizaTela();
        }
        catch (Exception err)
        {
            LogTrace.escreve(Apoio.localErro(getClass(), err), Apoio.getPathLogsSDCard(this), Apoio.getArqErr());
            DialogAlerta.show(this, Apoio.getMsgErr(getClass(), err), getString(R.string.atencao), getString(R.string.ok));
        }
    }

    @Override
    public void onReceiveData(Class classe, int iId, boolean bResultado, Object... oObjetos) throws Exception
    {
        CommRespostaToken commRespostaToken = null;
        String sMensagem = "";

        try
        {
            //Se for a classe da task de obtenção do token
            if (classe == TaskObtemToken.class)
            {
                //Se houve sucesso
                if (bResultado)
                {
                    //Obtem o objeto da comunicação
                    commRespostaToken = (CommRespostaToken) oObjetos[0];

                    //Chama o método para solicitar senha
                    realizaComunicacaoSolicitaSenha(commRespostaToken);
                }
                else
                {
                    //Obtem a mensagem de erro
                    sMensagem = (String) oObjetos[0];

                    //Chama a tela de finalizado com erro
                    chamaTelaFinalizado(false, sMensagem);
                }
            }
            //Se for a classe da task de solicitação de senha
            else if (classe == TaskRealizaSolicitacaoSenha.class)
            {
                //Obtem a mensagem de erro
                sMensagem = (String) oObjetos[0];

                //Chama a tela de finalizado com sucesso
                chamaTelaFinalizado(bResultado, sMensagem);
            }
        }
        catch (Exception err)
        {
            LogTrace.escreve(Apoio.localErro(getClass(), err), Apoio.getPathLogsSDCard(this), Apoio.getArqErr());
            DialogAlerta.show(this, Apoio.getMsgErr(getClass(), err), getString(R.string.atencao), getString(R.string.ok));
        }
    }
}
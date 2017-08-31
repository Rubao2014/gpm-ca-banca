package br.gov.ba.prefeitura.gpmapp.telas;

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
import br.gov.ba.prefeitura.gpmapp.R;
import br.gov.ba.prefeitura.gpmapp.base.ActivityBase;
import br.gov.ba.prefeitura.gpmapp.models.CommRespostaToken;
import br.gov.ba.prefeitura.gpmapp.tasks.TaskObtemToken;
import br.gov.ba.prefeitura.gpmapp.tasks.TaskRealizaGravacaoUsuario;
import br.gov.ba.prefeitura.gpmapp.util.Apoio;
import br.gov.ba.prefeitura.gpmapp.util.DialogAlerta;
import br.gov.ba.prefeitura.gpmapp.util.LogTrace;
import br.gov.ba.prefeitura.gpmapp.util.RestCommunication;

/**
 * Classe FrmCadastroSenha
 */
public class FrmCadastroSenha extends ActivityBase implements View.OnClickListener
{
    // Controles da classe
    private Toolbar toolbar = null;
    private TextInputLayout inputSenha = null;
    private EditText txtSenha = null;
    private TextInputLayout inputConfirmarSenha = null;
    private EditText txtConfirmarSenha = null;
    private Button cmdContinuar = null;

    @Override
    protected void onCreate(Bundle bundleSavedInstanceState)
    {
        // Carrega a tela
        setContentView(R.layout.frm_cadastro_senha);

        //chama os métodos automáticos da classe pai
        super.onCreate(bundleSavedInstanceState);
    }

    @Override
    public void iniciaControles() throws Exception
    {
        Drawable drawableArrow = null;

        // Obtem os controles
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        inputSenha = (TextInputLayout) findViewById(R.id.inputSenha);
        txtSenha = (EditText) findViewById(R.id.txtSenha);
        inputConfirmarSenha = (TextInputLayout) findViewById(R.id.inputConfirmarSenha);
        txtConfirmarSenha = (EditText) findViewById(R.id.txtConfirmarSenha);
        cmdContinuar = (Button) findViewById(R.id.cmdContinuar);

        //Muda a cor da flecha padrão do Android para branco
        drawableArrow = ContextCompat.getDrawable(this, R.drawable.abc_ic_ab_back_material);
        drawableArrow.setColorFilter(ContextCompat.getColor(this, R.color.branco), PorterDuff.Mode.SRC_ATOP);

        //Seta o navigation item
        toolbar.setNavigationIcon(drawableArrow);

        //Troca a cor do botao de voltar
        toolbar.setNavigationContentDescription(Apoio.TOOLBAR_VOLTAR);

        //Seta o listener dos controles
        cmdContinuar.setOnClickListener(this);

        //Coloca a toolbar como actionbar e adiciona as opções de menu
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public void carregaDados() throws Exception
    {
        //Seta tiver dados de senha, posiciona
        txtSenha.setText(Apoio.retornaPrefsValorString(this, Apoio.PREFS_CADASTRO_SENHA, ""));
        txtConfirmarSenha.setText(Apoio.retornaPrefsValorString(this, Apoio.PREFS_CADASTRO_SENHA, ""));

        //Mostra o teclado
        ((InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE)).toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

        //Seta o foco no controle
        txtSenha.requestFocus();
    }

    /**
     * Chama a comunicação para validação do token do app
     */
    private void realizaComunicacaoValidacaoToken() throws Exception
    {
        String sBody = "";

        //Remove os erros atuais dos campos
        removerErroDosCampos();

        //Valida os campos da tela
        if (!validaCamposTela())
        {
            return;
        }

        //Grava o dado na preferencia
        Apoio.gravaPrefsValorString(this, Apoio.PREFS_CADASTRO_SENHA, txtSenha.getText().toString().trim());

        //Se não tiver conexão
        /*
        if ( !Apoio.verificaConexao(this) )
        {
            DialogAlerta.show(this, getString(R.string.msg_login_sem_conexao), getString(R.string.atencao), getString(R.string.ok));
            return;
        }
        */
        //Monta o body para passar no post
        sBody = "client_id=" + Apoio.CLIENT_KEY + "&client_secret=" + Apoio.CLIENT_SECRET + "&grant_type=client_credentials&scope=adesao";

        //Chama a task de solicitação do token
        new TaskObtemToken(this, this, sBody).execute();
    }

    /**
     * Chama a comunicação para o cadsatro do usuário
     */
    private void realizaComunicacaoGravacaoUsuario(CommRespostaToken commRespostaToken) throws Exception
    {
        String sBody = "";

        //Monta o body e passa no post
        sBody = "nomeCompleto=" + URLEncoder.encode(Apoio.retornaPrefsValorString(this, Apoio.PREFS_CADASTRO_NOME, ""), "UTF-8");
        sBody += "&email=" + URLEncoder.encode(Apoio.retornaPrefsValorString(this, Apoio.PREFS_CADASTRO_EMAIL, ""), "UTF-8");
        sBody += "&senha=" + URLEncoder.encode(Apoio.retornaPrefsValorString(this, Apoio.PREFS_CADASTRO_SENHA, ""), "UTF-8");
        sBody += "&dataNascimento=" + URLEncoder.encode(Apoio.retornaPrefsValorString(this, Apoio.PREFS_CADASTRO_DATA_NASC, ""), "UTF-8");
        sBody += "&cpf=" + URLEncoder.encode(Apoio.retornaPrefsValorString(this, Apoio.PREFS_CADASTRO_CPF, ""), "UTF-8");
        sBody += "&telefoneCelular=" + URLEncoder.encode(Apoio.retornaPrefsValorString(this, Apoio.PREFS_CADASTRO_CELULAR, ""), "UTF-8");

        //Chama a task de gravação do usuário
        new TaskRealizaGravacaoUsuario(this, this, sBody, commRespostaToken.sAccessToken, RestCommunication.RESTPOST).execute();
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
        intent.putExtra(Apoio.TELA_SUCESSO_TITULO, getString(R.string.toolbar_cadastre_se));
        startActivityForResult(intent, Apoio.RETORNO_TELA_DETRAN);
    }

    /**
     * Chama a tela de cadastro (sexo)
     */
    /*private void chamaTelaCadastroSexo() throws Exception
    {
        Intent intent = null;

        //Remove os erros atuais dos campos
        removerErroDosCampos();

        //Valida os campos da tela
        if (!validaCamposTela())
        {
            return;
        }

        //Grava o dado na preferencia
        Apoio.gravaPrefsValorString(this, Apoio.PREFS_CADASTRO_SENHA, txtSenha.getText().toString().trim());

        //Chama a tela principal
        intent = new Intent(this, FrmCadastroSexo.class);
        startActivityForResult(intent, Apoio.RETORNO_TELA_DETRAN);
    }*/

    /**
     * Remove todos os erros dos InputTextLayout
     */
    private void removerErroDosCampos() throws Exception
    {
        //Tira o erro
        inputSenha.setError("");
        inputSenha.setErrorEnabled(false);
        inputConfirmarSenha.setError("");
        inputConfirmarSenha.setErrorEnabled(false);
    }

    /**
     * Valida os campos da tela
     */
    private boolean validaCamposTela() throws Exception
    {
        String sSenha = "";
        String sSenhaConfirmacao = "";

        //Valida o campo de senha
        sSenha = txtSenha.getText().toString().trim();
        if (sSenha.length() <= 0)
        {
            //Coloca a mensagem de erro embaixo do campo e solicita o foco
            inputSenha.setError(getString(R.string.msg_cadastro_senha_em_branco));
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

        //Se a senha tiver três ou menos caracteres
        if (sSenha.length() <= 3)
        {
            //Coloca a mensagem de erro embaixo do campo e solicita o foco
            inputSenha.setError(getString(R.string.msg_cadastro_senha_invalida));
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

        //Valida o campo de senha
        sSenhaConfirmacao = txtConfirmarSenha.getText().toString().trim();
        if (sSenhaConfirmacao.length() <= 0)
        {
            //Coloca a mensagem de erro embaixo do campo e solicita o foco
            inputConfirmarSenha.setError(getString(R.string.msg_cadastro_senha_conf_em_branco));
            inputConfirmarSenha.setErrorEnabled(true);
            Apoio.requisitarFoco(txtConfirmarSenha, this);
            return false;
        }
        else
        {
            //Se estier OK, remove o erro
            inputConfirmarSenha.setError("");
            inputConfirmarSenha.setErrorEnabled(false);
        }

        //Se a senha tiver trê sou menos caracteres
        if (sSenhaConfirmacao.length() <= 3)
        {
            //Coloca a mensagem de erro embaixo do campo e solicita o foco
            inputConfirmarSenha.setError(getString(R.string.msg_cadastro_senha_conf_invalida));
            inputConfirmarSenha.setErrorEnabled(true);
            Apoio.requisitarFoco(txtConfirmarSenha, this);
            return false;
        }
        else
        {
            //Se estier OK, remove o erro
            inputConfirmarSenha.setError("");
            inputConfirmarSenha.setErrorEnabled(false);
        }

        //Se a senha não bater com a confirmação
        if (!sSenha.equals(sSenhaConfirmacao))
        {
            //Coloca a mensagem de erro embaixo do campo e solicita o foco
            inputConfirmarSenha.setError(getString(R.string.msg_cadastro_senha_nao_confere));
            inputConfirmarSenha.setErrorEnabled(true);
            Apoio.requisitarFoco(txtConfirmarSenha, this);
            return false;
        }
        else
        {
            //Se estier OK, remove o erro
            inputConfirmarSenha.setError("");
            inputConfirmarSenha.setErrorEnabled(false);
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
            if (view == cmdContinuar)
            {
                //Realiza a comunicação para obter o token
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
            if(iRequest == Apoio.RETORNO_TELA_DETRAN)
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

                    //Chama o método para cadastro do usuário
                    realizaComunicacaoGravacaoUsuario(commRespostaToken);
                }
                else
                {
                    //Obtem a mensagem de erro
                    sMensagem = (String) oObjetos[0];

                    //Chama a tela de finalizado com erro
                    chamaTelaFinalizado(false, sMensagem);
                }
            }
            //Se for a classe da task de gravação
            else if (classe == TaskRealizaGravacaoUsuario.class)
            {
                //Obtem a mensagem de erro
                sMensagem = (String) oObjetos[0];

                //Se o resultado for sucesso
                if (bResultado)
                {
                    //Limpa as preferencias de cadastro
                    Apoio.limpaPreferenciasCadastro(this);
                }

                //Chama a tela de cadastro com sucesso
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
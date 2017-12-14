package br.gov.al.itec.gpmapp.telas;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.util.Pair;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.ca.mas.core.MobileSso;
import com.ca.mas.core.MobileSsoFactory;
import com.ca.mas.foundation.MAS;
import com.ca.mas.foundation.MASRequest;
import com.ca.mas.foundation.MASRequestBody;
import com.ca.mas.foundation.MASResponse;
import com.ca.mas.foundation.MASResponseBody;
import java.net.URI;
import java.util.ArrayList;

import br.gov.al.itec.gpmapp.R;
import br.gov.al.itec.gpmapp.base.ActivityBase;
import br.gov.al.itec.gpmapp.util.Apoio;
import br.gov.al.itec.gpmapp.util.DialogAlerta;
import br.gov.al.itec.gpmapp.util.IRetornoMASCallbackString;
import br.gov.al.itec.gpmapp.util.LogTrace;
import br.gov.al.itec.gpmapp.util.RetornoMASCallbackString;
import br.gov.al.itec.gpmapp.util.RetornoMASConnectionListener;

/**
 * Classe FrmMeusDadosTrocarSenha
 */
public class FrmMeusDadosTrocarSenha extends ActivityBase implements View.OnClickListener, IRetornoMASCallbackString
{
    // Controles da classe
    private Toolbar toolbar = null;
    private TextInputLayout inputNovaSenha = null;
    private EditText txtNovaSenha = null;
    private TextInputLayout inputConfirmarSenha = null;
    private EditText txtConfirmarSenha = null;
    private Button cmdEnviar = null;
    private ProgressDialog progressDialogMeusDadosTrocarSenha = null;

    //Variáveis da classe
    private int iTipoComunicacao = 0;

    @Override
    protected void onCreate(Bundle bundleSavedInstanceState)
    {
        // Carrega a tela
        setContentView(R.layout.frm_meus_dados_trocar_senha);

        //chama os métodos automáticos da classe pai
        super.onCreate(bundleSavedInstanceState);
    }

    @Override
    public void iniciaControles() throws Exception
    {
        Drawable drawableArrow = null;

        // Obtem os controles
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        inputNovaSenha = (TextInputLayout) findViewById(R.id.inputNovaSenha);
        txtNovaSenha = (EditText) findViewById(R.id.txtNovaSenha);
        inputConfirmarSenha = (TextInputLayout) findViewById(R.id.inputConfirmarSenha);
        txtConfirmarSenha = (EditText) findViewById(R.id.txtConfirmarSenha);
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
        txtNovaSenha.requestFocus();
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
     * Envia os dados do cadastro para o servidor
     */
    private void enviaDadosCadastroTrocarSenha() throws Exception
    {
        MobileSso mobileSso = null;
        URI uri = null;
        MASRequest masrequestDados = null;
        MASRequest.MASRequestBuilder builder = null;
        ArrayList<Pair<String, String>> arrParamEnvio = null;
        MASRequestBody masrequestBody = null;

        //Remove os erros atuais dos campos
        removerErroDosCampos();

        //Valida os campos da tela
        if (!validaCamposTela())
        {
            return;
        }

        //Cria o dialogo e exibe mensagem
        progressDialogMeusDadosTrocarSenha = Apoio.criarProgressDialog(this);
        Apoio.progressDialogMensagem(progressDialogMeusDadosTrocarSenha, getString(R.string.msg_meus_dados_trocar_senha_enviando));

        //Define o tipo da comunicação
        iTipoComunicacao = Apoio.TIPO_COMUNICACAO_MEUS_DADOS_TROCAR_SENHA;

        //Obtem a instancia da comunicação via SSO
        mobileSso = MobileSsoFactory.getInstance(this);

        //Realiza a chamada da comunicação na URL de obtenção das chaves de permissão
        uri = mobileSso.getURI(getString(R.string.url_conexao_gateway) + "cidadao/v1/pessoas/eu/trocarSenha");
        builder = new MASRequest.MASRequestBuilder(uri);
        builder.header("Content-Type", "application/x-www-form-urlencoded");

        //Adiciona os parametros de envio
        arrParamEnvio = new ArrayList<Pair<String, String>>();
        arrParamEnvio.add(new Pair<String, String>("novaSenha", txtNovaSenha.getText().toString().trim()));

        //Monta o body codificado para URL
        masrequestBody = MASRequestBody.urlEncodedFormBody(arrParamEnvio);

        //Constroi a URL de envio e faz a chamada
        masrequestDados = builder.responseBody(MASResponseBody.stringBody()).post(masrequestBody).connectionListener(new RetornoMASConnectionListener()).build();
        MAS.invoke(masrequestDados, new RetornoMASCallbackString(this));
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
        intent.putExtra(Apoio.TELA_SUCESSO_TITULO, getString(R.string.toolbar_meus_dados));
        startActivityForResult(intent, Apoio.RETORNO_TELA_MEUS_DADOS_TROCAR_SENHA);
    }

    /**
     * Remove todos os erros dos InputTextLayout
     */
    private void removerErroDosCampos() throws Exception
    {
        //Tira o erro
        inputNovaSenha.setError("");
        inputNovaSenha.setErrorEnabled(false);
        inputConfirmarSenha.setError("");
        inputConfirmarSenha.setErrorEnabled(false);
    }

    /**
     * Valida os campos da tela
     */
    private boolean validaCamposTela() throws Exception
    {
        String sNovaSenha = "";
        String sSenhaConfirmacao = "";

        //Valida o campo de senha
        sNovaSenha = txtNovaSenha.getText().toString().trim();
        if (sNovaSenha.length() <= 0)
        {
            //Coloca a mensagem de erro embaixo do campo e solicita o foco
            inputNovaSenha.setError(getString(R.string.msg_cadastro_senha_em_branco));
            inputNovaSenha.setErrorEnabled(true);
            Apoio.requisitarFoco(txtNovaSenha, this);
            return false;
        }
        else
        {
            //Se estier OK, remove o erro
            inputNovaSenha.setError("");
            inputNovaSenha.setErrorEnabled(false);
        }

        //Se a senha tiver três ou menos caracteres
        if (sNovaSenha.length() <= 3)
        {
            //Coloca a mensagem de erro embaixo do campo e solicita o foco
            inputNovaSenha.setError(getString(R.string.msg_cadastro_senha_invalida));
            inputNovaSenha.setErrorEnabled(true);
            Apoio.requisitarFoco(txtNovaSenha, this);
            return false;
        }
        else
        {
            //Se estier OK, remove o erro
            inputNovaSenha.setError("");
            inputNovaSenha.setErrorEnabled(false);
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
        if (!sNovaSenha.equals(sSenhaConfirmacao))
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
                //Envia os dados para trocar senha
                enviaDadosCadastroTrocarSenha();
            }
        }
        catch (Exception err)
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
    protected void onActivityResult(int iRequest, int iResultado, Intent intentData)
    {
        super.onActivityResult(iRequest, iResultado, intentData);

        try
        {
            //verifico se é positiva a resposta
            if(iRequest == Apoio.RETORNO_TELA_MEUS_DADOS_TROCAR_SENHA)
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
    public void onSuccess(MASResponse<String> masresponseResultado)
    {
        try
        {
            // fecha o dialog
            Apoio.fecharProgressDialog(progressDialogMeusDadosTrocarSenha);

            //Se for o tipo de comunicacao meus dados
            if ( iTipoComunicacao == Apoio.TIPO_COMUNICACAO_MEUS_DADOS_TROCAR_SENHA )
            {
                //Chama a tela de finalizado com sucesso
                chamaTelaFinalizado(true, getString(R.string.msg_meus_dados_trocar_senha_sucesso));
            }
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
        try
        {
            // fecha o dialog
            Apoio.fecharProgressDialog(progressDialogMeusDadosTrocarSenha);

            //Loga o erro retornado pela CA
            LogTrace.escreve(Apoio.localErro(getClass(), throwable), Apoio.getPathLogsSDCard(this), Apoio.getArqErr());

            //Se for o tipo de comunicacao meus dados
            if ( iTipoComunicacao == Apoio.TIPO_COMUNICACAO_MEUS_DADOS_TROCAR_SENHA )
            {
                //Chama a tela de finalizado com erro
                chamaTelaFinalizado(false, getString(R.string.msg_meus_dados_trocar_senha_erro));
            }
        }
        catch (Exception err)
        {
            LogTrace.escreve(Apoio.localErro(getClass(), err), Apoio.getPathLogsSDCard(this), Apoio.getArqErr());
            Toast.makeText(this, Apoio.getMsgErr(this.getClass(), err), Toast.LENGTH_LONG).show();
        }
    }
}
package br.com.ca.gpmcaieapp.telas;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ca.mas.core.MobileSso;
import com.ca.mas.core.MobileSsoFactory;
import com.ca.mas.foundation.MAS;
import com.ca.mas.foundation.MASRequest;
import com.ca.mas.foundation.MASResponse;

import org.json.JSONObject;

import java.net.URI;

import br.com.ca.gpmcaieapp.R;
import br.com.ca.gpmcaieapp.base.ActivityBase;
import br.com.ca.gpmcaieapp.models.CommRespostaToken;
import br.com.ca.gpmcaieapp.tasks.TaskCriarRelacionamentoCPFCNH;
import br.com.ca.gpmcaieapp.tasks.TaskObtemToken;
import br.com.ca.gpmcaieapp.util.Apoio;
import br.com.ca.gpmcaieapp.util.DialogAlerta;
import br.com.ca.gpmcaieapp.util.IRetornoMASCallbackJSON;
import br.com.ca.gpmcaieapp.util.LogTrace;
import br.com.ca.gpmcaieapp.util.RestCommunication;
import br.com.ca.gpmcaieapp.util.RetornoMASCallbackJSON;
import br.com.ca.gpmcaieapp.util.RetornoMASConnectionListener;

/**
 * Classe FrmCadastroCelular
 */
public class FrmIWMultasCNH extends ActivityBase implements View.OnClickListener, IRetornoMASCallbackJSON, Runnable
{
    // Controles da classe
    private Toolbar toolbar = null;
    private TextInputLayout inputCNH = null;
    private EditText txtCNH = null;
    private Button cmdContinuar = null;

    private ProgressDialog progressDialog = null;
    private int iTipoComunicacao = 0;
    private JSONObject jsonObjectRetornoDados = null;
    private boolean bSucesso = false;

    private String sCNH = null;
    private String sCPF = null;
    private String sNome = null;

    @Override
    protected void onCreate(Bundle bundleSavedInstanceState)
    {
        // Carrega a tela
        setContentView(R.layout.frm_iw_multas_cnh);

        //chama os métodos automáticos da classe pai
        super.onCreate(bundleSavedInstanceState);
    }

    @Override
    public void iniciaControles() throws Exception
    {
        Drawable drawableArrow = null;
        TextWatcher textWatcherCelular = null;

        // Obtem os controles
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        inputCNH = (TextInputLayout) findViewById(R.id.inputCNH);
        txtCNH = (EditText) findViewById(R.id.txtCNH);
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
        sCNH = Apoio.retornaPrefsValorString(this, Apoio.PREFS_CADASTRO_CNH, null);
        if (sCNH != null)
            txtCNH.setText(sCNH);

//        //Mostra o teclado
//        ((InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE)).toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
//
//        //Seta o foco no controle
//        txtCNH.requestFocus();
    }


    private void obtemDadosCadastroUsuario() throws Exception
    {
        MobileSso mobileSso = null;
        URI uri = null;
        MASRequest masrequestDados = null;
        MASRequest.MASRequestBuilder builder = null;

        //Cria o dialogo e exibe mensagem
        progressDialog = Apoio.criarProgressDialog(this);
        Apoio.progressDialogMensagem(progressDialog, "Obtendo dados pessoais. Aguarde...");

        //Define o tipo da comunicação
        iTipoComunicacao = Apoio.TIPO_COMUNICACAO_MEUS_DADOS;

        //Obtem a instancia da comunicação via SSO
        mobileSso = MobileSsoFactory.getInstance(this);

        //Realiza a chamada da comunicação na URL de obtenção das chaves de permissão
        uri = mobileSso.getURI(getString(R.string.url_conexao_gateway) + "cidadao/v1/pessoas/eu");
        builder = new MASRequest.MASRequestBuilder(uri);
        builder.header("Content-Type", "application/json");

        //Constroi a URL de envio e faz a chamada
        masrequestDados = builder.connectionListener(new RetornoMASConnectionListener()).build();
        MAS.invoke(masrequestDados, new RetornoMASCallbackJSON(this));
    }


    /**
     * Chama a tela de multas (lista)
     */
    private void chamaTelaMultasLista() throws Exception
    {
        if (!validaCamposTela()) return;

        //Remove os erros atuais dos campos
        removerErroDosCampos();

        if (sCPF == null)
            sCPF = Apoio.retornaPrefsValorString(this, Apoio.PREFS_CADASTRO_CPF, null);
        if (sCPF == null) {
            obtemDadosCadastroUsuario();
        } else {
            sCNH = txtCNH.getText().toString().trim();
            Apoio.gravaPrefsValorString(this, Apoio.PREFS_CADASTRO_CNH, sCNH);

            criaRelacionamentoCPF_CNH(sCPF, sCNH);

            Intent intent = null;
            intent = new Intent(this, FrmIWMultasLista.class);
            startActivityForResult(intent, Apoio.RETORNO_TELA_DASHBOARD);
        }
    }

    /**
     * Remove todos os erros dos InputTextLayout
     */
    private void removerErroDosCampos() throws Exception
    {
        //Tira o erro
        inputCNH.setError("");
        inputCNH.setErrorEnabled(false);
    }

    /**
     * Valida os campos da tela
     */
    private boolean validaCamposTela() throws Exception
    {
        String cnh = "";

        //Obtem o tamanho do campo celular
        cnh = txtCNH.getText().toString().trim();

        //Valida o campo
        if (cnh.length() <= 0)
        {
            //Coloca a mensagem de erro embaixo do campo e solicita o foco
            inputCNH.setError("O número do CNH precisa ser informado.");
            inputCNH.setErrorEnabled(true);
            Apoio.requisitarFoco(txtCNH, this);
            return false;
        }
        else
        {
            //Se estier OK, remove o erro
            removerErroDosCampos();
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

    private void criaRelacionamentoCPF_CNH(String cpf, String cnh) {

        String sBody = String.format(
                "{" +
                "\"cpf\": \"%s\", " +
                "\"cnh\": \"%s\"" +
                "}",
                cpf, cnh);

        //Chama a task de gravação do relacionamento
        new TaskCriarRelacionamentoCPFCNH(this ,this, sBody, null, RestCommunication.RESTPOST).execute();
    }

    @Override
    public void onClick(View view)
    {
        try
        {
            //Quando apertar o botão entrar
            if (view == cmdContinuar)
            {
                chamaTelaMultasLista();
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
                    //realizaComunicacaoSefazDae(commRespostaToken);
                }
                else
                {
                    //Obtem a mensagem de erro
                    sMensagem = (String) oObjetos[0];

                    //Chama a tela de finalizado com erro
                    //chamaTelaSefazDaeBoleto(false, sMensagem);
                }
            }
            //Se for a classe da task de gravação
            else if (classe == TaskCriarRelacionamentoCPFCNH.class)
            {
                //Obtem a mensagem de erro
                sMensagem = (String) oObjetos[0];



                //Chama a tela de cadastro com sucesso
                // atualiza lista
            }
        }
        catch (Exception err)
        {
            LogTrace.escreve(Apoio.localErro(getClass(), err), Apoio.getPathLogsSDCard(this), Apoio.getArqErr());
            DialogAlerta.show(this, Apoio.getMsgErr(getClass(), err), getString(R.string.atencao), getString(R.string.ok));
        }
    }


    @Override
    public void onSuccess(MASResponse<JSONObject> masresponseObjeto, int iRetorno) {
        try
        {
            // fecha o dialog
            Apoio.fecharProgressDialog(progressDialog);

            switch (iTipoComunicacao) {
                case Apoio.TIPO_COMUNICACAO_MEUS_DADOS: {
                    //Armazena o retorno
                    jsonObjectRetornoDados = masresponseObjeto.getBody().getContent();
                    //Seta com sucesso
                    bSucesso = true;
                    //Chama o preenchimento para rodar em thread, pois no retorno do sucesso, não conseguimos acessar os objetos de tela
                    this.runOnUiThread(this);
                    break;
                }
            }

        }
        catch (Exception err)
        {
            LogTrace.escreve(Apoio.localErro(getClass(), err), Apoio.getPathLogsSDCard(this), Apoio.getArqErr());
            Toast.makeText(this, Apoio.getMsgErr(this.getClass(), err), Toast.LENGTH_LONG).show();
        }
    }

    private void parseDadosUsuario() throws Exception
    {
        sCPF = jsonObjectRetornoDados.getString("cpf");
        sNome = jsonObjectRetornoDados.getString("nomeCompleto");
        Apoio.gravaPrefsValorString(this, Apoio.PREFS_CADASTRO_CPF, sCPF);
    }

    @Override
    public void run() {
        try {
            switch (iTipoComunicacao) {
                case Apoio.TIPO_COMUNICACAO_MEUS_DADOS: {
                    if (bSucesso) {
                        parseDadosUsuario();
                        chamaTelaMultasLista();
                    }
                    break;
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
    public void onError(Throwable throwable) {
        Snackbar snackbarErro = null;
        try
        {
            // fecha o dialog
            Apoio.fecharProgressDialog(progressDialog);

            //Loga o erro retornado pela CA
            LogTrace.escreve(Apoio.localErro(getClass(), throwable), Apoio.getPathLogsSDCard(this), Apoio.getArqErr());

            //Seta com sucesso
            bSucesso = false;


            //Monta snackbar com erro
            //snackbarErro = Snackbar.make(null, "Erro ao consumir API", Snackbar.LENGTH_LONG);
            //snackbarErro.show();

            //Chama o preenchimento para rodar em thread, pois no retorno do sucesso, não conseguimos acessar os objetos de tela
            this.runOnUiThread(this);
        }
        catch (Exception err)
        {
            LogTrace.escreve(Apoio.localErro(getClass(), err), Apoio.getPathLogsSDCard(this), Apoio.getArqErr());
            Toast.makeText(this, Apoio.getMsgErr(this.getClass(), err), Toast.LENGTH_LONG).show();
        }
    }
}
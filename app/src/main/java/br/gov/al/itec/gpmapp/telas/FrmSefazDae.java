package br.gov.al.itec.gpmapp.telas;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.ca.mas.core.MobileSso;
import com.ca.mas.core.MobileSsoFactory;
import com.ca.mas.foundation.MAS;
import com.ca.mas.foundation.MASRequest;
import com.ca.mas.foundation.MASResponse;

import org.json.JSONObject;

import java.net.URI;
import java.net.URLEncoder;
import java.util.HashMap;

import br.gov.al.itec.gpmapp.R;
import br.gov.al.itec.gpmapp.base.ActivityBase;
import br.gov.al.itec.gpmapp.models.CommRespostaToken;
import br.gov.al.itec.gpmapp.tasks.TaskObtemToken;
import br.gov.al.itec.gpmapp.tasks.TaskSefazGerarDAE;
import br.gov.al.itec.gpmapp.util.Apoio;
import br.gov.al.itec.gpmapp.util.DialogAlerta;
import br.gov.al.itec.gpmapp.util.IRetornoMASCallbackJSON;
import br.gov.al.itec.gpmapp.util.LogTrace;
import br.gov.al.itec.gpmapp.util.MascaraCampos;
import br.gov.al.itec.gpmapp.util.RestCommunication;
import br.gov.al.itec.gpmapp.util.RetornoMASCallbackJSON;
import br.gov.al.itec.gpmapp.util.RetornoMASConnectionListener;

/**
 * Classe FrmCadastroSenha
 */
public class FrmSefazDae extends ActivityBase implements View.OnClickListener, IRetornoMASCallbackJSON, Runnable
{
    // Controles da classe
    private Toolbar toolbar = null;
    private TextInputLayout inputDataVenctimento = null;
    private TextInputLayout inputCNPJOrgaoConveniado = null;
    private TextInputLayout inputCodigo = null;
    private TextInputLayout inputRazaoSocial = null;
    private TextInputLayout inputTipo = null;
    private TextInputLayout inputNumeroDoc = null;
    private TextInputLayout inputValorDoc = null;
    private TextInputLayout inputJurosDoc = null;
    private TextInputLayout inputMultaDoc = null;
    private Button cmdContinuar = null;
    private ProgressDialog progressDialogMeusDados = null;
    private CoordinatorLayout coordinatorLayoutAtualizacaoCad = null;


    //sefaz
    private EditText txtDataVencimento = null;
    //private EditText txtCodigo = null;
    private Spinner  txtCodigo = null;
    private EditText txtCNPJOrgaoConveniado = null;
    private EditText txtRazaoSocial = null;
    private EditText txtTipo = null;
    private EditText txtNumeroDoc = null;
    private EditText txtValorDoc = null;
    private EditText txtJurosDoc = null;
    private EditText txtMultaDoc = null;
    private TextWatcher textWatcherDataVencimento = null;
    private TextWatcher textWatcherCNPJOrgaoConveniado = null;
    private TextWatcher textWatcherCPF = null;
    private int iTipoComunicacao = 0;
    private boolean bSucesso = false;
    private String sCpf = null;
    private String sNome = null;

    private JSONObject jsonObjectRetornoDados = null;

    String sBodyPost = "{ \"dataVencimento\": \"_dataVencimento\", \"cnpjOrgaoConveniado\": \"_cnpjOrgaoConveniado\", \"codigoReceitaTributaria\": \"_codigoReceitaTributaria\",\"tipoDocumento\": \"_tipoDocumento\",\"numeroDocumento\": \"_numeroDocumento\",\"razaoSocial\": \"_razaoSocial\",\"valorPrincipal\": \"_valorPrincipal\",\"valorJuros\": \"_valorJuros\",\"valorMulta\": \"_valorMulta\"}";

    @Override
    protected void onCreate(Bundle bundleSavedInstanceState)
    {
        // Carrega a tela
        setContentView(R.layout.frm_sefaz_dae);

        //chama os métodos automáticos da classe pai
        super.onCreate(bundleSavedInstanceState);
    }

    @Override
    public void iniciaControles() throws Exception
    {
        Drawable drawableArrow = null;


        // Obtem os controles
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        inputDataVenctimento = (TextInputLayout) findViewById(R.id.inputDataVencimento);
        inputCodigo = (TextInputLayout) findViewById(R.id.inputCodigo);
        //inputCNPJOrgaoConveniado = (TextInputLayout) findViewById(R.id.inputCNPJOrgaoConveniado);
        inputRazaoSocial = (TextInputLayout) findViewById(R.id.inputRazaoSocial);
        //inputTipo = (TextInputLayout) findViewById(R.id.inputTipo);
        inputNumeroDoc = (TextInputLayout) findViewById(R.id.inputNumeroDoc);
        inputValorDoc = (TextInputLayout) findViewById(R.id.inputValorDoc);
        inputJurosDoc = (TextInputLayout) findViewById(R.id.inputJurosDoc);
        inputMultaDoc = (TextInputLayout) findViewById(R.id.inputMultaDoc);


        txtDataVencimento = (EditText) findViewById(R.id.txtDataVencimento);
        txtCodigo = (Spinner) findViewById(R.id.codigos_tributarios_spinner);
        //txtCNPJOrgaoConveniado = (EditText) findViewById(R.id.txtCNPJOrgaoConveniado);
        txtRazaoSocial = (EditText) findViewById(R.id.txtRazaoSocial);
        //txtTipo = (EditText) findViewById(R.id.txtTipo);
        txtNumeroDoc = (EditText) findViewById(R.id.txtNumeroDoc);
        txtValorDoc = (EditText) findViewById(R.id.txtValorDoc);
        txtJurosDoc = (EditText) findViewById(R.id.txtJurosDoc);
        txtMultaDoc = (EditText) findViewById(R.id.txtMultaDoc);


        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.codigos_e_itens_tributarios, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        txtCodigo.setAdapter(adapter);

        //inputConfirmarSenha = (TextInputLayout) findViewById(R.id.inputConfirmarSenha);
        //txtConfirmarSenha = (EditText) findViewById(R.id.txtConfirmarSenha);
        cmdContinuar = (Button) findViewById(R.id.cmdEnviarInscricao);

        //coloca a mascara nos campos
        textWatcherDataVencimento = MascaraCampos.insert("##/##/####", txtDataVencimento);
        txtDataVencimento.addTextChangedListener(textWatcherDataVencimento);

        //textWatcherCNPJOrgaoConveniado = MascaraCampos.insert("##.###.###/####-##", txtCNPJOrgaoConveniado);
        //txtCNPJOrgaoConveniado.addTextChangedListener(textWatcherCNPJOrgaoConveniado);

        textWatcherCPF  = MascaraCampos.insert("###.###.###-##", txtNumeroDoc);
        txtNumeroDoc.addTextChangedListener(textWatcherCPF);

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

    private void obtemDadosCadastroUsuario() throws Exception
    {
        MobileSso mobileSso = null;
        URI uri = null;
        MASRequest masrequestDados = null;
        MASRequest.MASRequestBuilder builder = null;

        //Cria o dialogo e exibe mensagem
        progressDialogMeusDados = Apoio.criarProgressDialog(this);
        Apoio.progressDialogMensagem(progressDialogMeusDados, getString(R.string.msg_meus_dados_obtendo));

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


    @Override
    public void carregaDados() throws Exception
    {
        //Seta tiver dados de senha, posiciona
        //txtSenha.setText(Apoio.retornaPrefsValorString(this, Apoio.PREFS_CADASTRO_SENHA, ""));
        //txtConfirmarSenha.setText(Apoio.retornaPrefsValorString(this, Apoio.PREFS_CADASTRO_SENHA, ""));


        //Obtem os dados de cadastro do usuário
        obtemDadosCadastroUsuario();


        //Mostra o teclado
        ((InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE)).toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);

        //Seta o foco no controle
        txtDataVencimento.requestFocus();
    }

    /**
     * Chama a comunicação para validação do token do app
     */
    private void realizaComunicacaoValidacaoToken() throws Exception
    {
        String sBody = "";
        String c = String.valueOf(getCodigoTributario(txtCodigo.getSelectedItem().toString().trim()));
        //Remove os erros atuais dos campos
        removerErroDosCampos();

        //Valida os campos da tela
        if (!validaCamposTela())
        {
            return;
        }

        //Grava o dado na preferencia
        Apoio.gravaPrefsValorString(this, Apoio.PREFS_SEFAZ_DATA_VENCIMENTO, txtDataVencimento.getText().toString().trim());
        Apoio.gravaPrefsValorString(this, Apoio.PREFS_SEFAZ_CODIGO_TRIBUTARIO, c); //"41".toString().trim());
        Apoio.gravaPrefsValorString(this, Apoio.PREFS_SEFAZ_CNPJ_ORGAO, "04.034.484/0001-40".toString().trim());
        Apoio.gravaPrefsValorString(this, Apoio.PREFS_SEFAZ_RAZAO_SOCIAL, txtRazaoSocial.getText().toString().trim());
        Apoio.gravaPrefsValorString(this, Apoio.PREFS_SEFAZ_TIPO, "1".toString().trim());
        Apoio.gravaPrefsValorString(this, Apoio.PREFS_SEFAZ_NUMERO_DOC, txtNumeroDoc.getText().toString().trim());
        Apoio.gravaPrefsValorString(this, Apoio.PREFS_SEFAZ_VALOR_DOC, txtValorDoc.getText().toString().trim());
        Apoio.gravaPrefsValorString(this, Apoio.PREFS_SEFAZ_JUROS_DOC, txtJurosDoc.getText().toString().trim());
        Apoio.gravaPrefsValorString(this, Apoio.PREFS_SEFAZ_MULTA_DOC, txtMultaDoc.getText().toString().trim());

        //Se não tiver conexão

        if ( !Apoio.verificaConexao(this) )
        {
            DialogAlerta.show(this, getString(R.string.msg_login_sem_conexao), getString(R.string.atencao), getString(R.string.ok));
            return;
        }

        //Monta o body para passar no post
        sBody = "client_id=" + Apoio.CLIENT_KEY + "&client_secret=" + Apoio.CLIENT_SECRET + "&grant_type=client_credentials&scope=adesao";

        //Chama a task de solicitação do token
        new TaskObtemToken(this, this, sBody).execute();
    }

    /**
     * Chama a comunicação para o cadsatro do usuário
     */
    private void realizaComunicacaoSefazDae(CommRespostaToken commRespostaToken) throws Exception
    {
        String sBody = "";

        //Monta o body e passa no post
        sBody = sBodyPost.replace("_dataVencimento", Apoio.parseData(Apoio.retornaPrefsValorString(this, Apoio.PREFS_SEFAZ_DATA_VENCIMENTO, ""),"dd/MM/yyyy","yyyy-MM-dd"));
        sBody = sBody.replace("_codigoReceitaTributaria", URLEncoder.encode(Apoio.retornaPrefsValorString(this, Apoio.PREFS_SEFAZ_CODIGO_TRIBUTARIO, ""), "UTF-8"));
        sBody = sBody.replace("_cnpjOrgaoConveniado", (Apoio.retornaPrefsValorString(this, Apoio.PREFS_SEFAZ_CNPJ_ORGAO, "")));
        sBody = sBody.replace("_tipoDocumento", URLEncoder.encode(Apoio.retornaPrefsValorString(this, Apoio.PREFS_SEFAZ_TIPO, ""), "UTF-8"));
        sBody = sBody.replace("_razaoSocial", URLEncoder.encode(Apoio.retornaPrefsValorString(this, Apoio.PREFS_SEFAZ_RAZAO_SOCIAL, ""), "UTF-8"));
        sBody = sBody.replace("_numeroDocumento", URLEncoder.encode(Apoio.retornaPrefsValorString(this, Apoio.PREFS_SEFAZ_NUMERO_DOC, ""), "UTF-8"));
        sBody = sBody.replace("_valorPrincipal", URLEncoder.encode(Apoio.retornaPrefsValorString(this, Apoio.PREFS_SEFAZ_VALOR_DOC, ""), "UTF-8"));
        sBody = sBody.replace("_valorJuros", URLEncoder.encode(Apoio.retornaPrefsValorString(this, Apoio.PREFS_SEFAZ_JUROS_DOC, ""), "UTF-8"));
        sBody = sBody.replace("_valorMulta", URLEncoder.encode(Apoio.retornaPrefsValorString(this, Apoio.PREFS_SEFAZ_MULTA_DOC, ""), "UTF-8"));

        //Chama a task de gravação do usuário
        new TaskSefazGerarDAE(this ,this, sBody, commRespostaToken.sAccessToken, RestCommunication.RESTPOST).execute();
    }


    private int getCodigoTributario(String sOpcao){

        String[] codigosItensTributarios = getResources().getStringArray(R.array.codigos_e_itens_tributarios);
        int[] numerosCodigosTributarios = getResources().getIntArray(R.array.codigos_tributarios);

        HashMap<String, Integer> myMap = new HashMap<String, Integer>();
        for (int i = 0; i < codigosItensTributarios.length; i++) {
            myMap.put(codigosItensTributarios[i],numerosCodigosTributarios[i]);
        }

       return (myMap.get(sOpcao));

    }

    /**
     * Chama a tela de finalizado
     */
    private void chamaTelaSefazDaeBoleto(boolean bSucesso, String sMensagem) throws Exception
    {
        Intent intent = null;
        String sDados = "";


        //Chama a tela principal
        intent = new Intent(this, FrmSefazDaeBoleto.class);
        intent.putExtra(Apoio.TELA_SUCESSO_TIPO_MENSAGEM, bSucesso);
        //intent.putExtra(Apoio.TELA_SUCESSO_MENSAGEM, sMensagem);
        //intent.putExtra(Apoio.TELA_SUCESSO_TITULO, getString(R.string.nome_servico_sefaz));


        //JSONObject jsonarray = new JSONObject(sMensagem);
        JSONObject json = new JSONObject(sMensagem);    // create JSON obj from string
        JSONObject json2 = json.getJSONObject("dae");    // this will return correct

        String razaoSocial = json2.getString("razaoSocial");
        String mensagem = json2.getString("mensagem");
        String strBarcode = json2.getString("strBarcode");
        String msgRetorno = json2.getString("msgRetorno");
        String receita = json2.getString("receita");

        intent.putExtra(Apoio.PREFS_SEFAZ_RAZAO_SOCIAL,(razaoSocial.equals("")?txtRazaoSocial.getText().toString():razaoSocial));
        intent.putExtra(Apoio.PREFS_SEFAZ_MENSAGEM_BOLETO, mensagem);
        intent.putExtra(Apoio.PREFS_SEFAZ_MENSAGEM_RETORNO_SERVICO, msgRetorno);
        intent.putExtra(Apoio.PREFS_SEFAZ_CODIGO_BARRAS, strBarcode);
        intent.putExtra(Apoio.PREFS_SEFAZ_CODIGO_TRIBUTARIO, receita);

        Log.v("Retorno:",  " -> " + razaoSocial  + " |  " + mensagem + " | " + strBarcode + " | " + msgRetorno);

        startActivityForResult(intent, Apoio.RETORNO_TELA_SEFAZ);
    }



    /**
     * Remove todos os erros dos InputTextLayout
     */
    private void removerErroDosCampos() throws Exception
    {
        //Tira o erro

        inputDataVenctimento.setError("");
        inputDataVenctimento.setErrorEnabled(false);

        //inputCNPJOrgaoConveniado.setError("");
        //inputCNPJOrgaoConveniado.setErrorEnabled(false);

        inputCodigo.setError("");
        inputCodigo.setErrorEnabled(false);

        inputRazaoSocial.setError("");
        inputRazaoSocial.setErrorEnabled(false);

        //inputTipo.setError("");
        //inputTipo.setErrorEnabled(false);

        inputNumeroDoc.setError("");
        inputNumeroDoc.setErrorEnabled(false);

        inputValorDoc.setError("");
        inputValorDoc.setErrorEnabled(false);

        inputJurosDoc.setError("");
        inputJurosDoc.setErrorEnabled(false);

        inputMultaDoc.setError("");
        inputMultaDoc.setErrorEnabled(false);
    }

    /**
     * Valida os campos da tela
     */
    private boolean validaCamposTela() throws Exception
    {
        String sSenha = "";
        String sSenhaConfirmacao = "";

        String sDataVencimento = "";


        //Obtem a data
        sDataVencimento = txtDataVencimento.getText().toString().trim();

        //Valida o campo de data de nascimento
        if (sDataVencimento.length() <= 0)
        {
            //Coloca a mensagem de erro embaixo do campo e solicita o foco
            inputDataVenctimento.setError(getString(R.string.msg_sefazdae_data_invalida));
            inputDataVenctimento.setErrorEnabled(true);
            Apoio.requisitarFoco(txtDataVencimento, this);
            return false;
        }
        else
        {
            //Se estier OK, remove o erro
            inputDataVenctimento.setError("");
            inputDataVenctimento.setErrorEnabled(false);
        }

        //verifica se a data é valida
        if ( Apoio.parseData(sDataVencimento, "dd/MM/yyyy", "yyyy-MM-dd").equals("") )
        {
            //Coloca a mensagem de erro embaixo do campo e solicita o foco
            inputDataVenctimento.setError(getString(R.string.msg_sefazdae_data_invalida));
            inputDataVenctimento.setErrorEnabled(true);
            Apoio.requisitarFoco(txtDataVencimento, this);
            return false;
        }
        else
        {
            //Se estier OK, remove o erro
            inputDataVenctimento.setError("");
            inputDataVenctimento.setErrorEnabled(false);
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
            if(iRequest == Apoio.RETORNO_TELA_SEFAZ)
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
                    realizaComunicacaoSefazDae(commRespostaToken);
                }
                else
                {
                    //Obtem a mensagem de erro
                    sMensagem = (String) oObjetos[0];

                    //Chama a tela de finalizado com erro
                    chamaTelaSefazDaeBoleto(false, sMensagem);
                }
            }
            //Se for a classe da task de gravação
            else if (classe == TaskSefazGerarDAE.class)
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
                chamaTelaSefazDaeBoleto(bResultado, sMensagem);
            }
        }
        catch (Exception err)
        {
            LogTrace.escreve(Apoio.localErro(getClass(), err), Apoio.getPathLogsSDCard(this), Apoio.getArqErr());
            DialogAlerta.show(this, Apoio.getMsgErr(getClass(), err), getString(R.string.atencao), getString(R.string.ok));
        }
    }

    @Override
    public void onSuccess(MASResponse<JSONObject> masresponseObjeto, int iRetorno)
    {
        try
        {
            // fecha o dialog
            Apoio.fecharProgressDialog(progressDialogMeusDados);

            //Se for o tipo de comunicacao meus dados
            if ( iTipoComunicacao == Apoio.TIPO_COMUNICACAO_MEUS_DADOS )
            {
                //Armazena o retorno
                jsonObjectRetornoDados = masresponseObjeto.getBody().getContent();

                //Seta com sucesso
                bSucesso = true;

                //Chama o preenchimento para rodar em thread, pois no retorno do sucesso, não conseguimos acessar os objetos de tela
                this.runOnUiThread(this);
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
        Snackbar snackbarErro = null;

        try
        {
            // fecha o dialog
            Apoio.fecharProgressDialog(progressDialogMeusDados);

            //Loga o erro retornado pela CA
            LogTrace.escreve(Apoio.localErro(getClass(), throwable), Apoio.getPathLogsSDCard(this), Apoio.getArqErr());

            //Seta com sucesso
            bSucesso = false;

            //Monta snackbar com erro
            snackbarErro = Snackbar.make(coordinatorLayoutAtualizacaoCad, getString(R.string.msg_meus_dados_impossivel_obter), Snackbar.LENGTH_LONG);
            snackbarErro.show();

            //Chama o preenchimento para rodar em thread, pois no retorno do sucesso, não conseguimos acessar os objetos de tela
            this.runOnUiThread(this);
        }
        catch (Exception err)
        {
            LogTrace.escreve(Apoio.localErro(getClass(), err), Apoio.getPathLogsSDCard(this), Apoio.getArqErr());
            Toast.makeText(this, Apoio.getMsgErr(this.getClass(), err), Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Preenche os dados do usuário
     */
    private void preencheDadosUsuario() throws Exception
    {
        //Preenche os dados do usuário
        sCpf = jsonObjectRetornoDados.getString("cpf");
        sNome = jsonObjectRetornoDados.getString("nomeCompleto");

        txtRazaoSocial.setText(sNome);
        txtNumeroDoc.setText(sCpf);

    }

    @Override
    public void run() {
        {
            try
            {
                //Se for os serviços
                if ( iTipoComunicacao == Apoio.TIPO_COMUNICACAO_MEUS_DADOS )
                {
                    //Se for sucesso
                    if ( bSucesso )
                    {
                        //Preenche os dados do usuário
                        preencheDadosUsuario();

                    }
                    else
                    {
                        // faz nada aind...
                    }
                }
            }
            catch(Exception err)
            {
                LogTrace.escreve(Apoio.localErro(getClass(), err), Apoio.getPathLogsSDCard(this), Apoio.getArqErr());
                DialogAlerta.show(this, Apoio.getMsgErr(getClass(), err), getString(R.string.atencao), getString(R.string.ok));
            }
        }
    }
}
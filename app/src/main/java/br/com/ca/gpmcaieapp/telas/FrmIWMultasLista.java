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
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ca.mas.core.MobileSso;
import com.ca.mas.core.MobileSsoFactory;
import com.ca.mas.foundation.MAS;
import com.ca.mas.foundation.MASRequest;
import com.ca.mas.foundation.MASResponse;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.net.URI;

import br.com.ca.gpmcaieapp.R;
import br.com.ca.gpmcaieapp.adapters.MultasListView;
import br.com.ca.gpmcaieapp.base.ActivityBase;
import br.com.ca.gpmcaieapp.models.CommRespostaToken;
import br.com.ca.gpmcaieapp.models.Multa;
import br.com.ca.gpmcaieapp.models.Multas;
import br.com.ca.gpmcaieapp.models.Vacinas;
import br.com.ca.gpmcaieapp.tasks.TaskObtemToken;
import br.com.ca.gpmcaieapp.tasks.TaskUpdateVacinaCNS;
import br.com.ca.gpmcaieapp.util.Apoio;
import br.com.ca.gpmcaieapp.util.DialogAlerta;
import br.com.ca.gpmcaieapp.util.IRetornoMASCallbackJSON;
import br.com.ca.gpmcaieapp.util.LogTrace;
import br.com.ca.gpmcaieapp.util.RetornoMASCallbackJSON;
import br.com.ca.gpmcaieapp.util.RetornoMASConnectionListener;

public class FrmIWMultasLista extends ActivityBase implements View.OnClickListener, IRetornoMASCallbackJSON, Runnable
{
    // Controles da classe
    private Toolbar toolbar = null;
    private ProgressDialog progressDialog = null;
    private CoordinatorLayout coordinatorLayout = null;


    //seduz
    private Spinner  txtCodigo = null;
    private int iTipoComunicacao = 0;
    private boolean bSucesso = false;
    private String sCNH = null;
    private String sCPF = null;
    private String sNome = null;
    private ListView listView = null;

    private Vacinas vacinasList = null;
    private Multas multasList = null;

    private JSONObject jsonObjectRetornoDados = null;

    private  static final String DUMMY_JSON_LIST_RESPONSE = new String
            ("{" +
                        "\"cnh\": \"123123123\", " +
                        "\"total_pts\": 26, " +
                        "\"multas\": [" +
                            "{\"id\": \"1\", \"desc\": \"Limite de velocidade\", \"pts\": 7}, " +
                            "{\"id\": \"2\", \"desc\": \"Limite de velocidade\", \"pts\": 1}, " +
                            "{\"id\": \"6\", \"desc\": \"Falar ao celular na direção\", \"pts\": 6} " +
	                    "]" +
                    "}");

    @Override
    protected void onCreate(Bundle bundleSavedInstanceState)
    {
        // Carrega a tela
        setContentView(R.layout.frm_iw_multas_lista);

        //chama os métodos automáticos da classe pai
        super.onCreate(bundleSavedInstanceState);
    }



    public void carregaMultas(){

        MultasListView customList = new MultasListView(
                this,
                multasList.getMultaList());

        listView = (ListView) findViewById(R.id.listViewMultas);
        listView.setAdapter(customList);


            /* Get Listview itens click*/
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Multa multa = (Multa) adapterView.getItemAtPosition(i);
                Toast.makeText(view.getContext(), multa.getDesc(), Toast.LENGTH_SHORT).show();
            }
        });

        TextView textTotalPts = (TextView) findViewById(R.id.txtTotalPts);
        textTotalPts.setText("Total de Pontos: " + multasList.getTotalPts());
    }

    @Override
    public void iniciaControles() throws Exception
    {
        Drawable drawableArrow = null;

        // Obtem os controles
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        //Muda a cor da flecha padrão do Android para branco
        drawableArrow = ContextCompat.getDrawable(this, R.drawable.abc_ic_ab_back_material);
        drawableArrow.setColorFilter(ContextCompat.getColor(this, R.color.branco), PorterDuff.Mode.SRC_ATOP);

        //Seta o navigation item
        toolbar.setNavigationIcon(drawableArrow);

        //Troca a cor do botao de voltar
        toolbar.setNavigationContentDescription(Apoio.TOOLBAR_VOLTAR);

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

    private void obtemNumeroCNH() throws Exception
    {
        MobileSso mobileSso = null;
        URI uri = null;
        MASRequest masrequestDados = null;
        MASRequest.MASRequestBuilder builder = null;

        //Cria o dialogo e exibe mensagem
        progressDialog = Apoio.criarProgressDialog(this);
        Apoio.progressDialogMensagem(progressDialog, "Obtendo CNH. Aguarde...");

        //Define o tipo da comunicação
        iTipoComunicacao = Apoio.TIPO_COMUNICACAO_NUMERO_CNH;

        //Obtem a instancia da comunicação via SSO
        mobileSso = MobileSsoFactory.getInstance(this);

        uri = mobileSso.getURI(getString(R.string.url_conexao_gateway) + "iw/transp/cpf_cnh?cpf="+ sCPF);
        builder = new MASRequest.MASRequestBuilder(uri);
        builder.header("Content-Type", "application/json");

        //Constroi a URL de envio e faz a chamada
        masrequestDados = builder.connectionListener(new RetornoMASConnectionListener()).build();
        MAS.invoke(masrequestDados, new RetornoMASCallbackJSON(this));
    }

    private void obtemMultasCNH() throws Exception
    {
        MobileSso mobileSso = null;
        URI uri = null;
        MASRequest masrequestDados = null;
        MASRequest.MASRequestBuilder builder = null;

        //Cria o dialogo e exibe mensagem
        progressDialog = Apoio.criarProgressDialog(this);
        Apoio.progressDialogMensagem(progressDialog, "Obtendo Multas. Aguarde...");

        //Define o tipo da comunicação
        iTipoComunicacao = Apoio.TIPO_COMUNICACAO_MULTAS_CNH;

        //Obtem a instancia da comunicação via SSO
        mobileSso = MobileSsoFactory.getInstance(this);

        uri = mobileSso.getURI(getString(R.string.url_conexao_gateway) + "iw/transp/multas?cnh="+ sCNH);
        builder = new MASRequest.MASRequestBuilder(uri);
        builder.header("Content-Type", "application/json");

        //Constroi a URL de envio e faz a chamada
        masrequestDados = builder.connectionListener(new RetornoMASConnectionListener()).build();
        MAS.invoke(masrequestDados, new RetornoMASCallbackJSON(this));
    }


    @Override
    public void carregaDados() throws Exception
    {
        sCNH = Apoio.retornaPrefsValorString(this, Apoio.PREFS_CADASTRO_CNH, null);
        if (sCNH == null) {
            sCPF = Apoio.retornaPrefsValorString(this, Apoio.PREFS_CADASTRO_CPF, null);
            if (sCPF == null) {
                //Sem CNS nem CPF: Obtem os dados de cadastro do usuário
                obtemDadosCadastroUsuario();
            } else {
                //Sem CNH, mas com CPF: Obtem o numero CNH
                obtemNumeroCNH();
            }
        } else {
            //Com CNH: O necessário para obter as multas
            obtemMultasCNH();
        }
    }

    /**
     * Chama a comunicação para validação do token do app
     */
    private void realizaComunicacaoValidacaoToken() throws Exception
    {
        String sBody = "";
        //String c = String.valueOf(getCodigoTributario(txtCodigo.getSelectedItem().toString().trim()));
        //Remove os erros atuais dos campos
        //removerErroDosCampos();

        /*
         [RUBENS] Grava o dado de persistencia para enviar para a proxima tela
        */
        //Apoio.gravaPrefsValorString(this, Apoio.PREFS_SEFAZ_DATA_VENCIMENTO, txtDataVencimento.getText().toString().trim());
        //Apoio.gravaPrefsValorString(this, Apoio.PREFS_SEFAZ_CODIGO_TRIBUTARIO, c); //"41".toString().trim());
        //Apoio.gravaPrefsValorString(this, Apoio.PREFS_SEFAZ_CNPJ_ORGAO, "04.034.484/0001-40".toString().trim());
        //Apoio.gravaPrefsValorString(this, Apoio.PREFS_SEFAZ_RAZAO_SOCIAL, txtRazaoSocial.getText().toString().trim());
        //Apoio.gravaPrefsValorString(this, Apoio.PREFS_SEFAZ_TIPO, "1".toString().trim());
        //Apoio.gravaPrefsValorString(this, Apoio.PREFS_SEFAZ_NUMERO_DOC, txtNumeroDoc.getText().toString().trim());
        //Apoio.gravaPrefsValorString(this, Apoio.PREFS_SEFAZ_VALOR_DOC, txtValorDoc.getText().toString().trim());
        //Apoio.gravaPrefsValorString(this, Apoio.PREFS_SEFAZ_JUROS_DOC, txtJurosDoc.getText().toString().trim());
        //Apoio.gravaPrefsValorString(this, Apoio.PREFS_SEFAZ_MULTA_DOC, txtMultaDoc.getText().toString().trim());

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
                    Intent intent = new Intent(this, FrmDashboard.class);
                    startActivityForResult(intent, Apoio.RETORNO_TELA_DASHBOARD);
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
//            //Quando apertar o botão entrar
//            if (view == cmdFazerNovaInscricao)
//            {
//                /*
//                * [RUBENS] Realiza nova inscriçao
//                * Chama tela de nova inscricao de matricular
//                */
//                //realizaComunicacaoValidacaoToken();
//                Intent intent = new Intent(this, FrmSeducMatriculaEscolar.class);
//                startActivityForResult(intent, Apoio.RETORNO_TELA_SEDUC);
//            }
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
            if(iRequest == Apoio.RETORNO_TELA_SEDUC)
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
            else if (classe == TaskUpdateVacinaCNS.class)
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
    public void onSuccess(MASResponse<JSONObject> masresponseObjeto, int iRetorno)
    {
        try
        {
            // fecha o dialog
            Apoio.fecharProgressDialog(progressDialog);

            switch (iTipoComunicacao) {
                case Apoio.TIPO_COMUNICACAO_MEUS_DADOS:
                case Apoio.TIPO_COMUNICACAO_NUMERO_CNH:
                case Apoio.TIPO_COMUNICACAO_MULTAS_CNH: {
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

    @Override
    public void onError(Throwable throwable)
    {
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
            snackbarErro = Snackbar.make(coordinatorLayout, "Erro ao consumir API", Snackbar.LENGTH_LONG);
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
    private void parseDadosUsuario() throws Exception
    {
        sCPF = jsonObjectRetornoDados.getString("cpf");
        sNome = jsonObjectRetornoDados.getString("nomeCompleto");
        Apoio.gravaPrefsValorString(this, Apoio.PREFS_CADASTRO_CPF, sCPF);
    }

    private void parseNumeroCNH() throws Exception
    {
        sCPF = jsonObjectRetornoDados.getString("cpf");
        sCNH = jsonObjectRetornoDados.getString("cnh");
        Apoio.gravaPrefsValorString(this, Apoio.PREFS_CADASTRO_CPF, sCPF);
        Apoio.gravaPrefsValorString(this, Apoio.PREFS_CADASTRO_CNH, sCNH);
    }

    private void parseMultas()
    {
        multasList = new Multas(jsonObjectRetornoDados);
        multasList.create();
        carregaMultas();
    }

    @Override
    public void run() {
        try {
            switch (iTipoComunicacao) {
                case Apoio.TIPO_COMUNICACAO_MEUS_DADOS: {
                    if (bSucesso) {
                        parseDadosUsuario();
                        obtemNumeroCNH();
                    }
                    break;
                }
                case Apoio.TIPO_COMUNICACAO_NUMERO_CNH: {
                    if (bSucesso) {
                        parseNumeroCNH();
                        obtemMultasCNH();
                        break;
                    }
                }
                case Apoio.TIPO_COMUNICACAO_MULTAS_CNH: {
                    if (bSucesso) {
                        parseMultas();
                    }
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
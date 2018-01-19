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
import android.widget.CompoundButton;
import android.widget.ListView;
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

import br.com.ca.gpmcaieapp.R;
import br.com.ca.gpmcaieapp.adapters.VacinasListView;
import br.com.ca.gpmcaieapp.base.ActivityBase;
import br.com.ca.gpmcaieapp.models.CommRespostaToken;
import br.com.ca.gpmcaieapp.models.Matriculas;
import br.com.ca.gpmcaieapp.models.Vacina;
import br.com.ca.gpmcaieapp.models.Vacinas;
import br.com.ca.gpmcaieapp.tasks.TaskObtemToken;
import br.com.ca.gpmcaieapp.tasks.TaskSeducGet;
import br.com.ca.gpmcaieapp.tasks.TaskSefazGerarDAE;
import br.com.ca.gpmcaieapp.tasks.TaskUpdateVacinaCNS;
import br.com.ca.gpmcaieapp.util.Apoio;
import br.com.ca.gpmcaieapp.util.DialogAlerta;
import br.com.ca.gpmcaieapp.util.IRetornoMASCallbackJSON;
import br.com.ca.gpmcaieapp.util.LogTrace;
import br.com.ca.gpmcaieapp.util.RestCommunication;
import br.com.ca.gpmcaieapp.util.RetornoMASCallbackJSON;
import br.com.ca.gpmcaieapp.util.RetornoMASConnectionListener;
import br.com.ca.gpmcaieapp.util.ValidarCPF;

public class FrmIWVacinasLista extends ActivityBase implements View.OnClickListener, CompoundButton.OnCheckedChangeListener, IRetornoMASCallbackJSON, Runnable
{
    // Controles da classe
    private Toolbar toolbar = null;
    private ProgressDialog progressDialog = null;
    private CoordinatorLayout coordinatorLayout = null;


    //seduz
    private Spinner  txtCodigo = null;
    private int iTipoComunicacao = 0;
    private boolean bSucesso = false;
    private String sCNS = null;
    private String sCPF = null;
    private String sNome = null;
    private ListView listView = null;
    private Matriculas matriculasList = null;
    private String inscricaoCorrente = null;

    private Vacinas vacinasList = null;

    String sPutBodyRequest = "{ " +
            "\"cns\": \"_cns\", " +
            "\"vac_id\": _vac_id, " +
            "\"desc\": \"_desc\", " +
            "\"flag\": _flag" +
            "}";


    private JSONObject jsonObjectRetornoDados = null;

    private  static final String DUMMY_JSON_LIST_RESPONSE = new String
            ("{\n" +
                    "\"cns\": \"11221122\",\n" +
                    "\"vacinas_cns\": [" +
                    " {\"id\": \"1\", \"desc\": \"Flu\", \"flag\": 1},\n" +
                    " {\"id\": \"2\", \"desc\": \"Chicungunha\", \"flag\": 0},\n" +
                    " {\"id\": \"3\", \"desc\": \"Gripe A\", \"flag\": 0},\n" +
                    " {\"id\": \"4\", \"desc\": \"Hepatite B\", \"flag\": 1},\n" +
                    " {\"id\": \"11\", \"desc\": \"Sarampo\", \"flag\": 1}\n" +
                    "\t]\n" +
                    "}");

    @Override
    protected void onCreate(Bundle bundleSavedInstanceState)
    {
        // Carrega a tela
        setContentView(R.layout.frm_iw_vacinas_lista);

        //chama os métodos automáticos da classe pai
        super.onCreate(bundleSavedInstanceState);
    }



    public void carregaVacinas(){

        VacinasListView customList = new VacinasListView(
                this,
                vacinasList.getVacinasList());

        listView = (ListView) findViewById(R.id.listViewVacinas);
        listView.setAdapter(customList);


            /* Get Listview itens click*/
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Vacina vacina = (Vacina) adapterView.getItemAtPosition(i);
                Toast.makeText(view.getContext(), vacina.getDescricao(), Toast.LENGTH_SHORT).show();

                /*
                * [RUBENS] Realiza nova inscriçao
                * Chama tela de nova inscricao de matricular
                */

//                Intent intent = new Intent(FrmIWVacinasLista.this, FrmSeducMatriculaEscolarSituacao.class);
//                intent.putExtra("matriculaObject", m);
//
//                startActivity(intent);

            }
        });


    }
    @Override
    public void iniciaControles() throws Exception
    {
        Drawable drawableArrow = null;


        // Obtem os controles
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        //Preenche Matriculas com valores Dummy
        //parseVacinas(DUMMY_JSON_LIST_RESPONSE);

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

    private void obtemNumeroCNS() throws Exception
    {
        MobileSso mobileSso = null;
        URI uri = null;
        MASRequest masrequestDados = null;
        MASRequest.MASRequestBuilder builder = null;

        //Cria o dialogo e exibe mensagem
        progressDialog = Apoio.criarProgressDialog(this);
        Apoio.progressDialogMensagem(progressDialog, "Obtendo CNS. Aguarde...");

        //Define o tipo da comunicação
        iTipoComunicacao = Apoio.TIPO_COMUNICACAO_NUMERO_CNS;

        //Obtem a instancia da comunicação via SSO
        mobileSso = MobileSsoFactory.getInstance(this);

        uri = mobileSso.getURI(getString(R.string.url_conexao_gateway) + "iw/saude/cpf_cns?cpf="+ sCPF);
        builder = new MASRequest.MASRequestBuilder(uri);
        builder.header("Content-Type", "application/json");

        //Constroi a URL de envio e faz a chamada
        masrequestDados = builder.connectionListener(new RetornoMASConnectionListener()).build();
        MAS.invoke(masrequestDados, new RetornoMASCallbackJSON(this));
    }

    private void obtemVacinasCNS() throws Exception
    {
        MobileSso mobileSso = null;
        URI uri = null;
        MASRequest masrequestDados = null;
        MASRequest.MASRequestBuilder builder = null;

        //Cria o dialogo e exibe mensagem
        progressDialog = Apoio.criarProgressDialog(this);
        Apoio.progressDialogMensagem(progressDialog, "Obtendo Vacinas. Aguarde...");

        //Define o tipo da comunicação
        iTipoComunicacao = Apoio.TIPO_COMUNICACAO_VACINAS_CNS;

        //Obtem a instancia da comunicação via SSO
        mobileSso = MobileSsoFactory.getInstance(this);

        uri = mobileSso.getURI(getString(R.string.url_conexao_gateway) + "iw/saude/vacinas_cns?cns="+ sCNS);
        builder = new MASRequest.MASRequestBuilder(uri);
        builder.header("Content-Type", "application/json");

        //Constroi a URL de envio e faz a chamada
        masrequestDados = builder.connectionListener(new RetornoMASConnectionListener()).build();
        MAS.invoke(masrequestDados, new RetornoMASCallbackJSON(this));
    }


    @Override
    public void carregaDados() throws Exception
    {
        sCNS = Apoio.retornaPrefsValorString(this, Apoio.PREFS_CADASTRO_CNS, null);
        if (sCNS == null) {
            sCPF = Apoio.retornaPrefsValorString(this, Apoio.PREFS_CADASTRO_CPF, null);
            if (sCPF == null) {
                //Sem CNS nem CPF: Obtem os dados de cadastro do usuário
                obtemDadosCadastroUsuario();
            } else {
                //Sem CNS, mas com CPF: Obtem o numero CNS
                obtemNumeroCNS();
            }
        } else {
            //Com CNS: O necessário para obter as vacinas
            obtemVacinasCNS();
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


    private void updateVacinaCNS(Vacina vacina) throws Exception {
        String sBody = vacina.toJson(Vacina.VacinaJsonOutput.COMPLETO);

        //Chama a task de gravação do usuário
        new TaskUpdateVacinaCNS(this ,this, sBody, null, RestCommunication.RESTPUT).execute();
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
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (buttonView.getTag() == null) return;

        try {
            Vacina vacina = (Vacina) buttonView.getTag();
            boolean vacinaTomada = vacina.getFlag() > 0;
            if (buttonView.isChecked() != vacinaTomada) {
                vacina.setFlag(isChecked ? 1 : 0);
                updateVacinaCNS(vacina);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
                if (bResultado) {
                    //Obtem a mensagem de erro
                    sMensagem = (String) oObjetos[0];
                    jsonObjectRetornoDados = new JSONObject(sMensagem);
                    parseVacinas();
                }

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
                case Apoio.TIPO_COMUNICACAO_NUMERO_CNS:
                case Apoio.TIPO_COMUNICACAO_VACINAS_CNS: {
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

    private void parseNumeroCNS() throws Exception
    {
        sCPF = jsonObjectRetornoDados.getString("cpf");
        sCNS = jsonObjectRetornoDados.getString("cns");
        Apoio.gravaPrefsValorString(this, Apoio.PREFS_CADASTRO_CPF, sCPF);
        Apoio.gravaPrefsValorString(this, Apoio.PREFS_CADASTRO_CNS, sCNS);
    }

    private void parseVacinas()
    {
        vacinasList = new Vacinas(jsonObjectRetornoDados);
        vacinasList.Create();
        carregaVacinas();
    }



    @Override
    public void run() {
        try {
            switch (iTipoComunicacao) {
                case Apoio.TIPO_COMUNICACAO_MEUS_DADOS: {
                    if (bSucesso) {
                        //Preenche os dados do usuário
                        parseDadosUsuario();
                        //Obtem o numero CNS
                        obtemNumeroCNS();
                    }
                    break;
                }
                case Apoio.TIPO_COMUNICACAO_NUMERO_CNS: {
                    if (bSucesso) {
                        //Preenche os dados do usuário
                        parseNumeroCNS();
                        //Obtem o CNS
                        obtemVacinasCNS();
                        break;
                    }
                }
                case Apoio.TIPO_COMUNICACAO_VACINAS_CNS: {
                    if (bSucesso) {
                        //Parse dos dados das Vacinas
                        parseVacinas();
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
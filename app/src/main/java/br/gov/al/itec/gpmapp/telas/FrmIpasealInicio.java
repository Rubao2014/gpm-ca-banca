package br.gov.al.itec.gpmapp.telas;

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
import android.widget.Button;
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

import br.gov.al.itec.gpmapp.R;
import br.gov.al.itec.gpmapp.adapters.DependentesBeneficiariosListView;
import br.gov.al.itec.gpmapp.base.ActivityBase;
import br.gov.al.itec.gpmapp.models.Beneficiario;
import br.gov.al.itec.gpmapp.models.CommRespostaToken;
import br.gov.al.itec.gpmapp.models.DependentesBeneficiarios;
import br.gov.al.itec.gpmapp.models.Matricula;
import br.gov.al.itec.gpmapp.models.Matriculas;
import br.gov.al.itec.gpmapp.tasks.TaskObtemToken;
import br.gov.al.itec.gpmapp.tasks.TaskSeducGet;
import br.gov.al.itec.gpmapp.util.Apoio;
import br.gov.al.itec.gpmapp.util.DialogAlerta;
import br.gov.al.itec.gpmapp.util.IRetornoMASCallbackJSON;
import br.gov.al.itec.gpmapp.util.LogTrace;
import br.gov.al.itec.gpmapp.util.RetornoMASCallbackJSON;
import br.gov.al.itec.gpmapp.util.RetornoMASConnectionListener;
import br.gov.al.itec.gpmapp.util.ValidarCPF;

/**
 * Classe FrmCadastroSenha
 */
public class FrmIpasealInicio extends ActivityBase implements View.OnClickListener, IRetornoMASCallbackJSON, Runnable
{
    // Controles da classe
    private Toolbar toolbar = null;
    private Button cmdConsultarSituacao = null;
    private ProgressDialog progressDialogMeusDados = null;
    private CoordinatorLayout coordinatorLayoutAtualizacaoCad = null;


    //seduz
    private Spinner  txtCodigo = null;
    private int iTipoComunicacao = 0;
    private boolean bSucesso = false;
    private String sCpf = null;
    private String sBeneficiario = null;
    private ListView listView = null;
    private DependentesBeneficiarios listaBeneficiarios = null;
    private String inscricaoCorrente;

    private JSONObject jsonObjectRetornoDados = null;


    @Override
    protected void onCreate(Bundle bundleSavedInstanceState)
    {
        // Carrega a tela
        setContentView(R.layout.frm_ipaseal_inicio);

        //chama os métodos automáticos da classe pai
        super.onCreate(bundleSavedInstanceState);
    }

    private void createDependentesBeneficiarios(String jsonResponse){

        if (listaBeneficiarios == null) {
            listaBeneficiarios = new DependentesBeneficiarios(jsonResponse);
            listaBeneficiarios.Create();
        }
    }

    public void refresh(String json){

        if (!Apoio.isJSONValid(json)) return;

        createDependentesBeneficiarios(json);

        DependentesBeneficiariosListView customList = new DependentesBeneficiariosListView(this,
                listaBeneficiarios.getBeneficiariosStringList("nome"),
                listaBeneficiarios.getBeneficiariosStringList("plano_odontologico"),
                listaBeneficiarios.getBeneficiariosStringList("situacao")
                );

        listView = (ListView) findViewById(R.id.listViewBeneficiarios);
        listView.setAdapter(customList);


            /* Get Listview itens click*/
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String item = (String) adapterView.getItemAtPosition(i);
                Toast.makeText(view.getContext(), item, Toast.LENGTH_SHORT).show();
                Beneficiario m = listaBeneficiarios.getParticipant(item);


            }
        });


    }
    @Override
    public void iniciaControles() throws Exception
    {
        Drawable drawableArrow = null;


        // Obtem os controles
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        //Botao Continuar Matricula
        cmdConsultarSituacao = (Button) findViewById(R.id.cmdConsultarSituacao);
        

        //Muda a cor da flecha padrão do Android para branco
        drawableArrow = ContextCompat.getDrawable(this, R.drawable.abc_ic_ab_back_material);
        drawableArrow.setColorFilter(ContextCompat.getColor(this, R.color.branco), PorterDuff.Mode.SRC_ATOP);

        //Seta o navigation item
        toolbar.setNavigationIcon(drawableArrow);

        //Troca a cor do botao de voltar
        toolbar.setNavigationContentDescription(Apoio.TOOLBAR_VOLTAR);

        //Seta o listener dos controles
        cmdConsultarSituacao.setOnClickListener(this);

        //Coloca a toolbar como actionbar e adiciona as opções de menu
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        inscricaoCorrente = new String();
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

    private void obtemSituacao() throws Exception
    {
        MobileSso mobileSso = null;
        URI uri = null;
        MASRequest masrequestDados = null;
        MASRequest.MASRequestBuilder builder = null;

        //Cria o dialogo e exibe mensagem
        progressDialogMeusDados = Apoio.criarProgressDialog(this);
        Apoio.progressDialogMensagem(progressDialogMeusDados, getString(R.string.msg_ipaseal_obtendo));

        //Define o tipo da comunicação
        iTipoComunicacao = Apoio.TIPO_COMUNICACAO_SAUDE;

        //Obtem a instancia da comunicação via SSO
        mobileSso = MobileSsoFactory.getInstance(this);


        uri = mobileSso.getURI(getString(R.string.url_conexao_gateway) + "gpm/al/ipaseal/contrato/situacao?cpf="+ ValidarCPF.imprimeCPF(sCpf));
        builder = new MASRequest.MASRequestBuilder(uri);
        builder.header("Content-Type", "application/json");

        //Constroi a URL de envio e faz a chamada
        masrequestDados = builder.connectionListener(new RetornoMASConnectionListener()).build();
        MAS.invoke(masrequestDados, new RetornoMASCallbackJSON(this));
    }

    @Override
    public void carregaDados() throws Exception
    {

        //Obtem os dados de cadastro do usuário
        obtemDadosCadastroUsuario();


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
            //Quando apertar o botão entrar
            if (view == cmdConsultarSituacao){


                //Obtem Situacao
                    obtemSituacao();
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
            if(iRequest == Apoio.RETORNO_TELA_SAUDE)
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
            else if (classe == TaskSeducGet.class)
            {
                //Obtem a mensagem de erro
                sMensagem = (String) oObjetos[0];

                //Se o resultado for sucesso
                if (bResultado)
                {

                }

                //Chama a tela de cadastro com sucesso
                //chamaTelaSefazDaeBoleto(bResultado, sMensagem);
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
            if (( iTipoComunicacao == Apoio.TIPO_COMUNICACAO_SAUDE ) || ( iTipoComunicacao == Apoio.TIPO_COMUNICACAO_MEUS_DADOS ))
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
            snackbarErro = Snackbar.make(coordinatorLayoutAtualizacaoCad, getString(R.string.msg_meus_dados_impossivel_obter_matriculas), Snackbar.LENGTH_LONG);
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
        sBeneficiario = jsonObjectRetornoDados.getString("nomeCompleto");


    }

    @Override
    public void run() {
        {
            try
            {
                //Se for os serviços
                if ( iTipoComunicacao == Apoio.TIPO_COMUNICACAO_SAUDE )
                {
                    //Se for sucesso
                    if ( bSucesso )
                    {
                        //Preenche os dados do usuário
                        refresh(jsonObjectRetornoDados.toString());

                    }
                    else
                    {
                        // faz nada aind...
                    }
                }
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
                        //Limpa a tela
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
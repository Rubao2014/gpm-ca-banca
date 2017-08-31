package br.gov.ba.prefeitura.gpmapp.telas;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.ca.mas.core.MobileSso;
import com.ca.mas.core.MobileSsoFactory;
import com.ca.mas.foundation.MAS;
import com.ca.mas.foundation.MASRequest;
import com.ca.mas.foundation.MASResponse;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.net.URI;

import br.gov.ba.prefeitura.gpmapp.R;
import br.gov.ba.prefeitura.gpmapp.adapters.PagerAdapterDetran;
import br.gov.ba.prefeitura.gpmapp.base.ActivityBase;
import br.gov.ba.prefeitura.gpmapp.models.CommRespostaToken;
import br.gov.ba.prefeitura.gpmapp.models.Pontos;
import br.gov.ba.prefeitura.gpmapp.models.Vinculos;
import br.gov.ba.prefeitura.gpmapp.tasks.TaskDetranConsultaPontuacao;
import br.gov.ba.prefeitura.gpmapp.tasks.TaskObtemToken;
import br.gov.ba.prefeitura.gpmapp.util.Apoio;
import br.gov.ba.prefeitura.gpmapp.util.DialogAlerta;
import br.gov.ba.prefeitura.gpmapp.util.IRetornoMASCallbackJSON;
import br.gov.ba.prefeitura.gpmapp.util.LogTrace;
import br.gov.ba.prefeitura.gpmapp.util.RestCommunication;
import br.gov.ba.prefeitura.gpmapp.util.RetornoMASCallbackJSON;
import br.gov.ba.prefeitura.gpmapp.util.RetornoMASConnectionListener;

/**
 * Tela de atualização cadastral com endereço e identificação
 */
public class FrmDetran extends ActivityBase implements TabLayout.OnTabSelectedListener, IRetornoMASCallbackJSON, Runnable
{
    //Controles da classe
    private TabLayout tabLayout = null;
    private ViewPager viewPager = null;
    private Toolbar toolbar = null;
    private String cpf = null;
    private CoordinatorLayout coordinatorLayoutAtualizacaoCad = null;
    private ProgressDialog progressDialogDetran = null;
    private FrgDetranPontuacao frgDetranPontuacao = null;
    private ProgressDialog progressDialogMeusDados = null;
    //private FrgDetranDadosVeiculo frgDetranDadosVeiculo = null;

    //Variáveis da classe
    private int iTipoComunicacao = 0;
    private PagerAdapterDetran pagerAdapterDetran = null;
    private JSONObject jsonObjectRetornoDados = null;
    private JSONObject jsonObjectRetornoGravacao = null;
    private boolean bSucesso = false;
    private boolean bPermiteRecadastro = true;
    private Vinculos vinculos = null;
    private String sProtocolo = "";

    @Override
    protected void onCreate(Bundle bundleSavedInstanceState)
    {
        //Carrega o formulario
        setContentView(R.layout.frm_detran);

        //O super vem depois, pois como estamos implementando de uma classe base, se não ficar na sequencia não carrega os controles
        super.onCreate(bundleSavedInstanceState);
    }

    @Override
    public void iniciaControles() throws Exception
    {
        Drawable drawableArrow = null;

        //Carrega os controles da classe
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        coordinatorLayoutAtualizacaoCad = (CoordinatorLayout) findViewById(R.id.coordinatorLayoutAtualizacaoCad);

        //Muda a cor da flecha padrão do Android para branco
        drawableArrow = ContextCompat.getDrawable(this, R.drawable.abc_ic_ab_back_material);
        drawableArrow.setColorFilter(ContextCompat.getColor(this, R.color.branco), PorterDuff.Mode.SRC_ATOP);

        //Seta o navigation item
        toolbar.setNavigationIcon(drawableArrow);

        //adiciona a toolbar como uma actionbar, possibilitando inflar itens
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        //Instancia os fragmentos
        frgDetranPontuacao = new FrgDetranPontuacao();
        //frgAtualizacaoCadDocumentos = new FrgAtualizacaoCadDocumentos();
        //frgAtualizacaoCadEndereco = new FrgAtualizacaoCadEndereco();
        //frgAtualizacaoCadDependentes = new FrgAtualizacaoCadDependentes();
        //frgAtualizacaoCadCursos = new FrgAtualizacaoCadCursos();
        //frgAtualizacaoCadVinculo = new FrgAtualizacaoCadVinculo();
        //frgAtualizacaoCadQuestionario = new FrgAtualizacaoCadQuestionario();


        //Inicia o adapter do viewpager principal
        pagerAdapterDetran = new PagerAdapterDetran(getSupportFragmentManager(), frgDetranPontuacao);
        viewPager.setAdapter(pagerAdapterDetran);

        //Seta a quantidade de guias DETRAN
        viewPager.setOffscreenPageLimit(Apoio.DETRAN_ABAS);

        //adiciona o listener de seleção para alterarmos os icones sempre que a aba for alterada
        tabLayout.setupWithViewPager(viewPager);

        //adiciona os icones nas abas
        tabLayout.getTabAt(Apoio.DETRAN_PONTUACAO).setText(getString(R.string.tablayout_detran_pontos));
        //tabLayout.getTabAt(Apoio.DETRAN_DADOS_VEICULO).setText(getString(R.string.tablayout_detran_dados_veiculo));
        //Seta o listener
        tabLayout.setOnTabSelectedListener(this);
    }

    @Override
    public void carregaDados() throws Exception
    {


        //Obtem os dados de cadastro do usuário
        obtemDadosCadastroUsuario();

        //Sera chamado se o retorno do cpf for OK!!!
        //Obtem os dados de cadastro
        //obtemPontuacao();


    }



    /**
     * Monta os dados de envio no JSON
     */
    private JSONObject montaDadosJSONEnvio() throws Exception {
        return null;
    }

    /**
     * Obtem os dados de atualização cadastral
     */
    private void obtemPontuacao() throws Exception
    {

        String sBody = "";

        try
        {


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
        catch (Exception err)
        {
            LogTrace.escreve(Apoio.localErro(getClass(), err), Apoio.getPathLogsSDCard(this), Apoio.getArqErr());
            DialogAlerta.show(this, Apoio.getMsgErr(getClass(), err), getString(R.string.atencao), getString(R.string.ok));
        }

    }

    /**
     * Carrega os dados de atualização cadastral
     */
    private void carregaPontuacao() throws Exception
    {
        String sDados = "";
        Gson gsonDados = null;
        Pontos pontos = null;

        //Instancia o gson
        gsonDados = new Gson();

        //Obtem os pontos
        sDados = jsonObjectRetornoDados.getString("pontuacaoTotal");
        /*
            { "pontuacaoTotal": "7"}

         */

        pontos = new Pontos();//gsonDados.fromJson(sDados, new TypeToken<Pontos>(){}.getType());
        pontos.sPontos = sDados;
        //Passa o JSON para o método da tela que faz o preenchimento
        frgDetranPontuacao.carregaCamposTela(pontos);

    }

    /**
     * Limpa os campos das telas
     */
    private void limpaCamposTelas() throws Exception
    {
        //Limpa os erros dos campos
        frgDetranPontuacao.removerErroDosCampos();

        //Limpa os campos de documentos
        frgDetranPontuacao.limpaCamposTela();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        try
        {
            //Infla os icones na toolbar
            getMenuInflater().inflate(R.menu.mnu_detran, menu);
        }
        catch (Exception err)
        {
            LogTrace.escreve(Apoio.localErro(getClass(), err), Apoio.getPathLogsSDCard(this), Apoio.getArqErr());
            DialogAlerta.show(this, Apoio.getMsgErr(getClass(), err), getString(R.string.atencao), getString(R.string.ok));
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
                //Verifico se o click é no gravar
                case R.id.mnuGravar:
                {
                    //Grava os dados da tela e chama as fotos
                    //gravaDadosChamaTelaFotos();
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
    public void onTabSelected(TabLayout.Tab tab)
    {
        View view = null;
        InputMethodManager inputMethodManager = null;
        //Se for as guias de dependentes e cursos
        //
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab)
    {
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab)
    {
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

    /*
    @Override
    public void onError(Throwable throwable)
    {
        Snackbar snackbarErro = null;

        try
        {
            // fecha o dialog
            Apoio.fecharProgressDialog(progressDialogDetran);

            //Loga o erro retornado pela CA
            LogTrace.escreve(Apoio.localErro(getClass(), throwable), Apoio.getPathLogsSDCard(this), Apoio.getArqErr());

            //Se for o tipo de comunicacao de dados cadastrais (carga)
            if ( iTipoComunicacao == Apoio.TIPO_COMUNICACAO_DADOS_CARGA)
            {
                //Monta snackbar com erro
                snackbarErro = Snackbar.make(coordinatorLayoutAtualizacaoCad, getString(R.string.msg_atualizacao_cad_impossivel_obter_dados), Snackbar.LENGTH_LONG);
                snackbarErro.show();

                //Seta com erro
                bSucesso = false;

                //Chama o preenchimento para rodar em thread, pois no retorno do sucesso, não conseguimos acessar os objetos de tela
                this.runOnUiThread(this);
            }
            //Se for o tipo de comunicacao de dados cadastrais (gravação)
            /*
            else if ( iTipoComunicacao == Apoio.TIPO_COMUNICACAO_DADOS_GRAVACAO )
            {
                //Monta snackbar com erro
                snackbarErro = Snackbar.make(coordinatorLayoutAtualizacaoCad, getString(R.string.msg_atualizacao_cad_impossivel_gravar_dados), Snackbar.LENGTH_LONG);
                snackbarErro.show();
            }

        }
        catch(Exception err)
        {
            LogTrace.escreve(Apoio.localErro(getClass(), err), Apoio.getPathLogsSDCard(this), Apoio.getArqErr());
            DialogAlerta.show(this, Apoio.getMsgErr(getClass(), err), getString(R.string.atencao), getString(R.string.ok));
        }
    }
    */
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
        cpf = jsonObjectRetornoDados.getString("cpf");
    }

    @Override
    public void run()
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

                    obtemPontuacao();
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

    /*
    @Override
    public void run()
    {
        Intent intent = null;

        try
        {
            //Se for os dados cadastrais (carga)
            if ( iTipoComunicacao == Apoio.TIPO_COMUNICACAO_DADOS_CARGA)
            {
                //Se for sucesso
                if ( bSucesso )
                {
                    //Carrega os dados na tela
                    carregaPontuacao();
                }
                else
                {
                    //Limpa os campos das telas
                    limpaCamposTelas();
                }
            }
            //Se for o tipo de comunicacao de dados cadastrais (gravação)
            else if ( iTipoComunicacao == Apoio.DADOS_CADASTRAIS_GRAVACAO)
            {
                //Chama a tela
                //intent = new Intent(this, FrmAtualizacaoCadFotos.class);
                //intent.putExtra(Apoio.TELA_FOTOS_PROTOCOLO, sProtocolo);
                //startActivityForResult(intent, Apoio.RETORNO_TELA_ATUALIZACAO_CADASTRAL_FOTOS);
            }
        }
        catch(Exception err)
        {
            LogTrace.escreve(Apoio.localErro(getClass(), err), Apoio.getPathLogsSDCard(this), Apoio.getArqErr());
            DialogAlerta.show(this, Apoio.getMsgErr(getClass(), err), getString(R.string.atencao), getString(R.string.ok));
        }
    }
    */

    @Override
    protected void onActivityResult(int iRequestCode, int iResultado, Intent intent)
    {
        try
        {

        }
        catch (Exception err)
        {
            LogTrace.escreve(Apoio.localErro(getClass(), err), Apoio.getPathLogsSDCard(this), Apoio.getArqErr());
            DialogAlerta.show(this, Apoio.getMsgErr(getClass(), err), getString(R.string.atencao), getString(R.string.ok));
        }
    }

    /**
     * Chama a comunicação para o cadsatro do usuário
     */
    private void realizaComunicacaoDetranConsultaPontuacao(CommRespostaToken commRespostaToken) throws Exception
    {
        String sBody = "";

         //Chama a task de gravação do usuário
        new TaskDetranConsultaPontuacao(this, this, sBody, commRespostaToken.sAccessToken, RestCommunication.RESTPOST, cpf).execute();
    }

    /**
     * Chama a tela de finalizado
     */
    private void chamaTelaDetran(boolean bSucesso, String sMensagem) throws Exception
    {
        Intent intent = null;

        //Chama a tela principal
        intent = new Intent(this, FrmFinalizado.class);
        intent.putExtra(Apoio.TELA_SUCESSO_TIPO_MENSAGEM, bSucesso);
        intent.putExtra(Apoio.TELA_SUCESSO_MENSAGEM, sMensagem);
        intent.putExtra(Apoio.TELA_SUCESSO_TITULO, getString(R.string.toolbar_detran));
        startActivityForResult(intent, Apoio.RETORNO_TELA_DETRAN);
    }

    /**
     * Obtem os dados de cadastro do usuário
     */
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
                    realizaComunicacaoDetranConsultaPontuacao(commRespostaToken);
                }
                else
                {
                    //Obtem a mensagem de erro
                    sMensagem = (String) oObjetos[0];
                    jsonObjectRetornoDados = new JSONObject(sMensagem);
                    //Chama a tela de finalizado com erro
                    //chamaTelaDetran(false, sMensagem);
                    carregaPontuacao();
                }
            }
            //Se for a classe da task de gravação
            else if (classe == TaskDetranConsultaPontuacao.class)
            {
                //Obtem a mensagem de erro
                sMensagem = (String) oObjetos[0];


                //Chama a tela de cadastro com sucesso
                // chamaTelaDetran(bResultado, sMensagem);
                //Armazena o retorno
                jsonObjectRetornoDados = new JSONObject(sMensagem);
                carregaPontuacao();
            }
        }
        catch (Exception err)
        {
            LogTrace.escreve(Apoio.localErro(getClass(), err), Apoio.getPathLogsSDCard(this), Apoio.getArqErr());
            DialogAlerta.show(this, Apoio.getMsgErr(getClass(), err), getString(R.string.atencao), getString(R.string.ok));
        }
    }
}
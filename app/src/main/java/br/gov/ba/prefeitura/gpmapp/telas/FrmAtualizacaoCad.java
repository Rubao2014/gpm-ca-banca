package br.gov.ba.prefeitura.gpmapp.telas;

import android.app.ProgressDialog;
import android.content.Context;
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
import com.ca.mas.foundation.MASRequestBody;
import com.ca.mas.foundation.MASResponse;
import com.ca.mas.foundation.MASResponseBody;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;
import java.net.URI;
import java.util.ArrayList;
import br.gov.ba.prefeitura.gpmapp.R;
import br.gov.ba.prefeitura.gpmapp.adapters.PagerAdapterAtualizacaoCad;
import br.gov.ba.prefeitura.gpmapp.base.ActivityBase;
import br.gov.ba.prefeitura.gpmapp.models.Cursos;
import br.gov.ba.prefeitura.gpmapp.models.Dependentes;
import br.gov.ba.prefeitura.gpmapp.models.Documentos;
import br.gov.ba.prefeitura.gpmapp.models.Enderecos;
import br.gov.ba.prefeitura.gpmapp.models.Questionarios;
import br.gov.ba.prefeitura.gpmapp.models.Vinculos;
import br.gov.ba.prefeitura.gpmapp.util.Apoio;
import br.gov.ba.prefeitura.gpmapp.util.DialogAlerta;
import br.gov.ba.prefeitura.gpmapp.util.IRetornoMASCallbackJSON;
import br.gov.ba.prefeitura.gpmapp.util.LogTrace;
import br.gov.ba.prefeitura.gpmapp.util.RetornoMASCallbackJSON;
import br.gov.ba.prefeitura.gpmapp.util.RetornoMASConnectionListener;

/**
 * Tela de atualização cadastral com endereço e identificação
 */
public class FrmAtualizacaoCad extends ActivityBase implements TabLayout.OnTabSelectedListener, IRetornoMASCallbackJSON, Runnable
{
    //Controles da classe
    private TabLayout tabLayout = null;
    private ViewPager viewPager = null;
    private Toolbar toolbar = null;
    private CoordinatorLayout coordinatorLayoutAtualizacaoCad = null;
    private ProgressDialog progressDialogAtualizacaoCadastral = null;
    private FrgAtualizacaoCadDocumentos frgAtualizacaoCadDocumentos = null;
    private FrgAtualizacaoCadEndereco frgAtualizacaoCadEndereco = null;
    private FrgAtualizacaoCadDependentes frgAtualizacaoCadDependentes = null;
    private FrgAtualizacaoCadCursos frgAtualizacaoCadCursos = null;
    private FrgAtualizacaoCadVinculo frgAtualizacaoCadVinculo = null;
    private FrgAtualizacaoCadQuestionario frgAtualizacaoCadQuestionario = null;

    //Variáveis da classe
    private int iTipoComunicacao = 0;
    private PagerAdapterAtualizacaoCad pagerAdapterAtualizacaoCad = null;
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
        setContentView(R.layout.frm_atualizacao_cad);

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
        frgAtualizacaoCadDocumentos = new FrgAtualizacaoCadDocumentos();
        frgAtualizacaoCadEndereco = new FrgAtualizacaoCadEndereco();
        frgAtualizacaoCadDependentes = new FrgAtualizacaoCadDependentes();
        frgAtualizacaoCadCursos = new FrgAtualizacaoCadCursos();
        frgAtualizacaoCadVinculo = new FrgAtualizacaoCadVinculo();
        frgAtualizacaoCadQuestionario = new FrgAtualizacaoCadQuestionario();

        //Inicia o adapter do viewpager principal
        pagerAdapterAtualizacaoCad = new PagerAdapterAtualizacaoCad(getSupportFragmentManager(), frgAtualizacaoCadDocumentos, frgAtualizacaoCadEndereco, frgAtualizacaoCadDependentes, frgAtualizacaoCadCursos, frgAtualizacaoCadVinculo, frgAtualizacaoCadQuestionario);
        viewPager.setAdapter(pagerAdapterAtualizacaoCad);

        //Seta a quantidade de guias
        viewPager.setOffscreenPageLimit(Apoio.ATUALIZACAO_CAD_QTD_ABAS);

        //adiciona o listener de seleção para alterarmos os icones sempre que a aba for alterada
        tabLayout.setupWithViewPager(viewPager);

        //adiciona os icones nas abas
        tabLayout.getTabAt(Apoio.ATUALIZACAO_CAD_TAB_DOCUMENTOS).setText(getString(R.string.tablayout_documentos));
        tabLayout.getTabAt(Apoio.ATUALIZACAO_CAD_TAB_ENDERECO).setText(getString(R.string.tablayout_endereco));
        tabLayout.getTabAt(Apoio.ATUALIZACAO_CAD_TAB_DEPENDENTES).setText(getString(R.string.tablayout_dependentes));
        tabLayout.getTabAt(Apoio.ATUALIZACAO_CAD_TAB_CURSOS).setText(getString(R.string.tablayout_cursos));
        tabLayout.getTabAt(Apoio.ATUALIZACAO_CAD_TAB_VINCULO).setText(getString(R.string.tablayout_vinculo));
        tabLayout.getTabAt(Apoio.ATUALIZACAO_CAD_TAB_QUESTIONARIO).setText(getString(R.string.tablayout_questionario));

        //Seta o listener
        tabLayout.setOnTabSelectedListener(this);
    }

    @Override
    public void carregaDados() throws Exception
    {
        //Obtem os dados de cadastro
        obtemDadosCadastroUsuario();

        //Mostra o teclado
        ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    /**
     * Grava os dados da tela e chama as fotos
     */
    private void gravaDadosChamaTelaFotos() throws Exception
    {
        Snackbar snackbarErro = null;
        MobileSso mobileSso = null;
        URI uri = null;
        MASRequest masrequestDados = null;
        MASRequest.MASRequestBuilder builder = null;
        JSONObject jsonObjectDados = null;

        //Chama a tela
        //TODO: Esta fixo para teste, mas precisa liberar o código abaixo
        //Intent intent = null;
        //intent = new Intent(this, FrmAtualizacaoCadFotos.class);
        //intent.putExtra(Apoio.TELA_FOTOS_PROTOCOLO, sProtocolo);
        //startActivityForResult(intent, Apoio.RETORNO_TELA_ATUALIZACAO_CADASTRAL_FOTOS);

        //Se não houve sucesso na comunicação para obter os dados
        if ( !bSucesso )
        {
            //Monta snackbar com erro
            snackbarErro = Snackbar.make(coordinatorLayoutAtualizacaoCad, getString(R.string.msg_atualizacao_cad_impossivel_obter), Snackbar.LENGTH_LONG);
            snackbarErro.show();
            return;
        }

        //Se não permite recadastro
        /*if ( !bPermiteRecadastro )
        {
            //Monta snackbar com erro
            snackbarErro = Snackbar.make(coordinatorLayoutAtualizacaoCad, getString(R.string.msg_atualizacao_cad_impossivel_recadastro), Snackbar.LENGTH_SHORT);
            snackbarErro.show();
            return;
        }*/

        //Remove os erros atuais dos campos
        frgAtualizacaoCadDocumentos.removerErroDosCampos();
        frgAtualizacaoCadEndereco.removerErroDosCampos();
        frgAtualizacaoCadQuestionario.removerErroDosCampos();

        //Valida os campos da tela
        if (!frgAtualizacaoCadDocumentos.validaCampos(this, viewPager))
        {
            return;
        }

        //Valida os campos da tela
        if (!frgAtualizacaoCadEndereco.validaCampos(this, viewPager))
        {
            return;
        }

        //Valida os campos da tela
        if (!frgAtualizacaoCadQuestionario.validaCampos(this, viewPager))
        {
            return;
        }

        //Cria o dialogo e exibe mensagem
        progressDialogAtualizacaoCadastral = Apoio.criarProgressDialog(this);
        Apoio.progressDialogMensagem(progressDialogAtualizacaoCadastral, getString(R.string.msg_atualizacao_cad_gravando_dados));

        //Define o tipo da comunicação
        iTipoComunicacao = Apoio.DADOS_CADASTRAIS_GRAVACAO;

        //Obtem a instancia da comunicação via SSO
        mobileSso = MobileSsoFactory.getInstance(this);

        //Realiza a chamada da comunicação na URL de obtenção das chaves de permissão
        uri = mobileSso.getURI(getString(R.string.url_conexao_gateway) + "governo/v1/servidores/eu");
        builder = new MASRequest.MASRequestBuilder(uri);
        builder.header("Content-Type", "application/json");

        //Monta os dados do JSON de envio
        jsonObjectDados = montaDadosJSONEnvio();

        String sAuxi = jsonObjectDados.toString().replace("\\", "");
        jsonObjectDados = new JSONObject(sAuxi);

        //Constroi a URL de envio e faz a chamada
        masrequestDados = builder.responseBody(MASResponseBody.jsonBody()).put(MASRequestBody.jsonBody(jsonObjectDados)).connectionListener(new RetornoMASConnectionListener()).build();
        MAS.invoke(masrequestDados, new RetornoMASCallbackJSON(this));
    }

    /**
     * Monta os dados de envio no JSON
     */
    private JSONObject montaDadosJSONEnvio() throws Exception
    {
        JSONObject jsonObjectDados = null;
        JSONObject jsonAuxi = null;
        JSONArray jsonArrayAuxi = null;
        Documentos documentos = null;
        Enderecos enderecos = null;
        Questionarios questionarios = null;
        ArrayList<Dependentes> arrDependentes = null;
        ArrayList<Cursos> arrCursos = null;
        Gson gsonDados = null;
        String sDados = "";

        //Seta os campos nas models
        documentos = frgAtualizacaoCadDocumentos.retornaDadosTela();
        enderecos = frgAtualizacaoCadEndereco.retornaDadosTela();
        questionarios = frgAtualizacaoCadQuestionario.retornaDadosTela();
        arrDependentes = frgAtualizacaoCadDependentes.retornaDadosTela();
        arrCursos = frgAtualizacaoCadCursos.retornaDadosTela();

        //Adiciona o JSON
        jsonObjectDados = new JSONObject();

        //Instancia o gson
        gsonDados = new Gson();

        //Adiciona os documentos
        sDados = gsonDados.toJson(documentos);
        jsonAuxi = new JSONObject(sDados);
        jsonObjectDados.put("documento", jsonAuxi);

        //Adiciona os endereços
        sDados = gsonDados.toJson(enderecos);
        jsonAuxi = new JSONObject(sDados);
        jsonObjectDados.put("endereco", jsonAuxi);

        //Adiciona os questionarios
        sDados = gsonDados.toJson(questionarios);
        jsonAuxi = new JSONObject(sDados);
        jsonObjectDados.put("questionario", jsonAuxi);

        //Adiciona os vinculos
        sDados = gsonDados.toJson(vinculos);
        jsonAuxi = new JSONObject(sDados);
        jsonObjectDados.put("vinculo", jsonAuxi);

        //Adiciona os cursos
        sDados = gsonDados.toJson(arrCursos);
        jsonArrayAuxi = new JSONArray(sDados);
        jsonObjectDados.put("cursos", jsonArrayAuxi);

        //Adiciona os dependentes
        sDados = gsonDados.toJson(arrDependentes);
        jsonArrayAuxi = new JSONArray(sDados);
        jsonObjectDados.put("dependentes", jsonArrayAuxi);

        return jsonObjectDados;
    }

    /**
     * Obtem os dados de atualização cadastral
     */
    private void obtemDadosCadastroUsuario() throws Exception
    {
        MobileSso mobileSso = null;
        URI uri = null;
        MASRequest masrequestDados = null;
        MASRequest.MASRequestBuilder builder = null;

        //Cria o dialogo e exibe mensagem
        progressDialogAtualizacaoCadastral = Apoio.criarProgressDialog(this);
        Apoio.progressDialogMensagem(progressDialogAtualizacaoCadastral, getString(R.string.msg_atualizacao_cad_obtendo_dados));

        //Define o tipo da comunicação
        iTipoComunicacao = Apoio.TIPO_COMUNICACAO_DADOS_CARGA;

        //Obtem a instancia da comunicação via SSO
        mobileSso = MobileSsoFactory.getInstance(this);

        //Realiza a chamada da comunicação na URL de obtenção das chaves de permissão
        uri = mobileSso.getURI(getString(R.string.url_conexao_gateway) + "governo/v1/servidores/eu");
        builder = new MASRequest.MASRequestBuilder(uri);
        builder.header("Content-Type", "application/json");

        //Constroi a URL de envio e faz a chamada
        masrequestDados = builder.connectionListener(new RetornoMASConnectionListener()).build();
        MAS.invoke(masrequestDados, new RetornoMASCallbackJSON(this));
    }

    /**
     * Carrega os dados de atualização cadastral
     */
    private void carregaDadosAtualizacaoCadastral() throws Exception
    {
        String sDados = "";
        Gson gsonDados = null;
        Documentos documentos = null;
        Enderecos enderecos = null;
        Questionarios questionarios = null;
        ArrayList<Dependentes> arrDependentes = null;
        ArrayList<Cursos> arrCursos = null;

        //Instancia o gson
        gsonDados = new Gson();

        //Obtem a flag de recadastro
        bPermiteRecadastro = jsonObjectRetornoDados.getBoolean("recadastrado");

        //Obtem os dados de documento
        sDados = jsonObjectRetornoDados.getString("documento");
        documentos = gsonDados.fromJson(sDados, new TypeToken<Documentos>(){}.getType());

        //Obtem os dados de endereço
        sDados = jsonObjectRetornoDados.getString("endereco");
        enderecos = gsonDados.fromJson(sDados, new TypeToken<Enderecos>(){}.getType());

        //Obtem os dados de questionários
        sDados = jsonObjectRetornoDados.getString("questionario");
        questionarios = gsonDados.fromJson(sDados, new TypeToken<Questionarios>(){}.getType());

        //Obtem os dados de vinculos
        sDados = jsonObjectRetornoDados.getString("vinculo");
        vinculos = gsonDados.fromJson(sDados, new TypeToken<Vinculos>(){}.getType());

        //Obtem os dados de cursos
        sDados = jsonObjectRetornoDados.getString("cursos");
        arrCursos = gsonDados.fromJson(sDados, new TypeToken<ArrayList<Cursos>>(){}.getType());

        //Obtem os dados de dependentes
        sDados = jsonObjectRetornoDados.getString("dependentes");
        arrDependentes = gsonDados.fromJson(sDados, new TypeToken<ArrayList<Dependentes>>(){}.getType());

        //Passa o JSON para o método da tela que faz o preenchimento
        frgAtualizacaoCadDocumentos.carregaCamposTela(documentos);
        frgAtualizacaoCadEndereco.carregaCamposTela(enderecos);
        frgAtualizacaoCadQuestionario.carregaCamposTela(questionarios);
        frgAtualizacaoCadCursos.preencheListaCursos(arrCursos);
        frgAtualizacaoCadVinculo.carregaCamposTela(vinculos);
        frgAtualizacaoCadDependentes.preencheListaDependentes(arrDependentes);
    }

    /**
     * Limpa os campos das telas
     */
    private void limpaCamposTelas() throws Exception
    {
        //Limpa os erros dos campos
        frgAtualizacaoCadDocumentos.removerErroDosCampos();
        frgAtualizacaoCadEndereco.removerErroDosCampos();
        frgAtualizacaoCadQuestionario.removerErroDosCampos();

        //Limpa os campos de documentos
        frgAtualizacaoCadDocumentos.limpaCamposTela();
        frgAtualizacaoCadEndereco.limpaCamposTela();
        frgAtualizacaoCadDependentes.limpaCamposTela();
        frgAtualizacaoCadCursos.limpaCamposTela();
        frgAtualizacaoCadVinculo.limpaCamposTela();
        frgAtualizacaoCadQuestionario.limpaCamposTela();
    }

    /**
     * Chama a tela de cadastro de novo curso
     */
    public void chamaTelaNovoAtualizacaoCadCursos(Cursos cursos) throws Exception
    {
        Intent intent = null;

        //Chama a tela Cadastrar Cursos
        intent = new Intent(this, FrmAtualizacaoCadCursosNovo.class);
        intent.putExtra(Apoio.TELA_CURSOS_OBJETO_ALTERACAO, cursos);
        startActivityForResult(intent, Apoio.RETORNO_TELA_ATUALIZACAO_CURSOS_CADASTRO);
    }

    /**
     * Chama a tela de cadastro de novo dependente
     */
    public void chamaTelaNovoAtualizacaoCadDependentes(Dependentes dependentes) throws Exception
    {
        Intent intent = null;

        //Chama a tela Cadastrar Dependentes
        intent = new Intent(this, FrmAtualizacaoCadDependentesNovo.class);
        intent.putExtra(Apoio.TELA_DEPENDENTES_OBJETO_ALTERACAO, dependentes);
        startActivityForResult(intent, Apoio.RETORNO_TELA_ATUALIZACAO_DEPENDENTES_CADASTRO);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        try
        {
            //Infla os icones na toolbar
            getMenuInflater().inflate(R.menu.mnu_atualizacao_cad, menu);
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
                    gravaDadosChamaTelaFotos();
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
        if (tab.getPosition() == Apoio.ATUALIZACAO_CAD_TAB_DEPENDENTES || tab.getPosition() == Apoio.ATUALIZACAO_CAD_TAB_CURSOS)
        {
            //Obtem a view
            view = this.getCurrentFocus();

            //Se tiver uma view
            if (view != null)
            {
                //Remove o teclado
                inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
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
            Apoio.fecharProgressDialog(progressDialogAtualizacaoCadastral);

            //Se for o tipo de comunicacao de dados cadastrais (carga)
            if ( iTipoComunicacao == Apoio.TIPO_COMUNICACAO_DADOS_CARGA)
            {
                //Armazena o retorno
                jsonObjectRetornoDados = masresponseObjeto.getBody().getContent();

                //Seta com sucesso
                bSucesso = true;

                //Chama o preenchimento para rodar em thread, pois no retorno do sucesso, não conseguimos acessar os objetos de tela
                this.runOnUiThread(this);
            }
            //Se for o tipo de comunicacao de dados cadastrais (gravação)
            else if ( iTipoComunicacao == Apoio.DADOS_CADASTRAIS_GRAVACAO)
            {
                //Armazena o retorno
                jsonObjectRetornoGravacao = masresponseObjeto.getBody().getContent();

                //Obtem o protocolo
                sProtocolo = jsonObjectRetornoGravacao.getString("protocolo");

                //Chama a thread para carregar a tela de fotos
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
            Apoio.fecharProgressDialog(progressDialogAtualizacaoCadastral);

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
            else if ( iTipoComunicacao == Apoio.DADOS_CADASTRAIS_GRAVACAO)
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
                    carregaDadosAtualizacaoCadastral();
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
                intent = new Intent(this, FrmAtualizacaoCadFotos.class);
                intent.putExtra(Apoio.TELA_FOTOS_PROTOCOLO, sProtocolo);
                startActivityForResult(intent, Apoio.RETORNO_TELA_ATUALIZACAO_CADASTRAL_FOTOS);
            }
        }
        catch(Exception err)
        {
            LogTrace.escreve(Apoio.localErro(getClass(), err), Apoio.getPathLogsSDCard(this), Apoio.getArqErr());
            DialogAlerta.show(this, Apoio.getMsgErr(getClass(), err), getString(R.string.atencao), getString(R.string.ok));
        }
    }

    @Override
    protected void onActivityResult(int iRequestCode, int iResultado, Intent intent)
    {
        try
        {
            //verifica se esta vindo do cadastro de cursos
            if (iRequestCode == Apoio.RETORNO_TELA_ATUALIZACAO_CURSOS_CADASTRO)
            {
                //Verifica se o resultado for OK
                if (iResultado == RESULT_OK)
                {
                    //Chama o método que atualiza ou insere no array o objeto de cursos
                    frgAtualizacaoCadCursos.atualizaInsereCurso((Cursos) intent.getSerializableExtra(Apoio.TELA_CURSOS_OBJETO_ALTERACAO));
                }
            }
            //verifica se esta vindo do cadastro de dependentes
            else if (iRequestCode == Apoio.RETORNO_TELA_ATUALIZACAO_DEPENDENTES_CADASTRO)
            {
                //Verifica se o resultado for OK
                if (iResultado == RESULT_OK)
                {
                    //Chama o método que atualiza ou insere no array o objeto de dependente
                    frgAtualizacaoCadDependentes.atualizaInsereDependente((Dependentes) intent.getSerializableExtra(Apoio.TELA_DEPENDENTES_OBJETO_ALTERACAO));
                }
            }
            //verifica se esta vindo da tela de fotos
            else if (iRequestCode == Apoio.RETORNO_TELA_ATUALIZACAO_CADASTRAL_FOTOS)
            {
                //Finaliza a tela
                Apoio.finalizaActivity(this, RESULT_OK);
            }
        }
        catch (Exception err)
        {
            LogTrace.escreve(Apoio.localErro(getClass(), err), Apoio.getPathLogsSDCard(this), Apoio.getArqErr());
            DialogAlerta.show(this, Apoio.getMsgErr(getClass(), err), getString(R.string.atencao), getString(R.string.ok));
        }
    }
}
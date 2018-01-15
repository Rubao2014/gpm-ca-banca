package br.com.ca.gpmcaieapp.telas;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Pair;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import com.ca.mas.core.MobileSso;
import com.ca.mas.core.MobileSsoFactory;
import com.ca.mas.foundation.MAS;
import com.ca.mas.foundation.MASRequest;
import com.ca.mas.foundation.MASRequestBody;
import com.ca.mas.foundation.MASResponse;
import com.ca.mas.foundation.MASUser;
import org.json.JSONArray;
import org.json.JSONObject;
import java.net.URI;
import java.util.ArrayList;
import br.com.ca.gpmcaieapp.models.CategoriasServicos;
import br.com.ca.gpmcaieapp.models.ItensMenu;
import br.com.ca.gpmcaieapp.R;
import br.com.ca.gpmcaieapp.adapters.AdapterCategoriasDashboard;
import br.com.ca.gpmcaieapp.adapters.AdapterMenuDashboard;
import br.com.ca.gpmcaieapp.base.IComunicacaoGeral;
import br.com.ca.gpmcaieapp.base.ActivityBase;
import br.com.ca.gpmcaieapp.util.Apoio;
import br.com.ca.gpmcaieapp.util.DialogAlerta;
import br.com.ca.gpmcaieapp.util.IRetornoMASCallbackJSONArray;
import br.com.ca.gpmcaieapp.util.IRetornoMASCallbackVoid;
import br.com.ca.gpmcaieapp.util.JSONArrayResponseBody;
import br.com.ca.gpmcaieapp.util.LogTrace;
import br.com.ca.gpmcaieapp.util.RetornoMASCallbackJSONArray;
import br.com.ca.gpmcaieapp.util.RetornoMASCallbackVoid;
import br.com.ca.gpmcaieapp.util.RetornoMASConnectionListener;

/**
 * Classe FrmDashboard
 */
public class FrmDashboard extends ActivityBase implements IComunicacaoGeral, DialogInterface.OnClickListener, IRetornoMASCallbackVoid, Runnable, TextWatcher, IRetornoMASCallbackJSONArray, View.OnClickListener
{
    // Controles da classe
    private Toolbar toolbar = null;
    private DrawerLayout drawerLayout = null;
    private EditText txtBuscar = null;
    private RecyclerView rcvCategorias = null;
    private RecyclerView rcvMenu = null;
    private ImageButton cmdFavoritos = null;
    private AlertDialog dlgSair = null;
    private CoordinatorLayout coordinatorLayoutDashboard = null;
    private ProgressDialog progressDialogDashboard = null;

    //variaveis da classe
    private ArrayList <ItensMenu> arrItensMenu = null;
    private ArrayList <CategoriasServicos> arrCategorias = null;
    private int iTipoComunicacao = 0;
    private JSONArray jsonArrayRetornoServicos = null;
    private boolean bSucesso = false;

    @Override
    protected void onCreate(Bundle bundleSavedInstanceState)
    {
        // Carrega a tela
        setContentView(R.layout.frm_dashboard);

        //chama os métodos automáticos da classe pai
        super.onCreate(bundleSavedInstanceState);
    }

    @Override
    public void iniciaControles() throws Exception
    {
        Drawable drawableMenu = null;

        // Obtem os controles
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        txtBuscar = (EditText) findViewById(R.id.txtBuscar);
        rcvCategorias = (RecyclerView) findViewById(R.id.rcvCategorias);
        rcvMenu = (RecyclerView) findViewById(R.id.rcvMenu);
        cmdFavoritos = (ImageButton) findViewById(R.id.cmdFavoritos);
        coordinatorLayoutDashboard = (CoordinatorLayout) findViewById(R.id.coordinatorLayoutDashboard);

        //Muda a cor da flecha padrão do Android para branco
        drawableMenu = ContextCompat.getDrawable(this, R.mipmap.home_menu);
        drawableMenu.setColorFilter(ContextCompat.getColor(this, R.color.branco), PorterDuff.Mode.SRC_ATOP);

        //Coloca a toolbar como actionbar e adiciona as opções de menu
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //seta os listeners
        txtBuscar.addTextChangedListener(this);
        cmdFavoritos.setOnClickListener(this);
    }

    @Override
    public void carregaDados() throws Exception
    {
        //carrega os items do menu
        obtemItensMenu();

        //Faz o envio do token para o servidor
        enviaTokenPushServidor();
    }

    /**
     * Realiza a obtenção dos serviços ou preenche desabilitado
     * @throws Exception
     */
    private void realizaObtencaoServicos() throws Exception
    {
        MASUser masuserUsuario = null;

        //Se não tiver um usuário logado
        masuserUsuario = Apoio.verificaUsuarioLogado(this);

        //Se tiver usuário
        /*
        if ( masuserUsuario != null )
        {
            //obtem os serviços
            obtemServicos();
        }
        else
        {
            //preenche a lista
            preencheListaServicos();
        }
        */

        preencheListaServicosNew();
    }

    /**
     * Obtem os itens do menu
     */
    private void obtemItensMenu() throws Exception
    {
        AdapterMenuDashboard adapterMenu = null;

        //instancia o array
        arrItensMenu = new ArrayList<>();

        //obtem os dados do array
        arrItensMenu.add(new ItensMenu(getString(R.string.botao_menu_lateral_meus_dados), R.mipmap.menu_lateral_meus_dados));
        //arrItensMenu.add(new ItensMenu(getString(R.string.botao_menu_lateral_configuracoes), R.mipmap.menu_lateral_configuracoes));
        //arrItensMenu.add(new ItensMenu(getString(R.string.botao_menu_lateral_ultimos_acessos), R.mipmap.menu_lateral_ultimos_acessos));
        //arrItensMenu.add(new ItensMenu(getString(R.string.botao_menu_lateral_suporte), R.mipmap.menu_lateral_suporte));
        arrItensMenu.add(new ItensMenu(getString(R.string.botao_menu_lateral_sair), R.mipmap.menu_lateral_sair));

        //inicia o layout maneger
        adapterMenu = new AdapterMenuDashboard(this, this, arrItensMenu);
        rcvMenu.setLayoutManager(new GridLayoutManager(this, 1, LinearLayoutManager.VERTICAL, false));
        rcvMenu.setAdapter(adapterMenu);
        rcvMenu.setNestedScrollingEnabled(false);

        //Atualize a lista
        adapterMenu.notifyDataSetChanged();
    }

    /**
     * Preenche a lista de serviços
     */
    private void preencheListaServicos() throws Exception
    {
        AdapterCategoriasDashboard adapterCategoriasDashboard = null;
        MASUser masuserUsuario = null;
        int iCont = 0;
        JSONObject jsonObjectServico = null;

        //Se não tiver um usuário logado
        masuserUsuario = Apoio.verificaUsuarioLogado(this);

        //instancia o array
        arrCategorias = new ArrayList<CategoriasServicos>();

        //Adiciona os serviços
        arrCategorias.add(new CategoriasServicos(getString(R.string.nome_servico_atualizacao_cadastral), false, false));

        //Se tiver retorno
        if ( jsonArrayRetornoServicos != null && masuserUsuario != null )
        {
            //Loop pelos serviços para adicionar no array de categorias
            for ( iCont = 0; iCont < jsonArrayRetornoServicos.length(); iCont++ )
            {
                //Obtem o serviço
                jsonObjectServico = jsonArrayRetornoServicos.getJSONObject(iCont);

                //Se o serviço for o de atualização
                if ( jsonObjectServico.getString("id").compareToIgnoreCase(getString(R.string.id_atualizacao_cadastral)) == 0 )
                {
                    //Ajusta o serviço para ativo
                    arrCategorias.set(0, new CategoriasServicos(jsonObjectServico.getString("descricao"), true, false));
                }
            }
        }

        //seta o adapter e carrega os itens
        adapterCategoriasDashboard = new AdapterCategoriasDashboard(this, this, arrCategorias);
        rcvCategorias.setLayoutManager(new GridLayoutManager(this, 3, LinearLayoutManager.VERTICAL, false));
        rcvCategorias.setAdapter(adapterCategoriasDashboard);
        rcvCategorias.setNestedScrollingEnabled(false);

        //Atualize a lista
        adapterCategoriasDashboard.notifyDataSetChanged();
    }

    /**
     * Preenche a lista de serviços
     */
    private void preencheListaServicosNew() throws Exception
    {
        AdapterCategoriasDashboard adapterCategoriasDashboard = null;
        MASUser masuserUsuario = null;
        int iCont = 0;
        JSONObject jsonObjectServico = null;

        //Se não tiver um usuário logado
        masuserUsuario = Apoio.verificaUsuarioLogado(this);

        //instancia o array
        arrCategorias = new ArrayList<CategoriasServicos>();
        //SERVICO SEMARH - MEIO AMBIENTE E RECURSOS HÍDRICOS
        arrCategorias.add(new CategoriasServicos(getString(R.string.nome_servico_semarh), true, false));
        // SERVICO IPASEAL - SAUDE
        arrCategorias.add(new CategoriasServicos(getString(R.string.nome_servico_ipaseal), true, false));


        //seta o adapter e carrega os itens
        adapterCategoriasDashboard = new AdapterCategoriasDashboard(this, this, arrCategorias);
        rcvCategorias.setLayoutManager(new GridLayoutManager(this, 3, LinearLayoutManager.VERTICAL, false));
        rcvCategorias.setAdapter(adapterCategoriasDashboard);
        rcvCategorias.setNestedScrollingEnabled(false);

        //Atualize a lista
        adapterCategoriasDashboard.notifyDataSetChanged();
    }


    /**
     * Chama a tela de Meus Dados
     */
    private void chamaTelaMeusDados() throws Exception
    {
        Intent intent = null;
        MASUser masuserUsuario = null;

        //Se não tiver um usuário logado
        masuserUsuario = Apoio.verificaUsuarioLogado(this);
        if ( masuserUsuario == null )
        {
            return;
        }

        //Chama a tela Meus Dados
        intent = new Intent(this, FrmMeusDados.class);
        startActivity(intent);
    }

    /**
     * Chama a tela de atualização cadastral
     */
    private void chamaTelaAtualizacaoCadastral(int iPosicao) throws Exception
    {
        Intent intent = null;
        MASUser masuserUsuario = null;
        CategoriasServicos categoriasServicos = null;

        //Se não tiver um usuário logado
        masuserUsuario = Apoio.verificaUsuarioLogado(this);
        if ( masuserUsuario == null )
        {
            return;
        }

        //Obtem o serviço
        categoriasServicos = arrCategorias.get(iPosicao);

        //Se não estiver habilitado
        if ( !categoriasServicos.bAtivo )
        {
            return;
        }

        //Chama a tela atualização cadastral
        intent = new Intent(this, FrmDetran.class);
        startActivity(intent);
    }

    /**
     * Chama a tela de atualização cadastral
     */
    private void chamaTelaDoServico(int iPosicao) throws Exception
    {
        Intent intent = null;
        MASUser masuserUsuario = null;
        CategoriasServicos categoriasServicos = null;

        //SEMARH
        if (iPosicao == 0) {
            intent = new Intent(this, FrmSemarh.class);
            startActivity(intent);
        }

        //IPASEAL
        if (iPosicao == 1) {
            intent = new Intent(this, FrmIpasealInicio.class);
            startActivity(intent);
        }
    }


    /**
     * Abre a opção selecionada do Menu
     */
    private void selecionaItemMenu(int iOpcao) throws Exception
    {
        try
        {
            //Fecha o menu laterial
            drawerLayout.closeDrawer(Gravity.LEFT);

            //switch de opções do menu
            switch (iOpcao)
            {
                //verifico se é o clique é na opção de MEUS DADOS
                case Apoio.MENU_MEUS_DADOS:
                {
                    //Abre a tela de meus Dados
                    chamaTelaMeusDados();
                    break;
                }
                //verifico se é o clique é na opção de SAIR
                case Apoio.MENU_SAIR:
                {
                    //Cria um dialogAlert com as opções
                    dlgSair = Apoio.criarAlertDialog(this, getString(R.string.sim), getString(R.string.nao), getString(R.string.atencao), getString(R.string.msg_sair_sistema), this);
                    dlgSair.show();
                    Apoio.trocaCoresBotoesDialog(dlgSair, this);
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

    /**
     * Realiza o logout, se o usuário tiver conectado e finaliza
     */
    private void realizaLogoutFinalizaTela(boolean bFinaliza) throws Exception
    {
        MASUser masuserUsuario = null;

        //Obtem o usuário
        masuserUsuario = Apoio.verificaUsuarioLogado(this);

        //Se tiver usuário logado
        if ( masuserUsuario != null)
        {
            //Cria o dialogo e exibe mensagem
            progressDialogDashboard = Apoio.criarProgressDialog(this);
            Apoio.progressDialogMensagem(progressDialogDashboard, getString(R.string.msg_dashboard_logout));

            //Define o tipo da comunicação
            iTipoComunicacao = Apoio.TIPO_COMUNICACAO_LOGOUT;

            //Realiza o logout do usuário
            masuserUsuario.logout(new RetornoMASCallbackVoid(this));
            return;
        }

        //Se for para finalizar
        if ( bFinaliza )
        {
            // Se não tem que fazer logout, apenas sai da tela
            Apoio.finalizaActivity(this, RESULT_OK);
        }
    }

    /**
     * Chama a comunicação para obter os serviços
     */
    private void obtemServicos() throws Exception
    {
        MobileSso mobileSso = null;
        URI uri = null;
        MASRequest masrequestDados = null;
        MASRequest.MASRequestBuilder builder = null;

        //Cria o dialogo e exibe mensagem
        progressDialogDashboard = Apoio.criarProgressDialog(this);
        Apoio.progressDialogMensagem(progressDialogDashboard, getString(R.string.msg_dashboard_obtendo_servicos));

        //Define o tipo da comunicação
        iTipoComunicacao = Apoio.TIPO_COMUNICACAO_SERVICOS;

        //Obtem a instancia da comunicação via SSO
        mobileSso = MobileSsoFactory.getInstance(this);

        //Realiza a chamada da comunicação na URL de obtenção das chaves de permissão
        //uri = mobileSso.getURI(getString(R.string.url_conexao_gateway) + "gpm/servicos");
        uri = mobileSso.getURI(getString(R.string.url_conexao_gateway) + "cidadao/v1/pessoas/eu/servicos");
        builder = new MASRequest.MASRequestBuilder(uri);

        //Constroi a URL de envio e faz a chamada
        masrequestDados = builder.responseBody(new JSONArrayResponseBody()).get().connectionListener(new RetornoMASConnectionListener()).build();
        MAS.invoke(masrequestDados, new RetornoMASCallbackJSONArray(this));
    }

    /**
     * Realiza a busca dos itens no array principal e filtra o conteudo
     */
    private void realizaBuscaItensArray(String sNovoTexto) throws Exception
    {
        AdapterCategoriasDashboard adapterCategoriasDashboard = null;
        MASUser masuserUsuario = null;
        JSONObject jsonObjectServico = null;
        int iCont = 0;

        //Se não tiver dados
        if ( jsonArrayRetornoServicos == null )
        {
            return;
        }

        //Se não tiver um usuário logado
        masuserUsuario = Apoio.verificaUsuarioLogado(this);

        //instancia o array
        arrCategorias = new ArrayList<CategoriasServicos>();

        //Loop pelos serviços para adicionar no array de categorias
        for ( iCont = 0; iCont < jsonArrayRetornoServicos.length(); iCont++ )
        {
            //Obtem o serviço
            jsonObjectServico = jsonArrayRetornoServicos.getJSONObject(iCont);

            //Se o texto do serviço conter o filtro
            if ( jsonObjectServico.getString("id").toUpperCase().contains(sNovoTexto.toUpperCase()) )
            {
                //Adiciona o item
                arrCategorias.add(new CategoriasServicos(jsonObjectServico.getString("descricao"), (masuserUsuario == null ? false : true), false));
            }
        }

        //seta o adapter e carrega os itens
        adapterCategoriasDashboard = new AdapterCategoriasDashboard(this, this, arrCategorias);
        rcvCategorias.setLayoutManager(new GridLayoutManager(this, 3, LinearLayoutManager.VERTICAL, false));
        rcvCategorias.setAdapter(adapterCategoriasDashboard);
        rcvCategorias.setNestedScrollingEnabled(false);

        //Atualize a lista
        adapterCategoriasDashboard.notifyDataSetChanged();
    }

    /**
     * Faz o envio do token do aparelho para o servidor
     */
    private void enviaTokenPushServidor() throws Exception
    {
        MobileSso mobileSso = null;
        URI uri = null;
        MASRequest masrequestDados = null;
        MASRequest.MASRequestBuilder builder = null;
        ArrayList<Pair<String, String>> arrParamEnvio = null;
        MASRequestBody masrequestBody = null;
        String sTokenAtualizado = "";

        //Obtem o token
        sTokenAtualizado = Apoio.retornaPrefsValorString(this, Apoio.PREFS_TOKEN, "");

        //Se tiver um token para envio
        if ( !sTokenAtualizado.equals("") )
        {
            //Define o tipo da comunicação
            iTipoComunicacao = Apoio.TIPO_COMUNICACAO_TOKEN;

            //Obtem a instancia da comunicação via SSO
            mobileSso = MobileSsoFactory.getInstance(this);

            //Realiza a chamada da comunicação na URL de obtenção das chaves de permissão
            uri = mobileSso.getURI(getString(R.string.url_conexao_gateway) + "cidadao/v1/pessoas/eu/dispositivos");
            builder = new MASRequest.MASRequestBuilder(uri);
            builder.header("Content-Type", "application/x-www-form-urlencoded");

            //Adiciona os parametros de envio
            arrParamEnvio = new ArrayList<Pair<String, String>>();
            arrParamEnvio.add(new Pair<String, String>("token", sTokenAtualizado));
            arrParamEnvio.add(new Pair<String, String>("plataforma", "ANDROID"));

            //Monta o body codificado para URL
            masrequestBody = MASRequestBody.urlEncodedFormBody(arrParamEnvio);

            //Constroi a URL de envio e faz a chamada
            masrequestDados = builder.responseBody(new JSONArrayResponseBody()).post(masrequestBody).connectionListener(new RetornoMASConnectionListener()).build();
            MAS.invoke(masrequestDados, new RetornoMASCallbackJSONArray(this));
        }
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
                //Verifica se clicou no botao de menu da toolbar
                case android.R.id.home:
                {
                    //abro o menu
                    drawerLayout.openDrawer(Gravity.LEFT);
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
    public void onReceiveData(Class classe, int iId, boolean bResultado, Object... oObjetos) throws Exception
    {
        super.onReceiveData(classe, iId, bResultado, oObjetos);

        int iPosicao = -1;

        try
        {
            //Se a classe for o menu do dashboard
            if (classe == AdapterMenuDashboard.class)
            {
                //abro a opção selecionada do menu
                selecionaItemMenu((int) oObjetos[0]);
            }
            //Se a classe for a de adapter de categorias
            else if (classe == AdapterCategoriasDashboard.class)
            {
                //Obtem a posição do item clicado
                if (oObjetos != null)
                {
                    iPosicao = (int) oObjetos[0];
                }

                //Chama a tela de atualizacao cadastral
                //chamaTelaAtualizacaoCadastral(iPosicao);

                chamaTelaDoServico(iPosicao);
            }
        }
        catch (Exception err)
        {
            LogTrace.escreve(Apoio.localErro(getClass(), err), Apoio.getPathLogsSDCard(this), Apoio.getArqErr());
            DialogAlerta.show(this, Apoio.getMsgErr(getClass(), err), getString(R.string.atencao), getString(R.string.ok));
        }
    }

    @Override
    public void onClick(DialogInterface dialog, int iEscolha)
    {
        try
        {
            //Se for o dialog de sair
            if ( dialog == dlgSair )
            {
                //Se tocou no botão sim
                if (iEscolha == DialogInterface.BUTTON_POSITIVE)
                {
                    //Faz o logout e finaliza
                    realizaLogoutFinalizaTela(true);
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
            //Cria um dialogAlert com as opções
            dlgSair = Apoio.criarAlertDialog(this, getString(R.string.sim), getString(R.string.nao), getString(R.string.atencao), getString(R.string.msg_sair_sistema), this);
            dlgSair.show();
            Apoio.trocaCoresBotoesDialog(dlgSair, this);
        }
        catch (Exception err)
        {
            LogTrace.escreve(Apoio.localErro(getClass(), err), Apoio.getPathLogsSDCard(this), Apoio.getArqErr());
            DialogAlerta.show(this, Apoio.getMsgErr(getClass(), err), getString(R.string.atencao), getString(R.string.ok));
        }
    }

    @Override
    public void onSuccess(Void voidParam)
    {
        try
        {
            // fecha o dialog
            Apoio.fecharProgressDialog(progressDialogDashboard);

            //Se for o logout
            if ( iTipoComunicacao == Apoio.TIPO_COMUNICACAO_LOGOUT )
            {
                //Grava a opção de manter conectado
                Apoio.gravaPrefsValorBooleano(this, Apoio.PREFS_MANTER_CONECTADO, false);

                // Se não tem que fazer logout, apenas sai da tela
                Apoio.finalizaActivity(this, RESULT_OK);
            }
        }
        catch (Exception err)
        {
            LogTrace.escreve(Apoio.localErro(getClass(), err), Apoio.getPathLogsSDCard(this), Apoio.getArqErr());
            Toast.makeText(this, Apoio.getMsgErr(this.getClass(), err), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onSuccess(MASResponse<JSONArray> jsonArrayResultado)
    {
        try
        {
            // fecha o dialog
            Apoio.fecharProgressDialog(progressDialogDashboard);

            //Se for os serviços
            if ( iTipoComunicacao == Apoio.TIPO_COMUNICACAO_SERVICOS )
            {
                //Armazena o retorno
                jsonArrayRetornoServicos = jsonArrayResultado.getBody().getContent();

                //Seta o sucesso
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
            Apoio.fecharProgressDialog(progressDialogDashboard);

            //Loga o erro retornado pela CA
            LogTrace.escreve(Apoio.localErro(getClass(), throwable), Apoio.getPathLogsSDCard(this), Apoio.getArqErr());

            //Se for o logout
            if ( iTipoComunicacao == Apoio.TIPO_COMUNICACAO_LOGOUT )
            {
                //Monta snackbar com erro
                snackbarErro = Snackbar.make(coordinatorLayoutDashboard, getString(R.string.msg_dashboard_impossivel_logout), Snackbar.LENGTH_LONG);
                snackbarErro.show();
            }
            else if ( iTipoComunicacao == Apoio.TIPO_COMUNICACAO_SERVICOS )
            {
                //Monta snackbar com erro
                snackbarErro = Snackbar.make(coordinatorLayoutDashboard, getString(R.string.msg_dashboard_impossivel_servicos), Snackbar.LENGTH_LONG);
                snackbarErro.show();

                //Seta o erro
                bSucesso = false;

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
    public void run()
    {
        try
        {
            //Se for os serviços
            if ( iTipoComunicacao == Apoio.TIPO_COMUNICACAO_SERVICOS )
            {
                //Se for erro
                if ( !bSucesso )
                {
                    //Zera o json de dados
                    jsonArrayRetornoServicos = null;
                }

                //Preenche a lista de serviços
                preencheListaServicos();
            }
        }
        catch(Exception err)
        {
            LogTrace.escreve(Apoio.localErro(getClass(), err), Apoio.getPathLogsSDCard(this), Apoio.getArqErr());
            DialogAlerta.show(this, Apoio.getMsgErr(getClass(), err), getString(R.string.atencao), getString(R.string.ok));
        }
    }

    @Override
    public void beforeTextChanged(CharSequence sTextoAtual, int iInicio, int iAntes, int iContador)
    {
    }

    @Override
    public void onTextChanged(CharSequence sTextoAtual, int iInicio, int iAntes, int iContador)
    {
        try
        {
            //Se mudou o texto
            realizaBuscaItensArray(sTextoAtual.toString());
        }
        catch(Exception err)
        {
            LogTrace.escreve(Apoio.localErro(getClass(), err), Apoio.getPathLogsSDCard(this), Apoio.getArqErr());
            DialogAlerta.show(this, Apoio.getMsgErr(getClass(), err), getString(R.string.atencao), getString(R.string.ok));
        }
    }

    @Override
    public void afterTextChanged(Editable editable)
    {
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        try
        {
            //realiza a obtenção dos serviços
            realizaObtencaoServicos();
        }
        catch(Exception err)
        {
            LogTrace.escreve(Apoio.localErro(getClass(), err), Apoio.getPathLogsSDCard(this), Apoio.getArqErr());
            DialogAlerta.show(this, Apoio.getMsgErr(getClass(), err), getString(R.string.atencao), getString(R.string.ok));
        }
    }

    @Override
    public void onClick(View view)
    {
        try
        {
            //Se clicou nos favoritos
            if ( view == cmdFavoritos )
            {
            }
        }
        catch(Exception err)
        {
            LogTrace.escreve(Apoio.localErro(getClass(), err), Apoio.getPathLogsSDCard(this), Apoio.getArqErr());
            DialogAlerta.show(this, Apoio.getMsgErr(getClass(), err), getString(R.string.atencao), getString(R.string.ok));
        }

    }
}
package br.com.ca.gpmcaieapp.telas;

import android.app.ProgressDialog;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.ca.mas.foundation.MASResponse;
import com.google.gson.Gson;

import org.json.JSONObject;
import org.json.JSONArray;

import java.util.ArrayList;

import br.com.ca.gpmcaieapp.R;
import br.com.ca.gpmcaieapp.base.ActivityBase;
import br.com.ca.gpmcaieapp.models.CommRespostaToken;
import br.com.ca.gpmcaieapp.models.Pontos;
import br.com.ca.gpmcaieapp.tasks.TaskCartaDeServicosObtemLista;
import br.com.ca.gpmcaieapp.tasks.TaskObtemToken;
import br.com.ca.gpmcaieapp.util.Apoio;
import br.com.ca.gpmcaieapp.util.DialogAlerta;
import br.com.ca.gpmcaieapp.util.IRetornoMASCallbackJSON;
import br.com.ca.gpmcaieapp.util.LogTrace;
import br.com.ca.gpmcaieapp.util.RestCommunication;


/**
 * Classe FrmMeusDados
 */
public class FrmCartaServicos extends ActivityBase implements IRetornoMASCallbackJSON, Runnable
{
    // Controles da classe
    private Toolbar toolbar = null;
    private ProgressDialog progressDialogCartaDeServicos = null;
    private View vCartaDeServicosLista;
    private ListView listView = null;
    private AdapterCartaServicos customList = null;

    //Variáveis da classe
    private int iTipoComunicacao = 0;
    private JSONObject jsonObjectRetornoDados = null;
    private boolean bSucesso = false;

    ArrayList<String> a = new ArrayList<String>();
    ArrayList<String> b = new ArrayList<String>();
    ArrayList<String> c = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle bundleSavedInstanceState)
    {
        // Carrega a tela
        setContentView(R.layout.frm_carta_servicos);

        //chama os métodos automáticos da classe pai
        super.onCreate(bundleSavedInstanceState);
    }

    @Override
    public void iniciaControles() throws Exception
    {
        Drawable drawableArrow = null;

        // Obtem os controles
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        vCartaDeServicosLista = findViewById(R.id.carta_de_servicos_lista);

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

        listView = (ListView) findViewById(R.id.listViewServicoList);

        customList = new AdapterCartaServicos(this, a,b,c);

        listView.setAdapter(customList);
    }

    @Override
    public void carregaDados() throws Exception
    {
        //Obtem os dados de cadastro do usuário
        obtemDadosCartaDeServicos();

    }

    /**
     * Obtem os dados de cadastro do usuário
     */
    private void obtemDadosCartaDeServicos() throws Exception
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
            sBody = "client_id=" + Apoio.CLIENT_KEY + "&client_secret=" + Apoio.CLIENT_SECRET + "&grant_type=client_credentials&scope=ativacao";


            //Chama a task de solicitação do token
            new TaskObtemToken(this, this, sBody).execute();

        }
        catch (Exception err)
        {
            LogTrace.escreve(Apoio.localErro(getClass(), err), Apoio.getPathLogsSDCard(this), Apoio.getArqErr());
            DialogAlerta.show(this, Apoio.getMsgErr(getClass(), err), getString(R.string.atencao), getString(R.string.ok));
        }

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
                    //preencheDadosUsuario();
                }
                else
                {

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

    /**
     * Obtem os dados de atualização cadastral
     */
    private void obtemCartaDeServicos() throws Exception
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
    private void carregaListaCartaServicos() throws Exception
    {
        String sDados = "";
        Gson gsonDados = null;
        Pontos pontos = null;

        a.clear();
        b.clear();
        c.clear();

        //Instancia o gson
        gsonDados = new Gson();

        //Obtem os pontos
        sDados = jsonObjectRetornoDados.getString("orgaos");
        jsonObjectRetornoDados = new JSONObject(sDados);
        sDados = jsonObjectRetornoDados.getString("orgao");

        JSONArray jsonarray = new JSONArray(sDados);

        for (int i = 0; i < jsonarray.length(); i++) {

            JSONObject jsonobject = jsonarray.getJSONObject(i);

            String sigla = jsonobject.getString("sigla");
            String nome = jsonobject.getString("nome");
            String unidade = jsonobject.getString("unidade");

            a.add(sigla);
            b.add(nome);
            c.add(unidade);

            //alsl.add(sl);

            Log.v("Servico da Lista:", i + " -> " + sigla  + " |  " + nome + " | " + unidade );

        }

        customList = new AdapterCartaServicos(this, a,b,c);
        listView.setAdapter(customList);
    }

    /**
     * Chama a comunicação para o cadsatro do usuário
     */
    private void realizaComunicacaoCartaDeServicos(CommRespostaToken commRespostaToken) throws Exception
    {

        String sBody = "";

        //Chama a task de gravação do usuário
        new TaskCartaDeServicosObtemLista(this, this, sBody, commRespostaToken.sAccessToken, RestCommunication.RESTPOST).execute();
    }

    @Override
    public void onSuccess(MASResponse<JSONObject> masresponseObjeto, int iRetorno)
    {
        try
        {
            // fecha o dialog
            Apoio.fecharProgressDialog(progressDialogCartaDeServicos);

            //Se for o tipo de comunicacao meus dados
            if ( iTipoComunicacao == Apoio.TIPO_COMUNICACAO_CARTA_SERVICOS )
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
            Apoio.fecharProgressDialog(progressDialogCartaDeServicos);

            //Loga o erro retornado pela CA
            LogTrace.escreve(Apoio.localErro(getClass(), throwable), Apoio.getPathLogsSDCard(this), Apoio.getArqErr());

            //Seta com sucesso
            bSucesso = false;

            //Monta snackbar com erro
            snackbarErro = Snackbar.make(vCartaDeServicosLista, getString(R.string.msg_meus_dados_impossivel_obter), Snackbar.LENGTH_LONG);
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
                    realizaComunicacaoCartaDeServicos(commRespostaToken);
                }
                else
                {
                    //Obtem a mensagem de erro
                    sMensagem = (String) oObjetos[0];
                    jsonObjectRetornoDados = new JSONObject(sMensagem);
                    //Chama a tela de finalizado com erro
                    //chamaTelaDetran(false, sMensagem);
                    //carregaListaCartaServicos();
                }
            }
            //Se for a classe da task de gravação
            else if (classe == TaskCartaDeServicosObtemLista.class)
            {
                //Obtem a mensagem de erro
                sMensagem = (String) oObjetos[0];


                //Chama a tela de cadastro com sucesso
                // chamaTelaDetran(bResultado, sMensagem);
                //Armazena o retorno
                jsonObjectRetornoDados = new JSONObject(sMensagem);
                carregaListaCartaServicos();


            }
        }
        catch (Exception err)
        {
            LogTrace.escreve(Apoio.localErro(getClass(), err), Apoio.getPathLogsSDCard(this), Apoio.getArqErr());
            DialogAlerta.show(this, Apoio.getMsgErr(getClass(), err), getString(R.string.atencao), getString(R.string.ok));
        }
    }
}
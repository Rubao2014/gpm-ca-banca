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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.ca.mas.core.MobileSso;
import com.ca.mas.core.MobileSsoFactory;
import com.ca.mas.foundation.MAS;
import com.ca.mas.foundation.MASRequest;
import com.ca.mas.foundation.MASResponse;
import org.json.JSONObject;
import java.net.URI;
import br.gov.al.itec.gpmapp.R;
import br.gov.al.itec.gpmapp.base.ActivityBase;
import br.gov.al.itec.gpmapp.util.Apoio;
import br.gov.al.itec.gpmapp.util.DialogAlerta;
import br.gov.al.itec.gpmapp.util.IRetornoMASCallbackJSON;
import br.gov.al.itec.gpmapp.util.LogTrace;
import br.gov.al.itec.gpmapp.util.RetornoMASCallbackJSON;
import br.gov.al.itec.gpmapp.util.RetornoMASConnectionListener;

/**
 * Classe FrmMeusDados
 */
public class FrmMeusDados extends ActivityBase implements View.OnClickListener, IRetornoMASCallbackJSON, Runnable
{
    // Controles da classe
    private Toolbar toolbar = null;
    private TextView lblNome = null;
    private TextView lblEmail = null;
    private Button cmdEditar = null;
    private Button cmdTrocarSenha = null;
    private ProgressDialog progressDialogMeusDados = null;
    private CoordinatorLayout coordinatorLayoutMeusDados = null;

    //Variáveis da classe
    private int iTipoComunicacao = 0;
    private JSONObject jsonObjectRetornoDados = null;
    private boolean bSucesso = false;

    @Override
    protected void onCreate(Bundle bundleSavedInstanceState)
    {
        // Carrega a tela
        setContentView(R.layout.frm_meus_dados);

        //chama os métodos automáticos da classe pai
        super.onCreate(bundleSavedInstanceState);
    }

    @Override
    public void iniciaControles() throws Exception
    {
        Drawable drawableArrow = null;

        // Obtem os controles
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        lblNome = (TextView) findViewById(R.id.lblNome);
        lblEmail = (TextView) findViewById(R.id.lblEmail);
        cmdEditar = (Button) findViewById(R.id.cmdEditar);
        cmdTrocarSenha = (Button) findViewById(R.id.cmdTrocarSenha);
        coordinatorLayoutMeusDados = (CoordinatorLayout) findViewById(R.id.coordinatorLayoutMeusDados);

        //Muda a cor da flecha padrão do Android para branco
        drawableArrow = ContextCompat.getDrawable(this, R.drawable.abc_ic_ab_back_material);
        drawableArrow.setColorFilter(ContextCompat.getColor(this, R.color.branco), PorterDuff.Mode.SRC_ATOP);

        //Seta o navigation item
        toolbar.setNavigationIcon(drawableArrow);

        //Troca a cor do botao de voltar
        toolbar.setNavigationContentDescription(Apoio.TOOLBAR_VOLTAR);

        //Habilita os botões da tela
        cmdEditar.setEnabled(true);
        cmdTrocarSenha.setEnabled(true);

        //Troca a cor dos botões
        cmdEditar.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
        cmdEditar.setTextColor(ContextCompat.getColor(this, R.color.branco));
        cmdTrocarSenha.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
        cmdTrocarSenha.setTextColor(ContextCompat.getColor(this, R.color.branco));

        //Seta o listener dos controles
        cmdEditar.setOnClickListener(this);
        cmdTrocarSenha.setOnClickListener(this);

        //Coloca a toolbar como actionbar e adiciona as opções de menu
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public void carregaDados() throws Exception
    {
        //Obtem os dados de cadastro do usuário
        obtemDadosCadastroUsuario();
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

    /**
     * Chama a tela de editar dados
     */
    private void chamaTelaMeusDadosEditar() throws Exception
    {
        Intent intent = null;

        //Chama a tela
        intent = new Intent(this, FrmMeusDadosEditar.class);
        intent.putExtra(Apoio.TELA_MEUS_DADOS_EDITAR, jsonObjectRetornoDados.toString());
        startActivityForResult(intent, Apoio.RETORNO_TELA_MEUS_DADOS_EDITAR);
    }

    /**
     * Chama a tela de trocar senha
     */
    private void chamaTelaMeusDadosTrocarSenha() throws Exception
    {
        Intent intent = null;

        //Chama a tela
        intent = new Intent(this, FrmMeusDadosTrocarSenha.class);
        startActivity(intent);
    }

    /**
     * Preenche os dados do usuário
     */
    private void preencheDadosUsuario() throws Exception
    {
        //Preenche os dados do usuário
        lblNome.setText(jsonObjectRetornoDados.getString("nomeCompleto"));
        lblEmail.setText(jsonObjectRetornoDados.getString("email"));
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
            //Quando apertar o botão editar
            if (view == cmdEditar)
            {
                //Chama a tela de editar dados
                chamaTelaMeusDadosEditar();
            }
            //Quando apertar o botão trocar senha
            else if (view == cmdTrocarSenha)
            {
                //Chama a tela de trocar senha
                chamaTelaMeusDadosTrocarSenha();
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
            snackbarErro = Snackbar.make(coordinatorLayoutMeusDados, getString(R.string.msg_meus_dados_impossivel_obter), Snackbar.LENGTH_LONG);
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
                }
                else
                {
                    //Desabilita os botões da tela
                    cmdEditar.setEnabled(false);
                    cmdTrocarSenha.setEnabled(false);

                    //Troca a cor dos botões
                    cmdEditar.setBackgroundColor(ContextCompat.getColor(this, R.color.cinza_escuro_ca));
                    cmdEditar.setTextColor(ContextCompat.getColor(this, R.color.preto));
                    cmdTrocarSenha.setBackgroundColor(ContextCompat.getColor(this, R.color.cinza_escuro_ca));
                    cmdTrocarSenha.setTextColor(ContextCompat.getColor(this, R.color.preto));
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
    protected void onActivityResult(int iRequest, int iResultado, Intent intentData)
    {
        super.onActivityResult(iRequest, iResultado, intentData);

        try
        {
            //verifico se é positiva a resposta
            if(iRequest == Apoio.RETORNO_TELA_MEUS_DADOS_EDITAR)
            {
                //verifico se é positiva a resposta
                if (iResultado == RESULT_OK)
                {
                    //Obtem os dados de cadastro do usuário
                    obtemDadosCadastroUsuario();
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
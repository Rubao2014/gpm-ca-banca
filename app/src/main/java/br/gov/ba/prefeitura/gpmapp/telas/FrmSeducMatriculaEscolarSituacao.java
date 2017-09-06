package br.gov.ba.prefeitura.gpmapp.telas;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ca.mas.core.MobileSso;
import com.ca.mas.core.MobileSsoFactory;
import com.ca.mas.foundation.MAS;
import com.ca.mas.foundation.MASRequest;
import com.ca.mas.foundation.MASResponse;

import org.json.JSONObject;

import java.net.URI;

import br.gov.ba.prefeitura.gpmapp.R;
import br.gov.ba.prefeitura.gpmapp.base.ActivityBase;
import br.gov.ba.prefeitura.gpmapp.models.Matricula;
import br.gov.ba.prefeitura.gpmapp.util.Apoio;
import br.gov.ba.prefeitura.gpmapp.util.DialogAlerta;
import br.gov.ba.prefeitura.gpmapp.util.IRetornoMASCallbackJSON;
import br.gov.ba.prefeitura.gpmapp.util.LogTrace;
import br.gov.ba.prefeitura.gpmapp.util.RetornoMASCallbackJSON;
import br.gov.ba.prefeitura.gpmapp.util.RetornoMASConnectionListener;

/**
 * Created by Rubens on 06/12/16.
 */

public class FrmSeducMatriculaEscolarSituacao extends ActivityBase implements View.OnClickListener, IRetornoMASCallbackJSON, Runnable {

    /**
     * Async Task
     */
    //private com.ca.emprestimoja.JsonPostTask mRequestTask = null;

    private static final String DUMMY_JSON = new String("{ \"inscricao\": {\"sele_numero_inscricao\": \"20160114460\",\"sele_seq\": \"355169\",\"sele_ano\": \"2017\",\"sele_lista\": \"1\",\"sele_codigo_escola\": \"0701\",\"sele_serie_seq\": \"43\",\"sele_aluno_nome\": \"Saturnino de Brito\",\"sele_telefone\": \"011999999990\",\"sele_celular\": \"0119999999991\",\"sele_pai\": \"Joao de Brito\",\"sele_pai_nao_declarado\": \"\",\"sele_mae\": \"Luanina de Brito\",\"sele_mae_nao_declarado\": \"\",\"sele_responsavel\": \"\",\"sele_rg\": \"\",\"sele_aluno_nasc\": \"2010-01-01T02:00:00.000Z\",\"sele_aluno_sexo\": \"\",\"sele_endereco\": \"\",\"sele_complemento\": \"\",\"sele_portador\": \"\",\"sele_responsavel_bolsa\": \"sim\",\"sele_gemeo\": \"sim\",\"sele_tipo_justificativa\": \"nao declarou bolsa familia\",\"sele_status\": \"em andamento\"}}");


    private Toolbar toolbar = null;

    private TextView txtInscricaoValue;
    private TextView txtNomeAlunoValue;
    private TextView txtNomeResponsavel01value;
    private TextView txtNomeResponsavel02value;
    private TextView txtDataNascimentoValue;
    private TextView txtEscolaValue;
    private TextView txtSerieValue;
    private TextView txtBolsaFamiliaValue;
    private TextView txtIrmaoGemeoValue;
    private TextView txtJustificativaValue;
    private TextView txtStatusValue;
    private TextView txtAnoInscricaoValue;


    private ListView listView = null;
    //private UserAuth uaCurrentUser = null;
    private Matricula m = null;
    private String cpf ;
    private String nrContrato;        // Store values at the time of the login attempt.


    // UI references.
    private View mProgressView;
    private View mContactListFormView;
    private String sResponse = null;
    private Button cmdInscricoesVoltar = null;


    private ProgressDialog progressDialogMeusDados = null;
    private CoordinatorLayout coordinatorLayoutMeusDados = null;
    //Variáveis da classe
    private int iTipoComunicacao = 0;
    private JSONObject jsonObjectRetornoDados = null;
    private boolean bSucesso = false;

    private void ContractDetailsActivity(){


    }

    @Override
    public void carregaDados() throws Exception
    {

        Intent i = getIntent();
        m = (Matricula)i.getSerializableExtra("matriculaObject");

        //Obtem os dados de cadastro do usuário
        obtemDadosInscricaoMatricula();

        /*
          [RUBENS] Para fins de testes somente
         */
        //refresh((String) DUMMY_JSON);
    }

    /**
     * Obtem os dados de cadastro do usuário
     */
    private void obtemDadosInscricaoMatricula() throws Exception
    {
        MobileSso mobileSso = null;
        URI uri = null;
        MASRequest masrequestDados = null;
        MASRequest.MASRequestBuilder builder = null;

        //Cria o dialogo e exibe mensagem
        progressDialogMeusDados = Apoio.criarProgressDialog(this);
        Apoio.progressDialogMensagem(progressDialogMeusDados, getString(R.string.msg_seduc_obtendo_detalhamento));

        //Define o tipo da comunicação
        iTipoComunicacao = Apoio.TIPO_COMUNICACAO_EDUCACAO;

        //Obtem a instancia da comunicação via SSO
        mobileSso = MobileSsoFactory.getInstance(this);

        //Realiza a chamada da comunicação na URL de obtenção das chaves de permissão
        uri = mobileSso.getURI(getString(R.string.url_conexao_gateway) + "gpm/ba/spm/smec/matesc/v1/inscricao/" + m.getInscricaoValue());
        builder = new MASRequest.MASRequestBuilder(uri);
        builder.header("Content-Type", "application/json");

        //Constroi a URL de envio e faz a chamada
        masrequestDados = builder.connectionListener(new RetornoMASConnectionListener()).build();
        MAS.invoke(masrequestDados, new RetornoMASCallbackJSON(this));
    }

    @Override
    public void iniciaControles() throws Exception {


        Drawable drawableArrow = null;

        // Obtem os controles
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        txtInscricaoValue = (TextView) findViewById(R.id.txtInscricaoValue);
        txtAnoInscricaoValue = (TextView) findViewById(R.id.txtAnoInscricaoValue);
        txtNomeAlunoValue = (TextView) findViewById(R.id.txtNomeAlunoValue);
        txtNomeResponsavel01value = (TextView) findViewById(R.id.txtNomeResponsavel01value);
        txtNomeResponsavel02value = (TextView) findViewById(R.id.txtNomeResponsavel02value);
        txtDataNascimentoValue = (TextView) findViewById(R.id.txtDataNascimentoValue);
        txtEscolaValue = (TextView) findViewById(R.id.txtEscolaValue);
        txtSerieValue = (TextView) findViewById(R.id.txtSerieValue);
        txtBolsaFamiliaValue = (TextView) findViewById(R.id.txtBolsaFamiliaValue );
        txtIrmaoGemeoValue = (TextView) findViewById(R.id.txtIrmaoGemeoValue);
        txtJustificativaValue = (TextView) findViewById(R.id.txtJustificativaValue);
        txtStatusValue = (TextView) findViewById(R.id.txtStatusValue);


        //Muda a cor da flecha padrão do Android para branco
        drawableArrow = ContextCompat.getDrawable(this, R.drawable.abc_ic_ab_back_material);
        drawableArrow.setColorFilter(ContextCompat.getColor(this, R.color.branco), PorterDuff.Mode.SRC_ATOP);

        //Seta o navigation item
        toolbar.setNavigationIcon(drawableArrow);
        //Troca a cor do botao de voltar
        toolbar.setNavigationContentDescription(Apoio.TOOLBAR_VOLTAR);

        //Botao Voltar
        cmdInscricoesVoltar = (Button) findViewById(R.id.cmdInscricoesVoltar);
        //Seta o listener dos controles
        cmdInscricoesVoltar.setOnClickListener(this);

        //Coloca a toolbar como actionbar e adiciona as opções de menu
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    }


    @Override
    protected void onCreate(Bundle bundleSavedInstanceState)
    {
        // Carrega a tela
        setContentView(R.layout.frm_seduc_matricula_escolar_situacao);

        //chama os métodos automáticos da classe pai
        super.onCreate(bundleSavedInstanceState);
    }


    private void refresh(String json){


        m.addDetais(json);

        Log.v("Incricao:", m.getTxtAnoInscricaoValue());
        Log.v("Ano:", m.getTxtAnoInscricaoValue());

        txtInscricaoValue.setText(m.getInscricaoValue());
        txtStatusValue.setText(m.getStatusValue());
        txtAnoInscricaoValue.setText(m.getTxtAnoInscricaoValue());
        txtNomeAlunoValue.setText(m.getTxtNomeAlunoValue());
        txtNomeResponsavel01value.setText(m.getTxtNomeResponsavel01value());
        txtNomeResponsavel02value.setText(m.getTxtNomeResponsavel02value());
        txtDataNascimentoValue.setText(m.getTxtDataNascimentoValue());
        txtEscolaValue.setText(m.getTxtEscolaValue());
        txtSerieValue.setText(m.getTxtSerieValue());
        txtBolsaFamiliaValue.setText(Apoio.retornaAtivoOuNao(m.getTxtBolsaFamiliaValue()));
        txtIrmaoGemeoValue.setText(Apoio.retornaAtivoOuNao(m.getTxtIrmaoGemeoValue()));
        txtJustificativaValue.setText(m.getTxtJustificativaValue());

    }

    @Override
    public void onClick(View view)
    {
        try
        {
            //Quando apertar o botão entrar
            if (view == cmdInscricoesVoltar)
            {
                /*
                * [RUBENS] Volta para a lista de inscrições
                */
                retorna();
            }
        }
        catch (Exception err)
        {
            LogTrace.escreve(Apoio.localErro(getClass(), err), Apoio.getPathLogsSDCard(this), Apoio.getArqErr());
            DialogAlerta.show(this, Apoio.getMsgErr(getClass(), err), getString(R.string.atencao), getString(R.string.ok));
        }
    }

    /**
     * Finaliza a tela e passa uma intent junto
     */
    public  void retorna()
    {
        Intent intent = new Intent(this, FrmSeducMatriculaEscolarInicio.class);
        startActivityForResult(intent, Apoio.RETORNO_TELA_SEDUC);
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
                    Intent intent = new Intent(this, FrmSeducMatriculaEscolarInicio.class);
                    startActivityForResult(intent, Apoio.RETORNO_TELA_SEDUC);
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
    public void onSuccess(MASResponse<JSONObject> masresponseObjeto, int iRetorno)
    {
        try
        {
            // fecha o dialog
            Apoio.fecharProgressDialog(progressDialogMeusDados);

            //Se for o tipo de comunicacao meus dados
            if ( iTipoComunicacao == Apoio.TIPO_COMUNICACAO_EDUCACAO )
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
            snackbarErro = Snackbar.make(coordinatorLayoutMeusDados, getString(R.string.msg_meus_dados_impossivel_obter_matriculas), Snackbar.LENGTH_LONG);
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
            if ( iTipoComunicacao == Apoio.TIPO_COMUNICACAO_EDUCACAO )
            {
                //Se for sucesso
                if ( bSucesso )
                {
                    //Preenche os dados do usuário
                    //Preenche os dados do usuário
                    refresh(jsonObjectRetornoDados.toString());
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

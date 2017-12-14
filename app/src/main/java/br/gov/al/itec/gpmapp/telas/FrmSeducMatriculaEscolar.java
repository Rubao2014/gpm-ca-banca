package br.gov.al.itec.gpmapp.telas;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.ca.mas.core.MobileSso;
import com.ca.mas.core.MobileSsoFactory;
import com.ca.mas.foundation.MAS;
import com.ca.mas.foundation.MASRequest;
import com.ca.mas.foundation.MASRequestBody;
import com.ca.mas.foundation.MASResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import br.gov.al.itec.gpmapp.R;
import br.gov.al.itec.gpmapp.base.ActivityBase;
import br.gov.al.itec.gpmapp.models.Escolas;
import br.gov.al.itec.gpmapp.models.Series;
import br.gov.al.itec.gpmapp.tasks.TaskObtemToken;
import br.gov.al.itec.gpmapp.util.Apoio;
import br.gov.al.itec.gpmapp.util.DialogAlerta;
import br.gov.al.itec.gpmapp.util.IRetornoMASCallbackJSON;
import br.gov.al.itec.gpmapp.util.IRetornoMASCallbackJSONArray;
import br.gov.al.itec.gpmapp.util.JSONArrayResponseBody;
import br.gov.al.itec.gpmapp.util.LogTrace;
import br.gov.al.itec.gpmapp.util.MascaraCampos;
import br.gov.al.itec.gpmapp.util.RetornoMASCallbackJSON;
import br.gov.al.itec.gpmapp.util.RetornoMASCallbackJSONArray;
import br.gov.al.itec.gpmapp.util.RetornoMASConnectionListener;


/**
 *
 * [22:55, 5/9/2017] Lucas Brero Ca: -
 txtNomeEscola
 txtSerie
 txtNomeAluno
 txtDataNascimentoAluno
 txtSexoAluno
 txtRGAluno
 txtNomeResponsavel01
 txtNomeResponsavel02
 txtCPFResponsavel
 checkboxDeficiente
 checkboxTemBolsaFamilia
 checkboxTemImaoGemeo


 */
public class FrmSeducMatriculaEscolar extends ActivityBase implements View.OnClickListener, AdapterView.OnItemSelectedListener, IRetornoMASCallbackJSON, IRetornoMASCallbackJSONArray, Runnable
{
    // Controles da classe
    private Toolbar toolbar = null;
    private ProgressDialog progressDialogMatriculaEscolar = null;
    private ProgressDialog progressDialogInscricao= null;
    private View vCartaDeServicosLista;


    private Spinner txtNomeEscola = null;

    private EditText txtNomeAluno = null;
    private TextInputLayout inputNomeAluno = null;

    private EditText txtDataNascimentoAluno = null;
    private TextInputLayout inputDataNascimentoAluno = null;

    private Spinner txtSexoAluno = null;
    private Spinner txtSerie = null;

    private EditText txtRGAluno = null;
    private TextInputLayout inputRGAluno = null;

    private EditText txtNomeResponsavel01 = null;
    private TextInputLayout inputNomeResponsavel01 = null;

    private EditText txtNomeResponsavel02 = null;
    private TextInputLayout inputNomeResponsavel02 = null;

    private EditText txtCPFResponsavel = null;
    private TextInputLayout inputCPFResponsavel = null;

    private CheckBox checkboxDeficiente = null;
    private CheckBox checkboxTemBolsaFamilia = null;
    private CheckBox checkboxTemImaoGemeo = null;
    private TextWatcher textWatcherDataVencimento = null;
    private TextWatcher textWatcherCPF = null;
    private String[] spinnerCodigosEscolas;
    private String[] spinnerNomesEscolas;
    private String[] spinnerCodigosSeries;
    private String[] spinnerTitulosSeries;
    private Button cmdEnviarInscricao = null;

    //Variáveis da classe
    private int iTipoComunicacao = 0;
    private JSONObject jsonObjectRetornoDados = null;
    private boolean bSucesso = false;
    private HashMap<String,String> mapEscolas = null;
    private HashMap<String,String> mapSeries = null;

    ArrayList<String> a = new ArrayList<String>();
    ArrayList<String> b = new ArrayList<String>();
    ArrayList<String> c = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle bundleSavedInstanceState)
    {
        // Carrega a tela
        setContentView(R.layout.frm_seduc_matricula_escolar_nova_inscricao);

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

        txtSexoAluno = (Spinner) findViewById(R.id.txtSexoAluno);
        txtNomeEscola = (Spinner) findViewById(R.id.txtNomeEscola);
        txtSerie = (Spinner) findViewById(R.id.txtSerie);

        txtDataNascimentoAluno = (EditText) findViewById(R.id.txtDataNascimentoAluno);
        inputDataNascimentoAluno = (TextInputLayout) findViewById(R.id.inputDataNascimentoAluno);

        txtCPFResponsavel = (EditText) findViewById(R.id.txtCPFResponsavel);
        inputCPFResponsavel = (TextInputLayout) findViewById(R.id.inputCPFResponsavel);

        txtNomeAluno = (EditText) findViewById(R.id.txtNomeAluno);
        inputNomeAluno = (TextInputLayout) findViewById(R.id.inputNomeAluno);

        txtRGAluno = (EditText) findViewById(R.id.txtRGAluno);
        inputRGAluno = (TextInputLayout) findViewById(R.id.inputRGAluno);

        txtNomeResponsavel01 = (EditText) findViewById(R.id.txtNomeResponsavel01);
        inputNomeResponsavel01 = (TextInputLayout) findViewById(R.id.inputNomeResponsavel01);

        txtNomeResponsavel02 = (EditText) findViewById(R.id.txtNomeResponsavel02);
        inputNomeResponsavel02 = (TextInputLayout) findViewById(R.id.inputNomeResponsavel02);

        checkboxDeficiente  = (CheckBox) findViewById(R.id.checkboxDeficiente);
        checkboxTemBolsaFamilia  = (CheckBox) findViewById(R.id.checkboxTemBolsaFamilia);
        checkboxTemImaoGemeo  = (CheckBox) findViewById(R.id.checkboxTemImaoGemeo);
        cmdEnviarInscricao = (Button) findViewById(R.id.cmdEnviarInscricao);

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


        /*
         SEXO ALUNO
         */
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.sexo_aluno, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        txtSexoAluno.setAdapter(adapter);

        /*
         ESCOLA
         */
        Escolas lst = new Escolas();
        lst.Create();
        mapEscolas = new HashMap<String,String>(lst.getLista());
        spinnerCodigosEscolas = new String[mapEscolas.size()];
        spinnerNomesEscolas = new String[mapEscolas.size()];
        //HashMap<String,String> spinnerMap = new HashMap<String, String>();

        Iterator it = mapEscolas.entrySet().iterator();
        int i = 0;
        while (it.hasNext()) {
            HashMap.Entry pair = (HashMap.Entry)it.next();
            //System.out.println( "Escola :" + pair.getKey() + " = " + pair.getValue());
            //spinnerMap.put(pair.getKey().toString(),pair.getValue().toString());
            spinnerCodigosEscolas[i] = pair.getKey().toString();
            spinnerNomesEscolas[i] = pair.getValue().toString();
            //it.remove(); // avoids a ConcurrentModificationException
            i++;
        }
        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, spinnerNomesEscolas);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        txtNomeEscola.setAdapter(adapter3);
        //Desliga na inicialização para nao dar pau com o obtemDadosCadastrados
        //txtNomeEscola.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);

        /*
         DATA NASCIMENTO
         */
        textWatcherDataVencimento = MascaraCampos.insert("##/##/####", txtDataNascimentoAluno);
        txtDataNascimentoAluno.addTextChangedListener(textWatcherDataVencimento);


        /*
         CPF
        */
        textWatcherCPF  = MascaraCampos.insert("###.###.###-##", txtCPFResponsavel);
        txtCPFResponsavel.addTextChangedListener(textWatcherCPF);



        /*
         SERIE
         */
        Series s = new Series("{\"series\": []}");
        s.Create();
        spinnerCodigosSeries = s.getCodigosSeries();
        spinnerTitulosSeries = s.getTitulosSeries();
        ArrayAdapter<String> adapter4 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, spinnerTitulosSeries);
        adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        txtSerie.setAdapter(adapter4);


        /*
         Botao Enviar Inscricao
         */
        //Seta o listener dos controles
        cmdEnviarInscricao.setOnClickListener(this);

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        try
        {
            //Quando apertar o botão entrar
            if (adapterView == txtNomeEscola)
            {

                String sNomeEscola = txtNomeEscola.getSelectedItem().toString();
                String sCodigoEscola = spinnerCodigosEscolas[i].toString();
                Log.d("CODIGO", "Selecionou :" + sCodigoEscola + " = " + sNomeEscola);
                try {
                    if (!sCodigoEscola.equals("")) obtemListaSeries(sCodigoEscola);
                }
                catch (Exception e)
                {

                }

            }
        }
        catch (Exception err)
        {
            LogTrace.escreve(Apoio.localErro(getClass(), err), Apoio.getPathLogsSDCard(this), Apoio.getArqErr());
            DialogAlerta.show(this, Apoio.getMsgErr(getClass(), err), getString(R.string.atencao), getString(R.string.ok));
        }


    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


    private void obtemDadosCadastroUsuario() throws Exception
    {
        MobileSso mobileSso = null;
        URI uri = null;
        MASRequest masrequestDados = null;
        MASRequest.MASRequestBuilder builder = null;

        //Cria o dialogo e exibe mensagem
        progressDialogMatriculaEscolar = Apoio.criarProgressDialog(this);
        Apoio.progressDialogMensagem(progressDialogMatriculaEscolar, getString(R.string.msg_meus_dados_obtendo));

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
        //Obtem os dados de cadastro do usuário
        obtemDadosCadastroUsuario();

        //Mostra o teclado
        ((InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE)).toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);

        //Seta o foco no controle
        txtNomeAluno.requestFocus();


    }

    private void obtemListaSeries(String sCodigoEscola) throws Exception
    {
        MobileSso mobileSso = null;
        URI uri = null;
        MASRequest masrequestDados = null;
        MASRequest.MASRequestBuilder builder = null;

        //Cria o dialogo e exibe mensagem
        progressDialogMatriculaEscolar = Apoio.criarProgressDialog(this);
        Apoio.progressDialogMensagem(progressDialogMatriculaEscolar, getString(R.string.msg_seduc_obtendo_escolas));

        //Define o tipo da comunicação
        iTipoComunicacao = Apoio.TIPO_COMUNICACAO_EDUCACAO_SERIES;

        //Obtem a instancia da comunicação via SSO
        mobileSso = MobileSsoFactory.getInstance(this);

        //Realiza a chamada da comunicação na URL de obtenção das chaves de permissão
        /*
            [RUBENS] Obtem aquio JSON com a lista de matriculas realizadas e seus status

         */

        uri = mobileSso.getURI(getString(R.string.url_conexao_gateway) + "gpm/ba/spm/smec/matesc/v1/series?escola_codigo="+sCodigoEscola);
        builder = new MASRequest.MASRequestBuilder(uri);
        builder.header("Content-Type", "application/json");

        //Constroi a URL de envio e faz a chamada
        masrequestDados = builder.connectionListener(new RetornoMASConnectionListener()).build();
        MAS.invoke(masrequestDados, new RetornoMASCallbackJSON(this));
    }

    /*

     */

    @Override
    public void onClick(View view) {
        try
        {
            //Quando apertar o botão enviar
            if (view == cmdEnviarInscricao)
            {
                //Faz o envio dos dados para o servidor
                enviaInscricao();
            }
        }
        catch (Exception err)
        {
            LogTrace.escreve(Apoio.localErro(getClass(), err), Apoio.getPathLogsSDCard(this), Apoio.getArqErr());
            DialogAlerta.show(this, Apoio.getMsgErr(getClass(), err), getString(R.string.atencao), getString(R.string.ok));
        }
    }


    /**
     * Remove todos os erros dos InputTextLayout
     */
    private void removerErroDosCampos() throws Exception
    {
        //Tira o erro do NOME DO ALUNO
        inputNomeAluno.setError("");
        inputNomeAluno.setErrorEnabled(false);

        //Tira o erro do RG DO ALUNO
        inputRGAluno.setError("");
        inputRGAluno.setErrorEnabled(false);

        //Tira o erro DATA NASC DO ALUNO
        inputDataNascimentoAluno.setError("");
        inputDataNascimentoAluno.setErrorEnabled(false);

        //Tira o erro RESP 01
        inputNomeResponsavel01.setError("");
        inputNomeResponsavel01.setErrorEnabled(false);

        //Tira o erro RESP 02
        inputNomeResponsavel02.setError("");
        inputNomeResponsavel02.setErrorEnabled(false);

        //Tira o erro CPF RESP
        inputCPFResponsavel.setError("");
        inputCPFResponsavel.setErrorEnabled(false);
    }


    /**
     * Valida os campos da tela
     */
    private boolean validaCamposTela() throws Exception
    {


        String sDataVencimento = "";
        int iTamanhoCPF = 0;
        String sCPF = "";

        //Obtem o tamanho do campo CPF
        iTamanhoCPF = txtCPFResponsavel.getText().toString().trim().length();


        //erro do NOME DO ALUNO
        if (txtNomeAluno.getText().length() <= 5)
        {
            //Coloca a mensagem de erro embaixo do campo e solicita o foco
            inputNomeAluno.setError(getString(R.string.msg_seduc_nome_invalido));
            inputNomeAluno.setErrorEnabled(true);
            Apoio.requisitarFoco(txtNomeAluno, this);
            return false;
        }
        else
        {
            //Se estier OK, remove o erro
            inputNomeAluno.setError("");
            inputNomeAluno.setErrorEnabled(false);
        }
        //erro do RG DO ALUNO
        //Nao tratei

        //erro DATA NASC DO ALUNO
        //Obtem a data
        sDataVencimento = txtDataNascimentoAluno.getText().toString().trim();
        if ( Apoio.parseData(sDataVencimento, "dd/MM/yyyy", "yyyy-MM-dd").equals("") )
        {
            //Coloca a mensagem de erro embaixo do campo e solicita o foco
            inputDataNascimentoAluno.setError(getString(R.string.msg_seduc_data_nasc_invalida));
            inputDataNascimentoAluno.setErrorEnabled(true);
            Apoio.requisitarFoco(txtDataNascimentoAluno, this);
            return false;
        }
        else
        {
            //Se estier OK, remove o erro
            inputDataNascimentoAluno.setError("");
            inputDataNascimentoAluno.setErrorEnabled(false);
        }

        //erro RESP 01
        if (txtNomeResponsavel01.getText().length() <= 5)
        {
            //Coloca a mensagem de erro embaixo do campo e solicita o foco
            inputNomeResponsavel01.setError(getString(R.string.msg_seduc_nome_invalido));
            inputNomeResponsavel01.setErrorEnabled(true);
            Apoio.requisitarFoco(txtNomeResponsavel01, this);
            return false;
        }
        else
        {
            //Se estier OK, remove o erro
            inputNomeResponsavel01.setError("");
            inputNomeResponsavel01.setErrorEnabled(false);
        }
        //erro RESP 02
        //Nao tratei...

        //o erro CPF RESP
        //Valida o campo de CPF
        if (iTamanhoCPF <= 0)
        {
            //Coloca a mensagem de erro embaixo do campo e solicita o foco
            inputCPFResponsavel.setError(getString(R.string.msg_cadastro_cpf_em_branco));
            inputCPFResponsavel.setErrorEnabled(true);
            Apoio.requisitarFoco(txtCPFResponsavel, this);
            return false;
        }
        else
        {
            //Se estier OK, remove o erro
            inputCPFResponsavel.setError("");
            inputCPFResponsavel.setErrorEnabled(false);
        }

        //Ajusta o CPF para tirar os pontos e o traço
        sCPF = txtCPFResponsavel.getText().toString().trim().replace(".", "");
        sCPF = sCPF.replace("-", "");

        //Valida o campo de CPF
        if ( !Apoio.verificaCPFValido(sCPF) )
        {
            //Coloca a mensagem de erro embaixo do campo e solicita o foco
            inputCPFResponsavel.setError(getString(R.string.msg_cadastro_cpf_invalido));
            inputCPFResponsavel.setErrorEnabled(true);
            Apoio.requisitarFoco(txtCPFResponsavel, this);
            return false;
        }
        else
        {
            //Se estier OK, remove o erro
            inputCPFResponsavel.setError("");
            inputCPFResponsavel.setErrorEnabled(false);
        }

        return true;

    }

    /**
     * Envia os dados do cadastro para o servidor
     */
    private void enviaInscricao() throws Exception
    {
        String sCPF = "";
        String sCelular = "";
        MobileSso mobileSso = null;
        URI uri = null;
        MASRequest masrequestDados = null;
        MASRequest.MASRequestBuilder builder = null;
        JSONObject jsonObjectDados = null;
        JSONObject jsonObjectHeader = null;

        //Remove os erros atuais dos campos
        removerErroDosCampos();

        //Valida os campos da tela
        if (!validaCamposTela())
        {
            return;
        }

        //Cria o dialogo e exibe mensagem
        progressDialogInscricao = Apoio.criarProgressDialog(this);
        Apoio.progressDialogMensagem(progressDialogInscricao, getString(R.string.msg_seduc_enviando_dados));

        //Define o tipo da comunicação
        iTipoComunicacao = Apoio.TIPO_COMUNICACAO_EDUCACAO;

        //Obtem a instancia da comunicação via SSO
        mobileSso = MobileSsoFactory.getInstance(this);

        //Realiza a chamada da comunicação na URL de obtenção das chaves de permissão
        uri = mobileSso.getURI(getString(R.string.url_conexao_gateway) + "gpm/ba/spm/smec/matesc/v1/inscricao");
        builder = new MASRequest.MASRequestBuilder(uri);
        builder.header("Content-Type", "application/json");

        /*
        {
          "inscricao" : {
              "sele_ano": "2017",
              "sele_lista": "1",
              "sele_codigo_escola": "0508",
              "sele_serie_seq": "41",
              "sele_aluno_nome": "Felipe Souza",
              "sele_aluno_nasc": "2015-01-01",
              "sele_aluno_sexo": "M",
              "sele_aluno_rg": "585025113",
              "sele_telefone": "7122887766",
              "sele_celular": "71999887766",
              "sele_pai": "Rubens Souza",
              "sele_mae": "",
              "sele_cpf_responsavel": "286.883.638-03",
              "sele_endereco": "Av. Sete de Sembro, 123",
              "sele_complemento": "Apto 85 - Bloco A",
              "sele_portador": "0",
              "sele_responsavel_bolsa": "0",
              "sele_gemeo": "0"
           }
        }
         */

        //Monta o body e passa no put
        jsonObjectDados = new JSONObject();
        jsonObjectDados.put("sele_ano", "2017");
        jsonObjectDados.put("sele_lista", "1");
        jsonObjectDados.put("sele_codigo_escola", spinnerCodigosEscolas[(int)txtNomeEscola.getSelectedItemId()]);
        jsonObjectDados.put("sele_serie_seq", spinnerCodigosSeries[(int)txtSerie.getSelectedItemId()]);
        jsonObjectDados.put("sele_aluno_nome", txtNomeAluno.getText().toString());
        jsonObjectDados.put("sele_aluno_nasc", Apoio.parseData(txtDataNascimentoAluno.getText().toString(),"dd/MM/yyyy","yyyy-MM-dd"));
        jsonObjectDados.put("sele_aluno_sexo", Apoio.retornaSexoShort(txtSexoAluno.getSelectedItem().toString()));
        jsonObjectDados.put("sele_aluno_rg", (txtRGAluno.getText().toString().isEmpty()?("0000000").toString():txtRGAluno.getText().toString()));
        jsonObjectDados.put("sele_telefone", "017000000000");
        jsonObjectDados.put("sele_celular", "017000000000");
        jsonObjectDados.put("sele_pai", txtNomeResponsavel01.getText().toString());
        jsonObjectDados.put("sele_mae", txtNomeResponsavel02.getText().toString());
        jsonObjectDados.put("sele_cpf_responsavel", txtCPFResponsavel.getText().toString());
        jsonObjectDados.put("sele_endereco", "Nao declarado");
        jsonObjectDados.put("sele_complemento", "Nao declarado");
        jsonObjectDados.put("sele_portador", (checkboxDeficiente.isChecked()?"1":"0"));
        jsonObjectDados.put("sele_responsavel_bolsa", (checkboxTemBolsaFamilia.isChecked()?"1":"0"));
        jsonObjectDados.put("sele_gemeo", (checkboxTemImaoGemeo.isChecked()?"1":"0"));
        jsonObjectHeader = new JSONObject();
        jsonObjectHeader.put("inscricao", jsonObjectDados);

        //Constroi a URL de envio e faz a chamada
        masrequestDados = builder.responseBody(new JSONArrayResponseBody()).post(MASRequestBody.jsonBody(jsonObjectHeader)).connectionListener(new RetornoMASConnectionListener()).build();
        MAS.invoke(masrequestDados, new RetornoMASCallbackJSONArray(this));
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

    private void refreshSeries(String json){



        Series s = new Series(json);
        s.Create();
        spinnerCodigosSeries = s.getCodigosSeries();
        spinnerTitulosSeries = s.getTitulosSeries();
        ArrayAdapter<String> adapter5 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, spinnerTitulosSeries);
        adapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        txtSerie.setAdapter(adapter5);

    }

    /**
     * Preenche os dados do usuário
     */
    private void preencheCPFUsuario() throws Exception
    {
        //Preenche os dados do usuário
        txtCPFResponsavel.setText(jsonObjectRetornoDados.getString("cpf"));

        //Agora sim.. habilita
        txtNomeEscola.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);

    }

    /**
     * Chama a tela de finalizado
     */
    private void chamaTelaFinalizado(boolean bSucesso, String sMensagem) throws Exception
    {
        Intent intent = null;

        //Chama a tela principal
        intent = new Intent(this, FrmFinalizado.class);
        intent.putExtra(Apoio.TELA_SUCESSO_TIPO_MENSAGEM, bSucesso);
        intent.putExtra(Apoio.TELA_SUCESSO_MENSAGEM, sMensagem);
        intent.putExtra(Apoio.TELA_SUCESSO_TITULO, getString(R.string.toolbar_seduc));
        startActivityForResult(intent, Apoio.RETORNO_TELA_SEDUC);
    }

    @Override
    public void onSuccess(MASResponse<JSONArray> jsonArrayResultado)
    {
        try
        {
            // fecha o dialog
            Apoio.fecharProgressDialog(progressDialogInscricao);

            //Se for o tipo de comunicacao meus dados
            if ( iTipoComunicacao == Apoio.TIPO_COMUNICACAO_EDUCACAO )
            {
                //Chama a tela de finalizado com sucesso
                chamaTelaFinalizado(true, getString(R.string.msg_seduc_inscricao_enviada_sucesso));
            }
        }
        catch (Exception err)
        {
            LogTrace.escreve(Apoio.localErro(getClass(), err), Apoio.getPathLogsSDCard(this), Apoio.getArqErr());
            Toast.makeText(this, Apoio.getMsgErr(this.getClass(), err), Toast.LENGTH_LONG).show();
        }
    }
    @Override
    public void onSuccess(MASResponse<JSONObject> masresponseObjeto, int iRetorno)
    {
        try
        {
            // fecha o dialog
            Apoio.fecharProgressDialog(progressDialogMatriculaEscolar);

            //Se for o tipo de comunicacao meus dados
            if (( iTipoComunicacao == Apoio.TIPO_COMUNICACAO_EDUCACAO_SERIES ) || ( iTipoComunicacao == Apoio.TIPO_COMUNICACAO_MEUS_DADOS ))
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
            Apoio.fecharProgressDialog(progressDialogInscricao);

            //Loga o erro retornado pela CA
            LogTrace.escreve(Apoio.localErro(getClass(), throwable), Apoio.getPathLogsSDCard(this), Apoio.getArqErr());

            //Se for o tipo de comunicacao meus dados
            {
                //Seta com sucesso
                bSucesso = false;

                //Monta snackbar com erro
                snackbarErro = Snackbar.make(vCartaDeServicosLista, getString(R.string.msg_meus_dados_impossivel_obter), Snackbar.LENGTH_LONG);
                snackbarErro.show();

                //Chama o preenchimento para rodar em thread, pois no retorno do sucesso, não conseguimos acessar os objetos de tela
                this.runOnUiThread(this);

            }
            if (iTipoComunicacao == Apoio.TIPO_COMUNICACAO_EDUCACAO){
                //Chama a tela de finalizado com erro
                chamaTelaFinalizado(false, getString(R.string.msg_seduc_inscricao_enviada_erro));
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
            if ( iTipoComunicacao == Apoio.TIPO_COMUNICACAO_EDUCACAO_SERIES )
            {
                //Se for sucesso
                if ( bSucesso )
                {
                    //Preenche os dados do usuário
                    refreshSeries(jsonObjectRetornoDados.toString());
                }
                else
                {
                    //Limpa a tela
                }
            }
            //Se for os serviços
            if ( iTipoComunicacao == Apoio.TIPO_COMUNICACAO_MEUS_DADOS )
            {
                //Se for sucesso
                if ( bSucesso )
                {
                    //Preenche os dados do usuário
                    preencheCPFUsuario();
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
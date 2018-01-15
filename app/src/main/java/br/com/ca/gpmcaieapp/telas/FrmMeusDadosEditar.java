package br.com.ca.gpmcaieapp.telas;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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
import br.com.ca.gpmcaieapp.R;
import br.com.ca.gpmcaieapp.base.ActivityBase;
import br.com.ca.gpmcaieapp.util.Apoio;
import br.com.ca.gpmcaieapp.util.DialogAlerta;
import br.com.ca.gpmcaieapp.util.IRetornoMASCallbackJSONArray;
import br.com.ca.gpmcaieapp.util.JSONArrayResponseBody;
import br.com.ca.gpmcaieapp.util.LogTrace;
import br.com.ca.gpmcaieapp.util.MascaraCampos;
import br.com.ca.gpmcaieapp.util.RetornoMASCallbackJSONArray;
import br.com.ca.gpmcaieapp.util.RetornoMASConnectionListener;

/**
 * Classe FrmMeusDadosEditar
 */
public class FrmMeusDadosEditar extends ActivityBase implements View.OnClickListener, IRetornoMASCallbackJSONArray
{
    // Controles da classe
    private Toolbar toolbar = null;
    private TextInputLayout inputNome = null;
    private EditText txtNome = null;
    private TextInputLayout inputCPF = null;
    private EditText txtCPF = null;
    private TextInputLayout inputDataNascimento = null;
    private EditText txtDataNascimento = null;
    private TextInputLayout inputCelular = null;
    private EditText txtCelular = null;
    private TextInputLayout inputEmail = null;
    private EditText txtEmail = null;
    //private Spinner cboSexo = null;
    //private TextView lblErroSexo = null;
    private Button cmdEnviar = null;
    private ProgressDialog progressDialogMeusDadosEditar = null;

    //Variáveis da classe
    private ArrayList<String> arrSexo = null;
    private int iTipoComunicacao = 0;
    private JSONObject jsonObjectRetornoDados = null;

    @Override
    protected void onCreate(Bundle bundleSavedInstanceState)
    {
        // Carrega a tela
        setContentView(R.layout.frm_meus_dados_editar);

        //chama os métodos automáticos da classe pai
        super.onCreate(bundleSavedInstanceState);
    }

    @Override
    public void iniciaControles() throws Exception
    {
        Drawable drawableArrow = null;
        TextWatcher textWatcherCPF = null;
        TextWatcher textWatcherDataNascimento = null;
        TextWatcher textWatcherCelular = null;

        // Obtem os controles
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        inputNome = (TextInputLayout) findViewById(R.id.inputNome);
        txtNome = (EditText) findViewById(R.id.txtNome);
        inputCPF = (TextInputLayout) findViewById(R.id.inputCPF);
        txtCPF = (EditText) findViewById(R.id.txtCPF);
        inputDataNascimento = (TextInputLayout) findViewById(R.id.inputDataNascimento);
        txtDataNascimento = (EditText) findViewById(R.id.txtDataNascimento);
        inputCelular = (TextInputLayout) findViewById(R.id.inputCelular);
        txtCelular = (EditText) findViewById(R.id.txtCelular);
        inputEmail = (TextInputLayout) findViewById(R.id.inputEmail);
        txtEmail = (EditText) findViewById(R.id.txtEmail);
        //cboSexo = (Spinner) findViewById(R.id.cboSexo);
        //lblErroSexo = (TextView) findViewById(R.id.lblErroSexo);
        cmdEnviar = (Button) findViewById(R.id.cmdEnviar);

        //coloca a mascara nos campos
        textWatcherCPF = MascaraCampos.insert("###.###.###-##", txtCPF);
        txtCPF.addTextChangedListener(textWatcherCPF);
        textWatcherDataNascimento = MascaraCampos.insert("##/##/####", txtDataNascimento);
        txtDataNascimento.addTextChangedListener(textWatcherDataNascimento);
        textWatcherCelular = MascaraCampos.insert("(##)#####-####", txtCelular);
        txtCelular.addTextChangedListener(textWatcherCelular);

        //Esconde o erro do sexo
        //lblErroSexo.setVisibility(View.GONE);

        //Muda a cor da flecha padrão do Android para branco
        drawableArrow = ContextCompat.getDrawable(this, R.drawable.abc_ic_ab_back_material);
        drawableArrow.setColorFilter(ContextCompat.getColor(this, R.color.branco), PorterDuff.Mode.SRC_ATOP);

        //Seta o navigation item
        toolbar.setNavigationIcon(drawableArrow);

        //Troca a cor do botao de voltar
        toolbar.setNavigationContentDescription(Apoio.TOOLBAR_VOLTAR);

        //Seta o listener dos controles
        cmdEnviar.setOnClickListener(this);

        //Coloca a toolbar como actionbar e adiciona as opções de menu
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public void carregaDados() throws Exception
    {

        //Preenche o combo de sexo
        //preencheComboSexo();

        //Seta os campos na tela
        txtNome.setText(jsonObjectRetornoDados.getString("nomeCompleto"));
        txtCPF.setText(jsonObjectRetornoDados.getString("cpf"));
        txtDataNascimento.setText(jsonObjectRetornoDados.getString("dataNascimento"));
        txtCelular.setText(jsonObjectRetornoDados.getString("telefoneCelular"));
        txtEmail.setText(jsonObjectRetornoDados.getString("email"));

        //Mostra o teclado
        ((InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE)).toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

        //Seta o foco no controle
        txtNome.requestFocus();
    }

    @Override
    public void obtemParametros() throws Exception
    {
        Intent intent;

        super.obtemParametros();

        //Obtem os parametros
        intent = this.getIntent();
        jsonObjectRetornoDados = new JSONObject(intent.getStringExtra(Apoio.TELA_MEUS_DADOS_EDITAR));
    }

    /**
     * Preenche o combo de sexo
     */
    private void preencheComboSexo() throws  Exception
    {
        ArrayAdapter<String> arrAdapterSexo = null;

        //Carrega os dados
        arrSexo = new ArrayList<String>();
        arrSexo.add("");
        arrSexo.add(getString(R.string.cadastro_sexo_masculino));
        arrSexo.add(getString(R.string.cadastro_sexo_feminino));

        //Cria o adapter
        arrAdapterSexo = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrSexo);

        //Seta o adapter no dropdown
        arrAdapterSexo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //Atacha o adapter ao controle
        //cboSexo.setAdapter(arrAdapterSexo);
    }

    /**
     * Finaliza a tela atual
     */
    private void finalizaTela() throws Exception
    {
        //Fecha o teclado
        Apoio.fechaTeclado(this);

        // Seta o resultado e finaliza
        Apoio.finalizaActivity(this, RESULT_CANCELED);
    }

    /**
     * Envia os dados do cadastro para o servidor
     */
    private void enviaDadosCadastroEditar() throws Exception
    {
        String sCPF = "";
        String sCelular = "";
        MobileSso mobileSso = null;
        URI uri = null;
        MASRequest masrequestDados = null;
        MASRequest.MASRequestBuilder builder = null;
        JSONObject jsonObjectDados = null;

        //Remove os erros atuais dos campos
        removerErroDosCampos();

        //Valida os campos da tela
        if (!validaCamposTela())
        {
            return;
        }

        //Cria o dialogo e exibe mensagem
        progressDialogMeusDadosEditar = Apoio.criarProgressDialog(this);
        Apoio.progressDialogMensagem(progressDialogMeusDadosEditar, getString(R.string.msg_meus_dados_editar_enviando));

        //Define o tipo da comunicação
        iTipoComunicacao = Apoio.TIPO_COMUNICACAO_MEUS_DADOS_EDITAR;

        //Obtem a instancia da comunicação via SSO
        mobileSso = MobileSsoFactory.getInstance(this);

        //Realiza a chamada da comunicação na URL de obtenção das chaves de permissão
        uri = mobileSso.getURI(getString(R.string.url_conexao_gateway) + "cidadao/v1/pessoas/eu");
        builder = new MASRequest.MASRequestBuilder(uri);
        builder.header("Content-Type", "application/json");

        //Ajusta o CPF para tirar os pontos e o traço
        sCPF = txtCPF.getText().toString().trim().replace(".", "");
        sCPF = sCPF.replace("-", "");

        //Ajusta o celular para tirar os parenteses e o traço
        sCelular = txtCelular.getText().toString().trim().replace("(", "");
        sCelular = sCelular.replace(")", "");
        sCelular = sCelular.replace("-", "");

        //Monta o body e passa no put
        jsonObjectDados = new JSONObject();
        jsonObjectDados.put("nomeCompleto", txtNome.getText().toString().trim().toUpperCase());
        jsonObjectDados.put("email", txtEmail.getText().toString().trim());
        jsonObjectDados.put("cpf", sCPF);
        jsonObjectDados.put("dataNascimento", txtDataNascimento.getText().toString().trim());
        jsonObjectDados.put("telefoneCelular", sCelular);

        //Constroi a URL de envio e faz a chamada
        masrequestDados = builder.responseBody(new JSONArrayResponseBody()).put(MASRequestBody.jsonBody(jsonObjectDados)).connectionListener(new RetornoMASConnectionListener()).build();
        MAS.invoke(masrequestDados, new RetornoMASCallbackJSONArray(this));
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
        intent.putExtra(Apoio.TELA_SUCESSO_TITULO, getString(R.string.toolbar_meus_dados));
        startActivityForResult(intent, Apoio.RETORNO_TELA_MEUS_DADOS_EDITAR);
    }

    /**
     * Remove todos os erros dos InputTextLayout
     */
    private void removerErroDosCampos() throws Exception
    {
        //Tira o erro
        inputNome.setError("");
        inputNome.setErrorEnabled(false);
        inputCPF.setError("");
        inputCPF.setErrorEnabled(false);
        inputDataNascimento.setError("");
        inputDataNascimento.setErrorEnabled(false);
        inputCelular.setError("");
        inputCelular.setErrorEnabled(false);
        inputEmail.setError("");
        inputEmail.setErrorEnabled(false);
        //lblErroSexo.setVisibility(View.GONE);
    }

    /**
     * Valida os campos da tela
     */
    private boolean validaCamposTela() throws Exception
    {
        int iTamanhoCPF = 0;
        String sCPF = "";
        String sDataNasc = "";
        String sCelular = "";
        String sEmail = "";

        //Valida o campo de nome
        if (txtNome.getText().toString().trim().length() <= 0)
        {
            //Coloca a mensagem de erro embaixo do campo e solicita o foco
            inputNome.setError(getString(R.string.msg_cadastro_nome_em_branco));
            inputNome.setErrorEnabled(true);
            Apoio.requisitarFoco(txtNome, this);
            return false;
        }
        else
        {
            //Se estier OK, remove o erro
            inputNome.setError("");
            inputNome.setErrorEnabled(false);
        }

        //Obtem o tamanho do campo CPF
        iTamanhoCPF = txtCPF.getText().toString().trim().length();

        //Valida o campo de CPF
        if (iTamanhoCPF <= 0)
        {
            //Coloca a mensagem de erro embaixo do campo e solicita o foco
            inputCPF.setError(getString(R.string.msg_cadastro_cpf_em_branco));
            inputCPF.setErrorEnabled(true);
            Apoio.requisitarFoco(txtCPF, this);
            return false;
        }
        else
        {
            //Se estier OK, remove o erro
            inputCPF.setError("");
            inputCPF.setErrorEnabled(false);
        }

        //Ajusta o CPF para tirar os pontos e o traço
        sCPF = txtCPF.getText().toString().trim().replace(".", "");
        sCPF = sCPF.replace("-", "");

        //Valida o campo de CPF
        if ( !Apoio.verificaCPFValido(sCPF) )
        {
            //Coloca a mensagem de erro embaixo do campo e solicita o foco
            inputCPF.setError(getString(R.string.msg_cadastro_cpf_invalido));
            inputCPF.setErrorEnabled(true);
            Apoio.requisitarFoco(txtCPF, this);
            return false;
        }
        else
        {
            //Se estier OK, remove o erro
            inputCPF.setError("");
            inputCPF.setErrorEnabled(false);
        }

        //Obtem a data
        sDataNasc = txtDataNascimento.getText().toString().trim();

        //Valida o campo de data de nascimento
        if (sDataNasc.length() <= 0)
        {
            //Coloca a mensagem de erro embaixo do campo e solicita o foco
            inputDataNascimento.setError(getString(R.string.msg_cadastro_data_nascimento_em_branco));
            inputDataNascimento.setErrorEnabled(true);
            Apoio.requisitarFoco(txtDataNascimento, this);
            return false;
        }
        else
        {
            //Se estier OK, remove o erro
            inputDataNascimento.setError("");
            inputDataNascimento.setErrorEnabled(false);
        }

        //verifica se a data é valida
        if ( Apoio.parseData(sDataNasc, "dd/MM/yyyy", "yyyy-MM-dd").equals("") )
        {
            //Coloca a mensagem de erro embaixo do campo e solicita o foco
            inputDataNascimento.setError(getString(R.string.msg_cadastro_data_nascimento_invalida));
            inputDataNascimento.setErrorEnabled(true);
            Apoio.requisitarFoco(txtDataNascimento, this);
            return false;
        }
        else
        {
            //Se estier OK, remove o erro
            inputDataNascimento.setError("");
            inputDataNascimento.setErrorEnabled(false);
        }

        //Obtem o tamanho do campo celular
        sCelular = txtCelular.getText().toString().trim();

        //Valida o campo de celular
        if (sCelular.length() <= 0)
        {
            //Coloca a mensagem de erro embaixo do campo e solicita o foco
            inputCelular.setError(getString(R.string.msg_cadastro_celular_em_branco));
            inputCelular.setErrorEnabled(true);
            Apoio.requisitarFoco(txtCelular, this);
            return false;
        }
        else
        {
            //Se estier OK, remove o erro
            inputCelular.setError("");
            inputCelular.setErrorEnabled(false);
        }

        //Valida o campo de celular
        if (!sCelular.matches(Apoio.CELULAR_PATTERN))
        {
            //Coloca a mensagem de erro embaixo do campo e solicita o foco
            inputCelular.setError(getString(R.string.msg_cadastro_celular_invalido));
            inputCelular.setErrorEnabled(true);
            Apoio.requisitarFoco(txtCelular, this);
            return false;
        }
        else
        {
            //Se estier OK, remove o erro
            inputCelular.setError("");
            inputCelular.setErrorEnabled(false);
        }

        //Valida o campo de email
        sEmail = txtEmail.getText().toString().trim();
        if (sEmail.length() <= 0)
        {
            //Coloca a mensagem de erro embaixo do campo e solicita o foco
            inputEmail.setError(getString(R.string.msg_cadastro_email_em_branco));
            inputEmail.setErrorEnabled(true);
            Apoio.requisitarFoco(txtEmail, this);
            return false;
        }
        else
        {
            //Se estier OK, remove o erro
            inputEmail.setError("");
            inputEmail.setErrorEnabled(false);
        }

        //Se o email não for valido
        if (!sEmail.matches(Apoio.EMAIL_PATTERN))
        {
            //Coloca a mensagem de erro embaixo do campo e solicita o foco
            inputEmail.setError(getString(R.string.msg_cadastro_email_invalido));
            inputEmail.setErrorEnabled(true);
            Apoio.requisitarFoco(txtEmail, this);
            return false;
        }
        else
        {
            //Se estier OK, remove o erro
            inputEmail.setError("");
            inputEmail.setErrorEnabled(false);
        }

        //Se não selecionou o sexo
        /*if (cboSexo.getSelectedItemPosition() <= 0)
        {
            //Coloca a mensagem de erro embaixo do campo e solicita o foco
            lblErroSexo.setText(getString(R.string.msg_cadastro_sexo_nao_selecionado));
            lblErroSexo.setVisibility(View.VISIBLE);
            Apoio.requisitarFoco(cboSexo, this);
            return false;
        }
        else
        {
            //Se estier OK, remove o erro
            lblErroSexo.setText("");
            lblErroSexo.setVisibility(View.GONE);
        }*/

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
                    finalizaTela();
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
            //Quando apertar o botão enviar
            if (view == cmdEnviar)
            {
                //Faz o envio dos dados para o servidor
                enviaDadosCadastroEditar();
            }
        }
        catch (Exception err)
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
            // Seta o resultado e finaliza
            finalizaTela();
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
            if(iRequest == Apoio.RETORNO_TELA_MEUS_DADOS_EDITAR)
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
    public void onSuccess(MASResponse<JSONArray> jsonArrayResultado)
    {
        try
        {
            // fecha o dialog
            Apoio.fecharProgressDialog(progressDialogMeusDadosEditar);

            //Se for o tipo de comunicacao meus dados
            if ( iTipoComunicacao == Apoio.TIPO_COMUNICACAO_MEUS_DADOS_EDITAR )
            {
                //Chama a tela de finalizado com sucesso
                chamaTelaFinalizado(true, getString(R.string.msg_meus_dados_editar_sucesso));
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
        try
        {
            // fecha o dialog
            Apoio.fecharProgressDialog(progressDialogMeusDadosEditar);

            //Loga o erro retornado pela CA
            LogTrace.escreve(Apoio.localErro(getClass(), throwable), Apoio.getPathLogsSDCard(this), Apoio.getArqErr());

            //Se for o tipo de comunicacao meus dados
            if ( iTipoComunicacao == Apoio.TIPO_COMUNICACAO_MEUS_DADOS_EDITAR )
            {
                //Chama a tela de finalizado com erro
                chamaTelaFinalizado(false, getString(R.string.msg_meus_dados_editar_erro));
            }
        }
        catch (Exception err)
        {
            LogTrace.escreve(Apoio.localErro(getClass(), err), Apoio.getPathLogsSDCard(this), Apoio.getArqErr());
            Toast.makeText(this, Apoio.getMsgErr(this.getClass(), err), Toast.LENGTH_LONG).show();
        }
    }
}
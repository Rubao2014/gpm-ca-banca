package br.gov.ba.prefeitura.gpmapp.telas;

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
import android.widget.Button;
import android.widget.EditText;
import br.gov.ba.prefeitura.gpmapp.R;
import br.gov.ba.prefeitura.gpmapp.base.ActivityBase;
import br.gov.ba.prefeitura.gpmapp.util.Apoio;
import br.gov.ba.prefeitura.gpmapp.util.DialogAlerta;
import br.gov.ba.prefeitura.gpmapp.util.LogTrace;
import br.gov.ba.prefeitura.gpmapp.util.MascaraCampos;

/**
 * Classe FrmCadastroNascimento
 */
public class FrmCadastroNascimento extends ActivityBase implements View.OnClickListener
{
    // Controles da classe
    private Toolbar toolbar = null;
    private TextInputLayout inputDataNascimento = null;
    private EditText txtDataNascimento = null;
    private Button cmdContinuar = null;

    @Override
    protected void onCreate(Bundle bundleSavedInstanceState)
    {
        // Carrega a tela
        setContentView(R.layout.frm_cadastro_nascimento);

        //chama os métodos automáticos da classe pai
        super.onCreate(bundleSavedInstanceState);
    }

    @Override
    public void iniciaControles() throws Exception
    {
        Drawable drawableArrow = null;
        TextWatcher textWatcherDataNascimento = null;

        // Obtem os controles
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        inputDataNascimento = (TextInputLayout) findViewById(R.id.inputDataNascimento);
        txtDataNascimento = (EditText) findViewById(R.id.txtDataNascimento);
        cmdContinuar = (Button) findViewById(R.id.cmdEnviarInscricao);

        //coloca a mascara nos campos
        textWatcherDataNascimento = MascaraCampos.insert("##/##/####", txtDataNascimento);
        txtDataNascimento.addTextChangedListener(textWatcherDataNascimento);

        //Muda a cor da flecha padrão do Android para branco
        drawableArrow = ContextCompat.getDrawable(this, R.drawable.abc_ic_ab_back_material);
        drawableArrow.setColorFilter(ContextCompat.getColor(this, R.color.branco), PorterDuff.Mode.SRC_ATOP);

        //Seta o navigation item
        toolbar.setNavigationIcon(drawableArrow);

        //Troca a cor do botao de voltar
        toolbar.setNavigationContentDescription(Apoio.TOOLBAR_VOLTAR);

        //Seta o listener dos controles
        cmdContinuar.setOnClickListener(this);

        //Coloca a toolbar como actionbar e adiciona as opções de menu
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public void carregaDados() throws Exception
    {
        //Seta tiver dados de data de nascimento, posiciona
        txtDataNascimento.setText(Apoio.retornaPrefsValorString(this, Apoio.PREFS_CADASTRO_DATA_NASC, ""));

        //Mostra o teclado
        ((InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE)).toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

        //Seta o foco no controle
        txtDataNascimento.requestFocus();
    }

    /**
     * Chama a tela de cadastro (celular)
     */
    private void chamaTelaCadastroCelular() throws Exception
    {
        Intent intent = null;

        //Remove os erros atuais dos campos
        removerErroDosCampos();

        //Valida os campos da tela
        if (!validaCamposTela())
        {
            return;
        }

        //Grava o dado na preferencia
        Apoio.gravaPrefsValorString(this, Apoio.PREFS_CADASTRO_DATA_NASC, txtDataNascimento.getText().toString().trim());

        //Chama a tela principal
        intent = new Intent(this, FrmCadastroCelular.class);
        startActivityForResult(intent, Apoio.RETORNO_TELA_DETRAN);
    }

    /**
     * Remove todos os erros dos InputTextLayout
     */
    private void removerErroDosCampos() throws Exception
    {
        //Tira o erro
        inputDataNascimento.setError("");
        inputDataNascimento.setErrorEnabled(false);
    }

    /**
     * Valida os campos da tela
     */
    private boolean validaCamposTela() throws Exception
    {
        String sDataNasc = "";

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
            if (view == cmdContinuar)
            {
                //Chama a tela de cadastro celular
                chamaTelaCadastroCelular();
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
            if(iRequest == Apoio.RETORNO_TELA_DETRAN)
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
}
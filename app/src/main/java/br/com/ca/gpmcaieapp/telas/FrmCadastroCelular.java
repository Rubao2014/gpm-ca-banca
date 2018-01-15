package br.com.ca.gpmcaieapp.telas;

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
import br.com.ca.gpmcaieapp.R;
import br.com.ca.gpmcaieapp.base.ActivityBase;
import br.com.ca.gpmcaieapp.util.Apoio;
import br.com.ca.gpmcaieapp.util.DialogAlerta;
import br.com.ca.gpmcaieapp.util.LogTrace;
import br.com.ca.gpmcaieapp.util.MascaraCampos;

/**
 * Classe FrmCadastroCelular
 */
public class FrmCadastroCelular extends ActivityBase implements View.OnClickListener
{
    // Controles da classe
    private Toolbar toolbar = null;
    private TextInputLayout inputCelular = null;
    private EditText txtCelular = null;
    private Button cmdContinuar = null;

    @Override
    protected void onCreate(Bundle bundleSavedInstanceState)
    {
        // Carrega a tela
        setContentView(R.layout.frm_cadastro_celular);

        //chama os métodos automáticos da classe pai
        super.onCreate(bundleSavedInstanceState);
    }

    @Override
    public void iniciaControles() throws Exception
    {
        Drawable drawableArrow = null;
        TextWatcher textWatcherCelular = null;

        // Obtem os controles
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        inputCelular = (TextInputLayout) findViewById(R.id.inputCelular);
        txtCelular = (EditText) findViewById(R.id.txtCelular);
        cmdContinuar = (Button) findViewById(R.id.cmdEnviarInscricao);

        //coloca a mascara nos campos
        textWatcherCelular = MascaraCampos.insert("(##)#####-####", txtCelular);
        txtCelular.addTextChangedListener(textWatcherCelular);

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
        //Seta tiver dados de celular, posiciona
        txtCelular.setText(Apoio.retornaPrefsValorString(this, Apoio.PREFS_CADASTRO_CELULAR, ""));

        //Mostra o teclado
        ((InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE)).toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

        //Seta o foco no controle
        txtCelular.requestFocus();
    }

    /**
     * Chama a tela de cadastro (email)
     */
    private void chamaTelaCadastroEmail() throws Exception
    {
        Intent intent = null;
        String sCelular = "";

        //Remove os erros atuais dos campos
        removerErroDosCampos();

        //Valida os campos da tela
        if (!validaCamposTela())
        {
            return;
        }

        //Ajusta o celular para tirar os parenteses e o traço
        sCelular = txtCelular.getText().toString().trim().replace("(", "");
        sCelular = sCelular.replace(")", "");
        sCelular = sCelular.replace("-", "");

        //Grava o dado na preferencia
        Apoio.gravaPrefsValorString(this, Apoio.PREFS_CADASTRO_CELULAR, sCelular);

        //Chama a tela principal
        intent = new Intent(this, FrmCadastroEmail.class);
        startActivityForResult(intent, Apoio.RETORNO_TELA_DETRAN);
    }

    /**
     * Remove todos os erros dos InputTextLayout
     */
    private void removerErroDosCampos() throws Exception
    {
        //Tira o erro
        inputCelular.setError("");
        inputCelular.setErrorEnabled(false);
    }

    /**
     * Valida os campos da tela
     */
    private boolean validaCamposTela() throws Exception
    {
        String sCelular = "";

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
                //Chama a tela de cadastro email
                chamaTelaCadastroEmail();
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
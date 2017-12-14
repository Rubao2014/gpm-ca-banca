package br.gov.al.itec.gpmapp.telas;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import br.gov.al.itec.gpmapp.R;
import br.gov.al.itec.gpmapp.base.ActivityBase;
import br.gov.al.itec.gpmapp.util.Apoio;
import br.gov.al.itec.gpmapp.util.DialogAlerta;
import br.gov.al.itec.gpmapp.util.LogTrace;

/**
 * Classe FrmCadastroNome
 */
public class FrmCadastroNome extends ActivityBase implements View.OnClickListener
{
    // Controles da classe
    private Toolbar toolbar = null;
    private TextInputLayout inputNome = null;
    private EditText txtNome = null;
    private Button cmdContinuar = null;

    @Override
    protected void onCreate(Bundle bundleSavedInstanceState)
    {
        // Carrega a tela
        setContentView(R.layout.frm_cadastro_nome);

        //chama os métodos automáticos da classe pai
        super.onCreate(bundleSavedInstanceState);
    }

    @Override
    public void iniciaControles() throws Exception
    {
        Drawable drawableArrow = null;

        // Obtem os controles
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        inputNome = (TextInputLayout) findViewById(R.id.inputNome);
        txtNome = (EditText) findViewById(R.id.txtNome);
        cmdContinuar = (Button) findViewById(R.id.cmdEnviarInscricao);

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
        //Seta tiver dados de nome, posiciona
        txtNome.setText(Apoio.retornaPrefsValorString(this, Apoio.PREFS_CADASTRO_NOME, ""));

        //Mostra o teclado
        ((InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE)).toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

        //Seta o foco no controle
        txtNome.requestFocus();
    }

    /**
     * Chama a tela de cadastro (nome)
     */
    private void chamaTelaCadastroCPF() throws Exception
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
        Apoio.gravaPrefsValorString(this, Apoio.PREFS_CADASTRO_NOME, txtNome.getText().toString().trim().toUpperCase());

        //Chama a tela principal
        intent = new Intent(this, FrmCadastroCPF.class);
        startActivityForResult(intent, Apoio.RETORNO_TELA_DETRAN);
    }

    /**
     * Remove todos os erros dos InputTextLayout
     */
    private void removerErroDosCampos() throws Exception
    {
        //Tira o erro
        inputNome.setError("");
        inputNome.setErrorEnabled(false);
    }

    /**
     * Valida os campos da tela
     */
    private boolean validaCamposTela() throws Exception
    {
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

        return true;
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
            //Quando apertar o botão entrar
            if (view == cmdContinuar)
            {
                //Chama a tela de cadastro nome
                chamaTelaCadastroCPF();
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
}
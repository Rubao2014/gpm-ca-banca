package br.gov.al.itec.gpmapp.telas;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import br.gov.al.itec.gpmapp.R;
import br.gov.al.itec.gpmapp.base.ActivityBase;
import br.gov.al.itec.gpmapp.util.Apoio;
import br.gov.al.itec.gpmapp.util.DialogAlerta;
import br.gov.al.itec.gpmapp.util.LogTrace;

/**
 * Classe FrmCadastroInicio
 */
public class FrmCadastroInicio extends ActivityBase implements View.OnClickListener
{
    // Controles da classe
    private Toolbar toolbar = null;
    private Button cmdContinuarAgora = null;
    private Button cmdJaPossuiConta = null;

    @Override
    protected void onCreate(Bundle bundleSavedInstanceState)
    {
        // Carrega a tela
        setContentView(R.layout.frm_cadastro_inicio);

        //chama os métodos automáticos da classe pai
        super.onCreate(bundleSavedInstanceState);
    }

    @Override
    public void iniciaControles() throws Exception
    {
        Drawable drawableArrow = null;

        // Obtem os controles
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        cmdContinuarAgora = (Button) findViewById(R.id.cmdContinuarAgora);
        cmdJaPossuiConta = (Button) findViewById(R.id.cmdJaPossuiConta);

        //Muda a cor da flecha padrão do Android para branco
        drawableArrow = ContextCompat.getDrawable(this, R.drawable.abc_ic_ab_back_material);
        drawableArrow.setColorFilter(ContextCompat.getColor(this, R.color.branco), PorterDuff.Mode.SRC_ATOP);

        //Seta o navigation item
        toolbar.setNavigationIcon(drawableArrow);

        //Troca a cor do botao de voltar
        toolbar.setNavigationContentDescription(Apoio.TOOLBAR_VOLTAR);

        //Seta o listener dos controles
        cmdContinuarAgora.setOnClickListener(this);
        cmdJaPossuiConta.setOnClickListener(this);

        //Coloca a toolbar como actionbar e adiciona as opções de menu
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public void carregaDados() throws Exception
    {
    }

    /**
     * Chama a tela de cadastro (nome)
     */
    private void chamaTelaCadastroNome() throws Exception
    {
        Intent intent = null;

        //Limpa as preferencias de cadastro
        Apoio.limpaPreferenciasCadastro(this);

        //Chama a tela principal
        intent = new Intent(this, FrmCadastroNome.class);
        startActivityForResult(intent, Apoio.RETORNO_TELA_DETRAN);
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
            if (view == cmdContinuarAgora)
            {
                //Chama a tela de cadastro nome
                chamaTelaCadastroNome();
            }
            //Quando apertar o botão registrar
            else if (view == cmdJaPossuiConta)
            {
                //Finaliza e volta para o login
                Apoio.finalizaActivity(this, RESULT_OK);
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
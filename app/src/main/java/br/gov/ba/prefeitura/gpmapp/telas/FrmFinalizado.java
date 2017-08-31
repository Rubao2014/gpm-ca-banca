package br.gov.ba.prefeitura.gpmapp.telas;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import br.gov.ba.prefeitura.gpmapp.R;
import br.gov.ba.prefeitura.gpmapp.base.ActivityBase;
import br.gov.ba.prefeitura.gpmapp.util.Apoio;
import br.gov.ba.prefeitura.gpmapp.util.DialogAlerta;
import br.gov.ba.prefeitura.gpmapp.util.LogTrace;

/**
 * Classe FrmFinalizado
 */
public class FrmFinalizado extends ActivityBase implements View.OnClickListener
{
    //Controles da classe
    private Toolbar toolbar = null;
    private TextView lblTituloTela = null;
    private ImageView imgStatus = null;
    private TextView lblMensagemTela = null;
    private Button cmdFinalizar = null;

    //Variáveis da classe
    private boolean bTipoMensagem = false;
    private String sMensagem = "";
    private String sTitulo = "";

    @Override
    protected void onCreate(Bundle bundleSavedInstanceState)
    {
        // Carrega a tela
        setContentView(R.layout.frm_finalizado);

        //chama os métodos automáticos da classe pai
        super.onCreate(bundleSavedInstanceState);
    }

    @Override
    public void iniciaControles() throws Exception
    {
        Drawable drawableArrow = null;

        // Obtem os controles
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        lblTituloTela = (TextView) findViewById(R.id.lblTituloTela);
        imgStatus = (ImageView) findViewById(R.id.imgStatus);
        lblMensagemTela = (TextView) findViewById(R.id.lblMensagemTela);
        cmdFinalizar = (Button) findViewById(R.id.cmdFinalizar);

        //Muda a cor da flecha padrão do Android para branco
        drawableArrow = ContextCompat.getDrawable(this, R.drawable.abc_ic_ab_back_material);
        drawableArrow.setColorFilter(ContextCompat.getColor(this, R.color.branco), PorterDuff.Mode.SRC_ATOP);

        //Seta o navigation item
        toolbar.setNavigationIcon(drawableArrow);

        //Troca a cor do botao de voltar
        toolbar.setNavigationContentDescription(Apoio.TOOLBAR_VOLTAR);

        //Seta o listener dos controles
        cmdFinalizar.setOnClickListener(this);

        //Coloca a toolbar como actionbar e adiciona as opções de menu
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public void carregaDados() throws Exception
    {
        //Seta o titulo da janela
        toolbar.setTitle(sTitulo);

        //Se for sucesso
        if ( bTipoMensagem )
        {
            //Mostra a imagem de sucesso no imageview e seta os campos
            lblTituloTela.setText(getString(R.string.label_cadastro_finalizado_titulo_sucesso));
            lblTituloTela.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));
            lblMensagemTela.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));
            imgStatus.setImageResource(R.mipmap.sucesso);
        }
        else
        {
            //Mostra a imagem de erro no imageview e seta os campos
            lblTituloTela.setText(getString(R.string.label_cadastro_finalizado_titulo_erro));
            lblTituloTela.setTextColor(ContextCompat.getColor(this, R.color.vermelho));
            lblMensagemTela.setTextColor(ContextCompat.getColor(this, R.color.vermelho));
            imgStatus.setImageResource(R.mipmap.erro);
        }

        //Seta a mensagem
        lblMensagemTela.setText(sMensagem);
    }

    @Override
    public void obtemParametros() throws Exception
    {
        Intent intent;

        super.obtemParametros();

        //Obtem os parametros
        intent = this.getIntent();
        bTipoMensagem = intent.getBooleanExtra(Apoio.TELA_SUCESSO_TIPO_MENSAGEM, false);
        sMensagem = intent.getStringExtra(Apoio.TELA_SUCESSO_MENSAGEM);
        sTitulo = intent.getStringExtra(Apoio.TELA_SUCESSO_TITULO);
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
            //Quando apertar o botão finalizar
            if (view == cmdFinalizar)
            {
                //Se for sucesso
                if ( bTipoMensagem )
                {
                    //Finaliza para voltar para o login
                    Apoio.finalizaActivity(this, RESULT_OK);
                }
                else
                {
                    //Finaliza para voltar para o login
                    Apoio.finalizaActivity(this, RESULT_CANCELED);
                }
            }
        }
        catch (Exception err)
        {
            LogTrace.escreve(Apoio.localErro(getClass(), err), Apoio.getPathLogsSDCard(this), Apoio.getArqErr());
            DialogAlerta.show(this, Apoio.getMsgErr(getClass(), err), getString(R.string.atencao), getString(R.string.ok));
        }
    }
}
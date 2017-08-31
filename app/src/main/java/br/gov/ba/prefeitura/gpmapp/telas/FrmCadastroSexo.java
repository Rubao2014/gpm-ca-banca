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
import android.widget.LinearLayout;
import java.net.URLEncoder;
import br.gov.ba.prefeitura.gpmapp.R;
import br.gov.ba.prefeitura.gpmapp.base.ActivityBase;
import br.gov.ba.prefeitura.gpmapp.models.CommRespostaToken;
import br.gov.ba.prefeitura.gpmapp.tasks.TaskObtemToken;
import br.gov.ba.prefeitura.gpmapp.tasks.TaskRealizaGravacaoUsuario;
import br.gov.ba.prefeitura.gpmapp.util.Apoio;
import br.gov.ba.prefeitura.gpmapp.util.DialogAlerta;
import br.gov.ba.prefeitura.gpmapp.util.LogTrace;
import br.gov.ba.prefeitura.gpmapp.util.RestCommunication;

/**
 * Classe FrmCadastroSexo
 */
public class FrmCadastroSexo extends ActivityBase implements View.OnClickListener
{
    // Controles da classe
    private Toolbar toolbar = null;
    private Button cmdContinuar = null;
    private LinearLayout lnlSexoMasculino = null;
    private LinearLayout lnlSexoFeminino = null;
    private ImageView imgMasculino = null;
    private ImageView imgFeminino = null;

    // Variaveis da classe
    private boolean bSexoSelecionado = false;

    @Override
    protected void onCreate(Bundle bundleSavedInstanceState)
    {
        // Carrega a tela
        setContentView(R.layout.frm_cadastro_sexo);

        //chama os métodos automáticos da classe pai
        super.onCreate(bundleSavedInstanceState);
    }

    @Override
    public void iniciaControles() throws Exception
    {
        Drawable drawableArrow = null;

        // Obtem os controles
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        cmdContinuar = (Button) findViewById(R.id.cmdContinuar);
        lnlSexoMasculino = (LinearLayout) findViewById(R.id.lnlSexoMasculino);
        lnlSexoFeminino = (LinearLayout) findViewById(R.id.lnlSexoFeminino);
        imgMasculino = (ImageView) findViewById(R.id.imgMasculino);
        imgFeminino = (ImageView) findViewById(R.id.imgFeminino);

        //Muda a cor da flecha padrão do Android para branco
        drawableArrow = ContextCompat.getDrawable(this, R.drawable.abc_ic_ab_back_material);
        drawableArrow.setColorFilter(ContextCompat.getColor(this, R.color.branco), PorterDuff.Mode.SRC_ATOP);

        //Seta o navigation item
        toolbar.setNavigationIcon(drawableArrow);

        //Troca a cor do botao de voltar
        toolbar.setNavigationContentDescription(Apoio.TOOLBAR_VOLTAR);

        //Seta o listener dos controles
        cmdContinuar.setOnClickListener(this);
        lnlSexoMasculino.setOnClickListener(this);
        lnlSexoFeminino.setOnClickListener(this);

        //Coloca a toolbar como actionbar e adiciona as opções de menu
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public void carregaDados() throws Exception
    {
        int iRetornoSexo = 0;

        //esconde os marcadores de sexo
        imgFeminino.setVisibility(View.GONE);
        imgMasculino.setVisibility(View.GONE);

        //Obtem o sexo
        iRetornoSexo = Apoio.retornaPrefsValorInteiro(this, Apoio.PREFS_CADASTRO_SEXO, 0);

        //Se tiver selecionado o sexo masculino
        if ( iRetornoSexo == Apoio.TELA_CADASTRO_SEXO_MASCULINO )
        {
            //Habilita o check
            imgMasculino.setVisibility(View.VISIBLE);
            bSexoSelecionado = true;
        }
        else if ( iRetornoSexo == Apoio.TELA_CADASTRO_SEXO_FEMININO )
        {
            //Habilita o check
            imgFeminino.setVisibility(View.VISIBLE);
            bSexoSelecionado = true;
        }
    }

    /**
     * Chama a comunicação para validação do token do app
     */
    private void realizaComunicacaoValidacaoToken() throws Exception
    {
        String sBody = "";

        //Valida os campos da tela
        if (!validaCamposTela())
        {
            return;
        }

        //Se tiver selecionado o sexo masculino
        if ( imgMasculino.getVisibility() == View.VISIBLE )
        {
            //Grava o dado na preferencia, para posicionamento no caso de ajuste
            Apoio.gravaPrefsValorInteiro(this, Apoio.PREFS_CADASTRO_SEXO, Apoio.TELA_CADASTRO_SEXO_MASCULINO);
        }
        else
        {
            //Grava o dado na preferencia, para posicionamento no caso de ajuste
            Apoio.gravaPrefsValorInteiro(this, Apoio.PREFS_CADASTRO_SEXO, Apoio.TELA_CADASTRO_SEXO_FEMININO);
        }

        //Se não tiver conexão
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

    /**
     * Chama a comunicação para o cadsatro do usuário
     */
    private void realizaComunicacaoGravacaoUsuario(CommRespostaToken commRespostaToken) throws Exception
    {
        String sBody = "";

        //Monta o body e passa no post
        sBody = "nomeCompleto=" + URLEncoder.encode(Apoio.retornaPrefsValorString(this, Apoio.PREFS_CADASTRO_NOME, ""), "UTF-8");
        sBody += "&email=" + URLEncoder.encode(Apoio.retornaPrefsValorString(this, Apoio.PREFS_CADASTRO_EMAIL, ""), "UTF-8");
        sBody += "&senha=" + URLEncoder.encode(Apoio.retornaPrefsValorString(this, Apoio.PREFS_CADASTRO_SENHA, ""), "UTF-8");
        sBody += "&dataNascimento=" + URLEncoder.encode(Apoio.retornaPrefsValorString(this, Apoio.PREFS_CADASTRO_DATA_NASC, ""), "UTF-8");
        sBody += "&cpf=" + URLEncoder.encode(Apoio.retornaPrefsValorString(this, Apoio.PREFS_CADASTRO_CPF, ""), "UTF-8");
        sBody += "&telefoneCelular=" + URLEncoder.encode(Apoio.retornaPrefsValorString(this, Apoio.PREFS_CADASTRO_CELULAR, ""), "UTF-8");

        //Chama a task de gravação do usuário
        new TaskRealizaGravacaoUsuario(this, this, sBody, commRespostaToken.sAccessToken, RestCommunication.RESTPOST).execute();
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
        intent.putExtra(Apoio.TELA_SUCESSO_TITULO, getString(R.string.toolbar_cadastre_se));
        startActivityForResult(intent, Apoio.RETORNO_TELA_DETRAN);
    }

    /**
     * Valida os campos da tela
     */
    private boolean validaCamposTela() throws Exception
    {
        //Valida se tem um sexo selecionado
        if (!bSexoSelecionado)
        {
            //Coloca a mensagem de erro embaixo do campo e solicita o foco
            DialogAlerta.show(this, getString(R.string.msg_cadastro_sexo_nao_selecionado), getString(R.string.atencao), getString(R.string.ok));
            return false;
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
                //Realiza a comunicação para obter o token
                realizaComunicacaoValidacaoToken();
            }
            //Quando apertar o sexo masculino
            else if (view == lnlSexoMasculino)
            {
                //Seta como sexo selecionado
                bSexoSelecionado = true;

                //mostra o sexo masculino
                imgFeminino.setVisibility(View.GONE);
                imgMasculino.setVisibility(View.VISIBLE);
            }
            //Quando apertar o sexo feminino
            else if (view == lnlSexoFeminino)
            {
                //Seta como sexo selecionado
                bSexoSelecionado = true;

                //mostra o sexo feminino
                imgFeminino.setVisibility(View.VISIBLE);
                imgMasculino.setVisibility(View.GONE);
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
                    realizaComunicacaoGravacaoUsuario(commRespostaToken);
                }
                else
                {
                    //Obtem a mensagem de erro
                    sMensagem = (String) oObjetos[0];

                    //Chama a tela de finalizado com erro
                    chamaTelaFinalizado(false, sMensagem);
                }
            }
            //Se for a classe da task de gravação
            else if (classe == TaskRealizaGravacaoUsuario.class)
            {
                //Obtem a mensagem de erro
                sMensagem = (String) oObjetos[0];

                //Se o resultado for sucesso
                if (bResultado)
                {
                    //Limpa as preferencias de cadastro
                    Apoio.limpaPreferenciasCadastro(this);
                }

                //Chama a tela de cadastro com sucesso
                chamaTelaFinalizado(bResultado, sMensagem);
            }
        }
        catch (Exception err)
        {
            LogTrace.escreve(Apoio.localErro(getClass(), err), Apoio.getPathLogsSDCard(this), Apoio.getArqErr());
            DialogAlerta.show(this, Apoio.getMsgErr(getClass(), err), getString(R.string.atencao), getString(R.string.ok));
        }
    }
}
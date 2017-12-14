package br.gov.al.itec.gpmapp.telas;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import java.util.ArrayList;
import br.gov.al.itec.gpmapp.R;
import br.gov.al.itec.gpmapp.base.ActivityBase;
import br.gov.al.itec.gpmapp.models.Combos;
import br.gov.al.itec.gpmapp.models.Cursos;
import br.gov.al.itec.gpmapp.util.Apoio;
import br.gov.al.itec.gpmapp.util.DialogAlerta;
import br.gov.al.itec.gpmapp.util.LogTrace;
import br.gov.al.itec.gpmapp.util.MascaraCampos;

/**
 * Classe FrmAtualizacaoCadCursosNovo
 */
public class FrmAtualizacaoCadCursosNovo extends ActivityBase
{
    // Controles da classe
    private Toolbar toolbar = null;
    private Spinner cboTipo = null;
    private TextView lblErroTipoCurso = null;
    private TextInputLayout inputDescricao = null;
    private TextInputLayout inputInstuicao = null;
    private TextInputLayout inputCargaHoraria = null;
    private TextInputLayout inputDataDeConclusao = null;
    private EditText txtDescricao = null;
    private EditText txtInstuicao = null;
    private EditText txtCargaHoraria = null;
    private EditText txtDataDeConclusao = null;

    //Variáveis da classe
    private ArrayList<Combos> arrComboTipoCurso = null;
    private Cursos cursos = null;

    @Override
    protected void onCreate(Bundle bundleSavedInstanceState)
    {
        // Carrega a tela
        setContentView(R.layout.frm_atualizacao_cad_cursos_novo);

        //chama os métodos automáticos da classe pai
        super.onCreate(bundleSavedInstanceState);
    }

    @Override
    public void iniciaControles() throws Exception
    {
        Drawable drawableArrow = null;
        TextWatcher textWatcherData = null;

        // Obtem os controles
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        cboTipo = (Spinner) findViewById(R.id.cboTipo);
        lblErroTipoCurso = (TextView) findViewById(R.id.lblErroTipoCurso);
        inputDescricao = (TextInputLayout) findViewById(R.id.inputDescricao);
        inputInstuicao = (TextInputLayout) findViewById(R.id.inputInstituicao);
        inputCargaHoraria = (TextInputLayout) findViewById(R.id.inputCargaHoraria);
        inputDataDeConclusao = (TextInputLayout) findViewById(R.id.inputDataDeConclusao);
        txtDescricao = (EditText) findViewById(R.id.txtDescricao);
        txtInstuicao = (EditText) findViewById(R.id.txtInstituicao);
        txtCargaHoraria = (EditText) findViewById(R.id.txtCargaHoraria);
        txtDataDeConclusao = (EditText) findViewById(R.id.txtDataDeConclusao);

        //Insere a mascara nos campos
        textWatcherData = MascaraCampos.insert("##/##/####", txtDataDeConclusao);
        txtDataDeConclusao.addTextChangedListener(textWatcherData);

        //Muda a cor da flecha padrão do Android para branco
        drawableArrow = ContextCompat.getDrawable(this, R.drawable.abc_ic_ab_back_material);
        drawableArrow.setColorFilter(ContextCompat.getColor(this, R.color.branco), PorterDuff.Mode.SRC_ATOP);

        //Seta o navigation item
        toolbar.setNavigationIcon(drawableArrow);

        //Troca a cor do botao de voltar
        toolbar.setNavigationContentDescription(Apoio.TOOLBAR_VOLTAR);

        //Esconde o label de erro
        lblErroTipoCurso.setVisibility(View.GONE);

        //Coloca a toolbar como actionbar e adiciona as opções de menu
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public void carregaDados() throws Exception
    {
        //Preenche combo de tipo de universitário
        preencheComboUniversitario();

        //Preenche o formulário se for alteração
        if(cursos != null)
        {
            //Posiciona os combos
            cboTipo.setSelection(Apoio.retornaPosicaoItemCombo(arrComboTipoCurso, cursos.sTipoCurso));

            //Seta os textos
            txtDescricao.setText(cursos.sDescricaoCurso);
            txtInstuicao.setText(cursos.sLocalCurso);
            txtCargaHoraria.setText(cursos.sCargaHorariaCurso);
            txtDataDeConclusao.setText(Apoio.parseData(cursos.sDataConclusaoCurso, "yyyyMMdd", "dd/MM/yyyy"));
        }
    }

    @Override
    public void obtemParametros() throws Exception
    {
        super.obtemParametros();

        //Obtem parâmetros
        cursos = (Cursos)getIntent().getExtras().get(Apoio.TELA_CURSOS_OBJETO_ALTERACAO);
    }

    /**
     * Preenche o combo de universitário
     */
    private void preencheComboUniversitario() throws Exception
    {
        ArrayAdapter<Combos> dataAdapter = null;

        //Carrega os dados
        arrComboTipoCurso = new ArrayList<Combos>();
        arrComboTipoCurso.add(new Combos("", ""));
        arrComboTipoCurso.add(new Combos("1", "FORMAÇÃO ACADÊMICA"));
        arrComboTipoCurso.add(new Combos("2", "CÍVIL"));
        arrComboTipoCurso.add(new Combos("3", "MILITAR"));
        arrComboTipoCurso.add(new Combos("4", "IDIOMAS"));
        arrComboTipoCurso.add(new Combos("5", "ESPECIALIZAÇÃO"));
        arrComboTipoCurso.add(new Combos("6", "OUTROS"));

        //Cria o adapter
        dataAdapter = new ArrayAdapter<Combos>(this, android.R.layout.simple_spinner_item, arrComboTipoCurso);

        //Seta o adapter no dropdown
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //Atacha o adapter ao controle
        cboTipo.setAdapter(dataAdapter);
    }

    /**
     * Grava os dados de um novo curso
     */
    private void gravaDadosNovoCurso() throws Exception
    {
        Intent intent = null;
        Combos combos = null;

        //Remove os erros atuais dos campos
        removerErroDosCampos();

        //Valida os campos da tela
        if (!validaCamposTela())
        {
            return;
        }

        //Se for um novo curso
        if ( cursos == null )
        {
            //Cria um novo objeto curso
            cursos = new Cursos();
        }

        //Obtem o tipo de curso
        combos = arrComboTipoCurso.get(cboTipo.getSelectedItemPosition());

        //Seta os valores obtidos em tela
        cursos.sTipoCurso = combos.sCodigo;
        cursos.sDescricaoCurso = txtDescricao.getText().toString().trim().toUpperCase();
        cursos.sLocalCurso = txtInstuicao.getText().toString().trim().toUpperCase();
        cursos.sCargaHorariaCurso = txtCargaHoraria.getText().toString().trim().toUpperCase();
        cursos.sDataConclusaoCurso = Apoio.parseData(txtDataDeConclusao.getText().toString().trim(), "dd/MM/yyyy", "yyyyMMdd");

        //Seta o parâmetro de retorno com a classe cursos
        intent = new Intent();
        intent.putExtra(Apoio.TELA_CURSOS_OBJETO_ALTERACAO, cursos);

        //Finaliza tela com o retorno
        Apoio.finalizaActivity(this, RESULT_OK, intent);
    }

    /**
     * Remove todos os erros dos InputTextLayout
     */
    private void removerErroDosCampos() throws Exception
    {
        //Tira o erro
        inputDescricao.setError("");
        inputDescricao.setErrorEnabled(false);
        inputInstuicao.setError("");
        inputInstuicao.setErrorEnabled(false);
        inputCargaHoraria.setError("");
        inputCargaHoraria.setErrorEnabled(false);
        inputDataDeConclusao.setError("");
        inputDataDeConclusao.setErrorEnabled(false);
        lblErroTipoCurso.setVisibility(View.GONE);
    }

    /**
     * Valida os campos da tela
     */
    private boolean validaCamposTela() throws Exception
    {
        int iAuxi = 0;
        String sDataConclusao = "";

        //Valida o combo de tipo
        if (cboTipo.getSelectedItemPosition() <= 0)
        {
            //Coloca a mensagem de erro embaixo do campo e solicita o foco
            lblErroTipoCurso.setText(getString(R.string.msg_cadastro_novos_cursos_tipo_nao_selecionado));
            lblErroTipoCurso.setVisibility(View.VISIBLE);
            Apoio.requisitarFoco(cboTipo, this);
            return false;
        }
        else
        {
            //Se estier OK, remove o erro
            lblErroTipoCurso.setText("");
            lblErroTipoCurso.setVisibility(View.GONE);
        }

        //Valida a descrição
        if (txtDescricao.getText().toString().trim().length() <= 0)
        {
            //Coloca a mensagem de erro embaixo do campo e solicita o foco
            inputDescricao.setError(getString(R.string.msg_cadastro_novos_cursos_descricao_em_branco));
            inputDescricao.setErrorEnabled(true);
            Apoio.requisitarFoco(txtDescricao, this);
            return false;
        }
        else
        {
            //Se estier OK, remove o erro
            inputDescricao.setError("");
            inputDescricao.setErrorEnabled(false);
        }

        //Valida a instituição
        if (txtInstuicao.getText().toString().trim().length() <= 0)
        {
            //Coloca a mensagem de erro embaixo do campo e solicita o foco
            inputInstuicao.setError(getString(R.string.msg_cadastro_novos_cursos_instituicao_em_branco));
            inputInstuicao.setErrorEnabled(true);
            Apoio.requisitarFoco(txtInstuicao, this);
            return false;
        }
        else
        {
            //Se estier OK, remove o erro
            inputInstuicao.setError("");
            inputInstuicao.setErrorEnabled(false);
        }

        //Valida a carga horária
        iAuxi = Apoio.tryParse(txtCargaHoraria.getText().toString().trim(), -1);
        if (iAuxi < 0)
        {
            //Coloca a mensagem de erro embaixo do campo e solicita o foco
            inputCargaHoraria.setError(getString(R.string.msg_cadastro_novos_cursos_carga_horaria_invalida));
            inputCargaHoraria.setErrorEnabled(true);
            Apoio.requisitarFoco(txtCargaHoraria, this);
            return false;
        }
        else
        {
            //Se estier OK, remove o erro
            inputCargaHoraria.setError("");
            inputCargaHoraria.setErrorEnabled(false);
        }

        //Obtem a data
        sDataConclusao = txtDataDeConclusao.getText().toString().trim();

        //Valida o campo de data de nascimento
        if (sDataConclusao.length() <= 0)
        {
            //Coloca a mensagem de erro embaixo do campo e solicita o foco
            inputDataDeConclusao.setError(getString(R.string.msg_cadastro_novos_cursos_data_de_conclusao_em_branco));
            inputDataDeConclusao.setErrorEnabled(true);
            Apoio.requisitarFoco(txtDataDeConclusao, this);
            return false;
        }
        else
        {
            //Se estier OK, remove o erro
            inputDataDeConclusao.setError("");
            inputDataDeConclusao.setErrorEnabled(false);
        }

        //verifica se a data é valida
        if ( Apoio.parseData(sDataConclusao, "dd/MM/yyyy", "yyyy-MM-dd").equals("") )
        {
            //Coloca a mensagem de erro embaixo do campo e solicita o foco
            inputDataDeConclusao.setError(getString(R.string.msg_cadastro_novos_cursos_data_de_conclusao_invalida));
            inputDataDeConclusao.setErrorEnabled(true);
            Apoio.requisitarFoco(txtDataDeConclusao, this);
            return false;
        }
        else
        {
            //Se estier OK, remove o erro
            inputDataDeConclusao.setError("");
            inputDataDeConclusao.setErrorEnabled(false);
        }

        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        try
        {
            //Infla os icones na toolbar
            getMenuInflater().inflate(R.menu.mnu_atualizacao_cad_cursos_novo, menu);
        }
        catch (Exception err)
        {
            LogTrace.escreve(Apoio.localErro(getClass(), err), Apoio.getPathLogsSDCard(this), Apoio.getArqErr());
            DialogAlerta.show(this, Apoio.getMsgErr(getClass(), err), getString(R.string.atencao), getString(R.string.ok));
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
                //Verifico se o click é no gravar
                case R.id.mnuGravar:
                {
                    //Grava o novo curso
                    gravaDadosNovoCurso();
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
}
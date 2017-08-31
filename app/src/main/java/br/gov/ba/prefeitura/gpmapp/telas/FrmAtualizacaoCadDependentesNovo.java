package br.gov.ba.prefeitura.gpmapp.telas;

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
import br.gov.ba.prefeitura.gpmapp.R;
import br.gov.ba.prefeitura.gpmapp.base.ActivityBase;
import br.gov.ba.prefeitura.gpmapp.models.Combos;
import br.gov.ba.prefeitura.gpmapp.models.Dependentes;
import br.gov.ba.prefeitura.gpmapp.util.Apoio;
import br.gov.ba.prefeitura.gpmapp.util.DialogAlerta;
import br.gov.ba.prefeitura.gpmapp.util.LogTrace;
import br.gov.ba.prefeitura.gpmapp.util.MascaraCampos;

/**
 * Classe FrmAtualizacaoCadDependentesNovo
 */
public class FrmAtualizacaoCadDependentesNovo extends ActivityBase
{
    // Controles da classe
    private Toolbar toolbar = null;
    private TextInputLayout inputNomeDependente = null;
    private TextInputLayout inputDataDeNascimentoDependente = null;
    private TextInputLayout inputNaturalidadeDependente = null;
    private TextInputLayout inputNacionalidadeDependente = null;
    private EditText txtNomeDependente = null;
    private EditText txtDataDeNascimentoDependente = null;
    private EditText txtCPFDependente = null;
    private EditText txtNaturalidadeDependente = null;
    private EditText txtNacionalidadeDependente = null;
    private Spinner cboSexoDependente = null;
    private Spinner cboUniversitario = null;
    private Spinner cboTipoDependente = null;
    private Spinner cboDependIR = null;
    private Spinner cboDependSF = null;
    private Spinner cboDependAssMedica = null;
    private TextView lblErroTipoDependente = null;
    private TextView lblErroDependAssMedica = null;
    private TextView lblErroDependIR = null;
    private TextView lblErroDependSF = null;
    private TextView lblErroUniversitario = null;

    //Variáveis da classe
    private ArrayList<Combos> arrComboTipoDependente = null;
    private ArrayList<Combos> arrComboUniversitario = null;
    private ArrayList<Combos> arrComboSexo = null;
    private ArrayList<Combos> arrComboDependIR = null;
    private ArrayList<Combos> arrComboDependSF = null;
    private ArrayList<Combos> arrComboDependAssMedica = null;
    private Dependentes dependentes = null;

    @Override
    protected void onCreate(Bundle bundleSavedInstanceState)
    {
        // Carrega a tela
        setContentView(R.layout.frm_atualizacao_cad_dependentes_novo);

        //chama os métodos automáticos da classe pai
        super.onCreate(bundleSavedInstanceState);
    }

    @Override
    public void iniciaControles() throws Exception
    {
        Drawable drawableArrow = null;
        TextWatcher textWatcherData = null;
        TextWatcher textWatcherCPF = null;

        // Obtem os controles
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        inputNomeDependente = (TextInputLayout) findViewById(R.id.inputNomeDependente);
        inputDataDeNascimentoDependente = (TextInputLayout) findViewById(R.id.inputDataNascimentoDependente);
        inputNaturalidadeDependente = (TextInputLayout) findViewById(R.id.inputNaturalidadeDependente);
        inputNacionalidadeDependente = (TextInputLayout) findViewById(R.id.inputNacionalidadeDependente);
        txtNomeDependente = (EditText) findViewById(R.id.txtNomeDependente);
        txtDataDeNascimentoDependente = (EditText) findViewById(R.id.txtDataNascimentoDependente);
        txtCPFDependente = (EditText) findViewById(R.id.txtCPFDependente);
        txtNaturalidadeDependente = (EditText) findViewById(R.id.txtNaturalidadeDependente);
        txtNacionalidadeDependente = (EditText) findViewById(R.id.txtNacionalidadeDependente);
        cboSexoDependente = (Spinner) findViewById(R.id.cboSexoDependente);
        cboUniversitario = (Spinner) findViewById(R.id.cboUniversitario);
        cboTipoDependente = (Spinner) findViewById(R.id.cboTipoDependente);
        cboDependIR = (Spinner) findViewById(R.id.cboDependIR);
        cboDependSF = (Spinner) findViewById(R.id.cboDependSF);
        cboDependAssMedica = (Spinner) findViewById(R.id.cboDependAssMedica);
        lblErroTipoDependente = (TextView) findViewById(R.id.lblErroTipoDependente);
        lblErroDependAssMedica = (TextView) findViewById(R.id.lblErroDependAssMedica);
        lblErroDependIR = (TextView) findViewById(R.id.lblErroDependIR);
        lblErroDependSF = (TextView) findViewById(R.id.lblErroDependSF);
        lblErroUniversitario = (TextView) findViewById(R.id.lblErroUniversitario);

        //coloca a mascara nos campos
        textWatcherData = MascaraCampos.insert("##/##/####", txtDataDeNascimentoDependente);
        txtDataDeNascimentoDependente.addTextChangedListener(textWatcherData);
        textWatcherCPF = MascaraCampos.insert("###.###.###-##", txtCPFDependente);
        txtCPFDependente.addTextChangedListener(textWatcherCPF);

        //Esconde labels de erro
        lblErroUniversitario.setVisibility(View.GONE);
        lblErroTipoDependente.setVisibility(View.GONE);
        lblErroDependAssMedica.setVisibility(View.GONE);
        lblErroDependIR.setVisibility(View.GONE);
        lblErroDependSF.setVisibility(View.GONE);

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
    }

    @Override
    public void carregaDados() throws Exception
    {
        //Preenche combo de tipo de dependente
        preencheComboTipoDependente();

        //Preenche combo de universitário
        preencheComboUniversitario();

        //Preenche combo de universitário
        preencheComboSexo();

        //Preenche combo de Depend. Ir
        preencheComboDependIr();

        //Preenche combo de Depend. Sf
        preencheComboDependSf();

        //Preenche combo de Depend. Assistencia Médica
        preencheComboDependAssMedica();

        //Se for alteração dependentes será igual a null, então os campos serão preenchidos
        if(dependentes != null)
        {
            txtNomeDependente.setText(dependentes.sNome);
            txtDataDeNascimentoDependente.setText( Apoio.parseData( dependentes.sDataNascimento,"yyyyMMdd" ,"dd/MM/yyyy" ) );
            txtCPFDependente.setText(dependentes.sCPFDependente);
            txtNaturalidadeDependente.setText(dependentes.sNaturalidade);
            txtNacionalidadeDependente.setText(dependentes.sNacionalidade);
            cboSexoDependente.setSelection(Apoio.retornaPosicaoItemCombo(arrComboSexo, dependentes.sSexo));
            cboUniversitario.setSelection(Apoio.retornaPosicaoItemCombo(arrComboUniversitario, dependentes.sUniversitario));
            cboTipoDependente.setSelection(Apoio.retornaPosicaoItemCombo(arrComboTipoDependente, dependentes.sTipoDependenteID));
            cboDependIR.setSelection(Apoio.retornaPosicaoItemCombo(arrComboDependIR, dependentes.sDependenteImpRenda));
            cboDependSF.setSelection(Apoio.retornaPosicaoItemCombo(arrComboDependSF, dependentes.sDependenteSalFamilia));
            cboDependAssMedica.setSelection(Apoio.retornaPosicaoItemCombo(arrComboDependAssMedica, dependentes.sDependenteAssMedica));
        }
    }

    @Override
    public void obtemParametros() throws Exception
    {
        super.obtemParametros();

        //Obtem os parâmetros da tela
        dependentes = (Dependentes) getIntent().getExtras().get(Apoio.TELA_DEPENDENTES_OBJETO_ALTERACAO);
    }

    /**
     * Preenche o combo de tipo de dependente
     */
    private void preencheComboTipoDependente() throws Exception
    {
        ArrayAdapter<Combos> dataAdapter = null;

        //Carrega os dados
        arrComboTipoDependente = new ArrayList<Combos>();
        arrComboTipoDependente.add(new Combos("", ""));
        arrComboTipoDependente.add(new Combos("0", "OUTROS"));
        arrComboTipoDependente.add(new Combos("1", "CÔNJUGE"));
        arrComboTipoDependente.add(new Combos("2", "COMPANHEIRO(A)"));
        arrComboTipoDependente.add(new Combos("3", "FILHO(A) NÃO EMANCIPADO MENOR 21 ANOS"));
        arrComboTipoDependente.add(new Combos("4", "FILHO(A) INVÁLIDO(A)"));
        arrComboTipoDependente.add(new Combos("5", "PAI / MÃE COM DEPENDÊNCIA ECONOMICA"));
        arrComboTipoDependente.add(new Combos("6", "IRMÃO NÂO EMANCIPADO MENOR DE 21 ANOS COM DEP. ECONÔMICA"));
        arrComboTipoDependente.add(new Combos("7", "IRMÃO INVÁLIDO COM DEPENDÊNCIA ECONÔMICA"));
        arrComboTipoDependente.add(new Combos("8", "ENTEADO NÃO EMANCIPADO MENOR DE 21 ANOS COM DEP. ECONÔMICA"));
        arrComboTipoDependente.add(new Combos("9", "ENTEADO INVÁLIDO COM DEPENDENCIA ECONÔMICA"));
        arrComboTipoDependente.add(new Combos("10", "MENOR TUTELADO NÃO EMANCIPADO MENOR DE 21 ANOS COM DEP. ECONÔMICA"));
        arrComboTipoDependente.add(new Combos("11", "MENOR TUTELADO INVÁLIDO COM DEP. ECONÔMICA"));
        arrComboTipoDependente.add(new Combos("12", "FILHO MAIOR DE 21 ANOS COM DEPENDÊNCIA ECONÔMICA"));
        arrComboTipoDependente.add(new Combos("14", "EX-CÔNJUGE QUE RECEBA PENSÃO DE ALIMENTOS"));

        //Cria o adapter
        dataAdapter = new ArrayAdapter<Combos>(this, android.R.layout.simple_spinner_item, arrComboTipoDependente);

        //Seta o adapter no dropdown
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //Atacha o adapter ao controle
        cboTipoDependente.setAdapter(dataAdapter);
    }

    /**
     * Preenche o combo de universitário
     */
    private void preencheComboUniversitario() throws Exception
    {
        ArrayAdapter<Combos> dataAdapter = null;

        //Carrega os dados
        arrComboUniversitario = new ArrayList<Combos>();
        arrComboUniversitario.add(new Combos("", ""));
        arrComboUniversitario.add(new Combos("N", "NÃO"));
        arrComboUniversitario.add(new Combos("S", "SIM"));

        //Cria o adapter
        dataAdapter = new ArrayAdapter<Combos>(this, android.R.layout.simple_spinner_item, arrComboUniversitario);

        //Seta o adapter no dropdown
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //Atacha o adapter ao controle
        cboUniversitario.setAdapter(dataAdapter);
    }

    /**
     * Preenche o combo de Depende Assistencia Médica
     */
    private void preencheComboDependAssMedica() throws Exception
    {
        ArrayAdapter<Combos> dataAdapter = null;

        //Carrega os dados
        arrComboDependAssMedica = new ArrayList<Combos>();
        arrComboDependAssMedica.add(new Combos("", ""));
        arrComboDependAssMedica.add(new Combos("N", "NÃO"));
        arrComboDependAssMedica.add(new Combos("S", "SIM"));

        //Cria o adapter
        dataAdapter = new ArrayAdapter<Combos>(this, android.R.layout.simple_spinner_item, arrComboDependAssMedica);

        //Seta o adapter no dropdown
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //Atacha o adapter ao controle
        cboDependAssMedica.setAdapter(dataAdapter);
    }

    /**
     * Preenche o combo de depend Ir
     */
    private void preencheComboDependIr() throws Exception
    {
        ArrayAdapter<Combos> dataAdapter = null;

        //Carrega os dados
        arrComboDependIR = new ArrayList<Combos>();
        arrComboDependIR.add(new Combos("", ""));
        arrComboDependIR.add(new Combos("N", "NÃO"));
        arrComboDependIR.add(new Combos("S", "SIM"));

        //Cria o adapter
        dataAdapter = new ArrayAdapter<Combos>(this, android.R.layout.simple_spinner_item, arrComboDependIR);

        //Seta o adapter no dropdown
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //Atacha o adapter ao controle
        cboDependIR.setAdapter(dataAdapter);
    }

    /**
     * Preenche o combo de depend. Sf
     */
    private void preencheComboDependSf() throws Exception
    {
        ArrayAdapter<Combos> dataAdapter = null;

        //Carrega os dados
        arrComboDependSF = new ArrayList<Combos>();
        arrComboDependSF.add(new Combos("", ""));
        arrComboDependSF.add(new Combos("N", "NÃO"));
        arrComboDependSF.add(new Combos("S", "SIM"));

        //Cria o adapter
        dataAdapter = new ArrayAdapter<Combos>(this, android.R.layout.simple_spinner_item, arrComboDependSF);

        //Seta o adapter no dropdown
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //Atacha o adapter ao controle
        cboDependSF.setAdapter(dataAdapter);
    }

    /**
     * Preenche o combo de sexo
     */
    private void preencheComboSexo() throws Exception
    {
        ArrayAdapter<Combos> dataAdapter = null;

        //Carrega os dados
        arrComboSexo = new ArrayList<Combos>();
        arrComboSexo.add(new Combos("", ""));
        arrComboSexo.add(new Combos("F", "FEMININO"));
        arrComboSexo.add(new Combos("M", "MASCULINO"));

        //Cria o adapter
        dataAdapter = new ArrayAdapter<Combos>(this, android.R.layout.simple_spinner_item, arrComboSexo);

        //Seta o adapter no dropdown
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //Atacha o adapter ao controle
        cboSexoDependente.setAdapter(dataAdapter);
    }

    /**
     * Grava os dados de um novo curso
     */
    private void gravaDadosNovoDependente() throws Exception
    {
        Intent intent = null;
        Combos comboTipoDependente = null;
        Combos comboSexo = null;
        Combos comboUniversitario = null;
        Combos comboDependenteIR = null;
        Combos comboDependenteSF = null;
        Combos comboDependenteAssistMedica = null;
        String sAuxi = "";

        //Remove os erros atuais dos campos
        removerErroDosCampos();

        //Valida os campos da tela
        if (!validaCamposTela())
        {
            return;
        }

        //TODO: Aqui entrara futuramente a gravação do curso
        //Se for um novo dependente
        if(dependentes == null)
        {
            //Cria um novo objeto Dependete
            dependentes = new Dependentes();
        }

        //Obtem o tipo de dependente
        comboTipoDependente = arrComboTipoDependente.get(cboTipoDependente.getSelectedItemPosition());
        comboSexo = arrComboSexo.get(cboSexoDependente.getSelectedItemPosition());
        comboUniversitario = arrComboUniversitario.get(cboUniversitario.getSelectedItemPosition());
        comboDependenteIR = arrComboDependIR.get(cboDependIR.getSelectedItemPosition());
        comboDependenteSF = arrComboDependSF.get(cboDependSF.getSelectedItemPosition());
        comboDependenteAssistMedica = arrComboDependAssMedica.get(cboDependAssMedica.getSelectedItemPosition());

        //Seta os valores obtidos na tela
        dependentes.sNome = txtNomeDependente.getText().toString().trim().toUpperCase();
        dependentes.sDataNascimento = Apoio.parseData(txtDataDeNascimentoDependente.getText().toString().trim(), "dd/MM/yyyy", "yyyyMMdd");
        dependentes.sNaturalidade = txtNaturalidadeDependente.getText().toString().trim().toUpperCase();
        dependentes.sNacionalidade = txtNacionalidadeDependente.getText().toString().trim().toUpperCase();
        dependentes.sSexo = comboSexo.sCodigo;
        dependentes.sUniversitario = comboUniversitario.sCodigo;
        dependentes.sDependenteImpRenda = comboDependenteIR.sCodigo;
        dependentes.sDependenteSalFamilia = comboDependenteSF.sCodigo;
        dependentes.sDependenteAssMedica = comboDependenteAssistMedica.sCodigo;
        dependentes.sTipoDependenteID = comboTipoDependente.sCodigo;

        //Tira os caracteres especiais
        sAuxi = txtCPFDependente.getText().toString().trim().toUpperCase();
        sAuxi = sAuxi.replace(".", "");
        sAuxi = sAuxi.replace("-", "");
        dependentes.sCPFDependente = sAuxi;

        //Seta o parâmetro de retorno com a classe dependentes
        intent = new Intent();
        intent.putExtra(Apoio.TELA_DEPENDENTES_OBJETO_ALTERACAO, dependentes);

        // Seta o resultado e finaliza
        Apoio.finalizaActivity(this, RESULT_OK, intent);
    }

    /**
     * Remove todos os erros dos InputTextLayout
     */
    private void removerErroDosCampos() throws Exception
    {
        //Tira o erro
        inputNomeDependente.setError("");
        inputNomeDependente.setErrorEnabled(false);
        inputDataDeNascimentoDependente.setError("");
        inputDataDeNascimentoDependente.setErrorEnabled(false);
        inputNaturalidadeDependente.setError("");
        inputNaturalidadeDependente.setErrorEnabled(false);
        inputNacionalidadeDependente.setError("");
        inputNacionalidadeDependente.setErrorEnabled(false);
        lblErroUniversitario.setVisibility(View.GONE);
        lblErroTipoDependente.setVisibility(View.GONE);
        lblErroDependAssMedica.setVisibility(View.GONE);
        lblErroDependIR.setVisibility(View.GONE);
        lblErroDependSF.setVisibility(View.GONE);
    }

    /**
     * Valida os campos da tela
     */
    private boolean validaCamposTela() throws Exception
    {
        String sDataNascimento = "";
        Combos comboTipoDependente = null;

        //Valida a descrição
        if (txtNomeDependente.getText().toString().trim().length() <= 0)
        {
            //Coloca a mensagem de erro embaixo do campo e solicita o foco
            inputNomeDependente.setError(getString(R.string.msg_cadastro_novos_dependentes_nome_em_branco));
            inputNomeDependente.setErrorEnabled(true);
            Apoio.requisitarFoco(txtNomeDependente, this);
            return false;
        }
        else
        {
            //Se estier OK, remove o erro
            inputNomeDependente.setError("");
            inputNomeDependente.setErrorEnabled(false);
        }

        //Obtem a data
        sDataNascimento = txtDataDeNascimentoDependente.getText().toString().trim();

        //Valida o campo de data de nascimento
        if (sDataNascimento.length() <= 0)
        {
            //Coloca a mensagem de erro embaixo do campo e solicita o foco
            inputDataDeNascimentoDependente.setError(getString(R.string.msg_cadastro_novos_dependentes_data_nasc_em_branco));
            inputDataDeNascimentoDependente.setErrorEnabled(true);
            Apoio.requisitarFoco(txtDataDeNascimentoDependente, this);
            return false;
        }
        else
        {
            //Se estier OK, remove o erro
            inputDataDeNascimentoDependente.setError("");
            inputDataDeNascimentoDependente.setErrorEnabled(false);
        }

        //verifica se a data é valida
        if ( Apoio.parseData(sDataNascimento, "dd/MM/yyyy", "yyyy-MM-dd").equals("") )
        {
            //Coloca a mensagem de erro embaixo do campo e solicita o foco
            inputDataDeNascimentoDependente.setError(getString(R.string.msg_cadastro_novos_dependentes_data_nasc_invalida));
            inputDataDeNascimentoDependente.setErrorEnabled(true);
            Apoio.requisitarFoco(txtDataDeNascimentoDependente, this);
            return false;
        }
        else
        {
            //Se estier OK, remove o erro
            inputDataDeNascimentoDependente.setError("");
            inputDataDeNascimentoDependente.setErrorEnabled(false);
        }

        //Se tiver tipo de dependente
        if ( cboTipoDependente.getSelectedItemPosition() > 0 )
        {
            //Obtem o tipo de dependente
            comboTipoDependente = arrComboTipoDependente.get(cboTipoDependente.getSelectedItemPosition());

            //Se o tipo de dependente for 3, 8, 9, 10, 11 ou 12, valida o combo de universitário
            if ( comboTipoDependente.sCodigo.equals("3") || comboTipoDependente.sCodigo.equals("8") || comboTipoDependente.sCodigo.equals("9") ||
                    comboTipoDependente.sCodigo.equals("10") || comboTipoDependente.sCodigo.equals("11") || comboTipoDependente.sCodigo.equals("12") )
            {
                //Valida o combo de universitario
                if (cboUniversitario.getSelectedItemPosition() <= 0)
                {
                    //Coloca a mensagem de erro embaixo do campo e solicita o foco
                    lblErroUniversitario.setText(getString(R.string.msg_cadastro_novos_dependentes_universitario_nao_selecionado));
                    lblErroUniversitario.setVisibility(View.VISIBLE);
                    Apoio.requisitarFoco(cboUniversitario, this);
                    return false;
                }
                else
                {
                    //Se estier OK, remove o erro
                    lblErroUniversitario.setText("");
                    lblErroUniversitario.setVisibility(View.GONE);
                }
            }
        }

        //Valida o combo de tipo de dependente
        if (cboTipoDependente.getSelectedItemPosition() <= 0)
        {
            //Coloca a mensagem de erro embaixo do campo e solicita o foco
            lblErroTipoDependente.setText(getString(R.string.msg_cadastro_novos_dependentes_tipo_nao_selecionado));
            lblErroTipoDependente.setVisibility(View.VISIBLE);
            Apoio.requisitarFoco(cboTipoDependente, this);
            return false;
        }
        else
        {
            //Se estier OK, remove o erro
            lblErroTipoDependente.setText("");
            lblErroTipoDependente.setVisibility(View.GONE);
        }

        //Valida a naturalidade
        if (txtNaturalidadeDependente.getText().toString().trim().length() <= 0)
        {
            //Coloca a mensagem de erro embaixo do campo e solicita o foco
            inputNaturalidadeDependente.setError(getString(R.string.msg_cadastro_novos_dependentes_naturalidade_em_branco));
            inputNaturalidadeDependente.setErrorEnabled(true);
            Apoio.requisitarFoco(txtNaturalidadeDependente, this);
            return false;
        }
        else
        {
            //Se estier OK, remove o erro
            inputNaturalidadeDependente.setError("");
            inputNaturalidadeDependente.setErrorEnabled(false);
        }

        //Valida a nacionalidade
        if (txtNacionalidadeDependente.getText().toString().trim().length() <= 0)
        {
            //Coloca a mensagem de erro embaixo do campo e solicita o foco
            inputNacionalidadeDependente.setError(getString(R.string.msg_cadastro_novos_dependentes_nacionalidade_em_branco));
            inputNacionalidadeDependente.setErrorEnabled(true);
            Apoio.requisitarFoco(txtNacionalidadeDependente, this);
            return false;
        }
        else
        {
            //Se estier OK, remove o erro
            inputNacionalidadeDependente.setError("");
            inputNacionalidadeDependente.setErrorEnabled(false);
        }

        //Valida o combo de dependente do IR
        if (cboDependIR.getSelectedItemPosition() <= 0)
        {
            //Coloca a mensagem de erro embaixo do campo e solicita o foco
            lblErroDependIR.setText(getString(R.string.msg_cadastro_novos_dependentes_ir_nao_selecionado));
            lblErroDependIR.setVisibility(View.VISIBLE);
            Apoio.requisitarFoco(cboDependIR, this);
            return false;
        }
        else
        {
            //Se estier OK, remove o erro
            lblErroDependIR.setText("");
            lblErroDependIR.setVisibility(View.GONE);
        }

        //Valida o combo de dependente do SF
        if (cboDependSF.getSelectedItemPosition() <= 0)
        {
            //Coloca a mensagem de erro embaixo do campo e solicita o foco
            lblErroDependSF.setText(getString(R.string.msg_cadastro_novos_dependentes_sf_nao_selecionado));
            lblErroDependSF.setVisibility(View.VISIBLE);
            Apoio.requisitarFoco(cboDependSF, this);
            return false;
        }
        else
        {
            //Se estier OK, remove o erro
            lblErroDependSF.setText("");
            lblErroDependSF.setVisibility(View.GONE);
        }

        //Valida o combo de dependente de assistencia medica
        if (cboDependAssMedica.getSelectedItemPosition() <= 0)
        {
            //Coloca a mensagem de erro embaixo do campo e solicita o foco
            lblErroDependAssMedica.setText(getString(R.string.msg_cadastro_novos_dependentes_assistencia_nao_selecionado));
            lblErroDependAssMedica.setVisibility(View.VISIBLE);
            Apoio.requisitarFoco(cboDependAssMedica, this);
            return false;
        }
        else
        {
            //Se estier OK, remove o erro
            lblErroDependAssMedica.setText("");
            lblErroDependAssMedica.setVisibility(View.GONE);
        }

        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        try
        {
            //Infla os icones na toolbar
            getMenuInflater().inflate(R.menu.mnu_atualizacao_cad_dependentes_novo, menu);
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
                    //Grava os dados do dependente
                    gravaDadosNovoDependente();
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
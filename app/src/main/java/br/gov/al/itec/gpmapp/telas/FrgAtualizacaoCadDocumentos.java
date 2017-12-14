package br.gov.al.itec.gpmapp.telas;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import java.util.ArrayList;
import br.gov.al.itec.gpmapp.R;
import br.gov.al.itec.gpmapp.base.FragmentBase;
import br.gov.al.itec.gpmapp.models.Documentos;
import br.gov.al.itec.gpmapp.models.Combos;
import br.gov.al.itec.gpmapp.util.Apoio;
import br.gov.al.itec.gpmapp.util.DialogAlerta;
import br.gov.al.itec.gpmapp.util.LogTrace;
import br.gov.al.itec.gpmapp.util.MascaraCampos;

/**
 * Fragmento de documentos da atualização cadastral
 */
public class FrgAtualizacaoCadDocumentos extends FragmentBase
{
    //Controles da tela
    private EditText txtNome = null;
    private EditText txtMae = null;
    private EditText txtPai = null;
    private EditText txtDataNascimento = null;
    private EditText txtNacionalidade = null;
    private EditText txtRG = null;
    private EditText txtRGDigito = null;
    private EditText txtOrgaoExpeditor = null;
    private EditText txtDataExpedicao = null;
    private EditText txtTituloEleitoral = null;
    private EditText txtTituloEleitoralDigito = null;
    private EditText txtZonaEleitoral = null;
    private EditText txtSecao = null;
    private EditText txtDataTituloEleitoral = null;
    private EditText txtCarteiraDeTrabalho = null;
    private EditText txtSerie = null;
    private EditText txtDataCarteiraDeTrabalho = null;
    private EditText txtCarteiraDeHabilitacao = null;
    private EditText txtCategoria = null;
    private EditText txtValidade = null;
    private EditText txtPis = null;
    private EditText txtDeficienciaFisica = null;
    private EditText txtDoencaCronica = null;
    private TextInputLayout inputNome = null;
    private TextInputLayout inputMae = null;
    private TextInputLayout inputDataNascimento = null;
    private TextInputLayout inputNacionalidade = null;
    private TextInputLayout inputRG = null;
    private TextInputLayout inputRGDigito = null;
    private TextInputLayout inputTituloEleitoral = null;
    private TextInputLayout inputPis = null;
    private TextInputLayout inputDeficienciaFisica = null;
    private TextInputLayout inputDoencaCronica = null;
    private TextView lblErroSexo = null;
    private TextView lblErroCor = null;
    private TextView lblErroEstadoCivil = null;
    private TextView lblErroGrauDeInstrucao = null;
    private TextView lblErroNaturalidade = null;
    private TextView lblErroDeficienciaFisica = null;
    private TextView lblErroDoencaCronica = null;
    private TextView lblErroRepresentanteLegal = null;
    private TextView lblErroAcompanhaDoente = null;
    private Spinner cboSexo = null;
    private Spinner cboCor = null;
    private Spinner cboGrupoSanguineo = null;
    private Spinner cboGrupoSanguineoRH = null;
    private Spinner cboNaturalidade = null;
    private Spinner cboEstadoCivil = null;
    private Spinner cboGrauDeInstrucao = null;
    private Spinner cboOrgaoExpeditorEstado = null;
    private Spinner cboSerieEstado = null;
    private Spinner cboDeficienciaFisica = null;
    private Spinner cboDoencaCronica = null;
    private Spinner cboRepresentanteLegal = null;
    private Spinner cboAcompanhaDoente = null;

    //Variaveis da classe
    private ArrayList<Combos> arrComboEstadoCivil = null;
    private ArrayList<Combos> arrComboGrauInstrucao = null;
    private ArrayList<Combos> arrComboCorRaca = null;
    private ArrayList<Combos> arrComboSexo = null;
    private ArrayList<Combos> arrComboGrupoSanguineo = null;
    private ArrayList<Combos> arrComboGrupoSanguineoRH = null;
    private ArrayList<Combos> arrComboOrgaoExpeditorEstado = null;
    private ArrayList<Combos> arrComboNaturalidade = null;
    private ArrayList<Combos> arrComboDeficienciaFisica = null;
    private ArrayList<Combos> arrComboDoencaCronica = null;
    private ArrayList<Combos> arrComboRepresentanteLegal = null;
    private ArrayList<Combos> arrComboAcompanhaDoente = null;
    private ArrayList<Combos> arrComboSerieEstado = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup vwgGrupo, @Nullable Bundle bundle)
    {
        View viewTela = null;

        try
        {
            //Infla o fragmento na view
            viewTela = inflater.inflate(R.layout.frg_atualizacao_cad_documentos, vwgGrupo, false);

            //chama o método para iniciar o fluxo de dados da classe
            iniciaFluxoDados(viewTela);
        }
        catch (Exception err)
        {
            LogTrace.escreve(Apoio.localErro(getClass(), err), Apoio.getPathLogsSDCard(getActivity()), Apoio.getArqErr());
            DialogAlerta.show(getActivity(), Apoio.getMsgErr(getClass(), err), getString(R.string.atencao), getString(R.string.ok));
        }

        return viewTela;
    }

    @Override
    public void iniciaControles(View viewTela) throws Exception
    {
        TextWatcher textWatcherData = null;

        //Carrega os controles da classe
        txtNome = (EditText) viewTela.findViewById(R.id.txtNome);
        txtMae = (EditText) viewTela.findViewById(R.id.txtMae);
        txtPai = (EditText) viewTela.findViewById(R.id.txtPai);
        txtDataNascimento = (EditText) viewTela.findViewById(R.id.txtDataNascimento);
        txtNacionalidade = (EditText) viewTela.findViewById(R.id.txtNacionalidade);
        txtRG = (EditText) viewTela.findViewById(R.id.txtRG);
        txtRGDigito = (EditText) viewTela.findViewById(R.id.txtRGDigito);
        txtOrgaoExpeditor = (EditText) viewTela.findViewById(R.id.txtOrgaoExpeditor);
        txtDataExpedicao = (EditText) viewTela.findViewById(R.id.txtExpedicao);
        txtTituloEleitoral = (EditText) viewTela.findViewById(R.id.txtTituloEleitoral);
        txtTituloEleitoralDigito = (EditText) viewTela.findViewById(R.id.txtTituloEleitoralDigito);
        txtZonaEleitoral = (EditText) viewTela.findViewById(R.id.txtZona);
        txtSecao = (EditText) viewTela.findViewById(R.id.txtSecao);
        txtDataTituloEleitoral = (EditText) viewTela.findViewById(R.id.txtDataTituloEleitoral);
        txtCarteiraDeTrabalho = (EditText) viewTela.findViewById(R.id.txtCarteiraDeTrabalho);
        txtSerie = (EditText) viewTela.findViewById(R.id.txtSerie);
        txtDataCarteiraDeTrabalho = (EditText) viewTela.findViewById(R.id.txtDataCarteiraDeTrabalho);
        txtCarteiraDeHabilitacao = (EditText) viewTela.findViewById(R.id.txtCarteiraDeHabilitacao);
        txtCategoria = (EditText) viewTela.findViewById(R.id.txtCategoria);
        txtValidade = (EditText) viewTela.findViewById(R.id.txtValidade);
        txtPis = (EditText) viewTela.findViewById(R.id.txtPis);
        txtDeficienciaFisica = (EditText) viewTela.findViewById(R.id.txtDeficienciaFisica);
        txtDoencaCronica = (EditText) viewTela.findViewById(R.id.txtDoencaCronica);
        txtNome = (EditText) viewTela.findViewById(R.id.txtNome);
        txtMae = (EditText) viewTela.findViewById(R.id.txtMae);
        txtDataNascimento = (EditText) viewTela.findViewById(R.id.txtDataNascimento);
        txtNacionalidade = (EditText) viewTela.findViewById(R.id.txtNacionalidade);
        lblErroSexo = (TextView) viewTela.findViewById(R.id.lblErroSexo);
        lblErroCor = (TextView) viewTela.findViewById(R.id.lblErroCor);
        lblErroNaturalidade = (TextView) viewTela.findViewById(R.id.lblErroNaturalidade);
        lblErroEstadoCivil = (TextView) viewTela.findViewById(R.id.lblErroEstadoCivil);
        lblErroGrauDeInstrucao = (TextView) viewTela.findViewById(R.id.lblErroGrauDeInstrucao);
        lblErroDeficienciaFisica = (TextView) viewTela.findViewById(R.id.lblErroDeficienciaFisica);
        lblErroDoencaCronica = (TextView) viewTela.findViewById(R.id.lblErroDoencaCronica);
        lblErroRepresentanteLegal = (TextView) viewTela.findViewById(R.id.lblErroRepresentanteLegal);
        lblErroAcompanhaDoente = (TextView) viewTela.findViewById(R.id.lblErroAcompanhaDoente);
        inputNome = (TextInputLayout) viewTela.findViewById(R.id.inputNome);
        inputMae = (TextInputLayout) viewTela.findViewById(R.id.inputMae);
        inputDataNascimento = (TextInputLayout) viewTela.findViewById(R.id.inputDataNascimento);
        inputNacionalidade = (TextInputLayout) viewTela.findViewById(R.id.inputNacionalidade);
        inputRG = (TextInputLayout) viewTela.findViewById(R.id.inputRG);
        inputRGDigito = (TextInputLayout) viewTela.findViewById(R.id.inputRGDigito);
        inputTituloEleitoral = (TextInputLayout) viewTela.findViewById(R.id.inputTituloEleitoral);
        inputPis = (TextInputLayout) viewTela.findViewById(R.id.inputPis);
        inputDeficienciaFisica = (TextInputLayout) viewTela.findViewById(R.id.inputDeficienciaFisica);
        inputDoencaCronica = (TextInputLayout) viewTela.findViewById(R.id.inputDoencaCronica);
        cboSexo = (Spinner) viewTela.findViewById(R.id.cboSexo);
        cboCor = (Spinner) viewTela.findViewById(R.id.cboCor);
        cboGrupoSanguineo = (Spinner) viewTela.findViewById(R.id.cboGrupoSanguineo);
        cboGrupoSanguineoRH = (Spinner) viewTela.findViewById(R.id.cboGrupoSanguineoRH);
        cboNaturalidade = (Spinner) viewTela.findViewById(R.id.cboNaturalidade);
        cboEstadoCivil = (Spinner) viewTela.findViewById(R.id.cboEstadoCivil);
        cboGrauDeInstrucao = (Spinner) viewTela.findViewById(R.id.cboGrauDeInstrucao);
        cboOrgaoExpeditorEstado = (Spinner) viewTela.findViewById(R.id.cboOrgaoExpeditorEstado);
        cboSerieEstado = (Spinner) viewTela.findViewById(R.id.cboSerieEstado);
        cboDeficienciaFisica = (Spinner) viewTela.findViewById(R.id.cboDeficiencia);
        cboDoencaCronica = (Spinner) viewTela.findViewById(R.id.cboDoencaCronica);
        cboRepresentanteLegal = (Spinner) viewTela.findViewById(R.id.cboRepresentanteLegal);
        cboAcompanhaDoente = (Spinner) viewTela.findViewById(R.id.cboPessoaDoente);

        //coloca a mascara nos campos
        textWatcherData = MascaraCampos.insert("##/##/####", txtDataNascimento);
        txtDataNascimento.addTextChangedListener(textWatcherData);
        textWatcherData = MascaraCampos.insert("##/##/####", txtDataExpedicao);
        txtDataExpedicao.addTextChangedListener(textWatcherData);
        textWatcherData = MascaraCampos.insert("##/##/####", txtDataTituloEleitoral);
        txtDataTituloEleitoral.addTextChangedListener(textWatcherData);
        textWatcherData = MascaraCampos.insert("##/##/####", txtDataCarteiraDeTrabalho);
        txtDataCarteiraDeTrabalho.addTextChangedListener(textWatcherData);
        textWatcherData = MascaraCampos.insert("##/##/####", txtValidade);
        txtValidade.addTextChangedListener(textWatcherData);

        //Esconde o label de erro
        lblErroSexo.setVisibility(View.GONE);
        lblErroCor.setVisibility(View.GONE);
        lblErroEstadoCivil.setVisibility(View.GONE);
        lblErroGrauDeInstrucao.setVisibility(View.GONE);
        lblErroNaturalidade.setVisibility(View.GONE);
        lblErroDeficienciaFisica.setVisibility(View.GONE);
        lblErroDoencaCronica.setVisibility(View.GONE);
        lblErroRepresentanteLegal.setVisibility(View.GONE);
        lblErroAcompanhaDoente.setVisibility(View.GONE);
    }

    @Override
    public void carregaDados(View viewTela) throws Exception
    {
        //Preenche combo de estado civil
        preencheComboEstadoCivil();

        //Preenche combo de grau de instrução
        preencheComboGrauInstrucao();

        //Preenche combo cor/raça
        preencheComboCorRaca();

        //Preenche o combo de sexo
        preencheComboSexo();

        //Preenche o combo de grupo sanguineo
        preencheComboGrupoSenguineo();

        //Preenche o combo de grupo sanguineo RH (fator)
        preencheComboGrupoSenguineoRH();

        //Preenche combo de orgão expeditor estado
        preencheComboOrgaoExpeditorEstado();

        //Preenche o combo de naturalidade
        preencheComboNaturalidade();

        //Preenche o combo de deficiência física
        preencheComboDeficienciaFisica();

        //Preenche o combo de doença crônica
        preencheComboDoencaCronica();

        //Preenche o combo de representante legal
        preencheComboRepresentanteLegal();

        //Preenche o combo de acompanha doente
        preencheComboAcompanhaDoente();

        //Preenche o combo de serie/estado
        preencheComboSerieEstado();
    }

    /**
     * Preenche o combo de estado civil
     */
    private void preencheComboEstadoCivil() throws Exception
    {
        ArrayAdapter<Combos> dataAdapter = null;

        //Carrega os dados
        arrComboEstadoCivil = new ArrayList<Combos>();
        arrComboEstadoCivil.add(new Combos("", ""));
        arrComboEstadoCivil.add(new Combos("0", "NÃO INFORMADO"));
        arrComboEstadoCivil.add(new Combos("1", "SOLTEIRO"));
        arrComboEstadoCivil.add(new Combos("2", "CASADO"));
        arrComboEstadoCivil.add(new Combos("3", "VIÚVO"));
        arrComboEstadoCivil.add(new Combos("4", "SEPARADA JUDICIALMENTE"));
        arrComboEstadoCivil.add(new Combos("5", "DIVORCIADO"));
        arrComboEstadoCivil.add(new Combos("6", "UNIÃO ESTÁVEL"));

        //Cria o adapter
        dataAdapter = new ArrayAdapter<Combos>(this.getActivity(), android.R.layout.simple_spinner_item, arrComboEstadoCivil);

        //Seta o adapter no dropdown
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //Atacha o adapter ao controle
        cboEstadoCivil.setAdapter(dataAdapter);
    }

    /**
     * Preenche o combo de grau de instrução
     */
    private void preencheComboGrauInstrucao() throws Exception
    {
        ArrayAdapter<Combos> dataAdapter = null;

        //Carrega os dados
        arrComboGrauInstrucao = new ArrayList<Combos>();
        arrComboGrauInstrucao.add(new Combos("", ""));
        arrComboGrauInstrucao.add(new Combos("0", "NÃO INFORMADO"));
        arrComboGrauInstrucao.add(new Combos("1", "ANALFABETO"));
        arrComboGrauInstrucao.add(new Combos("2", "ALFABETIZADO SEM CURSOS REGULARES"));
        arrComboGrauInstrucao.add(new Combos("3", "FUNDAMENTAL INCOMPLETO"));
        arrComboGrauInstrucao.add(new Combos("4", "FUNDAMENTAL COMPLETO"));
        arrComboGrauInstrucao.add(new Combos("5", "MÉDIO INCOMPLETO"));
        arrComboGrauInstrucao.add(new Combos("6", "MÉDIO COMPLETO"));
        arrComboGrauInstrucao.add(new Combos("7", "SUPERIOR INCOMPLETO"));
        arrComboGrauInstrucao.add(new Combos("8", "SUPERIOR COMPLETO OU EQUIVALENTE LEGAL"));
        arrComboGrauInstrucao.add(new Combos("9", "ESPECIALIZAÇÃO/PÓS-GRADUAÇÃO"));
        arrComboGrauInstrucao.add(new Combos("10", "MESTRADO"));
        arrComboGrauInstrucao.add(new Combos("11", "DOUTORADO"));

        //Cria o adapter
        dataAdapter = new ArrayAdapter<Combos>(this.getActivity(), android.R.layout.simple_spinner_item, arrComboGrauInstrucao);

        //Seta o adapter no dropdown
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //Atacha o adapter ao controle
        cboGrauDeInstrucao.setAdapter(dataAdapter);
    }

    /**
     * Preenche o combo de cor/raça
     */
    private void preencheComboCorRaca() throws Exception
    {
        ArrayAdapter<Combos> dataAdapter = null;

        //Carrega os dados
        arrComboCorRaca = new ArrayList<Combos>();
        arrComboCorRaca.add(new Combos("", ""));
        arrComboCorRaca.add(new Combos("1", "BRANCA"));
        arrComboCorRaca.add(new Combos("2", "PRETA"));
        arrComboCorRaca.add(new Combos("3", "AMARELA"));
        arrComboCorRaca.add(new Combos("4", "PARDA"));
        arrComboCorRaca.add(new Combos("5", "INDÍGENA"));
        arrComboCorRaca.add(new Combos("6", "IGNORADO"));
        arrComboCorRaca.add(new Combos("7", "MAMELUCO"));

        //Cria o adapter
        dataAdapter = new ArrayAdapter<Combos>(this.getActivity(), android.R.layout.simple_spinner_item, arrComboCorRaca);

        //Seta o adapter no dropdown
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //Atacha o adapter ao controle
        cboCor.setAdapter(dataAdapter);
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
        dataAdapter = new ArrayAdapter<Combos>(this.getActivity(), android.R.layout.simple_spinner_item, arrComboSexo);

        //Seta o adapter no dropdown
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //Atacha o adapter ao controle
        cboSexo.setAdapter(dataAdapter);
    }

    /**
     * Preenche o combo de grupo sanguineo
     */
    private void preencheComboGrupoSenguineo() throws Exception
    {
        ArrayAdapter<Combos> dataAdapter = null;

        //Carrega os dados
        arrComboGrupoSanguineo = new ArrayList<Combos>();
        arrComboGrupoSanguineo.add(new Combos("", ""));
        arrComboGrupoSanguineo.add(new Combos("1", "A"));
        arrComboGrupoSanguineo.add(new Combos("2", "B"));
        arrComboGrupoSanguineo.add(new Combos("3", "AB"));
        arrComboGrupoSanguineo.add(new Combos("4", "O"));

        //Cria o adapter
        dataAdapter = new ArrayAdapter<Combos>(this.getActivity(), android.R.layout.simple_spinner_item, arrComboGrupoSanguineo);

        //Seta o adapter no dropdown
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //Atacha o adapter ao controle
        cboGrupoSanguineo.setAdapter(dataAdapter);
    }

    /**
     * Preenche o combo de grupo sanguineo RH
     */
    private void preencheComboGrupoSenguineoRH() throws Exception
    {
        ArrayAdapter<Combos> dataAdapter = null;

        //Carrega os dados
        arrComboGrupoSanguineoRH = new ArrayList<Combos>();
        arrComboGrupoSanguineoRH.add(new Combos("", ""));
        arrComboGrupoSanguineoRH.add(new Combos("1", "POSITIVO"));
        arrComboGrupoSanguineoRH.add(new Combos("2", "NEGATIVO"));

        //Cria o adapter
        dataAdapter = new ArrayAdapter<Combos>(this.getActivity(), android.R.layout.simple_spinner_item, arrComboGrupoSanguineoRH);

        //Seta o adapter no dropdown
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //Atacha o adapter ao controle
        cboGrupoSanguineoRH.setAdapter(dataAdapter);
    }

    /**
     * Preenche o combo de orgão expeditor estado
     */
    private void preencheComboOrgaoExpeditorEstado() throws Exception
    {
        ArrayAdapter<Combos> dataAdapter = null;

        //Carrega os dados
        arrComboOrgaoExpeditorEstado = new ArrayList<Combos>();
        arrComboOrgaoExpeditorEstado.add(new Combos("", ""));
        arrComboOrgaoExpeditorEstado.add(new Combos("AC", "ACRE"));
        arrComboOrgaoExpeditorEstado.add(new Combos("AL", "ALAGOAS"));
        arrComboOrgaoExpeditorEstado.add(new Combos("AM", "AMAZONAS"));
        arrComboOrgaoExpeditorEstado.add(new Combos("AP", "AMAPÁ"));
        arrComboOrgaoExpeditorEstado.add(new Combos("BA", "BAHIA"));
        arrComboOrgaoExpeditorEstado.add(new Combos("CE", "CEÁRA"));
        arrComboOrgaoExpeditorEstado.add(new Combos("DF", "DISTRITO FEDERAL"));
        arrComboOrgaoExpeditorEstado.add(new Combos("ES", "ESPÍRITO SANTO"));
        arrComboOrgaoExpeditorEstado.add(new Combos("GO", "GOÍAS"));
        arrComboOrgaoExpeditorEstado.add(new Combos("MA", "MARANHÃO"));
        arrComboOrgaoExpeditorEstado.add(new Combos("MG", "MINAS GERAIS"));
        arrComboOrgaoExpeditorEstado.add(new Combos("MS", "MATO GROSSO DO SUL"));
        arrComboOrgaoExpeditorEstado.add(new Combos("MT", "MATO GROSSO"));
        arrComboOrgaoExpeditorEstado.add(new Combos("PA", "PARÁ"));
        arrComboOrgaoExpeditorEstado.add(new Combos("PB", "PARAÍBA"));
        arrComboOrgaoExpeditorEstado.add(new Combos("PE", "PERNAMBUCO"));
        arrComboOrgaoExpeditorEstado.add(new Combos("PI", "PIAUÍ"));
        arrComboOrgaoExpeditorEstado.add(new Combos("PR", "PARANÁ"));
        arrComboOrgaoExpeditorEstado.add(new Combos("RJ", "RIO DE JANEIRO"));
        arrComboOrgaoExpeditorEstado.add(new Combos("RN", "RIO GRANDE DO NORTE"));
        arrComboOrgaoExpeditorEstado.add(new Combos("RO", "RONDÔNIA"));
        arrComboOrgaoExpeditorEstado.add(new Combos("RR", "RORAIMA"));
        arrComboOrgaoExpeditorEstado.add(new Combos("RS", "RIO GRANDE DO SUL"));
        arrComboOrgaoExpeditorEstado.add(new Combos("SC", "SANTA CATARINA"));
        arrComboOrgaoExpeditorEstado.add(new Combos("SE", "SERGIPE"));
        arrComboOrgaoExpeditorEstado.add(new Combos("SP", "SÃO PAULO"));
        arrComboOrgaoExpeditorEstado.add(new Combos("TO", "TOCANTINS"));

        //Cria o adapter
        dataAdapter = new ArrayAdapter<Combos>(this.getActivity(), android.R.layout.simple_spinner_item, arrComboOrgaoExpeditorEstado);

        //Seta o adapter no dropdown
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //Atacha o adapter ao controle
        cboOrgaoExpeditorEstado.setAdapter(dataAdapter);
    }

    /**
     * Preenche o combo de naturalidade
     */
    private void preencheComboNaturalidade() throws Exception
    {
        ArrayAdapter<Combos> dataAdapter = null;

        //Carrega os dados
        arrComboNaturalidade = new ArrayList<Combos>();
        arrComboNaturalidade.add(new Combos("", ""));
        arrComboNaturalidade.add(new Combos("AC", "ACRE"));
        arrComboNaturalidade.add(new Combos("AL", "ALAGOAS"));
        arrComboNaturalidade.add(new Combos("AM", "AMAZONAS"));
        arrComboNaturalidade.add(new Combos("AP", "AMAPÁ"));
        arrComboNaturalidade.add(new Combos("BA", "BAHIA"));
        arrComboNaturalidade.add(new Combos("CE", "CEÁRA"));
        arrComboNaturalidade.add(new Combos("DF", "DISTRITO FEDERAL"));
        arrComboNaturalidade.add(new Combos("ES", "ESPÍRITO SANTO"));
        arrComboNaturalidade.add(new Combos("GO", "GOÍAS"));
        arrComboNaturalidade.add(new Combos("MA", "MARANHÃO"));
        arrComboNaturalidade.add(new Combos("MG", "MINAS GERAIS"));
        arrComboNaturalidade.add(new Combos("MS", "MATO GROSSO DO SUL"));
        arrComboNaturalidade.add(new Combos("MT", "MATO GROSSO"));
        arrComboNaturalidade.add(new Combos("PA", "PARÁ"));
        arrComboNaturalidade.add(new Combos("PB", "PARAÍBA"));
        arrComboNaturalidade.add(new Combos("PE", "PERNAMBUCO"));
        arrComboNaturalidade.add(new Combos("PI", "PIAUÍ"));
        arrComboNaturalidade.add(new Combos("PR", "PARANÁ"));
        arrComboNaturalidade.add(new Combos("RJ", "RIO DE JANEIRO"));
        arrComboNaturalidade.add(new Combos("RN", "RIO GRANDE DO NORTE"));
        arrComboNaturalidade.add(new Combos("RO", "RONDÔNIA"));
        arrComboNaturalidade.add(new Combos("RR", "RORAIMA"));
        arrComboNaturalidade.add(new Combos("RS", "RIO GRANDE DO SUL"));
        arrComboNaturalidade.add(new Combos("SC", "SANTA CATARINA"));
        arrComboNaturalidade.add(new Combos("SE", "SERGIPE"));
        arrComboNaturalidade.add(new Combos("SP", "SÃO PAULO"));
        arrComboNaturalidade.add(new Combos("TO", "TOCANTINS"));

        //Cria o adapter
        dataAdapter = new ArrayAdapter<Combos>(this.getActivity(), android.R.layout.simple_spinner_item, arrComboNaturalidade);

        //Seta o adapter no dropdown
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //Atacha o adapter ao controle
        cboNaturalidade.setAdapter(dataAdapter);
    }

    /**
     * Preenche o combo de deficiência física
     */
    private void preencheComboDeficienciaFisica() throws Exception
    {
        ArrayAdapter<Combos> dataAdapter = null;

        //Carrega os dados
        arrComboDeficienciaFisica = new ArrayList<Combos>();
        arrComboDeficienciaFisica.add(new Combos("", ""));
        arrComboDeficienciaFisica.add(new Combos("N", "NÃO"));
        arrComboDeficienciaFisica.add(new Combos("S", "SIM"));

        //Cria o adapter
        dataAdapter = new ArrayAdapter<Combos>(this.getActivity(), android.R.layout.simple_spinner_item, arrComboDeficienciaFisica);

        //Seta o adapter no dropdown
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //Atacha o adapter ao controle
        cboDeficienciaFisica.setAdapter(dataAdapter);
    }

    /**
     * Preenche o combo de doença crônica
     */
    private void preencheComboDoencaCronica() throws Exception
    {
        ArrayAdapter<Combos> dataAdapter = null;

        //Carrega os dados
        arrComboDoencaCronica = new ArrayList<Combos>();
        arrComboDoencaCronica.add(new Combos("", ""));
        arrComboDoencaCronica.add(new Combos("N", "NÃO"));
        arrComboDoencaCronica.add(new Combos("S", "SIM"));

        //Cria o adapter
        dataAdapter = new ArrayAdapter<Combos>(this.getActivity(), android.R.layout.simple_spinner_item, arrComboDoencaCronica);

        //Seta o adapter no dropdown
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //Atacha o adapter ao controle
        cboDoencaCronica.setAdapter(dataAdapter);
    }

    /**
     * Preenche o combo de representante legal
     */
    private void preencheComboRepresentanteLegal() throws Exception
    {
        ArrayAdapter<Combos> dataAdapter = null;

        //Carrega os dados
        arrComboRepresentanteLegal = new ArrayList<Combos>();
        arrComboRepresentanteLegal.add(new Combos("", ""));
        arrComboRepresentanteLegal.add(new Combos("N", "NÃO"));
        arrComboRepresentanteLegal.add(new Combos("S", "SIM"));

        //Cria o adapter
        dataAdapter = new ArrayAdapter<Combos>(this.getActivity(), android.R.layout.simple_spinner_item, arrComboRepresentanteLegal);

        //Seta o adapter no dropdown
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //Atacha o adapter ao controle
        cboRepresentanteLegal.setAdapter(dataAdapter);
    }

    /**
     * Preenche o combo de acompanha doente
     */
    private void preencheComboAcompanhaDoente() throws Exception
    {
        ArrayAdapter<Combos> dataAdapter = null;

        //Carrega os dados
        arrComboAcompanhaDoente = new ArrayList<Combos>();
        arrComboAcompanhaDoente.add(new Combos("", ""));
        arrComboAcompanhaDoente.add(new Combos("N", "NÃO"));
        arrComboAcompanhaDoente.add(new Combos("S", "SIM"));

        //Cria o adapter
        dataAdapter = new ArrayAdapter<Combos>(this.getActivity(), android.R.layout.simple_spinner_item, arrComboAcompanhaDoente);

        //Seta o adapter no dropdown
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //Atacha o adapter ao controle
        cboAcompanhaDoente.setAdapter(dataAdapter);
    }

    /**
     * Preenche o combo de serie/estado
     */
    private void preencheComboSerieEstado() throws Exception
    {
        ArrayAdapter<Combos> dataAdapter = null;

        //Carrega os dados
        arrComboSerieEstado = new ArrayList<Combos>();
        arrComboSerieEstado.add(new Combos("", ""));
        arrComboSerieEstado.add(new Combos("AC", "ACRE"));
        arrComboSerieEstado.add(new Combos("AL", "ALAGOAS"));
        arrComboSerieEstado.add(new Combos("AM", "AMAZONAS"));
        arrComboSerieEstado.add(new Combos("AP", "AMAPÁ"));
        arrComboSerieEstado.add(new Combos("BA", "BAHIA"));
        arrComboSerieEstado.add(new Combos("CE", "CEÁRA"));
        arrComboSerieEstado.add(new Combos("DF", "DISTRITO FEDERAL"));
        arrComboSerieEstado.add(new Combos("ES", "ESPÍRITO SANTO"));
        arrComboSerieEstado.add(new Combos("GO", "GOÍAS"));
        arrComboSerieEstado.add(new Combos("MA", "MARANHÃO"));
        arrComboSerieEstado.add(new Combos("MG", "MINAS GERAIS"));
        arrComboSerieEstado.add(new Combos("MS", "MATO GROSSO DO SUL"));
        arrComboSerieEstado.add(new Combos("MT", "MATO GROSSO"));
        arrComboSerieEstado.add(new Combos("PA", "PARÁ"));
        arrComboSerieEstado.add(new Combos("PB", "PARAÍBA"));
        arrComboSerieEstado.add(new Combos("PE", "PERNAMBUCO"));
        arrComboSerieEstado.add(new Combos("PI", "PIAUÍ"));
        arrComboSerieEstado.add(new Combos("PR", "PARANÁ"));
        arrComboSerieEstado.add(new Combos("RJ", "RIO DE JANEIRO"));
        arrComboSerieEstado.add(new Combos("RN", "RIO GRANDE DO NORTE"));
        arrComboSerieEstado.add(new Combos("RO", "RONDÔNIA"));
        arrComboSerieEstado.add(new Combos("RR", "RORAIMA"));
        arrComboSerieEstado.add(new Combos("RS", "RIO GRANDE DO SUL"));
        arrComboSerieEstado.add(new Combos("SC", "SANTA CATARINA"));
        arrComboSerieEstado.add(new Combos("SE", "SERGIPE"));
        arrComboSerieEstado.add(new Combos("SP", "SÃO PAULO"));
        arrComboSerieEstado.add(new Combos("TO", "TOCANTINS"));

        //Cria o adapter
        dataAdapter = new ArrayAdapter<Combos>(this.getActivity(), android.R.layout.simple_spinner_item, arrComboSerieEstado);

        //Seta o adapter no dropdown
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //Atacha o adapter ao controle
        cboSerieEstado.setAdapter(dataAdapter);
    }

    /**
     * Remove os erros dos campos da tela
     */
    public void removerErroDosCampos() throws Exception
    {
        //Tira o erro
        inputNome.setError("");
        inputNome.setErrorEnabled(false);
        inputMae.setError("");
        inputMae.setErrorEnabled(false);
        inputDataNascimento.setError("");
        inputDataNascimento.setErrorEnabled(false);
        inputNacionalidade.setError("");
        inputNacionalidade.setErrorEnabled(false);
        inputRG.setError("");
        inputRG.setErrorEnabled(false);
        inputTituloEleitoral.setError("");
        inputTituloEleitoral.setErrorEnabled(false);
        inputPis.setError("");
        inputPis.setErrorEnabled(false);
        inputDeficienciaFisica.setError("");
        inputDeficienciaFisica.setErrorEnabled(false);
        inputDoencaCronica.setError("");
        inputDoencaCronica.setErrorEnabled(false);
        lblErroSexo.setVisibility(View.GONE);
        lblErroCor.setVisibility(View.GONE);
        lblErroEstadoCivil.setVisibility(View.GONE);
        lblErroGrauDeInstrucao.setVisibility(View.GONE);
        lblErroNaturalidade.setVisibility(View.GONE);
        lblErroDeficienciaFisica.setVisibility(View.GONE);
        lblErroDoencaCronica.setVisibility(View.GONE);
        lblErroRepresentanteLegal.setVisibility(View.GONE);
        lblErroAcompanhaDoente.setVisibility(View.GONE);
    }

    /**
     * Valida os campos da tela
     */
    public boolean validaCampos(AppCompatActivity activity, ViewPager viewPager) throws Exception
    {
        String sData = "";
        Combos comboDeficienciaFisica = null;
        Combos comboDoencaCronica = null;

        //Valida o nome
        if (txtNome.getText().toString().trim().length() <= 0)
        {
            //Coloca a mensagem de erro embaixo do campo e solicita o foco
            inputNome.setError(getString(R.string.msg_atualizacao_cad_documentos_nome_em_branco));
            inputNome.setErrorEnabled(true);
            viewPager.setCurrentItem(0, true);
            Apoio.requisitarFoco(txtNome, activity);
            return false;
        }
        else
        {
            //Se estier OK, remove o erro
            inputNome.setError("");
            inputNome.setErrorEnabled(false);
        }

        //Valida a mãe
        if (txtMae.getText().toString().trim().length() <= 0)
        {
            //Coloca a mensagem de erro embaixo do campo e solicita o foco
            inputMae.setError(getString(R.string.msg_atualizacao_cad_documentos_mae_em_branco));
            inputMae.setErrorEnabled(true);
            viewPager.setCurrentItem(0, true);
            Apoio.requisitarFoco(txtMae, activity);
            return false;
        }
        else
        {
            //Se estier OK, remove o erro
            inputMae.setError("");
            inputMae.setErrorEnabled(false);
        }

        //Valida o combo de sexo
        if (cboSexo.getSelectedItemPosition() <= 0)
        {
            //Coloca a mensagem de erro embaixo do campo e solicita o foco
            lblErroSexo.setText(getString(R.string.msg_atualizacao_cad_documentos_sexo_nao_selecionado));
            lblErroSexo.setVisibility(View.VISIBLE);
            viewPager.setCurrentItem(0, true);
            Apoio.requisitarFoco(cboSexo, activity);
            return false;
        }
        else
        {
            //Se estier OK, remove o erro
            lblErroSexo.setText("");
            lblErroSexo.setVisibility(View.GONE);
        }

        //Valida o combo de cor/raça
        if (cboCor.getSelectedItemPosition() <= 0)
        {
            //Coloca a mensagem de erro embaixo do campo e solicita o foco
            lblErroCor.setText(getString(R.string.msg_atualizacao_cad_documentos_cor_raca_nao_selecionado));
            lblErroCor.setVisibility(View.VISIBLE);
            viewPager.setCurrentItem(0, true);
            Apoio.requisitarFoco(cboCor, activity);
            return false;
        }
        else
        {
            //Se estier OK, remove o erro
            lblErroCor.setText("");
            lblErroCor.setVisibility(View.GONE);
        }

        //Obtem a data
        sData = txtDataNascimento.getText().toString().trim();

        //Valida o campo de data de nascimento
        if (sData.length() <= 0)
        {
            //Coloca a mensagem de erro embaixo do campo e solicita o foco
            inputDataNascimento.setError(getString(R.string.msg_atualizacao_cad_documentos_data_de_nascimento_em_branco));
            inputDataNascimento.setErrorEnabled(true);
            viewPager.setCurrentItem(0, true);
            Apoio.requisitarFoco(txtDataNascimento, activity);
            return false;
        }
        else
        {
            //Se estier OK, remove o erro
            inputDataNascimento.setError("");
            inputDataNascimento.setErrorEnabled(false);
        }

        //verifica se a data é valida
        if ( Apoio.parseData(sData, "dd/MM/yyyy", "yyyy-MM-dd").equals("") )
        {
            //Coloca a mensagem de erro embaixo do campo e solicita o foco
            inputDataNascimento.setError(getString(R.string.msg_atualizacao_cad_documentos_data_de_nascimento_invalida));
            inputDataNascimento.setErrorEnabled(true);
            viewPager.setCurrentItem(0, true);
            Apoio.requisitarFoco(txtDataNascimento, activity);
            return false;
        }
        else
        {
            //Se estier OK, remove o erro
            inputDataNascimento.setError("");
            inputDataNascimento.setErrorEnabled(false);
        }

        //Valida a nacionalidade
        if (txtNacionalidade.getText().toString().trim().length() <= 0)
        {
            //Coloca a mensagem de erro embaixo do campo e solicita o foco
            inputNacionalidade.setError(getString(R.string.msg_atualizacao_cad_documentos_nacionalidade_em_branco));
            inputNacionalidade.setErrorEnabled(true);
            viewPager.setCurrentItem(0, true);
            Apoio.requisitarFoco(txtNacionalidade, activity);
            return false;
        }
        else
        {
            //Se estier OK, remove o erro
            inputNacionalidade.setError("");
            inputNacionalidade.setErrorEnabled(false);
        }

        //Valida o combo de naturalidade
        if (cboNaturalidade.getSelectedItemPosition() <= 0)
        {
            //Coloca a mensagem de erro embaixo do campo e solicita o foco
            lblErroNaturalidade.setText(getString(R.string.msg_atualizacao_cad_documentos_naturalidade_nao_selecionado));
            lblErroNaturalidade.setVisibility(View.VISIBLE);
            viewPager.setCurrentItem(0, true);
            Apoio.requisitarFoco(cboNaturalidade, activity);
            return false;
        }
        else
        {
            //Se estier OK, remove o erro
            lblErroNaturalidade.setText("");
            lblErroNaturalidade.setVisibility(View.GONE);
        }

        //Valida o combo de estado civil
        if (cboEstadoCivil.getSelectedItemPosition() <= 0)
        {
            //Coloca a mensagem de erro embaixo do campo e solicita o foco
            lblErroEstadoCivil.setText(getString(R.string.msg_atualizacao_cad_documentos_estado_civil_nao_selecionado));
            lblErroEstadoCivil.setVisibility(View.VISIBLE);
            viewPager.setCurrentItem(0, true);
            Apoio.requisitarFoco(cboEstadoCivil, activity);
            return false;
        }
        else
        {
            //Se estier OK, remove o erro
            lblErroEstadoCivil.setText("");
            lblErroEstadoCivil.setVisibility(View.GONE);
        }

        //Valida o combo de grau de instrução
        if (cboGrauDeInstrucao.getSelectedItemPosition() <= 0)
        {
            //Coloca a mensagem de erro embaixo do campo e solicita o foco
            lblErroGrauDeInstrucao.setText(getString(R.string.msg_atualizacao_cad_documentos_grau_instrucao_nao_selecionado));
            lblErroGrauDeInstrucao.setVisibility(View.VISIBLE);
            viewPager.setCurrentItem(0, true);
            Apoio.requisitarFoco(cboGrauDeInstrucao, activity);
            return false;
        }
        else
        {
            //Se estier OK, remove o erro
            lblErroGrauDeInstrucao.setText("");
            lblErroGrauDeInstrucao.setVisibility(View.GONE);
        }

        //Valida o RG
        if (txtRG.getText().toString().trim().length() <= 0)
        {
            //Coloca a mensagem de erro embaixo do campo e solicita o foco
            inputRG.setError(getString(R.string.msg_atualizacao_cad_documentos_rg_em_branco));
            inputRG.setErrorEnabled(true);
            viewPager.setCurrentItem(0, true);
            Apoio.requisitarFoco(txtRG, activity);
            return false;
        }
        else
        {
            //Se estier OK, remove o erro
            inputRG.setError("");
            inputRG.setErrorEnabled(false);
        }

        //Valida o titulo eleitoral
        if (txtTituloEleitoral.getText().toString().trim().length() <= 0)
        {
            //Coloca a mensagem de erro embaixo do campo e solicita o foco
            inputTituloEleitoral.setError(getString(R.string.msg_atualizacao_cad_documentos_titulo_eleitoral_em_branco));
            inputTituloEleitoral.setErrorEnabled(true);
            viewPager.setCurrentItem(0, true);
            Apoio.requisitarFoco(txtTituloEleitoral, activity);
            return false;
        }
        else
        {
            //Se estier OK, remove o erro
            inputTituloEleitoral.setError("");
            inputTituloEleitoral.setErrorEnabled(false);
        }

        //Valida o PIS/PASEP
        if (txtPis.getText().toString().trim().length() <= 0)
        {
            //Coloca a mensagem de erro embaixo do campo e solicita o foco
            inputPis.setError(getString(R.string.msg_atualizacao_cad_documentos_pis_pasep_em_branco));
            inputPis.setErrorEnabled(true);
            viewPager.setCurrentItem(0, true);
            Apoio.requisitarFoco(txtPis, activity);
            return false;
        }
        else
        {
            //Se estier OK, remove o erro
            inputPis.setError("");
            inputPis.setErrorEnabled(false);
        }

        //Valida o combo de deficiência física
        if (cboDeficienciaFisica.getSelectedItemPosition() <= 0)
        {
            //Coloca a mensagem de erro embaixo do campo e solicita o foco
            lblErroDeficienciaFisica.setText(getString(R.string.msg_atualizacao_cad_documentos_deficiencia_fisica_nao_selecionado));
            lblErroDeficienciaFisica.setVisibility(View.VISIBLE);
            viewPager.setCurrentItem(0, true);
            Apoio.requisitarFoco(cboDeficienciaFisica, activity);
            return false;
        }
        else
        {
            //Se estier OK, remove o erro
            lblErroDeficienciaFisica.setText("");
            lblErroDeficienciaFisica.setVisibility(View.GONE);
        }

        //Valida o combo de deficiência física
        if (cboDeficienciaFisica.getSelectedItemPosition() > 0)
        {
            //Obtem o item
            comboDeficienciaFisica = arrComboDeficienciaFisica.get(cboDeficienciaFisica.getSelectedItemPosition());

            //Se for Sim
            if ( comboDeficienciaFisica.sCodigo.equals("S") )
            {
                //Valida a deficiencia fisica
                if (txtDeficienciaFisica.getText().toString().trim().length() <= 0)
                {
                    //Coloca a mensagem de erro embaixo do campo e solicita o foco
                    inputDeficienciaFisica.setError(getString(R.string.msg_atualizacao_cad_documentos_deficiencia_fisica_em_branco));
                    inputDeficienciaFisica.setErrorEnabled(true);
                    viewPager.setCurrentItem(0, true);
                    Apoio.requisitarFoco(txtDeficienciaFisica, activity);
                    return false;
                }
                else
                {
                    //Se estier OK, remove o erro
                    inputDeficienciaFisica.setError("");
                    inputDeficienciaFisica.setErrorEnabled(false);
                }
            }
        }

        //Valida o combo de doença crônica
        if (cboDoencaCronica.getSelectedItemPosition() <= 0)
        {
            //Coloca a mensagem de erro embaixo do campo e solicita o foco
            lblErroDoencaCronica.setText(getString(R.string.msg_atualizacao_cad_documentos_doenca_cronica_nao_selecionado));
            lblErroDoencaCronica.setVisibility(View.VISIBLE);
            viewPager.setCurrentItem(0, true);
            Apoio.requisitarFoco(cboDoencaCronica, activity);
            return false;
        }
        else
        {
            //Se estier OK, remove o erro
            lblErroDoencaCronica.setText("");
            lblErroDoencaCronica.setVisibility(View.GONE);
        }

        //Valida o combo de doenca cronica
        if (cboDoencaCronica.getSelectedItemPosition() > 0)
        {
            //Obtem o item
            comboDoencaCronica = arrComboDoencaCronica.get(cboDoencaCronica.getSelectedItemPosition());

            //Se for Sim
            if (comboDoencaCronica.sCodigo.equals("S"))
            {
                //Valida a doença crônica
                if (txtDoencaCronica.getText().toString().trim().length() <= 0)
                {
                    //Coloca a mensagem de erro embaixo do campo e solicita o foco
                    inputDoencaCronica.setError(getString(R.string.msg_atualizacao_cad_documentos_doenca_cronica_em_branco));
                    inputDoencaCronica.setErrorEnabled(true);
                    viewPager.setCurrentItem(0, true);
                    Apoio.requisitarFoco(txtDoencaCronica, activity);
                    return false;
                }
                else
                {
                    //Se estier OK, remove o erro
                    inputDoencaCronica.setError("");
                    inputDoencaCronica.setErrorEnabled(false);
                }
            }
        }

        //Valida o combo de representante legal
        if (cboRepresentanteLegal.getSelectedItemPosition() <= 0)
        {
            //Coloca a mensagem de erro embaixo do campo e solicita o foco
            lblErroRepresentanteLegal.setText(getString(R.string.msg_atualizacao_cad_documentos_representante_legal_nao_selecionado));
            lblErroRepresentanteLegal.setVisibility(View.VISIBLE);
            viewPager.setCurrentItem(0, true);
            Apoio.requisitarFoco(cboRepresentanteLegal, activity);
            return false;
        }
        else
        {
            //Se estier OK, remove o erro
            lblErroRepresentanteLegal.setText("");
            lblErroRepresentanteLegal.setVisibility(View.GONE);
        }

        //Valida o combo de acompanha doente
        if (cboAcompanhaDoente.getSelectedItemPosition() <= 0)
        {
            //Coloca a mensagem de erro embaixo do campo e solicita o foco
            lblErroAcompanhaDoente.setText(getString(R.string.msg_atualizacao_cad_documentos_acompanha_doente_nao_selecionado));
            lblErroAcompanhaDoente.setVisibility(View.VISIBLE);
            viewPager.setCurrentItem(0, true);
            Apoio.requisitarFoco(cboAcompanhaDoente, activity);
            return false;
        }
        else
        {
            //Se estier OK, remove o erro
            lblErroAcompanhaDoente.setText("");
            lblErroAcompanhaDoente.setVisibility(View.GONE);
        }

        return true;
    }

    /**
     * Retorna os dados da tela
     */
    public Documentos retornaDadosTela() throws Exception
    {
        Documentos documentos = null;
        Combos combos = null;

        //Inicia a model de documentos
        documentos = new Documentos();

        //Seta os valores dos campos
        documentos.sNome = txtNome.getText().toString().trim().toUpperCase();
        documentos.sMae = txtMae.getText().toString().trim().toUpperCase();
        documentos.sPai = txtPai.getText().toString().trim().toUpperCase();
        documentos.sDataNascimento = Apoio.parseData(txtDataNascimento.getText().toString().trim(), "dd/MM/yyyy", "yyyyMMdd");
        documentos.sNacionalidade = txtNacionalidade.getText().toString().trim().toUpperCase();
        documentos.sRG = txtRG.getText().toString().trim().toUpperCase();
        documentos.sRGOrgao = txtOrgaoExpeditor.getText().toString().trim().toUpperCase();
        documentos.sRGData = Apoio.parseData(txtDataExpedicao.getText().toString().trim(), "dd/MM/yyyy", "yyyyMMdd");
        documentos.sTitulo = txtTituloEleitoral.getText().toString().trim().toUpperCase();
        documentos.sTituloDV = txtTituloEleitoralDigito.getText().toString().trim().toUpperCase();
        documentos.sTituloSecao = txtSecao.getText().toString().trim().toUpperCase();
        documentos.sTituloZona = txtZonaEleitoral.getText().toString().trim().toUpperCase();
        documentos.sTituloData = Apoio.parseData(txtDataTituloEleitoral.getText().toString().trim(), "dd/MM/yyyy", "yyyyMMdd");
        documentos.sCTPS = txtCarteiraDeTrabalho.getText().toString().trim().toUpperCase();
        documentos.sCTPSSerie = txtSerie.getText().toString().trim().toUpperCase();
        documentos.sCTPSDataEmissao = Apoio.parseData(txtDataCarteiraDeTrabalho.getText().toString().trim(), "dd/MM/yyyy", "yyyyMMdd");
        documentos.sCNH = txtCarteiraDeHabilitacao.getText().toString().trim().toUpperCase();
        documentos.sCNHCategoria = txtCategoria.getText().toString().trim().toUpperCase();
        documentos.sCNHValidade = Apoio.parseData(txtValidade.getText().toString().trim(), "dd/MM/yyyy", "yyyyMMdd");
        documentos.sPISPASEP = txtPis.getText().toString().trim().toUpperCase();
        documentos.sDeficienciaFisica = txtDeficienciaFisica.getText().toString().trim().toUpperCase();
        documentos.sDoencaCronica = txtDoencaCronica.getText().toString().trim().toUpperCase();

        //Obtem o sexo
        if ( cboSexo.getSelectedItemPosition() < 0 )
        {
            documentos.sSexo = "";
        }
        else
        {
            combos = arrComboSexo.get(cboSexo.getSelectedItemPosition());
            documentos.sSexo = combos.sCodigo;
        }

        //Obtem a cor/raça
        if ( cboCor.getSelectedItemPosition() < 0 )
        {
            documentos.sCorRaca = "";
        }
        else
        {
            combos = arrComboCorRaca.get(cboCor.getSelectedItemPosition());
            documentos.sCorRaca = combos.sCodigo;
        }

        //Obtem o grupo sanguineo
        if ( cboGrupoSanguineo.getSelectedItemPosition() < 0 )
        {
            documentos.sGrupoSanguineo = "";
        }
        else
        {
            combos = arrComboGrupoSanguineo.get(cboGrupoSanguineo.getSelectedItemPosition());
            documentos.sGrupoSanguineo = combos.sCodigo;
        }

        //Obtem o grupo sanguineo RH
        if ( cboGrupoSanguineoRH.getSelectedItemPosition() < 0 )
        {
            documentos.sFatorRH = "";
        }
        else
        {
            combos = arrComboGrupoSanguineoRH.get(cboGrupoSanguineoRH.getSelectedItemPosition());
            documentos.sFatorRH = combos.sCodigo;
        }

        //Obtem a naturalidade
        if ( cboNaturalidade.getSelectedItemPosition() < 0 )
        {
            documentos.sNaturalidade = "";
        }
        else
        {
            combos = arrComboNaturalidade.get(cboNaturalidade.getSelectedItemPosition());
            documentos.sNaturalidade = combos.sCodigo;
        }

        //Obtem o estado civil
        if ( cboEstadoCivil.getSelectedItemPosition() < 0 )
        {
            documentos.sEstadoCivil = "";
        }
        else
        {
            combos = arrComboEstadoCivil.get(cboEstadoCivil.getSelectedItemPosition());
            documentos.sEstadoCivil = combos.sCodigo;
        }

        //Obtem o grau de instrução
        if ( cboGrauDeInstrucao.getSelectedItemPosition() < 0 )
        {
            documentos.sGrauInstrucao = "";
        }
        else
        {
            combos = arrComboGrauInstrucao.get(cboGrauDeInstrucao.getSelectedItemPosition());
            documentos.sGrauInstrucao = combos.sCodigo;
        }

        //Obtem o estado do orgão expeditor
        if ( cboOrgaoExpeditorEstado.getSelectedItemPosition() < 0 )
        {
            documentos.sRGUF = "";
        }
        else
        {
            combos = arrComboOrgaoExpeditorEstado.get(cboOrgaoExpeditorEstado.getSelectedItemPosition());
            documentos.sRGUF = combos.sCodigo;
        }

        //Obtem o estado da CTPS
        if ( cboSerieEstado.getSelectedItemPosition() < 0 )
        {
            documentos.sCTPSUF = "";
        }
        else
        {
            combos = arrComboOrgaoExpeditorEstado.get(cboSerieEstado.getSelectedItemPosition());
            documentos.sCTPSUF = combos.sCodigo;
        }

        //Obtem a deficiencia fisica
        if ( cboDeficienciaFisica.getSelectedItemPosition() < 0 )
        {
            documentos.sDeficiente = "";
        }
        else
        {
            combos = arrComboDeficienciaFisica.get(cboDeficienciaFisica.getSelectedItemPosition());
            documentos.sDeficiente = combos.sCodigo;
        }

        //Obtem a doença cronica
        if ( cboDoencaCronica.getSelectedItemPosition() < 0 )
        {
            documentos.sDoenteCronico = "";
        }
        else
        {
            combos = arrComboDoencaCronica.get(cboDoencaCronica.getSelectedItemPosition());
            documentos.sDoenteCronico = combos.sCodigo;
        }

        //Obtem o representante legal
        if ( cboRepresentanteLegal.getSelectedItemPosition() < 0 )
        {
            documentos.sRepresentanteLegal = "";
        }
        else
        {
            combos = arrComboRepresentanteLegal.get(cboRepresentanteLegal.getSelectedItemPosition());
            documentos.sRepresentanteLegal = combos.sCodigo;
        }

        //Obtem o acompanha doente
        if ( cboAcompanhaDoente.getSelectedItemPosition() < 0 )
        {
            documentos.sAcompanhaDoente = "";
        }
        else
        {
            combos = arrComboAcompanhaDoente.get(cboAcompanhaDoente.getSelectedItemPosition());
            documentos.sAcompanhaDoente = combos.sCodigo;
        }

        return documentos;
    }

    /**
     * Carrega os campos da tela com base no JSON de dados
     */
    public void carregaCamposTela(Documentos documentos) throws Exception
    {
        //Seta os valores recebidos nos campos da tela
        txtNome.setText(documentos.sNome);
        txtMae.setText(documentos.sMae);
        txtPai.setText(documentos.sPai);
        txtDataNascimento.setText(Apoio.parseData(documentos.sDataNascimento, "yyyyMMdd", "dd/MM/yyyy"));
        txtNacionalidade.setText(documentos.sNacionalidade);
        txtRG.setText(documentos.sRG);
        txtRGDigito.setText(""); // Não tem
        txtOrgaoExpeditor.setText(documentos.sRGOrgao);
        txtDataExpedicao.setText(Apoio.parseData(documentos.sRGData, "yyyyMMdd", "dd/MM/yyyy"));
        txtTituloEleitoral.setText(documentos.sTitulo);
        txtTituloEleitoralDigito.setText(documentos.sTituloDV);
        txtZonaEleitoral.setText(documentos.sTituloZona);
        txtSecao.setText(documentos.sTituloSecao);
        txtDataTituloEleitoral.setText(Apoio.parseData(documentos.sTituloData, "yyyyMMdd", "dd/MM/yyyy"));
        txtCarteiraDeTrabalho.setText(documentos.sCTPS);
        txtSerie.setText(documentos.sCTPSSerie);
        txtDataCarteiraDeTrabalho.setText(Apoio.parseData(documentos.sCTPSDataEmissao, "yyyyMMdd", "dd/MM/yyyy"));
        txtCarteiraDeHabilitacao.setText(documentos.sCNH);
        txtCategoria.setText(documentos.sCNHCategoria);
        txtValidade.setText(Apoio.parseData(documentos.sCNHValidade, "yyyyMMdd", "dd/MM/yyyy"));
        txtPis.setText(documentos.sPISPASEP);
        txtDeficienciaFisica.setText(documentos.sDeficienciaFisica);
        txtDoencaCronica.setText(documentos.sDoencaCronica);

        //Posiciona os combos
        cboSexo.setSelection(Apoio.retornaPosicaoItemCombo(arrComboSexo, documentos.sSexo));
        cboCor.setSelection(Apoio.retornaPosicaoItemCombo(arrComboCorRaca, documentos.sCorRaca));
        cboGrupoSanguineo.setSelection(Apoio.retornaPosicaoItemCombo(arrComboGrupoSanguineo, documentos.sGrupoSanguineo));
        cboGrupoSanguineoRH.setSelection(Apoio.retornaPosicaoItemCombo(arrComboGrupoSanguineoRH, documentos.sFatorRH));
        cboNaturalidade.setSelection(Apoio.retornaPosicaoItemCombo(arrComboNaturalidade, documentos.sNaturalidade));
        cboEstadoCivil.setSelection(Apoio.retornaPosicaoItemCombo(arrComboEstadoCivil, documentos.sEstadoCivil));
        cboGrauDeInstrucao.setSelection(Apoio.retornaPosicaoItemCombo(arrComboGrauInstrucao, documentos.sGrauInstrucao));
        cboOrgaoExpeditorEstado.setSelection(Apoio.retornaPosicaoItemCombo(arrComboOrgaoExpeditorEstado, documentos.sRGUF));
        cboSerieEstado.setSelection(Apoio.retornaPosicaoItemCombo(arrComboSerieEstado, documentos.sCTPSUF));
        cboDeficienciaFisica.setSelection(Apoio.retornaPosicaoItemCombo(arrComboDeficienciaFisica, documentos.sDeficiente));
        cboDoencaCronica.setSelection(Apoio.retornaPosicaoItemCombo(arrComboDoencaCronica, documentos.sDoenteCronico));
        cboRepresentanteLegal.setSelection(Apoio.retornaPosicaoItemCombo(arrComboRepresentanteLegal, documentos.sRepresentanteLegal));
        cboAcompanhaDoente.setSelection(Apoio.retornaPosicaoItemCombo(arrComboAcompanhaDoente, documentos.sAcompanhaDoente));
    }

    /**
     * Limpa os campos da tela
     */
    public void limpaCamposTela() throws Exception
    {
        //Limpa os campos
        txtNome.setText("");
        txtMae.setText("");
        txtPai.setText("");
        txtDataNascimento.setText("");
        txtNacionalidade.setText("");
        txtRG.setText("");
        txtRGDigito.setText("");
        txtOrgaoExpeditor.setText("");
        txtDataExpedicao.setText("");
        txtTituloEleitoral.setText("");
        txtTituloEleitoralDigito.setText("");
        txtZonaEleitoral.setText("");
        txtSecao.setText("");
        txtDataTituloEleitoral.setText("");
        txtCarteiraDeTrabalho.setText("");
        txtSerie.setText("");
        txtDataCarteiraDeTrabalho.setText("");
        txtCarteiraDeHabilitacao.setText("");
        txtCategoria.setText("");
        txtValidade.setText("");
        txtPis.setText("");
        txtDeficienciaFisica.setText("");
        txtDoencaCronica.setText("");
        cboSexo.setSelection(0);
        cboCor.setSelection(0);
        cboGrupoSanguineo.setSelection(0);
        cboGrupoSanguineoRH.setSelection(0);
        cboNaturalidade.setSelection(0);
        cboEstadoCivil.setSelection(0);
        cboGrauDeInstrucao.setSelection(0);
        cboOrgaoExpeditorEstado.setSelection(0);
        cboSerieEstado.setSelection(0);
        cboDeficienciaFisica.setSelection(0);
        cboDoencaCronica.setSelection(0);
        cboRepresentanteLegal.setSelection(0);
        cboAcompanhaDoente.setSelection(0);
    }
}
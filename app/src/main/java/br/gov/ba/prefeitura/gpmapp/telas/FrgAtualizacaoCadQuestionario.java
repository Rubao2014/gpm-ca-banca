package br.gov.ba.prefeitura.gpmapp.telas;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import java.util.ArrayList;
import br.gov.ba.prefeitura.gpmapp.R;
import br.gov.ba.prefeitura.gpmapp.base.FragmentBase;
import br.gov.ba.prefeitura.gpmapp.models.Combos;
import br.gov.ba.prefeitura.gpmapp.models.Questionarios;
import br.gov.ba.prefeitura.gpmapp.util.Apoio;
import br.gov.ba.prefeitura.gpmapp.util.DialogAlerta;
import br.gov.ba.prefeitura.gpmapp.util.LogTrace;

/**
 * Fragmento de questionário da atualização cadastral
 */
public class FrgAtualizacaoCadQuestionario extends FragmentBase
{
    //Controles da tela
    private Spinner cboMoradia = null;
    private Spinner cboTipoMoradia = null;
    private Spinner cboEnergia = null;
    private Spinner cboAgua = null;
    private Spinner cboColetaLixo = null;
    private Spinner cboEsgoto = null;
    private Spinner cboAtividadeFisica = null;
    private Spinner cboAlergia = null;
    private Spinner cboHipertenso = null;
    private Spinner cboDiabetico = null;
    private Spinner cboCardiaco = null;
    private Spinner cboColesterol = null;
    private Spinner cboDoador = null;
    private Spinner cboFumante = null;
    private Spinner cboBeber = null;
    private TextView lblErroMoradia = null;
    private TextView lblErroTipoMoradia = null;
    private TextView lblErroEnergia = null;
    private TextView lblErroAgua = null;
    private TextView lblErroColetaLixo = null;
    private TextView lblErroEsgoto = null;
    private TextView lblErroAtividadeFisica = null;
    private TextView lblErroAlergia = null;
    private TextView lblErroHipertenso = null;
    private TextView lblErroDiabetico = null;
    private TextView lblErroCardiaco = null;
    private TextView lblErroColesterolAlto = null;
    private TextView lblErroDoadorOrgaos = null;
    private TextView lblErroFumante = null;
    private TextView lblErroBeber = null;

    //Variáveis da classe
    private ArrayList<Combos> arrComboMoradia = null;
    private ArrayList<Combos> arrComboTipoMoradia = null;
    private ArrayList<Combos> arrComboEnergia  = null;
    private ArrayList<Combos> arrComboAgua = null;
    private ArrayList<Combos> arrComboColetaLixo  = null;
    private ArrayList<Combos> arrComboEsgoto = null;
    private ArrayList<Combos> arrComboAtividadeFisica = null;
    private ArrayList<Combos> arrComboAlergia = null;
    private ArrayList<Combos> arrComboHipertenso = null;
    private ArrayList<Combos> arrComboDiabetico = null;
    private ArrayList<Combos> arrComboCardiaco = null;
    private ArrayList<Combos> arrComboColesterol = null;
    private ArrayList<Combos> arrComboDoador = null;
    private ArrayList<Combos> arrComboFumante = null;
    private ArrayList<Combos> arrComboBeber = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup vwgGrupo, @Nullable Bundle bundle)
    {
        View viewTela = null;

        try
        {
            //Infla o fragmento na view
            viewTela = inflater.inflate(R.layout.frg_atualizacao_cad_questionario, vwgGrupo, false);

            //chama o método para iniciar o fluxo de dados da classe
            iniciaFluxoDados(viewTela);
        }
        catch(Exception err)
        {
            LogTrace.escreve(Apoio.localErro(getClass(), err), Apoio.getPathLogsSDCard(getActivity()), Apoio.getArqErr());
            DialogAlerta.show(getActivity(), Apoio.getMsgErr(getClass(), err), getString(R.string.atencao), getString(R.string.ok));
        }

        return viewTela;
    }

    @Override
    public void iniciaControles(View viewTela) throws Exception
    {
        //Carrega os controles da classe
        cboMoradia = (Spinner) viewTela.findViewById(R.id.cboMoradia);
        cboTipoMoradia = (Spinner) viewTela.findViewById(R.id.cboTipoMoradia);
        cboEnergia = (Spinner) viewTela.findViewById(R.id.cboEnergia);
        cboAgua = (Spinner) viewTela.findViewById(R.id.cboAgua);
        cboColetaLixo = (Spinner) viewTela.findViewById(R.id.cboColetaLixo);
        cboEsgoto = (Spinner) viewTela.findViewById(R.id.cboEsgoto);
        cboAtividadeFisica = (Spinner) viewTela.findViewById(R.id.cboAtividadeFisica);
        cboAlergia = (Spinner) viewTela.findViewById(R.id.cboAlergia);
        cboHipertenso = (Spinner) viewTela.findViewById(R.id.cboHipertenso);
        cboDiabetico = (Spinner) viewTela.findViewById(R.id.cboDiabetico);
        cboCardiaco = (Spinner) viewTela.findViewById(R.id.cboCardiaco);;
        cboColesterol = (Spinner) viewTela.findViewById(R.id.cboColesterol);;
        cboDoador = (Spinner) viewTela.findViewById(R.id.cboDoador);
        cboFumante = (Spinner) viewTela.findViewById(R.id.cboFumante);
        cboBeber = (Spinner) viewTela.findViewById(R.id.cboBeber);
        lblErroMoradia = (TextView) viewTela.findViewById(R.id.lblErroMoradia);
        lblErroTipoMoradia = (TextView) viewTela.findViewById(R.id.lblErroTipoMoradia);
        lblErroEnergia = (TextView) viewTela.findViewById(R.id.lblErroEnergia);
        lblErroAgua = (TextView) viewTela.findViewById(R.id.lblErroAgua);
        lblErroColetaLixo = (TextView) viewTela.findViewById(R.id.lblErroColetaLixo);
        lblErroEsgoto = (TextView) viewTela.findViewById(R.id.lblErroEsgoto);
        lblErroAtividadeFisica = (TextView) viewTela.findViewById(R.id.lblErroAtividadeFisica);
        lblErroAlergia = (TextView) viewTela.findViewById(R.id.lblErroAlergia);
        lblErroHipertenso = (TextView) viewTela.findViewById(R.id.lblErroHipertenso);
        lblErroDiabetico = (TextView) viewTela.findViewById(R.id.lblErroDiabetico);
        lblErroCardiaco = (TextView) viewTela.findViewById(R.id.lblErroCardiaco);
        lblErroColesterolAlto = (TextView) viewTela.findViewById(R.id.lblErroColesterolAlto);
        lblErroDoadorOrgaos = (TextView) viewTela.findViewById(R.id.lblErroDoadorOrgaos);
        lblErroFumante = (TextView) viewTela.findViewById(R.id.lblErroFumante);
        lblErroBeber = (TextView) viewTela.findViewById(R.id.lblErroBeber);

        //Esconde o label de erro
        lblErroMoradia.setVisibility(View.GONE);
        lblErroTipoMoradia.setVisibility(View.GONE);
        lblErroEnergia.setVisibility(View.GONE);
        lblErroAgua.setVisibility(View.GONE);
        lblErroColetaLixo.setVisibility(View.GONE);
        lblErroEsgoto.setVisibility(View.GONE);
        lblErroAtividadeFisica.setVisibility(View.GONE);
        lblErroAlergia.setVisibility(View.GONE);
        lblErroHipertenso.setVisibility(View.GONE);
        lblErroDiabetico.setVisibility(View.GONE);
        lblErroCardiaco.setVisibility(View.GONE);
        lblErroColesterolAlto.setVisibility(View.GONE);
        lblErroDoadorOrgaos.setVisibility(View.GONE);
        lblErroFumante.setVisibility(View.GONE);
        lblErroBeber.setVisibility(View.GONE);
    }

    @Override
    public void carregaDados(View viewTela) throws Exception
    {
        //Preenche combo de Moradia
        preencheComboMoradia();

        //Preenche combo de tipo de Moradia
        preencheComboTipoMoradia();

        //Preenche combo de energia
        preencheComboEnergia();

        //Preenche combo Agua
        preencheComboAgua();

        //Preenche combo Coleta de Lixo
        preencheComboColetaLixo();

        //Preenche combo Esgoto
        preencheComboEsgoto();

        //Preenche combo Atividade Fisica
        preencheComboAtividadeFisica();

        //Preenche combo Alergia
        preencheComboAlergia();

        //Preenche combo Hipertenso
        preencheComboHipertenso();

        //Preenche combo Diabetico
        preencheComboDiabetico();

        //Preenche combo Cardiaco
        preencheComboCardiaco();

        //Preenche combo Colesterol
        preencheComboColesterol();

        //Preenche combo Doador
        preencheComboDoador();

        //Preenche combo Fumante
        preencheComboFumante();

        //Preenche combo Beber
        preencheComboBeber();
    }

    /**
     * Preenche o combo de moradia
     */
    private void preencheComboTipoMoradia() throws Exception
    {
        ArrayAdapter<Combos> dataAdapter = null;

        //Carrega os dados
        arrComboTipoMoradia = new ArrayList<Combos>();
        arrComboTipoMoradia.add(new Combos("", ""));
        arrComboTipoMoradia.add(new Combos("1", "ALVENARIA"));
        arrComboTipoMoradia.add(new Combos("2", "TAIPA"));
        arrComboTipoMoradia.add(new Combos("3", "MADEIRA"));

        //Cria o adapter
        dataAdapter = new ArrayAdapter<Combos>(this.getContext(), android.R.layout.simple_spinner_item, arrComboTipoMoradia);

        //Seta o adapter no dropdown
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //Atacha o adapter ao controle
        cboTipoMoradia.setAdapter(dataAdapter);
    }

    /**
     * Preenche o combo de Tipo de Moradia
     */
    private void preencheComboMoradia() throws Exception
    {
        ArrayAdapter<Combos> dataAdapter = null;

        //Carrega os dados
        arrComboMoradia = new ArrayList<Combos>();
        arrComboMoradia.add(new Combos("", ""));
        arrComboMoradia.add(new Combos("1", "PROPRIA"));
        arrComboMoradia.add(new Combos("2", "CEDIDA"));
        arrComboMoradia.add(new Combos("3", "ALUGADA"));
        arrComboMoradia.add(new Combos("4", "FINANCIADA"));

        //Cria o adapter
        dataAdapter = new ArrayAdapter<Combos>(this.getContext(), android.R.layout.simple_spinner_item, arrComboMoradia);

        //Seta o adapter no dropdown
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //Atacha o adapter ao controle
        cboMoradia.setAdapter(dataAdapter);
    }

    /**
     * Preenche o combo de Energia
     */
    private void preencheComboEnergia() throws Exception
    {
        ArrayAdapter<Combos> dataAdapter = null;

        //Carrega os dados
        arrComboEnergia = new ArrayList<Combos>();
        arrComboEnergia.add(new Combos("", ""));
        arrComboEnergia.add(new Combos("N", "NÃO"));
        arrComboEnergia.add(new Combos("S", "SIM"));

        //Cria o adapter
        dataAdapter = new ArrayAdapter<Combos>(this.getContext(), android.R.layout.simple_spinner_item, arrComboEnergia);

        //Seta o adapter no dropdown
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //Atacha o adapter ao controle
        cboEnergia.setAdapter(dataAdapter);
    }

    /**
     * Preenche o combo de Agua
     */
    private void preencheComboAgua() throws Exception
    {
        ArrayAdapter<Combos> dataAdapter = null;

        //Carrega os dados
        arrComboAgua = new ArrayList<Combos>();
        arrComboAgua.add(new Combos("", ""));
        arrComboAgua.add(new Combos("N", "NÃO"));
        arrComboAgua.add(new Combos("S", "SIM"));

        //Cria o adapter
        dataAdapter = new ArrayAdapter<Combos>(this.getContext(), android.R.layout.simple_spinner_item, arrComboAgua);

        //Seta o adapter no dropdown
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //Atacha o adapter ao controle
        cboAgua.setAdapter(dataAdapter);
    }

    /**
     * Preenche o combo de coleta de lixo
     */
    private void preencheComboColetaLixo() throws Exception
    {
        ArrayAdapter<Combos> dataAdapter = null;

        //Carrega os dados
        arrComboColetaLixo = new ArrayList<Combos>();
        arrComboColetaLixo.add(new Combos("", ""));
        arrComboColetaLixo.add(new Combos("N", "NÃO"));
        arrComboColetaLixo.add(new Combos("S", "SIM"));

        //Cria o adapter
        dataAdapter = new ArrayAdapter<Combos>(this.getContext(), android.R.layout.simple_spinner_item, arrComboColetaLixo);

        //Seta o adapter no dropdown
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //Atacha o adapter ao controle
        cboColetaLixo.setAdapter(dataAdapter);
    }

    /**
     * Preenche o combo de esgoto
     */
    private void preencheComboEsgoto() throws Exception
    {
        ArrayAdapter<Combos> dataAdapter = null;

        //Carrega os dados
        arrComboEsgoto = new ArrayList<Combos>();
        arrComboEsgoto.add(new Combos("", ""));
        arrComboEsgoto.add(new Combos("N", "NÃO"));
        arrComboEsgoto.add(new Combos("S", "SIM"));

        //Cria o adapter
        dataAdapter = new ArrayAdapter<Combos>(this.getContext(), android.R.layout.simple_spinner_item, arrComboEsgoto);

        //Seta o adapter no dropdown
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //Atacha o adapter ao controle
        cboEsgoto.setAdapter(dataAdapter);
    }

    /**
     * Preenche o combo atividade física
     */
    private void preencheComboAtividadeFisica() throws Exception
    {
        ArrayAdapter<Combos> dataAdapter = null;

        //Carrega os dados
        arrComboAtividadeFisica = new ArrayList<Combos>();
        arrComboAtividadeFisica.add(new Combos("", ""));
        arrComboAtividadeFisica.add(new Combos("N", "NÃO"));
        arrComboAtividadeFisica.add(new Combos("S", "SIM"));

        //Cria o adapter
        dataAdapter = new ArrayAdapter<Combos>(this.getContext(), android.R.layout.simple_spinner_item, arrComboAtividadeFisica);

        //Seta o adapter no dropdown
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //Atacha o adapter ao controle
        cboAtividadeFisica.setAdapter(dataAdapter);
    }

    /**
     * Preenche o combo alergia
     */
    private void preencheComboAlergia() throws Exception
    {
        ArrayAdapter<Combos> dataAdapter = null;

        //Carrega os dados
        arrComboAlergia = new ArrayList<Combos>();
        arrComboAlergia.add(new Combos("", ""));
        arrComboAlergia.add(new Combos("N", "NÃO"));
        arrComboAlergia.add(new Combos("S", "SIM"));

        //Cria o adapter
        dataAdapter = new ArrayAdapter<Combos>(this.getContext(), android.R.layout.simple_spinner_item, arrComboAlergia);

        //Seta o adapter no dropdown
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //Atacha o adapter ao controle
        cboAlergia.setAdapter(dataAdapter);
    }

    /**
     * Preenche o combo hipertenso
     */
    private void preencheComboHipertenso() throws Exception
    {
        ArrayAdapter<Combos> dataAdapter = null;

        //Carrega os dados
        arrComboHipertenso = new ArrayList<Combos>();
        arrComboHipertenso.add(new Combos("", ""));
        arrComboHipertenso.add(new Combos("N", "NÃO"));
        arrComboHipertenso.add(new Combos("S", "SIM"));

        //Cria o adapter
        dataAdapter = new ArrayAdapter<Combos>(this.getContext(), android.R.layout.simple_spinner_item, arrComboHipertenso);

        //Seta o adapter no dropdown
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //Atacha o adapter ao controle
        cboHipertenso.setAdapter(dataAdapter);
    }

    /**
     * Preenche o combo diabético
     */
    private void preencheComboDiabetico() throws Exception
    {
        ArrayAdapter<Combos> dataAdapter = null;

        //Carrega os dados
        arrComboDiabetico = new ArrayList<Combos>();
        arrComboDiabetico.add(new Combos("", ""));
        arrComboDiabetico.add(new Combos("N", "NÃO"));
        arrComboDiabetico.add(new Combos("S", "SIM"));

        //Cria o adapter
        dataAdapter = new ArrayAdapter<Combos>(this.getContext(), android.R.layout.simple_spinner_item, arrComboDiabetico);

        //Seta o adapter no dropdown
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //Atacha o adapter ao controle
        cboDiabetico.setAdapter(dataAdapter);
    }

    /**
     * Preenche o combo Cardiaco
     */
    private void preencheComboCardiaco() throws Exception
    {
        ArrayAdapter<Combos> dataAdapter = null;

        //Carrega os dados
        arrComboCardiaco = new ArrayList<Combos>();
        arrComboCardiaco.add(new Combos("", ""));
        arrComboCardiaco.add(new Combos("N", "NÃO"));
        arrComboCardiaco.add(new Combos("S", "SIM"));

        //Cria o adapter
        dataAdapter = new ArrayAdapter<Combos>(this.getContext(), android.R.layout.simple_spinner_item, arrComboCardiaco);

        //Seta o adapter no dropdown
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //Atacha o adapter ao controle
        cboCardiaco.setAdapter(dataAdapter);
    }

    /**
     * Preenche o combo Cardiaco
     */
    private void preencheComboColesterol() throws Exception
    {
        ArrayAdapter<Combos> dataAdapter = null;

        //Carrega os dados
        arrComboColesterol = new ArrayList<Combos>();
        arrComboColesterol.add(new Combos("", ""));
        arrComboColesterol.add(new Combos("N", "NÃO"));
        arrComboColesterol.add(new Combos("S", "SIM"));

        //Cria o adapter
        dataAdapter = new ArrayAdapter<Combos>(this.getContext(), android.R.layout.simple_spinner_item, arrComboColesterol);

        //Seta o adapter no dropdown
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //Atacha o adapter ao controle
        cboColesterol.setAdapter(dataAdapter);
    }

    /**
     * Preenche o combo Cardiaco
     */
    private void preencheComboDoador() throws Exception
    {
        ArrayAdapter<Combos> dataAdapter = null;

        //Carrega os dados
        arrComboDoador = new ArrayList<Combos>();
        arrComboDoador.add(new Combos("", ""));
        arrComboDoador.add(new Combos("N", "NÃO"));
        arrComboDoador.add(new Combos("S", "SIM"));

        //Cria o adapter
        dataAdapter = new ArrayAdapter<Combos>(this.getContext(), android.R.layout.simple_spinner_item, arrComboDoador);

        //Seta o adapter no dropdown
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //Atacha o adapter ao controle
        cboDoador.setAdapter(dataAdapter);
    }

    /**
     * Preenche o combo Fumante
     */
    private void preencheComboFumante() throws Exception
    {
        ArrayAdapter<Combos> dataAdapter = null;

        //Carrega os dados
        arrComboFumante = new ArrayList<Combos>();
        arrComboFumante.add(new Combos("", ""));
        arrComboFumante.add(new Combos("N", "NÃO"));
        arrComboFumante.add(new Combos("S", "SIM"));

        //Cria o adapter
        dataAdapter = new ArrayAdapter<Combos>(this.getContext(), android.R.layout.simple_spinner_item, arrComboFumante);

        //Seta o adapter no dropdown
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //Atacha o adapter ao controle
        cboFumante.setAdapter(dataAdapter);
    }

    /**
     * Preenche o combo Fumante
     */
    private void preencheComboBeber() throws Exception
    {
        ArrayAdapter<Combos> dataAdapter = null;

        //Carrega os dados
        arrComboBeber = new ArrayList<Combos>();
        arrComboBeber.add(new Combos("", ""));
        arrComboBeber.add(new Combos("N", "NÃO"));
        arrComboBeber.add(new Combos("S", "SIM"));

        //Cria o adapter
        dataAdapter = new ArrayAdapter<Combos>(this.getContext(), android.R.layout.simple_spinner_item, arrComboBeber);

        //Seta o adapter no dropdown
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //Atacha o adapter ao controle
        cboBeber.setAdapter(dataAdapter);
    }

    /**
     * Remove os erros dos campos da tela
     */
    public void removerErroDosCampos() throws Exception
    {
        //Esconde o label de erro
        lblErroMoradia.setVisibility(View.GONE);
        lblErroTipoMoradia.setVisibility(View.GONE);
        lblErroEnergia.setVisibility(View.GONE);
        lblErroAgua.setVisibility(View.GONE);
        lblErroColetaLixo.setVisibility(View.GONE);
        lblErroEsgoto.setVisibility(View.GONE);
        lblErroAtividadeFisica.setVisibility(View.GONE);
        lblErroAlergia.setVisibility(View.GONE);
        lblErroHipertenso.setVisibility(View.GONE);
        lblErroDiabetico.setVisibility(View.GONE);
        lblErroCardiaco.setVisibility(View.GONE);
        lblErroColesterolAlto.setVisibility(View.GONE);
        lblErroDoadorOrgaos.setVisibility(View.GONE);
        lblErroFumante.setVisibility(View.GONE);
        lblErroBeber.setVisibility(View.GONE);
    }

    /**
     * Valida os campos da tela
     */
    public boolean validaCampos(AppCompatActivity activity, ViewPager viewPager) throws Exception
    {
        //Valida o combo de moradia
        if (cboMoradia.getSelectedItemPosition() <= 0)
        {
            //Coloca a mensagem de erro embaixo do campo e solicita o foco
            lblErroMoradia.setText(getString(R.string.msg_atualizacao_cad_questionario_moradia_nao_selecionado));
            lblErroMoradia.setVisibility(View.VISIBLE);
            viewPager.setCurrentItem(4, true);
            Apoio.requisitarFoco(cboMoradia, activity);
            return false;
        }
        else
        {
            //Se estier OK, remove o erro
            lblErroMoradia.setText("");
            lblErroMoradia.setVisibility(View.GONE);
        }

        //Valida o combo de tipo de moradia
        if (cboTipoMoradia.getSelectedItemPosition() <= 0)
        {
            //Coloca a mensagem de erro embaixo do campo e solicita o foco
            lblErroTipoMoradia.setText(getString(R.string.msg_atualizacao_cad_questionario_tipo_moradia_nao_selecionado));
            lblErroTipoMoradia.setVisibility(View.VISIBLE);
            viewPager.setCurrentItem(4, true);
            Apoio.requisitarFoco(cboTipoMoradia, activity);
            return false;
        }
        else
        {
            //Se estier OK, remove o erro
            lblErroTipoMoradia.setText("");
            lblErroTipoMoradia.setVisibility(View.GONE);
        }

        //Valida o combo de energia
        if (cboEnergia.getSelectedItemPosition() <= 0)
        {
            //Coloca a mensagem de erro embaixo do campo e solicita o foco
            lblErroEnergia.setText(getString(R.string.msg_atualizacao_cad_questionario_energia_nao_selecionado));
            lblErroEnergia.setVisibility(View.VISIBLE);
            viewPager.setCurrentItem(4, true);
            Apoio.requisitarFoco(cboEnergia, activity);
            return false;
        }
        else
        {
            //Se estier OK, remove o erro
            lblErroEnergia.setText("");
            lblErroEnergia.setVisibility(View.GONE);
        }

        //Valida o combo de água
        if (cboAgua.getSelectedItemPosition() <= 0)
        {
            //Coloca a mensagem de erro embaixo do campo e solicita o foco
            lblErroAgua.setText(getString(R.string.msg_atualizacao_cad_questionario_agua_nao_selecionado));
            lblErroAgua.setVisibility(View.VISIBLE);
            viewPager.setCurrentItem(4, true);
            Apoio.requisitarFoco(cboAgua, activity);
            return false;
        }
        else
        {
            //Se estier OK, remove o erro
            lblErroAgua.setText("");
            lblErroAgua.setVisibility(View.GONE);
        }

        //Valida o combo de coleta de lixo
        if (cboColetaLixo.getSelectedItemPosition() <= 0)
        {
            //Coloca a mensagem de erro embaixo do campo e solicita o foco
            lblErroColetaLixo.setText(getString(R.string.msg_atualizacao_cad_questionario_coleta_lixo_nao_selecionado));
            lblErroColetaLixo.setVisibility(View.VISIBLE);
            viewPager.setCurrentItem(4, true);
            Apoio.requisitarFoco(cboColetaLixo, activity);
            return false;
        }
        else
        {
            //Se estier OK, remove o erro
            lblErroColetaLixo.setText("");
            lblErroColetaLixo.setVisibility(View.GONE);
        }

        //Valida o combo de esgoto
        if (cboEsgoto.getSelectedItemPosition() <= 0)
        {
            //Coloca a mensagem de erro embaixo do campo e solicita o foco
            lblErroEsgoto.setText(getString(R.string.msg_atualizacao_cad_questionario_esgoto_nao_selecionado));
            lblErroEsgoto.setVisibility(View.VISIBLE);
            viewPager.setCurrentItem(4, true);
            Apoio.requisitarFoco(cboEsgoto, activity);
            return false;
        }
        else
        {
            //Se estier OK, remove o erro
            lblErroEsgoto.setText("");
            lblErroEsgoto.setVisibility(View.GONE);
        }

        //Valida o combo de atividade fisica
        if (cboAtividadeFisica.getSelectedItemPosition() <= 0)
        {
            //Coloca a mensagem de erro embaixo do campo e solicita o foco
            lblErroAtividadeFisica.setText(getString(R.string.msg_atualizacao_cad_questionario_atividade_fisica_nao_selecionado));
            lblErroAtividadeFisica.setVisibility(View.VISIBLE);
            viewPager.setCurrentItem(4, true);
            Apoio.requisitarFoco(cboAtividadeFisica, activity);
            return false;
        }
        else
        {
            //Se estier OK, remove o erro
            lblErroAtividadeFisica.setText("");
            lblErroAtividadeFisica.setVisibility(View.GONE);
        }

        //Valida o combo de alergia
        if (cboAlergia.getSelectedItemPosition() <= 0)
        {
            //Coloca a mensagem de erro embaixo do campo e solicita o foco
            lblErroAlergia.setText(getString(R.string.msg_atualizacao_cad_questionario_alergia_nao_selecionado));
            lblErroAlergia.setVisibility(View.VISIBLE);
            viewPager.setCurrentItem(4, true);
            Apoio.requisitarFoco(cboAlergia, activity);
            return false;
        }
        else
        {
            //Se estier OK, remove o erro
            lblErroAlergia.setText("");
            lblErroAlergia.setVisibility(View.GONE);
        }

        //Valida o combo de hipertenso
        if (cboHipertenso.getSelectedItemPosition() <= 0)
        {
            //Coloca a mensagem de erro embaixo do campo e solicita o foco
            lblErroHipertenso.setText(getString(R.string.msg_atualizacao_cad_questionario_hipertenso_nao_selecionado));
            lblErroHipertenso.setVisibility(View.VISIBLE);
            viewPager.setCurrentItem(4, true);
            Apoio.requisitarFoco(cboHipertenso, activity);
            return false;
        }
        else
        {
            //Se estier OK, remove o erro
            lblErroHipertenso.setText("");
            lblErroHipertenso.setVisibility(View.GONE);
        }

        //Valida o combo de diabetico
        if (cboDiabetico.getSelectedItemPosition() <= 0)
        {
            //Coloca a mensagem de erro embaixo do campo e solicita o foco
            lblErroDiabetico.setText(getString(R.string.msg_atualizacao_cad_questionario_diabetico_nao_selecionado));
            lblErroDiabetico.setVisibility(View.VISIBLE);
            viewPager.setCurrentItem(4, true);
            Apoio.requisitarFoco(cboDiabetico, activity);
            return false;
        }
        else
        {
            //Se estier OK, remove o erro
            lblErroDiabetico.setText("");
            lblErroDiabetico.setVisibility(View.GONE);
        }

        //Valida o combo de cardiaco
        if (cboCardiaco.getSelectedItemPosition() <= 0)
        {
            //Coloca a mensagem de erro embaixo do campo e solicita o foco
            lblErroCardiaco.setText(getString(R.string.msg_atualizacao_cad_questionario_cardiaco_nao_selecionado));
            lblErroCardiaco.setVisibility(View.VISIBLE);
            viewPager.setCurrentItem(4, true);
            Apoio.requisitarFoco(cboCardiaco, activity);
            return false;
        }
        else
        {
            //Se estier OK, remove o erro
            lblErroCardiaco.setText("");
            lblErroCardiaco.setVisibility(View.GONE);
        }

        //Valida o combo de colesterol alto
        if (cboColesterol.getSelectedItemPosition() <= 0)
        {
            //Coloca a mensagem de erro embaixo do campo e solicita o foco
            lblErroColesterolAlto.setText(getString(R.string.msg_atualizacao_cad_questionario_colesterol_alto_nao_selecionado));
            lblErroColesterolAlto.setVisibility(View.VISIBLE);
            viewPager.setCurrentItem(4, true);
            Apoio.requisitarFoco(cboColesterol, activity);
            return false;
        }
        else
        {
            //Se estier OK, remove o erro
            lblErroColesterolAlto.setText("");
            lblErroColesterolAlto.setVisibility(View.GONE);
        }

        //Valida o combo de doador
        if (cboDoador.getSelectedItemPosition() <= 0)
        {
            //Coloca a mensagem de erro embaixo do campo e solicita o foco
            lblErroDoadorOrgaos.setText(getString(R.string.msg_atualizacao_cad_questionario_doador_orgaos_nao_selecionado));
            lblErroDoadorOrgaos.setVisibility(View.VISIBLE);
            viewPager.setCurrentItem(4, true);
            Apoio.requisitarFoco(cboDoador, activity);
            return false;
        }
        else
        {
            //Se estier OK, remove o erro
            lblErroDoadorOrgaos.setText("");
            lblErroDoadorOrgaos.setVisibility(View.GONE);
        }

        //Valida o combo de fumante
        if (cboFumante.getSelectedItemPosition() <= 0)
        {
            //Coloca a mensagem de erro embaixo do campo e solicita o foco
            lblErroFumante.setText(getString(R.string.msg_atualizacao_cad_questionario_habito_fumar_nao_selecionado));
            lblErroFumante.setVisibility(View.VISIBLE);
            viewPager.setCurrentItem(4, true);
            Apoio.requisitarFoco(cboFumante, activity);
            return false;
        }
        else
        {
            //Se estier OK, remove o erro
            lblErroFumante.setText("");
            lblErroFumante.setVisibility(View.GONE);
        }

        //Valida o combo de beber
        if (cboBeber.getSelectedItemPosition() <= 0)
        {
            //Coloca a mensagem de erro embaixo do campo e solicita o foco
            lblErroBeber.setText(getString(R.string.msg_atualizacao_cad_questionario_habito_beber_nao_selecionado));
            lblErroBeber.setVisibility(View.VISIBLE);
            viewPager.setCurrentItem(4, true);
            Apoio.requisitarFoco(cboBeber, activity);
            return false;
        }
        else
        {
            //Se estier OK, remove o erro
            lblErroBeber.setText("");
            lblErroBeber.setVisibility(View.GONE);
        }

        return true;
    }

    /**
     * Retorna os dados da tela
     */
    public Questionarios retornaDadosTela() throws Exception
    {
        Questionarios questionarios = null;
        Combos combos = null;

        //Inicia a model de endereços
        questionarios = new Questionarios();

        //Obtem a moradia
        if ( cboMoradia.getSelectedItemPosition() < 0 )
        {
            questionarios.sMoradia = "";
        }
        else
        {
            combos = arrComboMoradia.get(cboMoradia.getSelectedItemPosition());
            questionarios.sMoradia = combos.sCodigo;
        }

        //Obtem o tipo de moradia
        if ( cboTipoMoradia.getSelectedItemPosition() < 0 )
        {
            questionarios.sTipoMoradia = "";
        }
        else
        {
            combos = arrComboTipoMoradia.get(cboTipoMoradia.getSelectedItemPosition());
            questionarios.sTipoMoradia = combos.sCodigo;
        }

        //Obtem a energia
        if ( cboEnergia.getSelectedItemPosition() < 0 )
        {
            questionarios.sServicoEletronico = "";
        }
        else
        {
            combos = arrComboEnergia.get(cboEnergia.getSelectedItemPosition());
            questionarios.sServicoEletronico = combos.sCodigo;
        }

        //Obtem a agua
        if ( cboAgua.getSelectedItemPosition() < 0 )
        {
            questionarios.sServicoAgua = "";
        }
        else
        {
            combos = arrComboAgua.get(cboAgua.getSelectedItemPosition());
            questionarios.sServicoAgua = combos.sCodigo;
        }

        //Obtem a coleta de lixo
        if ( cboColetaLixo.getSelectedItemPosition() < 0 )
        {
            questionarios.sServicoLixo = "";
        }
        else
        {
            combos = arrComboColetaLixo.get(cboColetaLixo.getSelectedItemPosition());
            questionarios.sServicoLixo = combos.sCodigo;
        }

        //Obtem a coleta de lixo
        if ( cboEsgoto.getSelectedItemPosition() < 0 )
        {
            questionarios.sServicoEsgoto = "";
        }
        else
        {
            combos = arrComboEsgoto.get(cboEsgoto.getSelectedItemPosition());
            questionarios.sServicoEsgoto = combos.sCodigo;
        }

        //Obtem a atividade fisica
        if ( cboAtividadeFisica.getSelectedItemPosition() < 0 )
        {
            questionarios.sAtividadeFisica = "";
        }
        else
        {
            combos = arrComboAtividadeFisica.get(cboAtividadeFisica.getSelectedItemPosition());
            questionarios.sAtividadeFisica = combos.sCodigo;
        }

        //Obtem a alergia
        if ( cboAlergia.getSelectedItemPosition() < 0 )
        {
            questionarios.sAlergia = "";
        }
        else
        {
            combos = arrComboAlergia.get(cboAlergia.getSelectedItemPosition());
            questionarios.sAlergia = combos.sCodigo;
        }

        //Obtem a hipertensao
        if ( cboHipertenso.getSelectedItemPosition() < 0 )
        {
            questionarios.sHipertensao = "";
        }
        else
        {
            combos = arrComboHipertenso.get(cboHipertenso.getSelectedItemPosition());
            questionarios.sHipertensao = combos.sCodigo;
        }

        //Obtem o diabetico
        if ( cboDiabetico.getSelectedItemPosition() < 0 )
        {
            questionarios.sDiabetico = "";
        }
        else
        {
            combos = arrComboDiabetico.get(cboDiabetico.getSelectedItemPosition());
            questionarios.sDiabetico = combos.sCodigo;
        }

        //Obtem o cardiaco
        if ( cboCardiaco.getSelectedItemPosition() < 0 )
        {
            questionarios.sCardiaco = "";
        }
        else
        {
            combos = arrComboCardiaco.get(cboCardiaco.getSelectedItemPosition());
            questionarios.sCardiaco = combos.sCodigo;
        }

        //Obtem o colesterol alto
        if ( cboColesterol.getSelectedItemPosition() < 0 )
        {
            questionarios.sColesterol = "";
        }
        else
        {
            combos = arrComboColesterol.get(cboColesterol.getSelectedItemPosition());
            questionarios.sColesterol = combos.sCodigo;
        }

        //Obtem o doador
        if ( cboDoador.getSelectedItemPosition() < 0 )
        {
            questionarios.sDoador = "";
        }
        else
        {
            combos = arrComboDoador.get(cboDoador.getSelectedItemPosition());
            questionarios.sDoador = combos.sCodigo;
        }

        //Obtem o fumante
        if ( cboFumante.getSelectedItemPosition() < 0 )
        {
            questionarios.sHabitoFumar = "";
        }
        else
        {
            combos = arrComboFumante.get(cboFumante.getSelectedItemPosition());
            questionarios.sHabitoFumar = combos.sCodigo;
        }

        //Obtem o beber
        if ( cboBeber.getSelectedItemPosition() < 0 )
        {
            questionarios.sHabitoBeber = "";
        }
        else
        {
            combos = arrComboBeber.get(cboBeber.getSelectedItemPosition());
            questionarios.sHabitoBeber = combos.sCodigo;
        }

        return questionarios;
    }

    /**
     * Carrega os campos da tela com base no JSON de dados
     */
    public void carregaCamposTela(Questionarios questionarios) throws Exception
    {
        //Posiciona os combos
        cboMoradia.setSelection(Apoio.retornaPosicaoItemCombo(arrComboMoradia, questionarios.sMoradia));
        cboTipoMoradia.setSelection(Apoio.retornaPosicaoItemCombo(arrComboTipoMoradia, questionarios.sTipoMoradia));
        cboEnergia.setSelection(Apoio.retornaPosicaoItemCombo(arrComboEnergia, questionarios.sServicoEletronico));
        cboAgua.setSelection(Apoio.retornaPosicaoItemCombo(arrComboAgua, questionarios.sServicoAgua));
        cboColetaLixo.setSelection(Apoio.retornaPosicaoItemCombo(arrComboColetaLixo, questionarios.sServicoLixo));
        cboEsgoto.setSelection(Apoio.retornaPosicaoItemCombo(arrComboEsgoto, questionarios.sServicoEsgoto));
        cboAtividadeFisica.setSelection(Apoio.retornaPosicaoItemCombo(arrComboAtividadeFisica, questionarios.sAtividadeFisica));
        cboAlergia.setSelection(Apoio.retornaPosicaoItemCombo(arrComboAlergia, questionarios.sAlergia));
        cboHipertenso.setSelection(Apoio.retornaPosicaoItemCombo(arrComboHipertenso, questionarios.sHipertensao));
        cboDiabetico.setSelection(Apoio.retornaPosicaoItemCombo(arrComboDiabetico, questionarios.sDiabetico));
        cboCardiaco.setSelection(Apoio.retornaPosicaoItemCombo(arrComboCardiaco, questionarios.sCardiaco));
        cboColesterol.setSelection(Apoio.retornaPosicaoItemCombo(arrComboColesterol, questionarios.sColesterol));
        cboDoador.setSelection(Apoio.retornaPosicaoItemCombo(arrComboDoador, questionarios.sDoador));
        cboFumante.setSelection(Apoio.retornaPosicaoItemCombo(arrComboFumante, questionarios.sHabitoFumar));
        cboBeber.setSelection(Apoio.retornaPosicaoItemCombo(arrComboBeber, questionarios.sHabitoBeber));
    }

    /**
     * Limpa os campos da tela
     */
    public void limpaCamposTela() throws Exception
    {
        //Limpa os campos
        cboMoradia.setSelection(0);
        cboTipoMoradia.setSelection(0);
        cboEnergia.setSelection(0);
        cboAgua.setSelection(0);
        cboColetaLixo.setSelection(0);
        cboEsgoto.setSelection(0);
        cboAtividadeFisica.setSelection(0);
        cboAlergia.setSelection(0);
        cboHipertenso.setSelection(0);
        cboDiabetico.setSelection(0);
        cboCardiaco.setSelection(0);
        cboColesterol.setSelection(0);
        cboDoador.setSelection(0);
        cboFumante.setSelection(0);
        cboBeber.setSelection(0);
    }
}
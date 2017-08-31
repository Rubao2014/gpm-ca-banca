package br.gov.ba.prefeitura.gpmapp.telas;

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
import br.gov.ba.prefeitura.gpmapp.R;
import br.gov.ba.prefeitura.gpmapp.base.FragmentBase;
import br.gov.ba.prefeitura.gpmapp.models.Combos;
import br.gov.ba.prefeitura.gpmapp.models.Enderecos;
import br.gov.ba.prefeitura.gpmapp.util.Apoio;
import br.gov.ba.prefeitura.gpmapp.util.DialogAlerta;
import br.gov.ba.prefeitura.gpmapp.util.LogTrace;
import br.gov.ba.prefeitura.gpmapp.util.MascaraCampos;

/**
 * Fragmento de endereço da atualização cadastral
 */
public class FrgAtualizacaoCadEndereco extends FragmentBase
{
    //Controles da tela
    private Spinner cboTipoDeEndereco = null;
    private EditText txtLogradouro = null;
    private EditText txtNumero = null;
    private EditText txtComplemento = null;
    private EditText txtPontoDeReferencia = null;
    private EditText txtEmail = null;
    private EditText txtBairro = null;
    private EditText txtCidade = null;
    private EditText txtCEP = null;
    private EditText txtTelefoneContato = null;
    private EditText txtCelular = null;
    private EditText txtFixo = null;
    private TextInputLayout inputLogradouro = null;
    private TextInputLayout inputNumero = null;
    private TextInputLayout inputBairro = null;
    private TextInputLayout inputCidade = null;
    private TextInputLayout inputCEP = null;
    private TextInputLayout inputTelefoneContato = null;
    private TextView lblErroTipoDeEndereco = null;

    //Variáveis da classe
    private ArrayList<Combos> arrComboTipoEndereco = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup vwgGrupo, @Nullable Bundle bundle)
    {
        View viewTela = null;

        try
        {
            //Infla o fragmento na view
            viewTela = inflater.inflate(R.layout.frg_atualizacao_cad_endereco, vwgGrupo, false);

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
        TextWatcher textWatcherCEP = null;
        TextWatcher textWatcherCelularFone = null;

        //Carrega os controles da classe
        cboTipoDeEndereco = (Spinner) viewTela.findViewById(R.id.cboTipoDeEndereco);
        txtLogradouro = (EditText) viewTela.findViewById(R.id.txtLogradouro);
        txtNumero = (EditText) viewTela.findViewById(R.id.txtNumero);
        txtComplemento = (EditText) viewTela.findViewById(R.id.txtComplemento);
        txtPontoDeReferencia = (EditText) viewTela.findViewById(R.id.txtPontoDeReferencia);
        txtEmail = (EditText) viewTela.findViewById(R.id.txtEmail);
        txtBairro = (EditText) viewTela.findViewById(R.id.txtBairro);
        txtCidade = (EditText) viewTela.findViewById(R.id.txtCidade);
        txtCEP = (EditText) viewTela.findViewById(R.id.txtCEP);
        txtTelefoneContato = (EditText) viewTela.findViewById(R.id.txtTelefoneContato);
        txtCelular = (EditText) viewTela.findViewById(R.id.txtCelular);
        txtFixo = (EditText) viewTela.findViewById(R.id.txtFixo);
        inputLogradouro = (TextInputLayout) viewTela.findViewById(R.id.inputLogradouro);
        inputNumero = (TextInputLayout) viewTela.findViewById(R.id.inputNumero);
        inputBairro = (TextInputLayout) viewTela.findViewById(R.id.inputBairro);
        inputCidade = (TextInputLayout) viewTela.findViewById(R.id.inputCidade);
        inputCEP = (TextInputLayout) viewTela.findViewById(R.id.inputCEP);
        inputTelefoneContato = (TextInputLayout) viewTela.findViewById(R.id.inputTelefoneContato);
        lblErroTipoDeEndereco = (TextView) viewTela.findViewById(R.id.lblErroTipoDeEndereco);

        //coloca a mascara nos campos
        textWatcherCEP = MascaraCampos.insert("#####-###", txtCEP);
        txtCEP.addTextChangedListener(textWatcherCEP);
        textWatcherCelularFone = MascaraCampos.insert("(##)#####-####", txtTelefoneContato);
        txtTelefoneContato.addTextChangedListener(textWatcherCelularFone);
        textWatcherCelularFone = MascaraCampos.insert("(##)#####-####", txtCelular);
        txtCelular.addTextChangedListener(textWatcherCelularFone);
        textWatcherCelularFone = MascaraCampos.insert("(##)####-####", txtFixo);
        txtFixo.addTextChangedListener(textWatcherCelularFone);

        //Esconde o label de erro
        lblErroTipoDeEndereco.setVisibility(View.GONE);
    }

    @Override
    public void carregaDados(View viewTela) throws Exception
    {
        //Preenche combo de tipo de endereço
        preencheComboTipoEndereco();
    }

    /**
     * Preenche o combo de tipo de endereço
     */
    private void preencheComboTipoEndereco() throws Exception
    {
        ArrayAdapter<Combos> dataAdapter = null;

        //Carrega os dados
        arrComboTipoEndereco = new ArrayList<Combos>();
        arrComboTipoEndereco.add(new Combos("", ""));
        arrComboTipoEndereco.add(new Combos("ALA", "ALA"));
        arrComboTipoEndereco.add(new Combos("AV", "AVENIDA"));
        arrComboTipoEndereco.add(new Combos("BR", "BR"));
        arrComboTipoEndereco.add(new Combos("CNJ", "CONJUNTO"));
        arrComboTipoEndereco.add(new Combos("COND", "CONDOMÍNIO"));
        arrComboTipoEndereco.add(new Combos("ED", "EDIFÍCIO"));
        arrComboTipoEndereco.add(new Combos("FAZ", "FAZENDA"));
        arrComboTipoEndereco.add(new Combos("LOT", "LOTEAMENTO"));
        arrComboTipoEndereco.add(new Combos("LUG", "LUG"));
        arrComboTipoEndereco.add(new Combos("PC", "PRAÇA"));
        arrComboTipoEndereco.add(new Combos("PI", "PI"));
        arrComboTipoEndereco.add(new Combos("POV", "POVOADO"));
        arrComboTipoEndereco.add(new Combos("QD", "QUADRA"));
        arrComboTipoEndereco.add(new Combos("RES", "RESIDENCIAL"));
        arrComboTipoEndereco.add(new Combos("RUA", "RUA"));
        arrComboTipoEndereco.add(new Combos("STI", "SÍTIO"));
        arrComboTipoEndereco.add(new Combos("TV", "TRAVESSA"));
        arrComboTipoEndereco.add(new Combos("VL", "VILA"));

        //Cria o adapter
        dataAdapter = new ArrayAdapter<Combos>(this.getActivity(), android.R.layout.simple_spinner_item, arrComboTipoEndereco);

        //Seta o adapter no dropdown
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //Atacha o adapter ao controle
        cboTipoDeEndereco.setAdapter(dataAdapter);
    }

    /**
     * Carrega os campos da tela com base no JSON de dados
     */
    public void carregaCamposTela(Enderecos enderecos) throws Exception
    {
        //Seta os valores dos campos
        txtLogradouro.setText(enderecos.sLogradouro);
        txtNumero.setText(enderecos.sNumero);
        txtComplemento.setText(enderecos.sComplemento);
        txtPontoDeReferencia.setText(enderecos.sPontoReferencia);
        txtEmail.setText(enderecos.sEmail);
        txtBairro.setText(enderecos.sBairro);
        txtCidade.setText(enderecos.sCidade);
        txtCEP.setText(enderecos.sCEP);
        txtTelefoneContato.setText(enderecos.sFone2);
        txtCelular.setText(enderecos.sFone1);
        txtFixo.setText(enderecos.sFixo);

        //Posiciona os combos
        cboTipoDeEndereco.setSelection(Apoio.retornaPosicaoItemCombo(arrComboTipoEndereco, enderecos.sTipoEndereco));
    }

    /**
     * Remove os erros dos campos da tela
     */
    public void removerErroDosCampos() throws Exception
    {
        //Tira o erro
        inputLogradouro.setError("");
        inputLogradouro.setErrorEnabled(false);
        inputNumero.setError("");
        inputNumero.setErrorEnabled(false);
        inputBairro.setError("");
        inputBairro.setErrorEnabled(false);
        inputCidade.setError("");
        inputCidade.setErrorEnabled(false);
        inputCEP.setError("");
        inputCEP.setErrorEnabled(false);
        inputTelefoneContato.setError("");
        inputTelefoneContato.setErrorEnabled(false);
        lblErroTipoDeEndereco.setVisibility(View.GONE);
    }

    /**
     * Valida os campos da tela
     */
    public boolean validaCampos(AppCompatActivity activity, ViewPager viewPager) throws Exception
    {
        //Valida o combo de cor/raça
        if (cboTipoDeEndereco.getSelectedItemPosition() <= 0)
        {
            //Coloca a mensagem de erro embaixo do campo e solicita o foco
            lblErroTipoDeEndereco.setText(getString(R.string.msg_atualizacao_cad_enderecos_tipo_nao_selecionado));
            lblErroTipoDeEndereco.setVisibility(View.VISIBLE);
            viewPager.setCurrentItem(1, true);
            Apoio.requisitarFoco(cboTipoDeEndereco, activity);
            return false;
        }
        else
        {
            //Se estier OK, remove o erro
            lblErroTipoDeEndereco.setText("");
            lblErroTipoDeEndereco.setVisibility(View.GONE);
        }

        //Valida o logradouro
        if (txtLogradouro.getText().toString().trim().length() <= 0)
        {
            //Coloca a mensagem de erro embaixo do campo e solicita o foco
            inputLogradouro.setError(getString(R.string.msg_atualizacao_cad_enderecos_logradouro_em_branco));
            inputLogradouro.setErrorEnabled(true);
            viewPager.setCurrentItem(1, true);
            Apoio.requisitarFoco(txtLogradouro, activity);
            return false;
        }
        else
        {
            //Se estier OK, remove o erro
            inputLogradouro.setError("");
            inputLogradouro.setErrorEnabled(false);
        }

        //Valida o número
        if (txtNumero.getText().toString().trim().length() <= 0)
        {
            //Coloca a mensagem de erro embaixo do campo e solicita o foco
            inputNumero.setError(getString(R.string.msg_atualizacao_cad_enderecos_numero_em_branco));
            inputNumero.setErrorEnabled(true);
            viewPager.setCurrentItem(1, true);
            Apoio.requisitarFoco(txtNumero, activity);
            return false;
        }
        else
        {
            //Se estier OK, remove o erro
            inputNumero.setError("");
            inputNumero.setErrorEnabled(false);
        }

        //Valida o bairro
        if (txtBairro.getText().toString().trim().length() <= 0)
        {
            //Coloca a mensagem de erro embaixo do campo e solicita o foco
            inputBairro.setError(getString(R.string.msg_atualizacao_cad_enderecos_bairro_em_branco));
            inputBairro.setErrorEnabled(true);
            viewPager.setCurrentItem(1, true);
            Apoio.requisitarFoco(txtBairro, activity);
            return false;
        }
        else
        {
            //Se estier OK, remove o erro
            inputBairro.setError("");
            inputBairro.setErrorEnabled(false);
        }

        //Valida a cidade
        if (txtCidade.getText().toString().trim().length() <= 0)
        {
            //Coloca a mensagem de erro embaixo do campo e solicita o foco
            inputCidade.setError(getString(R.string.msg_atualizacao_cad_enderecos_cidade_em_branco));
            inputCidade.setErrorEnabled(true);
            viewPager.setCurrentItem(1, true);
            Apoio.requisitarFoco(txtCidade, activity);
            return false;
        }
        else
        {
            //Se estier OK, remove o erro
            inputCidade.setError("");
            inputCidade.setErrorEnabled(false);
        }

        //Valida o CEP
        if (txtCEP.getText().toString().trim().length() <= 0)
        {
            //Coloca a mensagem de erro embaixo do campo e solicita o foco
            inputCEP.setError(getString(R.string.msg_atualizacao_cad_enderecos_cep_em_branco));
            inputCEP.setErrorEnabled(true);
            viewPager.setCurrentItem(1, true);
            Apoio.requisitarFoco(txtCEP, activity);
            return false;
        }
        else
        {
            //Se estier OK, remove o erro
            inputCEP.setError("");
            inputCEP.setErrorEnabled(false);
        }

        //Valida o CEP
        if (txtTelefoneContato.getText().toString().trim().length() <= 0)
        {
            //Coloca a mensagem de erro embaixo do campo e solicita o foco
            inputTelefoneContato.setError(getString(R.string.msg_atualizacao_cad_enderecos_telefone_contato_em_branco));
            inputTelefoneContato.setErrorEnabled(true);
            viewPager.setCurrentItem(1, true);
            Apoio.requisitarFoco(txtTelefoneContato, activity);
            return false;
        }
        else
        {
            //Se estier OK, remove o erro
            inputTelefoneContato.setError("");
            inputTelefoneContato.setErrorEnabled(false);
        }

        return true;
    }

    /**
     * Retorna os dados da tela
     */
    public Enderecos retornaDadosTela() throws Exception
    {
        Enderecos enderecos = null;
        Combos combos = null;
        String sAuxi = "";

        //Inicia a model de endereços
        enderecos = new Enderecos();

        //Seta os valores dos campos
        enderecos.sLogradouro = txtLogradouro.getText().toString().trim().toUpperCase();
        enderecos.sNumero = txtNumero.getText().toString().trim().toUpperCase();
        enderecos.sComplemento = txtComplemento.getText().toString().trim().toUpperCase();
        enderecos.sPontoReferencia = txtPontoDeReferencia.getText().toString().trim().toUpperCase();
        enderecos.sEmail = txtEmail.getText().toString().trim().toUpperCase();
        enderecos.sBairro = txtBairro.getText().toString().trim().toUpperCase();
        enderecos.sCidade = txtCidade.getText().toString().trim().toUpperCase();

        //Tira os caracteres do CEP
        sAuxi = txtCEP.getText().toString().trim().toUpperCase();
        sAuxi = sAuxi.replace("-", "");
        enderecos.sCEP = sAuxi;

        //Tira os caracteres do telefone de contato
        sAuxi = txtTelefoneContato.getText().toString().trim().toUpperCase();
        sAuxi = sAuxi.replace("(", "");
        sAuxi = sAuxi.replace(")", "");
        sAuxi = sAuxi.replace("-", "");
        enderecos.sFone2 = sAuxi;

        //Tira os caracteres do telefone de celular
        sAuxi = txtCelular.getText().toString().trim().toUpperCase();
        sAuxi = sAuxi.replace("(", "");
        sAuxi = sAuxi.replace(")", "");
        sAuxi = sAuxi.replace("-", "");
        enderecos.sFone1 = sAuxi;

        //Tira os caracteres do telefone fixo
        sAuxi = txtFixo.getText().toString().trim().toUpperCase();
        sAuxi = sAuxi.replace("(", "");
        sAuxi = sAuxi.replace(")", "");
        sAuxi = sAuxi.replace("-", "");
        enderecos.sFixo = sAuxi;

        //Obtem o tipo de endereço
        if ( cboTipoDeEndereco.getSelectedItemPosition() < 0 )
        {
            enderecos.sTipoEndereco = "";
        }
        else
        {
            combos = arrComboTipoEndereco.get(cboTipoDeEndereco.getSelectedItemPosition());
            enderecos.sTipoEndereco = combos.sCodigo;
        }

        return enderecos;
    }

    /**
     * Limpa os campos da tela
     */
    public void limpaCamposTela() throws Exception
    {
        //Limpa os campos
        txtLogradouro.setText("");
        txtNumero.setText("");
        txtComplemento.setText("");
        txtPontoDeReferencia.setText("");
        txtEmail.setText("");
        txtBairro.setText("");
        txtCidade.setText("");
        txtCEP.setText("");
        txtTelefoneContato.setText("");
        txtCelular.setText("");
        txtFixo.setText("");
        cboTipoDeEndereco.setSelection(0);
    }
}
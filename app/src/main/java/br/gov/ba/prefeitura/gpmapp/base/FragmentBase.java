package br.gov.ba.prefeitura.gpmapp.base;

import android.os.Build;
import android.support.v4.app.Fragment;
import android.view.View;
import br.gov.ba.prefeitura.gpmapp.R;
import br.gov.ba.prefeitura.gpmapp.util.Apoio;
import br.gov.ba.prefeitura.gpmapp.util.DialogAlerta;
import br.gov.ba.prefeitura.gpmapp.util.LogTrace;

/**
 * A classe base dos fragmentos da aplicação.<br>
 * Esta classe possui todos os métodos obrigatórios exigidos pelo padrão
 * onde todos eles são chamados e tratados após a chamada do método <b>iniciaFluxoDados(view)</b>
 * no final do método <b>onCreateView()</b></b></b>.<br><br>
 *
 * Basta implementar os métodos obrigatórios e os eventos da
 * classe, caso seja necessário.
 */
public abstract class FragmentBase extends Fragment implements IComunicacaoGeral
{
    /**
     * Chame este método ao final do método onCreateView() para iniciar o fluxo de dados automático
     * dos métodos da classe.
     * @param viewTela O root view da tela. Normalmente é o layout inflado
     *                 dentro do método onCreateView().
     */
    public void iniciaFluxoDados(View viewTela) throws Exception
    {
        //Inicia todos os controles da tela
        iniciaControles(viewTela);

        //carrega os dados nas variaveis e controles
        carregaDados(viewTela);

        //Se a versão for maior ou igual ao lolipop, define as animações
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            //chama o método para definir os elementos compartilhados
            definirSharedElements();
        }
    }

    /**
     * O método utilizado para iniciar os controles da tela.
     * Este é usado para encontrar as views na tela e para atribuir
     * listeners aos views.<br><br>
     *
     * O método será chamado automáticamente pelo método iniciaFluxoDados().<br>
     * <b>NÃO CHAME ESTE MÉTODO MANUALMENTE</b>.
     */
    public abstract void iniciaControles(View viewTela) throws Exception;

    /**
     * Usado para atribuir dados aos controles da tela.
     * Este método é usado para definir valores para as variaveis
     * e para as views da tela. Chame todos os métodos para
     * preenchimento de dados aqui.<br><br>
     *
     * O método será chamado automáticamente pelo método iniciaFluxoDados().<br>
     * <b>NÃO CHAME ESTE MÉTODO MANUALMENTE</b>.
     */
    public abstract void carregaDados(View viewTela) throws Exception;

    /**
     * Usado para definir os elementos compartilhados entre telas.
     * Este método é usado para definir o transition name de uma view para realizar a animação
     * entre duas ou mais telas.<br><br>
     *
     * O método será chamado automáticamente pelo método OnCreate e a verificação da versão
     * (>= Lolipop) é feita automáticamente. Não é necessário tratar erros.<br>
     * Declare a anotação @TargetApi(Build.VERSION_CODES.LOLLIPOP) ao usar o método.<br>
     * <b>NÃO CHAME ESTE MÉTODO MANUALMENTE</b>.
     */
    public void definirSharedElements() throws Exception
    {
    }

    /**
     * Quando uma activity ou componente enviar dados para um fragmento, este método será chamado automáticamente.
     * Para identificar a activity ou componente que está chamando, basta comparar
     * o objeto classe com a classe que deseja.<br>
     *     Ex. <b>if (classe == FrmPrincipal.class)</b> {}<br><br>
     *
     * Este método será chamado automáticamente quando o método comunicaComFragment()
     * da interface IComunicacaoFragment for chamado por um componente externo.<br>
     * <b>NÃO CHAME ESTE MÉTODO MANUALMENTE</b><br><br>
     *
     * Não é necessário realizar o tratamento de erros.
     * @param classe classe que chamou o método
     * @param iId Se houver multiplos métodos chamando a comunicação com o
     *            fragmento, use um ID para identificar o botão/método que
     *            está chamando.
     * @param bResultado O resultado da operação/task (se houver)
     * @param oObjetos Dados adicionais podem ser passados por parametros caso necessário
     */
    public void onReceiveData(Class classe, int iId, boolean bResultado, Object... oObjetos) throws Exception
    {
    }

    @Override
    public void comunicaGeral(Class classe, int iId, boolean bResultado, Object... oObjetos)
    {
        try
        {
            //Chama o método para que a activity trate, caso deseje.
            onReceiveData(classe, iId, bResultado, oObjetos);
        }
        catch (Exception err)
        {
            LogTrace.escreve(Apoio.localErro(getClass(), err), Apoio.getPathLogsSDCard(getContext()), Apoio.getArqErr());
            DialogAlerta.show(getContext(), Apoio.getMsgErr(getClass(), err), getString(R.string.atencao), getString(R.string.ok));
        }
    }
}

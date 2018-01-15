package br.com.ca.gpmcaieapp.base;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import br.com.ca.gpmcaieapp.R;
import br.com.ca.gpmcaieapp.util.Apoio;
import br.com.ca.gpmcaieapp.util.DialogAlerta;
import br.com.ca.gpmcaieapp.util.LogTrace;

/**
 * A classe base das activities da aplicação.<br>
 * Esta classe possui todos os métodos obrigatórios exigidos pelo padrão
 * onde todos eles são chamados e tratados automáticamente.<br><br>
 *
 * Basta implementar os métodos obrigatórios e os eventos da
 * classe, caso seja necessário.
 */
public abstract class ActivityBase extends AppCompatActivity implements IComunicacaoGeral
{
    @Override
    protected void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);

        try
        {
            //Obtem os parametros da intent
            obtemParametros();

            //Inicia todos os controles da tela
            iniciaControles();

            //carrega os dados nas variaveis e controles
            carregaDados();

            //Se a versão for maior ou igual ao lolipop, define as animações
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            {
                //chama o método para definir os elementos compartilhados
               definirSharedElements();
            }
        }
        catch (Exception err)
        {
            LogTrace.escreve(Apoio.localErro(getClass(), err), Apoio.getPathLogsSDCard(this), Apoio.getArqErr());
            DialogAlerta.show(this, Apoio.getMsgErr(getClass(), err), getString(R.string.atencao), getString(R.string.ok));
        }
    }

    /**
     * O método utilizado para iniciar os controles da tela.
     * Este é usado para encontrar as views na tela e para atribuir
     * listeners aos views.<br><br>
     *
     * O método será chamado automáticamente pelo método OnCreate().<br>
     * <b>NÃO CHAME ESTE MÉTODO MANUALMENTE</b>.
     */
    public abstract void iniciaControles() throws Exception;

    /**
     * Usado para atribuir dados aos controles da tela.
     * Este método é usado para definir valores para as variaveis
     * e para as views da tela. Chame todos os métodos para
     * preenchimento de dados aqui.<br><br>
     *
     * O método será chamado automáticamente pelo método OnCreate().<br>
     * <b>NÃO CHAME ESTE MÉTODO MANUALMENTE</b>.
     */
    public abstract void carregaDados() throws Exception;

    /**
     * Usado para obter os parametros da intent.
     * Este método é usado para obter as variaveis da intent e atribui-las
     * á variaveis das classes.<br><br>
     *
     * O método será chamado automáticamente pelo método OnCreate().<br>
     * <b>NÃO CHAME ESTE MÉTODO MANUALMENTE</b>.
     */
    public void obtemParametros() throws Exception
    {
    }

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
     * Adiciona um fragmento em um layout especifico na tela.<br><br>
     * Este método é normalmente utilizado quando desejar utilizar fragmentos
     * sem a necessidade de transição com outros fragmentos (em um viewpager por exemplo).
     * Basta utilizar este método para adicionar uma nova instancia de um fragmento
     * dentro de um layout.
     *
     * @param iIdContainer O id do layout a ser utilizado (R.id.lnlMeuLayout por exemplo)
     * @param fragment Uma nova instância do fragmento (MeuFramento.novaInstancia() por exemplo)
     * @throws Exception
     */
    public void adicionarFragmento(int iIdContainer, Fragment fragment) throws Exception
    {
        FragmentManager fragmentManager = null;

        //Obtem o fragment manager
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(iIdContainer, fragment).commit();
    }

    /**
     * Quando um componente chamar a comunicação com a activity, este método será chamado automáticamente.
     * Para identificar a classe que está chamando, basta comparar
     * o objeto classe com a classe que deseja.<br>
     *     Ex. <b>if (classe == TaskImportaDados.class)</b> {}<br><br>
     *
     * Este método será chamado automáticamente quando o método comunicaComActivity()
     * da interface IComunicacaoActivity for chamado por um componente externo.<br>
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
    public final void comunicaGeral(Class classe, int iId, boolean bResultado, Object... oObjetos)
    {
        try
        {
            //Chama o método para que a activity trate, caso deseje.
            onReceiveData(classe, iId, bResultado, oObjetos);
        }
        catch (Exception err)
        {
            LogTrace.escreve(Apoio.localErro(getClass(), err), Apoio.getPathLogsSDCard(this), Apoio.getArqErr());
            DialogAlerta.show(this, Apoio.getMsgErr(getClass(), err), getString(R.string.atencao), getString(R.string.ok));
        }
    }
}
package br.gov.ba.prefeitura.gpmapp.base;

/**
 * Usado por tasks, fragmentos e qualquer outro componente do android que deseje
 * se comunicar diretamente com uma activity atráves do método onReceiveData().
 */
public interface IComunicacaoGeral
{
    /**
     * Chame este método para se comunicar diretamente com a activity através do método
     * onReceiveData().
     * @param classe A classe que está chamando (use o this.getClass())
     * @param iId Se houver multiplos métodos chamando a comunicação com o
     *            fragmento, use um ID para identificar o botão/método que
     *            está chamando.
     * @param bResultado O resultado da operação/task (se houver)
     * @param oObjetos Objetos extras podem ser enviados caso desejado.
     */
    void comunicaGeral(Class classe, int iId, boolean bResultado, Object... oObjetos);
}

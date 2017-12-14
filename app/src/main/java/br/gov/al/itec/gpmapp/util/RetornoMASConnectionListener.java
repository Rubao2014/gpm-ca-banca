package br.gov.al.itec.gpmapp.util;

import com.ca.mas.foundation.MASConnectionListener;
import java.net.HttpURLConnection;

/**
 * Classe para extender o callback (connection listener) e retornar a interface para a classe que fez a chamada
 */
public class RetornoMASConnectionListener implements MASConnectionListener
{
    @Override
    public void onObtained(HttpURLConnection httpURLConnection)
    {
        //Seta o timeout de comunicação
        httpURLConnection.setConnectTimeout(Apoio.TIME_OUT_COMUNICACAO*1000);
        httpURLConnection.setReadTimeout(Apoio.TIME_OUT_COMUNICACAO*1000);
    }

    @Override
    public void onConnected(HttpURLConnection httpURLConnection)
    {
    }
}
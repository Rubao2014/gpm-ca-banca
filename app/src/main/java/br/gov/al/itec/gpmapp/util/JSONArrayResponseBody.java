package br.gov.al.itec.gpmapp.util;

import com.ca.mas.foundation.MASResponseBody;
import org.json.JSONArray;
import org.json.JSONException;

/**
 * Classe da CA para conversão do objeto de resposta para JSON Array
 */
public class JSONArrayResponseBody extends MASResponseBody<JSONArray>
{
    @Override
    public JSONArray getContent()
    {
        try
        {
            return new JSONArray(new String(getRawContent()));
        }
        catch (JSONException e)
        {
            throw new RuntimeException(e);
        }
    }
}

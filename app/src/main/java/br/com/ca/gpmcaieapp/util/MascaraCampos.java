package br.com.ca.gpmcaieapp.util;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

/**
 * Classe para mascara de campos
 */
public class MascaraCampos
{
    public static String unmask(String s) {
        return s.replaceAll("[.]", "").replaceAll("[-]", "")
                .replaceAll("[/]", "").replaceAll("[(]", "")
                .replaceAll("[)]", "");
    }

    public static TextWatcher insert(final String mask, final EditText ediTxt) {
        return new TextWatcher() {
            boolean isUpdating;
            String old = "";

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                String str = MascaraCampos.unmask(s.toString());
                String mascara = "";
                if (isUpdating) {
                    old = str;
                    isUpdating = false;
                    return;
                }
                int i = 0;
                for (char m : mask.toCharArray()) {
                    if (m != '#' && str.length() > old.length()) {
                        mascara += m;
                        continue;
                    }
                    //try {
                    if ( i < str.length() )
                    {
                        mascara += str.charAt(i);
                    }
                    else
                    {
                        break;
                    }
                    //} catch (Exception e) {
                    //    break;
                    //}
                    i++;
                }
                isUpdating = true;
                ediTxt.setText(mascara);
                ediTxt.setSelection(mascara.length());
            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            public void afterTextChanged(Editable s) {
            }
        };
    }
}

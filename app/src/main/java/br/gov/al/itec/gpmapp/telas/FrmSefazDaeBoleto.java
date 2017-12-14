package br.gov.al.itec.gpmapp.telas;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.net.URLDecoder;
import java.util.EnumMap;
import java.util.Map;

import br.gov.al.itec.gpmapp.R;
import br.gov.al.itec.gpmapp.base.ActivityBase;
import br.gov.al.itec.gpmapp.util.Apoio;
import br.gov.al.itec.gpmapp.util.DialogAlerta;
import br.gov.al.itec.gpmapp.util.LogTrace;


import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

/**
 * Classe FrmFinalizado
 * {
 "razaoSocial": "",
 "mensagem": "N�O RECEBER APOS O VENCIMENTO",
 "receita": "41   - Taxa de Expediente",
 "strBarcode": "85660000001010002902017111028688360041000381",
 "barcode": "?UBdddnnd�feGofVX$d)dgQ?",
 "msgRetorno": ""
 }
 */
public class FrmSefazDaeBoleto extends ActivityBase implements View.OnClickListener
{
    //Controles da classe
    private Toolbar toolbar = null;
    private TextView lblTituloTela = null;
    private ImageView imgStatus = null;
    private TextView lblMensagemTela = null;
    private Button cmdFinalizar = null;
    private TextView lblRazaoSocialBoleto = null;
    private TextView lblMensagemBoleto = null;
    private TextView lblCodigoReceitaBoleto = null;
    private TextView lblCodigoBarrasBoleto = null;
    private ImageView ivBarCode = null;
    private CardView cvBoleto = null;
    private LinearLayout llBoleto = null;

    //Variáveis da classe
    private boolean bTipoMensagem = false;
    private String sMensagem = "";
    private String sTitulo = "";
    private String sRazaoSocialBoleto = "";
    private String sMensagemBoleto = "";
    private String sMensagemRetorno = "";
    private String sBarCode = "";
    private String sCodReceita = "";

    // barcode image
    String barcode_data = "123456";
    Bitmap bitmap = null;
    private static final int WHITE = 0xFFFFFFFF;
    private static final int BLACK = 0xFF000000;

    public Bitmap encodeAsBitmap(String contents, BarcodeFormat format, int img_width, int img_height) throws WriterException {
        String contentsToEncode = contents;
        if (contentsToEncode == null) {
            return null;
        }
        Map<EncodeHintType, Object> hints = null;
        String encoding = guessAppropriateEncoding(contentsToEncode);
        if (encoding != null) {
            hints = new EnumMap<EncodeHintType, Object>(EncodeHintType.class);
            hints.put(EncodeHintType.CHARACTER_SET, encoding);
        }
        MultiFormatWriter writer = new MultiFormatWriter();
        BitMatrix result;
        try {
            result = writer.encode(contentsToEncode, format, img_width, img_height, hints);
        } catch (IllegalArgumentException iae) {
            // Unsupported format
            return null;
        }
        int width = result.getWidth();
        int height = result.getHeight();
        int[] pixels = new int[width * height];
        for (int y = 0; y < height; y++) {
            int offset = y * width;
            for (int x = 0; x < width; x++) {
                pixels[offset + x] = result.get(x, y) ? BLACK : WHITE;
            }
        }

        Bitmap bitmap = Bitmap.createBitmap(width, height,
                Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }

    private static String guessAppropriateEncoding(CharSequence contents) {
        // Very crude at the moment
        for (int i = 0; i < contents.length(); i++) {
            if (contents.charAt(i) > 0xFF) {
                return "UTF-8";
            }
        }
        return null;
    }
    @Override
    protected void onCreate(Bundle bundleSavedInstanceState)
    {
        // Carrega a tela
        setContentView(R.layout.frm_sefaz_dae_boleto);

        //chama os métodos automáticos da classe pai
        super.onCreate(bundleSavedInstanceState);
    }

    @Override
    public void iniciaControles() throws Exception
    {
        Drawable drawableArrow = null;

        // Obtem os controles
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        lblTituloTela = (TextView) findViewById(R.id.lblTituloTela);
        //imgStatus = (ImageView) findViewById(R.id.imgStatus);
        lblMensagemTela = (TextView) findViewById(R.id.lblMensagemTela);
        cmdFinalizar = (Button) findViewById(R.id.cmdFinalizar);


        lblRazaoSocialBoleto = (TextView) findViewById(R.id.txtRazaoSocialBoleto);
        lblMensagemBoleto = (TextView) findViewById(R.id.txtMensagemBoleto);
        lblCodigoReceitaBoleto = (TextView) findViewById(R.id.txtCodigoReceitaBoleto);
        lblCodigoBarrasBoleto = (TextView) findViewById(R.id.txtCodigoBarrasBoleto);
        ivBarCode = (ImageView) findViewById(R.id.imageView);
        llBoleto = (LinearLayout) findViewById(R.id.llBoleto);

        Typeface custom_font = Typeface.createFromAsset(this.getAssets(),  "fonts/code25I.ttf");
        lblCodigoBarrasBoleto.setTypeface(custom_font);



        //Muda a cor da flecha padrão do Android para branco
        drawableArrow = ContextCompat.getDrawable(this, R.drawable.abc_ic_ab_back_material);
        drawableArrow.setColorFilter(ContextCompat.getColor(this, R.color.branco), PorterDuff.Mode.SRC_ATOP);

        //Seta o navigation item
        toolbar.setNavigationIcon(drawableArrow);

        //Troca a cor do botao de voltar
        toolbar.setNavigationContentDescription(Apoio.TOOLBAR_VOLTAR);

        //Seta o listener dos controles
        cmdFinalizar.setOnClickListener(this);

        //Coloca a toolbar como actionbar e adiciona as opções de menu
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public void carregaDados() throws Exception
    {
        //Seta o titulo da janela
        toolbar.setTitle(sTitulo);

        //Se for sucesso
        if (( bTipoMensagem ) && !(sBarCode.equals("")))
        {
            //Mostra a imagem de sucesso no imageview e seta os campos
            lblTituloTela.setText(getString(R.string.label_sefazdae_boleto_sucesso));
            lblTituloTela.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));
            lblMensagemTela.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));
            //imgStatus.setImageResource(R.mipmap.sucesso);
            llBoleto.setVisibility(LinearLayout.VISIBLE);
        }
        else
        {
            //Mostra a imagem de erro no imageview e seta os campos
            lblTituloTela.setText(getString(R.string.label_sefazdae_boleto_insucesso));
            lblTituloTela.setTextColor(ContextCompat.getColor(this, R.color.vermelho));
            lblMensagemTela.setTextColor(ContextCompat.getColor(this, R.color.vermelho));
            //imgStatus.setImageResource(R.mipmap.erro);
            //Toast.makeText(this, sMensagem, Toast.LENGTH_LONG).show();
            llBoleto.setVisibility(LinearLayout.GONE);

        }

        //Seta a mensagem
        lblMensagemTela.setText(sMensagem);
        lblMensagemBoleto.setText(sMensagemBoleto);
        lblRazaoSocialBoleto.setText(sRazaoSocialBoleto);
        lblCodigoBarrasBoleto.setText(sBarCode);
        lblCodigoReceitaBoleto.setText(sCodReceita);

        try {

            bitmap = encodeAsBitmap(sBarCode, BarcodeFormat.CODE_128, 200, 50);
            ivBarCode.setImageBitmap(bitmap);

        } catch (WriterException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void obtemParametros() throws Exception
    {
        Intent intent;

        super.obtemParametros();

        //Obtem os parametros
        intent = this.getIntent();
        bTipoMensagem = intent.getBooleanExtra(Apoio.TELA_SUCESSO_TIPO_MENSAGEM, false);
        //sMensagem = intent.getStringExtra(Apoio.TELA_SUCESSO_MENSAGEM);
        sTitulo = intent.getStringExtra(Apoio.TELA_SUCESSO_TITULO);

        sMensagemBoleto = URLDecoder.decode(intent.getStringExtra(Apoio.PREFS_SEFAZ_MENSAGEM_BOLETO), "ISO-8859-1");
        sMensagem = sMensagemRetorno = URLDecoder.decode(intent.getStringExtra(Apoio.PREFS_SEFAZ_MENSAGEM_RETORNO_SERVICO), "ISO-8859-1");
        sRazaoSocialBoleto = intent.getStringExtra(Apoio.PREFS_SEFAZ_RAZAO_SOCIAL);
        sBarCode = intent.getStringExtra(Apoio.PREFS_SEFAZ_CODIGO_BARRAS);
        sCodReceita = intent.getStringExtra(URLDecoder.decode(Apoio.PREFS_SEFAZ_CODIGO_TRIBUTARIO, "ISO-8859-1"));


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
            }
        }
        catch (Exception err)
        {
            LogTrace.escreve(Apoio.localErro(getClass(), err), Apoio.getPathLogsSDCard(this), Apoio.getArqErr());
            DialogAlerta.show(this, Apoio.getMsgErr(getClass(), err), getString(R.string.atencao), getString(R.string.ok));
        }

        return true;
    }

    @Override
    public void onClick(View view)
    {
        try
        {
            //Quando apertar o botão finalizar
            if (view == cmdFinalizar)
            {
                //Se for sucesso
                if ( bTipoMensagem )
                {
                    //Finaliza para voltar para o login
                    Apoio.finalizaActivity(this, RESULT_OK);
                }
                else
                {
                    //Finaliza para voltar para o login
                    Apoio.finalizaActivity(this, RESULT_CANCELED);
                }
            }
        }
        catch (Exception err)
        {
            LogTrace.escreve(Apoio.localErro(getClass(), err), Apoio.getPathLogsSDCard(this), Apoio.getArqErr());
            DialogAlerta.show(this, Apoio.getMsgErr(getClass(), err), getString(R.string.atencao), getString(R.string.ok));
        }
    }
}
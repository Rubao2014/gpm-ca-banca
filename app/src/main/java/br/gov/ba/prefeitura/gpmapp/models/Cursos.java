package br.gov.ba.prefeitura.gpmapp.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Classe de cursos
 */
public class Cursos implements Serializable
{
    //Variáveis da classe
    @SerializedName("cur_sequencial_curso")
    public String sSequencialCurso = "";

    @SerializedName("cur_data_conclusao_curso")
    public String sDataConclusaoCurso = "";

    @SerializedName("cur_descricao_curso")
    public String sDescricaoCurso = "";

    @SerializedName("cur_primeira_formacao_curso")
    public String sPrimeiraFormacaoCurso = "";

    @SerializedName("cur_tipo_curso")
    public String sTipoCurso = "";

    @SerializedName("cur_local_curso")
    public String sLocalCurso = "";

    @SerializedName("cur_carga_horaria_curso")
    public String sCargaHorariaCurso = "";
}

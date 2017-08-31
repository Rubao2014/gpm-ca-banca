package br.gov.ba.prefeitura.gpmapp.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Classe de dependentes
 */
public class Dependentes implements Serializable
{
    //Variáveis da classe
    @SerializedName("dep_sequencial")
    public String sSequencial = "";

    @SerializedName("dep_sexo")
    public String sSexo = "";

    @SerializedName("dep_naturalidade")
    public String sNaturalidade = "";

    @SerializedName("dep_nome")
    public String sNome = "";

    @SerializedName("dep_dependente_sal_familia")
    public String sDependenteSalFamilia = "";

    @SerializedName("dep_data_nascimento")
    public String sDataNascimento = "";

    @SerializedName("dep_dependente_ass_medica")
    public String sDependenteAssMedica = "";

    @SerializedName("dep_universitario")
    public String sUniversitario = "";

    @SerializedName("dep_tipo_dependente_id")
    public String sTipoDependenteID = "";

    @SerializedName("dep_cpf_dependente")
    public String sCPFDependente = "";

    @SerializedName("dep_nacionalidade")
    public String sNacionalidade = "";

    @SerializedName("dep_motivo_fim_dependencia_id")
    public String sMotivoFimDependenciaID = "";

    @SerializedName("dep_data_fim_dependencia")
    public String sDataFimDependencia = "";

    @SerializedName("dep_dependente_imp_renda")
    public String sDependenteImpRenda = "";
}

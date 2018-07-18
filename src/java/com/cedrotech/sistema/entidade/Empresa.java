/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cedrotech.sistema.entidade;

import java.util.Date;
import java.util.Objects;

/**
 *
 * @author notle
 */
public class Empresa {

    private Integer codigoDaEmpresa;
    private String nomeDaEmpresa, codigoCNPJ;
    private Date dataCadastro, dataDesativacao, dataFiltroInicio, dataFiltroFim;

    public Integer getCodigoDaEmpresa() {
        return codigoDaEmpresa;
    }

    public void setCodigoDaEmpresa(Integer codigoDaEmpresa) {
        this.codigoDaEmpresa = codigoDaEmpresa;
    }

    public String getNomeDaEmpresa() {
        return nomeDaEmpresa;
    }

    public void setNomeDaEmpresa(String nomeDaEmpresa) {
        this.nomeDaEmpresa = nomeDaEmpresa;
    }

    public String getCodigoCNPJ() {
        return codigoCNPJ;
    }

    public void setCodigoCNPJ(String codigoCNPJ) {
        this.codigoCNPJ = codigoCNPJ;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public Date getDataDesativacao() {
        return dataDesativacao;
    }

    public void setDataDesativacao(Date dataDesativacao) {
        this.dataDesativacao = dataDesativacao;
    }

    public Date getDataFiltroInicio() {
        return dataFiltroInicio;
    }

    public void setDataFiltroInicio(Date dataFiltroInicio) {
        this.dataFiltroInicio = dataFiltroInicio;
    }

    public Date getDataFiltroFim() {
        return dataFiltroFim;
    }

    public void setDataFiltroFim(Date dataFiltroFim) {
        this.dataFiltroFim = dataFiltroFim;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + Objects.hashCode(this.codigoDaEmpresa);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Empresa other = (Empresa) obj;
        if (!Objects.equals(this.codigoDaEmpresa, other.codigoDaEmpresa)) {
            return false;
        }
        return true;
    }

}

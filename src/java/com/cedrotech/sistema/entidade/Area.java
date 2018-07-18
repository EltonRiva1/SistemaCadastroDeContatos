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
public class Area {

    private Integer codigoDaArea;
    private String descricaoDaArea;
    private Date dataCadastro, dataDesativacao, dataFiltroInicio, dataFiltroFim;

    public Integer getCodigoDaArea() {
        return codigoDaArea;
    }

    public void setCodigoDaArea(Integer codigoDaArea) {
        this.codigoDaArea = codigoDaArea;
    }

    public String getDescricaoDaArea() {
        return descricaoDaArea;
    }

    public void setDescricaoDaArea(String descricaoDaArea) {
        this.descricaoDaArea = descricaoDaArea;
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

    public Date getDataFiltroFim() {
        return dataFiltroFim;
    }

    public void setDataFiltroFim(Date dataFiltroFim) {
        this.dataFiltroFim = dataFiltroFim;
    }

    public Date getDataFiltroInicio() {
        return dataFiltroInicio;
    }

    public void setDataFiltroInicio(Date dataFiltroInicio) {
        this.dataFiltroInicio = dataFiltroInicio;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.codigoDaArea);
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
        final Area other = (Area) obj;
        if (!Objects.equals(this.codigoDaArea, other.codigoDaArea)) {
            return false;
        }
        return true;
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cedrotech.sistema.bean;

import com.cedrotech.sistema.dao.CrudDAO;
import com.cedrotech.sistema.util.exception.ErroSistema;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author notle
 */
public abstract class CrudBean<E, D extends CrudDAO> {

    private String estadoTela = "buscar";
    private E entidade;
    private E entidadeBusca;
    private D dao;
    private Class<E> classeEntidade;
    private List<E> entidades = new ArrayList<>();

    public CrudBean() {
        entidade = criarNovaEntidade();
        entidadeBusca = criarNovaEntidade();
    }

    public void novo() {
        entidade = criarNovaEntidade();
        mudarParaInseri();
    }

    public void salvar() {
        try {
            entidade = (E) getDao().salvar(entidade);
            if (entidades != null) {
                int posicao = 0;
                if (entidades.contains(entidade)) {
                    posicao = entidades.indexOf(entidade);
                    entidades.remove(posicao);
                }
                entidades.add(posicao, entidade);
            }
            entidade = criarNovaEntidade();
            adicionarMensagem("Salvo com sucesso!", FacesMessage.SEVERITY_INFO);
            buscar();
        } catch (ErroSistema ex) {
            Logger.getLogger(CrudBean.class.getName()).log(Level.SEVERE, null, ex);
            adicionarMensagem(ex.getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }

    public void editar(E entidade) {
        this.entidade = entidade;
        mudarParaEdita();
    }

    public void deletar(E entidade) {
        try {
            getDao().deletar(entidade);
            entidades.remove(entidade);
            adicionarMensagem("Deletado com sucesso!", FacesMessage.SEVERITY_INFO);
        } catch (ErroSistema ex) {
            Logger.getLogger(CrudBean.class.getName()).log(Level.SEVERE, null, ex);
            adicionarMensagem(ex.getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }

    public void buscar() {
        if (isBusca() == false) {
            mudarParaBusca();
            return;
        }
        try {
            entidades = getDao().buscar(entidadeBusca);
            if (entidades == null) {
                entidades = new ArrayList<>();
            } else if (entidades.size() < 1) {
                adicionarMensagem("Não temos nada cadastrado!", FacesMessage.SEVERITY_WARN);
            }
        } catch (ErroSistema ex) {
            Logger.getLogger(CrudBean.class.getName()).log(Level.SEVERE, null, ex);
            adicionarMensagem(ex.getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }

    public void adicionarMensagem(String mensagem, FacesMessage.Severity tipoErro) {
        FacesMessage fm = new FacesMessage(tipoErro, mensagem, null);
        FacesContext.getCurrentInstance().addMessage(null, fm);
    }

    public E getEntidade() {
        return entidade;
    }

    public void setEntidade(E entidade) {
        this.entidade = entidade;
    }

    public E getEntidadeBusca() {
        return entidadeBusca;
    }

    public void setEntidadeBusca(E entidadeBusca) {
        this.entidadeBusca = entidadeBusca;
    }

    public List<E> getEntidades() {
        return entidades;
    }

    public void setEntidades(List<E> entidades) {
        this.entidades = entidades;
    }

    public E criarNovaEntidade() {
        try {
            return getClassEntidade().newInstance();
        } catch (InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(CrudBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public Class<E> getClassEntidade() {
        if (classeEntidade == null) {
            classeEntidade = (Class<E>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        }
        return classeEntidade;
    }

    public D getDao() {
        if (dao == null) {
            try {
                Class<D> classeDao = (Class<D>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
                dao = classeDao.newInstance();
            } catch (InstantiationException | IllegalAccessException ex) {
                Logger.getLogger(CrudBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return dao;
    }

    public boolean isInseri() {
        return "inserir".equals(estadoTela);
    }

    public boolean isEdita() {
        return "editar".equals(estadoTela);
    }

    public boolean isBusca() {
        return "buscar".equals(estadoTela);
    }

    public void mudarParaInseri() {
        estadoTela = "inserir";
    }

    public void mudarParaEdita() {
        estadoTela = "editar";
    }

    public void mudarParaBusca() {
        estadoTela = "buscar";
    }
}

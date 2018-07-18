/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cedrotech.sistema.dao;

import com.cedrotech.sistema.util.exception.ErroSistema;
import java.util.List;

/**
 *
 * @author notle
 */
public interface CrudDAO<E> {

    public E salvar(E entidade) throws ErroSistema;

    public E getUltimoSalvo() throws ErroSistema;

    public void deletar(E entidade) throws ErroSistema;

    public List<E> buscar(E entidade) throws ErroSistema;

}

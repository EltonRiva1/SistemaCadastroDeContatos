/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cedrotech.sistema.bean;

import com.cedrotech.sistema.dao.AreaDAO;
import com.cedrotech.sistema.entidade.Area;
import java.util.Date;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author notle
 */
@ManagedBean
@SessionScoped
public class AreaBean extends CrudBean<Area, AreaDAO> {

    public Date getDataAtual() {
        return new Date();
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cedrotech.sistema.dao;

import com.cedrotech.sistema.entidade.Area;
import com.cedrotech.sistema.util.Assert;
import com.cedrotech.sistema.util.DataUtil;
import com.cedrotech.sistema.util.FabricaConexao;
import com.cedrotech.sistema.util.exception.ErroSistema;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author notle
 */
public class AreaDAO implements CrudDAO<Area> {

    @Override
    public Area salvar(Area area) throws ErroSistema {
        try {
            if (Assert.isStringNullOrEmpty(area.getDescricaoDaArea())) {
                throw new ErroSistema("Informe o nome da área.");
            }
            if (validarAreaExistente(area.getDescricaoDaArea(), area.getCodigoDaArea())) {
                throw new ErroSistema("Área já cadastrada.");
            }
            if (area.getDataCadastro() == null) {
                throw new ErroSistema("Por favor, preencha a Descrição da Área e Data Cadastro.");
            }
            Connection conexao = FabricaConexao.getConexao();
            PreparedStatement ps;
            if (area.getCodigoDaArea() == null) {
                ps = conexao.prepareStatement("INSERT INTO `area` (`descricaoDaArea`,`dataCadastro`, `data_desativacao`) VALUES (?,?,?)");
            } else {
                ps = conexao.prepareStatement("update area set descricaoDaArea=?, dataCadastro=?, data_desativacao=? where codigoDaArea=?");
                ps.setInt(4, area.getCodigoDaArea());
            }
            ps.setString(1, area.getDescricaoDaArea());
            ps.setDate(2, new Date(area.getDataCadastro().getTime()));
            Date dataDesativacao = null;
            if (area.getDataDesativacao() != null) {
                dataDesativacao = new Date(area.getDataDesativacao().getTime());
            }
            ps.setDate(3, dataDesativacao);
            ps.execute();
            if (area.getCodigoDaArea() == null) {
                area = getUltimoSalvo();
            }
            FabricaConexao.fecharConexao();
            return area;
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao tentar salvar!", ex);
        }
    }

    @Override
    public Area getUltimoSalvo() throws ErroSistema {
        try {
            PreparedStatement ps = FabricaConexao.getConexao().prepareStatement("SELECT * FROM area WHERE codigoDaArea = LAST_INSERT_ID()");
            ResultSet resultSet = ps.executeQuery();
            Area area = null;
            if (resultSet.next()) {
                area = new Area();
                area.setCodigoDaArea(resultSet.getInt("codigoDaArea"));
                area.setDescricaoDaArea(resultSet.getString("descricaoDaArea"));
                area.setDataCadastro(resultSet.getDate("dataCadastro"));
            }
            return area;
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao buscar o ultimo codigo salvo");
        }
    }

    @Override
    public void deletar(Area area) throws ErroSistema {
        area.setDataDesativacao(new java.util.Date());
        salvar(area);
    }

    public boolean validarAreaExistente(String descricaoDaArea, Integer codigoDaArea) throws ErroSistema {
        try {
            boolean jaExiste = false;
            Connection conexao = FabricaConexao.getConexao();
            PreparedStatement ps;
            if (codigoDaArea == null) {
                ps = conexao.prepareStatement("select * from area where descricaoDaArea = ?");
            } else {
                ps = conexao.prepareStatement("select * from area where descricaoDaArea = ? and codigoDaArea <> ?");
                ps.setInt(2, codigoDaArea);
            }
            ps.setString(1, descricaoDaArea);
            ResultSet resultSet = ps.executeQuery();
            jaExiste = resultSet.next();
            FabricaConexao.fecharConexao();
            return jaExiste;
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao buscar as áreas!", ex);
        }
    }

    @Override
    public List<Area> buscar(Area areaFiltro) throws ErroSistema {
        try {
            if (areaFiltro.getDataFiltroInicio() == null || areaFiltro.getDataFiltroFim() == null) {
                throw new ErroSistema("Para buscar, preencha a Data Início e a Data Fim.");
            }
            Connection conexao = FabricaConexao.getConexao();
            PreparedStatement ps = conexao.prepareStatement("select * from area where data_desativacao is null and dataCadastro between ? and ?");
            java.sql.Date dataInicio = new java.sql.Date(DataUtil.setTempoInicioDia(areaFiltro.getDataFiltroInicio()).getTime());
            java.sql.Date dataFim = new java.sql.Date(DataUtil.setTempoInicioDia(areaFiltro.getDataFiltroFim()).getTime());
            ps.setDate(1, new java.sql.Date(areaFiltro.getDataFiltroInicio().getTime()));
            ps.setDate(2, new java.sql.Date(areaFiltro.getDataFiltroFim().getTime()));
            ResultSet resultSet = ps.executeQuery();
            List<Area> areas = new ArrayList<>();
            while (resultSet.next()) {
                Area area = new Area();
                area.setCodigoDaArea(resultSet.getInt("codigoDaArea"));
                area.setDescricaoDaArea(resultSet.getString("descricaoDaArea"));
                area.setDataCadastro(resultSet.getDate("dataCadastro"));
                areas.add(area);
            }
            FabricaConexao.fecharConexao();
            return areas;
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao buscar as áreas!", ex);
        }
    }

}

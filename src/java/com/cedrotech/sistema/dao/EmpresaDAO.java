/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cedrotech.sistema.dao;

import com.cedrotech.sistema.entidade.Empresa;
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
public class EmpresaDAO implements CrudDAO<Empresa> {

    @Override
    public Empresa salvar(Empresa empresa) throws ErroSistema {
        try {
            if (!Assert.isCnpjValido(empresa.getCodigoCNPJ())) {
                throw new ErroSistema("Informe o código CNPJ valido.");
            }
            if (validarCNPJExistente(empresa.getCodigoCNPJ(), empresa.getCodigoDaEmpresa())) {
                throw new ErroSistema("CNPJ já cadastrado.");
            }
            if (empresa.getDataCadastro() == null) {
                throw new ErroSistema("Por favor, preencha todos os campos.");
            }
            Connection conexao = FabricaConexao.getConexao();
            PreparedStatement ps;
            if (empresa.getCodigoDaEmpresa() == null) {
                ps = conexao.prepareStatement("INSERT INTO `empresa` (`nomeDaEmpresa`,`codigoCNPJ`, `dataCadastro`,`data_desativacao`) VALUES (?,?,?,?)");
            } else {
                ps = conexao.prepareStatement("update empresa set nomeDaEmpresa=?, codigoCNPJ=?, dataCadastro=?, data_desativacao=? where codigoDaEmpresa=?");
                ps.setInt(5, empresa.getCodigoDaEmpresa());
            }
            ps.setString(1, empresa.getNomeDaEmpresa());
            ps.setString(2, empresa.getCodigoCNPJ());
            ps.setDate(3, new Date(empresa.getDataCadastro().getTime()));
            Date dataDesativacao = null;
            if (empresa.getDataDesativacao() != null) {
                dataDesativacao = new Date(empresa.getDataDesativacao().getTime());
            }
            ps.setDate(4, dataDesativacao);
            ps.execute();
            if (empresa.getCodigoDaEmpresa() == null) {
                empresa = getUltimoSalvo();
            }
            FabricaConexao.fecharConexao();
            return empresa;
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao tentar salvar!", ex);
        }
    }

    @Override
    public Empresa getUltimoSalvo() throws ErroSistema {
        try {
            PreparedStatement ps = FabricaConexao.getConexao().prepareStatement("SELECT * FROM empresa WHERE codigoDaEmpresa = LAST_INSERT_ID();");
            ResultSet resultSet = ps.executeQuery();
            Empresa empresa = null;
            if (resultSet.next()) {
                empresa = new Empresa();
                empresa.setCodigoDaEmpresa(resultSet.getInt("codigoDaEmpresa"));
                empresa.setNomeDaEmpresa(resultSet.getString("nomeDaEmpresa"));
                empresa.setCodigoCNPJ(resultSet.getString("codigoCNPJ"));
                empresa.setDataCadastro(resultSet.getDate("dataCadastro"));
            }
            return empresa;
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao buscar o ultimo codigo salvo");
        }
    }

    @Override
    public void deletar(Empresa empresa) throws ErroSistema {
        empresa.setDataDesativacao(new java.util.Date());
        salvar(empresa);
    }

    public boolean validarCNPJExistente(String codigoCNPJ, Integer codigoDaEmpresa) throws ErroSistema {
        try {
            boolean jaExiste = false;
            Connection conexao = FabricaConexao.getConexao();
            PreparedStatement ps;
            if (codigoDaEmpresa == null) {
                ps = conexao.prepareStatement("select * from empresa where codigoCNPJ = ?");
            } else {
                ps = conexao.prepareStatement("select * from empresa where codigoCNPJ = ? and codigoDaEmpresa <> ?");
                ps.setInt(2, codigoDaEmpresa);
            }
            ps.setString(1, codigoCNPJ);
            ResultSet resultSet = ps.executeQuery();
            jaExiste = resultSet.next();
            FabricaConexao.fecharConexao();
            return jaExiste;
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao buscar os CNPJ!", ex);
        }
    }

    @Override
    public List<Empresa> buscar(Empresa empresaFiltro) throws ErroSistema {
        try {
            if (empresaFiltro.getDataFiltroInicio() == null || empresaFiltro.getDataFiltroFim() == null) {
                throw new ErroSistema("Para buscar, preencha a Data Início e a Data Fim.");
            }
            Connection conexao = FabricaConexao.getConexao();
            PreparedStatement ps = conexao.prepareStatement("select * from empresa where data_desativacao is null and dataCadastro between ? and ?");
            java.sql.Date dataInicio = new java.sql.Date(DataUtil.setTempoInicioDia(empresaFiltro.getDataFiltroInicio()).getTime());
            java.sql.Date dataFim = new java.sql.Date(DataUtil.setTempoInicioDia(empresaFiltro.getDataFiltroFim()).getTime());
            ps.setDate(1, new java.sql.Date(empresaFiltro.getDataFiltroInicio().getTime()));
            ps.setDate(2, new java.sql.Date(empresaFiltro.getDataFiltroFim().getTime()));
            ResultSet resultSet = ps.executeQuery();
            List<Empresa> empresas = new ArrayList<>();
            while (resultSet.next()) {
                Empresa empresa = new Empresa();
                empresa.setCodigoDaEmpresa(resultSet.getInt("codigoDaEmpresa"));
                empresa.setNomeDaEmpresa(resultSet.getString("nomeDaEmpresa"));
                empresa.setCodigoCNPJ(resultSet.getString("codigoCNPJ"));
                empresa.setDataCadastro(resultSet.getDate("dataCadastro"));
                empresas.add(empresa);
            }
            FabricaConexao.fecharConexao();
            return empresas;
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao buscar as empresas!", ex);
        }
    }
}

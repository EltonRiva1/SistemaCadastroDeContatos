/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cedrotech.sistema.dao;

import com.cedrotech.sistema.entidade.Contato;
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
public class ContatoDAO implements CrudDAO<Contato> {

    @Override
    public Contato salvar(Contato contato) throws ErroSistema {
        try {
            if (!Assert.isCpf(contato.getCodigoCPF())) {
                throw new ErroSistema("Informe o código CPF valido.");
            }
            if (validarCPFExistente(contato.getCodigoCPF(), contato.getCodigoDoContato())) {
                throw new ErroSistema("CPF já cadastrado.");
            }
            if (contato.getDataCadastro() == null) {
                throw new ErroSistema("Por favor, preencha todos os campos.");
            }
            Connection conexao = FabricaConexao.getConexao();
            PreparedStatement ps;
            if (contato.getCodigoDoContato() == null) {
                ps = conexao.prepareStatement("INSERT INTO `contato` (`nomeDoContato`,`codigoCPF`, `telResidencial`, `telCelular`, `emailContato`, `dataCadastro`, `data_desativacao`) VALUES (?,?,?,?,?,?,?)");
            } else {
                ps = conexao.prepareStatement("update contato set nomeDoContato=?, codigoCPF=?, telResidencial=?, telCelular=?, emailContato=?, dataCadastro=?, data_desativacao=? where codigoDoContato=?");
                ps.setInt(8, contato.getCodigoDoContato());
            }
            ps.setString(1, contato.getNomeDoContato());
            ps.setString(2, contato.getCodigoCPF());
            ps.setString(3, contato.getTelResidencial());
            ps.setString(4, contato.getTelCelular());
            ps.setString(5, contato.getEmailContato());
            ps.setDate(6, new Date(contato.getDataCadastro().getTime()));
            Date dataDesativacao = null;
            if (contato.getDataDesativacao() != null) {
                dataDesativacao = new Date(contato.getDataDesativacao().getTime());
            }
            ps.setDate(7, dataDesativacao);
            ps.execute();
            if (contato.getCodigoDoContato() == null) {
                contato = getUltimoSalvo();
            }
            FabricaConexao.fecharConexao();
            return contato;
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao tentar salvar!", ex);
        }
    }

    @Override
    public Contato getUltimoSalvo() throws ErroSistema {
        try {
            PreparedStatement ps = FabricaConexao.getConexao().prepareStatement("SELECT * FROM contato WHERE codigoDoContato = LAST_INSERT_ID();");
            ResultSet resultSet = ps.executeQuery();
            Contato contato = null;
            if (resultSet.next()) {
                contato = new Contato();
                contato.setCodigoDoContato(resultSet.getInt("codigoDoContato"));
                contato.setNomeDoContato(resultSet.getString("nomeDoContato"));
                contato.setCodigoCPF(resultSet.getString("codigoCPF"));
                contato.setTelResidencial(resultSet.getString("telResidencial"));
                contato.setTelCelular(resultSet.getString("telCelular"));
                contato.setEmailContato(resultSet.getString("emailContato"));
                contato.setDataCadastro(resultSet.getDate("dataCadastro"));
            }
            return contato;
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao buscar o ultimo codigo salvo");
        }
    }

    @Override
    public void deletar(Contato contato) throws ErroSistema {
        contato.setDataDesativacao(new java.util.Date());
        salvar(contato);
    }

    public boolean validarCPFExistente(String codigoCPF, Integer codigoDoContato) throws ErroSistema {
        try {
            boolean jaExiste = false;
            Connection conexao = FabricaConexao.getConexao();
            PreparedStatement ps;
            if (codigoDoContato == null) {
                ps = conexao.prepareStatement("select * from contato where codigoCPF = ?");
            } else {
                ps = conexao.prepareStatement("select * from contato where codigoCPF = ? and codigoDoContato <> ?");
                ps.setInt(2, codigoDoContato);
            }
            ps.setString(1, codigoCPF);
            ResultSet resultSet = ps.executeQuery();
            jaExiste = resultSet.next();
            FabricaConexao.fecharConexao();
            return jaExiste;
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao buscar os CPF!", ex);
        }
    }

    @Override
    public List<Contato> buscar(Contato contatoFiltro) throws ErroSistema {
        try {
            if (contatoFiltro.getDataFiltroInicio() == null || contatoFiltro.getDataFiltroFim() == null) {
                throw new ErroSistema("Para buscar, preencha a Data Início e a Data Fim.");
            }
            Connection conexao = FabricaConexao.getConexao();
            PreparedStatement ps = conexao.prepareStatement("select * from contato where data_desativacao is null and dataCadastro between ? and ?");
            java.sql.Date dataInicio = new java.sql.Date(DataUtil.setTempoInicioDia(contatoFiltro.getDataFiltroInicio()).getTime());
            java.sql.Date dataFim = new java.sql.Date(DataUtil.setTempoInicioDia(contatoFiltro.getDataFiltroFim()).getTime());
            ps.setDate(1, new java.sql.Date(contatoFiltro.getDataFiltroInicio().getTime()));
            ps.setDate(2, new java.sql.Date(contatoFiltro.getDataFiltroFim().getTime()));
            ResultSet resultSet = ps.executeQuery();
            List<Contato> contatos = new ArrayList<>();
            while (resultSet.next()) {
                Contato contato = new Contato();
                contato.setCodigoDoContato(resultSet.getInt("codigoDoContato"));
                contato.setNomeDoContato(resultSet.getString("nomeDoContato"));
                contato.setCodigoCPF(resultSet.getString("codigoCPF"));
                contato.setTelResidencial(resultSet.getString("telResidencial"));
                contato.setTelCelular(resultSet.getString("telCelular"));
                contato.setEmailContato(resultSet.getString("emailContato"));
                contato.setDataCadastro(resultSet.getDate("dataCadastro"));
                contatos.add(contato);
            }
            FabricaConexao.fecharConexao();
            return contatos;
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao buscar os contatos!", ex);
        }
    }

}

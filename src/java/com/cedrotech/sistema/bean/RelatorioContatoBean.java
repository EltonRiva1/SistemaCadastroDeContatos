/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cedrotech.sistema.bean;

import com.cedrotech.sistema.util.FabricaConexao;
import com.cedrotech.sistema.util.ReportUtil;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author notle
 */
@ManagedBean
@RequestScoped
public class RelatorioContatoBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private Date dataFiltroInicio, dataFiltroFim;
    @Inject
    private FacesContext facesContext;
    @Inject
    private HttpServletResponse response;
    @Inject
    private EntityManager manager;

    public StreamedContent emitir() {
        try {
            Map<String, Object> parametros = new HashMap<>();
            Calendar dataInicio = Calendar.getInstance();
            dataInicio.setTime(dataFiltroInicio);
            Calendar dataFim = Calendar.getInstance();
            dataFim.setTime(dataFiltroFim);
            dataInicio.add(Calendar.DAY_OF_YEAR, 90);
            if (dataInicio.before(dataFim)) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Periodo do relatorio nao pode ser maior que 3 meses.", null));
            }
            parametros.put("data_inicio", this.dataFiltroInicio);
            parametros.put("data_fim", this.dataFiltroFim);
            InputStream relatorio = getClass().getResourceAsStream("/report/relatorioDoContato.jrxml");
            byte[] relatorioPDF = ReportUtil.reportToPDF(FabricaConexao.getConexao(), relatorio, parametros);
            FabricaConexao.fecharConexao();
            InputStream relatorioPDFStream = new ByteArrayInputStream(relatorioPDF);
            return new DefaultStreamedContent(relatorioPDFStream, "application/pdf", "relatorio.pdf");
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Erro ao gerar relatorio. \n" + ex.getMessage(), null));
            ex.printStackTrace();
        }
        return null;
    }

    @NotNull
    public Date getDataFiltroInicio() {
        return dataFiltroInicio;
    }

    public void setDataFiltroInicio(Date dataFiltroInicio) {
        this.dataFiltroInicio = dataFiltroInicio;
    }

    @NotNull
    public Date getDataFiltroFim() {
        return dataFiltroFim;
    }

    public void setDataFiltroFim(Date dataFiltroFim) {
        this.dataFiltroFim = dataFiltroFim;
    }

    public Date getDataAtual() {
        return new Date();
    }
}

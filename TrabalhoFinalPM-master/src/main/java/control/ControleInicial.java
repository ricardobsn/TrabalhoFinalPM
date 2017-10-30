/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.io.File;
import model.ProcessadorPDF;
import view.TelaInicial;

/**
 *
 * @author Rodrigo
 */
public class ControleInicial {
    
    private TelaInicial telaInicial;
    private ProcessadorPDF processadorPDF;
    private File historico;
    
    public ControleInicial (TelaInicial telaInicial){
        this.telaInicial = telaInicial;
        processadorPDF = new ProcessadorPDF();
    }
    
    public File getHistorico(){
        return historico;
    }
    
    public void setHistorico(File historico){
       this.historico = historico; 
    }
    
    public void lerHistorico(){
        processadorPDF.leituraPDF(historico.getPath());
    }
}

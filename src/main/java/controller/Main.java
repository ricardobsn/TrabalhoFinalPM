/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.itextpdf.text.DocumentException;
import org.xml.sax.SAXException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.IOException;
import view.TelaInicial;

/**
 *
 * @author ricardoneves
 */
public class Main {
    
    public Main(){
    }

    public static void main(String[] args) throws IOException, DocumentException, TransformerException, ParserConfigurationException, SAXException {
        ControllerHistorico historico = new ControllerHistorico();
        TelaInicial tela = new TelaInicial(historico);
        tela.setVisible(true);
        
//        historico.leituraPDF(tela.getHistorico().getPath());        
        //String FLUX = "file:////home/ricardobsn/Downloads/grade_curricular.svg";        
        
    }
}

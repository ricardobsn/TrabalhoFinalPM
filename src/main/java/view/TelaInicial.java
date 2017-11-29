/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.ControllerFluxograma;
import controller.ControllerHistorico;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import org.xml.sax.SAXException;

/**
 *
 * @author Rodrigo
 */
public class TelaInicial extends javax.swing.JFrame {

    /**
     * Cria a Telainicial
     */
    private ControllerHistorico controle;
    private File historico;
    private File cronograma;
    
        public TelaInicial(ControllerHistorico controle) {
        initComponents();  
        this.controle = controle;
    }
    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        botaoSelecionarHistorico = new javax.swing.JButton();
        botaoOk = new javax.swing.JButton();
        botaoSelecionarCronograma = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Faculdade");

        botaoSelecionarHistorico.setFont(new java.awt.Font("Rockwell", 1, 18)); // NOI18N
        botaoSelecionarHistorico.setForeground(new java.awt.Color(255, 153, 0));
        botaoSelecionarHistorico.setText("Selecionar Histórico Escolar");
        botaoSelecionarHistorico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoSelecionarHistoricoActionPerformed(evt);
            }
        });

        botaoOk.setFont(new java.awt.Font("Rockwell", 1, 18)); // NOI18N
        botaoOk.setForeground(new java.awt.Color(0, 102, 153));
        botaoOk.setText("OK");
        botaoOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoOkActionPerformed(evt);
            }
        });

        botaoSelecionarCronograma.setFont(new java.awt.Font("Rockwell", 1, 18)); // NOI18N
        botaoSelecionarCronograma.setForeground(new java.awt.Color(153, 0, 0));
        botaoSelecionarCronograma.setText("Selecionar Cronograma");
        botaoSelecionarCronograma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoSelecionarCronogramaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(botaoSelecionarCronograma, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(botaoSelecionarHistorico, javax.swing.GroupLayout.DEFAULT_SIZE, 314, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(164, 164, 164)
                        .addComponent(botaoOk, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(59, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(botaoSelecionarHistorico, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(botaoSelecionarCronograma, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(botaoOk, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(31, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void botaoSelecionarHistoricoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoSelecionarHistoricoActionPerformed

        JFileChooser fileChooser = new JFileChooser(); //Instancia um seletor de arquivos
        FileNameExtensionFilter filtro = new FileNameExtensionFilter ("PDF", "pdf"); //Filtra apenas arquivos em PDF
        fileChooser.setFileFilter (filtro);
        fileChooser.setAcceptAllFileFilterUsed(false);
        int result = fileChooser.showOpenDialog(null);       
        if(result == JFileChooser.APPROVE_OPTION){  
            historico = fileChooser.getSelectedFile(); //adiciona o arquivo selecionado ao file historico  
            botaoSelecionarHistorico.setText("Histórico Selecionado");
        }   
        if(result == JFileChooser.CANCEL_OPTION){    
            return;
        }  
        
    }//GEN-LAST:event_botaoSelecionarHistoricoActionPerformed

    private void botaoOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoOkActionPerformed
        // TODO add your handling code here:
        controle.leituraPDF(historico.getPath()); //Chamada função para ler o PDF adicionado na função anterior        
        cronograma.getParentFile().mkdirs(); //Adiciona pastas para auxiliar a leitura do PDF
        ControllerFluxograma editorFluxograma = new ControllerFluxograma(cronograma, controle.getMapMaterias(), controle.getContOpt(), controle.getContEletiva());
        try {
            editorFluxograma.editFile();
        } catch (IOException ex) {
            Logger.getLogger(TelaInicial.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(TelaInicial.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(TelaInicial.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            editorFluxograma.pathParser();
        } catch (IOException ex) {
            Logger.getLogger(TelaInicial.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerException ex) {
            Logger.getLogger(TelaInicial.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(TelaInicial.class.getName()).log(Level.SEVERE, null, ex);
        }
                       
        JOptionPane.showMessageDialog(this, "Arquivo gerado com sucesso !","OK", JOptionPane.INFORMATION_MESSAGE);
        
        controle.getTelaVisualizacao().ColocarInformacoes(); //Chamada da função de mostra na Tela as informações lidas do PDF
        controle.getTelaVisualizacao().setVisible(true); //Deixa visível a Tela gerada anteriormente
    }//GEN-LAST:event_botaoOkActionPerformed

    private void botaoSelecionarCronogramaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoSelecionarCronogramaActionPerformed
        // TODO add your handling code here:
        JFileChooser fileChooser = new JFileChooser(); //Instancia um seletor de arquivos
        FileNameExtensionFilter filtro = new FileNameExtensionFilter ("SVG", "svg"); //Filtra apenas arquivos em PDF
        fileChooser.setFileFilter (filtro);
        fileChooser.setAcceptAllFileFilterUsed(false);
        int result = fileChooser.showOpenDialog(null);       
        if(result == JFileChooser.APPROVE_OPTION){  
            cronograma = fileChooser.getSelectedFile(); //adiciona o arquivo selecionado ao file cronograma
            botaoSelecionarCronograma.setText("Cronograma Selecionado");
        }   
        if(result == JFileChooser.CANCEL_OPTION){    
            return;
        }
    }//GEN-LAST:event_botaoSelecionarCronogramaActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TelaInicial.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaInicial.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaInicial.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaInicial.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Cria e mostra o formulario */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
            }
        });
    }

    // declaração das variaveis
    private javax.swing.JButton botaoOk;
    private javax.swing.JButton botaoSelecionarCronograma;
    private javax.swing.JButton botaoSelecionarHistorico;
    // Fim da declaração das variaveis
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import view.TelaInicial;

/**
 *
 * @author ricardoneves
 */
public class Main {

    public Main(){
    }


    public static void main(String[] args) throws Exception {
        
        ControllerHistorico historico = new ControllerHistorico();
        TelaInicial tela = new TelaInicial(historico);
        tela.setVisible(true);
    }
}

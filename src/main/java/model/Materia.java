/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author ricardo
 */

public class Materia {

    private String codigo;
    private String situacao;
    
    public Materia(){
        
    }
    
    public Materia(String codigo, String situacao) {
        this.codigo = codigo;
        this.situacao = situacao;
    }

    public String getCodigo() {
        return codigo;
    }
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
    public String getSituacao() {
        return situacao;
    }
    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    @Override
    public String toString() {
        return codigo + " - " + situacao; //To change body of generated methods, choose Tools | Templates.
    }


}
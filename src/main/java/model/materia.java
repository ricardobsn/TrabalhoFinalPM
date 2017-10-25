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

public class materia {
    private String codigo;
    private String situacao;
    
    public materia(){
        
    }
    
    public materia(String codigo, String situacao) {
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
}
package com.gep.forum_alura.infra.erros;

public class IntegrityValidacao extends RuntimeException{
    private static final long serialVersionUID = 1L;
    public IntegrityValidacao(String s){
        super(s);
    }
}



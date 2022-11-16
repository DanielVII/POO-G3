package model.entity;

public class Usuario{
    private String nome,email,senha;
    private int nivel;

    public void setNome(String nome){
        //metodo setNome não permite que a String esteja vazia
        if(nome != null){
            if(nome.isEmpty()){
                System.out.println("String nao pode ser vazia");
            }
            else{this.nome = nome;}
        }
    }
    
    public String getNome(){
        return nome;
    }
    
    public void setEmail(String email){
        //metodo setUser não permite que a String esteja vazia
        if(email != null){
            if(email.isEmpty()){
                System.out.println("String nao pode ser vazia");
            }
            else{this.email = email;}
        }
    }
    
    public String getEmail(){
        return this.email;
    }
    
    public void setSenha(String senha){
        //metodo setSenha não permite qu ela tenha menos que 8 caracteres
        if(senha != null){
            if(senha.length() < 0){
                System.out.println("a senha deve conter no minimo 8 caracteres");
            }
            else{this.senha = senha;}
        }   
    }
    
    public String getSenha(){
        return senha;
    }
    
    public void setNivel(int nivel){
        if(nivel < 0){
            System.out.println("o nivel nao pode ser negativo");
        }
        else{this.nivel = nivel;}
    }

    public int getNivel(){
        return nivel;
    }
    
}

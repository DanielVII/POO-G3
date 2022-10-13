package model.entity;

public class Funcionario{
    private String nome,user,senha;

    public void setNome(String nome){
        //metodo setNome não permite que a String esteja vazia
        if(nome != null){
            if(nome.isEmpty()){
                System.out.println("String nao pode ser vazia");
            }
            else{this.nome = nome;}
        }
    }
    public void setUser(String user){
        //metodo setUser não permite que a String esteja vazia
        if(user != null){
            if(user.isEmpty()){
                System.out.println("String nao pode ser vazia");
            }
            else{this.user = user;}
        }
    }
    public void setSenha(String senha){
        //metodo setSenha não permite qu ela tenha menos que 8 caracteres
        if(senha != null){
            if(senha.length() < 8){
                System.out.println("a senha deve conter no minimo 8 caracteres");
            }
            else{this.senha = senha;}
        }   
    }
    public String getNome(){
        return nome;
    }
    public String getUser(){
        return user;
    }
    public String getSenha(){
        return senha;
    }
}

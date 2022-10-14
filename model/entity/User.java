package model.entity;

public class User{
    private String nome,user,senha;
    private int nivel,id;

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
    public void setNivel(int nivel){
        if(nivel < 0){
            System.out.println("o nivel nao pode ser negativo");
        }
        else{this.nivel = nivel;}
    }
    public void setId(int id){
        if(id < 0){
            System.out.println("o id nao pode ser negativo");
        }
        else{this.id = id;}
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
    public int getNivel(){
        return nivel;
    }
    public int getId(){
        return id;
    }
}

public class Gerente {
    private String nome,user,senha;
    //metodo setNome não permite que a String esteja vazia
    public void setNome(String nome){
        if(nome != null){
            if(nome.isEmpty()){
                System.out.println("String nao pode ser vazia");
            }
            else{this.nome = nome;}
        }
    }
    //metodo setUser não permite que a String esteja vazia
    public void setUser(String user){
        if(user != null){
            if(user.isEmpty()){
                System.out.println("String nao pode ser vazia");
            }
            else{this.user = user;}
        }
    }
    //metodo setSenha não permite qu ela tenha menos que 8 caracteres
    public void setSenha(String senha){
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

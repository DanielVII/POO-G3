public class Funcionario{
    private String nome,user,senha;
    public void setNome(String nome){
        if(nome != null){
            if(nome.isEmpty()){
                System.out.println("String nao pode ser vazia");
            }
            else{this.nome = nome;}
        }
    }
    public void setUser(String user){
        if(user != null){
            if(user.isEmpty()){
                System.out.println("String nao pode ser vazia");
            }
            else{this.user = user;}
        }
    }
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
    public static void main(String args[]){
        Funcionario v = new Funcionario();
        v.setNome(" ");
        v.setUser("welanio");
        v.setSenha("12345678");
        System.out.println(v.getNome());
        System.out.println(v.getUser());
        System.out.println(v.getSenha());
    }
}
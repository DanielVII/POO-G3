public class Tipo {
    private int codigoDoTipo;
    private String nome, formaDeVenda;

    public int getCodigoDoTipo(){
        return this.codigoDoTipo;
    }

    public void setCodigoDoTipo(int cod){
        if(cod > 0){
            this.codigoDoTipo = cod;
        }
    }

    public String getNome(){
        return this.nome;
    }

    public void setNome(String nome){
        if (!nome.isEmpty()){
            this.nome = nome;
        }
    }

    public String getFormaDeVenda(){
        return this.formaDeVenda;
    }

    public void setFormaDeVenda(String formaDeVenda){
        if (formaDeVenda == "kg" || formaDeVenda == "unidade"){
            this.formaDeVenda = formaDeVenda;
        }
    }
}

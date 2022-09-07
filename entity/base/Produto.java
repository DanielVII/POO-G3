package base;

public class Produto {
    private String nome;
    private String marca;
    private String codDeBarras;
    private Double preco;
    private Tipo tipo;

    /*Se espera aqui que seja escolhido anteriormente um produto por meio da pesquisa do site
     * e então seja chamada essa função, a qual vai receber o valor preço do produto em 
     * questão e, após verificação, ele será trocado pelo novo valor
    */

    public void setPreco(Double valor) {
        if (valor > 0){
            this.preco = valor;
        }
    }

    public Double getPreco(){
        return this.preco;
    }

    public void setNome(String nome) {
        if (!nome.isEmpty()){
            this.nome = nome;
        }
    }

    public String getNome(){
        return this.nome;
    }

    public void setMarca(String marca) {
        if (!marca.isEmpty()){
            this.marca = marca;
        }
    }

    public String getMarca(){
        return this.marca;
    }

    public void setCodBarras(String cod) {
        if (!cod.isEmpty()){
            this.codDeBarras = cod;
        }
    }

    public String getCodBarras(){
        return this.codDeBarras;
    }

    public Tipo getTipo(){
        return this.tipo;
    }

    public void setTipo(String nome, String formaDeVenda, int cod){
        this.tipo.setNome(nome);
        this.tipo.setFormaDeVenda(formaDeVenda);
        this.tipo.setCodigoDoTipo(cod);
    }
}

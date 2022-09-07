package Jva;
import java.util.Scanner;
public class Produto {
    private String nome;
    private String marca;
    private String codDeBarras;
    private Double preco;
    /*private Tipo tipo;*/

    /*Se espera aqui que seja escolhido anteriormente um produto por meio da pesquisa do site
     * e então seja chamada essa função, a qual vai receber o valor preço do produto em 
     * questão e, após verificação, ele será trocado pelo novo valor
    */

    public void setpreDouble(Double valor) {
        if (valor < 0){
            System.out.println("Valor inválido!");
        }else{
            this.preco = valor;
        }
    }

    public Double getpreDouble(){
        return preco;
    }

    public void setNome(String nome) {
        if (is.empty(nome)==0){
            System.out.println("Nome inválido!");
        }else{
            this.nome = nome;
        }
    }

    public Double getNome(){
        return nome;
    }

    public void setMarca(String marca) {
        if (is.empty(marca)==0){
            System.out.println("Marca inválido!");
        }else{
            this.marca = marca;
        }
    }

    public Double getMarca(){
        return nome;
    }

public void setCodBarras(String cod) {
        if (is.empty(cod)==0){
            System.out.println("Código inválido!");
        }else{
            this.codDeBarras = cod;
        }
    }

    public Double getCodBarras(){
        return codDeBarras;
    }

    public void setalterarPreco(Double preco){
        System.out.println("Digite o novo valor");
        Scanner newP = new Scanner(System.in);
        preco = newP.nextDouble();
            if (preco < 0){
                System.out.println("Valor Inválido!");
            }else{
                this.preco = preco;
            }
        }
}


package entity;
import java.util.Scanner;

import Produto;

public class Estoque {
    private Produto produto1[] = new Produto[10];
    private Double estoqueKG;
    private int estoqueQuantidade;
    private int id;

    public void setProdutos(Produto produto){
        if (is.empty(produto)==0){
            System.out.println("Produto inválido");
        }else{}
    }

    public Produto getProduto(){
        return produto1[0];
    }

    public void setEstoqueKG(Double valor){
        if (valor < 0){
            System.out.println("Quantidade inválida");
        }else{}
    }

    public Double getEstoqueKG(){
        return estoqueKG;
    }

    public void setEstoqueQuantidade(int valor){
        if (valor < 0){
            System.out.println("Quantidade inválida");
        }else{}
    }

    public int getEstoqueQuantidade(){
        return estoqueQuantidade;
    }

    public void setId(int valor){
        if (valor < 0){
            System.out.println("Id inválido");
        }else{}
    }

    public int getid(){
        return id;
    }

    public void getByMarca(String marca, Produto produto1[]){
        if (is.empty(marca) == 0){
            /*A ideia é que ele percorra o vetor de produtos comparando
             * a marca dada pelo usuário com as marcas dos produtos 
            */
        }
    }

    public void getByCodDeBarra(String cod, Produto produto1[]){
        if (is.empty(cod) == 0){
            /*A ideia é que ele percorra o vetor de produtos comparando
             * a marca dada pelo usuário com os códigos de barra dos produtos 
            */
        }
    }

    public void comprar(Produto produto, Produto produto1[]){
        int quantidadeNova = 0;
        if(is.empty(produto)==0){
            /*Se percorra o array para encontrar o produto, se achado vai aumentar a quantidade em estoque
             * fazendo distinção entre produtos unitários e de peso
            */
            this.estoqueQuantidade = quantidadeNova;
        }
    }

    public void vender(Produto produto, Produto produto1[]){
        int quantidadeNova = 0;
        if(is.empty(produto)==0){
            /*Se percorra o array para encontrar o produto, se achado vai diminuir a quantidade em estoque
             * fazendo distinção entre produtos unitários e de peso
            */
            this.estoqueQuantidade = quantidadeNova;
        }
    }

    public void addNovoProduto(Produto produto1[]){
        Produto produto2 = new Produto();
        produto1[0] = produto2;
        /*O produto adcionado é verificado e analisado, 
        se obedecer os critériso ele é guardado em uma array 
        para ser salvo.
        */
    }

    public void removerProduto(Produto produto1[]){
        System.out.println("Digite o identificador do produto que deseja remover:");
        Scanner newP = new Scanner(System.in);
        String dado = newP.next();
        /*é necessário que aqui ocorra a validação do dado informado e se for validado
         * ele será parametro para procurar na array de produtos o produto correspondente
         */
    }
}


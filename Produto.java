package Jva;
import java.util.Scanner;
public class Produto {
    private String marca;
    private String codDeBarras;
    private Double preco;
    /*private Tipo tipo;*/

    /*Se espera aqui que seja escolhido anteriormente um produto por meio da pesquisa do site
     * e então seja chamada essa função, a qual vai receber o valor preço do produto em 
     * questão e, após verificação, ele será trocado pelo novo valor
     */

    public Double getpreDouble(){
        return preco;
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

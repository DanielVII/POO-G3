package model.entity;

import java.util.ArrayList;
import model.entity.Produto;

public class NotaFiscal{
    private Double total = 0.0;
    private ArrayList<ArrayList<String>> linhas = new ArrayList<ArrayList<String>>();

    public Double getTotal(){
        return this.total;
    } 

    private void addTotal(Double valor){
        if (valor > 0){
            this.total = this.total + valor;
        }
    }

    private void removeTotal(Double valor){
        if (valor > 0){
            this.total = this.total - valor;
        }
    }

    public ArrayList<ArrayList<String>> getLinhas(){
        return this.linhas;
    }

    public void addLinha(Produto produto, int quantidade){
        if (quantidade > 0 && produto.getCodBarras() != null){
            Double totalProduto = quantidade * produto.getPreco();
            ArrayList<String> linha = new ArrayList<String>();

            linha.add(produto.getCodBarras());
            linha.add(produto.getNome());
            linha.add(Integer.toString(quantidade));
            linha.add(produto.getPreco().toString());
            linha.add(totalProduto.toString());

            this.linhas.add(linha);

            this.addTotal(totalProduto);
        }
    }

    public void removeLinha(int posicaoLinha){
        if (posicaoLinha > 0 && posicaoLinha < this.linhas.size()){
            
            String valor = this.linhas.get(posicaoLinha).get(4);
            Double valorTrue = Double.parseDouble(valor);
            this.removeTotal(valorTrue);

            this.linhas.remove(posicaoLinha);
        }
    }

}

package model.entity;
public class Tipo {
    private String nome, formaDeVenda;
    private int id = 0;

    public int getId() {
    	return this.id;
    }
    
    public void setId(int id) {
    	if (id > 0) {
    		this.id = id;
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
    	if (formaDeVenda.equals("q") || formaDeVenda.equals("u")){
    		this.formaDeVenda = formaDeVenda;
    	}
    }
    
}

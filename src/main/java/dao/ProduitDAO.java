package dao;

import java.util.List;

import model.Categorie;
import model.Produit;

public interface ProduitDAO {
	public boolean addProduit(Produit produit);
	public void updateProduit(Produit produit);
	public List<Produit> listProduits();
	public List<Produit> selectCatByKeyword(String keyWord);
	public Produit getProduitById(int id);
	public void removeProduit(int id);
}

package beans;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import dao.CategorieDAO;
import model.Categorie;
import model.Produit;
import service.CategorieDAOImpl;
import service.ProduitDAOImpl;

@ManagedBean(name = "adminProduits", eager = true)
@SessionScoped
public class AdminProduits implements Serializable {
	private static final long serialVersionUID = 1L;

	private List<Produit> allProducts;
	private List<Categorie> filteredProducts;
	private List<Categorie> allCategories;
	private Produit selectedProduct;
	private Categorie selectedCategorie;
	private Produit productToAdd = new Produit();
	private int idCategorie;
	private boolean editMode = false;
	private boolean addMode = false;
	
	private UploadedFile file;
	
	private byte[] files;
	private String destination = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/");
	
	private CategorieDAO categService;
	{
		categService = new CategorieDAOImpl();
	}

	private ProduitDAOImpl prodService;
	{
		prodService = new ProduitDAOImpl();
	}

	@PostConstruct
	public void init() {
		allCategories = getAllCategories();
		allProducts = getAllProducts();
	}

	public void edit() {
		System.out.println("edit clicked");
		selectedCategorie = selectedProduct.getCategorie();
		editMode = true;
		addMode = false;
	}

	public void cancelUpdate() {
		editMode = false;
	}

	public void prepareAdd() {
		addMode = true;
		editMode = false;
	}

	public void cancelAdd() {
		productToAdd = new Produit();
		addMode = false;
	}

	public void addProduct() {
		System.out.println("**************\nCategorie conversion : \n"+selectedCategorie+"\n***************");
		if (productToAdd != null) {
			//productToAdd.setCategorie(categService.getCategorieById(idCategorie));
			productToAdd.setCategorie(selectedCategorie);
			prodService.addProduit(productToAdd);
			System.out.println("Ajout du produit avec Succès");
			addMessage(FacesMessage.SEVERITY_INFO, "Ajout Réussi", "Ajout du produit avec Succès");
		} else {
			addMessage(FacesMessage.SEVERITY_WARN, "Ajout échoué", "Erreur lors de l'ajout du produite");
		}
		addMode = false;
	}
	
	public void fileUpload(FileUploadEvent event) throws IOException {
        FacesMessage message = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");
	    String path = FacesContext.getCurrentInstance().getExternalContext()
	            .getRealPath("/");
	    SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMddHHmmss");
	    String name = fmt.format(new Date())
	            + event.getFile().getFileName().substring(
	                  event.getFile().getFileName().lastIndexOf('.'));
	    File file = new File(path + "photos/" + name);
	    System.out.println(name);
	    if (addMode)
			productToAdd.setPhoto(name);
		if (editMode)
			selectedProduct.setPhoto(name);
	    InputStream is = event.getFile().getInputstream();
	    OutputStream out = new FileOutputStream(file);
	    byte buf[] = new byte[1024];
	    int len;
	    while ((len = is.read(buf)) > 0)
	        out.write(buf, 0, len);
	    is.close();
	    out.close();
	}

	public void updateProduct() {
		if (selectedProduct != null) {
			System.out.println("Updating... => " + selectedProduct);
			selectedProduct.setCategorie(selectedCategorie);
			prodService.updateProduit(selectedProduct);
			System.out.println("Modification du produit avec Succès");
			addMessage(FacesMessage.SEVERITY_INFO, "Modification Réussie", "Modification du produit avec Succès");
		} else {
			addMessage(FacesMessage.SEVERITY_WARN, "Modification échouée", "Erreur lors de la modification du produit");
		}
		editMode = false;
	}

	public void deleteSelectedProduct() {
		prodService.removeProduit(selectedProduct.getIdProduit());
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage("msgDel",
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Produit Supprimé", selectedProduct.toString()));
		allProducts = getAllProducts();
	}

	public List<Categorie> getAllCategories() {
		allCategories = categService.listCategories();
		return allCategories;
	}

	public Produit getProductToAdd() {
		return productToAdd;
	}

	public void setProductToAdd(Produit productToAdd) {
		this.productToAdd = productToAdd;
	}

	public void setAllCategories(List<Categorie> allCategories) {
		this.allCategories = allCategories;
	}

	public List<Produit> getAllProducts() {
		allProducts = prodService.listProduits();
		return allProducts;
	}

	public void setAllProducts(List<Produit> allProducts) {
		this.allProducts = allProducts;
	}

	public Categorie getSelectedCategorie() {
		return selectedCategorie;
	}

	public void setSelectedCategorie(Categorie selectedCategorie) {
		System.out.println("------------- setting slected categorie -------------");
		this.selectedCategorie = selectedCategorie;
	}

	public boolean isEditMode() {
		return editMode;
	}

	public void setEditMode(boolean editMode) {
		this.editMode = editMode;
	}

	public boolean isAddMode() {
		return addMode;
	}

	public void setAddMode(boolean addMode) {
		this.addMode = addMode;
	}

	public List<Categorie> getFilteredProducts() {
		return filteredProducts;
	}

	public void setFilteredProducts(List<Categorie> filteredProducts) {
		this.filteredProducts = filteredProducts;
	}

	public Produit getSelectedProduct() {
		return selectedProduct;
	}

	public void setSelectedProduct(Produit selectedProduct) {
		this.selectedProduct = selectedProduct;
	}

	public int getIdCategorie() {
		return idCategorie;
	}

	public void setIdCategorie(int idCategorie) {
		this.idCategorie = idCategorie;
	}

	public void addMessage(FacesMessage.Severity severity, String summary, String detail) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, summary, detail));
	}
	
	public void copyFile(String fileName, InputStream in) {
		System.out.println("Appel de CopyFile avec : "+fileName+"\nFlux = "+in);
        try {
             // write the inputStream to a FileOutputStream
             OutputStream out = new FileOutputStream(new File(destination + fileName));
           
             int read = 0;
             byte[] bytes = new byte[1024];
           
             while ((read = in.read(bytes)) != -1) {
                 out.write(bytes, 0, read);
             }
             in.close();
             out.flush();
             out.close();
           
             System.out.println("Pièce jointe téléchargée!");
             } catch (IOException e) {
            	 System.out.println("Erreur dans CopyFile !");
            	 System.out.println(e.getMessage());
             }
 }
}

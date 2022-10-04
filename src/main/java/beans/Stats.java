package beans;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.PieChartModel;

import model.Categorie;
import service.CategorieDAOImpl;

@ManagedBean(name="stats")
@SessionScoped
public class Stats implements Serializable {

	private static final long serialVersionUID = 8107634688016550766L;
	
	private BarChartModel produitParCategorieBct;
	private PieChartModel produitParCategoriePCt;
	
	@PostConstruct
    public void init() {
        createModels();
    }
	
	private void createModels() {
		initBarChartModel();
		initPieChartModels();
	}

	private void initPieChartModels() {
		produitParCategoriePCt = new PieChartModel();
		CategorieDAOImpl catService = new CategorieDAOImpl();
		List<Categorie> allCategs = catService.listCategories();
		for (Categorie categorie : allCategs) {
			produitParCategoriePCt.set(categorie.getNomCategorie(), 
					catService.productsCount(categorie));
		}
		produitParCategoriePCt.setTitle("% de Produits par Catégories");
		produitParCategoriePCt.setLegendPosition("w");
		produitParCategoriePCt.setFill(true);
		produitParCategoriePCt.setShowDataLabels(true);
	}

	private void initBarChartModel() {
		produitParCategorieBct = new BarChartModel();
		CategorieDAOImpl catService = new CategorieDAOImpl();
		List<Categorie> allCategs = catService.listCategories();
		for(Categorie categ : allCategs) {
			ChartSeries serie = new ChartSeries();
			serie.setLabel(categ.getNomCategorie());
			serie.set(categ.getNomCategorie(), catService.productsCount(categ));
			produitParCategorieBct.addSeries(serie);
		}
		produitParCategorieBct.setAnimate(true);
		produitParCategorieBct.setZoom(true);
		produitParCategorieBct.setLegendPosition("ne");
	    produitParCategorieBct.setTitle("Nombre de produits par catégorie");
	    Axis axeX = produitParCategorieBct.getAxis(AxisType.X);
        axeX.setTickAngle(45);
	}

	public BarChartModel getProduitParCategorieBct() {
		return produitParCategorieBct;
	}

	public PieChartModel getProduitParCategoriePCt() {
		return produitParCategoriePCt;
	}
}

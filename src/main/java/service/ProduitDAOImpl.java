package service;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;

import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;

import dao.ProduitDAO;
import model.Categorie;
import model.Produit;
import util.HibernateUtil;


public class ProduitDAOImpl implements ProduitDAO {
	private static final Logger logger = Logger.getLogger(CategorieDAOImpl.class.getName());

	private final SessionFactory sessionFactory = getSessionFactory();

	protected SessionFactory getSessionFactory() {
		try {
			return HibernateUtil.getSessionFactory();
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Could not create SessionFactory", e);
			throw new IllegalStateException("Could not create SessionFactory");
		}
	} 

	@Override
	public boolean addProduit(Produit produit) {
		// TODO Auto-generated method stub
		try {
			Session session = this.sessionFactory.getCurrentSession();
			session.beginTransaction();
			session.save(produit);
			session.getTransaction().commit();
			logger.info("Contact saved successfully, Contact Details="+produit);
			return true;
		}catch (Exception e) {
			System.out.println("Error while adding contact\n"+e);
			return false;
		}
	}

	@Override
	public void updateProduit(Produit produit) {
		// TODO Auto-generated method stub
		Session session = this.sessionFactory.getCurrentSession();
		session.beginTransaction();
		session.update(produit);
		session.getTransaction().commit();
		logger.info("Categorie updated successfully, Categorie Details="+produit);
	}
	
	

	@Override
	public List<Produit> selectCatByKeyword(String keyWord) {
		// TODO Auto-generated method stub
		Session session = this.sessionFactory.getCurrentSession();
		session.beginTransaction();
		List<Produit> produitList = session.createQuery("from Produit p WHERE p.designation LIKE '%"+keyWord+"%'").list();
		session.getTransaction().commit();
		
		return produitList;
	}

	@Override
	public List<Produit> listProduits() {
		// TODO Auto-generated method stub
		Session session = this.sessionFactory.getCurrentSession();
		session.beginTransaction();
		List<Produit> produitList = session.createQuery("from Produit").list();
		session.getTransaction().commit();
		
		return produitList;
		
	}

	@Override
	public Produit getProduitById(int id) {
		// TODO Auto-generated method stub
		Session session = this.sessionFactory.getCurrentSession();	
		session.beginTransaction();
		Produit produit = (Produit) session.load(Produit.class, new Integer(id));
		session.getTransaction().commit();
		return produit;
	}

	@Override
	public void removeProduit(int id) {
		// TODO Auto-generated method stub
		Session session = this.sessionFactory.getCurrentSession();
		session.beginTransaction();
		Produit produit = (Produit) session.load(Produit.class, new Integer(id));
		
		if(null != produit){
			session.delete(produit);
		}
		session.getTransaction().commit();
		logger.info("Produit deleted successfully, Categorie details="+produit);
	}
}

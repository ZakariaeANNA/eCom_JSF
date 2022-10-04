package converters;

import java.util.ResourceBundle;

import javax.el.ValueExpression;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import beans.AdminCategories;
import beans.AdminProduits;
import model.Categorie;
import service.CategorieDAOImpl;

@FacesConverter(value = "categorieConverter")
public class CategorieConverter implements Converter{
	@Override
    public Categorie getAsObject(FacesContext ctx, UIComponent uiComponent, String value) {
		if(value == null) {
        	return null;
        }
        return new CategorieDAOImpl().getCategorieById(Integer.parseInt(value));
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
    	 return (value instanceof Categorie) ? String.valueOf(((Categorie) value).getIdCategorie()) : null;    }
}

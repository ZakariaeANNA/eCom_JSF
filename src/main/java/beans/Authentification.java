package beans;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import model.User;

@ManagedBean(name="authentification")
@SessionScoped
public class Authentification implements Serializable{

	private static final long serialVersionUID = 5785804737952806223L;
	
	private String login;
	private String pass;
	private boolean connected=false;
	private User currentUser=new User();
	private String theme="sunny";
	
	public static final String[] POSSIBLE_THEMES =
		{ "afterdark", "afternoon", "afterwork", "aristo",
		"black-tie", "blitzer", "bluesky", "casablanca",
		"cruze", "cupertino", "dark-hive", "dot-luv",
		"eggplant", "excite-bike", "flick", "glass-x",
		"home", "hot-sneaks", "humanity", "le-frog",
		"midnight", "mint-choc", "overcast", "pepper-grinder",
		"redmond", "rocket", "sam", "smoothness",
		"south-street", "start", "sunny", "swanky-purse",
		"trontastic", "twitter bootstrap", "ui-darkness",
		"ui-lightness", "vader" };
		
	
	public Authentification(){
		
	}


	public String getLogin() {
		return login;
	}


	public void setLogin(String login) {
		this.login = login;
	}


	public String getPass() {
		return pass;
	}


	public void setPass(String pass) {
		this.pass = pass;
	}


	public boolean isConnected() {
		return connected;
	}


	public void setConnected(boolean connected) {
		this.connected = connected;
	}


	public User getCurrentUser() {
		return currentUser;
	}


	public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
	}
	
	public String[] getThemes() {
		return(POSSIBLE_THEMES);
	}


	public String getTheme() {
		return theme;
	}


	public void setTheme(String theme) {
		this.theme = theme;
	}
	
	
}

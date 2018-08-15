package com.java.monitoring.jmx.mbean;

public class Stock implements StockMBean {

	  private static String nom = "StockMBean";
	  private int valeur = 100;
	  
	  public String getNom() {
	    return nom;
	  }

	  public int getValeur() {
	    return valeur;
	  }

	  public synchronized void setValeur(int valeur) {
	     this.valeur = valeur;
	  }

	  public void rafraichir() {
	    System.out.println("Rafraichir les donnees");

	  }

	  public Stock() {
	    
	  }
	}

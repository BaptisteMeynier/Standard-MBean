package com.java.monitoring.jmx.client;

import java.io.IOException;
import java.net.MalformedURLException;

import javax.management.MBeanServerConnection;
import javax.management.MBeanServerInvocationHandler;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

import com.java.monitoring.jmx.mbean.StockMBean;

/**
 * 
 * Pour lancer ce main executez la commande :
 * mvn clean compile exec:java -P jmxmp-agent
 * 
 * @author baptiste
 *
 */
public class ClientJMXAvecJMXMP {
  public static void main(String[] args) {
    MBeanServerConnection mbsc = null;
    JMXConnector connecteur = null;

    ObjectName name = null;
    try {
      name = new ObjectName("com.java.monitoring.jmx:type=StockMBean");

      JMXServiceURL url = new JMXServiceURL("service:jmx:jmxmp://localhost:9998");

      connecteur = JMXConnectorFactory.connect(url, null);

      mbsc = connecteur.getMBeanServerConnection();

      StockMBean mbean = (StockMBean) MBeanServerInvocationHandler.newProxyInstance(mbsc, name, StockMBean.class, false);
      int valeur = mbean.getValeur();
      System.out.println("valeur = " + valeur);
      mbean.rafraichir();

    } catch (MalformedObjectNameException e) {
      e.printStackTrace();
    } catch (NullPointerException e) {
      e.printStackTrace();
    } catch (MalformedURLException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (mbsc != null) {
        try {
          connecteur.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }

  }
}


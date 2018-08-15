package com.java.monitoring.jmx.agent;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.net.MalformedURLException;

import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;
import javax.management.remote.JMXConnectorServer;
import javax.management.remote.JMXConnectorServerFactory;
import javax.management.remote.JMXServiceURL;

import com.java.monitoring.jmx.mbean.Stock;

/**
 * Pour utiliser un connecteur RMI, il faut obligatoirement lancer un registre RMI 
 * en lancant la commande:
 * rmiregistry 9000
 * 
 * @author baptiste
 *
 */
public class LancerAgentAvecRMI {
	public static void main(String[] args) {

	    System.out.println("Lancement de l'agent JMX");
	    
	    MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();

	    ObjectName name = null;
	    try {
	      System.out.println("Instanciation et enregistrement du MBean");

	      name = new ObjectName("com.java.monitoring.jmx:type=StockMBean");

	      Stock mbean = new Stock();

	      mbs.registerMBean(mbean, name);

	      // Creation et demarrage du connecteur RMI
	      JMXServiceURL url = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://localhost:9000/server");
	      JMXConnectorServer cs = JMXConnectorServerFactory.newJMXConnectorServer(url, null, mbs);
	      cs.start();
	      System.out.println("Lancement connecteur RMI "+url);

	      int i = 0;
	      System.out.println("Incrementation de la valeur du MBean ...");
	      while (i < 60) {

	        mbean.setValeur(mbean.getValeur() + 1);
	        Thread.sleep(1000);
	        i++;        
	      }

	      System.out.println("Arret connecteur RMI ");
	      cs.stop();
	      
	      System.out.println("Arret de l'agent JMX");
	      
	    } catch (MalformedObjectNameException e) {
	      e.printStackTrace();
	    } catch (NullPointerException e) {
	      e.printStackTrace();
	    } catch (InstanceAlreadyExistsException e) {
	      e.printStackTrace();
	    } catch (MBeanRegistrationException e) {
	      e.printStackTrace();
	    } catch (NotCompliantMBeanException e) {
	      e.printStackTrace();
	    } catch (InterruptedException e) {
	      e.printStackTrace();
	    } catch (MalformedURLException e) {
	      e.printStackTrace();
	    } catch (IOException e) {
	      e.printStackTrace();
	    }
	  }
}

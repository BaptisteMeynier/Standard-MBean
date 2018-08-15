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
 * Nativement java ne supporte pas le protocol jmxmp. 
 * Pour s executer correctement une implementation du protocole doit lui etre fourni
 * Oracle propose une implem via le jar jmxremote_optional.
 * Attention ce jar est sous licence oracle et ne sera pas disponible via maven central 
 * 
 * Erreur sans avoir fourni l implem:
 * java.net.MalformedURLException: Unsupported protocol: jmxmp
 * at javax.management.remote.JMXConnectorServerFactory.newJMXConnectorServer(JMXConnectorServerFactory.java:344)
 * at com.java.monitoring.jmx.agent.LancerAgentAvecJMXMP.main(LancerAgentAvecJMXMP.java:39)
 * 
 * Pour lancer ce main executez la commande :
 * mvn clean compile exec:java -P jmxmp
 * 
 * @author baptiste
 *
 */
public class LancerAgentAvecJMXMP {
	 public static void main(String[] args) {

		    System.out.println("Lancement de l'agent JMX");

		    MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();

		    ObjectName name = null;
		    try {
		      System.out.println("Instanciation et enregistrement du Mbean");

		      name = new ObjectName("com.java.monitoring.jmx:type=StockMBean");

		      Stock mbean = new Stock();

		      mbs.registerMBean(mbean, name);
		      
		      // Creation et demarrage du connecteur pour le protocole JMXMP
		      JMXServiceURL url = new JMXServiceURL("service:jmx:jmxmp://localhost:9998");

		      JMXConnectorServer cs = JMXConnectorServerFactory.newJMXConnectorServer(url, null, mbs);
		      cs.start();
		      
		      System.out.println("Lancement connecteur pour le protocle JMXMP " + url);

		      int i = 0;
		      System.out.println("Incrementation de la valeur du MBean ...");
		      while (i < 6000) {

		        mbean.setValeur(mbean.getValeur() + 1);
		        Thread.sleep(1000);
		        i++;
		      }

		      System.out.println("Arret connecteur pour le protocle JMXMP ");
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

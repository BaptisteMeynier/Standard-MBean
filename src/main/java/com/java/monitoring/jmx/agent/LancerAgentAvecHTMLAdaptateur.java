package com.java.monitoring.jmx.agent;

import java.lang.management.ManagementFactory;

import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;

import com.java.monitoring.jmx.mbean.Stock;
import com.sun.jdmk.comm.HtmlAdaptorServer;

/**
 * To launch
 * mvn clean package exec:java -P html
 * @author baptiste
 *
 */
public class LancerAgentAvecHTMLAdaptateur {
  static final int PORT_ADAPTATEUR = 8000;

  public static void main(String[] args) {

    System.out.println("Lancement de l'agent JMX");

    MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();

    ObjectName name = null;
    ObjectName adapterName = null;

    try {
      System.out.println("Instanciation et enregistrement du MBean");

      name = new ObjectName("com.java.monitoring.jmx:type=StockMBean");

      Stock mbean = new Stock();

      mbs.registerMBean(mbean, name);

      // Creation et demarrage de l'adaptateur de protocole HTML
      HtmlAdaptorServer adapter = new HtmlAdaptorServer();
      adapterName = new ObjectName(
          "com.java.monitoring.jmx:name=htmladaptor,port=" + PORT_ADAPTATEUR);
      adapter.setPort(PORT_ADAPTATEUR);
      mbs.registerMBean(adapter, adapterName);
      adapter.start();
      System.out
          .println("Lancement de l'adaptateur de protocole HTML sur le port "
              + PORT_ADAPTATEUR);

      int i = 0;
      System.out.println("Incrementation de la valeur du MBean ...");
      while (i < 600) {

        mbean.setValeur(mbean.getValeur() + 1);
        Thread.sleep(1000);
        i++;
      }

      System.out.println("Arret de l'adaptateur de protocole HTML ");
      adapter.stop();

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
    }
  }
}

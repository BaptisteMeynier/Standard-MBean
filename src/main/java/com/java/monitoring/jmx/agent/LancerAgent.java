package com.java.monitoring.jmx.agent;

import java.lang.management.ManagementFactory;

import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;

import com.java.monitoring.jmx.mbean.Stock;

public class LancerAgent {

  public static void main(String[] args) {
    MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();

    ObjectName name = null;
    try {
      name = new ObjectName("com.java.monitoring.jmx:type=StockMBean");

      Stock mbean = new Stock();

      mbs.registerMBean(mbean, name);

      System.out.println("Lancement ...");
      while (true) {

        Thread.sleep(1000);
        mbean.setValeur(mbean.getValeur() + 1);
      }
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
    }
  }
}

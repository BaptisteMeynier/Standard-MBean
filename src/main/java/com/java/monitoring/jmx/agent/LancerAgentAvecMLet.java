package com.java.monitoring.jmx.agent;


import java.lang.management.ManagementFactory;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Objects;
import java.util.Set;

import javax.management.Attribute;
import javax.management.AttributeNotFoundException;
import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;
import javax.management.InvalidAttributeValueException;
import javax.management.MBeanException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;
import javax.management.ReflectionException;
import javax.management.ServiceNotFoundException;
import javax.management.loading.MLet;

public class LancerAgentAvecMLet {

	public static void main(String[] args) {
		
		MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();

		ObjectName name = null;
		try {
			name = new ObjectName("com.java.monitoring.jmx:type=StockMBean");

			// Instanciation et enregistrement du Service MLet
			System.out.println("Instanciation et enregistrement du Service MLet");
			MLet mlet = new MLet();
			mbs.registerMBean(mlet, new ObjectName("Services:type=MLet"));

			// Lecture du fichier de configuration pour instanciation et
			// enregistrement du MBean
			System.out.println("\nLecture du fichier de configuration");
			URL mletFile = Thread.currentThread().getContextClassLoader().getResource("mlet.txt");
			Set<Object> mbeans = mlet.getMBeansFromURL(mletFile);
			for (Object obj : mbeans) {
				System.out.println("Object = " + obj);
			}

			System.out.println("\nClasspath du service MLet : "+ Arrays.asList(mlet.getURLs()));

			System.out.println("\nRecherche du mbean enregistré");
			Set<ObjectName> names = mbs.queryNames(name, null);
			for (ObjectName objName : names) {
				System.out.println("ObjectName=" + objName);
			}

			System.out.println("\nExecution de l'agent ...");
			while (true) {

				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
				}

				int valeur = Integer.valueOf(mbs.getAttribute(name, "Valeur")
						.toString());
				Attribute attr = new Attribute("Valeur", valeur + 1);
				mbs.setAttribute(name, attr);
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
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (AttributeNotFoundException e) {
			e.printStackTrace();
		} catch (InstanceNotFoundException e) {
			e.printStackTrace();
		} catch (MBeanException e) {
			e.printStackTrace();
		} catch (ReflectionException e) {
			e.printStackTrace();
		} catch (InvalidAttributeValueException e) {
			e.printStackTrace();
		} catch (ServiceNotFoundException e) {
			e.printStackTrace();
		}
	}
}


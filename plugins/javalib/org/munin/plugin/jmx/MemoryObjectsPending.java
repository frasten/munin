package org.munin.plugin.jmx;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import javax.management.MBeanServerConnection;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.io.FileNotFoundException;
import java.io.IOException;
public class MemoryObjectsPending {

    public static void main(String args[])throws FileNotFoundException,IOException {
        String[] connectionInfo = ConfReader.GetConnectionInfo();

        if (args.length == 1) {
            if (args[0].equals("config")) {
                System.out.println(
                       "graph_title JVM (port " + connectionInfo[1] + ") MemoryObjectsPending\n" +
                       "graph_vlabel objects\n" +
		       "graph_category " + connectionInfo[2] + "\n" +
                       "Objects.info  The approximate number of objects for which finalization is pending.\n" +  
                       "Objects.label Objects\n" 
);
            }
         else {

          try {

            JMXServiceURL u = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://" + connectionInfo[0] + ":" + connectionInfo[1]+ "/jmxrmi");
            JMXConnector c = JMXConnectorFactory.connect(u);
            MBeanServerConnection connection = c.getMBeanServerConnection();

         MemoryMXBean mbean = ManagementFactory.newPlatformMXBeanProxy(connection,ManagementFactory.MEMORY_MXBEAN_NAME, MemoryMXBean.class);

            System.out.println("Objects.value " + mbean.getObjectPendingFinalizationCount());

 } catch (Exception e) {
                System.out.print(e);
            }
        }

    }
}

}
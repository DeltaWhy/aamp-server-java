package net.miscjunk.aamp.server.java;

import java.io.IOException;

import javax.jmdns.JmDNS;
import javax.jmdns.ServiceInfo;

public class Broadcaster {
    ServiceInfo serviceInfo;
    JmDNS jmdns;
    public boolean start() {
        System.out.println("Registering _aamp._tcp");
        serviceInfo = ServiceInfo.create("_aamp._tcp.local.", "AAMP", 13531, "");
        try {
            jmdns = JmDNS.create();
            jmdns.registerService(serviceInfo);
            System.out.println("Registered _aamp._tcp");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean stop() {
        System.out.println("Cleaning up _aamp._tcp");
        if (jmdns != null) {
            jmdns.unregisterAllServices();
            try {
                jmdns.close();
                System.out.println("Cleaned up _aamp._tcp");
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        } else return false;
    }
}

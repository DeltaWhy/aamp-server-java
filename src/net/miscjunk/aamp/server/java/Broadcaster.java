package net.miscjunk.aamp.server.java;

import java.io.IOException;

import javax.jmdns.JmDNS;
import javax.jmdns.ServiceInfo;

public class Broadcaster {
    ServiceInfo serviceInfo;
    JmDNS jmdns;
    public boolean start() {
        serviceInfo = ServiceInfo.create("_aamp._tcp.local.", "AAMP", 13531, "");
        try {
            jmdns = JmDNS.create();
            jmdns.registerService(serviceInfo);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean stop() {
        if (jmdns != null) {
            jmdns.unregisterAllServices();
            try {
                jmdns.close();
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        } else return false;
    }
}

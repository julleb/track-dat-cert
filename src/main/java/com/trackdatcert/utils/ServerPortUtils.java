package com.trackdatcert.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ServerPortUtils {

    public int getPortFromString(String serverAndPort) {
        var split = splitHostAndPort(serverAndPort);
        if (split[1].matches("[0-9]+")) {
            return Integer.parseInt(split[1]);
        } else {
            throw new IllegalArgumentException("Invalid port: " + split[1]);
        }
    }

    public String getHostFromString(String serverAndPort) {
        var split = splitHostAndPort(serverAndPort);
        return split[0];
    }

    private String[] splitHostAndPort(String serverAndPort) {
        var split = serverAndPort.split(":");
        if (split.length != 2) {
            throw new IllegalArgumentException("Invalid Server and port format: " + serverAndPort);
        }
        return split;
    }
}

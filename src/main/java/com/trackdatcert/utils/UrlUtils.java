package com.trackdatcert.utils;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UrlUtils {

    public URL getHttpsUrl(String url) {
        var urlObj = getUrl(url);
        if (!url.startsWith("https://")) {
            throw new IllegalArgumentException("url must start with https://");
        }
        return urlObj;
    }

    public URL getUrl(String url) {
        ObjectUtils.requireNonEmpty(url, "Url cannot be empty");
        try {
            return new URI(url).toURL();
        } catch (URISyntaxException | MalformedURLException e) {
            throw new IllegalArgumentException("Bad url " + url, e);
        }
    }

    public String getHostFromUrl(String url) {
        var urlObj = getUrl(url);
        return urlObj.getHost();
    }

    public int getPortFromUrl(String url) {
        var urlObj = getUrl(url);
        return urlObj.getPort();
    }
}

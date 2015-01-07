package org.kost.android.sensorsweblogger;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SendSensorData {
    protected String addSensorDataToUrl(String url, HashMap parms) {
        if(!url.endsWith("?"))
            url += "?";

        List<NameValuePair> params = new LinkedList<NameValuePair>();

        Set set = parms.entrySet();
        Iterator i = set.iterator();

        while(i.hasNext()) {
            Map.Entry me = (Map.Entry)i.next();
            params.add(new BasicNameValuePair(String.valueOf(me.getKey()),String.valueOf(me.getValue())));
        }

        String paramString = URLEncodedUtils.format(params, "utf-8");

        url += paramString;
        return url;
    }

    public String SendData (String baseurl, HashMap parms) {
        String result = null;
        String logUrl = addSensorDataToUrl(baseurl, parms);
        try {
            URL url = new URL(logUrl);
            URLConnection urlConnection = url.openConnection();
            InputStream in = urlConnection.getInputStream();
        }
        catch(Exception ex) {
            return ("HTTP NO WORK: "+logUrl);
        }
        return null;
    }
}


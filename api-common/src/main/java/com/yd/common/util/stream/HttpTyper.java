package com.yd.common.util.stream;

import com.yd.common.util.Checker;
import com.yd.common.util.properties.Proper;

import java.util.Iterator;
import java.util.Properties;

public class HttpTyper {
    private static final Properties mimeType = Proper.use("mime-types.properties").getProperties();
    private static final String RFC_2616_TOKEN_SPECIAL_CHARS_REGEX = "[\\s\\(\\)<>@,;:\\\\\"/\\[\\]\\?=\\{\\}]";

    public HttpTyper() {
    }

    public static String getContentTypeFromFileName(String filename) {
        String ext = filename.substring(filename.lastIndexOf(46) + 1);
        return mimeType.getProperty(ext);
    }

    public static String getContentTypeFromExtension(String ext) {
        return mimeType.getProperty(ext);
    }

    public static boolean isTextContentType(String contentType) {
        return contentType.startsWith("text/") || contentType.startsWith("application/json") || contentType.startsWith("application/javascript") || contentType.startsWith("application/ecmascript") || contentType.startsWith("application/atom+xml") || contentType.startsWith("application/rss+xml") || contentType.startsWith("application/xhtml+xml") || contentType.startsWith("application/soap+xml") || contentType.startsWith("application/xml");
    }

    public static String charsetFromContentType(String s) {
        return !s.contains("charset=") ? null : s.substring(s.indexOf("charset=") + "charset=".length());
    }

    public static String headerTokenCompatible(String s, String specialCharsReplacement) {
        Checker.checkArgument(specialCharsReplacement.replaceAll("[\\s\\(\\)<>@,;:\\\\\"/\\[\\]\\?=\\{\\}]", "blah").equals(specialCharsReplacement), "specialCharsReplacement `%s` is not itself compatible with rfc 2616 !", new Object[]{specialCharsReplacement});
        return s.replaceAll("[\\s\\(\\)<>@,;:\\\\\"/\\[\\]\\?=\\{\\}]", specialCharsReplacement);
    }

    static {
        Iterator var0 = mimeType.stringPropertyNames().iterator();

        while(var0.hasNext()) {
            String prop = (String)var0.next();
            Iterable<String> types = Lister.of(new Object[]{mimeType.getProperty(prop)});
            Iterator<String> iterator = types.iterator();
            mimeType.setProperty(prop, iterator.hasNext() ? (String)iterator.next() : "application/octet-stream");
        }

    }
}
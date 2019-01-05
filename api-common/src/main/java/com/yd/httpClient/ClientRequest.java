package com.yd.httpClient;

import com.yd.common.util.Maper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.yd.common.util.Checker.checkNotNull;

public class ClientRequest {
    private static final Pattern pattern = Pattern.compile("[^a-zA-Z0-9\\/\\?#&=\\+\\*\\$@!%\\.\\|\\(\\)\\^\\{\\}\\[\\]]");
    private String restPath;
    private String encoding = Encoding.UTF_8.name();
    private Map<String, String> params = Maper.of();
    private String jsonParam;
    private Map<String, String> headers = Maper.of();
    private int connectTimeOut = 5000;
    private int readTimeOut = 5000;
    private boolean overwrite = false;
    private String downloadFile;
    private Map<String, ClientFile> uploadFiles = Maper.of();
    private String contentType = ContentType.FORM.value() + ";charset=" + encoding;
    private String userAgent = "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.146 Safari/537.36";


    public ClientRequest(String restPath) {
        this(restPath, Encoding.UTF_8.name());
    }

    public ClientRequest(String restPath, String encoding) {
        this(restPath, encoding, Maper.<String, String>of());
    }

    public ClientRequest(String restPath, Map<String, String> params) {
        this(restPath, null, params);
    }

    public ClientRequest(String restPath, String encoding, Map<String, String> params) {
        this(restPath, encoding, params, Maper.<String, String>of());
    }

    public ClientRequest(String restPath, String encoding, Map<String, String> params, Map<String, String> headers) {
        this.restPath = checkNotNull(restPath);
        if (encoding != null) {
            this.encoding = encoding;
        }
        this.params = params;
        this.headers = headers;
    }

    public String getRestPath() {
        return this.restPath;
    }

    public String getEncodedRestPath() throws UnsupportedEncodingException {
        String url = this.restPath;
        String tmp;
        Matcher matcher = pattern.matcher(url);
        while (matcher.find()) {
            tmp = matcher.group();
            url = url.replaceAll(tmp, URLEncoder.encode(tmp, encoding));
        }
        return url;
    }

    public String getEncoding() {
        return this.encoding;
    }

    public ClientRequest setEncoding(String encoding) {
        this.encoding = encoding;
        return this;
    }

    public Map<String, String> getParams() {
        return this.params;
    }

    public ClientRequest setParams(Map<String, String> params) {
        this.params = params;
        return this;
    }

    public Map<String, String> getHeaders() {
        return this.headers;
    }

    public ClientRequest setHeaders(Map<String, String> headers) {
        this.headers = headers;
        return this;
    }

    public ClientRequest addParam(String name, String value) {
        this.params.put(name, value);
        return this;
    }

    public String getContentType() {
        return contentType;
    }

    public ClientRequest setContentType(String contentType) {
        this.contentType = contentType;
        return this;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public ClientRequest setUserAgent(String userAgent) {
        this.userAgent = userAgent;
        return this;
    }

    public String getJsonParam() {
        return jsonParam;
    }

    public ClientRequest setJsonParam(String jsonParam) {
        setContentType(ContentType.JSON.value() + ";charset=" + encoding);
        this.jsonParam = checkNotNull(jsonParam, "Json param could not be null.");
        return this;
    }

    public String getEncodedJsonParam() throws UnsupportedEncodingException {
        return URLEncoder.encode(jsonParam, encoding);
    }

    public ClientRequest addHeader(String key, String value) {
        this.headers.put(key, value);
        return this;
    }

    public int getConnectTimeOut() {
        return connectTimeOut;
    }

    public ClientRequest setConnectTimeOut(int connectTimeOut) {
        this.connectTimeOut = connectTimeOut;
        return this;
    }

    public int getReadTimeOut() {
        return readTimeOut;
    }

    public ClientRequest setReadTimeOut(int readTimeOut) {
        this.readTimeOut = readTimeOut;
        return this;
    }

    public boolean isOverwrite() {
        return overwrite;
    }

    public ClientRequest setDownloadFile(String downloadFile, boolean overwrite) {
        this.downloadFile = checkNotNull(downloadFile, "Download file could not be null.");
        this.overwrite = overwrite;
        return this;
    }

    public String getDownloadFile() {
        return downloadFile;
    }

    public ClientRequest setDownloadFile(String downloadFile) {
        setDownloadFile(downloadFile, false);
        return this;
    }

    public Map<String, ClientFile> getUploadFiles() {
        return uploadFiles;
    }

    public ClientRequest setUploadFiles(Map<String, ClientFile> uploadFiles) {
        this.uploadFiles = uploadFiles;
        setContentType(ContentType.MULTIPART.value() + ";charset=" + encoding);
        return this;
    }

    public ClientRequest addUploadFile(String name, String filepath) throws FileNotFoundException {
        this.uploadFiles.put(name, new ClientFile(filepath));
        setContentType(ContentType.MULTIPART.value() + ";charset=" + encoding);
        return this;
    }

    public ClientRequest addUploadFile(String name, File file) throws FileNotFoundException {
        this.uploadFiles.put(name, new ClientFile(file));
        setContentType(ContentType.MULTIPART.value() + ";charset=" + encoding);
        return this;
    }

    /**
     * @param name        param name
     * @param filename    file name
     * @param contentType
     * @param fileStream
     * @return
     */
    public ClientRequest addUploadFile(String name, String filename, String contentType, InputStream fileStream) {
        this.uploadFiles.put(name, new ClientFile(filename, contentType, fileStream));
        setContentType(ContentType.MULTIPART.value() + ";charset=" + encoding);
        return this;
    }

    public String getEncodedParams() throws UnsupportedEncodingException {
        String encodedParams = "";
        if (!this.params.isEmpty()) {
            Set<String> paramKeys = this.params.keySet();
            boolean isFirstParam = true;
            String value = null;
            for (String key : paramKeys) {
                value = this.params.get(key);
                if (value == null) continue;
                if (isFirstParam) {
                    encodedParams += key + "=" + URLEncoder.encode(value, this.getEncoding());
                    isFirstParam = false;
                } else {
                    encodedParams += "&" + key + "=" + URLEncoder.encode(value, this.getEncoding());
                }
            }
        }

        return encodedParams.trim();
    }

    public String getUnEncodedParams() {
        String params = "";
        if (!this.params.isEmpty()) {
            Set<String> paramKeys = this.params.keySet();
            boolean isFirstParam = true;
            String value = null;
            for (String key : paramKeys) {
                value = this.params.get(key);
                if (value == null) {
                    continue;
                }
                if (isFirstParam) {
                    params += key + "=" + value;
                    isFirstParam = false;
                } else {
                    params += "&" + key + "=" + value;
                }
            }
        }

        return params.trim();
    }

    public String getEncodedUrl() throws UnsupportedEncodingException {
        String encodedUrl = this.getEncodedRestPath();
        if (!this.params.isEmpty()) {
            encodedUrl += "?";
            Set<String> paramKeys = this.params.keySet();
            boolean isFirstParam = true;
            String value = null;
            for (String key : paramKeys) {
                value = this.params.get(key);
                if (value == null) continue;
                if (isFirstParam) {
                    encodedUrl += key + "=" + URLEncoder.encode(value, this.getEncoding());
                    isFirstParam = false;
                } else {
                    encodedUrl += "&" + key + "=" + URLEncoder.encode(value, this.getEncoding());
                }
            }
        }
        return encodedUrl.trim();
    }

    public String getUnEncodedUrl() {
        String url = this.getRestPath();
        if (!this.params.isEmpty()) {
            url += "?";
            Set<String> paramKeys = this.params.keySet();
            boolean isFirstParam = true;
            String value = null;
            for (String key : paramKeys) {
                value = this.params.get(key);
                if (value == null) {
                    continue;
                }
                if (isFirstParam) {
                    url += key + "=" + value;
                    isFirstParam = false;
                } else {
                    url += "&" + key + "=" + value;
                }
            }
        }
        return url.trim();
    }

}

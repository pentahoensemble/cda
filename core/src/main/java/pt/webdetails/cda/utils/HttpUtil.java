package pt.webdetails.cda.utils;

import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.ssl.AllowAllHostnameVerifier;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 * 
 * This class provide utility method execute Http Request and retrieve its
 * response
 *
 */
public class HttpUtil {
	
	private static final Log logger = LogFactory.getLog( HttpUtil.class );

  /**
   * @param url url to send get request
   * @param headerMap optional header if needed
   * @throws IOException if http connection cannot be executed
   */
  public static Response doGet(String url, Map<String, String> headerMap)
      throws IOException {
    HttpGet getRequest = new HttpGet(url);
    loadHeader(getRequest, headerMap);
    return execute(getRequest);
  }

  /**
   * @param url url to send post request
   * @param payload request entity
   * @param headerMap optional header if needed
   * @throws IOException if http connection cannot be executed
   */
  public static Response doPost(String url, String payload,
      Map<String, String> headerMap) throws IOException {
    HttpPost postRequest = new HttpPost(url);
    loadHeader(postRequest, headerMap);
    postRequest.setEntity(new StringEntity(payload));
    return execute(postRequest);
  }
  

  public static Response doPost(String url, UrlEncodedFormEntity payload, 
		  Map<String, String> headerMap) throws IOException {
	  HttpPost postRequest = new HttpPost(url);
	  loadHeader(postRequest, headerMap);
	  postRequest.setEntity(payload);
   	  return execute(postRequest);
  }

  private static void loadHeader(HttpRequestBase request,
      Map<String, String> headerMap) {
    if (headerMap != null) {
      for (String header : headerMap.keySet()) {
        request.setHeader(header, headerMap.get(header));
      }
    }
  }
  
  private static Response execute(HttpRequestBase request) throws IOException {
		CloseableHttpClient client = null;
		try {
			client = HttpClients.custom().
			        setHostnameVerifier(new AllowAllHostnameVerifier()).
			        setSslcontext(new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy()
			        {
			            public boolean isTrusted(X509Certificate[] arg0, String arg1) throws CertificateException
			            {
			                return true;
			            }
			        }).build()).build();
		} catch (Exception e) {
			logger.debug("Exception during httpClient initialization: " + e.getLocalizedMessage());
		}
		
	    Map<String, String> headerMap = new HashMap<String, String>();
	    int statusCode = -1;
	    String entityString = null;
		if (client != null) {
			HttpResponse response = client.execute(request);
	
		    HttpEntity responseEntity = response.getEntity();
		    if (responseEntity != null) {
		      entityString = EntityUtils.toString(responseEntity);
		    }
		    Header[] headers = response.getAllHeaders();
		    for (Header header : headers) {
		      headerMap.put(header.getName(), header.getValue());
		    }
		
		    statusCode = response.getStatusLine().getStatusCode();
		    client.close();
		}
		
	    return new Response(headerMap, entityString, statusCode);
  }

  /**
   * 
   * This POJO convert HttpResponse to simple object
   *
   */
  public static class Response {

    private final Map<String, String> headers;
    private final String body;
    private final int statusCode;

    public Response(Map<String, String> headers, String body, int statusCode) {
      this.headers = headers;
      this.body = body;
      this.statusCode = statusCode;
    }

    public Map<String, String> getHeaders() {
      return headers;
    }

    public String getBody() {
      return body;
    }

    public int getStatusCode() {
      return statusCode;
    }
  }


}

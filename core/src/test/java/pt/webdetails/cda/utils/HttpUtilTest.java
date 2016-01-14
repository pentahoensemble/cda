package pt.webdetails.cda.utils;

import java.io.IOException;

import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.entity.StringEntity;
import org.apache.http.localserver.LocalTestServer;
import org.apache.http.message.BasicHttpEntityEnclosingRequest;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.HttpRequestHandler;
import org.apache.http.util.EntityUtils;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import pt.webdetails.cda.utils.HttpUtil.Response;

public class HttpUtilTest {
  private static LocalTestServer server = new LocalTestServer(null, null);

  @BeforeClass
  public static void setUp() throws Exception {
    server.register("/test/*", new TestHttpHandler());
    server.start();
  }

  @Test
  public void testGet() throws IOException {
    String serverUrl = "http:/" + server.getServiceAddress();
    Response resp = HttpUtil.doGet(serverUrl + "/test/foo", null);
    Assert.assertEquals(resp.getStatusCode(), 200);
    Assert.assertEquals(resp.getBody(), "foobar");
  }

  @Test
  public void testPost() throws IOException {
    String serverUrl = "http:/" + server.getServiceAddress();
    Response resp = HttpUtil.doPost(serverUrl + "/test/foo", "testBody", null);
    Assert.assertEquals(resp.getStatusCode(), 200);
    Assert.assertEquals(resp.getBody(), "testBody");
  }

  @AfterClass
  public static void tearDown() throws Exception {
    server.stop();
  }

  private static class TestHttpHandler implements HttpRequestHandler {

    @Override
    public void handle(HttpRequest request, HttpResponse response,
        HttpContext context) throws HttpException, IOException {
      if (request.getRequestLine().toString().startsWith("POST")) {
        if (request instanceof BasicHttpEntityEnclosingRequest) {
          String entity = EntityUtils
              .toString(((BasicHttpEntityEnclosingRequest) request).getEntity());
          response.setEntity(new StringEntity(entity));
        }
      } else {
        response.setEntity(new StringEntity("foobar"));
      }

    }
  }

}

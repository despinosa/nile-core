/*
 * To change this license header, choose License Headers in Project Properties. To change this
 * template file, choose Tools | Templates and open the template in the editor.
 */
package mx.ipn.escom.supernaut.nile.logic;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.lang.reflect.ParameterizedType;
import java.net.URI;
import java.util.List;
import javax.ejb.PrePassivate;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.HttpClients;

/**
 *
 * @author supernaut
 */
public abstract class CommonBean<PK, Model> implements
    CommonBeanInterface<PK, Model> {

  protected static final HttpHost HOST =
      new HttpHost("localhost", 8081, "http");
  protected final HttpClient CLIENT = HttpClients.createDefault();
  protected final URI baseUri;
  protected final Class<Model> modelType;
  protected static Gson gson = new Gson();
  protected Model model;

  public CommonBean() {
    modelType =
        (Class<Model>) ((ParameterizedType) getClass().getGenericSuperclass())
            .getActualTypeArguments()[0];
    baseUri =
        URI.create(HOST.toURI() + "/resources/"
            + modelType.getName().toLowerCase());
  }

  protected abstract String getPkAsParams();

  protected InputStream streamedMarshall() {
    PipedInputStream in = new PipedInputStream();
    try {
      OutputStream out = new PipedOutputStream(in);
      JsonWriter writer = new JsonWriter(new OutputStreamWriter(out));
      gson.toJson(model, modelType, writer);
    } catch (IOException ex) {
      throw new RuntimeException(ex);
    }
    return in;
  }

  protected void storeModel(HttpEntity entity) throws IOException {
    model =
        (Model) gson.fromJson(
            new JsonReader(new InputStreamReader(entity.getContent())),
            modelType);
  }

  @Override
  public void initByPK(PK pk) {
    HttpGet request = new HttpGet(baseUri + getPkAsParams());
    request.addHeader("accept", "application/json");
    HttpResponse response;
    try {
      response = CLIENT.execute(HOST, request);
      storeModel(response.getEntity());
    } catch (IOException ex) {
      throw new RuntimeException(ex);
    }
    StatusLine statusLine = response.getStatusLine();
    if (statusLine.getStatusCode() < 200 || statusLine.getStatusCode() > 299)
      throw new RuntimeException(statusLine.getReasonPhrase());
  }

  @Override
  public void initNew(Model model) {
    HttpPost request = new HttpPost(baseUri);
    request.addHeader("accept", "application/json");
    request.setEntity(new InputStreamEntity(streamedMarshall()));
    HttpResponse response;
    try {
      response = CLIENT.execute(HOST, request);
    } catch (IOException ex) {
      throw new RuntimeException(ex);
    }
    StatusLine statusLine = response.getStatusLine();
    if (statusLine.getStatusCode() < 200 || statusLine.getStatusCode() > 299)
      throw new RuntimeException(statusLine.getReasonPhrase());
    this.model = model;
  }

  @Override
  public List<Model> getAll() {
    List<Model> list;
    HttpGet request = new HttpGet(baseUri);
    HttpResponse response;
    try {
      response = CLIENT.execute(HOST, request);
      list =
          gson.fromJson(new JsonReader(new InputStreamReader(response
              .getEntity().getContent())), modelType);
    } catch (IOException ex) {
      throw new RuntimeException(ex);
    }
    StatusLine statusLine = response.getStatusLine();
    if (statusLine.getStatusCode() < 200 || statusLine.getStatusCode() > 299)
      throw new RuntimeException(statusLine.getReasonPhrase());
    return list;
  }

  @Override
  public Model getModel() {
    return model;
  }

  @Override
  public void deleteModel() {
    HttpDelete request = new HttpDelete(baseUri + getPkAsParams());
    HttpResponse response;
    try {
      response = CLIENT.execute(HOST, request);
    } catch (IOException ex) {
      throw new RuntimeException(ex);
    }
    StatusLine statusLine = response.getStatusLine();
    if (statusLine.getStatusCode() < 200 || statusLine.getStatusCode() > 299)
      throw new RuntimeException(statusLine.getReasonPhrase());
  }

  @Override
  @PrePassivate
  public void commitModel() {
    HttpPut request = new HttpPut(baseUri + getPkAsParams());
    request.setEntity(new InputStreamEntity(streamedMarshall()));
    HttpResponse response;
    try {
      response = CLIENT.execute(HOST, request);
    } catch (IOException ex) {
      throw new RuntimeException(ex);
    }
    StatusLine statusLine = response.getStatusLine();
    if (statusLine.getStatusCode() < 200 || statusLine.getStatusCode() > 299)
      throw new RuntimeException(statusLine.getReasonPhrase());
  }

}

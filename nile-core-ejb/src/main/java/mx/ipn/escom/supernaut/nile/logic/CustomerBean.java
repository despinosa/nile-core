/*
 * To change this license header, choose License Headers in Project Properties. To change this
 * template file, choose Tools | Templates and open the template in the editor.
 */
package mx.ipn.escom.supernaut.nile.logic;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Date;
import javax.ejb.Stateful;
import mx.ipn.escom.supernaut.nile.model.Customer;
import mx.ipn.escom.supernaut.nile.model.Order;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.HttpGet;

/**
 *
 * @author supernaut
 */
@Stateful
public class CustomerBean extends CommonBean<Integer, Customer> implements
    CustomerBeanRemote, CustomerBeanLocal {

  @Override
  protected String getPkAsParams() {
    return getModel().getCustomerId().toString();
  }

  @Override
  public void initByUsername(String username) {
    HttpGet request = new HttpGet(baseUri + "/user/" + getPkAsParams());
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
  public boolean initWithLogin(String username, String pword) {
    initByUsername(username);
    boolean valid;
    try {
      valid =
          PasswordHash.validatePassword(pword, getModel().getPwordHash(),
              getModel().getPwordSalt());
    } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
      throw new RuntimeException(ex);
    }
    if (!valid)
      model = null;
    model.setLastLogin(new Date());
    return valid;
  }

  @Override
  public void addOrder(Order order) {
    throw new UnsupportedOperationException("Not supported yet."); // To change body of generated
                                                                   // methods, choose Tools |
                                                                   // Templates.
  }

}

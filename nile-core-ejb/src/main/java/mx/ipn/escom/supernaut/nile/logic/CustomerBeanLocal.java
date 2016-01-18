/*
 * To change this license header, choose License Headers in Project Properties. To change this
 * template file, choose Tools | Templates and open the template in the editor.
 */
package mx.ipn.escom.supernaut.nile.logic;

import javax.ejb.Local;
import mx.ipn.escom.supernaut.nile.model.Address;
import mx.ipn.escom.supernaut.nile.model.Customer;
import mx.ipn.escom.supernaut.nile.model.Order;

/**
 *
 * @author supernaut
 */
@Local
public interface CustomerBeanLocal extends
    CommonBeanInterface<Integer, Customer> {

  void addOrder(Order order);

  void initByUsername(String username);

  boolean initWithLogin(String username, String pword);

  public void setShippingAddress(Address address);

  void setBillingAddress(Address address);

}

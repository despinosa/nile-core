/*
 * To change this license header, choose License Headers in Project Properties. To change this
 * template file, choose Tools | Templates and open the template in the editor.
 */
package mx.ipn.escom.supernaut.nile.logic;

import javax.ejb.Local;
import mx.ipn.escom.supernaut.nile.model.Order;
import mx.ipn.escom.supernaut.nile.model.OrderPK;
import mx.ipn.escom.supernaut.nile.model.Product;

/**
 *
 * @author supernaut
 */
@Local
public interface OrderBeanLocal extends CommonBeanInterface<OrderPK, Order> {

  void addProduct(Product product, int quantity);

}

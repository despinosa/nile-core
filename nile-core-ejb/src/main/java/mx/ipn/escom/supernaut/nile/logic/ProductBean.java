/*
 * To change this license header, choose License Headers in Project Properties. To change this
 * template file, choose Tools | Templates and open the template in the editor.
 */
package mx.ipn.escom.supernaut.nile.logic;

import java.util.List;
import javax.ejb.Stateful;
import mx.ipn.escom.supernaut.nile.model.Product;

/**
 *
 * @author supernaut
 */
@Stateful
public class ProductBean extends CommonBean<Integer, Product> implements
    ProductBeanLocal {

  @Override
  protected String getPkAsParams() {
    throw new UnsupportedOperationException("Not supported yet."); // To change body of generated
                                                                   // methods, choose Tools |
                                                                   // Templates.
  }

}

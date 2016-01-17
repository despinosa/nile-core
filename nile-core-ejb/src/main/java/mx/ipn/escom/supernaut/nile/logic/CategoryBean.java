/*
 * To change this license header, choose License Headers in Project Properties. To change this
 * template file, choose Tools | Templates and open the template in the editor.
 */
package mx.ipn.escom.supernaut.nile.logic;

import javax.ejb.Stateful;
import mx.ipn.escom.supernaut.nile.model.Category;
import mx.ipn.escom.supernaut.nile.model.Product;

/**
 *
 * @author supernaut
 */
@Stateful
public class CategoryBean extends CommonBean<Short, Category> implements
    CategoryBeanRemote, CategoryBeanLocal {

  @Override
  protected String getPkAsParams() {
    return getModel().getCategoryId().toString();
  }

  @Override
  public void addProduct(Product product) {
    throw new UnsupportedOperationException("Not supported yet."); // To change body of generated
                                                                   // methods, choose Tools |
                                                                   // Templates.
  }
}

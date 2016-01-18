/*
 * To change this license header, choose License Headers in Project Properties. To change this
 * template file, choose Tools | Templates and open the template in the editor.
 */
package mx.ipn.escom.supernaut.nile.logic;

import javax.ejb.Stateful;
import mx.ipn.escom.supernaut.nile.model.Attribute;
import mx.ipn.escom.supernaut.nile.model.Category;
import mx.ipn.escom.supernaut.nile.model.CategoryDetail;
import mx.ipn.escom.supernaut.nile.model.CategoryDetailPK;

/**
 *
 * @author supernaut
 */
@Stateful
public class AttributeBean extends CommonBean<Integer, Attribute> implements
    AttributeBeanRemote, AttributeBeanLocal {

  @Override
  protected String getPkAsParams() {
    return model.getAttributeId().toString();
  }

  // Add business logic below. (Right-click in editor and choose
  // "Insert Code > Add Business Method")

  @Override
  public void addCategoryDetail(CategoryDetail categoryDetail) {
    categoryDetail.getCategoryDetailPK().setAttribute(model.getAttributeId());
    categoryDetail.setAttribute1(model);
    model.getCategoryDetailCollection().add(categoryDetail);
  }

  @Override
  public void addCategory(Category category) {
    CategoryDetail detail = new CategoryDetail();
    CategoryDetailPK pk = new CategoryDetailPK();
    pk.setAttribute(model.getAttributeId());
    pk.setCategory(category.getCategoryId());
    detail.setCategoryDetailPK(pk);
    detail.setAttribute1(model);
    detail.setCategory1(category);
    model.getCategoryDetailCollection().add(category);
    category.getCategoryDetailCollection().add(model);
  }

}

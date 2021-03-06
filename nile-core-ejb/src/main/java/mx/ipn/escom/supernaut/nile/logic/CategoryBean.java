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
    product.getCategoryCollection().add(model);
    model.getProductCollection().add(product);
  }

  @Override
  public void addCategoryDetail(CategoryDetail categoryDetail) {
    categoryDetail.setCategory1(model);
    categoryDetail.getCategoryDetailPK().setCategory(model.getCategoryId());
    model.getCategoryDetailCollection().add(categoryDetail);
  }

  @Override
  public void addAttribute(Attribute attribute) {
    CategoryDetail detail = new CategoryDetail();
    detail.setCategory1(model);
    detail.setAttribute1(attribute);
    CategoryDetailPK pk = new CategoryDetailPK();
    pk.setCategory(model.getCategoryId());
    pk.setAttribute(attribute.getAttributeId());
    detail.setCategoryDetailPK(pk);
    attribute.getCategoryDetailCollection().add(detail);
    model.getCategoryDetailCollection().add(detail);
  }

}

/*
 * To change this license header, choose License Headers in Project Properties. To change this
 * template file, choose Tools | Templates and open the template in the editor.
 */
package mx.ipn.escom.supernaut.nile.logic;

import java.util.Collection;
import java.util.HashSet;
import javax.ejb.Stateful;
import mx.ipn.escom.supernaut.nile.model.Attribute;
import mx.ipn.escom.supernaut.nile.model.Category;
import mx.ipn.escom.supernaut.nile.model.CategoryDetail;
import mx.ipn.escom.supernaut.nile.model.Product;
import mx.ipn.escom.supernaut.nile.model.ProductDetail;
import mx.ipn.escom.supernaut.nile.model.ProductDetailPK;

/**
 *
 * @author supernaut
 */
@Stateful
public class ProductBean extends CommonBean<Integer, Product> implements
    ProductBeanRemote, ProductBeanLocal {

  boolean attributeInProductCategories(Attribute attribute) {
    for (Object category : model.getCategoryCollection()) {
      for (Object detail : ((Category) category).getCategoryDetailCollection()) {
        if (attribute == ((CategoryDetail) detail).getAttribute1()) {
          return true;
        }
      }
    }
    return false;
  }

  @Override
  protected String getPkAsParams() {
    return model.getSku().toString();
  }

  @Override
  public void addCategory(Category category) {
    category.getProductCollection().add(model);
    model.getCategoryCollection().add(category);
  }

  @Override
  public void addProductDetail(ProductDetail productDetail) {
    productDetail.getProductDetailPK().setProduct(model.getSku());
    productDetail.setProduct1(model);
    model.getProductDetailCollection().add(productDetail);
  }

  @Override
  public void addAttribute(Attribute attribute, String value) {
    if (!attributeInProductCategories(attribute))
      throw new IllegalArgumentException();
    ProductDetail detail = new ProductDetail();
    ProductDetailPK pk = new ProductDetailPK();
    pk.setProduct(model.getSku());
    pk.setAttribute(attribute.getAttributeId());
    detail.setProductDetailPK(pk);
    detail.setAttribute1(attribute);
    detail.setProduct1(model);
    detail.setValue(value);
    model.getProductDetailCollection().add(detail);
  }
}

/*
 * To change this license header, choose License Headers in Project Properties. To change this
 * template file, choose Tools | Templates and open the template in the editor.
 */
package mx.ipn.escom.supernaut.nile.logic;

import javax.ejb.Stateful;
import mx.ipn.escom.supernaut.nile.model.Order;
import mx.ipn.escom.supernaut.nile.model.OrderDetail;
import mx.ipn.escom.supernaut.nile.model.OrderDetailPK;
import mx.ipn.escom.supernaut.nile.model.OrderPK;
import mx.ipn.escom.supernaut.nile.model.Product;

/**
 *
 * @author supernaut
 */
@Stateful
public class OrderBean extends CommonBean<OrderPK, Order> implements
    OrderBeanRemote, OrderBeanLocal {

  @Override
  protected String getPkAsParams() {
    return String.format("customer=%d;order_id=%d", model.getOrderPK()
        .getCustomer(), model.getOrderPK().getOrderId());
  }


  @Override
  public void addProduct(Product product, short quantity) {
    OrderDetail detail = new OrderDetail();
    detail.setOrder1(model);
    detail.setProduct1(product);
    detail.setQuantity(quantity);
    OrderDetailPK pk = new OrderDetailPK();
    pk.setCustomer(model.getOrderPK().getCustomer());
    pk.setOrder(model.getOrderPK().getOrderId());
    pk.setProduct(product.getSku());
    detail.setOrderDetailPK(pk);
    model.getOrderDetailCollection().add(detail);
  }

  @Override
  public void addOrderDetail(OrderDetail orderDetail) {
    orderDetail.setOrder1(model);
    orderDetail.getOrderDetailPK()
        .setCustomer(model.getOrderPK().getCustomer());
    orderDetail.getOrderDetailPK().setOrder(model.getOrderPK().getOrderId());
    model.getOrderDetailCollection().add(orderDetail);
  }

}

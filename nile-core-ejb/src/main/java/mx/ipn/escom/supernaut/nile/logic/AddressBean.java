/*
 * To change this license header, choose License Headers in Project Properties. To change this
 * template file, choose Tools | Templates and open the template in the editor.
 */
package mx.ipn.escom.supernaut.nile.logic;

import javax.ejb.Stateful;
import mx.ipn.escom.supernaut.nile.model.Address;
import mx.ipn.escom.supernaut.nile.model.AddressPK;

/**
 *
 * @author supernaut
 */
@Stateful
public class AddressBean extends CommonBean<AddressPK, Address> implements
    AddressBeanRemote, AddressBeanLocal {

  @Override
  protected String getPkAsParams() {
    return String.format("type=%s;customer=%d", model.getAddressPK().getType(),
        model.getAddressPK().getCustomer());
  }

  // Add business logic below. (Right-click in editor and choose
  // "Insert Code > Add Business Method")
}

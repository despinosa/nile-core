/*
 * To change this license header, choose License Headers in Project Properties. To change this
 * template file, choose Tools | Templates and open the template in the editor.
 */
package mx.ipn.escom.supernaut.nile.logic;

import javax.ejb.Local;
import mx.ipn.escom.supernaut.nile.model.Address;
import mx.ipn.escom.supernaut.nile.model.AddressPK;

/**
 *
 * @author supernaut
 */
@Local
public interface AddressBeanLocal extends
    CommonBeanInterface<AddressPK, Address> {

}

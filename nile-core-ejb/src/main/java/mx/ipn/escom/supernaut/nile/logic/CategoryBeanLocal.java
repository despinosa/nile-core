/*
 * To change this license header, choose License Headers in Project Properties. To change this
 * template file, choose Tools | Templates and open the template in the editor.
 */
package mx.ipn.escom.supernaut.nile.logic;

import javax.ejb.Local;
import mx.ipn.escom.supernaut.nile.model.Attribute;
import mx.ipn.escom.supernaut.nile.model.Category;
import mx.ipn.escom.supernaut.nile.model.CategoryDetail;

/**
 *
 * @author supernaut
 */
@Local
public interface CategoryBeanLocal extends CommonBeanInterface<Short, Category> {

    void addCategoryDetail(CategoryDetail categoryDetail);

    void addAttribute(Attribute attribute);
}

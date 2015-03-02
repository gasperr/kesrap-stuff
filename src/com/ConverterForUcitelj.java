package com;

import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

/**
 * Created by gasper on 2/28/15.
 */
@FacesConverter(forClass = Ucitelj.class)
@ManagedBean
public class ConverterForUcitelj implements javax.faces.convert.Converter {

    @Inject
    private Dao dao;

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        if(s != null && !s.equals("")){
            System.out.println("ID: "+s);
            Long id = Long.parseLong(s);
            if(dao == null) System.out.println("Dao is null");
            Ucitelj n = dao.getUciteljById(id);
            // System.out.println("TOLE VRNE CONVERTER: "+n.ime);
            return n;
        }

        return null;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
        if(o != null){
            if(o instanceof Ucitelj){
                Long id = ((Ucitelj) o).getUciteljId();
                return id.toString();
            }

        }

        return null;
    }
}

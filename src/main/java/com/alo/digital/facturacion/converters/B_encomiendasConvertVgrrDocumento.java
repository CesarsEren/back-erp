package com.alo.digital.facturacion.converters;

import com.alo.digital.facturacion.entity.B_Encomiendas;
import com.alo.digital.facturacion.entity.grt.VgrrDocumento;
import org.springframework.core.convert.converter.Converter;


public class B_encomiendasConvertVgrrDocumento  implements Converter<B_Encomiendas, VgrrDocumento> {
    @Override
    public VgrrDocumento convert(B_Encomiendas value) {

        VgrrDocumento vgrrDocumento = new VgrrDocumento();
        vgrrDocumento.setCodigotipodocumento("09");
        return null;
    }

}

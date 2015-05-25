/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.ByteArrayOutputStream;
import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;

/**
 *
 * @author Gandhi
 */
public class CodigoQR {


    public CodigoQR() {
    }

    public byte[] generar(String texto, ImageType imageType, int widht, int height) {
        ByteArrayOutputStream out = QRCode.from(texto)
                .to(imageType)
                .withSize(widht, height)
                .stream();
        return out.toByteArray();
    }

    
}

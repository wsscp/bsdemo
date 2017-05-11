package cc.oit.bsmes.common.util;

import org.krysalis.barcode4j.impl.code128.Code128Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;
import org.krysalis.barcode4j.tools.UnitConv;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * Created by joker on 2014/8/18 0018.
 */
public class OneBarcodeUtil {

    public static void createOneBarCode(String barCodeText,String realPath){
        try{
            Code128Bean bean = new Code128Bean();
            final int dpi = 110;

            //Configure the barcode generator
            bean.setModuleWidth(UnitConv.in2mm(1.0f / dpi)); //makes the narrow bar
            //width exactly one pixel
            //bean.setWideFactor(3);
            bean.doQuietZone(false);
            bean.setHeight(10.0);

            String file = realPath+barCodeText+".jpg";
            File outputFile = new File(file);
            OutputStream out = new FileOutputStream(outputFile);
            try {
                //Set up the canvas provider for monochrome JPEG output
                BitmapCanvasProvider canvas = new BitmapCanvasProvider(
                        out, "image/jpeg", dpi, BufferedImage.TYPE_BYTE_BINARY, false, 0);

                //Generate the barcode
                bean.generateBarcode(canvas, barCodeText);

                //Signal end of generation
                canvas.finish();
            } finally {
                out.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
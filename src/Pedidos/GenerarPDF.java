package Pedidos;/*
package Pedidos;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileOutputStream;
import java.util.List;

 */

public class GenerarPDF {

/*
    public class GeneradorPDF {
        public static void generarFacturaPDF(int idPedido, List<String> productos) {
            String archivo = "factura_pedido_" + idPedido + ".pdf";
            try {
                Document documento = new Document();
                PdfWriter.getInstance(documento, new FileOutputStream(archivo));
                documento.open();

                documento.add(new Paragraph("Factura de Pedido #" + idPedido));
                documento.add(new Paragraph(" ")); // Espacio

                for (String producto : productos) {
                    documento.add(new Paragraph(producto));
                }

                documento.close();
                System.out.println("Factura generada: " + archivo);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    /*
 */

}

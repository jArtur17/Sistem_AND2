package Pedidos;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class GenerarPDF {


        public void generarFacturaPDF(int idPedido, List<String> productos) {
            String ruta = "C:\\Users\\artur\\Desktop\\factura_pedido_" + idPedido + ".pdf";

            try {
                Document documento = new Document();
                PdfWriter.getInstance(documento, new FileOutputStream(ruta));
                documento.open();

                // Título
                documento.add(new Paragraph("Factura de Pedido #" + idPedido, FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16)));
                documento.add(new Paragraph(" ")); // Espacio

                // Lista de productos
                for (String producto : productos) {
                    documento.add(new Paragraph("- " + producto));
                }

                documento.close();
                System.out.println("Factura generada en: " + ruta);

            } catch (DocumentException | IOException e) {
                e.printStackTrace();
            }
        }
    }


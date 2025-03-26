package Pedidos;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;

import java.awt.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class GenerarPDF {


        public void generarFacturaPDF(int idPedido, List<String> productos, String fecha) {
            String ruta = "C:\\Users\\artur\\Desktop\\factura_pedido_" + idPedido + ".pdf";

            try {
                Document documento = new Document();
                PdfWriter.getInstance(documento, new FileOutputStream(ruta));
                documento.open();

                // Título
                documento.add(new Paragraph("Factura de Pedido", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 30)));
                //documento.add(new Paragraph(" ")); // Espacio
                documento.add(new Paragraph("Farmacia AND", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 8)));
                documento.add(new Paragraph("SENA CLEM, Tuluá Valle", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 8)));
                documento.add(new Paragraph("Fecha y hora: " + fecha, FontFactory.getFont(FontFactory.HELVETICA_BOLD, 8)));
                documento.add(new Paragraph(" ")); // Espacio

                // Lista de productos
                for (String producto : productos) {
                    documento.add(new Paragraph(" " + producto));
                }

                documento.close();
                System.out.println("Factura generada en: " + ruta);

            } catch (DocumentException | IOException e) {
                e.printStackTrace();
            }
        }
    }


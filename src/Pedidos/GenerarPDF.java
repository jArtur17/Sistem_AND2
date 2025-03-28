package Pedidos;
import com.itextpdf.text.Image;
import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.itextpdf.text.pdf.draw.LineSeparator;
import java.io.FileOutputStream;


import java.awt.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class GenerarPDF {


    /**
     * generar factura en pdf
     * se lleva el pdf a una ruta especifica
     * se agrega el logo y titulo
     */
    public void generarFacturaPDF(int idPedido, List<String> productos, String fecha) {
            String ruta = "C:\\Users\\artur\\Desktop\\factura_pedido_" + idPedido + ".pdf";

            try {

                // Título del pdf
                Font fontTitulo = FontFactory.getFont("Montserrat", BaseFont.WINANSI, BaseFont.EMBEDDED, 30, Font.BOLD, new BaseColor(0, 153, 204));


                Document documento = new Document();
                PdfWriter.getInstance(documento, new FileOutputStream(ruta));
                documento.open();

                BaseColor azulNoche = new BaseColor(25, 25, 112);

                //crear columnas para logo y titulo
                /************************************/
                PdfPTable table = new PdfPTable(2);
                table.setWidthPercentage(100); // Ancho total de la tabla
                table.setWidths(new float[]{70, 30}); // Proporción de cada columna
                /************************************/


                /************************************/
                PdfPCell cellTitulo = new PdfPCell(new Paragraph("Factura de Pedido", fontTitulo));
                cellTitulo.setBorder(PdfPCell.NO_BORDER); // Sin bordes
                cellTitulo.setVerticalAlignment(Element.ALIGN_MIDDLE); // Centrar verticalmente
                /************************************/


                /************************************/
                //Celda de la imagen (alineada a la derecha)
                String rutaLogo = "C:\\Users\\artur\\IdeaProjects\\Sistem_AND\\resources\\imagenes\\img_1.png";
                Image logo = Image.getInstance(rutaLogo);
                logo.scaleToFit(100, 100); // Ajustar tamaño
                PdfPCell cellLogo = new PdfPCell(logo);
                cellLogo.setBorder(PdfPCell.NO_BORDER); // Sin bordes
                cellLogo.setHorizontalAlignment(Element.ALIGN_RIGHT);
                /************************************/

                /************************************/
                //Agregar celdas a la tabla
                table.addCell(cellTitulo);
                table.addCell(cellLogo);

                // Agregar la tabla al documento
                documento.add(table);
                /************************************/


                //agregar al documento
                documento.add(new Paragraph("Farmacia AND", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, new BaseColor(0, 86, 163))));
                documento.add(new Paragraph("SENA CLEM, Tuluá Valle", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, new BaseColor(0, 86, 163))));
                documento.add(new Paragraph("Fecha y hora: " + fecha, FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, new BaseColor(0, 86, 163))));
                documento.add(new Paragraph(" ")); // Espacio

                LineSeparator redLine = new LineSeparator();
                redLine.setLineWidth(3f);
                redLine.setOffset(-2); // Posición respecto al texto
                redLine.setLineColor(new BaseColor(0, 86, 163)); // Color de la liena
                redLine.setPercentage(100); //ocupar todo el ancho


                documento.add(redLine);

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


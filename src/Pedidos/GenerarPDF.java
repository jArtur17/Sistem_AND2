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

/**
 * La clase {@code GenerarPDF} se encarga de generar un archivo PDF
 * que contiene la factura de un pedido. El PDF incluye información como
 * el título, logotipo, datos de la farmacia y una lista de productos.
 */
public class GenerarPDF {

    /**
     * Genera un archivo PDF de factura para un pedido específico.
     * <p>
     * El método crea un documento PDF en la ruta establecida, agrega el título,
     * el logotipo, la información de la farmacia, la fecha y hora, una línea separadora
     * y finalmente una lista de productos. Si ocurre algún error durante el proceso,
     * se imprime la traza de la excepción.
     * </p>
     *
     * @param idPedido   el identificador del pedido, usado para nombrar el archivo PDF.
     * @param productos  una lista de cadenas que representa los productos incluidos en el pedido.
     * @param fecha      la fecha y hora en que se genera la factura, en formato de cadena.
     *
     * @throws DocumentException si ocurre un error relacionado con la creación o manipulación del documento PDF.
     * @throws IOException       si ocurre un error de entrada/salida, por ejemplo al leer el logotipo o escribir el archivo.
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

            // Crear columnas para logo y título
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
            // Celda de la imagen (alineada a la derecha)
            String rutaLogo = "C:\\Users\\artur\\IdeaProjects\\Sistem_AND\\resources\\imagenes\\img_1.png";
            Image logo = Image.getInstance(rutaLogo);
            logo.scaleToFit(100, 100); // Ajustar tamaño
            PdfPCell cellLogo = new PdfPCell(logo);
            cellLogo.setBorder(PdfPCell.NO_BORDER); // Sin bordes
            cellLogo.setHorizontalAlignment(Element.ALIGN_RIGHT);
            /************************************/

            /************************************/
            // Agregar celdas a la tabla
            table.addCell(cellTitulo);
            table.addCell(cellLogo);

            // Agregar la tabla al documento
            documento.add(table);
            /************************************/

            // Agregar al documento información de la farmacia y fecha
            documento.add(new Paragraph("Farmacia AND", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, new BaseColor(0, 86, 163))));
            documento.add(new Paragraph("SENA CLEM, Tuluá Valle", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, new BaseColor(0, 86, 163))));
            documento.add(new Paragraph("Fecha y hora: " + fecha, FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, new BaseColor(0, 86, 163))));
            documento.add(new Paragraph(" ")); // Espacio

            // Línea separadora
            LineSeparator redLine = new LineSeparator();
            redLine.setLineWidth(3f);
            redLine.setOffset(-2); // Posición respecto al texto
            redLine.setLineColor(new BaseColor(0, 86, 163)); // Color de la línea
            redLine.setPercentage(100); // Ocupar todo el ancho

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

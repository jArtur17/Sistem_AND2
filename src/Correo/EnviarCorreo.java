package Correo;


import jakarta.activation.DataHandler;
import jakarta.activation.DataSource;
import jakarta.activation.FileDataSource;
import jakarta.mail.*;

import java.util.Properties;

import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;


/**
 * Clase para enviar correos electrónicos con archivos adjuntos.
 * @author Arturo
 */
public class EnviarCorreo {

    /**
     * Envía un correo electrónico con una factura adjunta al cliente especificado.
     * @param correoCliente La dirección de correo electrónico del cliente.
     */
    public  void EnviarCorreo(String correoCliente) {

        // Configuración del servidor SMTP
        final String host = "smtp.gmail.com"; // Servidor SMTP de Gmail
        final String port = "587"; // Puerto TLS
        final String username = "alejitoguzman.333@gmail.com"; //coreo
        final String password = "bklo nlxo dsvd ehuq"; // clave

        // Propiedades del correo
        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.ssl.trust", "*");
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");

        // Crear sesión con autenticación
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            // Crear mensaje de correo
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(correoCliente));

            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText("Hola, esta es tu factura del día de hoy!");


            // Crear la parte del archivo adjunto
            MimeBodyPart attachmentPart = new MimeBodyPart();
            String filename = "C:\\Users\\artur\\Desktop\\factura_pedido_1.pdf"; // ruta del correo
            DataSource source = new FileDataSource(filename);
            attachmentPart.setDataHandler(new DataHandler(source));
            attachmentPart.setFileName("factura.pdf"); // Nombre que se mostrará en el correo

            // Crear el multipart para combinar el texto y el adjunto
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);
            multipart.addBodyPart(attachmentPart);

            // Asignar el multipart al mensaje
            message.setContent(multipart);

            // Enviar correo
            Transport.send(message);

            System.out.println("Correo enviado con éxito!");

            // Enviar correo
            Transport.send(message);

            System.out.println("Correo enviado con éxito!");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
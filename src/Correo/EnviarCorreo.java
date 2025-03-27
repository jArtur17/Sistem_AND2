package Correo;


import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import java.util.Properties;
import jakarta.mail.MessagingException;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;


public class EnviarCorreo {





    public static void main(String[] args) {

            // Configuración del servidor SMTP
            final String host = "smtp.gmail.com"; // Servidor SMTP de Gmail
            final String port = "587"; // Puerto TLS
            final String username = "alejitoguzman.333@gmal.com"; // Cambia por tu correo
            final String password = "Alejo2005"; // Cambia por tu contraseña o usa una clave de aplicación

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
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("destinatario@gmail.com"));
                message.setSubject("Prueba de envío de correo");
                message.setText("Hola, este es un correo enviado desde Jakarta Mail en Java.");

                // Enviar correo
                Transport.send(message);

                System.out.println("Correo enviado con éxito!");

            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }
    }


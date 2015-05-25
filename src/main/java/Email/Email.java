package Email;
 
import java.util.Properties;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
 
public class Email {
    private String user="";
    private String pass="";
    private String body="";

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
    
    public Email(String user,String pass){
        this.user=user;
        this.pass=pass;
    }
   public Email(){
        this.user="zerphank@gmail.com";
        this.pass="Vivirparaluchar.";
    }
   
    public boolean enviarMensajeTexto(String subject,String body,String recipient){
         Properties props = new Properties();
         /*
         props.setProperty("mail.smtp.host", "smtp.gmail.com");
         props.setProperty("mail.smtp.starttls.enable", "true");
         props.setProperty("mail.smtp.port", "587");
         props.setProperty("mail.smtp.user",user);
         props.setProperty("mail.smtp.auth", "true");
         */
        props.put("mail.smtp.host", "smtp.gmail.com");
	props.put("mail.smtp.socketFactory.port", "465");
	props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
	props.put("mail.smtp.auth", "true");
	props.put("mail.smtp.port", "465");
       
        //Se obtiene una sesión con las propiedades anteriormente que hemos
        //guardado en -props-
        //Session sesion = Session.getDefaultInstance(props, null);
         Session session = Session.getDefaultInstance(props,new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(user,pass);
				}
			});       
        try {
            //Empezamos a crear el e-mail
            Message message = new MimeMessage(session);
            message.setSubject(subject);
            message.setFrom(new InternetAddress("NEKOCOMPANY"));
            Address [] receptores = new Address []{
                new InternetAddress (recipient)
                //,new InternetAddress ("traviesa_doro@hotmail.com")
            };
            message.addRecipients(Message.RecipientType.TO, receptores);
            //mensaje.setText(body);
            String htmlText = "<html><body>"+body+"<br/><br/><br/><br/><br/><br/><br/><br/><br/><hr/>&copy; Copyright NekoCompany</body></html>";
            message.setContent(htmlText,"text/html");
            Transport.send(message);
            //Transport t= session.getTransport("smtp");
           // t.connect(this.user, this.pass);
            //t.sendMessage(mensaje, mensaje.getRecipients(Message.RecipientType.TO));
            return true;
        }catch(MessagingException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

}
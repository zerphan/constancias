/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Email;
import Email.Email;

/**
 *
 * @author neko
 */
public class testEmail {
    public static void main(String[] args) {
        Email correo = new Email("zerphank@gmail.com","asdfg");
        String subject ="NEKOCOMPANY";
        String recipient ="zerphank@gmail.com";
        String body="\nFrom my first program for sending emails made with Javamail API.";
        if(correo.enviarMensajeTexto(subject,body,recipient)){
            System.out.println("Email enviado");
        }else{
            System.out.println("Email no enviado");
        }
    }
    
    
}

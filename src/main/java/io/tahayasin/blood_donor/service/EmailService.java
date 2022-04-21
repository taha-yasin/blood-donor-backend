package io.tahayasin.blood_donor.service;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@AllArgsConstructor
public class EmailService {

    private final static Logger LOGGER = LoggerFactory.getLogger(EmailService.class);

    private final JavaMailSender mailSender;

    public void send(String to, String email) {
//        try {
//            MimeMessage mimeMessage = mailSender.createMimeMessage();
//            MimeMessageHelper helper =
//                    new MimeMessageHelper(mimeMessage, "utf-8");
//            helper.setText(email, true);
//            helper.setTo(to);
//            helper.setSubject("BloodDonor Account Confirmation");
//            helper.setFrom("hello@blooddonor.com");
//            mailSender.send(mimeMessage);
//        } catch (MessagingException e) {
//            LOGGER.error("failed to send email", e);
//            throw new IllegalStateException("failed to send email");
//        }
    }

    public String buildEmail(String name, String link) {

        return "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional //EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n" +
                "<html xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:v=\"urn:schemas-microsoft-com:vml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\">\n" +
                "<head>\n" +
                "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <meta name=\"x-apple-disable-message-reformatting\">\n" +
                "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
                "    <title></title>\n" +
                "\n" +
                "    <style type=\"text/css\">\n" +
                "        *{\n" +
                "            padding: 0;\n" +
                "            margin: 0;\n" +
                "        }\n" +
                "        body{\n" +
                "            display: flex;\n" +
                "            align-items: center;\n" +
                "            text-align: center;\n" +
                "            justify-content: center;\n" +
                "            font-family: 'Cabin',sans-serif;\n" +
                "            background-color: white;\n" +
                "            color: black;\n" +
                "        }\n" +
                "        main{\n" +
                "            width: fit-content;\n" +
                "            max-width: 500px;\n" +
                "        }\n" +
                "    </style>\n" +
                "\n" +
                "    <link href=\"https://fonts.googleapis.com/css?family=Cabin:400,700&display=swap\" rel=\"stylesheet\" type=\"text/css\">\n" +
                "    <link href=\"https://fonts.googleapis.com/css?family=Lobster+Two:400,700&display=swap\" rel=\"stylesheet\" type=\"text/css\">\n" +
                "\n" +
                "</head>\n" +
                "<body>\n" +
                "<main style=\"max-width: 500px;\">\n" +
                "    <section style=\"margin-top: 20px; display:flex; align-items:center; justify-content:center; text-align:center;\">\n" +
                "        <a href=\"https://www.google.com\" target=\"_blank\" align=\"center\" style=\"text-decoration: none; margin: auto auto\">\n" +
                "            <img src=\"https://raw.githubusercontent.com/Code-4ge/Voluntary-Blood-Donor-UI/main/public/assets/logo.png\" alt=\"\" align=\"center\" style=\"width: 200px; height: auto; margin: auto auto;\">\n" +
                "        </a>\n" +
                "    </section>\n" +
                "    <section style=\"margin-top: 5px; display:flex; align-items:center; justify-content:center; text-align:center;\">\n" +
                "        <img src=\"https://raw.githubusercontent.com/Code-4ge/Voluntary-Blood-Donor-UI/main/public/assets/email_header.png\" alt=\"\" align=\"center\" style=\"max-width: 500px; width: 100%; height: auto; margin: auto auto;\">\n" +
                "    </section>\n" +
                "    <section>\n" +
                "        <h1 style=\"color: #ff3e3e; line-height: 200%; word-wrap: break-word; font-weight: bolder; font-family: 'Lobster Two',cursive; font-size: 30px; text-align:center;\">\n" +
                "            <strong>Your Generosity Matters!<br /></strong>\n" +
                "        </h1>\n" +
                "    </section>\n" +
                "    <section style=\"padding: 25px;\">\n" +
                "        <p style=\"font-size: 15px; word-wrap: break-word; text-align:center;\">Thanks <em>"+ name +"</em>, We're excited to have you get started! First you need to confirm your account. Just click the button below.</p>\n" +
                "    </section>\n" +
                "    <section style=\"margin: 30px 0 50px; text-align:center;\">\n" +
                "        <a href="+ link +" target=\"_blank\" style=\"box-sizing: border-box;display: inline-block;text-decoration: none;text-align: center;color: #FFFFFF; background-color: #d32f31; border-radius: 5px;-webkit-border-radius: 5px; width:auto;\">\n" +
                "            <span style=\"display:block;padding:10px 20px;line-height:120%;\"><span style=\"font-family: 'Cabin', sans-serif; font-size: 14px; line-height: 16.8px;\"><strong>Confirm Your Account</strong></span></span>\n" +
                "        </a>\n" +
                "    </section>\n" +
                "    <section style=\"padding: 10px;\">\n" +
                "        <p style=\"text-align: left; line-height: 150%; font-size: 11px; color: #747474;\">If above button did not worked, open below link on any browser <a rel=\"noopener\" href="+ link +" target=\"_blank\" style=\"color: #d32f31;\">"+ link +"</a></span></p>\n" +
                "    </section>\n" +
                "    <hr style=\"height: 1px; margin: 20px 0 50px 0; border: none; background-color: gray;\"/>\n" +
                "    <section style=\"padding: 0 30px;\">\n" +
                "        <p style=\"font-size: 13px; font-style: italic; line-height: 150%; text-align:center;\">\"We appreciate the generous donation you made earlier this year. We're asking you to help us again.\"</p>\n" +
                "    </section>\n" +
                "    <section style=\"margin-top:10px; padding: 0 0 10px 0; background-color: #c6414c;\">\n" +
                "        <div style=\"display:flex; align-items:center; justify-content:center; text-align:center;\">\n" +
                "            <img src=\"https://raw.githubusercontent.com/Code-4ge/Voluntary-Blood-Donor-UI/main/public/assets/email_footer.png\" alt=\"\" align=\"center\" style=\"width:100%; height:auto; margin: auto auto;\"/>\n" +
                "        </div>\n" +
                "        <div style=\"margin-top:20px\">\n" +
                "            <p style=\"font-size: 30px; line-height: 140%; color: white; text-align:center;\">Get In Touch</p>\n" +
                "        </div>\n" +
                "        <div style=\"width: 250px; height: 3px; border: none; background-color: white; margin: 10px auto;\"></div>\n" +
                "        <div style=\"width:fit-content; display: flex; justify-content: center; font-size:15px; color:white; text-align:center; margin:auto;\">\n" +
                "            <a href=\"https://www.facebook.com/\" title=\"Facebook\" target=\"_blank\" style=\"text-decoration: none; color:white;\">\n" +
                "                Facebook\n" +
                "            </a>\n" +
                "            &nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;\n" +
                "            <a href=\"https://www.instagram.com/\" title=\"Instagram\" target=\"_blank\" style=\"text-decoration: none; color:white;\">\n" +
                "                Instagram\n" +
                "            </a>\n" +
                "            &nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;" +
                "            <a href=\"https://www.whatsapp.com/\" title=\"WhatsApp\" target=\"_blank\" style=\"text-decoration: none; color:white;\">\n" +
                "                WhatsApp\n" +
                "            </a>\n" +
                "            &nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;\n" +
                "            <a href=\"mailto:blood.donor@mbox.re\" title=\"Mail\" target=\"_blank\" style=\"text-decoration: none; color:white;\">\n" +
                "                Mail\n" +
                "            </a>\n" +
                "        </div>\n" +
                "        <div style=\"margin: 50px 20px 20px ;\">\n" +
                "            <p style=\"font-size: 12px; line-height: 150%; color: white; text-align:center;\">If you have any questions, feel free message us at <a href=\"mailto:blood.donor@mbox.re\" style=\"color:white; text-decoration:none;\">blood.donor@mbox.re</a>.<br/><br/><span style=\"font-size: 13px;\">&copy; 2022 BloodDonor.</span></p>\n" +
                "        </div>\n" +
                "    </section>\n" +
                "</main>\n" +
                "</body>\n" +
                "</html>\n";

    }
}


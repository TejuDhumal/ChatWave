// This is a service class for sending emails to the user

package com.axis.team4.codecrafters.user_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromAddress;

    public void sendOtp(String to, String otp) throws MessagingException, jakarta.mail.MessagingException {
        sendEmail(to, otp, "Verify Chat Wave Account", generateOtpHtml(otp));
    }

    public void sendResetPasswordOtp(String to, String otp) throws MessagingException, jakarta.mail.MessagingException {
        sendEmail(to, otp, "Chat Wave Reset Password", generateResetPasswordHtml(otp));
    }

    public void sendReactivateOtp(String to, String otp) throws MessagingException, jakarta.mail.MessagingException {
        sendEmail(to, otp, "Good Bye Waver! Reactivate Account", generateReactivateAccountHtml(otp));
    }

    private void sendEmail(String to, String otp, String subject, String htmlContent) throws MessagingException, jakarta.mail.MessagingException {
        jakarta.mail.internet.MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setFrom(fromAddress);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlContent, true);

        mailSender.send(message);
    }

    public String generateOtpHtml(String otp) {
        return "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional //EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">" +
                "<html xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:v=\"urn:schemas-microsoft-com:vml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\">" +
                "<head>" +
                "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">" +
                "<meta name=\"x-apple-disable-message-reformatting\">" +
                "<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">" +
                "<title></title>" +
                "<style type=\"text/css\">" +
                "@media only screen and (min-width: 620px) {" +
                ".u-row {" +
                "width: 600px !important;" +
                "}" +
                ".u-row .u-col {" +
                "vertical-align: top;" +
                "}" +
                ".u-row .u-col-100 {" +
                "width: 600px !important;" +
                "}" +
                "}" +
                "@media (max-width: 620px) {" +
                ".u-row-container {" +
                "max-width: 100% !important;" +
                "padding-left: 0px !important;" +
                "padding-right: 0px !important;" +
                "}" +
                ".u-row .u-col {" +
                "min-width: 320px !important;" +
                "max-width: 100% !important;" +
                "display: block !important;" +
                "}" +
                ".u-row {" +
                "width: 100% !important;" +
                "}" +
                ".u-col {" +
                "width: 100% !important;" +
                "}" +
                ".u-col > div {" +
                "margin: 0 auto;" +
                "}" +
                "}" +
                "body {" +
                "margin: 0;" +
                "padding: 0;" +
                "}" +
                "table, tr, td {" +
                "vertical-align: top;" +
                "border-collapse: collapse;" +
                "}" +
                "p {" +
                "margin: 0;" +
                "}" +
                ".ie-container table, .mso-container table {" +
                "table-layout: fixed;" +
                "}" +
                "* {" +
                "line-height: inherit;" +
                "}" +
                "a[x-apple-data-detectors='true'] {" +
                "color: inherit !important;" +
                "text-decoration: none !important;" +
                "}" +
                "table, td {" +
                "color: #000000;" +
                "}" +
                "</style>" +
                "<link href=\"https://fonts.googleapis.com/css?family=Open+Sans:400,700\" rel=\"stylesheet\" type=\"text/css\">" +
                "<link href=\"https://fonts.googleapis.com/css?family=Source+Sans+Pro:400,700\" rel=\"stylesheet\" type=\"text/css\">" +
                "<link href=\"https://fonts.googleapis.com/css?family=Cabin:400,700\" rel=\"stylesheet\" type=\"text/css\">" +
                "</head>" +
                "<body class=\"clean-body u_body\" style=\"margin: 0;padding: 0;-webkit-text-size-adjust: 100%;background-color: #f9f9f9;color: #000000\">" +
                "<table style=\"border-collapse: collapse;table-layout: fixed;border-spacing: 0;mso-table-lspace: 0pt;mso-table-rspace: 0pt;vertical-align: top;min-width: 320px;Margin: 0 auto;background-color: #f9f9f9;width:100%\" cellpadding=\"0\" cellspacing=\"0\">" +
                "<tbody>" +
                "<tr style=\"vertical-align: top\">" +
                "<td style=\"word-break: break-word;border-collapse: collapse !important;vertical-align: top\">" +
                "<div class=\"u-row-container\" style=\"padding: 0px;background-color: transparent\">" +
                "<div class=\"u-row\" style=\"margin: 0 auto;min-width: 320px;max-width: 600px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: #ffffff;\">" +
                "<div style=\"border-collapse: collapse;display: table;width: 100%;height: 100%;background-color: transparent;\">" +
                "<div class=\"u-col u-col-100\" style=\"max-width: 320px;min-width: 600px;display: table-cell;vertical-align: top;\">" +
                "<div style=\"height: 100%;width: 100% !important;\">" +
                "<div style=\"box-sizing: border-box; height: 100%; padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\">" +
                "<table style=\"font-family:'Cabin',sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">" +
                "<tbody>" +
                "<tr>" +
                "<td style=\"overflow-wrap:break-word;word-break:break-word;padding:20px;font-family:'Cabin',sans-serif;\" align=\"left\">" +
                "<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">" +
                "<tr>" +
                "<td style=\"padding-right: 0px;padding-left: 0px;\" align=\"center\">" +
                "<img align=\"center\" border=\"0\" src=\"https://i.ibb.co/pQx7w7S/Chat-Wave-Logo.png\" alt=\"Image\" title=\"Image\" style=\"outline: none;text-decoration: none;-ms-interpolation-mode: bicubic;clear: both;display: inline-block !important;border: none;height: auto;float: none;width: 43%;max-width: 240.8px;\" width=\"240.8\" />" +
                "</td>" +
                "</tr>" +
                "</table>" +
                "</td>" +
                "</tr>" +
                "</tbody>" +
                "</table>" +
                "</div>" +
                "</div>" +
                "</div>" +
                "</div>" +
                "</div>" +
                "</div>" +
                "<div class=\"u-row-container\" style=\"padding: 0px;background-color: transparent\">" +
                "<div class=\"u-row\" style=\"margin: 0 auto;min-width: 320px;max-width: 600px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: #003399;\">" +
                "<div style=\"border-collapse: collapse;display: table;width: 100%;height: 100%;background-color: transparent;\">" +
                "<div class=\"u-col u-col-100\" style=\"max-width: 320px;min-width: 600px;display: table-cell;vertical-align: top;\">" +
                "<div style=\"background-color: #1271FF; height: 100%;width: 100% !important;\">" +
                "<div style=\"box-sizing: border-box; height: 100%; padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\">" +
                "<table style=\"font-family:'Cabin',sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">" +
                "<tbody>" +
                "<tr>" +
                "<td style=\"overflow-wrap:break-word;word-break:break-word;padding:40px 10px 10px;font-family:'Cabin',sans-serif;\" align=\"left\">" +
                "<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">" +
                "<tr>" +
                "<td style=\"padding-right: 0px;padding-left: 0px;\" align=\"center\">" +
                "<img align=\"center\" border=\"0\" src=\"https://cdn.templates.unlayer.com/assets/1597218650916-xxxxc.png\" alt=\"Image\" title=\"Image\" style=\"outline: none;text-decoration: none;-ms-interpolation-mode: bicubic;clear: both;display: inline-block !important;border: none;height: auto;float: none;width: 26%;max-width: 150.8px;\" width=\"150.8\" />" +
                "</td>" +
                "</tr>" +
                "</table>" +
                "</td>" +
                "</tr>" +
                "</tbody>" +
                "</table>" +
                "<table style=\"font-family:'Cabin',sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">" +
                "<tbody>" +
                "<tr>" +
                "<td style=\"overflow-wrap:break-word;word-break:break-word;padding:10px;font-family:'Cabin',sans-serif;\" align=\"left\">" +
                "<div style=\"font-size: 14px; color: #e5eaf5; line-height: 140%; text-align: center; word-wrap: break-word;\">" +
                "<p style=\"font-size: 14px; line-height: 140%;\"><strong>T H A N K S&nbsp; &nbsp;F O R&nbsp; &nbsp;S I G N I N G&nbsp; &nbsp;U P !</strong></p>" +
                "</div>" +
                "</td>" +
                "</tr>" +
                "</tbody>" +
                "</table>" +
                "<table style=\"font-family:'Cabin',sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">" +
                "<tbody>" +
                "<tr>" +
                "<td style=\"overflow-wrap:break-word;word-break:break-word;padding:0px 10px 31px;font-family:'Cabin',sans-serif;\" align=\"left\">" +
                "<div style=\"font-size: 14px; color: #e5eaf5; line-height: 140%; text-align: center; word-wrap: break-word;\">" +
                "<p style=\"font-size: 14px; line-height: 140%;\"><span style=\"font-size: 28px; line-height: 39.2px;\"><strong><span style=\"line-height: 39.2px; font-size: 28px;\">Verify Your E-mail Address </span></strong>" +
                "</span>" +
                "</p>" +
                "</div>" +
                "</td>" +
                "</tr>" +
                "</tbody>" +
                "</table>" +
                "</div>" +
                "</div>" +
                "</div>" +
                "</div>" +
                "</div>" +
                "<div class=\"u-row-container\" style=\"padding: 0px;background-color: transparent\">" +
                "<div class=\"u-row\" style=\"margin: 0 auto;min-width: 320px;max-width: 600px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: #ffffff;\">" +
                "<div style=\"border-collapse: collapse;display: table;width: 100%;height: 100%;background-color: transparent;\">" +
                "<div class=\"u-col u-col-100\" style=\"max-width: 320px;min-width: 600px;display: table-cell;vertical-align: top;\">" +
                "<div style=\"height: 100%;width: 100% !important;\">" +
                "<div style=\"box-sizing: border-box; height: 100%; padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\">" +
                "<table style=\"font-family:'Cabin',sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">" +
                "<tbody>" +
                "<tr>" +
                "<td style=\"overflow-wrap:break-word;word-break:break-word;padding:33px 55px;font-family:'Cabin',sans-serif;\" align=\"left\">" +
                "<div style=\"font-size: 14px; line-height: 160%; text-align: center; word-wrap: break-word;\">" +
                "<p style=\"font-size: 13px; line-height: 160%; text-align: left;\"><span style=\"font-size: 22px; line-height: 35.2px;\">Hello Waver,</span><span style=\"font-size: 22px; line-height: 35.2px;\"></span></p><br/>" +
                "<p style=\"font-size: 14px; line-height: 160%; text-align: left;\"><span style=\"font-size: 16px; line-height: 25.6px; font-family: 'Open Sans', sans-serif;\">You're almost ready to instant wave your communications. Please enter below OTP code to verify your email address</span></p>" +
                "</div>" +
                "</td>" +
                "</tr>" +
                "</tbody>" +
                "</table>" +
                "<table style=\"font-family:'Cabin',sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">" +
                "<tbody>" +
                "<tr>" +
                "<td style=\"overflow-wrap:break-word;word-break:break-word;padding:10px 55px;font-family:'Cabin',sans-serif;\" align=\"left\">" +
                "<div style=\"font-size: 14px; line-height: 20%; text-align: center; word-wrap: break-word;\">" +
                "<p style=\"font-size: 14px; line-height: 20%; text-align: center;\"><span style=\"color: #1271FF; line-height: 5.6px; font-size: 28px; font-family: 'Source Sans Pro', sans-serif;\"><strong><span style=\"line-height: 5.6px;\">" + otp + "</span></strong>" +
                "</span><span style=\"font-size: 22px; line-height: 4.4px;\"></span></p>" +
                "</div>" +
                "</td>" +
                "</tr>" +
                "</tbody>" +
                "</table>" +
                "<table style=\"font-family:'Cabin',sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">" +
                "<tbody>" +
                "<tr>" +
                "<td style=\"overflow-wrap:break-word;word-break:break-word;padding:33px 55px 60px;font-family:'Cabin',sans-serif;\" align=\"left\">" +
                "<div style=\"font-size: 14px; line-height: 160%; text-align: center; word-wrap: break-word;\">" +
                "<p style=\"line-height: 160%; text-align: left;\"><span style=\"font-size: 16px; line-height: 25.6px; font-family: 'Open Sans', sans-serif;\">Regards,</span></p>" +
                "<p style=\"line-height: 160%; text-align: left;\"><span style=\"font-size: 16px; line-height: 25.6px; font-family: 'Open Sans', sans-serif;\">Chat Wave Team</span></p>" +
                "</div>" +
                "</td>" +
                "</tr>" +
                "</tbody>" +
                "</table>" +
                "</div>" +
                "</div>" +
                "</div>" +
                "</div>" +
                "</div>" +
                "<div class=\"u-row-container\" style=\"padding: 0px;background-color: transparent\">" +
                "<div class=\"u-row\" style=\"margin: 0 auto;min-width: 320px;max-width: 600px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: #003399;\">" +
                "<div style=\"border-collapse: collapse;display: table;width: 100%;height: 100%;background-color: transparent;\">" +
                "<div class=\"u-col u-col-100\" style=\"max-width: 320px;min-width: 600px;display: table-cell;vertical-align: top;\">" +
                "<div style=\"background-color: #1271FF;height: 100%;width: 100% !important;\">" +
                "<div style=\"box-sizing: border-box; height: 100%; padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\">" +
                "<table style=\"font-family:'Cabin',sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">" +
                "<tbody>" +
                "<tr>" +
                "<td style=\"overflow-wrap:break-word;word-break:break-word;padding:10px;font-family:'Cabin',sans-serif;\" align=\"left\">" +
                "<div style=\"font-size: 14px; color: #fafafa; line-height: 180%; text-align: center; word-wrap: break-word;\">" +
                "<p style=\"font-size: 14px; line-height: 180%;\"><span style=\"font-size: 16px; line-height: 28.8px;\">Copyrights © Chat Wave Team!</span></p>" +
                "</div>" +
                "</td>" +
                "</tr>" +
                "</tbody>" +
                "</table>" +
                "</div>" +
                "</div>" +
                "</div>" +
                "</div>" +
                "</div>" +
                "</td>" +
                "</tr>" +
                "</tbody>" +
                "</table>" +
                "</body>" +
                "</html>";
    }
    private String generateResetPasswordHtml(String otp) {
        return "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional //EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">"
                + "<html xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:v=\"urn:schemas-microsoft-com:vml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\">"
                + "<head>"
                + "  <!--[if gte mso 9]>"
                + "  <xml>"
                + "    <o:OfficeDocumentSettings>"
                + "      <o:AllowPNG/>"
                + "      <o:PixelsPerInch>96</o:PixelsPerInch>"
                + "    </o:OfficeDocumentSettings>"
                + "  </xml>"
                + "  <![endif]-->"
                + "  <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">"
                + "  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">"
                + "  <meta name=\"x-apple-disable-message-reformatting\">"
                + "  <!--[if !mso]><!-->"
                + "  <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">"
                + "  <!--<![endif]-->"
                + "  <title></title>"
                + "  <style type=\"text/css\">"
                + "    @media only screen and (min-width: 620px) {"
                + "      .u-row {"
                + "        width: 600px !important;"
                + "      }"
                + "      .u-row .u-col {"
                + "        vertical-align: top;"
                + "      }"
                + "      .u-row .u-col-100 {"
                + "        width: 600px !important;"
                + "      }"
                + "    }"
                + "    @media (max-width: 620px) {"
                + "      .u-row-container {"
                + "        max-width: 100% !important;"
                + "        padding-left: 0px !important;"
                + "        padding-right: 0px !important;"
                + "      }"
                + "      .u-row .u-col {"
                + "        min-width: 320px !important;"
                + "        max-width: 100% !important;"
                + "        display: block !important;"
                + "      }"
                + "      .u-row {"
                + "        width: 100% !important;"
                + "      }"
                + "      .u-col {"
                + "        width: 100% !important;"
                + "      }"
                + "      .u-col > div {"
                + "        margin: 0 auto;"
                + "      }"
                + "    }"
                + "    body {"
                + "      margin: 0;"
                + "      padding: 0;"
                + "    }"
                + "    table, tr, td {"
                + "      vertical-align: top;"
                + "      border-collapse: collapse;"
                + "    }"
                + "    p {"
                + "      margin: 0;"
                + "    }"
                + "    .ie-container table, .mso-container table {"
                + "      table-layout: fixed;"
                + "    }"
                + "    * {"
                + "      line-height: inherit;"
                + "    }"
                + "    a[x-apple-data-detectors='true'] {"
                + "      color: inherit !important;"
                + "      text-decoration: none !important;"
                + "    }"
                + "    table, td {"
                + "      color: #000000;"
                + "    }"
                + "  </style>"
                + "  <!--[if !mso]><!-->"
                + "  <link href=\"https://fonts.googleapis.com/css?family=Open+Sans:400,700\" rel=\"stylesheet\" type=\"text/css\">"
                + "  <link href=\"https://fonts.googleapis.com/css?family=Source+Sans+Pro:400,700\" rel=\"stylesheet\" type=\"text/css\">"
                + "  <link href=\"https://fonts.googleapis.com/css?family=Cabin:400,700\" rel=\"stylesheet\" type=\"text/css\">"
                + "  <!--<![endif]-->"
                + "</head>"
                + "<body class=\"clean-body u_body\" style=\"margin: 0;padding: 0;-webkit-text-size-adjust: 100%;background-color: #f9f9f9;color: #000000\">"
                + "  <!--[if IE]><div class=\"ie-container\"><![endif]-->"
                + "  <!--[if mso]><div class=\"mso-container\"><![endif]-->"
                + "  <table style=\"border-collapse: collapse;table-layout: fixed;border-spacing: 0;mso-table-lspace: 0pt;mso-table-rspace: 0pt;vertical-align: top;min-width: 320px;Margin: 0 auto;background-color: #f9f9f9;width:100%\" cellpadding=\"0\" cellspacing=\"0\">"
                + "    <tbody>"
                + "      <tr style=\"vertical-align: top\">"
                + "        <td style=\"word-break: break-word;border-collapse: collapse !important;vertical-align: top\">"
                + "          <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td align=\"center\" style=\"background-color: #f9f9f9;\"><![endif]-->"
                + "          <div class=\"u-row-container\" style=\"padding: 0px;background-color: transparent\">"
                + "            <div class=\"u-row\" style=\"margin: 0 auto;min-width: 320px;max-width: 600px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: #ffffff;\">"
                + "              <div style=\"border-collapse: collapse;display: table;width: 100%;height: 100%;background-color: transparent;\">"
                + "                <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding: 0px;background-color: transparent;\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:600px;\"><tr style=\"background-color: #ffffff;\"><![endif]-->"
                + "                <!--[if (mso)|(IE)]><td align=\"center\" width=\"600\" style=\"width: 600px;padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\" valign=\"top\"><![endif]-->"
                + "                <div class=\"u-col u-col-100\" style=\"max-width: 320px;min-width: 600px;display: table-cell;vertical-align: top;\">"
                + "                  <div style=\"height: 100%;width: 100% !important;\">"
                + "                    <!--[if (!mso)&(!IE)]><!-->"
                + "                    <div style=\"box-sizing: border-box; height: 100%; padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\">"
                + "                      <!--<![endif]-->"
                + "                      <table style=\"font-family:'Cabin',sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">"
                + "                        <tbody>"
                + "                          <tr>"
                + "                            <td style=\"overflow-wrap:break-word;word-break:break-word;padding:20px;font-family:'Cabin',sans-serif;\" align=\"left\">"
                + "                              <table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">"
                + "                                <tr>"
                + "                                  <td style=\"padding-right: 0px;padding-left: 0px;\" align=\"center\">"
                + "                                    <img align=\"center\" border=\"0\" src=\"https://i.ibb.co/pQx7w7S/Chat-Wave-Logo.png\" alt=\"Image\" title=\"Image\" style=\"outline: none;text-decoration: none;-ms-interpolation-mode: bicubic;clear: both;display: inline-block !important;border: none;height: auto;float: none;width: 43%;max-width: 240.8px;\" width=\"240.8\" />"
                + "                                  </td>"
                + "                                </tr>"
                + "                              </table>"
                + "                            </td>"
                + "                          </tr>"
                + "                        </tbody>"
                + "                      </table>"
                + "                      <!--[if (!mso)&(!IE)]><!-->"
                + "                    </div>"
                + "                    <!--<![endif]-->"
                + "                  </div>"
                + "                </div>"
                + "                <!--[if (mso)|(IE)]></td><![endif]-->"
                + "                <!--[if (mso)|(IE)]></tr></table></td></tr></table><![endif]-->"
                + "              </div>"
                + "            </div>"
                + "          </div>"
                + "          <div class=\"u-row-container\" style=\"padding: 0px;background-color: transparent\">"
                + "            <div class=\"u-row\" style=\"margin: 0 auto;min-width: 320px;max-width: 600px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: #003399;\">"
                + "              <div style=\"border-collapse: collapse;display: table;width: 100%;height: 100%;background-color: transparent;\">"
                + "                <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding: 0px;background-color: transparent;\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:600px;\"><tr style=\"background-color: #003399;\"><![endif]-->"
                + "                <!--[if (mso)|(IE)]><td align=\"center\" width=\"600\" style=\"background: linear-gradient(-38deg, rgba(150, 149, 230, 1) 0%, rgb(60, 60, 190) 50%, rgba(80, 90, 170, 1) 100%);width: 600px;padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\" valign=\"top\"><![endif]-->"
                + "                <div class=\"u-col u-col-100\" style=\"max-width: 320px;min-width: 600px;display: table-cell;vertical-align: top;\">"
                + "                  <div style=\"background-color: #1271FF; height: 100%;width: 100% !important;\">"
                + "                    <!--[if (!mso)&(!IE)]><!-->"
                + "                    <div style=\"box-sizing: border-box; height: 100%; padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\">"
                + "                      <!--<![endif]-->"
                + "                      <table style=\"font-family:'Cabin',sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">"
                + "                        <tbody>"
                + "                          <tr>"
                + "                            <td style=\"overflow-wrap:break-word;word-break:break-word;padding:40px 10px 10px;font-family:'Cabin',sans-serif;\" align=\"left\">"
                + "                              <table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">"
                + "                                <tr>"
                + "                                  <td style=\"padding-right: 0px;padding-left: 0px;\" align=\"center\">"
                + "                                    <img align=\"center\" border=\"0\" src=\"https://cdn.templates.unlayer.com/assets/1597218650916-xxxxc.png\" alt=\"Image\" title=\"Image\" style=\"outline: none;text-decoration: none;-ms-interpolation-mode: bicubic;clear: both;display: inline-block !important;border: none;height: auto;float: none;width: 26%;max-width: 150.8px;\" width=\"150.8\" />"
                + "                                  </td>"
                + "                                </tr>"
                + "                              </table>"
                + "                            </td>"
                + "                          </tr>"
                + "                        </tbody>"
                + "                      </table>"
                + "                      <table style=\"font-family:'Cabin',sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">"
                + "                        <tbody>"
                + "                          <tr>"
                + "                            <td style=\"overflow-wrap:break-word;word-break:break-word;padding:10px;font-family:'Cabin',sans-serif;\" align=\"left\">"
                + "                              <div style=\"font-size: 14px; color: #e5eaf5; line-height: 140%; text-align: center; word-wrap: break-word;\">"
                + "                                <p style=\"font-size: 14px; line-height: 140%;\"><strong>T H A N K S&nbsp; &nbsp;F O R&nbsp; &nbsp;JOINING !</strong></p>"
                + "                              </div>"
                + "                            </td>"
                + "                          </tr>"
                + "                        </tbody>"
                + "                      </table>"
                + "                      <table style=\"font-family:'Cabin',sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">"
                + "                        <tbody>"
                + "                          <tr>"
                + "                            <td style=\"overflow-wrap:break-word;word-break:break-word;padding:0px 10px 31px;font-family:'Cabin',sans-serif;\" align=\"left\">"
                + "                              <div style=\"font-size: 14px; color: #e5eaf5; line-height: 140%; text-align: center; word-wrap: break-word;\">"
                + "                                <p style=\"font-size: 14px; line-height: 140%;\"><span style=\"font-size: 28px; line-height: 39.2px;\"><strong><span style=\"line-height: 39.2px; font-size: 28px;\">Reset Your Password</span></strong>"
                + "                                  </span>"
                + "                                </p>"
                + "                              </div>"
                + "                            </td>"
                + "                          </tr>"
                + "                        </tbody>"
                + "                      </table>"
                + "                      <!--[if (!mso)&(!IE)]><!-->"
                + "                    </div>"
                + "                    <!--<![endif]-->"
                + "                  </div>"
                + "                </div>"
                + "                <!--[if (mso)|(IE)]></td><![endif]-->"
                + "                <!--[if (mso)|(IE)]></tr></table></td></tr></table><![endif]-->"
                + "              </div>"
                + "            </div>"
                + "          </div>"
                + "          <div class=\"u-row-container\" style=\"padding: 0px;background-color: transparent\">"
                + "            <div class=\"u-row\" style=\"margin: 0 auto;min-width: 320px;max-width: 600px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: #ffffff;\">"
                + "              <div style=\"border-collapse: collapse;display: table;width: 100%;height: 100%;background-color: transparent;\">"
                + "                <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding: 0px;background-color: transparent;\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:600px;\"><tr style=\"background-color: #ffffff;\"><![endif]-->"
                + "                <!--[if (mso)|(IE)]><td align=\"center\" width=\"600\" style=\"width: 600px;padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\" valign=\"top\"><![endif]-->"
                + "                <div class=\"u-col u-col-100\" style=\"max-width: 320px;min-width: 600px;display: table-cell;vertical-align: top;\">"
                + "                  <div style=\"height: 100%;width: 100% !important;\">"
                + "                    <!--[if (!mso)&(!IE)]><!-->"
                + "                    <div style=\"box-sizing: border-box; height: 100%; padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\">"
                + "                      <!--<![endif]-->"
                + "                      <table style=\"font-family:'Cabin',sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">"
                + "                        <tbody>"
                + "                          <tr>"
                + "                            <td style=\"overflow-wrap:break-word;word-break:break-word;padding:33px 55px;font-family:'Cabin',sans-serif;\" align=\"left\">"
                + "                              <div style=\"font-size: 14px; line-height: 160%; text-align: center; word-wrap: break-word;\">"
                + "                                <p style=\"font-size: 13px; line-height: 160%; text-align: left;\"><span style=\"font-size: 22px; line-height: 35.2px;\">Hello Waver,</span><span style=\"font-size: 22px; line-height: 35.2px;\"></span></p><br/>"
                + "                                <p style=\"font-size: 14px; line-height: 160%; text-align: left;\"><span style=\"font-size: 16px; line-height: 25.6px; font-family: 'Open Sans', sans-serif;\">You're almost ready to instant wave your communications. Please enter below OTP code to reset your password</span></p>"
                + "                              </div>"
                + "                            </td>"
                + "                          </tr>"
                + "                        </tbody>"
                + "                      </table>"
                + "                      <table style=\"font-family:'Cabin',sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">"
                + "                        <tbody>"
                + "                          <tr>"
                + "                            <td style=\"overflow-wrap:break-word;word-break:break-word;padding:10px 55px;font-family:'Cabin',sans-serif;\" align=\"left\">"
                + "                              <div style=\"font-size: 14px; line-height: 20%; text-align: center; word-wrap: break-word;\">"
                + "                                <p style=\"font-size: 14px; line-height: 20%; text-align: center;\"><span style=\"color: #1271FF; line-height: 5.6px; font-size: 28px; font-family: 'Source Sans Pro', sans-serif;\"><strong><span style=\"line-height: 5.6px;\">" + otp + "</span></strong>"
                + "                                  </span><span style=\"font-size: 22px; line-height: 4.4px;\"></span></p>"
                + "                              </div>"
                + "                            </td>"
                + "                          </tr>"
                + "                        </tbody>"
                + "                      </table>"
                + "                      <table style=\"font-family:'Cabin',sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">"
                + "                        <tbody>"
                + "                          <tr>"
                + "                            <td style=\"overflow-wrap:break-word;word-break:break-word;padding:33px 55px 60px;font-family:'Cabin',sans-serif;\" align=\"left\">"
                + "                              <div style=\"font-size: 14px; line-height: 160%; text-align: center; word-wrap: break-word;\">"
                + "                                <p style=\"line-height: 160%; text-align: left;\"><span style=\"font-size: 16px; line-height: 25.6px; font-family: 'Open Sans', sans-serif;\">Regards,</span></p>"
                + "                                <p style=\"line-height: 160%; text-align: left;\"><span style=\"font-size: 16px; line-height: 25.6px; font-family: 'Open Sans', sans-serif;\">Chat Wave Team</span></p>"
                + "                              </div>"
                + "                            </td>"
                + "                          </tr>"
                + "                        </tbody>"
                + "                      </table>"
                + "                      <!--[if (!mso)&(!IE)]><!-->"
                + "                    </div>"
                + "                    <!--<![endif]-->"
                + "                  </div>"
                + "                </div>"
                + "                <!--[if (mso)|(IE)]></td><![endif]-->"
                + "                <!--[if (mso)|(IE)]></tr></table></td></tr></table><![endif]-->"
                + "              </div>"
                + "            </div>"
                + "          </div>"
                + "          <div class=\"u-row-container\" style=\"padding: 0px;background-color: transparent\">"
                + "            <div class=\"u-row\" style=\"margin: 0 auto;min-width: 320px;max-width: 600px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: #003399;\">"
                + "              <div style=\"border-collapse: collapse;display: table;width: 100%;height: 100%;background-color: transparent;\">"
                + "                <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding: 0px;background-color: transparent;\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:600px;\"><tr style=\"background-color: #003399;\"><![endif]-->"
                + "                <!--[if (mso)|(IE)]><td align=\"center\" width=\"600\" style=\"background: linear-gradient(-38deg, rgba(150, 149, 230, 1) 0%, rgb(60, 60, 190) 50%, rgba(80, 90, 170, 1) 100%);width: 600px;padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\" valign=\"top\"><![endif]-->"
                + "                <div class=\"u-col u-col-100\" style=\"max-width: 320px;min-width: 600px;display: table-cell;vertical-align: top;\">"
                + "                  <div style=\"background-color: #1271FF;height: 100%;width: 100% !important;\">"
                + "                    <!--[if (!mso)&(!IE)]><!-->"
                + "                    <div style=\"box-sizing: border-box; height: 100%; padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\">"
                + "                      <!--<![endif]-->"
                + "                      <table style=\"font-family:'Cabin',sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">"
                + "                        <tbody>"
                + "                          <tr>"
                + "                            <td style=\"overflow-wrap:break-word;word-break:break-word;padding:10px;font-family:'Cabin',sans-serif;\" align=\"left\">"
                + "                              <div style=\"font-size: 14px; color: #fafafa; line-height: 180%; text-align: center; word-wrap: break-word;\">"
                + "                                <p style=\"font-size: 14px; line-height: 180%;\"><span style=\"font-size: 16px; line-height: 28.8px;\">Copyrights © Code Crafters Team!</span></p>"
                + "                              </div>"
                + "                            </td>"
                + "                          </tr>"
                + "                        </tbody>"
                + "                      </table>"
                + "                      <!--[if (!mso)&(!IE)]><!-->"
                + "                    </div>"
                + "                    <!--<![endif]-->"
                + "                  </div>"
                + "                </div>"
                + "                <!--[if (mso)|(IE)]></td><![endif]-->"
                + "                <!--[if (mso)|(IE)]></tr></table></td></tr></table><![endif]-->"
                + "              </div>"
                + "            </div>"
                + "          </div>"
                + "          <!--[if (mso)|(IE)]></td></tr></table><![endif]-->"
                + "        </td>"
                + "      </tr>"
                + "    </tbody>"
                + "  </table>"
                + "  <!--[if mso]></div><![endif]-->"
                + "  <!--[if IE]></div><![endif]-->"
                + "</body>"
                + "</html>";
    }

    private String generateReactivateAccountHtml(String otp) {
        return "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional //EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">"
                + "<html xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:v=\"urn:schemas-microsoft-com:vml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\">"
                + "<head>"
                + "  <!--[if gte mso 9]>"
                + "  <xml>"
                + "    <o:OfficeDocumentSettings>"
                + "      <o:AllowPNG/>"
                + "      <o:PixelsPerInch>96</o:PixelsPerInch>"
                + "    </o:OfficeDocumentSettings>"
                + "  </xml>"
                + "  <![endif]-->"
                + "  <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">"
                + "  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">"
                + "  <meta name=\"x-apple-disable-message-reformatting\">"
                + "  <!--[if !mso]><!-->"
                + "  <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">"
                + "  <!--<![endif]-->"
                + "  <title></title>"
                + "  <style type=\"text/css\">"
                + "    @media only screen and (min-width: 620px) {"
                + "      .u-row {"
                + "        width: 600px !important;"
                + "      }"
                + "      .u-row .u-col {"
                + "        vertical-align: top;"
                + "      }"
                + "      .u-row .u-col-100 {"
                + "        width: 600px !important;"
                + "      }"
                + "    }"
                + "    @media (max-width: 620px) {"
                + "      .u-row-container {"
                + "        max-width: 100% !important;"
                + "        padding-left: 0px !important;"
                + "        padding-right: 0px !important;"
                + "      }"
                + "      .u-row .u-col {"
                + "        min-width: 320px !important;"
                + "        max-width: 100% !important;"
                + "        display: block !important;"
                + "      }"
                + "      .u-row {"
                + "        width: 100% !important;"
                + "      }"
                + "      .u-col {"
                + "        width: 100% !important;"
                + "      }"
                + "      .u-col > div {"
                + "        margin: 0 auto;"
                + "      }"
                + "    }"
                + "    body {"
                + "      margin: 0;"
                + "      padding: 0;"
                + "    }"
                + "    table, tr, td {"
                + "      vertical-align: top;"
                + "      border-collapse: collapse;"
                + "    }"
                + "    p {"
                + "      margin: 0;"
                + "    }"
                + "    .ie-container table, .mso-container table {"
                + "      table-layout: fixed;"
                + "    }"
                + "    * {"
                + "      line-height: inherit;"
                + "    }"
                + "    a[x-apple-data-detectors='true'] {"
                + "      color: inherit !important;"
                + "      text-decoration: none !important;"
                + "    }"
                + "    table, td {"
                + "      color: #000000;"
                + "    }"
                + "  </style>"
                + "  <!--[if !mso]><!-->"
                + "  <link href=\"https://fonts.googleapis.com/css?family=Open+Sans:400,700\" rel=\"stylesheet\" type=\"text/css\">"
                + "  <link href=\"https://fonts.googleapis.com/css?family=Source+Sans+Pro:400,700\" rel=\"stylesheet\" type=\"text/css\">"
                + "  <link href=\"https://fonts.googleapis.com/css?family=Cabin:400,700\" rel=\"stylesheet\" type=\"text/css\">"
                + "  <!--<![endif]-->"
                + "</head>"
                + "<body class=\"clean-body u_body\" style=\"margin: 0;padding: 0;-webkit-text-size-adjust: 100%;background-color: #f9f9f9;color: #000000\">"
                + "  <!--[if IE]><div class=\"ie-container\"><![endif]-->"
                + "  <!--[if mso]><div class=\"mso-container\"><![endif]-->"
                + "  <table style=\"border-collapse: collapse;table-layout: fixed;border-spacing: 0;mso-table-lspace: 0pt;mso-table-rspace: 0pt;vertical-align: top;min-width: 320px;Margin: 0 auto;background-color: #f9f9f9;width:100%\" cellpadding=\"0\" cellspacing=\"0\">"
                + "    <tbody>"
                + "      <tr style=\"vertical-align: top\">"
                + "        <td style=\"word-break: break-word;border-collapse: collapse !important;vertical-align: top\">"
                + "          <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td align=\"center\" style=\"background-color: #f9f9f9;\"><![endif]-->"
                + "          <div class=\"u-row-container\" style=\"padding: 0px;background-color: transparent\">"
                + "            <div class=\"u-row\" style=\"margin: 0 auto;min-width: 320px;max-width: 600px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: #ffffff;\">"
                + "              <div style=\"border-collapse: collapse;display: table;width: 100%;height: 100%;background-color: transparent;\">"
                + "                <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding: 0px;background-color: transparent;\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:600px;\"><tr style=\"background-color: #ffffff;\"><![endif]-->"
                + "                <!--[if (mso)|(IE)]><td align=\"center\" width=\"600\" style=\"width: 600px;padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\" valign=\"top\"><![endif]-->"
                + "                <div class=\"u-col u-col-100\" style=\"max-width: 320px;min-width: 600px;display: table-cell;vertical-align: top;\">"
                + "                  <div style=\"height: 100%;width: 100% !important;\">"
                + "                    <!--[if (!mso)&(!IE)]><!-->"
                + "                    <div style=\"box-sizing: border-box; height: 100%; padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\">"
                + "                      <!--<![endif]-->"
                + "                      <table style=\"font-family:'Cabin',sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">"
                + "                        <tbody>"
                + "                          <tr>"
                + "                            <td style=\"overflow-wrap:break-word;word-break:break-word;padding:20px;font-family:'Cabin',sans-serif;\" align=\"left\">"
                + "                              <table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">"
                + "                                <tr>"
                + "                                  <td style=\"padding-right: 0px;padding-left: 0px;\" align=\"center\">"
                + "                                    <img align=\"center\" border=\"0\" src=\"https://i.ibb.co/pQx7w7S/Chat-Wave-Logo.png\" alt=\"Image\" title=\"Image\" style=\"outline: none;text-decoration: none;-ms-interpolation-mode: bicubic;clear: both;display: inline-block !important;border: none;height: auto;float: none;width: 43%;max-width: 240.8px;\" width=\"240.8\" />"
                + "                                  </td>"
                + "                                </tr>"
                + "                              </table>"
                + "                            </td>"
                + "                          </tr>"
                + "                        </tbody>"
                + "                      </table>"
                + "                      <!--[if (!mso)&(!IE)]><!-->"
                + "                    </div>"
                + "                    <!--<![endif]-->"
                + "                  </div>"
                + "                </div>"
                + "                <!--[if (mso)|(IE)]></td><![endif]-->"
                + "                <!--[if (mso)|(IE)]></tr></table></td></tr></table><![endif]-->"
                + "              </div>"
                + "            </div>"
                + "          </div>"
                + "          <div class=\"u-row-container\" style=\"padding: 0px;background-color: transparent\">"
                + "            <div class=\"u-row\" style=\"margin: 0 auto;min-width: 320px;max-width: 600px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: #003399;\">"
                + "              <div style=\"border-collapse: collapse;display: table;width: 100%;height: 100%;background-color: transparent;\">"
                + "                <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding: 0px;background-color: transparent;\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:600px;\"><tr style=\"background-color: #003399;\"><![endif]-->"
                + "                <!--[if (mso)|(IE)]><td align=\"center\" width=\"600\" style=\"background: linear-gradient(-38deg, rgba(150, 149, 230, 1) 0%, rgb(60, 60, 190) 50%, rgba(80, 90, 170, 1) 100%);width: 600px;padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\" valign=\"top\"><![endif]-->"
                + "                <div class=\"u-col u-col-100\" style=\"max-width: 320px;min-width: 600px;display: table-cell;vertical-align: top;\">"
                + "                  <div style=\"background-color: #1271FF; height: 100%;width: 100% !important;\">"
                + "                    <!--[if (!mso)&(!IE)]><!-->"
                + "                    <div style=\"box-sizing: border-box; height: 100%; padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\">"
                + "                      <!--<![endif]-->"
                + "                      <table style=\"font-family:'Cabin',sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">"
                + "                        <tbody>"
                + "                          <tr>"
                + "                            <td style=\"overflow-wrap:break-word;word-break:break-word;padding:40px 10px 10px;font-family:'Cabin',sans-serif;\" align=\"left\">"
                + "                              <table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">"
                + "                                <tr>"
                + "                                  <td style=\"padding-right: 0px;padding-left: 0px;\" align=\"center\">"
                + "                                    <img align=\"center\" border=\"0\" src=\"https://cdn.templates.unlayer.com/assets/1597218650916-xxxxc.png\" alt=\"Image\" title=\"Image\" style=\"outline: none;text-decoration: none;-ms-interpolation-mode: bicubic;clear: both;display: inline-block !important;border: none;height: auto;float: none;width: 26%;max-width: 150.8px;\" width=\"150.8\" />"
                + "                                  </td>"
                + "                                </tr>"
                + "                              </table>"
                + "                            </td>"
                + "                          </tr>"
                + "                        </tbody>"
                + "                      </table>"
                + "                      <table style=\"font-family:'Cabin',sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">"
                + "                        <tbody>"
                + "                          <tr>"
                + "                            <td style=\"overflow-wrap:break-word;word-break:break-word;padding:10px;font-family:'Cabin',sans-serif;\" align=\"left\">"
                + "                              <div style=\"font-size: 14px; color: #e5eaf5; line-height: 140%; text-align: center; word-wrap: break-word;\">"
                + "                                <p style=\"font-size: 14px; line-height: 140%;\"><strong>GOOD BYE WAVER 👋 !</strong></p>"
                + "                              </div>"
                + "                            </td>"
                + "                          </tr>"
                + "                        </tbody>"
                + "                      </table>"
                + "                      <table style=\"font-family:'Cabin',sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">"
                + "                        <tbody>"
                + "                          <tr>"
                + "                            <td style=\"overflow-wrap:break-word;word-break:break-word;padding:0px 10px 31px;font-family:'Cabin',sans-serif;\" align=\"left\">"
                + "                              <div style=\"font-size: 14px; color: #e5eaf5; line-height: 140%; text-align: center; word-wrap: break-word;\">"
                + "                                <p style=\"font-size: 14px; line-height: 140%;\"><span style=\"font-size: 28px; line-height: 39.2px;\"><strong><span style=\"line-height: 39.2px; font-size: 28px;\">See You Soon Again!</span></strong>"
                + "                                  </span>"
                + "                                </p>"
                + "                              </div>"
                + "                            </td>"
                + "                          </tr>"
                + "                        </tbody>"
                + "                      </table>"
                + "                      <!--[if (!mso)&(!IE)]><!-->"
                + "                    </div>"
                + "                    <!--<![endif]-->"
                + "                  </div>"
                + "                </div>"
                + "                <!--[if (mso)|(IE)]></td><![endif]-->"
                + "                <!--[if (mso)|(IE)]></tr></table></td></tr></table><![endif]-->"
                + "              </div>"
                + "            </div>"
                + "          </div>"
                + "          <div class=\"u-row-container\" style=\"padding: 0px;background-color: transparent\">"
                + "            <div class=\"u-row\" style=\"margin: 0 auto;min-width: 320px;max-width: 600px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: #ffffff;\">"
                + "              <div style=\"border-collapse: collapse;display: table;width: 100%;height: 100%;background-color: transparent;\">"
                + "                <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding: 0px;background-color: transparent;\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:600px;\"><tr style=\"background-color: #ffffff;\"><![endif]-->"
                + "                <!--[if (mso)|(IE)]><td align=\"center\" width=\"600\" style=\"width: 600px;padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\" valign=\"top\"><![endif]-->"
                + "                <div class=\"u-col u-col-100\" style=\"max-width: 320px;min-width: 600px;display: table-cell;vertical-align: top;\">"
                + "                  <div style=\"height: 100%;width: 100% !important;\">"
                + "                    <!--[if (!mso)&(!IE)]><!-->"
                + "                    <div style=\"box-sizing: border-box; height: 100%; padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\">"
                + "                      <!--<![endif]-->"
                + "                      <table style=\"font-family:'Cabin',sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">"
                + "                        <tbody>"
                + "                          <tr>"
                + "                            <td style=\"overflow-wrap:break-word;word-break:break-word;padding:33px 55px;font-family:'Cabin',sans-serif;\" align=\"left\">"
                + "                              <div style=\"font-size: 14px; line-height: 160%; text-align: center; word-wrap: break-word;\">"
                + "                                <p style=\"font-size: 13px; line-height: 160%; text-align: left;\"><span style=\"font-size: 22px; line-height: 35.2px;\">Hello Waver,</span><span style=\"font-size: 22px; line-height: 35.2px;\"></span></p><br/>"
                + "                                <p style=\"font-size: 14px; line-height: 160%; text-align: left;\"><span style=\"font-size: 16px; line-height: 25.6px; font-family: 'Open Sans', sans-serif;\">Sad to hear you are leaving Chat Wave. But if you decide to comeback again, You can use the below OTP to reactivate account. Remember your data is safe in hands & chats will still be available after deactivation. You can revisit chats after activation. Click <a href='http://127.0.0.1:5173/reactivate-account'>here</a> to reactivate account!</span></p>"
                + "                              </div>"
                + "                            </td>"
                + "                          </tr>"
                + "                        </tbody>"
                + "                      </table>"
                + "                      <table style=\"font-family:'Cabin',sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">"
                + "                        <tbody>"
                + "                          <tr>"
                + "                            <td style=\"overflow-wrap:break-word;word-break:break-word;padding:10px 55px;font-family:'Cabin',sans-serif;\" align=\"left\">"
                + "                              <div style=\"font-size: 14px; line-height: 20%; text-align: center; word-wrap: break-word;\">"
                + "                                <p style=\"font-size: 14px; line-height: 20%; text-align: center;\"><span style=\"color: #1271FF; line-height: 5.6px; font-size: 28px; font-family: 'Source Sans Pro', sans-serif;\"><strong><span style=\"line-height: 5.6px;\">" + otp + "</span></strong>"
                + "                                  </span><span style=\"font-size: 22px; line-height: 4.4px;\"></span></p>"
                + "                              </div>"
                + "                            </td>"
                + "                          </tr>"
                + "                        </tbody>"
                + "                      </table>"
                + "                      <table style=\"font-family:'Cabin',sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">"
                + "                        <tbody>"
                + "                          <tr>"
                + "                            <td style=\"overflow-wrap:break-word;word-break:break-word;padding:33px 55px 60px;font-family:'Cabin',sans-serif;\" align=\"left\">"
                + "                              <div style=\"font-size: 14px; line-height: 160%; text-align: center; word-wrap: break-word;\">"
                + "                                <p style=\"line-height: 160%; text-align: left;\"><span style=\"font-size: 16px; line-height: 25.6px; font-family: 'Open Sans', sans-serif;\">Regards,</span></p>"
                + "                                <p style=\"line-height: 160%; text-align: left;\"><span style=\"font-size: 16px; line-height: 25.6px; font-family: 'Open Sans', sans-serif;\">Chat Wave Team</span></p>"
                + "                              </div>"
                + "                            </td>"
                + "                          </tr>"
                + "                        </tbody>"
                + "                      </table>"
                + "                      <!--[if (!mso)&(!IE)]><!-->"
                + "                    </div>"
                + "                    <!--<![endif]-->"
                + "                  </div>"
                + "                </div>"
                + "                <!--[if (mso)|(IE)]></td><![endif]-->"
                + "                <!--[if (mso)|(IE)]></tr></table></td></tr></table><![endif]-->"
                + "              </div>"
                + "            </div>"
                + "          </div>"
                + "          <div class=\"u-row-container\" style=\"padding: 0px;background-color: transparent\">"
                + "            <div class=\"u-row\" style=\"margin: 0 auto;min-width: 320px;max-width: 600px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: #003399;\">"
                + "              <div style=\"border-collapse: collapse;display: table;width: 100%;height: 100%;background-color: transparent;\">"
                + "                <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding: 0px;background-color: transparent;\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:600px;\"><tr style=\"background-color: #003399;\"><![endif]-->"
                + "                <!--[if (mso)|(IE)]><td align=\"center\" width=\"600\" style=\"background: linear-gradient(-38deg, rgba(150, 149, 230, 1) 0%, rgb(60, 60, 190) 50%, rgba(80, 90, 170, 1) 100%);width: 600px;padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\" valign=\"top\"><![endif]-->"
                + "                <div class=\"u-col u-col-100\" style=\"max-width: 320px;min-width: 600px;display: table-cell;vertical-align: top;\">"
                + "                  <div style=\"background-color: #1271FF;height: 100%;width: 100% !important;\">"
                + "                    <!--[if (!mso)&(!IE)]><!-->"
                + "                    <div style=\"box-sizing: border-box; height: 100%; padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\">"
                + "                      <!--<![endif]-->"
                + "                      <table style=\"font-family:'Cabin',sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">"
                + "                        <tbody>"
                + "                          <tr>"
                + "                            <td style=\"overflow-wrap:break-word;word-break:break-word;padding:10px;font-family:'Cabin',sans-serif;\" align=\"left\">"
                + "                              <div style=\"font-size: 14px; color: #fafafa; line-height: 180%; text-align: center; word-wrap: break-word;\">"
                + "                                <p style=\"font-size: 14px; line-height: 180%;\"><span style=\"font-size: 16px; line-height: 28.8px;\">Copyrights © Code Crafters Team!</span></p>"
                + "                              </div>"
                + "                            </td>"
                + "                          </tr>"
                + "                        </tbody>"
                + "                      </table>"
                + "                      <!--[if (!mso)&(!IE)]><!-->"
                + "                    </div>"
                + "                    <!--<![endif]-->"
                + "                  </div>"
                + "                </div>"
                + "                <!--[if (mso)|(IE)]></td><![endif]-->"
                + "                <!--[if (mso)|(IE)]></tr></table></td></tr></table><![endif]-->"
                + "              </div>"
                + "            </div>"
                + "          </div>"
                + "          <!--[if (mso)|(IE)]></td></tr></table><![endif]-->"
                + "        </td>"
                + "      </tr>"
                + "    </tbody>"
                + "  </table>"
                + "  <!--[if mso]></div><![endif]-->"
                + "  <!--[if IE]></div><![endif]-->"
                + "</body>"
                + "</html>";
    }
}


package io.hsiao.devops.clib.mail;

import io.hsiao.devops.clib.exception.Exception;
import io.hsiao.devops.clib.exception.RuntimeException;
import io.hsiao.devops.clib.logging.Logger;
import io.hsiao.devops.clib.logging.Logger.Level;
import io.hsiao.devops.clib.logging.LoggerFactory;

import java.io.IOException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public final class Mail {
  public Mail(final Properties props) {
    if (props == null) {
      throw new RuntimeException("argument 'props' is null");
    }

    session = Session.getInstance(props, null);
    message = new MimeMessage(session);
    messageBodyPart = new MimeBodyPart();
    attachsBodyPart = new LinkedList<>();
  }

  public static Properties getProperties(final String smtpHost, final String smtpPort) {
    if (smtpHost == null) {
      throw new RuntimeException("argument 'smtpHost' is null");
    }

    if (smtpPort == null) {
      throw new RuntimeException("argument 'smtpPort' is null");
    }

    final Properties props = new Properties();

    props.put("mail.smtp.host", smtpHost);
    props.put("mail.smtp.port", smtpPort);

    return props;
  }

  public void setContent(final Object object, final String type) throws Exception {
    if (object == null) {
      throw new RuntimeException("argument 'object' is null");
    }

    if (type == null) {
      throw new RuntimeException("argument 'type' is null");
    }

    try {
      messageBodyPart.setContent(object, type.trim());
    }
    catch (MessagingException ex) {
      final Exception exception = new Exception("failed to set message content");
      exception.initCause(ex);
      logger.log(Level.INFO, "failed to set message content [" + object + "] [" + type + "]", exception);
      throw exception;
    }
  }

  public void setFrom(final String address) throws Exception {
    if (address == null) {
      throw new RuntimeException("argument 'address' is null");
    }

    try {
      message.setFrom(address.trim());
    }
    catch (MessagingException ex) {
      final Exception exception = new Exception("failed to set message from address [" + address + "]");
      exception.initCause(ex);
      logger.log(Level.INFO, "failed to set message from address [" + address + "]", exception);
      throw exception;
    }
  }

  public void addRecipient(final String type, final String address) throws Exception {
    if (type == null) {
      throw new RuntimeException("argument 'type' is null");
    }

    if (address == null) {
      throw new RuntimeException("argument 'address' is null");
    }

    final Message.RecipientType recipientType;
    try {
      recipientType = (Message.RecipientType) Message.RecipientType.class.getField(type.trim()).get(null);
    }
    catch (java.lang.Exception ex) {
      final Exception exception = new Exception("invalid message recipient type [" + type + "]");
      exception.initCause(ex);
      logger.log(Level.INFO, "invalid message recipient type [" + type + "]", exception);
      throw exception;
    }

    try {
      final Address internetAddress = new InternetAddress(address.trim(), true);
      message.addRecipient(recipientType, internetAddress);
    }
    catch (MessagingException ex) {
      final Exception exception = new Exception("failed to add message recipient [" + type + "] [" + address + "]");
      exception.initCause(ex);
      logger.log(Level.INFO, "failed to add message recipient [" + type + "] [" + address + "]", exception);
      throw exception;
    }
  }

  public void setRecipients(final String type, final String addresses) throws Exception {
    if (type == null) {
      throw new RuntimeException("argument 'type' is null");
    }

    if (addresses == null) {
      throw new RuntimeException("argument 'addresses' is null");
    }

    final Message.RecipientType recipientType;
    try {
      recipientType = (Message.RecipientType) Message.RecipientType.class.getField(type.trim()).get(null);
    }
    catch (java.lang.Exception ex) {
      final Exception exception = new Exception("invalid message recipient type [" + type + "]");
      exception.initCause(ex);
      logger.log(Level.INFO, "invalid message recipient type [" + type + "]", exception);
      throw exception;
    }

    try {
      message.setRecipients(recipientType, addresses.trim());
    }
    catch (MessagingException ex) {
      final Exception exception = new Exception("failed to set message recipients");
      exception.initCause(ex);
      logger.log(Level.INFO, "failed to set message recipients [" + type + "] [" + addresses + "]", exception);
      throw exception;
    }
  }

  public void setRecipients(final String type, final List<String> addresses) throws Exception {
    if (type == null) {
      throw new RuntimeException("argument 'type' is null");
    }

    if (addresses == null) {
      throw new RuntimeException("argument 'addresses' is null");
    }

    final Message.RecipientType recipientType;
    try {
      recipientType = (Message.RecipientType) Message.RecipientType.class.getField(type.trim()).get(null);
    }
    catch (java.lang.Exception ex) {
      final Exception exception = new Exception("invalid message recipient type [" + type + "]");
      exception.initCause(ex);
      logger.log(Level.INFO, "invalid message recipient type [" + type + "]", exception);
      throw exception;
    }

    final StringBuilder sb = new StringBuilder();
    for (final String address: addresses) {
      if (!address.trim().isEmpty()) {
        sb.append(address.trim());
        sb.append(",");
      }
    }

    try {
      message.setRecipients(recipientType, sb.toString());
    }
    catch (MessagingException ex) {
      final Exception exception = new Exception("failed to set message recipients");
      exception.initCause(ex);
      logger.log(Level.INFO, "failed to set message recipients [" + type + "] [" + addresses + "]", exception);
      throw exception;
    }
  }

  public void setRecipients(final String type, final List<String> addresses, final String domain) throws Exception {
    if (type == null) {
      throw new RuntimeException("argument 'type' is null");
    }

    if (addresses == null) {
      throw new RuntimeException("argument 'addresses' is null");
    }

    if (domain == null) {
      throw new RuntimeException("argument 'domain' is null");
    }

    final Message.RecipientType recipientType;
    try {
      recipientType = (Message.RecipientType) Message.RecipientType.class.getField(type.trim()).get(null);
    }
    catch (java.lang.Exception ex) {
      final Exception exception = new Exception("invalid message recipient type [" + type + "]");
      exception.initCause(ex);
      logger.log(Level.INFO, "invalid message recipient type [" + type + "]", exception);
      throw exception;
    }

    final StringBuilder sb = new StringBuilder();
    for (final String address: addresses) {
      if (address.trim().isEmpty()) {
        continue;
      }
      if (!address.contains("@")) {
        sb.append(address.trim() + "@" + domain.trim());
      }
      else {
        sb.append(address.trim());
      }
      sb.append(",");
    }

    try {
      message.setRecipients(recipientType, sb.toString());
    }
    catch (MessagingException ex) {
      final Exception exception = new Exception("failed to set message recipients");
      exception.initCause(ex);
      logger.log(Level.INFO, "failed to set message recipients [" + type + "] [" + addresses + "] [" + domain + "]", exception);
      throw exception;
    }
  }

  public void setSentDate(final Date date) throws Exception {
    if (date == null) {
      throw new RuntimeException("argument 'date' is null");
    }

    try {
      message.setSentDate(date);
    }
    catch (MessagingException ex) {
      final Exception exception = new Exception("failed to set message sent date [" + date + "]");
      exception.initCause(ex);
      logger.log(Level.INFO, "failed to set message sent date [" + date + "]", exception);
      throw exception;
    }
  }

  public void setSubject(final String subject, final String charset) throws Exception {
    if (subject == null) {
      throw new RuntimeException("argument 'subject' is null");
    }

    if (charset == null) {
      throw new RuntimeException("argument 'charset' is null");
    }

    try {
      message.setSubject(subject, charset.trim());
    }
    catch (MessagingException ex) {
      final Exception exception = new Exception("failed to set message subject");
      exception.initCause(ex);
      logger.log(Level.INFO, "failed to set message subject [" + subject + "] [" + charset + "]", exception);
      throw exception;
    }
  }

  public void attachFile(final String file) throws Exception {
    if (file == null) {
      throw new RuntimeException("argument 'file' is null");
    }

    final MimeBodyPart attachBodyPart = new MimeBodyPart();

    try {
      attachBodyPart.attachFile(file);
      attachsBodyPart.add(attachBodyPart);
    }
    catch (IOException | MessagingException ex) {
      final Exception exception = new Exception("failed to attach file [" + file + "]");
      exception.initCause(ex);
      logger.log(Level.INFO, "failed to attach file [" + file + "]", exception);
      throw exception;
    }
  }

  public void send(final String username, final String password) throws Exception {
    if (username == null) {
      throw new RuntimeException("argument 'username' is null");
    }

    if (password == null) {
      throw new RuntimeException("argument 'password' is null");
    }

    try {
      final Multipart multipart = new MimeMultipart();
      multipart.addBodyPart(messageBodyPart);

      for (final MimeBodyPart attachBodyPart: attachsBodyPart) {
        multipart.addBodyPart(attachBodyPart);
      }

      message.setContent(multipart);

      Transport.send(message, username, password);
    }
    catch (MessagingException ex) {
      final Exception exception = new Exception("failed to send message");
      exception.initCause(ex);
      logger.log(Level.INFO, "failed to send message", exception);
      throw exception;
    }
  }

  private static final Logger logger = LoggerFactory.getLogger(Mail.class);

  private final Session session;
  private final MimeMessage message;
  private final MimeBodyPart messageBodyPart;
  private final List<MimeBodyPart> attachsBodyPart;

  public static final String RecipientTypeBCC = "BCC";
  public static final String RecipientTypeCC = "CC";
  public static final String RecipientTypeTO = "TO";
}

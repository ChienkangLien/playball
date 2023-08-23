package com.tibame.tga105.service;

import java.io.File;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class MailService {

	@Autowired
	private JavaMailSenderImpl mailSender;

	public void sendEmail(String to, String bookDate, String timeSlot) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("yourGoogleMail"); // 這邊使用google，供應商可在.properties配置
		message.setTo(to);
		message.setSubject(bookDate + timeSlot + " 候補上囉");
		message.setText("務必出席!!!  若無法出席請再取消預約");
		mailSender.setPassword(System.getenv("mail_password")); // 這邊是將密碼設定在環境變數中，也可明文方式寫在.properties中
		mailSender.send(message);
	}

	public void sendHighLevelEmail(String to, String bookDate, String timeSlot) {
		MimeMessage message = mailSender.createMimeMessage();
		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, true); // 夾帶檔案的話，第二個參數要給true
			helper.setFrom("yourGoogleMail"); // 這邊使用google，供應商可在.properties配置
			helper.setTo(to);
			helper.setSubject(bookDate + timeSlot + " 候補上囉");
			helper.setText("第二個參數給true、可以解析html", true);
			helper.addAttachment("夾帶檔案", new File("檔案路徑"));
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		mailSender.setPassword(System.getenv("mail_password")); // 這邊是將密碼設定在環境變數中，也可明文方式寫在.properties中
		mailSender.send(message);

	}
}

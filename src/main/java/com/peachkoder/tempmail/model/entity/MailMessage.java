package com.peachkoder.tempmail.model.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class MailMessage implements Serializable {
 
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String from;
	private String subject;
	private Date date;
	private List<Attachment> attachments = new ArrayList<>();
	private String body;
	private String textBody;
	private String htmlBody; 
	
	class Attachment {
		private String filename;
		private String contentType;
		private Long size;
	}

}

//{
//	"id": 639,
//	"from": "someone@example.com",
//	"subject": "Some subject",
//	"date": "2018-06-08 14:33:55",
//	"attachments": [{
//		"filename": "iometer.pdf",
//		"contentType": "application\/pdf",
//		"size": 47412
//	}],
//	"body": "Some message body\n\n",
//	"textBody": "Some message body\n\n",
//	"htmlBody": ""
//}
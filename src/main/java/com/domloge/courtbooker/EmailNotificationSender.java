package com.domloge.courtbooker;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.domloge.courtbooker.criteria.TimeSlotCriteria;
import com.domloge.courtbooker.domain.TimeSlot;

@Component
public class EmailNotificationSender implements NotificationSender {
	
	@Autowired
	private TemplateEngine templateEngine;
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Value("${notification_recipient:rdomloge@gmail.com}")
	private String notification_recipient;
	
	@SuppressWarnings("unchecked")
	private List<List<TimeSlot>> convertToTemplateStructure(List<TimeSlot> slots) {
		if(null == slots || slots.size() < 1) return Collections.EMPTY_LIST;
		List<List<TimeSlot>> list = new LinkedList<>();
		List<TimeSlot> currentDayList = new LinkedList<TimeSlot>();
		list.add(currentDayList);
		int currentDay = slots.get(0).getDate().getDayOfWeek();
		for (TimeSlot slot : slots) {
			int dayOfWeek = slot.getDate().getDayOfWeek();
			if(dayOfWeek != currentDay) {
				currentDayList = new LinkedList<TimeSlot>();
				list.add(currentDayList);
				currentDay = dayOfWeek;
			}
			currentDayList.add(slot);
		}
		return list;
	}
	
	@Override
	public void sendNoSlotFail(TimeSlotCriteria criteria, List<TimeSlot> slots) {
		Context context = new Context();
        context.setVariable("availableSlots", convertToTemplateStructure(slots));
        context.setVariable("criteria", criteria);
        String content = templateEngine.process("no-slot-template", context);
		prepareAndSend(notification_recipient, content);
	}
	
	@Override
	public void sendSuccess(TimeSlotCriteria criteria, List<TimeSlot> matched, List<TimeSlot> booked) {
		Context context = new Context();
        context.setVariable("matchedSlots", convertToTemplateStructure(matched));
        context.setVariable("bookedSlots", convertToTemplateStructure(booked));
        context.setVariable("criteria", criteria);
        String content = templateEngine.process("successTemplate", context);
		prepareAndSend(notification_recipient, content);
	}

	@Override
	public void sendBookingFail(TimeSlotCriteria criteria, List<TimeSlot> matched, String failMessage) {
		Context context = new Context();
        context.setVariable("matchedSlots", convertToTemplateStructure(matched));
        context.setVariable("criteria", criteria);
        String content = templateEngine.process("booking-fail-template", context);
		prepareAndSend(notification_recipient, content);
	}
	
	public void prepareAndSend(String recipient, String message) {
	    MimeMessagePreparator messagePreparator = mimeMessage -> {
	        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
	        messageHelper.setFrom(recipient);
	        messageHelper.setTo(recipient);
	        messageHelper.setSubject("Courtenator");
	        messageHelper.setText("Please use an HTML-capable mail client", message);
	    };

	    mailSender.send(messagePreparator);
	}
}

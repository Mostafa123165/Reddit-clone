package com.practice.reddit.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NotificationEmailDto {
    private String subject;
    private String recipient;
    private String body;
}

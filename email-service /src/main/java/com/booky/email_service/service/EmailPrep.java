package com.booky.email_service.service;

import java.util.Map;

public interface EmailPrep {
    Map<String,Object> prepareVerificationEmail(String otp);
}

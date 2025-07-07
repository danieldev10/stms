package ng.edu.aun.stms.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;

@Service
public class TwilioService {
    @Value("${twilio.account_sid}")
    private String accountSid;

    @Value("${twilio.auth_token}")
    private String authToken;

    @Value("${twilio.trial_number}")
    private String trialNumber;

    public TwilioService(@Value("${twilio.account_sid}") String accountSid,
            @Value("${twilio.auth_token}") String authToken,
            @Value("${twilio.trial_number}") String trialNumber) {
        System.out.println("Account SID: " + accountSid);
        System.out.println("Auth Token: " + authToken);
        System.out.println("Trial Number: " + trialNumber);

        Twilio.init(accountSid, authToken);
    }

    public void sendSms(String to, String body) {
        Message.creator(
                new com.twilio.type.PhoneNumber(to),
                new com.twilio.type.PhoneNumber(trialNumber),
                body).create();
    }
}
package com.plazoleta.messaging.adapters.driven.twilio.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "twilio")
public class TwilioConfig {

    private String accountSid;
    private String authToken;
    private String fromPhoneNumber;
    private boolean enabled;

    public TwilioConfig() {}

    public String getAccountSid() { return accountSid; }
    public void setAccountSid(String accountSid) { this.accountSid = accountSid; }

    public String getAuthToken() { return authToken; }
    public void setAuthToken(String authToken) { this.authToken = authToken; }

    public String getFromPhoneNumber() { return fromPhoneNumber; }
    public void setFromPhoneNumber(String fromPhoneNumber) { this.fromPhoneNumber = fromPhoneNumber; }

    public boolean isEnabled() { return enabled; }
    public void setEnabled(boolean enabled) { this.enabled = enabled; }

    public boolean isConfigured() {
        return accountSid != null && !accountSid.isEmpty() &&
                authToken != null && !authToken.isEmpty() &&
                fromPhoneNumber != null && !fromPhoneNumber.isEmpty();
    }
}
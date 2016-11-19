package org.nearbyshops;

import javax.ws.rs.core.SecurityContext;
import java.security.Principal;

/**
 * Created by sumeet on 18/11/16.
 */
public class CustomSecurityContext implements SecurityContext {

    private Object accountInformation;

    @Override
    public Principal getUserPrincipal() {
        return null;
    }

    @Override
    public boolean isUserInRole(String s) {
        return false;
    }

    @Override
    public boolean isSecure() {
        return false;
    }

    @Override
    public String getAuthenticationScheme() {
        return null;
    }


    public Object getAccountInformation() {
        return accountInformation;
    }

    public void setAccountInformation(Object accountInformation) {
        this.accountInformation = accountInformation;
    }
}

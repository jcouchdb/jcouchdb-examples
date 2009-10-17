package org.hood.domain;

/**
 * Encapsulates contant information
 * 
 * @author shelmberger
 *
 */
public class ContactInfo
{
    private String email;

    private String offlineAddress;

    private String onlineAddress;

    public String getEmail()
    {
        return email;
    }

    public String getOfflineAddress()
    {
        return offlineAddress;
    }

    public String getOnlineAddress()
    {
        return onlineAddress;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public void setOfflineAddress(String offlineAddress)
    {
        this.offlineAddress = offlineAddress;
    }

    public void setOnlineAddress(String onlineAddress)
    {
        this.onlineAddress = onlineAddress;
    }

}

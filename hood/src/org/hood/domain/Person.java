package org.hood.domain;

/**
 * Extends {@link PositionedDocument} with first name and contact info.
 * 
 * @author shelmberger
 *
 */
public class Person extends PositionedDocument
{
    private String firstName;
    
    private ContactInfo contactInfo;

    public String getFirstName()
    {
        return firstName;
    }

    public ContactInfo getContactInfo()
    {
        return contactInfo;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public void setContactInfo(ContactInfo contactInfo)
    {
        this.contactInfo = contactInfo;
    }
}

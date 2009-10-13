package org.hood.domain;

public class Person extends PositionedDocument
{
    private String name, firstName, description;
    
    private ContactInfo contactInfo;

    @Override
    public String getName()
    {
        return name;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public ContactInfo getContactInfo()
    {
        return contactInfo;
    }

    @Override
    public void setName(String name)
    {
        this.name = name;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public void setContactInfo(ContactInfo contactInfo)
    {
        this.contactInfo = contactInfo;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    
}

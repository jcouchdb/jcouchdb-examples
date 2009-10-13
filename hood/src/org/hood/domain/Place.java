package org.hood.domain;


public class Place
    extends PositionedDocument
{
    private String description;

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }
    
}

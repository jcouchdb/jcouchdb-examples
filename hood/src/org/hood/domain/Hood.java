package org.hood.domain;

public class Hood extends PositionedDocument
{
    private String userId, name, description;
    
    private boolean defaultHood;

    public String getUserId()
    {
        return userId;
    }

    @Override
    public String getName()
    {
        return name;
    }

    public String getDescription()
    {
        return description;
    }

    public void setUserId(String userId)
    {
        this.userId = userId;
    }

    @Override
    public void setName(String name)
    {
        this.name = name;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public boolean isDefaultHood()
    {
        return defaultHood;
    }

    public void setDefaultHood(boolean defaultHood)
    {
        this.defaultHood = defaultHood;
    }
}

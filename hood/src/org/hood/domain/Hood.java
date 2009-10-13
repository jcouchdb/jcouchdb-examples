package org.hood.domain;

public class Hood extends PositionedDocument
{
    private String userId, name, description, defaultHood;

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

    public String getDefaultHood()
    {
        return defaultHood;
    }

    public void setDefaultHood(String defaultHood)
    {
        this.defaultHood = defaultHood;
    }
}

package org.hood.domain;

/**
 * Extends {@link PositionedDocument} to represents a hood -- a user configurable central point of interest.
 *  
 * @author shelmberger
 *
 */
public class Hood extends PositionedDocument
{
    private String userId, name;
    
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

    public void setUserId(String userId)
    {
        this.userId = userId;
    }

    @Override
    public void setName(String name)
    {
        this.name = name;
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

package org.hood.domain;

import java.util.Date;
import java.util.List;

public class Rating
    extends AppDocument
{
    private String userId, targetId;

    private List<String> tags;

    private int rating;

    private Date created = new Date();

    public String getUserId()
    {
        return userId;
    }


    public List<String> getTags()
    {
        return tags;
    }


    public void setUserId(String userId)
    {
        this.userId = userId;
    }


    public void setTags(List<String> tags)
    {
        this.tags = tags;
    }


    public String getTargetId()
    {
        return targetId;
    }


    public void setTargetId(String targetId)
    {
        this.targetId = targetId;
    }


    public Date getCreated()
    {
        return created;
    }


    public void setCreated(Date created)
    {
        this.created = created;
    }


    public int getRating()
    {
        return rating;
    }


    public void setRating(int rating)
    {
        this.rating = rating;
    }


}

package org.hood.domain;

import java.util.List;

import org.svenson.JSONProperty;

/**
 * User domain object. 
 * 
 * @author shelmberger
 *
 */
public class User extends Person
{
    private String passwordHash, description;

    private List<String> roles;

    private boolean enabled = true;

    private boolean credentialsNonExpired = true;

    private boolean accountNonLocked = true;

    private boolean accountNonExpired = true;

    public String getPasswordHash()
    {
        return passwordHash;
    }

    @Override
    public String getDescription()
    {
        return description;
    }

    public void setPasswordHash(String passwordHash)
    {
        this.passwordHash = passwordHash;
    }

    @Override
    public void setDescription(String description)
    {
        this.description = description;
    }
    
    @JSONProperty(ignore = true)
    public String getSaltKey()
    {
        return (getName() + "XikSAZj80rz");
    }

    public List<String> getRoles()
    {
        return roles;
    }

    public boolean isEnabled()
    {
        return enabled;
    }

    public boolean isCredentialsNonExpired()
    {
        return credentialsNonExpired;
    }

    public boolean isAccountNonLocked()
    {
        return accountNonLocked;
    }

    public boolean isAccountNonExpired()
    {
        return accountNonExpired;
    }

    public void setRoles(List<String> roles)
    {
        this.roles = roles;
    }

    public void setEnabled(boolean enabled)
    {
        this.enabled = enabled;
    }

    public void setCredentialsNonExpired(boolean credentialsNonExpired)
    {
        this.credentialsNonExpired = credentialsNonExpired;
    }

    public void setAccountNonLocked(boolean accountNonLocked)
    {
        this.accountNonLocked = accountNonLocked;
    }

    public void setAccountNonExpired(boolean accountNonExpired)
    {
        this.accountNonExpired = accountNonExpired;
    }
}

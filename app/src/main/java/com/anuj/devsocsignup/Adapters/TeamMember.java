package com.anuj.devsocsignup.Adapters;

public class TeamMember
{
    String PositionName;
    String Name;
    int HomeImageId;

    public TeamMember(String Title, String Name, int homeImageId)
    {
        PositionName = Title;
        this.Name = Name;
        HomeImageId=homeImageId;
    }
}

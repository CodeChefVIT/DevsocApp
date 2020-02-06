package com.anuj.devsocsignup.Adapters;

public class TeamMember
{
    String PositionName;
    String Name;
    int HomeImageId;
    String url;

    public TeamMember(String Title, String Name, int homeImageId, String url)
    {
        PositionName = Title;
        this.Name = Name;
        HomeImageId=homeImageId;
        this.url = url;
    }

    public String getPositionName() {
        return PositionName;
    }

    public String getName() {
        return Name;
    }

    public int getHomeImageId() {
        return HomeImageId;
    }

    public String getUrl() {
        return url;
    }

    public TeamMember(String Title, String Name, int homeImageId)
    {
        PositionName = Title;
        this.Name = Name;
        HomeImageId=homeImageId;
    }
}

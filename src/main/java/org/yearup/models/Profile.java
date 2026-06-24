package org.yearup.models;

import jakarta.persistence.*;
// Represents a user's personal and shipping information.
// A profile record is automatically created when a user registers.
@Entity
@Table(name = "profiles")
public class Profile
{
    // userId is also the primary key
    @Id
    @Column(name = "user_id")
    private int userId;
    // Personal info all default to empty string so the profile
    // can be created at registration before the user fills anything in
    @Column(name = "first_name")
    private String firstName = "";

    @Column(name = "last_name")
    private String lastName = "";

    @Column(name = "phone")
    private String phone = "";

    @Column(name = "email")
    private String email = "";
    // Shipping address also used at checkout time to pre-fill the order
    @Column(name = "address")
    private String address = "";

    @Column(name = "city")
    private String city = "";

    @Column(name = "state")
    private String state = "";

    @Column(name = "zip")
    private String zip = "";
    // Default constructor required by JPA
    public Profile() {
    }
    // Convenience constructor for building a fully populated profile in one line
    public Profile(int userId, String firstName, String lastName, String phone, String email, String address, String city, String state, String zip)
    {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zip = zip;
    }

    public int getUserId()
    {
        return userId;
    }

    public void setUserId(int userId)
    {
        this.userId = userId;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getCity()
    {
        return city;
    }

    public void setCity(String city)
    {
        this.city = city;
    }

    public String getState()
    {
        return state;
    }

    public void setState(String state)
    {
        this.state = state;
    }

    public String getZip()
    {
        return zip;
    }

    public void setZip(String zip)
    {
        this.zip = zip;
    }
}

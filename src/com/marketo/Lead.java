package com.marketo;

import java.util.Date;

public class Lead {
    private String _id;
    private String email;
    private String firstName;
    private String lastName;
    private String address;
    private Date entryDate;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }

    /*
    @param lead
    Checks whether 'this' has a newer entry date than lead
     */
    public boolean isRecentThan(Lead lead){
        return this.getEntryDate().compareTo(lead.getEntryDate()) >= 0;
    }

    @Override
    public String toString() {
        return "Lead{" +
                "_id='" + _id + '\'' +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", address='" + address + '\'' +
                ", entryDate=" + entryDate +
                '}';
    }
}

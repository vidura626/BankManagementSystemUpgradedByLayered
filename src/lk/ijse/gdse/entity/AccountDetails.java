package lk.ijse.gdse.entity;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Time;

public class AccountDetails implements SuperEntity {
    private String accountId;
    private String nic;
    private String name;
    private String address;
    private String contact;
    private Date dateOfBirth;
    private String email;
    private String gender;
    private Date regDate;
    private Time regTime;
    private String state;

    public AccountDetails() {
    }

    public AccountDetails(ResultSet rst) {
    }

    public AccountDetails(String accountId, String nic, String name, String address, String contact, Date dateOfBirth, String email, String gender, Date regDate, Time regTime, String state) {
        this.accountId = accountId;
        this.nic = nic;
        this.name = name;
        this.address = address;
        this.contact = contact;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.gender = gender;
        this.regDate = regDate;
        this.regTime = regTime;
        this.state = state;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getRegDate() {
        return regDate;
    }

    public void setRegDate(Date regDate) {
        this.regDate = regDate;
    }

    public Time getRegTime() {
        return regTime;
    }

    public void setRegTime(Time regTime) {
        this.regTime = regTime;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

}

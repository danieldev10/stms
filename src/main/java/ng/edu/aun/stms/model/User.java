package ng.edu.aun.stms.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_id;

    private String firstname;

    private String lastname;

    private String username;

    private String email;

    private String phone_number;

    private String password;

    private String otp;

    private Long otpExpiryTime;

    private boolean isVerified;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns = { @JoinColumn(name = "user_id") }, inverseJoinColumns = {
            @JoinColumn(name = "role_id"), })
    Set<Role> roles = new HashSet<>();

    @ManyToMany(mappedBy = "students")
    private Set<TutorSession> tutorSessions = new HashSet<>();

    public User() {
    }

    public User(String firstname, String lastname, String username, String email, String phone_number, String password,
            String otp, Long otpExpiryTime, boolean isVerified, Set<Role> roles, Set<TutorSession> tutorSessions) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.email = email;
        this.phone_number = phone_number;
        this.password = password;
        this.otp = otp;
        this.otpExpiryTime = otpExpiryTime;
        this.isVerified = isVerified;
        this.roles = roles;
        this.tutorSessions = tutorSessions;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public Long getOtpExpiryTime() {
        return otpExpiryTime;
    }

    public void setOtpExpiryTime(Long otpExpiryTime) {
        this.otpExpiryTime = otpExpiryTime;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public void setVerified(boolean isVerified) {
        this.isVerified = isVerified;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Set<TutorSession> getTutorSessions() {
        return tutorSessions;
    }

    public void setTutorSessions(Set<TutorSession> tutorSessions) {
        this.tutorSessions = tutorSessions;
    }

    @Override
    public String toString() {
        return "User [user_id=" + user_id + ", firstname=" + firstname + ", lastname=" + lastname + ", username="
                + username + ", email=" + email + ", phone_number=" + phone_number + ", password=" + password + ", otp="
                + otp + ", otpExpiryTime=" + otpExpiryTime + ", isVerified=" + isVerified + "]";
    }
}

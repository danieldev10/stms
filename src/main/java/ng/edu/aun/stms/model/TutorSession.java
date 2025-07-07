package ng.edu.aun.stms.model;

import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class TutorSession {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    private String course_name;
    private String course_code;
    private String description;
    private String day_of_week;
    private LocalTime start_time;
    private LocalTime end_time;
    private String meeting_medium;
    private String location;
    @ManyToMany
    @JoinTable(name = "tutor_session_students", joinColumns = @JoinColumn(name = "tutor_session_id"), inverseJoinColumns = @JoinColumn(name = "student_id"))
    private Set<User> students = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "creator_id", nullable = false)
    private User creator;

    public TutorSession() {
    }

    public TutorSession(String course_name, String course_code, String description, LocalTime start_time,
            LocalTime end_time, String meeting_medium, String location, Set<User> students, User creator,
            String day_of_week) {
        this.course_name = course_name;
        this.course_code = course_code;
        this.description = description;
        this.start_time = start_time;
        this.end_time = end_time;
        this.meeting_medium = meeting_medium;
        this.location = location;
        this.students = students;
        this.creator = creator;
        this.day_of_week = day_of_week;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public String getCourse_code() {
        return course_code;
    }

    public void setCourse_code(String course_code) {
        this.course_code = course_code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalTime getStart_time() {
        return start_time;
    }

    public void setStart_time(LocalTime start_time) {
        this.start_time = start_time;
    }

    public LocalTime getEnd_time() {
        return end_time;
    }

    public void setEnd_time(LocalTime end_time) {
        this.end_time = end_time;
    }

    public String getMeeting_medium() {
        return meeting_medium;
    }

    public void setMeeting_medium(String meeting_medium) {
        this.meeting_medium = meeting_medium;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Set<User> getStudents() {
        return students;
    }

    public void setStudents(Set<User> students) {
        this.students = students;
    }

    public void addStudent(User student) {
        this.students.add(student);
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    @Override
    public String toString() {
        return "TutorSession [id=" + id + ", course_name=" + course_name + ", course_code=" + course_code
                + ", description=" + description + ", meeting_medium=" + meeting_medium + ", location=" + location
                + "]";
    }

    public String getDay_of_week() {
        return day_of_week;
    }

    public void setDay_of_week(String day_of_week) {
        this.day_of_week = day_of_week;
    }

}

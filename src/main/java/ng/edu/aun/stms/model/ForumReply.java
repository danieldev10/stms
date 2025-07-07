package ng.edu.aun.stms.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class ForumReply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createdAt = LocalDateTime.now();

    @ManyToOne
    private User author;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private ForumQuestion question;

    @ManyToOne
    @JoinColumn(name = "parent_reply_id")
    private ForumReply parentReply;

    @OneToMany(mappedBy = "parentReply", cascade = CascadeType.ALL)
    private List<ForumReply> childReplies = new ArrayList<>();

    public ForumReply() {
    }

    public ForumReply(String content, LocalDateTime createdAt, User author, ForumQuestion question,
            ForumReply parentReply, List<ForumReply> childReplies) {
        this.content = content;
        this.createdAt = createdAt;
        this.author = author;
        this.question = question;
        this.parentReply = parentReply;
        this.childReplies = childReplies;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public ForumQuestion getQuestion() {
        return question;
    }

    public void setQuestion(ForumQuestion question) {
        this.question = question;
    }

    public ForumReply getParentReply() {
        return parentReply;
    }

    public void setParentReply(ForumReply parentReply) {
        this.parentReply = parentReply;
    }

    public List<ForumReply> getChildReplies() {
        return childReplies;
    }

    public void setChildReplies(List<ForumReply> childReplies) {
        this.childReplies = childReplies;
    }
}

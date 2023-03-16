package com.cbo.core.model;


import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="memos")
public class Memo {
    @Id
    @SequenceGenerator(
            name = "memo_sequence",
            sequenceName = "memo_sequence",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.IDENTITY,
            generator = "memo_sequence")
    private Long refnom;

    private Date curdate;

    private Date sendate;

    private String toTo;

    private String fromFrom;

    private String carbonCopy;

    private String subject;

    private String body;


    public Memo() {

    }



    public Memo(Date curdate, Date sendate, String toTo, String fromFrom, String carbonCopy, String subject,
                    String body) {

        this.curdate = curdate;
        this.sendate = sendate;
        this.toTo = toTo;
        this.fromFrom = fromFrom;
        this.carbonCopy = carbonCopy;
        this.subject = subject;
        this.body = body;
    }


    public Long getRefnom() {
        return refnom;
    }

    public void setRefnom(Long refnom) {
        this.refnom = refnom;
    }

    public Date getCurdate() {
        return curdate;
    }

    public void setCurdate(Date curdate) {
        this.curdate = curdate;
    }

    public Date getSendate() {
        return sendate;
    }

    public void setSendate(Date sendate) {
        this.sendate = sendate;
    }

    public String getToTo() {
        return toTo;
    }

    public void setToTo(String toTo) {
        this.toTo = toTo;
    }

    public String getFromFrom() {
        return fromFrom;
    }

    public void setFromFrom(String fromFrom) {
        this.fromFrom = fromFrom;
    }

    public String getCarbonCopy() {
        return carbonCopy;
    }

    public void setCarbonCopy(String carbonCopy) {
        this.carbonCopy = carbonCopy;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}

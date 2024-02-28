package anny.oct.helpdesk.model;

import java.sql.Date;

public class Comment {

    private int id;
    private int user_id;
    private String text;
    private java.sql.Date date;
    private int ticket_id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getTicket_id() {
        return ticket_id;
    }

    public void setTicket_id(int ticket_id) {
        this.ticket_id = ticket_id;
    }

    public Comment () {};

    public Comment(int id, int user_id, String text, Date date, int ticket_id) {
        this.id = id;
        this.user_id = user_id;
        this.text = text;
        this.date = date;
        this.ticket_id = ticket_id;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", user_id=" + user_id +
                ", text='" + text + '\'' +
                ", date=" + date +
                ", ticket_id=" + ticket_id +
                '}';
    }
}

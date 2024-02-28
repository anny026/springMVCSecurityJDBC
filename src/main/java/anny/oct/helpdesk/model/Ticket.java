package anny.oct.helpdesk.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class Ticket {
    private int id;
    @NotEmpty(message = "Name shouldn't be empty")
    @Size(min = 2, max= 100, message = "Name should be 2-30 characters")
    private String name_ticket;
    private String description;
    private String created_on;
    private  java.sql.Date desired_resolution_date;
    private int assignee_id;
    private int owner_id;
    private int state_id;
    private int category_id;
    private int urgency_id;
    private int approver_id;

    public Ticket() {};

    public Ticket(int id, String name_ticket, String description, String created_on, java.sql.Date desired_resolution_date, int assignee_id, int owner_id, int state_id, int category_id, int urgency_id, int approver_id) {
        this.id = id;
        this.name_ticket = name_ticket;
        this.description = description;
        this.created_on = created_on;
        this.desired_resolution_date = desired_resolution_date;
        this.assignee_id = assignee_id;
        this.owner_id = owner_id;
        this.state_id = state_id;
        this.category_id = category_id;
        this.urgency_id = urgency_id;
        this.approver_id = approver_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName_ticket() {
        return name_ticket;
    }

    public void setName_ticket(String name_ticket) {
        this.name_ticket = name_ticket;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreated_on() {
        return created_on;
    }

    public void setCreated_on(String created_on) {
        this.created_on = created_on;
    }

    public java.sql.Date getDesired_resolution_date() {
        return desired_resolution_date;
    }

    public void setDesired_resolution_date(java.sql.Date desired_resolution_date) {
        this.desired_resolution_date = desired_resolution_date;
    }

    public int getAssignee_id() {
        return assignee_id;
    }

    public void setAssignee_id(int assignee_id) {
        this.assignee_id = assignee_id;
    }

    public int getOwner_id() {
        return owner_id;
    }

    public void setOwner_id(int owner_id) {
        this.owner_id = owner_id;
    }

    public int getState_id() {
        return state_id;
    }

    public void setState_id(int state_id) {
        this.state_id = state_id;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public int getUrgency_id() {
        return urgency_id;
    }

    public void setUrgency_id(int urgency_id) {
        this.urgency_id = urgency_id;
    }

    public int getApprover_id() {
        return approver_id;
    }

    public void setApprover_id(int approver_id) {
        this.approver_id = approver_id;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", name_ticket='" + name_ticket + '\'' +
                ", description='" + description + '\'' +
                ", created_on='" + created_on + '\'' +
                ", desired_resolution_date='" + desired_resolution_date + '\'' +
                ", assignee_id=" + assignee_id +
                ", owner_id=" + owner_id +
                ", state_id=" + state_id +
                ", category_id=" + category_id +
                ", urgency_id=" + urgency_id +
                ", approver_id=" + approver_id +
                '}';
    }
}

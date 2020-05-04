/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package messenger;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import messenger.Conversation;

/**
 * Model class for Messages table
 *
 * @author sscho
 */
@Entity
@Table(name = "Messages")
@NamedQueries({
    @NamedQuery(name = "Messages.findAll", query
            = "SELECT m "
            + "FROM Messages m"),
    @NamedQuery(name = "Messages.findByMessageId", query = "SELECT m FROM Messages m WHERE m.messageId = :messageId"),
    @NamedQuery(name = "Messages.findBySenderId", query = "SELECT m FROM Messages m WHERE m.senderId = :senderId"),
    @NamedQuery(name = "Messages.findByBody", query = "SELECT m FROM Messages m WHERE m.body = :body"),
    @NamedQuery(name = "Messages.findByTimestamp", query = "SELECT m FROM Messages m WHERE m.timeStamp = :timeStamp"),
    
    //@NamedQuery(name = "Messages.findByTimestamp", query = "SELECT m FROM Messages m WHERE m.timeStamp = :timeStamp"),
    
    @NamedQuery(name = "Messages.test", query = "SELECT m FROM Messages m WHERE m.id = :id"),
    @NamedQuery(name = "Messages.test", query = "SELECT m FROM Messages m WHERE m.id = :id"),
    @NamedQuery(name = "Messages.test", query = "SELECT m FROM Messages m WHERE m.id = :id"),
    
    //@NamedQuery(name = "Messages.getMessagesByConvoId", query = "SELECT m.body FROM Messages m WHERE m.conversation_id = :conversation_id"),
//    @NamedQuery(name = "Messages.getMessagesByConvoId", query 
//            = "SELECT c.id, m.messageId, m.id, m.senderId, m.body, m.timeStamp "
//            + "FROM Conversation c "
//            + "INNER JOIN Messages m ON c.id=m.id;"
//    ),
})

public class Messages implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)

    @Column(name = "message_id")
    private Long messageId;
    
    @Column(name = "sender_id")
    private BigInteger senderId;

    @Column(name = "body")
    private String body;

    @Column(name = "time_stamp")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeStamp;

    @JoinColumn(name = "conversation_id", referencedColumnName = "id")
    @ManyToOne
    private Conversation id;

    public Messages() {
    }

    public Messages(Long messageId) {
        this.messageId = messageId;
    }

    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    public BigInteger getSenderId() {
        return senderId;
    }

    public void setSenderId(BigInteger senderId) {
        this.senderId = senderId;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    public Conversation getId() {
        return id;
    }

    public void setId(Conversation id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (messageId != null ? messageId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Messages)) {
            return false;
        }
        Messages other = (Messages) object;
        if ((this.messageId == null && other.messageId != null) || (this.messageId != null && !this.messageId.equals(other.messageId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "messenger.Messages[ messageId=" + messageId + " ]";
    }

}

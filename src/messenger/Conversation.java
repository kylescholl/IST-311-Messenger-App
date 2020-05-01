/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package messenger;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author sscho
 */
@Entity
@Table(name = "Conversation")
@NamedQueries({
    @NamedQuery(name = "Conversation.findAll", query = "SELECT c FROM Conversation c"),
    @NamedQuery(name = "Conversation.findByConversationId", query = "SELECT c FROM Conversation c WHERE c.conversationId = :conversationId")})
public class Conversation implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "conversation_id")
    private Long conversationId;
    @JoinColumn(name = "first_user_id", referencedColumnName = "user_id")
    @ManyToOne
    private Users firstUserId;
    @JoinColumn(name = "second_user_id", referencedColumnName = "user_id")
    @ManyToOne
    private Users secondUserId;

    public Conversation() {
    }

    public Conversation(Long conversationId) {
        this.conversationId = conversationId;
    }

    public Long getConversationId() {
        return conversationId;
    }

    public void setConversationId(Long conversationId) {
        this.conversationId = conversationId;
    }

    public Users getFirstUserId() {
        return firstUserId;
    }

    public void setFirstUserId(Users firstUserId) {
        this.firstUserId = firstUserId;
    }

    public Users getSecondUserId() {
        return secondUserId;
    }

    public void setSecondUserId(Users secondUserId) {
        this.secondUserId = secondUserId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (conversationId != null ? conversationId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Conversation)) {
            return false;
        }
        Conversation other = (Conversation) object;
        if ((this.conversationId == null && other.conversationId != null) || (this.conversationId != null && !this.conversationId.equals(other.conversationId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "messenger.Conversation[ conversationId=" + conversationId + " ]";
    }
    
}

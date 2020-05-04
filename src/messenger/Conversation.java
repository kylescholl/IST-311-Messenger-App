package messenger;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Model class for Conversation table
 * @author sscho
 */
@Entity
@Table(name = "Conversation")
@NamedQueries({
    @NamedQuery(name = "Conversation.findAll", query = "SELECT c FROM Conversation c"),
    @NamedQuery(name = "Conversation.findByConversationId", query = "SELECT c FROM Conversation c WHERE c.id = :id")})
public class Conversation implements Serializable {

//    @Column(name = "first_user_id")
//    //private BigInteger firstUserId;
//    private BigInteger first_user_id;
//    @Column(name = "second_user_id")
//    //private BigInteger secondUserId;
//    private BigInteger second_user_id;
    @OneToMany(mappedBy = "id")
    
    private Collection<Messages> messagesCollection;

    private static final long serialVersionUID = 1L;
    
    @Id
    @Basic(optional = false)
    
    @Column(name = "id")
    private Long id;
    
    @JoinColumn(name = "first_user_id", referencedColumnName = "user_id")
    @ManyToOne
    private Users firstUserId;
    
    @JoinColumn(name = "second_user_id", referencedColumnName = "user_id")
    @ManyToOne
    private Users secondUserId;

    public Conversation() {
    }

    public Conversation(Long conversationId) {
        this.id = conversationId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Conversation)) {
            return false;
        }
        Conversation other = (Conversation) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "messenger.Conversation[ conversationId=" + id + " ]";
    }

//    public BigInteger getFirstUserId() {
//        return firstUserId;
//    }
//
//    public void setFirstUserId(BigInteger firstUserId) {
//        this.firstUserId = firstUserId;
//    }
//
//    public BigInteger getSecondUserId() {
//        return secondUserId;
//    }
//
//    public void setSecondUserId(BigInteger secondUserId) {
//        this.secondUserId = secondUserId;
//    }
    
    public Collection<Messages> getMessagesCollection() {
        return messagesCollection;
    }

    public void setMessagesCollection(Collection<Messages> messagesCollection) {
        this.messagesCollection = messagesCollection;
    }
    
}

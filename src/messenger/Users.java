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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * 
 * @author sscho
 */
@Entity
@Table(name = "Users")
@NamedQueries({
    @NamedQuery(name = "Users.findAll", query = "SELECT s FROM Users s"),
    @NamedQuery(name = "Users.findById", query = "SELECT s FROM Users s WHERE s.user_id = :id"),
    @NamedQuery(name = "Users.findByEmail", query = "SELECT s FROM Users s WHERE s.email = :email"),
    @NamedQuery(name = "Users.findByPassword", query = "SELECT s FROM Users s WHERE s.password = :password")
})
//@Table(name = "Users")
//@NamedQueries({
//    @NamedQuery(name = "Users.findAll", query = "SELECT s FROM Users s"),
//    @NamedQuery(name = "Users.findById", query = "SELECT s FROM Users s WHERE s.user_id = :id"),
//    @NamedQuery(name = "Users.findByEmail", query = "SELECT s FROM Users s WHERE s.email = :email"),
//    @NamedQuery(name = "Users.findByPassword", query = "SELECT s FROM Users s WHERE s.password = :password")
//})
public class Users implements Serializable {
    
    @Id
    @Basic(optional = false)
    @Column(name = "user_id") // This needs to be col name in table
    private Long user_id;    // This needs to be the same as SQL statement above
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;
    
    
//        @Basic(optional = false)
//    @Column(name = "UserID") // This needs to be col name in table
//    private Long user_id;    // This needs to be the same as SQL statement above
//    @Column(name = "Email")
//    private String email;
//    @Column(name = "Password")
//    private String password;
    
    private static final long serialVersionUID = 1L;
    
    public Users() {
    }
    
    public Users(Long user_id) {
        this.user_id = user_id;
    }
    
    public Long getId() {
        return user_id;
    }
    
    public void setId(Long user_id) {
        this.user_id = user_id;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (user_id != null ? user_id.hashCode() : 0);
        return hash;
    }
    
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the user_id fields are not set
        if (!(object instanceof Users)) {
            return false;
        }
        Users other = (Users) object;
        if ((this.user_id == null && other.user_id != null) || (this.user_id != null && !this.user_id.equals(other.user_id))) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString() {
        //return "messenger.Users[ id=" + user_id + " ]";
        return "messenger.Users[ id=" + user_id + " ]";
    }
    
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
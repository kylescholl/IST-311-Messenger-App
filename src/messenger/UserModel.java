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


@Entity
@Table(name = "Users") // --> should our table be called UsersModel?
@NamedQueries({
    @NamedQuery(name = "UserModel.findAll", query = "SELECT s FROM Users s"),
    @NamedQuery(name = "UserModel.findById", query = "SELECT s FROM Users s WHERE s.id = :id"),
    @NamedQuery(name = "UserModel.findByValue", query = "SELECT s FROM Users s WHERE s.value = :value")})
public class UserModel implements Serializable {

    @Id
    @Basic(optional = false)
    @Column(name = "UserID")
    private Long user_id;
    @Column(name = "Email")
    private String email;
    @Column(name = "Password")
    private String password;
    
    private static final long serialVersionUID = 1L;
    
    public UserModel() {
    }
    
    public UserModel(Long user_id) {
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
        if (!(object instanceof UserModel)) {
            return false;
        }
        UserModel other = (UserModel) object;
        if ((this.user_id == null && other.user_id != null) || (this.user_id != null && !this.user_id.equals(other.user_id))) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString() {
        
        return "messenger.UserModel[ id=" + user_id + " ]";
                        //might need to change this (id)
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
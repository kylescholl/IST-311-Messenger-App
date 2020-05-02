/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testing;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * Model class for Testing table
 * @author sscho
 */
@Entity
@Table(name = "Testing")
@NamedQueries({
    @NamedQuery(name = "Testing.findAll", query = "SELECT t FROM Testing t"),
    @NamedQuery(name = "Testing.findByTestId", query = "SELECT t FROM Testing t WHERE t.testId = :testId"),
    @NamedQuery(name = "Testing.findByFirstName", query = "SELECT t FROM Testing t WHERE t.firstName = :firstName"),
    @NamedQuery(name = "Testing.findByLastName", query = "SELECT t FROM Testing t WHERE t.lastName = :lastName")})
public class Testing implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "test_id")
    private Long testId;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;

    public Testing() {
    }
    
    public Testing(Long testId) {
        this.testId = testId;
    }

    public Long getTestId() {
        return testId;
    }

    public void setTestId(Long testId) {
        this.testId = testId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (testId != null ? testId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Testing)) {
            return false;
        }
        Testing other = (Testing) object;
        if ((this.testId == null && other.testId != null) || (this.testId != null && !this.testId.equals(other.testId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "testing.Testing[ testId=" + testId + " ]";
    }
    
}

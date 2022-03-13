package com.ems.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity(name = "USER_ROLES")
public class UserRoles {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long id;
	String roleName;
	/*
	 * @OneToMany(mappedBy = "role") List<User> user;
	 */
	@ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinTable(name="ROLE_PRIVILAGE_MAP",
        joinColumns=
            @JoinColumn(name="PRIVILAGES_ID", referencedColumnName="ID"),
        inverseJoinColumns=
            @JoinColumn(name="ROLE_ID", referencedColumnName="ID")
        )

	List<Privilages> privilages;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public List<Privilages> getPrivilages() {
		return privilages;
	}
	public void setPrivilages(List<Privilages> privilages) {
		this.privilages = privilages;
	}
	

}

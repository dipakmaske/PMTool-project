package com.app.pojos;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Email(message = "Username needs to be an email")
    @NotBlank(message = "username is required")
    @Column(unique = true,length = 40)
    private String username;
    @NotBlank(message = "Please enter your full name")
    @Column(length = 40)
    private String fullName;
    @Column(length = 40)
    @NotBlank(message = "please enter organization name")
    private String organizationName;
    @NotBlank(message = "Password field is required")
    private String password;
    @Transient
    private String confirmPassword;
    private LocalDate create_At;
    private LocalDate update_At;
  
    //add a role
  	@Enumerated(EnumType.STRING)
  	@Column(length = 20)
  	private Role role;
    

    @ManyToMany(mappedBy = "members")
    @JsonIgnore
    private Set<Project>projects=new HashSet<>();

    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    

    public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}



	public Set<Project> getProjects() {
		return projects;
	}

	
	public void setProjects(Set<Project> projects) {
		this.projects = projects;
	}

	public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }



    public LocalDate getCreate_At() {
		return create_At;
	}

	public void setCreate_At(LocalDate create_At) {
		this.create_At = create_At;
	}

	public LocalDate getUpdate_At() {
		return update_At;
	}

	public void setUpdate_At(LocalDate update_At) {
		this.update_At = update_At;
	}

	@PrePersist
    protected void onCreate(){
        this.create_At = LocalDate.now();
    }

    @PreUpdate
    protected void onUpdate(){
        this.update_At = LocalDate.now();
    }




@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", fullName=" + fullName + ", create_At=" + create_At
				+ ", update_At=" + update_At + ", role=" + role + "]";
	}

@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

@Override
@JsonIgnore
public Collection<? extends GrantedAuthority> getAuthorities() {
    return null;
}

@Override
@JsonIgnore
public boolean isAccountNonExpired() {
    return true;
}

@Override
@JsonIgnore
public boolean isAccountNonLocked() {
    return true;
}

@Override
@JsonIgnore
public boolean isCredentialsNonExpired() {
    return true;
}

@Override
@JsonIgnore
public boolean isEnabled() {
    return true;
}
}
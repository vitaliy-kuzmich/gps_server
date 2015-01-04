package vitaliy.kuzmich.model;


import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Entity
@NamedQueries({
        @NamedQuery(
                name = Role.FIND_ROLE_BY_NAME,
                query = "select r from Role r where r.authority = :authority "
        )
})
public class Role implements GrantedAuthority {
    public static final String FIND_ROLE_BY_NAME = "findRoleByName";
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique = true)
    private String authority;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return authority;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Role)) {
            return false;
        }

        final Role role = (Role) o;

        return !(authority != null ? !authority.equals(role.authority) : role.authority != null);

    }

    /**
     * {@inheritDoc}
     */
    public int hashCode() {
        return (authority != null ? authority.hashCode() : 0);
    }
}

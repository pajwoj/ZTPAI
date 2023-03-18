package ztpai.models.Role;

import jakarta.persistence.*;

@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long IDrole;

    @Enumerated(EnumType.STRING)
    private RoleEnum name;

    public Role(Long IDrole, RoleEnum name) {
        this.IDrole = IDrole;
        this.name = name;
    }

    public Role() {}

    public Long getIDrole() {
        return IDrole;
    }

    public void setIDrole(Long IDrole) {
        this.IDrole = IDrole;
    }

    public String getName() {
        return name.name();
    }

    public void setName(RoleEnum name) {
        this.name = name;
    }
}

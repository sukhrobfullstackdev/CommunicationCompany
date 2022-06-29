package uz.sudev.communicationcompany.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Filial {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @OneToOne
    private Address address;
    @Column(nullable = false)
    private String filialName;
    @ManyToOne(optional = false)
    private User filialManager;
    @ManyToOne(optional = false)
    private User filialLeader;
    @OnDelete(action = OnDeleteAction.CASCADE)
    @OneToMany(mappedBy = "filial",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<User> employees;

    public Filial(Address address, String filialName, User filialManager, User filialLeader) {
        this.address = address;
        this.filialName = filialName;
        this.filialManager = filialManager;
        this.filialLeader = filialLeader;
    }
}

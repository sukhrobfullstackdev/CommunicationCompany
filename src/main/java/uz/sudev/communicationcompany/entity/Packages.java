package uz.sudev.communicationcompany.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Packages {
    @Id
    @GeneratedValue
    private UUID id;
    @Column(nullable = false)
    private String name;
    @ManyToOne(optional = false)
    private PackagesType packagesType;
    @Column(nullable = false)
    private Double price;
    @Column(nullable = false)
    private Integer expireDaysNumber;
    @OnDelete(action = OnDeleteAction.CASCADE)
    @OneToMany(mappedBy = "packages", fetch = FetchType.LAZY)
    private List<SimCard> simCards;
    @Column(nullable = false)
    private Double amount;
    private boolean transfersAfterPayment;

    public Packages(String name, PackagesType packagesType, Double price, Integer expireDaysNumber, Double amount, boolean transfersAfterPayment) {
        this.name = name;
        this.packagesType = packagesType;
        this.price = price;
        this.expireDaysNumber = expireDaysNumber;
        this.amount = amount;
        this.transfersAfterPayment = transfersAfterPayment;
    }
}

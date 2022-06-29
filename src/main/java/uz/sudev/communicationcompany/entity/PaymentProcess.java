package uz.sudev.communicationcompany.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class PaymentProcess {
    @Id
    @GeneratedValue
    private UUID id;
    @ManyToOne
    private SimCard simCard;
    @Column(nullable = false)
    private Integer price;
    @ManyToOne
    private Tariff tariff;
    @ManyToOne
    private Packages packages;
    @ManyToOne
    private EntertainmentServices entertainmentService;
}

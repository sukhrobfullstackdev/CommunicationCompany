package uz.sudev.communicationcompany.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Dashboard {
    @Id
    @GeneratedValue
    private UUID id;
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Date createdAt;
    @Column(nullable = false)
    private Integer incomePrice;
    @ManyToOne
    private Tariff tariff;
    @ManyToOne
    private SimCard simCard;
}

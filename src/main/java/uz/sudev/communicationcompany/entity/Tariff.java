package uz.sudev.communicationcompany.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Tariff {
    @Id
    @GeneratedValue
    private UUID id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private Double price;
    @Column(nullable = false)
    private Double feePrice;
    @Column(nullable = false)
    private Integer validityPeriodDays = 30;
    @ManyToOne(optional = false)
    private TariffType tariffType;
    private boolean active = true;
    @OnDelete(action = OnDeleteAction.CASCADE)
    @OneToMany(mappedBy = "tariff", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<SimCard> simCardList;
    private Double megabytes = 0.0;
    private Double minutesWithinTheNetwork = 0.0;
    private Double minutesOffTheNetwork = 0.0;
    private Double sms = 0.0;
    private Double priceOfPerMegabyte = 3.0;
    private Double priceOfPerMinuteWithinTheNetwork = 12.0;
    private Double priceOfPerMinuteOffTheNetwork = 11.0;
    private Double priceOfPerSms = 5.0;
}

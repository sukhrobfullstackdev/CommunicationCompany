package uz.sudev.communicationcompany.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "sim_cards")
public class SimCard {
    @Id
    @GeneratedValue
    private UUID id;
    @Size(min = 7, max = 7)
    @Column(nullable = false, unique = true)
    private String phoneNumber;
    @Transient
    private String companyNumber = "95";
    @Transient
    private String uzNumber = "+998";
    @Column(nullable = false)
    private Double balance;
    @ManyToOne
    private User user;
    @Column(unique = true)
    private String passportNumber;
    private boolean active = true;
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToMany(fetch = FetchType.EAGER)
    private List<EntertainmentServices> entertainmentServices;
    @ManyToOne
    private Tariff tariff;
    @ManyToOne
    private Packages packages;
    private Double minutesFromTariff;
    private Double minutesFromEntertainmentServices;
    private Double minutesFromPackages;
    private Double megabytesFromTariff;
    private Double megabytesFromEntertainmentServices;
    private Double megabytesFromPackages;
    private Double smsFromTariff;
    private Double smsFromEntertainmentServices;
    private Double smsFromPackages;

    public SimCard(Double balance, Double minutesFromTariff, Double minutesFromEntertainmentServices, Double minutesFromPackages, Double megabytesFromTariff, Double megabytesFromEntertainmentServices, Double megabytesFromPackages, Double smsFromTariff, Double smsFromEntertainmentServices, Double smsFromPackages) {
        this.balance = balance;
        this.minutesFromTariff = minutesFromTariff;
        this.minutesFromEntertainmentServices = minutesFromEntertainmentServices;
        this.minutesFromPackages = minutesFromPackages;
        this.megabytesFromTariff = megabytesFromTariff;
        this.megabytesFromEntertainmentServices = megabytesFromEntertainmentServices;
        this.megabytesFromPackages = megabytesFromPackages;
        this.smsFromTariff = smsFromTariff;
        this.smsFromEntertainmentServices = smsFromEntertainmentServices;
        this.smsFromPackages = smsFromPackages;
    }

    public SimCard(List<EntertainmentServices> entertainmentServices, Tariff tariff, Packages packages) {
        this.entertainmentServices = entertainmentServices;
        this.tariff = tariff;
        this.packages = packages;
    }
}

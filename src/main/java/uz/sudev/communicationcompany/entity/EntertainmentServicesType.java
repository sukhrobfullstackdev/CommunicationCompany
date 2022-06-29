package uz.sudev.communicationcompany.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.sudev.communicationcompany.entity.enums.EntertainmentServicePeriodType;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class EntertainmentServicesType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Enumerated(EnumType.STRING)
    private EntertainmentServicePeriodType entertainmentServicePeriodType;
    @Column(nullable = false)
    private String type;


    public String getEntertainmentServicePeriodType() {
        return entertainmentServicePeriodType.name();
    }

    public void setEntertainmentServicePeriodType(String name) {
        this.entertainmentServicePeriodType = EntertainmentServicePeriodType.valueOf(name);
    }
}

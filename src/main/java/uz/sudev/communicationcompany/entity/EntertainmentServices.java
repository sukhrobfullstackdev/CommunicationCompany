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
public class EntertainmentServices {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    private EntertainmentServicesType entertainmentServicesType;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String description;
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToMany(mappedBy = "entertainmentServices", fetch = FetchType.LAZY)
    private List<SimCard> user;

    public EntertainmentServices(EntertainmentServicesType entertainmentServicesType, String name, String description) {
        this.entertainmentServicesType = entertainmentServicesType;
        this.name = name;
        this.description = description;
    }
}

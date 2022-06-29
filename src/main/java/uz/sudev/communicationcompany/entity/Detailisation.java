package uz.sudev.communicationcompany.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Detailisation {
    @Id
    @GeneratedValue
    private UUID id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String description;
    @ManyToOne(optional = false)
    private SimCard simCardFrom;
    @ManyToOne
    private SimCard simCardTo;
    @CreationTimestamp
    @Column(nullable = false)
    private Timestamp createdAt;

}

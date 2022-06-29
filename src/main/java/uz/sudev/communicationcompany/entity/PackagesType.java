package uz.sudev.communicationcompany.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.sudev.communicationcompany.entity.enums.PackagesTypeName;
import uz.sudev.communicationcompany.entity.enums.RoleName;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class PackagesType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Enumerated(EnumType.STRING)
    private PackagesTypeName packagesTypeName;

    public String getPackagesTypeName() {
        return packagesTypeName.name();
    }
}

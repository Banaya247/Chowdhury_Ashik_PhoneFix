package org.example.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedHashSet;
import java.util.Set;

@AllArgsConstructor
@Entity
@Table(name = "offices")
@Slf4j
@NoArgsConstructor
@Setter
@Getter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Office {

    @Id
    @NonNull
    int id;

    @NonNull
    String name;


    @JsonBackReference
    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "offices", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    private Set<Technician> technicians = new LinkedHashSet<>();


    public void addTechnician(Technician technician) {
        technicians.add(technician);
        technician.getOffices().add(this);
    }

}
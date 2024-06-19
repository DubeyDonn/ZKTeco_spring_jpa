package com.zkt.zktspringjpa.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "shift")
public class TableShift {

    @Id
    private int id;

    private String name;
    private String shiftStart;
    private String shiftEnd;
    private String breakStart;
    private String breakEnd;
    private String type;

    @OneToMany(mappedBy = "shift", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<TableStaff> staffList;
}

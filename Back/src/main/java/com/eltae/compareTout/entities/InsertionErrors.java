package com.eltae.compareTout.entities;

import com.eltae.compareTout.constants.Tables;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = Tables.INSERTIONS_ERRORS)
@Builder
@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class InsertionErrors implements Cloneable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "insertionerrors_generator")
    @SequenceGenerator(name="insertionerrors_generator", sequenceName = "insertionerrors_generator_seq")
    private Long id;

    private Integer lineNumber;

    private LocalDate insertionDate;

    @Column(length = 1500)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    private Supplier supplier;


    public Supplier getSupplier(){
        return this.supplier;
    }

    public LocalDate getInsertionDate(){
        return this.insertionDate;
    }

    public InsertionErrors clone() throws CloneNotSupportedException {
        return (InsertionErrors) super.clone();
    }

}

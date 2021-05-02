package ru.devegang.servercontactsbook.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "contacts")
@Schema(description = "Сущность контакта")
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private long id;


    @Column(name = "name", nullable = false)
    @Schema(description = "Имя контакта. Может повторяться")
    private String name;

    @Column(name = "number", nullable = false)
    @Schema(description = "Телефонный номер контакта", example = "89101232323 или +79101232323 или 79101232323 или 9101232323")
    private String number;


    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}

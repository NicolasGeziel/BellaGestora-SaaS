package com.nicolas.bellagestora.procedimento.model;
import com.nicolas.bellagestora.usuario.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Procedimentos")
public class Procedimento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column
    private String nome;

    @Column
    private String descricao;

    @Column
    private String observacaoDono;

    @Column
    private BigDecimal valor;

    public Procedimento(String nome, String descricao, String observacaoDono, BigDecimal valor){
        this.nome = nome;
        this.descricao = descricao;
        this.observacaoDono = observacaoDono;
        this.valor = valor;
    }

}

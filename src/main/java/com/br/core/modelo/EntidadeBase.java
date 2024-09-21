package com.br.core.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.Positive;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldNameConstants;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
@FieldNameConstants
@MappedSuperclass
public abstract class EntidadeBase implements Comparable<EntidadeBase> {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence")
  @Column(name = "ID", nullable = false, unique = true)
  @Positive
  @ToString.Include
  @EqualsAndHashCode.Include
  private Long id;
  @Column(name = "ID_USUARIO_CADASTRO")
  @CreatedBy
  protected String usuarioCadastro;
  @Column(name = "DH_CADASTRO")
  @CreatedDate
  protected LocalDateTime dataHoraCadastro;
  @Column(name = "ID_USUARIO_ULTIMA_ATUALIZACAO")
  @LastModifiedBy
  protected String usuarioUltimaAtualizacao;
  @Column(name = "DH_ULTIMA_ATUALIZACAO")
  @LastModifiedDate
  protected LocalDateTime dataHoraUltimaAtualizacao;
  @Column(name = "ID_USUARIO_EXCLUSAO")
  protected String usuarioExclusao;
  @Column(name = "DH_EXCLUSAO")
  protected LocalDateTime dataHoraExclusao;


  @Override
  public int compareTo(EntidadeBase entidadeBase) {
    if (this.id != null && this.id.equals(entidadeBase.id)) {
      return 0;
    }
    if (this.id == null && entidadeBase.id != null) {
      return -1;
    }
    if (this.id != null && entidadeBase.id == null) {
      return 1;
    }
    return this.id.compareTo(entidadeBase.id);
  }
}

package com.br.core.modelo;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
public abstract class EntidadeBaseDTO<E extends EntidadeBase>
    implements Comparable<EntidadeBaseDTO<E>> {

  private static final long serialVersionUID = 1L;

  @NotNull
  @Positive
  @ToString.Include
  @EqualsAndHashCode.Include
  protected Long id;

  protected String usuarioCadastro;

  protected LocalDateTime dataHoraCadastro;

  protected String usuarioUltimaAtualizacao;

  protected LocalDateTime dataHoraUltimaAtualizacao;

  protected String usuarioExclusao;

  protected LocalDateTime dataHoraExclusao;

  @Override
  public int compareTo(EntidadeBaseDTO<E> dto) {
    if (this.id.equals(dto.id)) {
      return 0;
    }
    if (this.id == null && dto.id != null) {
      return -1;
    }
    if (this.id != null && dto.id == null) {
      return 1;
    }
    return this.id.compareTo(dto.id);
  }

}

package com.br.core.servicos;

import com.br.core.modelo.EntidadeBase;
import com.br.core.repositorios.BaseRepository;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.util.Assert;

public interface QueryService<E extends EntidadeBase, R extends BaseRepository<E>>
    extends BaseService<R> {


  default long total() {
    return this.getRepository().count();
  }

  default boolean existe(Long id) {
    Assert.notNull(id, "Id não pode ser nulo");
    Assert.notNull(id > 0, "Id dever ser maior que zero");

    return this.getRepository().existsById(id);
  }

  default Optional<E> buscarPorId(Long id) {
    Assert.notNull(id, "Id não pode ser nulo");
    Assert.notNull(id > 0, "Id deve ser maior que zero");

    return this.getRepository().findById(id);
  }

  default Page<E> listar() {
    return this.getRepository().findAll(Pageable.unpaged());
  }

  default Page<E> listar(Pageable pageable) {
    Assert.notNull(pageable, "Pageable não pode ser nulo");
    return this.getRepository().findAll(pageable);
  }
}

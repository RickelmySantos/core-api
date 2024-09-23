package com.br.core.controladores;

import com.br.core.modelo.EntidadeBase;
import com.br.core.modelo.EntidadeBaseDTO;
import com.br.core.modelo.EntidadeBaseMapper;
import com.br.core.repositorios.BaseRepository;
import com.br.core.servicos.CrudService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.util.Optional;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public interface CrudRestApi<E extends EntidadeBase, S extends CrudService<E, ? extends BaseRepository<? extends E>>, D extends EntidadeBaseDTO<? extends E>, M extends EntidadeBaseMapper<E, D>>
    extends QueryRestApi<E, S, D, M> {


  @Override
  @RequestMapping(method = RequestMethod.OPTIONS)
  default ResponseEntity<?> collectionOp() {
    return ResponseEntity.ok().allow(HttpMethod.GET, HttpMethod.POST, HttpMethod.OPTIONS).build();
  }

  @Override
  @RequestMapping(value = "/{id}", method = RequestMethod.OPTIONS)
  default ResponseEntity<?> singularOptions() {
    return ResponseEntity.ok()
        .allow(HttpMethod.GET, HttpMethod.PUT, HttpMethod.DELETE, HttpMethod.OPTIONS).build();
  }

  @PostMapping(consumes = {"application/json"}, produces = {"application/json"})
  default ResponseEntity<?> cadastrar(@Valid @RequestBody D dto, final HttpServletRequest request) {
    Optional<E> entidade = this.getService().cadastrar(this.getMapper().paraEntidade(dto));

    if (entidade.isPresent()) {
      return ResponseEntity.status(HttpStatus.CREATED)
          .body(this.getMapper().paraDTO(entidade.get()));
    }

    return ResponseEntity.noContent().build();
  }


  @PutMapping(value = "/{id}", consumes = {"application/json"}, produces = {"application/json"})
  default ResponseEntity<D> atualizar(@Positive @PathVariable @NotNull Long id,
      @Valid @RequestBody D dto, final HttpServletRequest request) {

    Optional<E> entidade = this.getService().atualizar(this.getMapper().paraEntidade(dto), false);

    if (entidade.isPresent()) {
      return ResponseEntity.ok(this.getMapper().paraDTO(entidade.get()));
    }
    return ResponseEntity.noContent().build();

  }

  @DeleteMapping(value = "/{id}")
  default ResponseEntity<Void> excluir(@Positive @PathVariable @NotNull Long id,
      final HttpServletRequest request) {
    if (!this.getService().existe(id)) {
      return ResponseEntity.notFound().build();
    }
    this.getService().excluir(id);
    return ResponseEntity.noContent().build();

  }

}

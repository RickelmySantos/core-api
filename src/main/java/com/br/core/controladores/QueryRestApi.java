package com.br.core.controladores;

import com.br.core.modelo.EntidadeBase;
import com.br.core.modelo.EntidadeBaseDTO;
import com.br.core.modelo.EntidadeBaseMapper;
import com.br.core.repositorios.BaseRepository;
import com.br.core.servicos.QueryService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public interface QueryRestApi<E extends EntidadeBase, S extends QueryService<E, ? extends BaseRepository<? extends E>>, D extends EntidadeBaseDTO<? extends E>, M extends EntidadeBaseMapper<E, D>>
    extends BaseRestApi<E, S, D, M> {

  @RequestMapping(method = RequestMethod.OPTIONS)
  default ResponseEntity<?> collectionOp() {
    return ResponseEntity.ok().allow(HttpMethod.GET, HttpMethod.OPTIONS).build();
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.OPTIONS)
  default ResponseEntity<?> singularOptions() {
    return ResponseEntity.ok().allow(HttpMethod.GET, HttpMethod.OPTIONS).build();
  }

  @GetMapping(path = "/{id}", produces = {"application/json"})
  @Operation(summary = "Buscar por ID", description = "Retorna uma entidade por ID")
  default ResponseEntity<?> buscarPorId(@PathVariable @Positive @NotNull Long id) {

    Optional<E> entidade = this.getService().buscarPorId(id);

    if (entidade.isEmpty()) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(this.onBuscarPorId(this.getMapper().paraDTO(entidade.get())));
  }

  default D onBuscarPorId(D entidadeDto) {
    return entidadeDto;
  }

  @GetMapping(produces = {"application/json"})
  default ResponseEntity<Page<D>> listar(Pageable pageable) {
    final Page<E> entidades = this.getService().listar(pageable);

    return ResponseEntity.ok(this.onListar(this.getMapper().paraPageDTO(entidades)));
  }


  default Page<D> onListar(Page<D> entidadesDTO) {
    return entidadesDTO;
  }

}

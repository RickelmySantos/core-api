package com.br.core.modelo;

import java.util.List;
import org.mapstruct.IterableMapping;
import org.mapstruct.Named;
import org.springframework.data.domain.Page;

public interface EntidadeBaseMapper<E extends EntidadeBase, D extends EntidadeBaseDTO<? extends E>> {

  public static final String PARA_DTO_EM_LISTA = "paraDTOemLista";

  E paraEntidade(D dto);

  D paraDTO(E entidade);

  @IterableMapping(qualifiedByName = EntidadeBaseMapper.PARA_DTO_EM_LISTA)
  List<D> paraDTOs(List<E> entidades);

  @Named(EntidadeBaseMapper.PARA_DTO_EM_LISTA)
  D paraDTOemLista(E entidade);

  default Page<D> paraPageDTO(Page<E> pageEntidades) {
    return pageEntidades.map(this::paraDTOemLista);
  }

}

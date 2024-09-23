package com.br.core.controladores;

import com.br.core.modelo.EntidadeBase;
import com.br.core.modelo.EntidadeBaseDTO;
import com.br.core.modelo.EntidadeBaseMapper;
import com.br.core.repositorios.BaseRepository;
import com.br.core.servicos.BaseService;

public interface BaseRestApi<E extends EntidadeBase, S extends BaseService<? extends BaseRepository<? extends E>>, D extends EntidadeBaseDTO<? extends E>, M extends EntidadeBaseMapper<E, D>> {

  S getService();

  M getMapper();

  String getRequestMapping();

}

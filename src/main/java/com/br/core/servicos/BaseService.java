package com.br.core.servicos;

import com.br.core.modelo.EntidadeBase;
import com.br.core.repositorios.BaseRepository;

public interface BaseService<R extends BaseRepository<? extends EntidadeBase>> {
  R getRepository();
}

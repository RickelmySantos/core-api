package com.br.core.servicos;

import com.br.core.modelo.EntidadeBase;
import com.br.core.repositorios.BaseRepository;
import com.br.core.util.Bean;
import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.BeanUtils;
import org.springframework.util.Assert;

public interface CrudService<E extends EntidadeBase, R extends BaseRepository<E>>
    extends QueryService<E, R> {

  default List<E> salvarLote(List<E> entidades) {

    List<E> entidadesSalvas = new LinkedList<E>();

    entidades.forEach(entidade -> {
      if (entidade.getId() != null) {
        entidadesSalvas.add(this.atualizar(entidade).get());
      } else {
        entidadesSalvas.add(this.cadastrar(entidade).get());
      }
    });
    return entidadesSalvas;
  }

  default Optional<E> cadastrar(E entidade) {
    Assert.notNull(entidade, "Entidade não pode ser nula");
    Assert.isNull(entidade.getId(), "Id da entidade não deve estar preenchido");

    this.validarCadastro(entidade);
    this.onCadastrar(entidade);

    Optional<E> resultado = Optional.of(this.getRepository().save(entidade));
    if (resultado.isPresent()) {
      this.onCadastroRealizado(resultado.get());
    }
    return resultado;
  }

  default Optional<E> arquivar(Long id) {
    Assert.notNull(id, "Id não pode ser nulo");
    Assert.isTrue(id > 0, "Id deve ser maior que zero");

    final Optional<E> resultado = this.buscarPorId(id);
    if (resultado.isEmpty()) {
      throw new EntityNotFoundException("Não foi possível encontrar a entidade com o id informado");
    }
    final E entidadeBase = resultado.get();
    this.validarArquivamento(entidadeBase);
    this.onArquivar(entidadeBase);

    entidadeBase.setDataHoraExclusao(LocalDateTime.now());

    final Optional<E> result = Optional.of(this.getRepository().save(entidadeBase));

    if (result.isPresent()) {
      this.onArquivamentoRealizado(result.get());
    }
    return result;
  }


  default Optional<E> atualizar(E entidade) {
    return this.atualizar(entidade, false);
  }

  default Optional<E> atualizar(E entidade, boolean parcial) {
    Assert.notNull(entidade, "Entidade não pode ser nula");
    Assert.notNull(entidade.getId(), "Id não pode ser nulo");
    Assert.notNull(entidade.getId() > 0, "Id deve ser maior que zero");

    Optional<E> resultado = this.buscarPorId(entidade.getId());

    if (resultado.isEmpty()) {
      throw new EntityNotFoundException("Não foi possível encontrar a entidade com o id informado");
    }

    E entidadeBase = resultado.get();
    entidade.setDataHoraCadastro(entidadeBase.getDataHoraCadastro());
    entidade.setUsuarioCadastro(entidadeBase.getUsuarioCadastro());

    entidade.setDataHoraUltimaAtualizacao(entidadeBase.getDataHoraUltimaAtualizacao());
    entidade.setUsuarioUltimaAtualizacao(entidadeBase.getUsuarioUltimaAtualizacao());

    entidade.setDataHoraExclusao(entidadeBase.getDataHoraExclusao());
    entidade.setUsuarioExclusao(entidadeBase.getUsuarioExclusao());

    this.validarAtualizacao(entidade, entidadeBase, parcial);
    this.onAtualizar(entidade, entidadeBase, parcial);

    Optional<E> result = Optional.of(this.getRepository().save(entidadeBase));

    if (result.isPresent()) {
      this.onAtualizacaoRealizada(result.get(), parcial);
    }
    return result;

  }

  default void excluir(Long id) {
    Assert.notNull(id, "Id não pode ser nulo");
    Assert.isTrue(id > 0, "Id deve ser maior que zero");

    Optional<E> resultado = this.buscarPorId(id);

    if (resultado.isEmpty()) {
      throw new EntityNotFoundException("Não foi possível encontrar a entidade com o id informado");
    }

    E entidadeBase = resultado.get();
    this.onExcluir(entidadeBase);

    entidadeBase.setDataHoraExclusao(LocalDateTime.now());

    this.getRepository().save(entidadeBase);

    this.onExclusaoRealizada(entidadeBase);
  }


  default void onCadastrar(E entidade) {

  }

  default void onCadastroRealizado(E entidade) {

  }

  default void validarCadastro(E entidade) {
    // Implementação padrão
  }

  default void validarAtualizacao(E entidade, E entidadeBase, boolean atualizacaoParcial) {

  }

  default void onAtualizar(E entidade, E entidadeBase, boolean atualizacaoParcial) {
    if (atualizacaoParcial) {
      BeanUtils.copyProperties(entidade, entidadeBase, Bean.getNullPropertyNames(entidade));
    } else {
      BeanUtils.copyProperties(entidade, entidadeBase);
    }

  }

  default void onAtualizacaoRealizada(E entidade, boolean atualizacaoParcial) {

  }

  default void onExcluir(E entidadeBase) {

  }

  default void onExclusaoRealizada(E entidade) {

  }

  default void validarArquivamento(E entidadeBase) {

  }

  default void onArquivar(E entidadeBase) {

  }

  default void onArquivamentoRealizado(E entidadeBase) {

  }


}

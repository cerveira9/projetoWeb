package br.com.fsma.projeto_web.modelo.dao;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import br.com.fsma.projeto_web.filtros.FiscalizacaoFiltro;
import br.com.fsma.projeto_web.modelo.negocio.Fiscalizacao;

@Named
@RequestScoped
public class FiscalizacaoDao implements Serializable{
	private static final long serialVersionUID = 1L;

	private DAO<Fiscalizacao> dao;
	
	@PostConstruct
	void init() {
		this.dao = new DAO<Fiscalizacao>(this.em, Fiscalizacao.class);
	}

	@Inject
	private EntityManager em;
	
	public boolean existe(Fiscalizacao fiscalizacao) {

		TypedQuery<Fiscalizacao> query = em.createQuery(" select f from Fiscalizacao f " + " where f.cnpj = :pCnpj", Fiscalizacao.class);

		query.setParameter("pCnpj", fiscalizacao.getCnpj());
		try {
			@SuppressWarnings("unused")
			Fiscalizacao resultado = query.getSingleResult();
			return true;
		} catch (NoResultException ex) {
			return false;
		}
	}

	public Fiscalizacao buscaEmpresaPorCnpj(String cnpj) {
		TypedQuery<Fiscalizacao> query = em.createQuery(" select f from Fiscalizacao f " + " where f.cnpj = :pCnpj", Fiscalizacao.class);

		query.setParameter("pCnpj", cnpj);
		try {
			@SuppressWarnings("unused")
			Fiscalizacao resultado = query.getSingleResult();
			return resultado;
		} catch (NoResultException ex) {
			return null;
		}
	}
	
	public void adiciona(Fiscalizacao fiscalizacao) {
		dao.adiciona(fiscalizacao);
	}

	public void atualiza(Fiscalizacao fiscalizacao) {
		em.merge(fiscalizacao);
	}

	public void remove(Fiscalizacao fiscalizacao) {
		dao.remove(fiscalizacao);
	}

	public Fiscalizacao buscaPorId(Long id) {
		return dao.buscaPorId(id);
	}
	
	public List<Fiscalizacao> buscaTodasFiscalizacoes() {
		String jpql = "select f from Fiscalizacao f order by f.razaoSocial";
		TypedQuery<Fiscalizacao> query = em.createQuery(jpql, Fiscalizacao.class);
		return query.getResultList();
	}

	public List<Fiscalizacao> listaTodosPaginada(int firstResult, int maxResults) {
		return dao.listaTodosPaginada(firstResult, maxResults);
	}
	
	public List<Fiscalizacao> busca(FiscalizacaoFiltro filtro) {
		String jpql = "select f from Fiscalizacao f where f.cidade.id = :idCidade";

		if (filtro.getCnpj() != null && filtro.getCnpj().length() > 0) {
			jpql = jpql + " and f.cnpj like :cnpj";
		}

		TypedQuery<Fiscalizacao> query = em.createQuery(jpql, Fiscalizacao.class);
		query.setParameter("idCidade", filtro.getCidadeId());

		if (filtro.getCnpj() != null && filtro.getCnpj().length() > 0) {
			query.setParameter("cnpj", filtro.getCnpj() + "%");
		}
		System.out.println(".............. " + jpql);
		return query.getResultList();
	}
}

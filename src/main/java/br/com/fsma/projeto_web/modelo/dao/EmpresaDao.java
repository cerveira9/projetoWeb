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

import br.com.fsma.projeto_web.filtros.EmpresaFiltro;
import br.com.fsma.projeto_web.modelo.negocio.Empresa;

@Named
@RequestScoped
public class EmpresaDao implements Serializable{

	private static final long serialVersionUID = 1L;

	private DAO<Empresa> dao;
	
	@PostConstruct
	void init() {
		this.dao = new DAO<Empresa>(this.em, Empresa.class);
	}

	@Inject
	private EntityManager em;
	
	public boolean existe(Empresa empresa) {

		TypedQuery<Empresa> query = em.createQuery(" select e from Empresa e " + " where e.cnpj = :pCnpj", Empresa.class);

		query.setParameter("pCnpj", empresa.getCnpj());
		try {
			@SuppressWarnings("unused")
			Empresa resultado = query.getSingleResult();
			return true;
		} catch (NoResultException ex) {
			return false;
		}
	}

	public Empresa buscaEmpresaPorCnpj(String cnpj) {
		TypedQuery<Empresa> query = em.createQuery(" select e from Empresa e " + " where e.cnpj = :pCnpj", Empresa.class);

		query.setParameter("pCnpj", cnpj);
		try {
			@SuppressWarnings("unused")
			Empresa resultado = query.getSingleResult();
			return resultado;
		} catch (NoResultException ex) {
			return null;
		}
	}
	
	public void adiciona(Empresa empresa) {
		dao.adiciona(empresa);
	}

	public void atualiza(Empresa empresa) {
		em.merge(empresa);
	}

	public void remove(Empresa empresa) {
		dao.remove(empresa);
	}

	public Empresa buscaPorId(Long id) {
		return dao.buscaPorId(id);
	}
	
	public List<Empresa> buscaTodasEmpresas() {
		String jpql = "select e from Empresa e order by e.razaoSocial";
		TypedQuery<Empresa> query = em.createQuery(jpql, Empresa.class);
		return query.getResultList();
	}

	public List<Empresa> listaTodosPaginada(int firstResult, int maxResults) {
		return dao.listaTodosPaginada(firstResult, maxResults);
	}
	
	public List<Empresa> busca(EmpresaFiltro filtro) {
//		if (filtro.getUfId() != null || filtro.getCnpj() != null) {
//			return null;
//		}
		
		String jpql = "select e from Empresa e";
		
		if (filtro.getUfId() != null) {
			jpql = jpql + " where e.uf.id = :idUf";
		}
		
		if (filtro.getCidadeId() != null) {
			jpql = jpql + " and e.cidade.id = :idCidade";
		}
		
		if (filtro.getBairroId() != null) {
			jpql = jpql + " and e.bairro.id = :idBairro";
		}

		if (filtro.getCnpj() != null && filtro.getCnpj().length() > 0 && filtro.getUfId() != null) {
			jpql = jpql + " and e.cnpj like :cnpj";
		}
		
		if (filtro.getCnpj() != null && filtro.getCnpj().length() > 0 && filtro.getUfId() == null) {
			jpql = jpql + " where e.cnpj like :cnpj";
		}

		TypedQuery<Empresa> query = em.createQuery(jpql, Empresa.class);
		
		if (filtro.getUfId() != null) {
			query.setParameter("idUf", filtro.getUfId());
		}
		
		if (filtro.getCidadeId() != null) {
			query.setParameter("idCidade", filtro.getCidadeId());
		}
		
		if (filtro.getBairroId() != null) {
			query.setParameter("idBairro", filtro.getBairroId());
		}

		if (filtro.getCnpj() != null && filtro.getCnpj().length() > 0) {
			query.setParameter("cnpj", filtro.getCnpj() + "%");
		}
		return query.getResultList();
	}

}

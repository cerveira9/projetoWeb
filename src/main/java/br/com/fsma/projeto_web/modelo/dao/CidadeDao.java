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

import br.com.fsma.projeto_web.modelo.negocio.Cidade;

@Named
@RequestScoped
public class CidadeDao implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private DAO<Cidade> dao;

	@PostConstruct
	void init() {
		this.dao = new DAO<Cidade>(this.em, Cidade.class);
	}
	
	@Inject
	private EntityManager em;
	
	public boolean existe(Cidade cidade) {
		
		TypedQuery<Cidade> query = em.createQuery(
				  " select c from Cidade c "
				+ " where c.nome = :pNome", Cidade.class);
		
		query.setParameter("pNome", cidade.getNome());
		try {
			@SuppressWarnings("unused")
			Cidade resultado = query.getSingleResult();
			return true;
		} catch (NoResultException ex) {
			return false;
		}
	}
	
	public void adiciona(Cidade cidade) {
		dao.adiciona(cidade);
	}

	public void atualiza(Cidade cidade){
		em.merge(cidade);
	}

	public void remove(Cidade cidade) {
		dao.remove(cidade);
	}

	public Cidade buscaPorId(Long id) {
		return dao.buscaPorId(id);
	}
	
	public List<Cidade> buscaTodasCidades(){
		String jpql = "select c from Cidade c order by c.nome";
		TypedQuery<Cidade> query = em.createQuery(jpql, Cidade.class);
		return query.getResultList();
	}
	
	public List<Cidade> listaTodosPaginada(int firstResult, int maxResults) {
		return dao.listaTodosPaginada(firstResult, maxResults);
	}
	
	public Cidade buscaPorSigla(String nome) {
		String jpql = " select c from Cidade c where u.nome = :pNome";
		TypedQuery<Cidade> query = em.createQuery(jpql, Cidade.class);
		query.setParameter("pNome", nome.trim().toLowerCase());
		try {
			return query.getSingleResult();
		} catch (NoResultException ex) {
			return null;
		}
	}
}

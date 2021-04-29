package br.com.alura.loja.dao;

import java.util.List;

import javax.persistence.EntityManager;

import br.com.alura.loja.modelo.Categoria;

public class CategoriaDao {

	private EntityManager em;

	public CategoriaDao(EntityManager em) {
		this.em = em;
	}

	public void cadastrar(Categoria categoria) {
		this.em.persist(categoria);
	}

	public void atualizar(Categoria categoria) {
		this.em.merge(categoria);
	}
	
	public void remover(Categoria categoria) {
		categoria = this.em.merge(categoria);
		this.em.remove(categoria);
	}
	
	public Categoria buscarPorId(Long id) {
		return this.em.find(Categoria.class, id);
	}
	
	public List<Categoria> buscarTodos(){
		
		StringBuilder jpql = new StringBuilder();
		jpql.append("SELECT c FROM Categoria c");
		
		return this.em.createQuery(jpql.toString(), Categoria.class).getResultList();
	}
	
}

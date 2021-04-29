package br.com.alura.loja.dao;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.com.alura.loja.modelo.Produto;

public class ProdutoDao {

	private EntityManager em;

	public ProdutoDao(EntityManager em) {
		this.em = em;
	}

	public void cadastrar(Produto produto) {
		this.em.persist(produto);
	}
	
	public void atualizar(Produto produto) {
		this.em.merge(produto);
	}
	
	public void remover(Produto produto) {
		produto = this.em.merge(produto);
		this.em.remove(produto);
	}
	
	public Produto buscarPorId(Long id) {
		return this.em.find(Produto.class, id);
	}
	
	public List<Produto> buscarTodos(){
		
		StringBuilder jpql = new StringBuilder();
		jpql.append("SELECT p FROM Produto p");
		
		return this.em.createQuery(jpql.toString(), Produto.class).getResultList();
	}

	public List<Produto> buscarPorNome(String nome){
		
		StringBuilder jpql = new StringBuilder();
		jpql.append("SELECT p FROM Produto p where p.nome = :nome");
		
		return this.em.createQuery(jpql.toString(), Produto.class)
					  .setParameter("nome", nome)
					  .getResultList();
	}
	
	public List<Produto> buscarPorNomeDaCategoria(String nome){
		
		return this.em.createNamedQuery("Produto.produtosPorCategoria", Produto.class)
					  .setParameter("nome", nome)
					  .getResultList();
	}
	
	public BigDecimal buscarPrecoDoProdutoPorNome(String nome){
		
		StringBuilder jpql = new StringBuilder();
		jpql.append("SELECT p.preco FROM Produto p where p.nome = :nome");
		
		return this.em.createQuery(jpql.toString(), BigDecimal.class)
					  .setParameter("nome", nome)
					  .getSingleResult();
	}
	
	public List<Produto> buscarPorParametro(String nome, BigDecimal preco, LocalDate dataCadastro) {
		StringBuilder jpql = new StringBuilder();
		jpql.append(" SELECT p ");
		jpql.append("   FROM Produto p ");
		jpql.append("  WHERE 1=1 ");
		
		if (nome != null && !nome.trim().isEmpty()) {
			jpql.append(" AND p.nome = :nome ");
		}
		if (preco != null) {
			jpql.append(" AND p.preco = :preco ");
		}
		if (dataCadastro!= null) {
			jpql.append(" AND p.dataCadastro = :dataCadastro ");
		}
		
		TypedQuery<Produto> createQuery = em.createQuery(jpql.toString(), Produto.class);
		
		if (nome != null && !nome.trim().isEmpty()) {
			createQuery.setParameter("nome", nome);
		}
		if (preco != null) {
			createQuery.setParameter("preco", preco);
		}
		if (dataCadastro!= null) {
			createQuery.setParameter("dataCadastro", dataCadastro);
		}
		
		return createQuery.getResultList();
	}
	
	public List<Produto> buscarPorParametroComCriteria(String nome, BigDecimal preco, LocalDate dataCadastro) {
	
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Produto> query = builder.createQuery(Produto.class);
		Root<Produto> from = query.from(Produto.class);
		
		Predicate filtros = builder.and();
		
		if (nome != null && !nome.trim().isEmpty()) {
			filtros = builder.and(filtros, builder.equal(from.get("nome"), nome));
		}
		if (preco != null) {
			filtros = builder.and(filtros, builder.equal(from.get("preco"), preco));
		}
		if (dataCadastro!= null) {
			filtros = builder.and(filtros, builder.equal(from.get("dataCadastro"), dataCadastro));
		}
		query.where(filtros);
		
		return em.createQuery(query).getResultList();
	}
	
	
	
	
	
	
	
	
	
	
}

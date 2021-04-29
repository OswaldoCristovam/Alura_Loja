package br.com.alura.loja.dao;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;

import br.com.alura.loja.modelo.Pedido;
import br.com.alura.loja.vo.RelatorioDeVendasVo;

public class PedidoDao {

	private EntityManager em;

	public PedidoDao(EntityManager em) {
		this.em = em;
	}

	public void cadastrar(Pedido pedido) {
		this.em.persist(pedido);
	}
	
	public BigDecimal valorTotalVendido() {
		StringBuilder jpql = new StringBuilder();
		jpql.append(" SELECT SUM(p.valorTotal) ");
		jpql.append("   FROM Pedido p ");
		
		return em.createQuery(jpql.toString(), BigDecimal.class)
				.getSingleResult();
	}
	
	public List<RelatorioDeVendasVo> relatorioDeVendas() {
		StringBuilder jpql = new StringBuilder();
		jpql.append(" SELECT new br.com.alura.loja.vo.RelatorioDeVendasVo( ");
		jpql.append("        produto.nome, ");
		jpql.append("        SUM(item.quantidade), ");
		jpql.append("        MAX(pedido.data)) ");
		jpql.append("   FROM Pedido pedido ");
		jpql.append("   JOIN pedido.itens item ");
		jpql.append("   JOIN item.produto produto ");
		jpql.append("  GROUP BY produto.nome ");
		jpql.append("  ORDER BY item.quantidade DESC ");
		return em.createQuery(jpql.toString(), RelatorioDeVendasVo.class)
				.getResultList();
	}
	
	public Pedido buscarPedidoComCliente(Long id) {
		StringBuilder jpql = new StringBuilder();
		jpql.append(" SELECT p ");
		jpql.append("   FROM Pedido p ");
		jpql.append("   JOIN FETCH p.cliente ");
		jpql.append("  WHERE p.id = :id ");
		return em.createQuery(jpql.toString(), Pedido.class)
				.setParameter("id", id)
				.getSingleResult();
	}
	
}

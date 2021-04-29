package br.com.alura.loja.vo;

import java.time.LocalDate;

public class RelatorioDeVendasVo {

	private String nomeProduto;
	private Long quantidadeProduto;
	private LocalDate ultimaVenda;

	public RelatorioDeVendasVo(String nomeProduto, Long quantidadeProduto, LocalDate ultimaVenda) {
		this.nomeProduto = nomeProduto;
		this.quantidadeProduto = quantidadeProduto;
		this.ultimaVenda = ultimaVenda;
	}

	@Override
	public String toString() {
		return "RelatorioDeVendasVo [nomeProduto=" + nomeProduto + ", quantidadeProduto=" + quantidadeProduto
				+ ", ultimaVenda=" + ultimaVenda + "]";
	}

	public String getNomeProduto() {
		return nomeProduto;
	}

	public Long getQuantidadeProduto() {
		return quantidadeProduto;
	}

	public LocalDate getUltimaVenda() {
		return ultimaVenda;
	}

}

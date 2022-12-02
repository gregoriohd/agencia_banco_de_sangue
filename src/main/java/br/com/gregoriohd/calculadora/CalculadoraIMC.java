package br.com.gregoriohd.calculadora;

public class CalculadoraIMC {
	
	private Integer peso;
	private Double altura;
	
	public CalculadoraIMC(Integer peso, Double altura) {
		this.peso = peso;
		this.altura = altura;
	}

	public Double getImc() {
		return getPeso() / Math.pow(altura, 2);
	}

	public Integer getPeso() {
		return peso;
	}

	public void setPeso(Integer peso) {
		this.peso = peso;
	}

	public Double getAltura() {
		return altura;
	}

	public void setAltura(Double altura) {
		this.altura = altura;
	}
	
	
	
	

}

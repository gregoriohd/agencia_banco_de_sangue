package br.com.gregoriohd.calculadora;

import java.time.LocalDate;
import java.time.Period;

public class CalcularIdade {
	
	private String[] dataNascimento;

	public CalcularIdade(String dataNascimento) {
		this.dataNascimento = dataNascimento.split("/");
	}

	public String[] getDataNascimento() {
		return dataNascimento;
	}
	
	
	public int idade() {
		
		LocalDate dataNascimento = LocalDate.of(Integer.parseInt(getDataNascimento()[2]), Integer.parseInt(getDataNascimento()[1]),
				Integer.parseInt(getDataNascimento()[0]));
		
		LocalDate hoje = LocalDate.now();

		Period p = Period.between(dataNascimento, hoje);

		return p.getYears();
		
	}

}

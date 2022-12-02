package br.com.gregoriohd.service;

import java.text.DecimalFormat;

import java.util.List;

import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.com.gregoriohd.calculadora.CalculadoraIMC;
import br.com.gregoriohd.calculadora.CalcularIdade;
import br.com.gregoriohd.model.Candidato;
import br.com.gregoriohd.repository.CandidatoRepository;
import br.com.gregoriohd.shared.CandidatoDTO;

@Service
public class CandidatoService {

	@Autowired
	private CandidatoRepository candidatoRepository;

	private int totalIdades = 0;
	public Candidato adicionaCandidato(Candidato candidato) {
		return candidatoRepository.save(candidato);
	}

	public List<CandidatoDTO> listaTodos() {
		List<Candidato> candidatos = candidatoRepository.findAll();

		return candidatos.stream().map(candidato -> new ModelMapper().map(candidato, CandidatoDTO.class))
				.collect(Collectors.toList());
	}

	public List<CandidatoDTO> listarPorEstado(String estado) {
		return listaTodos().stream().filter(candidato -> candidato.getEstado().equalsIgnoreCase(estado)).toList();
	}

	public List<CandidatoDTO> listarPorTipoSanguineo(String sangue) {
		return listaTodos().stream().filter(candidato -> candidato.getTipo_sanguineo().equalsIgnoreCase(sangue))
				.toList();
	}

	public String listaPercentual() {

		List<CandidatoDTO> listaMulheres = listaTodos().stream()
				.filter(candidato -> candidato.getSexo().equalsIgnoreCase("feminino")).toList();

		double totalF = listaMulheres.stream().count();

		double gordinhas = 0.0;

		for (CandidatoDTO c : listaMulheres) {
			CalculadoraIMC cimc = new CalculadoraIMC(c.getPeso(), c.getAltura());

			if (cimc.getImc() > 30) {
				gordinhas++;
			}

		}

		List<CandidatoDTO> listaHomens = listaTodos().stream()
				.filter(candidato -> candidato.getSexo().equalsIgnoreCase("masculino")).toList();

		double totalM = listaHomens.stream().count();

		double gordinhos = 0.0;

		for (CandidatoDTO c : listaHomens) {
			CalculadoraIMC cimc = new CalculadoraIMC(c.getPeso(), c.getAltura());

			if (cimc.getImc() > 30) {
				gordinhos++;
			}

		}

		double percentMulher = ((gordinhas * 100) / totalF);
		double percentHomem = ((gordinhos * 100) / totalM);

		DecimalFormat df = new DecimalFormat("##.00");

		String total = df.format(percentMulher) + "% de mulheres com obesidade\n" + df.format(percentHomem)
				+ "% de homens com obesidade";

		return total;

	}

	public String mediaDeIdadePorTipoSangue(String sangue) {

		List<CandidatoDTO> candidatos = listarPorTipoSanguineo(sangue);
		double media = 0.0;
		totalIdades = 0;
		
		candidatos.stream().forEach(candidato -> {

			CalcularIdade ci = new CalcularIdade(candidato.getData_nasc());

			if (candidato.getPeso() <= 50 || ci.idade() < 16 || ci.idade() > 69) {

			} else {
				totalIdades += ci.idade();
			}

		});

		media = totalIdades / candidatos.stream().count();

		return "Tipo sanguineo: "+ sangue.toUpperCase() + " tem media de idade de "+media + " anos";

	}

	public void importar() {
		String uri = "https://s3.amazonaws.com/gupy5/production/companies/52441/emails/1669646172212/e8330670-6f23-11ed-91a8-05f5cf6759fb/data_1.json";
		RestTemplate restTemplate = new RestTemplate();
		List<?> listaExterna = restTemplate.getForObject(uri, List.class);

		ModelMapper mapper = new ModelMapper();

		List<Candidato> candidatos = listaExterna.stream().map(candidato -> mapper.map(candidato, Candidato.class))
				.collect(Collectors.toList());

		for (Candidato candidato : candidatos) {
			adicionaCandidato(candidato);
		}
	}

}

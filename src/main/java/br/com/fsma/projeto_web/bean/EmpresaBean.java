package br.com.fsma.projeto_web.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.fsma.projeto_web.filtros.EmpresaFiltro;
import br.com.fsma.projeto_web.modelo.dao.BairroDao;
import br.com.fsma.projeto_web.modelo.dao.CidadeDao;
import br.com.fsma.projeto_web.modelo.dao.EmpresaDao;
import br.com.fsma.projeto_web.modelo.dao.UfDao;
import br.com.fsma.projeto_web.modelo.negocio.Bairro;
import br.com.fsma.projeto_web.modelo.negocio.Cidade;
import br.com.fsma.projeto_web.modelo.negocio.Empresa;
import br.com.fsma.projeto_web.modelo.negocio.Uf;
import br.com.fsma.projeto_web.tx.Transacional;
import br.com.fsma.projeto_web.validador.EmpresaValidador;

@Named
@ViewScoped
public class EmpresaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EmpresaDao empresaDao;
	@Inject
	private BairroDao bairroDao;
	@Inject
	private CidadeDao cidadeDao;
	@Inject
	private UfDao ufDao;
	@Inject
	private EmpresaValidador validador;
	private EmpresaFiltro filtro;

	private Empresa empresa;
	private List<Empresa> empresas = new ArrayList<Empresa>();
	private Bairro bairro;
	private List<Bairro> bairros = new ArrayList<Bairro>();
	private Cidade cidade;
	private List<Cidade> cidades = new ArrayList<Cidade>();
	private Uf uf;
	private List<Uf> ufs;
	private Long idUfSelecionada;
	private Long idCidadeSelecionada;
	private Long idBairroSelecionado;

	private enum Status {
		LISTANDO, ALTERANDO, INCLUINDO
	};

	private Status status;

	@PostConstruct
	public void init() {
		ufs = ufDao.buscaTodasUfs();
		filtro = new EmpresaFiltro();
		status = Status.LISTANDO;
	}

	public boolean isEditando() {
		return (status == Status.INCLUINDO) || (status == Status.ALTERANDO);
	}

	public boolean isAlterando() {
		return status == Status.ALTERANDO;
	}

	public boolean isListando() {
		return status == Status.LISTANDO;
	}

	public boolean isIncluindo() {
		return status == Status.INCLUINDO;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public List<Empresa> getEmpresas() {
		return empresas;
	}

	public Bairro getBairro() {
		return bairro;
	}

	public List<Bairro> getBairros() {
		return bairros;
	}

	public List<Cidade> getCidades() {
		return cidades;
	}

	public Cidade getCidade() {
		return cidade;
	}

	public Uf getUf() {
		return uf;
	}

	public List<Uf> getUfs() {
		return ufs;
	}

	public List<Uf> listaUfs() {
		ufs = ufDao.buscaTodasUfs();
		return ufs;
	}

	public List<Cidade> listaCidades() {
		cidades = cidadeDao.buscaCidadePorUf(filtro.getUfId());
		return cidades;
	}

	public List<Cidade> listaCidadesParaOCadastro() {
		cidades = cidadeDao.buscaCidadePorUf(idUfSelecionada);
		return cidades;
	}

	public List<Bairro> listaBairros() {
		bairros = bairroDao.buscaBairroPorCidade(filtro.getCidadeId());
		return bairros;
	}

	public List<Bairro> listaBairrosParaOCadastro() {
		bairros = bairroDao.buscaBairroPorCidade(idCidadeSelecionada);
		return bairros;
	}

	public Long getIdUfSelecionada() {
		return idUfSelecionada;
	}

	public void setIdUfSelecionada(Long idUfSelecionada) {
		this.idUfSelecionada = idUfSelecionada;
	}

	public Long getIdCidadeSelecionada() {
		return idCidadeSelecionada;
	}

	public void setIdCidadeSelecionada(Long idCidadeSelecionada) {
		this.idCidadeSelecionada = idCidadeSelecionada;
	}

	public Long getIdBairroSelecionado() {
		return idBairroSelecionado;
	}

	public void setIdBairroSelecionado(Long idBairroSelecionado) {
		this.idBairroSelecionado = idBairroSelecionado;
	}

	public EmpresaFiltro getFiltro() {
		return filtro;
	}

	public void solicitaIncluir() {
		empresa = new Empresa();
		status = Status.INCLUINDO;
	}

	@Transacional
	public void confirmaInclusao() {

		if (idUfSelecionada != null) {
			Uf uf = ufDao.buscaPorId(idUfSelecionada);
			this.empresa.setUf(uf);
		}
		if (idCidadeSelecionada != null) {
			Cidade cidade = cidadeDao.buscaPorId(idCidadeSelecionada);
			this.empresa.setCidade(cidade);
		}
		if (idBairroSelecionado != null) {
			Bairro bairro = bairroDao.buscaPorId(idBairroSelecionado);
			this.empresa.setBairro(bairro);
		}

		if (validador.naoPodeIncluir(empresa)) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", validador.getMensagem()));
			return;
		}

		empresaDao.adiciona(empresa);
		empresas = empresaDao.busca(filtro);
		status = Status.LISTANDO;
	}

	public void cancelarInclusao() {
		status = Status.LISTANDO;
		cidade = null;
	}

	public void solicitaAlterar(Long empresaId) {
		empresa = empresaDao.buscaPorId(empresaId);
		status = Status.ALTERANDO;
	}

	@Transacional
	public void confirmaAlteracao() {

		if (idUfSelecionada != null) {
			Uf uf = ufDao.buscaPorId(idUfSelecionada);
			this.empresa.setUf(uf);
		}
		if (idCidadeSelecionada != null) {
			Cidade cidade = cidadeDao.buscaPorId(idCidadeSelecionada);
			this.empresa.setCidade(cidade);
		}
		if (idBairroSelecionado != null) {
			Bairro bairro = bairroDao.buscaPorId(idBairroSelecionado);
			this.empresa.setBairro(bairro);
		}

		if (validador.naoPodeAlterar(empresa)) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", validador.getMensagem()));
			return;
		}

		empresaDao.atualiza(empresa);
		empresas = empresaDao.busca(filtro);
		status = Status.LISTANDO;
	}

	@Transacional
	public void confirmaExclusao(Long empresaId) {
		Empresa empresa = empresaDao.buscaPorId(empresaId);
		empresaDao.remove(empresa);
		empresa = null;
		empresas = empresaDao.busca(filtro);
		status = Status.LISTANDO;
	}

	public void cancelarAlteracao() {
		status = Status.LISTANDO;
		cidade = null;
	}

	public void cancelarBusca() {
		status = Status.LISTANDO;

	}

	public void buscaEmpresa() {
		empresas = empresaDao.busca(filtro);
		status = Status.LISTANDO;
	}

	public Long getUfIdFiltro() {
		return filtro.getUfId();
	}

	public void setUfIdFiltro(Long id) {
		filtro.setUfId(id);
	}

	public Long getCidadeIdFiltro() {
		return filtro.getCidadeId();
	}

	public void setCidadeIdFiltro(Long id) {
		filtro.setCidadeId(id);
	}

	public Long getBairroIdFiltro() {
		return filtro.getBairroId();
	}

	public void setBairroIdFiltro(Long id) {
		filtro.setBairroId(id);
	}

	public void atualizaUf() {
		filtro.setCidadeId(null);
		atualizaListaCidade();
		filtro.setBairroId(null);
		atualizaListaBairro();

	}

	public void atualizaCidade() {
		filtro.setBairroId(null);
		atualizaListaBairro();
	}

	private void atualizaListaCidade() {
		bairros = new ArrayList<>();
		if (filtro.getUfId() == null) {
			cidades = new ArrayList<>();
			return;
		}
		cidades = cidadeDao.buscaTodasCidades();
	}

	private void atualizaListaBairro() {
		if (filtro.getCidadeId() == null) {
			bairros = new ArrayList<>();
			return;
		}
		bairros = bairroDao.buscaTodosBairros();
	}
}

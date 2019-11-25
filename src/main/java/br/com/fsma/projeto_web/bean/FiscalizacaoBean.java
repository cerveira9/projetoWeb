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

import br.com.fsma.projeto_web.filtros.FiscalizacaoFiltro;
import br.com.fsma.projeto_web.modelo.dao.BairroDao;
import br.com.fsma.projeto_web.modelo.dao.CidadeDao;
import br.com.fsma.projeto_web.modelo.dao.EmpresaDao;
import br.com.fsma.projeto_web.modelo.dao.FiscalizacaoDao;
import br.com.fsma.projeto_web.modelo.dao.UfDao;
import br.com.fsma.projeto_web.modelo.negocio.Bairro;
import br.com.fsma.projeto_web.modelo.negocio.Cidade;
import br.com.fsma.projeto_web.modelo.negocio.Empresa;
import br.com.fsma.projeto_web.modelo.negocio.Fiscalizacao;
import br.com.fsma.projeto_web.modelo.negocio.Uf;
import br.com.fsma.projeto_web.tx.Transacional;
import br.com.fsma.projeto_web.validador.FiscalizacaoValidador;

@Named
@ViewScoped
public class FiscalizacaoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private FiscalizacaoDao fiscalizacaoDao;
	@Inject
	private EmpresaDao empresaDao;
	@Inject
	private BairroDao bairroDao;
	@Inject
	private CidadeDao cidadeDao;
	@Inject
	private UfDao ufDao;
	@Inject
	private FiscalizacaoValidador validador;
	private FiscalizacaoFiltro filtro;

	private Fiscalizacao fiscalizacao;
	private List<Fiscalizacao> fiscalizacoes = new ArrayList<Fiscalizacao>();
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
	private Long idEmpresaSelecionada;

	private enum Status {
		LISTANDO, ALTERANDO, INCLUINDO
	};

	private Status status;

	@PostConstruct
	public void init() {
		ufs = ufDao.buscaTodasUfs();
		filtro = new FiscalizacaoFiltro();
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

	public Fiscalizacao getFiscalizacao() {
		return fiscalizacao;
	}

	public void setFiscalizacao(Fiscalizacao fiscalizacao) {
		this.fiscalizacao = fiscalizacao;
	}

	public List<Fiscalizacao> getFiscalizacoes() {
		return fiscalizacoes;
	}

	public void setFiscalizacoes(List<Fiscalizacao> fiscalizacoes) {
		this.fiscalizacoes = fiscalizacoes;
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

	public List<Bairro> listaBairros() {
		bairros = bairroDao.buscaBairroPorCidade(filtro.getCidadeId());
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

	public Long getIdEmpresaSelecionada() {
		return idEmpresaSelecionada;
	}

	public void setIdEmpresaSelecionada(Long idEmpresaSelecionada) {
		this.idEmpresaSelecionada = idEmpresaSelecionada;
	}

	public FiscalizacaoFiltro getFiltro() {
		return filtro;
	}

	public void solicitaIncluir() {
		fiscalizacao = new Fiscalizacao();
		status = Status.INCLUINDO;
	}

	@Transacional
	public void confirmaInclusao() {

		Cidade cidade = cidadeDao.buscaPorId(idCidadeSelecionada);
		Uf uf = ufDao.buscaPorId(filtro.getUfId());
		bairro.setCidade(cidade);
		bairro.setUf(uf);
		Empresa empresa = empresaDao.buscaPorId(idEmpresaSelecionada);

		if (validador.naoPodeIncluir(fiscalizacao)) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", validador.getMensagem()));
			return;
		}

		fiscalizacaoDao.adiciona(fiscalizacao);
		fiscalizacoes = fiscalizacaoDao.busca(filtro);
		status = Status.LISTANDO;
	}

	public void cancelarInclusao() {
		status = Status.LISTANDO;
		cidade = null;
	}

	public void solicitaAlterar(Long fiscalizacaoId) {
		fiscalizacao = fiscalizacaoDao.buscaPorId(fiscalizacaoId);
		status = Status.ALTERANDO;
	}

	@Transacional
	public void confirmaAlteracao() {

		Cidade cidade = cidadeDao.buscaPorId(idCidadeSelecionada);
		Uf uf = ufDao.buscaPorId(filtro.getUfId());
		bairro.setCidade(cidade);
		bairro.setUf(uf);
		Empresa empresa = empresaDao.buscaPorId(idEmpresaSelecionada);

		if (validador.naoPodeAlterar(fiscalizacao)) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", validador.getMensagem()));
			return;
		}

		fiscalizacaoDao.atualiza(fiscalizacao);
		fiscalizacoes = fiscalizacaoDao.busca(filtro);
		status = Status.LISTANDO;
	}

	@Transacional
	public void confirmaExclusao(Long fiscalizacaoId) {
		fiscalizacaoDao.remove(fiscalizacao);
		fiscalizacao = null;
		fiscalizacoes = fiscalizacaoDao.busca(filtro);
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
		status = Status.LISTANDO;
	}

	public Long getUfIdFiltro() {
		return filtro.getUfId();
	}

	public void setUfIdFiltro(Long id) {
		filtro.setUfId(id);
	}
}

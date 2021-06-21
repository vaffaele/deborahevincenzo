package raf.sollecito.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Commento {
	
	
	@Override
	public String toString() {
		return "Commento {id=" + id + ", post=" + post + ", commento=" + commento + ", nomeUtente=" + nomeUtente + "}";
	}



	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	
	@ManyToOne
    @JoinColumn(name="post_id", nullable=false)
    private Post post;

	private String commento;

	private String nomeUtente;

	private Date dataCreazione;
		
	public Post getPost() {
		return post;
	}



	public void setPost(Post post) {
		this.post = post;
	}



	public String getNomeUtente() {
		return nomeUtente;
	}



	public void setNomeUtente(String nomeUtente) {
		this.nomeUtente = nomeUtente;
	}



	public Integer getId() {
		return id;
	}



	public void setId(Integer id) {
		this.id = id;
	}



	public String getCommento() {
		return commento;
	}



	public void setCommento(String commento) {
		this.commento = commento;
	}



	public Date getDataCreazione() {
		return dataCreazione;
	}



	public void setDataCreazione(Date dataCreazione) {
		this.dataCreazione = dataCreazione;
	}

	
}

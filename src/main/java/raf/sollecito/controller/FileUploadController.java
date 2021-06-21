package raf.sollecito.controller;



import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.websocket.server.PathParam;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.gson.Gson;

import raf.sollecito.model.Commento;
import raf.sollecito.model.Post;
import raf.sollecito.model.PostFrontend;
import raf.sollecito.service.CommentoRepository;
import raf.sollecito.service.PostRepository;
//import  raf.sollecito.service.StorageFileNotFoundException;
import  raf.sollecito.service.StorageService;

@Controller
@CrossOrigin
public class FileUploadController {

	private final StorageService storageService;
	private final PostRepository postRepository;
	private final CommentoRepository commentoRepository;

	@Autowired
	public FileUploadController(StorageService storageService,PostRepository postRepository,CommentoRepository commentoRepository) {
		this.storageService = storageService;
		this.postRepository = postRepository;
		this.commentoRepository = commentoRepository;
	}

	@GetMapping("/upload")
	public String listUploadedFiles(Model model) throws IOException {
		PageRequest firstPageWithTwoElements = PageRequest.of(0,9);
		Page<Post> list =  postRepository.findAll(firstPageWithTwoElements);
		
		List<PostFrontend> immagini= new ArrayList<>();
		//System.out.println("**********************************************"+list.getSize());
		for(Post post : list.getContent()) {
			//System.out.println(post);
			PostFrontend np = new PostFrontend();
			np.setId(post.getId());
			np.setFileContent(post.getContent());
			np.setDescription(post.getDescription());
			np.setNome(post.getNome());
			np.setCommenti(post.getCommenti());
			np.setEstensione(getEstensione(post.getFileName()));
			immagini.add(np);
		}
		
		model.addAttribute("files", immagini);
		return "uploadForm";
	}
	private String getEstensione(String fileName) {
		//System.out.println("************************inside**********************"+fileName);
		String estensione = null;
		if(fileName!=null) {
			String[] arr = fileName.split("\\.");
			//System.out.println("************************size**********************"+arr.length);
			//System.out.println("************************size**********************"+arr[0]);
			estensione = arr[arr.length-1];
		}
		return estensione;
	}
	
	@RequestMapping(value = "/other", method = RequestMethod.GET)
	//@ResponseBody
	public String listOtherUploadedFiles(@RequestParam(name = "page") String page ,Model model) throws IOException {
		PageRequest firstPageWithTwoElements = PageRequest.of(Integer.valueOf(page), 9);
		Page<Post> list =  postRepository.findAll(firstPageWithTwoElements);
		List<PostFrontend> immagini= new ArrayList<>();
		System.out.println("**********************************************"+list.getSize());
		for(Post post : list.getContent()) {
			System.out.println(post);
			PostFrontend np = new PostFrontend();
			np.setId(post.getId());
			np.setFileContent(post.getContent());
			np.setDescription(post.getDescription());
			np.setNome(post.getNome());
			np.setCommenti(post.getCommenti());
			immagini.add(np);
		}
		System.out.println("richiesta pag "+page);
		System.out.println("restitutiti risultati in numero "+immagini.size());
		model.addAttribute("files", immagini);
		return "immagine";
	}

	@GetMapping("/post")
	public String serveFile(@RequestParam(name = "id") String id,Model model) {
		System.out.println("**********************************************");
		System.out.println("*******************ID******************* "+id);
		Optional<Post> post =  postRepository.findById(Integer.valueOf(id));
		System.out.println("*******************errore******************* "+id);
		Post found =post.get();
		model.addAttribute("file", found);
		return "post";
	}
	
	public String serveFile(Model model,Post found) {
		model.addAttribute("file", found);
		return "post";
	}

	@PostMapping("/upload")
	public String handleFileUpload(@RequestParam("file") MultipartFile file,@RequestParam("nome") String nome,@RequestParam("descrizione") String descrizione,
			RedirectAttributes redirectAttributes) throws Exception {
		
		
		if(file!=null && nome!=null && descrizione!=null) {
			Post newPost = new Post();
			newPost.setDescription(descrizione);
			newPost.setNome(nome);
			newPost.setFilePath(null);
			newPost.setContent(Base64.encodeBase64String(file.getBytes()));
			newPost.setFileName(file.getOriginalFilename());
			Post saved = postRepository.save(newPost);
			if(saved!=null)
				return "redirect:/upload";
			else
				throw new Exception();
		}else {
			
			throw new Exception();
		
		}
//		redirectAttributes.addFlashAttribute("message",
//				"You successfully uploaded " + file.getOriginalFilename() + "!");

		
	}
	
	@CrossOrigin
	@PostMapping("/commento")
	public String addCommento(@RequestParam("commento") String commento,@RequestParam("postId") Integer postId,@RequestParam("nome") String nome,
			RedirectAttributes redirectAttributes) {
		Optional<Post> postCollegato = postRepository.findById(postId);
		Commento nc = new Commento();
		nc.setCommento(commento);
		nc.setNomeUtente(nome);
		nc.setPost(postCollegato.get());
		commentoRepository.save(nc);
		
		
		
//		redirectAttributes.addFlashAttribute("message",
//				"You successfully uploaded " + file.getOriginalFilename() + "!");

		return "redirect:/upload";
	}

	@ExceptionHandler(Exception.class)
	public String handleStorageFileNotFound(Exception exc) {
		System.out.println("ERRORE : "+exc.getMessage());
		exc.printStackTrace();
		return "error";
	}

}
/*
 * 	<!---div>
		<form method="POST" enctype="multipart/form-data" action="/upload">
			<table>
				<tr><td>File to upload:</td><td><input type="file" name="file" /></td></tr>
				<tr><td></td><td><input type="submit" value="Upload" /></td></tr>
			</table>
		</form>
	</div---!>
	
	*
	*
	*
	*<form method="POST" enctype="multipart/form-data" action="/upload">
			
				<h3>Carica la tua Foto:</h3><input  class="btn-success" type="file" name="file" />
				<input type="submit" value="Carica" />
	
		</form>
		
		
		
		
						<div class="col-lg-12 spazio"></div>
								<div class="col-lg-3 col-md-3 col-sm-3 spazio"></div><div class=" card col-lg-6 col-md-6 col-sm-6" style="width: 18rem;background-color:#82923d;border-radius:5%;border:5px solid white;">
								<h5 class="card-title" th:text="${file.nome}" style="background-color:#adbaa4;margin-top: 12px;"></h5>
  <img class="card-img-top embed-responsive-item" th:src="${'data:image/jpeg;base64,' + file.fileContent}" alt="Card image cap">
  <div class="card-body">
    
    <p class="card-text" th:text="${file.description}" style="background-color:#adbaa4;"></p>
   
  </div>
</div>	<div class="col-lg-3 col-md-3 col-sm-3 spazio"></div>
				<div class="col-lg-2 col-md-2 col-sm-2 spazio"></div>
				<div class="col-lg-12  col-md-12 col-sm-12 boxCommento">
				<div class="col-lg-12  col-md-12 col-sm-12"><h3>Commenti</h3></div>
				

				<div class="col-lg-12  col-md-12 col-sm-12" th:each="commento : ${file.commenti}">


				<div class="col-lg-12 col-md-12 col-sm-12"><h4 class="commento" th:text="${commento.nomeUtente +' : '+ commento.commento}"></h4></div>

				</div>
				<div class="col-lg-12  col-md-12 col-sm-12"><input th:id="${'addNomeCommento_'+file.id}" placeHolder="Inserisci il tuo nome" class="form-control" type="text"  name="fname"><input th:id="${'addinputCommento_'+file.id}" placeHolder="Inserisci il tuo commento" class="form-control"type="text"  name="fname"><button  th:id="${'addCommento_'+file.id}" class="btn btn-secondary" type="button">Commenta</button><br></div>
	*/

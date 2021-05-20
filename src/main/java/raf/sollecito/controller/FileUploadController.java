package raf.sollecito.controller;



import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
		
		List<Post> list = (List<Post>) postRepository.findAll();
		
		List<String> stringhe = new ArrayList<>();
		
		for(Post post : list) {
			stringhe.add(post.getFilePath());
		}
		
		List<PostFrontend> immagini= new ArrayList<>();
		
		for(Post post : list) {
			File file = new File(post.getFilePath());
			PostFrontend np = new PostFrontend();
			np.setId(post.getId());
			np.setFileContent(Base64.encodeBase64String(Files.readAllBytes(file.toPath())));
			np.setDescription(post.getDescription());
			np.setNome(post.getNome());
			np.setCommenti(post.getCommenti());
			immagini.add(np);
		}
		
		model.addAttribute("files", immagini);

		return "uploadForm";
	}

	@GetMapping("/upload/files/{filename:.+}")
	@ResponseBody
	public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

		Resource file = storageService.loadAsResource(filename);
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
				"attachment; filename=\"" + file.getFilename() + "\"").body(file);
	}

	@PostMapping("/upload")
	public String handleFileUpload(@RequestParam("file") MultipartFile file,@RequestParam("nome") String nome,@RequestParam("descrizione") String descrizione,
			RedirectAttributes redirectAttributes) throws Exception {
		
		String path = storageService.store(file);
		if(path!=null) {
			Post newPost = new Post();
			newPost.setDescription(descrizione);
			newPost.setNome(nome);
			newPost.setFilePath(path);
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
		System.getProperties().list(System.out);
		System.getProperty("user.dir");
		System.out.println("ERRORE : "+exc.getMessage());
		System.out.println("ERRORE : "+exc.printStackTrace());
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
	*/

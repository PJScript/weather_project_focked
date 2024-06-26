package com.example.demo.community.service;

import com.example.demo.cody.entity.ClothsCategory;
import com.example.demo.cody.repo.ClothsCategoryRepository;
import com.example.demo.community.dto.PostDto;
import com.example.demo.community.entity.Post;
import com.example.demo.community.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final ClothsCategoryRepository clothsCategoryRepository;

    // CREATE
    public PostDto createOOTD(String title, String content, String category, MultipartFile imgFile) throws IOException {
        Post post = new Post();
        String oriImgName = imgFile.getOriginalFilename();
        String imgName = "";

        // 파일 저장 위치
        String projectPath = System.getProperty("user.dir") + "/src/main/resources/static/files/";

        post.setTitle(title);
        post.setContent(content);
        post.setCategory(category);

        UUID uuid = UUID.randomUUID(); // 식별자. 랜덤으로 이름 만들어줌

        String savedFileName = uuid + "_" + oriImgName; // 저장될 파일 이름 지정 = 랜덤식별자_원래파일이름

        imgName = savedFileName;

        File saveFile = new File(projectPath, imgName); // 빈껍데기 생성 이름은 imgName, projectPath라는 경로에 담김

        imgFile.transferTo(saveFile);

        post.setImgName(imgName); // 파일 이름
        post.setImgPath("/files/" + imgName); // 저장경로 , 파일 이름

        return PostDto.fromEntity(postRepository.save(post));


    }
    // READ
    // READ All
    public List<PostDto> readAll() {
        List<PostDto> postDtos = new ArrayList<>();
        for(Post post: postRepository.findAllByOrderByCreatedDateDesc()) {
            postDtos.add(PostDto.fromEntity(post));
        }
        return postDtos;
    }

    // READ One
    public PostDto readOne(Long postId) {
        return PostDto.fromEntity(postRepository.findById(postId).orElseThrow());
    }
    // UPDATE
    public void updateOOTD(PostDto postDto, Long postId) {
        Post post = postRepository.findById(postId).orElseThrow();

        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setCategory(postDto.getCategory());

        PostDto.fromEntity(postRepository.save(post));

    }
    // DELETE
    public void deletePost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow();
        postRepository.delete(post);
    }

}

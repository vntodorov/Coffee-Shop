package com.brewbox.service;

import com.brewbox.model.DTOs.CommentDTO;
import com.brewbox.model.DTOs.ProductDTO;
import com.brewbox.model.entity.CommentEntity;
import com.brewbox.model.entity.ProductEntity;
import com.brewbox.model.entity.UserEntity;
import com.brewbox.repository.CommentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    private final CommentRepository commentRepository;

    private final UserService userService;

    private final ProductService productService;

    private final ModelMapper mapper;

    @Autowired
    public CommentService(CommentRepository commentRepository, UserService userService, ProductService productService, ModelMapper mapper) {
        this.commentRepository = commentRepository;
        this.userService = userService;
        this.productService = productService;
        this.mapper = mapper;
    }

    public List<CommentDTO> getAllCommentsForProduct(Long id) {
        return commentRepository.
                findByProductId(id).
                get().
                stream().
                map(this::mapToCommentDTO).
                toList();
    }

    public void addCommentToProduct(CommentDTO commentDTO, UserDetails userDetails, Long pid) {
        CommentEntity comment = mapToCommentEntity(commentDTO);

        UserEntity user = userService.getCurrentUser(userDetails);
        ProductEntity product = mapper.map(productService.getProductById(pid), ProductEntity.class);

        comment.setUser(user);
        comment.setProduct(product);

        commentRepository.save(comment);
    }

    private CommentDTO mapToCommentDTO(CommentEntity comment) {
        return mapper.map(comment, CommentDTO.class);
    }

    private CommentEntity mapToCommentEntity(CommentDTO commentDTO) {
        return mapper.map(commentDTO, CommentEntity.class);
    }

    public void deleteCommentById(Long cid) {
        Optional<CommentEntity> commentById = commentRepository.findById(cid);
        if (commentById.isPresent()){
            CommentEntity comment = commentById.get();
            commentRepository.delete(comment);
        }
    }
}

package com.example.demo.service;

import com.example.demo.domain.User;
import com.example.demo.domain.response.ResUserDTO;
import com.example.demo.domain.response.ResultPaginationDTO;
import com.example.demo.repository.UserRepository;
import com.example.demo.util.error.IdInvalidException;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequestMapping(value = "/v1/user")
public class UserService {
    @Autowired
    private UserRepository userRepository;


    public ResultPaginationDTO fetchAllUser(Specification<User> spec, Pageable pageable) {
        Page<User> pageUser = this.userRepository.findAllByActiveTrue(spec, pageable);
        ResultPaginationDTO rs = new ResultPaginationDTO();
        ResultPaginationDTO.Meta mt = new ResultPaginationDTO.Meta();

        mt.setPage(pageable.getPageNumber() + 1);
        mt.setPageSize(pageable.getPageSize());

        mt.setPages(pageUser.getTotalPages());
        mt.setTotal(pageUser.getTotalElements());

        rs.setMeta(mt);

        // remove sensitive data


        List<ResUserDTO> listUser = pageUser.getContent()
                .stream().map(item -> this.convertToResUserDTO(item))
                .collect(Collectors.toList());

        rs.setResult(listUser);

        return rs;
    }


    public ResUserDTO convertToResUserDTO(User user) {
        ResUserDTO res = new ResUserDTO();
        res.setId(user.getId());
        res.setEmail(user.getEmail());
        res.setBirthday(user.getBirthday());
        res.setCreatedAt(user.getCreatedAt());
        res.setGender(user.getGender());
        res.setFullname(user.getLatename()+user.getFirstname());
        return res;
    }
    public boolean isEmailExist(String email) {
        return this.userRepository.existsByEmail(email);
    }

    public User handleCreateUser(User user) {
        return this.userRepository.save(user);
    }

    public void handleDeleteUser(Long id) throws IdInvalidException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IdInvalidException("User không tồn tại với id: " + id));
        user.setActive(false);
        userRepository.save(user);
    }



}

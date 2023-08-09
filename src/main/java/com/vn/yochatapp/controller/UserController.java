package com.vn.yochatapp.controller;

import com.vn.yochatapp.Constants;
import com.vn.yochatapp.config.security.service.UserDetailsImpl;
import com.vn.yochatapp.entities.AuthUser;
import com.vn.yochatapp.model.AuthUserModel;
import com.vn.yochatapp.model.AvatarRequestModel;
import com.vn.yochatapp.model.CommonResponse;
import com.vn.yochatapp.service.AuthUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    AuthUserService authUserService;

    @Value("${store-folder}")
    private String storeFolder;
    @Value("${store-url}")
    private String storeUrl;

    @GetMapping("/search")
    @PreAuthorize("hasAuthority('USER')")
    public CommonResponse<List<AuthUserModel>> searchUser(@RequestParam("name") String name) {
        CommonResponse<List<AuthUserModel>> response = new CommonResponse<>();
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            List<AuthUser> authUsers = authUserService.searchUserByNameAnhNotId(name, userDetails.getId());
            List<AuthUserModel> authUserModels = new ArrayList<>();
            for(AuthUser authUser : authUsers) {
                authUserModels.add(new AuthUserModel(authUser));
            }
            response.setStatusCode(Constants.RestApiReturnCode.SUCCESS);
            response.setMessage(Constants.RestApiReturnCode.SUCCESS_TXT);
            response.setOutput(authUserModels);
            response.setError("");
            return response;
        } catch (Exception e) {
            logger.error("", e);
            response.setStatusCode(Constants.RestApiReturnCode.SYS_ERROR);
            response.setMessage(Constants.RestApiReturnCode.SYS_ERROR_TXT);
            response.setOutput(null);
            response.setError("");
            return response;
        }
    }

    @GetMapping("")
    @PreAuthorize("hasAuthority('USER')")
    public CommonResponse<AuthUserModel> getUserById(@RequestParam("id") Long id) {
        CommonResponse<AuthUserModel> response = new CommonResponse<>();
        try {
            AuthUser authUser = authUserService.findOne(id);
            if (authUser != null) {
                response.setStatusCode(Constants.RestApiReturnCode.SUCCESS);
                response.setMessage(Constants.RestApiReturnCode.SUCCESS_TXT);
                response.setError("Thành công");
                AuthUserModel user = new AuthUserModel(authUser);
                response.setOutput(user);
            } else {
                response.setStatusCode(Constants.RestApiReturnCode.BAD_REQUEST);
                response.setMessage(Constants.RestApiReturnCode.BAD_REQUEST_TXT);
                response.setError("Không tìm thấy kết quả");
                response.setOutput(null);
            }
            return response;
        } catch (Exception e) {
            logger.error("", e);
            response.setStatusCode(Constants.RestApiReturnCode.SYS_ERROR);
            response.setMessage(Constants.RestApiReturnCode.SYS_ERROR_TXT);
            response.setOutput(null);
            response.setError("Lỗi máy chủ");
            return response;
        }
    }
    @PostMapping(value = "/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public CommonResponse setAvatar(@RequestParam("avatar") MultipartFile avatar) {
        CommonResponse response = new CommonResponse<>();
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            AuthUser authUser = authUserService.findOne(userDetails.getId());

            String fileName = "avatar"+authUser.getId().toString()+avatar.getOriginalFilename();
            String uploadDir = storeFolder + fileName;
            String pathUrl= storeUrl + fileName;

            File dest = new File(uploadDir);
            avatar.transferTo(dest);
            authUser.setAvatarUrl(pathUrl);
            authUserService.update(authUser);

            response.setStatusCode(Constants.RestApiReturnCode.SUCCESS);
            response.setMessage(Constants.RestApiReturnCode.SUCCESS_TXT);
            response.setError("Thành công");
            response.setOutput(null);
            return response;
        } catch (Exception e) {
            logger.error("Save avatar", e);
            response.setStatusCode(Constants.RestApiReturnCode.SYS_ERROR);
            response.setMessage(Constants.RestApiReturnCode.SYS_ERROR_TXT);
            response.setOutput(null);
            response.setError("Lỗi máy chủ");
            return response;
        }
    }
}

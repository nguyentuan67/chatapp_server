package com.vn.yochatapp.controller;

import com.vn.yochatapp.Constants;
import com.vn.yochatapp.config.security.service.UserDetailsImpl;
import com.vn.yochatapp.entities.AuthUser;
import com.vn.yochatapp.model.AuthUserModel;
import com.vn.yochatapp.model.CommonResponse;
import com.vn.yochatapp.service.AuthUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    AuthUserService authUserService;

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
}
